package com.backend.template.domain.QuanLyHoatDongBanHang;

import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.annotations.api.ApiCommonResponse;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.ResponseTool;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.base.common.response.model.APIResponse;
import com.backend.template.domain.QuanLyHoatDongBanHang.model.DonHangEntity;
import com.backend.template.domain.QuanLyHoatDongBanHang.service.DonHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "don-hang", produces = "application/json")
@ApiCommonResponse
public class DonHangController {
    @Autowired
    private DonHangService donHangService;

    @PostMapping(path = "create")
    @Operation(summary =  "Create Don Hang",
            description = "role = Admin, User",
            security = @SecurityRequirement(name = "bearer-jwt" )
    )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIResponse> createDonHang (
            @Valid @RequestBody DonHangEntity donHangEntity
    ) throws BackendError {

        if (Objects.isNull(donHangEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Don hang khong hop le");
        }
        return ResponseTool.POST_OK(donHangService.save(donHangEntity));
    }

    @GetMapping(path = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all don hang", description = "role = ADMIN, USer", security = @SecurityRequirement(name = "bearer-jwt"))
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse> getAllDonHang(
    ) throws BackendError {
        List<DonHangEntity> listDonHang = donHangService.findAll();
        return ResponseTool.GET_OK(new ArrayList<Object>(listDonHang), listDonHang.size());
    }

    @GetMapping(path = "get-don-hang-by-nhan-vien/{nhanVien}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get don hang by nhan vien", description =  "role = ADMIN", security = @SecurityRequirement(name = "bearer-jwt" ) )
    @PreAuthorize("@EndPointAuthorizer.authorizer({'ADMIN', 'USER'})")
    public ResponseEntity<APIPagingResponse>  getDonHangByNhanVien(
            @PathVariable("nhanVien") String nhanVien
    ) {
        List<DonHangEntity> listDonHang = donHangService.searchByNhanVien(nhanVien);
        return ResponseTool.GET_OK(new ArrayList<Object>(listDonHang), listDonHang.size());
    }





}
