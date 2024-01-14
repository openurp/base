[#ftl]
[@b.head/]
[@b.toolbar title="个人资料维护"]bar.addBack();[/@]
[@b.form action=sa theme="list" action=b.rest.save(profile) enctype="multipart/form-data"]
  [@b.field label="账户"]${staff.code} ${staff.name} ${staff.department.name}[/@]
  [@b.select label="教学院系" name="staff.department.id" items=departs value=staff.department required="true"/]
  [@b.select label="职称" name="staff.title.id" items=titles value=staff.title!  /]
  [@b.select label="学位" name="staff.degree.id" items=degrees value=staff.degree! /]
  [@b.select label="学历" name="staff.educationDegree.id" items=eduDegrees value=staff.educationDegree! /]
  [@b.textarea label="个人简介" name="profile.intro" value=profile.intro! placeholder="个人简介" cols="110" rows="10" required="true" maxlength="30000" comment="30000字以内"/]
  [@b.textarea label="研究领域" name="profile.research" value=profile.research! cols="110" rows="3" maxlength="300" comment="研究领域、方向；300字以内"/]
  [@b.textarea label="教授课程" name="profile.courses" value=profile.courses! placeholder="教授课程名称" cols="110" rows="3" maxlength="300" comment="300字以内"/]

  [@b.textarea label="学术工作经历" name="profile.career" value=profile.career!  placeholder="学术简介、教学经历" cols="110" rows="10" maxlength="1000" comment="学术简介、教学经历;1000字以内"/]
  [@b.textarea label="获奖情况" name="profile.awards" value=profile.awards!  placeholder="所获荣誉、获奖情况" cols="110" rows="5" maxlength="1000" comment="所获荣誉、获奖情况;500字以内"/]
  [@b.textarea label="科研成果" name="profile.harvest" value=profile.harvest!  placeholder="科研成果，主要著述" cols="110" rows="15" maxlength="30000" comment="科研成果，主要著述；30000字以内"/]
  [@b.textarea label="研究项目" name="profile.projects" value=profile.projects! placeholder="科研项目" cols="110" rows="4" maxlength="1000" comment="1000字以内"/]
  [@b.textarea label="学术兼职" name="profile.titles" value=profile.titles!  cols="110" rows="3" maxlength="500" comment="500字以内"/]
  [@b.textarea label="联系信息" name="profile.contact" placeholder="办公室电话、地址、电子邮箱等" value=profile.contact! cols="110" rows="2" comment="200字以内"/]

  [@b.formfoot]
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
  [/@]
[/@]
[@b.foot/]