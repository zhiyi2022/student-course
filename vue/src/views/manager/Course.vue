<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 260px; margin-right: 10px" v-model="data.name" placeholder="请输入课程查询名称" prefix-icon="Search"></el-input>
      <el-input style="width: 260px; margin-right: 10px" v-model="data.teacher" placeholder="请输入任课老师查询" prefix-icon="Search"></el-input>
      <el-input style="width: 260px; margin-right: 10px" v-model="data.no" placeholder="请输入课程编号查询" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table stripe :data="data.tableData" style="width: 100%;">
        <el-table-column prop="id" label="序号" width="80px; " />
        <el-table-column prop="name" label="课程名称"  />
        <el-table-column prop="no" label="课程编号" />
        <el-table-column prop="description" label="课程描述" />
        <el-table-column prop="times" label="课时" />
        <el-table-column prop="teacher" label="任课老师" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next" :total="data.total"
                     v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                      @current-change="handleCurrentChange"/>   <!-- 分页查询组件 -->
    </div>

    <el-dialog title="课程信息" width="35%" v-model="data.formVisible" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程编号" prop="no">
          <el-input v-model="data.form.no" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input v-model="data.form.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课时" prop="times">
          <el-input v-model="data.form.times" autocomplete="off" />
        </el-form-item>
        <el-form-item label="任课教师" prop="teacher">
          <el-input v-model="data.form.teacher" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save" >保 存</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {Search} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

request.get('/').then(res => {
  // console.log(res)
})

const data = reactive({
  name: '',
  no: '',
  teacher: '',
  tableData: [],
  total: 0,
  pageNum: 1,     //当前的页码
  pageSize: 5,     //每页的个数
  formVisible: false,
  form: {},
})

const load = () => {
  request.get('/course/selectPage',{
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      teacher: data.teacher,
      no: data.no
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

//调用方法获取后台数据
load()

const handleCurrentChange = () => {
  // 当翻页的时候重新加载数据即可
  load()
}

const reset = () => {
  data.name = ''
  data.teacher = ''
  data.no = ''
  load()
}
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// 保存数据到后台
const save = () => {
  request.request({
    url: data.form.id ? '/course/update' : '/course/add',
    method: data.form.id ? 'PUT' : 'POST',
    data: data.form
  }).then(res => {
    if (res.code === '200') {
      load()     // 重新获取数据
      data.formVisible = false    // 关闭弹窗
      ElMessage.success("操作成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const handleDelete = (id) => {

  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗?', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/course/delete/' + id).then(res => {
      if (res.code === '200') {
        load()     // 重新获取数据
        ElMessage.success("操作成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}
</script>