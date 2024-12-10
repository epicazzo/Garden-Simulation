package com.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
public class Garden {
	private int columnCount, rowCount;
	private ArrayList<ArrayList<Plant>> representation = new ArrayList<ArrayList<Plant>>();
	private HashMap<String, ArrayList<String>> plantCategories = new HashMap<String, ArrayList<String>>();
	
	public Garden(int rows, int columns) {
		if (columns <= 16) columnCount = columns;
		else{
			App.displayCommand("Too many plot columns\n");
			System.exit(0);
		}
		rowCount = rows;
		formGarden();
		createPlantCategories();
		
	}
	
	private void formGarden() {
		// Create 2d ArrayList to hold plants
		for (int i = 0; i<rowCount; i++) {
			ArrayList<Plant> row = new ArrayList<Plant>();
			for (int j = 0; j<columnCount; j++) {
				row.add(null);
			}
			representation.add(row);
		}
	}
	
	private void createPlantCategories() {
		// Map each possible plant type to a category
		plantCategories.put("flower", new ArrayList<String>(
			Arrays.asList("iris", "lily", "rose", "daisy", "tulip", "sunflower")));
		
		plantCategories.put("tree", new ArrayList<String>(
			Arrays.asList("oak", "willow", "banana", "coconut", "pine")));
		
		plantCategories.put("vegetable", new ArrayList<String>(
			Arrays.asList("garlic", "zucchini", "tomato", "yam", "lettuce")));
		
		plantCategories.put("fruit", new ArrayList<String>(
			Arrays.asList("melon", "honeydewmelon", "kiwi", "eggplant")));
	}
	
	public Plant getPlantAt(int x, int y) {
		return representation.get(x).get(y);
	}
	
	public void setPlantAt(int x, int y, String category, String type) {
		if (category.equals("vegetable")) 
			representation.get(x).set(y, new Vegetable(type));
		
		else if (category.equals("tree")) 
			representation.get(x).set(y, new Tree(type));
		
		else if (category.equals("flower")) 
			representation.get(x).set(y, new Flower(type));
		
		else if (category.equals("fruit")) 
			representation.get(x).set(y, new Fruit(type));
		
		else if (category.equals("null"))
			representation.get(x).set(y, null);
	}
	
	public boolean validatePoint(int x, int y) {
		return (x >= 0 && x <= rowCount-1) && (y >= 0 && y <= columnCount-1);
	}
	
	public boolean isOccupied(int x, int y) {
		return representation.get(x).get(y) != null;
	}
	
	public void plant(int x, int y, String type) {
		App.displayCommand("> PLANT (" + x + "," + y + ") " + type + "\n");
		if (validatePoint(x,y) == true) {
			if (isOccupied(x, y)) App.clearPlot(x, y);
			
			if (plantCategories.get("flower").contains(type)) 
				setPlantAt(x,y,"flower", type);
			
			else if (plantCategories.get("tree").contains(type)) 
				setPlantAt(x,y,"tree", type);
			
			else if (plantCategories.get("vegetable").contains(type)) 
				setPlantAt(x,y,"vegetable", type);		
			
			else if (plantCategories.get("fruit").contains(type)) 
				setPlantAt(x,y,"fruit", type);
			
			// Place the plant's representation in the screen
			App.placePlant(x, y, getPlantAt(x,y));
		}
	}	
	
	public void growAll(int num) {
		App.displayCommand("> GROW " + num + "\n");
		for (int i = 0; i<rowCount; i++) {
			for (int j = 0; j<columnCount; j++) {
				Plant currentPlant = getPlantAt(i,j);
				if (currentPlant != null) {
					currentPlant.grow(num);
					// Update the screen with the current status of the plant
					App.placePlant(i, j, currentPlant);
				}
			}
		}
	}
	
	public void growAt(int x, int y, int num) {
		App.displayCommand("> GROW " + num + " (" + x + "," + y + ")\n");
		if (validatePoint(x, y) == true && isOccupied(x, y)) {
			getPlantAt(x,y).grow(num);
			// Update the screen with the current status of the plant
			App.placePlant(x, y, getPlantAt(x,y));
		}
		else {
			App.displayCommand("Can't grow there.\n");
		}
	}
	
	public void growSpecific(int num, String specification) {
		App.displayCommand("> GROW " + num + " " + specification + "\n");
		//Grow based on class(i.e. Vegetable, Tree, Flower etc.)
		if (plantCategories.keySet().contains(specification)) {
			for (int i = 0; i<rowCount; i++) {
				for (int j = 0; j<columnCount; j++) {
					Plant currentPlant = getPlantAt(i,j);
					if (currentPlant != null && currentPlant.getCategory().equals(specification)) {
						currentPlant.grow(num);
						// Update the screen with the current status of the plant
						App.placePlant(i, j, currentPlant);
					}
				}
			}
		}
		
		else{ //Grow based on type (such as banana, oak etc.)
			for (int i = 0; i<rowCount; i++) {
				for (int j = 0; j<columnCount; j++) {
					
					Plant currentPlant = getPlantAt(i,j);
					if (currentPlant != null && currentPlant.getType().equals(specification)) {
						currentPlant.grow(num);
						// Update the screen with the current status of the plant
						App.placePlant(i, j, currentPlant);
					}
				}
			}
			
		}
	}
	
	public void removeCategory(String specification) {
		App.displayCommand("> " + specification.toUpperCase() + "\n");
		String typeToRemove;
		
		//Find which category is being removed based off the command
		if (specification.equals("harvest")) typeToRemove = "vegetable";
		else if (specification.equals("pick")) typeToRemove = "flower";
		else if (specification.equals("cut")) typeToRemove = "tree";
		else if (specification.equals("gather")) typeToRemove = "fruit";
		else typeToRemove = "tree";
		
		for (int i = 0; i<rowCount; i++) {
			for (int j = 0; j<columnCount; j++) {
				
				Plant currentPlant = representation.get(i).get(j);
				if (currentPlant != null && currentPlant.getCategory() == typeToRemove){
					setPlantAt(i, j, "null", "null");
					// Reset the plot the plant occupied
					App.clearPlot(i, j);
				}
			}
		}
	}
	
	public void removeType(String specification, String type) {
		App.displayCommand("> " + specification.toUpperCase() +  " " + type+"\n");
		for (int i = 0; i<rowCount; i++) {
			for (int j = 0; j<columnCount; j++) {
				
				Plant currentPlant = representation.get(i).get(j);
				if (currentPlant != null && currentPlant.getType().equals(type)){
					setPlantAt(i, j, "null", "null");
					// Reset the plot the plant occupied
					App.clearPlot(i, j);
				}
			}
		}
	}
	
	public void removeAt(int x, int y, String specification) {
		App.displayCommand("> " + specification.toUpperCase() + " (" + + x + "," + y + ")\n");
		String typeToRemove;
		//Find which category is being removed based off the command
		if (specification.equals("harvest")) typeToRemove = "vegetable";
		else if (specification.equals("pick")) typeToRemove = "flower";
		else if (specification.equals("cut")) typeToRemove = "tree";
		else if (specification.equals("gather")) typeToRemove = "fruit";
		else typeToRemove = "tree";

		if (validatePoint(x,y) == true && 
			isOccupied(x,y) == true &&
			representation.get(x).get(y).getCategory() == typeToRemove) {
			
			setPlantAt(x, y, "null", "null");
			//Reset the plot the plant occupied
			App.clearPlot(x, y);
		}
				
		else App.displayCommand("Can't " + specification + " there.\n");
		
	}
	
	public void print() {
		for (ArrayList<Plant> r : representation) {
			System.out.println(r);
		}
	}
}
