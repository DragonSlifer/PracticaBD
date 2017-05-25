/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd.view;

import es.uv.bd.model.Padecen;
import es.uv.bd.model.PadecenDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Jorge Martínez
 * @author Lidia Montero
 */
public class MascotaEditar extends JFrame {

    /**
     * Revisar los campos
     */
    private JTextField idCampista = new JTextField();
    private JTextField idEnfermedad = new JTextField();
    private JTextField nombrePatologia = new JTextField();
    private JComboBox activa = new JComboBox();
   
    
    private JTable padecenTable;
    private PadecenDAO padecenDao = new PadecenDAO();
    private Padecen padecen;

    public MascotaEditar(int idcampista, int idenfermedad, JTable padecenTable) {
        
        super("Editar Padecen");
        this.padecenTable = padecenTable;
        
        try {
            /*
             * Obtenemos el objeto a editar
             */
            padecen = padecenDao.leerPadecen(idcampista, idenfermedad);
            
            Container cp = this.getContentPane();
            cp.setLayout(new BorderLayout());
            
            /* Cabecera */
            JPanel cabecera = createCabecera();
      
            /* Formulario */
            JPanel form = createForm();
            
            /* Botón */
            JPanel boton = createButton();

            /* Añadimos todos los paneles al Container */
            cp.add(cabecera, BorderLayout.NORTH);
            cp.add(form, BorderLayout.CENTER);
            cp.add(boton, BorderLayout.SOUTH);
            
            setSize(600,400);
            pack();
            setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | ParseException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error modificiando padecen: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private JPanel createCabecera() {
        JPanel c = new JPanel();
        JLabel l = new JLabel("Editar padecen");
        l.setForeground(Color.BLUE);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,22));
        c.add(l);
        
        return c;
    }
    
    private JPanel createForm() {
        JPanel form = new JPanel();
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font textfFont = new Font("Arial", Font.PLAIN, 12);
        SimpleDateFormat formDia = new SimpleDateFormat("dd");
        SimpleDateFormat formMes = new SimpleDateFormat("MM");
        SimpleDateFormat formAny = new SimpleDateFormat("yyyy");
        
        form.setLayout(new GridLayout(5,2));
        
        
        idCampista.setFont(textfFont);
        idCampista.setColumns(4);
        idCampista.setText((padecen.getId_campista()+""));
        idCampista.setEditable(false);
        idCampista.setEnabled(true);
        
        idEnfermedad.setFont(textfFont);
        idEnfermedad.setColumns(4);
        idEnfermedad.setText((padecen.getId_patologia()+""));
        idEnfermedad.setEditable(false);
        idEnfermedad.setEnabled(true);
        
        nombrePatologia.setFont(textfFont);
        nombrePatologia.setColumns(30);
        nombrePatologia.setText((padecen.getNombrePatologia()));
        
        JLabel idMascotaLabel = new JLabel("Id del campista:");
        idMascotaLabel.setFont(labelFont);
        idMascotaLabel.setHorizontalAlignment(JLabel.RIGHT);
        form.add(idMascotaLabel);
        form.add(idCampista);
        
        JLabel idClienteLabel = new JLabel("Id de la patologia:");
        idClienteLabel.setFont(labelFont);
        idClienteLabel.setHorizontalAlignment(JLabel.RIGHT);
        form.add(idClienteLabel);
        idEnfermedad.setColumns(4);
        form.add(idEnfermedad);

        JLabel nombreMascotaLabel = new JLabel("Nombre de la patologia:");
        nombreMascotaLabel.setFont(labelFont);
        nombreMascotaLabel.setHorizontalAlignment(JLabel.RIGHT);
        form.add(nombreMascotaLabel);
        form.add(nombrePatologia);

        JPanel fechaPanel = new JPanel();
        fechaPanel.setLayout(new FlowLayout());
        
        activa.setModel(new DefaultComboBoxModel(new String[] {"activa", "inactiva"}));
        String actividad;
        if(padecen.isActiva()){
            actividad = "activa";
        } else {
            actividad = "false";
        }
        activa.setSelectedItem(formDia.format(actividad));
             
        fechaPanel.add(activa);
        fechaPanel.add(new JLabel("-"));
        
        form.add(fechaPanel);

        return form;
    }
        
    private JPanel createButton() {
        
        JPanel botonPanel = new JPanel();
        
        JButton boton = new JButton("Modificar");
        boton.setActionCommand("botonMascota");
        boton.addActionListener(new ButtonListener());
        botonPanel.add(boton);
        return botonPanel;
    }
    
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                String key = event.getActionCommand();
                switch (key) {
                    case "botonMascota":
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Padecen mascota = new Padecen();
                        mascota.setId_campista(Integer.parseInt(idCampista.getText()));
                        mascota.setId_patologia(Integer.parseInt(idEnfermedad.getText()));
                        mascota.setNombrePatologia(nombrePatologia.getText());
                        if("activa".equals((String) activa.getSelectedItem())){
                            mascota.setActiva(true);
                        } else {
                            mascota.setActiva(false);
                        }
                        
                        PadecenDAO mascotaDao = new PadecenDAO();
                        mascotaDao.actualizarPadecen(mascota);
                        /*
                         * Actualizamos el modelo
                         */
                        padecenTable.setModel(mascotaDao.getTablaPadecen());
                        padecenTable.updateUI();
                        /*
                         * Cerramos la ventana
                         */
                        dispose();
                        break;
                    default:
                        System.out.println("PadecenCrear: Accion '" + key + "' no reconocida.");
                        break;
                }
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
                JOptionPane.showMessageDialog(
                null,
                "Error modificiando mascota: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
