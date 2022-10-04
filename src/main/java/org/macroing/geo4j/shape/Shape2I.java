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
package org.macroing.geo4j.shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.macroing.geo4j.common.Point2I;
import org.macroing.java.lang.Doubles;

/**
 * A {@code Shape2I} represents a 2-dimensional shape that operates on {@code int}-based data types.
 * <p>
 * All official implementations of this interface are immutable and therefore thread-safe. But this cannot be guaranteed for all implementations.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface Shape2I {
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Shape2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Shape2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Shape2I} instance
	 */
	List<Point2I> findPoints(final boolean isIncludingBorderOnly);
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code Shape2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code Shape2I} instance
	 */
	Point2I max();
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code Shape2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code Shape2I} instance
	 */
	Point2I min();
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Shape2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Shape2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Shape2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	boolean contains(final Point2I point, final boolean isIncludingBorderOnly);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Shape2I} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * shape2I.findPoints(false);
	 * }
	 * </pre>
	 * 
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Shape2I} instance
	 */
	default List<Point2I> findPoints() {
		return findPoints(false);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances that represents the complement of this {@code Shape2I} instance within {@code shape}.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * shape2I.findPointsOfComplement(shape, false);
	 * }
	 * </pre>
	 * 
	 * @param shape a {@code Shape2I} instance
	 * @return a {@code List} with {@code Point2I} instances that represents the complement of this {@code Shape2I} instance within {@code shape}
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
	default List<Point2I> findPointsOfComplement(final Shape2I shape) {
		return findPointsOfComplement(shape, false);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances that represents the complement of this {@code Shape2I} instance within {@code shape}.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape a {@code Shape2I} instance
	 * @param isExcludingBorderOnly {@code true} if, and only if, this method should only exclude {@code Point2I} instances on the border of this {@code Shape2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances that represents the complement of this {@code Shape2I} instance within {@code shape}
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
	default List<Point2I> findPointsOfComplement(final Shape2I shape, final boolean isExcludingBorderOnly) {
		return shape.findPoints().stream().filter(point -> !contains(point) || isExcludingBorderOnly && !contains(point, true)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances that represents the intersection between this {@code Shape2I} instance and {@code shape}.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * shape2I.findPointsOfIntersection(shape, false);
	 * }
	 * </pre>
	 * 
	 * @param shape a {@code Shape2I} instance
	 * @return a {@code List} with {@code Point2I} instances that represents the intersection between this {@code Shape2I} instance and {@code shape}
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
	default List<Point2I> findPointsOfIntersection(final Shape2I shape) {
		return findPointsOfIntersection(shape, false);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances that represents the intersection between this {@code Shape2I} instance and {@code shape}.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape a {@code Shape2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Shape2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances that represents the intersection between this {@code Shape2I} instance and {@code shape}
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
	default List<Point2I> findPointsOfIntersection(final Shape2I shape, final boolean isIncludingBorderOnly) {
		return shape.findPoints().stream().filter(point -> contains(point, isIncludingBorderOnly)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Returns a {@link Point2I} instance that represents the closest point to {@code point} and is contained in this {@code Shape2I} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@code Point2I} instance
	 * @return a {@code Point2I} instance that represents the closest point to {@code point} and is contained in this {@code Shape2I} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	default Point2I findPointClosestTo(final Point2I point) {
		Objects.requireNonNull(point, "point == null");
		
		final double aX = point.x;
		final double aY = point.y;
		
		Point2I closestPoint = null;
		
		double closestDistance = Double.MAX_VALUE;
		
		for(final Point2I currentPoint : findPoints()) {
			final double bX = currentPoint.x;
			final double bY = currentPoint.y;
			
			final double dX = bX - aX;
			final double dY = bY - aY;
			
			final double distance = Doubles.sqrt(dX * dX + dY * dY);
			
			if(closestPoint == null || distance < closestDistance) {
				closestDistance = distance;
				closestPoint = currentPoint;
			}
		}
		
		return Objects.requireNonNull(closestPoint);
	}
	
	/**
	 * Returns a {@link Point2I} instance that represents the midpoint of {@link #max()} and {@link #min()} for this {@code Shape2I} instance.
	 * 
	 * @return a {@code Point2I} instance that represents the midpoint of {@code  max()} and {@code  min()} for this {@code Shape2I} instance
	 */
	default Point2I midpoint() {
		return Point2I.midpoint(max(), min());
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Shape2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * shape2I.contains(point, false);
	 * }
	 * </pre>
	 * 
	 * @param point a {@link Point2I} instance
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Shape2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	default boolean contains(final Point2I point) {
		return contains(point, false);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Shape2I} instance intersects {@code shape}, {@code false} otherwise.
	 * <p>
	 * If {@code shape} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param shape the {@code Shape2I} to perform an intersection test against
	 * @return {@code true} if, and only if, this {@code Shape2I} instance intersects {@code shape}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code shape} is {@code null}
	 */
	default boolean intersects(final Shape2I shape) {
		return !Collections.disjoint(findPoints(), shape.findPoints());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in {@code shapeLHS} but not in {@code shapeRHS}, {@code false} otherwise.
	 * <p>
	 * If either {@code point}, {@code shapeLHS} or {@code shapeRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param shapeLHS the {@code Shape2I} instance on the left-hand side of the operation
	 * @param shapeRHS the {@code Shape2I} instance on the right-hand side of the operation
	 * @return {@code true} if, and only if, {@code point} is contained in {@code shapeLHS} but not in {@code shapeRHS}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code shapeLHS} or {@code shapeRHS} are {@code null}
	 */
	static boolean containsDifference(final Point2I point, final Shape2I shapeLHS, final Shape2I shapeRHS) {
		Objects.requireNonNull(point, "point == null");
		Objects.requireNonNull(shapeLHS, "shapeLHS == null");
		Objects.requireNonNull(shapeRHS, "shapeRHS == null");
		
		final boolean containsShapeLHS = shapeLHS.contains(point);
		final boolean containsShapeRHS = shapeRHS.contains(point);
		final boolean containsDifference = containsShapeLHS && !containsShapeRHS;
		
		return containsDifference;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in both {@code shapeLHS} and {@code shapeRHS}, {@code false} otherwise.
	 * <p>
	 * If either {@code point}, {@code shapeLHS} or {@code shapeRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param shapeLHS the {@code Shape2I} instance on the left-hand side of the operation
	 * @param shapeRHS the {@code Shape2I} instance on the right-hand side of the operation
	 * @return {@code true} if, and only if, {@code point} is contained in both {@code shapeLHS} and {@code shapeRHS}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code shapeLHS} or {@code shapeRHS} are {@code null}
	 */
	static boolean containsIntersection(final Point2I point, final Shape2I shapeLHS, final Shape2I shapeRHS) {
		Objects.requireNonNull(point, "point == null");
		Objects.requireNonNull(shapeLHS, "shapeLHS == null");
		Objects.requireNonNull(shapeRHS, "shapeRHS == null");
		
		final boolean containsShapeLHS = shapeLHS.contains(point);
		final boolean containsShapeRHS = shapeRHS.contains(point);
		final boolean containsIntersection = containsShapeLHS && containsShapeRHS;
		
		return containsIntersection;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in either {@code shapeLHS} or {@code shapeRHS}, {@code false} otherwise.
	 * <p>
	 * If either {@code point}, {@code shapeLHS} or {@code shapeRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param shapeLHS the {@code Shape2I} instance on the left-hand side of the operation
	 * @param shapeRHS the {@code Shape2I} instance on the right-hand side of the operation
	 * @return {@code true} if, and only if, {@code point} is contained in either {@code shapeLHS} or {@code shapeRHS}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code point}, {@code shapeLHS} or {@code shapeRHS} are {@code null}
	 */
	static boolean containsUnion(final Point2I point, final Shape2I shapeLHS, final Shape2I shapeRHS) {
		Objects.requireNonNull(point, "point == null");
		Objects.requireNonNull(shapeLHS, "shapeLHS == null");
		Objects.requireNonNull(shapeRHS, "shapeRHS == null");
		
		final boolean containsShapeLHS = shapeLHS.contains(point);
		final boolean containsShapeRHS = shapeRHS.contains(point);
		final boolean containsUnion = containsShapeLHS || containsShapeRHS;
		
		return containsUnion;
	}
}