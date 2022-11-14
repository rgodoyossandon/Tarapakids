
package formularios;

import clases.bdConexion;
import clases.loggin;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ordoñez
 */
public class frmLoggin extends javax.swing.JFrame {
    bdConexion con;
    loggin log= new loggin();//instancia hacia la clase loggin
    public String cUser;//variable global
    
    //instancia hacia el frmPadre
    frmPrincipal fPrin=new frmPrincipal();
    
    
    /** Creates new form frmLoggin
     * @throws java.sql.SQLException */
    public frmLoggin() throws SQLException {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnIngresar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtContrasenia = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Acceso al Sistema");
        setModalExclusionType(null);
        setResizable(false);

        jPanel1.setBackground(java.awt.SystemColor.activeCaption);
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nombre de Usuario:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 120, 150, 35));

        txtNombreUsuario.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtNombreUsuario.setForeground(new java.awt.Color(0, 51, 255));
        jPanel1.add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 136, 35));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Contraseña:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 158, 150, 35));

        jPanel2.setBackground(java.awt.SystemColor.control);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true)));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnIngresar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/acept-32.png"))); // NOI18N
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        jPanel2.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 130, 50));

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/32_cancel.png"))); // NOI18N
        btnCancelar.setText("Cacelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 130, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 330, 70));

        txtContrasenia.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        txtContrasenia.setForeground(new java.awt.Color(0, 51, 255));
        txtContrasenia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseniaActionPerformed(evt);
            }
        });
        jPanel1.add(txtContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 158, 136, 35));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/96_users.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fondos/fondo-diente.jpg"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        try {
            // acceso al sistema
            accesoAlSistema();
        } catch (SQLException ex) {
            Logger.getLogger(frmLoggin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtContraseniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseniaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {                

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() 
            {
                try {
                    new frmLoggin().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(frmLoggin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });                        
    }
    
    
    public void accesoAlSistema() throws SQLException
    {
        String nU, pas;
        
        nU=txtNombreUsuario.getText();
        pas=String.valueOf(txtContrasenia.getPassword());
        
        if (nU.equals("") || pas.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Digite el usuario y la contraseña","SICO",0);
            txtNombreUsuario.requestFocus();
            limpiarControles();
            
        }else
        {//inciio else

            try
            {
                con = new bdConexion();
                ResultSet resultado=null;
                PreparedStatement pstmx=(PreparedStatement)
                con.getConnection().prepareStatement("SELECT IdUsuario, contrasenia FROM usuarios WHERE  nombreUsuario='"+nU+"'");
                resultado = pstmx.executeQuery();                                     

                int x=0;
                resultado.last();

                if(resultado.getRow()<=0)
                {
                    JOptionPane.showMessageDialog(null,"Nombre de Usuario incorrecto","SICO",0);
                    txtNombreUsuario.setText("");
                    txtNombreUsuario.requestFocus();

                }
                else
                {                
                    resultado.beforeFirst();

                    while(resultado.next())
                    {

                        cUser= resultado.getString("IdUsuario");
                        String contras= resultado.getString("contrasenia");

                        if (contras.equals(pas))
                        {
                            fPrin.cUser=cUser;//le enviamos a la variable globar cUser del formulario padre el nombre del usuario almacendao en cUser                            
                            fPrin.setVisible(true);
                            this.dispose();                                                                                    
                        }else
                        {
                            JOptionPane.showMessageDialog(null,"Contraseña incorrecta","SICO",0);
                            txtContrasenia.setText("");
                            txtContrasenia.requestFocus();
                        }

                        x++;
                    }                
                }
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
            }
                con.desconectar();             
            
        }//fin Else
        
        
    }
    
    private void limpiarControles()
    {
        txtNombreUsuario.setText("");
        txtContrasenia.setText("");
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtContrasenia;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
