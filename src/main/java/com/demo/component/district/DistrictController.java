package com.demo.component.district;

import com.demo.component.district.entity.District;
import com.demo.common.ResponseBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "002 - district")
@RequestMapping("/testDistrict")
public class DistrictController {
    @Autowired
    private DistrictServiceImpl service;

    @GetMapping("/find-all")
    @Operation(summary = "[Hiển thị tất cả--t]")
    public ResponseEntity<?> findAll(Model model) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
//        resBean.setMessage(Constants.SUCCESS);
        resBean.setData(service.findAll());
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "[Xóa]")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        service.delete(id);
        List<District> listDistrict = service.findAll();
        resBean.setData(listDistrict);
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "[Tìm kiếm theo id]")
    public ResponseEntity<Object> findById(@PathVariable("id") UUID id) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        resBean.setData(service.findById(id));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    @Operation(summary = "[Tìm kiếm theo tên]")
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
    public ResponseEntity<Object> add(Model model, @RequestBody @Valid District entity) {
        ResponseBean resBean = new ResponseBean();
        resBean.setCode(HttpStatus.OK.toString());
        service.save(entity);
        resBean.setData(service.save(entity));
        return new ResponseEntity<>(resBean, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "[Sửa]")
    public ResponseEntity<Object> update(@RequestParam("id") String id, @RequestBody @Valid District entity) {
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

        List<District> listDistrict = service.findAll();

        ExportDistrict excelExporter = new ExportDistrict(listDistrict);

        excelExporter.export(response);
    }
}
