/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Outils;

/**
 *
 * @author dinael
 */
public abstract class Port {

    public static final int portChrono = 2315;
    public static final int portStatue1 = 2319;
    public static final int portJournal1 = 2318;

      /**
     * port 2345: port utilisé par le server pour ecouter les nouveau joueur qui essais de se connecter
     * port 2320: port donnée au premier joueur connecté pour effectuer les echanges. s'incremente avec le nombre de joueur
     * port 2347: port utilisé pour les echanges de photos. 
     * port 2319: port utilisé pour envoie de statue de l'equipe 1
     * port 2318: port utilisé pour envoie du journal de l'equipe 1;
     * port 2317: port utilisé pour envoie de statue de l'equipe 2
     * port 2317 port utilisé pour envoie du journal equipe 2;
     * port 2315: port utilisé pour envoie de la chrono
     */
    
    
}
