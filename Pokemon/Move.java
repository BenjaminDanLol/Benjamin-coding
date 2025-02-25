import java.util.Random;

public class Move{

    // Name er public
    public String moveName;
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
    private double damage = 0.0;
    private double randomMultiplier;
    private Typechart typechart;
    private String whatStatus;
    private int howManyTics;

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
        // Denne linje skal laves før en "runde" går igang men gør ikke noget p.t.
        if (user.getSpd() > victim.getSpd())
        {
            System.out.println(user.getPokeName() + "is faster and should move first.");
        }
        // Checker først og fremmest om det er en miss eller ej, return er det samme som at exit.
        // Her er paralysis og sleep også inkluderet
        if (randomSuccess(accuracy) || paraOrSleepTic(user.getCurrentCondition(), user)) {
            System.out.println("You miss!");
            return 0;
        }
        double DamageNoRand = 0.0;
        typechart = new Typechart(victim.getType1(), victim.getType2());
        randomMultiplier = (217.0 + randomNum(38) / 255.0);
        // Critboosten er indbygget i logikken efter isCrit som er bare baseret på CritChance.
        if (this.power > 0) {
            // Gen 1 dmg calc https://imgur.com/a/KxmCrKD
            DamageNoRand = (((2 * user.getLevel() * (isCrit() == true ? critMultiplier : 1) / 5.0 + 2.0) * this.power 
            // Jeg tror at jeg har tjekket for crit nu
            * (isCrit() == true ? (this.isSpecial == true ? user.getSpA() * user.getSpAMod() 
            / (victim.getSpDef() * victim.getSpAMod()) 
            : user.getAtt() * user.getAttMod() / (victim.getDef() * victim.getDefMod())) : 
            this.isSpecial == true ? user.getSpA() / victim.getSpDef() :
            user.getAtt() / victim.getDef())+100) / 50.0

            //determine whether STAB because of user types
            * (typechart.detectType(user, getType()) == true ? 1.5 : 1.0)

            //determine type advantage/disadvantage of victim type 1 and 2 (CALC X SKAL IMPLEMENTERES HER)
            * (typechart.detectType(victim, getType()) == true ? 1.5 : 1.0)

            // Det her bør checke for typeChart's array af sig selv og sammenlign det hele med move typen
            * (typechart.calcX(type)));
        }
            if (DamageNoRand > 1.0) {
            damage = DamageNoRand * (randomMultiplier);
            }
            else if (DamageNoRand != 0.0) {
                damage = 1.0;
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
        victim.setHPMod(1+damage/victim.getHP());
        // doubles er upræcise derfor tager jeg range for at være sikker
        if (typechart.calcX(type) > 1 && typechart.calcX(type) <= 2) {
            System.out.println("Super effective!");
        } else if (typechart.calcX(type) > 2 && typechart.calcX(type) <= 4) {
            System.out.println("Super DUPER effective!!");
        } else if (typechart.calcX(type) < 1 && typechart.calcX(type) >= 0.5) {
            System.out.println("Not very effective"); 
        } else if (typechart.calcX(type) < 0.5 && typechart.calcX(type) > 0.05) {
            System.out.println("Pathetic");
        } else {
            System.out.println("Opponent is immune!");
        }
            System.out.println(user.getPokeName() + " uses " + moveName + " and " +
            victim.getPokeName() + " loses " + damage + " HP!");
            System.out.println(victim.getPokeName() + " HP is now: " + victim.getHPMod()
            * victim.getHP());
        if (!victim.getStatusCondition() && inflictsStatus){
            System.out.println(victim.getPokeName() + " receives " + whatStatus);
                // Sleep er tic baseret
                howManyTics = 0;
                victim.setCurrentCondition(whatStatus);
                victim.revertStatusCondition();

        }
    // Tror ikke at return damage er nødvendigt her, ihvertfald med min kode, fordi alle modifier ændringer
    // og bliver lavet inde i functionen.
    return damage;
}
    // need more fields from Pokemon class, and can add status methods, if there are more
    /*
     * To have this as usable code I could do the following. Add a String field in Pokemon class,
     * and also add a getter / setter method for that String.
     * Now when the status chance applies, I use the setter for that field and have moveName as parameter.
     */
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
    // Tænker jeg laver Switch cases her, med en masse specifikke Status logik ved siden af.
    public void applyStatusCondition(){

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