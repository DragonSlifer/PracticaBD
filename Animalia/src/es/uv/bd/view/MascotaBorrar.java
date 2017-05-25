/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd.view;

import es.uv.bd.model.Padecen;
import es.uv.bd.model.PadecenDAO;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Jorge Martínez
 * @author Lidia Montero
 */
public class MascotaBorrar {
    
    public MascotaBorrar(int idcampista,int idenfermedad, JTable padecenTable) {
    
        PadecenDAO padecenDao = new PadecenDAO();
        Padecen padecen;

        try {
            // Recuperamos la mascota a través de la clave primaria
            padecen = padecenDao.leerPadecen(idcampista,idenfermedad);
        
            // Dialogo de confirmación
            int reply = JOptionPane.showConfirmDialog(
                null,
                "¿Borrar la fila '" + padecen.toString() + "' (idcampista = " + idcampista + ", idenfermedad " + idenfermedad +")?",
                "Borrar Padecen",
                JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.YES_OPTION) {
                    // Borramos la mascota de la base de datos
                    padecenDao.borrarPadecen(idcampista,idenfermedad);
                    /*
                    * Actualizamos el modelo
                    */
                    padecenTable.setModel(padecenDao.getTablaPadecen());
                    padecenTable.updateUI();
                }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | ParseException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error borrando padecen: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
