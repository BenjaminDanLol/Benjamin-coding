
public class Typechart {

    private double attackerTypeMultiplier = 1;
    private String[] VictimTypings;

    public Typechart(String OppTypings[]){
        this.VictimTypings = new String[] {
        OppTypings[0], 
        OppTypings[1]
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
        return true;
    }

    // Ekstremt vigtigt her er at forstå at effectiveness calculateren iterere over alle elementer af en array
    public double calcX(String moveTyping)
    {

        switch (moveTyping)
        {
            case "Normal":
                attackerTypeMultiplier *= NormalCalc();

            case "Fire":
                attackerTypeMultiplier *= FireCalc();

            case "Water":
                attackerTypeMultiplier *= WaterCalc();

            case "Grass":
                attackerTypeMultiplier *= GrassCalc();

            case "Electric":
                attackerTypeMultiplier *= ElectricCalc();

            case "Ice":
                attackerTypeMultiplier *= IceCalc();

            case "Fighting":
                attackerTypeMultiplier *= FightingCalc();

            case "Poison":
                attackerTypeMultiplier *= PoisonCalc();

            case "Ground":
                attackerTypeMultiplier *= GroundCalc();

            case "Flying":
                attackerTypeMultiplier *= FlyingCalc();

            case "Psychic":
                attackerTypeMultiplier *= PsychicCalc();

            case "Bug":
                attackerTypeMultiplier *= BugCalc();

            case "Rock":
                attackerTypeMultiplier *= RockCalc();

            case "Ghost":
                attackerTypeMultiplier *= GhostCalc();

            case "Dragon":
                attackerTypeMultiplier *= DragonCalc();

            case "Dark":
                attackerTypeMultiplier *= DarkCalc();

            case "Steel":
                attackerTypeMultiplier *= SteelCalc();

            case "Fairy":
                attackerTypeMultiplier *= FairyCalc();

            default: 
            attackerTypeMultiplier = 1;
           
        return attackerTypeMultiplier;
        } 
    }
        


    public double FireCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
            switch (VictimTypings[i]) {
                case ("Grass"), ("Ice"), ("Bug"), ("Steel") -> storedModifier *= 2;
                case ("Fire"), ("Water"), ("Rock"), ("Dragon") -> storedModifier *= 0.5;
            }
        }
            return storedModifier;         
    }

    public double NormalCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
            case ("Rock"), ("Steel") -> storedModifier *= 0.5;
            case ("Ghost") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double WaterCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
            case ("Fire"), ("Poison"), ("Rock") -> storedModifier *= 2;
            case ("Water"), ("Grass"), ("Dragon") -> storedModifier *= 0.5;
            }
        }
        return storedModifier;
    }

    public double GrassCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
            case ("Fire"), ("Grass"), ("Poison"), ("Flying"), ("Bug"), ("Dragon"), ("Steel") -> storedModifier *= 0.5;
            case ("Water"), ("Ground"), ("Rock") -> storedModifier *= 2;
            }
        }
        return storedModifier;
    }

    public double ElectricCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case ("Water"), ("Flying") -> storedModifier *= 2;
                case ("Grass"), ("Electric"), ("Dragon") -> storedModifier *= 0.5;
                case ("Ground") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double IceCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case ("Fire"), ("Water"), ("Steel"), ("Ice") -> storedModifier *= 0.5;
                case("Grass"), ("Ground"), ("Flying"), ("Dragon") -> storedModifier *= 2;
            }
        }
        return storedModifier;
    }

    public double FightingCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Normal"), ("Ice"), ("Rock"), ("Dark"), ("Steel") -> storedModifier *= 2;
                case("Poison"), ("Flying"), ("Psychic"), ("Bug"), ("Fairy") -> storedModifier *= 0.5;
                case("Ghost") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double PoisonCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Grass"), ("Fairy") -> storedModifier *= 2;
                case("Poison"), ("Ground"), ("Rock"), ("Ghost") -> storedModifier *= 0.5;
                case("Steel") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double GroundCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fire"), ("Electric"), ("Poison"), ("Rock"), ("Steel") -> storedModifier *= 2;
                case("Grass"), ("Bug") -> storedModifier *= 0.5;
                case("Flying") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double FlyingCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Grass"), ("Fighting"), ("Bug") -> storedModifier *= 2;
                case("Electric"), ("Rock"), ("Steel") -> storedModifier *= 0.5;
            }
        }
        return storedModifier;
    }

    public double PsychicCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fighting"), ("Poison") -> storedModifier *= 2;
                case("Psychic"), ("Steel") -> storedModifier *= 0.5;
                case("Dark") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double BugCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fire"), ("Fighting"), ("Poison"), ("Flying"), ("Ghost"), ("Steel"), ("Fairy") -> storedModifier *= 0.5;
                case("Grass"), ("Psychic"), ("Dark") -> storedModifier *= 2;
            }
        }
        return storedModifier;
    }

    public double RockCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fire"), ("Ice"), ("Flying"), ("Bug") -> storedModifier *= 2;
                case("Fighting"), ("Ground"), ("Steel") -> storedModifier *= 0.5;
            }
        }
        return storedModifier;
    }

    public double GhostCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Normal") -> storedModifier *= 0;
                case("Psychic"), ("Ghost") -> storedModifier *= 2;
                case("Dark") -> storedModifier *= 0.5;
            }
        }
        return storedModifier;
    }

    public double DragonCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Dragon") -> storedModifier *= 2;
                case("Steel") -> storedModifier *= 0.5;
                case("Fairy") -> storedModifier *= 0;
            }
        }
        return storedModifier;
    }

    public double DarkCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fighting"), ("Dark"), ("Fairy") -> storedModifier *= 0.5;
                case("Psychic"), ("Ghost") -> storedModifier *= 2;
            }
        }
        return storedModifier;
    }

    public double SteelCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fire"), ("Water"), ("Electric"), ("Steel") -> storedModifier *= 0.5;
                case("Ice"), ("Rock"), ("Fairy") -> storedModifier *= 2;
            }
        }
        return storedModifier;
    }

    public double FairyCalc(){
        double storedModifier = 1;
        for (int i = 0; i < VictimTypings.length; i++) {
        switch (VictimTypings[i]) {
                case("Fire"), ("Poison"), ("Steel") -> storedModifier *= 0.5;
                case("Fighting"), ("Dragon"), ("Dark") -> storedModifier *= 2;
            }
        }
        return storedModifier;
    }
}