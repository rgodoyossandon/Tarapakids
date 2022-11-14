package formularios;

import clases.Screen;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frmBienvenida extends javax.swing.JFrame implements Runnable{

    int num = 0;
    Thread t;

    public frmBienvenida() {
        initComponents();
    }

    //metodo para llenar el jProgresbar
    public void Llena_Barra() {
        if (Barra.getValue() <= 100) {
            num += 4;//Valor con el que se va llenar el ProgressBar. Se llenará de 4 en 4
            //Le podemos dar un valor mas alto para que se llene mas rapido o un valor mas bajo
            //para que se llene mas lento
            Barra.setValue(num);
            Barra.setStringPainted(true);
        } else {
            Barra.setValue(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Barra = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaption);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Barra.setForeground(new java.awt.Color(0, 0, 255));
        getContentPane().add(Barra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 360, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Iniciando...");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 340, 30));

        jLabel1.setBackground(java.awt.SystemColor.activeCaption);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/splash1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 1, 400, 360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Screen sc = new Screen(this);
        sc.start();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new frmBienvenida().setVisible(true);
            }
        });
    }

    @Override
    public void run() {
        try {
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            Thread.sleep(4000);//Tiempo que deseamos que se muestre nuestro Splash: 4000 milisegundos = 4 segundos
                         //Debemos ajustarlo bien para que nuestro Splash desaparezaca justo cuando el ProgressBar
                        //acabe de llenarse.
            this.dispose();
            frmLoggin lo = null;
            try {
                lo = new frmLoggin();
            } catch (SQLException ex) {
                Logger.getLogger(frmBienvenida.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            lo.setLocationRelativeTo(null);
            lo.setVisible(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(frmBienvenida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar Barra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

}
