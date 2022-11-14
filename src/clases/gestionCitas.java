package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class gestionCitas {
    bdConexion con;
    public gestionCitas()
    {
        con = new bdConexion();
    }
    
    //para las citas vigentes
    public Object[][]getDatos1()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla citas
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM citas WHERE estado=1");//cuenta el total de registros de la tabla Citas que esten vigentes
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][4];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT * FROM citas WHERE estado=1 ORDER BY fecha");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//bucle que permite colocar los datos obtenidos de la consulta para mostrar en la tabla citasVigentes del frmGestionCitas
            {
                String ic = res.getString("IdCita");
                String ip = res.getString("IdPaciente");
                String fec= res.getString("fecha");
                String hora = res.getString("hora");
                                               
                                              
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=ic;
                data [i][1]=ip;
                data [i][2]=fec;
                data [i][3]=hora;                

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
    
        //para las citas Anuladas
    public Object[][]getDatos2()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla citas
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM citas WHERE estado=0");//cuenta el total de registros de la tabla Citas que esten anuladas
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][4];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT * FROM citas WHERE estado=0 ORDER BY fecha"); // estado=0 significa cita anulada
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//bucle que permite colocar los datos obtenidos de la consulta para mostrar en la tabla citasAnuladas del frmGestionCitas
            {
                String ic = res.getString("IdCita");
                String ip = res.getString("IdPaciente");
                String fec= res.getString("fecha");
                String hora = res.getString("hora");
                                               
                                              
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=ic;
                data [i][1]=ip;
                data [i][2]=fec;
                data [i][3]=hora;                

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
    
    //para las citas vencidas
    public Object[][]getDatos3()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla citas
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM citas WHERE estado=2");//cuenta el total de registros de la tabla Citas que esten vencidas
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][4];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT * FROM citas WHERE estado=2 ORDER BY fecha"); // estado=2 significa cita vencida
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//bucle que permite colocar los datos obtenidos de la consulta para mostrar en la tabla citasVencidas del frmGestionCitas
            {
                String ic = res.getString("IdCita");
                String ip = res.getString("IdPaciente");
                String fec= res.getString("fecha");
                String hora = res.getString("hora");
                                               
                                              
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=ic;
                data [i][1]=ip;
                data [i][2]=fec;
                data [i][3]=hora;                

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
    
    //METODO PARA ANULAR CITAS VIGENTES
    public void anularCita(String ic)
    {                
        //recordar que cero es el valor que indica que una cita se anulo.
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE citas SET estado='"+0+"' WHERE IdCita='"+ic+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }               
        
    }         
    
    //METODO REPROGRAMAR CITA VIGENTE O VENCIDA
    public void reprogramarCita(String ic,  String fe, String ho, String est)
    {
        int es=1;
        if(est.equals("1"))//significa que es una cita que esta vigente pero que se reprogramara
        {
            try
            {
                PreparedStatement pstm=(PreparedStatement)
                con.getConnection().prepareStatement("UPDATE citas SET fecha='"+fe+"', hora='"+ho+"' WHERE IdCita='"+ic+"'");
                pstm.executeUpdate();
                pstm.close();
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }            
        }else//significa que se reprogramara una cita que ya se vencio y pasara a estar vigente nuevamente.
        {
            try
            {
                PreparedStatement pstm=(PreparedStatement)
                con.getConnection().prepareStatement("UPDATE citas SET fecha='"+fe+"', hora='"+ho+"', estado='"+es+"' WHERE IdCita='"+ic+"'");
                pstm.executeUpdate();
                pstm.close();
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
            
        }
    }
    
    //METODO CITAS VENCIDAS O CADUCADAS QUE NO SE EFECTUARON LA CONSULTA
    public void citasVencidas(String ic)
    {               

        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE citas SET estado=2 WHERE IdCita='"+ic+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }            
    }        
    
}
