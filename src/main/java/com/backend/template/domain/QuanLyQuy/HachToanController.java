package com.backend.template.domain.QuanLyQuy;

import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.QuanLyQuy.dto.CreateHachToanDTO;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.HachToanEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class HachToanController {

    @Autowired
    GiaoDichService giaoDichService;

    @Autowired
    HachToanService hachToanService;

    @PostMapping( path =  "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create hachToan", description =  "create hach toan by id, role = ADMIN", security = @SecurityRequirement(name = "bearer-jwt" ) )
    public ResponseEntity<APIResponse> createHachToanController(
            @Valid @RequestBody CreateHachToanDTO createHachToanDTO
    ) {
        GiaoDichEntity giaoDich = giaoDichService.getBybId(createHachToanDTO.getMaGiaoDich());
        HachToanEntity hachToanEntity = new HachToanEntity();
//        hachToanEntity.setMaHachToan(createHachToanDTO.getMaHachToan());
        hachToanEntity.setDienGiai(createHachToanDTO.getDienGiai());
        hachToanEntity.setTaiKhoanCo(createHachToanDTO.getTaiKhoanCo());
        hachToanEntity.setTaiKhoanNo(createHachToanDTO.getTaiKhoanNo());
        hachToanEntity.setSoTien(createHachToanDTO.getSoTien());
        hachToanEntity.setDoiTuong(createHachToanDTO.getDoiTuong());
        hachToanEntity.setGiaoDich(giaoDich);
        return ResponseTool.POST_OK(hachToanService.createHachToanEntity(hachToanEntity));
    }

    @GetMapping(path = "get/{maHachToan}", produces =  MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get hach toan by id", description = "Get hach toan by id")
    public  ResponseEntity<APIResponse> getHachToanById(
            @Param("maHachToan") Integer maHachToan
    ) {
        return ResponseTool.GET_OK( this.hachToanService.getByMaHachToan(maHachToan));
    }

    @GetMapping(path = "get-by-ma-giao-dich/{maGiaoDich}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get by ma giao dich", description = "Get by ma giao dich")
    public  ResponseEntity<APIResponse> getByMaGiaoDich(
            @Param("maGiaoDich") String maGiaoDich
    ) {
        List<HachToanEntity> result = this.hachToanService.getHachToanByMaGiaoDich(maGiaoDich);
        return ResponseTool.GET_OK(result);
    }
}
