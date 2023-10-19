/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 * @description 判断路径是否为外部连接
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 * @description 判断用户名是否有效 用户名白名单
 */
//export function validUsername(str) {
//  const valid_map = ['admin', 'editor']
//  return valid_map.indexOf(str.trim()) >= 0
//}
