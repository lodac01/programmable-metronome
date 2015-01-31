package sk.majkl.metronome.stuff;

import android.graphics.Canvas;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent.PointerCoords;
import sk.majkl.metronome.ui.grid.GridCoordinate;

public class BarVisual {

    Paint paint = new Paint();
    private Bar bar;
    public static final int width = 150;
    public static final int heigh = 150;
    public static final int horizontal_spacing = 10;
    public static final int vertical_spacing = 10;
    private Point movingPosition = new Point(); //this is position of the bar while moving. once it is dropped, this is nulled, and bar sits on grid
    private Point position; //this value will be calcuated based on grid position when the grid pos is set
    private GridCoordinate gridCoordinate;
    private int color;
    private boolean selected = false;
    private BarSelection selectionMode = BarSelection.NOT_SELECTED;
    
    

    private static final Random random = new Random();

    public BarVisual(Bar bar) {
        this.bar = bar;
        this.position = new Point(horizontal_spacing, vertical_spacing); //just dummy, will be set later
        this.color = 0xFFFFFF00 & random.nextInt();

    }

    public Rect getRectangle() {
        return new Rect(position.x, position.y, position.x + width, position.y + heigh);
    }
    
    public Rect getMovingRectangle() {
        return new Rect(movingPosition.x, movingPosition.y, movingPosition.x + width, movingPosition.y + heigh);
    }

    public int getColor() {
        return color;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Point getPosition() {
        return position;
    }


    public boolean isInside(Point coords) {
        return getRectangle().contains(coords.x, coords.y);
    }

    public boolean isInside(PointerCoords coords) {
        return getRectangle().contains((int) coords.x, (int) coords.y);
    }

    public BarSelection getSelectionMode()
    {
        return selectionMode;
    }

    public void setSelectionMode(BarSelection mode)
    {
        this.selectionMode = mode;
        if(selectionMode==BarSelection.MOVING)
            this.movingPosition = this.position;
    }
    

    public GridCoordinate getGridCoordinate() {
        return gridCoordinate;
    }

    public void setGridCoordinate(GridCoordinate gridCoordinate) {
        this.gridCoordinate = gridCoordinate;
        recalculatePosition();
    }

    public Point getMovingPosition() {
        return movingPosition;
    }

    public void setMovingPosition(Point movingPosition) {
        this.movingPosition = movingPosition;
    }
    
    
    
    
    /**
     * Based on the grid position
     */
    private void recalculatePosition()
    {
        position.x = gridCoordinate.getCol()*(width+horizontal_spacing);
        position.y = gridCoordinate.getRow()*(heigh+vertical_spacing);
    }
    
    

    /**
     * Draws itself on the given canvas
     *
     * @param canvas
     * @param maxRect
     */
    public void drawItself(Canvas canvas ,Rect maxRect) {

        if(!getRectangle().intersect(maxRect))
        {
             Log.d("debug", "not drawing component. it is outside drawable area.");
             return;
        }
        
        if(selectionMode==BarSelection.SELECTED)
        {
            //draw line around element
            Rect r = getRectangle();
            paint.setColor(getColor());
            canvas.drawRect(r,paint);
            paint.setStrokeWidth(15);
            paint.setColor(selectionMode.getColor());
            canvas.drawLine(r.left, r.top, r.right, r.top, paint);
            canvas.drawLine(r.right, r.top, r.right, r.bottom, paint);
            canvas.drawLine(r.right, r.bottom, r.left, r.bottom, paint);
            canvas.drawLine(r.left, r.bottom, r.left, r.top, paint);
        }
        else if(selectionMode==BarSelection.MOVING)
        {
            //draw line around element
            Rect r = getMovingRectangle();
            paint.setColor(getColor());
            canvas.drawRect(r,paint);
            paint.setStrokeWidth(15);
            paint.setColor(selectionMode.getColor());
            canvas.drawLine(r.left, r.top, r.right, r.top, paint);
            canvas.drawLine(r.right, r.top, r.right, r.bottom, paint);
            canvas.drawLine(r.right, r.bottom, r.left, r.bottom, paint);
            canvas.drawLine(r.left, r.bottom, r.left, r.top, paint);
            
        }
        else
        {
            paint.setColor(getColor());
            canvas.drawRect(getRectangle(),paint);
        }
    }

}
