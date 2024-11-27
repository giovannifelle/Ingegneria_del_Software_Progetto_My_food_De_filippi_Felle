package business;

import business.result.ProductResult;
import dao.prodotto.ProdottoDAO;
import model.prodotto.composite.IProdotto;
import model.utente.Amministratore;

public class AmministratoreBusiness {
    private static AmministratoreBusiness instance;
    private static SessionBusiness session = SessionBusiness.getInstance();
    private Amministratore admin = (Amministratore) LoginBusiness.getInSessionAdmin();
    private ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();

    private AmministratoreBusiness() {
    }

    public static AmministratoreBusiness getInstance() {
        if (instance == null) {
            instance = new AmministratoreBusiness();
        }
        return instance;
    }

    public ProductResult removeProduct(IProdotto prodotto){
        ProductResult productResult= new ProductResult();
        if(prodotto == null){
            productResult.setProductResult(ProductResult.PRODUCT_RESULT.ERROR);
            productResult.setMessage("Impossibile rimuovere il prodotto!");
            return productResult;
        }
        prodottoDAO.removeProdotto(prodotto.getId());
        productResult.setProductResult(ProductResult.PRODUCT_RESULT.REMOVED_PRODUCT);
        productResult.setMessage("Prodotto rimosso con successo!");
        SessionBusiness.getInstance().removeFromSession(ProdottoBusiness.PRODOTTO);
        SessionBusiness.getInstance().removeFromSession(CatalogoBusiness.CATALOGO);
        CatalogoBusiness.loadProducts();
        OrdineBusiness.reloadOrdini();
        return productResult;
    }



    public ProductResult updateProduct(IProdotto prodotto) {
        ProductResult productResult = new ProductResult();
        if(prodotto == null){
            productResult.setProductResult(ProductResult.PRODUCT_RESULT.ERROR);
            productResult.setMessage("Impossibile rimuovere il prodotto!");
            return productResult;
        }
        prodottoDAO.updateProdotto(prodotto);
        SessionBusiness.getInstance().removeFromSession(ProdottoBusiness.PRODOTTO);
        SessionBusiness.getInstance().removeFromSession(CatalogoBusiness.CATALOGO);
        CatalogoBusiness.loadProducts();
        productResult.setProductResult(ProductResult.PRODUCT_RESULT.UPDATED_PRODUCT);
        productResult.setMessage("Prodotto modificato con successo!");
        return productResult;
    }
}
