package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Interface {
    InputStream pokemonTieringStream;
    InputStream pokemonStream;
    JsonNode pokemonRoot;
    JsonNode typesArray;
    JsonNode typeMappingNode;
    private final ObjectMapper mapper = new ObjectMapper();
    String[] allPokemonTypes;

    Map<String, Integer> pokemonMap = new HashMap<>();
    Map<String, Integer> pokemonMapAllInclusive = new HashMap<>();
    Iterator<Map.Entry<String, JsonNode>> fields;
    Map.Entry<String, JsonNode> entry;
    List<String> keyList;
    String[] keySetConverted;
    String randPokeChoice;

    Map<String, Pokemon> pokeMap;
    Pokemon currentPokemon;

    InputStream moveStream;
    Map<String, Move> moveMap;
    
    Move currentMove;
    Player[] pTeam1;
    Player[] pTeam2;

    public Interface(Scanner myScanner) {
        try {
            int sizeOfPlayerTeam1;
            int sizeOfPlayerTeam2;
            String confirmDecline;
            while (true) {
                System.out.println("How many players will be in team 1? ");
            try {            
            
            sizeOfPlayerTeam1 = myScanner.nextInt();
            if (sizeOfPlayerTeam1 > 0) {
                System.out.println("Team 1 will have " + sizeOfPlayerTeam1 + " players.");
                System.out.println("yes to confirm: ");
                confirmDecline = myScanner.next().trim().toLowerCase();
                if (confirmDecline.equals("yes")) {
                    // Not sure where this will lead but whatevs.
                    break;
                }
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                myScanner.nextLine(); 
            }   }

            while (true) {
            System.out.println("How many players will be in team 2? ");

            try {
                sizeOfPlayerTeam2 = myScanner.nextInt();
                if (sizeOfPlayerTeam2 > 0) {
                    System.out.println("Team 2 will have " + sizeOfPlayerTeam2 + " players.");
                    System.out.println("yes to confirm: ");
                    confirmDecline = myScanner.next().trim().toLowerCase();
                    if (confirmDecline.equals("yes")) {
                        // Not sure where this will lead but whatevs.
                        break;
                    }
                }
            
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                myScanner.nextLine();
            }   }
            pTeam1 = new Player[sizeOfPlayerTeam1];
            pTeam2 = new Player[sizeOfPlayerTeam2];
            for (int i = 0; i < pTeam1.length; i++) {
                /* It's weird on the first iteration it skips L14 in player constructer.
                It's an odd bug have no idea how come it happens, since it only happens for team 1 element 1. */
                System.out.println("Team 1, player " + (i + 1));
                pTeam1[i] = new Player(myScanner);
            }
            for (int i = 0; i < pTeam2.length; i++) {
                System.out.println("Team 2, player " + (i + 1));
                pTeam2[i] = new Player(myScanner);
            }

            for (Player team1 : pTeam1) {
                System.out.print(team1.getPlayerName() + " ");
            }
            System.out.print("VERSUS ");
            for (Player team2 : pTeam2) {
                System.out.print(team2.getPlayerName() + " ");
            }

            pokemonTieringStream = Interface.class.getClassLoader().getResourceAsStream("resources/PokeTiering.json");
                if (pokemonTieringStream == null) {
                    System.out.println("PokeTiering.json file not found");
                }
            pokemonRoot = mapper.readTree(pokemonTieringStream);
        
            typesArray = pokemonRoot.path("Typings");
            if (!typesArray.isArray() || typesArray.size() == 0) {
                System.out.println("\"Typings\" array is missing or empty.");
            }
        
            allPokemonTypes = new String[typesArray.size()];
            for (int i = 0; i < allPokemonTypes.length; i++){
                allPokemonTypes[i] = typesArray.get(i).asText();
            }

            pokemonStream = Interface.class.getClassLoader().getResourceAsStream("resources/PokeTieringattempt.json");
            if (pokemonStream == null) {
                // I may want to remove this shit, and just print it out and trust the catcher to catch.
                throw new IllegalArgumentException("Resource not found!");
            }
            pokeMap = mapper.readValue(pokemonStream, new TypeReference<Map<String, Pokemon>>(){});


            moveStream = Move.class.getClassLoader().getResourceAsStream("resources/moves.json");
            if (moveStream == null) {
                System.out.println("moves.json file not found");
            }
            moveMap = mapper.readValue(moveStream, new TypeReference<Map<String, Move>>(){});


        } catch (IOException e) {
            // IDE doesn't like this throw stack trace, maybe it's cuz it's impossible or smthn.
            }
    } 
    public void startBattleT1vT2(Scanner myScanner) {
        // This should naturally be adjusted off of which pokemon are alive/dead.
        // Also the loops are kind of non sensical, since I should simply take the moves and pokemon in one swoop.
        // So the next four loops should be sandwiched into two loops for team 1 and 2 respectively.
        Pokemon[] team1Choices = new Pokemon[pTeam1.length];
        Pokemon[] team2Choices = new Pokemon[pTeam2.length];
        Pokemon[] executionOrder = new Pokemon[pTeam1.length + pTeam2.length];
        // I could consider parsing or something here. To make it obvious which team it is.
        String[] pokeNamesT1Chose = new String[pTeam1.length];
        String[] pokeNamesT2Chose = new String[pTeam2.length];
        // When this will be looped until all pokemon are dead from a team, then I'll to add some checks
        // For if they are fainted. I imagine I'll need an arraylist instead sadly.
        for (int i = 0; i < team1Choices.length; i++) {            
                // The current iteration of team1 player chooses their pokemon

            team1Choices[i] = pTeam1[i].playerControllerBStart(myScanner);
            executionOrder[i] = team1Choices[i];
            // Used for targeting options for the opposing team later.
            pokeNamesT1Chose[i] = team1Choices[i].PokeName;
        }

        for (int i = 0; i < team1Choices.length; i++) {
            // I've added the + 1 since presentOptionsIndex is adjusted for 0 based indexes, but I don't want to show move index 0.

            System.out.println(pTeam1[i].getPlayerName() + " from Team 1, choose what move " + team1Choices[i].PokeName + " should use!");
            team1Choices[i].setMoveInUsage(team1Choices[i].getAPokemonsMove(presentOptionsIndex(team1Choices[i].pokemonMoves(),
            team1Choices[i].pokemonMoves().length, myScanner, pTeam1[i].getPlayerName()) + 1));
        }

        for (int i = 0; i < team1Choices.length; i++) {
            if (team1Choices[i].getMoveInUsage().toVictim != false) {
                System.out.println("Who should " + team1Choices[i].PokeName + "target?");

                team1Choices[i].target = pTeam2[presentOptionsIndex(pokeNamesT2Chose, 
                pokeNamesT2Chose.length, myScanner, pTeam1[i].getPlayerName())].getTarget(team1Choices[i]);
            } 
            
        }

        // Clear the scanner here so the opposing team can't see what the first team chose.
        
        for (int i = 0; i < team2Choices.length; i++) {
            team2Choices[i] = pTeam2[i].playerControllerBStart(myScanner);
            executionOrder[i] = team2Choices[i];
            pokeNamesT2Chose[i] = team2Choices[i].PokeName;
        }

        for (int i = 0; i < team2Choices.length; i++) {
            System.out.println(pTeam2[i].getPlayerName() + " from Team 2, choose what move " + team2Choices[i].PokeName + " should use!");
            team2Choices[i].setMoveInUsage(team2Choices[i].getAPokemonsMove(presentOptionsIndex(team2Choices[i].pokemonMoves(),
            team2Choices[i].pokemonMoves().length, myScanner, pTeam2[i].getPlayerName())));
        }

        for (int i = 0; i < team2Choices.length; i++) {
            if (team2Choices[i].getMoveInUsage().toVictim != false) {
                System.out.println("Who should " + team2Choices[i].PokeName + "target?");
                team2Choices[i].target = pTeam1[presentOptionsIndex(pokeNamesT1Chose, 
                pokeNamesT1Chose.length, myScanner, pTeam2[i].getPlayerName())].getTarget(team2Choices[i]);
            }
        }

        // Bubble sort the prio/speed of all pokemon, essentially order the pokemon by execution of moves.
        boolean swapped;
        do {
            swapped = false;
                for (int j = 0, n = executionOrder.length; j < n - 1; j++) {
                    /*
                     * It goes like so. If the next pokemon in array has a higher prio then the current
                     * swap, or if their prio is tied, then check for if the next pokemon is faster than
                     * the current if so swap and declare a swap has been done. In other words make it
                     * so that the this for loop is checked for again.
                     * Else nothing will happen.
                     * For cases where the pokemon is being swapped then their moveInUsage prio is 10.
                     * Later I can have moves like pursuit or other custom moves that interfere with pokeswapping.
                     */
                    if  (executionOrder[j].getMoveInUsage().priority < executionOrder[j + 1].getMoveInUsage().priority ||
                        (executionOrder[j].getMoveInUsage().priority == executionOrder[j + 1].getMoveInUsage().priority &&
                        (executionOrder[j].getSpdMod() * executionOrder[j].baseSpd) < 
                        (executionOrder[j + 1].getSpdMod() * executionOrder[j + 1].baseSpd))) {
                        swap(executionOrder, j, (j + 1));
                        swapped = true;
                    }
            }
        } while (swapped);

        for (Pokemon pokemon : executionOrder) {
            if (pokemon.isTeam1) {
            pokemon.getMoveInUsage().performMove(pokemon, pokemon.target);
            }
            else {
            pokemon.getMoveInUsage().performMove(pokemon, pokemon.target);
            }
        }
    }
    public void startBattleTwoPlayers(Player p1, Player p2, Scanner myScanner) {

        // Let player 1 and 2 choose their pokemon
        int p1choice = presentOptionsIndex(p1.allOfPokemonNames(), p1.allOfPokemonNames().length, myScanner, p1.getPlayerName());
        int p2choice = presentOptionsIndex(p2.allOfPokemonNames(), p2.allOfPokemonNames().length, myScanner, p2.getPlayerName());
        System.out.println(p1.getPlayerName() + " chooses " + p1.getPokemonFromPlayer(p1choice).PokeName);
        System.out.println(p2.getPlayerName() + " chooses " + p2.getPokemonFromPlayer(p2choice).PokeName);

        // Let player 1 and 2 furthermore choose a move for that pokemon to perform
        int p1moveChoice = presentOptionsIndex(p1.getPokemonFromPlayer(p1choice).pokemonMoves(), p1.getPokemonFromPlayer(p1choice)
        .pokemonMoves().length, myScanner, p1.getPlayerName());
        p1.getPokemonFromPlayer(p1choice).getAPokemonsMove(p1moveChoice)
        .performMove(p1.getPokemonFromPlayer(p1choice), p2.getPokemonFromPlayer(p2choice));

        int p2moveChoice = presentOptionsIndex(p2.getPokemonFromPlayer(p2choice).pokemonMoves(), p2.getPokemonFromPlayer(p2choice)
        .pokemonMoves().length, myScanner, p2.getPlayerName());
        p2.getPokemonFromPlayer(p2choice).getAPokemonsMove(p2moveChoice)
        .performMove(p2.getPokemonFromPlayer(p2choice), p1.getPokemonFromPlayer(p1choice));

    }
    public Pokemon getAPokemonStandardized(int filter, Scanner myScanner, int howManyMonsInPokemonPool, String playerName){
        String selectedType = presentOptions(allPokemonTypes, 4, myScanner, playerName);
        typeMappingNode = pokemonRoot.path("TypeMapping").path(selectedType);
        if (!typeMappingNode.isObject()){
        System.out.println("No corresponding mapping to type: " + selectedType);
        }
        // I'll sadly have to recreate a new AtomicInteger each time the method is called.
        return filterThroughThePokemon(filter, typeMappingNode, howManyMonsInPokemonPool, myScanner, playerName);
    }
    public Move getASpecificMove(Scanner myScanner, String theMove) {
        currentMove = moveMap.get(theMove);
        return currentMove;
        } 
    public Pokemon filterThroughThePokemon(int filter, JsonNode typeMappingNode, 
    int amountOfPokemon, Scanner myScanner, String playerName) {
        AtomicInteger chosenFilter = new AtomicInteger(filter);
        fields = typeMappingNode.fields();
        int pokemonPool = amountOfPokemon;

        while (fields.hasNext()) {
            entry = fields.next();
            pokemonMapAllInclusive.put(entry.getKey(), entry.getValue().asInt());
        }

        if (filter > 1000) {
            if (filter >= 2000) {
                pokemonPool = 4;
            } else {
            // Do something more unique here it's to lazy
                pokemonPool = 4;
            }
        }
        else {
        while (pokemonMap.size() < pokemonPool) {
            pokemonMap.clear();
            pokemonMapAllInclusive.forEach((key, value) -> {
                if (value < chosenFilter.get()){
                    pokemonMap.put(key, value);
                } 
            });
            chosenFilter.addAndGet(50);
                } 
        }
        chosenFilter.getAndSet(200);

        while (pokemonMap.size() < pokemonPool) {
            pokemonMap.clear();
            pokemonMapAllInclusive.forEach((key, value) -> {
                if (value > (1000 - chosenFilter.get())){
                    pokemonMap.put(key, value);
                } 
            });
            chosenFilter.addAndGet(100);
            }
        // This looks ugly but it's to convert the PokemonMap all the way down to just the key.
        keyList = new ArrayList<>(pokemonMap.keySet());
        keySetConverted = keyList.toArray(String[]::new);
        randPokeChoice = presentOptions(keySetConverted, 4, myScanner, playerName);
            // Since this method will be reused
            pokemonMap.clear();
            pokemonMapAllInclusive.clear();
        System.out.println(playerName + " chose: " + randPokeChoice);
        currentPokemon = pokeMap.get(randPokeChoice);
        currentPokemon.displayPokeInfo();
        return currentPokemon;
    }
    public static List<Integer> generateUniqueList(int size, int range) {
        if (size <= 0 || range <= 0) {
            throw new IllegalArgumentException("Size or range is 0 or less");
        }
        /* There may be no opportunity to randomize, since the sizing or choices
        the player can choose from is larger or equal to said Random elements.
        This is simply a complex but concise way of making an ordered list.
        Essentially it starts and 0, and ends size (exclusive).
        .range ensures the ints are ordered
        .boxed() converts to Int objects, or more accurate wrapper object Integer.
        We need .boxed since Lists don't store primitive values like ints.
        .collect, simply stores whatever was processed in the "Iteration" as
        a -sequential- list (because of range remember).
        Collectors.toList() initializes a List, (usually an ArrayList).
        The way it detects the type of List it should create is 1. Java type inference.
        2. .boxed() already has the typing it should store. So the invisible <T> for the
        method fills out the typing of the list initialized.
        And the Collectors.toList() is typically 
        */

        if (size >= range) {
            return IntStream.range(0, size)
                .boxed()
                .collect(Collectors.toList());
        }

        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (uniqueNumbers.size() < size) {
            int randomNumber = random.nextInt(range);
            uniqueNumbers.add(randomNumber);
        }

        // You can't access elements from a set directly, therefor we need a list instead
        List<Integer> choicesForPlayer = new ArrayList<>(uniqueNumbers);

        return choicesForPlayer;
    }
    public static String presentOptions(String[] choices, int numOfChoices, Scanner myScanner, String playerName) {

        List<Integer> randChoices = generateUniqueList(numOfChoices, choices.length);
        System.out.println("Choose one of " + numOfChoices + " " + playerName + ": ");
        for (int i = 0; i < numOfChoices; i++)
        {
            System.out.printf(" %d. %s", i + 1, choices[randChoices.get(i)]);
            System.out.printf(" or rather randChoice: " + randChoices.get(i));
            System.out.println();
        }
        int thePlayersChoice;
        while (true) {
        System.out.print("Your' choice(s) (1-" + numOfChoices + "): ");
        try {
        
            thePlayersChoice = myScanner.nextInt();
            if (thePlayersChoice >= 1 && thePlayersChoice <= numOfChoices) {
                break; // Exits the while loop. 
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            myScanner.nextLine(); // Read the next line, instead of the line where user misinputted.
            }
        }
        // Map user's choice (1-based) to the index in randChoices (0-based)
        int selectedIndex = randChoices.get(thePlayersChoice - 1);
        return choices[selectedIndex];
    }
    public static int presentOptionsIndex(String[] choices, int numOfChoices, Scanner myScanner, String playerName) {

        List<Integer> randChoices = generateUniqueList(numOfChoices, choices.length);
        System.out.println("Choose one of " + numOfChoices + " " + playerName + ": ");
        for (int i = 0; i < numOfChoices; i++)
        {
            System.out.printf(" %d. %s", i + 1, choices[randChoices.get(i)]);
            System.out.println();
        }
        int thePlayersChoice;
        while (true) {
        System.out.print("Your' choice(s) (1-" + numOfChoices + "): ");
        try {
        
            thePlayersChoice = myScanner.nextInt();
            if (thePlayersChoice >= 1 && thePlayersChoice <= numOfChoices) {
                break; // Exits the while loop. 
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            myScanner.nextLine(); // Read the next line, instead of the line where user misinputted.
            }
        }
        // Map user's choice (1-based) to the index in randChoices (0-based)
        return thePlayersChoice - 1;

    }
    public static <T> void swap(T[] refSwap, int i, int j) {
        /*
         * So my understanding is like so for this. I want to bubble sort and
         * naturally I'll need to swap elements in a list or array as a step under
         * the bubble sorting process.
         * So I'll need to be able to swap two elements in an array.
         * Also I've made this static void method generic which means I can sort anything
         * as long as it's an array. Since I may use it again later.
         * But yeah I have a temporary value that is the element, which is being swapped.
         * temp = refSwap[i] 'to kind of store the value or rather reference to refSwap[i]'
         * Then refSwap[i] reference/val is changed to refSwap[j]. *refSwap[i] = refSwap[j];*.
         * Immediately after the reference/val of refSwap[i] before it was overriden, overrides
         * the reference/val of refSwap[j].
         */
        T temp = refSwap[i];
        refSwap[i] = refSwap[j];
        refSwap[j] = temp;

    }
}
