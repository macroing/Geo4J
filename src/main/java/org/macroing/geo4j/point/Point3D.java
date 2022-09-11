/**
 * Copyright 2022 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.geo4j.
 * 
 * org.macroing.geo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.geo4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.geo4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.geo4j.point;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.geo4j.vector.Vector3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Ints;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.Arrays;

/**
 * A {@code Point3D} represents a point with three {@code double}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Point3D {
//	TODO: Add Javadocs!
	public static final Point3D MAX = max();
	
//	TODO: Add Javadocs!
	public static final Point3D MIN = min();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The X-component of this {@code Point3D} instance.
	 */
	public final double x;
	
	/**
	 * The Y-component of this {@code Point3D} instance.
	 */
	public final double y;
	
	/**
	 * The Z-component of this {@code Point3D} instance.
	 */
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Point3D} instance at {@code 0.0D}, {@code 0.0D} and {@code 0.0D}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Point3D(0.0D, 0.0D, 0.0D);
	 * }
	 * </pre>
	 */
	public Point3D() {
		this(0.0D, 0.0D, 0.0D);
	}
	
	/**
	 * Constructs a new {@code Point3D} instance at {@code x}, {@code y} and {@code z}.
	 * 
	 * @param x the X-component of this {@code Point3D} instance
	 * @param y the Y-component of this {@code Point3D} instance
	 * @param z the Z-component of this {@code Point3D} instance
	 */
	public Point3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Point3D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Point3D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Point3D(%s, %s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y), Strings.toNonScientificNotationJava(this.z));
	}
	
	/**
	 * Compares {@code object} to this {@code Point3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Point3D} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} instance to compare to this {@code Point3D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Point3D} and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point3D)) {
			return false;
		} else {
			return equals(Point3D.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code p} to this {@code Point3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code p} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param p the {@code Point3D} instance to compare to this {@code Point3D} instance for equality
	 * @return {@code true} if, and only if, {@code p} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Point3D p) {
		if(p == this) {
			return true;
		} else if(p == null) {
			return false;
		} else if(!Doubles.equals(this.x, p.x)) {
			return false;
		} else if(!Doubles.equals(this.y, p.y)) {
			return false;
		} else if(!Doubles.equals(this.z, p.z)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public double sphericalPhi() {
		return Doubles.addLessThan(Doubles.atan2(this.y, this.x), 0.0D, Doubles.PI * 2.0D);
	}
	
	/**
	 * Returns a hash code for this {@code Point3D} instance.
	 * 
	 * @return a hash code for this {@code Point3D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	/**
	 * Writes this {@code Point3D} instance to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} instance to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
//	TODO: Add unit tests!
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeDouble(this.x);
			dataOutput.writeDouble(this.y);
			dataOutput.writeDouble(this.z);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Point3D add(final Point3D p, final Vector3D v) {
		return new Point3D(p.x + v.x, p.y + v.y, p.z + v.z);
	}
	
//	TODO: Add Javadocs!
	public static Point3D add(final Point3D p, final Vector3D v, final double s) {
		return new Point3D(p.x + v.x * s, p.y + v.y * s, p.z + v.z * s);
	}
	
//	TODO: Add Javadocs!
	public static Point3D lerp(final Point3D a, final Point3D b, final double t) {
		return new Point3D(Doubles.lerp(a.x, b.x, t), Doubles.lerp(a.y, b.y, t), Doubles.lerp(a.z, b.z, t));
	}
	
//	TODO: Add Javadocs!
	public static Point3D max() {
		return new Point3D(Doubles.MAX_VALUE, Doubles.MAX_VALUE, Doubles.MAX_VALUE);
	}
	
//	TODO: Add Javadocs!
	public static Point3D max(final Point3D a, final Point3D b) {
		return new Point3D(Doubles.max(a.x, b.x), Doubles.max(a.y, b.y), Doubles.max(a.z, b.z));
	}
	
//	TODO: Add Javadocs!
	public static Point3D midpoint(final Point3D a, final Point3D b) {
		return new Point3D((a.x + b.x) / 2.0D, (a.y + b.y) / 2.0D, (a.z + b.z) / 2.0D);
	}
	
//	TODO: Add Javadocs!
	public static Point3D min() {
		return new Point3D(Doubles.MIN_VALUE, Doubles.MIN_VALUE, Doubles.MIN_VALUE);
	}
	
//	TODO: Add Javadocs!
	public static Point3D min(final Point3D a, final Point3D b) {
		return new Point3D(Doubles.min(a.x, b.x), Doubles.min(a.y, b.y), Doubles.min(a.z, b.z));
	}
	
	/**
	 * Returns a new {@code Point3D} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a new {@code Point3D} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
//	TODO: Add unit tests!
	public static Point3D read(final DataInput dataInput) {
		try {
			return new Point3D(dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Samples a point on a triangle with a uniform distribution.
	 * <p>
	 * Returns a {@link Point3D} instance with the sampled point.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point3D.sampleTriangleUniformDistribution(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point3D} instance with the sampled point
	 */
//	TODO: Add unit tests!
	public static Point3D sampleTriangleUniformDistribution() {
		return sampleTriangleUniformDistribution(Point2D.sampleRandom());
	}
	
	/**
	 * Samples a point on a triangle with a uniform distribution.
	 * <p>
	 * Returns a {@link Point3D} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point3D} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Point3D sampleTriangleUniformDistribution(final Point2D p) {
		final double a = Doubles.sqrt(p.x);
		
		final double x = 1.0D - a;
		final double y = p.y * a;
		final double z = 1.0D - x - y;
		
		return new Point3D(x, y, z);
	}
	
//	TODO: Add Javadocs!
	public static boolean coplanar(final Point3D... points) {
		Arrays.requireNonNull(points, "points");
		
		Ints.requireRange(points.length, 3, Integer.MAX_VALUE, "points.length");
		
		final Point3D p0 = points[0];
		final Point3D p1 = points[1];
		final Point3D p2 = points[2];
		
		final Vector3D v0 = Vector3D.directionNormalized(p0, p1);
		final Vector3D v1 = Vector3D.directionNormalized(p0, p2);
		
		for(int i = 3; i < points.length; i++) {
			final Point3D pI = points[i];
			
			final Vector3D v2 = Vector3D.directionNormalized(p0, pI);
			
			if(!Doubles.isZero(Vector3D.tripleProduct(v0, v2, v1))) {
				return false;
			}
		}
		
		return true;
	}
	
//	TODO: Add Javadocs!
	public static double distance(final Point3D eye, final Point3D lookAt) {
		return Vector3D.direction(eye, lookAt).length();
	}
	
//	TODO: Add Javadocs!
	public static double distanceSquared(final Point3D eye, final Point3D lookAt) {
		return Vector3D.direction(eye, lookAt).lengthSquared();
	}
}