package dbInterface.command;


import dbInterface.DbConnection;

public class ReadOperation implements IDbOperation {

    private DbConnection conn = DbConnection.getInstance();
    private String sql;

    public ReadOperation(String sql) {
        this.sql = sql;
    }

    @Override
    public DbOperationResult execute() {
        DbOperationResult result = new DbOperationResult();
        result.setResultSet(conn.executeQuery(this.sql));
        return result;
    }
}
