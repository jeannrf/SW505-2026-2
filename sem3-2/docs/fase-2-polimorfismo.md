# Fase 2: Refactor a Polimorfismo — Aislamiento del Cambio

## Impacto real al extender el sistema: el caso de Plin

El ejercicio práctico de incorporar `PagoPlin` como nuevo medio de cobro arroja una métrica contundente:

- **Archivos nuevos creados:** 1 (`PagoPlin.java`).
- **Archivos existentes modificados:** 0. Ni la interfaz `MetodoPago`, ni las clases previas (`PagoTarjeta`, `PagoYape`, `PagoEfectivo`), ni el orquestador `Cobrador` necesitaron un solo ajuste.

Este resultado contrasta directamente con el modelo inicial de `PedidoRigido`, donde soportar un medio adicional requería abrir la clase, localizar el bloque condicional e intercalar otra rama `else if`.

---

## Por qué esto demuestra un verdadero aislamiento del cambio

En el diseño rígido con condicionales sobre strings, cualquier cambio en los medios de pago ponía en riesgo el funcionamiento del pedido entero. Había un acoplamiento directo entre la orden y cada implementación concreta de pago. Si alguien cometía un error de sintaxis o alteraba sin querer otra rama del `if`, rompía flujos consolidados como tarjeta o efectivo.

Al aplicar polimorfismo mediante la interfaz `MetodoPago`, logramos cumplir el Principio Abierto/Cerrado (OCP): el sistema queda abierto para recibir nuevas formas de pago mediante nuevas clases, pero cerrado a la modificación de las que ya están probadas y funcionando.

La clase consumidora (sea `Cobrador` o más adelante `Pedido`) solo conoce el contrato general: sabe que cualquier `MetodoPago` tiene un método `procesar(monto)`. No necesita saber si el cobro se hace con código QR, pasarela bancaria o dinero en mano. El radio de impacto ante nuevas implementaciones se reduce exactamente a cero sobre el código preexistente.

> *"Agregar un nuevo método de pago costó 1 archivo nuevo, 0 archivos modificados. Ese es el ahorro del aislamiento."*

---

## Equivalente conceptual en Python

Para ilustrar este mismo patrón en Python, se recurre al módulo estándar `abc` para definir una clase base abstracta que actúe como interfaz:

```python
from abc import ABC, abstractmethod

class MetodoPago(ABC):
    @abstractmethod
    def procesar(self, monto: float) -> None:
        """Contrato abstracto para ejecutar el cobro."""
        pass

    @abstractmethod
    def nombre(self) -> str:
        """Nombre descriptivo del medio de pago."""
        pass

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

El principio se mantiene idéntico: el código cliente interactúa con la abstracción `MetodoPago` sin acoplarse a si se trata de Yape, Plin o una futura billetera digital.
