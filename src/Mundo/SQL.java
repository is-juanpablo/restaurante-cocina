package Mundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import recurso.Pedido;

/**
 *
 * @author Juan Pablo
 */
public class SQL {

    Connection connection = null;

    public SQL() {
        conectarDB();
    }

    public Connection conectarDB() {
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            String serverName = "localhost";
            String mydatabase = "software_restaurante";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = "root";
            String password = "";

            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Conexion establecida");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public ArrayList<Pedido> consultaPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        String query = "SELECT * FROM pedido WHERE estado = 'Esperando' ORDER BY dia DESC, tiempo DESC";

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String id = rs.getString("id");
                int mesa = rs.getInt("mesa");
                String estado = rs.getString("estado");
                String tiempo = calcularTiempo(rs.getString("tiempoEspera"));

                Pedido pedido = new Pedido(id, mesa, estado, tiempo, "");

                pedidos.add(pedido);
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return pedidos;
    }

    public void modificarPedido(String orden, int mesa) {
        String query = "UPDATE pedido SET estado = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, "Comiendo");
            stmt.setString(2, orden);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        /*
        query = "UPDATE mesa SET estado = ? WHERE numeroMesa = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, "0");
            stmt.setString(2, String.valueOf(mesa));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
         */
    }

    public Object[][] consultaDetalle(String id) {
        String query = "SELECT COUNT(*) FROM `detalle` WHERE idOrden='" + id + "'";
        int cantidadFilas = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                cantidadFilas = rs.getInt("COUNT(*)");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        query = "SELECT producto.nombre, detalle.cantidad, detalle.observacion FROM detalle INNER JOIN producto ON detalle.idProducto = producto.id WHERE idOrden = '" + id + "'";
        String[][] productosDetalle = new String[cantidadFilas][3];

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            int contX = 0;
            int contY = 0;
            while (rs.next()) {
                productosDetalle[contX][contY] = rs.getString("cantidad");
                productosDetalle[contX][contY + 1] = rs.getString("nombre");
                productosDetalle[contX][contY + 2] = rs.getString("observacion");
                contX++;
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return productosDetalle;
    }

    private String calcularTiempo(String tiempo) {
        String datosTiempo[] = tiempo.split(":");
        if (datosTiempo[0].equals("00")) {
            return "";
        } else {
            int datosT[] = new int[3];

            datosT[0] = Integer.parseInt(datosTiempo[0]);
            datosT[1] = Integer.parseInt(datosTiempo[1]);
            datosT[2] = Integer.parseInt(datosTiempo[2]);

            Calendar calendar = Calendar.getInstance();
            int hora = calendar.get(Calendar.HOUR_OF_DAY);
            int minuto = calendar.get(Calendar.MINUTE);
            int segundos = calendar.get(Calendar.SECOND);

            datosT[2] = (segundos < datosT[2]) ? datosT[1]++ + (60 - datosT[1] + 1) + segundos - datosT[2] : segundos - datosT[2];
            datosT[1] = (minuto < datosT[1]) ? datosT[0]++ + (60 - datosT[0] + 1) + minuto - datosT[1] : minuto - datosT[1];
            datosT[0] = hora - datosT[0];

            return ((datosT[0] == 0) ? "" : datosT[0] + ":") + datosT[1] + ":" + datosT[2];
        }
    }
}
