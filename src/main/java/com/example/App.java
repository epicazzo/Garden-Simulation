/*
 * CSC 210 Fall 2024
 * Project 7 -- Garden GUI output
 * Erik Picazzo
 * 
 * This program utilizes java fx to simulate a garden through a GUI.
 * To use this program, pass the dimensions of the garden through 
 * the command line with the number of rows first followed by columns
 * 
 * I.E. "args": "3 3"
 * 
 * From there input commands in the text feild of the GUI
 * 
 * possible commands: 
 *	PRINT
 *	PLANT (row,column) plantType
 *	GROW num, GROW num (x,y), 
 *  GROW num plantType GROW num plantClass
 *	HARVEST, HARVEST (x,y), HARVEST plantType  #Remove vegetables
 *	PICK, PICK (x,y), PICK plantType           # Remove flowers
 *	CUT, CUT (x,y), CUT plantType              # Remove tree
 * 	GATHER, GATHER (x,y), GATHER plantType     # Remove fruit
 */
package com.example;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import javafx.scene.control.Label;

public class App extends Application {

    private final static int TEXT_SIZE = 120;
    private final static int CELL_SIZE = 40;

    private static int SIZE_ACROSS;
    private static int SIZE_DOWN;

    private static GraphicsContext gc;
    private static TextArea outputArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
        Parameters params = getParameters();
        java.util.List<String> args = params.getRaw();
        int rows = Integer.valueOf(args.get(0));
        int cols = Integer.valueOf(args.get(1));


        SIZE_ACROSS = cols * 5 * CELL_SIZE;
        SIZE_DOWN = rows * 5 * CELL_SIZE;

		Garden myGarden = new Garden(rows,cols);

        HBox inputSection = createInputArea(myGarden);
        outputArea = new TextArea();
        
        gc = setupStage(primaryStage, SIZE_ACROSS, SIZE_DOWN,
                outputArea, inputSection);

