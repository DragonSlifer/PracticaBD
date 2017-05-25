package es.uv.bd.view;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author diaz
 */
public class AnimaliaMenu extends MenuBar {
    private Menu file, tablas, explorer;

    private MenuItem [] fileItem = new MenuItem[2];
    private String [] fileItemName = { "Exportar XML...", "Salir" };
    private String [] fileItemAction = { "menuExportar", "menuExit" };
    
    private MenuItem [] tablaItem = new MenuItem[1];
    private String [] tablaItemName = { "Mascotas" };
    private String [] tablaItemAction = { "menuMascotas" };
    
    private MenuItem [] explorerItem = new MenuItem[1];
    private String [] explorerItemName = { "Explorador de tablas" };
    private String [] explorerItemAction = { "menuExplorar" };
    

    
    public AnimaliaMenu() throws HeadlessException {
        Font menuFont = new Font("Courier", Font.BOLD, 12);
        
        /******************************************
         * Menu Fichero
         */
        file = new Menu("Fichero");
        file.setFont(menuFont);
        
        // Para cada una de las entradas del menu file...
        for (int i = 0; i < fileItem.length; i++) {
            fileItem[i] = new MenuItem(fileItemName[i]);
            fileItem[i].setActionCommand(fileItemAction[i]);
            file.add(fileItem[i]);
        }
        
        // Anadimos el menu a la barra de menu
        add(file);
        
       /*******************************************
        * Menu Consultar
        */
        tablas = new Menu("Tablas");
        tablas.setFont(menuFont);
        
        // Para cada una de las entradas del menu file...
        for (int i = 0; i < tablaItem.length; i++) {
            tablaItem[i] = new MenuItem(tablaItemName[i]);
            tablaItem[i].setActionCommand(tablaItemAction[i]);
            tablas.add(tablaItem[i]);
        }
        
        // Anadimos el menu a la barra de menu
        add(tablas);
        
        /*******************************************
        * Menu Explorar
        */
        explorer = new Menu("Explorar");
        explorer.setFont(menuFont);
        
        // Para cada una de las entradas del menu file...
        for (int i = 0; i < explorerItem.length; i++) {
            explorerItem[i] = new MenuItem(explorerItemName[i]);
            explorerItem[i].setActionCommand(explorerItemAction[i]);
            explorer.add(explorerItem[i]);
        }
        
        // Anadimos el menu a la barra de menu
        add(explorer);
    }

    public void setMenuActionListener(ActionListener menuActionListener) {
        for (MenuItem mi : tablaItem) {
            mi.addActionListener(menuActionListener);
        }
        for (MenuItem mi : fileItem) {
            mi.addActionListener(menuActionListener);
        }
        for (MenuItem mi : explorerItem) {
            mi.addActionListener(menuActionListener);
        }
    }

}
