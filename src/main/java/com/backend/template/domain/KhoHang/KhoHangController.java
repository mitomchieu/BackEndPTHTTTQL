package com.backend.template.domain.KhoHang;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.KhoHang.dto.CreateKhoHangDTO;
import com.backend.template.domain.KhoHang.model.KhoHangEntity;
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
@RequestMapping("kho-hang")
@ApiCommonResponse
public class KhoHangController {

    @Autowired
    KhoHangService khoHangService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create kho hang", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createKhoHang(
            @Valid @RequestBody CreateKhoHangDTO createKhoHangDTO
            ) {
        KhoHangEntity khoHangEntity = new KhoHangEntity();
        khoHangEntity.setMaKho(createKhoHangDTO.getMaKho());
        khoHangEntity.setTenKho(createKhoHangDTO.getTenKho());
        khoHangEntity.setDiaChi(createKhoHangDTO.getDiaChi());
        return ResponseTool.POST_OK(this.khoHangService.createKhoHang(khoHangEntity));
    }

    @GetMapping(path = "get/{maKhoHang}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get Kho hang by id", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getKhoHangById(
            @PathVariable String maKhoHang
    ) {
        return  ResponseTool.GET_OK(this.khoHangService.getByMaKhoHang(maKhoHang));
    }

    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all kho hang", description = "role = ADMIN, USer", security = @SecurityRequirement(name = "bearer-jwt"))
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllKhoHang(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter
    ) throws BackendError {
        APIPagingResponse result = this.khoHangService.getAll(pageable, searchParameter);
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }

    @GetMapping(path = "get-all-hang-hoa/{maKhoHang}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all hang hoa trong kho", description =  "role = ADMIN, USER. Cai nay chi cho search theo so luong hang", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getAllHangHoaTrongKho(
            @ParameterObject SearchParameter searchParameter,
            @RequestParam(value = "maHangHoa", required = false) String maHangHoa,
            @PathVariable("maKhoHang") String maKhoHang
            ) {
        Object result = this.khoHangService.getAllHangHoaTrongKho(maKhoHang, maHangHoa, searchParameter);
        return ResponseTool.GET_OK(result);
    }

}
