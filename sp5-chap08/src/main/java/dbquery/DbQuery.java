package dbquery;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQuery {
    private DataSource dataSource;

    public DbQuery(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public int count() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            try (Statement stmt = conn.createStatement(); // 풀에서 구함
                 ResultSet rs = stmt.executeQuery("select count(*) from MEMBER")) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close(); // 풀에서 반환
                } catch (SQLException e) {
                }
        }
    }
}
