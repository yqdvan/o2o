
$(function() {
//    $.toast('Js is running!!!');
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    getShopInitInfo();
    alert(initUrl);
    function getShopInitInfo(){
        $.getJSON(initUrl,function(data){
            if(data.success){
                var tempHtml = '';
                var tempAreaHtml = '';
//                data.shopCategoryList.map(function(item,index){
//                    tempHtml += '<option data-id=' + item.shopCategoryId + '">'
//                    + item.shopCategoryName + '</option>';
//                });
//                data.areaList.map(function(item,index){
//                    tempAreaHtml += '<option data-id=' + item.areaId + '">'
//                    + item.areaName + '</option>';
//                });
//                $('#shop-category').html(tempHtml );
//                $('#area').html(tempAreaHtml);
				// 迭代店铺分类列表
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				// 迭代区域信息
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
            }
        });
        $('#submit').click(function(){
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();

//            shop.shopCategory = {
//                shopCategoryId:$('#shop-category').find('option').not(function(){
//                    return !this.selected;
//                }).data('id')
//            };
            // 选择id,双重否定=肯定
            shop.shopCategory = {
                // 这里定义的变量要和ShopCategory.shopCategoryId保持一致，否则使用databind转换会抛出异常
                shopCategoryId : $('#shop-category').find('option').not(function() {
                    return !this.selected;
                }).data('id')
            };

            shop.area = {
                areaId:$('#area').find('option').not(function(){
                    return !this.selected;
                }).data('id')
            };

            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));
            var verifyCodeActual = $('#j_kaptcha').val();
            if (!verifyCodeActual) {
                $.toast("请输入验证码！");
                return;
            } else {
                formData.append('verifyCodeActual', verifyCodeActual);
            }

            $.ajax({
                url:registerShopUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast("提交成功！");
                    } else {
                        $.toast("FK!提交失败！" + data.errMsg);
                    }
                    $('#captcha_img').click();
//                    $.toast('after click captcha img!');
                }
            });
        })
    }
})
