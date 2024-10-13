import axios from "axios"

const API_URL: URLType = process.env.REACT_APP_API_URL
const headers = {
    'Content-Type': 'application/json'
}

export const doLogin = async () => {
    
}

export const doRegister = async () => {

}

export const checkID = async (id: string) => {
    const content: checkIDType = {
        loginId: id
    }

    try {
        const response = await axios.post(`${API_URL}/signup/check-loginid`,
            {content},
            {headers: headers}
        )
        console.log(response)
    } catch (error) {
        console.error(error)
    }
}