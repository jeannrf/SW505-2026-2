package pe.empresa.pedidos;

import pe.empresa.pedidos.domain.DescuentoBlackFriday;
import pe.empresa.pedidos.domain.Pedido;
import pe.empresa.pedidos.pago.PagoYape;

public class Main {

    public static void main(String[] args) {
        Pedido pedido = new Pedido(
            new DescuentoBlackFriday(),
            new PagoYape()
        );
        pedido.procesar(200.0);
    }
}
