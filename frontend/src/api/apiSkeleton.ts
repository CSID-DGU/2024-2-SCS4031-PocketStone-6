import axios from "axios"

const headers = {
    'Content-Type': 'application/json'
}

export const postAPISkeleton = async (content: Object, URL: string) => {
    try {
        const response = await axios.post(URL,
            content,
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