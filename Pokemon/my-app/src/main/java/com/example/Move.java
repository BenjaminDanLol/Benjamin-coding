package com.example;

import java.util.Random;

public class Move{
/* Alot of yellow but these values will def be overwrited or even modified depending on shop system.
Likewise alot of them are null, and that's so the Json file won't be endlessly long. Alot of moves
have simple logic, and I want to by simply specifying what is used have the compiler ignore alot
of the functions in here. Practically they're null because I want alot of if statments to skip alot
of functions under performMove.*/ 
    private String moveName = null;
    private int power = 0;
    private int accuracy = 100;
    private boolean isSpecial = false;
    private byte priority = 0;
    private String moveTyping = null;
    private boolean inflictsStatus = false;
    private String statusType = null;
    private byte statusChance = 10;
    private byte critChance = 6;
    private long damage = 0;
    private Typechart typechart;
    private String whatStatusCondition = null;
    private byte statModifierChange = 0;
    private String whatStatChanges = null;
    private boolean toVictim = true;
    private boolean alwaysHits = false;
    private boolean isCrit = false;

    Random random = new Random();

    /* I realized that Jackson will under the hood create a constructer which will overwrite all of
    keys which match the names of any of my local variables here.
    So that's cool. An afterthought overwriting seems powerful.
    */

    public void revertMoveToBase(){
        // This will look nonsensical but if I overwrite a move with attributes unintended this will help.
        // May be changed later, and also if there are new attributes for move I'll need to update as well.
        moveName = null;
        power = 0;
        accuracy = 100;
        isSpecial = false;
        priority = 0;
        moveTyping = null;
        inflictsStatus = false;
        statusType = null;
        statusChance = 10;
        critChance = 6;
        whatStatusCondition = null;
        statModifierChange = 0;
        whatStatChanges = null;
        toVictim = false;
        alwaysHits = false;
        isCrit = false;
    }

    public void performMove(Pokemon user, Pokemon victim) {
            if (moveName == null) {
            // This helps since there will always be 4 move slots, they need to be overriden first.
                System.out.println("Move doesn't exist yet!");
                return;
            }
        typechart = new Typechart(victim);
        // If statements since, they may all be true or only some.
        if (willHit(user, victim) || alwaysHits) {
            if (power > 0) {moveDoesDirectDamage(user, victim);}
            if (statusType != null) {mayApplyStatusCondition(user, victim);}
            if (whatStatChanges != null) {statChange(user, victim);}
            if (statusType == null && whatStatusCondition != null) {applySecondaryStatusCondition(user, victim);}
        }
    }
    public void applySecondaryStatusCondition(Pokemon user, Pokemon victim) {
        //There should be more logic here, it's not perfect that's for sure.
        if (toVictim) {
        victim.addSecondaryCondition(whatStatusCondition);
        System.out.println(victim.getPokeName() + " becomes " + whatStatusCondition);
        } else if (!toVictim) {
        user.addSecondaryCondition(whatStatusCondition);
        System.out.println(user.getPokeName() + " becomes " + whatStatusCondition);
        }
    }
    public void statChange(Pokemon user, Pokemon victim) {
        if (toVictim) {
            switch (whatStatChanges) {
                case "att" -> victim.setAttMod(statModifierChange);
                case "SpA" -> victim.setSpAMod(statModifierChange);
                case "def" -> victim.setDefMod(statModifierChange);
                case "SpDef" -> victim.setSpDefMod(statModifierChange);
                case "Spd" -> victim.setSpd(statModifierChange);
            }
        } else if (!toVictim) {
            switch (whatStatChanges) {
                case "att" -> user.setAttMod(statModifierChange);
                case "SpA" -> user.setSpAMod(statModifierChange);
                case "def" -> user.setDefMod(statModifierChange);
                case "SpDef" -> user.setSpDefMod(statModifierChange);
                case "Spd" -> user.setSpd(statModifierChange);
            }
        }
    }
    public boolean willHit(Pokemon user, Pokemon victim) {
        long realHitChance = Math.round(accuracy*victim.getEvasionMod());
        int fuck = (int) realHitChance;
        if (randomSuccess(fuck)) {
            System.out.println(user.getPokeName() + " missed!");
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
            System.out.println(user.getPokeName() + " uses " + moveName + " and " +
            victim.getPokeName() + " loses " + damage + " HP!");
            System.out.println(victim.getPokeName() + " HP is now: " + (victim.getHP() + victim.getHPMod()));

    }

    public void mayApplyStatusCondition(Pokemon user, Pokemon victim){
        inflictsStatus = applyStatus(statusChance);
        if (!victim.getStatusCondition() && inflictsStatus){
            System.out.println(victim.getPokeName() + " receives " + whatStatusCondition);
                victim.setCurrentCondition(whatStatusCondition);
                victim.revertStatusCondition();
            }

    }

    public int getPrio(){
        return priority;
    }
    private double aDividedD(Pokemon user, Pokemon victim, boolean isCrit) {
        if (isCrit)
            {
            System.out.println("CRIT!");
            if (isSpecial){
                return (user.getSpA() * user.getSpAMod()) / victim.getCritSpDef();
                }
            else if (!isSpecial){
                return (user.getAtt() * user.getAttMod()) / victim.getCritDef();
                }
            }
        else if (!isCrit)
            {
            System.out.println("No Crit");
            if (isSpecial){
                return (user.getSpA() * user.getSpAMod()) / (victim.getSpDef() * victim.getSpDefMod());
                }
            else if (!isSpecial){
                return (user.getAtt() * user.getAttMod()) / (victim.getDef() * victim.getDefMod());
                }
            } 
                // Idk what this is even is but I'll keep it.
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

    public String getType() {
        // This is move typing may remove method later
        return moveTyping;
    }

    public String getName() {
        return moveName;
    }

    public double randomNum(int range){
        return random.nextInt(range) + 1;
    }

    public boolean randomSuccess(int range) {
        double localRange = randomNum(100);
        return (range > localRange);
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
