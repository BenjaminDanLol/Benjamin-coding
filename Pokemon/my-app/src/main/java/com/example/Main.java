package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface(myScanner);
        int[] teamIndexes = {0, 1};
        myInterface.startBattle2Teams(myScanner, teamIndexes);
        }
    }
}

/*
 * for (int i = 0; i < myInterface.pTeam1.length; i++) {
            for (int n = 0; n < 8; n++) {
            myInterface.pTeam1[i].addAPokemon(myScanner, myInterface, 300);
            }
            for (int n = 0; n < myInterface.pTeam1[i].playersActualPokemon.size(); n++) {
                myInterface.pTeam1[i].playersActualPokemon.get(n).addMoveToPokemon
                (myInterface.getASpecificMove(myScanner, "Flamethrower"), myInterface.pTeam1[i], myScanner);
                }
        }
        System.out.println("Body Slam has PP: " + myInterface.pTeam1[0].getPokemonFromPlayer(0).pokemonMoves.get(0).PP);
        for (int i = 0; i < myInterface.pTeam2.length; i++) {
            for (int n = 0; n < 8; n++) {
            myInterface.pTeam2[i].addAPokemon(myScanner, myInterface, 300);
            }
            for (int n = 0; n < myInterface.pTeam2[i].playersActualPokemon.size(); n++) {
                myInterface.pTeam2[i].playersActualPokemon.get(n).addMoveToPokemon
                (myInterface.getASpecificMove(myScanner, "Flamethrower"), myInterface.pTeam2[i], myScanner);
                }
        }
        myInterface.initiateBattle(myScanner);
        myInterface.startBattleT1vT2(myScanner);
 */
