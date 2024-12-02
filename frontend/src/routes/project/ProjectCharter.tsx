import { useCharterState } from 'hooks/useCharterState';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, MS } from 'styles';
import S from './ProjectCharter.module.scss';
import { FaPlus } from 'react-icons/fa';
import {
  addObjectives,
  addPrinciples,
  addRisks,
  addScopes,
  addStakeholders,
  addVisions,
} from 'utils/addCharterData';
import {
  ObjectivesInput,
  PositionsInput,
  PrinciplesInput,
  RisksInput,
  ScopesInput,
  StakeholdersInput,
  VisionsInput,
} from 'components/Input/CharterInput';
import {
  handleObjectiveChange,
  handlePrinciplesChange,
  handleRisksChange,
  handleScopesChange,
  handleStakeholdersChange,
  handleVisionsChange,
} from 'utils/handleCharterContent';
import { postProjectCharter } from 'api/projects/postProjectCharter';
import { checkIsNoData } from 'utils/checkIsNoData';
import { putProjectCharter } from 'api/projects/putProjectCharter';
import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { parseCharterContent } from 'utils/parseCharterContent';

export default function ProjectCharter() {
  const { id } = useParams();
  const { charterQuery } = useProjectDetailInfoQuery(Number(id));
  const { charterContent, setCharterContent } = useCharterState(Number(id));
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>{id} 프로젝트 차터</p>
          <button
              className={BS.YellowBtn}
              onClick={() => {
                console.log(parseCharterContent(charterContent))
              }}>
              값 확인
            </button>
          {checkIsNoData(charterQuery?.data) ? (
            <button
              className={BS.YellowBtn}
              onClick={() => {
                postProjectCharter(Number(id), charterContent, navigate);
              }}>
              수정완료 및 저장POST
            </button>
          ) : (
            <button
              className={BS.YellowBtn}
              onClick={() => {
                putProjectCharter(Number(id), charterContent, navigate);
              }}>
              수정완료 및 저장PUT
            </button>
          )}
        </div>

        <div className={MS.contentBox}>
          <div className={S.section}>
            <div className={S.sectionTitle}>
              <p>필요 포지션</p>
            </div>
            <PositionsInput
              positionsList={charterContent?.positions}
              setCharterContent={setCharterContent}
            />
          </div>
          <div className={S.section}>
            <div className={S.sectionTitle}>
              <p>목표</p>
              <button
                className={BS.addBtn}
                onClick={() => {
                  setCharterContent(addObjectives(charterContent));
                }}>
                <FaPlus />
              </button>
            </div>
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
            <div className={S.sectionTitle}>
              <p>원칙</p>
              <button
                className={BS.addBtn}
                onClick={() => {
                  setCharterContent(addPrinciples(charterContent));
                }}>
                <FaPlus />
              </button>
            </div>
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
            <div className={S.sectionTitle}>
              <p>범위</p>
              <button
                className={BS.addBtn}
                onClick={() => {
                  setCharterContent(addScopes(charterContent));
                }}>
                <FaPlus />
              </button>
            </div>
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
            <div className={S.sectionTitle}>
              <p>비전</p>
              <button
                className={BS.addBtn}
                onClick={() => {
                  setCharterContent(addVisions(charterContent));
                }}>
                <FaPlus />
              </button>
            </div>
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
            <div className={S.sectionTitle}>
              <p>이해 관계자</p>
              <button
                className={BS.addBtn}
                onClick={() => {
                  setCharterContent(addStakeholders(charterContent));
                }}>
                <FaPlus />
              </button>
            </div>
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
            <div className={S.sectionTitle}>
              <p>위험 요소</p>
              <button
                className={BS.addBtn}
                onClick={() => {
                  setCharterContent(addRisks(charterContent));
                }}>
                <FaPlus />
              </button>
            </div>
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
