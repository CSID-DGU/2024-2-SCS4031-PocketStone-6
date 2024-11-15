export const saveCookies = (cookieName: string, cookieContent: string) => {
  document.cookie = `${cookieName}=${cookieContent}; path=/`;
};

export const getCookie = (cookieName: string) => {
  const value = document.cookie.match('(^|;) ?' + cookieName + '=([^;]*)(;|$)');
  return value ? value[2] : null;
};

export const deleteCookies = (name: string) => {
  document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
};
