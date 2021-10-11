/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;
import Joueur.Joueur;
import java.io.Serializable;

/**
 *
 * @author dinael
 */
public abstract class Objet implements Serializable{
    protected Position pos;//position dans le plateau
    protected volatile Plateau pl=null;//plateau de jeu
    protected int cmd;//commande
    protected int nb=0;//gestion des images
    protected boolean active=true;
    protected int dir;//direction de la destruction ou de l'attaque
    protected boolean infected=false;//etat de santé
    protected Joueur jr=null;//joueur assigné à l'objet
    protected boolean online=false;//s'il s'agit d'une partie local ou en ligne
    
    public Objet()
    {
        pos=new Position();
        cmd=0;
    }
    public Objet(Position p)
    {
        pos=new Position(p);
        cmd=0;
    }
    public Objet(int x,int y)
    {
        pos=new Position(x,y);
        cmd=0;
    }
    
    public Position getPosition()
    {
        return pos;
    }
    
    public boolean isActive()
    {
        return active;
    }
    
    public void setPlateau(Plateau plj)
    {
        pl=plj;
        pl.setObjet(pos, this);
    }
    
    public void setCommande(int c)
    {
        cmd=c;
    }
    
    public int getCommande()
            
    {
        return cmd;
    }

    public void setPosition(int x, int y)
    {
        pos=new Position(x,y);
    }   
    public boolean isInfected()
    {
        return infected;
    }
    public void setInfected(boolean b)
    {
        infected=b;
    }


    
    public void setJoueur(Joueur j)
    {
        jr=j;
    }
    
    public Joueur getJoueur()
    {
        return jr;
    }
    
    public abstract void deplacement(Position p);
    public abstract void attack();
    public abstract String getImage();
    public abstract String toString();
    
    
    public void setOnline(boolean b)
    {
        online=b;
    }
    
    public Position avancer()
    {
      return new Position(pos.getX(),pos.getY()+1);
    }
    public Position reculer()
    {
       return new Position(pos.getX(),pos.getY()-1);
    }
    public Position monter()
    {
     return new Position(pos.getX()-1,pos.getY());
    }
    public Position descendre()
    {
      return new Position(pos.getX()+1,pos.getY());
    }
    
    /**
     * renvoie la position suivante de l'objet selon sa direction
     * @return 
     */
    protected Position getPositionSuivante()
    {
        Position p=null;
        switch(cmd)
        {
            case 1:
                p=avancer();
                break;
            case 2:
                p=descendre();
                break;
            case 3:
                p=reculer();
                break;
            case 4:
                p=monter();
                break;
            default:
                sleep(100); 
        }
        return p;
    }    
    
    
    /**
     * prendre la place de l'objet dans la position passé en parametre en l'écrasant
     * @param p 
     */
    public void swap(Position p)
    {
        pl.setObjet(p, this);
        pl.setObjet(pos, null);
        pos=p;
    }
    
    

    /**
     * deplacement des objets de maniere generale dans le plateau de jeu
     * @param time 
     */
    public void runCode(int time)
    {
        int t=pl.getTaille();
        while(active)
        {
                if(cmd!=0)
                {
                    dir=cmd;//mise a jour de la direction des attaques suivant la direction de deplacement
                }
                Position p=getPositionSuivante();
                //si la position suivante est dans le plateau, on effectue le deplacement
                if(p!=null&&p.isInside(t))
                {
                    deplacement(p);
                }
                else
                {
                    cmd=0;
                }
                sleep(time);
        }
    }
    
    
    public void desactive()
    {
        active=false;
    }
    
    private void sleep(int time)
    {
        try
        {
            Thread.currentThread().sleep(time);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public void setActive(boolean b)
    {
        active=b;
    }
}
