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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.geo4j.vector.Vector3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.Randoms;

/**
 * A {@code Point2D} represents a point with two {@code double}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Point2D {
	/**
	 * The X-component of this {@code Point2D} instance.
	 */
	public final double x;
	
	/**
	 * The Y-component of this {@code Point2D} instance.
	 */
	public final double y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Point2D} instance at {@code 0.0D} and {@code 0.0D}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Point2D(0.0D, 0.0D);
	 * }
	 * </pre>
	 */
	public Point2D() {
		this(0.0D, 0.0D);
	}
	
	/**
	 * Constructs a new {@code Point2D} instance at {@code x} and {@code y}.
	 * 
	 * @param x the X-component of this {@code Point2D} instance
	 * @param y the Y-component of this {@code Point2D} instance
	 */
	public Point2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Point2D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Point2D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Point2D(%s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y));
	}
	
	/**
	 * Compares {@code object} to this {@code Point2D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Point2D} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} instance to compare to this {@code Point2D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Point2D} and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point2D)) {
			return false;
		} else {
			return equals(Point2D.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code p} to this {@code Point2D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code p} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param p the {@code Point2D} instance to compare to this {@code Point2D} instance for equality
	 * @return {@code true} if, and only if, {@code p} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Point2D p) {
		if(p == this) {
			return true;
		} else if(p == null) {
			return false;
		} else if(!Doubles.equals(this.x, p.x)) {
			return false;
		} else if(!Doubles.equals(this.y, p.y)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, both {@code x} and {@code y} are equal to {@code 0.0D}, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, both {@code x} and {@code y} are equal to {@code 0.0D}, {@code false} otherwise
	 */
	public boolean isZero() {
		return Doubles.isZero(this.x) && Doubles.isZero(this.y); 
	}
	
	/**
	 * Returns a hash code for this {@code Point2D} instance.
	 * 
	 * @return a hash code for this {@code Point2D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Point2D project(final Point3D a, final Point3D b, final Vector3D u, final Vector3D v) {
		final Vector3D directionAB = Vector3D.direction(a, b);
		
		final double x = Vector3D.dotProduct(directionAB, u);
		final double y = Vector3D.dotProduct(directionAB, v);
		
		return new Point2D(x, y);
	}
	
//	TODO: Add Javadocs!
	public static Point2D rotateCounterclockwise(final Point2D p, final double angle, final boolean isRadians) {
		final double angleRadians = isRadians ? angle : Doubles.toRadians(angle);
		final double angleRadiansCos = Doubles.cos(angleRadians);
		final double angleRadiansSin = Doubles.sin(angleRadians);
		
		final double x = p.x * angleRadiansCos - p.y * angleRadiansSin;
		final double y = p.y * angleRadiansCos + p.x * angleRadiansSin;
		
		/*
		 * To rotate around a different point than origin (0.0, 0.0):
		 * 
		 * x = (p.x - c.x) * angleRadiansCos - (p.y - c.y) * angleRadiansSin + c.x;
		 * y = (p.y - c.y) * angleRadiansCos + (p.x - c.x) * angleRadiansSin + c.y;
		 * 
		 * To rotate clockwise:
		 * 
		 * x = p.x * angleRadiansCos + p.y * angleRadiansSin;
		 * y = p.y * angleRadiansCos - p.x * angleRadiansSin;
		 */
		
		return new Point2D(x, y);
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution.
	 * <p>
	 * Returns a {@code Point2D} instance with the sampled point.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2D.sampleDiskUniformDistribution(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point2D} instance with the sampled point
	 */
