package model.prodotto.factory;

import model.prodotto.composite.IProdotto;
import model.prodotto.composite.Prodotto_Singolo;

public class ProdottoSingoloFactory implements AbstractProdottoFactory {
    @Override
    public IProdotto creaProdotto() {
        return new Prodotto_Singolo();
    }
}