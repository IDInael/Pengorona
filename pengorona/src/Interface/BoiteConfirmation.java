/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author dinael
 */
public class BoiteConfirmation extends javax.swing.JDialog {

    /**
     * Creates new form BoiteConfirmation
     */
    
    private int equipe=1;
    
    
    public BoiteConfirmation(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        centrer();
    }
    
    public BoiteConfirmation(java.awt.Frame parent, boolean modal, String titre,String choix1,String choix2) {
        super(parent, modal);
        initComponents();
        centrer();
        Titre.setText(titre);
        Choix1.setText(choix1);
        Choix2.setText(choix2);
    }
    
    public int getEquipe()
    {
        return equipe;
    }
    private void centrer()
    {
        Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
        
        int x=(int)(s.getWidth()-this.getWidth())/2;
        int y=(int)(s.getHeight()-this.getHeight())/5;
        this.setLocation(x,y);
        //repere=new Position(x,y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        Titre = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Choix1 = new javax.swing.JRadioButton();
        Choix2 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 200));

        Titre.setText("jLabel1");
        jPanel1.add(Titre);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        buttonGroup1.add(Choix1);
        Choix1.setSelected(true);
        Choix1.setText("jRadioButton1");
        Choix1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Choix1ActionPerformed(evt);
            }
        });
        jPanel2.add(Choix1);

        buttonGroup1.add(Choix2);
        Choix2.setText("jRadioButton1");
        Choix2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Choix2ActionPerformed(evt);
            }
        });
        jPanel2.add(Choix2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        jButton1.setText("Valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Choix1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Choix1ActionPerformed
        // TODO add your handling code here:
        equipe=1;
    }//GEN-LAST:event_Choix1ActionPerformed

    private void Choix2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Choix2ActionPerformed
        // TODO add your handling code here:
        equipe=2;
    }//GEN-LAST:event_Choix2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(BoiteConfirmation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BoiteConfirmation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BoiteConfirmation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoiteConfirmation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BoiteConfirmation dialog = new BoiteConfirmation(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Choix1;
    private javax.swing.JRadioButton Choix2;
    private javax.swing.JLabel Titre;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}