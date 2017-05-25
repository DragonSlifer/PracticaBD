package es.uv.bd.view;

import es.uv.bd.model.PadecenDAO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diaz
 */
public class MascotaBotones extends JPanel {
    private static final JButton[] button = new JButton[3];
    private static final String[] buttonName = { "Nueva Mascota", "Editar Mascota", "Borrar Mascota" };
    private static final String[] buttonAction = { "insertar", "editar", "borrar" };
    private final JTable mascotaTable;

    public MascotaBotones(JTable mascotaTable) {
    
        this.mascotaTable = mascotaTable;
        MascotaButtonListener mascotaButtonListener = new MascotaButtonListener();
        setLayout(new GridLayout(1,3));
        
        for (int i = 0; i < button.length; i++) {
            button[i] = new JButton(buttonName[i]);
            button[i].setActionCommand(buttonAction[i]);
            button[i].addActionListener(mascotaButtonListener);
            this.add(button[i]);
        }
    }
     
    private class MascotaButtonListener implements ActionListener {
            
        @Override
        public void actionPerformed(ActionEvent event) {
            
            int row, idMascota;
            PadecenDAO mascotaDao = new PadecenDAO();
            
            String key   = event.getActionCommand();
            switch (key) {
                case "insertar":
                    MascotaCrear mascotaCrear = new MascotaCrear(mascotaTable);
                    break;
                case "editar":
                    row = mascotaTable.getSelectedRow();
                    if (row == -1) {
                        showSelectionMessage();
                    }
                    else {
                        idMascota = (int)mascotaTable.getModel().getValueAt(row, 0);
                        MascotaEditar mascotaEditar = new MascotaEditar(idMascota, mascotaTable);
                    }
                    break;
                case "borrar":
                    // Fila seleccionada
                    row = mascotaTable.getSelectedRow();
                    if (row == -1) {
                        showSelectionMessage();
                    }
                    else {
                        idMascota = (int)mascotaTable.getModel().getValueAt(row, 0);
                        MascotaBorrar mascotaBorrar = new MascotaBorrar(idMascota, mascotaTable);
                    }
                    break;
                default:
                    System.out.println("MascotaButtonListener: Accion '" + key + "' no reconocida.");
                    break;
            }
        }
        
        private void showSelectionMessage() {
            JOptionPane.showMessageDialog(
                    null,
                    "Por favor, selecciona una fila de la tabla",
                    "Sin selección",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        
        private void mascotaBorrar(int row) {                        
            PadecenDAO mascotaDao = new PadecenDAO();
        
            // No hay ninguna fila seleccionada
            if (row == -1) {
                JOptionPane.showMessageDialog(
                    null,
                    "Por favor, selecciona una fila de la tabla",
                    "Sin selección",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                // Clave primaria de la fila seleccionada
                int idMascota = (int)mascotaTable.getModel().getValueAt(row, 0);
                String nombreMascota = (String)mascotaTable.getModel().getValueAt(row, 1);
            
                // Dialogo de confirmación
                int reply = JOptionPane.showConfirmDialog(
                    null,
                    "¿Borrar la mascota '" + nombreMascota + "' (idMascota = " + idMascota + ")?",
                    "Borrar Mascota",
                    JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        // Borramos la mascota de la base de datos
                        mascotaDao.borrarMascota(idMascota);
                        // y actualizamos la tabla
                        DefaultTableModel mascotaModel = (DefaultTableModel)mascotaTable.getModel();
                        mascotaModel.removeRow(row);
                        mascotaTable.updateUI();
                    }
                    catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Error borrando mascota: " + e.getMessage(),
                            "Atención",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
