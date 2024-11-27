package model;

import model.prodotto.composite.IProdotto;
import model.utente.Cliente;
import model.utente.Cucina;

import java.time.LocalDate;
import java.util.ArrayList;


public class Ordine {
    private Integer idOrdine;
    private LocalDate data;
    private Float importo;

    public enum STATO {
        NON_PAGATO,
        IN_LAVORAZIONE,
        PRONTO,
        ANNULLATO
    }

    private STATO stato;
    private Boolean salvato;
    private Cliente cliente;
    private Cucina cucina;

    private ArrayList<IProdotto> prodotti;

    public Integer getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(Integer idOrdine) {
        this.idOrdine = idOrdine;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getImporto() {
        return importo;
    }

    public void setImporto(Float importo) {
        this.importo = importo;
    }

    public STATO getStato() {
        return stato;
    }

    public void setStato(STATO stato) {
        this.stato = stato;
    }

    public Boolean getSalvato() {
        return salvato;
    }

    public void setSalvato(Boolean salvato) {
        this.salvato = salvato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cucina getCucina() {
        return cucina;
    }

    public void setCucina(Cucina cucina) {
        this.cucina = cucina;
    }

    public ArrayList<IProdotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<IProdotto> prodotti) {
        this.prodotti = prodotti;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "idOrdine=" + idOrdine +
                ", data=" + data +
                ", importo=" + importo +
                ", salvato=" + salvato +
                ", cliente=" + cliente +
                ", cucina=" + cucina +
                ", prodotti=" + prodotti.toString() +
                '}';
    }

    public Float getTotale() {
        Float sum = 0f;
        for (IProdotto p : prodotti) {
            sum += p.getPrezzo();
        }
        return (sum * 100.0f) / 100.0f;
    }
}
