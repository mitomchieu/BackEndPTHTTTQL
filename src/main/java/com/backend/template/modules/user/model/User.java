package com.backend.template.modules.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.backend.template.common.ConstSetting.TableName;
import com.backend.template.modules.auth.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = TableName.USER_DB)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(unique = true)
    @Size(max = 20)
    private String username;

    @Column()
    @NotNull
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    @Schema(hidden = true)
    private Role roles;
}
