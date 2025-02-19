//package com.reddy.service.compliance;
//
//import com.reddy.model.payroll.Payroll;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//
//@Service
//public class ComplianceService {
//    public void generatePFReport(LocalDate startDate, LocalDate endDate, HttpServletResponse response) {
//        List<Payroll> payrolls = payrollRepository.findByPayPeriodBetween(startDate, endDate);
//
//        // Create Excel workbook
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("PF Report");
//
//        // Add headers and data
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Employee ID");
//        headerRow.createCell(1).setCellValue("PF Deduction");
//
//        // Stream to response
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=pf_report.xlsx");
//        workbook.write(response.getOutputStream());
//        workbook.close();
//    }
//}
