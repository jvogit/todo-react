import {
  takeEvery,
  call,
  put,
} from "redux-saga/effects";
import { 
  SIGNUP_REQUEST,
  SENDING_REQUEST,
  SIGNUP_SUCCESS,
  SIGNUP_FAILURE,
} from "utils/storeConsts";
import AccountsService from "services/AccountService";

function* signup(history, { username, email, password }) {
  try {
    yield put({ type: SENDING_REQUEST });
    yield call(AccountsService.signup, username, email, password);
    yield put({ type: SIGNUP_SUCCESS });
    yield call(history.push, "/login");
  } catch (error) {
    yield put({ type: SIGNUP_FAILURE, error: error.response.data.message });
  }
}

export default function* signupSaga({ history }) {
  yield takeEvery(SIGNUP_REQUEST, signup, history);
}