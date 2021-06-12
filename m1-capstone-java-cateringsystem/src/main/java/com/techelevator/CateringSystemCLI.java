package com.techelevator;

import com.techelevator.service.impl.CateringServiceImpl;
import com.techelevator.view.Menu;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CateringSystemCLI {


    public static void main(String[] args) throws IOException { // runs the WHOLE SHOW
		/*
		Signals that an I/O exception of some sort has occurred.
		This class is the general class of exceptions produced by failed or interrupted I/O operations
		Constructs an IOException with the specified detail message and cause.
		 */
        Menu menu = new Menu(System.in, System.out);
        CateringServiceImpl cateringService = new CateringServiceImpl(menu);
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