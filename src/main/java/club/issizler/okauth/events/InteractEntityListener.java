package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.InteractEntityEvent;

public class InteractEntityListener implements EventHandler<InteractEntityEvent> {
    
    @Override
    public void handle(InteractEntityEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
