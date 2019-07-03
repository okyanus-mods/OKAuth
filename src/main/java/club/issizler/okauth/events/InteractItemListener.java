package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.InteractItemEvent;

public class InteractItemListener implements EventHandler<InteractItemEvent> {
    
    @Override
    public void handle(InteractItemEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
