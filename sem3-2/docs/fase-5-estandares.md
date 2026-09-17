# Fase 5: Estándares de Codificación — Auditoría y Limpieza

## Cambios aplicados en el código del proyecto

Siguiendo las pautas de McConnell (2004, Cap. 11), auditamos los módulos construidos en las fases previas para asegurar consistencia en nomenclatura, legibilidad y eliminación de números mágicos.

### Reemplazo de factores numéricos en estrategias de descuento
En las estrategias concretas, los porcentajes de descuento estaban expresados como literales directos dentro del cálculo. Los reemplazamos por constantes estáticas con nombres descriptivos y buscables:

En `DescuentoVip.java`, la línea original `return subtotal * 0.85;` pasó a definir primero `private static final double FACTOR_DESCUENTO_VIP = 0.85;` y operar como `return subtotal * FACTOR_DESCUENTO_VIP;`.

En `DescuentoNavidad.java`, sustituimos `return subtotal * 0.80;` por la constante `FACTOR_DESCUENTO_NAVIDAD = 0.80`.

En `DescuentoBlackFriday.java`, convertimos el cálculo `return subtotal * 0.70;` en `return subtotal * FACTOR_DESCUENTO_BLACK_FRIDAY = 0.70;`.

### Verificación de nombres y comentarios en el resto de paquetes
Comprobamos que las clases representen sustantivos claros (`Pedido`, `Configuracion`, `Cobrador`, `PagoTarjeta`) y los métodos utilicen verbos que revelen su intención (`procesar`, `aplicar`, `cobrar`, `getUrlPagos`). 

Asimismo, depuramos los comentarios redundantes: eliminamos notas que solo repetían lo que el código ya expresaba de forma evidente, conservando únicamente explicaciones de arquitectura cuando resultaba indispensable (como la justificación de la inyección de dependencias en `Pedido.java`).

---

## Violaciones intencionales detectadas en `PedidoRigido.java`

Por diseño pedagógico del laboratorio, la clase `PedidoRigido.java` se mantuvo sin modificaciones como testigo de la Fase 1. En ella encontramos varias violaciones claras a los estándares:

Presenta números mágicos sin nombrar (`0.85` y `0.80`), cadenas mágicas duplicadas (`"vip"`, `"navidad"`, `"tarjeta"`, etc.), comentarios que describen lo obvio como `// sin descuento` en ramas vacías, y un método sobrecargado de responsabilidades que mezcla lógica de cálculo con selección de medios de pago.

---

## Reflexión: automatizar el formato para enfocarse en el diseño

Debatir sobre tabulaciones, espacios alrededor de operadores o la posición de las llaves en una revisión de código (*Code Review*) suele consumir tiempo y generar discusiones estériles (*bikeshedding*). Cuando el equipo delega esas decisiones a herramientas automatizadas de formateo, se logra un beneficio doble:

Por un lado, el estilo se vuelve uniforme de manera instantánea y mecánica, eliminando preferencias personales. Por otro lado, y más importante aún, la energía mental de los desarrolladores y revisores se redirige a lo que realmente impacta en la calidad del software: evaluar la cohesión de las clases, verificar si los puntos de variación están bien aislados, discutir la testabilidad de los componentes y comprobar el cumplimiento de los principios SOLID.

---

## Herramientas de calidad automatizada (referencia de comandos)

Para integrar este proceso dentro del ciclo de desarrollo o en un pipeline de integración continua, se pueden utilizar las siguientes herramientas:

```bash
# Formatear automáticamente el código con el estándar de Google
java -jar google-java-format.jar --replace src/main/java/pe/empresa/pedidos/**/*.java

# Ejecutar análisis estático para validar reglas de estilo y buenas prácticas
java -jar checkstyle.jar -c google_checks.xml src/main/java/
```

*(Comandos referenciales documentados para su ejecución según los binarios y configuración disponible en el entorno del estudiante).*
