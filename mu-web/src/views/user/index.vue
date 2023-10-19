<template>
  <div class="app-container carBox">

    <el-card style="display: flex;">
      <span>用户名称</span>
      <el-input
        placeholder="请输入内容"
        prefix-icon="el-icon-search"
        v-model="uname"
        clearable
        @keyup.enter.native="getUserListByInput"
        style="border: 1px solid skyblue;border-radius: 5px;width: 300px;margin-left: 10px;">
      </el-input>
      <el-button type="danger" round style="margin-left: 10px;"
                 :disabled="deleteArr.length === 0"
                 @click="deleteUsers">
        批量删除
      </el-button>
    </el-card>

    <el-table
      :data="userList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55px">
      </el-table-column>
      <el-table-column
        prop="id"
        label="ID"
        width="100">
      </el-table-column>
      <el-table-column
        prop="username"
        label="姓名"
        width="100">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        width="100">
        <template scope="scope">
          {{scope.row.sex === 1 ? '男' : '女'}}
        </template>
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址"
        width="350">
      </el-table-column>
      <el-table-column
        prop="online"
        label="在线状态"
        width="110px">
        <template scope="scope">
          <el-button :type="scope.row.online === 1 ? 'success' : 'info'"
                     size="mini">{{scope.row.online === 1 ? '在线' : '离线'}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="score"
        label="信誉分"
        width="100px">
      </el-table-column>
      <el-table-column
        label="操作">
        <template scope="scope">
          <el-row>
            <el-button type="primary" icon="el-icon-edit" circle
                       @click="clickChange(scope.row)">
            </el-button>
            <el-button type="warning" size="mini" @click="updatePasswordById"
              v-if="scope.row.username === $store.state.user.name">
              修改密码
            </el-button>
          </el-row>
        </template>
      </el-table-column>
    </el-table>

    <!--更新Dialog对话框-->
    <el-dialog
      title="更新"
      :visible.sync="updateDialogVisible"
      width="50%">
      <el-form :model="formData">
        <el-form-item label="id" label-width="80px">
          <el-input v-model="formData.id" readonly></el-input>
        </el-form-item>
        <el-form-item label="用户名" label-width="80px">
          <el-input v-model="formData.username" readonly></el-input>
        </el-form-item>
        <el-form-item label="昵称" label-width="80px">
          <el-input v-model="formData.nickname"></el-input>
        </el-form-item>
        <el-form-item label="性别" label-width="80px">
          <el-select placeholder="请选择性别" v-model="formData.sex" clearable>
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" label-width="80px">
          <el-input v-model="formData.address"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelUpdate">取 消</el-button>
        <el-button type="primary" @click="confirmUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!--修改密码Dialog对话框-->
    <el-dialog
      title="修改密码"
      :visible.sync="updatePasswordDialog"
      width="50%">
      <el-form :model="updatePasswordData">
        <el-form-item label="旧密码" label-width="80px">
          <el-input v-model="updatePasswordData.oldPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" label-width="80px">
          <el-input v-model="updatePasswordData.newPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelUpdatePassword">取 消</el-button>
        <el-button type="primary"  @click="confirmUpdatePassword">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import { getUserList, getListByName } from "@/api/user";
import {Message} from "element-ui";

export default {
  name: "User",
  data() {
    return {
      userList: [],
      uname: '',
      pageNum: 1,
      pageSize: 10,
      total: '',
      deleteArr: [],
      updateDialogVisible: false,
      formData: {},
      options: [
        {
          value: 0,
          label: '女'
        },
        {
          value: 1,
          label: '男'
        }
      ],
      updatePasswordData: {},
      updatePasswordDialog: false
    }
  },
  mounted() {
    this.getUserList()
  },
  methods: {
    getUserList() {
      getUserList().then(response => {
        this.userList = response.data.data
        this.total = response.data.total
      })
    },
    getUserListByInput() {
      if (this.uname === '') {
        return this.getUserList()
      }
      getListByName({
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        username: this.uname
      }).then(({ data: response }) => {
        this.userList = response.data
        this.total = response.total
      }).catch(error => {
        console.log(error)
      })
    },
    clickChange(row) {
      this.formData = row
      this.updateDialogVisible = true
      console.log(row)
    },
    handleSelectionChange(val) {
      this.deleteArr = val
    },
    deleteUsers() {
      this.$confirm('确认删除', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '再想想',
        type: 'warning'
      }).then(() => {
        let deleteData = this.deleteArr.map(item => {
          return item.id
        })

        this.$store.dispatch('user/deleteUsers', deleteData).then((response) => {
          Message.success(response.message)
          this.getUserList()
        }).catch(error => {
          Message.error(error)
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    cancelUpdate() {
      this.getUserList()
      this.updateDialogVisible = false
    },
    confirmUpdate() {
      // 修改请求
      this.$store.dispatch('user/updateUser', this.formData).then((response) => {
        console.log(response)
        this.$message.success(response.message)
        this.getUserList()
      }).catch(error => {
        this.$message.error(error)
      })
      this.getUserList()
      this.updateDialogVisible = false
    },
    updatePasswordById() {
      this.updatePasswordDialog = true
    },
    cancelUpdatePassword() {
      this.updatePasswordData = {}
      this.updatePasswordDialog = false
    },
    confirmUpdatePassword() {
      this.updatePasswordData.id = this.$store.state.user.id
      console.log(this.updatePasswordData)
      this.$store.dispatch('user/updatePassword', this.updatePasswordData).then(response => {
        console.log(response)
        if (response.code !== 200) return this.$message.error(response.message)
        this.$message.success(response.message)
        this.cancelUpdatePassword()
      })
    }
  }
}
</script>

<style scoped>
.carBox {
  height: 100%;
}
</style>
