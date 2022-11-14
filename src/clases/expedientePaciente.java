package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class expedientePaciente {
    bdConexion con;
    
    public expedientePaciente()
    {
        con = new bdConexion();
    }
    
    //GENERAR NUEVO EXPEDIENTE
    public void nuevoExpediente(String ic, String ip, String fec)
    {
        String ie=null;
        
        /*primero debemos verificar si la tabla expedientepaciente esta vacia o no
         * para generar un nuevo expediente
         */
        
        int registros=0;
        //obtener la cantidad de registros que hay en la tabla expedientepaciente
        try
        {
            PreparedStatement pstm=(PreparedStatement)
            con.getConnection().prepareStatement("SELECT count(1) as total FROM expedientepaciente");
            ResultSet res=pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
            
            if (registros <=0)//significa que la tabla no tiene ningun registro
            {
                ie="000001";
                //colocamos la informacion en la tabla expedientepaciente
                try
                {
                    //com.mysql.jdbc.PreparedStatement pstm1=(com.mysql.jdbc.PreparedStatement)
                    PreparedStatement pstm1=(PreparedStatement)
                    con.getConnection().prepareStatement("insert into " + " expedientepaciente(IdExpediente, IdPaciente, fecha)" + "values (?,?,?)");

                    pstm1.setString(1, ie);            
                    pstm1.setString(2, ip);            
                    pstm1.setString(3, fec);

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
                    con.getConnection().prepareStatement("insert into " + " detalleexpedientepaciente(IdExpediente, IdConsulta, fecha)" + "values (?,?,?)");

                    pstm2.setString(1, ie);            
                    pstm2.setString(2, ic);
                    pstm2.setString(3, fec);


                    pstm2.execute();
                    pstm2.close();
                }
                catch(SQLException e)
                {
                    System.out.print(e);
                }
                
            }else//la tabla no esta vacia
            {
                /*si la tabla no esta vacia entonces se debe averiguar si el paciente ya tiene expediente
                 * creado por alguna consulta anterior, sino crear un nuevo expediente
                 */
                int registros2=0;
                try
                {
                    PreparedStatement pstm3=(PreparedStatement)
                    con.getConnection().prepareStatement("SELECT count(1) as total FROM expedientepaciente WHERE IdPaciente='"+ip+"'");
                    ResultSet res3=pstm3.executeQuery();
                    res3.next();
                    registros2 = res3.getInt("total");
                    res3.close();

                    if (registros2 <=0)//significa que el paciente no tiene expediente aun
                    {
                        //procedemos a crear un nuevo expediente para el paciente a partir del ultimo expediente creado
                        try
                        {
                           java.sql.PreparedStatement pstm4=(java.sql.PreparedStatement)
                            con.getConnection().prepareStatement("SELECT MAX(IdExpediente) as codigo FROM expedientepaciente");
                            ResultSet res4=pstm4.executeQuery();

                            res4.first();
                            String v =res4.getString("codigo");

                            int cod=(Integer.parseInt(v))+1;


                            if ( cod<10)
                            {
                                ie="00000"+cod;
                            }
                            else if(cod<100)
                            {
                                ie="0000"+cod;
                            }
                            else if(cod<1000)
                            {
                                ie="000"+cod;
                            }
                            else if(cod<10000)
                            {
                                ie="00"+cod;
                            }
                            else if(cod<100000)
                            {
                                ie="0"+cod;
                            }
                            else
                            {
                                ie=v;
                            }

                          
                            //ahora debemos crear el nuevo expediente para el paciente
                            try
                            {
                                //com.mysql.jdbc.PreparedStatement pstm5=(com.mysql.jdbc.PreparedStatement)
                                PreparedStatement pstm5=(PreparedStatement)
                                con.getConnection().prepareStatement("insert into " + "expedientepaciente(IdExpediente, IdPaciente, fecha)" + "values (?,?,?)");

                                pstm5.setString(1, ie);            
                                pstm5.setString(2, ip);
                                pstm5.setString(3, fec);


                                pstm5.execute();
                                pstm5.close();
                                
                                //tambien debemos colocar la informacion correspondiente en la tabla detalle expediente
                                try
                                {
                                    //com.mysql.jdbc.PreparedStatement pstm6=(com.mysql.jdbc.PreparedStatement)
                                    PreparedStatement pstm6=(PreparedStatement)
                                    con.getConnection().prepareStatement("insert into " + " detalleexpedientepaciente(IdExpediente, IdConsulta, fecha)" + "values (?,?,?)");

                                    pstm6.setString(1, ie);            
                                    pstm6.setString(2, ic);
                                    pstm6.setString(3, fec);


                                    pstm6.execute();
                                    pstm6.close();
                                }
                                catch(SQLException e)
                                {
                                    System.out.print(e);
                                } 
                                
                                
                            }
                            catch(SQLException e)
                            {
                                System.out.print(e);
                            }
                            
                                                                                   
                        pstm4.close();
                        }
                        catch(SQLException e)
                        {
                             System.out.println(e);   
                        }

                    }else//significa que el paciente ya tiene expediente por lo tanto solo hay que actualizar el detalleexpediente
                    {
                        String idEx=null;
                        //buscamos primero el numero del expediente del paciente para luego realizar el nuevo insert
                        try
                        {
                            PreparedStatement pstm7=(PreparedStatement)
                            con.getConnection().prepareStatement("SELECT IdExpediente FROM expedientepaciente WHERE IdPaciente='"+ip+"'");
                            ResultSet res7=pstm7.executeQuery();
                            res7.next();
                            idEx = res7.getString("IdExpediente");
                            
                            //insertamos la informacion solo en el detalleexpediente, basado en el numero de expediente del paciente
                            try
                            {
                                //com.mysql.jdbc.PreparedStatement pstm8=(com.mysql.jdbc.PreparedStatement)
                                PreparedStatement pstm8=(PreparedStatement)
                                con.getConnection().prepareStatement("insert into " + " detalleexpedientepaciente(IdExpediente, IdConsulta, fecha)" + "values (?,?,?)");

                                pstm8.setString(1, idEx);            
                                pstm8.setString(2, ic);
                                pstm8.setString(2, fec);


                                pstm8.execute();
                                pstm8.close();
                            }
                            catch(SQLException e)
                            {
                                System.out.print(e);
                            }                                                                                    
                            
                            res7.close();
                        }
                        catch(SQLException e)
                        {
                         System.out.println(e);   
                        }
                                                                                                                        
                    }//fin else
                }
                catch(SQLException e)
                {
                     System.out.println(e);   
                }
                
            }//fin else
            
            
        }
        catch(SQLException e)
        {
         System.out.println(e);   
        }         
    }
}
