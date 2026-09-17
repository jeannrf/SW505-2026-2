package pe.empresa.pedidos.domain;

public interface EstrategiaDescuento {
    double aplicar(double subtotal);
    String descripcion();
}
