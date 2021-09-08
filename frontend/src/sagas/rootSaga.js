import { fork } from "redux-saga/effects";
import authSaga from "sagas/accounts/authSaga";
import signupSaga from "sagas/accounts/signupSaga";

export const rootSaga = function* (args) {
  yield fork(authSaga, args);
  yield fork(signupSaga, args);
}

export default rootSaga;