package model.prodotto.composite;

import model.Immagine;
import model.Ingrediente;
import model.Tipologia_Prodotto;

import java.util.ArrayList;

public class Prodotto_Singolo implements IProdotto {

    private Integer id;
    private String nome;
    private Float prezzo;
    private Boolean disponibilita;
    private Tipologia_Prodotto tipologiaP;
    private ArrayList<Ingrediente> ingredienti;
    private ArrayList<Immagine> immagini;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }


    public Boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Boolean disponibilita) {
        this.disponibilita = disponibilita;
    }

    public Tipologia_Prodotto getTipologiaP() {
        return tipologiaP;
    }

    public void setTipologiaP(Tipologia_Prodotto tipologiaP) {
        this.tipologiaP = tipologiaP;
    }

    public ArrayList<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    /*public void setCommenti(ArrayList<Commento> commenti) {
        this.commenti = commenti;
    }*/

    public void setImmagini(ArrayList<Immagine> immagini) {
        this.immagini = immagini;
    }

    @Override
    public String toString() {
        return "Prodotto_Singolo{" +
                "\n\tidP_Singolo=" + id +
                "\n\tnome=" + nome +
                "\n\tprezzo=" + prezzo +
                "\n\tdisponibilita=" + disponibilita +
                "\n\ttipologiaP=" + tipologiaP +
                "\n\t\tingredienti=" + ingredienti +
                //"\n\t\tcommenti=" + commenti +
                "\n\t\timmagini=" + immagini +
                "\n}";
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public Float getPrezzo() {
        return prezzo;
    }

    @Override
    public ArrayList<Immagine> getImmagini() {
        return immagini;
    }


}
