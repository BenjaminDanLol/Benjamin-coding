import java.util.ArrayList;

public class Pokemon {
    // may need to be changed to public, so extenders have access or getters
    // are enough
    private String PokeName;
    private int HP;
    private int Att;
    private int Def;
    private int SpA;
    private int SpDef;
    private int Spd;
    private int Level;
    ArrayList<String> Typings = new ArrayList<>();
    private int evasion;
    private boolean statusCondition = false;
    private String currentCondition = "None";
    // Tilføjer modifiers
    private int HPMod = HP;
    private byte AttMod = 0;
    private byte SpAMod = 0;
    private byte DefMod = 0;
    private byte SpDefMod = 0;
    private byte SpdMod = 0;
    // Need stuff for the accuracyMod
    private byte accuracyMod = 1;
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
        for (int i = 0, n = type.length; i < n; i++) {
            Typings.add(type[i]);
        }
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

    public ArrayList<String> getTypings(){
        return Typings;
    }

    public String getASpecificTyping(int i){
        return Typings.get(i);
    }

    public void displayTypes() {
        System.out.println(PokeName + " has the following types");
            for (int i = 0, n = Typings.size(); i < n; i++){
            System.out.println(Typings.get(i));
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
     * 	int calculate_exp(int level)
	{
		return level * level * level;
	}

	int calculate_tnl()
	{
		(calculate_exp(level + 1) - experience);
	}
     */

    public int getHPMod() {
        return HPMod;
    }

    public void setHPMod(long changeModHP) {
        HPMod -= changeModHP;
    }

    public double getAttMod() {
        if (AttMod >= 0){
            return AttMod + 1;
        }
        else {
            return 2/(-AttMod+2);
        }
    }

    public void setAttMod(byte modifierChange) {
        if (AttMod + modifierChange > 6) {
            AttMod = 6;
            System.out.println("Attack is at Stage: " + SpAMod + ", attack won't go higher!");
            return;
        }
        
            else if (AttMod + modifierChange < -6) {
                AttMod = -6;
                System.out.println("Attack is at Stage: -6, attack won't go lower!");
                return;
            }
        AttMod += modifierChange;
    }

    public double getSpAMod() {
        if (SpAMod >= 0){
            return SpAMod + 1;
        }
            else {
                return 2/(-SpAMod+2);
            }
    }

    public void setSpAMod(byte modifierChange) {
        if (SpAMod + modifierChange > 6) {
            SpAMod = 6;
            System.out.println("Special Attack is at Stage: " + SpAMod + ", special attack won't go higher!");
            return;
        }
        
            else if (SpAMod + modifierChange < -6) {
                SpAMod = -6;
                System.out.println("Special Attack is at Stage: -6, special attack won't go lower!");
                return;
            }
        SpAMod += modifierChange;
    }

    public double getDefMod() {
        if (DefMod >= 0){
            return DefMod + 1;
        }
            else {
                return 2/(-DefMod+2);
            }
    }

    public void setDefMod(byte modifierChange) {
        if (DefMod + modifierChange > 6) {
            DefMod = 6;
            System.out.println("Defense is at Stage: " + SpAMod + ", defense won't go higher!");
            return;
        }
        
            else if (DefMod + modifierChange < -6) {
                DefMod = -6;
                System.out.println("Defense is at Stage: -6, defense won't go lower!");
                return;
            }
        DefMod += modifierChange;
    }

    public double getSpDefMod() {
        if (SpDefMod >= 0){
            return AttMod + 1;
        }
            else {
                return 2/(-SpDefMod+2);
            }
    }

    public void setSpDefMod(byte modifierChange) {
        if (SpDefMod + modifierChange > 6) {
            SpDefMod = 6;
            System.out.println("Special Defense is at Stage: " + SpAMod + ", special defense won't go higher!");
            return;
        }
        
            else if (SpDefMod + modifierChange < -6) {
                SpDefMod = -6;
                System.out.println("Special Defense is at Stage: -6, special defense won't go lower!");
                return;
            }
        SpDefMod += modifierChange;
    }

    public double getSpdMod() {
        if (SpdMod >= 0){
            return SpdMod + 1;
        }
            else {
                return 2/(-SpdMod+2);
            }
    }

    public void setSpdMod(byte modifierChange) {
        if (SpdMod + modifierChange > 6) {
            SpdMod = 6;
            System.out.println("Speed is at Stage: " + SpAMod + ", speed won't go higher!");
            return;
        }

            else if (SpdMod + modifierChange < -6) {
                SpdMod = -6;
                System.out.println("Speed is at Stage: " + SpAMod + ", speed won't go lower!");
                return;
            }
        SpdMod += modifierChange;
    }

    public double getCritDef() {
        // For negative stages
        if (DefMod < 0) {
        return DefMod * Def;
        } 
            else {
        // Pierce and ignore positive modifiers for Def
                return Def;
            }
    }
    public double getCritSpDef() {
        if (SpDefMod < 0) {
        return SpDefMod * SpDef;
        } 
            else {
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
