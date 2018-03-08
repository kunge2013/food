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
					 <el-form-item label="问题" prop="question">
					 	<el-input v-model="filterParams.question" placeholder="问题" ></el-input>
					 </el-form-item>
					 <el-form-item label="答案" prop="answer">
					 	<el-input v-model="filterParams.answer" placeholder="答案" ></el-input>
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
				
				
				<!-- 鼠标悬浮提示 -->
				<el-table-column fixed  label="问题" width="250">
				   <template slot-scope="scope">
                     <el-popover trigger="hover" placement="top">
         				 <p>{{ scope.row.question}}</p>        				
          				<div slot="reference" class="name-wrapper">
            			<el-tag size="medium">{{ scope.row.question }}</el-tag>
          				</div>
       				 </el-popover>
     				</template>
				</el-table-column>
				
				 <el-table-column fixed  label="答案" width="300">
				    <template slot-scope="scope">
                      <el-popover trigger="hover" placement="top">
         				 <p>{{ scope.row.answer}}</p>        				
          				<div slot="reference" class="name-wrapper">
            			<el-tag size="medium">{{ scope.row.answer }}</el-tag>
          				</div>
       				  </el-popover>
     				</template>		 
				 </el-table-column>				
    
				
				<el-table-column prop="createTime" label="创建时间" width="180" sortable="custom"></el-table-column>
					<el-table-column fixed="right" label="操作" width="200">
					<template slot-scope="scope">
						<el-button size="mini" type="primary" icon="el-icon-edit" @click="openEditForm('editFormEntity', scope.row)">编辑</el-button>
						<el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.row.id)">删除</el-button> 					
					</template>
					</el-table-column>
				</el-table>	
				
				<!-- 分页插件 -->		
				<el-pagination background highlight-current-row
					@size-change="handleSizeChange" @current-change="handleCurChange"
					:current-page="curPage" :page-size="pageSize" :page-sizes="pageSizes" :total="total" 
			     	layout="prev, pager, next, total, sizes, jumper">
			   	</el-pagination>
				
				
				<el-dialog :title="dialogFormTitle" :visible.sync="dialogFormVisible" width="700px">
				   <el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
						<el-form-item label="问题" prop="question">
							<el-input v-model="editFormEntity.question" placeholder="问题" ></el-input>
						</el-form-item>
						<el-form-item label="答案" prop="answer">
							<el-input v-model="editFormEntity.answer" placeholder="答案" ></el-input>
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
		question : null,
		answer :null,		
		createTime :null
	};
	
	// 创建分页表格
	var app = new PageTable({
		data : function() {
			return {
				defaultActive : "fag/grid.do",
				filterOptions : {// 表格下拉过滤参数
	          	},
	          	filterParams : {// 表单过滤参数
	          		question : null,
	          		answer : null
	          
	          	},
	          	rules : {// 校验规则
	          		question : [
	          			{ required : true, message: '请输入问题', trigger: 'change' },
	                    { min : 1, max : 30, message: '职位必须是1到30个字符！', trigger: 'change' }
	          		],
	             	answer : [
	             	   {required : true, message: '请输入答案', trigger: 'change'},    
	             	   {min : 1, max : 200, message: '答案必须是1到200个字符！', trigger: 'change'}                
	                ],
	          	},
	          	editFormEntity : Vue.copy(defaultRole, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultRole,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
	          	dataUrl : "${urlPath}faq/list.do",// 表格数据请求地址
	          	getUrl : "${urlPath}faq/get.do",// 获取数据请求地址
	          	addUrl : "${urlPath}faq/add.do",// 添加请求地址
	          	updateUrl : "${urlPath}faq/update.do",// 更新请求地址
	          	deleteUrl : "${urlPath}faq/delete.do",// 删除请求地址	          
	          	moduleName : "常见问题"// 模块名称
			}
		}
	   
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>