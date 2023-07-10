import {post,get,put,del} from "@/utils/request"

export function getPie(){
    return get("/echarts/pie");
}