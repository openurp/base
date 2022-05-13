[#ftl/]
[@b.head/]
[@b.toolbar title="班级信息"]bar.addBack();[/@]
<table class="formTable" style="width:90%;margin:10px auto auto;">
  <tr><td align="center" colspan="4" class="index_view"><b>班级基本信息</b></td></tr>
  <tr>
    <td class="title" width="20%">代码名称:</td>
    <td class="brightStyle" width="30%">${squad.code} ${(squad.name)!}</td>
    <td class="title" width="20%">年级:</td>
    <td class="brightStyle" width="30%">${(squad.grade)!}</td>
  </tr>
  <tr>
    <td class="title">院系:</td>
    <td>${(squad.department.name)!}</td>
    <td class="title" width="20%">学生类别:</td>
    <td>${(squad.stdType.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">专业/方向:</td>
    <td>${(squad.major.name)!} ${(squad.direction.name)!}</td>
    <td class="title" width="20%">校区:</td>
    <td>${(squad.campus.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">实际/计划人数:</td>
    <td>${(squad.stdCount)!}/${(squad.planCount)!}</td>
    <td class="title" width="20%">管理人员</td>
    <td>
      [#if squad.mentor??]辅导员:${squad.mentor.name}(${squad.mentor.code})[/#if]
      [#if squad.tutor??]班导师:${squad.tutor.name}(${squad.tutor.code})[/#if]
      [#if squad.master??]班主任:${squad.master.name}(${squad.master.code})[/#if]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期:</td>
    <td>${squad.beginOn?string('yyyy-MM-dd')}~${(squad.endOn?string('yyyy-MM-dd'))!}</td>
    <td class="title" width="20%">更新日期:</td>
    <td>${(squad.updatedAt?string('yyyy-MM-dd HH:mm:ss'))!}</td>
  </tr>
</table>

  <div style="width:90%;margin:auto;">
  [#list students?chunk(4) as row]
    <table align="center" width="100%" style="border:1px solid #A6C9E2;">
     <tr>
      [#list row as student]
      <td width="25%" style="background-color:#e8eefa;">
        <table width="100%">
          <tr>
            <td rowspan="3" width="80px"><img src="${urp.api}/platform/user/avatars/${md5.digest(student.user.code)}.jpg" width="40px" height="46px"/></td>
          </tr>
          <tr>
            <td>${(student.user.code)!}</td>
          </tr>
          <tr>
             <td>
               ${(student.person.name)!}&nbsp;
               ${(student.person.gender.name)!}&nbsp;
               [#if status[student.user.code]?? && !status[student.user.code].inschool]
                   <font color="red">${(status[student.user.code].status.name)!}</font>
               [/#if]
             </td>
          </tr>
         </table>
      </td>
      [/#list]
      [#if row?size <4]
        <td colspan="${4-row?size}" style="background-color:#e8eefa;">&nbsp;</td>
      [/#if]
     </tr>
    </table>
  [/#list]
  </div>
<script>
  function infoStd(stdId){
    bg.Go("${base}/studentSearch!info.action?studentId="+stdId,"_blank");
  }
</script>
[@b.foot /]
