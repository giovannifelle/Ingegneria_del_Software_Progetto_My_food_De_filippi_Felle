package dbInterface.command;

import dbInterface.DbConnection;

public class WriteOperation implements IDbOperation {

    private DbConnection conn = DbConnection.getInstance();
    private String sql;

    public WriteOperation(String sql) {
        this.sql = sql;
    }

    @Override
    public DbOperationResult execute() {
        DbOperationResult result = new DbOperationResult();
        result.setRowsAffected(conn.executeUpdate(this.sql));
        return result;
    }
}
