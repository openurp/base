import { backendPage } from '@/config'

export interface AdminLink {
  label: string
  href: string
}

export interface AdminModule {
  path: string
  titleKey: string
  links: AdminLink[]
}

const page = (path: string, label: string): AdminLink => ({ label, href: backendPage(path) })

/** 管理功能分组。href 仍打开 /base/admin 上的页面。对应 JSON 在 /api/base/admin。 */
export const adminModules: AdminModule[] = [
  {
    path: '/org',
    titleKey: 'nav.org',
    links: [
      page('/base/admin/school', '学校'),
      page('/base/admin/department', '部门'),
      page('/base/admin/depart-transition', '部门变迁'),
      page('/base/admin/campus', '校区'),
    ],
  },
  {
    path: '/calendar',
    titleKey: 'nav.calendar',
    links: [
      page('/base/admin/school-year', '学年'),
      page('/base/admin/semester', '学期'),
      page('/base/admin/calendar', '校历'),
      page('/base/admin/calendar-stage', '校历阶段'),
      page('/base/admin/holiday', '节假日'),
    ],
  },
  {
    path: '/edu',
    titleKey: 'nav.edu',
    links: [
      page('/base/admin/edu/major', '专业'),
      page('/base/admin/edu/course', '课程'),
      page('/base/admin/edu/textbook', '教材'),
      page('/base/admin/edu/teaching-office', '教研室'),
      page('/base/admin/edu/time-setting', '上课时间'),
      page('/base/admin/edu/code-index', '教学代码'),
    ],
  },
  {
    path: '/hr',
    titleKey: 'nav.hr',
    links: [
      page('/base/admin/hr/staff', '教职工'),
      page('/base/admin/hr/teacher', '教师'),
      page('/base/admin/hr/tutor', '导师'),
      page('/base/admin/hr/mentor', '辅导员'),
      page('/base/admin/hr/code-index', '人事代码'),
    ],
  },
  {
    path: '/std',
    titleKey: 'nav.std',
    links: [
      page('/base/admin/std/squad', '班级'),
      page('/base/admin/std/grade', '年级'),
      page('/base/admin/std/graduate-season', '毕业季'),
      page('/base/admin/std/code-index', '学籍代码'),
    ],
  },
  {
    path: '/resource',
    titleKey: 'nav.resource',
    links: [
      page('/base/admin/resource/building', '建筑物'),
      page('/base/admin/resource/classroom', '教室'),
      page('/base/admin/resource/laboratory', '实验室'),
      page('/base/admin/resource/device', '设备'),
      page('/base/admin/resource/code-index', '资源代码'),
    ],
  },
  {
    path: '/system',
    titleKey: 'nav.system',
    links: [
      page('/base/admin/project', '项目'),
      page('/base/admin/user', '用户'),
      page('/base/admin/user-group', '用户组'),
      page('/base/admin/person/code-index', '人员代码'),
    ],
  },
]

export function findModule(path: string): AdminModule | undefined {
  return adminModules.find((item) => item.path === path)
}
