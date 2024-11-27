package business.facade;

import business.facade.bridge.MailSender;
import business.facade.bridge.PdfAPI;
import business.facade.bridge.PdfITextAPI;
import model.Ordine;
import model.utente.Cliente;

import java.util.ArrayList;

import static business.OrdineBusiness.getOrdiniCliente;

public class PdfMailFacade {

    private static PdfAPI pdfAPI=new PdfITextAPI();


    public static void inviaMailPdf(ArrayList<Ordine> ordini, Cliente cliente){
        System.out.println("Verifico Ordine...");
        Ordine ordine = ordini.get(ordini.size() - 1);
        pdfAPI.createPDFOrdine(ordine, System.getProperty("user.dir") + "\\src\\pdf\\Ordine_Numero_" + ordine.getIdOrdine().toString() + ".pdf");
        ordine = getOrdiniCliente().get(getOrdiniCliente().size() - 1);
        try {
            MailSender mailSender = new MailSender(pdfAPI);
            mailSender.sendListaProdotti(cliente.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inviaMailPronto(String email){
        try {
            MailSender mailSender = new MailSender(pdfAPI);
            mailSender.sendAvvisoOrdinePronto(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
