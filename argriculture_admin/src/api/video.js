import {post,get,put,del} from "@/utils/request"

export function getVideos(form){
    return get("/videos/list",form);
}
export function getVideoById(id){
    return get("/videos/"+id);
}
export function deleteVideo(id){
    return del("/videos/"+id);
}
export function addVideo(form){
    return post("/videos/add",form);1
}
export function updateVideo(form){
    return put("/videos",form);
}