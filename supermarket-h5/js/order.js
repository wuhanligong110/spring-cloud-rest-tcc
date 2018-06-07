//获取运费
function getFreight(){
  var csn_id = $('#consignee h4').data('id') || 0;
  $.getJSON(freightApi, {csn_id:csn_id, shipping_id:$('#shipping_method').val()}, function(res){
    if(res.status == 'success') {
      $('#shipping-amount').text(res.amount);
      var totals = parseFloat($('#goods-amount').text()) + parseFloat(res.amount);
      $('#total-amount').text(totals.toFixed(2));
    }
  });
}

//弹出收件人列表
function popCsnList(){
  $('#csnli').show().animate({left: 0}, 200, function(){$('#wrapper').hide()});
}

//新建收件人
function addCsn(){
  var container = $('#csnform');
  container.find('.main').html($('#csn-form-tpl').html());
  container.show().animate({left: 0}, 200, function(){
    $('#wrapper').hide();
    $('#csnli').css({display:'none', left:'100%'});
  });
}

//隐藏收件人表单
function hideCsnForm(){
  $('#wrapper').hide();
  $('#csnli').css({left:0,display:'block'});
  $('#csnform').animate({left:'100%'}, 200, function(){$(this).hide()});
}

//隐藏收件人列表
function hideCsnList(){
  $('#wrapper').show();
  $('#csnli').animate({left:'100%'}, 200, function(){$(this).hide()});
}

//触发选换收件人
function onChangeCsn(e){
  var container = $(e).parent();
  if(container.hasClass('checked')) return false;
  /*$.vdsConfirm({
    content: '您确定要更换此收件人地址吗?',
    ok: function(){
      var html = '<div class="unfold fr"><i class="iconfont">&#xe614;</i></div>';
      html += container.find('dd.m').html();
      $('#consignee .rc').html(html);
      container.siblings('.checked').removeClass('checked');
      container.addClass('checked');
      $('#wrapper').show();
      $('#csnli').animate({left:'100%'}, 200);
    },
  });*/
    var html = '<div class="unfold fr"><i class="icon ion-android-checkbox-outline"></i></div>';
    html += container.find('dd.m').html();
    $('#consignee .rc').html(html);
    container.siblings('.checked').removeClass('checked');
    container.addClass('checked');
    $('#wrapper').show();
    $('#csnli').animate({left:'100%'}, 200);
}

//编辑收件人信息
function editCsn(e){
  $('#csnform .main').html($('#csn-form-tpl').html());
  var form = $('#csnform form'), data = $(e).data('json');
  form.find('input[name="id"]').val(data.id);
  form.find('input[name="receivingUserName"]').val(data.receivingUserName);
  form.find('input[name="mobile"]').val(data.mobile);
  form.find('input[name="provinceName"]').val(data.provinceName);
  form.find('input[name="cityName"]').val(data.cityName);
  form.find('input[name="area"]').val(data.area);
  form.find('textarea[name="receivingAddress"]').val(data.receivingAddress);
  $('#csnform').show().animate({left: 0}, 200, function(){
    $('#wrapper').hide();
    $('#csnli').css({display:'none', left:'100%'});
  });
}

//保存收件人表单
function saveCsnForm(data){

        /*var row = $('#csnli').find('#csnopt-'+data.id);
        if(row.size() > 0){
          if(row.hasClass('checked')){
            res.data.checked = 1;
            $('#consignee .rc').html(juicer($('#csn-checked-tpl').html(), data));
            getFreight();
          }
          row.remove();
        }*/
        $('#csnli .opts').prepend(juicer($('#csn-row-tpl').html(),data));
        hideCsnForm();

}