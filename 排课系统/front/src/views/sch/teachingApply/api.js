import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getTeachingApplyOne = (params) => {
    return getRequest('/teachingApply/getOne', params)
}
export const getTeachingApplyList = (params) => {
    return getRequest('/teachingApply/getByPage', params)
}
export const getTeachingApplyCount = (params) => {
    return getRequest('/teachingApply/count', params)
}
export const addTeachingApply = (params) => {
    return postRequest('/teachingApply/insert', params)
}
export const editTeachingApply = (params) => {
    return postRequest('/teachingApply/update', params)
}
export const addOrEditTeachingApply = (params) => {
    return postRequest('/teachingApply/insertOrUpdate', params)
}
export const deleteTeachingApply = (params) => {
    return postRequest('/teachingApply/delByIds', params)
}
export const getCurriculumList = (params) => {
    return getRequest('/curriculum/getAll', params)
}
export const getTeachingScheduleList = (params) => {
    return getRequest('/teachingSchedule/getAll', params)
}
export const work = (params) => {
    return getRequest('/teachingApply/work', params)
}
export const deleteAll = (params) => {
    return getRequest('/teachingApply/deleteAll', params)
}