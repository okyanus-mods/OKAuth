package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.chat.MessageType;
import club.issizler.okyanus.api.event.ConnectEvent;
import club.issizler.okyanus.api.event.EventHandler;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class JoinListener implements EventHandler<ConnectEvent> {

    @Override
    public void handle(ConnectEvent event) {
        UUID uuid = event.getPlayer().getUUID();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture future = executorService.scheduleAtFixedRate(() -> {
            try {
                if (Auth.INSTANCE.isLoggedIn(uuid))
                    return;

                if (Auth.INSTANCE.hasAcct(uuid))
                    event.getPlayer().send("Please log in with §a/login <password>", MessageType.INFO);
                else
                    event.getPlayer().send("Please register with §a/register <password> <password>", MessageType.INFO);
            } catch (SQLException e) {
                event.getPlayer().send("§cAn error has occured while joining. Check the server logs!", MessageType.INFO);
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

        executorService.schedule(() -> {
            if (Auth.INSTANCE.isLoggedIn(uuid))
                future.cancel(true);
            else
                event.getPlayer().kick("§cYou're too slow!");
        }, 15, TimeUnit.SECONDS);
    }

}
