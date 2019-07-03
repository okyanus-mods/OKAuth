package club.issizler.okauth;

import club.issizler.okauth.cmd.LoginCommand;
import club.issizler.okauth.cmd.RegisterCommand;
import club.issizler.okauth.db.Database;
import club.issizler.okauth.events.*;
import club.issizler.okyanus.api.Mod;
import club.issizler.okyanus.api.cmd.ArgumentType;
import club.issizler.okyanus.api.cmd.CommandBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class OKAuthMod extends Mod {

    private Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        registerCommand(
                new CommandBuilder("register")
                        .arg("password", ArgumentType.TEXT)
                        .arg("password_confirm", ArgumentType.TEXT)
                        .run(new RegisterCommand())
        );

        registerCommand(
                new CommandBuilder("login")
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

        registerEvent(new BreakListener());
        registerEvent(new ChatListener());
        registerEvent(new DropListener());
        registerEvent(new InteractBlockListener());
        registerEvent(new InteractEntityListener());
        registerEvent(new InteractItemListener());
        registerEvent(new JoinListener());
        registerEvent(new LeaveListener());
        registerEvent(new MoveListener());
        registerEvent(new PlaceListener());
        registerEvent(new StopListener());
    }

}
