import org.openurp.parent.Dependencies.*
import org.openurp.parent.Settings.*

ThisBuild / organization := "org.openurp.base"
ThisBuild / version := "0.4.6-SNAPSHOT"

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

val apiVer = "0.34.1-SNAPSHOT"
val starterVer = "0.3.8-SNAPSHOT"
val openurp_base_api = "org.openurp.base" % "openurp-base-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer
val openurp_stater_ws = "org.openurp.starter" % "openurp-starter-ws" % starterVer
val beangle_ems_app = "org.beangle.ems" % "beangle-ems-app_3" % "4.6.23-SNAPSHOT"
val beangle_security_core = "org.beangle.security" % "beangle-security-core_3" % "4.3.10-SNAPSHOT"
val beangle_webmvc_core = "org.beangle.webmvc" % "beangle-webmvc-core_3" % "0.9.8-SNAPSHOT"
val beangle_webmvc_view = "org.beangle.webmvc" % "beangle-webmvc-view_3" % "0.9.8-SNAPSHOT"

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
    libraryDependencies ++= Seq(beangle_ems_app,beangle_security_core,beangle_webmvc_core,beangle_webmvc_view),
    common
  ).dependsOn(admin, info)

publish / skip := true
