<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>${title }</title>
<!-- 引入ElementUI样式库 -->
<link rel="stylesheet" href="${urlPath }css/element-ui.css">
<link rel="stylesheet" href="${urlPath }css/display.css">
<!-- 阿里巴巴矢量图标库 -->
<link rel="stylesheet" href="${urlPath }css/iconfont/iconfont.css">
<!-- 系统公共样式 -->
<link rel="stylesheet" href="${urlPath }css/base.css">
</head>
<body>
<div id="app">
	<el-container>
		<jsp:include page="/WEB-INF/jsp/common/header.jsp" />
		<el-container :style="containerStyle2">
			<jsp:include page="/WEB-INF/jsp/common/menu.jsp" />
	  		<el-main>
	  			<el-form :inline="true" :model="filterParams" ref="filterParams" label-width="80px" label-position="left" @keyup.native.enter="handleQuery">
					<el-form-item label="状态" prop="status"> 
						<el-select v-model="filterParams.status" placeholder="请选择状态" @change="handleQuery">
							<el-option label="全部" value=""></el-option>
							<el-option v-for="statusItem in filterOptions.status" :key="statusItem.value" :label="statusItem.text" :value="statusItem.value"></el-option> 
						</el-select>
					</el-form-item>
					<el-form-item> 
						<el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
						<el-button icon="icon iconfont icon-qingkong" @click="handleClearQuery('filterParams','table')">清空</el-button>
					</el-form-item> 
				</el-form>
	  			<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button>
				<el-table ref="table" :data="tableData" :default-sort="defaultSort" @selection-change="handleSelection" @sort-change="handleSort" stripe style="width:100%;">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="content" label="公告内容" width="350" show-overflow-tooltip></el-table-column>
					<el-table-column prop="active" label="是否过期" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.active">
								有效
							</template>
							<template v-else>
								过期
							</template>
			      		</template>
					</el-table-column>
					<el-table-column prop="status" label="状态" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.status == 'Y'">
								激活
							</template>
							<template v-else-if="scope.row.status == 'N'">
								禁用
							</template>
			      		</template>
					</el-table-column>
					<el-table-column prop="startDate" label="开始时间" width="120"></el-table-column>
					<el-table-column prop="endDate" label="结束时间" width="120"></el-table-column>
					<el-table-column prop="order" label="顺序" width="100" sortable="custom"></el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="160" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="185">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
			      		</template>
			    	</el-table-column>
				</el-table>
				<el-pagination background highlight-current-row
					@size-change="handleSizeChange" @current-change="handleCurChange"
					:current-page="curPage" :page-size="pageSize" :page-sizes="pageSizes" :total="total" 
			     	layout="prev, pager, next, total, sizes, jumper">
			    </el-pagination>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="450px">
  					<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="平台" prop="platform">
							<el-input type="textarea" :rows="2" placeholder="请输入公告内容" autosize v-model="editFormEntity.content">
						</el-form-item>
			    		<el-form-item label="日期">
			    			<el-col :span="11">
      							<el-date-picker type="date" placeholder="选择开始日期" v-model="editFormEntity.startDate" @change="checkEndDate('editFormEntity', 'startDate', 'endDate')" style="width: 100%;"></el-date-picker>
   							</el-col>
    						<el-col class="line" :span="2">-</el-col>
    						<el-col :span="11">
      							<el-date-picker type="date" placeholder="选择结束日期" v-model="editFormEntity.endDate" @change="checkStartDate('editFormEntity', 'startDate', 'endDate')" style="width: 100%;"></el-time-picker>
    						</el-col>
						</el-form-item>
						<el-form-item label="顺序" prop="order">
							<el-input-number v-model="editFormEntity.order" :min="0" :max="1000" label="顺序"></el-input-number>
						</el-form-item>
  					</el-form>
			  		<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEdit('editFormEntity')">确 定</el-button>
			    		<el-button @click="dialogFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
			</el-main>
		</el-container>
	</el-container>
</div>
<!-- 引入Vue库 -->
<script type="text/javascript" src="${urlPath }js/vue.2.5.13.js"></script>
<!-- 引入ElementUI库 -->
<script type="text/javascript" src="${urlPath }js/element-ui.min.js"></script>
<!-- 引入Ajax库 -->
<script type="text/javascript" src="${urlPath }js/axios.min.js"></script>
<script type="text/javascript" src="${urlPath }js/qs.js"></script>
<!-- 引入基础库 -->
<script type="text/javascript" src="${urlPath }js/global.js"></script>
<script type="text/javascript">
	// 表单默认参数
	var defaultNotice =  {
		id : null,
		content : null,
		startDate : null,
		endDate : null,
		order : 0
	};
	
	// 创建分页表格
	var app = new PageTable({
		data : function() {
			return {
				defaultActive : "notice/grid.do",
				filterOptions : {// 表格下拉过滤参数
					status : [
	          			{text : "激活", value : "Y"},
	          			{text : "禁用", value : "N"}
	          		]
	          	},
	          	filterParams : {// 表单过滤参数
	          		status : null
	          	},
	          	rules : {// 校验规则
	        		content : [
	          			{ required : true, message: '请输入公告内容！', trigger: 'change' }
	          		],
	          		order : [
	          			{ required : true, message: '请输入顺序！', trigger: 'change' }
	          		]
	          	},
	          	editFormEntity : Vue.copy(defaultNotice, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultNotice,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}notice/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}notice/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}notice/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}notice/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}notice/delete.do",// 删除请求地址
	          	toggleUrl : "${urlPath}notice/toggleStatus.do",// 切换状态请求地址
	          	moduleName : "公告"// 模块名称
			}
		},
		methods : {
			
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>