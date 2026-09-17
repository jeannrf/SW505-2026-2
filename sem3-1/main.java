import polimorfismo.MetodoPago;
import polimorfismo.PagoTarjeta;
import polimorfismo.PagoYape;

public class main {

    public static void main(String[] args) {
        MetodoPago pago1 = new PagoTarjeta();
        MetodoPago pago2 = new PagoYape();

        pago1.pagar(200);
        pago2.pagar(12);
    }
}
