package model.prodotto.factory;

public class ProdottoFactory{

    public static AbstractProdottoFactory getFactory(String tipo) {
        switch (tipo) {
            case "menu":
                return new MenuFactory();
            case "prodotto_singolo":
                return new ProdottoSingoloFactory();
        }
        return null;
    }
}