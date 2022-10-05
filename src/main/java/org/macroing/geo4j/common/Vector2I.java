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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.Objects;

import org.macroing.java.lang.Ints;

/**
 * A {@code Vector2I} represents a vector with two {@code int}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Vector2I {
	/**
	 * The X-component of this {@code Vector2I} instance.
	 */
	public final int x;
	
	/**
	 * The Y-component of this {@code Vector2I} instance.
	 */
	public final int y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Vector2I} instance given the component values {@code 0} and {@code 0}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector2I(0, 0);
	 * }
	 * </pre>
	 */
	public Vector2I() {
		this(0, 0);
	}
	
	/**
	 * Constructs a new {@code Vector2I} instance given the component values {@code p.x} and {@code p.y}.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector2I(p.x, p.y);
	 * }
	 * </pre>
	 * 
	 * @param p a {@link Point2I} instance
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public Vector2I(final Point2I p) {
		this(p.x, p.y);
	}
	
	/**
	 * Constructs a new {@code Vector2I} instance given the component values {@code x} and {@code y}.
	 * 
	 * @param x the value of the X-component
	 * @param y the value of the Y-component
	 */
	public Vector2I(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Vector2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Vector2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Vector2I(%d, %d)", Integer.valueOf(this.x), Integer.valueOf(this.y));
	}
	
	/**
	 * Compares {@code object} to this {@code Vector2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Vector2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Vector2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Vector2I}, and their respective values are equal, {@code false} otherwise
	 */
