package club.issizler.okauth.events;

import club.issizler.okauth.Auth;
import club.issizler.okyanus.api.event.ChatEvent;
import club.issizler.okyanus.api.event.EventHandler;

public class ChatListener implements EventHandler<ChatEvent> {

    @Override
    public void handle(ChatEvent event) {
        if (!Auth.INSTANCE.isLoggedIn(event.getPlayer().getUUID()))
            event.setCancelled(true);
    }

}
