package com.example.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class AdminActivity extends AppCompatActivity {

    ArrayList<String> entries = new ArrayList<String>();
    ArrayList<String> selectedEntries = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView entriesList;
    String accountName;
    DBHelper dbHelper;

    private SharedPreferences sharedPref;
    private final String saveTableKey = "save_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MainActivity mainActivity = new MainActivity();
        //mainActivity.restoreLocale();
        setContentView(R.layout.activity_admin);

        dbHelper = new DBHelper(this);

        // добавляем начальные элементы
        Collections.addAll(entries);

        Log.i("AppLogger", "Переопределение onCreate у TableActivity");
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText(accountName);

        // получаем элемент ListView
        entriesList = findViewById(R.id.acc_table);
        // создаем адаптер
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, entries);
        // устанавливаем для списка адаптер
        entriesList.setAdapter(adapter);

        // обработка установки и снятия отметки в списке
        entriesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем нажатый элемент
                String entrie = adapter.getItem(position);
                if(entriesList.isItemChecked(position))
                    selectedEntries.add(entrie);
                else
                    selectedEntries.remove(entrie);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.table_menu, menu);
        return true;
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i("AppLogger", "Переопределение onStop у TableActivity");
    }
    @Override
    protected void onStart(){
        restoreTable();
        super.onStart();
        Log.i("AppLogger", "Переопределение onStart у TableActivity");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i("AppLogger", "Переопределение onPause у TableActivity");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("AppLogger", "Переопределение onResume у TableActivity");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("AppLogger", "Переопределение onRestart у TableActivity");
    }

    public void addEntrie(View view){

        EditText entrieET = findViewById(R.id.entrie);
        String entrie  = entrieET.getText().toString();
        if(!entrie.isEmpty()){
            adapter.add(entrie);
            entrieET.setText("");
            adapter.notifyDataSetChanged();
        }
    }
    public void removeEntrie(View view){
        EditText entrieET = findViewById(R.id.adm_acc_entrie);
        String login  = entrieET.getText().toString();
        if(dbHelper.deleteUser(login))
            Toast.makeText(this,
                    "Пользователь " + login + " успешно удалён", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,
                    "Ошибка удаления", Toast.LENGTH_SHORT).show();
        restoreTable();
    }

    private void restoreTable()
    {
        ArrayList<String> list = dbHelper.getDateList();
        adapter.clear();
        entries.clear();
        for(int i=0; i < list.size(); i = i + 2)
        {
            entries.add(list.get(i) + " " + list.get(i+1));
        }

    }

    public void showChangePasswordDialog(MenuItem menuItem){

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog dialog = new Dialog(this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.dialog_table);

        //Initializing the views of the dialog.


        final EditText rePassword = dialog.findViewById(R.id.repass);
        final EditText newPassword = dialog.findViewById(R.id.new_pass);
        Button changePass = dialog.findViewById(R.id.change_pass_bttn);
        Button exitDialog = dialog.findViewById(R.id.ch_pass_close_dialog);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String repass = rePassword.getText().toString();
                String pass = newPassword.getText().toString();

                changePassword(repass, pass);
            }
        });

        exitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout((6 * width)/7, height/2);
        dialog.show();
    }
    private void changePassword(String rePass, String pass){

        Log.i("AppLogger", "rePass = " + rePass + " ; pass = " + pass);

        if(rePass.equals("") || pass.equals("")){
            Toast.makeText(this,
                    "Ошибка. Есть незаполненные поля.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!dbHelper.checkUsernamePassword(accountName, rePass)){
            Toast.makeText(this,
                    "Ошибка. Неправильный пароль.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.changePassword(accountName, rePass, pass)){
            Toast.makeText(this,
                    "Пароль успешно изменён", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Ошибка при изменении пароля", Toast.LENGTH_SHORT).show();
        }

        return;

    }
}