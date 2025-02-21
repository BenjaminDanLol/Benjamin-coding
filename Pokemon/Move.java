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
    }

    public void performMove(Pokemon user, Pokemon victim) {
        // Mangler CritBoost metode logik (skal implementeres i Move som setter Boost parametre i Pokemon)
        int Damage = (((((2 * user.getLevel() * (this.critChance <= 6 * user.getCritBoost() ? critMultiplier : 1)) / 5 + 2) * this.Power * 
        (this.isSpecial == true ? user.getSpA() / victim.getSpDef() : user.getAtt() / victim.getSpDef())) / 50) + 2) * (this.isStab == true ? 1.5 : 1)
        * 


    }

}