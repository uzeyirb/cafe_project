package com.techelevator.util;

public interface Constants {

    //Methods inside interface are by default abstract you cannot create object from it
    //if you want create concrete method you shall write static or default
    //variable inside interface are by default public static final


    String DISPLAY_CATERING_ITEMS = "Display Catering Items"; // these just make our lives easier below
    String ORDER = "Order";
    String QUIT = "Quit";
    String[] MAIN_MENU = { DISPLAY_CATERING_ITEMS, ORDER, QUIT};
    String ADD_MONEY = "Add Money";
    String SELECT_PRODUCTS = "Select Products";
    String COMPLETE_TRANSACTION = "Complete Transaction";
    String[] ORDER_MENU_OPTIONS = {ADD_MONEY, SELECT_PRODUCTS, COMPLETE_TRANSACTION};
}
