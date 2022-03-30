<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel='stylesheet' href='webjars/bootstrap/4.5.3/css/bootstrap.min.css'>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <aside class="col-sm-4">
            <div class="card">
                <article class="card-body">
                    <h4 class="card-title mb-4 mt-1">HelpDeak!</h4>
                    <core:if test="${param.termsOfService == true}">
                        <div class="alert alert-danger">
                            Your must be agree with terms of service!
                        </div>
                    </core:if>

                    <core:if test="${param.loginError == true}">
                        <div class="alert alert-danger">
                            Your login incorrect!
                        </div>
                    </core:if>
                    <form method="post" action="<%=request.getContextPath()%>/">
                        <div class="form-group">
                            <label>Your login</label>
                            <input name="login" class="form-control" placeholder="login" type="text">
                        </div>
                        <div class="form-group">
                            <div class="checkbox">
                                <label> <input type="checkbox" name="termsOfService">
                                    I agree with terms of service
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block"> Login</button>
                        </div>
                    </form>
                </article>
            </div>
        </aside>
    </div>
</div>

<script type="text/javascript" src="webjars/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/4.5.3/js/bootstrap.min.js"></script>
</body>
</html>