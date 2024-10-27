import { useQuery } from "@tanstack/react-query"
import { getAllProject } from "../api/getProject"

export const useAllProjectQuery = () => {
    const query = useQuery({
        queryKey: ["useAllProjectInfo"],
        queryFn: getAllProject
    })

    return query
}