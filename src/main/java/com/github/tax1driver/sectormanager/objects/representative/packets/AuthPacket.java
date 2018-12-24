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


    /*
    * This field is used:
    *    a) at step 1 (server-client) as challenge message,
    *    b) at step 2 (client-server) as signature
    */
    private byte[] bytes;

    private String identity;



    public byte[] getByteField() {
        return bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public AuthPacket(byte[] bytes, String identity) {
        this.bytes = bytes;
        this.identity = identity;
    }
}
