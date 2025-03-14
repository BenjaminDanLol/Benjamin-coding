package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
    String playerName = "playerName";

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
            // May delete later
            for (String pokeTyping : allPokemonTypes) {
            System.out.printf(pokeTyping + ", ");
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
    public Pokemon getAPokemonStandardized(int filter, Scanner myScanner, int howManyMonsInPokemonPool){
        int currentChoice = limitChoicesNoDupes(allPokemonTypes.length, 4, myScanner, allPokemonTypes, playerName);
        String selectedType = allPokemonTypes[currentChoice-1];      
        typeMappingNode = pokemonRoot.path("TypeMapping").path(selectedType);
        if (!typeMappingNode.isObject()){
        System.out.println("No corresponding mapping to type: " + selectedType);
        }
        // I'll sadly have to recreate a new AtomicInteger each time the method is called.
        AtomicInteger chosenFilter = new AtomicInteger(filter);
        fields = typeMappingNode.fields();
        int pokemonPool = howManyMonsInPokemonPool;

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
        randPokeChoice = keySetConverted[(limitChoicesNoDupes(keySetConverted.length, 4, 
        myScanner, keySetConverted, playerName) - 1)];
        System.out.println("You chose: " + randPokeChoice);
        
        currentPokemon = pokeMap.get(randPokeChoice);
        currentPokemon.displayPokeInfo();
        return currentPokemon;
    }
    // Simply inserting String should work here.
    public Move getASpecificMove(Scanner myScanner, String theMove) {
        currentMove = moveMap.get(theMove);
        return currentMove;
        } 

    // The method choose1 has weird error messages for it's scanner
            public static int limitChoicesNoDupes(int range, int numOfChoices, 
            Scanner myScanner, String[] choices, String pName) {
            if (numOfChoices > range) {
                System.out.println("numOfChoices is greater than range");
                int[] simpleNumOfChoices = new int[range];
                for (int i = 0; i < simpleNumOfChoices.length; i++) {
                    simpleNumOfChoices[i] = i + 1;
                }
                return choose1(myScanner, pName, simpleNumOfChoices.length, choices, simpleNumOfChoices);
            }
            boolean hasDupes = true;
            Random random = new Random();
            int[] choiceIndex = new int[numOfChoices];

                for (int currentElement = 0; currentElement < numOfChoices; currentElement++) {
                choiceIndex[currentElement] = random.nextInt(range)+1;
                }
            outer:
                while (hasDupes) {
            hasDupes = false;
            for (int n = 0, r = choiceIndex.length; n < r; n++) {
                for (int i = 0; i < r; i++) {
                    if (i != n && choiceIndex[i] == choiceIndex[n]) {
                            System.out.println("Duplicate spotted! Arrayposition: " + i + 
                            " is currently == Arrayposition: " + n);
                            choiceIndex[i] = random.nextInt(range)+1;
                            for (int priorElements = 0; priorElements < i; priorElements++) {
                                if (choiceIndex[priorElements] == choiceIndex[i]) {
                                    hasDupes = true;
                                    continue outer;
                                    }
                                }   
                        }
                    }
                }
            }
            return choose1(myScanner, pName, numOfChoices, choices, choiceIndex);
        }
            public static int choose1(Scanner myScanner, String pName, 
            int numOfChoices, String[] choices, int[] choiceIndex){
                int thePlayersChoice;
                System.out.println("Choose one of the following " + pName +  ": ");
                for (int elementsOfChoices = 0; elementsOfChoices < numOfChoices; elementsOfChoices++)
                {
                    System.out.printf(" %d. %s", elementsOfChoices + 1, choices[choiceIndex[elementsOfChoices]-1]);
                }
                System.out.printf(": ");
                do {
                    thePlayersChoice = myScanner.nextInt();
                } while (thePlayersChoice < 1 || thePlayersChoice > numOfChoices);
                return choiceIndex[thePlayersChoice-1];
            }
}
