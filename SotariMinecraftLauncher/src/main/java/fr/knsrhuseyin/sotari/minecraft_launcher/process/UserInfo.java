package fr.knsrhuseyin.sotari.minecraft_launcher.process;

import fr.knsrhuseyin.utils.json_reader.JSONObject;
import fr.knsrhuseyin.utils.json_reader.parser.JSONParser;
import fr.knsrhuseyin.utils.json_reader.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public class UserInfo {

    private String username;
    private String accessToken;
    private String uuid;
    private int ram;

    public static void userJsonReader() throws IOException, ParseException {
        JSONParser jsonP = new JSONParser();
        JSONObject json0 = (JSONObject) jsonP.parse(new FileReader(""));
    }

    public UserInfo(Path launcherDir) throws IOException, ParseException {

        JSONParser jsonP = new JSONParser();
        JSONObject json0 = (JSONObject) jsonP.parse(new FileReader(launcherDir + "/data/json/user.json"));
        this.username = (String) json0.get("username");
        this.accessToken = (String) json0.get("token");
        this.uuid = (String) json0.get("uid");
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUuid() {
        return uuid;
    }

    public int getRam() {
        return ram;
    }
}
