package com.backend.template.domain.KhoHang;

import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.HangHoa.HangHoaService;
import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import com.backend.template.domain.KhoHang.dto.CreateKhoHangDTO;
import com.backend.template.domain.KhoHang.dto.CreatePhieuChuyenKhoDTO;
import com.backend.template.domain.KhoHang.dto.CreatePhieuNhapKhoDTO;
import com.backend.template.domain.KhoHang.dto.CreatePhieuXuatKhoDTO;
import com.backend.template.domain.KhoHang.model.KhoHangEntity;
import com.backend.template.domain.KhoHang.model.PhieuKhoEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("phieu-nhap-kho")
@ApiCommonResponse
public class PhieuKhoController {
    @Autowired
    private PhieuKhoService phieuKhoService;
    @Autowired
    private HangHoaService hangHoaService;
    @Autowired
    private KhoHangService khoHangService;

    @GetMapping(path = "get-loai-phieu-kho", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get loai phieu kho")
    public  ResponseEntity<APIResponse> getLoaiPhieuKho() {
        return ResponseTool.GET_OK(PhieuKhoEntity.ELoaiPhieuKho.values());
    }

    @PostMapping(path = "create-phieu-nhap-kho", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create phieu nhap kho", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> creaetPhieuNhapKho(
            @Valid @RequestBody CreatePhieuNhapKhoDTO createPhieuNhapKhoDTO
    ) throws BackendError {
        PhieuKhoEntity phieuKhoEntity = new PhieuKhoEntity();
        phieuKhoEntity.setDoiTuong(createPhieuNhapKhoDTO.getDoiTuong());
        phieuKhoEntity.setDiaChi(createPhieuNhapKhoDTO.getDiaChi());
        phieuKhoEntity.setDienGiai(createPhieuNhapKhoDTO.getDienGiai());
        phieuKhoEntity.setSoLuong(createPhieuNhapKhoDTO.getSoLuong());
        phieuKhoEntity.setNgayHachToan(createPhieuNhapKhoDTO.getNgayHachToan());
        phieuKhoEntity.setNgayChungTu(createPhieuNhapKhoDTO.getNgayChungTu());
        phieuKhoEntity.setSoChungTu(createPhieuNhapKhoDTO.getSoChungTu());
        HangHoaEntity hangHoaEntity = this.hangHoaService.getByMaHangHoa(createPhieuNhapKhoDTO.getMaHangHoa());
        KhoHangEntity khoHangEntity = this.khoHangService.getByMaKhoHang(createPhieuNhapKhoDTO.getMaKhoHang());
        if (Objects.isNull(hangHoaEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã hàng hóa không hợp lệ");
        }
        if (Objects.isNull(khoHangEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã kho hàng không hợp lệ");
        }
        phieuKhoEntity.setLoaiPhieuKho(PhieuKhoEntity.ELoaiPhieuKho.NHAPKHO);
        phieuKhoEntity.setMatHang(hangHoaEntity);
        phieuKhoEntity.setKhoNhap(khoHangEntity);
        PhieuKhoEntity result = this.phieuKhoService.createPhieuKho(phieuKhoEntity);
        return ResponseTool.POST_OK(result);
    }

    @PostMapping(path = "create-phieu-xuat-kho", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create phieu xuat kho", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createPhieuXuatKho(
            @Valid @RequestBody CreatePhieuXuatKhoDTO createPhieuXuaKhoDTO
    ) throws BackendError {
        PhieuKhoEntity phieuKhoEntity = new PhieuKhoEntity();
        phieuKhoEntity.setDoiTuong(createPhieuXuaKhoDTO.getDoiTuong());
        phieuKhoEntity.setDiaChi(createPhieuXuaKhoDTO.getDiaChi());
        phieuKhoEntity.setDienGiai(createPhieuXuaKhoDTO.getDienGiai());
        phieuKhoEntity.setSoLuong(createPhieuXuaKhoDTO.getSoLuong());
        phieuKhoEntity.setNgayHachToan(createPhieuXuaKhoDTO.getNgayHachToan());
        phieuKhoEntity.setNgayChungTu(createPhieuXuaKhoDTO.getNgayChungTu());
        phieuKhoEntity.setSoChungTu(createPhieuXuaKhoDTO.getSoChungTu());
        HangHoaEntity hangHoaEntity = this.hangHoaService.getByMaHangHoa(createPhieuXuaKhoDTO.getMaHangHoa());
        KhoHangEntity khoHangEntity = this.khoHangService.getByMaKhoHang(createPhieuXuaKhoDTO.getMaKhoHang());
        if (Objects.isNull(hangHoaEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã hàng hóa không hợp lệ");
        }
        if (Objects.isNull(khoHangEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã kho hàng không hợp lệ");
        }
        phieuKhoEntity.setLoaiPhieuKho(PhieuKhoEntity.ELoaiPhieuKho.XUATKHO);
        phieuKhoEntity.setMatHang(hangHoaEntity);
        phieuKhoEntity.setKhoNhap(khoHangEntity);
        PhieuKhoEntity result = this.phieuKhoService.createPhieuKho(phieuKhoEntity);
        return  ResponseTool.POST_OK(result);
    }

    @PostMapping(path = "create-phieu-chuyen-kho", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create phieu chuyen kho", description =  "role= ADMIN, USEr", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createPhieuXuatKho(
            @Valid @RequestBody CreatePhieuChuyenKhoDTO createPhieuChuyenKhoDTO
    ) throws BackendError {
        PhieuKhoEntity phieuKhoEntity = new PhieuKhoEntity();
        phieuKhoEntity.setDoiTuong(createPhieuChuyenKhoDTO.getDoiTuong());
        phieuKhoEntity.setDiaChi(createPhieuChuyenKhoDTO.getDiaChi());
        phieuKhoEntity.setDienGiai(createPhieuChuyenKhoDTO.getDienGiai());
        phieuKhoEntity.setSoLuong(createPhieuChuyenKhoDTO.getSoLuong());
        phieuKhoEntity.setNgayHachToan(createPhieuChuyenKhoDTO.getNgayHachToan());
        phieuKhoEntity.setNgayChungTu(createPhieuChuyenKhoDTO.getNgayChungTu());
        phieuKhoEntity.setSoChungTu(createPhieuChuyenKhoDTO.getSoChungTu());
        HangHoaEntity hangHoaEntity = this.hangHoaService.getByMaHangHoa(createPhieuChuyenKhoDTO.getMaHangHoa());
        KhoHangEntity khoHangEntity = this.khoHangService.getByMaKhoHang(createPhieuChuyenKhoDTO.getMaKhoHang());
        KhoHangEntity khoDuocChuyen = this.khoHangService.getByMaKhoHang(createPhieuChuyenKhoDTO.getMaKhoDuocChuyen());
        if (Objects.isNull(hangHoaEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã hàng hóa không hợp lệ");
        }
        if (Objects.isNull(khoHangEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã kho hàng không hợp lệ");
        }
        phieuKhoEntity.setLoaiPhieuKho(PhieuKhoEntity.ELoaiPhieuKho.CHUYENKHO);
        phieuKhoEntity.setMatHang(hangHoaEntity);
        phieuKhoEntity.setKhoNhap(khoHangEntity);
        phieuKhoEntity.setKhoDuocChuyen(khoDuocChuyen);
        PhieuKhoEntity result = this.phieuKhoService.createPhieuKho(phieuKhoEntity);
        return  ResponseTool.POST_OK(result);
    }

}
