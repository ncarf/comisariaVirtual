import axios from "axios";

export default axios.create({
baseURL: "http://172.24.0.4:8080",
headers: {
    "Content-type": "application/json"
  }
});