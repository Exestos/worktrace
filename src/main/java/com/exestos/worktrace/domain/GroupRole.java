package com.exestos.worktrace.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "group_roles")
@Getter
@Setter
@RequiredArgsConstructor
public class GroupRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @Column(name="privileges")
    private Long privileges;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_roles_users",
            joinColumns = @JoinColumn(name = "group_role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}

