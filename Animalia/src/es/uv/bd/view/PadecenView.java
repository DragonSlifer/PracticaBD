/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uv.bd.view;

import es.uv.bd.model.PadecenDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.JTableHeader;

    
/**
 *
 * @author Jorge Martínez
 * @author Lidia Montero
 */
public class PadecenView extends JFrame {

    private JTable padecenTable;
    private JPanel padecenBotones;
    
    private PadecenDAO padecenDao = new PadecenDAO();
    
    public PadecenView() {
        super("Gestión de Padecimientos");
        
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        
        /* Cabecera */
        JPanel cabecera = createCabecera();
  
        /* Tabla */
        padecenTable = createTablePanel();
        padecenTable.setModel(padecenDao.getTablaPadecen());
        // Ponemos la tabla dentro de un JScrollPane
        JScrollPane jsp = new JScrollPane(padecenTable);

        /* Botonera */
        padecenBotones = new PadecenBotones(padecenTable);
        
        /* Añadimos todos los paneles al Container */
        cp.add(cabecera, BorderLayout.NORTH);
        cp.add(jsp, BorderLayout.CENTER);
        cp.add(padecenBotones, BorderLayout.SOUTH);
        
        setSize(800,400);
        setVisible(true);
    }
   
    private JPanel createCabecera() {
        JPanel c = new JPanel();
        JLabel l = new JLabel("Lista de Padecimientos");
        l.setForeground(Color.RED);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,24));
        c.add(l);
        
        return c;
    }
 
    private JTable createTablePanel() {
        
        JTable tp = new JTable();
        /*
         * Cabecera de la tabla
         */
        JTableHeader th = tp.getTableHeader();
        th.setFont(new Font("Arial",Font.BOLD | Font.ITALIC,14));
        th.setBackground(Color.DARK_GRAY);
        th.setForeground(Color.WHITE);
        
        /*
         * Caracteristicas de la tabla
         */
        tp.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tp.setFont(new Font("Arial",Font.BOLD,12));
        tp.setRowHeight(22);
        
        return tp;
    }
}
