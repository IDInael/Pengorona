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
public class Pompier extends Objet implements Runnable {
    
    public Pompier()
    {
        super();
    }
    
    public Pompier(int i,int j)
    {
        super(i,j);
    }
    
    
    public void deplacement(Position p) {
        Objet ob=pl.getObjet(p);
        
        if(infected)//s'il est infecté
        {
            if(ob instanceof Secours)
            {
                ob.desactive();
                Joueur jr=ob.getJoueur();
                //Pengo.nbvie--;
                jr.mort();
                if(this.jr!=null)
                {
                    this.jr.bonus(200);
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
                                //Pengo.addSecours();
                                if(online)
                                {
                                    Serveur.message("le secours de "+jr.getPseudo()+" a été tué"); 
                                    Serveur.addSecours(jr);
                                }
                                else
                                {
                                    Pengo.addSecours();
                                    Pengo.message("le secours de "+jr.getPseudo()+" a été tué");   
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
            {
                if(ob instanceof Police)
                {
                    ob.setInfected(true);
                }
                else
                {
                    if(ob instanceof Pingouin&&!ob.isInfected())
                    {
                        ob.setInfected(true);
                        Joueur jr=ob.getJoueur();
                        
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
                        
                        //Pengo.addSecours();
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
                        if(ob==null)
                            swap(p);
                        else
                            cmd=0;
                    }
                }
            }
        }
        else
        {
            if(ob==null)
                swap(p);
            else
                cmd=0;
        }
    }

    @Override
    public void attack() {
    }

    @Override
    public String getImage() 
    {
        String s="";
        nb=(++nb)%40;
        
        if(infected||!active)
        {
            if(nb<20)
                s="pompiera.png";
            else
                s="pompierb.png";
        }
        else
        {
            if(nb<20)
                s="pompier1.png";
            else
                s="pompier2.png";
        }
        return s;        
    }
    
    private void AddAntivirus()
    {
        Position p=null;
        Random r=new Random();
        dir=r.nextInt(4)+1;
        switch(dir)
        {
            case 1:
                p=new Position(pos.getX(),pos.getY()+1);
                break;
            case 2:
                p=new Position(pos.getX()+1,pos.getY());
                break;  
            case 3:
                p=new Position(pos.getX(),pos.getY()-1);
                break;
            case 4:
                p=new Position(pos.getX()-1,pos.getY());
                break;
        }
        int t=pl.getTaille();
        if(p!=null&&p.isInside(t))
        {
            Objet ob=pl.getObjet(p);
            if(ob==null)
            {    
                  pl.addAntivirus(p);
            }
        }
    }

    @Override
    public void run() 
    {
        int t=pl.getTaille();
        while(active)
        {
            Position p=null;
            Random r=new Random();
            cmd=r.nextInt(7);
            
            if(cmd<5)
            {
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
            else
            {
                this.AddAntivirus();
                sleep(600);
            }
        }
    }
    
    private void sleep(int time)
    {
        try
        {
            Thread.currentThread().sleep(time);
        }
        catch(InterruptedException e )
        {
            e.printStackTrace();
        }
    }
    
    public String toString()
    {
        return "PP";
    }
}
