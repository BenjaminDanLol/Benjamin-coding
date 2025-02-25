import java.util.Random;

public class Typechart {

    private double attackerTypeMultiplier = 1;
    private String[] VictimTypings;

    // Til constructeren, kan man bare indsætte i parametrene nogle getters af specifikke pokemon Objekter.
    public Typechart(String OppType1, String OppType2){
    // Bør være klassens moveTyping og ikke parameterens moveTyping.
        this.VictimTypings = new String[] {
            OppType1, OppType2
            };
    }


    public boolean DetectTypingLocal(String whichType){
        // Loop så mange typings modstanderen har, og check om et af deres typings == typen af whichType
        // Hvis et af dem er det samme, stop metoden og returnere true, i alle andre tilfælde returnere false.
        for (int i = 0, n = VictimTypings.length; i < n; i++) { 
            if (whichType.equals(VictimTypings[i])) { 
                return true;
            }
        }
        return false;
    }
    //FRA OLIVER ****** VI KAN EVT TJEKKE LÆNGDEN AF INDIVIDUELLE STRINGS (TYPES) FOR AT SKIPPE NOGET AF SØGNINGEN

    public boolean detectType(Pokemon thisPokemon, String moveType){

        for (int i = 0, n = thisPokemon.getTypeArray().length; i < n; i++) {
            if (moveType.equals(thisPokemon.getTypeArray()[i])) {
                return true;
        }
    }
    return false;
    }

