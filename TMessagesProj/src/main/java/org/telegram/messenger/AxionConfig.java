package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;

public class AxionConfig {
    public static boolean ghostMode = false;
    public static boolean hideTyping = false;
    public static boolean hideReadVoice = false;
    public static boolean offlineMode = false;
    public static boolean antiDelete = true;
    public static boolean antiEdit = true;
    public static boolean allowSaveRestricted = true;
    public static boolean blockSponsored = true;
    public static boolean showPeerId = true;

    private static boolean configLoaded = false;

    public static void loadConfig() {
        if (configLoaded) return;
        try {
            if (ApplicationLoader.applicationContext != null) {
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("axionconfig", Context.MODE_PRIVATE);
                ghostMode = preferences.getBoolean("ghostMode", false);
                hideTyping = preferences.getBoolean("hideTyping", false);
                hideReadVoice = preferences.getBoolean("hideReadVoice", false);
                offlineMode = preferences.getBoolean("offlineMode", false);
                antiDelete = preferences.getBoolean("antiDelete", true);
                antiEdit = preferences.getBoolean("antiEdit", true);
                allowSaveRestricted = preferences.getBoolean("allowSaveRestricted", true);
                blockSponsored = preferences.getBoolean("blockSponsored", true);
                showPeerId = preferences.getBoolean("showPeerId", true);
                configLoaded = true;
            }
        } catch (Exception ignored) {}
    }

    public static void setGhostMode(boolean value) {
        ghostMode = value;
        save("ghostMode", value);
    }

    public static void setHideTyping(boolean value) {
        hideTyping = value;
        save("hideTyping", value);
    }

    public static void setHideReadVoice(boolean value) {
        hideReadVoice = value;
        save("hideReadVoice", value);
    }

    public static void setOfflineMode(boolean value) {
        offlineMode = value;
        save("offlineMode", value);
    }

    public static void setAntiDelete(boolean value) {
        antiDelete = value;
        save("antiDelete", value);
    }

    public static void setAntiEdit(boolean value) {
        antiEdit = value;
        save("antiEdit", value);
    }

    public static void setAllowSaveRestricted(boolean value) {
        allowSaveRestricted = value;
        save("allowSaveRestricted", value);
    }

    public static void setBlockSponsored(boolean value) {
        blockSponsored = value;
        save("blockSponsored", value);
    }

    public static void setShowPeerId(boolean value) {
        showPeerId = value;
        save("showPeerId", value);
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
