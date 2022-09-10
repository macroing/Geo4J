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
package org.macroing.geo4j.onb;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.geo4j.vector.Vector3D;

//TODO: Add Javadocs!
public final class OrthonormalBasis33D {
//	TODO: Add Javadocs!
	public final Vector3D u;
	
//	TODO: Add Javadocs!
	public final Vector3D v;
	
//	TODO: Add Javadocs!
	public final Vector3D w;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public OrthonormalBasis33D() {
		this.w = Vector3D.z();
		this.v = Vector3D.y();
		this.u = Vector3D.x();
	}
	
//	TODO: Add Javadocs!
	public OrthonormalBasis33D(final Vector3D w) {
		this.w = Vector3D.normalize(w);
		this.u = Vector3D.orthogonal(w);
		this.v = Vector3D.crossProduct(this.w, this.u);
	}
	
//	TODO: Add Javadocs!
	public OrthonormalBasis33D(final Vector3D w, final Vector3D v) {
		this.w = Vector3D.normalize(w);
		this.u = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(v), this.w));
		this.v = Vector3D.crossProduct(this.w, this.u);
	}
	
//	TODO: Add Javadocs!
	public OrthonormalBasis33D(final Vector3D w, final Vector3D v, final Vector3D u) {
		this.w = Objects.requireNonNull(w, "w == null");
		this.v = Objects.requireNonNull(v, "v == null");
		this.u = Objects.requireNonNull(u, "u == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("new OrthonormalBasis33D(%s, %s, %s)", this.w, this.v, this.u);
	}
	
	/**
	 * Transforms the {@code Vector3D} {@code v} with the {@link OrthonormalBasis33D} {@code o}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the transformation.
	 * <p>
	 * If either {@code v} or {@code o} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @param o an {@code OrthonormalBasis33D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, either {@code v} or {@code o} are {@code null}
	 */
//	TODO: Add unit tests!
	public Vector3D transform(final Vector3D v) {
		final double x = v.x * this.u.x + v.y * this.v.x + v.z * this.w.x;
		final double y = v.x * this.u.y + v.y * this.v.y + v.z * this.w.y;
		final double z = v.x * this.u.z + v.y * this.v.z + v.z * this.w.z;
		
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Transforms the {@code Vector3D} {@code v} with the {@link OrthonormalBasis33D} {@code o} and normalizes the result.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the transformation and normalization.
	 * <p>
	 * If either {@code v} or {@code o} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @param o an {@code OrthonormalBasis33D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation and normalization
	 * @throws NullPointerException thrown if, and only if, either {@code v} or {@code o} are {@code null}
	 */
//	TODO: Add unit tests!
	public Vector3D transformNormalize(final Vector3D v) {
		return Vector3D.normalize(transform(v));
	}
	
	/**
	 * Transforms the {@code Vector3D} {@code v} with the {@link OrthonormalBasis33D} {@code o} in reverse order.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the transformation.
	 * <p>
	 * If either {@code v} or {@code o} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @param o an {@code OrthonormalBasis33D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, either {@code v} or {@code o} are {@code null}
	 */
//	TODO: Add unit tests!
	public Vector3D transformReverse(final Vector3D v) {
		final double x = Vector3D.dotProduct(v, this.u);
		final double y = Vector3D.dotProduct(v, this.v);
		final double z = Vector3D.dotProduct(v, this.w);
		
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Transforms the {@code Vector3D} {@code v} with the {@link OrthonormalBasis33D} {@code o} in reverse order and normalizes the result.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the transformation and normalization.
	 * <p>
	 * If either {@code v} or {@code o} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @param o an {@code OrthonormalBasis33D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation and normalization
	 * @throws NullPointerException thrown if, and only if, either {@code v} or {@code o} are {@code null}
	 */
//	TODO: Add unit tests!
	public Vector3D transformReverseNormalize(final Vector3D v) {
		return Vector3D.normalize(transformReverse(v));
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof OrthonormalBasis33D)) {
			return false;
		} else {
			return equals(OrthonormalBasis33D.class.cast(object));
		}
	}
	
//	TODO: Add Javadocs!
	public boolean equals(final OrthonormalBasis33D o) {
		if(o == this) {
			return true;
		} else if(o == null) {
			return false;
		} else if(!Objects.equals(this.u, o.u)) {
			return false;
		} else if(!Objects.equals(this.v, o.v)) {
			return false;
		} else if(!Objects.equals(this.w, o.w)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.u, this.v, this.w);
	}
}