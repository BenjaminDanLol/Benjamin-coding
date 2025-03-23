package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String playerName = "Ash Ketchum";
    Pokemon[] playersPokemon = new Pokemon[6];
    // TODO After I've correctly compiled remove the shitter below.
    private int howManyPokemonDoesPlayerActuallyHave = 0;
    ArrayList<Pokemon> playersActualPokemon = new ArrayList<>();
    ArrayList<Pokemon> pokemonPlayerCanActuallyUse = new ArrayList<>();
    public boolean allPokemonAreFainted = false;
    public String[] namesOfPokemonPlayerCanUse; 
    public String[] nameOfPlayersPokemon;
    public boolean playerHasToSwap;
    private boolean flag;
    public Pokemon pokemonUsedBefore;
    public Pokemon pokemonInPlay;
    public Pokemon pokemonToDisplay;
    public Pokemon emptyPokemon = new Pokemon();
    String playerInput = "";
    int playerIndexAccess = 0;
    public Player(Scanner myScanner) {
        while (!playerInput.equals("x")) { 
            System.out.println("What will your name be player?");
            playerName = myScanner.nextLine().trim();
            System.out.println("Your' playername will be " + playerName + ", type X to confirm");
            playerInput = myScanner.nextLine().trim().toLowerCase();
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
        // Just use this and the field it updates .length instead.
        updatePlayersPokemonNames();
        if (howManyPokemonDoesPlayerActuallyHave >= 6) {
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
            System.out.println(playerName + " choose a which pokemon from party will be replaced by: " + pokemonToDisplay.PokeName);
            int pChoice = Interface.presentOptionsIndex(nameOfPlayersPokemon, 6, myScanner, playerName);
            playersPokemon[pChoice] = emptyPokemon;
            // After checking how many pokemon there are and, replacing in this case. The filter is applied and a new rand poke is added.
            playersPokemon[pChoice] = pokemonToDisplay;
        }
        else {
            playersPokemon[howManyPokemonDoesPlayerActuallyHave] = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
        }
    }
    public void updateNamesOfPokemonPlayerCanUse() {
        pokemonPlayerCanActuallyUse.clear();
        for (Pokemon validPokemon : playersPokemon) {
            if (validPokemon != null && !validPokemon.PokeName.equals("Pokemon shouldn't exist")
            && validPokemon.isFainted == false) {
                pokemonPlayerCanActuallyUse.add(validPokemon);
            }
        }
        if (pokemonPlayerCanActuallyUse.isEmpty()) {
            allPokemonAreFainted = true;
        }
        // I'm suddenly confused is how this becomes a String I think it's just references tbh I may be wrong
        namesOfPokemonPlayerCanUse = new String[pokemonPlayerCanActuallyUse.size()];
        int i = 0;
        for (Pokemon pokeInArrayList : pokemonPlayerCanActuallyUse) {
            namesOfPokemonPlayerCanUse[i] = pokeInArrayList.PokeName;
            i++;
        }
    }
    public void updateNamesOfPokemonPlayerCanUseExPokeInPlay() {
        pokemonPlayerCanActuallyUse.clear();
        for (Pokemon validPokemon : playersPokemon) {
            if (validPokemon != null && !validPokemon.PokeName.equals("Pokemon shouldn't exist")
            && validPokemon.isFainted == false) {
                pokemonPlayerCanActuallyUse.add(validPokemon);
            }
        }
        if (pokemonPlayerCanActuallyUse.isEmpty()) {
            allPokemonAreFainted = true;
        }
        pokemonPlayerCanActuallyUse.remove(pokemonInPlay);
        namesOfPokemonPlayerCanUse = new String[pokemonPlayerCanActuallyUse.size()];
        int i = 0;
        for (Pokemon pokeInArrayList : pokemonPlayerCanActuallyUse) {
            namesOfPokemonPlayerCanUse[i] = pokeInArrayList.PokeName;
            i++;
        }
    }
    // This method simply updates the String[] containing all pokemon the player actually has
    public void updatePlayersPokemonNames() {
        for (Pokemon validPokemon : playersPokemon) {
            // null may or may not be necessary
            if (validPokemon != null && !validPokemon.PokeName.equals("Pokemon shouldn't exist")){
                playersActualPokemon.add(validPokemon);
            }
        }
        nameOfPlayersPokemon = new String[playersActualPokemon.size()];
        int i = 0;
        for (Pokemon actualPokemon : playersActualPokemon) {
            nameOfPlayersPokemon[i] = actualPokemon.PokeName;
        }
    }
    // This method should only be called when player already has a pokemon in battle
    public void playerController(Scanner myScanner) {
        updateNamesOfPokemonPlayerCanUse();
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
        while (!playerInput.equals("yes") && !playerInput.equals("no")) {
            System.out.println("\t" + playerName + ", do you want to keep: " + pokemonInPlay.PokeName + " in? (yes/no)");
            playerInput = myScanner.nextLine().trim().toLowerCase();
            }

                if (playerInput.equals("yes")) {
                    pokemonInPlay.moveController(myScanner, this);
                    return;
                }
            playerInput = "";
        System.out.println("\tHere's the pokemon you can swap in " + playerName + ": ");
            for (int i = 0, n = namesOfPokemonPlayerCanUse.length; i < n; i++) {
                System.out.println((i + 1) + ")\t" + namesOfPokemonPlayerCanUse[i]);
            }
            System.out.println("Enter anything to continue! ");
            myScanner.nextLine();
        playerKeepSwapCommandPalette(myScanner);
    }
    private void playerKeepSwapCommandPalette(Scanner myScanner) {
    // Later for performance I'll convert these to bytes, prob all choices will be bytes instead.
        int i;
        try {
            while (playerIndexAccess != 1 && playerIndexAccess != 4) {
                System.out.printf("%n" + playerName + " you can now use the following commands:  %n" + 
                "\t(1) to swap out " + pokemonInPlay.PokeName + " with another pokemon on team.%n" +
                "\t(2) to see " + pokemonInPlay.PokeName + "'s current status.%n" +
                "\t(3) to access the description of one your pokemon.%n" +
                "\t(4) to keep " + pokemonInPlay.PokeName + " in.%n");
            
            playerIndexAccess = myScanner.nextInt();
            switch (playerIndexAccess) {
            case 1 -> {
                i = Interface.presentOptionsIndex(namesOfPokemonPlayerCanUse, namesOfPokemonPlayerCanUse.length, 
                myScanner, playerName);
                while (!playerInput.equals("yes") && !playerInput.equals("no")) {
                    System.out.println("\t" + playerName + ", are you sure you want to swap: " + 
                    pokemonInPlay.PokeName + 
                    " out for " + pokemonPlayerCanActuallyUse.get(i) + "? (yes/no)");
                    playerInput = myScanner.nextLine().trim().toLowerCase();
                }   
                if (playerInput.equals("yes")) {
                    pokemonPlayerCanActuallyUse.get(i).isSwapping = true;
                    pokemonUsedBefore = pokemonInPlay;
                    pokemonInPlay = pokemonPlayerCanActuallyUse.get(i);
                    playerInput = "";
                    return;
                    }
                    playerInput = "";
            }

            case 2 -> {
            pokemonInPlay.displayPokeInfo(); 
            System.out.println("\t" + pokemonInPlay.PokeName + " currently has " + pokemonInPlay.getHPMod() +
            ", HP left, and is level: " + pokemonInPlay.getLevel());
                // If they currently have a status condition it will displayed as well
                if (pokemonInPlay.getStatusCondition()) {
                    System.out.println("\t" + pokemonInPlay.PokeName + " also currently has the " + 
                    pokemonInPlay.getCurrentCondition() + " status affliction!");
                    }
                }

            case 3 -> {
                    displayPlayerPokemonMenu(myScanner);
                    playerIndexAccess = 3;
                }

            case 4 -> {System.out.println(playerName + " kept " + pokemonInPlay.PokeName + " in!");
            pokemonInPlay.moveController(myScanner, this);
            return;}
                }
                            } 
        }  catch (InputMismatchException e) {
            System.out.println("Invalid Input! Input needs to be either command 1, 2, 3 or 4!");
            }
                    }
    // This should only be called when player doesn't already have a pokemon in battle
    private void playerControllerBStart(Scanner myScanner) {
            System.out.println("Enter anything to continue! ");
            myScanner.nextLine();
            System.out.println(playerName + ", you must choose a pokemon to join this battle!");
            pokemonCommandSectionBStart(myScanner);
        }
        public void pokemonCommandSectionBStart(Scanner myScanner) {
            int i;
        try {
            flag = true;
            while (flag) {
                System.out.printf("%n" + playerName + " you can now use the following commands:  %n" + 
                "\t(1) to see all of your pokemon in detail.%n" +
                "\t(2) to access the description of just one of your pokemon.%n" +
                "\t(3) to choose the pokemon who will join this battle!%n");
            playerIndexAccess = myScanner.nextInt();
            switch (playerIndexAccess) {
                case 1 -> { 
                for (Pokemon e : pokemonPlayerCanActuallyUse) {
                    e.displayPokeInfo();
                        if (e.getStatusCondition()) {
                        System.out.println("\t" + e.PokeName + 
                        " also currently has the " + e.getCurrentCondition() + " status affliction!");
                            }
                    } 
                }
                case 2 -> {
                    displayPlayerPokemonMenu(myScanner);
                    flag = false;
                }
                case 3 -> {
                i = Interface.presentOptionsIndex(namesOfPokemonPlayerCanUse, namesOfPokemonPlayerCanUse.length, myScanner, playerName);
                myScanner.nextLine();
                playerInput = "";
                    while (!playerInput.equals("yes") && !playerInput.equals("no")) {
                        System.out.println(playerName + ", are you sure you want to put: " + pokemonPlayerCanActuallyUse.get(i).PokeName + " in? (yes/no)");
                        playerInput = myScanner.nextLine().trim().toLowerCase();
                        }
                            if (playerInput.equals("yes")) {
                                pokemonInPlay = pokemonPlayerCanActuallyUse.get(i);
                                flag = false;
                            }
                        }
                }
                                } 
        }  catch (InputMismatchException e) {
            System.out.println("Invalid Input! Input needs to be either command 1, 2, 3 or 4!");
            }
            flag = true;            
        }
        public void displayPlayerPokemonMenu(Scanner myScanner) {
            playerInput = "";
            myScanner.nextLine();
            // If the user types anything else than 1 or two they will enter this while loop.
            flag = true;
            while (flag) {
            System.out.printf("%n" + playerName + " do you wish to. %n1) see all your pokemon, or %n" +
            "2) only the ones you can choose? %n(1/2)%n" );
            playerInput = myScanner.nextLine().trim();
            if (playerInput.equals("1") || playerInput.equals("2")) {
                flag = false;
                }
            }

            if (playerInput.equals("1")) {
                updatePlayersPokemonNames();
                pokemonToDisplay = playersPokemon[Interface.presentOptionsIndex(nameOfPlayersPokemon,
                nameOfPlayersPokemon.length, myScanner, playerName)];

            }
            else if (playerInput.equals("2")) {
            pokemonToDisplay = pokemonPlayerCanActuallyUse.get(Interface.presentOptionsIndex
            (namesOfPokemonPlayerCanUse, namesOfPokemonPlayerCanUse.length, myScanner, playerName));
            }
            playerInput = "";
            pokemonToDisplay.displayPokeInfo();
            System.out.println( pokemonToDisplay.PokeName + " currently has " + 
            pokemonToDisplay.getHPMod() + ", HP left, and is level: " + pokemonToDisplay.getLevel());
                // If they currently have a status condition it will displayed as well
                if (pokemonToDisplay.getStatusCondition()) {
                    System.out.println("\t" + pokemonToDisplay.PokeName + " also currently has the " 
                    + pokemonToDisplay.getCurrentCondition() + " status affliction!");
                    }
            myScanner.nextLine();
            while (!playerInput.equals("yes") && !playerInput.equals("no")) {
                System.out.println(playerName + ", do you want to see the description of one of " +
                "your other pokemon, " + playerName + "? (yes/no)");
                playerInput = myScanner.nextLine().trim().toLowerCase();
            }
            if (playerInput.equals("yes")) {
                // Oh yes I'm a cool kid now, mom get the camera I've made a recersive method!!
                // Jokes aside it will exit the while loop if you type in either yes or no. So yes 
                // will repeat everything again, and no will simply avoid repeating everything.
               displayPlayerPokemonMenu(myScanner);
               
            }
            
            // I believe this is correct.
            if (playerHasToSwap) {
                pokemonCommandSectionBStart(myScanner);
            }   else if (!playerHasToSwap) {
                playerKeepSwapCommandPalette(myScanner);
            }
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

        