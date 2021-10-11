/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau.server;

import Joueur.Equipe;
import Joueur.Joueur;
import Objets.*;
import Outils.Chrono;
import Outils.Port;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author dinael
 */
public class Serveur 
{
    private Equipe equipe;
    private Equipe equipe2;
    
    private static Equipe listeJoueur=new Equipe();//liste de tous les joueurs connectés
    private static String journal="";
    public volatile static Plateau pl;
    
    private boolean actif;
    private boolean jeuEnCours;
    private boolean winner;
    private boolean gameOver;
    private int port=2345;
    private int portEchange=2320;
    private String ip;
    private ServerSocket server=null;
    private int fileAttente=10;
    private int taille=28;
    private Chrono chrono;
    private boolean modeCooperatif;//si oui une seul equipe, si non deux equipe s'affronte
    
    
    
    

    public Serveur()
    {
        
        chrono=new Chrono();
        actif=true;
        equipe= new Equipe("Equipe des Pinguoins ");
        equipe2=new Equipe("Equipe des Virus");
        
        winner=false;
        gameOver=false;
        pl=new Plateau(taille);
        pl.setOnline(true);
        jeuEnCours=false;
        modeCooperatif=true;
        
        try 
        {
            ip=InetAddress.getLocalHost().getHostAddress();
            server=new ServerSocket(port,fileAttente,InetAddress.getLocalHost());
        } 
        catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : lancement du serveur "+this.ip;
    }
    
    public void setModeCooperatif(boolean b)
    {
        this.modeCooperatif=b;
    }
    
    public boolean getModeCooperatif()
    {
        return this.modeCooperatif;
    }
    
    public void open()
    {
        Thread th=new Thread(new Runnable()
                {
                    public void run()
                    {
                        while(actif)
                        {
                            try
                            {
                                //protocole TCP
                                //attente de la connexion d'un client
                                server.setSoTimeout(10000);
                                Socket socketJoueur=server.accept();
                                
                                ObjectInputStream ois=new ObjectInputStream(socketJoueur.getInputStream());//flux de reception
                                ObjectOutputStream oos=new ObjectOutputStream(socketJoueur.getOutputStream());//flux d'envoie
                                //une fois reçue, on la traite dans un thread separé
                                
                                //identification de l'intention du client et reception de son profil
                                //int code =0;
                                int code=(int)ois.readObject();
                                Joueur j=(Joueur)ois.readObject();
                                if(code==1)//le joueur veut se rejoindre a la partie
                                {
                                    j.setConnected(true);
                                    if(!listeJoueur.estDans(j))
                                    {
                                        listeJoueur.AjoutJoueur(j);//ajout dans la liste des joueurs connecté
                                    }

                                    //reponse au client: le serveur envoi le mode de jeu
                                    
                                    oos.writeBoolean(modeCooperatif);
                                    oos.flush();

                                    //reponse du joueur sur l'equipe
                                    int team=(int)ois.readObject();
                                   // System.out.println(team);
                                    if(team==1)
                                    {         //s'il n'est pas dans l'equipe on l'ajoute
                                        if(!equipe.estDans(j))
                                            equipe.AjoutJoueur(j);
                                        //si le jeu a deja commencé et qu'un nouveau joueur arrive
                                        if(jeuEnCours)
                                            addPingouin(j);
                                    }
                                    else
                                    {
                                        modeCooperatif=false;
                                        if(!equipe2.estDans(j))
                                            equipe2.AjoutJoueur(j);
                                        if(jeuEnCours)
                                            addVirusControlable(j);
                                    }
                                    //envoie au joueur du port par laquelle il devrait dialoguer avec le serveur pour les commandes
                                    oos.writeObject(portEchange);
                                    oos.flush();
                                    //envoie des paquets
                                    Connexion con=new Connexion(j,portEchange);
                                    Thread th=new Thread(con);
                                    th.start();

                                    portEchange++;//chaque joueur a sa propre port d'echange
                                    journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : "+j+" vient de se connecter";
                                }
                                else
                                {
                                    if(code==2)//le joueur veut se deconnecter
                                    {
                                        Joueur njr=listeJoueur.retirerJoueur(j);
                                        njr.setConnected(false);
                                        //desactivation de l'objet du joueur
                                        if(njr.getObjet()!=null)
                                        {
                                            njr.getObjet().setActive(false);
                                            njr.setObjet(null);
                                        }
                                        
                                        oos.writeObject(njr);
                                        journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : "+njr+" a quitter la partie";
                                        
                                        if(equipe.estDans(njr))
                                        {
                                            Joueur tmp=equipe.retirerJoueur(njr);
                                        }
                                        else
                                        {
                                            if(equipe2.estDans(njr))
                                            {
                                                Joueur tmp=equipe2.retirerJoueur(njr);
                                            }
                                        }
                                    }
                                }
                                
                                oos.close();
                                ois.close();
                                socketJoueur.close();
                            }
                            catch(IOException e)
                            {
                                    //e.printStackTrace();
                            } 
                            catch (ClassNotFoundException ex) {
                               // ex.printStackTrace();
                            }
                            
                        }


                        try
                        {

                                server.close();

                        }
                        catch(IOException e)
                        {
                                e.printStackTrace();
                                server=null;
                        }
                    }	
                });

		//lancement du thread;
		th.start();
    }
    
