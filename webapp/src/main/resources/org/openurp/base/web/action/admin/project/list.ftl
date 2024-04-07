[#ftl]
[@b.head/]
[@b.grid items=projects var="project"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="17%" property="name" title="名称"][@b.a href="!info?id=${project.id}"]${project.name}[/@][/@]
    [@b.col property="school" title="适用学校"]${project.school.name}[/@]
    [@b.col width="8%" property="minor" title="辅修"]${(project.minor?string("是","否"))!}[/@]
    [@b.col width="10%" title="院系列表"]
      [#assign departNames][#list project.departments as department]${department.name}<#sep>,[/#list][/#assign]
      <span title="${departNames}">${project.departments?size}个院系</span>
    [/@]
    [@b.col width="10%" title="学生类别"]
      [#assign stdTypeNames][#list project.stdTypes as st]${st.name}<#sep>,[/#list][/#assign]
      <span title="${stdTypeNames}">${project.stdTypes?size}个学生类别</span>
    [/@]
    [@b.col width="15%" property="category.name" title="类型"/]
    [@b.col width="15%" property="beginOn" title="有效期"]${project.beginOn!}~${project.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
