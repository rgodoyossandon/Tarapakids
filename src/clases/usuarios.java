package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class usuarios {
    bdConexion con;
    
    public usuarios()
    {
        con = new bdConexion();
    }
    
    /* constructor explicito agregar un nuevo registro*/
    public void nuevoUsuario(String codU, String nom, String ap, String nomU,  String cont, 
            String modP, String gestionC, String resC, String realC, String modS, String modT, 
            String modE, String modM, String repor, String modUs, String modDE, String manBD)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " usuarios(IdUsuario, nombres, apellidos, "
                    + "nombreUsuario, contrasenia, modificarPaciente, gestionarCitas, reservarCitas, "
                    + "realizarConsultas, modificarServicios, modificarTratamientos, modificarEspecialista, "
                    + "modificarMedicamentos, verImprimirReportes, modificarUsuarios, modificarDatosEmpresa, "
                    + "manipularBD)" + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            pstm.setString(1, codU);
            pstm.setString(2, nom);            
            pstm.setString(3, ap);
            pstm.setString(4, nomU);
            pstm.setString(5, cont);
            pstm.setString(6, modP);
            pstm.setString(7, gestionC);            
            pstm.setString(8, resC);
            pstm.setString(9, realC);
            pstm.setString(10, modS);
            pstm.setString(11, modT);
            pstm.setString(12, modE);            
            pstm.setString(13, modM);
            pstm.setString(14, repor);
            pstm.setString(15, modUs);
            pstm.setString(16, modDE);          
            pstm.setString(17, manBD);

            
            pstm.execute();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.print(e);
        }
    }
    
    
    //Codigo para obtener los datos de la tabla
    public Object[][]getDatos()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla marca
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM usuarios");
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][5];
        
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT IdUsuario, nombres, apellidos, nombreUsuario, contrasenia FROM usuarios");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estCodUs = res.getString("IdUsuario");//nombre de usuario                
                String estNom = res.getString("nombres");//nombre real del usuario
                String estAp = res.getString("apellidos");//apellido real del usuario
                String estNomUs = res.getString("nombreUsuario");//nombre de usuario
                String estCont = res.getString("contrasenia");//contraseña asignada
                                                               
                data [i][0]=estCodUs;
                data [i][1]=estNom;                
                data [i][2]=estAp;
                data [i][3]=estNomUs;
                data [i][4]=estCont;                
                
                i++;
                
            }
            
            res.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return data;
    }
    
     //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA MARCA
    public void eliminarUsuarios(String codUsuario)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=con.getConnection().prepareStatement("DELETE usuarios.* FROM usuarios WHERE IdUsuario='"+codUsuario+"'");
            //PreparedStatement pstm=con.conexion.prepareStatement("DELETE usuarios.* FROM usuarios WHERE IdUsuario='"+codUsuario+"'");
            rows_update=pstm.executeUpdate();
            
            if (rows_update==1)
            {
                JOptionPane.showMessageDialog(null,"Registro eliminado exitosamente");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"No se pudo eliminar el registro, verifique datos");
                con.desconectar();
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Error "+e.getMessage());            
        }
    }
  
    //METODO PARA ACTUALIZAR/MODIFICAR REGISTROS DE LA TABLA USUARIOS
    public void modificarUsuarios(String codU, String nom, String ap, String nomU,  String cont, String modP, String gestionC, String resC, String realC, String modS, String modT, String modE, String modM, String repor, String modUs, String modDE, String manBD)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE usuarios SET nombres='"+nom+"',  apellidos='"+ap+"'"
                    + ",nombreUsuario='"+nomU+"',  contrasenia='"+cont+"', modificarPaciente='"+modP+"'"
                    + ", gestionarCitas='"+gestionC+"', reservarCitas='"+resC+"', realizarConsultas='"+realC+"'"
                    + ", modificarServicios='"+modS+"',  modificarTratamientos='"+modT+"'"
                    + ", modificarEspecialista='"+modE+"', modificarMedicamentos='"+modM+"'"
                    + ", verImprimirReportes='"+repor+"', modificarUsuarios='"+modUs+"'"
                    + ", modificarDatosEmpresa='"+modDE+"', manipularBD='"+manBD+"'  WHERE IdUsuario='"+codU+"'");
            pstm.executeUpdate();
            pstm.close();            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
            System.out.println(e);
        }
    }
    
}
