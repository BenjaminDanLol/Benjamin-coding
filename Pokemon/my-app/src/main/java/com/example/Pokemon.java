package com.example;

import java.util.ArrayList;

public class Pokemon {

    public String PokeName;
    public int evolvesAt;
    public String evolvesTo = null;
    public String[] evolutionArray = null;
    public int baseHP;
    public int baseAtt;
    public int baseDef;
    public int baseSpA;
    public int baseSpDef;
    public int baseSpd;
    private int level = 5;
    private String[] types;
    ArrayList<String> Typings = new ArrayList<>();
    private int evasion = 0;
    private boolean statusCondition = false;
    private String currentCondition = "None";
    // Tilføjer modifiers
    private int HPMod = baseHP;
    private byte AttMod = 0;
    private byte SpAMod = 0;
    private byte DefMod = 0;
    private byte SpDefMod = 0;
    private byte SpdMod = 0;
    private byte critChanceMod = 0;
    // Need stuff for the accuracyMod
    private byte accuracyMod = 1;
    ArrayList<String> SecondaryConditions = new ArrayList<>();
    
    public void addSecondaryCondition(String secondaryCondition) {
        SecondaryConditions.add(secondaryCondition);
    }

    public void removeSecondaryCondition(String secondaryCondition) {
        SecondaryConditions.remove(secondaryCondition);
    }

        public ArrayList<String> getSecondaryCondtions() {
            return SecondaryConditions;
        }

        // Very important to use this func before accessing the Typings ArrayList.

        public String[] getOldTypes() {
            return types;
        }
        // Jackson should automatically access this.
        public void setTypes(String types[]) {
            for (int i = 0, n = types.length; i < n; i++) {
                Typings.add(types[i]);
            }
        }

        public ArrayList<String> getTypings(){
            return Typings;
        }
        public String getASpecificTyping(int i){
            return Typings.get(i);
        }
    
        public void addATyping(String _Typing){
            Typings.add(_Typing);
        }
        public void displayPokeInfo(){
            System.out.printf("Name: %s, evolvesAt: %s, evolvesTo: %s%n", PokeName, evolvesAt, evolvesTo);
            System.out.printf("Stats: %d, %d, %d, %d, %d, %d%n", 
            baseHP, baseAtt, baseDef, baseSpA, baseSpDef, baseSpd);
            for (String e : Typings) {
                System.out.printf(e + " ");
            } System.out.println();
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
        public void setEvasionMod(int i){
            evasion += i;
        }

    public String getPokeName() {
        return this.PokeName;
    }

    public void setPokeName(String _Name) {
        PokeName = _Name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int _level) {
        level = _level;
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
    public void resetPokemon() {
        resetMods();
    }
    public void resetMods() {
        // Everything that should be reset after a battle should be reset here.
        int HPMod = baseHP;
        byte AttMod = 0;
        byte SpAMod = 0;
        byte DefMod = 0;
        byte SpDefMod = 0;
        byte SpdMod = 0;
        byte critChanceMod = 0;
    }

    public byte getCritMod() {
        return critChanceMod;
    }
    public void setCritMod(byte critChange) {
        // Scared of byte overflow shenanigans
        if (critChanceMod + critChange > 100) {
            critChanceMod = 100;
        } else if (critChanceMod + critChange <= 0) {
            critChanceMod = 0;
        }
        else {
            critChanceMod += critChange;
        }
    }
     
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
        return DefMod * baseDef;
        } 
            else {
        // Pierce and ignore positive modifiers for Def
                return baseDef;
            }
    }
    public double getCritSpDef() {
        if (SpDefMod < 0) {
        return SpDefMod * baseSpDef;
        } 
            else {
                return baseSpDef;
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

