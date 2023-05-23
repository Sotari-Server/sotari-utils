package fr.knsrhuseyin.sotari.minecraft_updater.process;

import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.knsrhuseyin.utils.component.colored.ColoredProgressBar;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.nio.file.Path;
import java.text.DecimalFormat;

public class ProgressCallback implements IProgressCallback {

    private final DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private KnsrLogger logger;
    private int port;
    static String localServer = "http://localhost";
    int progressRequest = 0;

    public ProgressCallback(KnsrLogger logger, int port) {
        this.logger = logger;
        this.port = port;
    }

    @Override
    public void init(ILogger logger) {}

    @Override
    public void step(Step step) {
        setRequest(logger, step.name(), port);
    }

    @Override
    public void onFileDownloaded(Path path) {

    }

    @Override
    public void update(DownloadList.DownloadInfo info) {
        double progress = (double) info.getDownloadedBytes() / info.getTotalToDownloadBytes() * 100.0d;
        int progressInt = (int) progress;
        if (progressRequest != progressInt) {
            progressRequest = progressInt;
            setRequest(logger, "progress/" + progressRequest, port);
        }
    }

    public static void setRequest(KnsrLogger logger, String callBack, int port) {
        try {
            String request = localServer + ":" + port + "/" + callBack;
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            logger.info("Request send to : " + request + " Response code = " + responsecode);
        } catch (IOException e) {
            logger.err("Erreur : " + e);
        }
    }

}
