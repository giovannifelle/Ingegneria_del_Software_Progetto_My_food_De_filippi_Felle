package model.prodotto.composite;

import model.Immagine;

import java.util.ArrayList;

public interface IProdotto {

    public Integer getId();
    public String getNome();
    public Float getPrezzo();
    public ArrayList<Immagine> getImmagini();




}
