package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

    public class Team {
        public Player[] players;
        private String teamName;
        private Player[] playersInBattle;
        public boolean hasWon;
        public int amountOfPlayersStillInBattle;
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
        public Pokemon getTarget(int index, Player initialTarget) {
            // Adjusting to 0 index, and int is currently the highest num index target.
            if (!players[index].pokemonInPlay.isFainted) {
                return players[index].pokemonInPlay;
            } else {
                if (reTargetingAlgorithm(index)) {
                    return players[adjustableTarget].pokemonInPlay;
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
        public int pickATarget(Scanner myScanner) {
            amountOfPlayersStillInBattle = 0;
            ArrayList<Integer> indexEntry = new ArrayList<>();
            int currentIndex = 0;
            for (Player p : players) {
                if (p.allPokemonAreFainted != true && 
                !p.pokemonInPlay.PokeName.equals("Pokemon shouldn't exist")) {
                System.out.printf("\n(%d) %s: HP %d/%d", 
                amountOfPlayersStillInBattle + 1, p.pokemonInPlay.PokeName, p.pokemonInPlay, p.
                pokemonInPlay.getHPMod(), p.pokemonInPlay.baseHP); // Now these will be adjusted later.
                if (p.pokemonInPlay.getStatusCondition()) {
                    System.out.print("\nStatus Condition: " + p.pokemonInPlay.getCurrentCondition());
                }
                if (p.pokemonInPlay.SecondaryConditions.isEmpty()) {
                    System.out.print("\n Secondary Condition(s): ");
                    for (int i = 0; i < p.pokemonInPlay.SecondaryConditions.size(); i++) {
                        if (i != p.pokemonInPlay.SecondaryConditions.size() - 1) {
                            System.out.print(p.pokemonInPlay.SecondaryConditions.get(i) + ", ");
                        }
                    }
                }
                System.out.printf("\nStat Changes: Att (%d), SpA (%d), Def (%d), SpDef (%d), " +
                "Spd (%d), Acc (%d), Eva (%d), Crit (%d)", p.pokemonInPlay.AttMod, p.pokemonInPlay.SpAMod, 
                p.pokemonInPlay.DefMod, p.pokemonInPlay.SpDefMod, p.pokemonInPlay.SpdMod, 
                p.pokemonInPlay.accuracyMod, p.pokemonInPlay.evasion ,p.pokemonInPlay.critChanceMod);
                amountOfPlayersStillInBattle++;
                indexEntry.add(currentIndex);
                }
                currentIndex++;
            }
            if (indexEntry.isEmpty()) {
                System.out.println("NO ENEMIES TO TARGET!");
                return -2; // I can directly this number to getTarget and it will redirect indexwise (upwards) if
                // and only if there is an actual target, so getTarget should be checked for, and if the returns null
                // then set the team the method is used upon set their attribute allPokemonFainted = true, and just +1
                // the other team who won with a congrats message, or smthn.
            }
            try {
                int target;
                do {
                target = myScanner.nextInt();
                } while (target < 1 || target > indexEntry.size());
                return indexEntry.get(target - 1); // Adjust the 1-size to instead 0 inclusive and max exc
                // Then performMove will use the opposing team object so just team.getTarget(whatever )
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a number between (1-" + indexEntry.size() + ")!");
                myScanner.nextLine(); // Read the next line, instead of the line where user misinputted.
                }

            // The below should be unreachable code.
            return -2;
        }
        public boolean hasPokemonInPlay() {
            for (Player p : players) {
                if (!p.pokemonInPlay.isFainted && 
                !p.pokemonInPlay.PokeName.equals("Pokemon shouldn't exist")) {
                    return true;
                }
            }
            return false;
        }

        public void displayLineUp() {
            for (Player p : players) {
                if (p.allPokemonAreFainted != true && 
                !p.pokemonInPlay.PokeName.equals("Pokemon shouldn't exist")) {
                System.out.printf("\n(%d) %s: HP %d/%d", 
                amountOfPlayersStillInBattle + 1, p.pokemonInPlay.PokeName, p.pokemonInPlay, p.
                pokemonInPlay.getHPMod(), p.pokemonInPlay.baseHP); // Now these will be adjusted later.
                if (p.pokemonInPlay.getStatusCondition()) {
                    System.out.print("\nStatus Condition: " + p.pokemonInPlay.getCurrentCondition());
                }
                if (p.pokemonInPlay.SecondaryConditions.isEmpty()) {
                    System.out.print("\n Secondary Condition(s): ");
                    for (int i = 0; i < p.pokemonInPlay.SecondaryConditions.size(); i++) {
                        if (i != p.pokemonInPlay.SecondaryConditions.size() - 1) {
                            System.out.print(p.pokemonInPlay.SecondaryConditions.get(i) + ", ");
                        }
                    }
                }
                System.out.printf("\nStat Changes: Att (%d), SpA (%d), Def (%d), SpDef (%d), " +
                "Spd (%d), Acc (%d), Eva (%d), Crit (%d)", p.pokemonInPlay.AttMod, p.pokemonInPlay.SpAMod, 
                p.pokemonInPlay.DefMod, p.pokemonInPlay.SpDefMod, p.pokemonInPlay.SpdMod, p.pokemonInPlay.accuracyMod,
                p.pokemonInPlay.evasion ,p.pokemonInPlay.critChanceMod);
                }
            }
        }
    }
