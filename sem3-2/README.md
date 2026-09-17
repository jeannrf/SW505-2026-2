# Sistema de Pedidos — Anticipación al Cambio (SW505)

Estructura modular del proyecto organizada según la separación de responsabilidades y el aislamiento de puntos de variación:

```text
src/
└── main/
    ├── java/pe/empresa/pedidos/
    │   ├── Main.java              # Composición del flujo e Inyección de Dependencias
    │   ├── PedidoRigido.java      # Referencia histórica inicial (Fase 1)
    │   ├── domain/                # Entidad Pedido y Strategy de descuentos
    │   ├── pago/                  # Polimorfismo de métodos de pago (Tarjeta, Yape, Plin, Efectivo)
    │   ├── notificacion/          # Canales de notificación polimórficos (Email, SMS, WhatsApp)
    │   ├── impuesto/              # Strategy de cálculo tributario por región (Lima, Arequipa, Selva)
    │   └── config/                # Lectura tipada de configuración con java.util.Properties
    └── resources/
        └── application.properties # Valores de entorno y tasas promocionales externalizadas
```
