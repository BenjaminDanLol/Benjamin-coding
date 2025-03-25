package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String playerName = "Ash Ketchum";
    ArrayList<Pokemon> playersActualPokemon = new ArrayList<>();
    ArrayList<String> namesOfPlayersPokemon = new ArrayList<>();
    ArrayList<Pokemon> pokemonPlayerCanActuallyUse = new ArrayList<>();
    ArrayList<String> namesOfPokemonPlayerCanUse = new ArrayList<>();
    public boolean allPokemonAreFainted = false;
    public boolean playerHasToSwap;
    public Pokemon pokemonUsedBefore;
    public Pokemon pokemonInPlay;
    public Pokemon pokemonToDisplay;
    public Pokemon emptyPokemon = new Pokemon();
    String playerInput = "";
    public Player(Scanner myScanner) {
        while (!playerInput.equals("x")) { 
            System.out.println("What will your name be player?");
            playerName = myScanner.nextLine().trim();
            System.out.println("Your' playername will be " + playerName + ", type X to confirm");
            playerInput = myScanner.nextLine().trim().toLowerCase();
        }
    }
    public void addAPokemon(Scanner myScanner, Interface myInterface, int filter) {
        /*
         * Things to remember howManyPokemonDoesPlayerActuallyHave is accurate now, therefor no changes
         * are applied, and it must be checked before adding a pokemon. So the code is less error prone.
         * This method is used to add a randomized pokemon, therefor if it should be a specific pokemon
         * then I would have to add a seperate function for that.
         * The function getAPokemonStandardized has 2 things that are sort of weird, but will be used in game.
         * 1. they have a pool to expect, where from the rng presentations of pokemon come, since Ghost/Dragon
         * only have 4 pokemon total, it needs to be 4. So in practice it isn't really that randomized.
         * 2. The filter is used to determine how the sorting of the pokemon list should go i.e. should
         * it look for the highest value pokemon, and determine the pool from there, or should it start from
         * the bottom? These effects will become more pronounced as the game has more Pokemon.
         */
        // Just use this and the field it updates .length instead.
        if (playersActualPokemon.size() == 6) {
            while (!playerInput.equals("yes") && !playerInput.equals("no")) {
            System.out.println("Party of pokemon can only be 6." + playerName + " do you wish to replace a Pokemon? (yes/no)");
            playerInput = myScanner.nextLine().trim().toLowerCase();
            }
            if (playerInput.equals("no")) {
                System.out.println("No new pokemon was chosen.");
                playerInput = "";
                return;
            }
            // From here the player is forced to replace a pokemon from their party.
            pokemonToDisplay = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
            pokemonToDisplay.displayPokeInfo();
            System.out.println(playerName + " choose which pokemon from party will be replaced by: " + pokemonToDisplay.PokeName);
            int pChoice = Interface.presentOptionsIndexList(namesOfPlayersPokemon, myScanner, playerName);
            int pokemonLevel = playersActualPokemon.get(pChoice).getLevel();
            // I need to remove, so what this code will do is that it will remove the index the player chose, and then
            // after add in the pokemon the player chose prior.
            playersActualPokemon.remove(pChoice);
            playersActualPokemon.add(pChoice, pokemonToDisplay);
            playersActualPokemon.get(pChoice).setLevel(pokemonLevel);
            namesOfPlayersPokemon.remove(pChoice);
            namesOfPlayersPokemon.add(pChoice, pokemonToDisplay.PokeName);
        }
        else {
            pokemonToDisplay = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
            playersActualPokemon.add(pokemonToDisplay);
            namesOfPlayersPokemon.add(pokemonToDisplay.PokeName);
        }
    }
    public void updatePokemonPlayerCanUse() {
        pokemonPlayerCanActuallyUse.clear();
        namesOfPokemonPlayerCanUse.clear();
        for (Pokemon validPokemon : playersActualPokemon) {
            if (validPokemon.isFainted == false) {
                pokemonPlayerCanActuallyUse.add(validPokemon);
                namesOfPokemonPlayerCanUse.add(validPokemon.PokeName);
            }
        }
        if (pokemonPlayerCanActuallyUse.isEmpty()) {
            allPokemonAreFainted = true;
        }
    }
    public void updatePokemonPlayerCanUseExPokeInPlay() {
        pokemonPlayerCanActuallyUse.clear();
        namesOfPokemonPlayerCanUse.clear();
        for (Pokemon validPokemon : playersActualPokemon) {
            if (validPokemon.isFainted == false && validPokemon != pokemonInPlay) {
                pokemonPlayerCanActuallyUse.add(validPokemon);
                namesOfPokemonPlayerCanUse.add(validPokemon.PokeName);
            }
        }
        if (pokemonPlayerCanActuallyUse.isEmpty()) {
            allPokemonAreFainted = true;
        }
    }    
    // This method should only be called when player already has a pokemon in battle
    public void playerController(Scanner myScanner) {
    updatePokemonPlayerCanUse();
        System.out.printf("%n" + playerName + "'s turn!%n");
        if (allPokemonAreFainted) {
            System.out.println(playerName + " has no pokemon left to swap in.");
            pokemonInPlay = emptyPokemon;
            return;
        }
        playerInput = "";
        if (!playerHasToSwap) {
            swapOrKeepPokemonIn(myScanner);
        }
        else if (playerHasToSwap) {
            playerControllerBStart(myScanner);
        }
    }
    private void swapOrKeepPokemonIn(Scanner myScanner) {
        updatePokemonPlayerCanUseExPokeInPlay();
        while (!playerInput.equals("yes") && !playerInput.equals("no")) {
            System.out.println(playerName + ", do you want to keep: " + pokemonInPlay.PokeName + " in? (yes/no)");
            playerInput = myScanner.nextLine().trim().toLowerCase();
            }

                if (playerInput.equals("yes")) {
                    System.out.println();
                    pokemonInPlay.moveController(myScanner, this);
                    return;
                }
            playerInput = "";
        System.out.println("\tHere's the pokemon you can swap in " + playerName + ": ");
        byte i = 1;
            for (String pokeName : namesOfPokemonPlayerCanUse) {
                System.out.println(i + ")\t" + pokeName);
                i++;
            }
            System.out.println("Enter anything to continue! ");
            myScanner.nextLine();
        playerKeepSwapCommandPalette(myScanner);
    }
    public void playerKeepSwapCommandPalette(Scanner myScanner) {
        int choice;
        boolean validChoice = false;
        
        while (!validChoice) {
            try {
                System.out.printf("%n%s's options:%n", playerName);
                System.out.println("1) Swap " + pokemonInPlay.PokeName);
                System.out.println("2) Check status");
                System.out.println("3) View Pokémon details");
                System.out.println("4) Keep " + pokemonInPlay.PokeName);
                System.out.print("Choose (1-4): ");
                
                choice = Integer.parseInt(myScanner.nextLine().trim());
                
                switch (choice) {
                    case 1 -> handleSwapDecision(myScanner);
                    case 2 -> displayCurrentStatus();
                    case 3 -> {
                        displayPlayerPokemonMenu(myScanner);
                        validChoice = true;
                    }
                    case 4 -> confirmKeepDecision(myScanner);
                    default -> System.out.println("Invalid choice! Enter 1-4.");
                }
                
                if (choice == 1 || choice == 4) {
                    validChoice = true;
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Numbers only! Enter 1-4.");
            }
        }
    }
    private void handleSwapDecision(Scanner scanner) {
        int index = Interface.presentOptionsIndexList(namesOfPokemonPlayerCanUse, scanner, playerName);
        Pokemon newPokemon = pokemonPlayerCanActuallyUse.get(index);
        
        String confirmation;
        do {
            System.out.printf("Swap %s for %s? (yes/no): ", pokemonInPlay.PokeName, newPokemon.PokeName);
            confirmation = scanner.nextLine().trim().toLowerCase();
        } while (!confirmation.matches("yes|no"));
    
        if (confirmation.equals("yes")) {
            executeSwap(newPokemon);
        }
    } 
    private void executeSwap(Pokemon newPokemon) {
        newPokemon.isSwapping = true;
        pokemonUsedBefore = pokemonInPlay;
        pokemonInPlay = newPokemon;
        System.out.println("Swapped to " + newPokemon.PokeName);
    }
    private void displayCurrentStatus() {
        pokemonInPlay.displayPokeInfo();
        System.out.printf("%s has %d HP (Level %d)%n",
            pokemonInPlay.PokeName,
            pokemonInPlay.getHPMod(),
            pokemonInPlay.getLevel());
        
        if (pokemonInPlay.getStatusCondition()) {
            System.out.printf("%s has %s status!%n",
                pokemonInPlay.PokeName,
                pokemonInPlay.getCurrentCondition());
        }
    }
    private void confirmKeepDecision(Scanner myScanner) {
        System.out.printf("%s kept %s in!%n",
            playerName, pokemonInPlay.PokeName);
        pokemonInPlay.moveController(myScanner, this);
    }
    // This should only be called when player doesn't already have a pokemon in battle
    private void playerControllerBStart(Scanner myScanner) {
            System.out.println("Enter anything to continue! ");
            myScanner.nextLine();
            System.out.println(playerName + ", you must choose a pokemon to join this battle!");
            pokemonCommandSectionBStart(myScanner);
        }
        private void pokemonCommandSectionBStart(Scanner myScanner) {
            updatePokemonPlayerCanUse();
            boolean validChoice = false;
            
            while (!validChoice) {
                try {
                    System.out.printf("%n%s you can now:%n", playerName);
                    System.out.println("1) View all Pokémon details");
                    System.out.println("2) Access specific Pokémon info");
                    System.out.println("3) Select battle Pokémon");
                    System.out.print("Enter choice (1-3): ");
                    
                    int choice = Integer.parseInt(myScanner.nextLine().trim());
                    
                    switch (choice) {
                        case 1 -> displayAllPokemon();
                        case 2 -> {
                            displayPlayerPokemonMenu(myScanner);
                            validChoice = true;
                        }
                        case 3 -> {
                            validChoice = handlePokemonSelection(myScanner);
                        }
                        default -> System.out.println("Invalid choice! Please enter 1-3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Numbers 1-3 only.");
                }
            }
        }
        private void displayPokemonPlayerCanUse() {
            for (Pokemon pokemon : pokemonPlayerCanActuallyUse) {
                pokemon.displayPokeInfo();
                if (pokemon.getStatusCondition()) {
                    System.out.printf("\t%s has %s status!%n", 
                        pokemon.PokeName, pokemon.getCurrentCondition());
                }
            }
        }
        private void displayAllPokemon() {
            for (Pokemon pokemon : playersActualPokemon) {
                pokemon.displayPokeInfo();
                if (pokemon.getStatusCondition()) {
                    System.out.printf("\t%s has %s status!%n", 
                        pokemon.PokeName, pokemon.getCurrentCondition());
                }
            }
        }
        private boolean handlePokemonSelection(Scanner myScanner) {
            int index = Interface.presentOptionsIndexList(namesOfPokemonPlayerCanUse, myScanner, playerName);
            Pokemon selected = pokemonPlayerCanActuallyUse.get(index);
            
            String confirmation;
            do {
                if (myScanner.hasNextLine()) {
                    myScanner.nextLine();
                }

                System.out.printf("%s, confirm %s for battle? (yes/no): ",
                    playerName, selected.PokeName);
                confirmation = myScanner.nextLine().trim().toLowerCase();
            } while (!confirmation.matches("yes|no"));
            
            if (confirmation.equals("yes")) {
                pokemonInPlay = selected;
                return true;
            }
            return false;
        }
    public void displayPlayerPokemonMenu(Scanner myScanner) {
        String playersInput;

        do {
            // Clear buffer more reliably
            if (myScanner.hasNextLine()) {
                myScanner.nextLine(); // Consume any leftover input
            }
    
            System.out.printf("%n%s do you wish to:%n1) See all your pokemon%n" +
            "2) Only see available ones%n(1/2)%n", playerName);
            
            playersInput = myScanner.nextLine().trim();
    
        } while (!playersInput.matches("[12]")); // Simplified validation
    
        if (playersInput.equals("1")) {
            displayAllPokemon();
        }
        else if (playersInput.equals("2")) {
            displayPokemonPlayerCanUse();
        }

    
        handlePostMenuDecision(myScanner);
    }
    
    private void handlePostMenuDecision(Scanner myScanner) {
        String playersInput;
        do {
            System.out.printf("%n%s, view another pokemon description? (yes/no)%n", playerName);
            playersInput = myScanner.nextLine().trim().toLowerCase();
        } while (!playersInput.matches("yes|no"));
    
        if (playersInput.equals("yes")) {
            displayPlayerPokemonMenu(myScanner); // Consider converting to loop instead of recursion
        } else {
            if (playerHasToSwap) {
                pokemonCommandSectionBStart(myScanner);
            } else {
                playerKeepSwapCommandPalette(myScanner);
            }
        }
    }
    public Pokemon getTarget(Pokemon enemy) {
        if (pokemonInPlay.isSwapping && enemy.getMoveInUsage().targetPokemonSwapping) {
            return pokemonUsedBefore;
        }
        return pokemonInPlay;
    }
    public String getPlayerName(){
        return playerName;
    }
    public Pokemon getPokemonFromPlayer(int index) {
        return playersActualPokemon.get(index);
    }
}        