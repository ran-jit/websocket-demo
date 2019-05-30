package websocket.demo;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** author: Ranjith Manickam @ 30 May' 2019 */
@ServerEndpoint("/data")
public class Service {

    private static final Set<Session> SESSIONS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private static ScheduledExecutorService TIMER = Executors.newSingleThreadScheduledExecutor();

    @OnOpen
    public void onOpen(final Session session) {
        SESSIONS.add(session);

        // schedule timer first time
        if (SESSIONS.size() == 1) {
            TIMER.scheduleAtFixedRate(() -> sendTimeToAll(session), 0, 1, TimeUnit.SECONDS);
        }
    }

    private void sendTimeToAll(Session session) {
        SESSIONS.clear();
        SESSIONS.addAll(session.getOpenSessions());

        for (Session openSession : SESSIONS) {
            try {
                if (openSession.isOpen()) {
                    //openSession.getBasicRemote().sendText(new Date().toString());
                    openSession.getAsyncRemote().sendText(new Date().toString());
                }
            } catch (Exception ex) {
                // client session is broken or closed
                SESSIONS.remove(openSession);
            }
        }
    }
}
