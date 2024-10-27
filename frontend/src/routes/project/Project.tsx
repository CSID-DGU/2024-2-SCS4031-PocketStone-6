import { useAllProjectQuery } from '../../hooks/useAllProjectQuery';

export default function Project() {
  const allProjectQuery = useAllProjectQuery();

  return (
    <div>
      <h2>프로젝트 목록</h2>
      {allProjectQuery.data?.map((project: Object) => {
        return <p>{JSON.stringify(project)}</p>;
      })}
    </div>
  );
}
