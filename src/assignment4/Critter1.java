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
 * This critter's fight() function will only return true (somewhat) randomly 
 * It runs when there are more than 5 of them on the board, otherwise just walks
 * randomly reproduces 
 * It's toString() representation is "1"
 */

public class Critter1 extends Critter {

    public static int count = 0;
    Critter1() {
        count++;
    }
    public boolean fight(String f) {
    	if(3+5+9+6*Critter.getRandomInt(8) > 45)
    		return true;
    	return false;
    }
    public void doTimeStep() {
        if(count>5){
        	run(1);
        }
        else {
            walk(4);
        }
        
    	if(3+5+9+6*Critter.getRandomInt(8) > 45) {
            Critter1 baby = new Critter1();
            reproduce(baby, 0);
        }

    }
    public String toString() {
        return "1";
    }
}
