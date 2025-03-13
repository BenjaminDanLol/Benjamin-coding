package com.example;

public class Poketest {
    public String PokeName;
    public int evolvesAt;
    public String evolvesTo;
    public String[] evolutionArray = null;
    public int baseHP;
    public int baseAtt;
    public int baseDef;
    public int baseSpA;
    public int baseSpDef;
    public int baseSpd;
    public String[] types;


    public void displayPokeInfo(){
        System.out.printf("Name: %s, evolvesAt: %s, evolvesTo: %s%n", PokeName, evolvesAt, evolvesTo);
        System.out.printf("Stats: %d, %d, %d, %d, %d, %d%n", 
        baseHP, baseAtt, baseDef, baseSpA, baseSpDef, baseSpd);
        for (String e : types) {
            System.out.printf(e + " ");
        } System.out.println();
    }
}
