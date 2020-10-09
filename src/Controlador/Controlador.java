package Controlador;

import Mundo.SQL;
import java.util.ArrayList;
import recurso.Pedido;

/**
 *
 * @author Juan Pablo
 */
public class Controlador {

    private SQL sql;

    public Controlador() {
        sql = new SQL();
    }

    public ArrayList<Pedido> consultaPedidos() {
        return sql.consultaPedidos();
    }

    public void modificarPedido(String orden, int mesa) {
        sql.modificarPedido(orden, mesa);
    }

    public Object[][] consultaDetalle(String id) {
        return sql.consultaDetalle(id);
    }
}
