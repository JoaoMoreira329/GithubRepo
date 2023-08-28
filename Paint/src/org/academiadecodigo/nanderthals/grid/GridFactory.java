package org.academiadecodigo.nanderthals.grid;


import org.academiadecodigo.nanderthals.gfx.simplegfx.SimpleGfxGrid;
import org.academiadecodigo.nanderthals.grid.position.Grid;

/**
 * A factory of different types of grids
 */
public class GridFactory {

    public static Grid makeGrid(int cols, int rows) {
        return new SimpleGfxGrid(cols, rows);
    }

}
