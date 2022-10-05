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
 - `org.macroing.geo4j.common` provides the Common API.
 - `org.macroing.geo4j.quaternion` provides the Quaternion API.
 - `org.macroing.geo4j.matrix` provides the Matrix API.
 - `org.macroing.geo4j.mc` provides the Morton Code API.
 - `org.macroing.geo4j.onb` provides the Orthonormal Basis API.
 - `org.macroing.geo4j.ray` provides the Ray API.
 - `org.macroing.geo4j.shape` provides the Shape API.
 - `org.macroing.geo4j.shape.circle` provides the Shape Circle API.
 - `org.macroing.geo4j.shape.ls` provides the Shape Line Segment API.
 - `org.macroing.geo4j.shape.polygon` provides the Shape Polygon API.
 - `org.macroing.geo4j.shape.rectangle` provides the Shape Rectangle API.
 - `org.macroing.geo4j.shape.triangle` provides the Shape Triangle API.

Examples
--------
Coming soon...

Documentation
-------------
The documentation for this library can be found in the Javadocs that are generated when building it.

Library
-------
The following table describes the different APIs and their current status in the library.

| Name                   | Javadoc | Unit Test | Package                            |
| ---------------------- | ------- | --------- | ---------------------------------- |
| Geo4J                  | N/A     |  92.4%    |                                    |
| Common API             | N/A     |  90.3%    | org.macroing.geo4j.common          |
| Matrix API             | 100.0%  | 100.0%    | org.macroing.geo4j.matrix          |
| Morton Code API        | 100.0%  |   0.0%    | org.macroing.geo4j.mc              |
| Orthonormal Basis API  | 100.0%  | 100.0%    | org.macroing.geo4j.onb             |
| Quaternion API         | 100.0%  |  69.5%    | org.macroing.geo4j.quaternion      |
| Ray API                | 100.0%  | 100.0%    | org.macroing.geo4j.ray             |
| Shape API              | 100.0%  | 100.0%    | org.macroing.geo4j.shape           |
| Shape Circle API       | 100.0%  | 100.0%    | org.macroing.geo4j.shape.circle    |
| Shape Line Segment API | 100.0%  | 100.0%    | org.macroing.geo4j.shape.ls        |
| Shape Polygon API      | 100.0%  | 100.0%    | org.macroing.geo4j.shape.polygon   |
| Shape Rectangle API    | 100.0%  | 100.0%    | org.macroing.geo4j.shape.rectangle |
| Shape Triangle API     | 100.0%  | 100.0%    | org.macroing.geo4j.shape.triangle  |

Dependencies
------------
 - [Java 8 - 17](http://www.java.com)
 - [Macroing / Java](https://github.com/macroing/Java)

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.