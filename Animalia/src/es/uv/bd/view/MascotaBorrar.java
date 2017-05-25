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
 * @author diaz
 */
public class MascotaBorrar {
    
    public MascotaBorrar(int idMascota, JTable mascotaTable) {
    
        PadecenDAO mascotaDao = new PadecenDAO();
        Padecen mascota;

        try {
            // Recuperamos la mascota a través de la clave primaria
            mascota = mascotaDao.leerMascota(idMascota);
        
            // Dialogo de confirmación
            int reply = JOptionPane.showConfirmDialog(
                null,
                "¿Borrar la mascota '" + mascota.getNombreMascota() + "' (idMascota = " + idMascota + ")?",
                "Borrar Mascota",
                JOptionPane.YES_NO_OPTION);

            if (reply == JOptionPane.YES_OPTION) {
                    // Borramos la mascota de la base de datos
                    mascotaDao.borrarMascota(idMascota);
                    /*
                    * Actualizamos el modelo
                    */
                    mascotaTable.setModel(mascotaDao.getTablaMascotas());
                    mascotaTable.updateUI();
                }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | ParseException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error borrando mascota: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
