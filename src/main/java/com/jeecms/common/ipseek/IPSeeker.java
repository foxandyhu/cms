package com.jeecms.common.ipseek;


public interface IPSeeker {
    static final int IP_RECORD_LENGTH = 7;
    static final byte REDIRECT_MODE_1 = 0x01;
    static final byte REDIRECT_MODE_2 = 0x02;

    public IPLocation getIPLocation(String ip);
}
