package com.example;

public abstract class Plant {
	protected String category;
	protected String type;
	//character that represents the specific plant
	protected String imageName;	
	protected char icon = 'x';
	// stage of growth
	protected int stage;
	//List of coordinates that the plant occupies in its plot
	protected char[][] representation = new char[][] {
		{'.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.'}
	};
	
	
	public Plant(String category, String type) {
		this.category = category;
		this.type=type;
		imageName = "file:images/" + type + ".jpeg";
		stage = 1;
	}
	
	
	public String getType() {
		return type;
	}
	
	public String getCategory() {
		return category;
	}
	
	public char[][] getRepresentation(){
		return representation;
	}
	
	public String getImageFileName(){
		return imageName;
	}

	public abstract void grow(int num);
	

	public  String toString() {
		return category + " of type " + type + " at stage " + stage;
	}
	
	public void print() {
		for (int i = 0; i < representation.length; i++) {
            // Loop through each element in the row
            for (int j = 0; j < representation[i].length; j++) {
                // Print each character without a new line
                System.out.print(representation[i][j]);
            }
            // Move to the next line after printing all elements in a row
            System.out.println();
        }
	}

	public abstract String getColor();
}
	
