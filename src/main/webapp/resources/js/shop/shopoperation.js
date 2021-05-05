$(function() {
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    getshopinitinfo();
    alert(initUrl);
    function getshopinitinfo(){
        $.getJSON(initUrl,function(data){
            if(data.sucess){
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function(item,index){
                    tempHtml += '<option data-id=' + item.shopCategoryId + '">'
                    + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function(item,index){
                    tempAreaHtml += '<option data-id=' + item.areaId + '">'
                    + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml );
                $('#area').html(tempAreaHtml);
            }
        });
        $('#submit').click(function(){
            var shop = {};
            shop.shopName = $('#ship-name').val();
            shop.shopAddr = $('#ship-addr').val();
            shop.phone = $('#ship-phone').val();
            shop.shopCategory = {
                shopCategoryId:$('shop-category').find('option').not(function(){
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

            $.ajax({
                url:registerShopUrl,
                type:'POST',
                data:formData,
                contentType:false,
                proceesData:false,
                cache:false,
                sucess:function(data){
                    if(data.success){
                        $.toast('提交成功!');
                    }else{
                        $.toast('提交失败!' + data.errMsg);
                    }
                }
            });
        })
    }
})