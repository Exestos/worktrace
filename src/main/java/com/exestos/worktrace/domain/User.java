package com.exestos.worktrace.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_admin")
    private boolean isAdmin = false;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<GroupRole> roles;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<Group> groups;

}
