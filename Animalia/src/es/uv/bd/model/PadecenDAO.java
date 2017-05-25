package es.uv.bd.model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lidia Montero Egidos y Jorge Martinez Hernandez
 */
public class PadecenDAO {
    /*
     * Parámetros de conexión a la base de datos
     */
    public static final String DRIVER = "oracle.jdbc.OracleDriver";
    public static final String DBURL = "jdbc:oracle:thin:@pokemon2.uv.es:1521:ORCL";
    public static final String USERNAME = "AL028";
    public static final String PASSWORD = "OneManArmy";
    /*
     * Consultas PADECEN
     *
     * id_campista		NUMBER
     * id_patología		NUMBER
     * activa			VARCHAR
     * gravedad			VARCHAR
     * ind_especiales	VARCHAR
     */

    private static final String CREATE = 
            "INSERT INTO padecen (id_campista,id_patologia,activa,gravedad,ind_especiales) " +
            "VALUES (?,?,?,?,?)";
    
    private static final String READ = 
            "SELECT * " +
            " FROM padecen " +
            " WHERE id_campista = ?, id_enfermedad = ?";
    
    private static final String READALL = 
            "SELECT pad.id_campista, pad.id_patologia, pat.nombre, pat.descripción, pat.indicaciones, pat.tratamiento,"
            + " pad.activa, pad.gravedad, pad.ind_especiales " +
            " FROM padecen pad, patologias pat " +
            " WHERE  pad.id_patologia = pat.id_patologia " +
            " ORDER BY 1";
    
    private static final String UPDATE =
            "UPDATE padecen " +
            " SET  activa = ?, gravedad = ?, ind_especiales = ? " +
            " WHERE id_campista = ?, id_patologia = ?";
    
    private static final String DELETE =
            "DELETE FROM padecen " +
            " WHERE id_campista = ? AND id_enfermedad = ?";

    public PadecenDAO() {}
    
    public void crearPadecen(Padecen padecen) throws 
            ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        /*
        * Conexion a la base de datos
        */
        Class.forName(DRIVER).newInstance();
        Connection oracleConn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
           
        oracleConn.setAutoCommit(false);
        // Sentencia de insert
        PreparedStatement insert = oracleConn.prepareStatement(CREATE);
        ///< Rellenar
        insert.executeUpdate();
        
        oracleConn.commit();
        oracleConn.setAutoCommit(true);
        oracleConn.close();
    }
    
    public Padecen leerPadecen(int id_campista, int id_enfermedad) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ParseException {
        
        Padecen padecen;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
      
        Class.forName(DRIVER).newInstance();
        Connection oracleConn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
           
        // Sentencia de insert
        PreparedStatement read = oracleConn.prepareStatement(READ);
        read.setInt(1, id_campista);
        read.setInt(2, id_enfermedad);
        ResultSet rs = read.executeQuery();
        
        padecen = new Padecen(rs.getInt("id_campista"),rs.getInt("id_patologia"),rs.getBoolean("activa"),rs.getString("gravedad"),rs.getString("ind_especiales"));
        
        return padecen;
    }
    
    public void actualizarPadecen(Padecen padecen) throws ClassNotFoundException, 
           InstantiationException, IllegalAccessException, SQLException {
           
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        /*
        * Conexion a la base de datos
        */
        Class.forName(DRIVER).newInstance();
        Connection oracleConn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
           
        oracleConn.setAutoCommit(false);
        // Sentencia de insert
        PreparedStatement update = oracleConn.prepareStatement(UPDATE);
        
       
        update.executeUpdate();
        
        oracleConn.commit();
        oracleConn.setAutoCommit(true);
        oracleConn.close();
    }
    
    public void borrarPadecen(int id_campista, int id_enfermedad) throws ClassNotFoundException, 
           InstantiationException, IllegalAccessException, SQLException {
        /*
        * Conexion a la base de datos
        */
        Class.forName(DRIVER).newInstance();
        Connection oracleConn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
            
        oracleConn.setAutoCommit(false);
        
        // Sentencia de borrado
        PreparedStatement delete = oracleConn.prepareStatement(DELETE);
        delete.setInt(1, id_campista);
        delete.executeUpdate();
        
        oracleConn.commit();
        oracleConn.setAutoCommit(true);
        oracleConn.close();
    }

    public DefaultTableModel getTablaPadecen() {
        
        DefaultTableModel tablaPadecen = new DefaultTableModel();
        
        try {
            /*
            * Conexion a la base de datos
            */
            Class.forName(DRIVER).newInstance();
            Connection oracleConn = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
            
            PreparedStatement s = oracleConn.prepareStatement(READALL);
            ResultSet rs = s.executeQuery();
            ResultSetMetaData rsMd = rs.getMetaData();
            //La cantidad de columnas que tiene la consulta
            int numeroColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= numeroColumnas; i++) {
                tablaPadecen.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[numeroColumnas];
                

                tablaPadecen.addRow(fila);
            }
            oracleConn.close();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            System.out.println("PadecenDAO::getTablaPadecen -- " + e.getMessage());
        }
        finally {
            return tablaPadecen;
        }
    }
}
