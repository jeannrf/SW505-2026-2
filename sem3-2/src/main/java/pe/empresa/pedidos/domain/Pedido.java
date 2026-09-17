package pe.empresa.pedidos.domain;

import pe.empresa.pedidos.notificacion.Notificador;
import pe.empresa.pedidos.pago.MetodoPago;

public class Pedido {

    private final EstrategiaDescuento estrategia;
    private final MetodoPago metodoPago;
    private final Notificador notificador;

    // Inyección de Dependencias por constructor para todos los colaboradores
    public Pedido(EstrategiaDescuento estrategia, MetodoPago metodoPago, Notificador notificador) {
        this.estrategia = estrategia;
        this.metodoPago = metodoPago;
        this.notificador = notificador;
    }

    public double procesar(double subtotal) {
        double total = estrategia.aplicar(subtotal);
        metodoPago.procesar(total);
        notificador.notificar("cliente@empresa.pe", "Pedido confirmado por un total de S/ " + total);
        return total;
    }
}
