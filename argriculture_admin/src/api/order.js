import {post,get,put,del} from "@/utils/request"

export function getOrders(form){
    return get("/order/list",form);
}
export function getOrderById(id){
    return get("/order/"+id);
}
export function deleteOrder(id){
    return del("/order/"+id);
}
export function addOrder(form){
    return post("/order/add",form);1
}
export function updateOrder(form){
    return put("/order",form);
}