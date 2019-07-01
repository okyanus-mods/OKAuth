package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.PlaceEvent;

public class PlaceListener implements EventHandler<PlaceEvent> {

    @Override
    public void handle(PlaceEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
