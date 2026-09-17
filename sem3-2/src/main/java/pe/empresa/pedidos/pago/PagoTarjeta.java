package pe.empresa.pedidos.pago;

public class PagoTarjeta implements MetodoPago {

    @Override
    public void procesar(double monto) {
        System.out.println("Cobrando S/ " + monto + " con tarjeta");
    }

    @Override
    public String nombre() {
        return "Tarjeta";
    }
}
