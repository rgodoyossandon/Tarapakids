package clases;

import formularios.frmPrincipal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class loggin {
    bdConexion con;
    public String a;
    
    public loggin()
    {
        con = new bdConexion();
    }  
    
        public void accesoSistema(String cUser) throws SQLException
    {               
        try
        {
            ResultSet resultado=null;
            Statement sentencia=null;
            con = new bdConexion();
            sentencia=(Statement)con.getConnection().createStatement();
            //sentencia=(Statement)con.conexion.createStatement();
            resultado=sentencia.executeQuery("SELECT * FROM usuarios WHERE  IdUsuario='"+cUser+"'");

            int x=0;
            resultado.last();

            if(resultado.getRow()<=0)
            {
                JOptionPane.showMessageDialog(null,"Error, Nombre de Usuario incorrecto","SICO",1);
                
            }
            else
            {                
                resultado.beforeFirst();

                while(resultado.next())
                {
                                       
                                      
                    x++;
                }
                
                
                frmPrincipal fP= new frmPrincipal();
                fP.show();

            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
        }
            con.desconectar();        
        
    }
    
    
}
