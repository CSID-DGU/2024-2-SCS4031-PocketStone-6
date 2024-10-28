import axios from "axios"
import { HEADERS } from "../constants/HEADERS"
import { tokenAxios } from "./tokenAPI"
import { API_URL } from "../constants/envText"

export const getAllProject = async () => {
    try {
        const response = await tokenAxios.get(`${API_URL}/api/projects/all`,
            { headers: HEADERS }
        )
        console.log(response)
        return response.data
    } catch (error) {
        console.error(error)
        if (axios.isAxiosError(error)) {
            return error.response?.data
        }
    }
}