

public class BenjaminScenario {
    /*
     * Starting the scenario itself, logic for randomize choices p1 or p2 will receive. Pokemon and possibly moves or even more shit.
     * Need scanners here which simply take a bunch of info store them into different variables.
     * Insert those variables chronologically into the parameters of the initialization of p1, and p2 respectively (inner workings).
     * The players are asked how many rounds they wish to play! Let's let the max be 100 here.
     * Store that value and have it as a parameter for a method which all the below methods are based upon. Reasoning being:
     * Simplicity and that the rounds will be looped all of them in the outer loop which is the number.
     * A Battle starts (Some sequence of printing here), which initiates the method turn. This is a method as well, and it
     * encompasses the method turn, important to add that all of the logic for after the battle is over, needs to be after 
     * turn chronologically in the method battle.
     * The method turn has the battle sequence with the various loops that check for moves and so forth, if possible make the
     * checking with the prints for 4 different move choices a method in and of itself.
     * The turn needs to start off by checking for movePriority and after speedDifferences with applied logic.
     * Tic++, this is the tic that counts what turn it is (used for status condition logic).
     * 
     * Now with an if statement? Maybe, or smthn else. Check for statusConditions, through methods, these methods are 
     * hard coded, and will be added after the sequence is fully fleshed out, with that said.
     * 
     * In the if statements where who moves first is already calculated, have a statusConditionsCheck, these are for 
     * a humongous amount of statusConditions in the game, the statusConditionsCheck will just be a switchStatement
     * Which checks for if main statusconditions are applied like paralysis, freeze, sleep etc. After that it will check
     * for "secondaryStatusEffects" such as "Flinched", "Charging", "Rampaging", "Encored" and more, these
     * statusConditions will probably be hard coded as the game goes on, but I will add them in here.
     * 
     * After this, check for what users have chosen as moves (Encore should be checked even before this). And store what they
     * chose and probably something like move1.performMove(p1, p2) will be executed.
     * 
     * Have a checkFainted before the second player moves (They shouldn't be able to attack if their fainted).
     * 
     * Have the other statusConditionCheck (IMPORTANT THESE ARE INHERENTLY DIFFERENT FROM EACH OTHER, CHECKS FOR OTHER CONDITIONS).
     * 
     * Now loop the turns until a player loses i.e. for now a Pokemon dies. 
     * 
     * Now after the battle is over have a roguelike selection for p1 and p2, where they can choose different modifiers, for the pokemon
     * or even moves they have. (WILL BE ADDED LATER, WHEN A PLAYER CLASS IS CREATED SINCE MOVES AND OTHER STUFF IS INDISTINGUISHABLE)
     * Keep a count of who won the battle.
     * Iterate this the amount of rounds chosen.
     * Display a victory screen where the player who won is mentioned.
     * 
     * 
     * I will program these functionalities inversely, i.e. the core and work my way up from the
     * Simulate a turn and up from there.
     */
    public void startNewBattle() {
        Flamethrower p1q = new Flamethrower();
        Flamethrower p1w = new Flamethrower();
        Flamethrower p1e = new Flamethrower();
        Flamethrower p1r = new Flamethrower();
        VineWhip p2z = new VineWhip();
        VineWhip p2x = new VineWhip();
        VineWhip p2c = new VineWhip();
        VineWhip p2v = new VineWhip();
        Charizard p1 = new Charizard();
        Venusaur p2 = new Venusaur();
        // Doing it 7 times to see what happens
        for (int i = 0; i < 7; i++) {
        performATwoBasedTurn(p1, p1q, p1w, p1e, p1r, p2, p2z, p2x, p2c, p2v);
        }
    }
    public void performATwoBasedTurn
    // Naturally more logic will be added here
    (
    Pokemon p1, Move p1q, Move p1w, Move p1e, Move p1r,
    Pokemon p2, Move p2z, Move p2x, Move p2c, Move p2v
    )
            {
    // The if and else if checks for who'se quicker, modifiers are included
    if (p1.getSpd() * p1.getSpdMod() > p2.getSpd() * p2.getSpdMod()) {
    // p1 performs Moveq, the mapping may look weird, but there's a reason. Just 1 move
    p1q.performMove(p1, p2);
    // p2 performs Movec, p2 chooses to use his third move.
    p2c.performMove(p2, p1);
    }
    else if (p2.getSpd() * p2.getSpdMod() > p1.getSpd() * p1.getSpdMod()) {
    p2c.performMove(p2, p1);
    p1q.performMove(p1, p2);
        }
    }
}

