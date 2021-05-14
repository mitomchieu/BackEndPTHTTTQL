package com.backend.template.domain.BangChamCong;

import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.BangChamCong.dto.CreateBangChamCongEntityDto;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.ChiNhanh.ChiNhanhService;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                    createBangChamCongEntityDto.getChiNhanh());
        bangChamCongEntity.setChiNhanhEntity(chiNhanhEntity);
        bangChamCongEntity = this.bangChamCongService.createBangChamCong(bangChamCongEntity);
        return ResponseTool.POST_OK( bangChamCongEntity);
    }

    public ResponseEntity<APIResponse> getBangChamCongById() {
        return null;
    }

    public ResponseEntity<APIPagingResponse> getAllBangChamCong() {
        return null;
    }

    public ResponseEntity<APIResponse> khoaBangChamCong() {
        return null;
    }

    public ResponseEntity<APIResponse> moKhoaBangChamCong() {
        return null;
    }

    public ResponseEntity<APIResponse> themNhanVienVaoBangChamCon() {
        return null;
    }

    public ResponseEntity<APIResponse> xoaNhanVienKhoiBangChamCong() {
        return null;
    }

    public ResponseEntity<APIResponse> thayDoiDiemChamCongNhanVien() {
        return null;
    }
}