    public boolean ShouldApplyStatus(String moveTyping){
    /*
     * I virkeligheden skal if statmenten have en && metode som tjekker om angrebet ramte (accuracy).
     * && nok også en der ser om procent chancen gik af.
     */
        if (DetectTypingLocal(moveTyping)){
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
            }
        }
        if (moveTyping.equals("Electric") && DetectTypingLocal("Ground")){
            return false;
        }
        else if(moveTyping.equals("Poison") && DetectTypingLocal("Steel")){
            return false;
        }
        // Could also check for abilities here
        return true;
    }


        // Hvis der kommer flere typings, kunne jeg loope igennem Arrayen her
    public double calcX(String moveTyping){
        /*
         * Hvis der er
         */
        for (int i = 0; i > 2; i++){    
    attackerTypeMultiplier *= NormalCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= FireCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= WaterCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= GrassCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= ElectricCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= IceCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= FightingCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= PoisonCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= GroundCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= FlyingCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= PsychicCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= BugCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= RockCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= GhostCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= DragonCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= DarkCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= SteelCalc(moveTyping, VictimTypings[i]);
    attackerTypeMultiplier *= FairyCalc(moveTyping, VictimTypings[i]);
        }
        return attackerTypeMultiplier;
    }
    public boolean applyStatusNum(int _StatusChance){
        Random random = new Random();
        int localRange = random.nextInt(100) + 1;
        if (_StatusChance > localRange) {
            return true;
        }
        return false;
    }
    public double FireCalc(String moveType, String oneofVictimTypes){
        if (!moveType.equals("Fire")){
        return 1;
        }
    else {
        switch (oneofVictimTypes)  {
            case ("Grass"), ("Ice"), ("Bug"), ("Steel"):
            return 2;
            case ("Fire"), ("Water"), ("Rock"), ("Dragon"): // Fire
            return 0.5;
        default:
        return 1;
            }
        }
    }

    public double NormalCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Normal"))
        {
            return 1;
        }
        else {
        switch (oneofVictimType) {
            case ("Rock"), ("Steel"):
            return 0.5;
            case ("Ghost"):
            return 0.0;
            default:
            return 1;
            }
        }
    }


    public double WaterCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Water")){
            return 1;
        }
        else {
        switch (oneofVictimType) {
            case ("Fire"), ("Poison"), ("Rock"):
            return 2;
            case ("Water"), ("Grass"), ("Dragon"):
            return 0.5;
        default:
        return 1;
            }
        }
    }


    public double GrassCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Grass")){
            return 1;
        }
        else {
        switch (oneofVictimType) {
            case ("Fire"), ("Grass"), ("Poison"), ("Flying"), ("Bug"), ("Dragon"), ("Steel"):
            return 0.5;
            case ("Water"), ("Ground"), ("Rock"):
            return 2;
        default:
        return 1;
            }
        }
    }


    public double ElectricCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Electric")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case ("Water"), ("Flying"):
                return 2;
                case ("Grass"), ("Electric"), ("Dragon"):
                return 0.5;
                case ("Ground"):
                return 0.0;
            default:
            return 1;
            }
        }
    }


    public double IceCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Ice")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case ("Fire"), ("Water"), ("Steel"), ("Ice"):
                return 0.5;
                case("Grass"), ("Ground"), ("Flying"), ("Dragon"):
                return 2;
            default:
            return 1;
            }
        }
    }


    public double FightingCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Fighting")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                // Steel!?!?!!?!?!?!
                case("Normal"), ("Ice"), ("Rock"), ("Dark"), ("Steel"):
                return 2;
                case("Poison"), ("Flying"), ("Psychic"), ("Bug"), ("Fairy"):
                return 0.5;
                case("Ghost"):
                return 0;
            default:
            return 1;
            }
        }
    }


    public double PoisonCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Poison")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Grass"), ("Fairy"):
                return 2;
                case("Poison"), ("Ground"), ("Rock"), ("Ghost"):
                return 0.5;
                case("Steel"):
                return 0;
            default:
            return 1;
            }
        }
    }


    public double GroundCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Ground")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fire"), ("Electric"), ("Poison"), ("Rock"), ("Steel"):
                return 2;
                case("Grass"), ("Bug"):
                return 0.5;
                case("Flying"):
                return 0;
            default:
            return 1;
            }
        }
    }


    public double FlyingCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Flying")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Grass"), ("Fighting"), ("Bug"):
                return 2;
                case("Electric"), ("Rock"), ("Steel"):
                return 0.5;
            default:
            return 1;
            }
        }
    }


    public double PsychicCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Psychic")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fighting"), ("Poison"):
                return 2;
                case("Psychic"), ("Steel"):
                return 0.5;
                case("Dark"):
                return 0.0;
            default:
            return 1;
            }
        }
    }


    public double BugCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Bug")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fire"), ("Fighting"), ("Poison"), ("Flying"), ("Ghost"), ("Steel"), ("Fairy"):
                return 0.5;
                case("Grass"), ("Psychic"), ("Dark"):
                return 2;
            default:
            return 1;
            }
        }
    }


    public double RockCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Rock")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fire"), ("Ice"), ("Flying"), ("Bug"):
                return 2;
                case("Fighting"), ("Ground"), ("Steel"):
                return 0.5;
            default:
            return 1;
            }
        }
    }


    public double GhostCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Ghost")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Normal"):
                return 0.0;
                case("Psychic"), ("Ghost"):
                return 2;
                case("Dark"):
                return 0.5;
            default:
            return 1;
            }
        }
    }


    public double DragonCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Dragon")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Dragon"):
                return 2;
                case("Steel"):
                return 0.5;
                case("Fairy"):
                return 0.0;
            default:
            return 1;
            }
        }
    }


    public double DarkCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Dark")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fighting"), ("Dark"), ("Fairy"):
                return 0.5;
                case("Psychic"):
                return 2;
                case("Ghost"):
                return 2;
            default:
            return 1;
            }
        }
    }


    public double SteelCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Steel")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fire"), ("Water"), ("Electric"), ("Steel"):
                return 0.5;
                case("Ice"), ("Rock"), ("Fairy"):
                return 2;
            default:
            return 1;
            }
        }
    }


    public double FairyCalc(String moveType, String oneofVictimType){
        if (!moveType.equals("Fairy")){
            return 1;
        }
        else {
            switch (oneofVictimType){
                case("Fire"), ("Poison"), ("Steel"):
                return 0.5;
                case("Fighting"), ("Dragon"), ("Dark"):
                return 2;
            default:
            return 1;
            }
        }
    }
}