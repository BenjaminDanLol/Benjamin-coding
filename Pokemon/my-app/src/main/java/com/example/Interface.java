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

    public Interface() {
        try {
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
            e.printStackTrace(); 
            }

    }
    
    public void startBattleTwoPlayers(Player p1, Player p2, Scanner myScanner) {

        int p1choice = chooseAPokemon(myScanner, p1);
        int p2choice = chooseAPokemon(myScanner, p2);
        System.out.println(p1.getPlayerName() + " chooses " + p1.getPokemonFromPlayer(p1choice + 1).PokeName);
        System.out.println(p2.getPlayerName() + " chooses " + p2.getPokemonFromPlayer(p2choice + 1).PokeName);

        p2.getPokemonFromPlayer(p2choice + 1).getASpecificPokemonsMove(1).
        performMove(p2.getPokemonFromPlayer(p2choice + 1), p1.getPokemonFromPlayer(p1choice + 1));
    }

    public int chooseAPokemon(Scanner myScanner, Player player) {
       // What String array should be shown?
       // This solution will be disgusting and dirty but it's gonna work
       String[] nameOfAllPlayersPokemon = new String[player.howManyPokemonDoesPlayerActuallyHave()];
        for (int i = 0; i < player.getPlayersPokemon().length; i++) {
            if (!player.getPokemonFromPlayer(i + 1).PokeName.equals("Pokemon shouldn't exist"))
            {
                nameOfAllPlayersPokemon[i] = player.getPokemonFromPlayer(i + 1).PokeName;
            }
        }
       return choose1Deterministic(myScanner, player.getPlayerName(), player.howManyPokemonDoesPlayerActuallyHave(), nameOfAllPlayersPokemon);
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
        keySetConverted = keyList.toArray(new String[0]);
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
    public static int choose1Deterministic(Scanner myScanner, String pName, 
            int numOfChoices, String[] choices){
                int thePlayersChoice;
                System.out.println("Choose one of the following " + pName +  ": ");
                for (int elementsOfChoices = 0; elementsOfChoices < numOfChoices; elementsOfChoices++)
                {
                    System.out.printf(" %d. %s", elementsOfChoices + 1, choices[elementsOfChoices]);
                }
                System.out.printf(": ");
                do {
                    thePlayersChoice = myScanner.nextInt();
                } while (thePlayersChoice < 1 || thePlayersChoice > numOfChoices);
                return thePlayersChoice-1;
            }
}
