package com.backend.template.domain.QuanLyQuy;

import javax.validation.Valid;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.QuanLyQuy.dto.CreateGiaoDichDTO;
import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;

import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("quan-ly-quy/thu-tien")
@ApiCommonResponse
public class ThuTienController {

    private  ThuTienService thuTienService;

    @Autowired
    ThuTienController(@Qualifier("ThuTienService") ThuTienService thuTienService) {
        this.thuTienService = thuTienService;
    }

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create thu tien", description =  "role = ADMIN, USER", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createThuTien(
            @Valid @RequestBody CreateGiaoDichDTO createGiaoDichDTO
    ) {
        ThuTienEntity thuTienEntity = new ThuTienEntity();
        thuTienEntity.setDoiTuongGiaoDich(createGiaoDichDTO.getDoiTuongGiaoDich());
        thuTienEntity.setTongTien(createGiaoDichDTO.getTongTien());
        thuTienEntity.setMaGiaoDich(createGiaoDichDTO.getMaGiaoDich());
        thuTienEntity.setThoiGianGiaoDich(createGiaoDichDTO.getThoiGianGiaoDich());
        thuTienEntity.setThoiGianHachToan(createGiaoDichDTO.getThoiGianHachToan());
        thuTienEntity.setLyDo(createGiaoDichDTO.getLyDo());
        ThuTienEntity result = this.thuTienService.createThuTienEntity(thuTienEntity);
        System.out.println(result);
        return ResponseTool.POST_OK(result);
    }
    
    @GetMapping(path = "get/{maGiaoDich}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get Thu tien", description =  "role = ADMIN, USER", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public  ResponseEntity<APIResponse> getThuTien(
            @PathVariable String maGiaoDich
    ) {
        return  ResponseTool.GET_OK(this.thuTienService.getByMaGiaoDich(maGiaoDich));
    }


    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get all thu tien", description =  "role = ADMIN, USER", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllThuTien(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter
            ) throws BackendError {
        APIPagingResponse result = this.thuTienService.getAll(pageable, searchParameter);
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }
}
