package com.github.tax1driver.sectormanager.objects.representative.enums;

public enum AuthenticationState {
    AWAITING_CHALLENGE, // the client has connected and is now awaiting his challenge.
    AWAITING_CHALLENGE_RESPONSE, // we have sent challenge to the client, now we are awaiting his response
    FAILED, // the client has supplied invalid signature
    SUCCESSFUL
}
