package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import org.telegram.tgnet.TLRPC;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;

public class AxionConfig {
    public static boolean ghostMode = false;
    public static boolean hideTyping = false;
    public static boolean hideReadVoice = false;
    public static boolean offlineMode = false;
    public static boolean allowSaveRestricted = true;
    public static boolean preventTTL = true;
    public static boolean blockSponsored = true;
    public static boolean showPeerId = true;
    public static boolean smoothTyping = true;
    public static boolean customUsernameEnabled = false;
    public static String customUsername = "durov";
    public static String extraUsernames = "axion, owner, ceo";
    public static int customApiId = 0;
    public static String customApiHash = "";

    private static HashMap<String, String> remotePrefixes = new HashMap<>();
    private static boolean configLoaded = false;
    private static boolean prefixesLoaded = false;

    public static void loadConfig() {
        if (configLoaded) return;
        try {
            if (ApplicationLoader.applicationContext != null) {
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("axionconfig", Context.MODE_PRIVATE);
                ghostMode = preferences.getBoolean("ghostMode", false);
                hideTyping = preferences.getBoolean("hideTyping", false);
                hideReadVoice = preferences.getBoolean("hideReadVoice", false);
                offlineMode = preferences.getBoolean("offlineMode", false);
                allowSaveRestricted = preferences.getBoolean("allowSaveRestricted", true);
                preventTTL = preferences.getBoolean("preventTTL", true);
                blockSponsored = preferences.getBoolean("blockSponsored", true);
                showPeerId = preferences.getBoolean("showPeerId", true);
                smoothTyping = preferences.getBoolean("smoothTyping", true);
                customUsernameEnabled = preferences.getBoolean("customUsernameEnabled", false);
                customUsername = preferences.getString("customUsername", "durov");
                extraUsernames = preferences.getString("extraUsernames", "axion, owner, ceo");
                customApiId = preferences.getInt("customApiId", 0);
                customApiHash = preferences.getString("customApiHash", "");
                configLoaded = true;
            }
        } catch (Exception ignored) {}
    }

    public static void setGhostMode(boolean value) { ghostMode = value; save("ghostMode", value); }
    public static void setHideTyping(boolean value) { hideTyping = value; save("hideTyping", value); }
    public static void setHideReadVoice(boolean value) { hideReadVoice = value; save("hideReadVoice", value); }
    public static void setOfflineMode(boolean value) { offlineMode = value; save("offlineMode", value); }
    public static void setAllowSaveRestricted(boolean value) { allowSaveRestricted = value; save("allowSaveRestricted", value); }
    public static void setPreventTTL(boolean value) { preventTTL = value; save("preventTTL", value); }
    public static void setBlockSponsored(boolean value) { blockSponsored = value; save("blockSponsored", value); }
    public static void setShowPeerId(boolean value) { showPeerId = value; save("showPeerId", value); }
    public static void setSmoothTyping(boolean value) { smoothTyping = value; save("smoothTyping", value); }
    public static void setCustomUsernameEnabled(boolean value) { customUsernameEnabled = value; save("customUsernameEnabled", value); }

    public static String getUserPrefix(TLRPC.User user) {
        if (user == null) return null;
        loadPrefixesAsync();
        
        String idStr = String.valueOf(user.id);
        if (remotePrefixes.containsKey(idStr)) return remotePrefixes.get(idStr);
        
        if (user.username != null) {
            String u = user.username.toLowerCase();
            if (remotePrefixes.containsKey(u)) return remotePrefixes.get(u);
        }
        
        if (user.usernames != null) {
            for (int i = 0; i < user.usernames.size(); ++i) {
                TLRPC.TL_username un = user.usernames.get(i);
                if (un != null && un.username != null) {
                    String u = un.username.toLowerCase();
                    if (remotePrefixes.containsKey(u)) return remotePrefixes.get(u);
                }
            }
        }
        
        if (user.username != null && "goodbye_death".equalsIgnoreCase(user.username)) {
            return "⭐ [@создатель]";
        }
        if (user.usernames != null) {
            for (int i = 0; i < user.usernames.size(); ++i) {
                TLRPC.TL_username un = user.usernames.get(i);
                if (un != null && "goodbye_death".equalsIgnoreCase(un.username)) {
                    return "⭐ [@создатель]";
                }
            }
        }
        return null;
    }

    public static void loadPrefixesAsync() {
        if (prefixesLoaded) return;
        prefixesLoaded = true;
        Utilities.globalQueue.postRunnable(() -> {
            try {
                URL url = new URL("https://raw.githubusercontent.com/programzy/Axiongram-Android/master/prefixes.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line);
                    br.close();
                    JSONObject json = new JSONObject(sb.toString());
                    HashMap<String, String> map = new HashMap<>();
                    java.util.Iterator<String> keys = json.keys();
                    while (keys.hasNext()) {
                        String key = keys.next().toLowerCase();
                        JSONObject obj = json.getJSONObject(key);
                        if (obj.has("prefix")) {
                            map.put(key, obj.getString("prefix"));
                        }
                    }
                    remotePrefixes = map;
                }
            } catch (Throwable ignored) {}
        });
    }

    public static boolean shouldFilterRequest(Object obj) {
        if (obj == null) return false;
        loadConfig();
        String className = obj.getClass().getSimpleName();
        if (ghostMode) {
            if (className.equals("TL_messages_readHistory") ||
                className.equals("TL_channels_readHistory")) {
                return true;
            }
        }
        if (hideReadVoice) {
            if (className.equals("TL_messages_readMessageContents") ||
                className.equals("TL_channels_readMessageContents")) {
                return true;
            }
        }
        if (hideTyping) {
            if (className.equals("TL_messages_setTyping") ||
                className.equals("TL_messages_setEncryptedTyping")) {
                return true;
            }
        }
        if (blockSponsored) {
            if (className.contains("Sponsored") || className.contains("sponsored")) {
                return true;
            }
        }
        return false;
    }

    private static void save(String key, boolean value) {
        try {
            if (ApplicationLoader.applicationContext != null) {
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("axionconfig", Context.MODE_PRIVATE);
                preferences.edit().putBoolean(key, value).apply();
            }
        } catch (Exception ignored) {}
    }
}
