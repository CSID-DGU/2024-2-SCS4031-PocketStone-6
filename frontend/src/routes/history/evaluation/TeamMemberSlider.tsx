import React, { useState, useEffect } from 'react';
import { Mail, Phone, Award } from 'lucide-react';
import S from './TeamMemberSlider.module.scss';
import { useOneEmployeeInfoQuery } from '../../../hooks/useOneEmployeeInfoQuery';
import { useOneEmployeeSpecQuery } from '../../../hooks/useOneEmployeeSpecQuery';
import { useProjectMemberQuery } from '../../../hooks/useProjectMemberQuery'; 

interface TeamMemberSliderProps {
  projectId: number;
  onMemberChange: (memberId: number) => void;
}

const TeamMemberSlider: React.FC<TeamMemberSliderProps> = ({ projectId, onMemberChange }) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  
  const { data: teamMembers, isLoading: isTeamMembersLoading } = useProjectMemberQuery(projectId);
  

  const currentMemberId = teamMembers?.[currentIndex]?.employeeId;
  const { data: employeeInfo, isLoading: isEmployeeInfoLoading } = useOneEmployeeInfoQuery(currentMemberId);
  const { data: employeeSpec, isLoading: isEmployeeSpecLoading } = useOneEmployeeSpecQuery(currentMemberId);

  useEffect(() => {
    if (teamMembers?.[currentIndex]?.employeeId) {

      onMemberChange(teamMembers[currentIndex].employeeId);
    }
  }, [currentIndex, teamMembers, onMemberChange]);

  if (isTeamMembersLoading || isEmployeeInfoLoading || isEmployeeSpecLoading) {
    return <div>Loading...</div>;
  }

  if (!teamMembers?.length || !employeeInfo || !employeeSpec) {
    return <div>No team members found</div>;
  }

  return (
    <div className={S.container}>
      <h2 className={S.title}>프로젝트 팀원</h2>
      <div className={S.sliderContainer}>
        <div className={S.card}>
          <div className={S.cardContent}>
            <div className={S.memberHeader}>
              <div className={S.memberInfo}>
                <h3 className={S.memberName}>{employeeInfo.name}</h3>
                <p className={S.memberRole}>{employeeSpec.role}</p>
              </div>
              <div className={S.contributionScore}>
                <Award className={S.scoreIcon} />
                <span className={S.scoreValue}>{employeeSpec.skillScore}%</span>
              </div>
            </div>
            
            <div className={S.contactInfo}>
              <div className={S.contactItem}>
                <Mail className={S.icon} />
                <span>{employeeInfo.email}</span>
              </div>
              <div className={S.contactItem}>
                <Phone className={S.icon} />
                <span>{employeeInfo.phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')}</span>
              </div>
            </div>
            
            <div className={S.skillsSection}>
              <p className={S.skillsTitle}>Skills:</p>
              <div className={S.skillsList}>
                {employeeSpec.skillSet.map((skill:string) => (
                  <span
                    key={skill}
                    className={S.skillTag}
                  >
                    {skill}
                  </span>
                ))}
              </div>
            </div>
            <div className={S.pagination}>
              {teamMembers?.map((_: any, index: number) => (
                <button
                  key={index}
                  onClick={() => setCurrentIndex(index)}
                  className={`${S.paginationDot} ${
                    index === currentIndex ? S.active : ''
                  }`}
                />
              ))}
            </div>

            <div className={S.navigationContainer}>
             
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeamMemberSlider;