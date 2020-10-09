package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

/**
 *
 * @author Juan Pablo
 */
public class InterfazApp extends JFrame {

    public static void main(String[] args) {
        InterfazApp interfazApp = new InterfazApp(new Controlador());
    }

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    private final Controlador ctrl;
    private final JPanel pnlPedidos;

    public InterfazApp(Controlador ctrl) {
        this.ctrl = ctrl;
        setExtendedState(MAXIMIZED_BOTH);
        getContentPane().setLayout(null);
        setTitle("  Software Restaurante  ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        //setResizable(false);

        /*
        pnlPedido = new PanelPedido();
        pnlPedido.setBackground(Color.white);
        pnlPedido.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Orden")));
        pnlPedido.setBounds(10, 10, 400, (int) height - 50);
        //add(pnlPedido);
         */
        pnlPedidos = new PanelPedidos(ctrl);
        add(pnlPedidos);
        setVisible(true);

    }
}
