/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;
import Interface.Pengo;
import Reseau.server.Serveur;

/**
 *
 * @author dinael
 */
public class Antivirus extends Objet implements Runnable {
        
    public Antivirus()
    {
        super();
        active=true;
    }
    public Antivirus(Position p)
    {
        super(p);
        active=true;
    }
    public Antivirus(int x, int y)
    {
        super(x,y);
        active=true;
    }
    
    
    public String getImage()
    {
        String s="";
        if(active)
        {
            if(cmd==0)
                s="an.png";//antivirus
            else
                s="ana.png";//antivirus en attaque
        }
        else
        {
            nb=(++nb)%40;
            if(nb<=20)
                s="ama.png";
            else
                s="amb.png";
        }
        return s;      
    }
    
    
    @Override
    public void deplacement(Position p)
    {   
        Objet ob=pl.getObjet(p);
        if(ob instanceof Antivirus)
        {
            active=false;
            sleep(1000);
            swap(p);
            this.active=true;
            cmd=0;
        }
        else
        {
            if(ob instanceof Virus)
            {
                ob.desactive();
                int v=((Virus) ob).getVirulence();
                //Pengo.joueur.bonus(v);
                this.jr.bonus(v);
                addNewVirus(p,v);
                
                if(online)
                {
                    Serveur.message("virus tué: bonus de"+v+" pour "+jr.getPseudo());
                }
                else
                {
                    Pengo.message("virus tué: bonus de"+v+" pour "+jr.getPseudo());
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
                    ob.desactive();
                    if(ob.isInfected())
                    { 
                        this.jr.bonus(50);
                        if(online)
                        {
                            Serveur.message("civil infecté tué: bonus de 50 pour "+jr.getPseudo());
                        }
                        else
                        {
                            Pengo.message("civil infecté tué: bonus de 50 pour "+jr.getPseudo());
                        }
                        //Pengo.joueur.bonus(50);
                    }
                    else
                    {
                        jr.amende(50);
                        if(online)
                        {
                            Serveur.message("civil sain tué: amende de 50 pour "+jr.getPseudo());
                        }
                        else
                        {
                            Pengo.message("civil sain tué: amende de 50 pour "+jr.getPseudo());
                        }
                    }
                    
                    if(ob instanceof Pompier)
                        this.addNewPompier(p);
                    else
                        this.addNewPolice(p);
                    
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
                    if(ob==null)
                        swap(p);
                    else
                        cmd=0;   
                }

            }
        }
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
    
        @Override
    public void attack() {
    }
    
    
    public void run()
    {
        runCode(300);
    }
    
    private void addNewVirus(Position p,int v)
    {
        Thread th=new Thread(
                            new Runnable() {
                        public void run() {
                            try
                            {
                                Thread.currentThread().sleep(1000);
                                pl.setObjet(p, null);//suppression du virus
                                Thread.currentThread().sleep(1000);
                                pl.addVirus(v);
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
    
    private void addNewPompier(Position p)
    {
        Thread th=new Thread(
                            new Runnable() {
                        public void run() {
                            try
                            {
                                Thread.currentThread().sleep(1000);
                                pl.setObjet(p, null);//suppression du virus
                                Thread.currentThread().sleep(1000);
                                pl.addPompier();
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
        
    private void addNewPolice(Position p)
    {
        Thread th=new Thread(
                            new Runnable() {
                        public void run() {
                            try
                            {
                                Thread.currentThread().sleep(1000);
                                pl.setObjet(p, null);//suppression du virus
                                Thread.currentThread().sleep(1000);
                                pl.addPolice();
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
    
    public String toString()
    {
        return "<>";
    }
}
