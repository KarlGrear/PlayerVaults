package com.drtshock.playervaults.vaultmanagement;

public class VaultDataContainer {
    public VaultDataContainer(String serverName, int playerVaultKey, String playerVaultData) {
        this.serverName = serverName;
        this.playerVaultKey = playerVaultKey;
        this.playerVaultData = playerVaultData;
    }

    public String serverName;
    public int playerVaultKey;
    public String playerVaultData;
}
