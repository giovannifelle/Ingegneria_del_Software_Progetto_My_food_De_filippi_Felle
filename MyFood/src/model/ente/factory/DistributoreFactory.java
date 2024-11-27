package model.ente.factory;

import model.ente.Distributore;
import model.ente.IEnte;

public class DistributoreFactory implements AbstractEnteFactory {
    @Override
    public IEnte creaEnte() {
        return new Distributore();
    }
}