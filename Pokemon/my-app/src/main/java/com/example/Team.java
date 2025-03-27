package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

    public class Team {
        public Player[] players;
        public String teamName;
        private ArrayList<Player> playersFieldedIntoBattle = new ArrayList<>(); // This is an absolute!
        // Used for retargeting algorithm size must only be changed once per battle max.

        public ArrayList<Player> playersCurrentlyInBattle = new ArrayList<>();
        public boolean wonOrLost;
        public int amountOfPlayersStillInBattle;
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

            if (myScanner.hasNextLine()) {myScanner.nextLine();} // I HATE THESE BUT SCANNER NEEDS TO BE CLEANED.

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
                players[i] = new Player(myScanner, i+1);
                players[i].setTeam(this); // VS Code and java disagree upon the safety of this. But I personally
                // believe it to be safe since objects are references in the end. Not primitives :)
            }
        }   
        
        public Pokemon getTarget(Player initialTarget) {
            if (!initialTarget.pokemonInPlay.isFainted) {
                return initialTarget.pokemonInPlay;
            } else {
                if (reTargetingAlgorithm(initialTarget) != -1) {
                    return playersFieldedIntoBattle.get(reTargetingAlgorithm(initialTarget)).pokemonInPlay;
                }
                else {
                    System.out.print("\nNo pokemon to target from team " + teamName + 
                    "!\n" + teamName + " lost!");
                    return null;
                }
            }
            // if target == null. The team performing it has won and then exit the turn sequence.
            
        }

        public void refreshPlayers() {
            playersCurrentlyInBattle.clear();
            for (Player p : playersFieldedIntoBattle) {
                if (!p.allPokemonAreFainted) {
                    playersCurrentlyInBattle.add(p);
                }
            }
        }

        private int reTargetingAlgorithm(Player initialTarget) {
            int index = -1;
            int counter = 0;
            for (Player p : playersFieldedIntoBattle) {
                if (p.equals(initialTarget)) {
                    index = counter;
                    // vedy gud we have ze 
                }
                counter++;
            }
            if (index == -1) {return index;} // edge case checking
            Random random = new Random();
            int maxRight = playersFieldedIntoBattle.size() - index - 1;
            int maxLeft = index;
            int shifts = 1;

        while (true) {
            if (index + shifts != maxRight && index - shifts != maxLeft) {
                /*If neither of these are 0 then it is possible to shift in both directions (indexwise). But I need
                to first check for if they are both not fainted.
                */
                if (!playersFieldedIntoBattle.get(index+shifts).pokemonInPlay.isFainted &&
                !playersFieldedIntoBattle.get(index-shifts).pokemonInPlay.isFainted) {
                    /*
                pokemon one index to left and right of currentTarget are targetable
                doACoinFlip
                if CoinFlip true: go right so return shifts;
                else: return go left so -shifts;
                    */
                    if (random.nextBoolean()) {
                        return index + shifts;
                    } else {
                        return index + shifts;
                    }
                }
        } else if (index + shifts != maxRight && index - shifts == maxLeft) {
            
            /*Can't shift to the left so we must loop until we find a target that is not Fainted to the right.
            BUT!! we must still check for if there actually are targets that are alive, therefor. We should loop
            whilst keeping that in mind. */

            while (index - shifts != maxRight) {
                if (playersFieldedIntoBattle.get(index+shifts).pokemonInPlay.isFainted) {
                    return index + shifts;
                }
                shifts++;
            }
            // If we reach here that means that there are absolutely no pokemon to target.

        } else if (index + shifts == maxRight && index - shifts != maxLeft) {
        //  Assuming the prior is correct then the opposite is correct as well.
            while (index - shifts != maxLeft) {
                if (playersFieldedIntoBattle.get(index-shifts).pokemonInPlay.isFainted) {
                    return index - shifts;
                }
                shifts++;
            }
            // If we reach here that means that there absolutely no pokemon to target.

        } else if (index + shifts == maxRight && index - shifts == maxLeft) {
            return 0;
            
            }
            shifts++;
                }
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
                !p.pokemonInPlay.equals(Interface.fakeMon)) {
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
