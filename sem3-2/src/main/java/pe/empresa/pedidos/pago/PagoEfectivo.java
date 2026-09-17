package pe.empresa.pedidos.pago;

public class PagoEfectivo implements MetodoPago {

    @Override
    public void procesar(double monto) {
        System.out.println("Cobrando S/ " + monto + " en efectivo");
    }

    @Override
    public String nombre() {
        return "Efectivo";
    }
}
