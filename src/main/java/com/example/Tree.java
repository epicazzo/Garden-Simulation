package com.example;

public class Tree extends Plant{
	private String color = "Brown";
	public Tree(String type) {
		super("tree", type);
		setInitialPoint();  // Set the starting position for a Tree
		setColor(type);
	}

	private void setColor(String type){
		switch (type){
			case "oak":
				color = "Maroon";
				break;
			case "willow":
				color = "lightgreen";
				break;
			case "banana":
				color = "yellow";
				break;
			case "coconut":
				color = "tan";
				break;
			case "pine":
				color = "darkgreen";
				break;			
		}
	}

	private void setInitialPoint() {
		representation[4][2] = icon;
	}
	
	public void grow(int num) {
		// Trees grow vertically from the 
		for (int i = 0; i<num; i++) {
			if (stage==5) break; //
			representation[4-stage][2] = icon;
			stage+=1;
		}
		
	}
	
	public String getColor(){
		return color;
	}
}
