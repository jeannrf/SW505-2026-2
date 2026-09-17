package pe.empresa.pedidos.pago;

public class Cobrador {

    public void cobrar(MetodoPago metodo, double monto) {
        metodo.procesar(monto);
    }
}
