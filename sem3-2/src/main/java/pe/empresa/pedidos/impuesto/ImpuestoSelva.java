package pe.empresa.pedidos.impuesto;

// PUNTO DE VARIACIÓN: Algoritmo de cálculo de impuestos por región (Strategy). Permite variar las tasas impositivas y fórmulas fiscales sin modificar Pedido.
public class ImpuestoSelva implements CalculadoraImpuesto {

    private static final double TASA_DEFECTO_SELVA = 0.10;
    private final double tasa;

    public ImpuestoSelva() {
        this(TASA_DEFECTO_SELVA);
    }

    public ImpuestoSelva(double tasa) {
        this.tasa = tasa;
    }

    @Override
    public double calcular(double montoBase) {
        return montoBase * tasa;
    }

    @Override
    public String descripcion() {
        return "Impuesto Selva promocional " + (int) Math.round(tasa * 100) + "%";
    }
}
