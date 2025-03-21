package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This iss apparently cleaner and out closes the scanner, after usage in method block
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface(myScanner);
        myInterface.startBattleT1vT2(myScanner);
        }
    }
}
