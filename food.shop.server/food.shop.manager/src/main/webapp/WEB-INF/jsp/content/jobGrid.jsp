<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
					 <el-form-item label="职位" prop="position">
					 	<el-input v-model="filterParams.position" placeholder="职位" ></el-input>
					 </el-form-item>
					 <el-form-item label="是否显示" prop="show">
					 	<el-select v-model="filterParams.show" @change="handleQuery">
						<el-option v-for="showItem in filterOptions.show" :key="showItem.value" :label="showItem.text" :value="showItem.value"></el-option> 
					    </el-select>
					</el-form-item>	
					<el-form-item> 
						<el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
						<el-button icon="icon iconfont icon-qingkong" @click="handleClearQuery('filterParams','table')">清空</el-button>
					</el-form-item> 			  
				</el-form>
				<el-button type="success" icon="el-icon-circle-plus-outline" @click="openAddForm('editFormEntity')">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" @click="handleBatchDelete">删除</el-button>
				<el-button type="primary" icon="el-icon-refresh" @click="handleQuery">刷新</el-button>
				<el-table stripe style="width:100%;" ref="table" :data="tableData" :default-sort="defaultSort"
					@selection-change="handleSelection" @sort-change="handleSort">
				<el-table-column fixed prop="id" label="ID" type="selection"></el-table-column>
				<el-table-column fixed prop="position" label="职位" width="120"></el-table-column>
				<el-table-column fixed prop="responsibilities" label="工作职责" width="150"></el-table-column>
				<el-table-column fixed prop="requirements" label="任职要求" width="150"></el-table-column>
				
				<el-table-column prop="show" label="是否显示" width="100">
						<template slot-scope="scope">
			        		<template v-if="scope.row.show">
								显示
							</template>
							<template v-else>
								隐藏
							</template>
			      		</template>
					</el-table-column>
				<el-table-column prop="publishTime" label="发布时间" width="180" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="200">
					<template slot-scope="scope">
						<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
						<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button> 					
					</template>
					</el-table-column>
				</el-table>	
				<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="400px">
				   <el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
						<el-form-item label="职位" prop="position">
							<el-input v-model="editFormEntity.position" placeholder="职位" ></el-input>
						</el-form-item>
						<el-form-item label="工作职责" prop="responsibilities">
							<el-input v-model="editFormEntity.responsibilities" placeholder="工作职责" ></el-input>
						</el-form-item>
						<el-form-item label="任职要求" prop="requirements">
							<el-input v-model="editFormEntity.requirements" placeholder="任职要求" ></el-input>
						</el-form-item>
						<el-form-item label="发布时间" prop="publishTime">
							<el-date-picker v-model="editFormEntity.publishTime" type="date" :editable="false" placeholder="选择日期"></el-date-picker>
						</el-form-item>
						<el-form-item label="是否显示" prop="show">
    						<el-switch v-model="editFormEntity.show"></el-switch>
						</el-form-item>
				   </el-form>
					<div slot="footer" class="dialog-footer">
			    		<el-button type="primary" @click="handleEdit('editFormEntity')">确 定</el-button>
			    		<el-button @click="dialogFormVisible = false">取 消</el-button>
			  		</div>
				</el-dialog>
			</el-main>
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
	var defaultRole =  {
		id : null,
		position : null,
		responsibilities :null,
		requirements :null,
		publishTime :null,
		show :true
	};
	
	// 创建分页表格
	var app = new Table({
		data : function() {
			return {
				defaultSort : {prop : 'publishTime', order: 'descending'},
				defaultActive : "job/grid.do",
				filterOptions : {// 表格下拉过滤参数
					show : [
							{text : "全部", value : null},
		          			{text : "显示", value : true},
		          			{text : "隐藏", value : false}
		          		]
	          	},
	          	filterParams : {// 表单过滤参数
	          		position : null,
	          		show : null
	          
	          	},
	          	rules : {// 校验规则
	          		position : [
	          			{ required : true, message: '请输入职位', trigger: 'change' },
	                    { min : 1, max : 20, message: '职位必须是1到20个字符！', trigger: 'change' }
	          		],
	             	responsibilities : [
	             	   {required : true, message: '请输入职责', trigger: 'change'},    
	             	   {min : 1, max : 50, message: '职位必须是1到50个字符！', trigger: 'change'}                
	                ],
	          	},
	          	editFormEntity : Vue.copy(defaultRole, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultRole,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}job/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}job/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}job/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}job/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}job/delete.do",// 删除请求地址	          
	          	moduleName : "人才招聘"// 模块名称
			}
		}
	   
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>