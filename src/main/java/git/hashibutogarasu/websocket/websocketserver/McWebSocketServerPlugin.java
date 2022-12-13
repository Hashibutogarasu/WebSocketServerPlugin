package git.hashibutogarasu.websocket.websocketserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import git.hashibutogarasu.util.PortUtil;
import git.hashibutogarasu.websocket.server.WebSocketServerMain;
import git.hashibutogarasu.websocket.translator.Translator;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;

public final class McWebSocketServerPlugin extends JavaPlugin {

    private WebSocketServerMain s;

    public static String PLUGIN_NAME = "websocketserver";
    public static Logger LOGGER = Logger.getLogger(PLUGIN_NAME);
    @Override
    public void onEnable(){
        // Plugin startup logic

        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://raw.githubusercontent.com/Hashibutogarasu/WebSocketServerPlugin/master/src/main/java/git/hashibutogarasu/websocket/lang/ja_jp.json"))
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder().build().send(request, bodyHandler);
        } catch (InterruptedException | IOException ignored) {

        }

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String, String>> type = new TypeReference<>() {};
        if (response != null) {
            try {
                Translator.translate_map = mapper.readValue(response.body(), type);
            } catch (JsonProcessingException ignored) {

            }
        }

        int wsport = 80;

        if(PortUtil.available(wsport)) try {
            s = new WebSocketServerMain(wsport, Path.of(this.getDataFolder().getAbsolutePath()).getParent().getParent(), getServer());
            s.start();
            LOGGER.info("ChatServer started on port: " + s.getPort());
            getServer().getPluginManager().registerEvents(s, this);
        } catch (IOException ignored) {

        }

        LOGGER.info(PLUGIN_NAME + "が有効化されました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (s != null) {
            try {
//                s.kickEveryone("Server is reloading or closing.");
                s.stop();
            } catch (InterruptedException ignored) {

            }
            LOGGER.info(PLUGIN_NAME + "が無効化されました");
        }
    }
}
