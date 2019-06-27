package club.issizler.okauth;

import club.issizler.okauth.cmd.LoginCommand;
import club.issizler.okauth.cmd.RegisterCommand;
import club.issizler.okauth.db.Database;
import club.issizler.okauth.events.*;
import club.issizler.okyanus.api.Mod;
import club.issizler.okyanus.api.cmd.ArgumentType;
import club.issizler.okyanus.api.cmd.CommandBuilder;
import club.issizler.okyanus.api.cmd.CommandManager;
import club.issizler.okyanus.api.event.EventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class OKAuthMod implements Mod {

    private Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        CommandManager.INSTANCE.register(
                new CommandBuilder()
                        .name("register")
                        .arg("password", ArgumentType.TEXT)
                        .arg("password_confirm", ArgumentType.TEXT)
                        .run(new RegisterCommand())
        );

        CommandManager.INSTANCE.register(
                new CommandBuilder()
                        .name("login")
                        .arg("password", ArgumentType.TEXT)
                        .run(new LoginCommand())
        );

        try {
            Database.INSTANCE.connect("okauth");
        } catch (SQLException e) {
            logger.info("OKAuth: Couldn't connect to database!");
            e.printStackTrace();
        }

        try {
            Database.INSTANCE.prepare(
                    "CREATE TABLE IF NOT EXISTS auth (" +
                            "uuid UUID NOT NULL UNIQUE, " +
                            "password VARCHAR(64) NOT NULL, " +
                            ");"
            ).executeUpdate();
        } catch (SQLException e) {
            logger.info("OKAuth: Couldn't prepare database!");
            e.printStackTrace();
        }

        EventManager.INSTANCE.register(new BreakListener());
        EventManager.INSTANCE.register(new DropListener());
        EventManager.INSTANCE.register(new JoinListener());
        EventManager.INSTANCE.register(new LeaveListener());
        EventManager.INSTANCE.register(new MoveListener());
        EventManager.INSTANCE.register(new StopListener());
    }

}
