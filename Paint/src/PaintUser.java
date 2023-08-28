import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

public class PaintUser implements KeyboardHandler {


    private Keyboard keyboard;
    private Rectangle user;
    private Rectangle filledCell;
    private boolean isPainting = false;
    private Paint paint;
    private boolean[][] savedState;
    private Color[][] savedColors;
    private Color color = Color.BLACK;
    private boolean colorSwitch = false;


    //class responsible for user interaction
    public PaintUser(Paint paint) {
        super();
        keyboard = new Keyboard(this);
        savedState = new boolean[Paint.cells.length][Paint.cells[0].length];
        savedColors = new Color[Paint.cells.length][Paint.cells[0].length];
        addKeyboard();
        this.user = new Rectangle(10, 10, 20, 20);
        user.draw();
        user.fill();
        filledCell = null;
        this.paint = paint;
    }

    //Set up keyEvents
    private void addKeyboard() {
        KeyboardEvent moveRight = new KeyboardEvent();
        moveRight.setKey(39);
        moveRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveRight);

        KeyboardEvent moveLeft = new KeyboardEvent();
        moveLeft.setKey(37);
        moveLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveLeft);

        KeyboardEvent moveUp = new KeyboardEvent();
        moveUp.setKey(38);
        moveUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveUp);

        KeyboardEvent moveDown = new KeyboardEvent();
        moveDown.setKey(40);
        moveDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(moveDown);

        KeyboardEvent startPainting = new KeyboardEvent();
        startPainting.setKey(KeyboardEvent.KEY_E);
        startPainting.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(startPainting);

        KeyboardEvent clear = new KeyboardEvent();
        clear.setKey(KeyboardEvent.KEY_C);
        clear.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(clear);

        KeyboardEvent save = new KeyboardEvent();
        save.setKey(KeyboardEvent.KEY_S);
        save.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(save);

        KeyboardEvent load = new KeyboardEvent();
        load.setKey(KeyboardEvent.KEY_L);
        load.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(load);

        KeyboardEvent stopPainting = new KeyboardEvent();
        stopPainting.setKey(KeyboardEvent.KEY_E);
        stopPainting.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        keyboard.addEventListener(stopPainting);

        KeyboardEvent switchColor = new KeyboardEvent();
        switchColor.setKey(KeyboardEvent.KEY_W);
        switchColor.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(switchColor);

    }

    //The 'move...' methods set up the users movement on the canvas
    public void moveRight() {
        if (user.getX() <= 780) {
            user.translate(20, 0);
        }
        if (isPainting) {  //this if checks if the cell is filled while the "e" + the movement key are pressed and deletes it if so
            if (Paint.cells[user.getX() / 20][user.getY() / 20].isFilled()) {
                deleteCell();
            } else {
                paintCell();
            }
        }
    }

    public void moveLeft() {
        if (user.getX() >= 20) {
            user.translate(-20, 0);
        }
        if (isPainting) {
            if (Paint.cells[user.getX() / 20][user.getY() / 20].isFilled()) {
                deleteCell();
            } else {
                paintCell();
            }
        }
    }

    public void moveUp() {
        if (user.getY() > 20) {
            user.translate(0, -20);
        }
        if (isPainting) {
            if (Paint.cells[user.getX() / 20][user.getY() / 20].isFilled()) {
                deleteCell();
            } else {
                paintCell();
            }
        }
    }

    public void moveDown() {
        if (user.getY() < 780) {
            user.translate(0, 20);
        }
        if (isPainting) {
            if (Paint.cells[user.getX() / 20][user.getY() / 20].isFilled()) {
                deleteCell();
            } else {
                paintCell();
            }
        }
    }

    public void setSavedState(int squareCol, int squareRow) {
        this.savedState[squareCol][squareRow] = !savedState[squareCol][squareRow];
    }

    //method responsible for painting the cell on the users coordinates on the canvas
    private void paintCell() {
        int squareCol = user.getX() / 20;
        int squareRow = user.getY() / 20;
        Rectangle cellToPaint = Paint.cells[squareCol][squareRow];
        cellToPaint.setColor(color);
        cellToPaint.fill();

        setSavedState(squareCol, squareRow);  //will change the value in the savedState on the specific position in the array to true
        savedColors[squareCol][squareRow] = color;
    }

    //method responsible for deleting the cell on the users coordinates on the canvas
    public void deleteCell() {
        int squareCol = user.getX() / 20;
        int squareRow = user.getY() / 20;
        Rectangle cellToDelete = Paint.cells[squareCol][squareRow];
        cellToDelete.draw();

        setSavedState(squareCol, squareRow);  //will change the value in the savedState on the specific position in the array to false
    }

    //method responsible for clearing the canvas
    public void clear() {
        for (int col = 0; col < Paint.cells.length; col++) {
            for (int row = 0; row < Paint.cells[col].length; row++) {
                Rectangle cell = Paint.cells[col][row];
                cell.draw();
                savedState[col][row] = false;
            }
        }
    }


    //method responsible for saving the canvas state into a txt file
    public void savedState() {
        try {
            FileWriter writer = new FileWriter("gridState.txt");

            for (int col = 0; col < Paint.cells.length; col++) {
                for (int row = 0; row < Paint.cells[col].length; row++) {
                    boolean state = savedState[col][row];
                    Color cellColor = savedColors[col][row];

                    writer.write(state ? "1" : "0"); //depending on the boolean value of "state" (true || false) it will save as either "1" or "0"

                    if (cellColor != null) {
                        int r = cellColor.getRed();
                        int g = cellColor.getGreen();
                        int b = cellColor.getBlue();
                        writer.write(":" + r + "," + g + "," + b); // Save the color value(s)
                    }

                    writer.write(" "); // Separate cells with space
                }
                writer.write(System.lineSeparator());
            }

            writer.close();
            System.out.println("Grid state saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method responsible for loading the canvas state from a txt file
    public void loadState() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("gridState.txt"));

            for (int col = 0; col < Paint.cells.length; col++) {
                String line = reader.readLine();  // Read a line from the file, representing the state of cells in this column
                String[] segments = line.split(" ");

                for (int row = 0; row < segments.length; row++) {
                    String segment = segments[row];
                    char c = segment.charAt(0);
                    boolean state = (c == '1'); // Convert character to boolean state
                    Rectangle cell = Paint.cells[col][row];

                    if (state) { // If the cell is in a filled state
                        if (segment.contains(":")) { // check for color information
                            String[] parts = segment.split(":");
                            String colorPart = parts[1];
                            String[] colorComponents = colorPart.split(",");
                            int r = Integer.parseInt(colorComponents[0]);
                            int g = Integer.parseInt(colorComponents[1]);
                            int b = Integer.parseInt(colorComponents[2]);

                            Color cellColor = new Color(r, g, b); // Create a Color object from the r g b values
                            cell.setColor(cellColor);  // Set the cell's color and fill it
                            cell.fill();

                            // Store the cell's state and color information in the saved arrays
                            savedState[col][row] = true;
                            savedColors[col][row] = cellColor;
                        } else { // If there's no color information
                            cell.setColor(null);
                            cell.draw();

                            // Store the cell's state in the saved array and set color to null
                            savedState[col][row] = state;
                            savedColors[col][row] = null;
                        }
                    }
                }
            }

            // Close the reader and print a message indicating successful state loading
            reader.close();
            System.out.println("Grid state loaded!");
        } catch (IOException e) {
            e.printStackTrace(); // Print any IOException stack trace
        }
    }

    private void switchColor() {
        if (color == Color.BLACK) {
            color = Color.RED;
        } else if (color == Color.RED) {
            color = Color.BLUE;
        } else if (color == Color.BLUE) {
            color = Color.GREEN;
        } else if (color == Color.GREEN) {
            color = Color.BLACK;
        }
        colorSwitch = false;
    }

    private void handlePaintingStart() {
        if (!isPainting) {
            if (colorSwitch) {
                switchColor();
            }
            if (Paint.cells[user.getX() / 20][user.getY() / 20].isFilled()) {
                deleteCell();
                return;
            }
            isPainting = true;
            paintCell();
        }
    }

    public void setColorSwitch() {
        this.colorSwitch = true;
    }

    //Overrides the keyPressed method from the KeyboardHandler interface. Handles various keyboard events, including movement, painting, clearing, saving, and loading.
    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int keyPressed = keyboardEvent.getKey();

        switch (keyPressed) {
            case 39:
                moveRight();
                break;
            case 37:
                moveLeft();
                break;
            case 38:
                moveUp();
                break;
            case 40:
                moveDown();
                break;
            case 67:
                clear();
                break;
            case 83:
                savedState();
                break;
            case 76:
                loadState();
                break;
            case 69:
                handlePaintingStart();
                break;
            case 87:
                setColorSwitch();
                break;
        }
    }

    //Overrides the keyReleased method from the KeyboardHandler interface. Handles the event when a key is released, particularly the 'E' key for stopping painting.
    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        int keyReleased = keyboardEvent.getKey();

        if (keyReleased == 69) {
            isPainting = false;
        }

    }
}
