# Fase 5: Estándares de Codificación — Auditoría y Limpieza

## 1. Auditoría y Cambios Aplicados por Archivo

Se realizó una auditoría completa del código desarrollado en las Fases 1 a 4 aplicando los 4 ejes de estándares (McConnell, 2004 — Cap. 11): **nomenclatura, formato, comentarios y organización**.

### Cambios Concretos en Clases de Dominio (Eliminación de Números Mágicos)

| Archivo | Antes (Número Mágico) | Después (Constante Buscable) |
| :--- | :--- | :--- |
| `DescuentoVip.java` | `return subtotal * 0.85;` | `private static final double FACTOR_DESCUENTO_VIP = 0.85;`<br>`return subtotal * FACTOR_DESCUENTO_VIP;` |
| `DescuentoNavidad.java` | `return subtotal * 0.80;` | `private static final double FACTOR_DESCUENTO_NAVIDAD = 0.80;`<br>`return subtotal * FACTOR_DESCUENTO_NAVIDAD;` |
| `DescuentoBlackFriday.java` | `return subtotal * 0.70;` | `private static final double FACTOR_DESCUENTO_BLACK_FRIDAY = 0.70;`<br>`return subtotal * FACTOR_DESCUENTO_BLACK_FRIDAY;` |

### Verificación de Nomenclatura en la Arquitectura
* **Clases como sustantivos:** `Pedido`, `Configuracion`, `Cobrador`, `PagoTarjeta`, `DescuentoVip`.
* **Métodos como verbos/acciones:** `procesar()`, `aplicar()`, `cobrar()`, `getUrlPagos()`, `getUmbralVip()`.
* **Intención explícita:** Parámetros como `subtotal`, `monto`, `metodoPago` y `estrategia` reemplazan nombres genéricos o abreviaturas crípticas.
* **Comentarios limpios:** Se eliminaron comentarios redundantes que describían "el qué" obvio, conservando únicamente explicaciones de decisiones arquitectónicas ("el porqué", como la inyección por constructor).

---

## 2. Declaración de Violaciones en `PedidoRigido.java` (Referencia Histórica)

`PedidoRigido.java` se conserva intencionalmente intacto como evidencia del "código que duele" de la Fase 1. En su análisis se registran las siguientes violaciones a los estándares:

1. **Números mágicos incrustados:** Presencia directa de `0.85` y `0.80` sin nombres simbólicos.
2. **Cadenas mágicas (*Magic Strings*):** Comparaciones literales con `"regular"`, `"vip"`, `"navidad"`, `"tarjeta"`, `"yape"`, `"efectivo"`.
3. **Comentarios redundantes:** Comentarios como `// sin descuento` que redundan sobre un bloque vacío.
4. **Múltiples responsabilidades acopladas:** Violación de organización modular al mezclar lógica de pedido, reglas financieras y protocolos de pago en un solo método.

---

## 3. Reflexión: Formato Automático vs. Discusión de Diseño

> **¿Por qué delegar el formato a una herramienta automática libera energía para discutir diseño?**

Cuando un equipo adopta un formateador automático (como `google-java-format` o `Prettier`):
* **Eliminación del *Bikeshedding*:** Se evitan debates improductivos en las revisiones de código (*Code Reviews*) sobre espacios, tabulaciones, saltos de línea o posición de llaves.
* **Foco en valor arquitectónico:** La energía mental de los desarrolladores y revisores se redirige por completo a lo esencial: cohesión, acoplamiento, cumplimiento de principios SOLID, aislamiento de puntos de variación y diseño de contratos/APIs.
* **Consistencia objetiva:** El estilo deja de ser una opinión individual y pasa a ser una regla automatizada e incuestionable del pipeline de CI/CD.

---

## 4. Comandos de Referencia de Herramientas de Calidad

Para ejecutar formateo y análisis estático automatizado en el entorno local o pipeline de integración continua:

```bash
# Formateo automático de código con Google Java Format
java -jar google-java-format.jar --replace src/main/java/pe/empresa/pedidos/**/*.java

# Verificación de cumplimiento de estándares con Checkstyle
java -jar checkstyle.jar -c google_checks.xml src/main/java/
```

*(Nota: Comandos documentados de forma referencial, pendientes de ejecución en el entorno local del estudiante según disponibilidad de binarios).*
