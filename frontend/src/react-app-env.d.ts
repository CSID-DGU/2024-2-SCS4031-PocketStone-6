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

interface Objectives {
  id?: number;
  objectiveName: string;
  objectiveContent: string;
}

interface Positions {
  id?: number;
  positionName: string;
  positionContent: string;
  positionCount?: number;
}

interface Principles {
  id?: number;
  principleName: string;
  principleContent: string;
}

interface Scopes {
  id?: number;
  scopeName: string;
  scopeContent: string;
}

interface Visions {
  id?: number;
  visionName: string;
  visionContent: string;
}

interface Stakeholders {
  id?: number;
  stakeholderName: string;
  stakeholderContent: string;
}

interface Risks {
  id?: number;
  riskName: string;
  riskContent: string;
}

interface CharterContent {
  id?: number;
  objectives: Objectives[];
  positions: Positions[];
  principles: Principles[];
  scopes: Scopes[];
  visions: Visions[];
  stakeholders: Stakeholders[];
  risks: Risks[];
}

interface TimelineData {
  id?: number;
  sprintOrder: number;
  sprintContent: string;
  sprintStartDate: string;
  sprintEndDate: string;
  requiredManmonth: number;
}
