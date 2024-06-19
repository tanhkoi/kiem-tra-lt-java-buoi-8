package com.example.NguyenTanKhoi.repository;

import com.example.NguyenTanKhoi.model.NHANVIEN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NHANVIENRepository extends JpaRepository<NHANVIEN, String> {
}
