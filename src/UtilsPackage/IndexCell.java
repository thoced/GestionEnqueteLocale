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
 
    @Override
    public void updateIndex(int i) 
    {
        super.updateIndex(i+1); //To change body of generated methods, choose Tools | Templates.

        if(i < this.getTableView().getItems().size())
            this.setText(String.valueOf(i + 1));
        else
            this.setText(null);

    }
    
    
    
}
