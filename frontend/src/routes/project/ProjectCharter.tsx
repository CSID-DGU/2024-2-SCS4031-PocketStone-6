import { useCharterState } from 'hooks/useCharterState';
import { useParams } from 'react-router-dom';
import { BS, MS } from 'styles';
import S from './ProjectCharter.module.scss';
import { FaPlus } from 'react-icons/fa';
import { addObjectives, addPrinciples, addRisks, addScopes, addStakeholders, addVisions } from 'utils/addCharterData';
import { ObjectivesInput, PrinciplesInput, RisksInput, ScopesInput, StakeholdersInput, VisionsInput } from 'components/Input/CharterInput';
import { handleObjectiveChange, handlePrinciplesChange, handleRisksChange, handleScopesChange, handleStakeholdersChange, handleVisionsChange } from 'utils/handleCharterContent';

export default function ProjectCharter() {
  const { id } = useParams();
  const { charterContent, setCharterContent } = useCharterState(Number(id));

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>{id} 프로젝트 차터</p>
          <button
            className={BS.YellowBtn}
            onClick={() => {
              console.log(charterContent);
            }}>
            값 확인
          </button>
        </div>

        <div className={MS.contentBox}>
          <div className={S.section}>
            <p>목표</p>
            <button className={BS.addBtn} onClick={() => {setCharterContent(addObjectives(charterContent))}}>
              <FaPlus />
            </button>
            {charterContent?.objectives === undefined ? null : (
              <ObjectivesInput
                objectivesList={charterContent?.objectives}
                onChange={handleObjectiveChange}
                content={charterContent}
                setContent={setCharterContent}
              />
            )}
          </div>
          <div className={S.section}>
            <p>원칙</p>
            <button className={BS.addBtn} onClick={() => {setCharterContent(addPrinciples(charterContent))}}>
              <FaPlus />
            </button>
            {charterContent?.principles === undefined ? null : (
              <PrinciplesInput
                principlesList={charterContent?.principles}
                onChange={handlePrinciplesChange}
                content={charterContent}
                setContent={setCharterContent}
              />
            )}
          </div>
          <div className={S.section}>
            <p>범위</p>
            <button className={BS.addBtn} onClick={() => {setCharterContent(addScopes(charterContent))}}>
              <FaPlus />
            </button>
            {charterContent?.scopes === undefined ? null : (
              <ScopesInput
                scopesList={charterContent?.scopes}
                onChange={handleScopesChange}
                content={charterContent}
                setContent={setCharterContent}
              />
            )}
          </div>
          <div className={S.section}>
            <p>비전</p>
            <button className={BS.addBtn} onClick={() => {setCharterContent(addVisions(charterContent))}}>
              <FaPlus />
            </button>
            {charterContent?.visions === undefined ? null : (
              <VisionsInput
                visionsList={charterContent?.visions}
                onChange={handleVisionsChange}
                content={charterContent}
                setContent={setCharterContent}
              />
            )}
          </div>
          <div className={S.section}>
            <p>이해 관계자</p>
            <button className={BS.addBtn} onClick={() => {setCharterContent(addStakeholders(charterContent))}}>
              <FaPlus />
            </button>
            {charterContent?.stakeholders === undefined ? null : (
              <StakeholdersInput
                stakeholdersList={charterContent?.stakeholders}
                onChange={handleStakeholdersChange}
                content={charterContent}
                setContent={setCharterContent}
              />
            )}
          </div>
          <div className={S.section}>
            <p>위험 요소</p>
            <button className={BS.addBtn} onClick={() => {setCharterContent(addRisks(charterContent))}}>
              <FaPlus />
            </button>
            {charterContent?.risks === undefined ? null : (
              <RisksInput
                risksList={charterContent?.risks}
                onChange={handleRisksChange}
                content={charterContent}
                setContent={setCharterContent}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
}