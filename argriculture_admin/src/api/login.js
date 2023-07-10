import {post} from "@/utils/request"

export function login(form){
    return post("/login",form);
}