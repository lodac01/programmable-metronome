/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.majkl.metronome.listeners;

import sk.majkl.metronome.stuff.TimeSignature;
import java.util.EventListener;

/**
 *
 * @author miso
 */
public interface TimeSignatureChangedListener extends EventListener{
    public void timeSignatureChanged(TimeSignature newSignature);
}
