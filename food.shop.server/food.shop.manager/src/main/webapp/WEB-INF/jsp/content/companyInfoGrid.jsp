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
	  			<el-form :model="editFormEntity" ref="editFormEntity" :rules="rules" label-width="80px" label-position="right">
		    		<el-form-item label="公司简介" prop="company.content">
						<el-input type="textarea" placeholder="请输入公司简介" autosize v-model="editFormEntity.company.content">
					</el-form-item>
		    		<el-form-item label="简介图片" prop="company.image">
						<el-upload class="avatar-uploader" :action="uploadUrl"
							:multiple="false" accept="image/*" :limit="1024" :auto-upload="true"
		  					:show-file-list="false" :on-success="handleUploadCompanySuccess">
		  					<img v-if="editFormEntity.company.image" :src="editFormEntity.company.image" class="avatar">
		  					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
					<el-form-item label="企业文化" prop="enterprise.content">
						<el-input type="textarea" placeholder="请输入企业文化" autosize v-model="editFormEntity.enterprise.content">
					</el-form-item>
					<el-form-item label="文化图片" prop="enterprise.image">
						<el-upload class="avatar-uploader" :action="uploadUrl"
							:multiple="false" accept="image/*" :limit="1024" :auto-upload="true"
		  					:show-file-list="false" :on-success="handleUploadEnterpriseSuccess">
		  					<img v-if="editFormEntity.enterprise.image" :src="editFormEntity.enterprise.image" class="avatar">
		  					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="saveCompanyInfo('editFormEntity', '${urlPath }companyInfo/save.do')">保 存</el-button>
					</el-form-item>
 				</el-form>
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
	var defaultCompanyInfo =  {
		company : {
			content : "${companyInfo.company.content}",
			image : "${companyInfo.company.image}"
		},
		enterprise : {
			content : "${companyInfo.enterprise.content}",
			image : "${companyInfo.enterprise.image}"
		}
	};
	
	// 创建分页表格
	var app = new Common({
		data : function() {
			return {
				defaultActive : "companyInfo/grid.do",
	          	rules : {// 校验规则
	          		"company.content" : [
	          			{ required : true, message: '请输入公司简介！', trigger: 'change' },
	          			{ min : 1, max : 500, message: '公司简介必须是1到500个字符！', trigger: 'change' }
	          		],
	          		"company.image" : [
		          		{ required : true, message: '请上传公司简介图片！', trigger: 'change' }
		          	],
	          		"enterprise.content" : [
	          			{ required : true, message: '请输入企业文化！', trigger: 'change' },
	          			{ min : 1, max : 500, message: '企业文化必须是1到500个字符！', trigger: 'change' }
	          		],
	          		"enterprise.image" : [
	          			{ required : true, message: '请上传企业文化图片！', trigger: 'change' }
	          		]
	          	},
	          	editFormEntity : Vue.copy(defaultCompanyInfo, {}),// 编辑表单实体
	          	defaultEditFormEntity : defaultCompanyInfo,// 默认编辑表单实体
		        baseUrl : "${urlPath}",// 根路径
		        uploadUrl : "${urlPath}companyInfo/uploadImage.do"// 上传路径
			}
		},
		methods : {
			handleUploadCompanySuccess : function(res) {// 处理上传成功事件
				if (res.success) {// 成功
			        this.editFormEntity.company.image = res.data;
				} else {// 失败
					this.$message.error(res.message);
				}
	      	},
			handleUploadEnterpriseSuccess : function(res) {// 处理上传成功事件
				if (res.success) {// 成功
			        this.editFormEntity.enterprise.image = res.data;
				} else {// 失败
					this.$message.error(res.message);
				}
	      	},
	      	saveCompanyInfo : function(formName, url) {
	      		this.submitForm(formName, url);
	      	}
		}
	});
	
	// 挂载分页表格
	app.$mount('#app');
</script>
</body>
</html>