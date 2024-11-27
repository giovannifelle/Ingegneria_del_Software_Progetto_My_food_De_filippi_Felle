package dbInterface;

import java.sql.*;

public class DbConnection implements IDbConnection {
    private static DbUser dbUser = DbUser.getInstance();
    private static DbConnection instance = new DbConnection();
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;
    private static int affectedRows;

    private DbConnection() {
        conn = null;
        stmt = null;
        rs = null;
        affectedRows = 0;

        try {
            Class cls = Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Ho caricato la classe: " + cls.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("Non riesco a caricare il drive jdbc");
        }
    }

    public static DbConnection getInstance() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + dbUser.getSchemaName() + "?serverTimeZone=UTC", dbUser.getUserName(), dbUser.getPwd());
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        }
        return instance;
    }

    @Override
    public ResultSet executeQuery(String sqlStatement) {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlStatement);
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        }
        return rs;
    }

    @Override
    public int executeUpdate(String sqlStatement) {
        try {
            stmt = conn.createStatement();
            affectedRows = stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State " + e.getSQLState());
            System.out.println("Vendor Error: " + e.getErrorCode());
        }
        return affectedRows;
    }

    @Override
    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("Vendor Error: " + e.getErrorCode());
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("Vendor Error: " + e.getErrorCode());
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("Vendor Error: " + e.getErrorCode());
            }
            conn = null;
        }
    }
}
