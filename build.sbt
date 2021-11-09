import org.beangle.tools.sbt.Sas
import org.openurp.parent.Settings._
import org.openurp.parent.Dependencies._

ThisBuild / organization := "org.openurp.base"
ThisBuild / version := "0.1.23"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/openurp/base"),
    "scm:git@github.com:openurp/base.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "chaostone",
    name  = "Tihua Duan",
    email = "duantihua@gmail.com",
    url   = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "OpenURP Starter"
ThisBuild / homepage := Some(url("http://openurp.github.io/base/index.html"))

val apiVer = "0.23.4"
val starterVer = "0.0.14"
val openurp_base_api = "org.openurp.base" % "openurp-base-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer
val openurp_stater_ws = "org.openurp.starter" % "openurp-starter-ws" % starterVer

lazy val root = (project in file("."))
  .settings()
  .aggregate(tag,static,admin,ws,webapp)

lazy val tag = (project in file("tag"))
  .settings(
    name := "openurp-base-tag",
    common,
    libraryDependencies ++= Seq(openurp_base_api,beangle_webmvc_support,beangle_data_hibernate,beangle_ems_app)
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
  .enablePlugins(WarPlugin)
  .settings(
    name := "openurp-base-ws",
    common,
    libraryDependencies ++= Seq(openurp_base_api,openurp_stater_ws,Sas.Tomcat % "test")
  )

lazy val webapp = (project in file("webapp"))
  .enablePlugins(WarPlugin)
  .settings(
    name := "openurp-base-webapp",
    common,
    libraryDependencies ++= Seq(Sas.Tomcat % "test")
  ).dependsOn(admin)

publish / skip := true
