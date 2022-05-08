[#ftl]
[@b.head/]
[@b.toolbar title="课程信息维护"]
  bar.addItem("新课查询", function() {
  bg.form.submit(document.courseSearchForm, "${b.url("!newCourses")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
        [@b.form name="courseSearchForm" action="!search" target="courselist" title="ui.searchForm" theme="search"]
            [@b.textfields names="course.code;代码"/]
            [@b.textfields names="course.name;名称"/]
            [@b.select style="width:100px" name="course.courseType.id" label="课程类别" items=courseTypes option="id,name" empty="..." /]
            [@b.select style="width:100px" name="course.nature.id" label="课程性质" items=courseNatures option="id,name" empty="..." /]
            [@b.select style="width:100px" name="course.category.id" label="评教分类" items=courseCategories option="id,name" empty="..." /]
            [@b.select style="width:100px" name="course.department.id" label="所属院系" items=departments option="id,name" empty="..." /]
            [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." /]
            [#if teachingOffices?size>0]
            [@b.select style="width:100px" name="course.teachingOffice.id" label="教研室" items=teachingOffices option="id,name" empty="..." /]
            [/#if]
            <input type="hidden" name="orderBy" value="course.code"/>
        [/@]
    </div>
    <div class="search-list">[@b.div id="courselist" href="!search?orderBy=course.code asc"/]
  </div>
</div>
[@b.foot/]
