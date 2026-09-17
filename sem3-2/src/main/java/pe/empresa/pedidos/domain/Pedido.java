package pe.empresa.pedidos.domain;

import pe.empresa.pedidos.impuesto.CalculadoraImpuesto;
import pe.empresa.pedidos.notificacion.Notificador;
import pe.empresa.pedidos.pago.MetodoPago;

public class Pedido {

    private final EstrategiaDescuento estrategia;
    private final MetodoPago metodoPago;
    private final Notificador notificador;
    private final CalculadoraImpuesto calculadoraImpuesto;

    // Inyección de Dependencias por constructor de los cuatro colaboradores
    public Pedido(
            EstrategiaDescuento estrategia,
            MetodoPago metodoPago,
            Notificador notificador,
            CalculadoraImpuesto calculadoraImpuesto) {
        this.estrategia = estrategia;
        this.metodoPago = metodoPago;
        this.notificador = notificador;
        this.calculadoraImpuesto = calculadoraImpuesto;
    }

    public double procesar(double subtotal) {
        // 1. Aplicación de estrategia de descuento sobre el subtotal base
        double subtotalConDescuento = estrategia.aplicar(subtotal);

        // 2. Cálculo de impuestos sobre el monto imponible resultante
        double impuesto = calculadoraImpuesto.calcular(subtotalConDescuento);
        double totalFinal = subtotalConDescuento + impuesto;

        // 3. Procesamiento del cobro con el método de pago inyectado
        metodoPago.procesar(totalFinal);

        // 4. Notificación de confirmación al cliente
        notificador.notificar(
                "cliente@empresa.pe",
                "Pedido confirmado por un total de S/ " + totalFinal
                        + " (incluye " + calculadoraImpuesto.descripcion() + ": S/ " + impuesto + ")");

        return totalFinal;
    }
}
