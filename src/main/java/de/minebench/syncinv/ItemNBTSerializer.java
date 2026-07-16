package de.minebench.syncinv;

/*
 * SyncInv
 * Copyright (c) 2021 Max Lee aka Phoenix616 (max@themoep.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;

/**
 * Serializes single {@link ItemStack}s to raw NBT (and back) via the NBTAPI plugin
 * (https://www.spigotmc.org/resources/nbt-api.7939/, declared as a hard depend in plugin.yml).
 * Round-trips the item's real NMS NBT data directly instead of going through
 * {@link ItemStack#serializeAsBytes()}'s data-version conversion path, which is what was
 * causing lore to get lost on sync.
 */
public class ItemNBTSerializer {

    public static byte[] serialize(ItemStack item) {
        ReadWriteNBT nbt = NBT.itemStackToNBT(item);
        return nbt.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static ItemStack deserialize(byte[] bytes) {
        String json = new String(bytes, StandardCharsets.UTF_8);
        return NBT.itemStackFromNBT(NBT.parseNBT(json));
    }
}
