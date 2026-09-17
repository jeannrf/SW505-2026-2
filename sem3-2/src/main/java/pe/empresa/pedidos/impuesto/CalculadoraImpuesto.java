package pe.empresa.pedidos.impuesto;

public interface CalculadoraImpuesto {
    double calcular(double montoBase);
    String descripcion();
}
