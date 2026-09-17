# Fase 2: Refactor a Polimorfismo — Aislamiento del Cambio

## 1. Métrica de Impacto ante la Extensión (Caso: `PagoPlin`)

Al incorporar un nuevo método de pago (**Plin**):

* **Archivos nuevos creados:** `1` (`PagoPlin.java`)
* **Archivos existentes modificados:** `0` (Ni `MetodoPago.java`, ni `PagoTarjeta.java`, ni `PagoYape.java`, ni `PagoEfectivo.java`, ni `Cobrador.java`, ni `PedidoRigido.java`).

---

## 2. Demostración del "Aislamiento del Cambio" vs. `PedidoRigido`

### Enfoque Rígido (`PedidoRigido` con `if / else`)
* **Alto acoplamiento:** Para soportar un nuevo medio de pago era obligatorio abrir el archivo `PedidoRigido.java`, modificar el método `calcularTotal` e intercalar un nuevo bloque condicional `else if (metodoPago.equals("plin"))`.
* **Riesgo de regresión:** Tocar código en producción aumentaba la probabilidad de romper los flujos existentes (efectivo, tarjeta, etc.) y obligaba a recompilar y re-probar toda la clase central de pedidos.
* **Violación de OCP:** La clase no estaba cerrada a la modificación ni abierta a la extensión.

### Enfoque Polimórfico (`MetodoPago` + Polimorfismo)
* **Principio Abierto/Cerrado (OCP):** El sistema está **abierto a la extensión** (basta con crear una nueva clase que implemente el contrato `MetodoPago`) y **cerrado a la modificación** (las clases existentes no sufren ningún cambio).
* **Aislamiento del cambio (*blast radius = 0*):** La variación queda contenida exclusivamente en su propio archivo (`PagoPlin.java`). El componente consumidor (`Cobrador` u orquestador) interactúa únicamente con la abstracción `MetodoPago` sin conocer los detalles de implementación de cada pasarela.
* **Costo predecible:** El costo de añadir $N$ métodos de pago escala linealmente en nuevos archivos independientes, sin degradar ni complejizar el código base existente.

> *"Agregar un nuevo método de pago costó 1 archivo nuevo, 0 archivos modificados. Ese es el ahorro del aislamiento."*

---

## 3. Equivalente Conceptual en Python (`abc`)

A modo ilustrativo, el equivalente conceptual en Python utiliza una clase base abstracta (`ABC`) y el decorador `@abstractmethod`:

```python
from abc import ABC, abstractmethod

class MetodoPago(ABC):
    @abstractmethod
    def procesar(self, monto: float) -> None:
        """Procesa el cobro por el monto especificado."""
        ...

    @abstractmethod
    def nombre(self) -> str:
        """Retorna el nombre descriptivo del método de pago."""
        ...

class PagoYape(MetodoPago):
    def procesar(self, monto: float) -> None:
        print(f"Cobrando S/ {monto} con Yape")

    def nombre(self) -> str:
        return "Yape"

class PagoPlin(MetodoPago):
    def procesar(self, monto: float) -> None:
        print(f"Cobrando S/ {monto} con Plin")

    def nombre(self) -> str:
        return "Plin"
```
