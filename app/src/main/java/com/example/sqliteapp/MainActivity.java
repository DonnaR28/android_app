package com.example.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText username, password, email, phone;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.name);
        password = findViewById(R.id.Password);
        email = findViewById(R.id.EmailAddress);
        phone = findViewById(R.id.Phoneno);

        insert = findViewById(R.id.button1);
        update = findViewById(R.id.button4);
        delete = findViewById(R.id.button2);
        view = findViewById(R.id.button3);

        DB = new DBHelper(this);

        insert.setOnClickListener(v -> {
            if (areFieldsFilled()) {
                String userTXT = username.getText().toString();
                String passTXT = password.getText().toString();
                String emailTXT = email.getText().toString();
                String phoneTXT = phone.getText().toString();

                Boolean checkInsertData = DB.insertUserData(userTXT, passTXT, emailTXT, phoneTXT);
                if (checkInsertData)
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(v -> {
            if (areFieldsFilled()) {
                String userTXT = username.getText().toString();
                String passTXT = password.getText().toString();
                String emailTXT = email.getText().toString();
                String phoneTXT = phone.getText().toString();

                Boolean checkUpdateData = DB.updateUserData(userTXT, passTXT, emailTXT, phoneTXT);
                if (checkUpdateData)
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(v -> {
            String userTXT = username.getText().toString();
            if (!userTXT.isEmpty()) {
                Boolean checkDeleteData = DB.deleteUser(userTXT);
                if (checkDeleteData)
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter the username to delete", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(v -> {
            Cursor cursor = DB.viewAllData();
            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "No Records Found", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                builder.append("Username: ").append(cursor.getString(1)).append(", ");
//                builder.append("Password: ").append(cursor.getString(2)).append(", ");
                builder.append("Email: ").append(cursor.getString(3)).append(", ");
                builder.append("Phone: ").append(cursor.getString(4)).append("\n");
            }
            cursor.close();

            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setCancelable(true);
            builder1.setTitle("User Entries");
            builder1.setMessage(builder.toString());
            builder1.show();
        });
    }
    private boolean areFieldsFilled() {
        return !username.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                !email.getText().toString().isEmpty() &&
                !phone.getText().toString().isEmpty();
    }
}
