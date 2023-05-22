package fr.knsrhuseyin.sotari.minecraft_launcher.process;

import fr.knsrhuseyin.utils.json_reader.JSONObject;
import fr.knsrhuseyin.utils.json_reader.parser.JSONParser;
import fr.knsrhuseyin.utils.json_reader.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class UserSettings {

    private int ram = 0;

    public static void userJsonReader() throws IOException, ParseException {
        JSONParser jsonP = new JSONParser();
        JSONObject json0 = (JSONObject) jsonP.parse(new FileReader(""));
    }

    public UserSettings(Path launcherDir) throws IOException, ParseException {
        JSONParser jsonP = new JSONParser();
        JSONObject json0 = (JSONObject) jsonP.parse(new FileReader(launcherDir + "/data/json/settings.json"));
        this.ram = (int) json0.get("ram");
    }

    public int getRam() {
        return ram;
    }
}
