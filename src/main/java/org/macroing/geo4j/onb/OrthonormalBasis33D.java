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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.matrix.Matrix44D;

/**
 * An {@code OrthonormalBasis33D} represents an orthonormal basis constructed by three {@link Vector3D} instances.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class OrthonormalBasis33D {
	private static final Map<OrthonormalBasis33D, OrthonormalBasis33D> CACHE = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The U-direction.
	 */
	public final Vector3D u;
	
	/**
	 * The V-direction.
	 */
	public final Vector3D v;
	
	/**
	 * The W-direction.
	 */
	public final Vector3D w;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code OrthonormalBasis33D} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new OrthonormalBasis33D(Vector3D.z(), Vector3D.y(), Vector3D.x());
	 * }
	 * </pre>
	 */
	public OrthonormalBasis33D() {
		this.w = Vector3D.z();
		this.v = Vector3D.y();
		this.u = Vector3D.x();
	}
	
	/**
	 * Constructs a new {@code OrthonormalBasis33D} instance given {@code w}.
	 * <p>
	 * If {@code w} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * <strong>Note:</strong> This constructor does normalize {@code w}.
	 * 
	 * @param w a {@link Vector3D} pointing in the W-direction
	 * @throws NullPointerException thrown if, and only if, {@code w} is {@code null}
	 */
	public OrthonormalBasis33D(final Vector3D w) {
		this.w = Vector3D.normalize(w);
		this.u = Vector3D.orthogonal(w);
		this.v = Vector3D.crossProduct(this.w, this.u);
	}
	
	/**
	 * Constructs a new {@code OrthonormalBasis33D} instance given {@code w} and {@code v}.
	 * <p>
	 * If either {@code w} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * <strong>Note:</strong> This constructor does normalize both {@code w} and {@code v}.
	 * 
	 * @param w a {@link Vector3D} pointing in the W-direction
	 * @param v a {@code Vector3D} pointing in the V-direction to some degree
	 * @throws NullPointerException thrown if, and only if, either {@code w} or {@code v} are {@code null}
	 */
	public OrthonormalBasis33D(final Vector3D w, final Vector3D v) {
		this.w = Vector3D.normalize(w);
		this.u = Vector3D.normalize(Vector3D.crossProduct(Vector3D.normalize(v), this.w));
		this.v = Vector3D.crossProduct(this.w, this.u);
	}
	
	/**
	 * Constructs a new {@code OrthonormalBasis33D} instance given {@code w}, {@code v} and {@code u}.
	 * <p>
	 * If either {@code w}, {@code v} or {@code u} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * <strong>Note:</strong> This constructor does not normalize {@code w}, {@code v} or {@code u}.
	 * 
	 * @param w a {@link Vector3D} pointing in the W-direction
	 * @param v a {@code Vector3D} pointing in the V-direction
	 * @param u a {@code Vector3D} pointing in the U-direction
	 * @throws NullPointerException thrown if, and only if, either {@code w}, {@code v} or {@code u} are {@code null}
	 */
	public OrthonormalBasis33D(final Vector3D w, final Vector3D v, final Vector3D u) {
		this.w = Objects.requireNonNull(w, "w == null");
		this.v = Objects.requireNonNull(v, "v == null");
		this.u = Objects.requireNonNull(u, "u == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Matrix44D} representation of this {@code OrthonormalBasis33D} instance.
	 * 
	 * @return a {@code Matrix44D} representation of this {@code OrthonormalBasis33D} instance
	 */
	public Matrix44D toMatrix() {
		return Matrix44D.rotate(this.w, this.v, this.u);
	}
	
	/**
	 * Transforms this {@code OrthonormalBasis33D} instance with {@code m}.
	 * <p>
	 * Returns an {@code OrthonormalBasis33D} instance with the result of the transformation.
	 * <p>
	 * If {@code m} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@link Matrix44D} instance
	 * @return an {@code OrthonormalBasis33D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code m} is {@code null}
	 */
	public OrthonormalBasis33D transform(final Matrix44D m) {
		final Vector3D u = Vector3D.normalize(m.transform(this.u));
		final Vector3D v = Vector3D.normalize(m.transform(this.v));
		final Vector3D w = Vector3D.normalize(m.transform(this.w));
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
	/**
	 * Transforms this {@code OrthonormalBasis33D} instance with {@code m} in transpose order.
	 * <p>
	 * Returns an {@code OrthonormalBasis33D} instance with the result of the transformation.
	 * <p>
	 * If {@code m} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@link Matrix44D} instance
	 * @return an {@code OrthonormalBasis33D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code m} is {@code null}
	 */
	public OrthonormalBasis33D transformTranspose(final Matrix44D m) {
		final Vector3D u = Vector3D.normalize(m.transformTranspose(this.u));
		final Vector3D v = Vector3D.normalize(m.transformTranspose(this.v));
		final Vector3D w = Vector3D.normalize(m.transformTranspose(this.w));
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code OrthonormalBasis33D} instance.
	 * 
	 * @return a {@code String} representation of this {@code OrthonormalBasis33D} instance
	 */
	@Override
	public String toString() {
		return String.format("new OrthonormalBasis33D(%s, %s, %s)", this.w, this.v, this.u);
	}
	
	/**
	 * Transforms {@code v} with this {@code OrthonormalBasis33D} instance.
	 * <p>
	 * Returns a {@link Vector3D} instance with the result of the transformation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public Vector3D transform(final Vector3D v) {
		final double x = v.x * this.u.x + v.y * this.v.x + v.z * this.w.x;
		final double y = v.x * this.u.y + v.y * this.v.y + v.z * this.w.y;
		final double z = v.x * this.u.z + v.y * this.v.z + v.z * this.w.z;
		
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Transforms {@code v} with this {@code OrthonormalBasis33D} instance and normalizes the result.
	 * <p>
	 * Returns a {@link Vector3D} instance with the result of the transformation and normalization.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation and normalization
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public Vector3D transformNormalize(final Vector3D v) {
		return Vector3D.normalize(transform(v));
	}
	
	/**
	 * Transforms {@code v} with this {@code OrthonormalBasis33D} instance in reverse order.
	 * <p>
	 * Returns a {@link Vector3D} instance with the result of the transformation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public Vector3D transformReverse(final Vector3D v) {
		final double x = Vector3D.dotProduct(v, this.u);
		final double y = Vector3D.dotProduct(v, this.v);
		final double z = Vector3D.dotProduct(v, this.w);
		
		return new Vector3D(x, y, z);
	}
	
	/**
	 * Transforms {@code v} with this {@code OrthonormalBasis33D} instance in reverse order and normalizes the result.
	 * <p>
	 * Returns a {@link Vector3D} instance with the result of the transformation and normalization.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation and normalization
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public Vector3D transformReverseNormalize(final Vector3D v) {
		return Vector3D.normalize(transformReverse(v));
	}
	
	/**
	 * Compares {@code object} to this {@code OrthonormalBasis33D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code OrthonormalBasis33D}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code OrthonormalBasis33D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code OrthonormalBasis33D}, and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Compares {@code o} to this {@code OrthonormalBasis33D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code o} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param o the {@code OrthonormalBasis33D} instance to compare to this {@code OrthonormalBasis33D} instance for equality
	 * @return {@code true} if, and only if, {@code o} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
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
	
	/**
	 * Returns {@code true} if, and only if, all {@link Vector3D} instances in this {@code OrthonormalBasis33D} instance are orthogonal, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all {@code Vector3D} instances in this {@code OrthonormalBasis33D} instance are orthogonal, {@code false} otherwise
	 */
	public boolean hasOrthogonalVectors() {
		final boolean orthogonalUV = Vector3D.orthogonal(this.u, this.v);
		final boolean orthogonalVW = Vector3D.orthogonal(this.v, this.w);
		final boolean orthogonalWU = Vector3D.orthogonal(this.w, this.u);
		
		return orthogonalUV && orthogonalVW && orthogonalWU;
	}
	
	/**
	 * Returns {@code true} if, and only if, all {@link Vector3D} instances in this {@code OrthonormalBasis33D} instance are unit vectors, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all {@code Vector3D} instances in this {@code OrthonormalBasis33D} instance are unit vectors, {@code false} otherwise
	 */
	public boolean hasUnitVectors() {
		final boolean isUnitVectorU = this.u.isUnitVector();
		final boolean isUnitVectorV = this.v.isUnitVector();
		final boolean isUnitVectorW = this.w.isUnitVector();
		
		return isUnitVectorU && isUnitVectorV && isUnitVectorW;
	}
	
	/**
	 * Returns {@code true} if, and only if, all {@link Vector3D} instances in this {@code OrthonormalBasis33D} instance are unit vectors and are orthogonal, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all {@code Vector3D} instances in this {@code OrthonormalBasis33D} instance are unit vectors and are orthogonal, {@code false} otherwise
	 */
	public boolean isOrthonormal() {
		return hasUnitVectors() && hasOrthogonalVectors();
	}
	
	/**
	 * Returns a hash code for this {@code OrthonormalBasis33D} instance.
	 * 
	 * @return a hash code for this {@code OrthonormalBasis33D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.u, this.v, this.w);
	}
	
	/**
	 * Writes this {@code OrthonormalBasis33D} instance to {@code dataOutput}.
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
		this.w.write(dataOutput);
		this.v.write(dataOutput);
		this.u.write(dataOutput);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a cached version of {@code o}.
	 * <p>
	 * If {@code o} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param o an {@code OrthonormalBasis33D} instance
	 * @return a cached version of {@code o}
	 * @throws NullPointerException thrown if, and only if, {@code o} is {@code null}
	 */
	public static OrthonormalBasis33D getCached(final OrthonormalBasis33D o) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(o, "o == null"), key -> new OrthonormalBasis33D(Vector3D.getCached(o.w), Vector3D.getCached(o.v), Vector3D.getCached(o.u)));
	}
	
	/**
	 * Flips the directions of {@code o}.
	 * <p>
	 * Returns an {@code OrthonormalBasis33D} with the directions flipped.
	 * <p>
	 * If {@code o} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param o the {@code OrthonormalBasis33D} instance to flip the directions for
	 * @return an {@code OrthonormalBasis33D} with the directions flipped
	 * @throws NullPointerException thrown if, and only if, {@code o} is {@code null}
	 */
	public static OrthonormalBasis33D flip(final OrthonormalBasis33D o) {
		return new OrthonormalBasis33D(Vector3D.negate(o.w), Vector3D.negate(o.v), Vector3D.negate(o.u));
	}
	
	/**
	 * Flips the U-direction of {@code o}.
	 * <p>
	 * Returns an {@code OrthonormalBasis33D} with the U-direction flipped.
	 * <p>
	 * If {@code o} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param o the {@code OrthonormalBasis33D} instance to flip the U-direction for
	 * @return an {@code OrthonormalBasis33D} with the U-direction flipped
	 * @throws NullPointerException thrown if, and only if, {@code o} is {@code null}
	 */
	public static OrthonormalBasis33D flipU(final OrthonormalBasis33D o) {
		return new OrthonormalBasis33D(o.w, o.v, Vector3D.negate(o.u));
	}
	
	/**
	 * Flips the V-direction of {@code o}.
	 * <p>
	 * Returns an {@code OrthonormalBasis33D} with the V-direction flipped.
	 * <p>
	 * If {@code o} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param o the {@code OrthonormalBasis33D} instance to flip the V-direction for
	 * @return an {@code OrthonormalBasis33D} with the V-direction flipped
	 * @throws NullPointerException thrown if, and only if, {@code o} is {@code null}
	 */
	public static OrthonormalBasis33D flipV(final OrthonormalBasis33D o) {
		return new OrthonormalBasis33D(o.w, Vector3D.negate(o.v), o.u);
	}
	
	/**
	 * Flips the W-direction of {@code o}.
	 * <p>
	 * Returns an {@code OrthonormalBasis33D} with the W-direction flipped.
	 * <p>
	 * If {@code o} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param o the {@code OrthonormalBasis33D} instance to flip the W-direction for
	 * @return an {@code OrthonormalBasis33D} with the W-direction flipped
	 * @throws NullPointerException thrown if, and only if, {@code o} is {@code null}
	 */
	public static OrthonormalBasis33D flipW(final OrthonormalBasis33D o) {
		return new OrthonormalBasis33D(Vector3D.negate(o.w), o.v, o.u);
	}
	
	/**
	 * Returns an {@code OrthonormalBasis33D} representation of {@code m}.
	 * <p>
	 * If {@code m} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@link Matrix44D} instance
	 * @return an {@code OrthonormalBasis33D} representation of {@code m}
	 * @throws NullPointerException thrown if, and only if, {@code m} is {@code null}
	 */
	public static OrthonormalBasis33D fromMatrix(final Matrix44D m) {
		final Vector3D u = new Vector3D(m.element11, m.element21, m.element31);
		final Vector3D v = new Vector3D(m.element12, m.element22, m.element32);
		final Vector3D w = new Vector3D(m.element13, m.element23, m.element33);
		
		return new OrthonormalBasis33D(w, v, u);
	}
	
	/**
	 * Returns an {@code OrthonormalBasis33D} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return an {@code OrthonormalBasis33D} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static OrthonormalBasis33D read(final DataInput dataInput) {
		return new OrthonormalBasis33D(Vector3D.read(dataInput), Vector3D.read(dataInput), Vector3D.read(dataInput));
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