package com.backend.template.domain.HangHoa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHangHoaDTO {

    @NotNull
    private  String maHangHoa;

    @NotNull
    private  String tenHangHoa;

    private Long thoiHanBaoHanh;

    private String nguonGoc;

    private String moTa;
}
