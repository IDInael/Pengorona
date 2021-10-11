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
public class Murs extends Objet{

    public Murs()
    {
        super();
    }
    
    public Murs(int i,int j)
    {
        super(i,j);
    }
    
    @Override
    public void deplacement(Position p) {
    }

    @Override
    public void attack() {
    }

    @Override
    public String getImage() 
    {
        String s="mur.png";;
        nb=(++nb)%40;
        
        if(!active&&nb<20)
        {
            s="mur1.png";
        }
        return s;
    }
    
    public String toString()
    {
        return "||";
    }
    
}
