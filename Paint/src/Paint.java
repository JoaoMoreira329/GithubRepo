import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Line;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Paint {
    private int cols;
    private int rows;
    private int delay;
    private int cellSize;
    private int padding;
    private Rectangle field;
    private PaintUser user;
    public static Rectangle[][] cells;


    //This class represents the Canvas

    public Paint(int cols, int rows, int delay) {
        this.cols = cols;
        this.rows = rows;
        this.delay = delay;
        this.cellSize = 20;
        this.padding = 10;
    }

    //init method: initializes the painting area
    public void init() {
        createCanvasAndCells();
        user = new PaintUser(this);
    }

    private void createCanvasAndCells() {
        field = new Rectangle(padding, padding, cols * cellSize, rows * cellSize);
        field.draw();
        cells = new Rectangle[cols][rows];

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int x = col * cellSize + padding;
                int y = row * cellSize + padding;
                cells[col][row] = new Rectangle(x, y, cellSize, cellSize);
                cells[col][row].draw();
            }
        }
    }
}