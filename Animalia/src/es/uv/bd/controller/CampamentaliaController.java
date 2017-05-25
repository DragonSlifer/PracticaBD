/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd.controller;

import es.uv.bd.view.CampamentaliaView;
import es.uv.bd.view.FrmCampamentalia;
import es.uv.bd.view.PadecenView;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author 
 */
public class CampamentaliaController {
    private Component c;
    
    public CampamentaliaController(CampamentaliaView view) {
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
                    Frame mascotaView = new PadecenView();
                    mascotaView.setVisible(true);
                    break;
                case "menuExplorar":
                    Frame frmAnimalia = new FrmCampamentalia();
                    frmAnimalia.setVisible(true);
                    break;
                default:
                    System.out.println("MenuActionListener: Acción '" + key + "' desconocida.");
                    break;
            }
        }
    }
}
