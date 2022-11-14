package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class consultas {
        
    bdConexion con;
    public consultas()
    {
        con = new bdConexion();
    }
    
    
    //constructor explicito para agregar un nuevo registro
    public void nuevaConsulta(String idC, String idP, String iS, String iT, String iE, String diag, String f, String h)
    {
        
        try
        {            
            PreparedStatement pstm=(PreparedStatement)
            //com.mysql.jdbc.PreparedStatement pstm=(com.mysql.jdbc.PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " consultas(IdConsulta, IdPaciente , IdServicio, IdTratamiento, IdEspecialista, diagnostico, fecha, hora)" + "values (?,?,?,?,?,?,?,?)");
            
            pstm.setString(1, idC);            
            pstm.setString(2, idP);            
            pstm.setString(3, iS);
            pstm.setString(4, iT);
            pstm.setString(5, iE);
            pstm.setString(6, diag);
            pstm.setString(7, f);
            pstm.setString(8, h);
                        
            pstm.execute();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.print(e);
        }
    }
    
    
    //constructor explicito para agregar un nuevo registro
    public void nuevaReceta(String idR, String idP, String f, String idM, String cant, String des, String pu, String t)
    {
       
        try
        {
            //com.mysql.jdbc.PreparedStatement pstm1=(com.mysql.jdbc.PreparedStatement)
            PreparedStatement pstm1=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " recetamedica(IdReceta, IdPaciente, fecha)" + "values (?,?,?)");

            pstm1.setString(1, idR);            
            pstm1.setString(2, idP);            
            pstm1.setString(3, f);

            pstm1.execute();
            pstm1.close();
        }
        catch(SQLException e)
        {
            System.out.print(e);
        }

        //tambien debemos colocar la informacion correspondiente en la tabla detalle expediente
        try
        {
            //com.mysql.jdbc.PreparedStatement pstm2=(com.mysql.jdbc.PreparedStatement)
            PreparedStatement pstm2=(PreparedStatement)
            con.getConnection().prepareStatement("insert into " + " detallerecetamedica(IdReceta, IdMedicamento, cantidad, descripcion, precio, total)" + "values (?,?,?,?,?,?)");

            pstm2.setString(1, idR);            
            pstm2.setString(2, idM);
            pstm2.setString(3, cant);
            pstm2.setString(4, des);
            pstm2.setString(5, pu);
            pstm2.setString(6, t);


            pstm2.execute();
            pstm2.close();
        }
        catch(SQLException e)
        {
            System.out.print(e);
        }               
        
    }             
    
    //Para los datos de los pacientes
    public Object[][]getDatosPacientes()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla paciente
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
        
        Object[][] data=new String [registros][3];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT IdPaciente, nombres, apellidos FROM  paciente ORDER BY IdPaciente");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//Bucle para recorrer la consulta y colocar los datos del paciente en la matriz
            {
                String ip = res.getString("IdPaciente");
                String np = res.getString("nombres")+" "+res.getString("apellidos");
                //String dir= res.getString("direccion");
                
                                               
                                              
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=ip;
                data [i][1]=np;
                //data [i][2]=dir;                

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
    
        //para los datos de las citas, cuando es una consulta programada
    public Object[][]getDatosCitas(String fec)
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla citas
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM citas WHERE estado=1 AND fecha='"+fec+"'");//cuenta el total de registros que esten vigentes a la fecha actual
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }
        
        Object[][] data=new String [registros][6];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            //SELECT c.*, p.nombres, p.apellidos FROM citas c INNER JOIN paciente p ON c.IdPaciente = p.IdPaciente WHERE c.estado=3 AND c.fecha='2012-11-30';
            //con.getConnection().prepareStatement("SELECT * FROM citas WHERE estado=1 AND fecha='"+fec+"'");
            con.getConnection().prepareStatement("SELECT c.*, p.nombres, p.apellidos FROM citas c INNER JOIN paciente p ON c.IdPaciente = p.IdPaciente WHERE c.estado=1 AND c.fecha='"+fec+"'");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//bucle que permite colocar los datos obtenidos de la consulta en la matriz de datos
            {
                String ic = res.getString("IdCita");
                String ip = res.getString("IdPaciente");
                String np = res.getString("nombres")+" "+res.getString("apellidos");
                //String ap = res.getString("apellidos");
                String fc= res.getString("fecha");
                String hc = res.getString("hora");
                                               
                                              
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=ic;
                data [i][1]=ip;
                data [i][2]=np;
                //data [i][3]=ap;
                data [i][3]=fc;
                data [i][4]=hc;                

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
    
    //para los servicios
    public Object[][]getDatosServicios()
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
            
            while (res.next())//bucle que permite colocar los datos obtenidos de la consulta para mostrar en la tabla citasVencidas del frmGestionCitas
            {
                String is = res.getString("IdServicio");
                String ns = res.getString("nombreServicio");
                String ds= res.getString("descripcion");
                                                                                                             
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=is;
                data [i][1]=ns;
                data [i][2]=ds;              

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
    
    //para los tratamientos
    public Object[][]getDatosTratamientos(String codSer)
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
        
        Object[][] data=new String [registros][4];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            //String sql = "SELECT * FROM tratamientos WHERE "+campo+" like ?";   
            con.getConnection().prepareStatement("SELECT * FROM tratamientos WHERE IdServicio = '"+codSer+"' ORDER BY IdTratamiento");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//Bucle para llenar la matriz de datos
            {
                String iT = res.getString("IdTratamiento");
                String nT = res.getString("nombreTratamiento");
                String dT= res.getString("descripcion");
                String tT= res.getString("tarifa");
                                                                                                             
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=iT;
                data [i][1]=nT;
                data [i][2]=dT;
                data [i][3]=tT;              

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
    
    //para los especialistas
    public Object[][]getDatosEspecialista()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla especialista
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
        
        Object[][] data=new String [registros][2];
                
        //realizamos la consulta sql y llenamos los datos del Object
        
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT IdEspecialista, nombres, apellidos FROM especialista ORDER BY IdEspecialista");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//Bucle para llenar la matriz de datos
            {
                String iE = res.getString("IdEspecialista");
                String nE = res.getString("nombres")+" "+res.getString("apellidos");
                
                                                                                                             
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=iE;
                data [i][1]=nE;

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
 
    //para los tratamientos
    public Object[][]getDatosMedicamentos()
    {
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla tratamientos
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM medicamentos");//cuenta el total de registros de la tabla medicamentos
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
            
            while (res.next())//Bucle para llenar la matriz de datos
            {
                String iM = res.getString("IdMedicamento");
                String nM = res.getString("nombreMedicamento");
                String dM= res.getString("descripcion");
                String cM= res.getString("cantidad");
                String pM= res.getString("precio");
                                                                                                             
                //llenamos la matriz con los valores encontrados de la consulta
                data [i][0]=iM;
                data [i][1]=nM;
                data [i][2]=dM;
                data [i][3]=cM;              
                data [i][4]=pM;              

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
    
    //METODO PARA ACTUALIZAR EL STOCK DE LA TABLA PRODUCTOS, CUANDO SE ANULA LA SALIDA DE MERCADERIA
    public void ajustarStockMedicamentos(String codM, String can)
    {
        double c=0;
        double cnt = Double.parseDouble(can);
        
    try
        {
            //com.mysql.jdbc.PreparedStatement pstm=(com.mysql.jdbc.PreparedStatement)
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT cantidad FROM medicamentos WHERE IdMedicamento= '"+codM+"'");
            ResultSet res=pstm.executeQuery();
            
            int i=0;
            
            while (res.next())//aqui obtenemos el stock actual de la mercaderia segun el codigo del producto
            {
                int cantidad = res.getInt("cantidad");
                                
                c= cantidad - cnt;//como se realizo una venta, se resta con el stock que ya existia para actualizar la cantidad
                                
                i++;                
            }
            
            res.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        try
        {
            //com.mysql.jdbc.PreparedStatement pstm=(com.mysql.jdbc.PreparedStatement)
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE medicamentos SET cantidad='"+c+"' WHERE IdMedicamento='"+codM+"'");
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    } 
    
    //METODO PARA CAMBIAR EL ESTADO DE LA CITA A REALIZADA, CUANDO SE EFECTUA LA CONSULTA
    public void citaRealizada(String ic)
    {               

        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("UPDATE citas SET estado=3 WHERE IdCita='"+ic+"'");
            //el estado=3, significa que la consulta se llevo a cabo por lo tanto la cita ya no esta vigente sino consumada
            pstm.executeUpdate();
            pstm.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }            
    }        
    
}
