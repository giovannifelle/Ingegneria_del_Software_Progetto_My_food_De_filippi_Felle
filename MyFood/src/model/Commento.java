package model;

import model.prodotto.composite.IProdotto;
import model.utente.Amministratore;
import model.utente.Cliente;

public class Commento {
    private Integer idCommento;

    public enum INDICE_GRADIMENTO {
        UNO,
        DUE,
        TRE,
        QUATTRO,
        CINQUE
    }

    private INDICE_GRADIMENTO indiceGradimento;
    private String testo;
    private String risposta;
    private IProdotto prodotto;
    private Cliente cliente;
    private Amministratore amministratore;

    public Commento() {

    }

    public Integer getIdCommento() {
        return idCommento;
    }

    public void setIdCommento(Integer idCommento) {
        this.idCommento = idCommento;
    }

    public INDICE_GRADIMENTO getIndiceGradimento() {
        return indiceGradimento;
    }

    public void setIndiceGradimento(INDICE_GRADIMENTO indiceGradimento) {
        this.indiceGradimento = indiceGradimento;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public IProdotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(IProdotto prodotto) {
        this.prodotto = prodotto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Amministratore getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(Amministratore amministratore) {
        this.amministratore = amministratore;
    }

    @Override
    public String toString() {
        return "Commento{" +
                "idCommento=" + idCommento +
                ", testo='" + testo + '\'' +
                ", risposta='" + risposta + '\'' +
                ", prodotto=" + prodotto +
                ", cliente=" + cliente +
                ", amministratore=" + amministratore +
                '}';
    }

}