        initializeStage(gc);
        primaryStage.show();
    }

    /**
     * Creates the area that the user can type commands into
     * @param myGarden
     * @return
     *                  Hbox containing the input section
     */
    
    public HBox createInputArea(Garden myGarden){
        // Middle section: TextField and Submit Button
        Label inputLabel = new Label("Enter a command");
        inputLabel.setStyle("-fx-font-size: 16px;");
        TextField inputField = new TextField();
        inputField.setPromptText("Enter your command here");
        Button submitButton = new Button("Submit");

        inputField.setOnAction(e -> {
            String command = inputField.getText();
            processCommand(command, myGarden);
            inputField.clear();
        });

        submitButton.setOnAction(event -> {
            String command = inputField.getText();
            processCommand(command, myGarden);
            inputField.clear();
        });

        // Add TextField and Button to an HBox for side-by-side layout
        HBox inputSection = new HBox(10, inputLabel, inputField, submitButton);
        inputSection.setPadding(new Insets(0,0,50,(SIZE_ACROSS - inputSection.getWidth()) / 4));;

        return inputSection;
    }
    /**
     * Sets up the whole application window and returns the GraphicsContext from
     * the canvas to enable later drawing. Also sets up the TextArea, which
     * should be originally be passed in empty.
     * 
     * @param primaryStage
     *            Reference to the stage passed to start().
     * @param canvas_width
     *            Width to draw the canvas.
     * @param canvas_height
     *            Height to draw the canvas.
     * @param outputArea
     *            Reference to a TextArea that will be setup.
     * @return Reference to a GraphicsContext for drawing on.
     */
    public GraphicsContext setupStage(Stage primaryStage, int canvas_width,
        int canvas_height, TextArea outputArea, HBox inputField) {
        // Border pane will contain canvas for drawing and text area underneath
        BorderPane p = new BorderPane();

        // Canvas(pixels across, pixels down)
        Canvas canvas = new Canvas(SIZE_ACROSS, SIZE_DOWN);

        // Wrap the Canvas in a VBox to add padding (acting as margin)
         VBox canvasContainer = new VBox(canvas);
        canvasContainer.setPadding(new Insets(0, 0, 0, 30));

        // Command TextArea will hold the commands from the file
        outputArea.setPrefHeight(TEXT_SIZE);
        outputArea.setEditable(false);

        // Place the canvas and command output areas in pane.
        p.setTop(canvasContainer);
        p.setCenter(inputField);
        p.setBottom(outputArea);

        // title the stage and place the pane into the scene into the stage
        primaryStage.setTitle("Erik's Garden");
        primaryStage.setScene(new Scene(p));

        return canvas.getGraphicsContext2D();
    }

    /**
     *  Creates the grid system that represents a garden. A garden is an
     *  m * n set of plots, each of which are represented as a 5x5 grid of
     *  grey squares
     */
    public void initializeStage(GraphicsContext gc){
        for (int i = 0; i < SIZE_ACROSS; i += 40){
            for (int j = 0; j < SIZE_DOWN; j+=40){
                Color c = Color.valueOf("GRAY");
                gc.setFill(c);
                gc.fillRect(i, j, 10, 10);
            }
        }
    }

    /**
     * Recevies a plant from the garden and renders it in Canvas
     * 
     * @param x
     *            x coordinate of the plant in the garden
     * @param y
     *            y coordinate of the plant in the garden
     * @param plant
     *            plant object to be represented on the canvas
     */
    public static void placePlant(int x, int y, Plant plant){
        // calculate where the plant should be placed on the canvas 
        int startRow = y * 5 * CELL_SIZE;
        int startCol = x * 5 * CELL_SIZE;

        // a 5x5 grid of characters the represent the growth of the plant
        char[][] plantRepresentation = plant.getRepresentation();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                // if a cell of a plot is occupied by the plant reflect it in the GUI
                if (plantRepresentation[i][j] != '.'){
                    gc.clearRect(startRow + j * 40, startCol + i * 40, CELL_SIZE, CELL_SIZE);

                    Color c = Color.valueOf(plant.getColor());
                    gc.setFill(c);
                    gc.setStroke(c);

                    if (plant instanceof Flower)
                        gc.fillOval(startRow + j * 40, startCol + i * 40, 20, 20);
                    else if (plant instanceof Fruit)
                        gc.strokeRect(startRow + j * 40, startCol + i * 40, 20, 20);
                    else if (plant instanceof Vegetable)
                        gc.strokeOval(startRow + j * 40, startCol + i * 40, 20, 20);
                    else if (plant instanceof Tree)
                        gc.fillRect(startRow + j * 40, startCol + i * 40, 20, 20);
                }
            }
        }
    }

    /**
     * Recevies coordinates of a plant that was removed from 
     * garden and updates the canvas accordingly
     * 
     * @param x
     *            x coordinate of the plant in the garden
     * @param y
     *            y coordinate of the plant in the garden
     */
    public static void clearPlot(int x, int y) {
        // calculate where the plant that was removed from 
        // the garden was on the canvas
        int startRow = y * 5 * CELL_SIZE;
        int startCol = x * 5 * CELL_SIZE;
 
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                // reset the cells of the respective plot
                Color c = Color.valueOf("White");
                gc.clearRect(startRow + j * 40, startCol + i * 40, CELL_SIZE, CELL_SIZE);

                // represent the unoccupied plot as grey squares
                c = Color.valueOf("GRAY");
                gc.setFill(c);
                gc.fillRect(startRow + j * 40, startCol + i * 40, 10, 10);
            }
        }
    }

    /**
     * Processes the user input from the text field and executed the command
     * 
     * @param command
     *                  command read in from text feild
     * @param garden
     *                  Garden object
     */
    public static void processCommand(String command, Garden garden){
        String[] commandList = command.toLowerCase().split(" ");

                if (commandList[0].equals("plant")) parser.executePlant(commandList,garden);
			    else if(commandList[0].equals("grow")) parser.executeGrow(commandList, garden);
			    // Any command that involves removing a plant from the Garden
			    else if (commandList[0].equals("harvest") ||
                         commandList[0].equals("cut") ||
                         commandList[0].equals("pick") ||
                         commandList[0].equals("gather"))
                         
                        parser.executeRemove(commandList, garden);
                else{
                    outputArea.appendText("Invalid Command\n");
                }
    }

    /**
     * Recevies commands that are exceuted by the garden
     * and displayes them in the program's Text Area
     * 
     * @param message
     *            a commands/message that was executed/produced by the gaden
     */
    public static void displayCommand(String message){
        outputArea.appendText(message);
    }
}
