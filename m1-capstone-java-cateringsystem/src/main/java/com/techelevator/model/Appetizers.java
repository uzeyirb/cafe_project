package com.techelevator.model;

import com.techelevator.model.Item;

public class Appetizers extends Item {

    /*
    If I will not have constructor for each item following runtime error will popup
    constructor Appetizers in class com.techelevator.model.Appetizers cannot be applied to given types;
  required: no arguments
  found: java.lang.String,double
  reason: actual and formal argument lists differ in length
  */


    public Appetizers(String name, double price) {
        super(name, price);
    }
}
