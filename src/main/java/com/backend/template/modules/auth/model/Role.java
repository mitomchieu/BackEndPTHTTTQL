package com.backend.template.modules.auth.model;


import com.backend.template.common.ConstSetting.TableName;
import com.backend.template.modules.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = TableName.ROLES_DB)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column()
    private String name;

    @OneToMany(mappedBy = TableName.ROLES_DB, cascade = CascadeType.ALL)
    @Schema(hidden = true)
    @ToString.Exclude
    private Set<User> user;
}
