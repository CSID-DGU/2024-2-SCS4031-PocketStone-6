package com.pocketstone.team_sync.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.dto.employeeDto.EmployeeInformationResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeListResponseDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    //의존성
    private final EmployeeRepository employeeRepository;

    // 엑셀로 사원 목록 등록
    @Transactional
    public void enrollEmployeeList(Company company, MultipartFile file) {
        List<Employee> employees = parseExcelFile(company, file);
        employeeRepository.saveAll(employees);
    }

    // 엑셀 파일 dto로 변환e
    private List<Employee> parseExcelFile(Company company, MultipartFile file) {
        List<Employee> employees = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header
                Row row = sheet.getRow(i);
                if (row != null) {
                    Employee employee = mapRowToEmployee(row, company);
                    employees.add(employee);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("엑셀파일 변환 실패", e);
        }

        return employees;
    }

    //엔티티변환
    private Employee mapRowToEmployee(Row row, Company company) {
        Employee employee = new Employee();

        employee.setName(row.getCell(0).getStringCellValue());
        employee.setEmail(row.getCell(1).getStringCellValue());
        employee.setStaffId(row.getCell(2).getStringCellValue());
        employee.setPhoneNumber(row.getCell(3).getStringCellValue());
        employee.setPosition(row.getCell(4).getStringCellValue());
        employee.setDepartment(row.getCell(5).getStringCellValue());
        employee.setHireDate(LocalDate.now());
        //employee.setRole(row.getCell(6));이건 별도로 할지 고민을 좀...
        employee.setCompany(company); // 사원과 회사 연결

        return employee;
    }

    // //사원목록 조회
    public List<EmployeeListResponseDto> getEmployees(Company company){
        List<Employee> employees = employeeRepository.findByCompany(company);
        if(employees == null || employees.isEmpty()){
            return null;
        }

        List<EmployeeListResponseDto> employeeList = new ArrayList<>(); ;

        for (int i=0; i < employees.size(); i++){

            Employee employee = employees.get(i);
            EmployeeListResponseDto dto = new EmployeeListResponseDto(
                            employee.getId(),
                            employee.getStaffId(),
                            employee.getName(),
                            employee.getDepartment(),
                            employee.getPosition()
            );
            employeeList.add(dto);
        }
    
        return employeeList;
    }

    public EmployeeInformationResponseDto getEmployeeInfo(Company company, Long employeeId) {
        Employee employee = employeeRepository.findByCompanyAndId(company,employeeId).orElse(null);
        if(employee == null){
            return null;
        }

        EmployeeInformationResponseDto dto = new EmployeeInformationResponseDto(
                                                employee.getId(),
                                                employee.getStaffId(),
                                                employee.getName(),
                                                employee.getDepartment(),
                                                employee.getPosition(),
                                                employee.getPhoneNumber(),
                                                employee.getEmail(),
                                                employee.getHireDate()
                                            );
        return dto;
    }
}
