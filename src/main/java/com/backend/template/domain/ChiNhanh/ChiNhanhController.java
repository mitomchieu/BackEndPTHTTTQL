package com.backend.template.domain.ChiNhanh;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.ChiNhanh.dto.ChiNhanhDTO;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("chi-nhanh")
public class ChiNhanhController {

    @Autowired
    private ChiNhanhService chiNhanhService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create chi nhanh", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createChiNhanh(
            @Valid @RequestBody ChiNhanhDTO chiNhanhDto
    ) {
        ChiNhanhEntity chiNhanhEntity = new ChiNhanhEntity();
        chiNhanhEntity.setMaChiNhanh(chiNhanhDto.getMaChiNhanh());
        chiNhanhEntity.setTenChiNhanh(chiNhanhDto.getTenChiNhanh());
        chiNhanhEntity.setDiaChi(chiNhanhDto.getDiaChi());
        this.chiNhanhService.createChiNhanh(chiNhanhEntity);
        return ResponseTool.GET_OK(chiNhanhEntity);
    }

    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all chi nhanh", description =  "role = ADMIN, USer", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllKhoHang(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter
    ) throws BackendError {
        APIPagingResponse result = this.chiNhanhService.getAll(pageable, searchParameter);
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }

}
