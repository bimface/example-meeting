package com.bimface.meeting.utils;

/**
 * @author dup, 2017-11-24
 */
public class IdGenerator {

    public static long generateId() {
        long leftLimit = 10000000L;
        long rightLimit = leftLimit * 10;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }
}
