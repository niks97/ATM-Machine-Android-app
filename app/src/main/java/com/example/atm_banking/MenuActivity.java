package com.example.atm_banking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {


    public static final String MY_BALANCE = "My_Balance";
    public static final String CHECKING_KEY = "checking_key";
    public static final String SAVINGS_KEY = "savings_key";

    String receivedString;
    public String chkBalance, savBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            receivedString = extras.getString("stringReference");
            Toast.makeText(MenuActivity.this, receivedString, Toast.LENGTH_LONG).show();
        }
        getPrefs();

        Button checking_BT = (Button) findViewById(R.id.checkingButton);
        Button savings_BT = (Button) findViewById(R.id.savingsButton);
        Button transfer_BT = (Button) findViewById(R.id.transferButton);


        checking_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user wants to access checking account
                Intent checkingIntent = new Intent(MenuActivity.this, TransactionActivity.class);
                //send only data related to checking account
                checkingIntent.putExtra("balance", chkBalance); //checking balance
                checkingIntent.putExtra("key", CHECKING_KEY); //key used to store checking balance
                checkingIntent.putExtra("title", "Checking Account"); //title for transaction activity
                //display transaction activity screen
                startActivity(checkingIntent);

            }
        });


        savings_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent savingsIntent = new Intent(MenuActivity.this, TransactionActivity.class);
                savingsIntent.putExtra("balance", savBalance); //savings balance
                savingsIntent.putExtra("key", SAVINGS_KEY); //key used to store savings balance
                savingsIntent.putExtra("title", "Savings Account"); //title for transaction activity
                startActivity(savingsIntent);

            }
        });


        transfer_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transferIntent = new Intent(MenuActivity.this, TransferActivity.class);
                transferIntent.putExtra("balanceC", chkBalance); //checking balance
                transferIntent.putExtra("balanceS", savBalance); //savings balance
                startActivity(transferIntent);

            }
        });
    }


    protected void onResume() {
        super.onResume();
        getPrefs();

    }

    public void getPrefs() {

        SharedPreferences BalancePref = getSharedPreferences(MenuActivity.MY_BALANCE, MODE_PRIVATE);

        chkBalance = BalancePref.getString(CHECKING_KEY, "5000.00");
        savBalance = BalancePref.getString(SAVINGS_KEY, "7000.00");

    }
}
