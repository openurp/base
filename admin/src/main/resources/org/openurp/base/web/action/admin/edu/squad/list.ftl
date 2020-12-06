[#ftl]
[@b.head/]
[@b.grid items=squads var="squad"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol name="squad.id"/]
    [@b.col width="9%" property="code" title="代码"]${squad.code}[/@]
    [@b.col width="23%" property="name" title="名称"][@b.a href="!info?id=${squad.id}"]${squad.name}[/@][/@]
    [@b.col width="5%" property="grade" title="年级"]${squad.grade!}[/@]
    [@b.col width="6%" property="level" title="培养层次"]${(squad.level.name)!}[/@]
    [@b.col width="15%" property="department" title="院系"]${(squad.department.name)!}[/@]
    [@b.col width="18%" property="major" title="专业(方向)"]${(squad.major.name)!} ${(squad.direction.name)!}[/@]
    [@b.col width="8%" property="stdType" title="学生类别"]${(squad.stdType.name)!}[/@]
    [@b.col width="5%" property="stdCount" title="人数"]${squad.stdCount!}[/@]
    [@b.col width="8%" property="instructor" title="辅导员"]${(squad.instructor.user.name)!}[/@]
  [/@]
[/@]
<form name="ImportExportForm" id="ImportExportForm" method="post" target="_blank"></form>
<script>
  //导出班级学生或者班级
  function exportData(value){
    if (bg.input.getCheckBoxValues("squad.id") == "") {
      if(confirm("是否导出查询条件内的所有数据?")){
        getExportData(value);
      }
      return;
    }else{
      getExportData(value);
    }
  }

  function getExportData(value){
    if(value=='std'){
      bg.form.addInput(document.searchForm,"exportType",value);
      bg.form.addInput(document.searchForm,"squadIds",bg.input.getCheckBoxValues("squad.id"));
      bg.form.addInput(document.searchForm,"keys","squad.code,squad.name,squad.department.name,squad.major.name,squad.stdType.name,code,name,gender.name,studentJournal.status");
      bg.form.addInput(document.searchForm,"titles","班级代码,班级名称,班级院系,班级专业,班级学生类别,学生代码,学生姓名,学生性别,学籍状态");
      bg.form.addInput(document.searchForm,"fileName","班级学生列表");
    }
    if(value=='squad'){
      bg.form.addInput(document.searchForm,"exportType",value);
      bg.form.addInput(document.searchForm,"squadIds",bg.input.getCheckBoxValues("squad.id"));
      bg.form.addInput(document.searchForm,"keys","code,name,grade,department.name,level.name,major.name,direction.name,stdType.name,planCount,stdCount,createdAt,updatedAt");
      bg.form.addInput(document.searchForm,"titles","班级代码,班级名称,年级,院系,培养层次,专业,方向,学生类别,计划人数,学籍有效人数,创建时间,修改时间");
      bg.form.addInput(document.searchForm,"fileName","班级信息");
    }
    bg.form.submit(document.searchForm, "${b.url('!export')}", '_blank');
  }
</script>
[@b.foot/]
