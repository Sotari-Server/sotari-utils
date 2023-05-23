package fr.knsrhuseyin.utils.downloader;

import fr.knsrhuseyin.utils.component.colored.ColoredProgressBar;
import fr.knsrhuseyin.utils.downloader.json.ExternalFile;
import fr.knsrhuseyin.utils.downloader.utils.IOUtils;
import fr.knsrhuseyin.utils.knsrlogger.KnsrLogger;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class KnsrDownloader {
    Path dir;
    KnsrLogger logger;
    private final List<ExternalFile> files;
    ColoredProgressBar progressBar;
    JLabel progressLabel;

    public KnsrDownloader(List<ExternalFile> externalFiles, ColoredProgressBar progressBar, JLabel progressLabel, KnsrLogger logger) {
        this.files = externalFiles;
        this.progressBar = progressBar;
        this.progressLabel = progressLabel;
        this.logger = logger;
    }

    public void updateExtFiles(Path dir)
    {
        final int[] downloadedFile = {0};
        if(getFiles().isEmpty()) return;

        //this.callback.step(Step.EXTERNAL_FILES);
        this.logger.info("File verification...");
        progressLabel.setText("Vérification des fichiers...");
        getFiles().forEach(extFile -> {
            try
            {
                final Path filePath = dir.resolve(extFile.getPath());
                if (!Files.exists(filePath)) {
                    IOUtils.download(this.logger, new URL(extFile.getDownloadURL()), filePath);
                }
                downloadedFile[0]++;
                progressBar.setMaximum(files.size());
                progressBar.setValue(downloadedFile[0]);
                progressLabel.setText(String.valueOf(filePath.getFileName()));
                //this.callback.onFileDownloaded(filePath);
            }
            catch (IOException e)
            {
                this.logger.err("Unable to install files : " + e);
            }
            //this.callback.update(this.downloadList.getDownloadInfo());
        });
        this.logger.info("All files have been downloaded !");
    }

    public List<ExternalFile> getFiles() {
        return files;
    }

    public KnsrLogger getLogger() {
        return logger;
    }
}
