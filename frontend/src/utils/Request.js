import axios from "axios";
import { ACCESS_TOKEN } from "utils/appConsts";

export async function postWithToken(url, data) {
  return requestWithToken("POST", url, { data });
}

export async function getWithToken(url, params) {
  return requestWithToken("GET", url, { params });
}

export async function requestWithToken(method, url, config) {
  return axios.request({
    method,
    url,
    headers: {
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
    ...config
  });
}

export default axios;