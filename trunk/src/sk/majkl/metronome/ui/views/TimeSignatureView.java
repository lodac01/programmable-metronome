/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.majkl.metronome.ui.views;

import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.ToneGenerator;
import sk.majkl.metronome.listeners.TimeSignatureChangedListener;
import sk.majkl.metronome.stuff.TimeSignature;
import java.util.EventListener;

/**
 *
 * @author miso
 */
public class TimeSignatureView extends ComponentView implements TimeSignatureChangedListener{

    private TimeSignature ts;

    public TimeSignatureView() {
    }

    
    
    public TimeSignatureView(TimeSignature ts) {
        this.ts = ts;
    }
    
    
    @Override
    public void draw(Canvas c) {
        drawBorder(c);
        
        if(ts!=null)
        {
            
            paint.setTextSize(viewRect.height()/2);
            c.drawText(ts.toString(), viewRect.left, viewRect.bottom, paint);
        }
        
    }

    @Override
    public void updateInnerComponents() {
        //HE? co tu?
    }

    
    
    public void timeSignatureChanged(TimeSignature newSignature) {
        this.ts = new TimeSignature(newSignature);
        ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        tg.startTone(ToneGenerator.TONE_DTMF_0, 250);
    }
    
}
