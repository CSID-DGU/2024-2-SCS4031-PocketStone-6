export const addElementAtList = (element: any, list: any[]) => {
  return [...list, element];
};

export const deleteElementAtList = (element: any, list: any[]) => {
  if (!list.includes(element)) return list;
  return list.filter((data) => data !== element);
};
