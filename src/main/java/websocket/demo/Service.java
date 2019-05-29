package websocket.demo;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** author: Ranjith Manickam @ 30 May' 2019 */
@ServerEndpoint("/data")
public class Service {

    private static final Set<Session> SESSIONS = new HashSet<>();

    private static ScheduledExecutorService TIMER = Executors.newSingleThreadScheduledExecutor();

    @OnOpen
    public void onOpen(final Session session) {
        SESSIONS.add(session);

        // schedule timer first time
        if (SESSIONS.size() == 1) {
            TIMER.scheduleAtFixedRate(() -> sendTimeToAll(), 0, 1, TimeUnit.SECONDS);
        }
    }

    private void sendTimeToAll() {
        for (Session session : SESSIONS) {
            try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(new Date().toString());
                }
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

}
