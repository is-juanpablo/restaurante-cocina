package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import recurso.Pedido;

/**
 *
 * @author Juan Pablo
 */
public class PanelPedidos extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    private PanelPedidoIndividual[] pnlPedido = new PanelPedidoIndividual[16];
    private Controlador ctrl;
    private int pedidos = 0;
    private JPanel pnlPrincipal;

    public PanelPedidos(Controlador ctrl) {
        this.ctrl = ctrl;
        setVisible(true);
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Pedidos")));
        setBackground(Color.white);
        setBounds(10, 10, (int) width - 20, (int) height - 50);
        setLayout(new GridLayout(2, 4));

        hiloPedidos();

    }

    private void hiloPedidos() {
        for (int i = 0; i < 8; i++) {
            PanelPedidoIndividual panelIndividual = new PanelPedidoIndividual(ctrl, new Pedido("1", 0, "2", "3", "4"));
            pnlPedido[i] = panelIndividual;
            pnlPedido[i].setVisible(false);
            add(panelIndividual);
        }

        new Thread(() -> {
            while (true) {
                ArrayList<Pedido> pedidos = ctrl.consultaPedidos();

                for (int i = 0; i < 8; i++) {
                    Pedido pedido = new Pedido(" ", 0, "", "", "");
                    boolean opcion = false;
                    if (i < pedidos.size()) {
                        pedido = pedidos.get(i);
                        opcion = true;
                    }
                    if (this.pedidos != pedidos.size()) {
                        pnlPedido[i].setLblMesa(pedido.getMesa());
                        pnlPedido[i].setLblOrden(pedido.getId());
                        pnlPedido[i].setLblEstado(pedido.getEstado());
                        pnlPedido[i].setVisible(opcion);
                        pnlPedido[i].setTabla(ctrl.consultaDetalle(pedido.getId()));

                        revalidate();
                        repaint();
                    }
                    pnlPedido[i].setLblTiempo(pedido.getTiempo());
                }
                this.pedidos = pedidos.size();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PanelPedidos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }
}
