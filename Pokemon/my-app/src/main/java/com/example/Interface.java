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
        int[] team1Choices = new int[pTeam1.length];
        int[] team2Choices = new int[pTeam2.length];
        String[] pokeNamesT1Chose = new String[pTeam1.length];
        String[] pokeNamesT2Chose = new String[pTeam2.length];
        // When this will be looped until all pokemon are dead from a team, then I'll to add some checks
        // For if they are fainted. I imagine I'll need an arraylist instead sadly.
        for (int i = 0; i < team1Choices.length; i++) {
            System.out.println("Choose one of your pokemon " + pTeam1[i].getPlayerName() + "!");

            // The current iteration of team1 player chooses their pokemon
            team1Choices[i] = presentOptionsIndex(pTeam1[i].allOfPokemonNames(), 
            pTeam1[i].allOfPokemonNames().length, myScanner, pTeam1[i].getPlayerName());
            
            System.out.println(pTeam1[i].getPlayerName() + " chose " + pTeam1[i].getPokemonFromPlayer(team1Choices[i]).PokeName + "!");
            // Used for targeting options for opposing team later.
            pokeNamesT1Chose[i] = pTeam1[i].getPokemonFromPlayer(team1Choices[i]).PokeName;
        }
        // Clear the scanner here so the opposing team can't see what the first team chose.
        for (int i = 0; i < team2Choices.length; i++) {
            System.out.println("Choose one of your pokemon " + pTeam2[i].getPlayerName() + "!");

            // The current iteration of team2 player chooses their pokemon.
            // Sometimes this has an error ignore it's VS code bugging.
            team2Choices[i] = presentOptionsIndex(pTeam2[i].allOfPokemonNames(), 
            pTeam2[i].allOfPokemonNames().length, myScanner, pTeam2[i].getPlayerName());
            System.out.println(pTeam2[i].getPlayerName() + " chose " + pTeam2[i].getPokemonFromPlayer(team2Choices[i]).PokeName+"!");
            pokeNamesT2Chose[i] = pTeam2[i].getPokemonFromPlayer(team2Choices[i]).PokeName;
        }

        // Just clear the scanner for symmetry.

        int[] team1MoveChoices = new int[pTeam1.length];
        int[] team2MoveChoices = new int[pTeam1.length];
        for (int i = 0; i < team1Choices.length; i++) {
            // The current iteration of team1 player chooses the move they wish the pokemon they chose prior to use.
            team1MoveChoices[i] = presentOptionsIndex(pTeam1[i].getPokemonFromPlayer(team1Choices[i]).pokemonMoves(),
            pTeam1[i].getPokemonFromPlayer(team1Choices[i]).pokemonMoves().length, myScanner, pTeam1[i].getPlayerName());
        }

        // Clear the terminal again

        for (int i = 0; i < team2Choices.length; i++) {
            team2MoveChoices[i] = presentOptionsIndex(pTeam2[i].getPokemonFromPlayer(team2Choices[i]).pokemonMoves(),
            pTeam2[i].getPokemonFromPlayer(team2Choices[i]).pokemonMoves().length, myScanner, pTeam2[i].getPlayerName());
        }

        // Clear the terminal.

        // these arrays are to map what pokemon each teams pokemon targets.
        int[] playerFromT1Targets = new int[team2Choices.length];
        int[] playerFromT2Targets = new int[team1Choices.length];
        for (int i = 0; i < team1Choices.length; i++) {
            // If they didn't choose a move that targets opposing pokemon then skip this process.
            // And just set it to some nonsensical value like 0.
            if (pTeam1[i].playersPokemon[team1Choices[i]].getAPokemonsMove(team1Choices[i]).toVictim != false) {
                System.out.println("Who should " + pTeam1[i].getPokemonFromPlayer(team1Choices[i]).PokeName + "target?");
                // this will present a list against who the player can target.
                playerFromT1Targets[i] = presentOptionsIndex(pokeNamesT2Chose, pokeNamesT2Chose.length, myScanner, pTeam1[i].getPlayerName());
            } else {
                // I need this step since, I'll use performMove which takes two poke params.
                playerFromT1Targets[i] = 0;
            }
        }

            // Clear the terminal.

        for (int i = 0; i < team2Choices.length; i++) {
            if (pTeam2[i].playersPokemon[team2Choices[i]].getAPokemonsMove(team2Choices[i]).toVictim != false) {
                System.out.println("Who should " + pTeam2[i].getPokemonFromPlayer(team2Choices[i]).PokeName + "target?");
                playerFromT2Targets[i] = presentOptionsIndex(pokeNamesT1Chose, pokeNamesT1Chose.length, myScanner, pTeam2[i].getPlayerName());
            } else {
                playerFromT2Targets[i] = 0;
            }
        }

        // TODO: SORT THE EXECUTION OF EVERYTHING IN A CHRONOLOGICAL WINDOW FROM FASTEST TO SLOWEST.
        // Check the prios, speed, differences and what have you and have an ordered execution of player combat.
        // At this step I'll have all the pokemon in play, also what move each of them use and against whom.


            Set<Byte> listOfPriosForTheTurn = new HashSet<>();
            // Would have been nice to be able to do this 
            // for (Move team1Move : pTeam1[i].getPokemonFromPlayer(team1Choices[i]).getAPokemonsMove(team1MoveChoices[i]))
            // I should definitely use maps here.
            for (int i = 0, n = team1MoveChoices.length; i < n; i++) {    
            listOfPriosForTheTurn.add(pTeam1[i].getPokemonFromPlayer(team1Choices[i]).getAPokemonsMove(team1MoveChoices[i]).priority);
            }
            for (int i = 0, n = team2MoveChoices.length; i < n; i++) {
            listOfPriosForTheTurn.add(pTeam2[i].getPokemonFromPlayer(team2Choices[i]).getAPokemonsMove(team2MoveChoices[i]).priority);
            }
            List<Byte> orderedListOfPrios = new ArrayList<>(listOfPriosForTheTurn);
            
            List<Byte> howManyMovesHaveThisPrio = new ArrayList<>();
            for (int i = 0, oLP = orderedListOfPrios.size(); i < oLP; i++) {
                byte localsizingOfThisPrio = 0;
                    /* 
                     * The general idea is that the while loop will
                     * "catch" what priorities there are dupes of. This is so we know what
                     * should shouldn't be compared i.e. if there's a pokemon who used a priority
                     * it doesn't share with any other the turn, then we can simply put that the
                     * players action all the way in from of what else isn't listed yet.
                    */
                if (!orderedListOfPrios.get(i + 1).equals(orderedListOfPrios.get(i))) {
                    // For stand alone priorities.
                    byte replaceableLikeMe = 1;
                    howManyMovesHaveThisPrio.add(replaceableLikeMe);
                } else if (orderedListOfPrios.get(i + 1).equals(orderedListOfPrios.get(i))) {
                    while (orderedListOfPrios.get(i + 1).equals(orderedListOfPrios.get(i))) {
                        localsizingOfThisPrio++;
                        // The i++ is to ensure that the loop doesn't reiterate over the same stuff 
                        // as done in the while loop.
                        i++;
                } 
                howManyMovesHaveThisPrio.add(localsizingOfThisPrio);
            }
               
            }
            boolean hasThereBeenASort = false;
            for (int n = 0; n < howManyMovesHaveThisPrio.size(); n++) {
                while (true) {
                for (byte i = 0; i < howManyMovesHaveThisPrio.get(n); i++) {
                    /*
                     * Pseudo code, since I haven't marked what pokemon is what
                     * since I've seperated prio completely from the pokemon.
                     * Tmrw I should revamp some of the stuff to a hashMap,
                     * or have an associated id. Just something that make it so
                     * I can from in here actually access the pokemons speed.
                     * If I know the pokemon I could prob just have a loop that
                     * loops as many pokemon there are in play, that where the
                     * associated move is performed, just take the move list
                     * and then take targets list as the enemy the pokemon performs
                     * move on.
                     * 
                     * So I just realized most of this was unneccasary but whatevs.
                     * I'm going to attempt to make this as a bubble sort.
                     * 
                     * So what I'll do inside of here is swap element i with it's next element.
                     * If and only if it's speedstat is less than the next element.
                     * This will be repeated seeming infinitely. The only time this should stop is
                     * when all of the speed stats have been sorted under this "umbrella" of priority.
                    My idea of checking this and also keeping the sorting infinite only to this point.
                    Is having all of this in a while loop. where the while loop will be broken if there
                    has been no instances of sorting. That's where the if statement below comes in.
                    It's assumed that there have been no sortings, but if there have, then inside the
                    code that executes that assumptions I'll set hasTherBeenASort to true.

                    Something worth pointing out is that I can simply take a set of all the prios like
                    before. Convert the set to an array. Execute the same idea but with one difference.
                    In the if statement where you check for if something should be swapped, you should add
                    an && checking for if they share priority.
                    We still run into the same problem there.
                    Maybe we should just load the pokemons performMoves in, unchronologically.
                    But then through this sort move both the actual values we're checking for, but in parallel
                    also swapping the pokemon themselves.
                    Yeah we should do that. Also the problem comes from using a set, wait it wouldn't
                    even work since it's not chronological it only removes dupes. Lol.
                    
                    I should seperate this into a method to not be confused.
                    So what I need is 1 the pokemon, and the move they used, also array of targets.
                    I should take this as an array param for all three.
                    Very important here is that their ordering is uniform in the array.
                    I could have an if state wherein which there's a boolean expression that simply has
                    a number starting at 0. Where I use the bubble sorting to instead express the pokemon
                    their move and target with a number.
                    The reasoning behind this is that, I believe it would be possible to have a for loop
                    iterating through every single pokemon, and their associated number. And if the iteration
                    finds number 0 then it will execute for the variable pokemon and their associated target
                    position. After that if block is executed then the number 0 is plused by 1 (it's naturally
                    an int or byte). Yup and then there will be a while loop, and I'll break through it when
                    the number which is the actual iterater is the same as length of the for loop e.g
                    int i = 0 ;i < n; i++
                    the actual iterater == n;
                    So we'll have a bubble sort that sorts the priority first, and then when that's don
                    we'll bubble sort the speed naturally with an if statement that checks for if their priority 
                    is the same if not then it shouldn't sort.
                    This sorts the number associated to the pokemon. 
                    Now the execution is litterally sorted with the target value and everything.
                    What I can't really imagine is how to tell the compiler to execute performMove based 
                    off of the already described mapping. I could prob just add an attribute under pokemon
                    that has that number, with a setter, where bubble sorting changes that number.
                    I make an ArrayList with the first pokemon to move as a number. And then I have
                    the method inside while loop that's set to some boolean that can be reverted from inside
                    the loop. Then I'll have an if statement, that is associated to the number listing.
                    Then inside there's perform move that performs Move from pokemon 0 and the move 0 and the
                    target 0. (Remember they were or should be proportional this is why). If they can't be uniform
                    I can simply store the indexes as an attribute for both with setter as well. That's maybe easier
                    to work with as well.
                    After performMove is done. I will increse the variable that accessed the pokemon, move, target
                    by 1. And I'll reset the while loop that compares with element 0 from the arraylist.
                    Inside the while loop before the performMove is even executed there's also an if statement
                    that checks for if the number that accesses the pokemon the same as the length of amount of
                    pokemon in this battle (if so set the while loops condition to false). This can naturally
                    and should probably be done from inside the performMove if statement instead.

                    TODO: Read the above.
                     */
                    }
                    if (!hasThereBeenASort) {
                    // Hopefully doesn't exit forloop (n).
                        break;
                    }
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
}
