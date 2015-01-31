package sk.majkl.metronome.ui.views;


import sk.majkl.metronome.stuff.Bar;
import sk.majkl.metronome.stuff.BarVisual;
import sk.majkl.metronome.stuff.Tempo;
import sk.majkl.metronome.stuff.TimeSignature;
import sk.majkl.metronome.stuff.TimeSignature.TS;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MotionEvent.PointerCoords;
import sk.majkl.metronome.stuff.BarSelection;

public class MainView extends View {

    Paint paint = new Paint();
    private int width;
    private int heigh;
    private BarsGridView gridView = new BarsGridView();
    private ControlView controlView= new ControlView();
    
    private int movingBar_dx = 0;
    private int movingBar_dy = 0;


    //hold handler
    
    private final Handler movingHandler = new Handler();
    private Runnable movingRunnable = null;

    //touched place coordinates
    PointerCoords coords = new PointerCoords();
    
    public MainView(Context context) {
        super(context);
        
        gridView.addTimeSignatureChangedLIstener(controlView.getTimeSignatureView());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        gridView.draw(canvas);
        controlView.draw(canvas);
        
    }

    

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.heigh = h;

        gridView.setViewRect(new Rect(0, 0, width, ((heigh*2)/3)-20));
        controlView.setViewRect( new Rect(0, (heigh*2)/3, width, heigh));
        

        Log.d("debug", "Display dimension is " + width + "x" + heigh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//		System.out.println("touchEvent "+event.getAction());
        Log.d("debug", "touchEvent " + event.getAction());
        
        event.getPointerCoords(0, coords)
                ;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                if (gridView.getMovingBar() != null) {
                    gridView.getMovingBar().setSelectionMode(BarSelection.NOT_SELECTED);
                    gridView.placeMovingBar();
                }
                gridView.setMovingBar(null);
                if (movingRunnable != null) {
                    movingHandler.removeCallbacks(movingRunnable);
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
                            
                            BarVisual bvo = (BarVisual)touchedObj;
                            if(bvo.getSelectionMode()==BarSelection.SELECTED)
                                gridView.setSelectionMode(bvo, BarSelection.NOT_SELECTED);
                            else
                                gridView.setSelectionMode(bvo, BarSelection.SELECTED);
                            invalidate();
                            //runs the delayed runnable
                            movingRunnable = new Runnable() {

                                public void run() {
                                    Object touchedObj2 = getTouchedObj(coords);
                                    if(touchedObj == touchedObj2)
                                    {
                                        //first touched the bar, remember where you touche it
                                        gridView.setMovingBar((BarVisual) touchedObj);
                                        movingBar_dx = (int) coords.x - gridView.getMovingBar().getPosition().x;
                                        movingBar_dy = (int) coords.y - gridView.getMovingBar().getPosition().y;
                                        invalidate();
                                    }
                                }
                            };
                            movingHandler.postDelayed(movingRunnable, 250);
                        } else if (touchedObj instanceof Rect) {
                            //buttons
                            Rect b = (Rect) touchedObj;
                            if (b == controlView.getAddButton()) {
                                addNewBar();
                            }else if (b == controlView.getClearButton()) {
                                gridView.clear();
                                invalidate();
                            }else if(b == controlView.getDebugButton())
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
                        if(gridView.getMovingBar()!=null)
                        {
                            gridView.getMovingBar().setMovingPosition(new Point((int) coords.x - movingBar_dx, (int) coords.y - movingBar_dy));
                            gridView.checkInsertionPosibility();
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
        gridView.addNewBarVisual(bv);
        invalidate();

    }

    private Object getTouchedObj(PointerCoords coords) {
		//iterate all objects in view and find touched baby

        

        //check if add button clicked
        if (controlView.getAddButton().contains((int) coords.x, (int) coords.y))
            return controlView.getAddButton();
        else if (controlView.getClearButton().contains((int) coords.x, (int) coords.y))
            return controlView.getClearButton();
        else if(controlView.getDebugButton().contains((int) coords.x, (int) coords.y))
            return controlView.getDebugButton();
        
        for (BarVisual bar : gridView.getGrid()) {
            if (bar.isInside(coords)) {
                return bar;
            }
        }

        return null;

    }

}
