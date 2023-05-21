package fr.knsrhuseyin.sotari.minecraft_updater;

import fr.knsrhuseyin.sotari.minecraft_updater.process.SotariProcess;
import fr.knsrhuseyin.sotari.minecraft_updater.ressource.RessourceMinecraft;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    static final Path launcherDir = GameDirGenerator.createGameDir(RessourceMinecraft.serverName, true);
    static KnsrLogger logger = new KnsrLogger("Sotari | MinecraftUpdater", Paths.get(launcherDir + "/data/logs/launcher.log"), true);

    public static void main(String[] args) {

        try {
            SotariProcess.updateMinecraft(launcherDir, logger, Integer.parseInt(args[0]));
        } catch (Exception e) {
            logger.err("Unable to update Sotari Minecraft  : " + e);
            throw new RuntimeException(e);
        }
    }

}
