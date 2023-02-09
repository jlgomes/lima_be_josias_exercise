package com.ecore.roles.utils;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.client.model.User;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {

    public static final UUID UUID_1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID UUID_2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID UUID_3 = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public static final UUID UUID_4 = UUID.fromString("44444444-4444-4444-4444-444444444444");

    public static final UUID DEVELOPER_ROLE_UUID = UUID.fromString("1b3c333b-36e7-4b64-aa15-c22ed5908ce4");
    public static final UUID PRODUCT_OWNER_UUID = UUID.fromString("25bbb7d2-26f3-11ec-9621-0242ac130002");
    public static final UUID TESTER_ROLE_UUID = UUID.fromString("37969e22-26f3-11ec-9621-0242ac130002");

    public static final UUID GIANNI_USER_UUID = UUID.fromString("fd282131-d8aa-4819-b0c8-d9e0bfb1b75c");

    public static final UUID ORDINARY_CORAL_LYNX_TEAM_UUID =
            UUID.fromString("7676a4bf-adfe-415c-941b-1739af07039b");

    public static final UUID DEFAULT_MEMBERSHIP_UUID =
            UUID.fromString("98de61a0-b9e3-11ec-8422-0242ac120002");

    public static Role DEVELOPER_ROLE() {
        return Role.builder()
                .id(DEVELOPER_ROLE_UUID)
                .name("Developer").build();
    }

    public static Role PRODUCT_OWNER_ROLE() {
        return Role.builder()
                .id(PRODUCT_OWNER_UUID)
                .name("Product Owner").build();
    }

    public static Role TESTER_ROLE() {
        return Role.builder()
                .id(TESTER_ROLE_UUID)
                .name("Tester").build();
    }

    public static Role DEVOPS_ROLE() {
        return Role.builder()
                .name("DevOps").build();
    }

    public static Team ORDINARY_CORAL_LYNX_TEAM(boolean full) {
        Team team = Team.builder()
                .id(ORDINARY_CORAL_LYNX_TEAM_UUID)
                .name("System Team").build();
        if (full) {
            User teamLeader = CREATE_USER(UUID_1, "User 1");
            team.setTeamLead(teamLeader);

            List<User> teamMembers = new ArrayList<>();
            teamMembers.add(CREATE_USER(UUID_2, "User 2"));
            teamMembers.add(CREATE_USER(UUID_3, "User 3"));
            teamMembers.add(GIANNI_USER());

            team.setTeamMembers(teamMembers);
        }
        return team;
    }

    public static Team ORDINARY_CORAL_LYNX_TEAM() {
        return ORDINARY_CORAL_LYNX_TEAM(true);
    }

    public static User GIANNI_USER(boolean full) {
        User user = User.builder()
                .id(GIANNI_USER_UUID)
                .displayName("gianniWehner").build();
        if (full) {
            user.setFirstName("Gianni");
            user.setLastName("Wehner");
            user.setAvatarUrl("https://cdn.fakercloud.com/avatars/rude_128.jpg");
            user.setLocation("Brakusstad");
        }
        return user;
    }

    public static User GIANNI_USER() {
        return GIANNI_USER(true);
    }

    private static User CREATE_USER(UUID id, String displayName) {
        User user = User.builder()
                .id(id)
                .displayName(displayName).build();
        return user;
    }

    public static Membership DEFAULT_MEMBERSHIP() {
        return Membership.builder()
                .id(DEFAULT_MEMBERSHIP_UUID)
                .role(DEVELOPER_ROLE())
                .user(GIANNI_USER(false))
                .team(ORDINARY_CORAL_LYNX_TEAM(false))
                .build();
    }

    public static Membership INVALID_MEMBERSHIP() {
        return Membership.builder()
                .id(DEFAULT_MEMBERSHIP_UUID)
                .role(DEVELOPER_ROLE())
                .user(CREATE_USER(UUID_4, "User 4"))
                .team(ORDINARY_CORAL_LYNX_TEAM(false))
                .build();
    }

}
