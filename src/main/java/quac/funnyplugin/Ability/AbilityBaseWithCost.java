package quac.funnyplugin.Ability;

import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbilityBaseWithCost extends AbilityBase{
    public int manaCost;

    public AbilityBaseWithCost(AbilityUseButton button) {
        super(button);
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        PlayerBase base = PlayerBase.getPlayerBase(event.getPlayer());

        if(base.mana >= manaCost) {
            if (!super.use(event)) {
                return false;
            }

            base.useMana(this);
            return true;
        } else {
            event.getPlayer().sendMessage(Text.translate("&cNot enough Mana!"));

            return false;
        }
    }

    @Override
    public List<String> getAbilityLore() {
        List<String> lore = new ArrayList<>();

        lore.add(Text.translate("&6Ability: " + this.name + " &e&l" + this.useButton.display));
        lore.add(Text.translate("&7" + this.description));
        lore.add(Text.translate("&8Mana Cost: &3" + this.manaCost));
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
