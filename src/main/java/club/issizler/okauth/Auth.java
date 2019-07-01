package club.issizler.okauth;

import club.issizler.okauth.db.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public enum Auth {
    INSTANCE;

    private Map<UUID, Boolean> _isLoggedIn = new HashMap<>();

    public void register(UUID uuid, String password) throws SQLException {
        if (hasAcct(uuid))
            return;

        PreparedStatement s = Database.INSTANCE.prepare("INSERT INTO auth (uuid, password) VALUES (?, ?)");
        s.setObject(1, uuid);
        s.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
        s.executeUpdate();

        _isLoggedIn.put(uuid, true);
    }

    public void login(UUID uuid, String password) throws SQLException {
        if (!hasAcct(uuid))
            return;

        PreparedStatement s = Database.INSTANCE.prepare("SELECT password FROM auth WHERE uuid = ?;");
        s.setObject(1, uuid);

        ResultSet rs = s.executeQuery();

        if (!rs.next())
            return;

        String hashedPw = rs.getString("password");

        _isLoggedIn.put(uuid, BCrypt.checkpw(password, hashedPw));
    }

    public boolean hasAcct(UUID uuid) throws SQLException {
        PreparedStatement s = Database.INSTANCE.prepare("SELECT password FROM auth WHERE uuid = ?;");
        s.setObject(1, uuid);

        return s.executeQuery().next();
    }

    public boolean isLoggedIn(UUID uuid) {
        if (_isLoggedIn.containsKey(uuid))
            return _isLoggedIn.get(uuid);

        return false;
    }

    public void disconnect(UUID uuid) {
        _isLoggedIn.remove(uuid);
    }
}
