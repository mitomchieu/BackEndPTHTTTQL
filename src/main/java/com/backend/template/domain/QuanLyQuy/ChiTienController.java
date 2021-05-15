package com.backend.template.domain.QuanLyQuy;

import javax.validation.Valid;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.QuanLyQuy.dto.CreateGiaoDichDTO;
import com.backend.template.domain.QuanLyQuy.model.ChiTienEntity;

import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController()
@RequestMapping("giao-dich/chi-tien")
@ApiCommonResponse
public class ChiTienController {

    @Autowired
    private ChiTienService chiTienService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create chi tien", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createChiTien(
            @Valid @RequestBody CreateGiaoDichDTO createGiaoDichDTO
    ) {
        ChiTienEntity chiTienEntity = new ChiTienEntity();
        chiTienEntity.setDoiTuongGiaoDich(createGiaoDichDTO.getDoiTuongGiaoDich());
        chiTienEntity.setTongTien(createGiaoDichDTO.getTongTien());
        chiTienEntity.setMaGiaoDich(createGiaoDichDTO.getMaGiaoDich());
        chiTienEntity.setThoiGianGiaoDich(createGiaoDichDTO.getThoiGianGiaoDich());
        chiTienEntity.setThoiGianHachToan(createGiaoDichDTO.getThoiGianHachToan());
        chiTienEntity.setIsTienMat(chiTienEntity.getIsTienMat());
        chiTienEntity.setLyDo(createGiaoDichDTO.getLyDo());
        ChiTienEntity result = this.chiTienService.createChiTienEntity(chiTienEntity);
        return ResponseTool.POST_OK(result);
    }

    @GetMapping(path = "get/{maGiaoDich}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create chi tien by id", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public  ResponseEntity<APIResponse> getChiTien(
            @PathVariable String maGiaoDich
    ) {
        return  ResponseTool.GET_OK(this.chiTienService.getByMaGiaoDich(maGiaoDich));
    }

    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all chi tien", description =  "role = ADMIN", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllChiTien(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter
    ) throws BackendError {
        APIPagingResponse result = this.chiTienService.getAll(pageable, searchParameter);
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }
}
