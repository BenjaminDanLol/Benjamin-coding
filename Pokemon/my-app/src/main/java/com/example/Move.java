package com.example;

import java.util.Arrays;
import java.util.Random;

public class Move{
/* Alot of yellow but these values will def be overwrited or even modified depending on shop system.
Likewise alot of them are null, and that's so the Json file won't be endlessly long. Alot of moves
have simple logic, and I want to by simply specifying what is used have the compiler ignore alot
of the functions in here. Practically they're null because I want alot of if statments to skip alot
of functions under performMove.*/ 
    public String moveName = null;
    public int PP = 0;
    public int power = 0;
    // I'll later have an isMultihit boolean and likewise an byte array hitsBetween, which is pr standard [2,5]
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
    public byte critChance = 6;
    public Typechart typechart;
    public String whatStatusCondition = null;
    public byte statModifierChange = 0;
    public String[] whatStatChanges = null;
    public byte statChangeChance = 100;
    public boolean toVictim = true;
    public boolean alwaysHits = false;
    public boolean isCrit = false;
    public boolean targetPokemonSwapping = false;
    public String moveDescription = "Move shouldn't exist";
    // This is cooking ngl. Used for moves like taunt and others.
    public boolean isDisabled = false;
    public long damage;


    public String getMoveTyping() {
        return moveTyping;
    }
    public void performMove(Pokemon user, Pokemon victim) {
        // Load in all enemy typings in, and apply the various logic for them. Maybe later I'll need to load
        // in a typechart for the user, e.g. the user could be paralyzed after a move or smthn.
        typechart = new Typechart(victim);
        // If statements since, they may all be true or only some.
        if (willHit(user, victim) || alwaysHits) {
            if (power > 0) {moveDoesDirectDamage(user, victim);}
            if (!statusType.equals("")) {mayApplyStatusCondition(user, victim);}
            if (whatStatChanges != null) {statChange(user, victim);}
            if (statusType.equals("") && whatStatusCondition != null) {applySecondaryStatusCondition(user, victim);}
        }
        if (victim.getHPMod() <= 0) {
            victim.isFainted = true;
            // I could even have custom messages based off of alot different things, since I have
            // access to everything regarding the move, the user and the victim/enemy.
            System.out.println(victim.PokeName + " fainted!");
        }
        // May be weird checking for it here, but flare blitz and other self inflicting moves will be added later.
        if (user.getHPMod() <= 0) {
            user.isFainted = true;
            System.out.println(user.PokeName + " fainted!");
        }
        PP--;
    }
    public void setMoveTyping(String moveTyping){
        this.moveTyping = moveTyping;
        // If the statusType is not specified then set it's value equal moveTyping.
        if (statusType.equals("")) {
            statusType = moveTyping;
        }
    }
    Random random = new Random();
    public boolean canMoveBeUsed() {
        return (PP != 0 || isDisabled == false);
    }
    public void displayMoveInfo() {
        System.out.printf("Movename: %s, Pwr: %d! %n Description: %s %n", moveName, power, moveDescription);
    }
    public void displayWAYToMuchInfo() {
        String local = Arrays.toString(whatStatChanges);
        System.out.printf("%s,%d,%d,%b,%d,%s,%b,%s,%d,%d,%d,%s,%d,%s,%b,%b,%b,%d,%d,%s"
        , moveName, power, accuracy, isSpecial, priority, moveTyping, inflictsStatus,statusType,statusChance,critChance,damage,
        whatStatusCondition,statChangeChance,local,toVictim,alwaysHits,isCrit,statModifierChange,PP,moveDescription);
    }
    public void applySecondaryStatusCondition(Pokemon user, Pokemon victim) {
        // These are for secondardary status conditions like seeded, cursed, charging, intargetable etc.
        if (randomSuccess(statusChance)) {
            if (toVictim) {
            victim.addSecondaryCondition(whatStatusCondition);
            System.out.println(victim.PokeName + " becomes " + whatStatusCondition);
            } else if (!toVictim) {
            user.addSecondaryCondition(whatStatusCondition);
            System.out.println(user.PokeName + " becomes " + whatStatusCondition);
            }
        }
    }
    public void statChange(Pokemon user, Pokemon victim) {
        // I need to account for multiple stat Changes at once. There are alot of moves like that.
        // I'll prob need to have a boolean array for that tbh.
        if (toVictim) {
            // Per default this will be true, but in some cases not.
            if (randomSuccess(statChangeChance)) {
                for (int i = 0, n = whatStatChanges.length; i < n; i++) {
            switch (whatStatChanges[i]) {
                case "att" -> victim.setAttMod(statModifierChange);
                case "SpA" -> victim.setSpAMod(statModifierChange);
                case "def" -> victim.setDefMod(statModifierChange);
                case "SpDef" -> victim.setSpDefMod(statModifierChange);
                case "Spd" -> victim.setSpdMod(statModifierChange);
                case "acc" -> victim.setAccMod(statModifierChange);
                case "eva" -> victim.setEvasionMod(statModifierChange);
                }
                }
            }
        } else if (!toVictim) {
            if (randomSuccess(statChangeChance)) {
                for (int i = 0, n = whatStatChanges.length; i < n; i++) {
            switch (whatStatChanges[i]) {
                case "att" -> user.setAttMod(statModifierChange);
                case "SpA" -> user.setSpAMod(statModifierChange);
                case "def" -> user.setDefMod(statModifierChange);
                case "SpDef" -> user.setSpDefMod(statModifierChange);
                case "Spd" -> user.setSpdMod(statModifierChange);
                case "acc" -> user.setAccMod(statModifierChange);
                case "eva" -> user.setEvasionMod(statModifierChange);
                }
                }
            }
        }
    }
    public boolean willHit(Pokemon user, Pokemon victim) {
        long realHitChance = Math.round(accuracy*(user.getAccMod()/victim.getEvasionMod()));
        int hitChance = (int) realHitChance;
        if (randomSuccess(hitChance)) {
            System.out.println(user.PokeName + " missed!");
            return false;
        }
        return true;
    }
    public void moveDoesDirectDamage(Pokemon user, Pokemon victim) {
        isCrit = isCrit(user);
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
    public void mayApplyStatusCondition(Pokemon user, Pokemon victim){
        inflictsStatus = applyStatus(statusChance);
        if (!victim.getStatusCondition() && inflictsStatus){
            System.out.println(victim.PokeName + " receives " + whatStatusCondition);
                victim.setCurrentCondition(whatStatusCondition);
                // The method below is a boolean that is used to revert if the enemy can have
                // a status condition at all. Where the if statement checks for if it's false i.e.
                // enemy doesn't have a statusCondition. But below that would mean they do now.
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
    public boolean applyStatus(int statusChance){
        int localRange = random.nextInt(100) + 1;
        return (statusChance > localRange && typechart.ShouldApplyStatus(statusType));
    }
    public boolean isCrit(Pokemon user){
        return (randomSuccess(critChance + user.getCritMod()));
    }
    public int randomNum(int range){
        // This is per standard 0 inclusive
        return random.nextInt(range) + 1;
    }
    public boolean randomSuccess(int range) {
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
