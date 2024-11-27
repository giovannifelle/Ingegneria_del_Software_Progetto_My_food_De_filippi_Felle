package model.prodotto.composite;

import model.Immagine;

import java.util.ArrayList;

public class Menu implements IProdotto {

    private Integer id;
    private String nome;
    private Float sconto;
    private ArrayList<IProdotto> prodotti = new ArrayList<>();
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

    public Float getSconto() {
        return sconto;
    }

    public void setSconto(Float sconto) {
        this.sconto = sconto;
    }

    public ArrayList<IProdotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<IProdotto> prodotti) {
       for(IProdotto p: prodotti){
           addProdotto(p);
       }
    }


    public void setImmagini(ArrayList<Immagine> immagini) {
        this.immagini = immagini;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "\nidMenu=" + id +
                "\nnome=" + nome +
                "\nsconto=" + sconto +
                "\nprodotti=\n" + prodotti +
                //"\ncommenti=" + commenti +
                "\nimmagini=" + immagini +
                '}';
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public Float getPrezzo() {
        Float somma = 0f;

        for (IProdotto p : prodotti) {
            somma += p.getPrezzo();
        }
        return Math.round((somma * (1 - sconto))*100)/100.0f;
    }

    @Override
    public ArrayList<Immagine> getImmagini() {
        return immagini;
    }

    public boolean addProdotto(IProdotto prodotto) {
        if (prodotto instanceof Prodotto_Singolo) {
            prodotti.add(prodotto);
        } else {
            Menu menu = (Menu) prodotto;
            for (IProdotto p : menu.getProdotti()) {
                if (p instanceof Menu) {
                    return true;
                }
            }
            prodotti.add(prodotto);

        }
        return false;
    }
}
