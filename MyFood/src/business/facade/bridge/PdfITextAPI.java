package business.facade.bridge;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.pdf.PdfWriter;
import model.Ordine;
import model.prodotto.composite.IProdotto;

import java.io.FileOutputStream;

public class PdfITextAPI implements PdfAPI {

    private String dest;


    public void createPDFOrdine(Ordine ordine, String dest) {
        this.dest= dest;
        try {
            // Creazione del documento PDF
            Document document = new Document();
            System.out.println("Compongo il PDF...");
            // Creazione del PdfWriter per scrivere il file PDF
            PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Apertura del documento
            document.open();

            Image icon = Image.getInstance(System.getProperty("user.dir") + "\\src\\image\\logo\\logo.jpg");
            icon.scaleToFit(200, 100); // Scala l'immagine per adattarla al documento
            icon.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(icon);

            Paragraph emptySpace = new Paragraph();
            emptySpace.setSpacingBefore(20f); // Aggiunge 20 unità di spazio prima del paragrafo
            document.add(emptySpace);

            // Aggiunta delle informazioni dell'ordine al documento PDF
            document.add(new Paragraph("Numero Ordine: " + ordine.getIdOrdine()));
            document.add(new Paragraph("Cliente: " + ordine.getCliente().getNome() + " " +
                    ordine.getCliente().getCognome() + " (" + ordine.getCliente().getEmail() + ")"));
            document.add(new Paragraph("Data: " + ordine.getData().toString()));
            document.add(new Paragraph("Sede: " + ordine.getCucina().getSede()));

            document.add(new Paragraph("Lista Prodotti:"));
            List productList = new List();
            for (IProdotto prodotto : ordine.getProdotti()) {
                productList.add(new ListItem(prodotto.getNome() + "  " + prodotto.getPrezzo() + " €"));
            }
            document.add(productList);

            document.add(emptySpace);

            Paragraph totale =new Paragraph("Totale:  " + ordine.getTotale() + " €");
            totale.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(totale);

            Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(255, 165, 0)); // Imposta il colore del font
            Paragraph info = new Paragraph("La presente lista dovrà essere esibita in cassa per procedere al pagamento dell'ordine", redFont);
            info.setAlignment(Paragraph.ALIGN_CENTER);

            emptySpace.setSpacingBefore(60f);
            document.add(emptySpace);
            document.add(info);
            // Chiusura del documento
            document.close();
            System.out.println("PDF Creato");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDest() {
        return dest;
    }
}
