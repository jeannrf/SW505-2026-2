package pe.empresa.pedidos.notificacion;

// PUNTO DE VARIACIÓN: Mecanismo de notificación (email, SMS, WhatsApp). Se aísla mediante polimorfismo para permitir nuevos canales sin modificar el flujo de pedidos.
public class NotificadorWhatsapp implements Notificador {

    @Override
    public void notificar(String destinatario, String mensaje) {
        System.out.println("Enviando WhatsApp a " + destinatario + ": " + mensaje);
    }
}
