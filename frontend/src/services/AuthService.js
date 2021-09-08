import axios, {
  getWithToken,
} from "utils/Request";
import {
  ACCESS_TOKEN
} from "utils/appConsts";

export const login = async (username, password) => {
  return axios.post("/api/account/login", {
    username,
    password,
  })
  .then(response => {
    localStorage.setItem(ACCESS_TOKEN, response.data.token);

    return response.data;
  });
};

export const logout = () => {
  localStorage.removeItem(ACCESS_TOKEN);
}

export const me = async () => {
  return getWithToken("/api/account/me")
  .then(response => response.data);
}

const AuthService = {
  login,
  logout,
  me,
}

export default AuthService;