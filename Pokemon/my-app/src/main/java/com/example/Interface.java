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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Interface {
    InputStream pokemonTieringStream;
    // InputStream pokemonStream;
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

    Map<String, Pokemon> allPokemonMapped;

    Pokemon currentPokemon;

    InputStream moveStream;
    Map<String, Move> moveMap;
    Move currentMove;

    // TODO Make a team class, so I can just use a getPokemonNames from it. Ofc there's more
    Player[] pTeam1;
    ArrayList<String> pokemonTargetsForT1 = new ArrayList<>();
    Player[] pTeam2;
    ArrayList<String> pokemonTargetsForT2 = new ArrayList<>();


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
                    myScanner.nextLine();
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
            System.out.println();
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

            /* pokemonStream = Interface.class.getClassLoader().getResourceAsStream("resources/PokeTieringattempt.json");
            if (pokemonStream == null) {
                // I may want to remove this shit, and just print it out and trust the catcher to catch.
                throw new IllegalArgumentException("Resource not found!");
            }
            I would rather define it inside the method findPokemon since it will close the Stream after every
            single pokemon is loaded into the hashMap pokeMap. 
            */
            // pokeMap = mapper.readValue(pokemonStream, new TypeReference<Map<String, Pokemon>>(){});
            loadPokemonData();

            moveStream = Move.class.getClassLoader().getResourceAsStream("resources/moves.json");
            if (moveStream == null) {
                System.out.println("moves.json file not found");
            }
            moveMap = mapper.readValue(moveStream, new TypeReference<Map<String, Move>>(){});


        } catch (IOException e) {
            // IDE doesn't like this throw stack trace, maybe it's cuz it's impossible or smthn.
            e.printStackTrace();
            throw new RuntimeException("failed to initialize object instance of class Interface", e);
            }
    } 
    public void startBattleT1vT2(Scanner myScanner) {
        // teams will later be an object array where index 1 and 2, are inserted.
        Pokemon[] executionOrder = new Pokemon[pTeam1.length + pTeam2.length];

        // Assuming this is the start the I'll need to set pTeam1 all to playerHasToSwap
        for (int i = 0; i < pTeam1.length; i++) {
            // The current iteration of team1 player chooses their pokemon
            pTeam1[i].playerController(myScanner);
            executionOrder[i] = pTeam1[i].pokemonInPlay;
            // Used for targeting options for the opposing team later.
        }

        // Clear the scanner here so the opposing team can't see what the first team chose.

        for (int i = 0; i < pTeam2.length; i++) {
            pTeam2[i].playerController(myScanner);
            executionOrder[i] = pTeam2[i].pokemonInPlay;
        }

        // TODO Targetting, I should honestly just take the array itself, where it's .pokemoninusage.target instead.
        for (int i = 0; i < pTeam1.length; i++){
            if (pTeam1[i].pokemonInPlay.getMoveInUsage().toVictim != false) {
                System.out.println("Who should " + pTeam1[i].pokemonInPlay.PokeName + "target?");
                
                /*
                pTeam1[i].pokemonInPlay.target = pTeam2[presentOptionsIndexList(pokemonTargetsForT1, 
                myScanner, pTeam1[i].getPlayerName())];
                
                team1Choices[i].target = pTeam2[presentOptionsIndex(pokeNamesT2Chose, 
                pokeNamesT2Chose.length, myScanner, pTeam1[i].getPlayerName())].getTarget(team1Choices[i]);
                */
            }
        } 
        
        // Clear Terminal and let team2 target as well.

        for (int i = 0; i < pTeam2.length; i++){
            if (pTeam2[i].pokemonInPlay.getMoveInUsage().toVictim != false) {
                System.out.println("Who should " + pTeam2[i].pokemonInPlay.PokeName + "target?");

                /*
                pTeam2[i].pokemonInPlay.target = pTeam1[presentOptionsIndexList(pokemonTargetsForT2, 
                myScanner, pTeam2[i].getPlayerName())];
                
                team2Choices[i].target = pTeam1[presentOptionsIndex(pokeNamesT1Chose, 
                pokeNamesT1Chose.length, myScanner, pTeam2[i].getPlayerName())].getTarget(team2Choices[i]);
                */
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
            /*if (pokemon.isTeam1) {
                 TODO
                if (pokemon.target.allPokemonAreFainted == true) {
                }
                 
            pokemon.getMoveInUsage().performMove(pokemon, pokemon.target.pokemonInPlay);
            }
            else {
            pokemon.getMoveInUsage().performMove(pokemon, pokemon.target.pokemonInPlay);
            }
            */
        }
        
    }
    public void initiateBattle(Scanner myScanner) {
    
        System.out.println("TEAM 2 DON'T LOOK");
        System.out.println("Team 1 choose your pokemon");
        
            for (int i = 0; i < pTeam1.length; i++) {
                pTeam1[i].playerHasToSwap = true;
                pTeam1[i].playerController(myScanner);
                pokemonTargetsForT1.add(pTeam1[i].pokemonInPlay.PokeName);
                pTeam1[i].playerHasToSwap = false;

            }
        for (int i = 0; i < pTeam2.length; i++) {
                pTeam2[i].playerHasToSwap = true;
                pTeam2[i].playerController(myScanner);
                pokemonTargetsForT2.add(pTeam2[i].pokemonInPlay.PokeName);
                pTeam2[i].playerHasToSwap = false;
            }
        
        for (int i = 0; i < 11; i++) {
        switch (i) {
            case 1 -> System.out.println("\t\t\tTeam1:");
            case 2 -> {
                System.out.println("\tPlayer(s)");
                for (int p = 0; p < pTeam1.length; p++) {
                    System.out.println((p + 1) + ")\t " + pTeam1[p].getPlayerName());
                    }
                }
            case 5 -> System.out.println("\t\t\tVERSUS!");
            case 8 -> System.out.println("\t\t\tTeam2:");
            case 9 -> {
                for (int p = 0; p < pTeam2.length; p++) {
                    System.out.println((p + 1) + ")\t " + pTeam2[p].getPlayerName());
                    }
                }
            default -> System.out.println();
            }
        }
        System.out.println("Type... when both teams are ready!");
        myScanner.nextLine();
        System.out.println("\tTeam1 lineup:");
        for (int i = 0; i < pTeam1.length; i++) {
            // I can just use the target arraylist.
            System.out.println(pTeam1[i].pokemonInPlay.PokeName);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print("-- ");
        }
        System.out.println();
        System.out.println("\tTeam2 lineup:");
        for (int i = 0; i < pTeam2.length; i++) {
            System.out.println(pTeam2[i].pokemonInPlay.PokeName);
        }
        myScanner.nextLine();
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
    private void loadPokemonData() throws IOException {
        try (InputStream pokemonStream = Interface.class.getClassLoader().getResourceAsStream("resources/PokeTieringattempt.json");
             JsonParser parser = new JsonFactory().createParser(pokemonStream)) {
                    
            if (parser.nextToken() != JsonToken.START_OBJECT) {
                throw new IOException("Invalid JSON format");
            }
            allPokemonMapped = new HashMap<>();
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String pokemonName = parser.currentName();
                parser.nextToken();
                Pokemon pokemon = mapper.readValue(parser, Pokemon.class);
                allPokemonMapped.put(pokemonName, pokemon); // Add to map in chunks
            }
        }    
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
        currentPokemon = allPokemonMapped.get(randPokeChoice);
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
    // Reason 1: I imagine it creates huge amounts of overhead when I constantly make sets and lists without reason.
    public static <T> int presentOptionsIndex(T[] choices, int numOfChoices, Scanner myScanner, String playerName) {
        if (choices.length == 0 || numOfChoices == 0) {
            // prevention of misuse. I.e. I'm calling on a pokemon to choose a move with 0 moves or
            // a player with 0 pokemon, or even a team with no players.
            System.out.println(playerName + " has no available choices.");
            return 0;
        }
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

    public static int presentOptionsIndexList(ArrayList<String> choices, Scanner myScanner, String playerName) {
        if (choices.isEmpty()) {
            // prevention of misuse. I.e. I'm calling on a pokemon to choose a move with 0 moves or
            // a player with 0 pokemon, or even a team with no players.
            System.out.println(playerName + " has no available choices.");
            return 0;
        }
        System.out.println("Choose one of " + choices.size() + " " + playerName + ": ");
        for (int i = 0; i < choices.size(); i++)
        {
            System.out.printf(" %d. %s", i + 1, choices.get(i));
            System.out.println();
        }
        int thePlayersChoice;
        while (true) {
        System.out.print("Your' choice(s) (1-" + choices.size() + "): ");
        try {
        
            thePlayersChoice = myScanner.nextInt();
            if (thePlayersChoice >= 1 && thePlayersChoice <= choices.size()) {
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
