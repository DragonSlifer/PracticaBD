/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd;

import es.uv.bd.controller.CampamentaliaController;
import es.uv.bd.view.CampamentaliaView;

/**
 *
 * @author 
 */
public class Campamentalia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //VinotecaModel model = new VinotecaModel();
        CampamentaliaView view = new es.uv.bd.view.CampamentaliaView();
        CampamentaliaController controller = new CampamentaliaController(view);

        view.setVisible(true);// TODO code application logic here
    }
}
