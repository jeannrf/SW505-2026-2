package polimorfismo;

public class PagoTarjeta implements MetodoPago {
    public void pagar(double monto) {
        System.out.println("Pagando " + monto + " con tarjeta");
    }

}
