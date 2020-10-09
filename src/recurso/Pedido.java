package recurso;

/**
 *
 * @author Juan Pablo
 */
public class Pedido {

    private String id;
    private int mesa;
    private String estado;
    private String tiempo;
    private String dia;

    public Pedido() {
    }

    public Pedido(String id, int mesa, String estado, String tiempo, String dia) {
        this.id = id;
        this.mesa = mesa;
        this.estado = estado;
        this.tiempo = tiempo;
        this.dia = dia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
    
}
