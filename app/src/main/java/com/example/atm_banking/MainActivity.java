package com.example.atm_banking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    String UserName, Password;
    int loginCntr = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(MainActivity.this, "Welcome User. Please enter your Username and Password.", Toast.LENGTH_LONG).show();


        Button myButton = (Button) findViewById(R.id.loginButton);
        final EditText myUserName = (EditText) findViewById(R.id.usernameEditText);
        final EditText myPassword = (EditText) findViewById(R.id.passwordEditText);

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                UserName = myUserName.getText().toString();
                Password = myPassword.getText().toString();


                if (UserName.equals("1") && Password.equals("1")) {

                    Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
                    myIntent.putExtra("stringReference", "Access Granted!");
                    startActivity(myIntent);

                }
                else if (loginCntr != 1) {

                    loginCntr = loginCntr - 1;

                    Toast.makeText(MainActivity.this, "Access Denied! Please try again.You have " + loginCntr + " attempt(s) remaining", Toast.LENGTH_LONG).show();
                }
                else {

                    Toast.makeText(MainActivity.this, "Access Denied! Closing app!", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}
