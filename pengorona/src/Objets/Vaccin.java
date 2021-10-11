/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;

/**
 *
 * @author dinael
 */
public class Vaccin extends Objet implements Runnable{
    
    public Vaccin()
    {
        super();
    }
    public Vaccin(Position p)
    {
        super(p);
    }
    
    public Vaccin(int x, int y)
    {
        super(x,y);
    }
    
    
    public void deplacer()
    {
        
    }
    
    public String toString()
    {
      return "[]";
    }
    
    public String getImage()
    {
        return "vc.png";
    }
    
    public void run()
    {
        runCode(500);
    }
  
    public void deplacement(Position p)
    {        
        if(pl.getObjet(p)==null)
            swap(p);
        else
            cmd=0;//fin du deplacement
        
        try
        {
            Thread.currentThread().sleep(80);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
        @Override
    public void attack() {
    }
}
