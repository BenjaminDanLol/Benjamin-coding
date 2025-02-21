
public class Blastoise extends Pokemon {
    int DefenseOpp;
    int BPpower;
    int totalDMGSpA = (this.getSpA()-DefenseOpp/2) * BPpower/100;
    public Blastoise() {
        super("Blastoise", 362, 291, 328, 295, 339, 280);
    }
    public void useWaterGun(Pokemon defender){
        BPpower = 40;
        DefenseOpp = defender.getSpDef();
        totalDMGSpA += totalDMGSpA/4; // Stab
        System.out.println("Watergun what a chad!");
        System.out.println(this.getPokeName() + " used watergun " + defender.getPokeName() +
                " takes damage and loses " + totalDMGSpA + " HP");
        defender.setHP(defender.getHP() - totalDMGSpA);
        System.out.println(defender.getPokeName() + " HP is now down to " + defender.getHP());

        if (checkFainted(defender)){ // takes defender as parameter
            System.out.println(defender.getPokeName() + " gets wet, " + defender.getPokeName() + " freezes and faints.");
        }
    }
    public void useIronDefense(){
        if (getDef() < 328 * 6) {
            this.setDef(getDef() + 328); // Den bør tage den nuværende Def stat og pluse den med base
        }
        else {
            System.out.println("You can't boost your' Def over 6* your base");
        }
    }
    public void useHydroPump(Pokemon defender){
        System.out.println("Cool");
        int random = randomNum(100); // random is a random int between 1-100.
        if (random <= 80){ // If random is less than or equal 80
            BPpower = 110;
            DefenseOpp = defender.getSpDef();
            totalDMGSpA += totalDMGSpA/4; // Stab
            System.out.println(this.getPokeName() + " used hydro pump " + defender.getPokeName() +
                    " takes damage and loses " + totalDMGSpA + " HP");
            defender.setHP(defender.getHP() - totalDMGSpA);
            System.out.println(defender.getPokeName() + " HP is now down to " + defender.getHP());

            if (checkFainted(defender)){ // takes defender as parameter
                System.out.println(defender.getPokeName() + " gets flooded, " + defender.getPokeName() + " drowns and faints.");
            }

        }
        else {
            System.out.println("You missed!");
        }
    }

}