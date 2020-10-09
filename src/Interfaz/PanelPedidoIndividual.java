package Interfaz;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import recurso.Pedido;
import recurso.Variables;

/**
 *
 * @author Juan Pablo
 */
public class PanelPedidoIndividual extends JPanel {

    private Pedido pedido;
    private String posicion;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private int ancho = width / 4 - 35;
    private int alto = height / 2 - 140;
    private final JLabel lblMesa, lblOrden, lblTiempo;
    private final Controlador ctrl;
    private final JPanel pnlMesa, pnlOrden, pnlDatos;
    private JButton btnListo;
    private String orden;
    private int mesa;
    private DefaultTableModel dtm;
    String[] columnas = {"Cantidad", "Producto", "Extra"};
    //private JRadioButton checkSi, checkNo;

    public PanelPedidoIndividual(Controlador ctrl, Pedido pedido) {
        this.ctrl = ctrl;
        this.pedido = pedido;
        setLayout(null);
        setBackground(Color.white);
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(pedido.getId())));

        pnlMesa = new JPanel(new GridLayout(1, 2));
        pnlMesa.setBackground(Color.white);
        pnlMesa.setBounds(15, 15, ancho, 30);
        lblMesa = new JLabel(pedido.getMesa() + "", SwingConstants.CENTER);
        lblMesa.setForeground(Color.blue.darker().darker());
        pnlMesa.add(lblMesa);
        lblOrden = new JLabel(pedido.getId() + "hola", SwingConstants.CENTER);
        pnlMesa.add(lblOrden);
        add(pnlMesa);

        pnlOrden = new JPanel(new GridLayout());
        pnlOrden.setBackground(Color.white);
        pnlOrden.setBounds(15, 45, ancho, alto);
        pnlOrden.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Orden")));
        add(pnlOrden);

        dtm = new DefaultTableModel(null, columnas);
        JTable table = new JTable(dtm);
        pnlOrden.add(table);
        JScrollPane scrollPane = new JScrollPane(table);
        pnlOrden.add(scrollPane, BorderLayout.CENTER);

        pnlDatos = new JPanel(new GridLayout());
        pnlDatos.setBackground(Color.white);
        pnlDatos.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("")));
        pnlDatos.setBounds(15, 45 + alto + 2, ancho, 40);
        add(pnlDatos);

        /*
        JPanel pnlCheck = new JPanel(new GridLayout(1, 2));
        pnlCheck.setBackground(Color.white);
        checkSi = new JRadioButton("SI");
        checkSi.setBackground(Color.white);
        checkNo = new JRadioButton("NO");
        checkNo.setSelected(true);
        checkNo.setBackground(Color.white);
        ButtonGroup grupoCheck = new ButtonGroup();
        grupoCheck.add(checkSi);
        grupoCheck.add(checkNo);
        pnlCheck.add(checkSi);
        pnlCheck.add(checkNo);
        pnlDatos.add(pnlCheck, SwingConstants.CENTER);
         */
        btnListo = new JButton("LISTO");
        btnListo.setBackground(Variables.rojo);
        btnListo.setForeground(Color.white);
        pnlDatos.add(btnListo);
        btnListo.addActionListener((ActionEvent ae) -> {
            ctrl.modificarPedido(orden, mesa);
        });
        lblTiempo = new JLabel("12:30", JLabel.CENTER);
        pnlDatos.add(lblTiempo);

        setVisible(true);
    }

    public void setLblMesa(int mesa) {
        this.mesa = mesa;
        lblMesa.setText((mesa == 0) ? "" : "Mesa # " + mesa);
    }

    public void setLblTiempo(String tiempo) {
        if (tiempo.length() > 1) {
            int tiempoM = Integer.parseInt(tiempo.split(":")[0]);
            if (tiempoM >= 10) {
                lblTiempo.setForeground(colorEstado("Tiempo"));
            }
            lblTiempo.setText(tiempo);
        }
    }

    public void setLblOrden(String orden) {
        this.orden = orden;
        lblOrden.setText(orden);
    }

    public void setLblEstado(String estado) {
        /*lblEstado.setText(estado);
        lblEstado.setForeground(colorEstado(estado));
         */
    }

    private Color colorEstado(String estado) {
        Color color = new Color(0);
        switch (estado) {
            case "Completado":
                color = Color.green.darker();
                break;
            case "Esperando":
                color = Color.yellow.darker();
                break;
            case "Ordenando":
                color = Color.blue.darker();
                break;
            case "Comiendo":
                color = Color.red.darker();
                break;
            case "Tiempo":
                color = Color.red.brighter().brighter();
                break;
        }
        return color;
    }

    public void setTabla(Object[][] tabla) {
        dtm.setDataVector(tabla, columnas);
    }

}
