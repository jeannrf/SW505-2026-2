package pe.empresa.pedidos.domain;

public class DescuentoNavidad implements EstrategiaDescuento {

    private static final double FACTOR_DESCUENTO_NAVIDAD = 0.80;

    @Override
    public double aplicar(double subtotal) {
        return subtotal * FACTOR_DESCUENTO_NAVIDAD;
    }

    @Override
    public String descripcion() {
        return "Navidad 20%";
    }
}
