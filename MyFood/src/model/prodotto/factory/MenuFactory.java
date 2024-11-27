package model.prodotto.factory;
import model.prodotto.composite.IProdotto;
import model.prodotto.composite.Menu;

public class MenuFactory implements AbstractProdottoFactory {
    @Override
    public IProdotto creaProdotto() {
        return new Menu();
    }
}