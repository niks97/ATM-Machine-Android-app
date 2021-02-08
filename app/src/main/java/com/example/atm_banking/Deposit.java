package com.example.atm_banking;


public class Deposit {

    private double balance;
    private double dpValue;

    public void setBalance(double b) {

        balance = b;

    }


    public void setDeposit (double dp) {

        dpValue = dp;

    }

    public double getNewBalance() {

        return balance + dpValue;

    }
}
