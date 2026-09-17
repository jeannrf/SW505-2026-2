package pe.empresa.pedidos.pago;

public interface MetodoPago {
    void procesar(double monto);
    String nombre();
}
