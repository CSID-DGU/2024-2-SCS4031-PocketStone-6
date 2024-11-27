import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { completeMessage } from '../../constants/completeMessage';
import { ERROR_AT_CHARTER_REGISTER } from 'constants/errorMessage';
import { refreshPage } from 'utils/movePage';

export const createProjectCharter = async (projectId: number, navigate: NavigateFunction) => {
  const content = {
    id: 0,
    objectives: [
      {
        id: 0,
        objectiveName: '목표 0',
        objectiveContent: '이 프로젝트의 목표 내용을 적습니다.',
      },
    ],
    positions: [
      {
        id: 0,
        positionName: '포지션 0',
        positionContent: '이 프로젝트에 필요한 포지션 내용을 적습니다.',
      },
    ],
    principles: [
      {
        id: 0,
        principleName: '프로젝트 원칙 0',
        principleContent: '프로젝트에서 제공해야 하는 원칙을 설명합니다.',
      },
    ],
    scopes: [
      {
        id: 0,
        scopeName: '범위 0',
        scopeContent: '개발하는 기능의 범위를 정의합니다.',
      },
    ],
    visions: [
      {
        id: 0,
        visionName: '비전 0',
        visionContent: '이 프로젝트의 비전을 설명합니다.',
      },
    ],
    stakeholders: [
      {
        id: 0,
        stakeholderName: '이해 관계자 0',
        stakeholderContent: '이해 관계자의 설명을 적습니다.',
      },
    ],
    risks: [
      {
        id: 0,
        riskName: '리스크 0',
        riskContent: '이 프로젝트를 수행했을 때 발생할 수 있는 리스크를 적습니다.',
      },
    ],
  };

  try {
    const response = await tokenAxios.post(
      `${API_URL}/api/projects/charter/${projectId}`,
      content,
      {
        headers: headers,
      }
    );
    if (response.data) {
      alert(completeMessage.CREATE_CHARTER);
      refreshPage(navigate);
      return;
    }
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(`${error.response?.data?.message}`);
      return;
    }
    alert(`${ERROR_AT_CHARTER_REGISTER}`);
  }
};
