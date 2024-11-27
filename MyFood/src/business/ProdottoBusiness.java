package business;

import business.result.ProductResult;
import dao.prodotto.ProdottoDAO;
import model.Immagine;
import model.Ingrediente;
import model.Tipologia_Prodotto;
import model.prodotto.composite.IProdotto;
import model.prodotto.composite.Menu;
import model.prodotto.composite.Prodotto_Singolo;
import model.prodotto.factory.ProdottoFactory;

import java.util.ArrayList;

public class ProdottoBusiness {
    private static ProdottoBusiness instance;

    private static ProdottoDAO prodottoDao = ProdottoDAO.getInstance();

    private static SessionBusiness session = SessionBusiness.getInstance();
    public static final String PRODOTTO = "PRODOTTO";

    public static boolean disponibile = true;

    private ProdottoBusiness() {
    }

    public static ProdottoBusiness getInstance() {
        if (instance == null) {
            instance = new ProdottoBusiness();
        }
        return instance;
    }

    public static IProdotto getProduct() {
        return (IProdotto) session.getFromSession(PRODOTTO);
    }

    public static void loadProduct(String nome) {
        ArrayList<IProdotto> prodotti = (ArrayList<IProdotto>) session.getFromSession(CatalogoBusiness.CATALOGO);
        for (IProdotto p : prodotti) {
            if (p.getNome().equals(nome.replaceAll("\\<.*?\\>", ""))) {
                session.putInSession(PRODOTTO, p);
                break;
            }
        }
    }

    public static String fillListaInformazioni(Prodotto_Singolo p) {
        String listaInformazioni = "<html><ul>";
        listaInformazioni += "<li> Costo: " + p.getPrezzo() + " €</li>";
        listaInformazioni += "<li> Tipologia Prodotto: " + p.getTipologiaP().getNome() + "</li>";
        if (p.getDisponibilita()) {
            listaInformazioni += "<li> Disponibilità:  Sì </li>";
            disponibile = true;
        } else {
            listaInformazioni += "<li> Disponibilità:  No </li>";
            disponibile = false;
        }
        listaInformazioni += "</ul></html>";
        return listaInformazioni;
    }

    public static String fillListaInformazioni(Menu m) {
        String listaInformazioni = "<html><ul>";
        listaInformazioni += "<li> Costo: " + m.getPrezzo() + " €</li>";
        listaInformazioni += "<li> Tipologia Prodotto:  Menu </li>";
        for (IProdotto p : m.getProdotti()) {
            if (p instanceof Prodotto_Singolo) {
                if (!((Prodotto_Singolo) p).getDisponibilita()) {
                    listaInformazioni += "<li> Disponibilità:  No </li>";
                    disponibile = false;
                    listaInformazioni += "</ul></html>";
                    return listaInformazioni;
                }
                if (p instanceof Menu) {
                    for (IProdotto ps : ((Menu) p).getProdotti()) {
                        if (!((Prodotto_Singolo) ps).getDisponibilita()) {
                            listaInformazioni += "<li> Disponibilità:  No </li>";
                            disponibile = false;
                            listaInformazioni += "</ul></html>";
                            return listaInformazioni;
                        }
                    }
                }
            }

        }
        listaInformazioni += "<li> Disponibilità:  Sì </li>";
        disponibile = true;
        listaInformazioni += "</ul></html>";
        return listaInformazioni;
    }

    public static String fillListaIngredienti(Prodotto_Singolo p) {
        String listaIngredienti = "<html><ul>";
        for (Ingrediente i : p.getIngredienti()) {
            listaIngredienti += "<li>" + i.getNome() + "</li>";

        }
        listaIngredienti += "</ul></html>";
        return listaIngredienti;
    }

    public static String fillListaProdotti(Menu m) {
        String listaProdotti = "<html><ul>";
        for (IProdotto p : m.getProdotti()) {
            listaProdotti += "<li>" + p.getNome() + "</li>";

        }
        listaProdotti += "</ul></html>";
        return listaProdotti;
    }

    public static boolean getDisponibilita() {
        return disponibile;
    }

