package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.DisconnectEvent;
import club.issizler.okyanus.api.event.EventHandler;

public class LeaveListener implements EventHandler<DisconnectEvent> {

    @Override
    public void handle(DisconnectEvent event) {
        Auth.INSTANCE.disconnect(event.getPlayer().getUUID());
    }

}
