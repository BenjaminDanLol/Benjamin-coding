import java.util.Random;

public class Move {

    private String Name;
    private int Power;
    private int Accuracy;
    private boolean isSpecial;
    private boolean hasPriority;
    private boolean isStab;
    private final double Stab = 0.25;
    // if (isStab) { Power *= Stab;}
    private String type;
    private String typeReq;
    private boolean inflictsStatus;
    private String StatusType;
    private int StatusChance;
    private int critChance;
    private final double critMultiplier = 2.0;
    private int Damage = 0;
    private double randomMultiplier;

    public Move(String Name, int Pwr, int Acc, boolean isSpcl, boolean hasPrio, boolean isStab, String type, String typeReq, boolean inflictsStatus, String StatusType, int StatusChance, int critChance) {
        this.Name = Name;
        this.Power = Pwr;
        this.Accuracy = Acc;
        this.isSpecial = isSpcl;
        this.hasPriority = hasPrio;
        this.isStab = isStab; //stab should be a string called stabReq to check for the type of the pokemon
        this.type = type;
        this.typeReq = typeReq;
        this.StatusChance = StatusChance;
        this.critChance = critChance;
        this.randomMultiplier = (217 + randomNum(38) / 255);

    }

    public void performMove(Pokemon user, Pokemon victim) {
        // Mangler CritBoost metode logik (skal implementeres i Move som setter Boost parametre i Pokemon)
        if (this.Power > 0) {
            // Gen 1 dmg calc
            int Damage = (((((2 * user.getLevel() * (this.critChance <= 6 * user.getCritBoost() ? critMultiplier : 1)) / 5 + 2) * this.Power * 
            (this.isSpecial == true ? user.getSpA() / victim.getSpDef() : user.getAtt() / victim.getSpDef()))+100) / 50) * (this.isStab == true ? 1.5 : 1)
            * 
            /* 
             Mig ikke forstå (Move.getRandomMultiplier() != 1.0 ? getRandomMultiplier() : 1.0) hvorfor ikke virke?
             Fordi jeg vil gerne have en unik randomMultiplier for alle instanser af Move.

             type chart missing (skal implementeres som en class med separate metoder for hver type, for immune, effektiv, normal og ueffektiv)
             if (Damage <1 && victim.getType1MODIFIER * victim.getType2MODIFIER > 0)
            */ 
        }
    }

    public double getRandomMultiplier(){
        return randomMultiplier;
    }
    public int randomNum(int range){
        Random random = new Random();
        return random.nextInt(range) + 1; // Returns a random number from 1 - int range.
    }

}