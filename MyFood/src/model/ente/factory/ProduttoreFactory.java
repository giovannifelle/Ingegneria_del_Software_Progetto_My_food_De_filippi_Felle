package model.ente.factory;

import model.ente.IEnte;
import model.ente.Produttore;

public class ProduttoreFactory implements AbstractEnteFactory {
    @Override
    public IEnte creaEnte() {
        return new Produttore();
    }
}