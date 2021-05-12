/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.template.domain.ChiNhanh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author chudu
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiNhanhEntity {
    @Id
    @Column(unique = true)
    private String maChiNhanh;
    
    @Column
    private String tenChiNhanh;
    
    @Column
    private String diaChi;
}
