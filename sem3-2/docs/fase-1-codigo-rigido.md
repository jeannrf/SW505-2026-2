# Fase 1: El Código que Duele — Análisis de Rigidez y Puntos de Variación

Respuestas al cuestionario guía sobre la clase `PedidoRigido.java`:

---

### 1. ¿Cuántos puntos de variación hay? (McConnell, 2004 — Cap. 5)

En la clase existen **dos puntos de variación principales**:

1. **La regla de cálculo de descuentos:** la lógica condicional que evalúa si el cliente es regular, VIP o de campaña navideña para decidir qué porcentaje rebajar (`0.85`, `0.80` o ninguno).
2. **El mecanismo de procesamiento del cobro:** el bloque condicional que inspecciona la cadena de texto (`tarjeta`, `yape`, `efectivo`) para ejecutar la acción de pago correspondiente.

Adicionalmente, existen variaciones secundarias acopladas al código, como el uso de factores numéricos fijos (*números mágicos*) y cadenas literales (*magic strings*), que representan valores susceptibles de cambio pero sin nombres que expliquen su intención.

---

### 2. ¿Qué pasa si el negocio agrega "descuento por Black Friday"?

Nos vemos forzados a modificar directamente el código fuente de `PedidoRigido`, insertando una nueva rama condicional `else if (tipoCliente.equals("blackfriday"))`.

Esto viola el Principio Abierto/Cerrado (OCP), ya que la clase no está cerrada a la modificación para admitir nuevas promociones. Además, genera un riesgo innecesario de regresión: abrir y alterar una clase crítica para una campaña comercial temporal puede introducir errores accidentales en los cálculos de clientes regulares o VIP, obligando a re-probar y redesplegar todo el flujo de pedidos.

---

### 3. ¿Qué pasa si se agrega "Plin" como método de pago?

Se evidencia una violación directa del Principio de Responsabilidad Única (SRP): la clase `PedidoRigido` no solo calcula el total de la venta, sino que asume la responsabilidad de saber cómo se procesa cada medio de pago.

Para soportar Plin, tendríamos que agregar otro `else if (metodoPago.equals("plin"))`. Si más adelante la pasarela de Plin requiere llamadas HTTP a un API externo, manejo de tokens, reintentos o respuestas asíncronas, toda esa lógica de infraestructura y red terminaría contaminando la clase de pedidos.

---

### 4. ¿Qué pasa si el descuento VIP cambia de 15% a 20%?

Aunque parece un cambio menor (cambiar `0.85` por `0.80`), al estar incrustado como un literal numérico dentro del método, exige modificar el archivo Java, recompilar el proyecto y generar un nuevo despliegue.

No existe un lugar centralizado, entidad ni configuración independiente donde las reglas y políticas comerciales residan, lo que dificulta el rastreo y mantenimiento de las tarifas del negocio.

---

### 5. ¿Qué pasa si el umbral de "cliente VIP" cambia según el monto? (ej. VIP solo si subtotal > S/ 500)

El parámetro simple `String tipoCliente` resulta insuficiente. La lógica condicional tendría que volverse compuesta dentro del cálculo: `else if (tipoCliente.equals("vip") && subtotal > 500)`.

Esto provoca que la regla sobre quién califica comercialmente como cliente VIP se filtre y disperse dentro del cálculo del pedido, en lugar de pertenecer a una entidad o servicio de evaluación de clientes independiente.

---

## Conclusión sobre puntos de variación

> *"Un punto de variación es una decisión de diseño que se sabe que puede cambiar. Encapsularlo significa que el cambio afecta solo al código dentro de esa frontera."* (McConnell, 2004)

Encapsular ambos puntos de variación detrás de contratos estables (como interfaces) garantiza que, cuando el negocio agregue campañas o pasarelas, el impacto quede confinado a una nueva clase sin poner en riesgo el código en producción.
