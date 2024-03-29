package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.MoveEvent;

public class MoveListener implements EventHandler<MoveEvent> {

    @Override
    public void handle(MoveEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
