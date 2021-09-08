import { combineReducers } from 'redux';
import auth from "reducers/accounts/authReducer";
import signup from "reducers/accounts/signupReducer";

export const rootReducer = combineReducers({
  auth,
  signup,
});

export default rootReducer;