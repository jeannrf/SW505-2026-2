# Fase 1: El Código que Duele — Análisis de Rigidez y Puntos de Variación

## Puntos de variación detectados en `PedidoRigido`

Al revisar la implementación inicial de `PedidoRigido.java`, destacan dos puntos de variación centrales que cambian por motivos distintos:

El primer punto es la **estrategia de descuentos según el tipo de cliente o campaña**. El método determina mediante condicionales si el cliente es `"regular"`, `"vip"` o `"navidad"`, aplicando directamente factores como `0.85` o `0.80`.

El segundo punto es el **mecanismo de procesamiento del pago**, donde otro bloque condicional evalúa cadenas de texto como `"tarjeta"`, `"yape"` o `"efectivo"` para decidir la acción de cobro.

Además de estos dos ejes principales, existen variaciones secundarias acopladas al código, como el uso de cadenas mágicas (*magic strings*) y factores numéricos fijos (*magic numbers*) sin nombres que expliquen su significado.

---

## Análisis de impacto ante cambios del negocio

### 1. Incorporación de un nuevo descuento (ej. "Black Friday")
Para agregar una campaña como Black Friday, nos vemos obligados a modificar el código fuente de `PedidoRigido`, insertando una nueva rama `else if (tipoCliente.equals("blackfriday"))`. Esto rompe el principio Abierto/Cerrado (OCP), ya que la clase no está cerrada a la modificación. Además, genera un riesgo innecesario de regresión: tocar una clase central para una promoción temporal puede alterar por error los cálculos de clientes regulares o VIP, obligando a revalidar todo el flujo de pedidos.

### 2. Soporte para un nuevo método de pago (ej. "Plin")
Agregar Plin evidencia un problema de responsabilidades. `PedidoRigido` no solo calcula montos, sino que también decide cómo se cobra, violando el principio de responsabilidad única (SRP). Tendríamos que añadir otro bloque condicional dentro del mismo método. Si en el futuro la integración con Plin exigiera validar tokens, consumir un API REST o manejar reintentos, toda esa lógica ajena al pedido terminaría ensuciando la misma clase.

### 3. Modificación del porcentaje de descuento VIP (de 15% a 20%)
Aunque parece un cambio menor (pasar de `0.85` a `0.80`), al estar incrustado como un número mágico dentro del flujo de ejecución, exige recompilar, versionar y redesplegar toda la clase de pedidos. No existe un lugar centralizado ni configurable donde residan las políticas comerciales de la empresa.

### 4. Condición dinámica para calificar a VIP (ej. VIP solo si subtotal > S/ 500)
En este escenario, el parámetro simple `String tipoCliente` colapsa. La lógica condicional tendría que volverse compuesta (`tipoCliente.equals("vip") && subtotal > 500`), mezclando la evaluación del perfil del cliente con el cálculo de la orden. La regla de negocio sobre qué define a un cliente VIP queda dispersa dentro del código de facturación en lugar de pertenecer al módulo de clientes.

---

## Qué es un punto de variación y por qué aislarlo

Siguiendo a Steve McConnell en *Code Complete* (Capítulo 5), un punto de variación es cualquier elemento del diseño, regla de negocio o algoritmo que sabemos que está sujeto a cambios futuros o que puede tener múltiples formas de resolverse a lo largo de la vida del software.

Encapsular un punto de variación significa colocar esa lógica cambiante detrás de un contrato estable (como una interfaz o abstracción). Al hacerlo, el radio de impacto de una modificación se reduce al mínimo: si agregamos una nueva pasarela o ajustamos una promoción, únicamente creamos o modificamos la pieza responsable de esa variación, manteniendo intacto y protegido el núcleo del negocio.
