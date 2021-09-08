import axios from "utils/Request";

export const signup = async (username, email, password) => {
  return axios.post("/api/account/signup", {
    username,
    email,
    password,
  })
  .then(response => response.data);
}

const AccountService = {
  signup,
}

export default AccountService;
