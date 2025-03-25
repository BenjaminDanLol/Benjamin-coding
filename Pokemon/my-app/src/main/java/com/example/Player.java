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
    // TODO maybe this setup I'm uncertain tbh. It's possible that it's actually from Team level instead.
    public Player playerTargetted = this;
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
        if (playersActualPokemon.size() == 6) {
            while (!playerInput.equals("yes") && !playerInput.equals("no")) {
            System.out.printf("%nParty of pokemon can only be 6.%n" + playerName + ", do you wish to replace a Pokemon? (yes/no)");
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
                    handleMoveSelection(myScanner);
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
        handleMoveSelection(myScanner);
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
            if (myScanner.hasNextLine()) {
                myScanner.nextLine();
            }
    
            System.out.printf("%n%s do you wish to:%n1) View all your pokemon%n" +
            "2) Only view available ones%n(1/2)%n", playerName);
            
            playersInput = myScanner.nextLine().trim();
    
        } while (!playersInput.matches("[12]"));
    
        if (playersInput.equals("1")) {
            pokemonToDisplay = playersActualPokemon.get(Interface.
            presentOptionsIndexList(namesOfPlayersPokemon, myScanner, playerName));
        }
        else if (playersInput.equals("2")) {
            pokemonToDisplay = pokemonPlayerCanActuallyUse.get(Interface.
            presentOptionsIndexList(namesOfPokemonPlayerCanUse, myScanner, playerName));
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
    // In Player class
    public void handleMoveSelection(Scanner scanner) {
        pokemonInPlay.refreshMovesThatCanBeUsed();
        boolean exitMoveMenu = false;
        
        while (!exitMoveMenu) {
            System.out.printf("%n%s's Move Management for %s:%n", 
                playerName, pokemonInPlay.PokeName);
            
            System.out.println("1) Choose battle move");
            System.out.println("2) View move details");
            int choice = getValidatedInput(scanner, 1, 2);

            switch (choice) {
                case 1 -> {
                boolean didPlayerChoseAMove = selectBattleMove(scanner);
                // Player is forced to choose a move, after entering 1, so naturally they will exit after.
                exitMoveMenu = didPlayerChoseAMove;
                }
                case 2 -> browseMoveDetails(scanner);
            
                }
            }
        }
        
    // Super buggy confirmation but functionally it works
    private boolean getConfirmation(String prompt, Scanner scanner) {
       while (true) {
            System.out.printf("%s (yes/no): ", prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.matches("y|yes")) {
                return true;
            } else if (input.matches("n|no")) {
                return false;
                }   
            System.out.println("Invalid input! Please answer yes or no.");  
            // This should clean up any weird garbage in the scanner
            if (scanner.hasNext()) {
                scanner.nextLine();
            }          
            } 
        }
    private boolean selectBattleMove (Scanner scanner) {
        if (pokemonInPlay.availableMoves.isEmpty()) {
            System.out.println("\n" + pokemonInPlay.PokeName + " has no available moves");
            return false;
        }
        // Show available moves
        int moveIndex = Interface.presentOptionsIndexList(
            pokemonInPlay.availableMoveNames, scanner, playerName
        );
        Move selectedMove = pokemonInPlay.availableMoves.get(moveIndex);
        
        // Show move details for confirmation
        System.out.println("\nSelected Move Details:");
        selectedMove.displayMoveInfo();
        
        // Get confirmation
        if (getConfirmation("Use this move?", scanner)) {
            pokemonInPlay.moveInUsage = selectedMove;
            System.out.println(pokemonInPlay.PokeName + " will use " + selectedMove.moveName + "!");
            return true;
        } 

        System.out.println("Move selection cancelled. Choose again:");
        return false;
            
    }
    private void browseMoveDetails(Scanner scanner) {
        boolean viewingMoves = true;
        
        while (viewingMoves) {
            System.out.printf("%n%s's Move Details for %s:%n",
                playerName, pokemonInPlay.PokeName);
            
            System.out.println("1) View all moves");
            System.out.println("2) View available moves");
            System.out.println("3) Return");
            int choice = getValidatedInput(scanner, 1, 3);

            switch (choice) {
                case 1 -> displayFullMoveList(scanner);
                case 2 -> displayAvailableMoves(scanner);
                case 3 -> viewingMoves = false;
            }
        }
    }

    private void displayFullMoveList(Scanner scanner) {
        int moveIndex = Interface.presentOptionsIndexList(
            pokemonInPlay.allPokemonMoveNames, scanner, playerName
        );
        pokemonInPlay.pokemonMoves.get(moveIndex).displayMoveInfo();
    }

    private void displayAvailableMoves(Scanner scanner) {
        int moveIndex = Interface.presentOptionsIndexList(
            pokemonInPlay.availableMoveNames, scanner, playerName
        );
        pokemonInPlay.availableMoves.get(moveIndex).displayMoveInfo();
    }
    private int getValidatedInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                System.out.print("Enter choice: ");
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) return input;
                System.out.printf("Invalid choice! Enter %d-%d%n", min, max);
            } catch (NumberFormatException e) {
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