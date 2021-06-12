package com.techelevator.service.impl;

import com.techelevator.model.Item;
import com.techelevator.service.CateringService;
import com.techelevator.util.Constants;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class CateringServiceImpl implements CateringService {

	/*
	As this is our first ever code that has spanned for more than 3 classes to make it more readable we have long
	variable names to make it clear of what is happening.
	 */
	TransactionServiceImpl transactionService = new TransactionServiceImpl();
	CafeServiceImpl cafeService = new CafeServiceImpl();



	private Menu menu;
	List<Item> purchasedItems = new ArrayList<Item>(); // keeps track of what has been removed(purchased)

	public CateringServiceImpl(Menu menu) { // makes our lives easier below
		this.menu = menu;
	}

	public void run() throws IOException { // the engine that powers the machine!!
		menu.showWelcomeMessage();
		/*
		Code between line 34 and 150 is perfect example for control flow of JAVA here based on the input from console
		 */

		File givenFilePath = new File(menu.getInventoryPathFromUser());
		Map<String, Item> inventoryMap = cafeService.getInventory(givenFilePath);

		menu.showWelcomeMessage();

		while(true) {
			String choicesForMainMenu = menu.getChoiceFromOptions( Constants.MAIN_MENU, cafeService.getCurrentAccountBalance());

			if(choicesForMainMenu.equals(Constants.DISPLAY_CATERING_ITEMS)) {

				this.cafeService.displayCateringItems(inventoryMap, menu); // you send the object to
			}

			if(choicesForMainMenu.equals(Constants.ORDER)) {

				createOrder(cafeService, inventoryMap);
			}

			if(choicesForMainMenu.equals(Constants.QUIT)) {
				System.out.println("\nThank you for your business!"); // always thank the customer!
				menu.stopApplication();
			}
		}
	}



	private void createOrder(CafeServiceImpl cafe, Map<String, Item> inventoryMap) throws IOException {
		while(true) {
			String choicesForOrderMenu = menu.getChoiceFromOptions(Constants.ORDER_MENU_OPTIONS, cafe.getCurrentAccountBalance());

			if(choicesForOrderMenu.equals(Constants.ADD_MONEY)) {
				transactionService.addMoney(cafe);

			} else if(choicesForOrderMenu.equals(Constants.SELECT_PRODUCTS)) {
				selectProduct(cafe, inventoryMap);

			} else if(choicesForOrderMenu.equals(Constants.COMPLETE_TRANSACTION)){
				transactionService.completeTransaction(cafe);
				break;
			}

			else {
				System.out.println("\n*** This is not a valid option, please enter a choice from above. ***\n"); //letter
			}

		}
	}

	private void selectProduct(CafeServiceImpl cafe, Map<String, Item> inventoryMap) {
		while(true) {
			System.out.println("\nPlease enter the item code(1st) and amount you'd like(2nd) one at a time. Example: B1(item code) 10(amount)");
			System.out.println("Once done adding to cart, press (R) to return to last page.");
			Scanner in = new Scanner(System.in);
			String input = in.next().toUpperCase().trim(); // takes the user input and grabs the item code

			if (input.equalsIgnoreCase("R")) {
				System.out.println();
				break;
			}

			String inputtedAmountOfItem = in.next().trim(); // takes the user input and grabs the amount requested

			if (inventoryMap.containsKey(input)) {

				if (inventoryMap.get(input).isAvailableToPurchase() &&
						cafe.currentAccountBalance >= inventoryMap.get(input).getPrice() * Integer.parseInt(inputtedAmountOfItem) &&
						Integer.parseInt(inputtedAmountOfItem) <= (inventoryMap.get(input).getNumberOfItems())) {

					cafe.logPurchase(Integer.parseInt(inputtedAmountOfItem), inventoryMap.get(input).getName(),
							(Double.parseDouble(inputtedAmountOfItem) * inventoryMap.get(input).getPrice()), cafe.currentAccountBalance);

					for (int i = 1; i <= Integer.parseInt(inputtedAmountOfItem); i++) {
						inventoryMap.get(input).removeItem(); //removeItem() - this comes from item abstract class
						purchasedItems.add(inventoryMap.get(input));
						cafe.currentAccountBalance -= inventoryMap.get(input).getPrice();
					}
					System.out.println("\nAdded to cart!");
				}
				else if (!inventoryMap.get(input).isAvailableToPurchase()) {
					System.out.println("\n*** Sorry " + inventoryMap.get(input).getName() + " is sold out! ***\n");
				}
				else if (Integer.parseInt(inputtedAmountOfItem) > (inventoryMap.get(input).getNumberOfItems())) {
					System.out.println("\n*** There is only " + inventoryMap.get(input).getNumberOfItems() + " " + inventoryMap.get(input).getName() +
							" left. Please change amount or choose other item. ***\n");
				} else {
					System.out.println("\n*** Insufficient Funds, Please make a deposit! ***\n");
					break;
				}
			}
			else{
				System.out.println("\n*** Something went wrong, please try again. ***\n");
				break;
			}
		}
	}


}