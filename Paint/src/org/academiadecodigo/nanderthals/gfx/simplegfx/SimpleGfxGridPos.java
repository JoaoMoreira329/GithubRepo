package org.academiadecodigo.nanderthals.gfx.simplegfx;

import org.academiadecodigo.nanderthals.grid.position.AbstractGridPos;
import org.academiadecodigo.nanderthals.grid.GridColor;
import org.academiadecodigo.nanderthals.grid.GridDirection;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;


/**
 * Simple graphics position
 */
public class SimpleGfxGridPos extends AbstractGridPos {

    private Rectangle rectangle;
    private SimpleGfxGrid simpleGfxGrid;

    /**
     * Simple graphics position constructor
     * @param grid Simple graphics grid
     */
    public SimpleGfxGridPos(SimpleGfxGrid grid){
        super((int) (Math.random() * grid.getCols()), (int) (Math.random() * grid.getRows()), grid);

        simpleGfxGrid = grid;

        int x = simpleGfxGrid.columnToX(getCol());
        int y = simpleGfxGrid.rowToY(getRow());

        this.rectangle = new Rectangle(x, y,simpleGfxGrid.getCellSize(),simpleGfxGrid.getCellSize());

        show();
    }

    /**
     * Simple graphics position constructor
     * @param col position column
     * @param row position row
     * @param grid Simple graphics grid
     */
    public SimpleGfxGridPos(int col, int row, SimpleGfxGrid grid){
        super(col, row, grid);

        simpleGfxGrid = grid;

        int x = simpleGfxGrid.columnToX(col);
        int y = simpleGfxGrid.rowToY(row);

        this.rectangle = new Rectangle(x, y,simpleGfxGrid.getCellSize(),simpleGfxGrid.getCellSize());

        show();
    }

    /**
     * @see GridPos#show()
     */
    @Override
    public void show() {
        this.rectangle.fill();
    }

    /**
     * @see GridPos#hide()
     */
    @Override
    public void hide() {
        this.rectangle.delete();
    }

    /**
     * @see GridPos#moveInDirection(GridDirection, int)
     */
    @Override
    public void moveInDirection(GridDirection direction, int distance) {

        int initialCol = getCol();
        int initialRow = getRow();

        super.moveInDirection(direction, distance);

        int dx = simpleGfxGrid.columnToX(getCol()) - simpleGfxGrid.columnToX(initialCol);
        int dy = simpleGfxGrid.rowToY(getRow()) - simpleGfxGrid.rowToY(initialRow);

        this.rectangle.translate(dx,dy);

    }

    /**
     * @see AbstractGridPos#setColor(GridColor)
     */
    @Override
    public void setColor(GridColor color) {
        this.rectangle.setColor(SimpleGfxColorMapper.getColor(color));
        super.setColor(color);
    }
}
