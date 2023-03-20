package quac.funnyplugin.Ability.Abilities;

import quac.funnyplugin.Ability.AbilityUseButton;

public class INSTANT_HEAL_ABILITY extends HEAL_ABILITY{
    public INSTANT_HEAL_ABILITY(AbilityUseButton button, int healAmount) {
        super(button, healAmount);
        this.name = "Instant Heal";
        this.cooldownTicks = 5;

    }
}
