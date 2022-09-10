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
package org.macroing.geo4j.vector;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.geo4j.point.Point2D;
import org.macroing.geo4j.point.Point3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Strings;

/**
 * A {@code Vector2D} represents a vector with two {@code double}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Vector2D {
	/**
	 * The X-component of this {@code Vector2D} instance.
	 */
	public final double x;
	
	/**
	 * The Y-component of this {@code Vector2D} instance.
	 */
	public final double y;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Vector2D() {
		this(0.0D, 0.0D);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add unit tests!
	public Vector2D(final Point2D p) {
		this(p.x, p.y);
	}
	
//	TODO: Add Javadocs!
	public Vector2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("new Vector2D(%s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y));
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector2D)) {
			return false;
		} else {
			return equals(Vector2D.class.cast(object));
		}
	}
	
//	TODO: Add Javadocs!
	public boolean equals(final Vector2D v) {
		if(v == this) {
			return true;
		} else if(v == null) {
			return false;
		} else if(!Doubles.equals(this.x, v.x)) {
			return false;
		} else if(!Doubles.equals(this.y, v.y)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public double length() {
		return Doubles.sqrt(lengthSquared());
	}
	
//	TODO: Add Javadocs!
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static Vector2D direction(final Point2D eye, final Point2D lookAt) {
		return new Vector2D(lookAt.x - eye.x, lookAt.y - eye.y);
	}
	
//	TODO: Add Javadocs!
	public static Vector2D directionXY(final Point3D p) {
		return new Vector2D(p.x, p.y);
	}
	
//	TODO: Add Javadocs!
	public static Vector2D directionYZ(final Point3D p) {
		return new Vector2D(p.y, p.z);
	}
	
//	TODO: Add Javadocs!
	public static Vector2D directionZX(final Point3D p) {
		return new Vector2D(p.z, p.x);
	}
	
//	TODO: Add Javadocs!
	public static Vector2D subtract(final Vector2D vLHS, final Vector2D vRHS) {
		return new Vector2D(vLHS.x - vRHS.x, vLHS.y - vRHS.y);
	}
	
//	TODO: Add Javadocs!
	public static double crossProduct(final Vector2D vLHS, final Vector2D vRHS) {
		return vLHS.x * vRHS.y - vLHS.y * vRHS.x;
	}
}