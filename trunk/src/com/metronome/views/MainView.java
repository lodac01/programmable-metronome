package com.metronome.views;

import java.util.ArrayList;

import com.metronome.stuff.Bar;
import com.metronome.stuff.BarVisual;
import com.metronome.stuff.Tempo;
import com.metronome.stuff.TimeSignature;
import com.metronome.stuff.TimeSignature.TS;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MotionEvent.PointerCoords;
import com.metronome.grid.BarsGrid;

public class MainView extends View {

    Paint paint = new Paint();
    private int width;
    private int heigh;
//    private ArrayList<BarVisual> bars = new ArrayList<BarVisual>();
    private BarsGrid grid = null;
    
//    private BarVisual movingBar = null;
    private int movingBar_dx = 0;
    private int movingBar_dy = 0;

    //fixed buttons
    private final Rect addButton = new Rect(0, heigh - 40, 40, heigh);
    private final Rect clearButton = new Rect();
    private final Rect debugButton = new Rect();

    //hold handler
    private final Handler pressedHandler = new Handler();
    private Runnable pressedRunnable = null;

    //touched place coordinates
    PointerCoords coords = new PointerCoords();
    
    public MainView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        grid.drawGrid(canvas);
        drawAddButton(canvas);
        drawClearButton(canvas);
        drawDebugButton(canvas);

    }

    private void drawAddButton(Canvas canvas) {

        //draws the add button at the bottom-left of the screen
        paint.setColor(Color.GREEN);
        canvas.drawRect(addButton, paint);

    }

    private void drawClearButton(Canvas canvas) {

        paint.setColor(Color.RED);
        canvas.drawRect(clearButton, paint);

    }
    
    private void drawDebugButton(Canvas canvas)
    {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(debugButton, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.heigh = h;

        grid = new BarsGrid(width, heigh);
        
        //update position of addButton
        addButton.set(0, heigh - 150, 150, heigh);
        clearButton.set(155, heigh - 150, 155 + 150, heigh);
        debugButton.set(width-155, heigh-150, width-5, heigh);

        Log.d("debug", "Display dimension is " + width + "x" + heigh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//		System.out.println("touchEvent "+event.getAction());
        Log.d("debug", "touchEvent " + event.getAction());
        
        event.getPointerCoords(0, coords);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (grid.getMovingBar() != null) {
                    grid.getMovingBar().setSelected(false);
                }
                grid.setMovingBar(null);
                if (pressedRunnable != null) {
                    pressedHandler.removeCallbacks(pressedRunnable);
                }
                invalidate();
                break;
            }
            default: {
                final Object touchedObj = getTouchedObj(coords);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        
                        if (touchedObj instanceof BarVisual) {
                            //bars
                            //runs the delayed runnable
                            pressedRunnable = new Runnable() {

                                public void run() {
                                    Object touchedObj2 = getTouchedObj(coords);
                                    if(touchedObj == touchedObj2)
                                    {
                                        //first touched the bar, remember where you touche it
                                        grid.setMovingBar((BarVisual) touchedObj);
                                        movingBar_dx = (int) coords.x - grid.getMovingBar().getPosition().x;
                                        movingBar_dy = (int) coords.y - grid.getMovingBar().getPosition().y;
                                        invalidate();
                                    }
                                }
                            };
                            pressedHandler.postDelayed(pressedRunnable, 250);
                        } else if (touchedObj instanceof Rect) {
                            //buttons
                            Rect b = (Rect) touchedObj;
                            if (b == addButton) {
                                addNewBar();
                            }else if (b == clearButton) {
                                grid.clear();
                                invalidate();
                            }else if(b == debugButton)
                            {
                                for(int i = 0; i< 30; i++)
                                {
                                    addNewBar();
                                }
                            }
                        }
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if(grid.getMovingBar()!=null)
                        {
                            grid.getMovingBar().setMovingPosition(new Point((int) coords.x - movingBar_dx, (int) coords.y - movingBar_dy));
                            grid.checkInsertionPosibility();
                            invalidate();
                        }
                        break;
                    }

                }//switch

            }//default

        }//switch

        
        return true;
    }

    private void addNewBar() {
        TimeSignature ts = new TimeSignature(TS.T44);
        Tempo t = new Tempo();
        Bar b = new Bar(t, ts);
        BarVisual bv = new BarVisual(b);
        grid.addNewBarVisual(bv);
        invalidate();

    }

    private Object getTouchedObj(PointerCoords coords) {
		//iterate all objects in view and find touched baby

        

        //check if add button clicked
        if (addButton.contains((int) coords.x, (int) coords.y))
            return addButton;
        else if (clearButton.contains((int) coords.x, (int) coords.y))
            return clearButton;
        else if(debugButton.contains((int) coords.x, (int) coords.y))
            return debugButton;
        
        for (BarVisual bar : grid.getGrid()) {
            if (bar.isInside(coords)) {
                return bar;
            }
        }

        return null;

    }

}
