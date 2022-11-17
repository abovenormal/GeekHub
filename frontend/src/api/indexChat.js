import axios from "axios";

const API_BASE_URL = "http://k7c205.p.ssafy.io:8088";

export function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    Headers: {
      "Content-type": "application/json",
      "Access-Control-Allow-Origin": "*",
    },
  });
  return instance;
}

export function authInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    Headers: {
      "Content-type": "application/json",
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Credentials": true,
      Authorization: `Bearer ${localStorage.getItem("accesstoken")}`,
    },
  });
  return instance;
}
