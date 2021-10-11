/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objets;

import Outils.Position;
import Joueur.Joueur;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author dinael
 */
public class Plateau implements Serializable
{
    private int taille;//taille du tableau
    private volatile Objet[][] plateau;
    
    private ReadWriteLock verrou=new ReentrantReadWriteLock(); //verrou
    private Lock lecture=verrou.readLock();// verrou en lecture
    private Lock ecriture=verrou.writeLock();// verrou en ecriture
    private boolean online=false;
    
    public Plateau()
    {
        this(10);
    }
    
    public Plateau(int t)
    {
        taille=t;
        plateau=new Objet[taille][taille];
        this.afficherTitre();
    }
    
    public int getTaille()
    {
        return taille;
    }
    
    public void setObjet(Position p,Objet ob)
    {
        //fermeture du verrrou pour tout lecture et ecriture
        //accès interdit en lecture et en écriture pour tous les autres threads
        ecriture.lock();
        try
        {
            plateau[p.getX()][p.getY()]=ob;
        }
        finally
        {
            ecriture.unlock();
            //ouverture du verrou
        }
    }
    
    public void initPlateau()
    {
        int i,j,k;
        Random r=new Random();
        for(i=0;i<taille;i++)
        {
            for(j=0;j<taille;j++)
            {
                plateau[i][j]=new Antivirus(i,j);
                plateau[i][j].setPlateau(this);
                plateau[i][j].setOnline(online);
            }
        }
        int nbs=0;//nb cube supprim&
        int s=0;
        do
        {
            do
            {
                i=r.nextInt(taille);
                j=r.nextInt(taille);
            }while(plateau[i][j]==null);
            
            k=r.nextInt(taille);
            for(int l=0;l<k;l++)
            {
                if(s%2==0)
                {
                    i=i%taille;
                    if(plateau[i][j]!=null)
                    {
                        plateau[i][j]=null;
                        nbs++;
                    }
                    i++;
                }
                else
                {
                    j%=taille;
                    if(plateau[i][j]!=null)
                    {
                        plateau[i][j]=null;
                        nbs++;
                    }
                    j++;
                }
            }
            s++;
        }while(nbs<(2*taille*taille)/3);
        
        //ajout des vaccins, nbs=nb vaccin ajouté
        for(nbs=0;nbs<3;nbs++)
        {
            do
            {
                i=r.nextInt(taille-2)+1;
                j=r.nextInt(taille-2)+1;
            }while(plateau[i][j]!=null);
            plateau[i][j]=new Vaccin(i,j);
            plateau[i][j].setOnline(online);
            plateau[i][j].setPlateau(this);
          
            if(i-1>=0)
            {
                if(plateau[i-1][j]==null||plateau[i-1][j] instanceof Antivirus)
                {
                    plateau[i-1][j]=new Murs(i-1,j);
                    plateau[i-1][j].setPlateau(this);
                    plateau[i-1][j].setOnline(online);
                }
                if(j-1>=0)
                {
                    if(plateau[i-1][j-1]==null||plateau[i-1][j-1] instanceof Antivirus)
                    {
                        plateau[i-1][j-1]=new Murs(i-1,j-1);
                        plateau[i-1][j-1].setPlateau(this);  
                        plateau[i-1][j-1].setOnline(online);
                    }
                    if(plateau[i][j-1]==null||plateau[i][j-1] instanceof Antivirus)
                    {
                        plateau[i][j-1]=new Murs(i,j-1);
                        plateau[i][j-1].setPlateau(this);
                        plateau[i][j-1].setOnline(online);
                    }
                }
                if(j+1<taille)
                {
                    if(plateau[i-1][j+1]==null||plateau[i-1][j+1] instanceof Antivirus)
                    {
                        plateau[i-1][j+1]=new Murs(i-1,j+1);
                        plateau[i-1][j+1].setPlateau(this);
                        plateau[i-1][j+1].setOnline(online);
                    }
                    if(plateau[i][j+1]==null||plateau[i][j+1] instanceof Antivirus)
                    {
                        plateau[i][j+1]=new Murs(i,j+1);
                        plateau[i][j+1].setPlateau(this);
                        plateau[i][j+1].setOnline(online);
                    }
                }
            }
            if(i+1<taille)
            {
                if(plateau[i+1][j]==null||plateau[i+1][j] instanceof Antivirus)
                {
                    plateau[i+1][j]=new Murs(i+1,j);
                    plateau[i+1][j].setPlateau(this);
                    plateau[i+1][j].setOnline(online);
                }
                if(j-1>=0)
                {
                    if(plateau[i+1][j-1]==null||plateau[i+1][j-1] instanceof Antivirus)
                    {
                        plateau[i+1][j-1]=new Murs(i+1,j-1);
                        plateau[i+1][j-1].setPlateau(this);
                        plateau[i+1][j-1].setOnline(online);
                    }
                }
                if(j+1<taille)
                {
                    if(plateau[i+1][j+1]==null||plateau[i+1][j+1] instanceof Antivirus)
                    {
                        plateau[i+1][j+1]=new Murs(i+1,j+1);
                        plateau[i+1][j+1].setPlateau(this);
                        plateau[i+1][j+1].setOnline(online);
                    }
                }
            }
            
        }        
    }
    
