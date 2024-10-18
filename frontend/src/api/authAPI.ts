import axios from "axios"

const API_URL: URLType = process.env.REACT_APP_API_URL
const headers = {
    'Content-Type': 'application/json'
}

export const doLogin = async () => {
    
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
            {headers: headers}
        )
        return response.data
    } catch (error) {
        console.error(error)
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
            {headers: headers}
        )
        return response.data
    } catch (error) {
        console.error(error)
        if (axios.isAxiosError(error)) {
            return error.response?.data
        }
    }
}