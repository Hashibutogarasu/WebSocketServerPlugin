package git.hashibutogarasu.websocket.translator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Translator{
    public String translated = "";
    @Contract(pure = true)
    public Translator(@NotNull String text){
        if(text.contains(".adventure.") || text.contains(".end.") || text.contains(".husbandry.") || text.contains(".nether.") || text.contains(".story.")){
            translated = text;
        }
    }
}
