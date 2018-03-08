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
	// 创建分页表格
	var app = new Common({
		data : function() {
			return {
				defaultActive : "",
				baseUrl : "${urlPath}"
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