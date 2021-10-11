/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueur;

import java.net.InetAddress;
import java.util.Vector;

/**
 *
 * @author dinael
 */
public class Equipe {
    private String nom;
    private Vector<Joueur> membre;
    private String journal;
    private int score;
    
    public Equipe()
    {
        nom="Mon Equipe";
        membre=new Vector<Joueur>();
        journal="";
        score=0;
    }
    
    public Equipe(String name)
    {
        nom=name;
        membre=new Vector<Joueur>();
        journal="";
        score=0;
    }
    
    public String getNom()
    {
        return nom;
    }
    
    public Joueur getJoueurAt(int i)
    {
        if(i<membre.size())
            return membre.get(i);
        else
            return null;
    }
    
    public void AjoutJoueur(Joueur j)
    {
        membre.add(j);
    }
    
    public int getScore()
    {
        int sc=score;
        for(Joueur j:membre)
        {
            sc+=j.getScore();
        }
        return sc;
    }
    
    public void setScore(int sc)
    {
        score=sc;
    }
    
    public void amende(Joueur j,int somme)
    {
        System.out.println(j.getPseudo()+" a pris une amende de "+somme);
        j.amende(somme);
    }

     public void bonus(Joueur j,int somme)
    {
        System.out.println(j.getPseudo()+" a pris un bonus de "+somme);
        j.bonus(somme);
    }
     
     public String toString()
     {
         //String res="Membre de l'equipe "+membre.size()+": \n";
         String res=this.nom+" : score: "+getScore()+"\n";
         for(Joueur j:membre)
             res+="- "+j+"\n";
         return res;
     }
     
     public int getNbJoueur()
     {
         return membre.size();
     }
     
     public InetAddress getIpAt(int i)
     {
        return membre.get(i).getAdresseIP();
     }
     
     public void resetNbVie()
     {
         for(Joueur j:membre)
             j.setNbVie(3);
     }
     
     public int getNbVie()
     {
         int res=0;
         for(Joueur j:membre)
             res+=j.getNbVie();
         return res;
     }
     
     public boolean estDans(Joueur j)
     {
         for(Joueur jr:membre)
         {
             if(jr.equals(j))
                 return true;
         }
         return false;
     }
     
     public String getJournal()
     {
         return journal;
     }
     
     public void ecrireJournal(String s)
     {
         journal+=s+"\n";
     }
     
     public void quickSort( int low, int high) 
    { 
        if (low < high) 
        { 
            int pi = partition(low, high); 
            quickSort(low, pi-1); 
            quickSort(pi+1, high); 
        } 
    } 
    private int partition(int low, int high) 
    { 
        int pivot = membre.get(high).getScore();  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            if (membre.get(j).getScore() > pivot) 
            { 
                i++;
                Joueur temp = membre.get(i); 
                membre.set(i, membre.get(j)); 
                membre.set(j, temp); 
            } 
        } 
  
        Joueur temp = membre.get(i+1); 
        membre.set(i+1, membre.get(high)); 
        membre.set(high, temp);
        return i+1; 
    }
    
    public void miseAJourMeilleurJoueur()
    {
        this.quickSort(0,membre.size()-1);
    }
    
    public Joueur retirerJoueur(Joueur j)
    {
        for(Joueur jr:membre)
        {
            if(jr.equals(j))
            {
                Joueur njr=jr;
                membre.remove(jr);
                return njr;
            }
        }
        return null;
    }
    
    
}
