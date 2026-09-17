package pe.empresa.pedidos.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion {

    private final Properties props = new Properties();

    public Configuracion() throws IOException {
        try (InputStream in = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("No se pudo encontrar el archivo application.properties en el classpath");
            }
            props.load(in);
        }
    }

    public String getUrlPagos() {
        return props.getProperty("pagos.url");
    }

    public double getUmbralVip() {
        return Double.parseDouble(props.getProperty("pagos.umbral.vip"));
    }

    public int getTimeoutSegundos() {
        return Integer.parseInt(props.getProperty("pagos.timeout.segundos"));
    }
}