    public String getAdresse()
    {
        return server.getInetAddress().getHostAddress();
    }
    
    public String affichage()
    {
        String s=server.getInetAddress().getHostAddress()+":\n";
        s+=equipe.toString()+"\n\n";
        s+=equipe2.toString();
        return s;
    }
    
    public void close()
    {
        Timer cptRebours=new Timer();//lance le compte à rebours pour la fermeture
        cptRebours.schedule(new TimerTask() 
        {
            int time=10;
            public void run() 
            {
               if(time==0)
               {
                   message("fermeture du serveur");
                   cancel();
                   try {
                        server.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    for(int i=0;i<listeJoueur.getNbJoueur();i++)
                    {
                        Joueur j= listeJoueur.getJoueurAt(i);
                       j.setConnected(false);
                       if(j.getObjet()!=null)
                       {
                        j.getObjet().setActive(false);
                        j.setObjet(null);
                       }
                    }actif=false;
                    gameOver=true;//mettre fin au jeu
               }
               else
               {
                   //prevenir les joueurs
                   message("Fermeture dans "+time+"s : deconnectez-vous!");
               }
               time--;
            }
        }, 1000,1000);
        this.enregistrementJournal();
        
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
    
    public static void addPingouin(Joueur j)
    {
        Pingouin p=new Pingouin();
        j.setObjet(p);
        pl.addPingouin(p);
        
    }
    
    public static void addVirusControlable(Joueur j)
    {
        Virus p=new Virus();
        j.setObjet(p);
        pl.addVirus(p);
        
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
    
    public static void addSecours(Joueur j)
    {
        Secours s=new Secours();
        j.setObjet(s);
        pl.addSecours(s);
    }
    
    public void initJeu()
    {
        pl.initPlateau();
        this.addVirus();
        this.addPompier(5);
        this.addPolice(5);
        
        Thread cleaner=new Thread(new Runnable(){
            public void run()
            {
                while(!winner&&!gameOver)
                    pl.nettoyer();
            }
        });
        cleaner.start();
    }
    
    private void sleep(int t)
    {
        try
        {
            Thread.currentThread().sleep(t);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public void lancerStreaming()     
    {
        Thread th1=new Thread(new Runnable()
        {
            public void run()
            {
                int portEnvoi=2347;
        
                int t=pl.getTaille();
                for(int i=0;i<t;i++)
                    for(int j=0;j<t;j++)
                    {
                        //thread qui envoie l'image dans le bouton à la position i,j dans le plateau de jeu
                        sendImage(i,j,portEnvoi);
                        portEnvoi++;
                    }
            }
        });
        th1.start();
    }
    
   private void sendImage(int i, int j,int p)
    {
        Thread envoi=new Thread(new Runnable()
        {
            public void run()
            {
                String imgEnvoye="";
                int cpt=0;
                while(actif)
                {
                    try
                    {
                        //instance du flix d'ecriture
                       Objet ob=pl.getObjet(i,j);
                       String img="null";
                       if(ob!=null)
                       {
                            img=ob.getImage();
                       }
                       
                       if(!img.equals(imgEnvoye)||cpt==0)
                       {
                        //si l'image a changé on l'envoie   
                            DatagramSocket soc=new DatagramSocket();
                            ByteArrayOutputStream flux=new ByteArrayOutputStream(64);
                            ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream(flux));
                            oos.flush();
                            oos.writeObject(img);//envoie du nom de l'image l'image
                            oos.flush();

                            byte[] fluxEnvoi=flux.toByteArray();
                            int t=listeJoueur.getNbJoueur();
                            //on envoie l'image à tous le joueurs connecté
                            for(int i=0;i<t;i++)
                            {
                                DatagramPacket paquet=new DatagramPacket(fluxEnvoi,fluxEnvoi.length,listeJoueur.getIpAt(i),p);
                                soc.send(paquet);
                            }
                            
                           oos.close();
                            soc.close();
                      }
                       imgEnvoye=img;
                       Thread.currentThread().sleep(17);
                    }
                    catch(UnknownHostException e)
                    {
                        e.printStackTrace();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }   
                    cpt=(++cpt)%100;
                }
                
            }
        });
        envoi.start();
    }
   
   public void newPartie()
    {   
        journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : lancement d'une nouvelle partie";
        equipe.resetNbVie();//reinitialisation du nombre de ie de l'equipe
        equipe.setScore(0);
        winner=false;
        gameOver=false;
        
        pl.gameOver();//nettoyage du plateau avant de lancer une nouvelle partie
        sleep(1000);
        initJeu();//initialisation du plateau pour recevoir une nouvelle partie
        
        for(int i=0;i<equipe.getNbJoueur();i++)
            this.addPingouin(equipe.getJoueurAt(i));//ajout des pinguoins
        
        if(!this.modeCooperatif)
        {
            for(int i=0;i<equipe2.getNbJoueur();i++)
                this.addVirusControlable(equipe2.getJoueurAt(i));//ajout des virus controlable
        }
        
        jeuEnCours=true;
        
        lancerChrono();//lancement du chrono
        sendStatueEquipe();
        
        Thread partiePerdu=new Thread(
               new Runnable()
               {
                   public void run()
                   {
                       while(!gameOver&&!winner)
                       {
                           if(equipe.getNbVie()<=0)
                           {
                               
                                try
                                {
                                    Thread.currentThread().sleep(1000);
                                    pl.gameOver();
                                    Thread.currentThread().sleep(1000);
                                    pl.gameOver();
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                    gameOver=true;
                                    chrono.Stop();
                                    jeuEnCours=false;
                                    journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : fin de la partie";
                               
                           }
                           try
                           {
                               Thread.currentThread().sleep(200);
                           }
                           catch(InterruptedException e)
                           {
                               e.printStackTrace();
                           }
                       }
                   }
               }
        );
        partiePerdu.start();//thread qui mettra fin a la partie au cas l'equipe perd

        Thread partieGagnantH=new Thread(
               new Runnable()
               {
                   public void run()
                   {
                       
                       while(!gameOver&&!winner)
                       {   
                           if(pl.isWinHLine())
                           {
                               
                                try
                               {
                                   Thread.currentThread().sleep(1000);
                                   pl.gameOver();
                                   Thread.currentThread().sleep(1000);
                                   pl.gameOver();
                               }
                               catch(InterruptedException e)
                               {
                                   e.printStackTrace();
                               }
                                winner=true;
                                chrono.Stop();
                                equipe.setScore(chrono.getBonus());
                                jeuEnCours=false;
                                journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : victoire de l'equipe 1";
                           }
                           try
                           {
                               Thread.currentThread().sleep(200);
                           }
                           catch(InterruptedException e)
                           {
                               e.printStackTrace();
                           }
                       }
                   }
               }
        );
        partieGagnantH.start();//thread qui mettra fin a la partie en cas de victoire
        
        Thread partieGagnantV=new Thread(
               new Runnable()
               {
                   public void run()
                   {
                       
                       while(!gameOver&&!winner)
                       {   
                           if(pl.isWinVLine())
                           {
                                try
                                {
                                    Thread.currentThread().sleep(1000);
                                    pl.gameOver();
                                    Thread.currentThread().sleep(1000);
                                    pl.gameOver();
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                winner=true;
                                chrono.Stop();
                                equipe.setScore(chrono.getBonus());
                                jeuEnCours=false;
                                journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : victoire de l'equipe 1";
                           }
                           try
                           {
                               Thread.currentThread().sleep(200);
                           }
                           catch(InterruptedException e)
                           {
                               e.printStackTrace();
                           }
                       }
                   }
               }
        );
        partieGagnantV.start();
        
        Thread affichage=new Thread(
                    new Runnable()
                    {
                        public void run()
                        {
                            boolean fin=false;
                            while(!fin)
                            {
                                //mise à jour meilleur joueur
                                equipe.miseAJourMeilleurJoueur();
                                equipe2.miseAJourMeilleurJoueur();
                                try
                                {
                                    Thread.currentThread().sleep(1000);
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                
                                if(gameOver)
                                {
                                    pl.gameOver();
                                    try
                                    {
                                        Thread.currentThread().sleep(500);
                                    }
                                    catch(InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    pl.afficherGameOver();
                                    fin=true;
                                }
                                else
                                {
                                    if(winner)
                                    {
                                        pl.gameOver();
                                        try
                                        {
                                            Thread.currentThread().sleep(500);
                                        }
                                        catch(InterruptedException e)
                                        {
                                            e.printStackTrace();
                                        }
                                        pl.afficherWinner();
                                        fin=true;
                                    }
                                }
                            }
                        }
                    }
                );
                affichage.start();
                
        chrono.Stop();
        chrono=new Chrono();
        chrono.start();
    }
    
   
   public void lancerChrono()
   {
       Thread chr=new Thread(
               new Runnable()
               {
                   public void run()
                   {
                       while(jeuEnCours)
                       {
                            sendMessage(chrono.toString(), Port.portChrono);
                            sleep(200);
                       }
                   }
               }
       );
       chr.start();
   }
   
   public void sendStatueEquipe()
   {
        Thread th=new Thread(
           new Runnable()
           {
               public void run()
               {
                   while(jeuEnCours)
                   {
                       String s=equipe+"\n";
                       if(!modeCooperatif)
                       {
                           s+=equipe2.toString();
                       }
                        sendMessage(s, Port.portStatue1);
                        sleep(200);
                   }
               }
           }
        );
        th.start();  
   }
   
   private synchronized static void sendMessage(String msg,int p)
   {
              try
       {
            DatagramSocket soc=new DatagramSocket();
            ByteArrayOutputStream flux=new ByteArrayOutputStream(64);
            ObjectOutputStream oos=new ObjectOutputStream(new BufferedOutputStream(flux));
            oos.flush();
            oos.writeObject(msg);
            oos.flush();

            byte[] fluxEnvoi=flux.toByteArray();
            int t=listeJoueur.getNbJoueur();

            //on envoie du message à tous le joueurs connecté
            for(int i=0;i<t;i++)
            {
                DatagramPacket paquet=new DatagramPacket(fluxEnvoi,fluxEnvoi.length,listeJoueur.getIpAt(i),p);
                soc.send(paquet);
            }

           oos.close();
           soc.close();
        }
              
        catch (SocketException ex) 
        {
             Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
         }  
        catch (IOException ex) 
        {
                Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public static synchronized void message(String msg)
   {
       journal+=msg+"\n";
       //méthode qui envoie le message en paramètre à tous les joueurs
       //l'envoie se fait par le protocole UDP
       sendMessage(msg, Port.portJournal1);
   }
   
   public boolean isActif()
   {
       return actif;
   }
   
   private void enregistrementJournal()
    {
        journal+="\nFermeture de l'application : "+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date());
                
        BufferedWriter bufWriter = null;
        FileWriter fileWriter = null;
        File fichier=new File("journal_server.txt"); 
       
        try {
            //si le fichier n'existe pas on le creer 
            if(!fichier.exists())
            {
                fichier.createNewFile();
            }
            
            fileWriter = new FileWriter(fichier, true);
            bufWriter = new BufferedWriter(fileWriter);
            //Insérer un saut de ligne
            bufWriter.newLine();
            bufWriter.write(journal);
            bufWriter.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }        finally {
            try {
                bufWriter.close();
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
   
  
}
