import {post,get,put,del} from "@/utils/request"

export function getUsers(form){
    return get("/user/list",form);
}
export function getUserById(id){
    return get("/user/"+id);
}
export function deleteUser(id){
    return del("/user/"+id);
}
export function addUser(form){
    return post("/user/add",form);1
}
export function updateUser(form){
    return put("/user",form);
}
