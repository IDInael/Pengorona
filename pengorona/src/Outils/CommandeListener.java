/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

import Interface.Pengo;
import Joueur.Joueur;
import Objets.Objet;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinael
 */
public class CommandeListener implements KeyListener {
    
    private Joueur jr;
    public static int port=2344;
    
    public CommandeListener(Joueur j)
    {
        jr=j;
    }

    public CommandeListener()
    {
        jr=null;
    }    
    
    public void keyTyped(KeyEvent e) 
    {    
        if(jr!=null)
        {
            int cmd=decodeCommande(e);
            //si mode multijoueur envoyer la commande au serveur
            if(Pengo.modeMultijoueur)
            {   
                sendCommande(cmd);
            }
            else
            {
                Objet o=jr.getObjet();//si l'objet n'est pas null
                if(o!=null)
                {
                    if(cmd==5)
                        o.attack();
                    else
                        o.setCommande(cmd);
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) 
    {
        if(jr!=null)
        {
            int cmd=decodeCommande(e);
            //si mode multijoueur envoyer la commande au serveur
            if(Pengo.modeMultijoueur)
            {   
                sendCommande(cmd);
            }
            else
            {
                Objet o=jr.getObjet();
                if(o!=null)
                {
                    if(cmd==5)
                        o.attack();
                    else
                        o.setCommande(cmd);
                }
            }
        }
    }
    
    public void setObjet(Objet o)
    {
        jr.setObjet(o);
    }

    public void keyReleased(KeyEvent e) {
        if(jr!=null)
        {
            if(Pengo.modeMultijoueur)
                sendCommande(0);
            else
            {
                if(jr.getObjet()!=null)
                    jr.getObjet().setCommande(0);
            }
        }
        
    }
    
    private int decodeCommande(KeyEvent e)
    {
        int cmd=0;
        switch(e.getKeyCode())
        {
            case VK_RIGHT://fleche droite pour avancer
                cmd=1;
                break;
            case VK_DOWN://fleche bas pour descendre
                cmd=2;
                break;
            case VK_LEFT://flèche gauche pour reculer
                cmd=3;
                break;
            case VK_UP://fleche haut pour monter
                cmd=4;
                break;
            case VK_SPACE://espace pour attaquer
                cmd=5;
                break;
        }
        return cmd;
    }
    
    public void sendCommande(int cmd)
    {
        try 
        {
            DatagramSocket soc=new DatagramSocket();//instanciation du serveur UDP
            ByteArrayOutputStream flux=new ByteArrayOutputStream(256);
            ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream(flux));
            oos.flush();
            oos.writeObject(cmd);//ecriture de l'objet
            oos.flush();

            byte[] fluxEnvoi=flux.toByteArray();
            DatagramPacket paquet=new DatagramPacket(fluxEnvoi,fluxEnvoi.length,Pengo.ipServer,port);
            //envoi du paquet
            soc.send(paquet);
            //fermeture des canaux
            oos.close();
            soc.close();
        } catch (SocketException ex) {
            Logger.getLogger(CommandeListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommandeListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
