public class Pokemon {
    // may need to be changed to public, so extenders have access or getters
    // are enough
    private String PokeName;
    private int HP; // Maybe float, any knowers?
    private int Att;
    private int Def;
    private int SpA;
    private int SpDef;
    private int Spd;
    private String type1;
    private String type2;
    private int Level;
    private int CritBoost;
    private boolean statusCondition = false;
    private String currentCondition = "None";
    // Tilføjer modifiers
    private double HPMod = 1;
    private double AttMod = 1;
    private double SpAMod = 1;
    private double DefMod = 1;
    private double SpDefMod = 1;
    private double SpdMod = 1;
    public Pokemon(String name, int HealthPoints, int Attack, int Defence, 
    int SpecialAttack, int SpecialDefence, int Speed, int Level, String... type) {
        // Something that should refer to the extenders somehow
        // Maybe this whole code is meaningless, or I need to take args as to pass in stuff.
        // Yup needed to take args for all the values and assign them
        this.PokeName = name;
        this.HP = HealthPoints;
        this.Att = Attack;
        this.Def = Defence;
        this.SpA = SpecialAttack;
        this.SpDef = SpecialDefence;
        this.Spd = Speed;
        this.type1 = type[0];
        this.type2 = type[1];
        this.Level = Level;
    }

    // Ville gerne kunne få adgang i metoder til alle disse fields
    // I sidste ende blev det all på nær Spd.

    public String getPokeName() {
        return this.PokeName;
    }
    // For each move with stat change introduced I'll need to get a setter
    public int getHP() {
        return this.HP;
    }
    public int getAtt() {
        return this.Att;
    }
    public int getDef() {
        return this.Def;
    }
    public int getSpA() {
        return this.SpA;
    }
    public int getSpDef() {
        return this.SpDef;
    }
    public int getSpd() {
        return this.Spd;
    }
    public String showType() {
        return this.type1 + (this.type2 == null || this.type2.isEmpty() ? "" : "/" + this.type2);
    }

    public String[] getTypeArray() {
        return new String[] {getType1(), (getType2() == null ? "" : getType2())};
    }

    public String getType1() {
        return this.type1;
    }
    public String getType2() {
        return this.type2;
    }
    public int getLevel() {
        return this.Level;
    }

    public double getHPMod() {
        return HPMod;
    }
    public void setHPMod(double modifierChange) {
        HPMod -= modifierChange;
    }
    public double getAttMod() {
        return AttMod;
    }
    public void setAttMod(double modifierChange) {
        if (AttMod * modifierChange > 6) {
            AttMod = 6;
            System.out.println("Attack is at Stage: " + SpAMod + ", attack won't go higher!");
            return;
        }
        
        if (AttMod * modifierChange < 1/6) {
            AttMod = 1/6;
            System.out.println("Attack is at Stage: -6, attack won't go lower!");
            return;
        }
        AttMod *= modifierChange;
    }
    public double getSpAMod() {
        return SpAMod;
    }
    public void setSpAMod(double modifierChange) {
        if (SpAMod * modifierChange > 6) {
            SpAMod = 6;
            System.out.println("Special Attack is at Stage: " + SpAMod + ", special attack won't go higher!");
            return;
        }
        
        if (SpAMod * modifierChange < 1/6) {
            SpAMod = 1/6;
            System.out.println("Special Attack is at Stage: -6, special attack won't go lower!");
            return;
        }
        SpAMod *= modifierChange;
    }
    public double getDefMod() {
        return DefMod;
    }
    public void setDefMod(double modifierChange) {
        if (DefMod * modifierChange > 6) {
            DefMod = 6;
            System.out.println("Defense is at Stage: " + SpAMod + ", defense won't go higher!");
            return;
        }
        
        if (DefMod * modifierChange < 1/6) {
            DefMod = 1/6;
            System.out.println("Defense is at Stage: -6, defense won't go lower!");
            return;
        }
        DefMod *= modifierChange;
    }
    public double getSpDefMod() {
        return SpDefMod;
    }
    public void setSpDefMod(double modifierChange) {
        if (SpDefMod * modifierChange > 6) {
            SpDefMod = 6;
            System.out.println("Special Defense is at Stage: " + SpAMod + ", special defense won't go higher!");
            return;
        }
        
        if (SpDefMod * modifierChange < 1/6) {
            SpDefMod = 1/6;
            System.out.println("Special Defense is at Stage: -6, special defense won't go lower!");
            return;
        }
        SpDefMod *= modifierChange;
    }
    public double getSpdMod() {
        return SpdMod;
    }
    public void setSpdMod(double modifierChange) {
        if (SpdMod * modifierChange > 6) {
            SpdMod = 6;
            System.out.println("Speed is at Stage: " + SpAMod + ", speed won't go higher!");
            return;
        }
        
        if (SpdMod * modifierChange < 1/6) {
            SpdMod = 1/6;
            System.out.println("Speed is at Stage: " + SpAMod + ", speed won't go lower!");

            return;
        }
        SpdMod *= modifierChange;
    }


    public boolean checkFainted() { // check the Pokemon in parameter if their HP 0 or less
        if (getHPMod() <= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getStatusCondition() {
        return statusCondition;
    }
    public void revertStatusCondition() {
        // Den her burde gøre at den får en status condition, hvis den ikke har en og omvendt.
        if(!statusCondition){
            statusCondition = true;
        }
        else {
            statusCondition = false;
        }
    }
    public String getCurrentCondition() {
        return currentCondition;
    }
    public void setCurrentCondition(String newCondition) {
        currentCondition = newCondition;
    }
}
