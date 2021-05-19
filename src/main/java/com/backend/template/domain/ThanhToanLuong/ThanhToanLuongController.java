package com.backend.template.domain.ThanhToanLuong;

import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.BangChamCong.BangChamCongService;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.ChiNhanh.ChiNhanhService;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import com.backend.template.domain.ThanhToanLuong.dto.CreateThanhToanLuongDTO;
import com.backend.template.domain.ThanhToanLuong.model.ThanhToanLuongEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(name = "thanh-toan-luong")
@ApiCommonResponse
public class ThanhToanLuongController {

    @Autowired
    private ChiNhanhService chiNhanhService;
    @Autowired
    private ThanhToanLuongService thanhToanLuongService;
    @Autowired
    private BangChamCongService bangChamCongService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create thanh toan luong", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createBangThanhToanLuong(
            @Valid @RequestBody CreateThanhToanLuongDTO createThanhToanLuongDTO
            ) {
        ChiNhanhEntity chiNhanhEntity = chiNhanhService.getChiNhanhByMaChiNhanhh(
                createThanhToanLuongDTO.getChiNhanhEntity()
        );
        ThanhToanLuongEntity thanhToanLuongEntity
                = ThanhToanLuongEntity
                .builder()
                .ngayTao(new Date())
                .trangThaiThanhToan(ThanhToanLuongEntity.ETrangThaiThanhToan.CHUATHANHTOAN)
                .chiNhanhEntity(chiNhanhEntity)
                .build();
        return ResponseTool.POST_OK(this
        .thanhToanLuongService.createThanhToanLuong(thanhToanLuongEntity));
    }

    @GetMapping(path = "get/{maThanhToanLuong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get thanh toan luong by ma", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getThanhToanLuongById(
            @PathVariable("maThanhToanLuong") Long maThanhToanLuong
    ) {
        return ResponseTool.GET_OK(this.thanhToanLuongService.getByMaBangThanhToanLuong(maThanhToanLuong));
    }

    @PutMapping(path = "them-bang-cham-cong/{maThanhToanLuong}/{maBangChamCong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "them bang cham cong", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> themBangChamCongVaoNhom(
            @PathVariable("maThanhToanLuong") Long maThanhToanLuong,
            @PathVariable("maBangChamCong") Long maBangChamCong
            ) throws BackendError {
        BangChamCongEntity bangChamCongEntity = this.bangChamCongService
                .getBangChamCongById(maBangChamCong);
        ThanhToanLuongEntity thanhToanLuongEntity = this.thanhToanLuongService
                .getByMaBangThanhToanLuong(maThanhToanLuong);
        if (Objects.isNull(bangChamCongEntity) || Objects.isNull(thanhToanLuongEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã gửi lên không hợp lệ");
        }
        this.thanhToanLuongService.themVaoThanhToanLuong(
                thanhToanLuongEntity,
                bangChamCongEntity
        );
        return ResponseTool.PUT_OK("Done");
    }

    public  ResponseEntity<ApiCommonResponse> traLuongChoNhanVien() {
        return null;
    }
}
