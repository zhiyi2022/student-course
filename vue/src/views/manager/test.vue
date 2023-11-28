<template>
  <div>
    <div class="card" style="margin-bottom: 10px">
      <el-input style="width: 260px; " v-model="data.name" placeholder="请输入课程查询名称" prefix-icon="Search"/>
      <el-button type="primary" style="margin-left: 10px;">查询</el-button>
      <el-button type="info">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px;">
      <div style="margin-bottom: 10px;">
        <el-button type="primary">新增</el-button>
      </div>

      <div>
        <el-table :data="data.tableData" width="100%; " >
          <el-table-column prop="id" label="序号" width="80px; " />
          <el-table-column prop="name" label="课程名称"  />
          <el-table-column prop="no" label="课程编号" />
          <el-table-column prop="description" label="课程描述" />
          <el-table-column prop="times" label="课时" />
          <el-table-column prop="teacher" label="任课老师" />
          <el-table-column>
            <template #default="scope">
              <el-button type="primary">编辑</el-button>
              <el-button type="danger">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
    <div class="card">
      <el-pagination background layout="prev, pager, next" :total="1000" />
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { Search } from "@element-plus/icons-vue"
const data = reactive({
  name: '',
  tableData: [
    {id: 1, name: '数学', no: 13645, description: '好难学', times: '25节课', teacher: '张三'},
    {id: 2, name: '数学', no: 13645, description: '好难学', times: '25节课', teacher: '张三'},
    {id: 3, name: '数学', no: 13645, description: '好难学', times: '25节课', teacher: '张三'},
  ]
})
</script>