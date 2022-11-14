package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class servicios {
    bdConexion con;
    
    public servicios()
    {
        con = new bdConexion();
    }
    
     /* constructor explicito agregar un nuevo registro*/
    public void nuevoServicio(String idSer, String nSer, String des)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " servicios(IdServicio, nombreServicio, descripcion)" + "values (?,?,?)");
            
            pstm.setString(1, idSer);
            pstm.setString(2, nSer);
            pstm.setString(3, des);
            
            
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
        //obtener la cantidad de registros que hay en la tabla servicios
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM servicios");//cuenta el total de registros de la tabla servicios
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][3];
        
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT * FROM servicios ORDER BY IdServicio");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estIdSer = res.getString("IdServicio");
                String estNomSer = res.getString("nombreServicio");
                String estDes = res.getString("descripcion");
                                
                data [i][0]=estIdSer;
                data [i][1]=estNomSer;
                data [i][2]=estDes;
                                                             
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
    
    //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA SERVICIOS
    public void eliminarServicio(String codigo)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=(PreparedStatement) con.getConnection().prepareStatement("DELETE servicios.* FROM servicios WHERE IdServicio='"+codigo+"'");
            //PreparedStatement pstm=(PreparedStatement) con.conexion.prepareStatement("DELETE servicios.* FROM servicios WHERE IdServicio='"+codigo+"'");
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
    
     //METODO PARA ACTUALIZAR/MODIFICAR REGISTROS DE LA TABLA SERVICIOS
    public void modificarServicio(String idser, String nser, String des)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE servicios SET nombreServicio='"+nser+"' , descripcion='"+des+"'WHERE IdServicio='"+idser+"'");
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
