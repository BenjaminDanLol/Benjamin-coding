import java.util.Scanner;

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
            System.out.println("1. Charizard, 2. Venusaur, 3. Charizard, 4. Blastoise");
            byte[] pokemonChoice = coreMechanic();
            Pokemon p1 = new Pokemon();
            Pokemon p2 = new Pokemon();
            switch (pokemonChoice[0]) {
                case (1): 
                p1.setPokeName("Charizard");
                p1.setHP(360);
                p1.setAtt(293);
                p1.setDef(280);
                p1.setSpA(348);
                p1.setSpDef(295);
                p1.setSpd(328);
                p1.setLevel(100);
                p1.addATyping("Fire");
                p1.addATyping("Flying");
                break;
                case (2): 
                p1.setPokeName("Venusaur");
                p1.setHP(364);
                p1.setAtt(289);
                p1.setDef(291);
                p1.setSpA(328);
                p1.setSpDef(328);
                p1.setSpd(284);
                p1.setLevel(100);
                p1.addATyping("Grass");
                p1.addATyping("Poison");
                break;
                case (3): 
                p1.setPokeName("Charizard");
                p1.setHP(360);
                p1.setAtt(293);
                p1.setDef(280);
                p1.setSpA(348);
                p1.setSpDef(295);
                p1.setSpd(328);
                p1.setLevel(100);
                p1.addATyping("Fire");
                p1.addATyping("Flying");
                break;
                case (4): 
                p1.setPokeName("Blastoise");
                p1.setHP(362);
                p1.setAtt(291);
                p1.setDef(328);
                p1.setSpA(295);
                p1.setSpDef(339);
                p1.setSpd(280);
                p1.setLevel(100);
                p1.addATyping("Water");
                break;
            }
            System.out.println("p1 chose " + p1.getPokeName());
            switch (pokemonChoice[1]) {
                case (1): 
                p2.setPokeName("Charizard");
                p2.setHP(360);
                p2.setAtt(293);
                p2.setDef(280);
                p2.setSpA(348);
                p2.setSpDef(295);
                p2.setSpd(328);
                p2.setLevel(100);
                p2.addATyping("Fire");
                p2.addATyping("Flying");
                break;
                case (2): 
                p2.setPokeName("Venusaur");
                p2.setHP(364);
                p2.setAtt(289);
                p2.setDef(291);
                p2.setSpA(328);
                p2.setSpDef(328);
                p2.setSpd(284);
                p2.setLevel(100);
                p2.addATyping("Grass");
                p2.addATyping("Poison");
                break;
                case (3): 
                p2.setPokeName("Charizard");
                p2.setHP(360);
                p2.setAtt(293);
                p2.setDef(280);
                p2.setSpA(348);
                p2.setSpDef(295);
                p2.setSpd(328);
                p2.setLevel(100);
                p2.addATyping("Fire");
                p2.addATyping("Flying");
                break;
                case (4): 
                p2.setPokeName("Blastoise");
                p2.setHP(362);
                p2.setAtt(291);
                p2.setDef(328);
                p2.setSpA(295);
                p2.setSpDef(339);
                p2.setSpd(280);
                p2.setLevel(100);
                p2.addATyping("Water");
                break;
            }
        System.out.println("p2 chose " + p2.getPokeName());
        // Too lazy to do the above rn.
        BodySlam p1move1 = new BodySlam();
        Flamethrower p1move2 = new Flamethrower();
        VineWhip p1move3 = new VineWhip();
        SleepPowder p1move4 = new SleepPowder();
        BodySlam p2move1 = new BodySlam();
        Flamethrower p2move2 = new Flamethrower();
        VineWhip p2move3 = new VineWhip();
        SleepPowder p2move4 = new SleepPowder();
        for (int i = 0; i < 5; i++){
            System.out.println("1. BodySlam, 2. Flamethrower, 3. VineWhip, 4. SleepPowder");
            performATwoBasedTurn(p1, p1move1, p1move2, p1move3, p1move4, p2, p2move1, p2move2, p2move3, p2move4);
            }
        }


        public void performATwoBasedTurn
        // Naturally more logic will be added here
        (Pokemon p1, Move p1move1, Move p1move2, Move p1move3, Move p1move4,
        Pokemon p2, Move p2move1, Move p2move2, Move p2move3, Move p2move4)
        {
            byte[] choices = coreMechanic();
                // p1 has prio
            if (getp1MovePrio(p1move1, p1move2, p1move3, p1move4, choices) > 
            getp2MovePrio(p2move1, p2move2, p2move3, p2move4, choices))
                {
                System.out.println(p1.getPokeName() + " has priority!");
                    switch (choices[0]) {
                        case (1) -> p1move1.performMove(p1, p2);
                        case (2) -> p1move2.performMove(p1, p2);
                        case (3) -> p1move3.performMove(p1, p2);
                        case (4) -> p1move4.performMove(p1, p2);
                    }
                    
                    switch (choices[1]) {
                        case (1) -> p2move1.performMove(p2, p1);
                        case (2) -> p2move2.performMove(p2, p1);
                        case (3) -> p2move3.performMove(p2, p1);
                        case (4) -> p2move4.performMove(p2, p1);

                    }

                }
                // p2 has prio
            else if (getp1MovePrio(p1move1, p1move2, p1move3, p1move4, choices) < 
            getp2MovePrio(p2move1, p2move2, p2move3, p2move4, choices))
                {
                    System.out.println(p2.getPokeName() + " has priority!");
                    switch (choices[1]) {
                        case (1) -> p2move1.performMove(p2, p1);
                        case (2) -> p2move2.performMove(p2, p1);
                        case (3) -> p2move3.performMove(p2, p1);
                        case (4) -> p2move4.performMove(p2, p1);

                    }

                    switch (choices[0]) {
                        case (1) -> p1move1.performMove(p1, p2);
                        case (2) -> p1move2.performMove(p1, p2);
                        case (3) -> p1move3.performMove(p1, p2);
                        case (4) -> p1move4.performMove(p1, p2);
                    }
                    
                }
            else {
                // p1 is faster
                if ((p1.getSpd() * p1.getSpdMod()) > p2.getSpd() * p2.getSpdMod())
                {
                    System.out.println(p1.getPokeName() + " is faster!");
                    switch (choices[0]) {
                        case (1) -> p1move1.performMove(p1, p2);
                        case (2) -> p1move2.performMove(p1, p2);
                        case (3) -> p1move3.performMove(p1, p2);
                        case (4) -> p1move4.performMove(p1, p2);
                    }

                    switch (choices[1]) {
                        case (1) -> p2move1.performMove(p2, p1);
                        case (2) -> p2move2.performMove(p2, p1);
                        case (3) -> p2move3.performMove(p2, p1);
                        case (4) -> p2move4.performMove(p2, p1);
                    }
                }
                // p2 is faster
                else if ((p1.getSpd() * p1.getSpdMod()) < p2.getSpd() * p2.getSpdMod())
                {
                    System.out.println(p2.getPokeName() + " is faster!");
                    switch (choices[1]) {
                        case (1) -> p2move1.performMove(p2, p1);
                        case (2) -> p2move2.performMove(p2, p1);
                        case (3) -> p2move3.performMove(p2, p1);
                        case (4) -> p2move4.performMove(p2, p1);

                    }

                    switch (choices[0]) {
                        case (1) -> p1move1.performMove(p1, p2);
                        case (2) -> p1move2.performMove(p1, p2);
                        case (3) -> p1move3.performMove(p1, p2);
                        case (4) -> p1move4.performMove(p1, p2);
                    }
                }
            }
        
        }

        public int getp1MovePrio(Move p1move1, Move p1move2, Move p1move3, Move p1move4, byte[] moveChoices) {
                switch (moveChoices[0]){
                    case (1) : return p1move1.getPrio();
                    case (2) : return p1move2.getPrio();
                    case (3) : return p1move3.getPrio();
                    case (4) : return p1move4.getPrio();
                    default:
                    System.out.println("Something went wrong check getp1MovePrio");
                    return 0;
                }
        }
        public int getp2MovePrio(Move p2move1, Move p2move2, Move p2move3, Move p2move4, byte[] moveChoices) {
                switch (moveChoices[1]){
                    case (1) : return p2move1.getPrio();
                    case (2) : return p2move2.getPrio();
                    case (3) : return p2move3.getPrio();
                    case (4) : return p2move4.getPrio();
                    default:
                    System.out.println("Something went wrong check getp2MovePrio");
                    return 0;
                }
        }


        public byte[] coreMechanic(){
            Scanner scammer = new Scanner(System.in);
            byte[] userInputs = new byte[2];
            do {
                System.out.println("Player 1 input:" );
                userInputs[0] = scammer.nextByte();
            }
                while (userInputs[0] < 1 || userInputs[0] > 4);
            
            System.out.printf("Player 1 chose %d%n", userInputs[0]);
            do { 
                System.out.println("Player 2 input:");
                userInputs[1] = scammer.nextByte();
            } while (userInputs[1] < 1 || userInputs[1] > 4);
            System.out.printf("Player 2 chose %d%n", userInputs[1]);
            return userInputs;
        }

}

