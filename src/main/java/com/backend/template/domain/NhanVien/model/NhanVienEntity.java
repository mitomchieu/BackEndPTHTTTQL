
package com.backend.template.domain.NhanVien.model;

import java.util.Date;
import javax.persistence.*;

import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienEntity {
    @Id
    @Column(unique = true)
    private String maNhanVien;
    
    @Column
    private String tenNhanVien;
    
    @Temporal(TemporalType.DATE)
    protected Date ngaySinh;

    @Column
    private String soTaiKhoanNganHang;

    @ManyToOne()
    @JoinColumn(name = "chiNhanh")
    private ChiNhanhEntity chiNhanh;
    
}
