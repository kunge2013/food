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
				<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button>
				<el-table ref="table" :data="tableData" :default-sort="defaultSort" @selection-change="handleSelection" @sort-change="handleSort" stripe style="width:100%;">
					<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
					<el-table-column fixed prop="phone" label="服务热线" width="180"></el-table-column>
					<el-table-column prop="order" label="顺序" width="100" sortable="custom"></el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="160" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="185">
			      		<template slot-scope="scope">
			        		<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
			        		<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button>
			      		</template>
			    	</el-table-column>
				</el-table>
			   	<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="360px">
  					<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
			    		<el-form-item label="服务热线" prop="phone">
							<el-input v-model="editFormEntity.phone" placeholder="服务热线"></el-input>
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
	var defaultHotline =  {
		id : null,
		phone : null,
		order : 0
	};
	
	// 创建分页表格
	var app = new Table({
		data : function() {
			return {
				defaultActive : "hotline/grid.do",
				filterOptions : {// 表格下拉过滤参数
	          	},
	          	filterParams : {// 表单过滤参数
	          		type : null,
	          		platform : null
	          	},
	          	rules : {// 校验规则
	          		phone : [
	          			{ required : true, message: '请输入服务热线！', trigger: 'change' },
	          			{ min : 11, max : 13, message: '服务热线必须是11到13个字符！', trigger: 'change' }
	          		],
	          		order : [
	          			{ required : true, message: '请输入顺序！', trigger: 'change' }
	          		]
	          	},
	          	editFormEntity : Vue.copy(defaultHotline, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultHotline,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}hotline/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}hotline/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}hotline/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}hotline/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}hotline/delete.do",// 删除请求地址
	          	moduleName : "服务热线"// 模块名称
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