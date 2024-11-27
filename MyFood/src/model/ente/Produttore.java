package model.ente;

public class Produttore implements IEnte {
    private Integer idProduttore;
    private String nome;
    private String partitaIVA;

    public Integer getId() {
        return idProduttore;
    }

    public void setIdProduttore(Integer idProduttore) {
        this.idProduttore = idProduttore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }

    @Override
    public String toString() {
        return "Produttore{" +
                "idProduttore=" + idProduttore +
                ", nome='" + nome + '\'' +
                ", partitaIVA='" + partitaIVA + '\'' +
                '}';
    }
}
