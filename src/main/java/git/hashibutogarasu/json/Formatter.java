package git.hashibutogarasu.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.java_websocket.WebSocket;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.logging.Logger;

public class Formatter {
    private final WebSocket webSocket;
    private final LocationType locationType;

    public enum LocationType{
        NORMAL,
        DISCORD
    }

    public Formatter(@NotNull WebSocket webSocket){
        if(webSocket.getResourceDescriptor().equals("/json")) this.locationType = Formatter.LocationType.DISCORD;
        else this.locationType = Formatter.LocationType.NORMAL;
        this.webSocket = webSocket;
    }

    public String getMessage(String beforeformatstring, String player_name, String eventname) throws JsonProcessingException {
        if(this.locationType == LocationType.DISCORD){
            JsonModel model = new JsonModel();
            model.eventname = eventname;
            model.ip = this.webSocket.getRemoteSocketAddress().getAddress().getHostAddress();
            if(player_name != null) {
                model.player_name = player_name;
                File face_path = new File("./wsdata/" + player_name + "/" + player_name + "_face.png");
                try {
                    String contentType = Files.probeContentType(face_path.toPath());
                    byte[] data = Files.readAllBytes(face_path.toPath());
                    // byte[]をbase64文字列に変換する(java8)
                    String base64str = Base64.getEncoder().encodeToString(data);

                    // data URIを作る
                    String sb = "data:" +
                            contentType +
                            ";base64," +
                            base64str;

                    model.icon_base64 = sb;
                } catch (IOException ignored) {

                }
            }
            model.message = beforeformatstring;

            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(model));
            return mapper.writeValueAsString(model);
        }
        else if(this.locationType == LocationType.NORMAL){
            return beforeformatstring;
        }
        return beforeformatstring;
    }

    class JsonModel{
        @SerializedName("player_name")
        @Expose
        public String player_name;
        @SerializedName("ip")
        @Expose
        public String ip;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("event_name")
        @Expose
        public String eventname;
        @SerializedName("icon_base64")
        @Expose
        public String icon_base64;
    }
}
