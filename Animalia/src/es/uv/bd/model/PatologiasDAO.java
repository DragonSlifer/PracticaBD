package es.uv.bd.model;

import java.sql.*;
import java.util.HashMap;

/**
 *
 * @author Lidia Montero Egidos y Jorge Martinez Hernandez
 */
public class PatologiasDAO {

    /*
     * Parámetros de conexión a la base de datos
     */
    public static final String DRIVER = "oracle.jdbc.OracleDriver";
    public static final String DBURL = "jdbc:oracle:thin:@pokemon2.uv.es:1521:ORCL";
    public static final String USERNAME = "AL028";
    public static final String PASSWORD = "OneManArmy";

    private static final String LCLIENTE
            = "SELECT "
            + "  FROM "
            + " ORDER BY ";

    public PatologiasDAO() {
    }

    public HashMap<String, Integer> getTListaPatologias() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

        HashMap<String, Integer> listaClientes = new HashMap<>();

        Class.forName(DRIVER).newInstance();
        Connection oracleConn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

        PreparedStatement s = oracleConn.prepareStatement(LCLIENTE);
        ResultSet rs = s.executeQuery();

        while (rs.next()) {
            listaClientes.put(rs.getString("nombre"), rs.getInt("id_patología"));
        }
        oracleConn.close();

        return listaClientes;

    }

}
