package fr.knsrhuseyin.sotari.minecraft_launcher.process;

import fr.flowarg.openlauncherlib.NoFramework;
import fr.knsrhuseyin.sotari.minecraft_launcher.ressource.RessourceMinecraft;
import fr.knsrhuseyin.utils.json_reader.parser.ParseException;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;

import java.io.IOException;
import java.nio.file.Path;

public class SotariMinecraftLauncher {

    public static void minecraftLauncherNoFramework(Path launcherDir, KnsrLogger logger) throws IOException, ParseException {
        UserInfo userInfo = new UserInfo(launcherDir);
        UserSettings userSettings = new UserSettings(launcherDir);

        logger.info("Set authInfo !");
        AuthInfos authInfos = new AuthInfos(userInfo.getUsername(), userInfo.getAccessToken(), userInfo.getUuid());
        try {
            NoFramework noFramework = new NoFramework(
                    launcherDir,
                    authInfos,
                    GameFolder.FLOW_UPDATER
            );
            if (userSettings.getRam() != 0) {
                noFramework.getAdditionalVmArgs().add("-Xmx" + userSettings.getRam() + "G");
            } else {
                noFramework.getAdditionalVmArgs().add("-Xmx3G");
            }
            logger.info("Launching Sotari Minecraft !");
            Process p = noFramework.launch(RessourceMinecraft.gameVersion, RessourceMinecraft.FORGE_VERSION, NoFramework.ModLoader.FORGE);
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        logger.info("Closing Sotari Minecraft !");
        System.exit(0);
    }

}
