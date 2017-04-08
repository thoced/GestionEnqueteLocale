/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginPackage;

/**
 *
 * @author Thonon
 */
public class NoLoginException extends Exception
{

    @Override
    public String getMessage()
    {
        return "Mauvais Login et/ou Password";
    }
    
}
