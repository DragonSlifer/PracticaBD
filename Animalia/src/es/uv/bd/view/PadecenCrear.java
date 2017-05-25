package es.uv.bd.view;

import es.uv.bd.model.PatologiasDAO;
import es.uv.bd.model.Padecen;
import es.uv.bd.model.PadecenDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author Jorge Martínez
 * @author Lidia Montero
 */
public class PadecenCrear extends JFrame {
    
    private JTextField idCampista = new JTextField();
    private JComboBox idPatologia = new JComboBox();
    private JComboBox gravedad = new JComboBox();
    private JComboBox activa = new JComboBox();
    private JTextField ind_especiales = new JTextField();
    
    private JTable mascotaTable;
    private HashMap<String, Integer> listaPatologias = new HashMap();
    private Object [] claves;
        
    public PadecenCrear(JTable mascotaTable) {
            
        super("Nuevo Padecen");
        
        this.mascotaTable = mascotaTable;
        
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
    }
   
    private JPanel createCabecera() {
        JPanel c = new JPanel();
        JLabel l = new JLabel("Dar de alta padecen");
        l.setForeground(Color.BLUE);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,22));
        c.add(l);
        
        return c;
    }
    
    private JPanel createForm() {
        
        PatologiasDAO clienteDao = new PatologiasDAO();
            
        JPanel form = new JPanel();
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font textfFont = new Font("Arial", Font.PLAIN, 12);
        
        try {  
        
            listaPatologias = clienteDao.getTListaPatologias();
            claves = listaPatologias.keySet().toArray();
        
            form.setLayout(new GridLayout(5,2));
            
            
            idCampista.setFont(textfFont);
            idCampista.setColumns(4);
            idPatologia.setFont(textfFont);
            gravedad.setFont(textfFont);
            activa.setFont(textfFont);
            
            JLabel idCampistaLabel = new JLabel("Id del campista:");
            idCampistaLabel.setFont(labelFont);
            idCampistaLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(idCampistaLabel);
            form.add(idCampista);
            
            JLabel idPatologiaLabel = new JLabel("Id de la patologia:");
            idPatologiaLabel.setFont(labelFont);
            idPatologiaLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(idPatologiaLabel);
            
            idPatologia.setModel(new DefaultComboBoxModel(claves));
            
            form.add(idPatologia);

            JLabel nombreMascotaLabel = new JLabel("Gravedad:");
            nombreMascotaLabel.setFont(labelFont);
            nombreMascotaLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(nombreMascotaLabel);
            form.add(gravedad);

            JLabel tipoAnimalLabel = new JLabel("Activa:");
            tipoAnimalLabel.setFont(labelFont);
            tipoAnimalLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(tipoAnimalLabel);
            form.add(activa);
            
            JPanel fechaPanel = new JPanel();
            fechaPanel.setLayout(new FlowLayout());
            
            form.add(fechaPanel);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error creando padecen: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
        }
        finally {
            return form;
        }
    }
        
    private JPanel createButton() {
        
        JPanel botonPanel = new JPanel();
        
        JButton boton = new JButton("Añadir");
        boton.setActionCommand("botonPadecen");
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
                    case "botonPadecen":
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Padecen mascota = new Padecen();
                        mascota.setId_campista(Integer.parseInt(idCampista.getText()));
                        String seleccion = (String)claves[idPatologia.getSelectedIndex()];
                        int idcli = (int)listaPatologias.get(seleccion);
                        mascota.setId_patologia(idcli);
                        mascota.setGravedad((String)gravedad.getSelectedItem());
                        if("activa".equals((String) activa.getSelectedItem())){
                            mascota.setActiva(true);
                        } else {
                            mascota.setActiva(false);
                        }
                        
                        PadecenDAO mascotaDao = new PadecenDAO();
                        mascotaDao.crearPadecen(mascota);
                        /*
                         * Actualizamos el modelo
                         */
                        mascotaTable.setModel(mascotaDao.getTablaPadecen());
                        mascotaTable.updateUI();
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
                "Error creando padecen: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
