package fr.knsrhuseyin.sotari.minecraft_launcher;

import fr.knsrhuseyin.sotari.minecraft_launcher.process.SotariMinecraftLauncher;
import fr.knsrhuseyin.sotari.minecraft_launcher.ressource.RessourceMinecraft;
import fr.knsrhuseyin.utils.json_reader.parser.ParseException;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static final Path launcherDir = GameDirGenerator.createGameDir(RessourceMinecraft.serverName, true);
    static KnsrLogger logger = new KnsrLogger("Sotari | MinecraftLauncher", Paths.get(launcherDir + "/data/logs/launcher.log"), true);

    public static void main(String[] args) {
        try {
            SotariMinecraftLauncher.minecraftLauncherNoFramework(launcherDir, logger);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
