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

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private boolean hasMoved;
	private boolean fight;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

    /**
     * Update critter's energy and then update it's position by 1 step in chosen direction
     * @param direction integer direction of motion
     */
	protected final void walk(int direction) {
        this.energy = this.energy - Params.walk_energy_cost;
        if(hasMoved) return;
        int x =this.x_coord;
        int y= this.y_coord;
        /*Changing x coordinate */
        if (direction==0 || direction==1 || direction==7) {
            this.x_coord++;
        }
        else if (direction==3 || direction==4 || direction==5) {
            this.x_coord--;
        }

        /*Changing y coordinate */
        if (direction==1 || direction==2 || direction==3) {
            this.y_coord--;
        }
        else if (direction==5 || direction==6 || direction==7) {
            this.y_coord++;
        }
        
        if(this.x_coord >= Params.world_width) this.x_coord = this.x_coord - Params.world_width;
        if(this.x_coord < 0) 				  this.x_coord = this.x_coord + Params.world_width;
        if(this.y_coord >= Params.world_height)this.y_coord = this.y_coord - Params.world_height;
        if(this.y_coord < 0) 				  this.y_coord = this.y_coord + Params.world_height;
        this.hasMoved = true;
        if(fight){
        	for(Critter c: population){
        		if(c.x_coord == this.x_coord && c.y_coord == this.y_coord && c.energy >0){
        			this.x_coord = x;
        			this.y_coord = y;
        			break;
        		}
        	}
        }
	}

    /**
     * Update's critter's energy and updates it's position by 2 steps in chosen direction
     * @param direction integer direction of motion. Direction increases in counter clockwise direction
     */
	protected final void run(int direction) {
		this.energy = this.energy - Params.run_energy_cost;
		if(hasMoved) return;
		int x = this.x_coord;
		int y = this.y_coord;
		 /*Changing x coordinate */
        if (direction==0 || direction==1 || direction==7) {
            this.x_coord+=2;
        }
        else if (direction==3 || direction==4 || direction==5) {
            this.x_coord-=2;
        }

        /*Changing y coordinate */
        if (direction==1 || direction==2 || direction==3) {
            this.y_coord-=2;
        }
        else if (direction==5 || direction==6 || direction==7) {
            this.y_coord+=2;
        }
        if(this.x_coord >= Params.world_width) this.x_coord = this.x_coord - Params.world_width;
        if(this.x_coord < 0) 				  this.x_coord = this.x_coord + Params.world_width;
        if(this.y_coord >= Params.world_height)this.y_coord = this.y_coord - Params.world_height;
        if(this.y_coord < 0) 				  this.y_coord = this.y_coord + Params.world_height;
        this.hasMoved = true;
        if(fight){
        	for(Critter c: population){
        		if(c.x_coord == this.x_coord && c.y_coord == this.y_coord && c.energy >0){
        			this.x_coord = x;
        			this.y_coord = y;
        			break;
        		}
        	}
        }
		
	}

	/**
	 * Called by parent Critter. Function will take newly created Critter and set its position to be adjacent to
     * the parent's position. It's exact position is determined by direction parameter
	 * @param offspring newly created Critter
	 * @param direction integer direction. Determines where next to parent the offspring should be placed
     */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
            return;
        }
        offspring.energy = (this.energy/2);
        this.energy = (this.energy/2)+1;

        offspring.x_coord = this.x_coord;
        offspring.y_coord = this.y_coord;

        offspring.walk(direction);
        offspring.energy = offspring.energy + Params.walk_energy_cost; //walk() takes off energy which we don't want
                                                                       //to take from a newborn critter
		offspring.hasMoved = false;
        babies.add(offspring);

	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name the name of the critter class being created
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        Critter c;
		try {
			Class newCritter = Class.forName(myPackage + "." + critter_class_name);
            c = (Critter) newCritter.newInstance();
            int x = getRandomInt(Params.world_width);
            int y = getRandomInt(Params.world_height);
            c.x_coord = x;
            c.y_coord = y;
            c.energy = Params.start_energy;
            population.add(c);
            c.hasMoved = false; 
            c.fight = false;
		}
		catch (ClassNotFoundException | InstantiationException |IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
        
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> instances = new java.util.ArrayList<Critter>();
		try {
			Critter c;
			Class<?> newCritter = Class.forName(myPackage + "." + critter_class_name);
			c = (Critter) newCritter.newInstance();
			for(int i=0; i<population.size(); i++){
				if(population.get(i) != null && newCritter.isInstance(population.get(i))){
					instances.add(population.get(i));
				}
			}
		}
		catch (ClassNotFoundException | InstantiationException |IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		return instances;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}

		/**
		 * Returns whether a Critter is dead
		 * @return
         */
		protected boolean isDead() {
			return super.energy <=0;

		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}

    /**
     * Step 1 - Invoke doTimeStep() for every living critter. Encounters are handled only after
     *          every critter has moved
     * Step 2 - Handle encounters
     * Step 3 - Update rest energy for all critters
     * Step 4 - Remove dead critters
     * Step 5 - Add babies to general population
     */
	public static void worldTimeStep() {

        /*Invoking timeStep for all critters */
        for (int i = 0; i < population.size(); i++) {
        	population.get(i).hasMoved= false;
            population.get(i).doTimeStep();
            population.get(i).fight = false;
        }

        for(int i = 0; i < population.size(); i++){
        	for(int j = i+1; j < population.size(); j++){
        		if(population.get(i).x_coord ==population.get(j).x_coord && population.get(i).y_coord == population.get(j).y_coord){
        			handleEncounter(population.get(i),population.get(j));
        		}
        	}
        }
        
        /*Subtracting rest energy cost */
        for (Critter c: population) {
            c.energy = c.energy - Params.rest_energy_cost;
        }

        /*Removing dead critters */
        for (int i =0; i<population.size(); i++) {
            if (population.get(i).energy <=0) {
                population.remove(i);
                i--;
            }
        }
    //    System.out.println(population.size());
        
        /*Adding babies to general population */
        for (Critter c: babies) {
            population.add(c);
        }
        babies.clear();

   //     Adding algae 
        for (int i= 0; i < Params.refresh_algae_count; i++) {
            Critter c = new Algae();
            c.energy = Params.start_energy;
            c.x_coord = getRandomInt(Params.world_width);
            c.y_coord = getRandomInt(Params.world_height);
            population.add(c);
        }
        
	}

    /**
     * Will handle encounters between 2 critters.
     * First calls each critter's fight() method. If none of the critters move, roll random numbers and decide
     * fate of encounter
     * @param a one critter participant in the encounter
     * @param b the other participant
     */
	private static void handleEncounter(Critter a, Critter b){
		a.fight = true;
		b.fight = true;
		boolean a_fight = a.fight(b.toString());
		boolean b_fight = b.fight(a.toString());
		int a_roll=0;
		int b_roll=0;
		if(a.x_coord == b.x_coord && a.y_coord == b.y_coord && a.energy>0 && b.energy>0){
			if(a_fight) a_roll = getRandomInt(a.energy);
			if(b_fight) b_roll = getRandomInt(b.energy);
			if(a_roll >= b_roll){
				a.energy = a.energy + (b.energy/2);
				b.energy = 0;
			}
			else{
				b.energy = b.energy + (a.energy/2);
				a.energy = 0;
			}
		}
		

	}

    /**
     * Displays the world and all living critters, including Algae. Calls each critter's toString method
     */
    public static void displayWorld() {
		int rows = Params.world_height;
		int columns = Params.world_width;
		String[][] display = new String[rows + 2][columns + 2];
		display[0][0] = "+";
		display[rows + 1][0] = "+";
		display[0][columns+1] = "+";
		display[rows+1][columns+1] = "+";
		
		for(int i = 1; i<= columns; i++) {
			display[0][i]="-";
			display[rows + 1][i]="-";
		}
		for(int i = 1; i<= rows; i++) {
			display[i][0]="|";
			display[i][columns+1]="|";
		}

		for(Critter c: population){
			display[c.y_coord + 1][c.x_coord+1] = c.toString();
		}
		
		for(int i=0; i<rows+2; i++){
			for(int j=0; j<columns+2; j++){
				if((display[i][j] == null)) 
					System.out.print(" ");
				else	
					System.out.print(display[i][j]);
			}
			System.out.print("\n");
		}
        
		
	}
}
