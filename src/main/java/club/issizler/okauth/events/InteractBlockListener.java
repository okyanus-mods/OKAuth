package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.InteractBlockEvent;

public class InteractBlockListener implements EventHandler<InteractBlockEvent> {

    @Override
    public void handle(InteractBlockEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
