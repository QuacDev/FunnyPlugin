package quac.funnyplugin.Commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Custom.EmptyChunkGenerator;

import java.sql.SQLException;

public class CreateWorld extends CustomCommand {
    public CreateWorld() {
        this.argsType = ArgsType.Min;
        this.argsAmount = 4;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        String name = args[0];
        int index = Integer.parseInt(args[1]);
        WorldType type = WorldType.getByName(args[2]);
        boolean structures = Boolean.getBoolean(args[3]);
        boolean voidWorld;

        if(args.length == 5) {
            voidWorld = Boolean.parseBoolean(args[4]);
        } else {
            voidWorld = false;
        }

        World.Environment environment = World.Environment.values()[index];

        if(type == null) {
            type = WorldType.NORMAL;
        }

        if(voidWorld) {
            WorldCreator worldCreator = new WorldCreator(name)
                    .type(WorldType.FLAT)
                    .generatorSettings("2;0;1")
                    .generateStructures(false);

            World world = worldCreator.createWorld();
            world.getBlockAt(0,120,0).setType(Material.BEDROCK);
            world.setSpawnLocation(0,121,0);
        } else {
            WorldCreator worldCreator = new WorldCreator(name)
                    .environment(environment)
                    .type(type)
                    .generateStructures(structures);


            Bukkit.createWorld(worldCreator);
        }
    }
}
