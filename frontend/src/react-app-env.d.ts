/// <reference types="react-scripts" />

type URLType = string | undefined;
interface checkIDType {
  loginId: string;
}
interface colorsType {
  [key: string]: string;
}
interface loginInfoType {
  loginId: string;
  password: string;
}
interface registerInfoType {
  loginId: string;
  email: string;
  password: string;
  companyName: string;
}
interface projectType {
  projectDto: {
    projectName: string;
    startDate: string;
    mvpDate: string;
  };
  projectCharterDto: {
    teamPositions: string;
    vision: string;
    objective: string;
    stakeholder: string;
    scope: string;
    risk: string;
    principle: string;
  };
  successCriteriaDto: {
    criteriaName: string;
    criteriaDescription: string;
    successCondition: string;
  };
  timelineDto: {
    id: number;
    sprintOrder: number;
    sprintContent: string;
    sprintDurationWeek: number;
  };
  manMonthDto: {
    id: number;
    position: string;
    manMonth: number;
  };
}
