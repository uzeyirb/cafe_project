package com.techelevator.service.impl;

import com.techelevator.service.LogService;
import com.techelevator.service.TransactionService;

import java.io.IOException;
import java.util.Scanner;

public class TransactionServiceImpl implements TransactionService {

LogService logService = new LogServiceImpl();
    public void completeTransaction(CafeServiceImpl cafe) throws IOException {
        cafe.giveChangeAndPrintReceipt();
        logService.logFile(cafe.getList()); //log file methodunu log service cixartdiq list olmadigina gore ona parametr elave eledik.
        // log service classdan log file methodu cagiranda cafedeki listti goturur//

        cafe.currentAccountBalance = 0;
    }

    public void addMoney(CafeServiceImpl cafe) {
        while (true) {
            try {
                System.out.println("\nPlease enter amount to deposit or (R) to return to last page: ");
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();

                if (input.equalsIgnoreCase("R")) {
                    break;
                } else {
                    double amountEntered = Double.parseDouble(input);
                    cafe.addToAccountBalance(amountEntered);
                    System.out.println("\nThank you for your deposit, but feel free to add more! ;) \n");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease re-enter deposit amount in this format ($100 = 100.00): \n");
            }
        }
    }
}
