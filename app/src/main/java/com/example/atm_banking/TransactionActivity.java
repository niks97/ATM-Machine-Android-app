package com.example.atm_banking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
public class TransactionActivity extends AppCompatActivity {

    public static final String MY_BALANCE = "My_Balance";


    public String receivedBalance, receivedKey, receivedTitle; //data received from menu activity
    public double BalanceD;
    public double DepositEntered, NewBalance, WithdrawEntered;
    TextView BalanceTV, TitleTV;
    public DecimalFormat currency = new DecimalFormat("$###,##0.00"); //decimal formatting
    SharedPreferences.Editor myEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);



        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            receivedBalance = extras.getString("balance");
            receivedKey = extras.getString("key");
            receivedTitle = extras.getString("title");

        }
        TitleTV = (TextView) findViewById(R.id.titleTextView);
        TitleTV.setText(receivedTitle);


        BalanceTV = (TextView) findViewById(R.id.BalanceTextView);
        BalanceD = Double.parseDouble(String.valueOf(receivedBalance));
        BalanceTV.setText(String.valueOf(currency.format(BalanceD)));


        Button DepositB = (Button) findViewById(R.id.DepositButton);
        final EditText DepositET = (EditText) findViewById(R.id.DepositEditText);


        DepositB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(DepositET.getText())) {
                    DepositEntered = Double.parseDouble(String.valueOf(DepositET.getText()));
                    Deposit dp = new Deposit();
                    dp.setBalance(BalanceD);
                    dp.setDeposit(DepositEntered);

                    NewBalance = dp.getNewBalance();
                    BalanceTV.setText(String.valueOf(currency.format(NewBalance)));
                    BalanceD = NewBalance;

                    DepositEntered = 0;
                }
                else {

                    Toast.makeText(TransactionActivity.this, "Please enter deposit amount and try again!", Toast.LENGTH_LONG).show();
                }
                DepositET.setText(null);
            }
        });


        Button WithdrawB = (Button) findViewById(R.id.WithdrawButton);

        final EditText WithdrawET = (EditText) findViewById(R.id.WithdrawEditText);


        WithdrawB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(WithdrawET.getText())) {

                    WithdrawEntered = Double.parseDouble(String.valueOf(WithdrawET.getText()));


                    if (BalanceD >= WithdrawEntered) {

                        Withdraw wd = new Withdraw();
                        wd.setBalance(BalanceD);
                        wd.setWithdraw(WithdrawEntered);


                        NewBalance = wd.getNewBalance();

                        BalanceTV.setText(String.valueOf(currency.format(NewBalance)));
                        BalanceD = NewBalance;

                        WithdrawEntered = 0;
                    }
                    else
                        Toast.makeText(TransactionActivity.this, "Insufficient funds! Please enter a valid withdraw amount and try again!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(TransactionActivity.this, "Nothing entered! Please enter withdraw amount and try again!", Toast.LENGTH_LONG).show();
                }


                WithdrawET.setText(null);
            }
        });//end Withdraw onClick




    }
    protected void onPause() {

        super.onPause();

        myEditor = getSharedPreferences(MY_BALANCE, MODE_PRIVATE).edit();


        myEditor.putString(receivedKey, String.valueOf(BalanceD));
        myEditor.apply();

    }

}



