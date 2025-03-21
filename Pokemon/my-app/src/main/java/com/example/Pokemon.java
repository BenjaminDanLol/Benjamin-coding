package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pokemon {

    public String PokeName = "Pokemon shouldn't exist";
    public int evolvesAt;
    public String evolvesTo = null;
    public String[] evolutionArray = null;
    public boolean isFainted = false;
    public int baseHP;
    public int baseAtt;
    public int baseDef;
    public int baseSpA;
    public int baseSpDef;
    public int baseSpd;
    private int level = 5;
    private String[] types;
    ArrayList<String> Typings = new ArrayList<>();
    private byte evasion = 0;
    private boolean statusCondition = false;
    private String currentCondition = "None";
    // Tilføjer modifiers
    private int HPMod;
    private byte AttMod = 0;
    private byte SpAMod = 0;
    private byte DefMod = 0;
    private byte SpDefMod = 0;
    private byte SpdMod = 0;
    private byte critChanceMod = 0;
    // Need stuff for the accuracyMod
    private byte accuracyMod = 0;
    ArrayList<String> SecondaryConditions = new ArrayList<>();
    public Move[] movesForThePokemonSlot = new Move[5];
    public ArrayList<Move> movesThatCanBeUsed = new ArrayList<>();
    public String[] movesNamesThatCanBeUsed;
    private String playerInput = "";
    byte thePokemonsChoice = 0;
    private Move moveInUsage;
    private Move selectedMove;
    // Yup it's targeting itself pr standard shouldn't matter though.
    public Pokemon target = this;
    public boolean isSwapping = false;
    public boolean isTeam1 = true;
    private int movesTotal = 0;
    Move blankMove = new Move();
    public void moveController(Scanner myScanner, Player player) {
    if (isSwapping) {
        // movesForThePokemonSlot is a fake move, that has no name, it only has a prio of 10.
        moveInUsage = movesForThePokemonSlot[0];
        isSwapping = false;
        return;
    }
    // This updates the ArrayList movesThatCanBeUsed to reflect only the moves the pokemon has actually
    // learned. In conjuction with that it updates movesThatCanBeUsed as well, to reflect that.
    updatePokemonMovesStringAll();
    System.out.println("\t" + PokeName + " has the following moves:");
    byte m = 1;
        for (String s : movesNamesThatCanBeUsed) {
            System.out.println("\t(" + m + ") " + s);
            m++;
        }
    // This updates the ArrayList movesThatCanBeUsed to be concurrent with the state of the pokemon.
    // Like updatePokemonMovesStringAll(); it updates movesThatCanBeUsed as well.
    // It's inefficient sure, but it works as of now.
    refreshMovesThatCanBeUsed();
    System.out.println("\t But of those " + PokeName + " can only use the following: ");
    m = 1;
    for (Move e : movesThatCanBeUsed) {
        System.out.println("\t(" + m + ") " + e.moveName);
        m++;
        }
    System.out.println("\t " + player.getPlayerName() + " type anything to continue!");
    myScanner.nextLine();
    moveCommandSection(myScanner, player);
    }
    private void moveCommandSection(Scanner myScanner, Player player) {
    try {
        while (thePokemonsChoice != 1 && thePokemonsChoice != 4){
        System.out.println(player.getPlayerName() + " can do the following: %n" +
        "\t(1) choose a move that " + PokeName + " should use this turn.%n" +
        "\t(2) see move's details.%n" +
        "\t(3) exit back to the player menu.%n");
        thePokemonsChoice = myScanner.nextByte(); 
        switch (thePokemonsChoice) {
            case 1 -> {
                // Could have a yes/no prompt here
                moveInUsage = movesForThePokemonSlot[Interface.presentOptionsIndex(movesNamesThatCanBeUsed, 
                movesNamesThatCanBeUsed.length, myScanner, PokeName)];
                    }
            case 2 -> {
                // ANOTHER RECURSION
                displayMoveMenu(myScanner, player);
                    }
            case 3 -> {
                // ANOTHER ONE BOOM!
                player.playerController(myScanner);
            }
                }
            }   
    } catch (InputMismatchException e) {
    System.out.println("Invalid Input! Input needs to be either command 1, 2, 3 or 4!");
        }
    }
    private void displayMoveMenu(Scanner myScanner, Player player) {
        playerInput = "";
        while (!playerInput.equals("1") && !playerInput.equals("2")) {
            System.out.println(player.getPlayerName() + " do you wish to. %n1) see all of " + this.PokeName + 
            "'s moves or %n" + "2) only the ones you can choose? (1/2)" );
            playerInput = myScanner.nextLine().trim();
            }
        
        if (playerInput.equals("1")) {
            // Only update here since when this method is used the pokemonNames are of the ones that can be used.
            updatePokemonMovesStringAll();
            selectedMove = movesThatCanBeUsed.get(Interface.presentOptionsIndex(movesNamesThatCanBeUsed, 
            movesNamesThatCanBeUsed.length, myScanner, PokeName));
        }
        else if (playerInput.equals("2")) {
            selectedMove = movesThatCanBeUsed.get(Interface.presentOptionsIndex(movesNamesThatCanBeUsed,
            movesNamesThatCanBeUsed.length, myScanner, PokeName));
        }
        playerInput = "";
        selectedMove.displayMoveInfo();
        System.out.println(player.getPlayerName() + " press.. to continue");
        myScanner.nextLine();
        while (!playerInput.equals("yes") && !playerInput.equals("no")) {
            System.out.println("\tDo you want to see the description of one of " + this.PokeName +
            "'s other moves " + player.getPlayerName() + "? (yes/no)");
            playerInput = myScanner.nextLine().trim().toLowerCase();
        }
        if (playerInput.equals("yes")) {
            moveCommandSection(myScanner, player);
        }
    }
    public Move getMoveInUsage() {
        return moveInUsage;
    }
    public void refreshMovesThatCanBeUsed() {
        movesThatCanBeUsed.clear();
        byte i = 0;
        for (; i < movesForThePokemonSlot.length; i++) {
            // move index 1 or [0] is filtered away because it's description is Move shouldn't exist.
            if (movesForThePokemonSlot[i].PP != 0 && 
            !movesForThePokemonSlot[i].moveDescription.equals("Move shouldn't exist")){
                // This will add indexes of moves from movesForPokemonSlot
                // that can be used as available for player.
                movesThatCanBeUsed.add(movesForThePokemonSlot[i]);
            }
        }
        movesNamesThatCanBeUsed = new String[movesThatCanBeUsed.size()];
        i = 0;
        for (Move move : movesThatCanBeUsed) {
            movesNamesThatCanBeUsed[i] = move.moveName;
            i++;
        }

    }
    public void addMoveToPokemon(Move theMove, Player player, Scanner myScanner) {
        // Since there's a move nobody should see that has a prio 10 first it's 0 exclusive.
        // TODO THIS IS UGLY
        movesTotal = howManyMovesDoesPokemonHave() + 1;
        if (movesTotal == 5) {
        System.out.println(PokeName + " has four moves. ");
        System.out.println(player.getPlayerName() + " do you wish to replace a move.");
        System.out.println("yes to confirm");
        if (myScanner.nextLine().equals("yes")) {
        System.out.println(player.getPlayerName() + " choose which move you will replace for your' " + this.PokeName +":");
        updatePokemonMovesStringAll();
        Move pChoice = movesForThePokemonSlot[Interface.presentOptionsIndex(movesNamesThatCanBeUsed, 
        movesNamesThatCanBeUsed.length, myScanner, player.getPlayerName())];
        // TODO have a random selection of moves in addMove.
        System.out.println(PokeName + " forgot " + pChoice.moveName + " and learned " + " not yet implemented");
        movesForThePokemonSlot[movesTotal] = blankMove;
        // And then the move the new should be assigned to movesForThe..[movesTotal].
        movesForThePokemonSlot[movesTotal] = theMove;
        System.out.println(theMove.moveDescription);
            }
            // If player didn't enter yes they will skip everything and not override any Move for pokemon.
        }  else {
            System.out.println(PokeName + " learned " + theMove.moveName);
            movesForThePokemonSlot[movesTotal] = theMove;
            System.out.println(theMove.moveDescription);
            }
    }
    public int howManyMovesDoesPokemonHave() {
        int i = 0;
        while (!movesForThePokemonSlot[i].moveDescription.equals("Move shouldn't exist") && movesForThePokemonSlot[i] != null) {
            i++;
        }
        // Since movesForThePokemonSlot[0] is "empty".
        return i - 1;
    }
    public void updatePokemonMovesStringAll(){
    movesThatCanBeUsed.clear();        
        for (Move m : movesForThePokemonSlot) {
            if (!m.moveDescription.equals("Move shouldn't exist")) {
                movesThatCanBeUsed.add(m);
            }
        }
        movesNamesThatCanBeUsed = new String[movesThatCanBeUsed.size()];
        byte i = 0;
        for (Move m : movesThatCanBeUsed) {
            movesNamesThatCanBeUsed[i] = m.moveName;
            i++;
        }
    }
    public void addSecondaryCondition(String secondaryCondition) {
        for (String e : SecondaryConditions) {
            if (e.equals(secondaryCondition)) {
                System.out.println(PokeName + " already has " + e);
                return;
            }
        }
        SecondaryConditions.add(secondaryCondition);
    }
    public void removeSecondaryCondition(String secondaryCondition) {
        SecondaryConditions.remove(secondaryCondition);
    }
        public ArrayList<String> getSecondaryCondtions() {
            return SecondaryConditions;
        }
        public String[] getOldTypes() {
            return types;
        }
        public void setTypes(String types[]) {
            for (int i = 0, n = types.length; i < n; i++) {
                Typings.add(types[i]);
            }

            for (int i = 0, n = movesForThePokemonSlot.length; i < n; i++) {
                movesForThePokemonSlot[i] = new Move();
            }
            // (Swap Pokemon move type, scuffed solution but what evs)
            movesForThePokemonSlot[0].priority = 10;
            resetMods();
        }
        public ArrayList<String> getTypings(){
            return Typings;
        }
        public String getASpecificTyping(int i){
            return Typings.get(i);
        }
        public void addATyping(String _Typing){
            Typings.add(_Typing);
        }
        public void displayPokeInfo(){
            if (evolvesTo != null) {
            System.out.printf("Name: %s, evolvesAt: %s, evolvesTo: %s%n", PokeName, evolvesAt, evolvesTo);
            } else if (evolutionArray != null) {
            System.out.printf("Name: %s, evolvesAt: %s%nCan evolve to the following: %s%n", PokeName, evolvesAt, Arrays.toString(evolutionArray));
            } else {
            System.out.println("Name: " + PokeName);    
            }
            System.out.printf("Stats: %d, %d, %d, %d, %d, %d%n", 
            baseHP, baseAtt, baseDef, baseSpA, baseSpDef, baseSpd);
            for (String e : Typings) {
                System.out.printf(e + " ");
            } System.out.println();
        }
        public double getEvasionMod() {
            if (evasion >= 0)
            {
                return (3+1*(evasion))/3;
                } 
                else if (evasion < 0)
                    {
                    return 3/(3-(1*(evasion)));
                }
            return 1;
        }
        public void setEvasionMod(byte modifierChange) {
            if (evasion + modifierChange > 6) {
                evasion = 6;
                System.out.println("Evasion is at Stage: " + evasion + ", evasion won't go higher!");
                return;
            }
                else if (evasion + modifierChange < -6) {
                    evasion = -6;
                    System.out.println("Evasion is at Stage: " + evasion + ", evasion won't go lower!");
                    return;
                }
            evasion += modifierChange;
        }
        public double getAccMod() {
            if (accuracyMod >= 0) {
                return (3+accuracyMod)/3;
            } else if (accuracyMod < 0) {
                return 3/(3-(1*(accuracyMod)));
            }
            return 1;
        }
        public void setAccMod(byte modifierChange) {
            if (accuracyMod + modifierChange > 6) {
                accuracyMod = 6;
                System.out.println("Accuracy is at Stage: " + accuracyMod + ", accuracy won't go higher!");
                return;
            }
            
                else if (accuracyMod + modifierChange < -6) {
                    accuracyMod = -6;
                    System.out.println("Accuracy is at Stage: " + accuracyMod + ", accuracy won't go lower!");
                    return;
                }
            accuracyMod += modifierChange;
        }
        // Can use this later.
    public void setPokeName(String _Name) {
        PokeName = _Name;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int _level) {
        level = _level;
    }
    public void resetMods() {
        // Everything that should be reset after a battle should be reset here.
        HPMod = baseHP;
        AttMod = 0;
        SpAMod = 0;
        DefMod = 0;
        SpDefMod = 0;
        SpdMod = 0;
        critChanceMod = 0;
        accuracyMod = 0;
        evasion = 0;
    }
    public byte getCritMod() {
        return critChanceMod;
    }
    public void setCritMod(byte critChange) {
        // Scared of byte overflow shenanigans
        if (critChanceMod + critChange > 100) {
            critChanceMod = 100;
        } else if (critChanceMod + critChange <= 0) {
            critChanceMod = 0;
        }
        else {
            critChanceMod += critChange;
        }
    }
    public int getHPMod() {
        return HPMod;
    }
    public void setHPMod(long changeModHP) {
        HPMod -= changeModHP;
    }
    public double getAttMod() {
        if (AttMod >= 0){
            return (2 + AttMod) / 2;
        }
        else {
            return (2/(2-AttMod));
        }
    }
    public void setAttMod(byte modifierChange) {
        if (AttMod + modifierChange > 6) {
            AttMod = 6;
            System.out.println("Attack is at Stage: " + AttMod + ", attack won't go higher!");
            return;
        }
        
            else if (AttMod + modifierChange < -6) {
                AttMod = -6;
                System.out.println("Attack is at Stage: " + AttMod + ", attack won't go lower!");
                return;
            }
        AttMod += modifierChange;
    }
    public double getSpAMod() {
        if (SpAMod >= 0){
            return (2 + SpAMod) / 2;
        }
            else {
                return (2/(2-SpAMod));
            }
    }
    public void setSpAMod(byte modifierChange) {
        if (SpAMod + modifierChange > 6) {
            SpAMod = 6;
            System.out.println("Special Attack is at Stage: " + SpAMod + ", special attack won't go higher!");
            return;
        }
        
            else if (SpAMod + modifierChange < -6) {
                SpAMod = -6;
                System.out.println("Special Attack is at Stage: -6, special attack won't go lower!");
                return;
            }
        SpAMod += modifierChange;
    }
    public double getDefMod() {
        if (DefMod >= 0){
            return (2 + DefMod) / 2;
        }
            else {
                return (2/(2-DefMod));
            }
    }
    public void setDefMod(byte modifierChange) {
        if (DefMod + modifierChange > 6) {
            DefMod = 6;
            System.out.println("Defense is at Stage: " + DefMod + ", defense won't go higher!");
            return;
        }
        
            else if (DefMod + modifierChange < -6) {
                DefMod = -6;
                System.out.println("Defense is at Stage: " + DefMod + ", defense won't go lower!");
                return;
            }
        DefMod += modifierChange;
    }
    public double getSpDefMod() {
        if (SpDefMod >= 0){
            return (2 + SpDefMod) / 2;
        }
            else {
                return (2/(2-SpDefMod));
            }
    }
    public void setSpDefMod(byte modifierChange) {
        if (SpDefMod + modifierChange > 6) {
            SpDefMod = 6;
            System.out.println("Special Defense is at Stage: " + SpDefMod + ", special defense won't go higher!");
            return;
        }
        
            else if (SpDefMod + modifierChange < -6) {
                SpDefMod = -6;
                System.out.println("Special Defense is at Stage: " + SpDefMod + ", special defense won't go lower!");
                return;
            }
        SpDefMod += modifierChange;
    }
    public double getSpdMod() {
        if (SpdMod >= 0){
            return (2 + SpdMod) / 2;
        }
            else {
                return (2/(2-SpdMod));
            }
    }
    public void setSpdMod(byte modifierChange) {
        if (SpdMod + modifierChange > 6) {
            SpdMod = 6;
            System.out.println("Speed is at Stage: " + SpdMod + ", speed won't go higher!");
            return;
        }

            else if (SpdMod + modifierChange < -6) {
                SpdMod = -6;
                System.out.println("Speed is at Stage: " + SpdMod + ", speed won't go lower!");
                return;
            }
        SpdMod += modifierChange;
    }
    public double getCritDef() {
        // For negative stages
        if (DefMod < 0) {
        return DefMod * baseDef;
        } 
            else {
        // Pierce and ignore positive modifiers for Def
                return baseDef;
            }
    }
    public double getCritSpDef() {
        if (SpDefMod < 0) {
        return SpDefMod * baseSpDef;
        } 
            else {
                return baseSpDef;
            }
    }
    public boolean getStatusCondition() {
        return statusCondition;
    }
    public void revertStatusCondition() {
        statusCondition = !statusCondition;
    }
    public String getCurrentCondition() {
        return currentCondition;
    }
    public void setCurrentCondition(String _Condition) {
        currentCondition = _Condition;
    }
}

