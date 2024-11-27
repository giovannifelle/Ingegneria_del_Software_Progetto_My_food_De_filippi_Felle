package model.prodotto.factory;

import model.prodotto.composite.IProdotto;

public interface AbstractProdottoFactory {
    IProdotto creaProdotto();
}