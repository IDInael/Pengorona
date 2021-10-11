/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

import Joueur.Equipe;
import Joueur.Joueur;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author dinael
 */
public class Test {
    public static void main(String args[])
    {
        Equipe e=new Equipe();
        
        Joueur j1,j2,j3,j4;
        j1=new Joueur("j1");
        j1.bonus(50);
        j2=new Joueur("j2");
        j2.bonus(120);
        j3=new Joueur("j3");
        j3.bonus(100);
        j4=new Joueur("j4");
        j4.bonus(110);
        
        e.AjoutJoueur(j1);
        e.AjoutJoueur(j2);
        e.AjoutJoueur(j3);
        e.AjoutJoueur(j4);
        
        System.out.println(e);
        System.out.println();
        e.miseAJourMeilleurJoueur();
        System.out.println(e);
        System.out.println(e.retirerJoueur(j4));
        System.out.println(e);
        
    }
}
