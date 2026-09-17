package polimorfismo;

public class PagoYape implements MetodoPago {
    public void pagar(double monto) {
        System.out.println("Pagando " + monto + " con Yape");
    }
}