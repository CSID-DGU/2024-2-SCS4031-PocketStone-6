package com.pocketstone.team_sync.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.dto.employeeDto.EmployeeInformationResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeListResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeSpecificationResponseDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.entity.PastProject;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.enums.Role;
import com.pocketstone.team_sync.entity.enums.Skill;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.EmployeeRepository;
import com.pocketstone.team_sync.repository.PastProjectRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    //의존성
    private final EmployeeRepository employeeRepository;
    private final PastProjectRepository pastProjectRepository;
    private final CompanyRepository companyRepository;

    // 엑셀로 사원 목록 등록
    @Transactional
    public void enrollEmployeeList(User user, MultipartFile file) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        System.out.println("처음까지ssss 변환");
        List<Employee> employees = parseExcelFile(company, file);
        System.out.println("처음까지sssssssss 변환");
        List<PastProject> pastProjects = parseExcelFileProject(company, file, employees);
        employeeRepository.saveAll(employees);
        pastProjectRepository.saveAll(pastProjects);
        
    }

    // 엑셀 파일 dto로 변환
    private List<Employee> parseExcelFile(Company company, MultipartFile file) {
        List<Employee> employees = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
                System.out.println("처음까지asdf 변환");
            //첫번째 시트
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println("처음까지 변환");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // header 제외
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
     // pastproject변환
    private List<PastProject> parseExcelFileProject(Company company, MultipartFile file, List<Employee> employees) {
    
        List<PastProject> pastProjects = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            
            //두번째 시트
            Sheet sheet2 = workbook.getSheetAt(1);
            for (int i = 1; i <= sheet2.getLastRowNum(); i++) { // header 제외
                Row row = sheet2.getRow(i);
                if (row != null) {
                    PastProject pastProject = mapRowToPastProject(row, employees);
                    if (pastProject !=null){
                        System.out.println("이곳까지 성공");
                        pastProjects.add(pastProject);
                    }
                    
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("엑셀파일 변환 실패", e);
        }

        return pastProjects;
    }

    //employee 엔티티변환
    private Employee mapRowToEmployee(Row row, Company company) {
        Employee employee = new Employee();

        employee.setStaffId(row.getCell(0).getStringCellValue());
        System.out.println("스킬까지 변환");
        employee.setName(row.getCell(1).getStringCellValue());
        System.out.println("스킬까지 변환");
        employee.setDepartment(row.getCell(2).getStringCellValue());
        System.out.println("스킬까지 변환");
        employee.setPosition(row.getCell(3).getStringCellValue());
        System.out.println("스킬까지 변환");
        employee.setEmail(row.getCell(4).getStringCellValue());
        System.out.println("스킬까지 변환");
        employee.setPhoneNumber(row.getCell(5).getStringCellValue());
        System.out.println("스킬까지 변환");
        //employee.setHireDate(LocalDate.parse(row.getCell(6).getStringCellValue()));
        // Date 타입일 경우
        Cell hireDateCell = row.getCell(6);
        Date date = hireDateCell.getDateCellValue();
        LocalDate hireDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        employee.setHireDate(hireDate);
        System.out.println("처음기본변ㄴ까지 변환");
         // 역할 변환
        String roleLabel = row.getCell(7).getStringCellValue();
        Role role = Role.fromLabel(roleLabel); // label을 통해 Role로 변환
        employee.setRole(role);
        System.out.println("역할까지 변환");
        // 스킬 목록 변환
        String skills = row.getCell(8) != null ? row.getCell(8).getStringCellValue() : ""; // 셀이 비어있는지 확인
        List<Skill> skillSet = new ArrayList<>();

        if (!skills.isEmpty()) {
            for (String skillLabel : skills.split(",")) {
                Skill skill = Skill.fromLabel(skillLabel.trim()); // 각 label을 Skill enum으로 변환
                skillSet.add(skill);
            }
        }
        employee.setSkillSet(skillSet); // 빈 목록이든 실제 값이든 설정
        
        System.out.println("스킬까지 변환");


        employee.setSkillScore(row.getCell(9).getNumericCellValue());
        employee.setKpiScore(row.getCell(10).getNumericCellValue());
        employee.setPeerEvaluationScore(row.getCell(11).getNumericCellValue());
        employee.setPersonalType(row.getCell(12).getStringCellValue());

        employee.setCompany(company); // 사원과 회사 연결

        return employee;
    }


    //employee 엔티티변환
    private PastProject mapRowToPastProject(Row row, List<Employee> employees) {
        PastProject pastProject = new PastProject();
        String staffId = row.getCell(0).getStringCellValue();
        System.out.println("이걸 봐봐"+staffId);
        for (int i=0; i< employees.size(); i++) {
            if (staffId.equals(employees.get(i).getStaffId())){
                pastProject.setEmployee(employees.get(i));
                break;
                
            }
        }
        pastProject.setProjectName(row.getCell(1).getStringCellValue());
        pastProject.setDescription(row.getCell(2).getStringCellValue());

        return pastProject;
    }

    

    // //사원목록 조회
    public List<EmployeeListResponseDto> getEmployees(User user){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
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
                            employee.getPosition(),
                            employee.getRole()
           
            );
            employeeList.add(dto);
        }
    
        return employeeList;
    }

    //사원 정보 조회
    public EmployeeInformationResponseDto getEmployeeInfo(User user, Long employeeId) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
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

    //사원 스펙정보 조회
    public EmployeeSpecificationResponseDto getEmployeeSpec(User user, Long employeeId) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
        Employee employee = employeeRepository.findByCompanyAndId(company,employeeId).orElse(null);
        if(employee == null){
            return null;
        }

        EmployeeSpecificationResponseDto dto = new EmployeeSpecificationResponseDto(
                                                employee.getId(),
                                                employee.getSkillScore(),
                                                employee.getSkillSet(),
                                                employee.getKpiScore(),
                                                employee.getPeerEvaluationScore(),
                                                employee.getPersonalType(),
                                                employee.getRole(),
                                                employee.getPastProjects()
                                            );
        return dto;
    }


    //사원 삭제
    //계정삭제
    @Transactional
    public void deleteEmployee(User user, Long employeeId) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        employeeRepository.deleteByCompanyAndId(company, employeeId);//계정삭제
    }
    @Transactional
    public void deleteAllEmployee(User user) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        employeeRepository.deleteByCompany(company);//계정삭제
    }

    //템플릿 다운로드
    public Resource getTemplate(){

        try {
             // ClassPath에서 엑셀 파일 로드
            return new ClassPathResource("excel/사원정보예시.xlsx");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load Excel template: " +  e);
        }
        
                                            
       
    }
}
