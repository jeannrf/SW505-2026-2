# Fase 3: Strategy + Inyección de Dependencias

## Qué gana `Pedido` al no instanciar sus propios colaboradores

Cuando `Pedido` dejaba de usar `new` internamente para crear sus dependencias y pasa a recibirlas por constructor, logramos desacoplar por completo la lógica del pedido de sus implementaciones concretas. 

En primer lugar, `Pedido` ya no está atado a clases fijas como `DescuentoVip` o `PagoYape`, sino que colabora únicamente a través de contratos (`EstrategiaDescuento` y `MetodoPago`), cumpliendo con el Principio de Inversión de Dependencias (DIP). 

En segundo lugar, separamos las responsabilidades con claridad: la clase `Pedido` se concentra en coordinar el flujo comercial (aplicar descuento, procesar pago y devolver el total), dejando que la decisión de qué estrategia o qué pasarela utilizar pertenezca a quien configura el sistema (por ejemplo, el método `main` o un contenedor de dependencias). Esto hace que cambiar una política de descuento sea tan sencillo como pasar una instancia distinta al construir el objeto.

---

## Cómo testear `Pedido` de forma aislada mediante dobles de prueba

Uno de los mayores beneficios de la inyección de dependencias es la testabilidad. En un entorno real, procesar un pago implicaría conectarse a un servicio bancario externo o registrar movimientos en una base de datos, lo que volvería las pruebas lentas, frágiles y dependientes de la red.

Con la estructura actual, podemos evaluar el comportamiento de `Pedido` usando un doble de prueba (*test double* o *mock* en memoria):

```java
class MetodoPagoFalso implements MetodoPago {
    double ultimoMontoCobrado = 0.0;
    boolean fueLlamado = false;

    @Override
    public void procesar(double monto) {
        this.ultimoMontoCobrado = monto;
        this.fueLlamado = true;
    }

    @Override
    public String nombre() {
        return "Prueba";
    }
}
```

Para una prueba unitaria, basta instanciar `new Pedido(new DescuentoVip(), new MetodoPagoFalso())` y ejecutar `pedido.procesar(100.0)`. Podemos comprobar de inmediato que el método retorna `85.0` y que el cobrador simulado recibió exactamente `85.0`, ejecutándose en milisegundos y sin tocar infraestructura externa.

---

## Diferencia entre polimorfismo simple y el patrón Strategy

Aunque ambos mecanismos se apoyan en interfaces y métodos sobreescritos, su intención de diseño es diferente:

En el caso de `MetodoPago` (Fase 2), estamos ante un polimorfismo orientado a modelar distintos ejecutores o canales de una acción externa (tarjeta, efectivo, billetera móvil). Cada clase representa un medio o destino de cobro.

En cambio, con `EstrategiaDescuento` (Fase 3), aplicamos el patrón **Strategy** porque **el objeto completo representa y encapsula un algoritmo de negocio intercambiable**. No modelamos una entidad física o un servicio externo, sino una regla de cálculo matemático (`aplicar(subtotal)`). 

El contexto `Pedido` no conoce la fórmula ni los porcentajes; simplemente delega la ejecución de la regla al algoritmo que lleva consigo el objeto inyectado. Esto permite alternar entre `DescuentoVip`, `DescuentoNavidad` o `DescuentoBlackFriday` en caliente, tratando a cada algoritmo como una pieza intercambiable de la aplicación.
