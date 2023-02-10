package com.ecore.roles.service.impl;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.repository.TeamRepository;
import com.ecore.roles.service.MembershipsService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
public class MembershipsServiceImpl implements MembershipsService {

    private final MembershipRepository membershipRepository;
    private final RoleRepository roleRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MembershipsServiceImpl(
            MembershipRepository membershipRepository,
            RoleRepository roleRepository, TeamRepository teamRepository) {
        this.membershipRepository = membershipRepository;
        this.roleRepository = roleRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Membership assignRoleToMembership(@NonNull Membership membership) {

        UUID roleId = ofNullable(membership.getRole()).map(Role::getId)
                .orElseThrow(() -> new InvalidArgumentException(Role.class));
        UUID teamId = ofNullable(membership.getTeam()).map(Team::getId)
                .orElseThrow(() -> new InvalidArgumentException(Team.class));

        if (membershipRepository.findByUserIdAndTeamId(membership.getUser().getId(), membership.getTeam().getId())
                .isPresent()) {
            throw new ResourceExistsException(Membership.class);
        }

        roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));
        teamRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
        return membershipRepository.save(membership);
    }

    @Override
    public List<Membership> getMemberships(@NonNull UUID roleId) {
        return membershipRepository.findByRoleId(roleId);
    }
}
