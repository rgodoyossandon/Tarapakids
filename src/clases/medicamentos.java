package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class medicamentos {
    bdConexion con;
    
    public medicamentos()
    {
        con = new bdConexion();
    }
    
    //constructor explicito para agregar un nuevo registro
    public void nuevoMedicamento(String idMed, String nomMed, String des, String can, String prec)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " medicamentos(IdMedicamento, nombreMedicamento, descripcion, cantidad, precio)" + "values (?,?,?,?,?)");
            
            pstm.setString(1, idMed);
            pstm.setString(2, nomMed);
            pstm.setString(3, des);
            pstm.setString(4, can);
            pstm.setString(5, prec);
            
            
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
            con.getConnection().prepareStatement("SELECT count(1) as total FROM medicamentos");//cuenta el total de registros de la tabla tratamientos
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
            con.getConnection().prepareStatement("SELECT * FROM medicamentos ORDER BY IdMedicamento");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estIdM = res.getString("IdMedicamento");
                String estNomM = res.getString("nombreMedicamento");
                String estDes = res.getString("descripcion");
                String estCan = res.getString("cantidad");
                String estPre = res.getString("precio");
                                
                data [i][0]=estIdM;
                data [i][1]=estNomM;
                data [i][2]=estDes;
                data [i][3]=estCan;
                data [i][4]=estPre;
                                                             
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
    
    //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA MEDICAM
    public void eliminarMedicamento(String codigo)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=(PreparedStatement) 
            con.getConnection().prepareStatement("DELETE medicamentos.* FROM medicamentos WHERE IdMedicamento='"+codigo+"'");
            //con.conexion.prepareStatement("DELETE medicamentos.* FROM medicamentos WHERE IdMedicamento='"+codigo+"'");
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
    public void modificarMedicamento(String idme, String nme, String des, String can, String pre)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE medicamentos SET nombreMedicamento='"+nme+"' , descripcion='"+des+"', cantidad='"+can+"', precio='"+pre+"' WHERE IdMedicamento='"+idme+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA SERVICIOS
    public DefaultTableModel buscarMedicamento(String campo, String valor) throws SQLException
    {
        String v = '%'+valor+'%';
        
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("Id Medicina");
        res.addColumn("nombre");
        res.addColumn("Descripción");        
        res.addColumn("Cantidad");        
        res.addColumn("Precio");
  
    try
    {
        String sql = "SELECT * FROM medicamentos WHERE "+campo+" like ?";        
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
                res.setValueAt(resultado.getString("IdMedicamento"), x, 0);
                res.setValueAt(resultado.getString("nombreMedicamento"), x, 1);                
                res.setValueAt(resultado.getString("descripcion"), x, 2);
                res.setValueAt(resultado.getString("cantidad"), x, 3);
                res.setValueAt(resultado.getString("precio"), x, 4);
                
                
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
