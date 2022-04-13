import org.openurp.parent.Dependencies._
import org.openurp.parent.Settings._

ThisBuild / organization := "org.openurp.base"
ThisBuild / version := "0.1.26-SNAPSHOT"

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

ThisBuild / description := "OpenURP Starter"
ThisBuild / homepage := Some(url("http://openurp.github.io/base/index.html"))

val apiVer = "0.24.1"
val starterVer = "0.0.16"
val openurp_base_api = "org.openurp.base" % "openurp-base-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer
val openurp_stater_ws = "org.openurp.starter" % "openurp-starter-ws" % starterVer

lazy val root = (project in file("."))
  .settings()
  .aggregate(tag, static, admin, ws, webapp)

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

lazy val ws = (project in file("ws"))
  .enablePlugins(WarPlugin, UndertowPlugin)
  .settings(
    name := "openurp-base-ws",
    common,
    libraryDependencies ++= Seq(openurp_base_api, openurp_stater_ws)
  )

lazy val webapp = (project in file("webapp"))
  .enablePlugins(WarPlugin, UndertowPlugin)
  .settings(
    name := "openurp-base-webapp",
    common
  ).dependsOn(admin)

publish / skip := true
