package quac.funnyplugin.database;


import jdk.nashorn.internal.objects.annotations.Getter;

import java.math.BigDecimal;

public class PlayerData {
    String uuid;

    BigDecimal money;

    public BigDecimal getMoney() {
        return money;
    }

    public PlayerData(String uuid, BigDecimal money) {
        this.uuid = uuid;
        this.money = money;
    }
}
