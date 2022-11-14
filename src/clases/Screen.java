/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import formularios.frmBienvenida;


public class Screen extends Thread {
 frmBienvenida ref;

    public Screen(frmBienvenida ref) {
        this.ref = ref;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(150);
                ref.Llena_Barra();

            } catch (InterruptedException ex) {

            }
        }
    }
}