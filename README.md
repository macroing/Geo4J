Geo4J
=====
Geo4J is a geometry library for Java.

This project will supersede the old [Math4J](https://github.com/macroing/Math4J) project and the Geometry APIs in the [Dayflower](https://github.com/macroing/Dayflower) project.

Getting Started
---------------
To clone this repository and build the project using Apache Ant, you can type the following in Git Bash.

```bash
git clone https://github.com/macroing/Geo4J.git
cd Geo4J
ant
```

Supported Features
------------------
 - `org.macroing.geo4j` provides the Geometry API.

Examples
--------
Coming soon...

Documentation
-------------
The documentation for this library can be found in the Javadocs that are generated when building it.

Library
-------
The following table describes the different APIs and their current status in the library.

| Name                  | Javadoc | Unit Test | Package                       |
| --------------------- | ------- | --------- | ----------------------------- |
| Geo4J                 | N/A     |  85.8%    |                               |
| Matrix API            | 100.0%  |  94.8%    | org.macroing.geo4j.matrix     |
| Orthonormal Basis API | N/A     |  61.5%    | org.macroing.geo4j.onb        |
| Point API             | N/A     |  92.3%    | org.macroing.geo4j.point      |
| Quaternion API        | N/A     |  76.6%    | org.macroing.geo4j.quaternion |
| Ray API               | N/A     | 100.0%    | org.macroing.geo4j.ray        |
| Vector API            | N/A     |  77.5%    | org.macroing.geo4j.vector     |

Dependencies
------------
 - [Java 8](http://www.java.com)
 - [Macroing / Java](https://github.com/macroing/Java)

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.