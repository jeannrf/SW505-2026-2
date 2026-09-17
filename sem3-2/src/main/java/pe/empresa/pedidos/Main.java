package pe.empresa.pedidos;

import pe.empresa.pedidos.domain.DescuentoVip;
import pe.empresa.pedidos.domain.Pedido;
import pe.empresa.pedidos.impuesto.ImpuestoLima;
import pe.empresa.pedidos.notificacion.NotificadorEmail;
import pe.empresa.pedidos.pago.PagoYape;

public class Main {

    public static void main(String[] args) {
        Pedido pedido = new Pedido(
            new DescuentoVip(),
            new PagoYape(),
            new NotificadorEmail(),
            new ImpuestoLima()
        );
        pedido.procesar(200.0);
    }
}
