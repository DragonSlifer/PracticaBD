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
public class MascotaCrear extends JFrame {
    
    private JTextField idMascota = new JTextField();
    //private JTextField idCliente = new JTextField();
    private JComboBox idCliente = new JComboBox();
    private JTextField nombreMascota = new JTextField();
    private JTextField tipoAnimal = new JTextField();
    private JComboBox dia = new JComboBox();
    private JComboBox mes = new JComboBox();
    private JComboBox anyo = new JComboBox();
    
    private JTable mascotaTable;
    private HashMap<String, Integer> listaClientes = new HashMap();
    private Object [] claves;
        
    public MascotaCrear(JTable mascotaTable) {
            
        super("Nueva Mascota");
        
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
        JLabel l = new JLabel("Dar de alta mascota");
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
        
            listaClientes = clienteDao.getTListaClientes();
            claves = listaClientes.keySet().toArray();
        
            form.setLayout(new GridLayout(5,2));
            
            
            idMascota.setFont(textfFont);
            idMascota.setColumns(4);
            idCliente.setFont(textfFont);
            //idCliente.setColumns(4);
            nombreMascota.setFont(textfFont);
            nombreMascota.setColumns(30);
            tipoAnimal.setFont(textfFont);
            tipoAnimal.setColumns(4);
            
            JLabel idMascotaLabel = new JLabel("Id de la mascota:");
            idMascotaLabel.setFont(labelFont);
            idMascotaLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(idMascotaLabel);
            form.add(idMascota);
            
            JLabel idClienteLabel = new JLabel("Id del dueño:");
            idClienteLabel.setFont(labelFont);
            idClienteLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(idClienteLabel);
            
            idCliente.setModel(new DefaultComboBoxModel(claves));
            //idCliente.setColumns(4);
            form.add(idCliente);

            JLabel nombreMascotaLabel = new JLabel("Nombre de la mascota:");
            nombreMascotaLabel.setFont(labelFont);
            nombreMascotaLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(nombreMascotaLabel);
            form.add(nombreMascota);

            JLabel tipoAnimalLabel = new JLabel("Tipo de animal:");
            tipoAnimalLabel.setFont(labelFont);
            tipoAnimalLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(tipoAnimalLabel);
            form.add(tipoAnimal);

            JLabel fechaNacimientoLabel = new JLabel("Fecha de nacimiento:");
            fechaNacimientoLabel.setFont(labelFont);
            fechaNacimientoLabel.setHorizontalAlignment(JLabel.RIGHT);
            form.add(fechaNacimientoLabel);
            
            
            JPanel fechaPanel = new JPanel();
            fechaPanel.setLayout(new FlowLayout());
            
            dia.setModel(new DefaultComboBoxModel(new String[] {"01","02","03","04","05","06","07","08","09","10",
                    "11","12","13","14","15","16","17","18","19","20",
                    "21","22","23","24","25","26","27","28","29","30","31"}));
            mes.setModel(new DefaultComboBoxModel(new String[] {"01","02","03","04","05","06","07","08","09","10",
                "11","12"}));
            anyo.setModel(new DefaultComboBoxModel(new String[] {"1990","1991","1992","1993","1994","1995","1996",
                "1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010",
                    "2011","2012"}));
            
            fechaPanel.add(dia);
            fechaPanel.add(new JLabel("-"));
            fechaPanel.add(mes);
            fechaPanel.add(new JLabel("-"));
            fechaPanel.add(anyo);
            
            form.add(fechaPanel);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error creando mascota: " + e.getMessage(),
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
                        mascota.setIdMascota(Integer.parseInt(idMascota.getText()));
                        String seleccion = (String)claves[idCliente.getSelectedIndex()];
                        int idcli = (int)listaClientes.get(seleccion);
                        mascota.setIdCliente(idcli);
                        mascota.setNombreMascota(nombreMascota.getText());
                        mascota.setTipoAnimal(Integer.parseInt(tipoAnimal.getText()));
                        String f = (String)dia.getSelectedItem() + "-" + 
                            (String)mes.getSelectedItem() + "-" +
                            (String)anyo.getSelectedItem();
                        mascota.setFechaNacimiento(sdf.parse(f));

                        PadecenDAO mascotaDao = new PadecenDAO();
                        mascotaDao.crearMascota(mascota);
                        /*
                         * Actualizamos el modelo
                         */
                        mascotaTable.setModel(mascotaDao.getTablaMascotas());
                        mascotaTable.updateUI();
                         /*
                         * Cerramos la ventana
                         */
                        dispose();
                        break;
                    default:
                        System.out.println("MascotaCrear: Accion '" + key + "' no reconocida.");
                        break;
                }
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | ParseException e) {
                JOptionPane.showMessageDialog(
                null,
                "Error creando mascota: " + e.getMessage(),
                "Atención",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
