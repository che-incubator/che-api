## Eclipse Che® API for Kotlin-based projects

It's a utility library that helps working with a DevWorkspace.
It's created mainly for using by [Eclipse Che®'s IDEA editor](https://github.com/che-incubator/jetbrains-editor-images/blob/2b20c1664ae3769aecade08106789f81672e640c/che-plugin/build.gradle.kts#L30).

### Release

Make sure your `settings.xml` contains:
```xml
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username><!-- sonatype username --></username>
      <password><!-- sonatype password --></password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>ossrh</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <gpg.executable>gpg2</gpg.executable>
        <gpg.passphrase><!-- passprhase --></gpg.passphrase>
      </properties>
    </profile>
  </profiles>
</settings>
```

Do a release:
```
mvn release:clean release:prepare -DdryRun=true
mvn release:prepare
mvn release:perform
```

### Trademark

"Che" is a trademark of the Eclipse Foundation.
