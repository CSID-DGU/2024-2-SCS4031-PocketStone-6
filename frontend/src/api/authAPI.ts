import axios from "axios"
import { HEADERS } from "../constants/HEADERS"
import { NavigateFunction } from "react-router-dom"
import { deleteCookies, getCookie, saveCookies } from "../utils/handleCookies"
import { WRONG_LOGIN_INFO } from "../constants/errorMessage"
import { API_URL } from "../constants/envText"

export const doLogin = async (id: string, password: string, navigate: NavigateFunction) => {
    const content: loginInfoType = {
        loginId: id,
        password: password
    }

    // 로그인 시도
    try {
        const response = await axios.post(`${API_URL}/api/users/login`,
            content,
            { headers: HEADERS }
        )
        const { accessToken, refreshToken } = response.data
        saveCookies('access', accessToken)
        saveCookies('refresh', refreshToken)
        navigate('/')

    } catch (error) {
        alert(WRONG_LOGIN_INFO)
        if (axios.isAxiosError(error)) {
            return error.response?.data
        }
    }
}


export const doRegister = async (id: string, email: string, password: string, companyName: string) => {
    const content: registerInfoType = {
        loginId: id,
        email: email,
        password: password,
        companyName: companyName
    }

    try {
        const response = await axios.post(`${API_URL}/api/users/signup`,
            content,
            { headers: HEADERS }
        )
        return response.data
    } catch (error) {
        if (axios.isAxiosError(error)) {
            return error.response?.data
        }
    }
}

export const checkID = async (id: string) => {
    const content: checkIDType = {
        loginId: id
    }

    try {
        const response = await axios.post(`${API_URL}/api/users/check-loginid`,
            content,
            { headers: HEADERS }
        )
        return response.data
    } catch (error) {
        console.error(error)
        if (axios.isAxiosError(error)) {
            return error.response?.data
        }
    }
}

export const doLogout = () => {
    deleteCookies('access')
    deleteCookies('refresh')
}

export const getAccessToken = async () => {
    const content: { 'refreshToken': string | null } = {
        refreshToken: getCookie('refresh')
    }

    try {
        const response = await axios.post(`${API_URL}/api/users/refresh`,
            content,
            {headers: HEADERS}
        )
        return response.data
    } catch (error) {
        console.error(error)
        if (axios.isAxiosError(error)) {
            console.error(error.response?.data)
        }
        return Promise.reject(error);
    }
}