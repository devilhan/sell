<html>
    <#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
        <#include "../common/nav.ftl">
<#--主要内容content-->
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" action="/sell/seller/category/save" method="post">
                    <div class="form-group">
                        <label>名字</label><input type="text" name="categoryName" class="form-control" value="${(category.categoryName)!''}" />
                    </div>
                    <div class="form-group">
                        <label>类型</label><input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!''}"/>
                    </div>
                    <input hidden type="text" name="categoryId" <#if (category.categoryId)??> value="${(category.categoryId)!''}"</#if>>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>