export const addTimelines = (
  timelinesContent: TimelineData[],
  sprintStartDate: string,
  sprintEndDate: string
) => {
  const basicTimeline: TimelineData = {
    sprintOrder: timelinesContent.length,
    sprintContent: '새 타임라인 내용',
    sprintStartDate: sprintStartDate,
    sprintEndDate: sprintEndDate,
    requiredManmonth: 0,
  };

  return [...timelinesContent, basicTimeline];
};
