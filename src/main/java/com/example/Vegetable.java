package com.example;

public class Vegetable extends Plant{
	private String color = "Green";
	public Vegetable(String type) {
		super("vegetable", type);
		setInitialPoint();
		setColor(type);
	}

	private void setColor(String type){
		switch (type){
			case "garlic":
				color = "black";
				break;
			case "zucchini":
				color = "darkgreen";
				break;
			case "tomato":
				color = "red";
				break;
			case "yam":
				color = "darkorange";
				break;
			case "lettuce":
				color = "greenyellow";
				break;			
		}
	}

	private void setInitialPoint() {
		representation[0][2] = icon;
	}
	
	@Override
	public void grow(int num) {
		// TODO Auto-generated method stub
		for (int i = 0; i<num; i++) {
			if (stage==5) break;
			representation[stage][2] = icon;
			stage+=1;
		}
		
	}

	public String getColor(){
		return color;
	}
	
}
