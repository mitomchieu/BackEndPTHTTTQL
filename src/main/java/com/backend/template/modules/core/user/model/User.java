package com.backend.template.modules.core.user.model;

import com.backend.template.base.common.ConstSetting.TableName;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.modules.core.auth.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = TableName.USER_DB)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    private Role role;
}
