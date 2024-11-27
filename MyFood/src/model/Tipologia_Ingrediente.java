package model;

public class Tipologia_Ingrediente {
    private Integer idTipologiaI;
    private String nome;

    public Integer getIdTipologiaI() {
        return idTipologiaI;
    }

    public void setIdTipologiaI(Integer idTipologiaI) {
        this.idTipologiaI = idTipologiaI;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Tipologia_Ingrediente{" +
                "idTipologiaI=" + idTipologiaI +
                ", nome='" + nome + '\'' +
                '}';
    }
}
