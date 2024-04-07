[#ftl/]
[@b.head/]
[@b.toolbar title="班级信息"]bar.addBack();[/@]
<table class="infoTable" style="width:90%;margin:10px auto auto;">
  <colgroup>
    <col width="7%"/>
    <col width="18%"/>
    <col width="7%"/>
    <col width="18%"/>
    <col width="7%"/>
    <col width="18%"/>
    <col width="7%"/>
    <col width="18%"/>
  </colgroup>
  <tr><td align="center" colspan="8" class="index_view"><b>班级基本信息</b></td></tr>
  <tr>
    <td class="title">代码:</td>
    <td>${squad.code}</td>
    <td class="title">名称:</td>
    <td>${(squad.name)!}</td>
    <td class="title">简称:</td>
    <td>${(squad.shortName)!}</td>
    <td class="title">英文名称:</td>
    <td>${(squad.enName)!}</td>
  </tr>
  <tr>
    <td class="title">年级:</td>
    <td>${(squad.grade)!}</td>
    <td class="title">培养类型:</td>
    <td>${(squad.eduType.name)!}</td>
    <td class="title">层次:</td>
    <td>${(squad.level.name)!}</td>
    <td class="title">学生类别:</td>
    <td>${(squad.stdType.name)!}</td>
  </tr>
  <tr>
    <td class="title">院系:</td>
    <td>${(squad.department.name)!}</td>
    <td class="title">专业:</td>
    <td>${(squad.major.name)!}</td>
    <td class="title">方向:</td>
    <td>${(squad.direction.name)!}</td>
    <td class="title">校区:</td>
    <td>${(squad.campus.name)!}</td>
  </tr>
  <tr>
    <td class="title">人数:</td>
    <td>计划：${(squad.planCount)!} 实际：${(squad.stdCount)!} </td>
    <td class="title">管理人员:</td>
    <td>
      [#if squad.mentor??]辅导员:${squad.mentor.name}(${squad.mentor.code})[/#if]
      [#if squad.tutor??]班导师:${squad.tutor.name}(${squad.tutor.code})[/#if]
      [#if squad.master??]班主任:${squad.master.name}(${squad.master.code})[/#if]
    </td>
    <td class="title">生效日期:</td>
    <td>${squad.beginOn?string('yyyy-MM-dd')}~${(squad.endOn?string('yyyy-MM-dd'))!}</td>
    <td class="title">更新日期:</td>
    <td>${(squad.updatedAt?string('yyyy-MM-dd HH:mm:ss'))!}</td>
  </tr>
</table>

  <div style="width:90%;margin:auto;">
  [#list students?chunk(4) as row]
    <table align="center" width="100%" style="border-bottom:1px solid #A6C9E2;">
     <tr>
      [#list row as student]
      <td width="25%">
        <table width="100%">
          <tr>
            <td rowspan="3" width="80px">[@ems.avatar username=student.code  width="40px" height="46px"/]</td>
          </tr>
          <tr>
            <td>${(student.code)!}</td>
          </tr>
          <tr>
             <td>
               ${(student.name)!}&nbsp;
               ${(student.gender.name)!}&nbsp;
               [#if student.level != squad.level] ${(student.level.name)!}&nbsp;[/#if]
               [#if ((student.state.major.name)!'--') != ((squad.major.name)!'--')] ${(student.state.major.name)!'--'}&nbsp;[/#if]
               [#if student.state.status.id != 1]
                   <font color="red">${student.state.status.name}</font>
               [/#if]
             </td>
          </tr>
         </table>
      </td>
      [/#list]
      [#if row?size <4]
        <td colspan="${4-row?size}">&nbsp;</td>
      [/#if]
     </tr>
    </table>
  [/#list]
  </div>
[@b.foot /]
