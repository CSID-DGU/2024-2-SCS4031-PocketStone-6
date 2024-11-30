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
interface projectInfoType {
  id: 0;
  projectName: string;
  startDate: string;
  mvpDate: string;
}

interface employeeInfoType {
  employeeId: number;
  staffId: string;
  name: string;
  department: string;
  position: string;
  role: string;
}
interface employeeIdObject {
  employeeId: number;
}
interface RegisterContent {
  memberList: employeeIdObject[];
}

interface CharterContent {
  id?: number;
  objectives: {
    id?: number;
    objectiveName: string;
    objectiveContent: string;
  }[];
  positions: {
    id?: number;
    positionName: string;
    positionContent: string;
    positionCount?: number;
  }[];

  principles: {
    id?: number;
    principleName: string;
    principleContent: string;
  }[];
  scopes: {
    id?: number;
    scopeName: string;
    scopeContent: string;
  }[];
  visions: {
    id?: number;
    visionName: string;
    visionContent: string;
  }[];

  stakeholders: {
    id?: number;
    stakeholderName: string;
    stakeholderContent: string;
  }[];

  risks: {
    id?: number;
    riskName: string;
    riskContent: string;
  }[];
}

interface TimelineData {
  id?: number;
  sprintOrder: number;
  sprintContent: string;
  sprintStartDate: string;
  sprintEndDate: string;
  requiredManmonth: number;
}