//	TODO: Add unit tests!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector2I)) {
			return false;
		} else {
			return equals(Vector2I.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code v} to this {@code Vector2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code v} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param v the {@code Vector2I} instance to compare to this {@code Vector2I} instance for equality
	 * @return {@code true} if, and only if, {@code v} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
//	TODO: Add unit tests!
	public boolean equals(final Vector2I v) {
		if(v == this) {
			return true;
		} else if(v == null) {
			return false;
		} else if(this.x != v.x) {
			return false;
		} else if(this.y != v.y) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, both components in this {@code Vector2I} instance have values that are zero, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, both components in this {@code Vector2I} instance have values that are zero, {@code false} otherwise
	 */
//	TODO: Add unit tests!
	public boolean isZero() {
		return (this.x == -0 || this.x == +0) && (this.y == -0 || this.y == +0);
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
//	TODO: Add unit tests!
	public int getComponent(final int index) {
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
	 * Returns a hash code for this {@code Vector2I} instance.
	 * 
	 * @return a hash code for this {@code Vector2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.x), Integer.valueOf(this.y));
	}
	
	/**
	 * Returns the length of this {@code Vector2I} instance.
	 * 
	 * @return the length of this {@code Vector2I} instance
	 */
	public int length() {
		return (int)(Math.sqrt(lengthSquared()));
	}
	
	/**
	 * Returns the squared length of this {@code Vector2I} instance.
	 * 
	 * @return the squared length of this {@code Vector2I} instance
	 */
	public int lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
	/**
	 * Returns a {@code int[]} representation of this {@code Vector2I} instance.
	 * 
	 * @return a {@code int[]} representation of this {@code Vector2I} instance
	 */
//	TODO: Add unit tests!
	public int[] toArray() {
		return new int[] {this.x, this.y};
	}
	
	/**
	 * Writes this {@code Vector2I} instance to {@code dataOutput}.
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
			dataOutput.writeInt(this.x);
			dataOutput.writeInt(this.y);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Vector2I} instance with the absolute component values of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector2I} instance
	 * @return a {@code Vector2I} instance with the absolute component values of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I abs(final Vector2I v) {
		return new Vector2I(Ints.abs(v.x), Ints.abs(v.y));
	}
	
	/**
	 * Adds the component values of {@code vRHS} to the component values of {@code vLHS}.
	 * <p>
	 * Returns a {@code Vector2I} instance with the result of the addition.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector addition is performed componentwise.
	 * 
	 * @param vLHS the {@code Vector2I} instance on the left-hand side
	 * @param vRHS the {@code Vector2I} instance on the right-hand side
	 * @return a {@code Vector2I} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I add(final Vector2I vLHS, final Vector2I vRHS) {
		return new Vector2I(vLHS.x + vRHS.x, vLHS.y + vRHS.y);
	}
	
	/**
	 * Adds the component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * Returns a {@code Vector2I} instance with the result of the addition.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector addition is performed componentwise.
	 * 
	 * @param a a {@code Vector2I} instance
	 * @param b a {@code Vector2I} instance
	 * @param c a {@code Vector2I} instance
	 * @return a {@code Vector2I} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I add(final Vector2I a, final Vector2I b, final Vector2I c) {
		return new Vector2I(a.x + b.x + c.x, a.y + b.y + c.y);
	}
	
	/**
	 * Returns a {@code Vector2I} instance that is pointing in the direction of {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@link Point2I} instance denoting the eye to look from
	 * @param lookAt a {@code Point2I} instance denoting the target to look at
	 * @return a {@code Vector2I} instance that is pointing in the direction of {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static Vector2I direction(final Point2I eye, final Point2I lookAt) {
		return new Vector2I(lookAt.x - eye.x, lookAt.y - eye.y);
	}
	
	/**
	 * Divides the component values of {@code v} with {@code s}.
	 * <p>
	 * Returns a {@code Vector2I} instance with the result of the division.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector division is performed componentwise.
	 * 
	 * @param v the {@code Vector2I} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Vector2I} instance with the result of the division
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I divide(final Vector2I v, final int s) {
		return new Vector2I(v.x / s, v.y / s);
	}
	
	/**
	 * Multiplies the component values of {@code v} with {@code s}.
	 * <p>
	 * Returns a {@code Vector2I} instance with the result of the multiplication.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector multiplication is performed componentwise.
	 * 
	 * @param v the {@code Vector2I} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Vector2I} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I multiply(final Vector2I v, final int s) {
		return new Vector2I(v.x * s, v.y * s);
	}
	
	/**
	 * Negates the component values of {@code v}.
	 * <p>
	 * Returns a {@code Vector2I} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector2I} instance
	 * @return a {@code Vector2I} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I negate(final Vector2I v) {
		return new Vector2I(-v.x, -v.y);
	}
	
	/**
	 * Returns a {@code Vector2I} instance that is perpendicular to {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector2I} instance
	 * @return a {@code Vector2I} instance that is perpendicular to {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I perpendicular(final Vector2I v) {
		return new Vector2I(v.y, -v.x);
	}
	
	/**
	 * Returns a {@code Vector2I} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Vector2I} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
//	TODO: Add unit tests!
	public static Vector2I read(final DataInput dataInput) {
		try {
			return new Vector2I(dataInput.readInt(), dataInput.readInt());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Subtracts the component values of {@code vRHS} from the component values of {@code vLHS}.
	 * <p>
	 * Returns a {@code Vector2I} instance with the result of the subtraction.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector subtraction is performed componentwise.
	 * 
	 * @param vLHS the {@code Vector2I} instance on the left-hand side
	 * @param vRHS the {@code Vector2I} instance on the right-hand side
	 * @return a {@code Vector2I} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector2I subtract(final Vector2I vLHS, final Vector2I vRHS) {
		return new Vector2I(vLHS.x - vRHS.x, vLHS.y - vRHS.y);
	}
	
	/**
	 * Returns a {@code Vector2I} instance equivalent to {@code new Vector2I(1, 0)}.
	 * 
	 * @return a {@code Vector2I} instance equivalent to {@code new Vector2I(1, 0)}
	 */
//	TODO: Add unit tests!
	public static Vector2I x() {
		return x(1);
	}
	
	/**
	 * Returns a {@code Vector2I} instance equivalent to {@code new Vector2I(x, 0)}.
	 * 
	 * @param x the value of the X-component
	 * @return a {@code Vector2I} instance equivalent to {@code new Vector2I(x, 0)}
	 */
//	TODO: Add unit tests!
	public static Vector2I x(final int x) {
		return new Vector2I(x, 0);
	}
	
	/**
	 * Returns a {@code Vector2I} instance equivalent to {@code new Vector2I(0, 1)}.
	 * 
	 * @return a {@code Vector2I} instance equivalent to {@code new Vector2I(0, 1)}
	 */
//	TODO: Add unit tests!
	public static Vector2I y() {
		return y(1);
	}
	
	/**
	 * Returns a {@code Vector2I} instance equivalent to {@code new Vector2I(0, y)}.
	 * 
	 * @param y the value of the Y-component
	 * @return a {@code Vector2I} instance equivalent to {@code new Vector2I(0, y)}
	 */
//	TODO: Add unit tests!
	public static Vector2I y(final int y) {
		return new Vector2I(0, y);
	}
}