Geo4J
=====
Geo4J is a geometry library for Java. It is suitable for 2D- and 3D-graphics.

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
 - `org.macroing.geo4j.bv` provides the Bounding Volume API.
 - `org.macroing.geo4j.bv.aabb` provides the Bounding Volume Axis Aligned Bounding Box API.
 - `org.macroing.geo4j.bv.bs` provides the Bounding Volume Bounding Sphere API.
 - `org.macroing.geo4j.bv.ibv` provides the Bounding Volume Infinite Bounding Volume API.
 - `org.macroing.geo4j.bv.reader` provides the Bounding Volume Reader API.
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
 - `org.macroing.geo4j.shape.reader` provides the Shape Reader API.
 - `org.macroing.geo4j.shape.rectangle` provides the Shape Rectangle API.
 - `org.macroing.geo4j.shape.triangle` provides the Shape Triangle API.

Documentation
-------------
The documentation for this library can be found in the Javadocs that are generated when building it using Apache Ant.

Library
-------
The following table describes the different APIs and their current status in the library.

| Name                                          | Javadoc | Unit Test | Package                            |
| --------------------------------------------- | ------- | --------- | ---------------------------------- |
| Geo4J                                         | 100.0%  |  90.5%    | org.macroing.geo4j                 |
| Bounding Volume API                           | 100.0%  | 100.0%    | org.macroing.geo4j.bv              |
| Bounding Volume Axis Aligned Bounding Box API | 100.0%  | 100.0%    | org.macroing.geo4j.bv.aabb         |
| Bounding Volume Bounding Sphere API           | 100.0%  | 100.0%    | org.macroing.geo4j.bv.bs           |
| Bounding Volume Infinite Bounding Volume API  | 100.0%  | 100.0%    | org.macroing.geo4j.bv.ibv          |
| Bounding Volume Reader API                    | 100.0%  |   0.0%    | org.macroing.geo4j.bv.reader       |
| Common API                                    | 100.0%  |  98.0%    | org.macroing.geo4j.common          |
| Matrix API                                    | 100.0%  | 100.0%    | org.macroing.geo4j.matrix          |
| Morton Code API                               | 100.0%  |   0.0%    | org.macroing.geo4j.mc              |
| Orthonormal Basis API                         | 100.0%  | 100.0%    | org.macroing.geo4j.onb             |
| Quaternion API                                | 100.0%  |  69.5%    | org.macroing.geo4j.quaternion      |
| Ray API                                       | 100.0%  | 100.0%    | org.macroing.geo4j.ray             |
| Shape API                                     | 100.0%  |  29.6%    | org.macroing.geo4j.shape           |
| Shape Circle API                              | 100.0%  | 100.0%    | org.macroing.geo4j.shape.circle    |
| Shape Line Segment API                        | 100.0%  |  97.6%    | org.macroing.geo4j.shape.ls        |
| Shape Polygon API                             | 100.0%  | 100.0%    | org.macroing.geo4j.shape.polygon   |
| Shape Reader API                              | 100.0%  |   0.0%    | org.macroing.geo4j.shape.reader    |
| Shape Rectangle API                           | 100.0%  | 100.0%    | org.macroing.geo4j.shape.rectangle |
| Shape Triangle API                            | 100.0%  | 100.0%    | org.macroing.geo4j.shape.triangle  |

Dependencies
------------
 - [Java 8 - 17](http://www.java.com)
 - [Macroing / Java](https://github.com/macroing/Java)

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.