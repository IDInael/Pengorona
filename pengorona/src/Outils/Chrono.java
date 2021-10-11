/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author dinael
 */
public class Chrono {
    Timer chrono;
    private int s;//seconde
    private int m;//minute
    private int h;//heure
    private int bonus=12000;//apres 20 minute de temps de jeu; le bonus devient 0;
    


    public Chrono() 
    {
        chrono=new Timer();
    }

    
    public void start()
    {
        h=0;
        m=0;
        s=0;
        bonus=12000;
        chrono.schedule(new TimerTask() {
            public void run() 
            {
                s++;
                if(s==60)
                {
                    s=0;
                    m++;
                }
                if(m==60)
                {
                    m=0;
                    h++;
                }
                bonus--;//le bonus diminue à chaque seconde perdu
            }
        }, 1000,1000);
    }
    
    public int getBonus()
    {
        if(bonus<0)
            bonus=0;
        return bonus;
    }
    
    public String toString()
    {
        String ss=s+"";
        String mm=m+"";
        String hh=h+"";
        
        if(s<10)
            ss="0"+s;
        if(m<10)
            mm="0"+m;
        if(h<10)
            hh="0"+h;
        
        return hh+":"+mm+":"+ss;
    }
    
    public void Stop()
    {
        chrono.cancel();
    }
    
    
}
