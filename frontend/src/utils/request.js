import axios from 'axios'
import {ElMessage} from "element-plus";

const defaultError =()=> ElMessage.error("出现错误!请联系管理员")
const defaultFailure=(message)=>ElMessage.warning(message)

function  post(url,data,success,failure=defaultFailure,error=defaultError){
    axios.post(url,data,{
        headers:{
            'content-type':'application/json;charset=UTF-8'
        },
        withCredentials: true
    }).then((response) => {
        const res = response.data;
        if(res.success)
            success(res.message,data.status)
        else
            failure(res.message || '请求失败',data.status)
    }).catch((err) => {
        // 关键：捕获所有异常并调用错误回调
        if (err.response) {
            // 服务器返回了错误响应（如 4xx/5xx）
            const { status, data } = err.response;
            failure(data?.message || `服务器错误: ${status}`, status);
        } else if (err.request) {
            // 请求已发送但无响应（如网络断开）
            error('网络错误，请检查连接');
        } else {
            // 请求配置错误（如 URL 无效）
            error('请求配置异常: ' + err.message);
        }
    })
}

function  get(url,data,success,failure=defaultFailure,error=defaultError){
    axios.post(url,data,{
        params: data, // GET参数通过params传递
        withCredentials: true
    }).then((response) => {
        const res = response.data;
        if(res.success)
            success(res.message,data.status)
        else
            failure(res.message || '请求失败',data.status)
    }).catch((err) => {
        // 关键：捕获所有异常并调用错误回调
        if (err.response) {
            // 服务器返回了错误响应（如 4xx/5xx）
            const { status, data } = err.response;
            failure(data?.message || `服务器错误: ${status}`, status);
        } else if (err.request) {
            // 请求已发送但无响应（如网络断开）
            error('网络错误，请检查连接');
        } else {
            // 请求配置错误（如 URL 无效）
            error('请求配置异常: ' + err.message);
        }
    })
}

export {get,post}