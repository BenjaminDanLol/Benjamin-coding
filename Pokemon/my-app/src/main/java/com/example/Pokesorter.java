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

public class Pokesorter 
{
    public static void main( String[] args ) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Scanner myScanner = new Scanner(System.in);
        try {
        InputStream inputStream = Pokesorter.class.getClassLoader().getResourceAsStream("resources/PokeTiering.json");
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found!");
        }
        JsonNode root = mapper.readTree(inputStream);

        JsonNode typesArray = root.path("Typings");
        if (!typesArray.isArray() || typesArray.size() == 0) {
            System.out.println("\"Typings\" array is missing or empty.");
            return;
        }

        int size = typesArray.size();
        String[] types = new String[size];
        for (int i = 0; i < size; i++){
            types[i] = typesArray.get(i).asText();
        }

        int currentChoice = limitChoicesNoDupes(size, 4, myScanner, types, "DickHead");
        String selectedType = types[currentChoice-1];        

        JsonNode typeMappingNode = root.path("TypeMapping").path(selectedType);
        if (!typeMappingNode.isObject()){
            System.out.println("No corresponding mapping to type: " + selectedType);
            return;
        }
        /*
        This works perfectly for starts since you essentially want to start at the lowest value
        and incrementally work your way upwards.
        And maybe I could just say "it's apart of the game". But I at least see a world where
        the chosenFilter is incrementally higher, the longer you are into the game. And it just seems
        shitty to still be getting rattatas 100 rounds in. Or maybe not I mean you still have a chance
        for porygon-z or the other high value Normal types. But it is all inclusive.
        Maybe it's apart of the RNG, but for me at least it seems a bit to much.
        
        A way I could prevent this is by encompassing everything except the initialization
        of chosenFilter (that part is simply a param from outside where the round logic is based).

        Then I have two ways of doing this where 1 is computationally heavy scuffed but accurate.
        And the will work at determined time.

        1.
        I could add an if statement right after the while (fields.hasNext()) that checks for
        if size is 4. 
        Because if it is then I could have a different.forEach loop that checks if it is between
        a Value say (chosen filter -50) and the chosen filter, and if it is not then check if it
        is between (chosen filter -100) and the chosen filter.
        This way I check from the top, but honestly I could also have it at -100 since
        it's alright if there's RNG.

        Now I thought this would be an idiotic approach, since I thought that when starting from the bottom.
        That it would have an infinite loop. But in this case since I checked for it's sizing first, then I
        can filter out those cases for when I wish to start from the top.

        Now I could even just check for if the AtomicInteger is let's say 1000, then I could have a specific
        range of between 500 and 700, and then it could check for 300 and 700.
        Maybe to combat this I could have a loop that incrementally for each "check", decreases the decrease
        of the lower bound. E.g. 700/500, 700/400, 700/350, 700/325, 700/312 "truncation". 
        I could either have the initial decrease to be based off of the upper bound. And loop 
        a fixed amount of times. So if say upper bound was 1500 then maybe smthn like 1000 lower bound
        Then 1500/750, then 1500/625 then 1500/625-0.5*(750-125). 
        From there if that doesn't work produce 4 distinct strong pokemon. I'll simply take smthn like.
        800/500 -> 800/400 -> 800/300. As a fail safe.
         */
        final AtomicInteger chosenFilter = new AtomicInteger(400);

        Map<String, Integer> pokemonMap = new HashMap<>();
        Map<String, Integer> pokemonMapAllInclusive = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = typeMappingNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            pokemonMapAllInclusive.put(entry.getKey(), entry.getValue().asInt());
        }

        while (pokemonMap.size() < 4) {
        pokemonMap.clear();
        pokemonMapAllInclusive.forEach((key, value) -> {
            if (value < chosenFilter.get()){
                pokemonMap.put(key, value);
            } 
        });
        chosenFilter.addAndGet(50);
        }

        List<String> keyList = new ArrayList<>(pokemonMap.keySet());
        String[] keySetConverted = keyList.toArray(new String[0]);
        String randPokeChoice = keySetConverted[(limitChoicesNoDupes(keySetConverted.length, 4, 
        myScanner, keySetConverted, "DickHead") - 1)];
        System.out.println("You chose: " + randPokeChoice);

        InputStream pokemonStream = Pokesorter.class.getClassLoader().getResourceAsStream("resources/PokeTieringattempt.json");
        if (pokemonStream == null) {
            throw new IllegalArgumentException("Resource not found!");
        }
        Map<String, Poketest> pokeMap = mapper.readValue(pokemonStream, new TypeReference<Map<String, Poketest>>(){});
        Poketest currentPokemon = pokeMap.get(randPokeChoice);
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
                    currentPokemon.evolutionArray, "Dickhead") - 1];

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
        myScanner.close();

    } catch (IOException e) {
        e.printStackTrace();
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

