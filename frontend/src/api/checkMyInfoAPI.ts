import axios from "axios"
import { headers } from "../constants/headers"
import { tokenAxios } from "./tokenAPI"
import { API_URL } from "../constants/envText"

export const checkMyInfo = async () => {
    try {
        const response = await tokenAxios.get(`${API_URL}/api/users/me`,
            { headers: headers }
        )
        return response.data
    } catch (error) {
        console.error(error)
        if (axios.isAxiosError(error)) {
            return error.response?.data
        }
    }
}