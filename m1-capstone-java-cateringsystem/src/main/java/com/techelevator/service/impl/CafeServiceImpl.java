package com.techelevator.service.impl;

import com.techelevator.model.*;
import com.techelevator.service.CafeService;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class CafeServiceImpl implements CafeService {
    double currentAccountBalance = 0;

    /*
    This class intended to group methods specific to action related to cafe such as get inventory, add To AccountBalance
    give change, log file
     */
    List<String> list = new ArrayList<String>(); // we have used list in order to store cafe log data

    public Map<String, Item> getInventory(File inputFile) throws FileNotFoundException { // this allows us to read and categorize our inventory
        Map<String, Item> inventory = new LinkedHashMap<String, Item>();

        try(Scanner inventoryFile = new Scanner(inputFile)){

            while(inventoryFile.hasNextLine()) {
                String item = inventoryFile.nextLine();
                String[] parts = item.split(Pattern.quote("|"));
                Item newItem;

                if(parts[3].equalsIgnoreCase("A")) {

                    /*

                    If you do not have constructor for each item here you will have runtime errors
                    constructor Appetizers in class com.techelevator.model.Appetizers cannot be applied to given types;
                    required: no arguments

                     */

                    newItem = new Appetizers(parts[1], Double.parseDouble(parts[2]));
                    inventory.put(parts[0], newItem);
                } else if(parts[3].equalsIgnoreCase("B")) {
                    newItem = new Beverage(parts[1], Double.parseDouble(parts[2]));
                    inventory.put(parts[0], newItem);
                } else if(parts[3].equalsIgnoreCase("D")) {
                    newItem = new Dessert(parts[1], Double.parseDouble(parts[2]));
                    inventory.put(parts[0], newItem);
                } else if(parts[3].equalsIgnoreCase("E")) {
                    newItem = new Entree(parts[1], Double.parseDouble(parts[2]));
                    inventory.put(parts[0], newItem);
                }
            }
        }
        return inventory;
    }

    public void addToAccountBalance(double addMoney) { // this allows deposits to be made as well as logs any deposits made onto log
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDateTime = time.format(timeFormat);
        DecimalFormat myFormat = new DecimalFormat("#0.00");

        if((currentAccountBalance + addMoney) <= 5000.00) {
            currentAccountBalance = currentAccountBalance + addMoney;
            String logInput = formattedDateTime + " ADD MONEY: " + myFormat.format(addMoney) + "  " + myFormat.format(currentAccountBalance);
            list.add(logInput);
        } else {
            System.out.println("\n*** MAXIMUM ALLOWED IS $5000.00 ***\n ");
        }
    }

    public void giveChangeAndPrintReceipt() { // this gives the customer back their money, prints a receipt on the console, and logs onto log.txt
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDateTime = time.format(timeFormat);
        DecimalFormat myFormat = new DecimalFormat("#0.00"); //75-78 is setup with built in methods from object library
        Double[] change = new Double[] {20.0, 10.0, 5.0, 2.0, 1.0, 0.25, 0.10, 0.05, };
        String[] moneyName = new String[] {"Twenty(s)","Ten(s)", "Five(s)", "Two(s)", "One(s)", "Quarter(s)", "Dime(s)", "Nickle(s)"};
        String logInput = formattedDateTime + " GAVE CHANGE: " + myFormat.format(currentAccountBalance) + " $0.00";

        list.add(logInput);
        System.out.println("--------------------");
        System.out.println("Change Given: $" + myFormat.format(currentAccountBalance));
        System.out.println("--------------------");

        for(int i = 0; i < change.length; i++) {
            int counter;
            counter = (int) (currentAccountBalance / change[i]);
            currentAccountBalance -= (change[i] * counter);
            System.out.println(counter + " " + moneyName[i]);
        }
        System.out.println("--------------------");
        System.out.println("  Come back again!  ");
        System.out.println("--------------------\n");
    }
/*
    public void logFile() throws IOException  { // allows rest of code to access our desired log.txt file
        File outputFile = new
                File("C:\\Users\\Student\\source\\repos\\team4-java-blue-week04-pair-exercises\\m1-capstone-java-cateringsystem\\log.txt");
        List<String> list = getList();
        try(FileWriter logWriter = new FileWriter(outputFile, true)){
            for(String logInput : list) {
                logWriter.write(logInput);
                logWriter.write("\n");
            }
        }
    }*/

    public List<String> logPurchase(int amountSold, String name, double beginningAmount, double endAmount) { // allows us to log whenever a purchase is made
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formattedDateTime = time.format(timeFormat);
        DecimalFormat myFormat = new DecimalFormat("#.00");
        String logInput = formattedDateTime + "  " + amountSold + "  " + name + "  " + myFormat.format(beginningAmount) + "  " + myFormat.format(endAmount);
        list.add(logInput);
        return list;
    }

    public List<String> getList(){ // just a getter
        return this.list;
    }

    public double getCurrentAccountBalance() { // just a getter
        return currentAccountBalance;
    }

    public void displayCateringItems(Map<String, Item> inventoryMap, Menu menu) { // Menu menu  adds object as a parameter
        while(true) {
            String[] itemsForSale = new String[inventoryMap.size()];
            Set<Map.Entry<String, Item>> entrySet = inventoryMap.entrySet();
            int count = 0; // helps display current inventory if a purchase is made and we go back to display items

            for (Map.Entry<String, Item> item: entrySet) {
                String itemName = item.getKey();
                Item itemCost = item.getValue();
                itemsForSale[count] = itemName + " " + itemCost.toString();
                count++;
            }
            menu.displayCateringItems(itemsForSale);
            break;
        }
    }

}