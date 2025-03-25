package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

    public class Team {
        private Player[] players;
        private String teamName;
        private ArrayList<Player> playersInBattle = new ArrayList<>();
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
    }
