package business.facade.bridge;

import model.Ordine;

public interface PdfAPI {

    public void createPDFOrdine(Ordine ordine, String dest);

    public String getDest();
}
