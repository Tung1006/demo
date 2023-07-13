package com.demo.component.commune;

import com.demo.component.commune.entity.Commune;
import com.demo.common.ResponseBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@Tag(name = "001 - commune")
@RequestMapping("/testCommunne")
public class CommuneController {
    @Autowired
    private CommuneService service;

    @Autowired
    private CommuneRepository repo;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả--t]")
    public ResponseEntity<?> findAll(Model model) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setMessage("SUCCESS");
        resBean.setData(service.findAll());
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        service.delete(id);
        List<Commune> listCommune = service.findAll();
        resBean.setData(listCommune);
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "[Lấy ra 1 xã]")
    public ResponseEntity<Object> findById(@PathVariable("id") UUID id) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(service.findById(id));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    @Operation(summary = "[Tìm kiếm xã]")
    public ResponseEntity<?> findByName(@RequestParam(name = "name") String name) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(service.findByName(name));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/findByCode")
    @Operation(summary = "[Tìm kiếm theo mã]")
    public ResponseEntity<?> findByCode(@RequestParam(name = "code") String code) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(service.findByCode(code));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Operation(summary = "[Thêm xã mới]")
    public ResponseEntity<Object> add(Model model, @RequestBody @Valid Commune entity) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(service.save(entity));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Sửa]")
    public ResponseEntity<Object> update(Model model, @RequestParam("id") String id, @RequestBody @Valid Commune entity) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(service.save(entity));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/export/excel")
    @Operation(summary = "[Xuất excel]")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Commune> listCommune = service.findAll();

        ExportCommune excelExporter = new ExportCommune(listCommune);

        excelExporter.export(response);
    }

    @PostMapping("/import/excel")
    @Operation(summary = "[đọc file excel]")
    public ResponseEntity<?> importToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");

        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
//        resBean.setData(service.save(entity));
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + ".xlsx";
//        response.setHeader(headerKey, headerValue);
        ImportCommune importCommune = new ImportCommune();
        List<Commune> newFile = importCommune.readExcel("D:\\download\\users_kkkk.xlsx");
        for (Commune commune : newFile) {
            resBean.setData(service.save(commune));
        }
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "[Đếm số bản ghi]")
    public ResponseEntity<?> count() {
        long soLuong = repo.count();
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(soLuong);
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping(value = "/trang-chu/page")
    @Operation(summary = "[phân trang]")
    public ResponseEntity<?> listTrangChu(@RequestParam("pageNumber") int currentPage,
                                          @RequestParam("totalItems") int totalItems) {
        Page<Commune> page = service.pageCommune(currentPage, totalItems);
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(page);
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("listSP", page.getContent());
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }
}
