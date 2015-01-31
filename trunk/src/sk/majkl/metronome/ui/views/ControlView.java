/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.majkl.metronome.ui.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 *
 * @author miso
 */
public class ControlView extends ComponentView{

    private final int buttonPadding_horizontal = 5; 
    private final int buttonPadding_vertical = 5; 
    private final int button_width = 150;
    private final int button_height = 150;
    
    //fixed buttons
    private Rect addButton;
    private Rect clearButton;
    private  Rect debugButton;
    
    private TimeSignatureView timeSignatureView = new TimeSignatureView();

    public ControlView() {
    }
    
    
    
    public ControlView(Rect viewRect ) {
        this.viewRect = viewRect;
                
    }
    

    @Override
    public void updateInnerComponents() {
        addButton = new Rect(
                viewRect.width()-buttonPadding_horizontal-button_width,
                viewRect.top, 
                viewRect.width()-buttonPadding_horizontal,
                viewRect.top+button_height);
        clearButton = new Rect(
                viewRect.width()-buttonPadding_horizontal-button_width,
                addButton.bottom+buttonPadding_vertical,
                viewRect.width()-buttonPadding_horizontal,
                addButton.bottom+buttonPadding_vertical+button_height);
        debugButton = new Rect(
                viewRect.width()-buttonPadding_horizontal-button_width,
                clearButton.bottom+buttonPadding_vertical,
                viewRect.width()-buttonPadding_horizontal,
                clearButton.bottom+buttonPadding_vertical+button_height);
        
        timeSignatureView.setViewRect(new Rect(
                viewRect.left+buttonPadding_horizontal,
                viewRect.top+buttonPadding_vertical,
                viewRect.right/2, 
                viewRect.bottom-buttonPadding_vertical));
    }

    @Override
    public void draw(Canvas c) {
        //draw lines around 
        super.drawBorder(c);
        
        paint.setColor(Color.GREEN);
        c.drawRect(addButton,paint);
        
        paint.setColor(Color.RED);
        c.drawRect(clearButton,paint);
        
        paint.setColor(Color.GREEN);
        c.drawRect(debugButton,paint);
        
        timeSignatureView.draw(c);
        
    }

    public Rect getAddButton() {
        return addButton;
    }

    public Rect getClearButton() {
        return clearButton;
    }

    public Rect getDebugButton() {
        return debugButton;
    }

    public TimeSignatureView getTimeSignatureView() {
        return timeSignatureView;
    }
    
    
    
    
    
    
}
