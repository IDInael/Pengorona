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
public class Virus extends Objet implements Runnable {
    
    private int virulence=200;
    
    public Virus()
    {
        super();
    }
    public Virus(Position p)
    {
        super(p);
    }
    
    public Virus(int v)
    {
        super();
        virulence=v;
    }
    
    public void setVirulence(int v)
    {
        virulence=v;
    }
    
    public int getVirulence()
    {
        return virulence;
    }
    
    public Virus(int x, int y)
    {
        super(x,y);
    }
    
    public String getImage()
    {
        nb=(++nb)%40;
        String s="";
        if(active)
        {
            if(nb<=20)
                s= "v"+virulence+"a.png";
            else
                s= "v"+virulence+"b.png";
        }
        else
        {
            if(nb<=20)
                s="vma.png";
            else
                s="vmb.png";
        }
        return s;
    }

    public void run()
    {
        int t=pl.getTaille();
        
        while(active)
        {
                if(this.jr!=null)
                {
                    Position p=getPositionSuivante();
                    if(p!=null&&p.isInside(t))
                    {
                        deplacement(p);
                    }
                    else
                    {
                        cmd=0;
                    }
                    sleep(300);
                }
                else
                {
                    Position p=null;
                    Random r=new Random();
                    cmd=r.nextInt(6);
                    if(cmd==5)
                    {
                        this.attack();
                        cmd=r.nextInt(4)+1;
                    }

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
                        sleep(800-virulence);
                    }while(i<nbPas&&cmd!=0); 
                }
        }
    }

    
    public void deplacement(Position p)
    {
        Objet ob=pl.getObjet(p);
        if(ob instanceof Antivirus)
        {
            sleep(40);
            ob.desactive();
            sleep(1000);
            swap(p);
            //sleep(100);
        }
        else
        {
            if(ob instanceof Secours)
            {
                ob.desactive();
                Joueur jr=ob.getJoueur();
                
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
            {
                if(ob instanceof Pompier||ob instanceof Police)
                {
                    ob.setInfected(true);
                    ob.setJoueur(this.jr);
                    
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
                    if(ob instanceof Pingouin&&!ob.isInfected())
                    {
                        ob.setInfected(true);
                        Joueur jr=ob.getJoueur();
                        
                        if(this.jr!=null)
                        {
                            this.jr.bonus(200);
                        }
                        if(online)
                        {
                            Serveur.message("le pingouin de "+jr.getPseudo()+" a été infecté");
                            Serveur.addSecours(jr);
                        }
                        else
                        {
                            Pengo.message("le pingouin de "+jr.getPseudo()+" a été infecté");
                            Pengo.addSecours();
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
                        if(ob==null)
                        {
                            swap(p);
                        }
                        else
                        {
                            if(ob instanceof Vaccin)
                            {
                                this.addMurs(pos);
                                cmd=(cmd+1)%4+1;
                            }
                            else
                            {
                                if(ob instanceof Murs)
                                {
                                    this.addBombe(pos,this.jr);
                                    cmd=(cmd+1)%4+1;
                                }
                                else
                                    cmd=0;    
                            }
                        }
                    }
                }
            }
        }
        
    }

    @Override
    public void attack() 
    {
        this.addBombe(pos, jr);
    }
    
    private void addMurs(Position p)
    {
        Thread th=new Thread(
                new Runnable()
                {
                    public void run()
                    {
                        sleep(1000);
                        pl.addMurs(p);
                    }
                }
        );
        th.start();
    }
    
    private void addBombe(Position p,Joueur jr)
    {
        Thread th=new Thread(
                new Runnable()
                {
                    public void run()
                    {
                        sleep(1000);
                        pl.addBombe(p,jr);
                    }
                }
        );
        th.start();
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
        return "**";
    }
}
