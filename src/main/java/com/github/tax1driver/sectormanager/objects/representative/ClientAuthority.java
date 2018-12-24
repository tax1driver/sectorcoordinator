package com.github.tax1driver.sectormanager.objects.representative;

import com.github.tax1driver.sectormanager.objects.representative.enums.AuthenticationState;

public class ClientAuthority {
    private AuthenticationState state;
    private long timeout;
    private String serverName;

    public ClientAuthority() {
        this.state = AuthenticationState.AWAITING_CHALLENGE;
        this.timeout = System.currentTimeMillis() + 5000L;
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
