package com.demo.component.district;

import com.demo.component.district.entity.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DistrictServiceImpl {
    @Autowired
    private DistrictRepository repository;

    public List<District> findAll() {

        return repository.findAll();
    }

    public District save(District district) {
        return repository.save(district);
    }

    public District findById(UUID id) {
        return repository.findById(id).get();
    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }

//    public void update(String id, District district) {
//        District newCom = repository.findById(id).get();
//        newCom.setName(district.getName());
//        newCom.setCode(district.getCode());
//        newCom.setFoundedYear(district.getFoundedYear());
//        newCom.setAcreage(district.getAcreage());
//        newCom.setNumberOfPeople(district.getNumberOfPeople());
//        newCom.setDistrict(District.getDistrict());
//        newCom.setProvince(district.getProvince());
//        repository.save(newCom);
//    }

    public List<District> findByName(String name) {
        return repository.findByNameContains(name);
    }

    public List<District> findByCode(String code) {
        return repository.findByCodeContains(code);
    }
//
//    public List<District> findByProvince(String idProvince) {
//        return repository.findByProvince(idProvince);
//    }

//    public void generateExcel(HttpServletResponse response) throws IOException {
//        List<District> listDistrict = repository.findAll();
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("District info");
//        HSSFRow row = sheet.createRow(0);
//        row.createCell(0).setCellValue("ID");
//        row.createCell(1).setCellValue("Code");
//        row.createCell(2).setCellValue("name");
//        row.createCell(3).setCellValue("founded year");
//        row.createCell(4).setCellValue("acreage");
//
//        int dataRowIndex = 1;
//
//        for (District District : listDistrict
//        ) {
//            HSSFRow dataRow = sheet.createRow(dataRowIndex);
//            dataRow.createCell(0).setCellValue(District.getId());
//            dataRow.createCell(1).setCellValue(District.getCode());
//            dataRow.createCell(2).setCellValue(District.getName());
//            dataRow.createCell(3).setCellValue(District.getFoundedYear());
//            dataRow.createCell(4).setCellValue(District.getAcreage());
//            dataRowIndex++;
//        }
//        ServletOutputStream ops = response.getOutputStream();
//        workbook.write(ops);
//        workbook.close();
//        ops.close();
//
//    }
}
