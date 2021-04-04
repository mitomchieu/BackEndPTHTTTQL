package com.backend.template.modules.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.backend.template.common.ConstSetting.TableName;
import com.backend.template.modules.auth.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Size(max = 20)
    private String username;

    @Column()
    @NotNull
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    @Schema(hidden = true)
    @ToString.Exclude
    private Role roles;
}
