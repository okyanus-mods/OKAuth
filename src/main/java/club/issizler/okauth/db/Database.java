package club.issizler.okauth.db;

import java.sql.*;

public enum Database {
    INSTANCE;

    private Connection conn;

    public void connect(String filename) throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:./" + filename);
    }

    public PreparedStatement prepare(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public void close() throws SQLException {
        conn.close();
    }

}
