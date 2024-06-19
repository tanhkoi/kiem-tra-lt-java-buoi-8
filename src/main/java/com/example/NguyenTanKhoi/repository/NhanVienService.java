package com.example.NguyenTanKhoi.repository;

import com.example.NguyenTanKhoi.model.NHANVIEN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NhanVienService {

    private final NHANVIENRepository nhanVienRepository;

    @Autowired
    public NhanVienService(NHANVIENRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    public Page<NHANVIEN> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return nhanVienRepository.findAll(pageable);
    }
}