    public void addMurs(Position p)
    {
        if(p.isInside(taille)&&this.getObjet(p)==null)
        {
            Murs m=new Murs(p.getX(),p.getY());
            m.setPlateau(this);
            plateau[p.getX()][p.getY()]=m;
        }
    }
    
    public void addBombe(Position p,Joueur jouer)
    {
        if(p.isInside(taille)&&this.getObjet(p)==null)
        {
            Random r=new Random();
            int ti=r.nextInt(17000)+3000;//temps d'activation de la bombe
            
            Bombe b=new Bombe(p,ti);
            b.setPlateau(this);
            b.setJoueur(jouer);
            b.setOnline(online);
            
            new Thread(b).start();
            plateau[p.getX()][p.getY()]=b;
        }
    }
    
    public void addVirus(int v)
    {
        int i,j;
        Random r=new Random();
        
        do
        {
            i=r.nextInt(taille);
            j=r.nextInt(taille);
        }while(plateau[i][j]!=null);
        
        Virus vr=new Virus(i,j);
        vr.setPlateau(this);
        vr.setVirulence(v);
        vr.setOnline(online);
        plateau[i][j] =vr;
        
        Thread monster=new Thread(vr);
        sleep(50);
        monster.start();
    }
    
    public void addPompier()
    {
        int i,j;
        Random r=new Random();
        
        do
        {
            i=r.nextInt(taille);
            j=r.nextInt(taille);
        }while(plateau[i][j]!=null);
        
        Pompier p=new Pompier(i,j);
        p.setPlateau(this);
        p.setOnline(online);
        plateau[i][j] =p;
        
        Thread pompier=new Thread(p);
        sleep(50);
        pompier.start();
    }
    
    public void addPolice()
    {
        int i,j;
        Random r=new Random();
        
        do
        {
            i=r.nextInt(taille);
            j=r.nextInt(taille);
        }while(plateau[i][j]!=null);
        
        Police p=new Police(i,j);
        p.setPlateau(this);
        p.setOnline(online);
        plateau[i][j] =p;
        
        Thread police=new Thread(p);
        sleep(50);
        police.start();
    }
    
    public void addPingouin(Pingouin p)
    {
        int i,j;
        //recherche d'une position libre
        Random r=new Random();
        do
        {
            i=r.nextInt(taille);
            j=r.nextInt(taille);
        }while(plateau[i][j]!=null);
        
        //verouiller l'acces à la lecture et à l'écriture
        ecriture.lock();
        try
        {
            p.setPosition(i,j);
            p.setPlateau(this);
            p.setOnline(online);
            plateau[i][j]=p;  
            
            //lancement du pingouin dans le plateau
            Thread pingouin=new Thread(p);
            pingouin.start();        }
        finally
        {
            ecriture.unlock();
            //deverouillage
        }

    }
    
