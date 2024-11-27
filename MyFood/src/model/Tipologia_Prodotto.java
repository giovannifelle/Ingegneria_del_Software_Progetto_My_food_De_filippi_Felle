package model;

public class Tipologia_Prodotto {
    private Integer idTipologiaP;
    private String nome;

    public Integer getIdTipologiaP() {
        return idTipologiaP;
    }

    public void setIdTipologiaP(Integer idTipologiaP) {
        this.idTipologiaP = idTipologiaP;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Tipologia_Prodotto{" +
                "idTipologiaP=" + idTipologiaP +
                ", nome='" + nome + '\'' +
                '}';
    }
}
