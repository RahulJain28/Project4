/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Aditya Kharosekar
 * amk3587
 * 16465
 * Rahul Jain
 * rj8656
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4;

/**
 * Created by Aditya Kharosekar
 * This critter's fight() function will always return true. It will fight with any Critter.
 * It randomly decides a direction to walk in.
 * It's toString() representation is "4"
 * It will always reproduce and its baby will be placed in the same direction that the parent was moving
 */
public class Critter4 extends Critter {

    public void doTimeStep() {
        int direction = getRandomInt(8);
        walk(direction);
        Critter4 baby = new Critter4();
        reproduce(baby, direction);
    }
    public boolean fight(String fightStatus) {
        return true;
    }
    public String toString() {
        return "4";
    }
}
