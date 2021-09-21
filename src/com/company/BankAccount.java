package com.company;
public class BankAccount {
    private double balance;
    private float interestRate;
    private int accountID;
    private static int nextId = 100;

    public BankAccount(){
        interestRate = 0.02f;
        accountID = nextId;
        nextId++;  //same as nextID = nextId + 1
    }
    public BankAccount(double initialBalance, float initialRate){
        balance = initialBalance;
        interestRate = initialRate;
        accountID = nextId;
        nextId += 1;
    }

    public int getAccountID(){
        return accountID;
    }
    public void deposit(double amount){
        balance += amount;
    }
    public boolean withdraw(double amount){
        if(amount > balance){
            return false;
        }
        balance = balance - amount;
        return true;
    }
    public double checkBalance(){
        return balance;
    }
    public double addInterest(){
        balance +=  interestRate*balance;
        return balance;
    }
}
