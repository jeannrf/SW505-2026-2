# Fase 4: Parámetros de Configuración — Externalización de Variaciones

## 1. El Antipatrón: Constantes Hardcodeadas en Código Fuente

Antes de aislar este punto de variación, los valores dependientes del entorno solían definirse como constantes estáticas dentro de clases de servicio:

```java
// Antipatrón: Valores dependientes del entorno acoplados al código
public class ServicioPago {
    private static final String URL_PASARELA = "https://sandbox-pagos.empresa.pe/api";
    private static final double UMBRAL_VIP = 500.0;
    private static final int TIMEOUT_SEGUNDOS = 30;
    // ...
}
```

---

## 2. Análisis: Pruebas de QA sin Recompilación

### Escenario "Antes" (Valores Hardcodeados)
* Si el equipo de QA necesita probar cómo responde el sistema cuando el umbral VIP es `S/ 300.0` en lugar de `S/ 500.0`, no puede hacerlo directamente.
* Obliga a modificar el código fuente, generar un nuevo commit, recompilar el proyecto y volver a desplegar el artefacto, consumiendo tiempo y generando riesgo de inconsistencias.

### Escenario "Después" (Configuración Externa)
* El comportamiento se ajusta modificando únicamente `application.properties` (o inyectando variables de entorno en el servidor de pruebas).
* El mismo binario compilado sirve de forma idéntica para entornos de desarrollo, pruebas (QA), pre-producción y producción (*Twelve-Factor App*).

---

## 3. Riesgos de Hardcodear URLs y Credenciales Sensibles

* **Exposición en Control de Versiones (Git):** Al commitear secretos (API Keys, contraseñas, URLs privadas), estos quedan grabados en el historial de Git de forma permanente, siendo vulnerables ante filtraciones o repositorios compartidos.
* **Incapacidad de Rotación Ágil de Secretos:** Si un secreto se ve comprometido, cambiarlo exige recompilar y desplegar toda la aplicación en lugar de simplemente actualizar el almacén seguro de secretos (Vault, Secrets Manager).
* **Riesgo de Cruce de Entornos:** Puede provocar accidentes catastróficos, como un desarrollador ejecutando transacciones reales contra pasarelas de producción desde su entorno local de pruebas.

---

## 4. Nota Conceptual: Equivalente en Python (`python-dotenv`)

En el ecosistema Python, la externalización equivalente se realiza mediante variables de entorno con `os.getenv` y la biblioteca `python-dotenv`:

```python
import os
from dotenv import load_dotenv

# Carga variables desde el archivo .env
load_dotenv()

URL_PAGOS = os.getenv("PAGOS_URL", "https://sandbox-pagos.empresa.pe/api")
UMBRAL_VIP = float(os.getenv("PAGOS_UMBRAL_VIP", "500.0"))
TIMEOUT_SEGUNDOS = int(os.getenv("PAGOS_TIMEOUT_SEGUNDOS", "30"))
```
