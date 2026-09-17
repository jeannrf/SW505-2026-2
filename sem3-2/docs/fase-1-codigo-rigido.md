# Fase 1: El Código que Duele — Análisis de Rigidez y Puntos de Variación

## 1. Identificación de Puntos de Variación (McConnell, 2004 — Cap. 5)

En la clase `PedidoRigido` se identifican claramente **dos puntos de variación principales**:

1. **Estrategia y política de cálculo de descuentos:** La regla para determinar qué porcentaje o monto descontar según la clasificación del cliente o temporada (`"regular"`, `"vip"`, `"navidad"`).
2. **Mecanismo de procesamiento del pago:** El flujo de cobro e interacción según el medio de pago elegido (`"tarjeta"`, `"yape"`, `"efectivo"`).

*Observación adicional:* Existen puntos de variación secundarios acoplados directamente al código, tales como los valores constantes hardcodeados (números mágicos `0.85`, `0.80`) y los identificadores en texto plano (*magic strings*).

---

## 2. Impacto ante Cambios del Negocio

### ¿Qué pasa si el negocio agrega "descuento por Black Friday"?
* **Modificación obligatoria del código fuente existente:** Se debe alterar directamente la clase `PedidoRigido` agregando una nueva rama `else if (tipoCliente.equals("blackfriday"))`.
* **Violación del Principio Abierto/Cerrado (OCP):** La clase no está cerrada a la modificación para soportar nuevas promociones.
* **Riesgo de regresión:** Tocar una clase central de pedidos para una campaña temporal puede introducir errores accidentales en los cálculos de clientes regulares o VIP, obligando a re-probar todo el flujo de pedidos.

### ¿Qué pasa si se agrega "Plin" como método de pago?
* **Afectación de una responsabilidad ajena:** La clase `PedidoRigido` mezcla cálculo financiero con lógica de ejecución de pagos (violando el Principio de Responsabilidad Única - SRP).
* **Nuevo bloque condicional:** Se requiere incrustar otro `else if (metodoPago.equals("plin"))` dentro del mismo método. Si en el futuro Plin requiere llamar a un API externo o validar tokens, todo ese código invasivo terminaría ensuciando `PedidoRigido`.

### ¿Qué pasa si el descuento VIP cambia de 15% a 20%?
* **Modificación en el núcleo de la lógica:** Aunque parece un cambio trivial (cambiar `0.85` por `0.80`), al estar incrustado como número mágico (*hardcoded*), obliga a recompilar y desplegar toda la clase de pedidos.
* **Falta de trazabilidad:** No existe una entidad o configuración aislada donde las reglas de negocio de descuentos residan explícitamente.

### ¿Qué pasa si el umbral de "cliente VIP" cambia según el monto? (ej. VIP si subtotal > S/ 500)
* **Ruptura de la firma y abstracción del método:** El parámetro simple `String tipoCliente` resulta insuficiente. La lógica condicional tendría que combinarse: `else if (tipoCliente.equals("vip") && subtotal > 500)`.
* **Dispersión de reglas de negocio:** La determinación de quién califica como VIP se filtra dentro del cálculo del pedido en lugar de pertenecer a una entidad de cliente o una regla de elegibilidad independiente.

---

## 3. Definición de Punto de Variación y Conclusión

> **Punto de Variación:** Es cualquier decisión de diseño, regla de negocio o algoritmo dentro de un sistema de software que se anticipa que cambiará, evolucionará o tendrá múltiples implementaciones a lo largo del ciclo de vida del producto.

### ¿Por qué encapsularlo limita el alcance del cambio?
Encapsular un punto de variación consiste en esconder la lógica cambiante detrás de una frontera estable (como una interfaz o clase especializada). Al aislarlo:
* **Reduce el radio de impacto (*blast radius*):** Si se añade un nuevo descuento o método de pago, únicamente se crea o modifica el componente específico que encapsula esa variación, sin tocar ni arriesgar el flujo principal de `Pedido`.
* **Facilita la mantenibilidad y pruebas:** Cada variación puede probarse de manera aislada sin depender del resto del sistema.
