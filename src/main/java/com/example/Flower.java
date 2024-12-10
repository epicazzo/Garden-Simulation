package com.example;
public class Flower extends Plant{
	
	private String color;
	public Flower(String type) {
		super("flower", type);
		// Set the starting position for a flower
		setInitialPoint();
		System.out.println(type);
		setColor(type);

	}
	private void setColor(String type){
		switch (type){
			case "iris":
				color = "purple";
				break;
			case "lily":
				color = "deeppink";
				break;
			case "rose":
				color = "red";
				break;
			case "daisy":
				color = "darkorange";
				break;
			case "tulip":
				color = "aqua";
				break;
			case "sunflower":
				color = "Gold";
				break;
				
		}
	}

	private void setInitialPoint() {
			representation[2][2] = icon;
	}
		
	@Override
	public void grow(int num) {
		for (int i = 0; i<num; i++) {
			if (stage == 1){ // grow to stage 2
				representation[1][2] = icon; representation[3][2] = icon;
				representation[2][1] = icon; representation[2][3] = icon;
				
				stage += 1;
			}
			else if(stage == 2) { // grow to stage 3
				representation[0][2] = icon; representation[4][2] = icon;
				representation[1][1] = icon; representation[1][3] = icon;
				representation[2][0] = icon; representation[2][4] = icon;
				representation[3][1] = icon; representation[3][3] = icon;
				stage += 1;
			}
			else if(stage == 3) { // grow to stage 4
				representation[0][1] = icon; representation[0][3] = icon;
				representation[1][0] = icon; representation[1][4] = icon;
				representation[3][0] = icon; representation[3][4] = icon;
				representation[4][1] = icon; representation[4][3] = icon;
				stage += 1;
				
			}
			else if(stage == 4) { // grow to stage 5
				representation[0][0] = icon; representation[0][4] = icon;
				representation[4][0] = icon; representation[4][4] = icon;
				stage += 1;
			}
			else break; // if stage is 5, don't grow
			
		}
		
	}

	public String getColor(){
		return color;
	}
	
}
