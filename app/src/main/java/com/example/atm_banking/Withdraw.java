package com.example.atm_banking;



public class Withdraw {

    private double balance;
    private double wdValue;


    public void setBalance(double b) {

        balance = b;

    }
    public void setWithdraw (double wd) {


        wdValue = wd;
    }

    public double getNewBalance() {

        return balance - wdValue;


    }
}
