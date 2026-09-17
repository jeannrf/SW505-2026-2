# Sistema de Procesamiento de Pedidos — SW505 (Semana 3-2)

Proyecto modular para el procesamiento desacoplado de pedidos aplicando anticipación al cambio y principios SOLID:

- `pe.empresa.pedidos`: Punto de entrada (`Main`) y referencia histórica (`PedidoRigido`).
- `pe.empresa.pedidos.domain`: Entidad central `Pedido` y estrategias de cálculo de descuentos (`EstrategiaDescuento`).
- `pe.empresa.pedidos.pago`: Contrato e implementaciones polimórficas de pasarelas y métodos de pago (`MetodoPago`).
- `pe.empresa.pedidos.notificacion`: Aislamiento polimórfico de canales de confirmación (`Notificador`).
- `pe.empresa.pedidos.impuesto`: Cálculo de impuestos por región mediante el patrón Strategy (`CalculadoraImpuesto`).
- `pe.empresa.pedidos.config`: Carga tipada de propiedades externas (`Configuracion`) desde `resources/application.properties`.

