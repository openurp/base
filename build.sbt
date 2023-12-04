import org.openurp.parent.Dependencies.*
import org.openurp.parent.Settings.*

ThisBuild / organization := "org.openurp.base"
ThisBuild / version := "0.4.14"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/openurp/base"),
    "scm:git@github.com:openurp/base.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "OpenURP Base Webapp"
ThisBuild / homepage := Some(url("http://openurp.github.io/base/index.html"))

val apiVer = "0.34.5"
val starterVer = "0.3.17"
val openurp_base_api = "org.openurp.base" % "openurp-base-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer
val openurp_stater_ws = "org.openurp.starter" % "openurp-starter-ws" % starterVer

val ojdbc11 = "com.oracle.database.jdbc" % "ojdbc11" % "23.2.0.0"
val orai18n = "com.oracle.database.nls" % "orai18n" % "23.2.0.0"

lazy val root = (project in file("."))
  .settings()
  .aggregate(tag, static, admin, info, ws, webapp)

lazy val tag = (project in file("tag"))
  .settings(
    name := "openurp-base-tag",
    common,
    libraryDependencies ++= Seq(openurp_base_api, beangle_webmvc_support, beangle_data_orm, beangle_ems_app)
  )

lazy val static = (project in file("static"))
  .settings(
    name := "openurp-base-static",
    common
  )

lazy val admin = (project in file("admin"))
  .settings(
    name := "openurp-base-admin",
    common,
    libraryDependencies ++= Seq(openurp_stater_web)
  ).dependsOn(tag)

lazy val info = (project in file("info"))
  .settings(
    name := "openurp-base-info",
    common,
    libraryDependencies ++= Seq(openurp_stater_web)
  ).dependsOn(tag)

lazy val ws = (project in file("ws"))
  .enablePlugins(WarPlugin, UndertowPlugin)
  .settings(
    name := "openurp-base-ws",
    common,
    libraryDependencies ++= Seq(openurp_base_api, openurp_stater_ws)
  )

lazy val webapp = (project in file("webapp"))
  .enablePlugins(WarPlugin, UndertowPlugin, TomcatPlugin)
  .settings(
    name := "openurp-base-webapp",
    common
  ).dependsOn(admin, info)

publish / skip := true
