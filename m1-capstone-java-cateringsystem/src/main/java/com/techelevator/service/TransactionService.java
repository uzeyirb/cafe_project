package com.techelevator.service;

import com.techelevator.service.impl.CafeServiceImpl;

import java.io.IOException;

public interface  TransactionService {
    void completeTransaction(CafeServiceImpl cafe) throws IOException;

    void addMoney(CafeServiceImpl cafe);
}
