# Fase 6b: Reto Integrador — Cálculo de Impuestos por Región

## Por qué modelar los impuestos con el patrón Strategy

A diferencia de un polimorfismo básico pensado para seleccionar un dispositivo o canal de salida, el cálculo tributario por región encaja directamente en la definición del patrón **Strategy**:

Las leyes fiscales raramente se reducen a multiplicar por un número fijo. En un escenario tributario real, cada región o régimen puede implicar reglas algorítmicas completas, como montos inafectos, exoneraciones temporales, topes de facturación o impuestos combinados (como IGV más impuestos municipales).

Al crear la interfaz `CalculadoraImpuesto`, cada clase concreta (`ImpuestoLima`, `ImpuestoArequipa`, `ImpuestoSelva`) encapsula un algoritmo impositivo completo e intercambiable. La clase `Pedido` no asume cómo se liquida el tributo; únicamente recibe el monto imponible (el subtotal ya rebajado con el descuento), delega el cálculo llamando a `calcular(montoBase)` y suma el impuesto resultante para obtener el total final a cobrar.

---

## Impacto ante una nueva región o normativa impositiva

Si la administración tributaria crea una zona franca o una nueva tasa regional (por ejemplo, un régimen especial para Cusco):

- Se requiere crear un solo archivo nuevo: `ImpuestoCusco.java`, implementando `CalculadoraImpuesto`.
- Cero archivos existentes modificados: ni `Pedido.java`, ni las demás calculadoras de impuestos, ni las interfaces sufren cambios.

El costo de adaptación sigue siendo predecible y no existe riesgo de alterar los cálculos tributarios de Lima o Arequipa por tocar una clase compartida.

---

## Ventajas de externalizar la tasa promocional en `application.properties`

En el caso de la Selva, la tasa del 10% se considera promocional y está sujeta a revisiones periódicas por parte de las autoridades comerciales. Para evitar acoplar esa cifra al código fuente, la definimos en `application.properties` bajo la propiedad `impuesto.selva.porcentaje=0.10` y la leemos a través de `Configuracion.java`.

Esto permite que, si el beneficio fiscal se modifica temporalmente (por ejemplo, si baja al 8% o sube al 12%), el cambio se aplique directamente en el archivo de propiedades del servidor o mediante variables de entorno en el contenedor de despliegue. No hace falta abrir el proyecto, editar archivos Java, compilar ni generar un nuevo empaquetado para que el sistema empiece a cobrar con la nueva tasa.
