package pe.empresa.pedidos.pago;

public class PagoYape implements MetodoPago {

    @Override
    public void procesar(double monto) {
        System.out.println("Cobrando S/ " + monto + " con Yape");
    }

    @Override
    public String nombre() {
        return "Yape";
    }
}
