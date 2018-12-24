package com.github.tax1driver.sectormanager.objects.representative;

import com.github.tax1driver.sectormanager.objects.representative.enums.AuthenticationState;

import java.security.Signature;
import java.util.concurrent.ThreadLocalRandom;

public class ClientAuthority {
    public static final int CHALLENGE_SIZE = 4096;

    private AuthenticationState state;
    private byte[] challengeBuffer;

    private String serverName;

    private long timeout;

    public ClientAuthority() {
        this.state = AuthenticationState.AWAITING_CHALLENGE;
        this.timeout = System.currentTimeMillis() + 5000L;

        this.challengeBuffer = new byte[CHALLENGE_SIZE];
        ThreadLocalRandom.current().nextBytes(this.challengeBuffer);

    }

    public byte[] getChallengeBuffer() {
        return challengeBuffer;
    }

    public AuthenticationState getState() {
        return state;
    }

    public void setState(AuthenticationState state) {
        this.state = state;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
