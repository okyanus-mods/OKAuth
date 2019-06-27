package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.BreakEvent;
import club.issizler.okyanus.api.event.DropEvent;
import club.issizler.okyanus.api.event.EventHandler;

public class DropListener implements EventHandler<DropEvent> {

    @Override
    public void handle(DropEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
