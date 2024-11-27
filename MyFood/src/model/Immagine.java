package model;

import model.prodotto.composite.IProdotto;

public class Immagine {

    private Integer idImmagine;
    private String nomeFile;

    public Integer getIdImmagine() {
        return idImmagine;
    }

    public void setIdImmagine(Integer idImmagine) {
        this.idImmagine = idImmagine;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }


    @Override
    public String toString() {
        return "Immagine{" +
                "idImmagine=" + idImmagine +
                ", nomeFile='" + nomeFile + '\'' +
                '}';
    }
}
