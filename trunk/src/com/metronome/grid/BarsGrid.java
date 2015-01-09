/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//#################################################################################################
//# Repository path:      $HeadURL$
//# Last committed:       $Revision$
//# Last changed by:      $Author$
//# Last changed date:    $Date$
//# ID:                   $Id$
//#################################################################################################

package com.metronome.grid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import com.metronome.stuff.BarVisual;
import java.util.ArrayList;

/**
 *
 * @author Michal Molcan
 */
public class BarsGrid {
    private Paint paint = new Paint();
    private ArrayList<BarVisual> grid = new ArrayList<BarVisual>();
    private ArrayList<BarVisual> proposedGrid = new ArrayList<BarVisual>();
    private GridCoordinate separator = null;
    private BarVisual movingBar = null;
    private int rows = 0;
    private int cols = 0;
    private int width = 0;
    private int height = 0;
    

    /**
     * 
     * @param width width of display
     * @param height heigh of display
     */
    public BarsGrid(int width, int height) {
        this.width = width;
        this.height = height;
        cols = width / (BarVisual.width+BarVisual.horizontal_spacing);
        rows = height / (BarVisual.heigh+BarVisual.vertical_spacing);
        Log.d("debug", "Grid is "+cols+"x"+rows+".");
    }
       
    
    
    /**
     * Adds new bar at the end of grid
     * @param bar 
     */
    public void addNewBarVisual(BarVisual bar)
    {
        //get the grid position of new element
        GridCoordinate coor = newGridCoordinates();
        bar.setGridCoordinate(coor);
        
        this.grid.add(bar);
    }
    
    
    /**
     * Calculate grid coorinate for new BarVisual appended at the end of array
     * @return GridCoordinate
     */
    private GridCoordinate newGridCoordinates()
    {
        //get the last element in grid and calulate the new elements position
        if(grid.isEmpty())
            return new GridCoordinate(0, 0);
        else
        {
            GridCoordinate newCoordinate = new GridCoordinate(0,0);
            GridCoordinate lastElementGridPos = grid.get(grid.size()-1).getGridCoordinate();
            //calculate column
            if(lastElementGridPos.getCol()<cols-1)
                newCoordinate.setCol(lastElementGridPos.getCol()+1);
            //calculate row
            if(newCoordinate.getCol()==0)
                newCoordinate.setRow(lastElementGridPos.getRow()+1);
            else
                newCoordinate.setRow(lastElementGridPos.getRow());
            
            Log.d("debug", "New bar has grid position "+newCoordinate.toString() );
            return newCoordinate;
        }
    }
    
    public void drawGrid(Canvas canvas)
    {
        for( int i = grid.size()-1; i>=0; i--)
        {
            if(movingBar!=null)
            {
                if(grid.get(i) == movingBar)
                    continue;//will be draw as last of all
            }
            grid.get(i).drawItself(canvas);
        }
        if(movingBar!=null)
            movingBar.drawItself(canvas);
        
        if(separator!= null)
        {
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(5);
            int x = separator.getCol()*(BarVisual.width+BarVisual.horizontal_spacing) + 2;
            int y1 = separator.getRow()*(BarVisual.heigh+BarVisual.vertical_spacing);
            int y2 = separator.getRow()*(BarVisual.heigh+BarVisual.vertical_spacing) + BarVisual.heigh;
            canvas.drawLine(x, y1, x, y2, paint);
        }
    }
    
    public void clear()
    {
        grid.clear();
    }
    
    public void removeBar(BarVisual bar)
    {
        grid.remove(bar);
    }
    
    public void setMovingBar(BarVisual bar)
    {
        this.movingBar = bar;
        if(this.movingBar!=null)
        {
            this.movingBar.setSelected(true);
            grid.remove(bar);
        }
        else
        {
            separator = null;
            
        }
            
    }

    public BarVisual getMovingBar() {
        return movingBar;
    }

    public ArrayList<BarVisual> getGrid() {
        return grid;
    }
    
    /**
     * Checks if the moving bar is close to space between two (or before first in line) bar.
     */
    public void checkInsertionPosibility()
    {
        if(movingBar==null)
            return;
        
        Point movingPosition = movingBar.getMovingPosition();
        int row = (movingPosition.y + (BarVisual.heigh/2))/ (BarVisual.heigh + BarVisual.vertical_spacing);
        int col = (movingPosition.x + (BarVisual.width/2)) / (BarVisual.width + BarVisual.horizontal_spacing);
        separator = new GridCoordinate(row, col);
        Log.d("debug", "moving around row "+row+" and col "+col);
        
    }
    
    
    
    
    
}
