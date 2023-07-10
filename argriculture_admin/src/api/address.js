import {post,get,put,del} from "@/utils/request"

export function getAddresss(form){
    return get("/address/list",form);
}
export function getAddressById(id){
    return get("/address/"+id);
}
export function deleteAddress(id){
    return del("/address/"+id);
}
export function addAddress(form){
    return post("/address/add",form);1
}
export function updateAddress(form){
    return put("/address",form);
}