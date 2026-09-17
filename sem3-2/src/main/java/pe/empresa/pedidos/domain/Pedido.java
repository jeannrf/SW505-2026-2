package pe.empresa.pedidos.domain;

import pe.empresa.pedidos.pago.MetodoPago;

public class Pedido {
    private final EstrategiaDescuento estrategia;
    private final MetodoPago metodoPago;

    // DI por constructor: los colaboradores llegan desde fuera
    public Pedido(EstrategiaDescuento estrategia, MetodoPago metodoPago) {
        this.estrategia = estrategia;
        this.metodoPago = metodoPago;
    }

    public double procesar(double subtotal) {
        double total = estrategia.aplicar(subtotal);
        metodoPago.procesar(total);
        return total;
    }
}
