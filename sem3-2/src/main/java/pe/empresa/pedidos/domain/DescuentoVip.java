package pe.empresa.pedidos.domain;

public class DescuentoVip implements EstrategiaDescuento {

    @Override
    public double aplicar(double subtotal) {
        return subtotal * 0.85;
    }

    @Override
    public String descripcion() {
        return "VIP 15%";
    }
}
