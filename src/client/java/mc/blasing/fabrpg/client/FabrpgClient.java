package mc.blasing.fabrpg.client;

import mc.blasing.fabrpg.client.network.ClientNetworkHandler;
import net.fabricmc.api.ClientModInitializer;

public class FabrpgClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetworkHandler.registerClientPackets();
    }
}