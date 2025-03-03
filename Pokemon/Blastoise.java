
public class Blastoise extends Pokemon {
    int DefenseOpp;
    int BPpower;
    int totalDMGSpA = (this.getSpA()-DefenseOpp/2) * BPpower/100;
    public Blastoise() {
        super("Blastoise", 362, 291, 328, 295, 339, 280, 100, "Water");
    }
}