package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This iss apparently cleaner and out closes the scanner, after usage in method block
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface(myScanner);
        for (int i = 0; i < myInterface.pTeam1.length; i++) {
        myInterface.pTeam1[i].addAPokemon(myScanner, myInterface, 300);
        myInterface.pTeam1[i].getPokemonFromPlayer(0).addMoveToPokemon
        (myInterface.getASpecificMove(myScanner, "Body Slam"), myInterface.pTeam1[i], myScanner);
        }
        for (int i = 0; i < myInterface.pTeam2.length; i++) {
        myInterface.pTeam2[i].addAPokemon(myScanner, myInterface, 300);
        myInterface.pTeam2[i].getPokemonFromPlayer(0).addMoveToPokemon
        (myInterface.getASpecificMove(myScanner, "Body Slam"), myInterface.pTeam2[i], myScanner);
        myScanner.nextLine();
        }
        myInterface.initiateBattle(myScanner);
        myInterface.startBattleT1vT2(myScanner);
        }
    }
}
