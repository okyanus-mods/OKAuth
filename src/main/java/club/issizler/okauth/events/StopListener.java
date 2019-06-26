package club.issizler.okauth.events;

import club.issizler.okauth.db.Database;
import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.StopEvent;

import java.sql.SQLException;

public class StopListener implements EventHandler<StopEvent> {

    @Override
    public void handle(StopEvent event) {
        try {
            Database.INSTANCE.close();
        } catch (SQLException e) {
            System.err.println("Couldn't close database instance!");
            e.printStackTrace();
        }
    }

}
