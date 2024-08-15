import http from 'k6/http'
import { sleep } from 'k6'

export const options = {
  stages: [
    { duration: '30s', target: 10000 },
    { duration: '2m', target: 30000 },
    { duration: '5m', target: 50000 },
    // { duration: '5m', target: 100000 },
  ],
  tresholds: {
    http_req_failed: ['rate<0.05']
  }
}

export default function () {
  http.get("http://localhost:8080/country");
  sleep(1);
}