package com.backend.template.domain.BangChamCong;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.BangChamCong.dto.CreateBangChamCongEntityDto;
import com.backend.template.domain.BangChamCong.dto.UpdataBangChamCongDTO;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.BangChamCong.model.NhanVienInBangChamCongEntity;
import com.backend.template.domain.BangChamCong.model.NhanVienInBangChamCongID;
import com.backend.template.domain.ChiNhanh.ChiNhanhService;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.hibernate.loader.plan.build.internal.FetchStyleLoadPlanBuildingAssociationVisitationStrategy;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("bang-cham-comg")
@ApiCommonResponse
public class BangChamCongController {

    @Autowired
    private ChiNhanhService chiNhanhService;

    @Autowired
    private BangChamCongService bangChamCongService;


    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create bang cham cong", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
//    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createBangChamCong(
            @Valid @RequestBody CreateBangChamCongEntityDto createBangChamCongEntityDto
            ) {
        BangChamCongEntity bangChamCongEntity = new BangChamCongEntity();
        bangChamCongEntity.setGhiChu(createBangChamCongEntityDto.getGhiChu());
        bangChamCongEntity.setNgayTao(createBangChamCongEntityDto.getNgayTao());
        ChiNhanhEntity chiNhanhEntity = this.chiNhanhService.getChiNhanhByMaChiNhanhh(
                    createBangChamCongEntityDto.getChiNhanhEntity());
        bangChamCongEntity.setChiNhanhEntity(chiNhanhEntity);
        bangChamCongEntity = this.bangChamCongService.createBangChamCong(bangChamCongEntity);
        return ResponseTool.POST_OK( bangChamCongEntity);
    }


    @GetMapping(path = "get/{maBangChamCong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get bang cham cong by id", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getBangChamCongById(
            @PathVariable("maBangChamCong") Long maBangChamCong
    ) {
        return ResponseTool.GET_OK(this.bangChamCongService.getBangChamCongById(maBangChamCong));
    }

    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all bang cham cong by ma chi nhanh", description = "role = ADMIN, USer", security = @SecurityRequirement(name = "bearer-jwt"))
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllBangChamCong(
            @ParameterObject Pageable pageable,
            @ParameterObject SearchParameter searchParameter,
            @RequestParam(value = "maChiNhanh", required = false, defaultValue = "") String maChiNhanh
            ) throws BackendError {
        APIPagingResponse result = this.bangChamCongService.getBangChamCongByMaChiNhanh(
                maChiNhanh,
                pageable,
                searchParameter
        );
        return ResponseTool.GET_OK(result.getData(), result.getTotal());
    }

    @PutMapping(path = "update/{maBangChamCong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Khoa bang cham cong",
            description = "role = ADMIN, USer",
            security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<APIResponse> khoaBangChamCong(
            @PathVariable("maBangChamCong") Long maBangChamCong,
            @Valid @RequestBody UpdataBangChamCongDTO updateBangChamCongDTO
    ) {
        BangChamCongEntity bangChamCongEntity = new BangChamCongEntity();
        bangChamCongEntity.setGhiChu(updateBangChamCongDTO.getGhiChu());
        bangChamCongEntity.setNgayTao(updateBangChamCongDTO.getNgayTao());
        ChiNhanhEntity chiNhanhEntity = this.chiNhanhService.getChiNhanhByMaChiNhanhh(
                updateBangChamCongDTO.getChiNhanhEntity());
        bangChamCongEntity.setChiNhanhEntity(chiNhanhEntity);
        bangChamCongEntity.setKhoaBang(updateBangChamCongDTO.getKhoaBang());
        bangChamCongEntity.setMaBangChamCong(maBangChamCong);
        BangChamCongEntity result = this.bangChamCongService.updateBangChamCong(bangChamCongEntity);
        return  ResponseTool.PUT_OK(result);
    }

    @PutMapping(path = "/{maBangChamCong}/{maNhanVien}/{heSo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Them Nhan vien vao bang cham cong",
            description = "role = ADMIN, USer",
            security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<APIResponse> themNhanVienVaoBangChamCon(
            @PathVariable("maBangChamCong") Long maBangChamCong,
            @PathVariable("maNhanVien") String maNhanVien,
            @PathVariable("heSo") Double heSo
    ) throws BackendError {
        NhanVienInBangChamCongEntity result = this.bangChamCongService.themNhanVienVaoBangChamCong(
                maBangChamCong,
                maNhanVien,
                heSo
        );
        return ResponseTool.PUT_OK(result);
    }

    @DeleteMapping(path = "xoa/{maBangChamCong}/{maNhanVien}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Xoa Nhan vien khoi bang cham cong",
            description = "role = ADMIN, USer",
            security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<APIResponse> xoaNhanVienKhoiBangChamCong(
            @PathVariable("maBangChamCong") Long maBangChamCong,
            @PathVariable("maNhanVien") String maNhanVien
    ) {
        return ResponseTool.DELETE_OK(
                this.bangChamCongService.xoaNhanVienKhoiBangChamCong(
                        maBangChamCong,
                        maNhanVien
                )
        );
    }

    @GetMapping(path = "get-nhan-vien/{maBangChamCong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get bang cham cong by id", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getAllNhanVienInBangChamCong(
            @PathVariable("maBangChamCong") Long maBangChamCong
    ) {
        return ResponseTool.GET_OK(this.bangChamCongService.getAllNhanVienTrongBangChamCong(maBangChamCong));
    }
}
