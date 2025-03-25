package com.example;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

    public class Team {
        private Player[] players;
        private String teamName;
        private Player[] playersInBattle;
        private int adjustableTarget;
        public Team(Scanner myScanner, int teamNumber) {

            // May want to use teamNumber later.
            int sizeOfPlayerTeam;
            String confirm;
            if (myScanner.hasNextLine()) {
                myScanner.nextLine();
            }
            
            while (true) {
                System.out.println("Team " + teamNumber + " what is your Team's Name?\n");
                teamName = myScanner.nextLine();

                System.out.println("Team " + teamNumber + "'s name will be: " + teamName + "!\n");

                System.out.println("Enter X to confirm.");
                confirm = myScanner.next().trim().toLowerCase();
                if (confirm.equals("x")) {
                    System.out.println("Team " + teamNumber + "'s name is now: " + teamName + "!\n");
                    break;
                }
            }
            if (myScanner.hasNextLine()) {
                myScanner.nextLine();
            }
            while (true) {
                System.out.println("How many players are playing on team " + teamName +"?\n");
            try {            
            
            sizeOfPlayerTeam = myScanner.nextInt();
            if (sizeOfPlayerTeam > 0) {
                System.out.println(teamName + " will have " + sizeOfPlayerTeam + " players.");
                System.out.println("X to confirm: ");
                confirm = myScanner.next().trim().toLowerCase();
                if (confirm.equals("x")) {
                    break;
                }
            }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input!");
                    myScanner.nextLine(); 
                }   
            }
            players = new Player[sizeOfPlayerTeam];
            for (int i = 0; i < sizeOfPlayerTeam; i++) {
                players[i] = new Player(myScanner, teamName, i+1);
            }
        }   
        public Pokemon getTarget(int index) {
            // Adjusting to 0 index, and int is currently the highest num index target.
            if (!playersInBattle[index].pokemonInPlay.isFainted) {
                return playersInBattle[index].pokemonInPlay;
            } else {
                if (reTargetingAlgorithm(index)) {
                    return playersInBattle[adjustableTarget].pokemonInPlay;
                }
                else {
                    System.out.println("No pokemon to target " + teamName + " won this battle!");
                    return null;

                }
            }
            // Need to check for this before performingMove, so prob Pokemon target = dasd.getTarget(index)];
            // if target == null or .equals not sure. The team performing it has won and then exit the turn sequence.
            
        }
        private boolean reTargetingAlgorithm(int index) {
            Random random = new Random();
            int maxRight = playersInBattle.length - index - 1;
            int maxLeft = index;
            int amountOfShifts = 0;

            return false;
        }
        public void teamChoosesPokemon() {
            // Need to have a plan for the flow of how this will go since recursion messes everything up.

        }
        public void teamPerformsTurn(Team enemyTeam) {
            // Need to have a plan for the flow of how this will go since recursion messes everything up.
        }
    }
