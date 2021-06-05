import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import view.Menu;

public class VendingMachineCLI {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Catering Items";
    private static final String MAIN_MENU_OPTION_ORDER = "Order";
    private static final String MAIN_MENU_OPTION_QUIT = "Quit";
    private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_ORDER, MAIN_MENU_OPTION_QUIT};
    private static final String SUB_MENU_ADD_MONEY = "Add Money";
    private static final String SUB_MENU_SELECT_PRODUCT = "Select Product";
    private static final String SUB_MENU_FINISH_TRANSACTION = "Complete Transaction";
    private static final String[] SUB_MENU_OPTIONS = {SUB_MENU_ADD_MONEY, SUB_MENU_SELECT_PRODUCT, SUB_MENU_FINISH_TRANSACTION};

    private Menu menu;
    List<Product> purchasedObjects = new ArrayList<Product>();

    public VendingMachineCLI(Menu menu) {

        this.menu = menu;
    }


    public void run() throws IOException {
        Cafe cafe = new Cafe();
        File file = cafe.getInputFile();
        Map<String, Product> inventoryMap = cafe.getInventory(file);
        menu.showWelcomeMessage();
        menu.getInventoryPathFromUser();

        while(true) {
            String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS, cafe.getBalance());

            if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                while(true) {
                    String[] productArray = new String[inventoryMap.size()];
                    int count = 0;
                    Set<Map.Entry<String, Product>> entrySet = inventoryMap.entrySet();
                    for (Entry<String, Product> entry: entrySet) {
                        String key = entry.getKey();
                        Product value = entry.getValue();
                        productArray[count] = key + " " + value.toString();
                        count++;
                    }
                    menu.displayMenuOptionsForItems(productArray);
                    break;
                }
            } if(choice.equals(MAIN_MENU_OPTION_ORDER)) {

                while(true) {
                    String choice2 = (String)menu.getChoiceFromOptions(SUB_MENU_OPTIONS, cafe.getBalance());
                    if(choice2.toUpperCase() == "R") {
                        break;
                    }
                    if(choice2.equals(SUB_MENU_ADD_MONEY)) {
                        while(true) {
                            try {
                                System.out.println("Enter amount you would like to add to your balance or (R)eturn to previous menu ");

                                Scanner in = new Scanner(System.in);
                                String input = in.nextLine();

                                if(input.toUpperCase().equals("R")) {
                                    break;
                                } else {
                                    double amountEntered = Double.parseDouble(input);
                                    cafe.addBalance(amountEntered);
                                    System.out.println("Current balance $" + cafe.getBalance());
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Your Account Balance should not exceed $5000 "); // : needs escaped!
                            }
                        }

                    } else if(choice2.equals(SUB_MENU_SELECT_PRODUCT)) {
                        while(true) {
                            System.out.println("Please enter the item you would like to purchase or (R)eturn to previous menu ");

                            Scanner in = new Scanner(System.in);
                            String input = in.nextLine();

                            if(input.toUpperCase().equals("R")) {
                                break;

                            } else if(inventoryMap.containsKey(input.toUpperCase())) {
                                if(inventoryMap.get(input).isAvailableToPurchase() && cafe.balance >= inventoryMap.get(input).getPrice()) {
                                    inventoryMap.get(input).purchaseItem();
                                    purchasedObjects.add(inventoryMap.get(input));
                                    cafe.balance -= inventoryMap.get(input).getPrice();
                                    cafe.log(inventoryMap.get(input).getName(), (cafe.balance + inventoryMap.get(input).getPrice()), cafe.balance);
                                    System.out.println("Purchase Successful" );

                                } else if(!inventoryMap.get(input).isAvailableToPurchase()) {
                                    System.out.println("ITEM HAS BEEN SOLD OUT!");
                                    break;
                                } else {
                                    System.out.println("Insufficient Funds, Please add to the balance!");
                                    break;
                                }

                            } else {
                                System.out.println("That is not a valid item code, try again");
                                break;
                            }
                        }

                    } else if(choice2.equals(SUB_MENU_FINISH_TRANSACTION)){
                        cafe.returnChange();
                        cafe.logFile();
                        cafe.balance = 0;
                        System.out.println("Current Account Balance: $" + cafe.getBalance());
                        System.out.println("");


                        break;
                    }

                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        try {
            cli.run();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}