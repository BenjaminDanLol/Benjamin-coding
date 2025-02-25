public class Flamethrower extends Move {
    public Flamethrower() {
        super("Flamethrower", 95, 100, true, false, 
        "Fire", "-", true, "Fire", 10, 
        10);
        }
    // For at kalde på denne smukke function, så er vi nødt til at vide hvordan vi referer til modstanderens typings.
    private boolean willBurn(Pokemon victimType) {
        Typechart Statuscheck = new Typechart(victimType.getType1(), victimType.getType2());
        return (randomSuccess(10) && Statuscheck.ShouldApplyStatus("Fire"));
    }
}