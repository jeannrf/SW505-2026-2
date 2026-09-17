# Fase 6a: Reto Integrador — Notificaciones Polimórficas

## Por qué resolver las notificaciones con polimorfismo y no con `if/else`

Cuando el sistema necesita enviar confirmaciones por distintos canales (correo electrónico, SMS o WhatsApp), recurrir a una estructura condicional sobre cadenas dentro de `Pedido` habría repetido los mismos vicios que sufrimos en la Fase 1.

Cada canal de comunicación tiene requerimientos técnicos muy distintos: el correo suele requerir protocolos SMTP o clientes HTTP especializados, el SMS interactúa con módems o APIs de mensajería (como Twilio), y WhatsApp depende de servicios como la Cloud API de Meta. Si resolviéramos esto con `if/else` dentro de `Pedido`, obligaríamos a la clase de pedidos a importar librerías de red, gestionar tokens y conocer detalles de transporte que nada tienen que ver con su responsabilidad.

Al definir la interfaz `Notificador`, convertimos este punto de variación en un contrato polimórfico. Cada implementación (`NotificadorEmail`, `NotificadorSms`, `NotificadorWhatsapp`) encapsula su propia lógica y dependencias. `Pedido` únicamente sabe que puede invocar `notificar(destinatario, mensaje)` sin preocuparse por la tecnología que esté por detrás.

---

## Decisión de diseño al integrar el colaborador en `Pedido`

En `Pedido.java` incorporamos `Notificador` como tercer colaborador inyectado a través del constructor. Dentro del método `procesar(subtotal)`, una vez aplicado el descuento y ejecutado el cobro, llamamos a `notificador.notificar("cliente@empresa.pe", ...)`.

Decidimos colocar un destinatario representativo en este método para mantener la firma `procesar(double subtotal)` compatible con las fases anteriores del laboratorio, demostrando que el pedido delega el aviso de confirmación inmediatamente después de asegurar el cobro.

---

## Qué implicaría agregar "Notificaciones Push" en el futuro

Si el negocio decide lanzar una aplicación móvil y requiere enviar notificaciones Push (por ejemplo mediante Firebase Cloud Messaging), el impacto en el código es mínimo y perfectamente predecible:

- Se crea un único archivo nuevo: `NotificadorPush.java`, implementando la interfaz `Notificador`.
- No se modifica ningún archivo existente: ni la interfaz `Notificador`, ni `Pedido.java`, ni las clases de correo, SMS o WhatsApp sufren alteraciones.

La única modificación se dará en la capa de composición (`Main.java` o el punto de inicio de la app), donde simplemente pasaremos `new NotificadorPush()` al instanciar el pedido. Esto confirma que el principio Abierto/Cerrado se mantiene firme.
