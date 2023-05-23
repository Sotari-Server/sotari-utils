package fr.knsrhuseyin.utils.downloader.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import fr.knsrhuseyin.utils.knsrlogger.ILogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class IOUtils {

    public static void download(ILogger logger, URL in, Path out)
    {
        try
        {
            logger.info(String.format("Downloading %s from %s...", out.getFileName().toString(), in.toExternalForm()));
            Files.createDirectories(out.getParent());
            Files.copy(catchForbidden(in), out, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
        {
            logger.printStackTrace(e);
        }
    }

    public static JsonElement readJson(URL jsonURL)
    {
        try
        {
            return readJson(catchForbidden(jsonURL));
        } catch (IOException e)
        {
            System.out.println("Erreur : " + e);
        }
        return JsonNull.INSTANCE;
    }

    public static JsonElement readJson(InputStream inputStream)
    {
        JsonElement element = JsonNull.INSTANCE;
        try(InputStream stream = new BufferedInputStream(inputStream))
        {
            final ReadableByteChannel rbc = Channels.newChannel(stream);
            final Reader enclosedReader = Channels.newReader(rbc, StandardCharsets.UTF_8.newDecoder(), -1);
            final BufferedReader reader = new BufferedReader(enclosedReader);
            final StringBuilder sb = new StringBuilder();

            int character;
            while ((character = reader.read()) != -1) sb.append((char)character);

            element = JsonParser.parseString(sb.toString());

            reader.close();
            enclosedReader.close();
            rbc.close();
        } catch (IOException e)
        {
            System.out.println("Erreur : " + e);
        }

        return element.getAsJsonObject();
    }

    public static InputStream catchForbidden(URL url) throws IOException
    {
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
        connection.setInstanceFollowRedirects(true);
        return connection.getInputStream();
    }

}
