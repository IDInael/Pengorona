/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;
import Joueur.Joueur;
import Objets.Plateau;
import Outils.Position;
import Outils.Chrono;
import Outils.Port;
import Reseau.server.Serveur;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author dinael
 */
public class Pengo extends javax.swing.JFrame {

    public static volatile Plateau pl;
    public static String journal="\n";
     public static boolean modeMultijoueur=false;//mode multijoueur ou solo
    public static InetAddress ipServer=null;
    public static boolean modeCooperatif=false;//mode cooperatif ou non
    
    private final int t=28;//taille du plateau
    private Chrono chrono;
    public Joueur joueur;
    public boolean winner;
    public boolean gameOver;
    private Serveur server=null;
    
    public Pengo() {
        this.importationDonneesSauvegardes();
        joueur.miseAjourIP();//au cas ou l'ip du joueur a changé depuis la derniere utilisation
        chrono=new Chrono();
        pl=new Plateau(t);
        panel=new Panel(pl,joueur);
        MsgEquipe1=new JTextArea();
        MsgEquipe2=new JTextArea();
        
        
        initComponents();
        
        MsgEquipe1.setEditable(false);
        MsgEquipe1.setFocusable(false);
        MsgEquipe2.setEditable(false);
        MsgEquipe2.setFocusable(false);
        jScrollPane3.setViewportView(MsgEquipe1);
        
        panel.setPreferredSize(new Dimension(1170, 1010));
        this.add(panel);
        panel.requestFocus();
        centrer();
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        BConnexion = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        BServer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        ipLabel = new javax.swing.JLabel();
        Statue = new javax.swing.JPanel();
        PEquipe1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        LEquipe1 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        StatueEquipe1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        lancerServer = new javax.swing.JMenuItem();
        rejoindreServer = new javax.swing.JMenuItem();
        deconnexion = new javax.swing.JMenuItem();
        ServerDeconexion = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        Modifier = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        LancerPartie = new javax.swing.JMenuItem();
        quitter = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(254, 254, 254));
        setMinimumSize(new java.awt.Dimension(1000, 900));
        setPreferredSize(new java.awt.Dimension(1227, 967));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel7.setFocusable(false);
        jPanel7.setRequestFocusEnabled(false);
        jPanel7.setLayout(new java.awt.GridLayout(2, 2));

        jPanel10.setLayout(new java.awt.BorderLayout());

        BConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BConnexionActionPerformed(evt);
            }
        });
        jPanel10.add(BConnexion, java.awt.BorderLayout.EAST);

        jLabel5.setText("Connexion :");
        jPanel12.add(jLabel5);

        jPanel10.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel10);

        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel6.setText("Server :");
        jPanel13.add(jLabel6);

        jPanel11.add(jPanel13, java.awt.BorderLayout.CENTER);

        BServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BServerActionPerformed(evt);
            }
        });
        jPanel11.add(BServer, java.awt.BorderLayout.EAST);

        jPanel7.add(jPanel11);

        jLabel1.setText("Temps de jeu : ");
        jPanel2.add(jLabel1);

        time.setFont(new java.awt.Font("Garamond", 0, 18)); // NOI18N
        jPanel2.add(time);

        jPanel7.add(jPanel2);

        jPanel3.add(ipLabel);

        jPanel7.add(jPanel3);

        jPanel1.add(jPanel7, java.awt.BorderLayout.NORTH);

        Statue.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));
        Statue.setFocusable(false);
        Statue.setRequestFocusEnabled(false);
        Statue.setLayout(new java.awt.BorderLayout());

        PEquipe1.setFocusable(false);
        PEquipe1.setRequestFocusEnabled(false);
        PEquipe1.setLayout(new java.awt.BorderLayout());

        jPanel16.setFocusable(false);
        jPanel16.setRequestFocusEnabled(false);

        jLabel7.setText("Equipe I :");
        jPanel16.add(jLabel7);
        jPanel16.add(LEquipe1);

        PEquipe1.add(jPanel16, java.awt.BorderLayout.NORTH);

        jPanel14.setFocusable(false);
        jPanel14.setRequestFocusEnabled(false);
        jPanel14.setLayout(new java.awt.GridLayout(2, 0));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Statue Joueur"));
        jScrollPane1.setFocusable(false);
        jScrollPane1.setRequestFocusEnabled(false);

        StatueEquipe1.setEditable(false);
        StatueEquipe1.setColumns(20);
        StatueEquipe1.setRows(5);
        jScrollPane1.setViewportView(StatueEquipe1);

        jPanel14.add(jScrollPane1);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Journal Equipe "));
        jScrollPane3.setFocusable(false);
        jScrollPane3.setRequestFocusEnabled(false);
        jPanel14.add(jScrollPane3);

        PEquipe1.add(jPanel14, java.awt.BorderLayout.CENTER);

        Statue.add(PEquipe1, java.awt.BorderLayout.WEST);

        jPanel1.add(Statue, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.EAST);

        jMenu1.setText("Multijoueur");
        jMenu1.setFocusable(false);

        lancerServer.setText("Lancer un serveur");
        lancerServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lancerServerActionPerformed(evt);
            }
        });
        jMenu1.add(lancerServer);

        rejoindreServer.setText("Rejoindre un serveur");
        rejoindreServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejoindreServerActionPerformed(evt);
            }
        });
        jMenu1.add(rejoindreServer);

        deconnexion.setText("Se deconnecter");
        deconnexion.setEnabled(false);
        deconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deconnexionActionPerformed(evt);
            }
        });
        jMenu1.add(deconnexion);

        ServerDeconexion.setText("Deconnecter le serveur");
        ServerDeconexion.setEnabled(false);
        ServerDeconexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServerDeconexionActionPerformed(evt);
            }
        });
        jMenu1.add(ServerDeconexion);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Joueur");

        Modifier.setText("Modifier");
        Modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifierActionPerformed(evt);
            }
        });
        jMenu2.add(Modifier);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Jeu");

        LancerPartie.setText("Nouvelle partie");
        LancerPartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LancerPartieActionPerformed(evt);
            }
        });
        jMenu3.add(LancerPartie);

        quitter.setText("Quitter le jeu");
        quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitterActionPerformed(evt);
            }
        });
        jMenu3.add(quitter);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lancerServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lancerServerActionPerformed
        // TODO add your handling code here:
        
        if(!Pengo.modeMultijoueur&&server==null)
        {
                this.lancerServeur();
                centrer();

                this.receiveChrono();
                this.receiveJournal1();
                this.receiveStatue1();
                deconnexion.setEnabled(true);
                ServerDeconexion.setEnabled(true);
        }
    }//GEN-LAST:event_lancerServerActionPerformed

    private void rejoindreServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejoindreServerActionPerformed
        // TODO add your handling code here:
        if(!joueur.isConnected())
        {
            
            JOptionPane saisie=new JOptionPane();
            String adresse="";
            //saisie de l'adresse  ip du serveur à rejoindre
            adresse= saisie.showInputDialog(null, "Entrer le code multijoueur", "Rejoindre un serveur !",JOptionPane.QUESTION_MESSAGE);
            
            if(adresse!=null&&!adresse.equals(""))
            {
                this.jouerEnLigne(adresse);//connexion au serveur
                centrer();
                //lancement des threads qui reçoivent les paquets diffusés par le serveur
                this.receiveChrono();
                this.receiveJournal1();
                this.receiveStatue1();
                deconnexion.setEnabled(true);
                journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+": "+joueur.getPseudo()+" a rejoint une partie à l'adresse "+adresse;

            }
        }
        
    }//GEN-LAST:event_rejoindreServerActionPerformed

    private void LancerPartieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LancerPartieActionPerformed
        // TODO add your handling code here:
        if(Pengo.modeMultijoueur)
        {
            if(server!=null)
            {
                server.newPartie();
            }
        }
        else
        {
            journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : debut de la partie";
            pl.gameOver();
            this.newPartie();
        }
    }//GEN-LAST:event_LancerPartieActionPerformed

    private void deconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deconnexionActionPerformed
        // TODO add your handling code here:
        if(Pengo.modeMultijoueur&&joueur.isConnected())
        {
            
            Pengo.modeMultijoueur=false;
            deconnexion.setEnabled(false);
            joueur.seDeconnecter();
        }
        centrer();
        
        Thread th=new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        Thread.currentThread().sleep(3000);
                        pl.afficherTitre();
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
            });
            th.start();
    }//GEN-LAST:event_deconnexionActionPerformed

    private void quitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitterActionPerformed
        // TODO add your handling code here:
        this.enregistrementDesDonnées();
        System.exit(0);
    }//GEN-LAST:event_quitterActionPerformed

    private void ModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifierActionPerformed
        // TODO add your handling code here:
        
            JOptionPane saisie=new JOptionPane();
            
            String nom="";
            //saisie de l'adresse  ip du serveur à rejoindre
            nom= saisie.showInputDialog(null, "Entrer le nouveau nom du joueur", "Modification Joueur !",JOptionPane.QUESTION_MESSAGE);
            
            if(!nom.equals(""))
            {
                joueur.setPseudo(nom);
                journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : modification du noom du joueur";
            }
    }//GEN-LAST:event_ModifierActionPerformed

    private void ServerDeconexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ServerDeconexionActionPerformed
        // TODO add your handling code here:
        
            if(this.server!=null)
            {
                if(joueur.isConnected())
                {
                    //Pengo.modeMultijoueur=false;
                    //joueur.seDeconnecter();
                }
                //deconnexion.setEnabled(false);
                server.close();
            }
            centrer();
            
            Thread th=new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        Thread.currentThread().sleep(3000);
                        pl.afficherTitre();
                        server=null;
                    }
                    catch(InterruptedException e)
                    {
                        
                    }
                }
            });
            th.start();
            
            ServerDeconexion.setEnabled(false);
    }//GEN-LAST:event_ServerDeconexionActionPerformed

    private void BConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BConnexionActionPerformed
        // TODO add your handling code here:
        if(!Pengo.modeMultijoueur)
        {
            rejoindreServer.doClick();
        }
        else
        {
            deconnexion.doClick();
        }
        
    }//GEN-LAST:event_BConnexionActionPerformed

    private void BServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BServerActionPerformed
        // TODO add your handling code here:
        if(server!=null&&server.isActif())
        {
            ServerDeconexion.doClick();
        }
        else
        {
            if(server==null)
            {
                lancerServer.doClick();
            }
        }
    }//GEN-LAST:event_BServerActionPerformed
    
   public static void message(String s)
   {
       journal+=s+"\n";
       MsgEquipe1.append(s+"\n");
   }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pengo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pengo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pengo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pengo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pengo fenetre=new Pengo();
                fenetre.setVisible(true);
               // Pengo.gameOver=true;
                Thread th=new Thread(new Runnable()
                {
                    public void run()
                    {
                        while(true)
                        {
                            fenetre.repaint();
                            try
                            {
                                Thread.currentThread().sleep(1000);
                            }
                            catch(InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                th.start();
            }
        });
        
        
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        panel.requestFocus();
        
        if(!modeMultijoueur)
        {
            StatueEquipe1.setText(joueur+"");
            time.setText(chrono+"");
        }
    }
    
    public static void addPingouin()
    {
        panel.addPingouin();
    }
    
    public static void addPompier()
    {
        panel.addPompier(1);
    }
    
    public static void addSecours()
    {
        try
        {
            Thread.currentThread().sleep(2000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        panel.addSecours();
    }
         
    private void centrer()
    {
        Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(1360,(int)s.getHeight());
        if(this.modeMultijoueur)
        {
//            this.setSize(1620,(int)s.getHeight());
            BConnexion.setIcon(new ImageIcon(getClass().getResource("/Media/Images/wifi_on.png")));
        }
        else
        {
           // this.setSize(1490,(int)s.getHeight());
            
            BConnexion.setIcon(new ImageIcon(getClass().getResource("/Media/Images/wifi_off.png")));
            if(server!=null&&server.isActif())
                BServer.setIcon(new ImageIcon(getClass().getResource("/Media/Images/server_on.png")));
            else
                BServer.setIcon(new ImageIcon(getClass().getResource("/Media/Images/server_off.png")));
                
        }
        
        int x=(int)(s.getWidth()-this.getWidth())/2;
        int y=(int)(s.getHeight()-this.getHeight())/2;
        this.setLocation(x,y);
    }

    public void newPartie()
    {
        centrer();
        MsgEquipe1.setText("");
        MsgEquipe2.setText("");
        
        joueur.setNbVie(3);
        winner=false;
        gameOver=false;
        
        panel.newPartie();
        chrono.Stop();
        chrono=new Chrono();
        chrono.start();
        Thread partiePerdu=new Thread(
               new Runnable()
               {
                   public void run()
                   {
                       while(!gameOver&&!winner)
                       {
                           if(joueur.getNbVie()<=0)
                           {
                               
                                try
                                {
                                    Thread.currentThread().sleep(1000);
                                    pl.gameOver();
                                    Thread.currentThread().sleep(1000);
                                    pl.gameOver();
                                    chrono.Stop();
                                }
                                catch(InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                 journal+="\n"+joueur.getPseudo()+" a perdu";
                               journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : fin de la partie!";
                                gameOver=true;
                               
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
        partiePerdu.start();

        Thread partieGagnantH=new Thread(
               new Runnable()
               {
                   public void run()
                   {
                       
                       while(!gameOver&&!winner)
                       {   
                           if(pl.isWinHLine())
                           {
                               chrono.Stop();
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
                               
                                journal+="\n"+joueur.getPseudo()+" a gagné";
                               journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : fin de la partie!";
                               winner=true;
                               joueur.bonus(chrono.getBonus());
                               Pengo.message(joueur.getPseudo()+" bonus de fin de partie de "+chrono.getBonus()+"$");
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
        partieGagnantH.start();
        
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
                                 journal+="\n"+joueur.getPseudo()+" a gagné";
                               journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+" : fin de la partie!";
                               winner=true;
                               chrono.Stop();
                               joueur.bonus(chrono.getBonus());
                               Pengo.message(joueur.getPseudo()+" bonus de fin de partie de "+chrono.getBonus()+"$");
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
                
                Thread cleaner=new Thread(new Runnable(){
                public void run()
                {
                    while(true)
                        pl.nettoyer();
                }
            });
            cleaner.start();
    }
    
     private static void sleep(int d)
    {
        try
        {
            Thread.currentThread().sleep(d);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public void jouerEnLigne(String add)
    {
        joueur.seConnecter(add);
        try {
            Pengo.ipServer=InetAddress.getByName(add);
            pl.gameOver();
            chrono.Stop();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Pengo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void lancerServeur()
    {
        //thread qui met fin à la partie local
        Thread destruction=new Thread(new Runnable()
        {
            public void run()
            {
                for(int i=0;i<10;i++)
                {
                    pl.gameOver();
                    pl.nettoyer();
                    chrono.Stop();
                    try
                    {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Pengo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        destruction.start();
        
        sleep(1000);
        
        journal+="\n"+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date())+": "+joueur.getPseudo()+" a lancé un serveur de mode multijoueur";

        if(server==null)
        {
            server=new Serveur();//instanciation du serveur
            ipLabel.setText("IP Server : "+server.getAdresse());
        }
        //choix du mode de jeu en multijoueur: associatif ou combat par equipe
        BoiteConfirmation dlg=new BoiteConfirmation(null,true,"Mode de jeu","Mode cooperatif : une seule equipe","mode affrontement : deux equipes");
        dlg.setVisible(true);
        int team=dlg.getEquipe();
        
        if(team==2)
            server.setModeCooperatif(false);
        
        server.open();
        server.lancerStreaming();//lancement de la diffusion des données en broadcast
       
        joueur.seConnecter(server.getAdresse());//le joueur qui lance le serveur se connecte automatiquement
        try {
            Pengo.ipServer=InetAddress.getByName(server.getAdresse());
            BServer.setIcon(new ImageIcon(getClass().getResource("/Media/Images/server_on.png")));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Pengo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void receiveChrono()
    {
        Thread th=new Thread(
            new Runnable()
            {
                public void run()
                {
                    sleep(500);
                    while(modeMultijoueur)
                    {
                        try
                        {
                            String chr="00:00:00";
                            
                            DatagramSocket soc=new DatagramSocket(Port.portChrono);//initialisation du socket 
                            soc.setSoTimeout(5000);
                            
                            byte[] buffer=new byte[128];//preparation du buffer

                            DatagramPacket paquet=new DatagramPacket(buffer,buffer.length);
                            //reception du paquet
                            soc.receive(paquet);//methode bloquant

                            ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);
                            
                            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));
                            //lecture du paquet reçue
                            chr=(String)ois.readObject();
                            
                            time.setText(chr);//affichage du chrono
                            sleep(100);
                            //fermeture des flux
                            ois.close();
                            byteStream.close();
                            soc.close();
                        }

                        catch(IOException e)
                        {
                            //e.printStackTrace();
                        } 
                        catch (ClassNotFoundException ex) {
                            Logger.getLogger(Bloc.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        );
        th.start();
    }
    
    private void receiveJournal1()
    {
        Thread th=new Thread(
            new Runnable()
            {
                public void run()
                {
                    sleep(5000);
                    MsgEquipe1.setForeground(Color.black);
                    MsgEquipe1.setText("");
                    while(modeMultijoueur)
                    {
                        try
                        {
                            String jrl="";
                            
                            DatagramSocket soc=new DatagramSocket(Port.portJournal1);
                            soc.setSoTimeout(3000);
                            
                            byte[] buffer=new byte[128];

                            DatagramPacket paquet=new DatagramPacket(buffer,buffer.length);

                            soc.receive(paquet);//methode bloquant

                            ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);

                            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

                            jrl=(String)ois.readObject();
                            
                            MsgEquipe1.append(jrl+"\n");
                            
                            sleep(100);

                            ois.close();
                            byteStream.close();
                            soc.close();
                        }

                        catch(IOException e)
                        {
                            //e.printStackTrace();
                        } 
                        catch (ClassNotFoundException ex) {
                            Logger.getLogger(Bloc.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        );
        th.start();
    }
    
    private void receiveStatue1()
    {
        Thread th=new Thread(
            new Runnable()
            {
                public void run()
                {
                    sleep(500);
                    StatueEquipe1.setText("");
                    while(modeMultijoueur)
                    {
                        DatagramSocket soc=null;
                        try
                        {
                            String statue="1";
                            
                            soc=new DatagramSocket(Port.portStatue1);
                            soc.setSoTimeout(1000);
                            
                            byte[] buffer=new byte[128];

                            DatagramPacket paquet=new DatagramPacket(buffer,buffer.length);

                            soc.receive(paquet);//methode bloquant
                            
        
                            ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer);

                            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

                            statue=(String)ois.readObject();
                            
                            StatueEquipe1.setText(statue+"\n");
                            
                            sleep(30);

                            ois.close();
                            byteStream.close();
                            soc.close();
                        }

                        catch(IOException e)
                        {
                           // e.printStackTrace();
                        } 
                        catch (ClassNotFoundException ex) {
                            Logger.getLogger(Bloc.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally
                        {
                            soc.close();
                        }
                    }
                }
            }
        );
        th.start();
    }
    
    private void enregistrementDesDonnées()
    {
        this.enregistrementJournal();
        this.sauvegardeJoueur();
        
        if(this.server!=null&&this.server.isActif())
        {
            server.close();
        }
    }
    
    private void enregistrementJournal()
    {
        journal+="\nFermeture de l'application : "+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date());
        
        //ouverture des flux
        BufferedWriter bufWriter = null;
        FileWriter fileWriter = null;
        File fichier=new File("journal_local.txt"); 
       
        try {
            //si le fichier n'existe pas on le creer 
            if(!fichier.exists())
            {
                fichier.createNewFile();
            }
            
            //ici on ecrit le journal à la fin du fichier sans ecraser le contenu
            fileWriter = new FileWriter(fichier, true);
            bufWriter = new BufferedWriter(fileWriter);
            //Insérer un saut de ligne
            bufWriter.newLine();
            bufWriter.write(journal);//ecriture des flux de données dans le fichier
            bufWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Pengo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            try {
                bufWriter.close();
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Pengo.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    private void sauvegardeJoueur()
    {
        ObjectOutputStream oos;
        //importation du fichier
        File fichier=new File("joueur_sauvegarde.txt");
        try 
        {
            //si le fichier n'existe pas on le creer 
            if(!fichier.exists())
            {
                fichier.createNewFile();
            }
            
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fichier)));
            //ecriture du joueur dans un fichier en ecrasant le contenu avant
            oos.writeObject(joueur);
            
            //Ne pas oublier de fermer le flux !
            oos.close();
        }   
        catch (IOException ex) {
            Logger.getLogger(Pengo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void importationDonneesSauvegardes()
    {
        //ecrire dans le journal qu'on vient de lancer l'applicaiton
        journal+="\nOuverture de l'application : "+DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.MEDIUM).format(new Date());
        
        //importation du joueur sauvegarde
        ObjectInputStream ois;
        File fichier=new File("joueur_sauvegarde.txt");
        try
        {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fichier)));
            //lecture du fichier contenant le sauvegarde du joueur
            this.joueur=(Joueur)ois.readObject();
        } 
        catch (FileNotFoundException ex) 
        {
            //au cas ou on ne trouve pas le fichier on creer un nouveau joueur
            this.joueur=new Joueur();
        } 
        catch (IOException ex) 
        {
            this.joueur=new Joueur();
        } catch (ClassNotFoundException ex) 
        {
            this.joueur=new Joueur();
        }
        finally
        {
            if(joueur==null)
            {
                this.joueur=new Joueur();
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BConnexion;
    private javax.swing.JButton BServer;
    private javax.swing.JLabel LEquipe1;
    private javax.swing.JMenuItem LancerPartie;
    private javax.swing.JMenuItem Modifier;
    private javax.swing.JPanel PEquipe1;
    private javax.swing.JMenuItem ServerDeconexion;
    private javax.swing.JPanel Statue;
    private javax.swing.JTextArea StatueEquipe1;
    private javax.swing.JMenuItem deconnexion;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem lancerServer;
    private javax.swing.JMenuItem quitter;
    private javax.swing.JMenuItem rejoindreServer;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables

   public static Panel panel;
   public static JTextArea MsgEquipe1;
   public static JTextArea MsgEquipe2;
}
