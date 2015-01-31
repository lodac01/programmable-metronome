/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.majkl.metronome.ui.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 *
 * @author miso
 */
public abstract class ComponentView {
    protected Rect viewRect;
    protected Paint paint = new Paint();

    public Rect getViewRect() {
        return viewRect;
    }

    public void setViewRect(Rect viewRect) {
        this.viewRect = viewRect;
        updateInnerComponents();
    }
    
    protected void updateViewRect(int left, int top, int right, int bottom)
    {
        viewRect = new Rect(left, top, right, bottom);
    }
    
    protected void drawBorder(Canvas c)
    {
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(1);
        c.drawLine(viewRect.left, viewRect.top, viewRect.right-1, viewRect.top, paint);
        c.drawLine(viewRect.right-1, viewRect.top, viewRect.right-1, viewRect.bottom-1, paint);
        c.drawLine(viewRect.right-1, viewRect.bottom-1, viewRect.left, viewRect.bottom-1, paint);
        c.drawLine(viewRect.left, viewRect.bottom-1, viewRect.left, viewRect.top, paint);
    }
    
    public abstract void draw(Canvas c);
    
    public abstract void updateInnerComponents();
    
}
