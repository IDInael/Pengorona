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
public class Pingouin extends Objet implements Runnable {
        
    public Pingouin()
    {
        super();
    }
    
    public Pingouin(Position p) 
    {
        super(p);
    }

    public Pingouin(int x, int y) 
    {
        super(x, y);
    }
    
    
    public void deplacement(Position p)
    {   Objet ob=pl.getObjet(p);
        if(!infected)
        {
            if(ob instanceof Antivirus)
            {
                Antivirus c=(Antivirus)ob;
                c.setJoueur(this.jr);//lui affecté le joueur qui lui a lancé
                c.setCommande(cmd);//faire deplacer l'objet suivant la direction du pingouin
                Thread th=new Thread(c);
                th.start();  //lancement du thread de l'objet antivirus
            }
            else
            {
                if(ob instanceof Vaccin)
                {
                    Vaccin d=(Vaccin)ob;
                    d.setCommande(cmd);//donner la direction du vaccin
                    Thread th=new Thread(d);
                    th.start();//lancement du déplacement du vaccun
                }
                else
                {
                    if(ob==null)
                    {
                        swap(p);//ecraser
                    }
                }
            }
        }
        else//si le pingouin est infecté
        {
            if(ob==null)
                swap(p);
            else
            {
                if(ob instanceof Police||ob instanceof Pompier)
                {
                    ob.setInfected(true);//infection des polices et pompier
                    cmd=0;
                }
            }
        }
    }
    

    public String getImage()
    {
        String s;
        nb=(++nb)%40;
        if(active&&!infected)
        {
            switch(cmd)
            {
                case 1:
                    s="p1.png";break;
                case 2:
                    s= "p2.png";break;
                case 3:
                    s= "p3.png";break;
                case 4:
                    s= "p4.png";break;
                default:
                    if(nb<=20)
                        s= "pa.png";
                    else
                        s= "pb.png";
            }      
        } 
        else
        {
            if(nb<=20)
                s="pma.png";
            else
                s="pmb.png";
        }
        return s;
    }
    
    public void run()
    {
        int t=pl.getTaille();
        Random r=new Random();
        while(active)
        {
            if(infected)//si infecté, le programme controle le pingouin
            {
                //code deplacement automatique
                Position p=null;
                cmd=r.nextInt(5);//choix de la direction au hasard
                int nbPas=r.nextInt(t);//nb de deplacement au hasard
                int i=0;
                do
                {
                    p=getPositionSuivante();
                    if(p!=null&&p.isInside(t))
                    {
                        deplacement(p);
                    }
                    i++;
                    sleep(500);
                }while(i<nbPas&&cmd!=0);
            }
            else//code deplacement controlé par l'utilisateur
            {
                if(cmd!=0)
                {
                    dir=cmd;
                }
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
    
    public void attack()
    {
        if(this.jr.getScore()>=100)
        {
            Position p=null;
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
                if(ob instanceof Antivirus||ob instanceof Murs)
                {    
                    ob.desactive();
                    destruction(p); //destruction du murs ou de l'antivirus
                    if(ob instanceof Murs)
                    {
                        jr.amende(100);
                        if(online)
                        {
                            //ecriture dans le journal du serveur
                            Serveur.message(jr.getPseudo()+": destruction bien public : amende 100$ ");
                            
                        }
                        else
                        {
                            //ecriture dans le journal des evenements local
                            Pengo.message(jr.getPseudo()+": destruction bien public : amende 100$ ");
                        }
                    }
                }
            }
        }
        else
        {
            if(online)
                Serveur.message(jr.getPseudo()+" n'a pas assez d'argent pour payer l'amende ");
            else
                Pengo.message(jr.getPseudo()+" n'a pas assez d'argent pour payer l'amende ");
        }
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
    
    public String toString()
    {
        return "()";
    }
}
