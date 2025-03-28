package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String playerName = "Ash Ketchum";
    private Team myTeam;
    ArrayList<Pokemon> playersActualPokemon = new ArrayList<>();
    ArrayList<String> namesOfPlayersPokemon = new ArrayList<>();
    ArrayList<Pokemon> pokemonPlayerCanActuallyUse = new ArrayList<>();
    ArrayList<String> namesOfPokemonPlayerCanUse = new ArrayList<>();
    public boolean allPokemonAreFainted = false;
    public boolean playerHasToSwap;
    public Pokemon pokemonUsedBefore;
    public Pokemon pokemonInPlay;
    public Pokemon pokemonToDisplay;
    // insert this into getTarget, when it is the pokemons turn.
    public int enemyPlayerTargetInitialIndex;

    public Player(Scanner myScanner, int playerNum) {
        String playerInput = "";
        while (!playerInput.equals("x")) { 
            // It constantly skip the players input so I needed to clean scanner here as well
            if (myScanner.hasNextLine()) {
                myScanner.nextLine();
            }
            System.out.println("\nPlayer " + playerNum + " of team " + myTeam.teamName + "!\n");
            System.out.println("What will your name be this session?");
            playerName = myScanner.nextLine().trim();
            System.out.println("\nYour' playername will be " + playerName + "\nType X to confirm: ");
            playerInput = myScanner.next().toLowerCase();
        }

        pokemonUsedBefore = Interface.fakeMon;
        pokemonInPlay = Interface.fakeMon;
        pokemonToDisplay = Interface.fakeMon;
    }

    public Team getMyTeam() {
        return myTeam;
    }
    public void setTeam(Team team) {
        myTeam = team;
    }

    public void addAPokemon(Scanner myScanner, Interface myInterface, int filter) {
        String playerInput = "";
        if (playersActualPokemon.size() == 6) {
            while (!playerInput.equals("yes") && !playerInput.equals("no")) {
            System.out.printf("%nParty of pokemon can only be 6.%n" + playerName + ", do you wish to replace a Pokemon? (yes/no)");
            playerInput = myScanner.nextLine().trim().toLowerCase();
            }
            if (playerInput.equals("no")) {
                System.out.println("No new pokemon was chosen.");
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
            playersActualPokemon.get(pChoice).trainer = this;
            namesOfPlayersPokemon.remove(pChoice);
            namesOfPlayersPokemon.add(pChoice, pokemonToDisplay.PokeName);
        }
        else {
            pokemonToDisplay = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
            playersActualPokemon.add(pokemonToDisplay);
            namesOfPlayersPokemon.add(pokemonToDisplay.PokeName);
            playersActualPokemon.get(playersActualPokemon.size() - 1).trainer = this;
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
            pokemonInPlay = Interface.fakeMon;
            return;
        }
        if (!playerHasToSwap) {
            swapOrKeepPokemonIn(myScanner);
        }
        else if (playerHasToSwap) {
            playerControllerBStart(myScanner);
        }
    }
    private void swapOrKeepPokemonIn(Scanner myScanner) {
        String playerInput = "";
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
                System.out.printf("\n%s lvl %d HP (%d/%d) ", pokemon.PokeName, pokemon.getLevel(), 
                pokemon.getHPMod(), pokemon.baseHP);
                if (pokemon.isFainted) {
                    System.out.print("(Fainted): \nKnows move(s)");
                }
                else {
                    System.out.print(": \nKnows move(s)");
                }
                for (int i = 0; i < pokemon.pokemonMoves.size(); i++) {
                    System.out.print(pokemon.pokemonMoves.get(i).moveName);
                    if (pokemon.pokemonMoves.get(i).PP <= 0 || pokemon.pokemonMoves.get(i).isDisabled) {
                        // Felt that writing out isDisabled or anything else would feel misleading.
                        System.out.print(" (!)");
                    }
                    if (i != pokemon.pokemonMoves.size() - 1) {
                        System.out.print(", ");
                    }
                    else {
                        System.out.print(".");
                    }
                }
                if (pokemon.getStatusCondition()) {
                    System.out.printf("\nCurrently has the status condition: %s!", 
                    pokemon.getCurrentCondition());
                }
                if (!pokemon.SecondaryConditions.isEmpty()) {
                    if (pokemon.SecondaryConditions.size() == 1) {
                System.out.printf("\nCurrently has the secondary status condition: %s!", pokemon
                .SecondaryConditions.get(0));
                    } else {
                        System.out.print("\nCurrently has the secondary status conditions: ");
                        for (int i = 0; i < pokemon.SecondaryConditions.size(); i++) {
                            System.out.print(pokemon.SecondaryConditions.get(i));
                            if (i != pokemon.SecondaryConditions.size() - 1) {
                                System.out.print(", ");
                            } else {
                                System.out.print(".");
                            }
                        }
                    }
                }
            }
        }
        private void displayOnlyPokemonPlayerCanUse() {
            for (Pokemon pokemon : pokemonPlayerCanActuallyUse) {
                System.out.printf("\n%s lvl %d HP (%d/%d) ", pokemon.PokeName, pokemon.getLevel(), 
                pokemon.getHPMod(), pokemon.baseHP);
                    System.out.print(": \nKnows move(s)");
                for (int i = 0; i < pokemon.pokemonMoves.size(); i++) {
                    System.out.print(pokemon.pokemonMoves.get(i).moveName);
                    if (pokemon.pokemonMoves.get(i).PP <= 0 || pokemon.pokemonMoves.get(i).isDisabled) {
                        // Felt that writing out isDisabled or anything else would feel misleading.
                        System.out.print(" (!)");
                    }
                    if (i != pokemon.pokemonMoves.size() - 1) {
                        System.out.print(", ");
                    }
                    else {
                        System.out.print(".");
                    }
                }
                if (pokemon.getStatusCondition()) {
                    System.out.printf("\nCurrently has the status condition: %s!", 
                    pokemon.getCurrentCondition());
                }
                if (!pokemon.SecondaryConditions.isEmpty()) {
                    if (pokemon.SecondaryConditions.size() == 1) {
                System.out.printf("\nCurrently has the secondary status condition: %s!", pokemon
                .SecondaryConditions.get(0));
                    } else {
                        System.out.print("\nCurrently has the secondary status conditions: ");
                        for (int i = 0; i < pokemon.SecondaryConditions.size(); i++) {
                            System.out.print(pokemon.SecondaryConditions.get(i));
                            if (i != pokemon.SecondaryConditions.size() - 1) {
                                System.out.print(", ");
                            } else {
                                System.out.print(".");
                            }
                        }
                    }
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
        if (scanner.hasNext()) {
            scanner.nextLine();
        } 
       while (true) {
            System.out.printf("\n%s (yes/no): ", prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.matches("y|yes")) {
                return true;
            } else if (input.matches("n|no")) {
                return false;
                }   
            System.out.println("Invalid input! Please answer yes or no.");  
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
        if (scanner.hasNext()) {
            scanner.nextLine();
        }
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
    
    // Used for battles specifically.
    public void playerController (Scanner myScanner, Team enemyTeam) {

    System.out.printf("\n%s's turn", playerName); 
    updatePokemonPlayerCanUse();

        if (allPokemonAreFainted) {
            System.out.println(playerName + " has no pokemon left to swap in.");
            return;
        }
    
    System.out.println("Type anything to continue.");
    playerAction(myScanner, enemyTeam);
    }

    // Global boolean
    private boolean hasPerformedAction;

    private void playerAction(Scanner myScanner, Team enemyTeam) {
    hasPerformedAction = false;
    String playerInput = "0";

    if (!playerHasToSwap && !enemyTeam.playersCurrentlyInBattle.isEmpty()) {
        while (!hasPerformedAction) {
            while (true) {
                System.out.printf("\n(1) To Party\n(2) View Enemy LineUp\n(3) Use A Move\n%s: ", playerName);
    
                playerInput = myScanner.next();
    
                if (playerInput.equals("1")  || 
                playerInput.equals("2") || playerInput.equals("3")) {
                break;
                    }
                System.out.println("Invalid Input! " + playerName + ", can only enter command 1 or 2!");

                }
            switch (playerInput) {
                case "1" -> toParty(myScanner);
                case "2" -> enemyTeam.displayLineUp();
                case "3" -> chooseMoveTurn(myScanner, enemyTeam);
            }
        }
    }

    else if (playerHasToSwap && !enemyTeam.playersCurrentlyInBattle.isEmpty()) {
        while (!hasPerformedAction) {
            while (true) {
                System.out.printf("\n(1) To Party\n(2) View Enemy LineUp\n%s: ", playerName);
    
                playerInput = myScanner.next();
    
                if (playerInput.equals("1")  || playerInput.equals("2")) {
                break;
                    }
                System.out.println("Invalid Input! " + playerName + ", can only enter command 1 or 2!");
                }
            switch (playerInput) {
                case "1" -> toParty(myScanner);
                case "2" -> enemyTeam.displayLineUp();
            }
        }
    }

    else if (playerHasToSwap && enemyTeam.playersCurrentlyInBattle.isEmpty()) {
        while (!hasPerformedAction) {
            // Player has no choice
            toParty(myScanner);
        }
    }

    else if (!playerHasToSwap && enemyTeam.playersCurrentlyInBattle.isEmpty()) {
        System.out.print("\n" + playerName + " doesn't need to perfrom any actions since." + 
        "\n" + myTeam.teamName + " won!!");
        myTeam.winCount++; // Pretty shit if it goes off on every player, but I don't see
        myTeam.wonOrLost = true; // I'll use this as a check under performMove loop.
            }
    }

    private void chooseMoveTurn(Scanner myScanner, Team enemyTeam) {
        if (pokemonInPlay.equals(Interface.fakeMon)) {
            System.out.println("Player has no pokemon. Returning to menu");
            return;
        } // Just a lil checkerooski
    
    boolean localFlag = false;
    ArrayList<Integer> indexEntry = new ArrayList<>();

    while (!localFlag) {
    System.out.println();
    int m = 1;
        for (int i = 0; i < pokemonInPlay.pokemonMoves.size(); i++) {
            if (pokemonInPlay.pokemonMoves.get(i).canMoveBeUsed()) {
            System.out.printf("\n(%d) %s", m, pokemonInPlay.pokemonMoves.get(i).moveName);
            indexEntry.add(i); // Ensuring that it’s the correct index being added
            m++;
                }
        }

    if (m == 1) {
        System.out.printf("\n%s has no available moves. \n " +
        "%s only option is to have %s use struggle.\n(1) Struggle", 
        pokemonInPlay.PokeName, playerName, pokemonInPlay.PokeName);
        pokemonInPlay.moveInUsage = Interface.struggle;
    break; // Important that the next while loop isn’t entered. If this if statement is correct.
    }
        int playerChoice;
        playerChoice = getValidatedInput(myScanner, 1, pokemonInPlay.pokemonMoves.size());
        pokemonInPlay.moveInUsage = pokemonInPlay.pokemonMoves.get(indexEntry.get(playerChoice - 1)); 
    break;
    }
    // While loop is exited here, and it’s guaranteed player has chosen a move
    enemyPlayerTargetInitialIndex = enemyTeam.pickATarget(myScanner); 
    /*
    Should  honestly be an attribute that is a modifier. Then later I’ll store a player object that is = getTarget 
    and if that at any time is null, then one of the teams one if not, I can directly insert it as the victim in 
    performMove.
     */

    if (myScanner.hasNext()) {myScanner.nextLine();}

    System.out.printf("\n\n%s will use %s against %s.\nType X to confirm: ", pokemonInPlay.PokeName, 
    pokemonInPlay.moveInUsage.moveName, enemyTeam.players[enemyPlayerTargetInitialIndex].pokemonInPlay.PokeName);

        if (!myScanner.next().toLowerCase().startsWith("x")){
        return;	
        } 
        hasPerformedAction = true; // And just like that the player can exit in and out of this method. 
    }


    // Not inside since the toPartyWillRepeat flag needs to be adjusted outside this method. 
    // Even outside the object itself therefore it is public as well.
    public boolean toPartyWillRepeat;


    public void toParty(Scanner myScanner) {
        String playerInput;
        toPartyWillRepeat = true;
        while (toPartyWillRepeat) {
        playerInput = "0";
        if (myScanner.hasNext()) {myScanner.nextLine();}
        while (!playerInput.equals("1") || !playerInput.equals("2")) {
            System.out.printf("\n(1) Switch In Pokemon\n(2) Enter ViewingMode\n%s: ", playerName);

            playerInput = myScanner.next();

            if (!playerInput.equals("1") || !playerInput.equals("2")) {
                System.out.println("Invalid Input! " + playerName + ", can only enter command 1 or 2!");
                }
            }

            switch (playerInput) {
                case "1" -> {
                    displayAllPokemon();
                    // From this case the player can/will have set performAnAction to true
                    // naturally also toPartyWillRepeat is set to false in conjuction with that.
                        if (playerHasToSwap) {
                            fieldAPokemon(myScanner);
                        } else if (!playerHasToSwap) {
                            optionalChooseAPokemon(myScanner);
                        }
                }
                case "2" -> {
                    enterViewingMode(myScanner);
                }
            }
        }
    }

    // Global boolean
    private boolean viewRepeat;

    private void enterViewingMode(Scanner myScanner) {
        viewRepeat = true;
        // If this is a global variabel 
        do { 
        String playerInput = "0";

        if (myScanner.hasNext()) {
            myScanner.nextLine();
        }
        
        while (!playerInput.equals("1") || 
        !playerInput.equals("2") || !playerInput.equals("3")) {
            System.out.printf("\n(1) View battle ready Pokémon" + 
            "\n(2) View All Pokémon (including fainted Pokémon)\n(3) Return\n%s: ", playerName);
            playerInput = myScanner.next();

            if (!playerInput.equals("1") || 
            !playerInput.equals("2") || !playerInput.equals("3")) {
                System.out.println("Invalid Input! " + playerName + ", can only enter command 1, 2 or 3!");
                }
            }
            
        //updatePokemonPlayerCanUse();

        switch (playerInput) {
            case "1" -> viewPlayersPokemon(myScanner, true);
            case "2" -> viewPlayersPokemon(myScanner, false);
            case "3" -> viewRepeat = false;
            }
        } while (viewRepeat);
        // toPartyWillRepeat = false; only use this when toParty itself should be exited
    }

    // Essentially a replacement of a global flag. I believe it's called.
    boolean shouldNotExitViewPlayersPokemon;

    private void viewPlayersPokemon(Scanner myScanner, boolean onlyNonFainted) {
        byte counter;
        ArrayList<Integer> mapOfCounterToPokemonSlot = new ArrayList<>();
        String input;
        shouldNotExitViewPlayersPokemon = true;

        while (shouldNotExitViewPlayersPokemon) {
        counter = 1;
        mapOfCounterToPokemonSlot.clear();
        System.out.println();

        for (int i = 0; i < playersActualPokemon.size(); i++) {
            if (!playersActualPokemon.get(i).isFainted && onlyNonFainted) {
                System.out.printf("\n(%d) View %s", counter, 
                playersActualPokemon.get(i).PokeName);
                mapOfCounterToPokemonSlot.add(i);
                counter++;
            }
            if (!onlyNonFainted) {
                System.out.printf("\n(%d) View %s", counter, 
                playersActualPokemon.get(i).PokeName);
                mapOfCounterToPokemonSlot.add(i);
                counter++;
            }
        }
        System.out.printf("\n(%d) Return", counter);

        System.out.println(".. to continue");
        myScanner.next();
        if (myScanner.hasNext()) {myScanner.nextLine();}
        
        boolean dontRepeatCommand = false;
        int indexChoice = -1;

        while (!dontRepeatCommand) {
            System.out.printf("\n%s (1-" + mapOfCounterToPokemonSlot.size() + "): ", playerName);
            input = myScanner.next();
            try {
                indexChoice = Integer.parseInt(input);
                if (indexChoice >= 1 && indexChoice <= mapOfCounterToPokemonSlot.size()) {
                    dontRepeatCommand = true;
                } else {
                System.out.print("\nPlease enter a number between 1 and " + mapOfCounterToPokemonSlot.size());
                }
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input! Please enter a number");
            }
        }
        if (indexChoice == mapOfCounterToPokemonSlot.size()) {return;}

        dontRepeatCommand = false;
        System.out.println(".. to continue");
        myScanner.next();
        if (myScanner.hasNext()) {
            myScanner.nextLine();
        }

        System.out.printf("\n(1) View %s's moves\n(2) View %s's usable moves" + 
        "\n(3) View another Pokémon\n(4) Exit Viewing-Mode\n", 
        playersActualPokemon.get(mapOfCounterToPokemonSlot.get(indexChoice - 1)).PokeName, 
        playersActualPokemon.get(mapOfCounterToPokemonSlot.get(indexChoice - 1)).PokeName);
        int playerChoice;

        while (!dontRepeatCommand) {
            System.out.printf("\n%s (1-4): ", playerName);
            input = myScanner.next();
            try {
                playerChoice = Integer.parseInt(input);
                if (playerChoice >= 1 && playerChoice <= 4) {
                    dontRepeatCommand = true;
                }
            } catch (NumberFormatException e) {
                System.out.printf("\nInvalid input!", playerName);
            }
            System.out.println("Invalid input! Please use command 1, 2, 3 or 4.");
        }
        switch (indexChoice) {
            case 1 -> {
            playersActualPokemon.get(mapOfCounterToPokemonSlot.get(indexChoice - 1)).
            viewMoves(myScanner, false);
            }
            case 2 -> {
            playersActualPokemon.get(mapOfCounterToPokemonSlot.get(indexChoice - 1)).
            viewMoves(myScanner, true);
            }
            // case 3 -> switch case is in an infinite loop, so per default we will view another pokemon.
            case 4 -> {
                shouldNotExitViewPlayersPokemon = false;
                }
            }
        }
        viewRepeat = false; //
    }
    // TODO delay this so it executes first after the opposing team has made their "move". FUTURE ME unsure if that really is necessary.
    private void optionalChooseAPokemon(Scanner myScanner) {
        int indexChoice = -1;

        while (true) {
        System.out.print("\nWhich Pokémon will you choose to swap in? (1-" + playersActualPokemon.size() + "): ");
        String input = myScanner.nextLine();
        try {
            indexChoice = Integer.parseInt(input);
            if (indexChoice >= 1 && indexChoice <= playersActualPokemon.size()) {
                break;
            }
        } catch (NumberFormatException e) {
            System.out.printf("\nInvalid input. %s must choose a pokemon!", playerName);
        }
        System.out.println("Invalid input! Please enter a number between 1 and " + 
        playersActualPokemon.size() + ".");
        }

            if (playersActualPokemon.get(indexChoice-1).isFainted) {
            System.out.printf("\nYou cannot switch in a pokemon which is fainted! \nReturning to menu!");
            hasPerformedAction = false;
            return;
            }

            if (playersActualPokemon.get(indexChoice-1).equals(pokemonInPlay) && !playerHasToSwap) {
            System.out.printf("\nDoes %s wish to keep in %s?\nType X to confirm: ", playerName, 
            pokemonInPlay.PokeName);
                if (!myScanner.nextLine().trim().equalsIgnoreCase("x")) {
                hasPerformedAction = false;
                return;	
                    } 
                }

            System.out.printf("\n%s is currently swapping out %s for %s? \nType X to confirm", playerName,
            pokemonInPlay.PokeName, playersActualPokemon.get(indexChoice-1).PokeName);
            if (!myScanner.next().trim().equalsIgnoreCase("x")) {
                    hasPerformedAction = false;
                    return;	
                } 

            pokemonUsedBefore = pokemonInPlay;
            pokemonInPlay = playersActualPokemon.get(indexChoice - 1);
            toPartyWillRepeat = false;
            hasPerformedAction = true;
        }
    private void fieldAPokemon(Scanner myScanner) {
        int indexChoice = -1;
        // Ensure valid input and range check
    while (true) {
        System.out.print("\nWhich Pokémon will you choose to swap in? (1-" + playersActualPokemon.size() + "): ");
        String input = myScanner.nextLine();
        try {
            indexChoice = Integer.parseInt(input);
            if (indexChoice >= 1 && indexChoice <= playersActualPokemon.size()) {
                break; // Valid input, exit loop
            }
        } catch (NumberFormatException e) {
            System.out.printf("\nInvalid input. \nReturning to menu!");
            return;
        }
        System.out.println("To switch in a pokemon! Please enter a number between 1 and " + 
        playersActualPokemon.size() + ".\n");
        }
            if (playersActualPokemon.get(indexChoice-1).isFainted) {
            System.out.printf("\nYou cannot switch in a pokemon which is fainted! \nReturning to menu!");
            hasPerformedAction = false;
            return;
            }

            System.out.printf("\nIs %s currently switching in in %s? \nType X to confirm: ", playerName,
            playersActualPokemon.get(indexChoice-1).PokeName);

            if (!myScanner.next().trim().equalsIgnoreCase("x")) {
                    hasPerformedAction = false;
                    return;	
                } 

            pokemonUsedBefore = pokemonInPlay;
            pokemonInPlay = playersActualPokemon.get(indexChoice - 1);
            toPartyWillRepeat = false;
            hasPerformedAction = true;
        }
    } 