package com.backend.template.domain.HangHoa;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.HangHoa.dto.CreateHangHoaDTO;
import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("hang-hoa")
@ApiCommonResponse
public class HangHoaController {

    @Autowired
    private HangHoaService hangHoaService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create hang hoa", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createstHangHoa(
            @Valid @RequestBody CreateHangHoaDTO createHangHoaDTO
            ) {
        HangHoaEntity hangHoaEntity = new HangHoaEntity();
        hangHoaEntity.setMaHangHoa(createHangHoaDTO.getMaHangHoa());
        hangHoaEntity.setTenHangHoa(createHangHoaDTO.getTenHangHoa());
        hangHoaEntity.setThoiHanBaoHanh(createHangHoaDTO.getThoiHanBaoHanh());
        hangHoaEntity.setNguonGoc(createHangHoaDTO.getNguonGoc());
        hangHoaEntity.setMoTa(createHangHoaDTO.getMoTa());
        hangHoaEntity = this.hangHoaService.createHangHoa(hangHoaEntity);
        return ResponseTool.POST_OK(hangHoaEntity);
    }

    @GetMapping(path = "get/{maHangHoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create chi tien by ma hang hoa", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public  ResponseEntity<APIResponse> getHangHoaById(
            @Param("maHangHoa") String maHangHoa
    ) {
        return  ResponseTool.GET_OK(this.hangHoaService.getByMaHangHoa(maHangHoa));
    }

    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all hang hoa", description =  "Get profile by id, role = ADMIN", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllHangHoa(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter
            ) throws BackendError {
        APIPagingResponse result = this.hangHoaService.getAll(pageable, searchParameter);
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }

    @DeleteMapping(path = "delte/{maHangHoa}")
    public ResponseEntity<APIResponse> deleteByMaHangHoa(
            @Param("maHangHoa") String maHangHoa
    ) {
        return ResponseTool.DELETE_OK(this.hangHoaService.deleteByMaHangHoa(maHangHoa));
    }

}
