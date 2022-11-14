package clases;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class decimal extends InputVerifier{
    public boolean verify(JComponent editor) {
        if (editor instanceof JTextField)
        {
            String clave = ((JTextField)editor).getText();
            try
            {
                Double.parseDouble(clave);
                return true;
 
            }
 
            catch (Exception e)
 
            {
                ((JTextField)editor).setText("");
                return false;
            }
        }
        return true;
    }
}
