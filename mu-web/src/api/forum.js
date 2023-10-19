import request from "@/utils/request";

export function getForumList(data) {
  return request({
    url: '/forum/list',
    method: 'post',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize
    }
  })
}

export function deleteForums(deleteData) {
  return request({
    url: `/forum/remove/batch/${deleteData}`,
    method: 'post',
  })
}


export function insertForum(noticeData) {
  return request({
    url: '/forum/add',
    method: 'post',
    data: JSON.stringify(noticeData),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function updateForumById(noticeData) {
  return request({
    url: '/forum/mod',
    method: 'post',
    data: JSON.stringify(noticeData),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
