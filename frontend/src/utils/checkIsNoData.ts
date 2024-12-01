export const checkIsNoData = (data: any) => {
  if (typeof data === 'object') return data === undefined || Object.keys(data).length === 0;
  return data === undefined || data.length === 0;
};
