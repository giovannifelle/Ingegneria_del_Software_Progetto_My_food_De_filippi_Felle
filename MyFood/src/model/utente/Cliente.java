package model.utente;

import java.time.LocalDate;

public class Cliente implements IUtente {
    private Integer idCliente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private LocalDate dataNascita;
    private String professione;
    private String residenza;
    private String telefono;
    private LocalDate dataRegistrazione;
    private LocalDate dataUltimoAccesso;
    private Boolean disabilitato;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getProfessione() {
        return professione;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public String getResidenza() {
        return residenza;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public LocalDate getDataUltimoAccesso() {
        return dataUltimoAccesso;
    }

    public void setDataUltimoAccesso(LocalDate dataUltimoAccesso) {
        this.dataUltimoAccesso = dataUltimoAccesso;
    }

    public Boolean getDisabilitato() {
        return disabilitato;
    }

    public void setDisabilitato(Boolean disabilitato) {
        this.disabilitato = disabilitato;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "\nidCliente=" + idCliente +
                "\nnome='" + nome + '\'' +
                "\ncognome='" + cognome + '\'' +
                "\nemail='" + email + '\'' +
                "\npassword='" + password + '\'' +
                "\ndataNascita=" + dataNascita +
                "\nprofessione='" + professione + '\'' +
                "\nresidenza='" + residenza + '\'' +
                "\ntelefono='" + telefono + '\'' +
                "\ndataRegistrazione=" + dataRegistrazione +
                "\ndataUltimoAccesso=" + dataUltimoAccesso +
                "\ndisabilitato=" + disabilitato +
                "\n}";
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setNewCliente(String nome,String cognome, String email, String password, String professione, String residenza, String telefono, LocalDate data){
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.professione=professione;
        this.residenza=residenza;
        this.telefono=telefono;
        this.dataNascita=data;
        this.dataRegistrazione=LocalDate.now();
        this.dataUltimoAccesso=LocalDate.now();
        this.disabilitato=false;
    }
}
