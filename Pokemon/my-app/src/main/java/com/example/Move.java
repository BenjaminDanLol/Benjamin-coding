package com.example;

import java.util.Random;

public class Move{

    private String moveName;
    private int power;
    private int accuracy;
    private boolean isSpecial;
    private int priority;
    private String type;
    private boolean inflictsStatus = false;
    private String statusType;
    private int statusChance;
    private int critChance;
    private final double critMultiplier = 2.0;
    private long damage = 0;
    private Typechart typechart;
    private String whatStatus;
    private int howManyTics;
    private boolean isCrit;

    public Move(String Name, int Pwr, int Acc, boolean isSpcl, int _priority, 
    String moveType, String StatusType, int StatusChance, 
    int critChance, String whatStatusCondition) {
        this.moveName = Name;
        this.power = Pwr;
        this.accuracy = Acc;
        this.isSpecial = isSpcl;
        this.priority = _priority;
        this.type = moveType;
        this.statusType = StatusType;
        this.statusChance = StatusChance;
        this.critChance = critChance;
        this.whatStatus = whatStatusCondition;
    }

    public double performMove(Pokemon user, Pokemon victim) {
        long realHitChance = Math.round(accuracy*victim.getEvasionMod());
        int fuck = (int) realHitChance;
        if (randomSuccess(fuck)) {
            System.out.println(user.getPokeName() + " misses!");
            return 0;
        }
        double DamageNoRand = 0.0;
        typechart = new Typechart(victim);
        double randomMultiplier = (217.0 + randomNum(38)) / 255.0;
        if (this.power > 0) {
            isCrit = isCrit();
            DamageNoRand = (((2 * user.getLevel() * (isCrit == true ? 2.0 : 1.0)) 
            / 5.0 + 2.0) 
            * this.power * (aDividedD(user, victim, isCrit)) + 100) / 50.0
            * (typechart.detectType(user, type) == true ? 1.5 :  1 ) * (typechart.calcX(type));
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
        inflictsStatus = applyStatus(statusChance);
        victim.setHPMod(damage);

        System.out.println(user.getPokeName() + " uses " + moveName + " and " +
        victim.getPokeName() + " loses " + damage + " HP!");
        System.out.println(victim.getPokeName() + " HP is now: " + (victim.getHP() + victim.getHPMod()));

        
        if (!victim.getStatusCondition() && inflictsStatus){
            System.out.println(victim.getPokeName() + " receives " + whatStatus);
                howManyTics = 0;
                victim.setCurrentCondition(whatStatus);
                victim.revertStatusCondition();
            }
        return damage;
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
                System.out.println("Something went wrong! isCrit = " + isCrit + " and " + isSpecial);
                return 1;
        }
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

    public boolean paralysisTic() {
        return randomSuccess(25);
    }

    public boolean sleepTic(Pokemon user) {
        if (howManyTics < 3) {
        howManyTics++;
        return randomSuccess(25); 
        }
        user.revertStatusCondition();
        return false;
    }

    public boolean applyStatus(int StatusChance){
        Random random = new Random();
        int localRange = random.nextInt(100) + 1;
        return (StatusChance > localRange && typechart.ShouldApplyStatus(statusType));
    }

    public boolean shouldInflictStatus(){
        return inflictsStatus;
    }

    public boolean isCrit(){
        return (randomSuccess(critChance));
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return this.moveName;
    }

    public double randomNum(int range){
        Random random = new Random();
        return random.nextInt(range) + 1;
    }

    public boolean randomSuccess(int range) {
        double localRange = randomNum(100);
        if (range > localRange) {
            return true;
        }
        return false;
    }
}
