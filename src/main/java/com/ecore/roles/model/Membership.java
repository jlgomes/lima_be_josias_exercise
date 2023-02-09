package com.ecore.roles.model;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.client.model.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "team_id", "user_id"}))
public class Membership {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    @EqualsAndHashCode.Include
    private Role role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Include
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @EqualsAndHashCode.Include
    private Team team;
}
