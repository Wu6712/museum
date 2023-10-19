import request from "@/utils/request";

export function getNoticeList(data) {
  return request({
    url: '/notice/list',
    method: 'post',
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize
    }
  })
}

export function deleteNotices(deleteData) {
  return request({
    url: `/notice/remove/batch/${deleteData}`,
    method: 'post',
  })
}


export function insertNotice(noticeData) {
  return request({
    url: '/notice/add',
    method: 'post',
    data: JSON.stringify(noticeData),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function updateNoticeById(noticeData) {
  return request({
    url: '/notice/mod',
    method: 'post',
    data: JSON.stringify(noticeData),
    headers: {
      'Content-Type': 'application/json'
    }
  })
}
