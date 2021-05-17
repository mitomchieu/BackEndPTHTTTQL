package com.backend.template.domain.BangLuong;

import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.BangLuong.dto.CreateBangLuongDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ResponseEntity<APIResponse> updateBangLuong() {
        return null;
    }

    public ResponseEntity<APIResponse> getBangLuongByMaBangLuong() {
        return null;
    }

    public ResponseEntity<APIResponse> getBangTinhLuongByMaNhanVien() {
        return null;
    }
}
