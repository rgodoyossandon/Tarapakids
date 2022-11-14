package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class paciente {
    bdConexion con;
    
    public paciente()
    {
        con = new bdConexion();
    }
    
     /* constructor explicito agregar un nuevo registro*/
    public void nuevoPaciente(String idPaciente, String dui, String nombres, String apellidos, String direccion, String region, String ciudad, String telefono, String email, String edad)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " paciente(idPaciente, dui, nombres, apellidos, direccion, region, ciudad, telefono, email, edad)" + "values (?,?,?,?,?,?,?,?,?,?)");
            
            pstm.setString(1, idPaciente);
            pstm.setString(2, dui);
            pstm.setString(3, nombres);
            pstm.setString(4, apellidos);
            pstm.setString(5, direccion);
            pstm.setString(6, region);
            pstm.setString(7, ciudad);
            pstm.setString(8, telefono);
            pstm.setString(9, email);
            pstm.setString(10, edad);            
            
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
            con.getConnection().prepareStatement("SELECT count(1) as total FROM paciente");//cuenta el total de registros de la tabla pacientes
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][10];
        
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT * FROM paciente ORDER BY IdPaciente ASC");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())
            {
                String estIdPaciente = res.getString("IdPaciente");
                String estDui = res.getString("dui");
                String estNombres = res.getString("nombres");
                String estApellidos = res.getString("apellidos");
                String estDireccion = res.getString("direccion");
                String estRe = res.getString("region");
                String estCi = res.getString("ciudad");
                String estTelefono = res.getString("telefono");
                String estEmail = res.getString("email");
                String estFechaNac = res.getString("edad");                               
                
                data [i][0]=estIdPaciente;
                data [i][1]=estDui;
                data [i][2]=estNombres;
                data [i][3]=estApellidos;
                data [i][4]=estDireccion;
                data [i][5]=estRe;
                data [i][6]=estCi;
                data [i][7]=estTelefono;
                data [i][8]=estEmail;
                data [i][9]=estFechaNac;
                                             
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
    
    public Object[][]getDatos2Pacientes()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla pacientes
        con = new bdConexion();
        try
        {            
            String sql = "SELECT count(1) as total FROM paciente";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            try (ResultSet res = ps.executeQuery()) {
                res.next();
                registros = res.getInt("total");
            }
            System.out.println("Total pacientes: "+registros);
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
            con.getConnection().prepareStatement("SELECT IdPaciente, concat(nombres,' ',apellidos) as nombrePa FROM paciente ORDER BY IdPaciente ASC");
            ResultSet res=pstm.executeQuery();
                        
            int i=0;            
            while (res.next())
            {
                String estIdPaciente = res.getString("IdPaciente");                
                String estNombres = res.getString("nombrePa");
                                              
                
                data [i][0]=estIdPaciente;                
                data [i][1]=estNombres;
  
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
    
    
    //METODO QUE PERMITE ELIMINAR UN REGISTRO DE LA TABLA PACIENTE
    public void eliminarPaciente(String codigo)
    {
        try
        {
            int rows_update=0;
            PreparedStatement pstm=con.getConnection().prepareStatement("DELETE paciente.* FROM paciente WHERE IdPaciente='"+codigo+"'");
            //PreparedStatement pstm=con.conexion.prepareStatement("DELETE paciente.* FROM paciente WHERE IdPaciente='"+codigo+"'");
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
    
    //METODO PARA ACTUALIZAR/MODIFICAR REGISTROS DE LA TABLA PACIENTE
    public void modificarPaciente(String idpac, String dui, String nom, String ape, String dir, String re, String ci, String tel, String em, String edad)
    {
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE paciente SET dui='"+dui+"' , nombres='"+nom+"', apellidos='"+ape+"', direccion='"+dir+"', region='"+re+"', ciudad='"+ci+"', telefono1='"+tel+"', email='"+em+"', edad='"+edad+"' WHERE IdPaciente='"+idpac+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA PACIENTE
    public DefaultTableModel buscarPaciente(String campo, String valor) throws SQLException
    {               
        String v = '%'+valor+'%';
        
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdPaciente");
        res.addColumn("Dui");
        res.addColumn("Nombres");
        res.addColumn("Apellidos");
        res.addColumn("Direccion");
        res.addColumn("Region");
        res.addColumn("Ciudad");
        res.addColumn("Celular");
        res.addColumn("Email");
        res.addColumn("Edad");                
  
    try
    {
        String sql = "SELECT * FROM paciente WHERE "+campo+" like ?";        
        PreparedStatement ps = con.getConnection().prepareStatement(sql);        
        ps.setString(1, v);
        ResultSet resultado = ps.executeQuery();           
        
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            //JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" \n y con el valor "+valor+" no obtubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdPaciente"), x, 0);
                res.setValueAt(resultado.getString("dui"), x, 1);                
                res.setValueAt(resultado.getString("nombres"), x, 2);
                res.setValueAt(resultado.getString("apellidos"), x, 3);
                res.setValueAt(resultado.getString("direccion"), x, 4);
                res.setValueAt(resultado.getString("region"), x, 5);
                res.setValueAt(resultado.getString("ciudad"), x, 6);
                res.setValueAt(resultado.getString("telefono"), x, 7);
                res.setValueAt(resultado.getString("email"), x, 8);
                res.setValueAt(resultado.getString("edad"), x, 9);                            
                
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
     
     //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA PACIENTE
    public DefaultTableModel buscarPaciente2(String campo, String valor) throws SQLException
    {               
        String v = '%'+valor+'%';
        
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("IdPaciente");        
        res.addColumn("Nombre del Paciente");
                  
  
    try
    {
        String sql = "SELECT * FROM paciente WHERE "+campo+" like ?";        
        PreparedStatement ps = con.getConnection().prepareStatement(sql);        
        ps.setString(1, v);
        ResultSet resultado = ps.executeQuery();           
        
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            //JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" \n y con el valor "+valor+" no obtubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdPaciente"), x, 0);                           
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
    
    //METODO PARA REALIZAR BUSQUEDA DE REGISTROS TABLA PACIENTE EN formulario Citas
    public DefaultTableModel buscarPaciente3(String campo, String valor) throws SQLException
    {               
        String v = '%'+valor+'%';
        
        DefaultTableModel res= new DefaultTableModel();
        res.addColumn("Codigo");      
        res.addColumn("Nombre");
        res.addColumn("Direccion");
        res.addColumn("Celular");
        res.addColumn("Telefono");            
  
    try
    {
        String sql = "SELECT * FROM paciente WHERE "+campo+" like ?";        
        PreparedStatement ps = con.getConnection().prepareStatement(sql);        
        ps.setString(1, v);
        ResultSet resultado = ps.executeQuery();           
        
        int x=0;
        resultado.last();
        
        if(resultado.getRow()<=0)
        {
            //JOptionPane.showMessageDialog(null,"Lo siento, su consulta con el campo "+campo+" \n y con el valor "+valor+" no obtubo resultados");
            return res;
        }
        else
        {
            resultado.beforeFirst();
            
            while(resultado.next())
            {
                res.addRow(new Object[]{});
                res.setValueAt(resultado.getString("IdPaciente"), x, 0);
                res.setValueAt(resultado.getString("nombres")+" "+resultado.getString("apellidos"), x, 1);                
                res.setValueAt(resultado.getString("direccion"), x, 2);
                res.setValueAt(resultado.getString("telefono"), x, 3);
                res.setValueAt(resultado.getString("email"), x, 4);                        
                
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
