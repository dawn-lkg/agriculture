import {post,get,put,del} from "@/utils/request"

export function getCategorys(form){
    return get("/category/list",form);
}
export function getCategoryById(id){
    return get("/category/"+id);
}
export function deleteCategory(id){
    return del("/category/"+id);
}
export function addCategory(form){
    return post("/category/add",form);
}
export function updateCategory(form){
    return put("/category",form);
}
export function getAllCategory(){
    return get("/category/all")
}