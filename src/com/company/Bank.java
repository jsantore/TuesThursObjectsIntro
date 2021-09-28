package com.company;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Bank {
    private ArrayList<BankAccount> allAccounts;
    private ArrayList<Customer> allCustomers;

    public Bank(){
        allAccounts = new ArrayList<BankAccount>();
        allCustomers = new ArrayList<Customer>();
    }

    public void doBanking(){
        var menuReader = new Scanner(System.in);
        while(true){
            printMenu();
            var userChoice = menuReader.nextInt();
            switch (userChoice){
                case 1:
                    System.exit(0);
                case 2:
                    addCustomer(menuReader);
                    break;
                case 3:
                    Optional<Customer> current = selectCustomer(menuReader);
                    if(current.isPresent())
                        doCustomerMenu(menuReader, current.get());
                    else
                        System.out.println("No customer with that ID found");
                    break;
                case 4:
                    doYearlyMaintenance();
                    break;
                default:
                    System.out.println("Please choose one of the menu options");
            }
        }
    }

    private void doYearlyMaintenance() {
        //for each account - call addInterest and then print account info
        for (var currentAccount: allAccounts){
            currentAccount.addInterest();
            System.out.println("Account ID: "+ currentAccount.getAccountID() + " has balance of "
                    + currentAccount.checkBalance());
        }

    }

    private void doCustomerMenu(Scanner menuReader, Customer currentCustomer) {
        while(true){
            printCustomerMenu();
            var customerChoice = menuReader.nextInt();
            switch (customerChoice){
                case 1:
                    openCustomerAccount(menuReader, currentCustomer);
                    break;
                case 2:
                    closeCustomerAccount(menuReader, currentCustomer);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Input, please choose one of the options listed");
            }
        }
    }

    private void closeCustomerAccount(Scanner menuReader, Customer currentCustomer) {
        //ask the user what account number to close
        System.out.print("Enter account number of account to close:");
        var accountNum = menuReader.nextInt();
        //call close account on the customer passing that number
        Optional<BankAccount> accountToClose = currentCustomer.closeAccount(accountNum);
        //if the close succeeded remove the account from allAccounts
        if(accountToClose.isPresent()){
            allAccounts.remove(accountToClose.get());
        }
    }

    private void openCustomerAccount(Scanner menuReader, Customer currentCustomer) {
        //ask the user how much money the starting deposit is
        System.out.println("Creating new account....");
        System.out.print("What is the initial deposit for the account: ");
        var initialDeposit = menuReader.nextDouble();
        //call open account on the customer
        var newAccount = currentCustomer.openAccount(initialDeposit);
        //add the new account to allAccounts
        allAccounts.add(newAccount);
    }

    private void printCustomerMenu() {
        System.out.println("****************************************");
        System.out.println("What do you want to do with this Customer");
        System.out.println("   [1] Open an account");
        System.out.println("   [2] Close an account");
        System.out.println("   [3] Return to Main menu");
        System.out.println("****************************************");
        System.out.print(" Enter choice: ");
    }

    private Optional<Customer> selectCustomer(Scanner reader) {
        System.out.print("Customer Id of customer to select:");
        var idToFind = reader.nextInt();
        for (var currentCustomer: allCustomers){
            if(currentCustomer.getId() == idToFind)
                return Optional.of(currentCustomer);
        }
        return Optional.empty();
    }

    private void addCustomer(Scanner inputReader) {
        System.out.print("What is the new Customer's name:");
        inputReader.nextLine(); //eat the orphan newline from previous nextInt call
        var customerName = inputReader.nextLine();
        System.out.print("What is the new Customer's Tax Id (SSN):");
        var taxId = inputReader.nextInt();
        var newCustomer = new Customer(customerName, taxId);
        allCustomers.add(newCustomer);
    }

    private void printMenu() {
        System.out.println("===================================================");
        System.out.println("what would you like to do next (select the number):");
        System.out.println("   [1] Exit the program");
        System.out.println("   [2] Add a customer");
        System.out.println("   [3] Select customer by ID");
        System.out.println("   [4] Do the yearly maintenance and show all accounts");
        System.out.println("===================================================");
        System.out.print("Enter choice:");
    }

}
