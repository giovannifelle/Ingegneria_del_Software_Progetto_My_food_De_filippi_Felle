package model.ente.factory;

public class EnteFactory {

    public static AbstractEnteFactory getFactory(String tipo) {
        switch (tipo) {
            case "produttore":
                return new ProduttoreFactory();
            case "distributore":
                return new DistributoreFactory();
        }
        return null;
    }
}