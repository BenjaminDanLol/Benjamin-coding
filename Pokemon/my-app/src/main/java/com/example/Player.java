package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String playerName;
    Pokemon[] playersPokemon = new Pokemon[6];
    Pokemon emptyPokemon = new Pokemon();
    private int howManyPokemonDoesPlayerActuallyHave = 0;
    Pokemon pokemonUsedBefore;
    Pokemon pokemonInPlay;
    String playerInput;
    int playerIndexAccess;
    public Player(Scanner myScanner) {
        while (true) { 
            System.out.println("What will your name be player? ");
            playerName = myScanner.nextLine().trim();
            System.out.println("Playername will be " + playerName + " are you sure?");
            System.out.println("yes to confirm:");
            if (myScanner.nextLine().trim().toLowerCase().equals("yes")) {
                break;
            }
        }
            for (int i = 0, n = playersPokemon.length; i < n; i++) {
                playersPokemon[i] = emptyPokemon;
            }
    }
    public int howManyPokemonDoesPlayerActuallyHave() {

        int amount = 0;
        for (int i = 0; i < 6; i++) {
            if (!playersPokemon[i].PokeName.equals("Pokemon shouldn't exist") && playersPokemon[i].PokeName != null) {
                amount++;
            }
        }
        return amount;
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
        howManyPokemonDoesPlayerActuallyHave = howManyPokemonDoesPlayerActuallyHave();
        if (howManyPokemonDoesPlayerActuallyHave >= 6) {
            System.out.println("Party of pokemon can only be 6." + playerName +" is currently replacing a pokemon.");
            System.out.println("Type anything to continue. Type no to cancel! ");
            myScanner.nextLine();
            String yesno = myScanner.nextLine().trim().toLowerCase();
            if (yesno.equals("no")) {
                System.out.println("No new pokemon was chosen.");
                return;
            }
            System.out.println(playerName + " choose a pokemon from party to replace.");
            int pChoice = Interface.presentOptionsIndex(allOfPokemonNames(), 6, myScanner, playerName);
            playersPokemon[pChoice] = emptyPokemon;
            // After checking how many pokemon there are and, replacing in this case. The filter is applied and a new rand poke is added.
            playersPokemon[pChoice] = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
        }
        else {
            playersPokemon[howManyPokemonDoesPlayerActuallyHave] = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
        }
    }
    public Pokemon selectAPokemonViaName(String nameOfPokemon) {
        for (Pokemon thePokemon : playersPokemon) {
            if (thePokemon.PokeName.equals(nameOfPokemon)) {
                System.out.println(playerName + " chooses " + thePokemon.PokeName);
                return thePokemon;
            }
        }
        System.out.println("No such Pokemon exists");
        return null;
    }
    public String[] allOfPokemonNames() {
        ArrayList<String> pokemonNames = new ArrayList<>();
        for (Pokemon validPokemon : playersPokemon) {
            if (validPokemon != null && !validPokemon.PokeName.equals("Pokemon shouldn't exist")){
                pokemonNames.add(validPokemon.PokeName);
            }
        }
        // VS Code recommends this: return pokemonNames.toArray(String[]::new);
        return pokemonNames.toArray(new String[0]);
    }
    public void playerController(Scanner myScanner) {
        
        while (!playerInput.equals("yes") || !playerInput.equals("no")) {
            System.out.println("\t" + playerName + ", do you want to keep: " + pokemonInPlay.PokeName + " in? (yes/no)");
            playerInput = myScanner.nextLine().trim().toLowerCase();
            }
                if (playerInput.equals("yes")) {
                    return;
                }
            System.out.println("Here's your pokemon " + playerName + ": ");
                    for (int i = 0, n = playersPokemon.length; i < n; i++) {
                        System.out.println((i + 1) + ")\t" + playersPokemon[i].PokeName);
                    }
        int i;
            System.out.println("Enter anything to continue! ");
            myScanner.nextLine();
        try {
            // Honestly the try catch doesn't matter here.
            while (playerIndexAccess != 1 || playerIndexAccess != 4) {
                System.out.println("\t" + playerName + " you can now use the following commands:  %n" + 
                "\t(1) to swap out " + pokemonInPlay.PokeName + " with another pokemon on team." +
                "\t(2) to see " + pokemonInPlay.PokeName + "'s current status.%n" +
                "\t(3) to access the description of one your other pokemon.%n" +
                "\t(4) to keep " + pokemonInPlay.PokeName + " in.%n");
            playerIndexAccess = myScanner.nextInt();
            switch (playerIndexAccess) {
                case 1 -> {
                    // It's possible to swap out the pokemon with itself
                    i = Interface.presentOptionsIndex(allOfPokemonNames(), allOfPokemonNames().length, myScanner, playerName);
                    while (!playerInput.equals("yes") || !playerInput.equals("no")) {
                        System.out.println("\t" + playerName + ", are you sure you want to swap: " + pokemonInPlay.PokeName + " out? (yes/no)");
                        playerInput = myScanner.nextLine().trim().toLowerCase();
                    }   
                    if (playerInput.equals("yes")) {
                        playersPokemon[i].isSwapping = true;
                        pokemonUsedBefore = pokemonInPlay;
                        pokemonInPlay = playersPokemon[i];
                        return;
                        }
                        // Pretty sure that it will exit this while loop and enter the outer while loop in the case of "no"
                }
                case 2 -> pokemonInPlay.displayPokeInfo(); // Can have different display methods for pokemon. Would make sense as well.
                case 3 -> playersPokemon[Interface.presentOptionsIndex(allOfPokemonNames(), allOfPokemonNames().length, myScanner, playerName)].displayPokeInfo();
                case 4 -> System.out.println(playerName + " kept " + pokemonInPlay.PokeName + " in!");
                    }
                                } 
        }  catch (InputMismatchException e) {
            System.out.println("Invalid Input! Input needs to be either command 1, 2, 3 or 4!");
            }
            System.out.println("No swap has occurred");
        }

        public Pokemon playerControllerBStart(Scanner myScanner) {
        
            
            int i;
                System.out.println(playerName + "'s turn!");
                System.out.println("Enter anything to continue! ");
                myScanner.nextLine();
                System.out.println(playerName + " you must choose one of your pokemon to join the battle!");
                for (String e : allOfPokemonNames()) {
                    System.out.println("\t" + e);
                }
            try {
                while (playerIndexAccess != 4) {
                    System.out.println("\t" + playerName + " you can now use the following commands:  %n" + 
                    "\t(1) to see all of your pokemon in detail.%n" +
                    "\t(2) to see one of your pokemon's current status.%n" +
                    "\t(3) to access the description of one your pokemon.%n" +
                    "\t(4) to finalize your pokemon choice for this fight!");
                playerIndexAccess = myScanner.nextInt();
                switch (playerIndexAccess) {
                    case 1 -> { 
                    for (Pokemon e : playersPokemon) {
                        e.displayPokeInfo();
                        } 
                    }
                    case 2 -> {
                    i = Interface.presentOptionsIndex(allOfPokemonNames(), allOfPokemonNames().length, myScanner, playerName);
                    System.out.println("\t" + playersPokemon[i].PokeName + " currently has " + playersPokemon[i].getHPMod() + " and is level: " + playersPokemon[i].getLevel());
                    }
                    case 3 -> playersPokemon[Interface.presentOptionsIndex(allOfPokemonNames(), allOfPokemonNames().length, myScanner, playerName)].displayPokeInfo();
                    case 4 -> {
                    i = Interface.presentOptionsIndex(allOfPokemonNames(), allOfPokemonNames().length, myScanner, playerName);

                        while (!playerInput.equals("yes") || !playerInput.equals("no")) {
                            System.out.println("\t" + playerName + ", are you sure you want to put: " + playersPokemon[i].PokeName + " in? (yes/no)");
                            playerInput = myScanner.nextLine().trim().toLowerCase();
                            }
                                if (playerInput.equals("yes")) {
                                    pokemonInPlay = playersPokemon[i];
                                }
                                else {
                                    // Just to repeat the loop
                                    playerIndexAccess = 5;
                                }
                            }
                    }
                                    } 
            }  catch (InputMismatchException e) {
                System.out.println("Invalid Input! Input needs to be either command 1, 2, 3 or 4!");
                }
                // Hopefully I never get here to this part of the code at least it would be an awful bug.
                return pokemonInPlay;
            }

    public Pokemon[] getPlayersPokemon(){
        return playersPokemon;
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
        return playersPokemon[index];
    }
}

        