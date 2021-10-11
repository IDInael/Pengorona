/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueur;

import Objets.*;
import Reseau.client.JouerEnLigne;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author dinael
 */
public class Joueur implements Serializable{
    private String pseudo;
    private int score;
    private InetAddress ip;
    private Objet ob=null;
    private int nbVie=3;
    private boolean connected;
    private String ipServer;
    
    public Joueur(String nom)
    {
        pseudo=nom;
        score=0;
        connected=false;
        ipServer=null;
        try
        {
            ip=InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
    
        public Joueur()
    {
        pseudo="MonJoueur";
        score=0;
        connected=false;
        ipServer=null;
        try
        {
            ip=InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
    
    public int getScore(){return score;}
    
    public void bonus(int s)
    {
        score+=s;
    }
    
    public void amende(int s)
    {
        if(score-s>0)
            score-=s;
        else
            score=0;
    }
    
    public String getIP()
    {
        return ip.getHostAddress();
    }
    
    public InetAddress getAdresseIP()
    {
        return ip;
    }
    
    public void setPseudo(String s)
    {
        pseudo=s;
    }
    
    public String getPseudo(){return pseudo;}

    
    public String toString()
    {
        return pseudo+" : "+score+" $ :: "+nbVie+" vie(s)";
    }
    
    public void setObjet(Objet ob)
    {
        
        if(ob!=null)
        {
            this.ob=ob;
            ob.setJoueur(this);
        }
        
    }
    
    public Objet getObjet()
    {
        return this.ob;
    }
    public void mort()
    {
        nbVie--;
    }
    
    public void setNbVie(int t)
    {
        nbVie=t;
    }
    
    public int getNbVie()
    {
        return nbVie;
    }
    
    public void setConnected(boolean b)
    {
       connected=b;
    }
    
    public boolean isConnected()
    {
        return connected;
    }
    
    public void seConnecter(String add)
    {
        connected=true;
        JouerEnLigne jeu=new JouerEnLigne(this,add);
        jeu.connect();
    }
    
    public void setIPServer(String add)
    {
        this.ipServer=add;
    }
    
    public void seDeconnecter()
    {
        if(connected)
        {
            JouerEnLigne jeu=new JouerEnLigne(this,ipServer);
            jeu.deconnexion();
            connected=false;
        }
    }
    
    public void setScore(int s)
    {
        score=s;
    }
    
    
    public boolean equals(Joueur jr)
    {
        return this.pseudo.equals(jr.getPseudo());
    }
    
    public void miseAjourIP()
    {
        try
        {
            ip=InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
