/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

import java.io.Serializable;

/**
 *
 * @author dinael
 */
public class Position implements Serializable {
    private int y;
    private int x;
    
    public Position()
    {
        x=0;
        y=0;
    }
    
    public Position(int i,int j)
    {
        this.x=i;
        this.y=j;
    }
    
    public Position(Position p)
    {
        x=p.getX();
        y=p.getY();
    }
    
    public int getX(){return x;}
    
    public void setX(int i)
    {
        x=i;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int j)
    {
        y=j;
    }
    
    public boolean isInside(int t)
    {
        return x>=0&&x<t&&y>=0&&y<t;
    }
    
    public String toString()
    {
        return "("+x+","+y+")";
    }
}
