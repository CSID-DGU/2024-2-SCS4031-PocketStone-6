import { useParams } from "react-router-dom";

export default function ProjectMember() {
    const { id } = useParams();
    return (
        <div>프로젝트 ID: {id} 인원 수정 페이지임</div>
    )
}