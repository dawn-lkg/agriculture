import {post,get,put,del} from "@/utils/request"

export function getComments(form){
    return get("/comment/list",form);
}
export function deleteComment(id){
    return del("/comment/"+id);
}
export function getCommentById(id){
    return get("/comment/"+id);
}

export function addComment(form){
    return post("/comment/add",form);
}
export function updateComment(form){
    return put("/comment",form);
}