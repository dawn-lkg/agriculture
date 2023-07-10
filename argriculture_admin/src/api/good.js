import {post,get,put,del} from "@/utils/request"

export function getGoods(form){
    return get("/good/list",form);
}
export function getGoodById(id){
    return get("/good/"+id);
}
export function deleteGood(id){
    return del("/good/"+id);
}
export function addGood(form){
    return post("/good/add",form);
}
export function updateGood(form){
    return put("/good",form);
}