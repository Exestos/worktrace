package com.exestos.worktrace.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "group_roles")
@Getter
@Setter
@RequiredArgsConstructor
public class GroupRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<GroupPrivilege> privileges;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "grole_users",
            joinColumns = @JoinColumn(name = "group_role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}

