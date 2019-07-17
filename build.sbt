lazy val root = (project in file("."))
    .settings(
        scalaVersion := "2.12.8",
        scalacOptions := Seq(
          "-feature",
          "-deprecation",
          "-explaintypes",
          "-unchecked",
          "-Xfuture",
          "-encoding", "UTF-8",
          "-language:higherKinds",
          "-language:existentials",
          "-Ypartial-unification",
          "-Xlint:-infer-any,_",
          "-Ywarn-value-discard",
          "-Ywarn-numeric-widen",
          "-Ywarn-extra-implicit",
          "-Ywarn-unused:_",
          "-Ywarn-inaccessible",
          "-Ywarn-nullary-override",
          "-Ywarn-nullary-unit",
          "-opt:l:inline"
        ),
        libraryDependencies ++= Seq(
            "co.fs2"               %% "fs2-core"               % "1.0.5",

            "org.http4s"           %% "http4s-dsl"             % "0.20.3",
            "org.http4s"           %% "http4s-blaze-server"    % "0.20.3",

            "org.typelevel"        %% "cats-effect"            % "1.3.1",

            "dev.zio"              %% "zio"                    % "1.0.0-RC10-1",
            "dev.zio"              %% "zio-interop-cats"       % "1.3.1.0-RC3"
        )
)

