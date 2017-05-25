/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd;

import es.uv.bd.controller.AnimaliaController;
import es.uv.bd.view.AnimaliaView;

/**
 *
 * @author 
 */
public class Animalia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //VinotecaModel model = new VinotecaModel();
        AnimaliaView view = new es.uv.bd.view.AnimaliaView();
        AnimaliaController controller = new AnimaliaController(view);

        view.setVisible(true);// TODO code application logic here
    }
}
