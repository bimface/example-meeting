package com.bimface.meeting.holder;

import com.bimface.meeting.bean.User;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 用户集合，key为sessionId
 *
 * @author dup, 2017-11-24
 */
public class UserHolder {
    public static final String COMPERE = "compere";
    public static final ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();
    public static final Map<String, User> compereMap = new HashMap<>();

    public static List<User> getUserList() {
        List<User> users = UserHolder.userMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        users.sort(Comparator.comparing(User::getCreateTime));
        users.sort(Comparator.comparing(User::getRole).reversed());
        return users;
    }
}
