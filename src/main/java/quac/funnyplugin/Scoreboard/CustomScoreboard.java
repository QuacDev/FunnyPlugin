package quac.funnyplugin.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomScoreboard {

    Player player;
    String displayName;
    private List<String> lines = new ArrayList<>();
    Scoreboard board;
    Objective obj;

    public CustomScoreboard(Player p, String displayName) throws SQLException {
        this.player = p;
        this.displayName = displayName;

        createBoard();
    }

    void createBoard() throws SQLException {
        PlayerBase playerBase = PlayerBase.getPlayerBase(this.player);

        ScoreboardManager manager = Bukkit.getScoreboardManager();

        this.board = manager.getNewScoreboard();
        this.obj = board.registerNewObjective("TestScoreBoard", Text.translate(this.displayName));

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(Text.translate(this.displayName));
    }

    public void addLine(String display) {
        lines.add(display);
    }

    public void finish() {
        for (int i = lines.size()-1; i>=0; i--) {
            this.obj.getScore(Text.translate(lines.get(i))).setScore(lines.size()-i);
        }
    }

    public void display() {
        this.player.setScoreboard(this.board);
    }
}
