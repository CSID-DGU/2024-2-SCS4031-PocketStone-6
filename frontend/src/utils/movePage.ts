import { NavigateFunction } from "react-router-dom"

export const refreshPage = (navigateFunction: NavigateFunction) => {
    navigateFunction(0)
}
export const movePrevPage = (navigateFunction: NavigateFunction) => {
    navigateFunction(-1)
}