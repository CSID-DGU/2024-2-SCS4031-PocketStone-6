export const parsePhoneNumber = (number: string | null | undefined): string => {
  if (number === null || number === undefined) return '';
  return `${number.substring(0, 3)}-${number.substring(3, 7)}-${number.substring(7)}`;
};
