package view;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;




public class Menu {

    private PrintWriter output;
    private Scanner input;

    public Menu(InputStream input, OutputStream output) {

        this.output = new PrintWriter(output);
        this.input = new Scanner(input);
    }
    public Object getInventoryPathFromUser(){
        System.out.println("Please enter file path to inventory: ");
        return input.nextLine();
    }
    public Object getChoiceFromOptions(Object[] options, double balance) {

        Object choice = null;
        while(choice == null) {

            displayMenuOptions(options, balance);

            choice = getChoiceFromUserInput(options);

        }
        return choice;
    }

    public Object showWelcomeMessage() {
        System.out.println("*************************");
        System.out.println("**     Weyland Corp.   **");
        System.out.println("**      Catering       **");
        System.out.println("*************************");
        System.out.println();
        return "";
    }

    private Object getChoiceFromUserInput(Object[] options) {

        Object choice = null;
        String userInput = input.nextLine();


        try {
            if(userInput.toUpperCase().equals(("R"))) {
                choice = "R";
                return choice;
            }
            int selectedOption = Integer.valueOf(userInput);
            if(selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch(NumberFormatException e) {
            // eat the exception, an error message will be displayed below since choice will be null
        }

        if(choice == null) {
            output.println("\n*** "+userInput+" is not a valid option ***\n");
        }

        return choice;

    }



    private void displayMenuOptions(Object[] options, double balance) {

        output.println();
        for(int i = 0; i < options.length; i++) {
            int optionNum = i+1;
            output.println(optionNum+") "+options[i]);
        }
        DecimalFormat myFormat = new DecimalFormat("#0.00");
        output.println("Current Account Balance: $" + myFormat.format(balance));
        output.print("\nPlease choose an option");

        output.flush();
    }
    public void displayMenuOptionsForItems(Object[] options) {
        output.println();
        String str = String.format("%1$-7s %2$-13s %3$7s %4$10s", "Slot", "Name", "Price", "Quantity");
        output.println(str);
        for(int i = 0; i < options.length; i++) {
            output.println(options[i]);

        }

        output.flush();

    }

}

