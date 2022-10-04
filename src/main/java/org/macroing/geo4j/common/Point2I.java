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
package org.macroing.geo4j.common;

import java.util.Objects;

import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Floats;

/**
 * A {@code Point2I} represents a point with two {@code int}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Point2I {
	/**
	 * A {@code Point2I} instance with the largest component values.
	 */
	public static final Point2I MAX = max();
	
	/**
	 * A {@code Point2I} instance with the smallest component values.
	 */
	public static final Point2I MIN = min();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The X-component of this {@code Point2I} instance.
	 */
	public final int x;
	
	/**
	 * The Y-component of this {@code Point2I} instance.
	 */
	public final int y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Point2I} instance given the component values {@code 0} and {@code 0}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Point2I(0, 0);
	 * }
	 * </pre>
	 */
	public Point2I() {
		this(0, 0);
	}
	
	/**
	 * Constructs a new {@code Point2I} instance given the component values {@code x} and {@code y}.
	 * 
	 * @param x the value of the X-component
	 * @param y the value of the Y-component
	 */
	public Point2I(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Point2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Point2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Point2I(%s, %s)", Integer.toString(this.x), Integer.toString(this.y));
	}
	
	/**
	 * Compares {@code object} to this {@code Point2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Point2I}, and they are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Point2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Point2I}, and they are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point2I)) {
			return false;
		} else if(this.x != Point2I.class.cast(object).x) {
			return false;
		} else if(this.y != Point2I.class.cast(object).y) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Point2I} instance.
	 * 
	 * @return a hash code for this {@code Point2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.x), Integer.valueOf(this.y));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Point2I} instance with the largest component values.
	 * 
	 * @return a new {@code Point2I} instance with the largest component values
	 */
	public static Point2I max() {
		return new Point2I(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the largest component values of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @return a new {@code Point2I} instance with the largest component values of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2I max(final Point2I a, final Point2I b) {
		return new Point2I(Math.max(a.x, b.x), Math.max(a.y, b.y));
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the largest component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @return a new {@code Point2I} instance with the largest component values of {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Point2I max(final Point2I a, final Point2I b, final Point2I c) {
		return new Point2I(Math.max(Math.max(a.x, b.x), c.x), Math.max(Math.max(a.y, b.y), c.y));
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the largest component values of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @param d a {@code Point2I} instance
	 * @return a new {@code Point2I} instance with the largest component values of {@code a}, {@code b}, {@code c} and {@code d}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public static Point2I max(final Point2I a, final Point2I b, final Point2I c, final Point2I d) {
		return new Point2I(Math.max(Math.max(a.x, b.x), Math.max(c.x, d.x)), Math.max(Math.max(a.y, b.y), Math.max(c.y, d.y)));
	}
	
	/**
	 * Returns a new {@code Point2I} instance that represents the midpoint of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @return a new {@code Point2I} instance that represents the midpoint of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2I midpoint(final Point2I a, final Point2I b) {
		return new Point2I((a.x + b.x) / 2, (a.y + b.y) / 2);
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the smallest component values.
	 * 
	 * @return a new {@code Point2I} instance with the smallest component values
	 */
	public static Point2I min() {
		return new Point2I(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the smallest component values of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @return a new {@code Point2I} instance with the smallest component values of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2I min(final Point2I a, final Point2I b) {
		return new Point2I(Math.min(a.x, b.x), Math.min(a.y, b.y));
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the smallest component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @return a new {@code Point2I} instance with the smallest component values of {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Point2I min(final Point2I a, final Point2I b, final Point2I c) {
		return new Point2I(Math.min(Math.min(a.x, b.x), c.x), Math.min(Math.min(a.y, b.y), c.y));
	}
	
	/**
	 * Returns a new {@code Point2I} instance with the smallest component values of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @param d a {@code Point2I} instance
	 * @return a new {@code Point2I} instance with the smallest component values of {@code a}, {@code b}, {@code c} and {@code d}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public static Point2I min(final Point2I a, final Point2I b, final Point2I c, final Point2I d) {
		return new Point2I(Math.min(Math.min(a.x, b.x), Math.min(c.x, d.x)), Math.min(Math.min(a.y, b.y), Math.min(c.y, d.y)));
	}
	
	/**
	 * Rotates {@code point} by {@code angle} degrees around {@code new Point2I(0, 0)}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2I.rotate(point, angle, false);
	 * }
	 * </pre>
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angle the rotation angle in degrees
	 * @return a new {@code Point2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public static Point2I rotate(final Point2I point, final double angle) {
		return rotate(point, angle, false);
	}
	
	/**
	 * Rotates {@code point} by {@code angle} degrees or radians around {@code new Point2I(0, 0)}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2I.rotate(point, angle, isAngleInRadians, new Point2I(0, 0));
	 * }
	 * </pre>
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @return a new {@code Point2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public static Point2I rotate(final Point2I point, final double angle, final boolean isAngleInRadians) {
		return rotate(point, angle, isAngleInRadians, new Point2I(0, 0));
	}
	
	/**
	 * Rotates {@code point} by {@code angle} degrees or radians around {@code center}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code point} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Point2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code center} are {@code null}
	 */
	public static Point2I rotate(final Point2I point, final double angle, final boolean isAngleInRadians, final Point2I center) {
		final double a = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double aCos = Doubles.cos(a);
		final double aSin = Doubles.sin(a);
		
		final int x = (int)(Doubles.rint((point.x - center.x) * aCos - (point.y - center.y) * aSin + center.x));
		final int y = (int)(Doubles.rint((point.x - center.x) * aSin + (point.y - center.y) * aCos + center.y));
		
		return new Point2I(x, y);
	}
	
	/**
	 * Rotates {@code point} by {@code angleCos} and {@code angleSin} around {@code center}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code point} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angleCos the cosine of the rotation angle
	 * @param angleSin the sine of the rotation angle
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Point2I} instance with the result of the rotation
	 */
	public static Point2I rotate(final Point2I point, final double angleCos, final double angleSin, final Point2I center) {
		final int x = (int)(Doubles.rint((point.x - center.x) * angleCos - (point.y - center.y) * angleSin + center.x));
		final int y = (int)(Doubles.rint((point.x - center.x) * angleSin + (point.y - center.y) * angleCos + center.y));
		
		return new Point2I(x, y);
	}
	
	/**
	 * Rotates {@code point} by {@code angle} degrees around {@code new Point2I(0, 0)}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2I.rotate(point, angle, false);
	 * }
	 * </pre>
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angle the rotation angle in degrees
	 * @return a new {@code Point2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public static Point2I rotate(final Point2I point, final float angle) {
		return rotate(point, angle, false);
	}
	
	/**
	 * Rotates {@code point} by {@code angle} degrees or radians around {@code new Point2I(0, 0)}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2I.rotate(point, angle, isAngleInRadians, new Point2I(0, 0));
	 * }
	 * </pre>
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @return a new {@code Point2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	public static Point2I rotate(final Point2I point, final float angle, final boolean isAngleInRadians) {
		return rotate(point, angle, isAngleInRadians, new Point2I(0, 0));
	}
	
	/**
	 * Rotates {@code point} by {@code angle} degrees or radians around {@code center}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code point} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Point2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, either {@code point} or {@code center} are {@code null}
	 */
	public static Point2I rotate(final Point2I point, final float angle, final boolean isAngleInRadians, final Point2I center) {
		final float a = isAngleInRadians ? angle : Floats.toRadians(angle);
		final float aCos = Floats.cos(a);
		final float aSin = Floats.sin(a);
		
		final int x = (int)(Floats.rint((point.x - center.x) * aCos - (point.y - center.y) * aSin + center.x));
		final int y = (int)(Floats.rint((point.x - center.x) * aSin + (point.y - center.y) * aCos + center.y));
		
		return new Point2I(x, y);
	}
	
	/**
	 * Rotates {@code point} by {@code angleCos} and {@code angleSin} around {@code center}.
	 * <p>
	 * Returns a new {@code Point2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code point} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param point the {@code Point2I} instance to rotate
	 * @param angleCos the cosine of the rotation angle
	 * @param angleSin the sine of the rotation angle
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Point2I} instance with the result of the rotation
	 */
	public static Point2I rotate(final Point2I point, final float angleCos, final float angleSin, final Point2I center) {
		final int x = (int)(Floats.rint((point.x - center.x) * angleCos - (point.y - center.y) * angleSin + center.x));
		final int y = (int)(Floats.rint((point.x - center.x) * angleSin + (point.y - center.y) * angleCos + center.y));
		
		return new Point2I(x, y);
	}
	
	/**
	 * Returns a {@code String} representation of {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param points a {@code Point2I[]} instance
	 * @return a {@code String} representation of {@code points}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static String toString(final Point2I... points) {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("new Point2I[] {");
		
		for(int i = 0; i < points.length; i++) {
			stringBuilder.append(i > 0 ? ", " : "");
			stringBuilder.append(Objects.requireNonNull(points[i]));
		}
		
		stringBuilder.append("}");
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns the distance from {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@code Point2I} instance denoting the eye to look from
	 * @param lookAt a {@code Point2I} instance denoting the target to look at
	 * @return the distance from {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static int distance(final Point2I eye, final Point2I lookAt) {
		return Vector2I.direction(eye, lookAt).length();
	}
	
	/**
	 * Returns the squared distance from {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@code Point2I} instance denoting the eye to look from
	 * @param lookAt a {@code Point2I} instance denoting the target to look at
	 * @return the squared distance from {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static int distanceSquared(final Point2I eye, final Point2I lookAt) {
		return Vector2I.direction(eye, lookAt).lengthSquared();
	}
}