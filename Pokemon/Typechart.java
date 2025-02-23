public class Typechart {
    private double attackerTypeMultiplier = 1;

    public void NormalCalc(String type, String typeVictim){
    // Check if type is Normal
        if (!type.equals("Normal")) {
        return;
        }
    // Here are all opposing weaknesses of Normal.
    if (typeVictim.equals("Rock")) {
    attackerTypeMultiplier *= 0.5;
        }
    
    else if (typeVictim.equals("Steel")) {
    attackerTypeMultiplier *= 0.5;
        }
    // And an immunity
    else if (typeVictim.equals("Ghost")) {
    attackerTypeMultiplier = 0.0;
        }
    }

    public void FireCalc(String type, String typeVictim){
    // Doing the exact same as in NormalCalc
        if (!type.equals("Fire")){
        return;
        }
    // Super effective:
    if (typeVictim.equals("Grass")){
    attackerTypeMultiplier *= 2;
        }
    else if (typeVictim.equals("Ice")){
    attackerTypeMultiplier *= 2;
        }
    else if (typeVictim.equals("Bug")){
    attackerTypeMultiplier *= 2;
        }
    else if (typeVictim.equals("Steel")){
    attackerTypeMultiplier *= 2;
        }
    // Weaknesses;
    else if (typeVictim.equals("Fire")){
    attackerTypeMultiplier *= 0.5;
        }
    else if (typeVictim.equals("Water")){
    attackerTypeMultiplier *= 0.5; 
        }
    else if (typeVictim.equals("Rock")){
    attackerTypeMultiplier *= 0.5;
        }
    else if (typeVictim.equals("Dragon")){
    attackerTypeMultiplier *= 0.5;
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
             * Nej, det er super effective derfor skal jeg lave (Type[n] == Type[3] eller tegn Type[n] Type[4])
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