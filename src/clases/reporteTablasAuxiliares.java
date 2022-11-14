package clases;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class reporteTablasAuxiliares {
    private bdConexion c=null;
    
    public String fecha;
    
    public reporteTablasAuxiliares()
    {

    }
     //Reporte Especialistas   
    public void abrirReporteListadoEspecialistas() throws SQLException {
        try {
            c = new bdConexion();            
            String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Listar_Todos_Especialistas.jrxml";
            System.out.println("Cadena de conexion: "+c.getConnection());
            System.out.println("Ruta del reporte: "+ruta_reporte);
            
            if (ruta_reporte.equals("")) {
                JOptionPane.showMessageDialog(null,"No se encontró el archivo del reporte solicitado");
                System.exit(2);
                c.desconectar();
            }
            
            JasperReport report = JasperCompileManager.compileReport(ruta_reporte);
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), c.getConnection());
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setVisible(true);
            jviewer.setExtendedState(MAXIMIZED_BOTH);
            
        } catch (JRException jRException) {
            System.err.println(jRException.getMessage());
            JOptionPane.showMessageDialog(null, "Error al internar abrir el reporte");
        }
    }
    
    //Reporte Tratamientos
    public void abrirReporteListadoTratamientos() throws SQLException {
        try {
            c = new bdConexion();            
            String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Listar_Todos_Tratamientos.jrxml";
            System.out.println("Cadena de conexion: "+c.getConnection());
            System.out.println("Ruta del reporte: "+ruta_reporte);
            
            if (ruta_reporte.equals("")) {
                JOptionPane.showMessageDialog(null,"No se encontró el archivo del reporte solicitado");
                System.exit(2);
                c.desconectar();
            }
            
            JasperReport report = JasperCompileManager.compileReport(ruta_reporte);
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), c.getConnection());
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setVisible(true);
            jviewer.setExtendedState(MAXIMIZED_BOTH);
            
        } catch (JRException jRException) {
            System.err.println(jRException.getMessage());
            JOptionPane.showMessageDialog(null, "Error al internar abrir el reporte");
        }
    }
    
    //Reporte Servicios
    public void abrirReporteListadoServicios() throws SQLException {
        try {
            c = new bdConexion();            
            String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Listar_Todos_Servicios.jrxml";
            System.out.println("Cadena de conexion: "+c.getConnection());
            System.out.println("Ruta del reporte: "+ruta_reporte);
            
            if (ruta_reporte.equals("")) {
                JOptionPane.showMessageDialog(null,"No se encontró el archivo del reporte solicitado");
                System.exit(2);
                c.desconectar();
            }
            
            JasperReport report = JasperCompileManager.compileReport(ruta_reporte);
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), c.getConnection());
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setVisible(true);
            jviewer.setExtendedState(MAXIMIZED_BOTH);
            
        } catch (JRException jRException) {
            System.err.println(jRException.getMessage());
            JOptionPane.showMessageDialog(null, "Error al internar abrir el reporte");
        }
    }
    
    //Reporte Medicamentos
    public void abrirReporteListadoMedicamentos() throws SQLException {
        try {
            c = new bdConexion();            
            String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Listar_Todos_Medicamentos.jrxml";
            System.out.println("Cadena de conexion: "+c.getConnection());
            System.out.println("Ruta del reporte: "+ruta_reporte);
            
            if (ruta_reporte.equals("")) {
                JOptionPane.showMessageDialog(null,"No se encontró el archivo del reporte solicitado");
                System.exit(2);
                c.desconectar();
            }
            
            JasperReport report = JasperCompileManager.compileReport(ruta_reporte);
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), c.getConnection());
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setVisible(true);
            jviewer.setExtendedState(MAXIMIZED_BOTH);
            
        } catch (JRException jRException) {
            System.err.println(jRException.getMessage());
            JOptionPane.showMessageDialog(null, "Error al internar abrir el reporte");
        }
    }     
    
}
