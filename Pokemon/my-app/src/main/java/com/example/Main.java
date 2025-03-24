package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This iss apparently cleaner and out closes the scanner, after usage in method block
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface(myScanner);
        for (int i = 0; i < myInterface.pTeam1.length; i++) {
            for (int n = 0; n < 2; n++) {
            myInterface.pTeam1[i].addAPokemon(myScanner, myInterface, 300);
            myInterface.pTeam1[i].getPokemonFromPlayer(n).addMoveToPokemon
            (myInterface.getASpecificMove(myScanner, "Body Slam"), myInterface.pTeam1[i], myScanner);
            }
        }
        System.out.println("Body Slam has PP: " + myInterface.pTeam1[0].getPokemonFromPlayer(0).pokemonMoves.get(0).PP);
        for (int i = 0; i < myInterface.pTeam2.length; i++) {
            for (int n = 0; n < 2; n++) {
            myInterface.pTeam2[i].addAPokemon(myScanner, myInterface, 300);
            myInterface.pTeam2[i].getPokemonFromPlayer(n).addMoveToPokemon
            (myInterface.getASpecificMove(myScanner, "Body Slam"), myInterface.pTeam2[i], myScanner);
            }
        }
        System.out.println("Body Slam has PP: " + myInterface.pTeam1[0].getPokemonFromPlayer(0).pokemonMoves.get(0).PP);
        myScanner.nextLine();
        myInterface.initiateBattle(myScanner);
        myInterface.startBattleT1vT2(myScanner);
        }
    }
}
