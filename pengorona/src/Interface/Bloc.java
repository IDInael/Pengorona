/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Outils.Position;
import Objets.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author dinael
 */
public class Bloc extends JButton implements Runnable{
    Position pos;
    Plateau pl;
    int port;
    
    public Bloc(Position p,Plateau plj)
    {
        super();
        pl=plj;
        pos=p;
        super.setBorder(BorderFactory.createEmptyBorder());
        this.setContentAreaFilled(false);
    }
    
    public void run()
    {
        while(true)
        {
            if(Pengo.modeMultijoueur)
            {
                DatagramSocket soc=null;
                try
                    {
                        soc=new DatagramSocket(port);//initialisation du socket
                        soc.setSoTimeout(3000);//temps d'attente maximum
                        
                        byte[] buffer=new byte[64];//initialisation du buffer pour la reception des paquets
                        
                        DatagramPacket paquet=new DatagramPacket(buffer,buffer.length);

                        soc.receive(paquet);//methode bloquant

                        ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);

                        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

                        String img=(String)ois.readObject();//assignation du string réçu à la variable img
                        if(img.equals("null"))
                        {
                            super.setIcon(null);//affichage d'un objet vide
                        }
                        else
                        {//importation de l'image et affichage dans la fenetre
                            super.setIcon(new ImageIcon(getClass().getResource("/Media/Images/"+img)));
                        }
                        //fermeture des flux
                        ois.close();
                        byteStream.close();
                        soc.close();
                    }
                    catch(SocketTimeoutException e)
                    {
                       // e.printStackTrace();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    } catch (ClassNotFoundException ex) 
                    {
                        Logger.getLogger(Bloc.class.getName()).log(Level.SEVERE, null, ex);
                    }
                finally
                {
                    if(soc!=null)
                        soc.close();
                }
                    
            }
            else
            {
                Objet ob=pl.getObjet(pos);
                if(ob!=null)
                {
                        super.setIcon(new ImageIcon(getClass().getResource("/Media/Images/"+ob.getImage())));
                }
                else
                {
                    super.setIcon(null);
                }
            }
            
            try
            {
                Thread.currentThread().sleep(10);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void setPort(int p)
    {
        port=p;
    }
}
