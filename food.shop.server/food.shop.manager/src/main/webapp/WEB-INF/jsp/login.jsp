<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<!-- 登录样式 -->
<link rel="stylesheet" href="${urlPath }css/login.css">
</head>
<body>
	<el-form id="app" :model="loginForm" ref="loginForm" label-width="60px" label-position="right" @keyup.native.enter="login">
		<div class="login-page">
			<div class="dialog">
				<div class="info">
					<el-form-item label="用户名" prop="username">
						<el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
					</el-form-item>
		    		<el-form-item label="密码" prop="password">
						<el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
					</el-form-item>
		    		<el-form-item label="" prop="rememberMe">
						<el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
					</el-form-item>
				</div>
				<div class="btndiv">
					<el-button type="primary" style="width:100%;" @click="login">登录</el-button>
				</div>
				<div class="logos">
					<div class="copyright ng-binding">© 2017 版权所有</div>
				</div>
			</div>
		</div>
	</el-form>
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
		var app = new Base({
			data : function(){
				return {
					loginUrl : "${utlPath }login.do",// 登录URL
					loginForm : {// 登录表单参数
						username : null,
						password : null,
						rememberMe : true
					}
				}
			}, 
			methods : {
				login : function() {// 登录
					// 获取实例对象
					var _this = this;
					// 请求数据
					axios.get(_this.loginUrl, {params : _this.loginForm})
						.then(function (response) {
							// 获取响应数据
							var r = response.data;
							if (r.curSysUser || r.success) {
								window.location.href = "${urlPath }index.do";
							} else {
								// 提示错误信息
								_this.$message.error(r.message);
							}
					 	}).catch(function (error) {// 请求数据处理失败
					 		// 提示错误信息
					 		_this.$message.error("服务器异常！");
					 	});
				}
			}
		});
	
		app.$mount("#app");
	</script>
</body>
</html>