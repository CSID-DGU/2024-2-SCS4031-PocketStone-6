export const handleTimelinesChange = (
  index: number,
  key: keyof TimelineData,
  value: string,
  timelinesContent: TimelineData[],
  setTimelinesContent: React.Dispatch<React.SetStateAction<TimelineData[]>>
) => {
  if (!timelinesContent) return;

  const updatedTimelines = [...timelinesContent];
  if (key === 'sprintOrder' || key === 'requiredManmonth')
    updatedTimelines[index] = {
      ...timelinesContent[index],
      [key]: Number(value),
    };
  else
    updatedTimelines[index] = {
      ...timelinesContent[index],
      [key]: value,
    };

  setTimelinesContent(updatedTimelines);
};
