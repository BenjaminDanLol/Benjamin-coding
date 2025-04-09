package com.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Move{

    public String moveName = null;
    public int PP = 0;
    public int basePP;
    public int power = 0;
    public int accuracy = 100;
    public boolean isSpecial = false;
    public byte priority = 0;
    private String moveTyping = null;
    public boolean inflictsStatus = false;
    /* Not to be confused with status condition. Think about moves like body slam, they have differing types.
    And take sleep powder as well. Grass types are not immune to sleep, but they are sleep powder that's
    the moveTyping. Also moveTyping is used to calc effectiveness of moves
    */
    public String statusType = "";
    public byte statusChance = 10;
    public byte critChance = 4;
    public Typechart typechart;
    public String whatStatusCondition = null;
    public byte statModifierChange = 0;
    public String[] whatStatChanges = null;
    public byte statChangeChance = 100;
    public boolean toVictim = true;
    public boolean statToVictim = true;
    public boolean isMultiHit = false;
    public boolean hitsThrice = false;
    public boolean increasinglyDoesMoreDamage = false;
    public boolean hitsTwice = false;
    public boolean inflictsToSelf = false;
    public byte percentageToSelf = 0;
    public byte percentageMaxHP = 0;
    public boolean alwaysHits = false;
    public boolean targetPokemonSwapping = false;
    public String moveDescription = "Move shouldn't exist";
    // This is cooking ngl. Used for moves like taunt and others.
    public boolean isDisabled = false;
    public long damage;


    public String getMoveTyping() {
        return moveTyping;
    }
    public void performMove(Pokemon user, Pokemon victim, Scanner myScanner) {
        // Load in all enemy typings in, and apply the various logic for them. Maybe later I'll need to load
        // in a typechart for the user, e.g. the user could be paralyzed after a move or smthn.
        typechart = new Typechart(victim);
        // If statements since, they may all be true or only some.
        if (willHit(user, victim) || alwaysHits) {
            if (power > 0 && !isMultiHit && !hitsThrice) {moveDoesDirectDamage(user, victim);}
            if (isMultiHit) {normalMultiHit(user, victim);}
            if (hitsThrice) {moveHitsThrice(user, victim);}
            if (percentageToSelf != 0) {inflictsPercentageToSelf(user);}
            if (!statusType.equals("")) {mayApplyStatusCondition(victim);}
            if (whatStatChanges != null && whatStatChanges.length > 0) {statChange(user, victim);}
            if (statusType == null && whatStatusCondition != null) {applySecondaryStatusCondition(user, victim);}

            if (hitsTwice) { // Since some moves that hit twice have multiple effects. I.e. twineedle
                if (power > 0 && !isMultiHit && !hitsThrice) {moveDoesDirectDamage(user, victim);}
                if (isMultiHit) {normalMultiHit(user, victim);}
                if (hitsThrice) {moveHitsThrice(user, victim);}
                if (!statusType.equals("")) {mayApplyStatusCondition(victim);}
                if (whatStatChanges != null && whatStatChanges.length > 0) {statChange(user, victim);}
                if (statusType == null && whatStatusCondition != null) {applySecondaryStatusCondition(user, victim);}    
            }
        }
        
        fixedHPChanged(user); // Synthesis, roost, substitute, curse, Explosion etc.
        if (victim.getHPMod() <= 0) {
            victim.isFainted = true;
            // I could even have custom messages based off of alot different things, since I have
            // access to everything regarding the move, the user and the victim/enemy.
            System.out.println(victim.PokeName + " fainted!\n" + victim.trainer.getPlayerName() + 
            " has to choose a new pokemon to replace their " + victim.PokeName);
            victim.trainer.playerHasToSwap = true;
            victim.trainer.pokemonPlayerCanActuallyUse.remove(victim);
            // Just didn't want any pokemon to performMoves/get attacked from/in the grave xd.
            victim.moveInUsage = Interface.fakeMove;
            victim.trainer.pokemonInPlay = Interface.fakeMon;
            victim.trainer.playerController(myScanner, user.trainer.getMyTeam());
        }
        // May be weird checking for it here, but flare blitz and other self inflicting moves will be added later.
        if (user.getHPMod() <= 0) {
            user.isFainted = true;
            System.out.println(user.PokeName + " fainted!\n" + user.trainer.getPlayerName() + 
            " has to choose a new pokemon to replace their " + user.PokeName);
            user.trainer.playerHasToSwap = true;
            user.trainer.pokemonPlayerCanActuallyUse.remove(user);
            user.trainer.pokemonInPlay = Interface.fakeMon;
            user.moveInUsage = Interface.fakeMove;
            user.trainer.playerController(myScanner, victim.trainer.getMyTeam());
        }
        PP--;
    }
    public void setMoveTyping(String moveTyping){
        this.moveTyping = moveTyping;
        // If the statusType is not specified then set it's value equal moveTyping.
        if (statusType.equals("")) {
            statusType = moveTyping;
        }
        basePP = PP;
    }
    Random random = new Random();
    public boolean canMoveBeUsed() {
        return (PP != 0 || isDisabled == false);
    }
    // The best viewMove to ever exist
    public void viewMove() {

    System.out.printf("\nMove Name: %s.\n", moveName);
    if (power > 0) {
        
        if (isMultiHit) {
        System.out.printf("Has a RNG multihit, hits between 2 and 5 times!\n");
        }
        if (inflictsToSelf) {
            if (percentageToSelf < 0) {
            System.out.print("\n" + percentageToSelf + "% of dmg is done to the users themselves " +
            "after move is performed.\n");
            } else if (percentageToSelf > 0) {
            System.out.print("\nHeals the user by a percentage of dmg dealt: " + percentageToSelf + "%.\n");
            }
        }

    System.out.printf("Power %d, isSpecial %b, Crit Chance %d", power, isSpecial, critChance);
    System.out.print("%, "); // The reason for this is that the compiler gets confused with % and placeholders.
    } else {
        System.out.print("Doesn't do direct damage, ");
    }

    if (alwaysHits) {
    System.out.printf("AlwaysHits, %b", alwaysHits);
    } else {
        System.out.print("Accuracy " + accuracy + "%");
    }

    if (priority == 0 && statModifierChange == 0 || whatStatusCondition == null) {
        System.out.print(", "); // beauty is in the eye of the beholder.
    }

    if (priority != 0) {
        if (statModifierChange == 0) {
        System.out.printf("Priority %d, ", priority);
        } else {
            System.out.printf("Priority %d", priority);
        }
    }

    if (statModifierChange != 0) {
        System.out.print("\nUpon success move has a " + statChangeChance + "% chance to ");
        if (toVictim) {
        System.out.printf("raise targets: ");
        System.out.printf("decrease targets: ");

        for (int i = 0; i < whatStatChanges.length; i++) {
            if (i != whatStatChanges.length - 1) {
            System.out.print(whatStatChanges[i] + ", ");
            } else {
                System.out.print(whatStatChanges[i]);
            }
            }
        } 
        
        else {
        System.out.printf("raise pokemons: ");
        System.out.printf("decrease pokemons: ");

        }
        if (statModifierChange < 0) {
        System.out.print(" by " + (statModifierChange * -1) + " stage(s), ");
        } else {
        System.out.print(" by " + statModifierChange + " stage(s), ");
        }
    }

    if (inflictsStatus == true) {
        System.out.print("\nHas a " + statusChance + "% chance to inflict status effect " + whatStatusCondition);
        System.out.print("%, ");
    }

    if (targetPokemonSwapping) {
        System.out.print("intercepts pokemon swap, ");
    }

    System.out.printf("Move's Typing %s, PP (%d/%d), isDisabled %b", moveTyping, PP, basePP, isDisabled);
    System.out.printf("\nDescription: %s", moveDescription);
    }
    
    public void displayMoveInfo() {
        System.out.printf("Movename: %s, Pwr: %d! %n Description: %s %n", moveName, power, moveDescription);
    }
    public void displayWAYToMuchInfo() {
        String local = Arrays.toString(whatStatChanges);
        System.out.printf("%s,%d,%d,%b,%d,%s,%b,%s,%d,%d,%d,%s,%d,%s,%b,%b,%d,%d,%s"
        , moveName, power, accuracy, isSpecial, priority, moveTyping, inflictsStatus,statusType,statusChance,critChance,damage,
        whatStatusCondition,statChangeChance,local,toVictim,alwaysHits,statModifierChange,PP,moveDescription);
    }

    private void fixedHPChanged(Pokemon user) {
        if (percentageMaxHP == 0) {return;}
        user.setHPMod(-percentageMaxHP/100*user.baseHP); // Minus since negative percentageMaxHP make more sense.
    }
    private void applySecondaryStatusCondition(Pokemon user, Pokemon victim) {
        // These are for secondardary status conditions like seeded, cursed, charging, intargetable etc.
        // If status type is null, and inflictstatus is true, pokemon doesn't already have this secondary
        // and the randomSuccess of statusSchance is true then the moves secondary status condition will be added.

        if (randomSuccess(statusChance)) {
            if (toVictim) {
                for (String s : user.getSecondaryCondtions()) {
                    if (s.equals(whatStatusCondition)) {
                        return;
                    }
                }
            victim.addSecondaryCondition(whatStatusCondition);
            System.out.println(victim.PokeName + " becomes " + whatStatusCondition);
            } else if (!toVictim) {
                for (String s : victim.getSecondaryCondtions()) {
                    if (s.equals(whatStatusCondition)) {
                        return;
                    }
                }
            user.addSecondaryCondition(whatStatusCondition);
            System.out.println(user.PokeName + " becomes " + whatStatusCondition);
            }
        }
    }
    private void statChange(Pokemon user, Pokemon victim) {
        // I need to account for multiple stat Changes at once. There are alot of moves like that.
        // I'll prob need to have a boolean array for that tbh.
        if (statToVictim) {
            // Per default this will be true, but in some cases not.
            if (randomSuccess(statChangeChance)) {
                for (int i = 0, n = whatStatChanges.length; i < n; i++) {
            switch (whatStatChanges[i]) {
                // Should prob just be a map instead, but I can't imagine how the hashmap should work with methods.
                case "+att" -> victim.setAttMod(statModifierChange);
                case "+SpA" -> victim.setSpAMod(statModifierChange);
                case "+def" -> victim.setDefMod(statModifierChange);
                case "+SpDef" -> victim.setSpDefMod(statModifierChange);
                case "+Spd" -> victim.setSpdMod(statModifierChange);
                case "+acc" -> victim.setAccMod(statModifierChange);
                case "+eva" -> victim.setEvasionMod(statModifierChange);

                case "-att" -> victim.setAttMod(-statModifierChange);
                case "-SpA" -> victim.setSpAMod(-statModifierChange);
                case "-def" -> victim.setDefMod(-statModifierChange);
                case "-SpDef" -> victim.setSpDefMod(-statModifierChange);
                case "-Spd" -> victim.setSpdMod(-statModifierChange);
                case "-acc" -> victim.setAccMod(-statModifierChange);
                case "-eva" -> victim.setEvasionMod(-statModifierChange);
                    }
                }
            }
        } else if (!statToVictim) {
            if (randomSuccess(statChangeChance)) {
                for (int i = 0, n = whatStatChanges.length; i < n; i++) {
            switch (whatStatChanges[i]) {
                case "+att" -> user.setAttMod(statModifierChange);
                case "+SpA" -> user.setSpAMod(statModifierChange);
                case "+def" -> user.setDefMod(statModifierChange);
                case "+SpDef" -> user.setSpDefMod(statModifierChange);
                case "+Spd" -> user.setSpdMod(statModifierChange);
                case "+acc" -> user.setAccMod(statModifierChange);
                case "+eva" -> user.setEvasionMod(statModifierChange);

                case "-att" -> victim.setAttMod(-statModifierChange);
                case "-SpA" -> victim.setSpAMod(-statModifierChange);
                case "-def" -> victim.setDefMod(-statModifierChange);
                case "-SpDef" -> victim.setSpDefMod(-statModifierChange);
                case "-Spd" -> victim.setSpdMod(-statModifierChange);
                case "-acc" -> victim.setAccMod(-statModifierChange);
                case "-eva" -> victim.setEvasionMod(-statModifierChange);
                    }
                }
            }
        }
    }
    private boolean willHit(Pokemon user, Pokemon victim) {
        long realHitChance = Math.round(accuracy*(user.getAccMod()/victim.getEvasionMod()));
        int hitChance = (int) realHitChance;
        if (randomSuccess(hitChance)) {
            System.out.println(user.PokeName + " missed!");
            return false;
        }
        return true;
    }
    private void inflictsPercentageToSelf(Pokemon user) {
        // Note this does not work as intended for multiHit moves.
        // If needed insert this method into all damaging methods at end, then it will work.
            user.setHPMod(damage*(percentageToSelf/100));        
    }
    private void normalMultiHit(Pokemon user, Pokemon victim) {
        int rngHits = randomNum(100);
        // Note status conditions are not applied
        if (rngHits >= 0 && rngHits <= 37) {
            rngHits = 2;
        } else if (rngHits >= 38 && rngHits <= 74) {
            rngHits = 3;
        } else if (rngHits >= 75 && rngHits <= 87) {
            rngHits = 4;
        } else {
            rngHits = 5;
        }
        for (int i = 0; i < rngHits; i++) {
            moveDoesDirectDamage(user, victim);
        }
    }
    private void moveHitsThrice(Pokemon user, Pokemon victim) {
        boolean isCrit;
        double DamageNoRand;
        double randomMultiplier;
        int currentPower = power;
        for (int i = 0; i < 3; i++) {
        isCrit = isCrit(user);
        DamageNoRand = 0.0;
        randomMultiplier = (217.0 + randomNum(38)) / 255.0;
        if (currentPower > 0) {
            DamageNoRand = (((2 * user.getLevel() * (isCrit == true ? 2.0 : 1.0)) 
            / 5.0 + 2.0) 
            * currentPower * (aDividedD(user, victim, isCrit)) + 100) / 50.0
            * (typechart.detectType(user, moveTyping) == true ? 1.5 :  1 ) * (typechart.calcX(moveTyping));
        }
            if (DamageNoRand > 1) {
            damage = Math.round(DamageNoRand * (randomMultiplier));

            }
            else if (DamageNoRand < 1 && DamageNoRand > 0) {
                damage = 1;
            }
            else {
                damage = 0;
                System.out.println("Damage is < 0, revert to just 0 dmg");
            }
            victim.setHPMod(damage);
            if (victim.getHPMod() >= 0) {
                victim.setHPMod(0);
            }
            System.out.println(user.PokeName + " uses " + moveName + " and " +
            victim.PokeName + " loses " + damage + " HP!");
            System.out.println(victim.PokeName + " HP is now: " + victim.getHPMod());
            if (increasinglyDoesMoreDamage) {
            currentPower += power;
            }
        }
    }
    private void moveDoesDirectDamage(Pokemon user, Pokemon victim) {
        boolean isCrit = isCrit(user);
        double DamageNoRand = 0.0;
        double randomMultiplier = (217.0 + randomNum(38)) / 255.0;
        if (this.power > 0) {
            DamageNoRand = (((2 * user.getLevel() * (isCrit == true ? 2.0 : 1.0)) 
            / 5.0 + 2.0) 
            * this.power * (aDividedD(user, victim, isCrit)) + 100) / 50.0
            * (typechart.detectType(user, moveTyping) == true ? 1.5 :  1 ) * (typechart.calcX(moveTyping));
        }
            if (DamageNoRand > 1) {
            damage = Math.round(DamageNoRand * (randomMultiplier));

            }
            else if (DamageNoRand < 1 && DamageNoRand > 0) {
                damage = 1;
            }
            else {
                damage = 0;
                System.out.println("Damage is < 0, revert to just 0 dmg");
            }
            victim.setHPMod(damage);
            if (victim.getHPMod() >= 0) {
                victim.setHPMod(0);
            }
            System.out.println(user.PokeName + " uses " + moveName + " and " +
            victim.PokeName + " loses " + damage + " HP!");
            System.out.println(victim.PokeName + " HP is now: " + victim.getHPMod());
    }
    private void mayApplyStatusCondition(Pokemon victim){
        // typeChart.shouldApplyStatus conditional is checked for in method applyStatus.
        inflictsStatus = applyStatus(statusChance);
        if (!victim.getStatusCondition() && inflictsStatus){
            System.out.println(victim.PokeName + " receives " + whatStatusCondition);
                victim.setCurrentCondition(whatStatusCondition);
                victim.revertStatusCondition();
            }

    }
    private double aDividedD(Pokemon user, Pokemon victim, boolean isCrit) {
        if (isCrit)
            {
            System.out.println("CRIT!");
            if (isSpecial){
                return (user.baseSpA * user.getSpAMod()) / victim.getCritSpDef();
                }
            else if (!isSpecial){
                return (user.baseAtt * user.getAttMod()) / victim.getCritDef();
                }
            }
        else if (!isCrit)
            {
            System.out.println("No Crit");
            if (isSpecial){
                return (user.baseSpA * user.getSpAMod()) / (victim.baseSpDef * victim.getSpDefMod());
                }
            else if (!isSpecial){
                return (user.baseAtt * user.getAttMod()) / (victim.baseDef * victim.getDefMod());
                }
            } 
                // If there's no crit and it's neither special nor physical, (should never happen)
                System.out.println("Something went wrong! isCrit = " + isCrit + " and " + isSpecial);
                return 1;
        }
    private boolean applyStatus(int statusChance){
        int localRange = random.nextInt(100) + 1;
        return (statusChance > localRange && typechart.ShouldApplyStatus(statusType));
    }
    private boolean isCrit(Pokemon user){
        // Worth noting that it isn't a percentage crit increase.
        return (randomSuccess(critChance + user.getCritMod()));
    }

    private int randomNum(int range){
        // Since this is per standard 0 inclusive
        return random.nextInt(range) + 1;
    }
    private boolean randomSuccess(int range) {
        return (range > randomNum(100));
    }
}
    /*
    public boolean paraOrSleepTic(String _StatusName, Pokemon user) {
        return switch (_StatusName) {
            case "paralysis" -> {
            paralysisTic();
            yield true; 
        }
            case "Sleep" -> {
            sleepTic(user);
            yield true; 
        } default -> {
            yield false;
        }
    };
    }
    */
