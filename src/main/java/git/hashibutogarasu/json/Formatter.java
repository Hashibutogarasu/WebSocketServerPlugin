package git.hashibutogarasu.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.java_websocket.WebSocket;
import org.jetbrains.annotations.NotNull;

public class Formatter {
    private WebSocket webSocket;
    private LocationType locationType;

    public static enum LocationType{
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
            if(player_name != null) model.player_name = player_name;
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
    }
}
