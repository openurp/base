[#ftl/]
[@b.head/]
[@b.toolbar title="班级信息"]bar.addBack();[/@]
<table class="formTable" style="width:90%;margin:10px auto auto;">
  <tr><td align="center" colspan="4" class="index_view"><b>班级基本信息</b></td></tr>
  <tr>
    <td class="title" width="20%">班级名称:</td>
    <td class="brightStyle" width="30%">${(squad.name)!}</td>
    <td class="title" width="20%">班级代码:</td>
    <td class="brightStyle" width="30%">${(squad.code)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">院系:</td>
    <td width="30%">${(squad.department.name)!}</td>
    <td class="title" width="20%">学生类别:</td>
    <td width="30%">${(squad.stdType.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">专业/方向:</td>
    <td width="30%">${(squad.major.name)!} ${(squad.direction.name)!}</td>
    <td class="title" width="20%">校区:</td>
    <td width="30%">${(squad.campus.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">计划人数:</td>
    <td width="30%">${(squad.planCount)!}</td>
    <td class="title" width="20%">人数:</td>
    <td width="30%">${(squad.stdCount)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">辅导员</td>
    <td width="30%">[#if squad.instructor??]${squad.instructor.user.name}(${squad.instructor.user.code})[/#if]</td>
    <td class="title" width="20%">更新日期:</td>
    <td width="30%">${(squad.updatedAt?string('yyyy-MM-dd HH:mm:ss'))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期:</td>
    <td width="30%">${squad.beginOn?string('yyyy-MM-dd')}</td>
    <td class="title" width="20%">失效日期:</td>
    <td width="30%">${(squad.endOn?string('yyyy-MM-dd'))!}</td>
  </tr>
</table>
<div style="width:90%;margin:auto;background-color:#E1ECFF;text-align:center">
  <b>班级学生列表</b>
</div>
  <div style="width:90%;margin:auto;">
    [#list students! as student]
      [#if student_index%3==0]
        <table align="center" width="100%" style="border:1px solid #A6C9E2;">
          <tr>
      [/#if]
          <td width="30%" style="background-color:#e8eefa;">
          <table width="100%">
              <tr>
                <td rowspan="3" width="80px"><img src="${urp.api}/platform/user/avatars/${md5.digest(student.user.code)}.jpg" width="40px" height="55px"/></td>
              </tr>
              <tr>
                   <td>${(student.user.code)!}</td>
              </tr>
              <tr>
                   <td>
                     ${(student.person.name)!}&nbsp;
                     ${(student.person.gender.name)!}<br>
                     [#if status[student.user.code]?? && status[student.user.code].inschool]
                         在校
                     [/#if]
                     [#if status[student.user.code]?? && !status[student.user.code].inschool]
                         <font color="red">${(status[student.user.code].status.name)!}</font>
                     [/#if]
                   </td>
              </tr>
           </table>
         </td>
      [#if student_index%3==2]
          </tr>
        </table>
      [/#if]
      [#if (students?size)%3==1&&student_index==(students?size-1)]
          </tr>
        </table>
      [/#if]
      [#if (students?size)%3==2&&student_index==(students?size-1)]
            <td width="30%" style="background-color:#e8eefa;">
              <table width="100%">
                  <tr><td>&nbsp;</td></tr>
                  <tr><td>&nbsp;</td></tr>
                  <tr><td>&nbsp;</td></tr>
               </table>
             </td>
           </tr>
        </table>
      [/#if]
    [/#list]
    [#if (students?size)==0]
      <div align="center" style="color:#666666;background:#E1ECFF;"><b>该班级没有学生!</b></div>
    [/#if]
  </div>
<script>
  function infoStd(stdId){
    bg.Go("${base}/studentSearch!info.action?studentId="+stdId,"_blank");
  }
</script>
[@b.foot /]
