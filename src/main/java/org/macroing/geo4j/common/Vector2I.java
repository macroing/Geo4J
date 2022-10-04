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
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector2I)) {
			return false;
		} else if(this.x != Vector2I.class.cast(object).x) {
			return false;
		} else if(this.y != Vector2I.class.cast(object).y) {
			return false;
		} else {
			return true;
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Vector2I} instance that is pointing in the direction of {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@link Point2I} instance denoting the eye to look from
	 * @param lookAt a {@code Point2I} instance denoting the target to look at
	 * @return a new {@code Vector2I} instance that is pointing in the direction of {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static Vector2I direction(final Point2I eye, final Point2I lookAt) {
		return new Vector2I(lookAt.x - eye.x, lookAt.y - eye.y);
	}
}