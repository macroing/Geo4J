/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.Arrays;
import org.macroing.java.util.Randoms;
import org.macroing.java.util.visitor.Node;

/**
 * A {@code Point2F} represents a point with two {@code float}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Point2F implements Node {
	/**
	 * A {@code Point2F} instance with the largest component values.
	 */
	public static final Point2F MAX = max();
	
	/**
	 * A {@code Point2F} instance with the smallest component values.
	 */
	public static final Point2F MIN = min();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Point2F, Point2F> CACHE = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The X-component of this {@code Point2F} instance.
	 */
	public final float x;
	
	/**
	 * The Y-component of this {@code Point2F} instance.
	 */
	public final float y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Point2F} instance at {@code 0.0F} and {@code 0.0F}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Point2F(0.0F, 0.0F);
	 * }
	 * </pre>
	 */
	public Point2F() {
		this(0.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code Point2F} instance given the component values {@code v.x} and {@code v.y}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Point2F(v.x, v.y);
	 * }
	 * </pre>
	 * 
	 * @param v a {@link Vector2F} instance
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public Point2F(final Vector2F v) {
		this(v.x, v.y);
	}
	
	/**
	 * Constructs a new {@code Point2F} instance given the component values {@code component} and {@code component}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Point2F(component, component);
	 * }
	 * </pre>
	 * 
	 * @param component the value for both components
	 */
	public Point2F(final float component) {
		this(component, component);
	}
	
	/**
	 * Constructs a new {@code Point2F} instance at {@code x} and {@code y}.
	 * 
	 * @param x the X-component of this {@code Point2F} instance
	 * @param y the Y-component of this {@code Point2F} instance
	 */
	public Point2F(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Point2F} instance.
	 * 
	 * @return a {@code String} representation of this {@code Point2F} instance
	 */
	@Override
	public String toString() {
		return String.format("new Point2F(%s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y));
	}
	
	/**
	 * Compares {@code object} to this {@code Point2F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Point2F} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} instance to compare to this {@code Point2F} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Point2F} and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Point2F)) {
			return false;
		} else {
			return equals(Point2F.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code p} to this {@code Point2F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code p} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param p the {@code Point2F} instance to compare to this {@code Point2F} instance for equality
	 * @return {@code true} if, and only if, {@code p} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Point2F p) {
		if(p == this) {
			return true;
		} else if(p == null) {
			return false;
		} else if(!Floats.equals(this.x, p.x)) {
			return false;
		} else if(!Floats.equals(this.y, p.y)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Point2F} instance has at least one component with a value that is infinite, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Point2F} instance has at least one component with a value that is infinite, {@code false} otherwise
	 */
	public boolean hasInfinites() {
		return Floats.isInfinite(this.x) || Floats.isInfinite(this.y);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Point2F} instance has at least one component with a value that is NaN (Not a Number), {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Point2F} instance has at least one component with a value that is NaN (Not a Number), {@code false} otherwise
	 */
	public boolean hasNaNs() {
		return Floats.isNaN(this.x) || Floats.isNaN(this.y);
	}
	
	/**
	 * Returns {@code true} if, and only if, both components in this {@code Point2F} instance have values that are finite, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, both components in this {@code Point2F} instance have values that are finite, {@code false} otherwise
	 */
	public boolean isFinite() {
		return !hasInfinites() && !hasNaNs();
	}
	
	/**
	 * Returns {@code true} if, and only if, both components in this {@code Point2F} instance have values that are zero, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, both components in this {@code Point2F} instance have values that are zero, {@code false} otherwise
	 */
	public boolean isZero() {
		return Floats.isZero(this.x) && Floats.isZero(this.y);
	}
	
	/**
	 * Returns the value of the component at index {@code index}.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the index of the component whose value to return
	 * @return the value of the component at index {@code index}
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 2}
	 */
	public float getComponent(final int index) {
		switch(index) {
			case 0:
				return this.x;
			case 1:
				return this.y;
			default:
				throw new IllegalArgumentException(String.format("Illegal index: index=%s", Integer.toString(index)));
		}
	}
	
	/**
	 * Returns a {@code float[]} representation of this {@code Point2F} instance.
	 * 
	 * @return a {@code float[]} representation of this {@code Point2F} instance
	 */
	public float[] toArray() {
		return new float[] {this.x, this.y};
	}
	
	/**
	 * Returns a hash code for this {@code Point2F} instance.
	 * 
	 * @return a hash code for this {@code Point2F} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y));
	}
	
	/**
	 * Writes this {@code Point2F} instance to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} instance to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeFloat(this.x);
			dataOutput.writeFloat(this.y);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds the component values of {@code v} to the component values of {@code p}.
	 * <p>
	 * Returns a {@code Point2F} instance with the result of the addition.
	 * <p>
	 * If either {@code p} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p the {@code Point2F} instance on the left-hand side
	 * @param v the {@link Vector2F} instance on the right-hand side
	 * @return a {@code Point2F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code p} or {@code v} are {@code null}
	 */
	public static Point2F add(final Point2F p, final Vector2F v) {
		return new Point2F(p.x + v.x, p.y + v.y);
	}
	
	/**
	 * Adds the component values of {@code v} multiplied by {@code s} to the component values of {@code p}.
	 * <p>
	 * Returns a {@code Point2F} instance with the result of the addition.
	 * <p>
	 * If either {@code p} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p the {@code Point2F} instance on the left-hand side
	 * @param v the {@link Vector2F} instance on the right-hand side
	 * @param s the scalar to multiply the component values of {@code v} with
	 * @return a {@code Point2F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code p} or {@code v} are {@code null}
	 */
	public static Point2F add(final Point2F p, final Vector2F v, final float s) {
		return new Point2F(p.x + v.x * s, p.y + v.y * s);
	}
	
	/**
	 * Adds {@code s} to the component values of {@code p}.
	 * <p>
	 * Returns a {@code Point2F} instance with the result of the addition.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p the {@code Point2F} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Point2F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F add(final Point2F p, final float s) {
		return new Point2F(p.x + s, p.y + s);
	}
	
	/**
	 * Performs Barycentric interpolation on {@code a}, {@code b} and {@code c} given the Barycentric coordinates {@code barycentricCoordinates}.
	 * <p>
	 * Returns a {@code Point2F} instance with the interpolated point.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code barycentricCoordinates} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @param barycentricCoordinates a {@link Point3F} instance that contains the Barycentric coordinates
	 * @return a {@code Point2F} instance with the interpolated point
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code barycentricCoordinates} are {@code null}
	 */
	public static Point2F barycentricInterpolation(final Point2F a, final Point2F b, final Point2F c, final Point3F barycentricCoordinates) {
		final float x = a.x * barycentricCoordinates.x + b.x * barycentricCoordinates.y + c.x * barycentricCoordinates.z;
		final float y = a.y * barycentricCoordinates.x + b.y * barycentricCoordinates.y + c.y * barycentricCoordinates.z;
		
		return new Point2F(x, y);
	}
	
	/**
	 * Returns a {@code Point2F} instance that represents the centroid of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Note: This method is equivalent to {@link #midpoint(Point2F, Point2F)}.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @return a {@code Point2F} instance that represents the centroid of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2F centroid(final Point2F a, final Point2F b) {
		return new Point2F((a.x + b.x) / 2.0F, (a.y + b.y) / 2.0F);
	}
	
	/**
	 * Returns a {@code Point2F} instance that represents the centroid of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @return a {@code Point2F} instance that represents the centroid of {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Point2F centroid(final Point2F a, final Point2F b, final Point2F c) {
		return new Point2F((a.x + b.x + c.x) / 3.0F, (a.y + b.y + c.y) / 3.0F);
	}
	
	/**
	 * Returns a {@code Point2F} instance that represents the centroid of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @param d a {@code Point2F} instance
	 * @return a {@code Point2F} instance that represents the centroid of {@code a}, {@code b}, {@code c} and {@code d}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public static Point2F centroid(final Point2F a, final Point2F b, final Point2F c, final Point2F d) {
		return new Point2F((a.x + b.x + c.x + d.x) / 4.0F, (a.y + b.y + c.y + d.y) / 4.0F);
	}
	
	/**
	 * Returns a {@code Point2F} instance that represents the centroid of {@code a}, {@code b}, {@code c}, {@code d}, {@code e}, {@code f}, {@code g} and {@code h}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c}, {@code d}, {@code e}, {@code f}, {@code g} or {@code h} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @param d a {@code Point2F} instance
	 * @param e a {@code Point2F} instance
	 * @param f a {@code Point2F} instance
	 * @param g a {@code Point2F} instance
	 * @param h a {@code Point2F} instance
	 * @return a {@code Point2F} instance that represents the centroid of {@code a}, {@code b}, {@code c}, {@code d}, {@code e}, {@code f}, {@code g} and {@code h}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c}, {@code d}, {@code e}, {@code f}, {@code g} or {@code h} are {@code null}
	 */
	public static Point2F centroid(final Point2F a, final Point2F b, final Point2F c, final Point2F d, final Point2F e, final Point2F f, final Point2F g, final Point2F h) {
		return new Point2F((a.x + b.x + c.x + d.x + e.x + f.x + g.x + h.x) / 8.0F, (a.y + b.y + c.y + d.y + e.y + f.y + g.y + h.y) / 8.0F);
	}
	
	/**
	 * Returns a cached version of {@code p}.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2F} instance
	 * @return a cached version of {@code p}
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F getCached(final Point2F p) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(p, "p == null"), key -> p);
	}
	
	/**
	 * Performs a linear interpolation operation on the supplied values.
	 * <p>
	 * Returns a {@code Point2F} instance with the result of the linear interpolation operation.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param t the factor
	 * @return a {@code Point2F} instance with the result of the linear interpolation operation
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2F lerp(final Point2F a, final Point2F b, final float t) {
		return new Point2F(Floats.lerp(a.x, b.x, t), Floats.lerp(a.y, b.y, t));
	}
	
	/**
	 * Returns a {@code Point2F} instance with the largest component values.
	 * 
	 * @return a {@code Point2F} instance with the largest component values
	 */
	public static Point2F max() {
		return new Point2F(Floats.MAX_VALUE, Floats.MAX_VALUE);
	}
	
	/**
	 * Returns a {@code Point2F} instance with the largest component values of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @return a {@code Point2F} instance with the largest component values of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2F max(final Point2F a, final Point2F b) {
		return new Point2F(Floats.max(a.x, b.x), Floats.max(a.y, b.y));
	}
	
	/**
	 * Returns a {@code Point2F} instance with the largest component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @return a {@code Point2F} instance with the largest component values of {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Point2F max(final Point2F a, final Point2F b, final Point2F c) {
		return new Point2F(Floats.max(a.x, b.x, c.x), Floats.max(a.y, b.y, c.y));
	}
	
	/**
	 * Returns a {@code Point2F} instance with the largest component values of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @param d a {@code Point2F} instance
	 * @return a {@code Point2F} instance with the largest component values of {@code a}, {@code b}, {@code c} and {@code d}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public static Point2F max(final Point2F a, final Point2F b, final Point2F c, final Point2F d) {
		return new Point2F(Floats.max(a.x, b.x, c.x, d.x), Floats.max(a.y, b.y, c.y, d.y));
	}
	
	/**
	 * Returns a {@code Point2F} instance that represents the midpoint of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @return a {@code Point2F} instance that represents the midpoint of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2F midpoint(final Point2F a, final Point2F b) {
		return new Point2F((a.x + b.x) * 0.5F, (a.y + b.y) * 0.5F);
	}
	
	/**
	 * Returns a {@code Point2F} instance with the smallest component values.
	 * 
	 * @return a {@code Point2F} instance with the smallest component values
	 */
	public static Point2F min() {
		return new Point2F(Floats.MIN_VALUE, Floats.MIN_VALUE);
	}
	
	/**
	 * Returns a {@code Point2F} instance with the smallest component values of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @return a {@code Point2F} instance with the smallest component values of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Point2F min(final Point2F a, final Point2F b) {
		return new Point2F(Floats.min(a.x, b.x), Floats.min(a.y, b.y));
	}
	
	/**
	 * Returns a {@code Point2F} instance with the smallest component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @return a {@code Point2F} instance with the smallest component values of {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Point2F min(final Point2F a, final Point2F b, final Point2F c) {
		return new Point2F(Floats.min(a.x, b.x, c.x), Floats.min(a.y, b.y, c.y));
	}
	
	/**
	 * Returns a {@code Point2F} instance with the smallest component values of {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point2F} instance
	 * @param b a {@code Point2F} instance
	 * @param c a {@code Point2F} instance
	 * @param d a {@code Point2F} instance
	 * @return a {@code Point2F} instance with the smallest component values of {@code a}, {@code b}, {@code c} and {@code d}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public static Point2F min(final Point2F a, final Point2F b, final Point2F c, final Point2F d) {
		return new Point2F(Floats.min(a.x, b.x, c.x, d.x), Floats.min(a.y, b.y, c.y, d.y));
	}
	
	/**
	 * Projects {@code p} to the plane represented by {@code o}, {@code u} and {@code v}.
	 * <p>
	 * Returns a {@code Point2F} instance with the projected coordinates.
	 * <p>
	 * If either {@code p}, {@code o}, {@code u} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p the {@link Point3F} instance to project to the plane
	 * @param o the {@code Point3F} instance that represents the origin of the plane
	 * @param u the {@link Vector3F} instance that represents the U-direction of the plane
	 * @param v the {@code Vector3F} instance that represents the V-direction of the plane
	 * @return a {@code Point2F} instance with the projected coordinates
	 * @throws NullPointerException thrown if, and only if, either {@code p}, {@code o}, {@code u} or {@code v} are {@code null}
	 */
	public static Point2F project(final Point3F p, final Point3F o, final Vector3F u, final Vector3F v) {
		final Vector3F directionOP = Vector3F.direction(o, p);
		
		final float x = Vector3F.dotProduct(directionOP, u);
		final float y = Vector3F.dotProduct(directionOP, v);
		
		return new Point2F(x, y);
	}
	
	/**
	 * Returns a {@code Point2F} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Point2F} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Point2F read(final DataInput dataInput) {
		try {
			return new Point2F(dataInput.readFloat(), dataInput.readFloat());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Rotates {@code p} around origin by {@code angle} degrees or radians.
	 * <p>
	 * Returns a {@code Point2F} instance with the rotation applied.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2F} instance
	 * @param angle an angle in degrees or radians
	 * @param isRadians {@code true} if, and only if, {@code angle} is in radians, {@code false} otherwise
	 * @return a {@code Point2F} instance with the rotation applied
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F rotateCounterclockwise(final Point2F p, final float angle, final boolean isRadians) {
		final float angleRadians = isRadians ? angle : Floats.toRadians(angle);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final float x = p.x * angleRadiansCos - p.y * angleRadiansSin;
		final float y = p.y * angleRadiansCos + p.x * angleRadiansSin;
		
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
		
		return new Point2F(x, y);
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution.
	 * <p>
	 * Returns a {@code Point2F} instance with the sampled point.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2F.sampleDiskUniformDistribution(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point2F} instance with the sampled point
	 */
	public static Point2F sampleDiskUniformDistribution() {
		return sampleDiskUniformDistribution(sampleRandom());
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution.
	 * <p>
	 * Returns a {@code Point2F} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point2F} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F sampleDiskUniformDistribution(final Point2F p) {
//		Convert the component values in 'p' to Polar coordinates:
		final float r = Floats.sqrt(p.x);
		final float t = Floats.PI_MULTIPLIED_BY_2 * p.y;
		
//		Convert the Polar coordinates to Cartesian coordinates:
		final float x = r * Floats.cos(t);
		final float y = r * Floats.sin(t);
		
		return new Point2F(x, y);
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution using concentric mapping.
	 * <p>
	 * Returns a {@code Point2F} instance with the sampled point.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2F.sampleDiskUniformDistributionByConcentricMapping(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point2F} instance with the sampled point
	 */
	public static Point2F sampleDiskUniformDistributionByConcentricMapping() {
		return sampleDiskUniformDistributionByConcentricMapping(sampleRandom());
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution using concentric mapping.
	 * <p>
	 * Returns a {@code Point2F} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2F.sampleDiskUniformDistributionByConcentricMapping(p, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param p a {@code Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point2F} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F sampleDiskUniformDistributionByConcentricMapping(final Point2F p) {
		return sampleDiskUniformDistributionByConcentricMapping(p, 1.0F);
	}
	
	/**
	 * Samples a point on a disk with a uniform distribution using concentric mapping.
	 * <p>
	 * Returns a {@code Point2F} instance with the sampled point.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2F} instance with components in the interval [0.0, 1.0]
	 * @param radius the radius of the disk
	 * @return a {@code Point2F} instance with the sampled point
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F sampleDiskUniformDistributionByConcentricMapping(final Point2F p, final float radius) {
		if(p.isZero()) {
			return p;
		}
		
//		Convert the component values in 'p' from the interval [0.0, 1.0] to the interval [-1.0, 1.0]:
		final float a = p.x * 2.0F - 1.0F;
		final float b = p.y * 2.0F - 1.0F;
		
		if(a * a > b * b) {
//			Convert 'a' and 'b' to Polar coordinates:
			final float r = radius * a;
			final float t = Floats.PI / 4.0F * (b / a);
			
//			Convert the Polar coordinates to Cartesian coordinates:
			final float x = r * Floats.cos(t);
			final float y = r * Floats.sin(t);
			
			return new Point2F(x, y);
		}
		
//		Convert 'a' and 'b' to Polar coordinates:
		final float r = radius * b;
		final float t = Floats.PI / 2.0F - Floats.PI / 4.0F * (a / b);
		
//		Convert the Polar coordinates to Cartesian coordinates:
		final float x = r * Floats.cos(t);
		final float y = r * Floats.sin(t);
		
		return new Point2F(x, y);
	}
	
	/**
	 * Samples a {@code Point2F} instance using the exact inverse CDF of a tent filter.
	 * <p>
	 * Returns a {@code Point2F} instance with the result.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Point2F.sampleExactInverseTentFilter(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Point2F} instance with the result
	 */
	public static Point2F sampleExactInverseTentFilter() {
		return sampleExactInverseTentFilter(sampleRandom());
	}
	
	/**
	 * Samples a {@code Point2F} instance using the exact inverse CDF of a tent filter.
	 * <p>
	 * Returns a {@code Point2F} instance with the result.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The X-component of the returned {@code Point2F} instance will be in the interval [-1.0, 1.0] if, and only if, {@code p.x} is in the interval [0.0, 1.0]. Otherwise it will be NaN (Not a Number).
	 * <p>
	 * The Y-component of the returned {@code Point2F} instance will be in the interval [-1.0, 1.0] if, and only if, {@code p.y} is in the interval [0.0, 1.0]. Otherwise it will be NaN (Not a Number).
	 * 
	 * @param p a {@code Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Point2F} instance with the result
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F sampleExactInverseTentFilter(final Point2F p) {
		final float a = p.x * 2.0F;
		final float b = p.y * 2.0F;
		
		final float x = a < 1.0F ? Floats.sqrt(a) - 1.0F : 1.0F - Floats.sqrt(2.0F - a);
		final float y = b < 1.0F ? Floats.sqrt(b) - 1.0F : 1.0F - Floats.sqrt(2.0F - b);
		
		return new Point2F(x, y);
	}
	
	/**
	 * Samples a {@code Point2F} instance using a PRNG.
	 * <p>
	 * Returns a {@code Point2F} instance with the result.
	 * <p>
	 * The X-component of the returned {@code Point2F} instance will be in the interval [0.0, 1.0).
	 * <p>
	 * The Y-component of the returned {@code Point2F} instance will be in the interval [0.0, 1.0).
	 * 
	 * @return a {@code Point2F} instance with the result
	 */
	public static Point2F sampleRandom() {
		return new Point2F(Randoms.nextFloat(), Randoms.nextFloat());
	}
	
	/**
	 * Scales {@code p} using {@code v}.
	 * <p>
	 * Returns a {@code Point2F} instance with the scale applied.
	 * <p>
	 * If either {@code p} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@code Point2F} instance
	 * @param v a {@link Vector2F} instance
	 * @return a {@code Point2F} instance with the scale applied
	 * @throws NullPointerException thrown if, and only if, either {@code p} or {@code v} are {@code null}
	 */
	public static Point2F scale(final Point2F p, final Vector2F v) {
		return new Point2F(p.x * v.x, p.y * v.y);
	}
	
	/**
	 * Returns a {@code Point2F} instance with the spherical coordinates of {@code direction} as its component values.
	 * <p>
	 * If {@code direction} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param direction a {@link Vector3F} instance
	 * @return a {@code Point2F} instance with the spherical coordinates of {@code direction} as its component values
	 * @throws NullPointerException thrown if, and only if, {@code direction} is {@code null}
	 */
	public static Point2F sphericalCoordinates(final Vector3F direction) {
		return new Point2F(direction.sphericalPhi() * Floats.PI_MULTIPLIED_BY_2_RECIPROCAL, direction.sphericalTheta() * Floats.PI_RECIPROCAL);
	}
	
	/**
	 * Subtracts the component values of {@code v} from the component values of {@code p}.
	 * <p>
	 * Returns a {@code Point2F} instance with the result of the subtraction.
	 * <p>
	 * If either {@code p} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p the {@code Point2F} instance on the left-hand side
	 * @param v the {@link Vector2F} instance on the right-hand side
	 * @return a {@code Point2F} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, either {@code p} or {@code v} are {@code null}
	 */
	public static Point2F subtract(final Point2F p, final Vector2F v) {
		return new Point2F(p.x - v.x, p.y - v.y);
	}
	
	/**
	 * Subtracts {@code s} from the component values of {@code p}.
	 * <p>
	 * Returns a {@code Point2F} instance with the result of the subtraction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p the {@code Point2F} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Point2F} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Point2F subtract(final Point2F p, final float s) {
		return new Point2F(p.x - s, p.y - s);
	}
	
	/**
	 * Returns a {@code String} representation of {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param points a {@code Point2F[]} instance
	 * @return a {@code String} representation of {@code points}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static String toString(final Point2F... points) {
		Arrays.requireNonNull(points, "points");
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("new Point2F[] {");
		
		for(int i = 0; i < points.length; i++) {
			stringBuilder.append(i > 0 ? ", " : "");
			stringBuilder.append(points[i]);
		}
		
		stringBuilder.append("}");
		
		return stringBuilder.toString();
	}
	
	/**
	 * Returns the distance from {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@code Point2F} instance denoting the eye to look from
	 * @param lookAt a {@code Point2F} instance denoting the target to look at
	 * @return the distance from {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static float distance(final Point2F eye, final Point2F lookAt) {
		return Vector2F.direction(eye, lookAt).length();
	}
	
	/**
	 * Returns the squared distance from {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@code Point2F} instance denoting the eye to look from
	 * @param lookAt a {@code Point2F} instance denoting the target to look at
	 * @return the squared distance from {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static float distanceSquared(final Point2F eye, final Point2F lookAt) {
		return Vector2F.direction(eye, lookAt).lengthSquared();
	}
	
	/**
	 * Returns the size of the cache.
	 * 
	 * @return the size of the cache
	 */
	public static int getCacheSize() {
		return CACHE.size();
	}
	
	/**
	 * Clears the cache.
	 */
	public static void clearCache() {
		CACHE.clear();
	}
}