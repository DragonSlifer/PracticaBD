/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd.controller;

import es.uv.bd.view.AnimaliaView;
import es.uv.bd.view.FrmAnimalia;
import es.uv.bd.view.MascotaView;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author 
 */
public class AnimaliaController {
    private Component c;
    
    public AnimaliaController(AnimaliaView view) {
        view.setMenuActionListener(new MenuActionListener());
    }
    
    class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
       
            String key   = event.getActionCommand();
            switch (key) {
                case "menuExportar":
                    System.out.println("MenuActionListener: Accion '" + key + "' no implementada.");
                    break;
                case "menuExit":
                    System.exit(0);
                    break;
                case "menuPadecimientos":
                    Frame mascotaView = new MascotaView();
                    mascotaView.setVisible(true);
                    break;
                case "menuExplorar":
                    Frame frmAnimalia = new FrmAnimalia();
                    frmAnimalia.setVisible(true);
                    break;
                default:
                    System.out.println("MenuActionListener: Acción '" + key + "' desconocida.");
                    break;
            }
        }
    }
}
