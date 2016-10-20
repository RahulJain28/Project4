package assignment4;

/**
 * Created by Aditya Kharosekar
 * This critter's fight() function will only return true against algae.
 * It only runs, never walks, and it only moves in 1 direction - west.
 * Every other instance of Critter3 will reproduce and its babies will always move west as well.
 * It's toString() representation is "3"
 */

public class Critter3 extends Critter {

    public static int count = 0;
    Critter3() {
        count++;
    }
    public boolean fight(String fightStatus) {
        if (fightStatus.equals("@")) {
            return true;
        }
        return false;
    }
    public void doTimeStep() {
        run(0);
        if (count%2==0) {
            Critter3 baby = new Critter3();
            reproduce(baby, 0);
        }

    }
    public String toString() {
        return "3";
    }
}
