package model;

import model.ente.Distributore;
import model.ente.Produttore;

public class Ingrediente {
    private Integer idIngrediente;
    private String nome;
    private Produttore produttore;
    private Distributore distributore;
    private Tipologia_Ingrediente tipologiaI;

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Produttore getProduttore() {
        return produttore;
    }

    public void setProduttore(Produttore produttore) {
        this.produttore = produttore;
    }

    public Distributore getDistributore() {
        return distributore;
    }

    public void setDistributore(Distributore distributore) {
        this.distributore = distributore;
    }

    public Tipologia_Ingrediente getTipologiaI() {
        return tipologiaI;
    }

    public void setTipologiaI(Tipologia_Ingrediente tipologiaI) {
        this.tipologiaI = tipologiaI;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "\n\t\tidIngrediente=" + idIngrediente +
                "\n\t\tnome=" + nome +
                "\n\t\tproduttore=" + produttore +
                "\n\t\t\tdistributore=" + distributore +
                "\n\t\t\ttipologiaI=" + tipologiaI +
                '}';
    }
}
