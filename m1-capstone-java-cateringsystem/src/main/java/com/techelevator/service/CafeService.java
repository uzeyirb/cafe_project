package com.techelevator.service;

import com.techelevator.model.Item;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CafeService {
    Map<String, Item> getInventory(File inputFile) throws FileNotFoundException;

    void addToAccountBalance(double addMoney);

    void giveChangeAndPrintReceipt();

   /* void logFile() throws IOException;*/

    List<String> logPurchase(int amountSold, String name, double beginningAmount, double endAmount);

    double getCurrentAccountBalance();

    void displayCateringItems(Map<String, Item> inventoryMap, Menu menu);

    List<String> getList();

}
