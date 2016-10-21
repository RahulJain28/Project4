/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Aditya Kharosekar
 * amk3587
 * 16465
  * Rahul Jain	
 * rj8656
 *16470
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;    // scanner connected to keyboard input, or input file
    private static String inputFile;    // input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;    // if test specified, holds all console output
    private static String myPackage;    // package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;    // if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     *
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     *             and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        String input = null;
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        while(true){
        	try {
        		System.out.print("critters>");
        		input = kb.nextLine().trim();
        		String[] words = input.split(" ");
        		/*if command is invalid */
        		if (!words[0].equals("quit") && !words[0].equals("show") && !words[0].equals("seed") && !words[0].equals("step") && !words[0].equals("make") && !words[0].equals("stats")) {
        			System.out.println("invalid command: " + input);
        			continue;
        		}

        		/*if command is valid but maybe some extra text */
        		if (words[0].equals("quit")) {
        			break;
        		} else if (words[0].equals("show") && words.length == 1) {
        			Critter.displayWorld();
        			System.out.println();
        		} else if (words[0].equals("seed") && words.length == 2) {
        			long num = Integer.parseInt(words[1]);
        			Critter.setSeed(num);
        			System.out.println();
        		} else if (words[0].equals("step") && words.length == 1) { //command is "step". No number argument
        			Critter.worldTimeStep();
        		} else if (words[0].equals("step") && words.length == 2) {
        			int num = Integer.parseInt(words[1]);
        			for (int i = 0; i < num; i++) {
        				Critter.worldTimeStep();
        			}
        		} else if (words[0].equals("make") && words.length == 2) {
        			String name = words[1];
        			Critter.makeCritter(name);
        		} else if (words[0].equals("make") && words.length == 3) {
        			String name = words[1];
        			int num = Integer.parseInt(words[2]);
        			for (int i = 0; i < num; i++) {
        				Critter.makeCritter(name);
        			}
        		} else if (words[0].equals("stats") && words.length == 2) {
        			java.util.List<Critter> result = Critter.getInstances(words[1]);
        			Critter c;
        			Class<?> newCritter = Class.forName(myPackage + "." + words[1]);
        			c = (Critter) newCritter.newInstance();
        			Method m = newCritter.getMethod("runStats", List.class);
        			m.invoke(null, result);
        		}
        		else {
        			System.out.println("error processing: " + input);
        		}
        		System.out.flush();
        	} catch (Exception e) {
        		System.out.println("error processing: " + input);
        	}
        }
        System.out.flush();
    }
}
