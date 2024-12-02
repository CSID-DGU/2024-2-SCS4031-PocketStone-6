import { usePositionState } from 'hooks/usePositionState';
import { useProjectSkillsQuery } from 'hooks/useProjectSkillsQuery';
import { MS } from 'styles';
import S from 'styles/Inputs.module.scss';
import { checkIsNoData } from 'utils/checkIsNoData';

export const PositionsInput = ({
  positionsList,
  setCharterContent,
}: {
  positionsList: Positions[];
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  const { position, setPosition } = usePositionState(positionsList);

  const handleCountChange = (e: React.ChangeEvent<HTMLInputElement>, category: string) => {
    const newValue = parseInt(e.target.value, 10) || 0;

    const updatedPositions = position.map((pos) =>
      pos.positionName === category ? { ...pos, positionCount: newValue } : pos
    );

    setPosition(updatedPositions);

    setCharterContent((prevCharter) => ({
      ...prevCharter,
      positions: updatedPositions,
    }));
  };

  return (
    <div>
      {['PM', 'BE', 'FE', 'DE', 'DA'].map((category) => {
        const currentCount =
          position.find((pos) => pos.positionName === category)?.positionCount || 0;

        return (
          <div key={category} className={S.positionsContainer}>
            <div className={S.positionsTitle}>
              <p>{category}</p>
              <input
                className={S.textInput}
                placeholder="인원(명)"
                type="number"
                min={0}
                value={currentCount}
                onChange={(e) => handleCountChange(e, category)}
              />
              <p>명</p>
            </div>
            <StacksBlock
              category={category}
              position={position}
              setPosition={setPosition}
              setCharterContent={setCharterContent}
            />
          </div>
        );
      })}
    </div>
  );
};

const StacksBlock = ({
  category,
  position,
  setPosition,
  setCharterContent,
}: {
  category: string;
  position: Positions[];
  setPosition: React.Dispatch<React.SetStateAction<Positions[]>>;
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>;
}) => {
  const skillQuery = useProjectSkillsQuery();

  const handleStackClick = (stack: string) => {
    const updatedPositions = position.map((pos) => {
      if (pos.positionName !== category) return pos;

      const currentContent = pos.positionContent || '';
      const stackArray = currentContent.split(',').filter((s) => s);

      // 이미 포함된 경우 제거, 포함되지 않은 경우 추가
      const updatedStackArray = stackArray.includes(stack)
        ? stackArray.filter((s) => s !== stack)
        : [...stackArray, stack];

      return {
        ...pos,
        positionContent: updatedStackArray.join(','),
      };
    });

    setPosition(updatedPositions);

    setCharterContent((prevCharter) => ({
      ...prevCharter,
      positions: updatedPositions,
    }));
  };

  return (
    <div className={MS.displayFlex}>
      {checkIsNoData(skillQuery?.data)
        ? null
        : skillQuery.data[category].map((stack: string) => {
            const posInfo = position.find((pos) => pos.positionName === category)?.positionContent;
            const isSelected =
              posInfo === undefined || posInfo === '' ? false : posInfo.split(',').includes(stack);

            return (
              <p
                key={stack}
                className={isSelected ? S.selectedStack : S.stack}
                onClick={() => handleStackClick(stack)}>
                {stack}
              </p>
            );
          })}
    </div>
  );
};

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
