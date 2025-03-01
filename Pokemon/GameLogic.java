/**
Low key easy at lave alt til en kommentar. Bare kigge ovenfor denne linje, og den nederste
linje i koden.
public class GameLogic
{
    private int DMGgiven;
    public int damageCalc(int AttackType, int AttackpercBP, int DefenseOpp)
    // Parameter AttackType is the basepower of the Pokemons' attack for the given move, this can either be physical or special
    // Likewise AttackpercBP is the power of the move, where e.g. fire blast with a BP of 110, is shrunk down to 1.1
    {
        // Naturally this could introduce elements of crit, supereffectiveness, stab, accuracy of the move.
        DMGgiven = (AttackType-DefenseOpp/2) * AttackpercBP/100; // remember to just write basepower in
        if (DMGgiven < 1)
        {
            return 1; // Just a lil fail-safe if the dmg is way too low.
            // make this return as answer.
        }
        else {
            return DMGgiven;
        }
    }
    public void useWaterGun(Pokemon attacker, Pokemon defender){
        DMGgiven = damageCalc(attacker.getSpA(), 40, defender.getSpDef());
        System.out.println("Watergun what a chad!");
        System.out.println(attacker.getPokeName() + " used watergun " + defender.getPokeName() +
                " takes damage and loses " + DMGgiven + " HP");
        defender.setHP(defender.getHP() - DMGgiven);
        System.out.println(defender.getPokeName() + " HP is now down to " + defender.getHP());

        if (checkFainted(defender)){ // takes defender as parameter
            System.out.println(defender.getPokeName() + " gets wet, " + defender.getPokeName() + " freezes and faints.");
        }
    }
    public void useEarthQuake(Pokemon attacker, Pokemon defender) {
        DMGgiven = damageCalc(attacker.getAtt(), 100, defender.getDef());
        System.out.println(attacker.getPokeName() + " used Earthquake " + defender.getPokeName() + " takes damage and loses "
                + DMGgiven + " HP");

        defender.setHP(defender.getHP() - DMGgiven);
        System.out.println(defender.getPokeName() + " HP is now down to " + defender.getHP());

        if (checkFainted(defender)){ // takes defender as parameter
            System.out.println(defender.getPokeName() + " was sent 6 feet under, " + defender.getPokeName() + " has fainted.");
        }
    }
    public void useRoost(Pokemon attacker, Pokemon attackerbase) {
        if (attacker.getHP() + (attackerbase.getHP()/2) > attackerbase.getHP())
        // If the else statements value exceeds the BaseHP of Charizard i.e. over max HP
        // Then simply fill Charizards HP to full
        {
            int HPvalue = attackerbase.getHP(); // made some random variable to store an int which can be set into a parameter
            attacker.setHP(HPvalue);
            System.out.println(attacker.getPokeName() + " has used roost and healed " + HPvalue + " HP!");
        }
        else
        {
            int HPvalue = attackerbase.getHP()/2; // Roost restores half max HP
            attacker.setHP(HPvalue);
            System.out.println(attacker.getPokeName() + " has used roost and healed " + HPvalue + " HP!");
            // Now what exactly to use to refer to base as parameter idk.
        }
    }
    public void useFlameThrower(Pokemon attacker, Pokemon defender)
    {

        DMGgiven = damageCalc(attacker.getSpA(), 90, defender.getSpDef());
        System.out.println(attacker.getPokeName() + " used flamethrower " + defender.getPokeName() + " takes damage and loses "
                + DMGgiven + " HP");

        defender.setHP(defender.getHP() - DMGgiven);
        System.out.println(defender.getPokeName() + " HP is now down to " + defender.getHP());

        if (checkFainted(defender)){ // takes defender as parameter
            System.out.println(defender.getPokeName() + " got burnt to a crisp, " + defender.getPokeName() + " has fainted.");
        }
    }
}
*/