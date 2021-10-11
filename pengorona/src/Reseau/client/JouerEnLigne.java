/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau.client;

import Interface.BoiteConfirmation;
import Outils.CommandeListener;
import Interface.Pengo;
import Joueur.Joueur;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinael
 */
public class JouerEnLigne{
    private String ipServer=null;
    private int portServer=2345;
    private Socket clientServer=null;
    private Joueur jr;
    
    public JouerEnLigne(String nom)
    {
        ipServer="127.0.0.1";
        jr=new Joueur(nom);
        try
        {
            clientServer=new Socket(ipServer,portServer);
        }
        catch(UnknownHostException e)
        {
                e.printStackTrace();
        }
        catch (IOException ex) 
        {
            Logger.getLogger(JouerEnLigne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JouerEnLigne(Joueur j,String add)
    {
        ipServer=add;
        jr=j;
        try
        {
            clientServer=new Socket(ipServer,portServer);
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
            Pengo.modeMultijoueur=false;
            jr.setConnected(false);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(JouerEnLigne.class.getName()).log(Level.SEVERE, null, ex);
             Pengo.modeMultijoueur=false;
             jr.setConnected(false);
        }
    }
    
    public void connect()
    {
        try {            
            int code=1;//code pour la connexion

            ObjectOutputStream oos=new ObjectOutputStream(clientServer.getOutputStream());
            
            //envoi du code de connexion
            oos.writeObject(code);
            oos.flush();
            
            //envoie du profil du joueur dans le server
            oos.writeObject(jr);
            oos.flush();
            
            ObjectInputStream ois=new ObjectInputStream(clientServer.getInputStream());
            
            //reponse du server sur le mode de jeu cooperatif ou match par equipe
            
            boolean rep=ois.readBoolean();
            Pengo.modeCooperatif=rep;
            jr.setConnected(true);
            jr.setIPServer(ipServer);
            
            int equipe=1;
            
            if(!Pengo.modeCooperatif)
            {
                BoiteConfirmation dlg=new BoiteConfirmation(null,true,"Choix de l'equipe","Equipe 1: pinguoin","Equipe 2 : virus");
                dlg.setVisible(true);
                equipe=dlg.getEquipe();//choix de l'equipe
   
            }
            
            oos.writeObject(equipe);//envoie de l'equipe choisi au serveur
            oos.flush();
            
            CommandeListener.port=(int)ois.readObject();//reception du port d'envoie des commande du joueur
            
            Pengo.modeMultijoueur=true;//changement du mode de jeu

            oos.close();
            ois.close();
            clientServer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JouerEnLigne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deconnexion()
    {
        if(jr.isConnected())
        {
            try {
                //envoie du joueur dans le server
                int code=2;//code pour se deconnecter

                ObjectOutputStream oos=new ObjectOutputStream(clientServer.getOutputStream());
                //envoie du profil du joueur dans le server

                oos.writeObject(code);//envoi du code au server
                oos.flush();

                //envoie du joueur au serveur
                oos.writeObject(jr);
                oos.flush();
                //reception des données sur le joueur de la part du serveur
                ObjectInputStream ois=new ObjectInputStream(clientServer.getInputStream());

                Joueur j=(Joueur)ois.readObject();//mise a jour du joueur local

                if(j!=null)
                    jr.setScore(j.getScore());//mise a jour du score ju joueur 

                Pengo.modeMultijoueur=false;

                oos.close();
                ois.close();
                clientServer.close();

            } catch (IOException ex) {
                //ex.printStackTrace();
                 Pengo.modeMultijoueur=false;
                 jr.setConnected(false);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(JouerEnLigne.class.getName()).log(Level.SEVERE, null, ex);
                 Pengo.modeMultijoueur=false;
                 jr.setConnected(false);
            }
        }
    }                   
}
