/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsPackage;

import javafx.scene.control.TableCell;

/**
 *
 * @author Thonon
 */
public class IndexCell extends TableCell
{
    public static int index = 1;
    
    @Override
    protected void updateItem(Object item, boolean empty)
    {
        super.updateItem(item, empty); 
        this.setText(null);
        
        if(item != null)
        {
            this.setText(String.valueOf(index));
            index++;
        }
        else
            index = 1; // reset index
    }
    
}
