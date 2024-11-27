package business.facade.bridge;

import business.facade.bridge.PdfAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {

    private static Properties prop;

    private PdfAPI pdfAPI;
    private MimeMessage message;
    private Transport transport;
    private Session session;

    public MailSender(PdfAPI pdfAPI) throws Exception {
        this.pdfAPI= pdfAPI;
        prop = new Properties();
        FileInputStream ip = new FileInputStream("src/config.properties");
        prop.load(ip);
        if (ip != null) {
            ip.close();
        }
        session = Session.getDefaultInstance(prop);
        transport = session.getTransport("smtp");
        System.out.println("Connessione al mail server");
        transport.connect(prop.getProperty("mail.smtp.host"), prop.getProperty("mail.smtp.user"),
                prop.getProperty("mail.smtp.password"));
        System.out.println("Connesso...");
    }


    // email con allegato la lista di prodotti dell'ordine
    public void sendListaProdotti(String email) {
        message = new MimeMessage(session);
        try {
            // Impostazione del destinatario
            InternetAddress toAddress = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            // Impostazione del mittente
            message.setFrom(new InternetAddress(prop.getProperty("mail.smtp.user"), "MyFood"));

            // Impostazione dell'oggetto del messaggio
            message.setSubject("Scontrino MyFood del tuo ordine");

            // aggiunta di un allegato
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Questo è lo scontrino del tuo ordine, dirigiti in cassa per pagare!");
            MimeBodyPart attachment = new MimeBodyPart();

            // allego il file
            attachment.attachFile(pdfAPI.getDest());
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(attachment);
            message.setContent(emailContent);

            //invio la mail
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail inviata!!");
            transport.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void sendAvvisoOrdinePronto(String email) {
        message = new MimeMessage(session);
        try {
            // Impostazione del destinatario
            InternetAddress toAddress = new InternetAddress(email);
            message.addRecipient(Message.RecipientType.TO, toAddress);

            // Impostazione del mittente
            message.setFrom(new InternetAddress(prop.getProperty("mail.smtp.user"), "MyFood"));

            // Impostazione dell'oggetto del messaggio
            message.setSubject("Il tuo ordine MyFood è pronto!");

            message.setText("Il tuo ordine MyFood è pronto, dirigiti in cassa per il ritiro!");


            //invio la mail
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail inviata!!");
            transport.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

}