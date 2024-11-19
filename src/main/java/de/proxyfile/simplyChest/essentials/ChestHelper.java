package de.proxyfile.simplyChest.essentials;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.proxyfile.simplyChest.SimplyChest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChestHelper {
    private static final String DEFAULT_INVENTORY_JSON = "{\"1\":{\"Item\":\"NETHERITE_SWORD\",\"Meta\":{\"CustomName\":\"§6Blade of the Nether\",\"Enchantments\":[{\"Enchantment\":\"SHARPNESS\",\"Level\":5},{\"Enchantment\":\"FIRE_ASPECT\",\"Level\":2}],\"Lore\":[\"§7A legendary weapon wielded\",\"§7by the heroes of the Nether.\",\"§eIts flames never fade.\"],\"Unbreakable\":true}}}";

    public static List<Object> getUserData(String uuid) {
        String query = "SELECT USERNAME, SIZE, INVENTORY FROM sc_userdata WHERE UUID = ?";
        try (PreparedStatement ps = QueryHandler.prepareStatement(query)) {
            ps.setString(1, uuid);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    List<Object> data = new ArrayList<>();
                    data.add(rs.getString("USERNAME"));
                    data.add(rs.getInt("SIZE"));
                    data.add(rs.getString("INVENTORY"));
                    return data;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch user data for UUID: " + uuid, e);
        }
        return Collections.emptyList();
    }

    public static ItemStack[] decodeJSON(String inventoryJSON) {
        Gson gson = new Gson();

        Type mapType = new TypeToken<Map<String, JsonObject>>() {}.getType();
        Map<String, JsonObject> slots = gson.fromJson(inventoryJSON, mapType);

        ItemStack[] inventory = new ItemStack[27];

        for (Map.Entry<String, JsonObject> entry : slots.entrySet()) {
            int slotIndex = Integer.parseInt(entry.getKey());
            JsonObject itemData = entry.getValue();

            Material material = Material.valueOf(itemData.get("Item").getAsString());
            JsonObject metaData = itemData.getAsJsonObject("Meta");

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                if (metaData.has("CustomName")) {
                    meta.setDisplayName(metaData.get("CustomName").getAsString());
                }
                if (metaData.has("Lore")) {
                    List<String> lore = gson.fromJson(metaData.get("Lore"), new TypeToken<List<String>>() {}.getType());
                    meta.setLore(lore);
                }
                if (metaData.has("Enchantments")) {
                    JsonArray enchantments = metaData.getAsJsonArray("Enchantments");
                    for (int i = 0; i < enchantments.size(); i++) {
                        JsonObject enchantmentObj = enchantments.get(i).getAsJsonObject();
                        String enchantName = enchantmentObj.get("Enchantment").getAsString();
                        int level = enchantmentObj.get("Level").getAsInt();

                        // Check if the enchantment is valid
                        Enchantment enchantment = Enchantment.getByName(enchantName);
                        if (enchantment != null) {
                            item.addUnsafeEnchantment(enchantment, level);
                        } else {
                            System.out.println("Invalid enchantment: " + enchantName);  // Log invalid enchantments
                        }
                    }
                }
                if (metaData.has("Unbreakable")) {
                    meta.setUnbreakable(metaData.get("Unbreakable").getAsBoolean());
                }
                item.setItemMeta(meta);
            }
            inventory[slotIndex] = item;
        }

        return inventory;
    }

    public static void create(Player ply) {
        String checkQuery = "SELECT INVENTORY FROM sc_userdata WHERE UUID = ?";
        try (PreparedStatement ps = QueryHandler.prepareStatement(checkQuery)) {
            ps.setString(1, ply.getUniqueId().toString());

            String insertQuery = "INSERT INTO sc_userdata (UUID, USERNAME, SIZE, INVENTORY) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertPs = QueryHandler.prepareStatement(insertQuery)) {
                insertPs.setString(1, ply.getUniqueId().toString());
                insertPs.setString(2, ply.getName());
                insertPs.setInt(3, SimplyChest.get().getConfig().getInt("simplychest.settings.size"));
                insertPs.setString(4, DEFAULT_INVENTORY_JSON);

                insertPs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user data for player: " + ply.getName(), e);
        }
    }

    public static void open(Player ply) {
        List<Object> data = getUserData(ply.getUniqueId().toString());
        if (data.isEmpty()) {
            ply.sendMessage("No data found for your user. Please create your profile first.");
            return;
        }

        int size = (int) data.get(1);
        String inventoryJSON = (String) data.get(2);

        Inventory inv = Bukkit.createInventory(ply, size, "Your Chest");
        ItemStack[] items = decodeJSON(inventoryJSON);
        inv.setContents(items);

        ply.openInventory(inv);
    }
}
