package com.ecore.roles.web.dto;

import com.ecore.roles.client.model.Team;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TeamDto {

    @JsonProperty
    private UUID id;

    @JsonProperty
    private String name;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto teamLead;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDto> teamMembers;

    public static TeamDto fromModel(Team team) {
        if (team == null) {
            return null;
        }
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .teamLead(UserDto.fromModel(ofNullable(team.getTeamLead()).orElse(null)))
                .teamMembers(team.getTeamMembers()
                        .stream().map(user -> UserDto.fromModel(ofNullable(user).orElse(null)))
                        .collect(Collectors.toList()))
                .build();
    }

    public static Team toModel(TeamDto teamDto) {
        if (teamDto == null) {
            return null;
        }
        return Team.builder()
                .id(teamDto.getId())
                .name(teamDto.getName())
                .teamLead(UserDto.toModel(ofNullable(teamDto.getTeamLead()).orElse(null)))
                .teamMembers(teamDto.getTeamMembers()
                        .stream().map(userDto -> UserDto.toModel(ofNullable(userDto).orElse(null)))
                        .collect(Collectors.toList()))
                .build();
    }
}
