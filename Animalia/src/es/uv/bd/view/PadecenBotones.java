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
 * @author Jorge Martínez
 * @author Lidia Montero
 */
public class PadecenBotones extends JPanel {
    private static final JButton[] button = new JButton[3];
    private static final String[] buttonName = { "Nuevo Padecimiento", "Editar Padecimiento", "Borrar Padecimiento" };
    private static final String[] buttonAction = { "insertar", "editar", "borrar" };
    private final JTable padecenTable;

    public PadecenBotones(JTable mascotaTable) {
    
        this.padecenTable = mascotaTable;
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
            
            int row, idCampista, idPatologia;
            PadecenDAO mascotaDao = new PadecenDAO();
            
            String key   = event.getActionCommand();
            switch (key) {
                case "insertar":
                    PadecenCrear mascotaCrear = new PadecenCrear(padecenTable);
                    break;
                case "editar":
                    row = padecenTable.getSelectedRow();
                    if (row == -1) {
                        showSelectionMessage();
                    }
                    else {
                        idCampista = (int)padecenTable.getModel().getValueAt(row, 0);
                        idPatologia = (int)padecenTable.getModel().getValueAt(row, 1);
                        PadecenEditar mascotaEditar = new PadecenEditar(idCampista, idPatologia, padecenTable);
                    }
                    break;
                case "borrar":
                    // Fila seleccionada
                    row = padecenTable.getSelectedRow();
                    if (row == -1) {
                        showSelectionMessage();
                    }
                    else {
                        idCampista = (int)padecenTable.getModel().getValueAt(row, 0);
                        idPatologia = (int)padecenTable.getModel().getValueAt(row, 1);
                        PadecenBorrar mascotaBorrar = new PadecenBorrar(idCampista, idPatologia, padecenTable);
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
                int idCampista = (int)padecenTable.getModel().getValueAt(row, 0);
                int idPatologia = (int)padecenTable.getModel().getValueAt(row, 1);
            
                // Dialogo de confirmación
                int reply = JOptionPane.showConfirmDialog(null,
                    "¿Borrar la relación (idPatologia = '" + idPatologia + "' idCampista = " + idCampista + ")?",
                    "Borrar Mascota",
                    JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        // Borramos la mascota de la base de datos
                        mascotaDao.borrarPadecen(idCampista,idPatologia);
                        // y actualizamos la tabla
                        DefaultTableModel mascotaModel = (DefaultTableModel)padecenTable.getModel();
                        mascotaModel.removeRow(row);
                        padecenTable.updateUI();
                    }
                    catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Error borrando padecen: " + e.getMessage(),
                            "Atención",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
