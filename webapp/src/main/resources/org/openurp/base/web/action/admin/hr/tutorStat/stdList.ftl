[@b.grid items=stds var="student" theme="mini"]
  [@b.row]
    [@b.col property="code" title="学号" width="100px"/]
    [@b.col property="name" title="姓名" width="80px"]
      <div title="${student.name}" class="text-ellipsis">${student.name}</div>
    [/@]
    [@b.col property="gender.name" title="性别" width="50px"/]
    [@b.col property="state.grade" title="年级" width="60px"/]
    [@b.col property="level.name" title="培养层次" width="70px"/]
    [@b.col property="eduType.name" title="培养类型" width="70px"/]
    [@b.col property="state.department.name" title="院系" width="90px"]
      ${student.state.department.shortName!student.state.department.name}
    [/@]
    [@b.col property="state.major.name" title="专业与方向"]
      ${(student.state.major.name)!} ${(student.state.direction.name)!}
    [/@]
  [/@]
[/@]
