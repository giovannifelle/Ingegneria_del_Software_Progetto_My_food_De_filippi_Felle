package model.utente;

public class Cucina implements IUtente {
    private Integer idCucina;
    private String email;
    private String password;
    private String sede;

    public Integer getIdCucina() {
        return idCucina;
    }

    public void setIdCucina(Integer idCucina) {
        this.idCucina = idCucina;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return "Cucina{" +
                "\nidCucina=" + idCucina +
                "\nemail='" + email + '\'' +
                "\npassword='" + password + '\'' +
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

}
