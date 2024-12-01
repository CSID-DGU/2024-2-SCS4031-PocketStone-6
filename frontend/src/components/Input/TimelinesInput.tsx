import DatePicker from 'react-datepicker';
import { MS, TS } from 'styles';
import S from 'styles/Inputs.module.scss';
import { isCanPickMonday, isCanPickSunday } from 'utils/filterDate';
import { parseDateToString } from 'utils/parseDate';

export const TimelinesInput = ({
  timelinesList,
  onChange,
  content,
  setContent,
}: {
  timelinesList: TimelineData[];
  onChange: (
    index: number,
    key: keyof TimelineData,
    value: string,
    content: TimelineData[],
    setContent: React.Dispatch<React.SetStateAction<TimelineData[]>>
  ) => void;
  content: TimelineData[];
  setContent: React.Dispatch<React.SetStateAction<TimelineData[]>>;
}) => {
  return (
    <div>
      {timelinesList.map(
        (
          { sprintOrder, sprintContent, sprintStartDate, sprintEndDate, requiredManmonth },
          index
        ) => (
          <div key={index} className={S.timeline}>
            <div className={S.timelineTitle}>
              <div className={S.sprintDiv}>
                <p className={`${TS.smallTitle} ${MS.Mr10}`}>🚩 스프린트</p>
                <input
                  type="number"
                  className={S.sprintOrder}
                  value={sprintOrder}
                  placeholder="순서"
                  onChange={(e) =>
                    onChange(index, 'sprintOrder', e.target.value, content, setContent)
                  }
                />
              </div>
              <div className={S.sprintDate}>
                <DatePicker
                  className={`${S.textInput}`}
                  filterDate={(date) => isCanPickMonday(date, new Date(sprintEndDate))}
                  placeholderText="시작일"
                  selected={new Date(sprintStartDate)}
                  onChange={(date) =>
                    onChange(index, 'sprintStartDate', parseDateToString(date), content, setContent)
                  }
                  dateFormat="yyyy-MM-dd"
                />
                <p>~</p>
                <DatePicker
                  className={`${S.textInput}`}
                  filterDate={(date) => isCanPickSunday(date, new Date(sprintStartDate))}
                  placeholderText="종료일"
                  selected={new Date(sprintEndDate)}
                  onChange={(date) =>
                    onChange(index, 'sprintEndDate', parseDateToString(date), content, setContent)
                  }
                  dateFormat="yyyy-MM-dd"
                />
              </div>

              <div className={S.sprintManMonth}>
                <p>Man-Month</p>
                <input
                  type="number"
                  min={0}
                  max={1}
                  step={0.05}
                  className={S.textInput}
                  value={requiredManmonth}
                  placeholder="필요 Man-Month"
                  onChange={(e) =>
                    onChange(index, 'requiredManmonth', e.target.value, content, setContent)
                  }
                />
              </div>
            </div>
            <input
              className={`${S.textInput} ${MS.width100}`}
              value={sprintContent}
              placeholder="내용"
              onChange={(e) =>
                onChange(index, 'sprintContent', e.target.value, content, setContent)
              }
            />
          </div>
        )
      )}
    </div>
  );
};
