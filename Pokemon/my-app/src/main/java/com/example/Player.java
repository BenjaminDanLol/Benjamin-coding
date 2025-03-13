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

public class Player {
    private String playerName;
    ArrayList<Pokemon> playersPokemon = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    // Maybe make a hashMap, where the entry is based off the Pokemon. That way the pokemon can "own" the moves.
    public Player(int valueSortingRange, Scanner pScanner, InputStream pokeTiering, InputStream allPokemon) {

    }
    public Player(Scanner playerScanner){
        System.out.println("What will your name be player? ");
        playerName = playerScanner.nextLine();
        System.out.println("Nice to meet you: " + playerName + " my name is Dr. Dorfenschimdt.");
        System.out.println("This is my fanmade Pokemon game. I've tried my best to reflect the" +
        "actual pokemon game's mechanics as much as possible. Whilst providing an entertaining" +
        " roguelike spinoff you can play with friends. ");
    }
    public void addPokemon(int spotForPokemonInArray, Scanner myScanner) {
        if (playersPokemon.size() == 7) {
            // Looks weird but it's because the scanner minuses by one by default as to not confuse player
            // with arrays and such.
            System.out.println("Sorry you cannot add more Pokemon to team");
            return;
        }
        playersPokemon.add(makeANewPokemon(myScanner));
    }
    public void displayASpecificPokemon(int spotForPokemonInArray, Scanner myScanner) {
        // So for the first pokemon just type 1, it will be adjusted
        playersPokemon.get(spotForPokemonInArray - 1);
    }
    public void displayPokemonTeam() {
        for (int i = 0; i < playersPokemon.size(); i++) {
            playersPokemon.get(i).displayPokeInfo();
        }
    }

    public Pokemon makeANewPokemon(Scanner myScanner) {
    try {
    InputStream inputStream = Pokesorter.class.getClassLoader().getResourceAsStream("resources/PokeTiering.json");
    if (inputStream == null) {
        throw new IllegalArgumentException("Resource not found!");
    }
    JsonNode root = mapper.readTree(inputStream);

    JsonNode typesArray = root.path("Typings");
    if (!typesArray.isArray() || typesArray.size() == 0) {
        System.out.println("\"Typings\" array is missing or empty.");
        return null;
    }

    int size = typesArray.size();
    String[] types = new String[size];
    for (int i = 0; i < size; i++){
        types[i] = typesArray.get(i).asText();
    }

    int currentChoice = limitChoicesNoDupes(size, 4, myScanner, types, playerName);
    String selectedType = types[currentChoice-1];        

    JsonNode typeMappingNode = root.path("TypeMapping").path(selectedType);
    if (!typeMappingNode.isObject()){
        System.out.println("No corresponding mapping to type: " + selectedType);
            return null;
    }
    int filter = 1050;
    int pokemonPool = 4;
    final AtomicInteger chosenFilter = new AtomicInteger(400);

    Map<String, Integer> pokemonMap = new HashMap<>();
    Map<String, Integer> pokemonMapAllInclusive = new HashMap<>();
    Iterator<Map.Entry<String, JsonNode>> fields = typeMappingNode.fields();
    while (fields.hasNext()) {
        Map.Entry<String, JsonNode> entry = fields.next();
        pokemonMapAllInclusive.put(entry.getKey(), entry.getValue().asInt());
    }
    // IMPORTANT AS OF NOW THE POKEMMON POOL MUST NOT BE HIGHER THAN 4 CUZ GHOST/DRAGON ONLY HAS 4 POKE IN TOTAL
    if (filter > 1000) {
        if (filter >= 2000) {
            pokemonPool = 4;
        } else {
        // Do something more unique here it's to lazy
            pokemonPool = 4;
        }
    }
    else {
    while (pokemonMap.size() < 4) {
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

    List<String> keyList = new ArrayList<>(pokemonMap.keySet());
    String[] keySetConverted = keyList.toArray(new String[0]);
    String randPokeChoice = keySetConverted[(limitChoicesNoDupes(keySetConverted.length, 4, 
    myScanner, keySetConverted, playerName) - 1)];
    System.out.println("You chose: " + randPokeChoice);

    InputStream pokemonStream = Pokesorter.class.getClassLoader().getResourceAsStream("resources/PokeTieringattempt.json");
    if (pokemonStream == null) {
        throw new IllegalArgumentException("Resource not found!");
    }
    Map<String, Pokemon> pokeMap = mapper.readValue(pokemonStream, new TypeReference<Map<String, Pokemon>>(){});
    Pokemon currentPokemon = pokeMap.get(randPokeChoice);
    currentPokemon.displayPokeInfo();
    for (byte level = 0; currentPokemon.evolvesTo != null; level++){
        if (currentPokemon.evolvesAt != level) {
            if (level % 5 == 0) {
           System.out.println(currentPokemon.PokeName + " is level: " + level + 
           " and will evolve at, " + currentPokemon.evolvesAt);
            }
        } else if (currentPokemon.evolutionArray != null && currentPokemon.evolvesAt == level) {
                System.out.println(currentPokemon.PokeName + "is at level: " + level);
                System.out.println("Time to evolve!");

                currentPokemon.evolvesTo = currentPokemon.evolutionArray
                [limitChoicesNoDupes(currentPokemon.evolutionArray.length, 4, myScanner, 
                currentPokemon.evolutionArray, playerName) - 1];

                randPokeChoice = currentPokemon.evolvesTo;
                System.out.println(currentPokemon.PokeName + " is level: " + level + 
                " and evolves to, " + currentPokemon.evolvesTo);

                currentPokemon = pokeMap.get(randPokeChoice);
                currentPokemon.displayPokeInfo();
        } else {
        randPokeChoice = currentPokemon.evolvesTo;
        System.out.println(currentPokemon.PokeName + " is level: " + level + 
        " and evolves to, " + currentPokemon.evolvesTo);
        currentPokemon = pokeMap.get(randPokeChoice);
        currentPokemon.displayPokeInfo();
        }
    }

    System.out.println(currentPokemon.getLevel());
    return currentPokemon;
} catch (IOException e) {
    e.printStackTrace(); 
    return null;   
    }
    }

public static int limitChoicesNoDupes(int range, int numOfChoices, Scanner myScanner, 
String[] choices, String pName) {
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

        