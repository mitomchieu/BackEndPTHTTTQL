package com.backend.template.domain.KhoHang.model;

import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuKhoEntity {

    public enum ELoaiPhieuKho {
        NHAPKHO("NHAPKHO"),
        CHUYENKHO ("CHUYENKHO"),
        XUATKHO ("XUATKHO");
        public String name;
        ELoaiPhieuKho(String name) {
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPhieuKho;

    @Column
    private String doiTuong;
    @Column
    private  String diaChi;
    @Column
    private String dienGiai;
    @Column
    private Integer soLuong;

    @Temporal(TemporalType.DATE)
    private Date ngayHachToan;

    @Temporal(TemporalType.DATE)
    private Date ngayChungTu;

    @Column
    private String soChungTu;

    @ManyToOne()
    @JoinColumn()
    private HangHoaEntity matHang;

    @ManyToOne()
    @JoinColumn()
    private KhoHangEntity khoNhap;
    // Kho mà hàng được xuất ra, hoặc nhập vào
    // là chủ thể thực hiện hành động

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn()
    private KhoHangEntity khoDuocChuyen;
    // Với loại phiếu là chuyển kho thi đây là kho mà hàng được chuyển tới.

    @Enumerated(EnumType.STRING)
    @Column ()
    protected ELoaiPhieuKho loaiPhieuKho;

}
