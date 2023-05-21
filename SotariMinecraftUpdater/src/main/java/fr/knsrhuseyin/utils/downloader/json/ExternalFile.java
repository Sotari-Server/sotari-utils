package fr.knsrhuseyin.utils.downloader.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.knsrhuseyin.utils.downloader.utils.IOUtils;
import fr.knsrhuseyin.utils.downloader.utils.KnsrUpdaterException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExternalFile {

    private final String path;
    private final String downloadURL;
    private final String sha1;
    private final long size;
    private final boolean update;

    public ExternalFile(String path, String downloadURL, String sha1, long size)
    {
        this.path = path;
        this.downloadURL = downloadURL;
        this.sha1 = sha1;
        this.size = size;
        this.update = true;
    }

    public ExternalFile(String path, String downloadURL, String sha1, long size, boolean update)
    {
        this.path = path;
        this.downloadURL = downloadURL;
        this.sha1 = sha1;
        this.size = size;
        this.update = update;
    }

    public static List<ExternalFile> getExternalFilesFromJson(URL jsonUrl)
    {
        final List<ExternalFile> result = new ArrayList<>();
        final JsonArray extfiles = IOUtils.readJson(jsonUrl).getAsJsonObject().getAsJsonArray("extfiles");
        extfiles.forEach(extFileElement -> {
            final JsonObject obj = extFileElement.getAsJsonObject();
            final String path = obj.get("path").getAsString();
            final String sha1 = obj.get("sha1").getAsString();
            final String downloadURL = obj.get("downloadURL").getAsString();
            final long size = obj.get("size").getAsLong();
            if(obj.get("update") != null)
                result.add(new ExternalFile(path, downloadURL, sha1, size, obj.get("update").getAsBoolean()));
            else result.add(new ExternalFile(path, downloadURL, sha1, size));
        });
        return result;
    }

    /**
     * Provide a List of external file from a JSON file.
     * @param jsonUrl the JSON file URL.
     * @return an external file list.
     */
    public static List<ExternalFile> getExternalFilesFromJson(String jsonUrl)
    {
        try
        {
            return getExternalFilesFromJson(new URL(jsonUrl));
        } catch (Exception e)
        {
            throw new KnsrUpdaterException(e);
        }
    }

    public String getPath()
    {
        return this.path;
    }

    public String getDownloadURL()
    {
        return this.downloadURL;
    }

    public String getSha1()
    {
        return this.sha1;
    }

    public long getSize()
    {
        return this.size;
    }

    public boolean isUpdate()
    {
        return this.update;
    }

}
