package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Pokemon {

    public Player trainer;
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
    public byte evasion = 0;
    private boolean statusCondition = false;
    private String currentCondition = "None";
    // Tilføjer modifiers
    private int HPMod;
    public byte AttMod = 0;
    public byte SpAMod = 0;
    public byte DefMod = 0;
    public byte SpDefMod = 0;
    public byte SpdMod = 0;
    public byte critChanceMod = 0;
    public byte accuracyMod = 0;
    ArrayList<String> SecondaryConditions = new ArrayList<>();
    public ArrayList<Move> pokemonMoves = new ArrayList<>();
    public ArrayList<String> allPokemonMoveNames = new ArrayList<>();
    public ArrayList<Move> availableMoves = new ArrayList<>();
    public ArrayList<String> availableMoveNames = new ArrayList<>();
    public Move moveInUsage;

    public boolean isSwapping = false;
    public boolean isTeam1 = true;

    public void refreshMovesThatCanBeUsed() {
        availableMoves.clear();
        availableMoveNames.clear();
        for (Move e : pokemonMoves) {
            if (e.canMoveBeUsed()) {
                availableMoves.add(e);
                availableMoveNames.add(e.moveName);
            }
        }
    }
    public void enterViewingMode() {

    }
    public void displayPokemon() {
        System.out.printf("\nPokemon Name: %s" +
        "\nTyping(s) ", PokeName);
        for (String e : Typings) {
            System.out.printf(e + " ");
        }
        System.out.printf("\n%d HP", baseHP);
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0 -> {
                    System.out.printf(", %.2f Att", (baseAtt * getAttMod()));
                    if (AttMod != 0) {
                        System.out.printf(" (%d)", AttMod);
                    }
                }
                case 1 -> {
                    System.out.printf(", %.2f SpA", (baseSpA * getSpAMod()));
                    if (SpAMod != 0) {
                        System.out.printf(" (%d)", SpAMod);
                    }
                }
                case 2 -> {
                    System.out.printf(", %.2f Def", (baseDef * getDefMod()));
                    if (DefMod != 0) {
                        System.out.printf(" (%d)", DefMod);
                    }
                }
                case 3 -> {
                    System.out.printf(", %.2f SpDef", (baseSpDef * getSpDefMod()));
                    if (SpDefMod != 0) {
                        System.out.printf(" (%d)", SpDefMod);
                    }
                }
                case 4 -> {
                    System.out.printf(", %.2f Spd", (baseSpd * getSpdMod()));
                    if (SpdMod != 0) {
                        System.out.printf(" (%d)", SpdMod);
                    }
                }
            }
        }
        System.out.printf("\nLevel %d, 0/100\n", level); // When levelelling system is added, then insert here
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0 -> {
                    if (AttMod != 0) {
                        System.out.printf("\nStat changes: Physical Attack %d -> %.2f (%d)", 
                        baseAtt, (baseAtt*getAttMod()), AttMod);
                    }
                }
                case 1 -> {
                    if (SpAMod != 0) {
                        System.out.printf("\nStat changes: Special Attack %d -> %.2f (%d)", 
                        baseSpA, (baseSpA*getSpAMod()), SpAMod);
                    }
                }
                case 2 -> {
                    if (DefMod != 0) {
                        System.out.printf("\nStat changes: Defense %d -> %.2f (%d)", 
                        baseDef, (baseDef*getDefMod()), DefMod);
                    }
                }
                case 3 -> {
                    if (SpDefMod != 0) {
                        System.out.printf("\nStat changes: Special Defense %d -> %.2f (%d)", 
                        baseSpDef, (baseSpDef*getSpDefMod()), SpDefMod);
                    }
                }
                case 4 -> {
                    if (SpdMod != 0) {
                        System.out.printf("\nStat changes: Speed %d -> %.2f (%d)", 
                        baseSpd, (baseSpd*getSpdMod()), SpdMod);
                    }
                }
            }
        }
        displayPokemonsMoves();
    }

    public void displayPokemonsMoves() {
        if (!pokemonMoves.isEmpty()) {
            System.out.print("\nMoves:");
            for (int i = 0; i < pokemonMoves.size(); i++) {
                System.out.printf(" %s (%d/%d)", pokemonMoves.get(i).moveName, 
                pokemonMoves.get(i).PP, pokemonMoves.get(i).basePP);

                if (pokemonMoves.get(i).isDisabled) {
                    System.out.printf(" Disabled");
                }

                if (i != pokemonMoves.size() - 1) {
                    System.out.print(",");
                }
            }
        } else {
            System.out.printf("\n%s hasn't learnt any moves yet!", PokeName);
        }
    }
    
    public void viewMoves (Scanner myScanner, boolean onlyMovesThatCanBeUsed) {
        int counter = 1;
        ArrayList<Integer> mapOfCounterToMoveSlot = new ArrayList<>();
        while (true) {
        System.out.println();
        if (!onlyMovesThatCanBeUsed) {
            for (int i = 0; i < pokemonMoves.size(); i++) {
                System.out.printf("(%d) View %s", counter, pokemonMoves.get(i).moveName);
                mapOfCounterToMoveSlot.add(i);
                counter++;
            }
        } else {
            for (int i = 0; i < pokemonMoves.size(); i++) {
                if (pokemonMoves.get(i).canMoveBeUsed()) {
                System.out.printf("(%d) View %s", counter, pokemonMoves.get(i).moveName);
                mapOfCounterToMoveSlot.add(i);
                counter++;
                }
            }
        }

        System.out.println(".. to continue");
        myScanner.next();

        if (myScanner.hasNext()) {myScanner.nextLine();}

        int indexChoice = -1;
        boolean dontRepeatCommand = false;
        
        if (mapOfCounterToMoveSlot.isEmpty()) {
                System.out.println(PokeName + " doesn't know any moves. GitGood bud");
                return; // GitGood bud is necessary because of reasons.
            }
            String input;

        while (!dontRepeatCommand) {
            System.out.printf("\n%s (1-" + mapOfCounterToMoveSlot.size() + "): ", trainer.getPlayerName());
            input = myScanner.next();
            try {
                indexChoice = Integer.parseInt(input);
                if (indexChoice >= 1 && indexChoice <= mapOfCounterToMoveSlot.size()) {
                    dontRepeatCommand = true;
                }
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input! Please enter a number");
            }
            System.out.print("\nInvalid input! Please enter a number between 1 and " + 
            mapOfCounterToMoveSlot.size() + ".");
        }
        pokemonMoves.get(mapOfCounterToMoveSlot.get(indexChoice - 1)).viewMove();

        System.out.println(".. to continue");
        myScanner.next();

        if (myScanner.hasNext()) {myScanner.nextLine();}

        dontRepeatCommand = false;
        System.out.printf("\n(1) View more of %s's moves\n(2) View another Pokémon\n(3) Exit Viewing-Mode"
        , PokeName);

        while (!dontRepeatCommand) {
            System.out.printf("\n%s (1-3): ", trainer.getPlayerName());
            input = myScanner.next();
            try {
                indexChoice = Integer.parseInt(input);
                if (indexChoice >= 1 && indexChoice <= 3) {
                    dontRepeatCommand = true;
                }
            } catch (NumberFormatException e) {
                System.out.printf("\nInvalid input!", trainer.getPlayerName());
            }
            System.out.println("Invalid input! Please use command 1, 2 or 3.");
        }

        switch (indexChoice) {
            // case 1 -> this loop by default repeats itself.
            case 2 -> {return;} // Will stay inside players pokemon cuz that will loop is not exited 
            case 3 -> {
                trainer.shouldNotExitViewPlayersPokemon = false; // while loop is exited in players pokemon.
                break;
                }
            }
        }
    }

    public void addMoveToPokemon(Move theMove, Player player, Scanner myScanner) {
        String playerInput;
            if (pokemonMoves.size() == 4) {
            System.out.println(PokeName + " has four moves. ");
            System.out.println(player.getPlayerName() + " do you wish to replace a move.");
            playerInput = myScanner.nextLine();
            System.out.println("X to confirm");
            
            playerInput = myScanner.nextLine().trim().toLowerCase();
                if (playerInput.equals("x")) {

                System.out.println(player.getPlayerName() + " choose which move you will replace for your' " + PokeName +":");
                
                int pChoice = Interface.presentOptionsIndexList(allPokemonMoveNames, myScanner, player.getPlayerName());
                System.out.println(PokeName + " forgot " + allPokemonMoveNames.get(pChoice) + " and learned " + theMove.moveName);
                pokemonMoves.remove(pChoice);
                pokemonMoves.add(pChoice, theMove);
                allPokemonMoveNames.remove(pChoice);
                allPokemonMoveNames.add(pChoice, theMove.moveName);

                System.out.println(theMove.moveDescription);

                    }
            // If player didn't enter yes they will skip everything and not override any Move for pokemon.
        }  else {
            System.out.println(PokeName + " learned " + theMove.moveName);
            pokemonMoves.add(theMove);
            allPokemonMoveNames.add(theMove.moveName);
            System.out.println(theMove.moveDescription);
            }
    }
    public Move getMoveInUsage() {
        return moveInUsage;
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
        // If I want an attribute to be set other than primitives I'll need to assign it here.
        public void setTypes(String types[]) {
            for (int i = 0, n = types.length; i < n; i++) {
                Typings.add(types[i]);
            }
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
        public void setEvasionMod(int modifierChange) {
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
        public void setAccMod(int modifierChange) {
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
            return (2/(2-AttMod)); // It is minus because -*- = +
        }
    }
    public void setAttMod(int modifierChange) {
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
    public void setSpAMod(int modifierChange) {
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
    public void setDefMod(int modifierChange) {
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
    public void setSpDefMod(int modifierChange) {
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
    public void setSpdMod(int modifierChange) {
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

