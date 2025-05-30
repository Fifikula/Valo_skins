package org.example.app_val.services;

import com.google.gson.*;
import org.example.app_val.model.Skin;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class ValorantApiService {
    private static final String VINFO_SKINS_URL = ConfigManager.get("vinfo.api.skins.url");
    private List<Skin> vinfoSkinsCache;

    private synchronized void loadVinfoSkinsCache() {
        try {
            vinfoSkinsCache = new ArrayList<>();
            JsonArray arr = JsonParser
                    .parseReader(new InputStreamReader(new URL(VINFO_SKINS_URL).openStream()))
                    .getAsJsonArray();

            for (JsonElement el : arr) {
                JsonObject o = el.getAsJsonObject();
                String id = o.has("id") ? o.get("id").getAsString() : "";
                String name = o.has("name") ? o.get("name").getAsString() : "";
                String weaponId = o.has("weaponId") ? o.get("weaponId").getAsString() : "";

                if (name.equalsIgnoreCase("Random Favorite Skin")) {
                    continue;
                }

                String icon = "";
                JsonArray chromas = o.getAsJsonArray("chromas");
                if (chromas != null && !chromas.isEmpty()) {
                    JsonObject ch = chromas.get(0).getAsJsonObject();
                    if (ch.has("displayIcon") && !ch.get("displayIcon").isJsonNull()) {
                        icon = ch.get("displayIcon").getAsString();
                    }
                }

                vinfoSkinsCache.add(new Skin(name, icon, id, weaponId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Skin> fetchSkins() {
        if (vinfoSkinsCache == null) {
            loadVinfoSkinsCache();
        }
        return new ArrayList<>(vinfoSkinsCache);
    }

    public List<Skin> fetchWeaponSkins() {
        if (vinfoSkinsCache == null) {
            loadVinfoSkinsCache();
        }

        Map<String, Skin> repr = new LinkedHashMap<>();
        for (Skin skin : vinfoSkinsCache) {
            String wid = skin.weaponId();
            if (!repr.containsKey(wid)) {
                repr.put(wid, skin);
            }

            if (skin.name().equalsIgnoreCase("Melee")) {
                repr.put(wid, skin);
            }
            else if ( skin.name().toLowerCase().contains("standard")) {
                repr.put(wid, skin);
            }
        }

        return new ArrayList<>(repr.values());
    }
}
