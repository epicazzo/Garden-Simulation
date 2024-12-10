package com.example;
public class Fruit extends Plant{
	private String color = "Red";
	public Fruit(String type) {
		super("fruit", type);
		setInitialPoint();
		setColor(type);
	}
	
	private void setColor(String type){
		switch (type){
			case "melon":
				color = "darkorange";
				break;
			case "honeydewmelon":
				color = "greenyellow";
				break;
			case "kiwi":
				color = "CHARTREUSE";
				break;
			case "eggplant":
				color = "purple";
				break;
		}
	}
	
	private void setInitialPoint() {
			representation[4][0] = icon;
			representation[4][4] = icon;
	}

	public void grow(int num) {
		for (int i = 0; i<num; i++) {
			if (stage == 1){ // grow to stage 2
				representation[3][1] = icon; representation[3][3] = icon;			
				stage += 1;
			}
			else if(stage == 2) { // grow to stage 3
				representation[2][0] = icon; representation[2][4] = icon;
				stage += 1;
			}
			else if(stage == 3) { // grow to stage 4
				representation[1][1] = icon; representation[1][3] = icon;
				stage += 1;
				
			}
			else if(stage == 4) { // grow to stage 5
				representation[0][0] = icon; representation[0][4] = icon;
				stage += 1;
			}
			else break; // if stage is 5, don't grow
		}
	}

	public String getColor(){
		return color;
	}
}
