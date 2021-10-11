/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau.server;

import Joueur.Joueur;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dinael
 */
public class Connexion implements Runnable{

    private Joueur jr;
    private DatagramSocket socket=null;
    private int portEcoute;
    
    public Connexion(Joueur jr,int portE)
    {
        this.jr=jr;
        this.portEcoute=portE;
        try 
        {
            socket=new DatagramSocket(portEcoute);
            socket.setSoTimeout(3000);
        } 
        catch (SocketException ex) 
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() 
    {
        while(jr.isConnected())
        {
            try
            {
                byte[] buffer=new byte[256];

                DatagramPacket paquet=new DatagramPacket(buffer,buffer.length);//initialisation du paquet
                socket.receive(paquet);//reception du paquet

                    ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
                    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));//ouverture du flux de lecture

                    int cmd=(int)ois.readObject();//lecture du flux
                    
                    if(jr.getObjet()!=null)
                    {
                        if(cmd!=5)
                            jr.getObjet().setCommande(cmd);//envoie du commande a l'objet controlé par le joueur
                        else
                        {
                                jr.getObjet().attack();
                        }
                    }
                    ois.close();
                    
                Thread.currentThread().sleep(15);
            }
            catch(SocketTimeoutException e)
            {
                
            }
            catch (IOException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        socket.close();
    }
}