//	TODO: Add unit tests!
	public static Point2D sampleDiskUniformDistribution() {
		return sampleDiskUniformDistribution(sampleRandom());
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution.
	 * <p>
	 * Returns a {@code Point2D} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point2D} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Point2D sampleDiskUniformDistribution(final Point2D p) {
		final double r = Doubles.sqrt(p.x);
		final double theta = Doubles.PI_MULTIPLIED_BY_2 * p.y;
		
		return new Point2D(r * Doubles.cos(theta), r * Doubles.sin(theta));
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution using concentric mapping.
	 * <p>
	 * Returns a {@code Point2D} instance with the sampled point.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2D.sampleDiskUniformDistributionByConcentricMapping(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point2D} instance with the sampled point
	 */
	public static Point2D sampleDiskUniformDistributionByConcentricMapping() {
		return sampleDiskUniformDistributionByConcentricMapping(sampleRandom());
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution using concentric mapping.
	 * <p>
	 * Returns a {@code Point2D} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2D.sampleDiskUniformDistributionByConcentricMapping(p, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param p a {@code Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point2D} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2D sampleDiskUniformDistributionByConcentricMapping(final Point2D p) {
		return sampleDiskUniformDistributionByConcentricMapping(p, 1.0D);
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution using concentric mapping.
	 * <p>
	 * Returns a {@code Point2D} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2D} instance with components in the interval [0.0, 1.0]
	 * @param radius the radius of the disk
	 * @return a {@code Point2D} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2D sampleDiskUniformDistributionByConcentricMapping(final Point2D p, final double radius) {
		if(p.isZero()) {
			return p;
		}
		
		final double a = p.x * 2.0D - 1.0D;
		final double b = p.y * 2.0D - 1.0D;
		
		if(a * a > b * b) {
			final double phi = Doubles.PI / 4.0D * (b / a);
			final double r = radius * a;
			
			return new Point2D(r * Doubles.cos(phi), r * Doubles.sin(phi));
		}
		
		final double phi = Doubles.PI / 2.0D - Doubles.PI / 4.0D * (a / b);
		final double r = radius * b;
		
		return new Point2D(r * Doubles.cos(phi), r * Doubles.sin(phi));
	}
	
	/**
	 * Samples a {@code Point2D} instance using the exact inverse CDF of a tent filter.
	 * <p>
	 * Returns a {@code Point2D} instance with the result.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2D.sampleExactInverseTentFilter(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point2D} instance with the result
	 */
	public static Point2D sampleExactInverseTentFilter() {
		return sampleExactInverseTentFilter(sampleRandom());
	}
	
	/**
	 * Samples a {@code Point2D} instance using the exact inverse CDF of a tent filter.
	 * <p>
	 * Returns a {@code Point2D} instance with the result.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The X-component of the returned {@code Point2D} instance will be in the interval [-1.0, 1.0] if, and only if, {@code p.x} is in the interval [0.0, 1.0]. Otherwise it will be NaN (Not a Number).
	 * <p>
	 * The Y-component of the returned {@code Point2D} instance will be in the interval [-1.0, 1.0] if, and only if, {@code p.y} is in the interval [0.0, 1.0]. Otherwise it will be NaN (Not a Number).
	 * 
	 * @param p a {@code Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point2D} instance with the result
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2D sampleExactInverseTentFilter(final Point2D p) {
		final double a = p.x * 2.0D;
		final double b = p.y * 2.0D;
		
		final double x = a < 1.0D ? Doubles.sqrt(a) - 1.0D : 1.0D - Doubles.sqrt(2.0D - a);
		final double y = b < 1.0D ? Doubles.sqrt(b) - 1.0D : 1.0D - Doubles.sqrt(2.0D - b);
		
		return new Point2D(x, y);
	}
	
	/**
	 * Samples a {@code Point2D} instance using a PRNG.
	 * <p>
	 * Returns a {@code Point2D} instance with the result.
	 * <p>
	 * The X-component of the returned {@code Point2D} instance will be in the interval [0.0, 1.0).
	 * <p>
	 * The Y-component of the returned {@code Point2D} instance will be in the interval [0.0, 1.0).
	 * 
	 * @return a {@code Point2D} instance with the result
	 */
	public static Point2D sampleRandom() {
		return new Point2D(Randoms.nextDouble(), Randoms.nextDouble());
	}
}