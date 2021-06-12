package com.techelevator.service;

import java.io.IOException;
import java.util.List;

public interface LogService {

    void logFile( List<String> list) throws IOException;
}
