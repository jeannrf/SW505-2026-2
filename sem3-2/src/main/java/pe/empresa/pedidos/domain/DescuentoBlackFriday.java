package pe.empresa.pedidos.domain;

public class DescuentoBlackFriday implements EstrategiaDescuento {

    @Override
    public double aplicar(double subtotal) {
        return subtotal * 0.70;
    }

    @Override
    public String descripcion() {
        return "Black Friday 30%";
    }
}
