package com.github.tax1driver.sectormanager.objects.representative.packets;

import com.github.tax1driver.sectormanager.objects.representative.enums.ConnectionSide;
import com.github.tax1driver.sectormanager.objects.representative.enums.DisconnectReason;
import com.github.tax1driver.sectormanager.objects.representative.enums.KickReason;

public class DisconnectPacket extends GenericPacket {
    /*
     * This packet is sent before a disconnect happens.
     */


    private ConnectionSide initiator;
    private DisconnectReason reason;

    // present if initiator == ConnectionSide.SERVER && reason == DisconnectReason.KICK
    private KickReason kickReason;

    // present if initiator == ConnectionSide.SERVER && reason == DisconnectReason.KICK && kickReason.UNKNOWN/MANUAL
    private String kickMessage;




    public ConnectionSide getInitiator() {
        return initiator;
    }

    public void setInitiator(ConnectionSide initiator) {
        this.initiator = initiator;
    }

    public DisconnectReason getReason() {
        return reason;
    }

    public void setReason(DisconnectReason reason) {
        this.reason = reason;
    }

    public KickReason getKickReason() {
        return kickReason;
    }

    public void setKickReason(KickReason kickReason) {
        this.kickReason = kickReason;
    }

    public String getKickMessage() {
        return kickMessage;
    }

    public void setKickMessage(String kickMessage) {
        this.kickMessage = kickMessage;
    }
}
