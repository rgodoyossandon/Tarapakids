package clases;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class citas {
    bdConexion con;
    
    public citas()
    {
        con = new bdConexion();
    }
    
    //constructor explicito para agregar un nuevo registro
    public void nuevaCita(String idCita, String idPac, String fecha, String hora, String estado)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " citas(IdCita, IdPaciente, fecha, hora, estado)" + "values (?,?,?,?,?)");
            
            pstm.setString(1, idCita);
            pstm.setString(2, idPac);
            pstm.setString(3, fecha);
            pstm.setString(4, hora);
            pstm.setString(5, estado);
                        
            pstm.execute();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.print(e);
        }
    }
    
    /*Para obtener los datos necesarios de los pacientes cuando se elija el boton buscarpaciente 
     del formulario citas*/
    //Codigo para obtener los datos de la tabla
    public Object[][]getDatosPacientes()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla citas
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM paciente");//cuenta el total de registros de la tabla paciente
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
            con.getConnection().prepareStatement("SELECT IdPaciente, nombres, apellidos, direccion, telefono, email FROM paciente ORDER BY IdPaciente");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estIdP = res.getString("IdPaciente");
                String estNomP = res.getString("nombres");
                String estApeP = res.getString("apellidos");
                String estD = res.getString("direccion");
                String estTe = res.getString("telefono");
                String estEm = res.getString("email");
                String nombre = estNomP+' '+estApeP;                                
                
                data [i][0]=estIdP;
                data [i][1]=nombre;
                data [i][2]=estD;
                data [i][3]=estTe;
                data [i][4]=estEm;
                                                             
                i++;//retorna el ciclo hasta finalizar
                
            }
            
            res.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return data;
    }
    
    //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA CITAS
    public void eliminarCita(String codigo)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=(PreparedStatement) 
            con.getConnection().prepareStatement("DELETE citas.* FROM citas WHERE IdCita='"+codigo+"'");
            //con.conexion.prepareStatement("DELETE citas.* FROM citas WHERE IdCita='"+codigo+"'");
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
    
     //METODO PARA ACTUALIZAR/MODIFICAR REGISTROS DE LA TABLA CITAS
    public void modificarTratamiento(String idcita, String idpac, String fec, String hor, String est)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE citas SET IdPaciente='"+idpac+"' , fecha='"+fec+"', hora='"+hor+"', estado='"+est+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA SERVICIOS
    public DefaultTableModel buscarServicio(String campo, String valor) throws SQLException
    {
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdCita");
        res.addColumn("IdPaciente");
        res.addColumn("Fecha");        
        res.addColumn("Estado");        
  
    try
    {
        ResultSet resultado=null;
        Statement sentencia=null;
        
        sentencia=(Statement)con.getConnection().createStatement();
        //sentencia=(Statement)con.conexion.createStatement();
        resultado=sentencia.executeQuery("SELECT * FROM citas WHERE "+ campo +" = '"+ valor +"'");
        
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" con el valor "+valor+" no tubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdCita"), x, 0);
                res.setValueAt(resultado.getString("IdPaciente"), x, 1);                
                res.setValueAt(resultado.getString("fecha"), x, 2);
                res.setValueAt(resultado.getString("hora"), x, 3);
                res.setValueAt(resultado.getString("estado"), x, 4);                
                
                x++;
            }
            return res;
        }
    }
    catch(SQLException e)
    {
        JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
    }
        con.desconectar();
        return res;
    }
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA CITAS
    public DefaultTableModel buscarCita(String campo, String valor) throws SQLException
    {
        
        String v = '%'+valor+'%';
        
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdServicio");
        res.addColumn("nombreServicio");
        res.addColumn("Descripción");        
  
    try
    {
        String sql = "SELECT * FROM servicios WHERE "+campo+" like ?";        
        PreparedStatement ps = con.getConnection().prepareStatement(sql);        
        ps.setString(1, v);
        ResultSet resultado = ps.executeQuery();
                       
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            //JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" con el valor "+valor+" no tubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdServicio"), x, 0);
                res.setValueAt(resultado.getString("nombreServicio"), x, 1);                
                res.setValueAt(resultado.getString("descripcion"), x, 2);
                
                
                x++;
            }
            return res;
        }
    }
    catch(SQLException e)
    {
        JOptionPane.showMessageDialog(null,"Error "+e.getMessage());
    }
        con.desconectar();
        return res;
    }
    
}
