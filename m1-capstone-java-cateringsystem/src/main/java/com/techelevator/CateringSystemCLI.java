package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.model.Item;
import com.techelevator.service.CafeService;
import com.techelevator.service.CateringService;
import com.techelevator.view.Menu;

public class CateringSystemCLI {
	/*
	As this is our first ever code that has spanned for more than 3 classes to make it more readable we have long
	variable names to make it clear of what is happening.
	 */
	private static final String DISPLAY_CATERING_ITEMS = "Display Catering Items"; // these just make our lives easier below
	private static final String ORDER = "Order";
	private static final String QUIT = "Quit";
	private static final String[] MAIN_MENU = { DISPLAY_CATERING_ITEMS, ORDER, QUIT};
	private static final String ADD_MONEY = "Add Money";
	private static final String SELECT_PRODUCTS = "Select Products";
	private static final String COMPLETE_TRANSACTION = "Complete Transaction";
	private static final String[] ORDER_MENU_OPTIONS = {ADD_MONEY, SELECT_PRODUCTS, COMPLETE_TRANSACTION};
	private Menu menu;
	List<Item> purchasedItems = new ArrayList<Item>(); // keeps track of what has been removed(purchased)

	public CateringSystemCLI(Menu menu) { // makes our lives easier below
		this.menu = menu;
	}

	public void run() throws IOException { // the engine that powers the machine!!


	}


	public static void main(String[] args) throws IOException { // runs the WHOLE SHOW
		/*
		Signals that an I/O exception of some sort has occurred.
		This class is the general class of exceptions produced by failed or interrupted I/O operations
		Constructs an IOException with the specified detail message and cause.
		 */
		Menu menu = new Menu(System.in, System.out);
		CateringService cateringService = new CateringService(menu);
		try {
			cateringService.run();
		} catch (FileNotFoundException e) {
			/*
			The printStackTrace() method of Java.lang.Throwable class used to print this Throwable
			along with other details like class name and line number where the exception occurred means its backtrace.
			This method prints a stack trace for this Throwable object on the standard error output stream.
			 */
			e.printStackTrace();
		}
	}

}