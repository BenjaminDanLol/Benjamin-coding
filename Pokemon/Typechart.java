public class Typechart {

    private double attackerTypeMultiplier = 1;
    private String[] OppTypings;
    private String moveTyping;

    // Til constructeren, kan man bare indsætte i parametrene nogle getters af specifikke pokemon Objekter.
    public Typechart(Move thisMove, String OppType1, String OppType2){
    // Bør være klassens moveTyping og ikke parameterens moveTyping.
        this.moveTyping = thisMove.getType();
        this.OppTypings = new String[] {
            OppType1, OppType2
            };
    }


    public boolean DetectTypingLocal(String whichType){
        // Loop så mange typings modstanderen har, og check om et af deres typings == typen af whichType
        // Hvis et af dem er det samme, stop metoden og returnere true, i alle andre tilfælde returnere false.
        for (int i = 0, n = OppTypings.length; i < n; i++) { 
            if (whichType.equals(OppTypings[i])) { 
                return true;
            }
        }
        return false;
    }
    //FRA OLIVER ****** VI KAN EVT TJEKKE LÆNGDEN AF INDIVIDUELLE STRINGS (TYPES) FOR AT SKIPPE NOGET AF SØGNINGEN

    


    public boolean ShouldApplyStatus(){
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
    public void calcX(){
    // Victim type 1
    if (arraylist)
    attackerTypeMultiplier *= NormalCalc(moveTyping, OppTypings[0]);
    // Victim's type 2
    attackerTypeMultiplier *= NormalCalc(moveTyping, OppTypings[1]);
    // Same thing for calc of other types
    attackerTypeMultiplier *= FireCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= FireCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= WaterCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= WaterCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= GrassCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= GrassCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= ElectricCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= ElectricCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= IceCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= IceCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= FightingCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= FightingCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= PoisonCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= PoisonCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= GroundCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= GroundCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= FlyingCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= FlyingCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= PsychicCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= PsychicCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= BugCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= BugCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= RockCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= RockCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= GhostCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= GhostCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= DragonCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= DragonCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= DarkCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= DarkCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= SteelCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= SteelCalc(moveTyping, OppTypings[1]);

    attackerTypeMultiplier *= FairyCalc(moveTyping, OppTypings[0]);
    attackerTypeMultiplier *= FairyCalc(moveTyping, OppTypings[1]);
    }


    public double FireCalc(String moveType, String oneofVictimTypes){
    // Doing the exact same as in NormalCalc
        if (!moveType.equals("Fire")){
        return 1;
        }
    // Super effective:
    else {
        switch (oneofVictimTypes)  {
            case ("Grass"): // Grass
            return 2;
            case ("Ice"): // Ice
            return 2;
            case ("Bug"): // Bug
            return 2;
            case ("Steel"): // Steel
            return 2;
    // Not very effective:
            case ("Fire"): // Fire
            return 0.5;
            case ("Water"): // Water
            return 0.5;
            case ("Rock"): // Rock
            return 0.5;
            case ("Dragon"): // Dragon
            return 0.5;
        default:
        return 1;
            }
        }
    }


        // Methods like these may or may not breakdown if type = null, since whilst using switch
    // functions, the compilers "default" function doesn't read null.
    public double NormalCalc(String moveType, String oneofVictimType){
        // Give no meaningful modifier value if not Normal attack and exit.
        if (!moveType.equals("Normal"))
        {
            return 1;
        }
        else {
        // Here are all opposing weaknesses of Normal.
        switch (oneofVictimType) {
            case ("Rock"): // Rock
            return 0.5;
            case ("Ghost"): // Ghost Immune
            return 0.0;
            case ("Steel"): // Steel
            return 0.5;
            default:
            // Denne her bør ikke trigger, men compileren ønsker at alle paths er taget højde for
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
            case ("Fire"):
            return 2;
            case ("Water"):
            return 0.5;
            case ("Grass"):
            return 0.5;
            case ("Poison"):
            return 2;
            case ("Rock"):
            return 2;
            case ("Dragon"):
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
            case ("Fire"):
            return 0.5;
            case ("Water"):
            return 2;
            case ("Grass"):
            return 0.5;
            case ("Poison"):
            return 0.5;
            case ("Ground"):
            return 2;
            case ("Flying"):
            return 0.5;
            case ("Bug"):
            return 0.5;
            case ("Rock"):
            return 2;
            case ("Dragon"):
            return 0.5;
            case ("Steel"):
            return 0.5;
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
                case ("Water"):
                return 2;
                case ("Grass"):
                return 0.5;
                case ("Electric"):
                return 0.5;
                case ("Ground"):
                return 0.0;
                case ("Flying"):
                return 2;
                case ("Dragon"):
                return 0.5;
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
                case ("Fire"):
                return 0.5;
                case("Water"):
                return 0.5;
                case("Grass"):
                return 2;
                case("Ice"):
                return 0.5;
                case("Ground"):
                return 2;
                case("Flying"):
                return 2;
                case("Dragon"):
                return 2;
                case("Steel"):
                return 0.5;
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
                case("Normal"):
                return 2;
                case("Ice"):
                return 2;
                case("Poison"):
                return 0.5;
                case("Flying"):
                return 0.5;
                case("Psychic"):
                return 0.5;
                case("Bug"):
                return 0.5;
                case("Rock"):
                return 2;
                case("Ghost"):
                return 0;
                case("Dark"):
                return 2;
                case("Steel"):
                return 2;
                case("Fairy"):
                return 0.5;
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
                case("Grass"):
                return 2;
                case("Poison"):
                return 0.5;
                case("Ground"):
                return 0.5;
                case("Rock"):
                return 0.5;
                case("Ghost"):
                return 0.5;
                case("Steel"):
                return 0;
                case("Fairy"):
                return 2;
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
                case("Fire"):
                return 2;
                case("Grass"):
                return 0.5;
                case("Electric"):
                return 2;
                case("Poison"):
                return 2;
                case("Flying"):
                return 0;
                case("Bug"):
                return 0.5;
                case("Rock"):
                return 2;
                case("Steel"):
                return 2;
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
                case("Grass"):
                return 2;
                case("Electric"):
                return 0.5;
                case("Fighting"):
                return 2;
                case("Bug"):
                return 2;
                case("Rock"):
                return 0.5;
                case("Steel"):
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
                case("Fighting"):
                return 2;
                case("Poison"):
                return 2;
                case("Psychic"):
                return 0.5;
                case("Dark"):
                return 0.0;
                case("Steel"):
                return 0.5;
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
                case("Fire"):
                return 0.5;
                case("Grass"):
                return 2;
                case("Fighting"):
                return 0.5;
                case("Poison"):
                return 0.5;
                case("Flying"):
                return 0.5;
                case("Psychic"):
                return 2;
                case("Ghost"):
                return 0.5;
                case("Dark"):
                return 2;
                case("Steel"):
                return 0.5;
                case("Fairy"):
                return 0.5;
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
                case("Fire"):
                return 2;
                case("Ice"):
                return 2;
                case("Fighting"):
                return 0.5;
                case("Ground"):
                return 0.5;
                case("Flying"):
                return 2;
                case("Bug"):
                return 2;
                case("Steel"):
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
                case("Psychic"):
                return 2;
                case("Ghost"):
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
                case("Fighting"):
                return 0.5;
                case("Psychic"):
                return 2;
                case("Ghost"):
                return 2;
                case("Dark"):
                return 0.5;
                case("Fairy"):
                return 0.5;
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
                case("Fire"):
                return 0.5;
                case("Water"):
                return 0.5;
                case("Electric"):
                return 0.5;
                case("Ice"):
                return 2;
                case("Rock"):
                return 2;
                case("Steel"):
                return 0.5;
                case("Fairy"):
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
                case("Fire"):
                return 0.5;
                case("Fighting"):
                return 2;
                case("Poison"):
                return 0.5;
                case("Dragon"):
                return 2;
                case("Dark"):
                return 2;
                case("Steel"):
            default:
            return 1;
            }
        }
    }
}
            /**
             * skal lave en loop inde i en loop
             * Den inderste kan faktisk bare være while (true) hvoraf der er en if statement
             * der exiter loopen med exit et eller andet -kender ikke syntaksen-.
             * Den inderste skal loope fra n's plads (yderste loops boolean og int) af type 1 og type 2
             * muligvis skal de laves om til en array.
             * Men hvad er if statementen bare (Type[n] == Type[n])?
             * Nej, det er super effective derfor skal jeg lave (Type[n] == Type[3] eller-tegn Type[n] Type[4])
             * Her er det vigtigt at forstå Type[3] og Type[4] skal mappe, til den Type, Type[n] er super effective
             * imod. 
             * Denne metode virker, men det virker lidt lige meget, da jeg er nødt til at have en metode/funktion
             * der mapper hvilken Type's er super effektive mod hinanden.
             * Jeg kunne overlappe forskellige metoder oveni hinanden. Hvoraf den bare returnere true
             * hvis Type[n] er super effektive imod Typen på den anden side af ligheds tegnet.
             * N/Ydre loop skal også loope indtil Pladsen af arrayen Type == Null, da det er der, at der
             * ikke er flere types der skal læses.
             * Metoder kunne også være en switch (kan undersøge hvad det er), ellers er det bare if
             * statements med else if statements. Der finder ud af hvad fire type er super effective
             * eller weak, eller endda immune imod. Hvoraf alle cases så bliver tjekket for. I det til-
             * fælde kan metoden hedde calcFire og den kan returnere en int istedet for som så bliver
             * multiplieren brugt til Damage fielden.
             * Hvis vi forestiller os at vi så har alle typesene mappet til en bestemt int, og ydre loopen
             * sammenligner Type[n] med alle dens modparter i inner loop. Og så at vi har alle disse calcX
             * metoder. Så vil det være fedt at vi bare havde en metode der hed calcX, som bare havde alle
             * calc metoderne i sig. 
             * Under skrivningen er der to problemer, som jeg er kommet i tanke om. 1. boolean udtrykket i
             * dobbelt loopen, som skal hoppe ud af n's plads skal trigger. Svar: Vi skal bare have den til
             * hver gang at loope længden af alle typesene, og så have den til at stoppe efter det.
             * 2. Alt dette antager at vi har types til at være en array, så vi kan lave en type[n] udtryk
             * til metoderne som calculere type effectiveness. Denne array vil vi skulle få adgang til gennem
             * en public getter, men den kan vidst ikke returnere en array som dens primitive type. Spørgsmål:
             * Kan man oprette et objekt med en array konstrueret i sig, og på en eller anden måde kalde
             * på en array field inde i objektet?
             * Jeg kunne også bare lave en lokal array, som bestod af Type1 og Type2.
             * Metoderne med Typesene skal have 2 integer som parametre, hvilket bliver n og i, taget
             * fra de to for loops.
             */