    public void addVirus(Virus p)
    {
        int i,j;
        Random r=new Random();
        do
        {
            i=r.nextInt(taille);
            j=r.nextInt(taille);
        }while(plateau[i][j]!=null);
        
                ecriture.lock();
        try
        {
            p.setPosition(i,j);
            p.setPlateau(this);
            p.setOnline(online);
            plateau[i][j]=p;  

            Thread virus=new Thread(p);
            virus.start();        }
        finally
        {
            ecriture.unlock();
        }

    }
    
    public void addSecours(Secours s)
    {
        int i,j;
        Random r=new Random();
        do
        {
            i=r.nextInt(taille);
            j=r.nextInt(taille);
        }while(plateau[i][j]!=null);
        
                ecriture.lock();
        try
        {
            s.setPosition(i,j);
            s.setPlateau(this);
            s.setOnline(online);
            plateau[i][j]=s;  

            Thread secours=new Thread(s);
            secours.start();        }
        finally
        {
            ecriture.unlock();
        }

    }
    
    public void addAntivirus(Position p)
    {
        ecriture.lock();
        try
        {
            int i=p.getX();
            int j=p.getY();

            Antivirus v=new Antivirus(i,j);
            v.setPlateau(this);
            v.setOnline(online);
            plateau[i][j]=v;        }
        finally
        {
            ecriture.unlock();
        }

    }
    
    
    public Objet getObjet(Position p)
    {
        //verrouillage en lecture
        //le verrou lecture est ouvert s'il n'y a pas d'écriture en cours
        lecture.lock();
        try
        {
            return plateau[p.getX()][p.getY()];
        }
        finally
        {
            lecture.unlock();
            //deverouillage
        }
    }
    
    public Objet getObjet(int i, int j)
    {
        return getObjet(new Position(i,j));
    }
    
    public void gameOver()
    {
        //parcourir le plateau pour mettre fin a tous les threads dans le plateau
        for(int i=0;i<taille;i++)
            for(int j=0;j<taille;j++)
            {
                if(plateau[i][j]!=null)
                {
                    plateau[i][j].desactive();//mettre fin au thread
                    plateau[i][j]=null;
                }
            }
    }
    
    public void nettoyer()
    {
        sleep(2000);
        //nettoyage du plateau des threads non necessaire qui tourne encore
        for(int i=0;i<taille;i++)
            for(int j=0;j<taille;j++)
            {
                if(plateau[i][j]!=null&&!plateau[i][j].isActive())
                    plateau[i][j]=null;
            }
    }
    
    public boolean isWinHLine(int i)
    {
        int j=0;
        int nbs=0;
        boolean cpt=false;//true s'il y deja eu un vaccin trouvé
        while(j<taille&&nbs!=3)
        {
            if(plateau[i][j]!=null)
            {
                //s'il s'agit d'un mur, on sort direct
                if(plateau[i][j] instanceof Murs||plateau[i][j] instanceof Bombe)
                {
                    j=taille;
                }
                else
                {
                    //s'il s'agit d'un antivirus ou d'un vaccin 
                    if(plateau[i][j] instanceof Antivirus||plateau[i][j] instanceof Vaccin)
                    {
                        //s'il sont en mouvement on sors de la boucle
                        if(plateau[i][j].getCommande()!=0)
                            j=taille;
                        else
                        {
                            //si cpt=rue, donc l'antivirus est aligné directement au vaccin donc on sort de la boucle
                            if(plateau[i][j] instanceof Antivirus &&cpt)
                                j=taille;
                            else
                            {
                                //s'il sagit d'un vaccin on incremente le nombre de vaccin aligné
                                if(plateau[i][j] instanceof Vaccin)
                                {
                                    cpt=true;
                                    nbs++;
                                }
                                j++;
                            }
                        }
                    }
                    else
                        j++;
                }
            }
            //si la case est vide, on passe au suivant
            else
                j++;
        }
        //s'il y a 3 vaccin aligné, on renvoie true
        if(nbs==3)
            return true;
        else
            return false;
    }
    
