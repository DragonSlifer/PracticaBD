package es.uv.bd.test;

import es.uv.bd.model.Padecen;
import es.uv.bd.model.PadecenDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Martínez
 * @author Lidia Montero
 */
public class PadecenDaoTest {
    private static String modulo = "PadecenDaoTest::";
    private PadecenDAO padecenDAO;
    private Padecen p1, p2;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PadecenDaoTest mascotaDaoTest = new PadecenDaoTest();
        mascotaDaoTest.doTest();
    }

    public PadecenDaoTest() {
        padecenDAO = new PadecenDAO();
        
    }
    
    public void doTest() {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
        try {
            print("Test 1: Crear patologia");            
            ///< int id_campista, int id_patologia, boolean activa, String gravedad, String ind_especiales
            p1 = new Padecen(1,2,true,"ninguna",null);
            padecenDAO.crearPadecen(p1);
            print("Test 1: " + p1 + " creada.");
            
            print("Test 2: Buscar padecen");
            p2 = padecenDAO.leerPadecen(p1.getId_campista(),p1.getId_patologia());
            print("Test 2: " + p2 + " leída.");
            
            print("Test 3: Cambiar gravedad");
            p2.setGravedad("altísima");
            padecenDAO.actualizarPadecen(p2);
            p1 = padecenDAO.leerPadecen(p2.getId_campista(), p2.getId_patologia());
            print("Test 3: " + p1 + " modificada.");
            
            print("Test 4: Borrar padecen " + p1.getId_campista() + " (" + p1.getId_patologia()+ ")");
            padecenDAO.borrarPadecen(p1.getId_campista(),p1.getId_patologia());
            print("Test 4: Padecen borrada");
            
            print("Todos los test correctos");
            
        } catch (ParseException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            print("Error ejecutando tests *************************");
            print(" Excepción: " + ex.getMessage());
            print("");
            ex.printStackTrace();
        }
    }
    
    private void print(String string) {
        System.out.println(modulo + string);
    }
}
