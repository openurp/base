[@b.head/]
[#include "/org/openurp/app/nav.ftl"/]
[@displayFrame/]
[#if schools?size > 1]
<script>
var schools=[];
[#list schools as school]
  schools.push({id:${school.id},name:'${school.name}'});
[/#list]
var createSchoolNav=function(){
      var schoolSelectTemplate=
       '<li class="dropdown">' +
          '<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="school_switcher" aria-expanded="false">{first}' +
              '<span class="caret"></span></a> '+
          '<ul class="dropdown-menu">{list}</ul>' +
      '</li>';
      var schoolTemplate='<li><a href="{school.url}">{school.name}</a></li>'
        var schoolhtml= schoolSelectTemplate.replace('{first}','${school.name}');
        var list="";
        for(var i=0;i<schools.length;i++){
          if(schools[i].id != ${school.id}){
            var url= (location.origin + location.pathname +"?schoolId=" +schools[i].id);
            var schoolItem=schoolTemplate.replace("{school.url}",url);
            schoolItem=schoolItem.replace("{school.name}",schools[i].name);
            list +=schoolItem
          }
        }
        schoolhtml = schoolhtml.replace('{list}',list);
        jQuery('.navbar-custom-menu > .navbar-nav').prepend(schoolhtml)
    }
    createSchoolNav();
</script>
[/#if]
[@b.foot/]
