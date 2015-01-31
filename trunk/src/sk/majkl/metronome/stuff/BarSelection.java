/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.majkl.metronome.stuff;

import android.graphics.Color;

/**
 *
 * @author miso
 */
public enum BarSelection {
 
    NOT_SELECTED(0),
    SELECTED(Color.YELLOW),
    MOVING(Color.RED);
    
    private int color;

    private BarSelection(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
    
    
    
    
}
