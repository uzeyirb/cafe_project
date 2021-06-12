package com.techelevator.service.impl;

import com.techelevator.service.CafeService;
import com.techelevator.service.LogService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LogServiceImpl implements LogService {


    CafeService cafeService = new CafeServiceImpl(); // left side is interface and right side is object
    @Override
    public void logFile (List<String> list) throws IOException  { // allows rest of code to access our desired log.txt file
        File outputFile = new
                File("C:\\Users\\Student\\source\\repos\\team4-java-blue-week04-pair-exercises\\m1-capstone-java-cateringsystem\\log.txt");
        //List<String> list = cafeService.getList();
        try(FileWriter logWriter = new FileWriter(outputFile, true)){
            for(String logInput : list) {
                logWriter.write(logInput);
                logWriter.write("\n");
            }
        }
    }

}
