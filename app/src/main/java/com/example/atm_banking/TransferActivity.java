package com.example.atm_banking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class TransferActivity extends AppCompatActivity {


    public static final String MY_BALANCE = "My_Balance";
    public static final String CHECKING_KEY = "checking_key";
    public static final String SAVINGS_KEY = "savings_key";

    public String receivedBalanceC, receivedBalanceS;
    public DecimalFormat currency = new DecimalFormat("$###,##0.00");
    TextView cBalanceTV, sBalanceTV;
    public double cBalanceD, sBalanceD, cNewBalance, sNewBalance;
    public double TransferEntered;
    int transferChoice;


    SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            receivedBalanceC = extras.getString("balanceC");
            receivedBalanceS = extras.getString("balanceS");


        }
        cBalanceTV = (TextView) findViewById(R.id.cBalanceTextView);
        cBalanceD = Double.parseDouble(String.valueOf(receivedBalanceC));
        cBalanceTV.setText(String.valueOf(currency.format(cBalanceD)));

        sBalanceTV = (TextView) findViewById(R.id.sBalanceTextView);
        sBalanceD = Double.parseDouble(String.valueOf(receivedBalanceS));
        sBalanceTV.setText(String.valueOf(currency.format(sBalanceD)));



        final EditText TransferET = (EditText) findViewById(R.id.TransferEditText);
        final Spinner TransferS = (Spinner) findViewById(R.id.TransferSpinner);
        Button transferB= (Button) findViewById(R.id.TransferButton);


        transferB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(TransferET.getText())) {

                    TransferEntered = Double.parseDouble(String.valueOf(TransferET.getText()));
                    transferChoice = TransferS.getSelectedItemPosition();

                    switch (transferChoice) {

                     case 0:


                             if (cBalanceD >= TransferEntered) {


                                 Withdraw wd = new Withdraw();
                                 wd.setBalance(cBalanceD);
                                 wd.setWithdraw(TransferEntered);
                                 cNewBalance = wd.getNewBalance();

                                 cBalanceTV.setText(String.valueOf(currency.format(cNewBalance)));
                                 cBalanceD = cNewBalance;


                                 Deposit dp = new Deposit();
                                 dp.setBalance(sBalanceD);
                                 dp.setDeposit(TransferEntered);


                                 sNewBalance = dp.getNewBalance();
                                 sBalanceTV.setText(String.valueOf(currency.format(sNewBalance)));
                                 sBalanceD = sNewBalance;


                                 TransferEntered = 0;
                             }
                             else {

                                 noFundsMsg();
                             }
                         return;


                     case 1:


                             if (sBalanceD >= TransferEntered) {


                                     Withdraw wd = new Withdraw();
                                     wd.setBalance(sBalanceD);
                                     wd.setWithdraw(TransferEntered);

                                     sNewBalance = wd.getNewBalance();

                                     sBalanceTV.setText(String.valueOf(currency.format(sNewBalance)));
                                     sBalanceD = sNewBalance;


                                     Deposit dp = new Deposit();
                                     dp.setBalance(cBalanceD);
                                     dp.setDeposit(TransferEntered);


                                     cNewBalance = dp.getNewBalance();

                                     cBalanceTV.setText(String.valueOf(currency.format(cNewBalance)));
                                     cBalanceD = cNewBalance;


                                     TransferEntered = 0;
                                 }

                             else {


                                 noFundsMsg();
                             }

                         return;
                 }
                }

                else {


                    noAmountMsg();
                }
            }
        });
    }


    protected void onPause() {

        super.onPause();
        myEditor = getSharedPreferences(MY_BALANCE, MODE_PRIVATE).edit();


        myEditor.putString(CHECKING_KEY, String.valueOf(cBalanceD));
        myEditor.putString(SAVINGS_KEY, String.valueOf(sBalanceD));
        myEditor.apply();

    }
    public  void noFundsMsg() {

        Toast.makeText(TransferActivity.this, "Insufficient funds! Please enter a valid transfer amount and try again!", Toast.LENGTH_LONG).show();
    }
    public  void noAmountMsg()
    {

        Toast.makeText(TransferActivity.this, "Nothing entered! Please enter transfer amount and try again!", Toast.LENGTH_LONG).show();
    }
}
