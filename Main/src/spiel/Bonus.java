package spiel;

/**
 * Klasse, welche //TODO: Beschreibung der Klasse
 *
 * @author TPE_UIB_01
 */
public class Bonus {

    private int bonus;

    public Bonus(int bonus) {
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus() {
        if (bonus-- != 0) {
            this.bonus--;
        }
    }

    public int pop() {
        int bonus = this.getBonus();
        this.setBonus();
        return bonus;
    }
}
