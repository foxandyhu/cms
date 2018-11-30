package com.bfly.common.ipseek;


public interface IPSeeker {
    int IP_RECORD_LENGTH = 7;
    byte REDIRECT_MODE_1 = 0x01;
    byte REDIRECT_MODE_2 = 0x02;

    IPLocation getIPLocation(String ip);
}
