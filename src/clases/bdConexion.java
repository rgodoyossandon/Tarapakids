package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class bdConexion {
    private static java.sql.Connection conn = null;
    static Statement st= null;
    static ResultSet rs =null;
    
    
    static String bd="tarapakids";
    static String user="root";
    static String pass="root";
    static String url="jdbc:mysql://localhost:3306/"+bd+"?serverTimezone=UTC&useSSL=false";    
    
   // Connection conexion = null;

    public bdConexion() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);

            if (conn != null) {
                //JOptionPane.showMessageDialog(null, "Conectado satisfactoriamente");
                System.out.println("Conexion exitosa");
            }

        } catch (SQLException e) {
            // System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (ClassNotFoundException e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void desconectar() throws SQLException {
        conn.close();
    }
}
