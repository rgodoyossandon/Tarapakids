package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class tratamientos {
    bdConexion con;
    
    public tratamientos()
    {
        con = new bdConexion();
    }
    
    //constructor explicito para agregar un nuevo registro
    public void nuevoTratamiento(String idTra, String nomTra, String des, String tar, String cser)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " tratamientos(IdTratamiento, nombreTratamiento, descripcion, tarifa, IdServicio)" + "values (?,?,?,?,?)");
            
            pstm.setString(1, idTra);
            pstm.setString(2, nomTra);
            pstm.setString(3, des);
            pstm.setString(4, tar);
            pstm.setString(5, cser);
            
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
        //obtener la cantidad de registros que hay en la tabla tratamientos
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM tratamientos");//cuenta el total de registros de la tabla tratamientos
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
            //con.getConnection().prepareStatement("SELECT * FROM tratamientos ORDER BY IdTratamiento");
            con.getConnection().prepareStatement("SELECT t.*, s.nombreServicio FROM tratamientos t INNER JOIN servicios s ON t.IdServicio=s.IdServicio ORDER BY IdTratamiento;");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estIdTra = res.getString("IdTratamiento");
                String estNomTra = res.getString("nombreTratamiento");
                String estDes = res.getString("descripcion");
                String estTar = res.getString("tarifa");
                String estSer = res.getString("nombreServicio");
                                
                data [i][0]=estIdTra;
                data [i][1]=estNomTra;
                data [i][2]=estDes;
                data [i][3]=estTar;
                data [i][4]=estSer;
                                                             
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
    
    //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA TRATAMIENTOS
    public void eliminarTratamiento(String codigo)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=(PreparedStatement) 
            con.getConnection().prepareStatement("DELETE tratamientos.* FROM tratamientos WHERE IdTratamiento='"+codigo+"'");
            //con.conexion.prepareStatement("DELETE tratamientos.* FROM tratamientos WHERE IdTratamiento='"+codigo+"'");
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
    
     //METODO PARA ACTUALIZAR/MODIFICAR REGISTROS DE LA TABLA TRATAMIENTOS
    public void modificarTratamiento(String idtra, String ntra, String des, String tar, String ser)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE tratamientos SET nombreTratamiento='"+ntra+"' , descripcion='"+des+"', tarifa='"+tar+"', IdServicio='"+ser+"' WHERE IdTratamiento='"+idtra+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA SERVICIOS
    public DefaultTableModel buscarTratamiento(String campo, String valor) throws SQLException
    {
        String v = '%'+valor+'%';
        
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdTratamiento");
        res.addColumn("nombreTratamiento");
        res.addColumn("Descripción");        
        res.addColumn("Tarifa");        
        res.addColumn("Servicio");        
  
    try
    {
        String sql = "SELECT * FROM tratamientos WHERE "+campo+" like ?";        
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
                res.setValueAt(resultado.getString("IdTratamiento"), x, 0);
                res.setValueAt(resultado.getString("nombreTratamiento"), x, 1);                
                res.setValueAt(resultado.getString("descripcion"), x, 2);
                res.setValueAt(resultado.getString("tarifa"), x, 3);
                res.setValueAt(resultado.getString("IdServicio"), x, 4);
                
                
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
