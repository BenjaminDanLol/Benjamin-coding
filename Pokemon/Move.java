import java.util.Random;

public class Move{

    private String Name;
    private int Power;
    private int Accuracy;
    private boolean isSpecial;
    private boolean hasPriority;
    private final double Stab = 0.25;
    // if (isStab) { Power *= Stab;}
    private String type;
    private String learnReq;
    private boolean inflictsStatus;
    private String StatusType;
    private int StatusChance;
    private int critChance;
    private final double critMultiplier = 2.0;
    private double Damage = 0.0;
    private double randomMultiplier;
    private Typechart typechart;

    public Move(String Name, int Pwr, int Acc, boolean isSpcl, boolean hasPrio, 
    String _type, String learnReq, boolean inflictsStatus, String StatusType, int StatusChance, 
    int critChance) {
        this.Power = Pwr;
        this.Accuracy = Acc;
        this.isSpecial = isSpcl;
        this.hasPriority = hasPrio;
        this.type = _type;
        this.learnReq = learnReq;
        this.StatusChance = StatusChance;
        this.critChance = critChance;
    }

    public void performMove(Pokemon user, Pokemon victim) {
        double DamageNoRand = 0.0;
        typechart = new Typechart(victim.getType1(), victim.getType2());
        randomMultiplier = (217.0 + randomNum(38) / 255.0);
        // Critboosten er indbygget i logikken efter isCrit som er bare baseret på CritChance.
        if (this.Power > 0) {
            // Gen 1 dmg calc https://imgur.com/a/KxmCrKD
            DamageNoRand = (((2 * user.getLevel() * (isCrit() == true ? 2 : 1) / 5.0 + 2.0) * this.Power 
            // Her mangler vi noget som checker at hvis critChance er true så skal den tage unmodified Def/SpDef
            * (this.isSpecial == true ? user.getSpA() / victim.getSpDef() : user.getAtt() 
            / victim.getDef()))+100) / 50.0

            //determine whether STAB because of user types
            * (typechart.detectType(user, getType()) == true ? 1.5 : 1.0)

            //determine type advantage/disadvantage of victim type 1 and 2 (CALC X SKAL IMPLEMENTERES HER)
            * (typechart.detectType(victim, getType()) == true ? 1.5 : 1.0)

            // Det her bør checke for typeChart's array af sig selv og sammenlign det hele med move typen
            * (typechart.calcX(type));
        }
            if (DamageNoRand > 1.0) {
            Damage = DamageNoRand * (randomMultiplier);
            }
            else if (DamageNoRand != 0.0) {
                Damage = 1.0;
                }
        }
    
    public boolean isCrit(){
        return (randomSuccess(critChance));
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return Name;
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