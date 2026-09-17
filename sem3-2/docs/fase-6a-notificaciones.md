# Fase 6a: Reto Integrador — Notificaciones Polimórficas

## 1. Justificación del Polimorfismo vs. `if/else` en Notificaciones

El punto de variación de notificaciones se resolvió mediante **polimorfismo e inversión de dependencias** debido a:

* **Principio de Responsabilidad Única (SRP):** `Pedido` solo debe preocuparse por coordinar el ciclo de vida del pedido, no por conocer los detalles técnicos, APIs o protocolos de envío (SMTP para correo, gateway SMS o API de Meta para WhatsApp).
* **Principio Abierto/Cerrado (OCP):** Con un enfoque `if/else`, agregar un canal implicaría abrir y modificar `Pedido.java`, introduciendo dependencias directas y riesgo de regresión en el flujo de ventas. Con polimorfismo, el sistema está abierto a nuevos canales sin tocar la lógica existente.
* **Aislamiento de fallos y dependencias externas:** Cada canal (`NotificadorEmail`, `NotificadorSms`, `NotificadorWhatsapp`) gestiona sus propias dependencias y librerías sin contaminar el dominio central.

---

## 2. Decisión de Diseño en `Pedido.java`

* Se inyectó `Notificador` en el constructor de `Pedido` junto con `EstrategiaDescuento` y `MetodoPago`.
* En el método `procesar(double subtotal)`, tras calcular el total con descuento y ejecutar el cobro, se dispara la notificación de confirmación (`notificador.notificar("cliente@empresa.pe", "Pedido confirmado por un total de S/ " + total)`).
* El destinatario de ejemplo simula el correo/teléfono del cliente titular de la orden, desacoplando completamente a `Pedido` del canal real empleado.

---

## 3. Impacto ante la Extensión: Caso "Notificación Push"

Si el negocio solicita habilitar **Notificaciones Push** (por ejemplo, vía Firebase Cloud Messaging):

* **Archivos nuevos creados:** `1` (`NotificadorPush.java` implementando el contrato `Notificador`).
* **Archivos existentes modificados:** `0` (La interfaz `Notificador`, la clase de negocio `Pedido`, y los notificadores existentes `Email`, `SMS`, `WhatsApp` permanecen 100% inalterados).

> *El cambio se limita exclusivamente a crear la nueva clase y pasarla por inyección en el punto de composición (`Main`), manteniendo el blast radius en 0.*
