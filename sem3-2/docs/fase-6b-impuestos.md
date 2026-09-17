# Fase 6b: Reto Integrador — Cálculo de Impuestos por Región

## 1. Justificación del Patrón Strategy para Impuestos

El cálculo impositivo por región se modela bajo el patrón **Strategy** y no simplemente como un polimorfismo básico por los siguientes motivos:

* **Encapsulamiento de algoritmos fiscales completos:** Cada región no solo difiere en una tasa porcentual, sino potencialmente en la fórmula de cálculo (exenciones tributarias, regímenes especiales de frontera o Amazonía, impuestos adicionales como IPM o ISC).
* **El objeto completo representa una regla algorítmica:** La interfaz `CalculadoraImpuesto` define la firma para ejecutar el cálculo sobre la base imponible (`calcular(montoBase)`), permitiendo al contexto (`Pedido`) variar su lógica de liquidación fiscal en tiempo de ejecución sin acoplarse a normativas geográficas.
* **Cohesión y orden de cálculo en el dominio:** En `Pedido.java`, se aplica primero la estrategia de descuento sobre el subtotal bruto y, sobre el subtotal resultante (base imponible), se calcula el impuesto antes de consolidar el total final a cobrar.

---

## 2. Impacto ante una Nueva Región Tributaria

Si la entidad reguladora o el negocio expande operaciones a una nueva región con una tasa impositiva particular (ej. Cusco con régimen especial):

* **Archivos nuevos creados:** `1` (`ImpuestoCusco.java` implementando `CalculadoraImpuesto`).
* **Archivos existentes modificados:** `0` (`Pedido.java`, `CalculadoraImpuesto.java` y las clases `ImpuestoLima`, `ImpuestoArequipa`, `ImpuestoSelva` permanecen intactas).

---

## 3. Externalización de Tasas Promocionales en `application.properties`

* La tasa de la Selva (`impuesto.selva.porcentaje=0.10`) se externalizó en `application.properties` y se expone a través de `Configuracion.java`.
* **Beneficio ante cambios normativos:** Si el gobierno modifica temporalmente la tasa promocional de la Selva (por ejemplo, del 10% al 8%), la actualización se realiza en el archivo de configuración o mediante variables de entorno del servidor. No se requiere editar código fuente, recompilar el proyecto ni generar un nuevo despliegue de artefactos.
