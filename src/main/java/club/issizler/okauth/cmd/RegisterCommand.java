package club.issizler.okauth.cmd;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.cmd.CommandRunnable;
import club.issizler.okyanus.api.cmd.CommandSource;
import club.issizler.okyanus.api.entity.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

public class RegisterCommand implements CommandRunnable {

    private Logger logger = LogManager.getLogger();

    @Override
    public int run(CommandSource source) {
        Optional<String> password = source.getArgText("password");
        Optional<String> passwordConfirm = source.getArgText("password_confirm");
        Optional<Player> player = source.getPlayer();

        if (!player.isPresent())
            return -1;

        if (!password.equals(passwordConfirm)) {
            source.send("§cPasswords do not match!");
            return -1;
        }

        try {
            if (!password.isPresent())
                return -1;

            Auth.INSTANCE.register(player.get().getUUID(), password.get());
            source.send("§aYou have successfully registered!");
        } catch (SQLException e) {
            logger.error("OKAuth: Couldn't register!");
            source.send("§cAn unknown error has occurred. Check the server console!");

            e.printStackTrace();
        }

        return 0;
    }

}
