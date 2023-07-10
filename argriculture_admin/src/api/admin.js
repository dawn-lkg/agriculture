import {post,get,put,del} from "@/utils/request"

export function getAdmins(form){
    return get("/admin/list",form);
}
export function getAdminById(id){
    return get("/admin/"+id);
}
export function deleteAdmin(id){
    return del("/admin/"+id);
}
export function addAdmin(form){
    return post("/admin/add",form);1
}
export function updateAdmin(form){
    return put("/admin",form);
}
export function getInfo(){
    return get("/admin/info");
}