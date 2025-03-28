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
    public static Move swapMove;
    public static Move struggle;
    public static Move fakeMove;
    public static Pokemon fakeMon;

    ArrayList<Team> teamsInGame = new ArrayList<>();


    public Interface(Scanner myScanner) {
            try {
                int amountOfTeams;
                String confirmDecline;
                while (true) {
                    System.out.println("How many teams are playing? min. 2");
                try {            
                    amountOfTeams = myScanner.nextInt();
                if (amountOfTeams >= 2) {
                    System.out.println("Game session will have " + amountOfTeams + " teams.");
                    System.out.println("Type X to confirm: ");
                    confirmDecline = myScanner.next().trim().toLowerCase();
                    if (confirmDecline.equals("x")) {
                        break;
                    }
                }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        myScanner.nextLine(); 
                }   
            }

            for (int i = 0; i < amountOfTeams; i++) {
                teamsInGame.add(new Team(myScanner, (i+1)));
            }

            for (Team t : teamsInGame) {
                t.addPlayersInternal(myScanner);
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

            /* 
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

            fakeMon = new Pokemon();
            fakeMove = new Move();

            swapMove = new Move();
            struggle = new Move();
            struggle.power = 10;
            struggle.moveName = "Struggle";
            struggle.moveDescription = "You poor soul";
            swapMove.priority = 10;

        } catch (IOException e) {
            // IDE doesn't like this throw stack trace, maybe it's cuz it's impossible or smthn.
            e.printStackTrace();
            throw new RuntimeException("failed to initialize object instance of class Interface", e);
            }
    } 
    
    public void startBattle2Teams(Scanner myScanner, int[] teamIndexes) {

        for (int i = 0; i < 2; i++) {
            teamsInGame.get(teamIndexes[i]).fieldPlayersIntoBattle();
            teamsInGame.get(teamIndexes[i]).giveAllPlayersPokemon(myScanner, this);
            teamsInGame.get(teamIndexes[i]).teamChoosesPokemon(myScanner);
        }

        ArrayList<Pokemon> executionOrder = new ArrayList<>();
        letBattleCommence(myScanner, teamsInGame.get(teamIndexes[0]), teamsInGame.get(teamIndexes[1]));

        // Some cool view of the pokemon that are playing vs each other
        for (int i = 0; i < 2; i++) {
            for (Player p : teamsInGame.get(teamIndexes[i]).playersCurrentlyInBattle) {

                if (i == 0) {
                p.playerHasToSwap = false; // Abilities like trapped will not work with this approach
                p.playerController(myScanner, teamsInGame.get(teamIndexes[i + 1]));
                executionOrder.add(p.pokemonInPlay);
                } 
                
                else if (i == 1) {
                p.playerHasToSwap = false;
                p.playerController(myScanner, teamsInGame.get(teamIndexes[i - 1]));
                executionOrder.add(p.pokemonInPlay);
                }
            }
        }
        
        Pokemon[] execOrdPokemons = new Pokemon[executionOrder.size()];
        int i = 0;
        for (Pokemon p : executionOrder) {
            execOrdPokemons[i] = p;
            i++;
        }

        // Bubble sort the prio/speed of all pokemon, essentially order the pokemon by execution of moves.
        boolean swapped;
        do {
            swapped = false;
                for (int j = 0, n = executionOrder.size(); j < n - 1; j++) {
                    /*
                     * It goes like so. If the next pokemon in array has a higher prio then the current
                     * swap, or if their prio is tied, then check for if the next pokemon is faster than
                     * the current if so swap and declare a swap has been done. In other words make it
                     * so that the this for loop is checked for again.
                     * Else nothing will happen.
                     * For cases where the pokemon is being swapped then their moveInUsage prio is 10.
                     * Later I can have moves like pursuit or other custom moves that interfere with pokeswapping.
                     */
                    if  (execOrdPokemons[j].getMoveInUsage().priority < execOrdPokemons[j + 1].getMoveInUsage().priority ||
                        (execOrdPokemons[j].getMoveInUsage().priority == execOrdPokemons[j + 1].getMoveInUsage().priority &&
                        (execOrdPokemons[j].getSpdMod() * execOrdPokemons[j].baseSpd) < 
                        (execOrdPokemons[j + 1].getSpdMod() * execOrdPokemons[j + 1].baseSpd))) {
                        swap(execOrdPokemons, j, (j + 1));
                        swapped = true;
                    }
            }
        } while (swapped);
        Pokemon targetForPokemon = new Pokemon();
        for (Pokemon pokemon : execOrdPokemons) {

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
            if (pokemon.trainer.getMyTeam().equals(teamsInGame.get(teamIndexes[0]))) {

                targetForPokemon = teamsInGame.get(teamIndexes[1]).getTarget(pokemon.trainer.enemyPlayerTargetInitialIndex);
                if (targetForPokemon == null) {
                    return;
                }

                pokemon.getMoveInUsage().performMove(pokemon, targetForPokemon, myScanner);
            } else if (pokemon.trainer.getMyTeam().equals(teamsInGame.get(teamIndexes[1]))) {

                targetForPokemon = teamsInGame.get(teamIndexes[0]).getTarget(pokemon.trainer.enemyPlayerTargetInitialIndex);
                if (targetForPokemon == null) {
                    return;
                }

                pokemon.getMoveInUsage().performMove(pokemon, targetForPokemon, myScanner);
            }

        }
        
    }
    public void letBattleCommence(Scanner myScanner, Team pTeam1, Team pTeam2) {

        for (int i = 0; i < 11; i++) {
        switch (i) {
            case 1 -> System.out.printf("\n\t\t\t%s:", pTeam1.teamName);
            case 2 -> {
                System.out.println("\tPlayer(s)");
                for (int p = 0; p < pTeam1.playersCurrentlyInBattle.size(); p++) {
                    System.out.println((p + 1) + ")\t " + pTeam1.playersCurrentlyInBattle.get(p).getPlayerName());
                    }
                }
            case 5 -> System.out.println("\t\t\tVERSUS!");
            case 8 -> System.out.printf("\n\t\t\t%s:", pTeam2.teamName);
            case 9 -> {
                for (int p = 0; p < pTeam2.playersCurrentlyInBattle.size(); p++) {
                    System.out.println((p + 1) + ")\t " + pTeam2.playersCurrentlyInBattle.get(p).getPlayerName());
                    }
                }
            default -> System.out.println();
            }
        }
        System.out.println("Type... when both teams are ready!");
        myScanner.nextLine();
        System.out.printf("\n\t%s lineup:", pTeam1.teamName);
        for (int i = 0; i < pTeam1.playersCurrentlyInBattle.size(); i++) {
            // I can just use the target arraylist.
            System.out.printf("\n%s: %s", pTeam1.playersCurrentlyInBattle.get(i).getPlayerName(), 
            pTeam1.playersCurrentlyInBattle.get(i).pokemonInPlay.PokeName);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print("-- ");
        }
        System.out.println();
        System.out.printf("\n\t%s lineup:", pTeam2.teamName);
        for (int i = 0; i < pTeam2.playersCurrentlyInBattle.size(); i++) {
            System.out.printf("\n%s: %s", pTeam2.playersCurrentlyInBattle.get(i).getPlayerName(), 
            pTeam2.playersCurrentlyInBattle.get(i).pokemonInPlay.PokeName);
        }

        System.out.println(".. to continue");
        myScanner.nextLine();
        if (myScanner.hasNext()) {myScanner.nextLine();}

        /* TODO When the battle sequence commences, moves that force the player to switch out, should immediatly.
        after being performed call upon playerController. And then that will make the player go through the steps
        to switch out a pokemon.
        Second edge case which can be encountered often moveInUsage needs to be set to nothing. Or a blank move
        or something that can be checked for right before performMove is done. Since I don't want it to when
        a player switches in immediately using a move.

        When displayingTeam under class Team, it will show what the player will switch into if they chronologically
        have their move after player. This is not desired, therefor it needs to be split up differently 1.
        if playerIsSwapping then display pokemonUsedBefore with all of it's logic. else it can work exactly
        as is. Will be a bit weird being updated real time what your team mates switched into, but honestly that's
        better interface wise in my opinion.

        Also before performin move check again for if the move.IsDisabled, if this
        is the case then skip that players action.

        Something cool I could do is asking if there's a player that regrets their action this turn (internally)
        in team if so. Then I can just call upon their playerController again (BUT!). In doing so I need to check
        again, are they currently swapping if so then. Swap pokemonUsedBefore with pokemonInPlay, then let them
        do their turn. I believe that will do the trick.

        Each turn after performMoves has been done for both team I need to.
        1. Ensure that hasToSwap is set to false.
        2. Ensure moveInUsage is set to a blank move.
        3. A turn counter. (Very necessary for status conditions which will be added).
        4. (Optional) check if pokemonPlayerCanActuallyUse is the same before and after refresh
        since I would prefer just removing the refreshes, and instead just add/remove from the
        arrayLists of the various objects. So I'll try and see if they are the same.
        */ 
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
