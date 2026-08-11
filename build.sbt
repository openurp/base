import org.openurp.parent.Dependencies.*
import org.openurp.parent.Settings.*

organization := "org.openurp.base"
version := "0.4.90-SNAPSHOT"

scmInfo := Some(
  ScmInfo(uri("https://github.com/openurp/base"), "scm:git@github.com:openurp/base.git")
)

developers := List(
  Developer(
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = uri("http://github.com/duantihua")
  )
)

description := "OpenURP Base Webapp"
homepage := Some(uri("http://openurp.github.io/base/index.html"))

val apiVer = "1.4.13"
val openurp_base_api = "org.openurp.base" % "openurp-base-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % apiVer

lazy val root = (project in file("."))
  .settings(common,
    publish / skip := true
  )
  .aggregate(tag, static, ws, webapp)

lazy val tag = (project in file("tag"))
  .settings(
    name := "openurp-base-tag",
    common,
    libraryDependencies ++= Seq(openurp_base_api, beangle_bui_bootstrap, beangle_data_model, beangle_ems_app)
  )

lazy val static = (project in file("static"))
  .settings(
    name := "openurp-base-static",
    common
  )

lazy val ws = (project in file("ws"))
  .enablePlugins(WarPlugin, UndertowPlugin)
  .settings(
    name := "openurp-base-ws",
    common,
    libraryDependencies ++= Seq(openurp_base_api, openurp_stater_web)
  )

lazy val webapp = (project in file("webapp"))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "openurp-base-webapp",
    common,
    libraryDependencies ++= Seq(openurp_stater_web, beangle_bui_bootstrap, beangle_ems_app)
  ).dependsOn(tag)


