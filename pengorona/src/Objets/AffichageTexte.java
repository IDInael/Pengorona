/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinael
 */
public class AffichageTexte extends Objet 
{

    private String img;
    public AffichageTexte()
    {
        super();
        img="lin";
    }  
    
    public AffichageTexte(String s)
    {
        img=s;
    }

    @Override
    public void deplacement(Position p) {
    }

    @Override
    public void attack() {
    }

    @Override
    public String getImage() {
        nb=(++nb)%80;
        
        try {
            Thread.currentThread().sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        if(nb<40)
            return img+"1.png";
        else
            return img+"2.png";
    }

    @Override
    public String toString() {
        return img;
    }
}
