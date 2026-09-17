# Fase 3: Strategy + Inyección de Dependencias

## 1. Beneficios de la Inyección de Dependencias en `Pedido`

Al recibir sus colaboradores (`EstrategiaDescuento` y `MetodoPago`) a través del constructor en lugar de instanciarlos con `new` internamente:

* **Bajo acoplamiento:** `Pedido` depende de contratos abstractos e interfaces, no de clases concretas (cumpliendo el *Principio de Inversión de Dependencias - DIP*).
* **Alta testabilidad:** La clase puede aislarse completamente para pruebas unitarias rápidas y deterministas.
* **Separación de responsabilidades (SRP):** `Pedido` se encarga exclusivamente de la orquestación del flujo de procesamiento, delegando la creación y configuración a la capa de composición (`Main` o contenedor IoC).

---

## 2. Estrategia de Testing sin Pasarelas ni Bases de Datos Reales

Gracias a la inyección de dependencias por constructor, `Pedido` se puede testear utilizando **Dobles de Prueba (*Test Doubles*)**, como *Mocks*, *Stubs* o *Spies*:

```java
// Doble de prueba en memoria sin llamadas externas
class MetodoPagoMock implements MetodoPago {
    public double montoRecibido = 0;
    public boolean fueLlamado = false;

    @Override
    public void procesar(double monto) {
        this.montoRecibido = monto;
        this.fueLlamado = true;
    }

    @Override
    public String nombre() { return "Mock"; }
}
```

* **Prueba unitaria aislada:** Se inyecta `new MetodoPagoMock()` y `new DescuentoVip()` en `Pedido`.
* **Verificación directa:** Se ejecuta `pedido.procesar(100.0)` y se valida que retorne `85.0` y que `mock.montoRecibido == 85.0`, sin requerir conexiones de red, APIs de terceros ni persistencia en base de datos.

---

## 3. Diferencia entre Polimorfismo Simple y Patrón Strategy

| Criterio | Polimorfismo Simple (`MetodoPago`) | Patrón Strategy (`EstrategiaDescuento`) |
| :--- | :--- | :--- |
| **Propósito principal** | Representar diferentes variantes de un concepto o adaptador externo (tarjeta, Yape, efectivo). | Encapsular familias de **algoritmos y reglas de negocio** intercambiables. |
| **Naturaleza del objeto** | El objeto representa un canal, mecanismo o ejecutor de pago. | **El objeto completo representa un algoritmo** (una fórmula de cálculo de descuento). |
| **Rol en el contexto** | Ejecuta una acción final del flujo. | Altera la lógica interna de cómputo del contexto (`Pedido`) según la regla inyectada. |

> *"En Strategy, cada clase concreta encapsula un algoritmo completo y su variación de negocio, permitiendo cambiar la lógica de cálculo sin alterar el contexto que lo consume."*
