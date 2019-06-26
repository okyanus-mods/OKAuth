package club.issizler.okauth.cmd;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.cmd.CommandRunnable;
import club.issizler.okyanus.api.cmd.CommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class RegisterCommand implements CommandRunnable {

    private Logger logger = LogManager.getLogger();

    @Override
    public int run(CommandSource source) {
        String password = source.getArgText("password");
        String passwordConfirm = source.getArgText("password_confirm");

        if (!password.equals(passwordConfirm)) {
            source.send("§cPasswords do not match!");
            return -1;
        }

        try {
            Auth.INSTANCE.register(source.getPlayer().getUUID(), password);
            source.send("§aYou have successfully registered!");
        } catch (SQLException e) {
            logger.error("OKAuth: Couldn't register!");
            source.send("§cAn unknown error has occurred. Check the server console!");

            e.printStackTrace();
        }

        return 0;
    }

}
