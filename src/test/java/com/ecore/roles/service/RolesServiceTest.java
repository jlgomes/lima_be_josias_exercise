package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.impl.RolesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.ecore.roles.utils.TestData.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolesServiceTest {

    @InjectMocks
    private RolesServiceImpl rolesService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private MembershipsService membershipsService;

    @Test
    public void shouldCreateRole() {
        Role developerRole = DEVELOPER_ROLE();
        when(roleRepository.save(developerRole)).thenReturn(developerRole);

        Role role = rolesService.CreateRole(developerRole);

        assertNotNull(role);
        assertEquals(developerRole, role);
    }

    @Test
    public void shouldFailToCreateRoleWhenRoleIsNull() {
        assertThrows(NullPointerException.class,
                () -> rolesService.CreateRole(null));
    }

    @Test
    public void shouldReturnRoleWhenRoleIdExists() {
        Role developerRole = DEVELOPER_ROLE();
        when(roleRepository.findById(developerRole.getId())).thenReturn(Optional.of(developerRole));

        Role role = rolesService.GetRole(developerRole.getId());

        assertNotNull(role);
        assertEquals(developerRole, role);
    }

    @Test
    public void shouldFailToGetRoleWhenRoleIdDoesNotExist() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.GetRole(UUID_1));

        assertEquals(format("Role %s not found", UUID_1), exception.getMessage());
    }

    @Test
    public void shouldFailToGetRoleWhenUserIdAndTeamIdDoesNotExist() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.GetRole(UUID_1, UUID_2));

        assertEquals(format("Role %s %s not found", UUID_1, UUID_2), exception.getMessage());
    }

    @Test
    public void shouldFailToGetRoleWhenUserIdDoesNotExist() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.GetRole(UUID_3, ORDINARY_CORAL_LYNX_TEAM_UUID));

        assertEquals(format("Role %s %s not found", UUID_3, ORDINARY_CORAL_LYNX_TEAM_UUID), exception.getMessage());
    }

    @Test
    public void shouldFailToGetRoleWhenTeamIdDoesNotExist() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.GetRole(GIANNI_USER_UUID, UUID_4));

        assertEquals(format("Role %s %s not found", GIANNI_USER_UUID, UUID_4), exception.getMessage());
    }

    @Test
    public void shouldReturnRoleWhenUserIdAndTeamIdExists() {
        Membership membership = DEFAULT_MEMBERSHIP();
        when(membershipRepository.findByUserIdAndTeamId(GIANNI_USER_UUID, ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(Optional.of(membership));

        Role role = rolesService.GetRole(membership.getUser().getId(), membership.getTeam().getId());

        assertNotNull(role);
        assertEquals(membership.getRole(), role);
    }
}
