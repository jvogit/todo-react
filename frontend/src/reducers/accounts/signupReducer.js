import { 
  SIGNUP_SUCCESS,
  SIGNUP_FAILURE,
  SENDING_REQUEST,
} from "utils/storeConsts";

const initial_state = { inProgress: false, error: null, success: false };

export const signupReducer = (state = initial_state, action) => {
  let new_state = { ...initial_state };
  
  switch(action.type) {
    default:
      break;
    case SENDING_REQUEST:
      new_state.inProgress = true;
      break;
    case SIGNUP_SUCCESS:
      new_state.success = true;
      new_state.error = null;
      new_state.inProgress = false;
      break;
    case SIGNUP_FAILURE:
      new_state.success = false;
      new_state.error = action.error;
      new_state.inProgress = false;
      break;
  }

  return new_state;
}

export default signupReducer;