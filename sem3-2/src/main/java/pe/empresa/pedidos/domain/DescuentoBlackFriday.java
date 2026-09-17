package pe.empresa.pedidos.domain;

public class DescuentoBlackFriday implements EstrategiaDescuento {

    private static final double FACTOR_DESCUENTO_BLACK_FRIDAY = 0.70;

    @Override
    public double aplicar(double subtotal) {
        return subtotal * FACTOR_DESCUENTO_BLACK_FRIDAY;
    }

    @Override
    public String descripcion() {
        return "Black Friday 30%";
    }
}
