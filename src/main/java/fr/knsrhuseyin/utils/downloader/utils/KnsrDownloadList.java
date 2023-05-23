package fr.knsrhuseyin.utils.downloader.utils;

import fr.knsrhuseyin.utils.downloader.json.ExternalFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KnsrDownloadList {

    private final List<ExternalFile> extFiles = new ArrayList<>();
    private final Lock updateInfoLock = new ReentrantLock();
    private DownloadInfo downloadInfo;
    private boolean init = false;

    public void init()
    {
        if (this.init) return;

        this.downloadInfo = new DownloadInfo();
        this.extFiles.forEach(
                externalFile -> this.downloadInfo.totalToDownloadBytes.set(
                        this.downloadInfo.totalToDownloadBytes.get() + externalFile.getSize()));

        this.downloadInfo.totalToDownloadFiles.set(
                this.downloadInfo.totalToDownloadFiles.get() +
                        this.extFiles.size());
        this.init = true;
    }

    public void incrementDownloaded(long bytes)
    {
        this.updateInfoLock.lock();
        this.downloadInfo.downloadedFiles.incrementAndGet();
        this.downloadInfo.downloadedBytes.set(this.downloadInfo.downloadedBytes.get() + bytes);
        this.updateInfoLock.unlock();
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public List<ExternalFile> getExtFiles() {
        return extFiles;
    }

    public void clear()
    {
        this.extFiles.clear();
        this.downloadInfo.reset();
        this.init = false;
    }

    public static class DownloadInfo
    {
        private final AtomicLong totalToDownloadBytes = new AtomicLong(0);
        private final AtomicLong downloadedBytes = new AtomicLong(0);
        private final AtomicInteger totalToDownloadFiles = new AtomicInteger(0);
        private final AtomicInteger downloadedFiles = new AtomicInteger(0);

        /**
         * Reset this download info object.
         */
        public void reset()
        {
            this.totalToDownloadBytes.set(0);
            this.downloadedBytes.set(0);
            this.totalToDownloadFiles.set(0);
            this.downloadedFiles.set(0);
        }

        /**
         * Get the total of bytes to download.
         * @return bytes to download.
         */
        public long getTotalToDownloadBytes()
        {
            return this.totalToDownloadBytes.get();
        }

        /**
         * Get the downloaded bytes.
         * @return the downloaded bytes.
         */
        public long getDownloadedBytes()
        {
            return this.downloadedBytes.get();
        }

        /**
         * Get the number of files to download.
         * @return number of files to download.
         */
        public int getTotalToDownloadFiles()
        {
            return this.totalToDownloadFiles.get();
        }

        /**
         * Get the number of downloaded files.
         * @return the number of downloaded files.
         */
        public int getDownloadedFiles()
        {
            return this.downloadedFiles.get();
        }
    }

}
