/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;
import Interface.Pengo;
import Joueur.Joueur;

/**
 *
 * @author dinael
 */
public class Secours extends Objet implements Runnable {
    
    public Secours(int i,int j)
    {
        super(i,j);
    }
    
    public Secours()
    {
        super();
    }

public void deplacement(Position p)
    {
        Objet ob=pl.getObjet(p);
        if(ob instanceof Antivirus)
        {
            ob.desactive();
            sleep(500);
            swap(p);
        }
        else
        {
            if(ob instanceof Pingouin)
            {
                ob.setInfected(false);//guerir le pinguoin
                //Joueur jr=this.getJoueur();
                jr.setObjet(ob);
                //Pengo.panel.setCommandeObjet(ob);//le joueur continue de controler le pinguoin
                this.active=false;
                //autodestruction
                autoDestruction(pos);
            }
            else
            {
                if(ob==null)
                {
                    swap(p);
                }
            }
        }
    }

    @Override
    public void attack() {
        
    }

    @Override
    public String getImage()
    {
        String s;
        nb=(++nb)%40;

        switch(cmd)
        {
            case 1:
                if(nb<20)
                    s="amb1a.png";
                else
                    s="amb1b.png";
                break;
            case 3:
                if(nb<20)
                    s= "amb3a.png";
                else
                    s="amb3b.png";
                break;
            default:
                if(nb<=20)
                    s= "amb1.png";
                else
                    s= "amb2.png";
        }
        return s;
    }
    
    private void autoDestruction(Position p)
    {
        Thread th=new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.currentThread().sleep(500);
                    pl.setObjet(p, null);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
    
    public void run()
    {
        runCode(300);
    }
    
        private void destruction(Position p)
    {
        Thread th=new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.currentThread().sleep(1500);
                    pl.setObjet(p, null);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
            });
        th.start();
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
    
    public String toString()
    {
        return "SS";
    }
    
}
