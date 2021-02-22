package com.backend.template.modules.user.model;


import lombok.Data;
import lombok.Generated;

import javax.persistence.*;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column()
    private String password;
}
