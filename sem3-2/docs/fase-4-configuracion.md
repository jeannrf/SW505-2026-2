# Fase 4: Parámetros de Configuración — Externalización de Variaciones

## El problema de las constantes estáticas en el código

Un antipatrón recurrente al construir servicios es definir valores dependientes del entorno directamente dentro de las clases de Java, como en este ejemplo representativo:

```java
// Antipatrón: constantes fijas dependientes del entorno incrustadas en código
public class ServicioPago {
    private static final String URL_PASARELA = "https://sandbox-pagos.empresa.pe/api";
    private static final double UMBRAL_VIP = 500.0;
    private static final int TIMEOUT_SEGUNDOS = 30;
    // ...
}
```

A simple vista parece ordenado porque usa constantes con nombre, pero introduce una rigidez crítica: cualquier cambio en la infraestructura o en las reglas de negocio exige alterar el código fuente.

---

## Cómo impacta a las pruebas de QA: antes vs. después

Si el equipo de control de calidad (QA) necesita validar cómo se comporta el flujo cuando el umbral para clientes VIP baja de S/ 500 a S/ 300, el escenario cambia por completo según el diseño utilizado.

En el escenario previo con valores fijos en el código, QA no podía realizar esa prueba por su cuenta. Tenían que solicitar a un desarrollador que modificara la constante en Java, creara una nueva rama, recompilara el proyecto y desplegara un nuevo binario solo para cambiar un número. Este ciclo genera fricción, consume tiempo y aumenta el riesgo de introducir cambios no deseados en producción.

Con la configuración externalizada en `application.properties` (y leída mediante `Configuracion.java`), el binario compilado permanece intacto. El equipo de QA simplemente edita el archivo de propiedades en el servidor de pruebas o inyecta una variable de entorno al levantar el servicio. La misma versión del aplicativo sirve para pruebas, homologación y producción, cumpliendo con las buenas prácticas de entrega continua (*Twelve-Factor App*).

---

## Riesgos de seguridad al hardcodear URLs y credenciales sensibles

Dejar URLs privadas, tokens de pasarelas o credenciales de bases de datos dentro de archivos `.java` acarrea peligros serios para cualquier equipo de software:

El historial de Git conserva todo de forma permanente. Aunque más adelante se borre una clave con un nuevo commit, esa clave seguirá visible en los registros anteriores del repositorio, accesible para cualquiera con permisos de lectura o expuesta públicamente si el proyecto se comparte por error.

Además, cuando un secreto se ve comprometido, su rotación se vuelve traumática: obliga a realizar un ciclo completo de compilación, empaquetado y despliegue de emergencia, en lugar de actualizar una clave de forma inmediata en un gestor de secretos o en variables de entorno.

Existe también el peligro de cruce accidental de entornos, donde una prueba ejecutada en la máquina de un desarrollador termine llamando a la pasarela real de producción porque la URL apuntaba al servicio definitivo.

---

## Nota conceptual: el equivalente en el ecosistema Python

En aplicaciones desarrolladas con Python, este mismo aislamiento se implementa habitualmente combinando variables de entorno del sistema operativo con la librería `python-dotenv`:

```python
import os
from dotenv import load_dotenv

# Lee las variables definidas en un archivo .env local
load_dotenv()

url_pasarela = os.getenv("PAGOS_URL", "https://sandbox-pagos.empresa.pe/api")
umbral_vip = float(os.getenv("PAGOS_UMBRAL_VIP", "500.0"))
timeout_segundos = int(os.getenv("PAGOS_TIMEOUT_SEGUNDOS", "30"))
```

La lógica es la misma que logramos con `Properties` en Java: el código consume configuraciones externas sin asumir valores fijos en tiempo de compilación.