    public static ProductResult addProdotto(String nome, String tipologia, String listaIng, Object costo, String immagini, Boolean disponibilita) {
        Prodotto_Singolo p = (Prodotto_Singolo) ProdottoFactory.getFactory("prodotto_singolo").creaProdotto();
        ProductResult productResult = new ProductResult();
        if (prodottoDao.findProdottoByNome(nome) != null) {
            productResult.setProductResult(ProductResult.PRODUCT_RESULT.EXIST);
            productResult.setMessage("Esiste già un prodotto con questo nome");
            return productResult;
        }
        p.setNome(nome);
        for (Tipologia_Prodotto tp : CatalogoBusiness.getProductsFilter()) {
            if (tipologia.equals(tp.getNome())) {
                p.setTipologiaP(tp);
            }
        }
        ArrayList<Ingrediente> listaIngredienti = new ArrayList<>();
        String[] ingredienti = listaIng.split(", ");
        for (Ingrediente i : CatalogoBusiness.getIngredientsFilter()) {
            for (String ingrediente : ingredienti) {
                if (ingrediente.equals(i.getNome())) {
                    listaIngredienti.add(i);
                }
            }
        }
        p.setIngredienti(listaIngredienti);
        if (costo instanceof Double) {
            p.setPrezzo(((Double) costo).floatValue());
        } else if (costo instanceof Float) {
            p.setPrezzo((Float) costo);
        }
        p.setDisponibilita(disponibilita);
        if (!immagini.isEmpty()) {
            ArrayList<Immagine> listaImmagini = new ArrayList<>();
            String[] nomiImmagini = immagini.split(", ");
            for (String nomeImmagine : nomiImmagini) {
                Immagine i = new Immagine();
                i.setNomeFile(nomeImmagine);
                listaImmagini.add(i);
            }
            p.setImmagini(listaImmagini);
        }
        prodottoDao.addProdotto(p);
        productResult.setProductResult(ProductResult.PRODUCT_RESULT.ADDED_PRODUCT);
        productResult.setMessage("Prodotto aggiunto con successo!");
        session.removeFromSession(CatalogoBusiness.CATALOGO);
        CatalogoBusiness.loadProducts();
        return productResult;

    }

    public static ProductResult addMenu(String nome, String listaProdotti, Object sconto, String immagini) {
        Menu m = (Menu) ProdottoFactory.getFactory("menu").creaProdotto();
        ProductResult productResult = new ProductResult();
        if (prodottoDao.findProdottoByNome(nome) != null) {
            productResult.setProductResult(ProductResult.PRODUCT_RESULT.EXIST);
            productResult.setMessage("Esiste già un menu con questo nome");
            return productResult;
        }
        m.setNome(nome);
        m.setSconto(((Integer) sconto).floatValue() / 100);
        if (!immagini.isEmpty()) {
            ArrayList<Immagine> listaImmagini = new ArrayList<>();
            String[] nomiImmagini = immagini.split(", ");
            for (String nomeImmagine : nomiImmagini) {
                Immagine i = new Immagine();
                i.setNomeFile(nomeImmagine);
                listaImmagini.add(i);
            }
            m.setImmagini(listaImmagini);
        }
        String[] nomiProdotti = listaProdotti.split(", ");
        for (String nomeProdotto : nomiProdotti) {
            for (IProdotto p : CatalogoBusiness.getProducts()) {
                if (p.getNome().equals(nomeProdotto)) {
                    if (m.addProdotto(p)) {
                        productResult.setProductResult(ProductResult.PRODUCT_RESULT.ERROR);
                        productResult.setMessage("Impossibile aggiungere " + p.getNome() + " al menu!");
                        return productResult;
                    }
                }
            }
        }
        prodottoDao.addProdotto(m);
        productResult.setProductResult(ProductResult.PRODUCT_RESULT.ADDED_PRODUCT);
        productResult.setMessage("Menu aggiunto con successo!");
        session.removeFromSession(CatalogoBusiness.CATALOGO);
        CatalogoBusiness.loadProducts();
        return productResult;
    }
}

