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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.entity.Applicant;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Portfolio;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.enums.Role;
import com.pocketstone.team_sync.entity.enums.Skill;
import com.pocketstone.team_sync.repository.ApplicantRepository;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApplicantService {

    //의존성
    private final ApplicantRepository applicantRepository;
    private final PortfolioRepository portfolioRepository;
    private final CompanyRepository companyRepository;

    // 엑셀로 사원 목록 등록
    @Transactional
    public void enrollApplicantList(User user, MultipartFile file) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        List<Applicant> applicants = parseExcelFile(company, file);
        List<Portfolio> portfolios = parseExcelFilePortfolio(company, file, applicants);
        applicantRepository.saveAll(applicants);
        portfolioRepository.saveAll(portfolios);
        
    }

    // 엑셀 파일 dto로 변환
    private List<Applicant> parseExcelFile(Company company, MultipartFile file) {
        List<Applicant> applicants = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            //첫번째 시트
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // header 제외
                Row row = sheet.getRow(i);
                if (row != null) {
                    Applicant applicant = mapRowToApplicant(row, company);
                    applicants.add(applicant);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("지원정보엑셀파일 변환 실패", e);
        }

        return applicants;
    }
     // portfolio변환
    private List<Portfolio> parseExcelFilePortfolio(Company company, MultipartFile file, List<Applicant> applicants) {
    
        List<Portfolio> portfolios = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            
            //두번째 시트
            Sheet sheet2 = workbook.getSheetAt(1);
            for (int i = 1; i <= sheet2.getLastRowNum(); i++) { // header 제외
                Row row = sheet2.getRow(i);
                if (row != null) {
                    Portfolio portfolio = mapRowToPortfolio(row, applicants);
                    if (portfolio !=null){
                        System.out.println("이곳까지 성공");
                        portfolios.add(portfolio);
                    }
                    
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("엑셀파일 변환 실패", e);
        }

        return portfolios;
    }

    // 엔티티변환
    private Applicant mapRowToApplicant(Row row, Company company) {
        Applicant applicant = new Applicant();

        applicant.setApplicantCode(row.getCell(0).getStringCellValue());
        System.out.println("지원번호 변환");
        applicant.setName(row.getCell(1).getStringCellValue());
        System.out.println("이름 변환");
        applicant.setEmail(row.getCell(2).getStringCellValue());
        System.out.println("이메일 변환");
        applicant.setPhoneNumber(row.getCell(3).getStringCellValue());
        System.out.println("폰번호 변환");
        //applicant.setApplyDate(LocalDate.parse(row.getCell(4).getStringCellValue()));
        // Date 타입일 경우
        Cell applyDateCell = row.getCell(4);
        Date date = applyDateCell.getDateCellValue();
        LocalDate applyDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        applicant.setApplyDate(applyDate);
        System.out.println("날짜까지 변환");
         // 역할 변환
        String roleLabel = row.getCell(5).getStringCellValue();
        Role role = Role.fromLabel(roleLabel); // label을 통해 Role로 변환
        applicant.setRole(role);
        System.out.println("역할까지 변환");
        // 스킬 목록 변환
        String skills = row.getCell(6) != null ? row.getCell(6).getStringCellValue() : ""; // 셀이 비어있는지 확인
        List<Skill> skillSet = new ArrayList<>();

        if (!skills.isEmpty()) {
            for (String skillLabel : skills.split(",")) {
                Skill skill = Skill.fromLabel(skillLabel.trim()); // 각 label을 Skill enum으로 변환
                skillSet.add(skill);
            }
        }
        applicant.setSkillSet(skillSet); // 빈 목록이든 실제 값이든 설정
        
        System.out.println("스킬까지 변환");


        applicant.setSkillScore(row.getCell(7).getNumericCellValue());
        applicant.setPersonalType(row.getCell(8).getStringCellValue());

        applicant.setCompany(company); // 사원과 회사 연결

        return applicant;
    }


    //applicant 엔티티변환
    private Portfolio mapRowToPortfolio(Row row, List<Applicant> applicants) {
        Portfolio portfolio = new Portfolio();
        String applicantCode = row.getCell(0).getStringCellValue();
        System.out.println("이걸 봐봐"+applicantCode);
        for (int i=0; i< applicants.size(); i++) {
            if (applicantCode.equals(applicants.get(i).getApplicantCode())){
                portfolio.setApplicant(applicants.get(i));
                break;
                
            }
        }
        portfolio.setProjectName(row.getCell(1).getStringCellValue());
        portfolio.setDescription(row.getCell(2).getStringCellValue());

        return portfolio;
    }

}
