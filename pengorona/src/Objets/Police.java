/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;
import Interface.Pengo;
import Joueur.Joueur;
import Reseau.server.Serveur;
import java.util.Random;

/**
 *
 * @author dinael
 */
public class Police extends Objet implements Runnable {
    
    public Police(int x,int y)
    {
        super(x,y);
    }

    @Override
    public void deplacement(Position p) {
        Objet ob=pl.getObjet(p);
        
        if(ob==null)
        {
            swap(p);
        }
        else
        {
            if(ob instanceof Pingouin&&!ob.isInfected())
            {
                //Pengo.joueur.amende(50);
                Joueur jr=ob.getJoueur();
                jr.amende(50);
                
                
                if(online)
                {
                    Serveur.message(jr.getPseudo()+" a recu une amende de 50$");
                }
                else
                {
                    Pengo.message(jr.getPseudo()+" a recu une amende de 50$");
                }
                
                if(infected)
                {
                    ob.setInfected(true);
                    if(this.jr!=null)
                    {
                        this.jr.bonus(50);
                    }
                    
                    if(online)
                    {
                        Serveur.message("le pinguoin de "+jr.getPseudo()+" a été infecté");
                        Serveur.addSecours(jr);
                    }
                    else
                    {
                        Pengo.addSecours();
                        Pengo.message("le pinguoin de "+jr.getPseudo()+" a été infecté");
                    }
                }
                
                Position p1=pos;
                pos=p;
                Position p2=getPositionSuivante();
                pos=p1;
                if(p2!=null&&p2.isInside(pl.getTaille()))
                {
                    deplacement(p2);
                }
                else
                    cmd=0;
            }
            else
            {
                if(ob instanceof Pompier&&infected)
                {
                    ob.setInfected(true);
                }
                else
                {
                    if(ob instanceof Secours&&infected)
                    {
                        ob.desactive();
                        Joueur jr=ob.getJoueur();
                        jr.mort();
                        if(this.jr!=null)
                        {
                            this.jr.bonus(50);
                        }
                        if(jr.getNbVie()>0)
                        {
                            Thread th=new Thread(
                                    new Runnable() {
                                public void run() {
                                    try
                                    {
                                        Thread.currentThread().sleep(1000);
                                        pl.setObjet(p, null);//suppression de l'objet
                                        Thread.currentThread().sleep(1000);
                                        if(online)
                                        {
                                            Serveur.message("le secours de "+jr.getPseudo()+" a été tué");
                                            Serveur.addSecours(jr);
                                        }
                                        else
                                        {
                                            Pengo.message("le secours de "+jr.getPseudo()+" a été tué");
                                            Pengo.addSecours();
                                        }
                                    }
                                    catch(InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            );
                            th.start();   
                        }

                        Position p1=pos;
                        pos=p;
                        Position p2=getPositionSuivante();
                        pos=p1;
                        if(p2!=null&&p2.isInside(pl.getTaille()))
                        {
                            deplacement(p2);
                        }
                        else
                            swap(p);
                    }
                    else
                        cmd=0;
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
        if(active&&!infected)
        {
            switch(cmd)
            {
                case 1:
                    if(nb<20)
                        s="copa.png";
                    else
                        s="copb.png";
                    break;
                case 3:
                    if(nb<20)
                        s="copa.png";
                    else
                        s="copb.png";
                    break;
                default:
                    if(nb<20)
                        s="cop1.png";
                    else
                        s="cop2.png";
                    break;
            }      
        } 
        else
        {
            if(nb<=20)
                s="copInfected1.png";
            else
                s="copInfected2.png";
        }
        return s;
    }

public void run()
    {
        int t=pl.getTaille();
        while(active)
        {
                Position p=null;
                Random r=new Random();
                cmd=r.nextInt(5);
                int nbPas=r.nextInt(t);
                int i=0;
                do
                {
                    p=getPositionSuivante();
                    if(p!=null&&p.isInside(t))
                    {
                        deplacement(p);
                    }
                    i++;
                    sleep(600);
                }while(i<nbPas&&cmd!=0);
        }
    }

    private void sleep(int d)
    {
        try
        {
            Thread.currentThread().sleep(d);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public String toString()
    {
        return "PL";
    }
}
