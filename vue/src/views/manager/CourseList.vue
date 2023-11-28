<template>
  <div>

    <div class="card" style="margin-bottom: 10px;">
      <el-input style="width: 260px; margin-right: 10px" v-model="data.name" placeholder="请输入课程查询名称" prefix-icon="Search"></el-input>
      <el-input style="width: 260px; margin-right: 10px" v-model="data.teacher" placeholder="请输入教师查询名称" prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <el-table stripe :data="data.tableData" style="width: 100%;">
        <el-table-column prop="id" label="序号" width="80px; " />
        <el-table-column prop="name" label="课程名称"  />
        <el-table-column prop="no" label="课程编号" />
        <el-table-column prop="description" label="课程描述" />
        <el-table-column prop="times" label="课时" />
        <el-table-column prop="teacher" label="任课老师" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="primary" @click="selectCourse(scope.row)">选 课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next" :total="data.total"
                     v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                      @current-change="handleCurrentChange"/>   <!-- 分页查询组件 -->
    </div>

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
  student: JSON.parse(localStorage.getItem('student-user') || '{}')
})

const load = () => {
  const {student, tableData, total, ...the_params} = data
  request.get('/course/selectPage',{
    params: the_params
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
  load()
}

const selectCourse = (row) => {
  request.post('/studentCourse/add', {studentId: data.student.id, name: row.name, no: row.no, courseId: row.id}).then(res => {
    if (res.code === '200') {
      load()     // 重新获取数据
      ElMessage.success("操作成功")
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>