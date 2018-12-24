package com.github.tax1driver.sectormanager.objects.representative.listeners;

import com.github.tax1driver.sectormanager.helpers.ClientManager;
import com.github.tax1driver.sectormanager.helpers.ConfigurationManager;
import com.github.tax1driver.sectormanager.helpers.EventManager;
import com.github.tax1driver.sectormanager.objects.networking.events.ConnectionAcceptedEvent;
import com.github.tax1driver.sectormanager.objects.networking.events.ConnectionIncomingDataEvent;
import com.github.tax1driver.sectormanager.objects.networking.events.ServerTickEvent;
import com.github.tax1driver.sectormanager.objects.prototyping.EventHandler;
import com.github.tax1driver.sectormanager.objects.prototyping.Listener;
import com.github.tax1driver.sectormanager.objects.representative.Client;
import com.github.tax1driver.sectormanager.objects.representative.enums.AuthenticationState;
import com.github.tax1driver.sectormanager.objects.representative.enums.KickReason;
import com.github.tax1driver.sectormanager.objects.representative.helpers.PacketDeserializer;
import com.github.tax1driver.sectormanager.objects.representative.enums.PacketType;
import com.github.tax1driver.sectormanager.objects.representative.SectorCommunicationPacket;
import com.github.tax1driver.sectormanager.objects.representative.packets.AuthPacket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.internal.Base64;
import sun.security.rsa.RSAKeyFactory;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class ConnectionListener implements Listener {

    public ConnectionListener() {
        EventManager.getInstance().addEventListener(this);
    }

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(SectorCommunicationPacket.class, new PacketDeserializer())
            .create();

    @EventHandler
    public void onDataReceived(ConnectionIncomingDataEvent e) {
        String stringRepresentation = new String(e.data(), StandardCharsets.UTF_8);
        SectorCommunicationPacket packet = gson.fromJson(stringRepresentation, SectorCommunicationPacket.class);
        Client client = ClientManager.getClientByConnection(e.getSender());

        try {
            if (packet.type() == PacketType.AUTH) {
                AuthPacket data = (AuthPacket) packet.data();

                if (client.getAuthority().getState() == AuthenticationState.AWAITING_CHALLENGE_RESPONSE) {
                    // Client already authorized, ignore this.
                    if (data.getIdentity() == null) {
                        client.getAuthority().setState(AuthenticationState.FAILED);
                        return;
                    }

                    for (String encodedKey : ConfigurationManager.getConfig().getPublicKeys().get(data.getIdentity())) {
                        byte[] publicKeyBytes = Base64.decode(encodedKey);

                        KeyFactory factory = KeyFactory.getInstance("SHA256withRSA");
                        X509EncodedKeySpec specification = new X509EncodedKeySpec(publicKeyBytes);

                        PublicKey publicKey = factory.generatePublic(specification);

                        Signature sig = Signature.getInstance("SHA256withRSA");

                        sig.initVerify(publicKey);
                        sig.update(client.getAuthority().getChallengeBuffer());

                        if (sig.verify(data.getByteField())) {
                            client.getAuthority().setState(AuthenticationState.SUCCESSFUL);
                            break;
                        }
                    }

                    if (client.getAuthority().getState() != AuthenticationState.SUCCESSFUL) {
                        client.getAuthority().setState(AuthenticationState.FAILED);
                    }
                }

            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onConnectionAccepted(ConnectionAcceptedEvent e) {
        ClientManager.addClient(new Client(e.getConnection()));
    }

    @EventHandler
    public void onTick(ServerTickEvent e) {
        for (Client client : ClientManager.getClients()) {
            long timeMillis = System.currentTimeMillis();

            if (timeMillis > client.getAuthority().getTimeout()) {
                client.kick(KickReason.AUTHORIZATION_FAILED);
                // Client failed to authenticate in time.
            }
        }
    }
}
