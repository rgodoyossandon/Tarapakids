package clases;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class especialista {
    bdConexion con;
    
    public especialista()
    {
        con = new bdConexion();
    }
    
     /* constructor explicito agregar un nuevo registro*/
    public void nuevoEspecialista(String idesp, String dui, String nombres, String apellidos, String direccion, String region, String ciudad, String tel, String cel)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " especialista(idEspecialista, dui, nombres, apellidos, direccion, region, ciudad, telefono, celular)" + "values (?,?,?,?,?,?,?,?,?)");
            
            pstm.setString(1, idesp);
            pstm.setString(2, dui);
            pstm.setString(3, nombres);
            pstm.setString(4, apellidos);
            pstm.setString(5, direccion);
            pstm.setString(6, region);
            pstm.setString(7, ciudad);
            pstm.setString(8, tel);
            pstm.setString(9, cel);            
            
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
        //obtener la cantidad de registros que hay en la tabla pacientes
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM especialista");//cuenta el total de registros de la tabla especialista
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][9];
        
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT * FROM especialista ORDER BY IdEspecialista ASC");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estIdEsp = res.getString("IdEspecialista");
                String estDui = res.getString("dui");
                String estNombres = res.getString("nombres");
                String estApellidos = res.getString("apellidos");
                String estDireccion = res.getString("direccion");
                String estRe = res.getString("region");
                String estCi = res.getString("ciudad");
                String estTel = res.getString("telefono");
                String estCel = res.getString("celular");
                                              
                
                data [i][0]=estIdEsp;
                data [i][1]=estDui;
                data [i][2]=estNombres;
                data [i][3]=estApellidos;
                data [i][4]=estDireccion;
                data [i][5]=estRe;
                data [i][6]=estCi;
                data [i][7]=estTel;
                data [i][8]=estCel;                
                                             
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
    
    //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA ESPECIALISTA
    public void eliminarEspecialista(String codigo)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=con.getConnection().prepareStatement("DELETE especialista.* FROM especialista WHERE IdEspecialista='"+codigo+"'");
            //PreparedStatement pstm=con.conexion.prepareStatement("DELETE especialista.* FROM especialista WHERE IdEspecialista='"+codigo+"'");
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
    
    //METODO PARA ACTUALIZAR/MODIFICAR REGISTROS DE LA TABLA ESPECIALISTA
    public void modificarEspecialista(String idesp, String dui, String nom, String ape, String dir, String re, String ciu, String tel, String cel)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE especialista SET dui='"+dui+"' , nombres='"+nom+"', apellidos='"+ape+"', direccion='"+dir+"', region='"+re+"', ciudad='"+ciu+"', telefono='"+tel+"', celular='"+cel+"' WHERE IdEspecialista='"+idesp+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA ESPECIALISTA
    public DefaultTableModel buscarEspecialista(String campo, String valor) throws SQLException
    {
        String v = '%'+valor+'%';
         
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdEspecialista");
        res.addColumn("DUI");
        res.addColumn("Nombres");
        res.addColumn("Apellidos");
        res.addColumn("Direccion");
        res.addColumn("Region");
        res.addColumn("Ciudad");
        res.addColumn("Teléfono");
        res.addColumn("Celular");                       
  
    try
    {
        String sql = "SELECT * FROM especialista WHERE "+campo+" like ?";        
        PreparedStatement ps = con.getConnection().prepareStatement(sql);        
        ps.setString(1, v);
        ResultSet resultado = ps.executeQuery();
        
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            //JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" y con el valor "+valor+" no tubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdEspecialista"), x, 0);
                res.setValueAt(resultado.getString("dui"), x, 1);                
                res.setValueAt(resultado.getString("nombres"), x, 2);
                res.setValueAt(resultado.getString("apellidos"), x, 3);
                res.setValueAt(resultado.getString("direccion"), x, 4);
                res.setValueAt(resultado.getString("region"), x, 5);
                res.setValueAt(resultado.getString("ciudad"), x, 6);
                res.setValueAt(resultado.getString("telefono"), x, 7);
                res.setValueAt(resultado.getString("celular"), x, 8);
                
                
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
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA ESPECIALISTA
    public DefaultTableModel buscarEspecialista2(String campo, String valor) throws SQLException
    {
        String v = '%'+valor+'%';
         
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdEspecialista");        
        res.addColumn("Nombre");                          
  
    try
    {
        String sql = "SELECT * FROM especialista WHERE "+campo+" like ?";        
        PreparedStatement ps = con.getConnection().prepareStatement(sql);        
        ps.setString(1, v);
        ResultSet resultado = ps.executeQuery();
        
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            //JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" y con el valor "+valor+" no tubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdEspecialista"), x, 0);
                res.setValueAt(resultado.getString("nombres")+" "+resultado.getString("apellidos"), x, 1);                                
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
