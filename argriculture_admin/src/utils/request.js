import axios from "axios";
const service=axios.create({
    baseURL:'http://localhost:8081/',
    timeout: 5000
})
service.interceptors.request.use(config=>{
    const token=window.sessionStorage.getItem('token');
    config.headers.token=token;
    return config;
},error=>{
    return Promise.reject(error);
})
service.interceptors.response.use(config=>{
    return config.data;
})
export function get(url,params){
    return service.get(url,{params});
}

export function post(url,data){
    return service.post(url,data);
}
export function del(url,params){
    return service.delete(url,{params});
}
export function put(url,data){
    return service.put(url,data);
}