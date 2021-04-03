package com.backend.template.modules.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotNull
    @Size(min = 5, max = 20)
    private String username;
    
    @NotNull
    @Size(min = 5, max = 20)
    private String password;
}
