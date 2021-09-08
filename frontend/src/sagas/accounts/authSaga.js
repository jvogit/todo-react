import {
  takeEvery,
  call,
  put,
} from "redux-saga/effects";
import { 
  SENDING_REQUEST,
  LOGIN_REQUEST,
  LOGIN_FAILURE,
  LOGIN_SUCCESS,
  LOGOUT_REQUEST,
  LOGOUT_SUCCESS,
  TOKEN_VALIDATE,
} from "utils/storeConsts";
import AuthService from "services/AuthService";
import { ACCESS_TOKEN } from "utils/appConsts";

function* login(history, { username, password }) {
  try {
    yield put({ type: SENDING_REQUEST });
    let { user } = yield call(AuthService.login, username, password);
    yield put({ type: LOGIN_SUCCESS, user });
    yield call(history.push, "/todos");
  } catch (error) {
    yield put({ type: LOGIN_FAILURE, error: error.response.data.message });
  }
}

function* logout(history) {
  yield put({ type: SENDING_REQUEST });
  yield call(AuthService.logout);
  yield put({ type: LOGOUT_SUCCESS, user: null });
  yield call(history.push, "/");
}

function* tokenValidate(history) {
  try {
    yield put({ type: SENDING_REQUEST });
    let  { user } = yield call(AuthService.me);
    yield put({ type: LOGIN_SUCCESS, user });
  } catch (error) {
    if (error.response.status === 401) {
      localStorage.removeItem(ACCESS_TOKEN);
      yield call(history.push, "/");
    }
    yield put({ type: LOGIN_FAILURE, error: error.response.data.message });
  }
}

export default function* authSaga({ history }) {
  yield takeEvery(LOGIN_REQUEST, login, history);
  yield takeEvery(LOGOUT_REQUEST, logout, history);
  yield takeEvery(TOKEN_VALIDATE, tokenValidate, history);
}
