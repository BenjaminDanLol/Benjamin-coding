package com.example;

import java.util.ArrayList;

public class Typechart {
    ArrayList<String> VictimTypings = new ArrayList<>();

    public Typechart(Pokemon victim){
        VictimTypings = victim.getTypings();
    }

    public boolean DetectTypingLocal(String whichType){
        for (int i = 0, n = VictimTypings.size(); i < n; i++) { 
            if (whichType.equals(VictimTypings.get(i))) { 
                return true;
            }
        }
        return false;
    }

    public boolean detectType(Pokemon thePokemon, String moveType){

        for (int i = 0, n = thePokemon.getTypings().size(); i < n; i++) {
            if (moveType.equals(thePokemon.getASpecificTyping(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean ShouldApplyStatus(String moveTyping){
        if (DetectTypingLocal(moveTyping)){
            // This should be a better way of doing it.
            if (moveTyping.equals("Fire") || moveTyping.equals("Grass") ||
            moveTyping.equals("Electric") || moveTyping.equals("Ice") ||
            moveTyping.equals("Poison")) {
                return false;
            }
            /*
            switch (moveTyping) {
                case("Fire"):
                return false;
                case("Grass"):
                return false;
                case("Electric"):
                return false;
                case("Ice"):
                return false;
                case("Poison"):
                return false;
            } */
        }
        else if (moveTyping.equals("Electric") && DetectTypingLocal("Ground")){
            return false;
        }
        else if(moveTyping.equals("Poison") && DetectTypingLocal("Steel")){
            return false;
        }
        return true;
    }

    public double calcX(String moveTyping)
    {
        switch (moveTyping)
        {
            case "Normal":
                return NormalCalc();

            case "Fire":
                return FireCalc();

            case "Water":
                return WaterCalc();

            case "Grass":
                return GrassCalc();

            case "Electric":
                return ElectricCalc();

            case "Ice":
                return IceCalc();

            case "Fighting":
                return FightingCalc();

            case "Poison":
                return PoisonCalc();

            case "Ground":
                return GroundCalc();

            case "Flying":
                return FlyingCalc();

            case "Psychic":
                return PsychicCalc();

            case "Bug":
                return BugCalc();

            case "Rock":
                return RockCalc();

            case "Ghost":
                return GhostCalc();

            case "Dragon":
                return DragonCalc();

            case "Dark":
                return DarkCalc();

            case "Steel":
                return SteelCalc();

            case "Fairy":
                return FairyCalc();

            default: 
            return 1;
           
        } 
    }
        


    public double FireCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
            switch (VictimTypings.get(i)) {
                case ("Grass"), ("Ice"), ("Bug"), ("Steel") -> storedModifier *= 2;
                case ("Fire"), ("Water"), ("Rock"), ("Dragon") -> storedModifier *= 0.5;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double NormalCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
            case ("Rock"), ("Steel") -> storedModifier *= 0.5;
            case ("Ghost") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double WaterCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
            case ("Fire"), ("Poison"), ("Rock") -> storedModifier *= 2;
            case ("Water"), ("Grass"), ("Dragon") -> storedModifier *= 0.5;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double GrassCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
            case ("Fire"), ("Grass"), ("Poison"), ("Flying"), ("Bug"), ("Dragon"), ("Steel") -> storedModifier *= 0.5;
            case ("Water"), ("Ground"), ("Rock") -> storedModifier *= 2;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double ElectricCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case ("Water"), ("Flying") -> storedModifier *= 2;
                case ("Grass"), ("Electric"), ("Dragon") -> storedModifier *= 0.5;
                case ("Ground") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double IceCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case ("Fire"), ("Water"), ("Steel"), ("Ice") -> storedModifier *= 0.5;
                case("Grass"), ("Ground"), ("Flying"), ("Dragon") -> storedModifier *= 2;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double FightingCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Normal"), ("Ice"), ("Rock"), ("Dark"), ("Steel") -> storedModifier *= 2;
                case("Poison"), ("Flying"), ("Psychic"), ("Bug"), ("Fairy") -> storedModifier *= 0.5;
                case("Ghost") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double PoisonCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Grass"), ("Fairy") -> storedModifier *= 2;
                case("Poison"), ("Ground"), ("Rock"), ("Ghost") -> storedModifier *= 0.5;
                case("Steel") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double GroundCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fire"), ("Electric"), ("Poison"), ("Rock"), ("Steel") -> storedModifier *= 2;
                case("Grass"), ("Bug") -> storedModifier *= 0.5;
                case("Flying") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double FlyingCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Grass"), ("Fighting"), ("Bug") -> storedModifier *= 2;
                case("Electric"), ("Rock"), ("Steel") -> storedModifier *= 0.5;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double PsychicCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fighting"), ("Poison") -> storedModifier *= 2;
                case("Psychic"), ("Steel") -> storedModifier *= 0.5;
                case("Dark") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double BugCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fire"), ("Fighting"), ("Poison"), ("Flying"), ("Ghost"), ("Steel"), ("Fairy") -> storedModifier *= 0.5;
                case("Grass"), ("Psychic"), ("Dark") -> storedModifier *= 2;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double RockCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fire"), ("Ice"), ("Flying"), ("Bug") -> storedModifier *= 2;
                case("Fighting"), ("Ground"), ("Steel") -> storedModifier *= 0.5;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double GhostCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Normal") -> storedModifier *= 0;
                case("Psychic"), ("Ghost") -> storedModifier *= 2;
                case("Dark") -> storedModifier *= 0.5;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double DragonCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Dragon") -> storedModifier *= 2;
                case("Steel") -> storedModifier *= 0.5;
                case("Fairy") -> storedModifier *= 0;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double DarkCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fighting"), ("Dark"), ("Fairy") -> storedModifier *= 0.5;
                case("Psychic"), ("Ghost") -> storedModifier *= 2;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double SteelCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fire"), ("Water"), ("Electric"), ("Steel") -> storedModifier *= 0.5;
                case("Ice"), ("Rock"), ("Fairy") -> storedModifier *= 2;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }

    public double FairyCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.size(); i++) {
        switch (VictimTypings.get(i)) {
                case("Fire"), ("Poison"), ("Steel") -> storedModifier *= 0.5;
                case("Fighting"), ("Dragon"), ("Dark") -> storedModifier *= 2;
            }
        }
        System.out.printf("Effectiveness is %.2f%n", storedModifier);
        return storedModifier;
    }
}
