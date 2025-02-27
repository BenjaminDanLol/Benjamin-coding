import java.util.ArrayList;

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
    private int Level;
    private int CritBoost;
    private String[] Typings;
    private int evasion;
    private boolean statusCondition = false;
    private String currentCondition = "None";
    // Tilføjer modifiers
    private int HPMod = HP;
    private double AttMod = 1;
    private double SpAMod = 1;
    private double DefMod = 1;
    private double SpDefMod = 1;
    private double SpdMod = 1;
    private double accuracyMod = 1;
    ArrayList<String> SecondaryConditions = new ArrayList<>();
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
        this.Typings = type;
        this.Level = Level;
    }

    public void addSecondaryCondition(String secondaryCondition) {
        SecondaryConditions.add(secondaryCondition);
    }
    public void removeSecondaryCondition(String secondaryCondition) {
        SecondaryConditions.remove(secondaryCondition);
    }
    public ArrayList<String> getSecondaryCondtions() {
        return SecondaryConditions;
    }

    public String[] getTypings(){
        return Typings;
    }

    public String getASpecificTyping(int i) {
        return Typings[i];
    }

    public void displayTypes() {
        System.out.println(PokeName + " has the following types");
        for (String i : Typings) {
            int n = 0;
            System.out.println(", " + getASpecificTyping(n));
            n++;
        }
    }


    public double getEvasionMod(){
        if (evasion >  0)
        {
            return 3/(3+evasion);
            } 
        else if (evasion < 0)
            {
            return ((3-evasion)/3);
        }
        // Not writing else {}, cuz I'm lazy tihi
        return evasion;
    }

    public String getPokeName() {
        return this.PokeName;
    }

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


    public int getLevel() {
        return this.Level;
    }

    /*
     * I'm tired rn but I belive that the stat changes are wrong since they start at 1, based off the logic.
     * I.e. Speed, Attack, SpA can be 0, this calls for further adjustments.
     * I'll probably need to change them into int's where there are division calculations inside.
     */
    public int getHPMod() {
        return HPMod;
    }

    public void setHPMod(long changeModHP) {
        HPMod -= changeModHP;
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
        
        else if (AttMod * modifierChange < 1/6) {
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
        
        else if (SpAMod * modifierChange < 1/6) {
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
        
        else if (DefMod * modifierChange < 1/6) {
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
        
        else if (SpDefMod * modifierChange < 1/6) {
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
        
        else if (SpdMod * modifierChange < 1/6) {
            SpdMod = 1/6;
            System.out.println("Speed is at Stage: " + SpAMod + ", speed won't go lower!");

            return;
        }
        SpdMod *= modifierChange;
    }

    public double getCritDef() {
        if (DefMod < 1) {
        return DefMod * Def;
        } else {
            return Def;
        }
    }
    /* Since we want to check for if the SpDef or Def modifiers are not in the negative, since
    in the case of the mods being in the negative we would want the modified SpDef value.
    But on the flip side we want piercing for positive Def/Spdef modifiers.
    */
    public double getCritSpDef() {
        if (SpDefMod < 1) {
        return SpDefMod * SpDef;
        } else {
           return SpDef;
        }
    }

    // check the if HP Mod is 0 or less
    public boolean checkFainted() {
        if (getHPMod() <= 0) {
            System.out.println(PokeName + "fainted");
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
        statusCondition = !statusCondition;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String newCondition) {
        currentCondition = newCondition;
    }

}
