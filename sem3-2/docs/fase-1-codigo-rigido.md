# Fase 1: El Código que Duele — Análisis de Rigidez y Puntos de Variación

Respuestas al cuestionario guía sobre la clase `PedidoRigido.java`, identificando los problemas de diseño y planteando las soluciones arquitectónicas adecuadas.

---

### 1. ¿Cuántos puntos de variación hay? (McConnell, 2004 — Cap. 5)

En la clase existen **dos puntos de variación principales**:

1. **La regla de cálculo de descuentos:** la lógica condicional que decide qué porcentaje rebajar según el cliente o la temporada (`"regular"`, `"vip"`, `"navidad"`).
2. **El mecanismo de procesamiento del cobro:** el bloque condicional que ejecuta la acción de pago según el canal elegido (`"tarjeta"`, `"yape"`, `"efectivo"`).

Adicionalmente, existen variaciones secundarias acopladas: el uso de números mágicos (`0.85`, `0.80`) y cadenas literales fijas (*magic strings*).

* **Solución arquitectónica:** Separar ambos ejes en contratos independientes (interfaces). `Pedido` no debe contener la lógica de cómo se descuenta ni de cómo se cobra, sino únicamente coordinar la ejecución delegando cada tarea a componentes especializados.

---

### 2. ¿Qué pasa si el negocio agrega "descuento por Black Friday"?

* **El problema:** Estamos obligados a abrir `PedidoRigido.java` e intercalar un nuevo `else if (tipoCliente.equals("blackfriday"))`. Esto viola el Principio Abierto/Cerrado (OCP) y genera riesgo de regresión: tocar una clase central en producción para una campaña comercial temporal puede alterar por error los descuentos existentes de clientes regulares o VIP.

* **Solución propuesta — Patrón Strategy (Estrategia):**
  - *¿En qué consiste?* En lugar de tener condicionales con fórmulas matemáticas, creamos una interfaz común llamada `EstrategiaDescuento` con un método `aplicar(subtotal)`. Cada tipo de descuento (`DescuentoVip`, `DescuentoNavidad`, `DescuentoBlackFriday`) se convierte en una clase independiente.
  - *¿Por qué lo usamos?* Porque convierte cada algoritmo de cálculo en una pieza intercambiable. Cuando llegue Black Friday, simplemente creamos un archivo nuevo `DescuentoBlackFriday.java`. La clase `Pedido` no se modifica en lo absoluto; solo recibe la nueva estrategia desde fuera.

---

### 3. ¿Qué pasa si se agrega "Plin" como método de pago?

* **El problema:** Se viola el Principio de Responsabilidad Única (SRP), porque la clase de pedidos mezcla el cálculo financiero con los protocolos de cobro. Agregar Plin exige otro `else if (metodoPago.equals("plin"))`. Si en el futuro Plin requiere invocar un API REST, manejar tokens o reintentos por caída de red, todo ese código técnico terminaría ensuciando la clase de pedidos.

* **Solución propuesta — Polimorfismo mediante Interfaz:**
  - *¿En qué consiste?* Creamos una interfaz `MetodoPago` con el método `procesar(monto)`. Clases como `PagoTarjeta`, `PagoYape` y `PagoPlin` implementan este contrato y ocultan sus detalles técnicos.
  - *¿Por qué lo usamos?* Permite que `Pedido` solo invoque `metodo.procesar(total)` sin importarle si el dinero viaja por una pasarela bancaria, billetera móvil o efectivo. Para soportar Plin, basta con crear la clase `PagoPlin` (1 archivo nuevo, 0 modificados en el núcleo del sistema).

---

### 4. ¿Qué pasa si el descuento VIP cambia de 15% a 20%?

* **El problema:** El porcentaje está incrustado como un número mágico (`0.85`) dentro del flujo del método. Aunque es un ajuste de una sola línea, obliga a modificar código fuente, recompilar el proyecto, generar una nueva versión y desplegar el sistema. Además, no hay visibilidad centralizada de las políticas comerciales.

* **Solución propuesta — Constantes con Nombre y Externalización de Configuración:**
  - *¿En qué consiste?* Primero, a nivel de código, reemplazamos los números mágicos por constantes descriptivas como `FACTOR_DESCUENTO_VIP = 0.80`. Segundo, si las tasas cambian con frecuencia por promociones, ese valor se extrae a un archivo externo de propiedades (`application.properties`) o variables de entorno.
  - *¿Por qué lo usamos?* Permite que el equipo comercial o de operaciones ajuste las tasas directamente en la configuración sin necesidad de que un programador reescriba código ni recompilar la aplicación.

---

### 5. ¿Qué pasa si el umbral de "cliente VIP" cambia según el monto? (ej. VIP solo si subtotal > S/ 500)

* **El problema:** El parámetro primitivo `String tipoCliente` colapsa. La condición tendría que volverse compleja: `else if (tipoCliente.equals("vip") && subtotal > 500)`. La regla sobre quién califica comercialmente como cliente VIP invade y contamina la rutina de cálculo de la orden.

* **Solución propuesta — Encapsulamiento en el Dominio (Modelo `Cliente`):**
  - *¿En qué consiste?* En vez de pasar cadenas de texto sueltas (`String tipoCliente`), creamos un objeto o entidad `Cliente` que sea dueño de sus propios atributos (historial de compras, categoría, etc.) y contenga métodos como `cliente.esVip(monto)`.
  - *¿Por qué lo usamos?* Traslada la responsabilidad al lugar correcto: saber si alguien califica como VIP le compete al módulo de clientes, no al módulo de facturación de pedidos. Si mañana la regla de calificación cambia (por ejemplo, VIP por acumulación de puntos anuales), solo cambia la clase `Cliente` y la clase `Pedido` permanece inalterada.

---

## Conclusión sobre puntos de variación

> *"Un punto de variación es una decisión de diseño que se sabe que puede cambiar. Encapsularlo significa que el cambio afecta solo al código dentro de esa frontera."* (McConnell, 2004)

Identificar y encapsular estos puntos de variación desde el inicio evita que un requerimiento habitual del negocio provoque modificaciones invasivas en el código de producción. Las soluciones aplicadas (Polimorfismo, Strategy, Configuración Externa y Encapsulamiento en el Dominio) son las que transforman un diseño rígido en un sistema preparado para sobrevivir al cambio.
