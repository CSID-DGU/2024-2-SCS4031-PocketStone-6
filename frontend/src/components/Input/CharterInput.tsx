import S from 'styles/Inputs.module.scss';

export const ObjectivesInput = ({
  objectivesList,
  onChange,
  content,
  setContent,
}: {
  objectivesList: Objectives[];
  onChange: (
    index: number,
    key: keyof Objectives,
    value: string,
    content: CharterContent,
    setContent: React.Dispatch<React.SetStateAction<CharterContent>>
  ) => void;
  content: CharterContent;
  setContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  return (
    <div>
      {objectivesList.map(({ objectiveName, objectiveContent }, index) => (
        <div key={index} className={S.twoInputDiv}>
          <input
            className={S.textInput}
            value={objectiveName}
            placeholder="목표 제목"
            onChange={(e) => onChange(index, 'objectiveName', e.target.value, content, setContent)}
          />
          <input
            className={S.textInput}
            value={objectiveContent}
            placeholder="목표 내용"
            onChange={(e) =>
              onChange(index, 'objectiveContent', e.target.value, content, setContent)
            }
          />
        </div>
      ))}
    </div>
  );
};

export const PrinciplesInput = ({
  principlesList,
  onChange,
  content,
  setContent,
}: {
  principlesList: Principles[];
  onChange: (
    index: number,
    key: keyof Principles,
    value: string,
    content: CharterContent,
    setContent: React.Dispatch<React.SetStateAction<CharterContent>>
  ) => void;
  content: CharterContent;
  setContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  return (
    <div>
      {principlesList.map(({ principleName, principleContent }, index) => (
        <div key={index} className={S.twoInputDiv}>
          <input
            className={S.textInput}
            value={principleName}
            placeholder="원칙 제목"
            onChange={(e) => onChange(index, 'principleName', e.target.value, content, setContent)}
          />
          <input
            className={S.textInput}
            value={principleContent}
            placeholder="원칙 내용"
            onChange={(e) =>
              onChange(index, 'principleContent', e.target.value, content, setContent)
            }
          />
        </div>
      ))}
    </div>
  );
};

export const ScopesInput = ({
  scopesList,
  onChange,
  content,
  setContent,
}: {
  scopesList: Scopes[];
  onChange: (
    index: number,
    key: keyof Scopes,
    value: string,
    content: CharterContent,
    setContent: React.Dispatch<React.SetStateAction<CharterContent>>
  ) => void;
  content: CharterContent;
  setContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  return (
    <div>
      {scopesList.map(({ scopeName, scopeContent }, index) => (
        <div key={index} className={S.twoInputDiv}>
          <input
            className={S.textInput}
            value={scopeName}
            placeholder="범위 제목"
            onChange={(e) => onChange(index, 'scopeName', e.target.value, content, setContent)}
          />
          <input
            className={S.textInput}
            value={scopeContent}
            placeholder="범위 내용"
            onChange={(e) => onChange(index, 'scopeContent', e.target.value, content, setContent)}
          />
        </div>
      ))}
    </div>
  );
};

export const VisionsInput = ({
  visionsList,
  onChange,
  content,
  setContent,
}: {
  visionsList: Visions[];
  onChange: (
    index: number,
    key: keyof Visions,
    value: string,
    content: CharterContent,
    setContent: React.Dispatch<React.SetStateAction<CharterContent>>
  ) => void;
  content: CharterContent;
  setContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  return (
    <div>
      {visionsList.map(({ visionName, visionContent }, index) => (
        <div key={index} className={S.twoInputDiv}>
          <input
            className={S.textInput}
            value={visionName}
            placeholder="비전 제목"
            onChange={(e) => onChange(index, 'visionName', e.target.value, content, setContent)}
          />
          <input
            className={S.textInput}
            value={visionContent}
            placeholder="비전 내용"
            onChange={(e) => onChange(index, 'visionContent', e.target.value, content, setContent)}
          />
        </div>
      ))}
    </div>
  );
};

export const StakeholdersInput = ({
  stakeholdersList,
  onChange,
  content,
  setContent,
}: {
  stakeholdersList: Stakeholders[];
  onChange: (
    index: number,
    key: keyof Stakeholders,
    value: string,
    content: CharterContent,
    setContent: React.Dispatch<React.SetStateAction<CharterContent>>
  ) => void;
  content: CharterContent;
  setContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  return (
    <div>
      {stakeholdersList.map(({ stakeholderName, stakeholderContent }, index) => (
        <div key={index} className={S.twoInputDiv}>
          <input
            className={S.textInput}
            value={stakeholderName}
            placeholder="이해관계자 이름"
            onChange={(e) =>
              onChange(index, 'stakeholderName', e.target.value, content, setContent)
            }
          />
          <input
            className={S.textInput}
            value={stakeholderContent}
            placeholder="이해관계자 내용"
            onChange={(e) =>
              onChange(index, 'stakeholderContent', e.target.value, content, setContent)
            }
          />
        </div>
      ))}
    </div>
  );
};

export const RisksInput = ({
  risksList,
  onChange,
  content,
  setContent,
}: {
  risksList: Risks[];
  onChange: (
    index: number,
    key: keyof Risks,
    value: string,
    content: CharterContent,
    setContent: React.Dispatch<React.SetStateAction<CharterContent>>
  ) => void;
  content: CharterContent;
  setContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  return (
    <div>
      {risksList.map(({ riskName, riskContent }, index) => (
        <div key={index} className={S.twoInputDiv}>
          <input
            className={S.textInput}
            value={riskName}
            placeholder="위험요소 제목"
            onChange={(e) => onChange(index, 'riskName', e.target.value, content, setContent)}
          />
          <input
            className={S.textInput}
            value={riskContent}
            placeholder="위험요소 내용"
            onChange={(e) => onChange(index, 'riskContent', e.target.value, content, setContent)}
          />
        </div>
      ))}
    </div>
  );
};
