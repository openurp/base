Compile / packageBin / mappings := {
  val converter = fileConverter.value
  val prefix = "META-INF/resources/openurp-base/" + version.value
  (Compile / products).value
    .flatMap(Path.allSubpaths)
    .filter(_._1.isFile)
    .map { (file, path) =>
      converter.toVirtualFile(file.toPath) -> s"$prefix/$path"
    }
}
