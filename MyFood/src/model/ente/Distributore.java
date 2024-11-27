package model.ente;

public class Distributore implements IEnte {
    private Integer idDistributore;
    private String nome;
    private String partitaIVA;

    public Integer getId() {
        return idDistributore;
    }

    public void setIdDistributore(Integer idDistributore) {
        this.idDistributore = idDistributore;
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
        return "Distributore{" +
                "idDistributore=" + idDistributore +
                ", nome='" + nome + '\'' +
                ", partitaIVA='" + partitaIVA + '\'' +
                '}';
    }
}
