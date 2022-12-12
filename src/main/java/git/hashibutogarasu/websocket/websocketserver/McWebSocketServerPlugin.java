package git.hashibutogarasu.websocket.websocketserver;

import git.hashibutogarasu.websocket.server.WebSocketServerMain;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public final class McWebSocketServerPlugin extends JavaPlugin {

    private WebSocketServerMain s;

    public static String PLUGIN_NAME = "websocketserver";
    public static Logger LOGGER = Logger.getLogger(PLUGIN_NAME);
    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            s = new WebSocketServerMain(80, Path.of(this.getDataFolder().getAbsolutePath()).getParent().getParent());
            s.start();
            System.out.println("ChatServer started on port: " + s.getPort());
            getServer().getPluginManager().registerEvents(s, this);
        } catch (IOException ignored) {

        }

        LOGGER.info(PLUGIN_NAME + "が有効化されました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            s.stop();
        } catch (InterruptedException ignored) {

        }
        LOGGER.info(PLUGIN_NAME + "が無効化されました");
    }
}
