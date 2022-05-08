[#ftl]
[@b.head/]
[@b.toolbar title="修改教室"]bar.addBack();[/@]
[#assign is_virtual][#if classroom.persisted && !classroom.roomNo??]1[#else]0[/#if][/#assign]
  [@b.form action=b.rest.save(classroom) theme="list" onsubmit="validVirtual"]
    [@b.textfield name="classroom.code" label="代码" value="${classroom.code!}" required="true" maxlength="20"/]
    [@b.textfield name="classroom.name" label="名称" value="${classroom.name!}" required="true" maxlength="20"/]
    [@b.radios name="virtual_room"  label="是否虚拟" items={"1":"虚拟教室，没有实际房间","0":"对应具体房间"} value=is_virtual required="true"/]
    [@b.textfield name="classroom.roomNo" label="房间号" value=classroom.roomNo! required="false" maxlength="20"/]
    [@b.textfield name="classroom.enName" label="英文名" value="${classroom.enName!}" maxlength="100" style="width:400px"/]
    [@b.select name="classroom.campus.id" label="校区" value=classroom.campus
               style="width:200px;" items=campuses option="id,name" empty="..."  required="true"/]
    [@b.select name="classroom.building.id" label="教学楼" value=classroom.building!
               style="width:200px;" items=buildings option="id,name" empty="..."  required="false"/]
    [@b.select name="classroom.roomType.id" label="教室类型" value="${(classroom.roomType.id)!}"
               style="width:200px;" items=roomTypes option="id,name" empty="..."  required="true"/]
    [@b.textfield name="classroom.capacity" label="最大容量" required="true"  value=classroom.capacity! maxlength="20"/]
    [@b.textfield name="classroom.courseCapacity" label="上课容量" required="true"  value="${classroom.courseCapacity!}" maxlength="20"/]
    [@b.textfield name="classroom.examCapacity" label="考试容量"  required="true" value="${classroom.examCapacity!}" maxlength="20"/]
    [@b.select2 label="使用部门" name1st="departId1st" name2nd="departId2nd" style = "height:100px;width:152px"
      items1st=departs items2nd= classroom.departs
      option="id,name"  required="true" /]
    [@b.startend label="有效期"  name="classroom.beginOn,classroom.endOn" required="true,false"
      start=classroom.beginOn end=classroom.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
    [/@]
  [/@]
<script>
  function validVirtual(form){
     if(form['virtual_room'].value=='0'){
        if(!form['classroom.roomNo'].value){
           alert("请选择具体房间");
           return false;
        }
     }
     return true;
  }
</script>
[@b.foot/]
