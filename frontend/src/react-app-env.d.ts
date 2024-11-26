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
}
interface departmeentInfoType {
  employeeId: number;
  staffId: string;
  name: string;
  departmeent: string;
  position: string;
}
