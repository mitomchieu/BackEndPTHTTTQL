/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.template.domain.NhanVien;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.ChiNhanh.ChiNhanhService;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import com.backend.template.domain.NhanVien.dto.CreateNhanVienDTO;
import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 *
 * @author chudu
 */
@RestController
@RequestMapping("nhan-vien")
@ApiCommonResponse
public class NhanVienController {

    @Autowired
    private ChiNhanhService chiNhanhService;

    @Autowired
    private NhanVienService nhanVienService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary =  "Create Nhan Vien",
            description = "role = Admin, User",
            security = @SecurityRequirement(name = "bearer-jwt" )
    )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createNhanVien (
        @Valid @RequestBody CreateNhanVienDTO createNhanVienDTO
    ) throws BackendError {
        NhanVienEntity nhanVienEntity = new NhanVienEntity();
        nhanVienEntity.setMaNhanVien(createNhanVienDTO.getMaNhanVien());
        nhanVienEntity.setTenNhanVien(createNhanVienDTO.getTenNhanVien());
        nhanVienEntity.setNgaySinh(createNhanVienDTO.getNgaySinh());
        ChiNhanhEntity chiNhanhEntity  = this.chiNhanhService
                    .getChiNhanhByMaChiNhanhh(createNhanVienDTO.getMaChiNhanh());
        if (Objects.isNull(chiNhanhEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã chi nhánh không hợp lệ");
        }
        nhanVienEntity.setChiNhanh(chiNhanhEntity);
        return ResponseTool.POST_OK(this.nhanVienService
                                    .createNhanVien(nhanVienEntity));
    }

    public ResponseEntity<APIResponse> getNhanVienByMaNhanVien(
             @PathVariable("maNhanVien") String maNhanVien
    ) {
        return ResponseTool.GET_OK(this.nhanVienService.getByMaNhanVien(maNhanVien));
    }

    @GetMapping(path = "get-all-by-ma-chi-nhanh", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all nhan vien by ma chi nhanh", description =  "role = ADMIN", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse>  getAllNhanVienByMaChiNhanh(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter,
            @RequestParam(value = "maChiNhanh", required = false, defaultValue = "") String maChiNhanh
    ) throws BackendError {
        APIPagingResponse result = this.nhanVienService.getAllByMaChiNhanh(
                maChiNhanh,
                pageable,
                searchParameter
        );
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }

}
