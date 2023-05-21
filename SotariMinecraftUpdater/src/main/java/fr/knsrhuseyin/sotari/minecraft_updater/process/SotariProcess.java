package fr.knsrhuseyin.sotari.minecraft_updater.process;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.download.json.Mod;
import fr.flowarg.flowupdater.download.json.OptiFineInfo;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.knsrhuseyin.sotari.minecraft_updater.ressource.RessourceMinecraft;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;
import java.nio.file.Path;

public class SotariProcess {

    public static void updateMinecraft(Path launcherDir, KnsrLogger logger, int port) throws Exception {

        //List<Mod> mods = Mod.getModsFromJson("http://81.254.78.0:90/KnsrGames/files/Mods/mods.php");

        final VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder()
                .withName(RessourceMinecraft.gameVersion)
                .build();

        AbstractForgeVersion forgeVersionUpdater = new ForgeVersionBuilder(RessourceMinecraft.FORGE_VERSION_TYPE)
                .withForgeVersion(RessourceMinecraft.forgeVersion)
                //.withMods(mods)
                .withOptiFine(new OptiFineInfo(RessourceMinecraft.optifineVersion))
                .build();

        final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                .withVanillaVersion(vanillaVersion)
                .withModLoaderVersion(forgeVersionUpdater)
                .withProgressCallback(new ProgressCallback(logger, port))
                .withExternalFiles(ExternalFile.getExternalFilesFromJson("http://81.254.78.0:90/KnsrGames/files/extfile/sotari-files/sotari-launcher/extFiles.php"))
                .build();

        updater.update(launcherDir);
        System.exit(0);
    }
}
