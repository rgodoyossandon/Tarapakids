package clases;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class reportePacientes {
    
    private bdConexion c=null;
    

    public reportePacientes() {       

    }
    
    //Reporte sin paremetros
    public void abrirReporteListadoPacientes() throws SQLException {
        try {
            c = new bdConexion();            
            String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Listado_Pacientes.jrxml";
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
            
            
        } catch (JRException e) {
           System.err.println("Ocurrió este error: "+e.getMessage());
           JOptionPane.showMessageDialog(null, "Ocurrió este error: "+e.getMessage());
        }
    }
    
    public void abrirReporteExpedienteClinicoPaciente(String IdPac) throws SQLException {
        c = new bdConexion(); 
        
        String sql = "SELECT * FROM expedientepaciente WHERE IdPaciente = "+IdPac+"";        
        try {
            PreparedStatement ps = c.getConnection().prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();

            resultado.last();
            if (resultado.getRow() <= 0) {
                JOptionPane.showMessageDialog(null, "Aún No Existe Expediente Clínico para este Paciente");
            } else {
                //Llamamos el Reporte y lo mostramos
                JasperReport report;
                String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Expediente_Clinico_Paciente.jrxml";
                try {
                    //pametro/s que necesite el reporte
                    Map parametros = new HashMap();
                    parametros.put("IdPaciente", IdPac);

                    report = JasperCompileManager.compileReport(ruta_reporte);
                    System.out.println("Ruta: " + report);
                    System.out.println("Parametro: " + parametros);

                    JasperPrint print = JasperFillManager.fillReport(report, parametros, c.getConnection());
                    JasperViewer jviewer = new JasperViewer(print, false);
                    jviewer.setVisible(true);
                    jviewer.setExtendedState(MAXIMIZED_BOTH);

                } catch (JRException e) {
                    System.err.println("Ocurrió este error: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "Ocurrió este error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage());
            c.desconectar();
        }
       
        
        
    }
    
}
