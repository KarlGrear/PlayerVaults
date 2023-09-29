/*
 * PlayerVaultsX
 * Copyright (C) 2013 Trent Hensler
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.drtshock.playervaults.vaultmanagement;

import com.drtshock.playervaults.PlayerVaults;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class VaultManager<T> implements IVaultManager {

    private static final String VAULTKEY = "vault%d";
    private static IVaultManager instance;
    private final File directory = PlayerVaults.getInstance().getVaultData();
    private final Map<String, YamlConfiguration> cachedVaultFiles = new ConcurrentHashMap<>();
    private final PlayerVaults plugin;

    public VaultManager(T t, PlayerVaults plugin) {
        this.plugin = plugin;
        instance = (IVaultManager) t;
    }

    /**
     * Get the instance of this class.
     *
     * @return - instance of this class.
     */
    public static IVaultManager getInstance() {
        return instance;
    }

    /**
     * Saves the inventory to the specified player and vault number.
     *
     * @param inventory The inventory to be saved.
     * @param target The player of whose file to save to.
     * @param number The vault number.
     */
    @Override
    public void saveVault(Inventory inventory, String target, int number) {
        instance.saveVault(inventory,target,number);
    }

    /**
     * Load the player's vault and return it.
     *
     * @param player The holder of the vault.
     * @param number The vault number.
     */
    @Override
    public Inventory loadOwnVault(Player player, int number, int size) {
      return instance.loadOwnVault(player,number, size);
    }

    /**
     * Load the player's vault and return it.
     *
     * @param name The holder of the vault.
     * @param number The vault number.
     */
    @Override
    public Inventory loadOtherVault(String name, int number, int size) {
       return instance.loadOtherVault(name, number, size);
    }

    /**
     * Get an inventory from file. Returns null if the inventory doesn't exist. SHOULD ONLY BE USED INTERNALLY
     *
     * @param vaultDataContainerList the List of VaultDataContainer objects file.
     * @param size the size of the vault.
     * @param number the vault number.
     * @return inventory if exists, otherwise null.
     */
    @Override
    public Inventory getInventory(InventoryHolder owner, String ownerName,  List<VaultDataContainer> vaultDataContainerList, int size, int number, String title) {
       return instance.getInventory(owner, ownerName, vaultDataContainerList,size,number,title);
    }

    /**
     * Gets an inventory without storing references to it. Used for dropping a players inventories on death.
     *
     * @param holder The holder of the vault.
     * @param number The vault number.
     * @return The inventory of the specified holder and vault number. Can be null.
     */
    @Override
    public Inventory getVault(String holder, int number) {
        return instance.getVault(holder, number);
    }

    /**
     * Checks if a vault exists.
     *
     * @param holder holder of the vault.
     * @param number vault number.
     * @return true if the vault file and vault number exist in that file, otherwise false.
     */
    @Override
    public boolean vaultExists(String holder, int number) {
       return instance.vaultExists(holder, number);
    }

    /**
     * Gets the numbers belonging to all their vaults.
     *
     * @param holder holder
     * @return a set of Integers, which are player's vaults' numbers (fuck grammar).
     */
    @Override
    public Set<Integer> getVaultNumbers(String holder) {
        return instance.getVaultNumbers(holder);
    }

    @Override
    public void deleteAllVaults(String holder) {
        instance.deleteAllVaults(holder);
    }

    /**
     * Deletes a players vault.
     *
     * @param sender The sender of whom to send messages to.
     * @param holder The vault holder.
     * @param number The vault number.
     */
    @Override
    public void deleteVault(CommandSender sender, final String holder, final int number) {
        instance.deleteVault(sender,holder,number);
    }

    // Should only be run asynchronously
    @Override
    public void cachePlayerVaultFile(String holder) {
       instance.cachePlayerVaultFile(holder);
    }

    @Override
    public void removeCachedPlayerVaultFile(String holder) {
        instance.removeCachedPlayerVaultFile(holder);
    }

    /**
     * Get the holder's vault file. Create if doesn't exist.
     *
     * @param holder The vault holder.
     * @return The holder's vault config file.
     */
    @Override
    public List<VaultDataContainer> getPlayerVaultFile(String holder, boolean createIfNotFound) {
       return instance.getPlayerVaultFile(holder, createIfNotFound);
    }

    @Override
    public List<VaultDataContainer> loadPlayerVaultFile(String holder) {

        return instance.loadPlayerVaultFile(holder);
    }

    /**
     * Attempt to delete a vault file.
     *
     * @param holder UUID of the holder.
     */
    @Override
    public void deletePlayerVaultFile(String holder) {
       instance.deletePlayerVaultFile(holder);
    }

    @Override
    public List<VaultDataContainer> loadPlayerVaultFile(String uniqueId, boolean createIfNotFound) {
        return instance.loadPlayerVaultFile(uniqueId, createIfNotFound);
    }

    @Override
    public void saveFileSync(final String holder, final List<VaultDataContainer> vaultDataContainerList) {
    instance.saveFileSync(holder, vaultDataContainerList);
    }
}
