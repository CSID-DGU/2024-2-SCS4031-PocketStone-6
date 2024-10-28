import S from './NewProject.module.css';
import { DatePickerComponent } from '../../components/Input/DatePickerComponent';

export const NewProject = () => {
  return (
    <div className={S.container}>
      <div>새 프로젝트 생성</div>
      <input placeholder="프로젝트 이름"></input>
      <div className={S.inlineBlockContainer}>
        <DatePickerComponent></DatePickerComponent>
        <DatePickerComponent></DatePickerComponent>
      </div>
    </div>
  );
};
