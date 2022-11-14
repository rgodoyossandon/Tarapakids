package clases;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class reporteRecetasMedicas {
    private bdConexion c=null;
        
    public String fecha;
    
    public reporteRecetasMedicas()
    {       
    }
    
    
    public void abrirReporteRecetaMedicaPorPaciente(String IdPac) {
        c = new bdConexion(); 
        JasperReport report;
        String ruta_reporte = System.getProperty("user.dir") + "/src/reportes/reporte_Receta_Por_Paciente.jrxml";
        try {                      
            //pametro/s que necesite el reporte
            Map parametros = new HashMap();
            parametros.put("IdPaciente", IdPac);
            
            report = JasperCompileManager.compileReport(ruta_reporte);
            System.out.println("Ruta: "+report);
            System.out.println("Parametro: "+parametros);
            
            JasperPrint print = JasperFillManager.fillReport(report, parametros, c.getConnection());            
            JasperViewer jviewer = new JasperViewer(print,false);
            jviewer.setVisible(true);
            jviewer.setExtendedState(MAXIMIZED_BOTH);
            
        } catch (JRException e) {
            System.err.println("Ocurrió este error: "+e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió este error: "+e.getMessage());
        }
    }         
    
}
