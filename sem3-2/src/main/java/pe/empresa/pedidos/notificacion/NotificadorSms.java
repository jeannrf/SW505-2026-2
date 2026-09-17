package pe.empresa.pedidos.notificacion;

// PUNTO DE VARIACIÓN: Mecanismo de notificación (email, SMS, WhatsApp). Se aísla mediante polimorfismo para permitir nuevos canales sin modificar el flujo de pedidos.
public class NotificadorSms implements Notificador {

    @Override
    public void notificar(String destinatario, String mensaje) {
        System.out.println("Enviando SMS a " + destinatario + ": " + mensaje);
    }
}
