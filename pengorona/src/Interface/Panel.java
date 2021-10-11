/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Outils.CommandeListener;
import Outils.Position;
import Joueur.Joueur;
import Objets.*;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author dinael
 */
public class Panel extends JPanel{
    
    private Plateau pl;
    private CommandeListener controle;
    private int t;
    //le premier numero de port associé au nombre de bouton
    int portDeb=2347;
    //private boolean online=false;
    
    public Panel()
    {
        super();
        t=10;
        initPanel();
        pl=new Plateau(t);
        controle=new CommandeListener();
        this.addKeyListener(controle);
    }
    
    public Panel(Plateau pl,Joueur jr)
    {
        super();
        t=pl.getTaille();
        this.pl=pl;
        controle=new CommandeListener(jr);
        initPanel();
        this.addKeyListener(controle);
    }
    
    private void initPanel()
    {
        int port=portDeb;
        this.setBackground(new java.awt.Color(254, 254, 254));
        this.setLayout(new java.awt.GridLayout(1, 1));
        this.setLayout(new GridLayout(t,1));//nombre de ligne à la taille du plateau
        
        JPanel pan;
        Bloc b;
        
        for(int i=0;i<t;i++)
        {
            pan=new JPanel();
            pan.setLayout(new GridLayout(1,t));//nb de colonne à la taille du plateau
            pan.setBackground(new Color(254,254,254));
            for(int j=0;j<t;j++)
            {
                b=new Bloc(new Position(i,j),pl);//ajout des Blocs à la position i,j
                b.setPort(port);
                pan.add(b);
               Thread th=new Thread(b);//thread affichant le contenu de chaque bouton
               th.start();
               
               port++;
            }
            this.add(pan);
        }
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    
    public void addPingouin()
    {
        Pingouin p=new Pingouin();
        controle.setObjet(p);
        pl.addPingouin(p);
    }
    
    public void addVirus()
    {
        pl.addVirus(100);
        pl.addVirus(100);
        pl.addVirus(200);
        pl.addVirus(200);
        pl.addVirus(300);
        pl.addVirus(300);
        pl.addVirus(400);
    }
    
    public void addPompier(int nb)
    {
        for(int i=0;i<nb;i++)
            pl.addPompier();
    }
    
    public void addPolice(int nb)
    {
        for(int i=0;i<nb;i++)
            pl.addPolice();
    }
    
    public void newPartie()
    {
        pl.initPlateau();
        this.addPingouin();
        this.addVirus();
        this.addPompier(5);
        this.addPolice(5);
    }
    
    public void setCommandeObjet(Objet ob)
    {
        controle.setObjet(ob);
    }
    
    public void addSecours()
    {
        Secours s=new Secours();
        controle.setObjet(s);
        pl.addSecours(s);
    }
    
  /*  public void setOnline(boolean b)
    {
        online=b;
    }*/
}
