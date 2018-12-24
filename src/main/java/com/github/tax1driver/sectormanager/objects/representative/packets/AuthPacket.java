package com.github.tax1driver.sectormanager.objects.representative.packets;

import com.github.tax1driver.sectormanager.objects.representative.Client;
import com.github.tax1driver.sectormanager.objects.representative.enums.KickReason;

public class AuthPacket extends GenericPacket {
    /*
    * Key verification is done with RSA
    * The client has a private key with which it signs a challenge of 4096 bytes,
    * then sends it to the server.
    * The server then verifies the challenge using server's public key.
    * If succeeded the client is authenticated.
    * */


    enum AuthStep {
        CHALLENGE_MESSAGE, // sent by server, handled on client
        SIGNATURE_MESSAGE  // sent by client, handled on server
    }

    private AuthStep step;

    /*
    * This field is used:
    *    a) at step 1 (server-client) as challenge message,
    *    b) at step 2 (client-server) as signature
    */
    private byte[] bytes;

    public byte[] getByteField() {
        return bytes;
    }

    public AuthStep getStep() {
        return step;
    }

}
