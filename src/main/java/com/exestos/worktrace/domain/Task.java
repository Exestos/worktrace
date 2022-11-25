package com.exestos.worktrace.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@RequiredArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}

