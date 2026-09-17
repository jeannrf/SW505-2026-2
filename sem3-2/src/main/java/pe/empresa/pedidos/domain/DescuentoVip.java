package pe.empresa.pedidos.domain;

public class DescuentoVip implements EstrategiaDescuento {

    private static final double FACTOR_DESCUENTO_VIP = 0.85;

    @Override
    public double aplicar(double subtotal) {
        return subtotal * FACTOR_DESCUENTO_VIP;
    }

    @Override
    public String descripcion() {
        return "VIP 15%";
    }
}
