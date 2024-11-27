package dbInterface;

public class DbUser {
    private static DbUser instance = new DbUser();

    private String schemaName;
    private String userName;
    private String pwd;

    protected DbUser() {
        this.schemaName = "myfood";
        this.userName = "root";
        this.pwd = "IngSoft2024";
    }

    public static DbUser getInstance() {
        return instance;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }
}
