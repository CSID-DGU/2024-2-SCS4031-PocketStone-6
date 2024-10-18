/// <reference types="react-scripts" />

type URLType = string | undefined
interface checkIDType {
    loginId: string
}
interface colorsType {
    [key: string]: string
}
interface registerInfoType {
    loginId: string,
    email: string,
    password: string,
    companyName: string
}