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
package org.macroing.geo4j.bv;

import java.lang.reflect.Field;//TODO: Add unit tests!

import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.matrix.Matrix44D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.java.lang.Doubles;

/**
 * A {@code BoundingVolume3D} represents a 3-dimensional bounding volume that operates on {@code double}-based data types.
 * <p>
 * All official implementations of this interface are immutable and therefore thread-safe. But this cannot be guaranteed for all implementations.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface BoundingVolume3D extends BoundingVolume {
	/**
	 * Performs a transformation.
	 * <p>
	 * Returns a {@code BoundingVolume3D} instance with the result of the transformation.
	 * <p>
	 * If {@code matrix} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param matrix the {@link Matrix44D} instance to perform the transformation with
	 * @return a {@code BoundingVolume3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code matrix} is {@code null}
	 */
	BoundingVolume3D transform(final Matrix44D matrix);
	
	/**
	 * Returns a {@link Point3D} instance that represents the closest point to {@code point} and is contained in this {@code BoundingVolume3D} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@code Point3D} instance
	 * @return a {@code Point3D} instance that represents the closest point to {@code point} and is contained in this {@code BoundingVolume3D} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	Point3D getClosestPointTo(final Point3D point);
	
	/**
	 * Returns a {@link Point3D} with the largest component values needed to contain this {@code BoundingVolume3D} instance.
	 * 
	 * @return a {@code Point3D} with the largest component values needed to contain this {@code BoundingVolume3D} instance
	 */
	Point3D max();
	
	/**
	 * Returns a {@link Point3D} with the smallest component values needed to contain this {@code BoundingVolume3D} instance.
	 * 
	 * @return a {@code Point3D} with the smallest component values needed to contain this {@code BoundingVolume3D} instance
	 */
	Point3D min();
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code BoundingVolume3D} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point3D} instance
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code BoundingVolume3D} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	boolean contains(final Point3D point);
	
	/**
	 * Returns the surface area of this {@code BoundingVolume3D} instance.
	 * 
	 * @return the surface area of this {@code BoundingVolume3D} instance
	 */
	double getSurfaceArea();
	
	/**
	 * Returns the volume of this {@code BoundingVolume3D} instance.
	 * 
	 * @return the volume of this {@code BoundingVolume3D} instance
	 */
	double getVolume();
	
	/**
	 * Performs an intersection test between {@code ray} and this {@code BoundingVolume3D} instance.
	 * <p>
	 * Returns {@code t}, the parametric distance from {@code ray} to this {@code BoundingVolume3D} instance, or {@code Double.NaN} if no intersection exists.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@link Ray3D} to perform an intersection test against this {@code BoundingVolume3D} instance
	 * @param tMinimum the minimum parametric distance
	 * @param tMaximum the maximum parametric distance
	 * @return {@code t}, the parametric distance from {@code ray} to this {@code BoundingVolume3D} instance, or {@code Double.NaN} if no intersection exists
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
	double intersection(final Ray3D ray, final double tMinimum, final double tMaximum);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Point3D} with the component values in the middle of this {@code BoundingVolume3D} instance.
	 * 
	 * @return a {@code Point3D} with the component values in the middle of this {@code BoundingVolume3D} instance
	 */
//	TODO: Add Unit Tests!
	default Point3D midpoint() {
		return Point3D.midpoint(max(), min());
	}
	
	/**
	 * Performs an intersection test between {@code boundingVolume} and this {@code BoundingVolume3D} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code boundingVolume} intersects this {@code BoundingVolume3D} instance, {@code false} otherwise.
	 * <p>
	 * If {@code boundingVolume} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param boundingVolume the {@code BoundingVolume3D} to perform an intersection test against this {@code BoundingVolume3D} instance
	 * @return {@code true} if, and only if, {@code boundingVolume} intersects this {@code BoundingVolume3D} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code boundingVolume} is {@code null}
	 */
//	TODO: Add Unit Tests!
	default boolean intersects(final BoundingVolume3D boundingVolume) {
		return contains(boundingVolume.getClosestPointTo(midpoint()));
	}
	
	/**
	 * Performs an intersection test between {@code ray} and this {@code BoundingVolume3D} instance.
	 * <p>
	 * Returns {@code true} if, and only if, {@code ray} intersects this {@code BoundingVolume3D} instance, {@code false} otherwise.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@link Ray3D} to perform an intersection test against this {@code BoundingVolume3D} instance
	 * @param tMinimum the minimum parametric distance
	 * @param tMaximum the maximum parametric distance
	 * @return {@code true} if, and only if, {@code ray} intersects this {@code BoundingVolume3D} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
//	TODO: Add Unit Tests!
	default boolean intersects(final Ray3D ray, final double tMinimum, final double tMaximum) {
		return !Doubles.isNaN(intersection(ray, tMinimum, tMaximum));
	}
}