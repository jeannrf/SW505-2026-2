package pe.empresa.pedidos.impuesto;

// PUNTO DE VARIACIÓN: Algoritmo de cálculo de impuestos por región (Strategy). Permite variar las tasas impositivas y fórmulas fiscales sin modificar Pedido.
public class ImpuestoArequipa implements CalculadoraImpuesto {

    private static final double TASA_IGV_AREQUIPA = 0.18;

    @Override
    public double calcular(double montoBase) {
        return montoBase * TASA_IGV_AREQUIPA;
    }

    @Override
    public String descripcion() {
        return "IGV Arequipa 18%";
    }
}
