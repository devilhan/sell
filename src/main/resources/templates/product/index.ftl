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
                    <form role="form" action="/sell/seller/product/save" method="post">
                        <div class="form-group">
                            <label>名称</label><input type="text" name="productName" class="form-control" value="${(productInfo.productName)!''}" />
                        </div>
                        <div class="form-group">
                            <label>价格</label><input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label><input name="productStock" type="number" value="${(productInfo.productStock)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>描述</label><input name="productDescription" type="text" value="${(productInfo.productDescription)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img <#if (productInfo.productIcon)??>style="height: 100px;width: 100px;display: block"</#if> src="${(productInfo.productIcon)!''}" alt="">
                            <input name="productIcon" type="text" value="${(productInfo.productIcon)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categories as category>
                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>>
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" <#if (productInfo.productId)??> value="${(productInfo.productId)!''}"</#if>>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>