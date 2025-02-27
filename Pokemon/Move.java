import java.util.Random;

public class Move{

    // Name er public (gjort private for at tjekke om det virker)
    private String moveName;
    private int power;
    private int accuracy;
    private boolean isSpecial;
    private boolean hasPriority;
    private String type;
    private boolean inflictsStatus = false;
    private String statusType;
    private int statusChance;
    private int critChance;
    private final double critMultiplier = 2.0;
    private long damage = 0;
    private double randomMultiplier;
    private Typechart typechart;
    private String whatStatus;
    private int howManyTics;
    private boolean isCrit;

    public Move(String Name, int Pwr, int Acc, boolean isSpcl, boolean hasPrio, 
    String moveType, String StatusType, int StatusChance, 
    int critChance, String whatStatusCondition) {
        this.moveName = Name;
        this.power = Pwr;
        this.accuracy = Acc;
        this.isSpecial = isSpcl;
        this.hasPriority = hasPrio;
        this.type = moveType;
        this.statusType = StatusType;
        this.statusChance = StatusChance;
        this.critChance = critChance;
        this.whatStatus = whatStatusCondition;
    }

    public double performMove(Pokemon user, Pokemon victim) {
        // Checker først og fremmest om det er en miss eller ej, return er det samme som at exit.
        // Her er paralysis og sleep også inkluderet
        long realHitChance = Math.round(accuracy*user.getEvasionMod());
        int fuck = (int) realHitChance;
        if (randomSuccess(fuck) || paraOrSleepTic(user.getCurrentCondition(), user)) {
            System.out.println(user.getPokeName() + " misses!");
            return 0;
        }
        double DamageNoRand = 0.0;
        typechart = new Typechart(victim.getTypings());
        randomMultiplier = (217.0 + randomNum(38) / 255.0);
        // Critboosten er indbygget i logikken efter isCrit som er bare baseret på CritChance.
        if (this.power > 0) {
            isCrit = isCrit();
            // Gen 1 dmg calc https://imgur.com/a/KxmCrKD
            DamageNoRand = (((2 * user.getLevel() * (isCrit == true ? critMultiplier : 1) / 5.0 + 2.0) * this.power 
            // Jeg tror at jeg har tjekket for crit nu
            * (isCrit == true ? (this.isSpecial == true ? user.getSpA() * user.getSpAMod() 
            / (victim.getCritSpDef()) 
            : user.getAtt() * user.getAttMod() / (victim.getCritDef())) : 
            // Hvis crit ikke er true
            this.isSpecial == true ? user.getSpA() * user.getSpAMod() / victim.getSpDef()
            * victim.getSpDefMod() :
            user.getAtt() * user.getAttMod() / victim.getDef() * victim.getDefMod())+100) / 50.0

            //determine whether STAB because of user types
            * (typechart.detectType(user, getType()) == true ? 1.5 : 1.0)

            //determine type advantage/disadvantage of victim type 1 and 2 (CALC X SKAL IMPLEMENTERES HER)
            * (typechart.detectType(victim, getType()) == true ? 1.5 : 1.0)

            // Det her bør checke for typeChart's array af sig selv og sammenlign det hele med move typen
            * (typechart.calcX(type)));
        }
            if (DamageNoRand > 1.0) {
            damage = Math.round(DamageNoRand * (randomMultiplier));

            }
            else if (DamageNoRand < 1 && DamageNoRand > 0) {
                damage = 1;
            }
            else {
                damage = 0;
                System.out.println("Damage is < 0, revert to just 0 dmg");
            }
        // inflictStatus bliver true/false, til scenariet, kan der så bare efter moven bliver
        // udført en p1move1.getInflictStatus.
        inflictsStatus = applyStatus(statusChance);
        /*
         * Tur 1: Pikachu 80 HP, tager 10 DMG, Pikachu HP nu 70 HP
         * Tur 2: Pikachu 70 HP, tager 10 DMG, Pikachu HP nu 60 HP
         * 3. Pikachu 60 HP, tager 10 DMG, Pikachu HP nu 50 HP
         * 4. Pikachu 50 HP, tager 10 DMG, Pikachu HP nu 40 HP
         * 
         * Tur 1 Pikachu 80 HP, mister -10/80*80 = -10 HP, Pikachu HP nu 70 HP
         * Modifier for denne tur 1 + (-10/80) = 0.875
         * victim.setHPMod(1+(damage/victim.getHP()));
         * Hvis man ganger modifier med Base så vil man få Pikachus HP hvis man gør det 
         * ovenstående iterativt
         */
        victim.setHPMod(damage);
        // doubles er upræcise derfor tager jeg range for at være sikker
        if (typechart.calcX(type) > 1 && typechart.calcX(type) <= 2) {
            System.out.println("Super effective! (2x)");
        }
        
        else if (typechart.calcX(type) > 2 && typechart.calcX(type) <= 4) {
            System.out.println("Super effective!! (4x)");
        }
        
        else if (typechart.calcX(type) < 1 && typechart.calcX(type) >= 0.5) {
            System.out.println("Not very effective (0.5x)"); 
        }
        
        else if (typechart.calcX(type) < 0.5 && typechart.calcX(type) > 0.05) {
            System.out.println("Pathetic (0.25x)");
        }
        
        else if (typechart.calcX(type) == 0) {
            System.out.println("Opponent is immune! (0x)");
        }

        else {
            System.out.println("Effective (1x)");
        }

        System.out.println(user.getPokeName() + " uses " + moveName + " and " +
        victim.getPokeName() + " loses " + damage + " HP!");
        System.out.println(victim.getPokeName() + " HP is now: " + victim.getHPMod()
        * victim.getHP());

        if (!victim.getStatusCondition() && inflictsStatus){
            System.out.println(victim.getPokeName() + " receives " + whatStatus);
                howManyTics = 0;
                victim.setCurrentCondition(whatStatus);
                victim.revertStatusCondition();
        }
    return damage;
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

        // Parametren er højst sandsynligt midlertidigt
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

    public double getRandomMultiplier(){
        return randomMultiplier;
    }

    public double randomNum(int range){
        Random random = new Random();
        return random.nextInt(range) + 1; // Returns a random number from 1 - int range.
    }

    public boolean randomSuccess(int range) {
        double localRange = randomNum(100);
        if (range > localRange) {
            return true;
        }

        return false;
    }
}