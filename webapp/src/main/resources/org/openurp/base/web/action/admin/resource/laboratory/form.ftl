[#ftl]
[@b.head/]
[@b.toolbar title="修改教室"]bar.addBack();[/@]
[#assign is_virtual][#if laboratory.persisted && !laboratory.roomNo??]1[#else]0[/#if][/#assign]
  [@b.form action=b.rest.save(laboratory) theme="list" onsubmit="validVirtual"]
    [@b.textfield name="laboratory.code" label="代码" value="${laboratory.code!}" required="true" maxlength="20"/]
    [@b.textfield name="laboratory.name" label="名称" value="${laboratory.name!}" required="true" maxlength="20"/]
    [@b.radios name="virtual_room"  label="是否虚拟" items={"1":"虚拟教室，没有实际房间","0":"对应具体房间"} value=is_virtual required="true"/]
    [@b.textfield name="laboratory.roomNo" label="房间号" value=laboratory.roomNo! required="false" maxlength="20"/]
    [@b.select name="laboratory.campus.id" label="校区" value=laboratory.campus
               style="width:200px;" items=campuses option="id,name" empty="..."  required="true"/]
    [@b.select name="laboratory.building.id" label="教学楼" value=laboratory.building!
               style="width:200px;" items=buildings option="id,name" empty="..."  required="false"/]
    [@b.startend label="有效期"  name="laboratory.beginOn,laboratory.endOn" required="true,false"
      start=laboratory.beginOn end=laboratory.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
    [/@]
  [/@]
<script>
  function validVirtual(form){
     if(form['virtual_room'].value=='0'){
        if(!form['laboratory.roomNo'].value){
           alert("请选择具体房间");
           return false;
        }
     }
     return true;
  }
</script>
[@b.foot/]
