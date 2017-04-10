
package main.java.com.djrapitops.plan.data.handlers;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import main.java.com.djrapitops.plan.Plan;
import main.java.com.djrapitops.plan.data.SessionData;
import main.java.com.djrapitops.plan.data.UserData;
import main.java.com.djrapitops.plan.data.cache.DataCacheHandler;

/**
 *
 * @author Rsl1122
 */
public class SessionHandler {
    private final HashMap<UUID, SessionData> activeSessions;
    private final DataCacheHandler handler;
    private final Plan plugin;

    /**
     *
     * @param plugin
     */
    public SessionHandler(Plan plugin) {
        this.plugin = plugin;
        this.handler = plugin.getHandler();
        this.activeSessions = new HashMap<>();    
    }
    
    /**
     *
     * @param data
     */
    public void startSession(UUID uuid) {
        long now = new Date().toInstant().getEpochSecond() * (long) 1000;
        SessionData session = new SessionData(now);
        activeSessions.put(uuid, session);
    }
    
    /**
     *
     * @param data
     */
    public void endSession (UserData data) {
        UUID uuid = data.getUuid();
        SessionData currentSession = activeSessions.get(uuid);
        if (currentSession != null) {
            long now = new Date().toInstant().getEpochSecond() * (long) 1000;
            currentSession.endSession(now);
            data.addSession(currentSession);
            activeSessions.remove(uuid);
        }
    }
}
