package git.hashibutogarasu.websocket.translator;

import org.jetbrains.annotations.NotNull;
import java.util.Map;

public class Translator{
    public static Map<String, String> translate_map;
    public String basetext;

    public Translator(@NotNull String text){
        basetext = text;
    }

    public String getTranslated(){
        return translate_map.get(basetext);
    }

    public String getAdvancementtype(){
        if(basetext.contains(".froglights.") ||
                basetext.contains(".all_potions.") ||
                basetext.contains(".kill_mob_near_sculk_catalyst.") ||
                basetext.contains(".explore_nether.") ||
                basetext.contains(".sniper_duel.") ||
                basetext.contains(".levitate.") ||
                basetext.contains(".all_effects.") ||
                basetext.contains(".bullseye.") ||
                basetext.contains(".breed_all_animals.") ||
                basetext.contains(".two_birds_one_arrow.") ||
                basetext.contains(".arbalistic.") ||
                basetext.contains(".return_to_sender.") ||
                basetext.contains(".kill_all_mobs.") ||
                basetext.contains(".netherite_hoe.") ||
                basetext.contains(".adventuring_time.") ||
                basetext.contains(".hero_of_the_village.") ||
                basetext.contains(".balanced_diet.") ||
                basetext.contains(".fast_travel.") ||
                basetext.contains(".uneasy_alliance.") ||
                basetext.contains(".netherite_armor.") ||
                basetext.contains(".complete_catalogue.")){
            return "挑戦";
        }
        else{
            return "進捗";
        }
    }
}
