package com.backend.template.domain.BangLuong;

import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.BangLuong.dto.CreateBangLuongDTO;
import com.backend.template.domain.BangLuong.dto.UpdateBangLuongDTO;
import com.backend.template.domain.BangLuong.model.BangLuongEntity;
import com.backend.template.domain.NhanVien.NhanVienService;
import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("bang-luong")
@ApiCommonResponse
public class BangLuongController {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private BangLuongService bangLuongService;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create bang luong", description =  "role= ADMIN, USEr",
            security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createBangLuong(
            @Valid @RequestBody CreateBangLuongDTO createBangLuongDTO
    ) throws BackendError {
        BangLuongEntity bangLuongEntity = new BangLuongEntity();
        bangLuongEntity.setTienLuongTheoNgay(createBangLuongDTO.getTienLuongTheoNgay());
        NhanVienEntity nhanVienEntity = this.nhanVienService.getByMaNhanVien(
                createBangLuongDTO.getNhanVienEntity()
        );
        if (Objects.isNull(nhanVienEntity)){
            throw new BackendError(HttpStatus.BAD_REQUEST,"Mã Nhân viên không hợp lệ");
        }
        bangLuongEntity.setNhanVienEntity(nhanVienEntity);
        BangLuongEntity result = this.bangLuongService.createBangLuong(bangLuongEntity);
        return ResponseTool.POST_OK(result);
    }

    @PutMapping(path = "update/{maBangLuong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create bang luong", description =  "role= ADMIN, USEr",
            security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> updateBangLuong(
            @Valid @RequestBody UpdateBangLuongDTO updateBangLuongDTO,
            @PathVariable("maBangLuong") Long maBangLuong
            ) throws BackendError {
        BangLuongEntity bangLuongEntity = new BangLuongEntity();
        bangLuongEntity.setTienLuongTheoNgay(updateBangLuongDTO.getTienLuongTheoNgay());
        NhanVienEntity nhanVienEntity = this.nhanVienService.getByMaNhanVien(
                updateBangLuongDTO.getNhanVienEntity()
        );
        if (Objects.isNull(nhanVienEntity)){
            throw new BackendError(HttpStatus.BAD_REQUEST,"Mã Nhân viên không hợp lệ");
        }
        bangLuongEntity.setNhanVienEntity(nhanVienEntity);
        bangLuongEntity.setMaBangLuong(maBangLuong);
        BangLuongEntity result = this.bangLuongService.updateBangLuong(bangLuongEntity);
        return ResponseTool.PUT_OK(result);
    }

    @GetMapping(path = "get-by-ma-bang-luong/{maBangLuong}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get bang luong by id", description =  "role= ADMIN, USEr",
            security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getBangLuongByMaBangLuong(
            @PathVariable("maBangLuong") Long maBangLuong
    ) {
        return ResponseTool.GET_OK(this.bangLuongService
                .getByMaBangLuong(maBangLuong));
    }

    @GetMapping(path = "get-by-ma-nhan-vien/{maNhanVien}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get bang luong by ma nhan vien", description =  "role= ADMIN, USEr",
            security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> getBangTinhLuongByMaNhanVien(
            @PathVariable("maNhanVien") String maNhanVien
    ) {
        return ResponseTool.GET_OK(this.bangLuongService
            .getByMaNhanVien(maNhanVien));
    }
}
