package com.ecore.roles.web.dto;

import com.ecore.roles.model.Membership;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembershipDto {

    @JsonProperty
    private UUID id;

    @JsonProperty
    @Valid
    @NotNull
    @EqualsAndHashCode.Include
    private RoleDto role;

    @JsonProperty(value = "teamMember")
    @Valid
    @NotNull
    @EqualsAndHashCode.Include
    private UserDto user;

    @JsonProperty
    @Valid
    @NotNull
    @EqualsAndHashCode.Include
    private TeamDto team;

    public static MembershipDto fromModel(Membership membership) {
        if (membership == null) {
            return null;
        }
        return MembershipDto.builder()
                .id(membership.getId())
                .role(RoleDto.fromModel(ofNullable(membership.getRole()).orElse(null)))
                .user(UserDto.fromModel(ofNullable(membership.getUser()).orElse(null)))
                .team(TeamDto.fromModel(ofNullable(membership.getTeam()).orElse(null)))
                .build();
    }

    public Membership toModel() {
        return Membership.builder()
                .id(this.id)
                .role(RoleDto.toModel(ofNullable(this.role).orElse(null)))
                .user(UserDto.toModel(ofNullable(this.user).orElse(null)))
                .team(TeamDto.toModel(ofNullable(this.team).orElse(null)))
                .build();
    }

}
