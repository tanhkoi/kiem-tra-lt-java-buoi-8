package com.example.NguyenTanKhoi.controller;

import com.example.NguyenTanKhoi.model.NHANVIEN;
import com.example.NguyenTanKhoi.model.PHONGBAN;
import com.example.NguyenTanKhoi.repository.NHANVIENRepository;
import com.example.NguyenTanKhoi.repository.NhanVienService;
import com.example.NguyenTanKhoi.repository.PHONGBANRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {
    private final NHANVIENRepository nhanvienRepository;
    private final PHONGBANRepository phongbanRepository;
    private final NhanVienService nhanVienService;

    public NhanVienController(NHANVIENRepository nhanvienRepository, PHONGBANRepository phongbanRepository, NhanVienService nhanVienService) {
        this.nhanvienRepository = nhanvienRepository;
        this.phongbanRepository = phongbanRepository;
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public String showNhanVienList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<NHANVIEN> nhanVienPage = nhanVienService.findPaginated(page, 5);
        model.addAttribute("nhanViens", nhanVienPage);
        model.addAttribute("currentPage", page);
        return "nhanvien/nhanvien-list";
    }

    @GetMapping("/add")
    public String addNhanVienForm(Model model) {
        model.addAttribute("nhanVien", new NHANVIEN());
        List<PHONGBAN> phongBans = phongbanRepository.findAll();
        model.addAttribute("phongBans", phongBans);
        return "nhanvien/add-nhanvien";
    }

    @PostMapping("/add")
    public String addNhanVien(@ModelAttribute NHANVIEN nhanVien) {
        nhanvienRepository.save(nhanVien);
        return "redirect:/nhanvien";
    }

    @GetMapping("/edit/{maNV}")
    public String editNhanVienForm(@PathVariable String maNV, Model model) {
        NHANVIEN nhanVien = nhanvienRepository.findById(maNV)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + maNV));
        model.addAttribute("nhanVien", nhanVien);
        List<PHONGBAN> phongBans = phongbanRepository.findAll();
        model.addAttribute("phongBans", phongBans);
        return "nhanvien/edit-nhanvien";
    }

    @PostMapping("/edit/{maNV}")
    public String editNhanVien(@PathVariable String maNV, @ModelAttribute NHANVIEN nhanVien) {
        nhanvienRepository.save(nhanVien);
        return "redirect:/nhanvien";
    }

    @GetMapping("/delete/{maNV}")
    public String deleteNhanVien(@PathVariable String maNV) {
        nhanvienRepository.deleteById(maNV);
        return "redirect:/nhanvien";
    }
}
