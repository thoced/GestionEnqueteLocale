/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

import ModelPackage.ModelUser;

/**
 *
 * @author Thonon
 */
public class OkLoginException extends Exception
{
    private ModelUser modelUser;
    
    private boolean resetPassword;

    public OkLoginException() 
    {
        // reset password
        resetPassword = false;
        
        modelUser = new ModelUser();
    }
    
    

    public ModelUser getModelUser() {
        return modelUser;
    }

    public void setModelUser(ModelUser modelUser) {
        this.modelUser = modelUser;
    }

    @Override
    public String getMessage() 
    {
        return "Login Ok";
    }
    
    
}
