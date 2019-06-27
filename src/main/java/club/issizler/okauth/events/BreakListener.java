package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.BreakEvent;
import club.issizler.okyanus.api.event.EventHandler;

public class BreakListener implements EventHandler<BreakEvent> {

    @Override
    public void handle(BreakEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
