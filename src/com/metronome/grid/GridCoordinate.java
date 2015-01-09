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

/**
 *
 * @author Michal Molcan
 */
public class GridCoordinate {

    int row;
    int col;

    public GridCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "["+col+"]["+row+"]";
    }
    
    
    
    
}
