package quac.funnyplugin.Ability;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbilityBase {
    public String name;
    public AbilityUseButton useButton;
    public int cooldownTicks;
    public String description;

    public boolean use(PlayerInteractEvent event) throws SQLException {
        Player p = event.getPlayer();
        PlayerBase base = PlayerBase.getPlayerBase(p);

        if(base.cooldowns.containsKey(name)) {
            return false;
        }

        base.useAbility(this);

        return true;
    }

    public AbilityBase(AbilityUseButton button) {
        this.name = "UndefinedAbilityName";
        this.useButton = button;
        this.description = "UndefinedAbilityDescription";
        this.cooldownTicks = 20;
    }

    public List<String> getAbilityLore() {
        List<String> lore = new ArrayList<>();

        lore.add(Text.translate("&6Ability: " + this.name + " &e&l" + this.useButton.display));
        lore.add(Text.translate("&7" + this.description));
        if(this.cooldownTicks > 0) {
            double cooldownSeconds = cooldownTicks / 20d;
            String cooldownString = String.valueOf(cooldownSeconds);
            if(cooldownString.endsWith(".0")) {
                cooldownString = String.valueOf(Integer.parseInt(cooldownString));
            }

            lore.add(Text.translate("&8Cooldown: &a" + cooldownString + "s"));
        }

        return lore;
    }
}
