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

/**
 *
 * @author dinael
 */
public class Bombe extends Objet implements Runnable
{
    private int activation=3000;
    
    private boolean explosion=false;
    
    public Bombe(int i,int j)
    {
        super(i,j);
    }
    
    public Bombe(Position p,int t)
    {
        super(p);
        activation=t;
    }
    
    public void deplacement(Position p) {
    }

    @Override
    public void attack()
    {
        try
        {
        int i=pos.getX();
            int j=pos.getY();
            this.explosion=true;
            //explosion d'une durée de 3 seconde
            for(int k=0;k<3;k++)
            {
                explosion(i-1,j-1);
                explosion(i-1,j);
                explosion(i-1,j+1);
                explosion(i,j-1);
                explosion(i,j+1);
                explosion(i+1,j-1);
                explosion(i+1,j);
                explosion(i+1,j+1);
                Thread.currentThread().sleep(1000);
            }
        }
        catch(InterruptedException ex)
        {
            
        }
    }

    @Override
    public String getImage() 
    {
        String s="";
        
        if(!explosion)
            s="bomb.png";
        else
        {
            nb=(++nb)%60;
            if(nb<20)
                s="exp0.png";
            else
            {
                if(nb<40)
                    s="exp1.png";
                else
                    s="exp2.png";
            }
        }
        return s;
    }

    @Override
    public String toString() 
    {
        return "b";
    }

    @Override
    public void run() 
    {
        try
        {
            Thread.currentThread().sleep(activation);
            
            attack();
            pl.setObjet(pos, null);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private void explosion(int i,int j)
    {
        Position p=new Position(i,j);
        if(p.isInside(pl.getTaille()))
        {
            Objet ob=pl.getObjet(p);
            //l'explosion affecte seulement un pingouin non infecté
            if(ob instanceof Pingouin&&ob.isActive()&&!ob.isInfected())
            {
                ob.desactive();
                Joueur joueur=ob.getJoueur();
                joueur.mort();
                if(this.jr!=null)
                {
                    this.jr.bonus(200);
                }
                
                if(joueur.getNbVie()>0)
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
                                    Serveur.addPingouin(joueur);
                                    Serveur.message("le pinguoin de "+joueur.getPseudo()+" a été tué par la bombe");
                                }
                                else
                                {
                                    Pengo.message("le pinguoin de "+joueur.getPseudo()+" a été tué par la bombe");
                                    Pengo.addPingouin();
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
            }
        }
    }
    
}
