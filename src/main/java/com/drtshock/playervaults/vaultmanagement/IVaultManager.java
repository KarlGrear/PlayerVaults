package com.drtshock.playervaults.vaultmanagement;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;
import java.util.Set;


public interface IVaultManager {

    IVaultManager instance = null;
    /**
     * Get the instance of this class.
     *
     * @return - instance of this class.
     */
    static IVaultManager getInstance() {
        return instance;
    }

    /**
     * Saves the inventory to the specified player and vault number.
     *
     * @param inventory The inventory to be saved.
     * @param target The player of whose file to save to.
     * @param number The vault number.
     */
    void saveVault(Inventory inventory, String target, int number);

    /**
     * Load the player's vault and return it.
     *
     * @param player The holder of the vault.
     * @param number The vault number.
     */
    Inventory loadOwnVault(Player player, int number, int size);

    /**
     * Load the player's vault and return it.
     *
     * @param name The holder of the vault.
     * @param number The vault number.
     */
    Inventory loadOtherVault(String name, int number, int size);
    /**
     * Get an inventory from file. Returns null if the inventory doesn't exist. SHOULD ONLY BE USED INTERNALLY
     *
     * @param vaultDataContainerList the List VaultDataContainer objects.
     * @param size the size of the vault.
     * @param number the vault number.
     * @return inventory if exists, otherwise null.
     */
    Inventory getInventory(InventoryHolder owner, String ownerName,  List<VaultDataContainer> vaultDataContainerList, int size, int number, String title);

    /**
     * Gets an inventory without storing references to it. Used for dropping a players inventories on death.
     *
     * @param holder The holder of the vault.
     * @param number The vault number.
     * @return The inventory of the specified holder and vault number. Can be null.
     */
    Inventory getVault(String holder, int number);

    /**
     * Checks if a vault exists.
     *
     * @param holder holder of the vault.
     * @param number vault number.
     * @return true if the vault file and vault number exist in that file, otherwise false.
     */
    boolean vaultExists(String holder, int number);
    /**
     * Gets the numbers belonging to all their vaults.
     *
     * @param holder holder
     * @return a set of Integers, which are player's vaults' numbers (fuck grammar).
     */
    Set<Integer> getVaultNumbers(String holder);

    void deleteAllVaults(String holder);

    /**
     * Deletes a players vault.
     *
     * @param sender The sender of whom to send messages to.
     * @param holder The vault holder.
     * @param number The vault number.
     */
    void deleteVault(CommandSender sender, final String holder, final int number);

    // Should only be run asynchronously
    void cachePlayerVaultFile(String holder);

    void removeCachedPlayerVaultFile(String holder);

    /**
     * Get the holder's vault file. Create if doesn't exist.
     *
     * @param holder The vault holder.
     * @return The holder's vault config file.
     */
    List<VaultDataContainer> getPlayerVaultFile(String holder, boolean createIfNotFound);

    List<VaultDataContainer> loadPlayerVaultFile(String holder);

    /**
     * Attempt to delete a vault file.
     *
     * @param holder UUID of the holder.
     */
    void deletePlayerVaultFile(String holder);

    public List<VaultDataContainer> loadPlayerVaultFile(String uniqueId, boolean createIfNotFound);

    void saveFileSync(final String holder, final List<VaultDataContainer> vaultDataContainerList);

}
