package com.github.tax1driver.sectormanager.objects.representative.enums;

public enum KickReason {
    UNKNOWN,

    // client failed to authorize within time
    AUTHORIZATION_FAILED,

    // client has tried to execute secure requests when unauthorized
    UNAUTHORIZED,

    // client was manually kicked
    MANUAL,
}
