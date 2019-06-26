package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.MoveEvent;

import java.util.UUID;

public class MoveListener implements EventHandler<MoveEvent> {

    @Override
    public void handle(MoveEvent event) {
        UUID uuid = event.getPlayer().getUUID();

        if (!Auth.INSTANCE.isLoggedIn(uuid))
            event.setCancelled(true);
    }

}
