package com.backend.template.domain.HangHoa.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long donGia;
}
