package club.issizler.okauth.cmd;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.cmd.CommandRunnable;
import club.issizler.okyanus.api.cmd.CommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginCommand implements CommandRunnable {

    private Logger logger = LogManager.getLogger();
    private Map<UUID, Integer> loginCounter = new HashMap<>();

    @Override
    public int run(CommandSource source) {
        String password = source.getArgText("password");
        UUID uuid = source.getPlayer().getUUID();

        try {
            Auth.INSTANCE.login(uuid, password);

            if (Auth.INSTANCE.isLoggedIn(uuid)) {
                source.send("§aYou have successfully logged in!");
            } else {
                source.send("§cTry again!");
                if (!incrementLoginCounter(uuid)) {
                    source.getPlayer().kick("§cToo many failed login attempts.");
                }
            }
        } catch (SQLException e) {
            logger.error("OKAuth: Couldn't login!");
            source.send("§cAn unknown error has occurred. Check the server console!");

            e.printStackTrace();
        }

        return 0;
    }

    private boolean incrementLoginCounter(UUID uuid) {
        int counter = 1;
        if (loginCounter.containsKey(uuid))
            counter = loginCounter.get(uuid) + 1;

        loginCounter.put(uuid, counter);

        return !(counter > 3);
    }

}
