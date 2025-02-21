import java.util.Random;
public class Pokemon {
    // may need to be changed to public, so extenders have access or getters
    // are enough
    private String PokeName;
    private int HP; // Maybe float, any knowers?
    private int Att;
    private int Def;
    private int SpA;
    private int SpDef;
    private int Spd;

    public Pokemon(String name, int HealthPoints, int Attack, int Defence, int SpecialAttack, int SpecialDefence, int Speed) {
        // Something that should refer to the extenders somehow
        // Maybe this whole code is meaningless, or I need to take args as to pass in stuff.
        // Yup needed to take args for all the values and assign them
        this.PokeName = name;
        this.HP = HealthPoints;
        this.Att = Attack;
        this.Def = Defence;
        this.SpA = SpecialAttack;
        this.SpDef = SpecialDefence;
        this.Spd = Speed;
    }

    // Ville gerne kunne få adgang i metoder til alle disse fields
    // I sidste ende blev det all på nær Spd.

    public String getPokeName() {
        return this.PokeName;
    }
    // For each move with stat change introduced I'll need to get a setter
    public int getHP() {
        return this.HP;
    }
    public int getAtt() {
        return this.Att;
    }
    public int getDef() {
        return this.Def;
    }
    public int getSpA() {
        return this.SpA;
    }
    public int getSpDef() {
        return this.SpDef;
    }
    public int getSpd() {
        return this.Spd;
    }

    // Jeg var nødt til at have at have en setter for at kunne ændre på HP værdien midt kamp
    public void setHP(int changeHP)
    {
        this.HP = changeHP;
    }
    public void setDef(int changeDef){
        this.Def = changeDef;
    }
    public boolean checkFainted(Pokemon current) { // check the Pokemon in parameter if their HP 0 or less
        if (current.getHP() <= 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public int randomNum(int range){
        Random random = new Random();
        return random.nextInt(range) + 1; // Returns a random number from 1 - int range.
    }
}