    public boolean isWinVLine(int j)
    {
        int i=0;
        int nbs=0;
        boolean cpt=false;
        while(i<taille&&nbs!=3)
        {
            if(plateau[i][j]==null)
            {
                i++;
            }
            else
            {
                if(plateau[i][j] instanceof Murs||plateau[i][j] instanceof Bombe)
                {
                    i=taille;
                }
                else
                {
                    if(plateau[i][j] instanceof Antivirus||plateau[i][j] instanceof Vaccin)
                    {
                        if(plateau[i][j].getCommande()!=0)
                            i=taille;
                        else
                        {
                            if(plateau[i][j] instanceof Antivirus&&cpt)
                                i=taille;
                            else
                            {
                                if(plateau[i][j] instanceof Vaccin)
                                {
                                    cpt=true;
                                    nbs++;
                                }
                                i++;
                            }
                        }
                    }
                    else
                        i++;
                }
            }
        }
        if(nbs==3)
            return true;
        else
            return false;
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
    
    public void afficherGameOver()
    {
        this.gameOver();
      int i,j;
        for( i=0;i<taille;i++)
        {
            for( j=0;j<taille;j++)
                plateau[i][j]=null;
        }
        
        //lettre G
        Virus v1=new Virus(400);
        Virus v2=new Virus(400);
        Virus v3=new Virus(400);
        Virus v4=new Virus(400);
        for( j=2;j<=7;j++)
        {
            plateau[3][j]=v4;
            plateau[11][j]=v4;
            if(j>=5)
                plateau[7][j]=v4;
        }
        for(i=4;i<12;i++)
        {
            plateau[i][2]=v4;
            if(i>=9)
                plateau[i][7]=v4;
        }
        plateau[4][7]=v4;
        
        //lettre A
        for(i=3;i<12;i++)
        {
            plateau[i][9]=v3;
            plateau[i][13]=v3;
        }
        for(j=10;j<=12;j++)
        {
            plateau[3][j]=v3;
            plateau[7][j]=v3;
        }
        
        //lettre plateau
        for(i=3;i<=11;i++)
        {
            plateau[i][15]=v2;
            plateau[i][21]=v2;
        }
        plateau[4][16]=v2;
        plateau[5][17]=v2;
        plateau[6][18]=v2;
        plateau[5][19]=v2;
        plateau[4][20]=v2;
        
        //lettre E
        for(i=3;i<12;i++)
        {
            plateau[i][23]=v1;
        }
        for(j=24;j<=26;j++)
        {
            plateau[3][j]=v1;
            plateau[7][j]=v1;
            plateau[11][j]=v1;
        }
        
        //lettre O
        for(i=15;i<24;i++)
        {
            plateau[i][2]=v1;
            plateau[i][6]=v1;
        }
        for(j=3;j<6;j++)
        {
            plateau[15][j]=v1;
            plateau[23][j]=v1;
        }
                //lettre E
        for(i=15;i<24;i++)
        {
            plateau[i][18]=v3;
        }
        for(j=19;j<22;j++)
        {
            plateau[15][j]=v3;
            plateau[19][j]=v3;
            plateau[23][j]=v3;
        }
        
        //lettre R
        for(i=15;i<24;i++)
        {
            plateau[i][23]=v4;
            if(i!=19&&i!=20)
                plateau[i][26]=v4;
            else
                plateau[i][25]=v4;
        }
        plateau[15][24]=v4;
         plateau[15][25]=v4;
          plateau[19][24]=v4;
          
        //lettre V
        plateau[15][8]=v2;
        plateau[16][8]=v2;
        plateau[17][9]=v2;
        plateau[18][9]=v2;
        plateau[19][10]=v2;
        plateau[20][10]=v2;
        plateau[21][11]=v2;
        plateau[22][11]=v2;
        plateau[23][12]=v2;
        plateau[22][13]=v2;
        plateau[21][13]=v2;
        plateau[20][14]=v2;
        plateau[19][14]=v2;
        plateau[18][15]=v2;
        plateau[17][15]=v2;
        plateau[16][16]=v2;
        plateau[15][16]=v2;
        
    }
    
    public void afficherWinner()
    {
        this.gameOver();
        
        int i;
        AffichageTexte linux=new AffichageTexte("usd");
        AffichageTexte windows=new AffichageTexte("win");
        AffichageTexte windows2=new AffichageTexte("win");
        AffichageTexte windows3=new AffichageTexte("win");
        AffichageTexte mac=new AffichageTexte("mac");
        AffichageTexte mac1=new AffichageTexte("mac");
        AffichageTexte mac2=new AffichageTexte("mac");
        
        //lettre Y
        for(i=4;i<=12;i++)
        {
            if(i<7)
            {
                plateau[i][i]=mac;
                plateau[i][14-i]=mac;
            }
            else
                plateau[i][7]=mac;
        }
        
        //lettre O
        for(i=4;i<=12;i++)
        {
            plateau[i][13]=windows;
            plateau[i][17]=windows;
            if(i==4||i==12)
            {
                plateau[i][14]=windows;
                plateau[i][15]=windows;
                plateau[i][16]=windows;
            }
        }
        
        //lettre U
        for(i=4;i<=12;i++)
        {
            plateau[i][20]=mac1;
            plateau[i][24]=mac1;
            if(i==12)
            {
                plateau[i][21]=mac1;
                plateau[i][22]=mac1;
                plateau[i][23]=mac1;
            }
        }
        
        //lettre W I N !
        for(i=16;i<=24;i++)
        {
            plateau[i][4]=windows2;
            plateau[i][12]=windows2;
            plateau[i][15]=mac2;
            plateau[i][18]=windows3;
            plateau[i][22]=windows3;
            plateau[i][24]=linux;
        }
        
        plateau[20][8]=windows2;
        plateau[21][7]=windows2;
        plateau[21][9]=windows2;
        plateau[22][6]=windows2;
        plateau[22][10]=windows2;
        plateau[23][5]=windows2;
        plateau[23][11]=windows2;
        
        plateau[14][15]=mac2;
        
        plateau[17][19]=windows3;
        plateau[18][20]=windows3;
        plateau[19][21]=windows3;
        
        plateau[23][24]=null;
    }
    
    public void afficherTitre()
    {
        this.gameOver();
        int i,j;
        AffichageTexte p=new AffichageTexte("win");
        AffichageTexte e=new AffichageTexte("mac");
        AffichageTexte n=new AffichageTexte("win");
        AffichageTexte g=new AffichageTexte("mac");
        AffichageTexte o=new AffichageTexte("win");
        AffichageTexte r=new AffichageTexte("mac");
        AffichageTexte o2=new AffichageTexte("win");
        AffichageTexte n2=new AffichageTexte("mac");
        AffichageTexte a=new AffichageTexte("win");
        
        AffichageTexte s=new AffichageTexte("mac");
        AffichageTexte a2=new AffichageTexte("win");
        AffichageTexte v=new AffichageTexte("mac");
        AffichageTexte e2=new AffichageTexte("win");
        AffichageTexte l=new AffichageTexte("mac");
        AffichageTexte i2=new AffichageTexte("win");
        AffichageTexte f=new AffichageTexte("mac");
        AffichageTexte e3=new AffichageTexte("win");
        
        //Pengo
        for(i=3;i<=9;i++)
        {
            if(i==3)
            {
                plateau[i][1]=p;
                plateau[i][2]=p;
                
                plateau[i][5]=e;
                plateau[i][6]=e;
                plateau[i][7]=e;
                
                plateau[i][13]=g;
                plateau[i][14]=g;
                plateau[i][15]=g;
                
                plateau[i][17]=o;
                plateau[i][18]=o;
            }
            else
                if(i==9)
                {
                    plateau[i][5]=e;
                    plateau[i][6]=e;
                    plateau[i][7]=e;
                    
                    plateau[i][13]=g;
                    plateau[i][14]=g;
                }
            
            plateau[i][0]=p;
            if(i<=6)
                plateau[i][3]=p;
            
            plateau[i][4]=e;
            
            plateau[i][8]=n;
            plateau[i][11]=n;
            
            plateau[i][12]=g;
            if(i>=6)
                plateau[i][15]=g;
            
            plateau[i][16]=o;
            plateau[i][19]=o;
        }
        plateau[4][9]=n;
        plateau[5][10]=n;
        
        plateau[6][14]=g;
        
        plateau[10][16]=o;
        plateau[10][19]=o;
        
        plateau[6][1]=p;
        plateau[6][2]=p;
        
        plateau[6][5]=e;
        plateau[6][6]=e;
        plateau[6][7]=e;
        
        //rona
        for(i=11;i<=17;i++)
        {
            if(i==11||i==14)
            {
                plateau[i][13]=r;
                plateau[i][14]=r;
                
                plateau[i][25]=a;
                plateau[i][26]=a;
            }else
                if(i==17)
                {
                    plateau[i][17]=o2;
                    plateau[i][18]=o2;
                }
            
            if(i<=14)
            {
                plateau[i][15]=r;
            }
            plateau[i][12]=r;
            
            plateau[i][16]=o2;
            plateau[i][19]=o2;
            
            plateau[i][20]=n2;
            plateau[i][23]=n2;
            
            plateau[i][24]=a;
            plateau[i][27]=a;
        }
        plateau[15][13]=r;
        plateau[16][14]=r;
        plateau[17][15]=r;
        
        plateau[12][21]=n2;
        plateau[13][22]=n2;
        
        //save life
        for(i=19;i<24;i++)
        {
            if(i==19)
            {
                plateau[i][2]=s;
                plateau[i][5]=a2;
                plateau[i][7]=v;
                plateau[i][11]=v;
                plateau[i][13]=e2;
                plateau[i][14]=e2;
                plateau[i][22]=f;
                plateau[i][23]=f;
                plateau[i][25]=e3;
                plateau[i][26]=e3;
            }else
            {
                if(i==23)
                {
                    plateau[i][2]=s;
                    plateau[i][13]=e2;
                    plateau[i][14]=e2;
                    plateau[i][18]=l;
                    plateau[i][19]=l;
                    plateau[i][25]=e3;
                    plateau[i][26]=e3;
                }
                else
                    if(i==21)
                    {
                        plateau[i][2]=s;
                        plateau[i][5]=a;
                        plateau[i][13]=e2;
                        plateau[i][22]=f;
                        plateau[i][23]=f;
                        plateau[i][25]=e3;
                        plateau[i][8]=v;
                        plateau[i][10]=v;
                    }
            }
            
            plateau[i][1]=s;
            plateau[i][3]=s;
            
            plateau[i][4]=a;
            plateau[i][6]=a;
            
            plateau[i][12]=e3;
            
            plateau[i][17]=l;
            plateau[i][20]=i2;
            
            plateau[i][21]=f;
            plateau[i][24]=e3; 
        }
        plateau[22][1]=null;
        plateau[20][3]=null;
        
        plateau[20][7]=v;
        plateau[20][11]=v;
        plateau[22][8]=v;
        plateau[22][10]=v;
        plateau[23][9]=v;
    }
    
    public String toString()
    {
        String s="";
        
        for(int i=0;i<taille;i++)
        {
            for(int j=0;j<taille;j++)
            {
                if(plateau[i][j]!=null)
                    s+=plateau[i][j].toString();
                else
                    s+=" ";
            }
            s+="\n";
        }
        s+="\n\n";
        return s;
    }
    
    public void setOnline(boolean b)
    {
        online=b;
    }
    
    /**
     * renvoie true s'il y a une ligne horizontale gagnante
     * @return 
     */
    public boolean isWinHLine()
    {
        boolean res=false;
        int i=0;
        int t=taille;
        while(!res&&i<t)
        {
            res=isWinHLine(i);
            i++;
        }
        return res;
    }
    
    /**
     * renvoie true s'il y a une ligne vercticale gagnante
     * @return 
     */
    public boolean isWinVLine()
    {
        boolean res=false;
        int i=0;
        while(!res&&i<taille)
        {
            res=isWinVLine(i);
            i++;
        }
        return res;
    }
}
