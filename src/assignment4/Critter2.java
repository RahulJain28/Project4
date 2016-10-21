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
 * Created by Rahul Jain
 * It will try to walk away from every fight, as well as unsuccessfully run. Then if it cant walk, it will fight
 * It randomly decides a direction to run in.
 * It's toString() representation is "2"
 * It will always reproduce and its baby will be placed in a random direction 
 */
public class Critter2 extends Critter{
    public void doTimeStep() {
        int direction = getRandomInt(8);
        run(direction);
        Critter4 baby = new Critter4();
        reproduce(baby, getRandomInt(8));
    }
    public boolean fight(String fightStatus) {
    	walk(getRandomInt(8));
    	run(getRandomInt(8));
        return true;
    }
    public String toString() {
        return "2";
    }
}
