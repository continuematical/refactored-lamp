import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getTeachingScheduleOne = (params) => {
    return getRequest('/teachingSchedule/getOne', params)
}
export const getTeachingScheduleList = (params) => {
    return getRequest('/teachingSchedule/getByPage', params)
}
export const getCardList = (params) => {
    return getRequest('/teachingSchedule/getCardList', params)
}
export const getTeachingScheduleCount = (params) => {
    return getRequest('/teachingSchedule/count', params)
}
export const addTeachingSchedule = (params) => {
    return postRequest('/teachingSchedule/insert', params)
}
export const editTeachingSchedule = (params) => {
    return postRequest('/teachingSchedule/update', params)
}
export const addOrEditTeachingSchedule = (params) => {
    return postRequest('/teachingSchedule/insertOrUpdate', params)
}
export const deleteTeachingSchedule = (params) => {
    return postRequest('/teachingSchedule/delByIds', params)
}