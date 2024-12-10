/**
 * RunGarden.java
 * Erik Picazzo
 * CSC 210 FA24 001
 * This program reads a text file where the first two lines specify the 
 * dimensions of a garden, followed by commands to that alter the garden.
 * This file processes the commands and makes use of other classes to render 
 * the inputed garden
 * 
 * possible commands: 
 	PRINT
 	PLANT (row,column) plantType
	GROW num, GROW num (x,y), GROW num plantType GROW num plantClass
	HARVEST, HARVEST (x,y), HARVEST plantType  # Remove vegetables
	PICK, PICK (x,y), PICK plantType           # Remove flowers
	CUT, CUT (x,y), CUT plantType              # Remove tree
	GATHER, GATHER (x,y), GATHER plantType     # Remove fruit
 */
package com.example;

public class parser {
	
	public static void executePlant(String[] command, Garden myGarden) {
		String[] coordinates = command[1].replace("(", "").replace(")","").split(",");
		int xCoord = Integer.parseInt(coordinates[0]);
		int yCoord = Integer.parseInt(coordinates[1]);

		myGarden.plant(xCoord,yCoord, command[2]);
	}
	
	public static void executeGrow(String[] command, Garden myGarden) {
		
		int timesToGrow = Integer.parseInt(command[1]);
		// Grow command of form (GROW #)
		if (command.length == 2) myGarden.growAll(timesToGrow);
		else {
			// Grow command of form (GROW (#,#))
			if (command[2].contains("(")) {	
				String[] coordinates = command[2].replace("(", "").replace(")","").split(",");
				int xCoord = Integer.parseInt(coordinates[0]);
				int yCoord = Integer.parseInt(coordinates[1]);
				myGarden.growAt(xCoord, yCoord, timesToGrow);
			}
			// Grow commands of form (GROW type/category)
			else {
				myGarden.growSpecific(timesToGrow, command[2]);
			}
		}
		
	}
	public static void executeRemove(String[] command, Garden myGarden) {
		// Remove commands = harvest, cut, harvest ,
		// Remove commands of form (Command)
		if (command.length == 1) myGarden.removeCategory(command[0]);
			
		else {
			// Remove command of form (Command (#,#))
			if (command[1].contains("(")) {
				String[] coordinates = command[1].replace("(", "").replace(")","").split(",");
				int xCoord = Integer.parseInt(coordinates[0]);
				int yCoord = Integer.parseInt(coordinates[1]);
				myGarden.removeAt(xCoord, yCoord, command[0]);
			}
			// Remove commands of form (Command type)
			else {
				myGarden.removeType(command[0], command[1]);
			}
		}
		
	}

	

	

	

}
