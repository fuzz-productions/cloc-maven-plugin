# CLOC Maven Plugin

This maven plugin is responsible to print out metrics referring counts blank lines, comment lines, and physical lines of source code in many programming languages.

Using [AlDanial/cloc](https://github.com/AlDanial/cloc) project as base.

## Getting Started

Please download and build the source to be able to use it

### Prerequisites

What things you need to install the software and how to install them

```
pear must be in $PATH
```

### Installing

#### Simple usage:

```$xslt
mvn com.vvezani:cloc-maven-plugin:0.1.0-SNAPSHOT:analyze
```

#### Plugin configuration

To use the plugin integrated in your build - execute the `analyze` goal as follows:

```
<plugin>
  <groupId>com.vvezani</groupId>
  <artifactId>cloc-maven-plugin</artifactId>
  <version>0.1.0-SNAPSHOT</version>
  <executions>
    <execution>
      <!-- whatever phase you want -->
      <phase>compile</phase>
      <goals>
        <goal>analyze</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Result:

```$xslt
[INFO] --- cloc-maven-plugin:0.1.0-SNAPSHOT:analyze (default) @ springboot-redis-cache ---
      38 text files.
      37 unique files.                              
      14 files ignored.

github.com/AlDanial/cloc v 1.80  T=0.18 s (136.2 files/s, 86820.7 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
XML                             10              0              0           1735
Java                             9             75              4            255
Bourne Shell                     1             31             60            195
DOS Batch                        1             32              0            129
Maven                            1              6              0             68
Markdown                         1             22              0             34
YAML                             1              1              0              9
-------------------------------------------------------------------------------
SUM:                            25           1002           1355          13578
-------------------------------------------------------------------------------

```

## Parameters

You can use parameters to further customize the plugin execution
```
<configuration>
  <excludeDirs>
    <!-- this param is a list --> 
    <param1>src/main/test</param1>
  </excludeDirs>
</configuration>
```

## Running the tests

```
mvn clean test
```

## Built With

* [Java](https://www.java.com/en) - The use programming language
* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/vitorvezani/cloc-maven-plugin/tags). 

## Authors

* **Vitor Vezani** - *Initial work* - [vvezani](https://github.com/vitorvezani/cloc-maven-plugin)

See also the list of [contributors](https://github.com/vitorvezani/cloc-maven-plugin/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Next features

* Parameters support
* Windows support
* Tests