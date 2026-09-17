package pe.empresa.pedidos.domain;

public class SinDescuento implements EstrategiaDescuento {

    @Override
    public double aplicar(double subtotal) {
        return subtotal;
    }

    @Override
    public String descripcion() {
        return "Sin descuento";
    }
}
