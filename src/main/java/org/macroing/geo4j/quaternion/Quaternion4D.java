/**
 * Copyright 2022 - 2023 J&#246;rgen Lundgren
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
package org.macroing.geo4j.quaternion;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.Objects;

import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.matrix.Matrix44D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.visitor.Node;

/**
 * A {@code Quaternion4D} represents a quaternion with four {@code double}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Quaternion4D implements Node {
	/**
	 * The W-component of this {@code Quaternion4D} instance.
	 */
	public final double w;
	
	/**
	 * The X-component of this {@code Quaternion4D} instance.
	 */
	public final double x;
	
	/**
	 * The Y-component of this {@code Quaternion4D} instance.
	 */
	public final double y;
	
	/**
	 * The Z-component of this {@code Quaternion4D} instance.
	 */
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Quaternion4D} instance given the component values {@code 0.0D}, {@code 0.0D}, {@code 0.0D} and {@code 1.0D}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Quaternion4D(0.0D, 0.0D, 0.0D);
	 * }
	 * </pre>
	 */
	public Quaternion4D() {
		this(0.0D, 0.0D, 0.0D);
	}
	
	/**
	 * Constructs a new {@code Quaternion4D} instance given the component values {@code v.x}, {@code v.y}, {@code v.z} and {@code 1.0D}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Quaternion4D(v.x, v.y, v.z);
	 * }
	 * </pre>
	 * 
	 * @param v a {@link Vector3D} instance
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public Quaternion4D(final Vector3D v) {
		this(v.x, v.y, v.z);
	}
	
	/**
	 * Constructs a new {@code Quaternion4D} instance given the component values {@code x}, {@code y}, {@code z} and {@code 1.0D}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Quaternion4D(x, y, z, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @param x the value of the X-component
	 * @param y the value of the Y-component
	 * @param z the value of the Z-component
	 */
	public Quaternion4D(final double x, final double y, final double z) {
		this(x, y, z, 1.0D);
	}
	
	/**
	 * Constructs a new {@code Quaternion4D} instance given the component values {@code x}, {@code y}, {@code z} and {@code w}.
	 * 
	 * @param x the value of the X-component
	 * @param y the value of the Y-component
	 * @param z the value of the Z-component
	 * @param w the value of the W-component
	 */
	public Quaternion4D(final double x, final double y, final double z, final double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Matrix44D} representation of this {@code Quaternion4D} instance.
	 * 
	 * @return a {@code Matrix44D} representation of this {@code Quaternion4D} instance
	 */
	public Matrix44D toMatrix() {
		final Quaternion4D q = Quaternion4D.normalize(this);
		
		final Vector3D u = new Vector3D(1.0D - 2.0D * (q.y * q.y + q.z * q.z),        2.0D * (q.x * q.y - q.w * q.z),        2.0D * (q.x * q.z + q.w * q.y));
		final Vector3D v = new Vector3D(       2.0D * (q.x * q.y + q.w * q.z), 1.0D - 2.0D * (q.x * q.x + q.z * q.z),        2.0D * (q.y * q.z - q.w * q.x));
		final Vector3D w = new Vector3D(       2.0D * (q.x * q.z - q.w * q.y),        2.0D * (q.y * q.z + q.w * q.x), 1.0D - 2.0D * (q.x * q.x + q.y * q.y));
		
		return Matrix44D.rotate(w, v, u);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Quaternion4D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Quaternion4D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Quaternion4D(%s, %s, %s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y), Strings.toNonScientificNotationJava(this.z), Strings.toNonScientificNotationJava(this.w));
	}
	
	/**
	 * Compares {@code object} to this {@code Quaternion4D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Quaternion4D}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Quaternion4D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Quaternion4D}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Quaternion4D)) {
			return false;
		} else {
			return equals(Quaternion4D.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code q} to this {@code Quaternion4D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code q} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param q the {@code Quaternion4D} instance to compare to this {@code Quaternion4D} instance for equality
	 * @return {@code true} if, and only if, {@code q} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Quaternion4D q) {
		if(q == this) {
			return true;
		} else if(q == null) {
			return false;
		} else if(!Doubles.equals(this.w, q.w)) {
			return false;
		} else if(!Doubles.equals(this.x, q.x)) {
			return false;
		} else if(!Doubles.equals(this.y, q.y)) {
			return false;
		} else if(!Doubles.equals(this.z, q.z)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code Quaternion4D} instance.
	 * 
	 * @return the length of this {@code Quaternion4D} instance
	 */
	public double length() {
		return Doubles.sqrt(lengthSquared());
	}
	
	/**
	 * Returns the squared length of this {@code Quaternion4D} instance.
	 * 
	 * @return the squared length of this {@code Quaternion4D} instance
	 */
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	/**
	 * Returns a hash code for this {@code Quaternion4D} instance.
	 * 
	 * @return a hash code for this {@code Quaternion4D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.w), Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	/**
	 * Writes this {@code Quaternion4D} instance to {@code dataOutput}.
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
			dataOutput.writeDouble(this.x);
			dataOutput.writeDouble(this.y);
			dataOutput.writeDouble(this.z);
			dataOutput.writeDouble(this.w);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds the component values of {@code qRHS} to the component values of {@code qLHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the addition.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Quaternion addition is performed componentwise.
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
	public static Quaternion4D add(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return new Quaternion4D(qLHS.x + qRHS.x, qLHS.y + qRHS.y, qLHS.z + qRHS.z, qLHS.w + qRHS.w);
	}
	
	/**
	 * Conjugates the component values of {@code q}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the conjugation.
	 * <p>
	 * If {@code q} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param q a {@code Quaternion4D} instance
	 * @return a {@code Quaternion4D} instance with the result of the conjugation
	 * @throws NullPointerException thrown if, and only if, {@code q} is {@code null}
	 */
	public static Quaternion4D conjugate(final Quaternion4D q) {
		return new Quaternion4D(-q.x, -q.y, -q.z, +q.w);
	}
	
	/**
	 * Divides the component values of {@code q} with {@code s}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the division.
	 * <p>
	 * If {@code q} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Quaternion division is performed componentwise.
	 * 
	 * @param q the {@code Quaternion4D} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the division
	 * @throws NullPointerException thrown if, and only if, {@code q} is {@code null}
	 */
	public static Quaternion4D divide(final Quaternion4D q, final double s) {
		return new Quaternion4D(q.x / s, q.y / s, q.z / s, q.w / s);
	}
	
	/**
	 * Returns a {@code Quaternion4D} representation of {@code m}.
	 * <p>
	 * If {@code m} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@link Matrix44D} instance
	 * @return a {@code Quaternion4D} representation of {@code m}
	 * @throws NullPointerException thrown if, and only if, {@code m} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D fromMatrix(final Matrix44D m) {
		if(m.element11 + m.element22 + m.element33 > 0.0D) {
			final double scalar = 0.5D / Doubles.sqrt(m.element11 + m.element22 + m.element33 + 1.0D);
			
			return normalize(new Quaternion4D((m.element23 - m.element32) * scalar, (m.element31 - m.element13) * scalar, (m.element12 - m.element21) * scalar, 0.25D / scalar));
		} else if(m.element11 > m.element22 && m.element11 > m.element33) {
			final double scalar = 2.0D * Doubles.sqrt(1.0D + m.element11 - m.element22 - m.element23);
			
			return normalize(new Quaternion4D(0.25D * scalar, (m.element21 + m.element12) / scalar, (m.element31 + m.element13) / scalar, (m.element23 - m.element32) / scalar));
		} else if(m.element22 > m.element33) {
			final double scalar = 2.0D * Doubles.sqrt(1.0D + m.element22 - m.element11 - m.element33);
			
			return normalize(new Quaternion4D((m.element21 + m.element12) / scalar, 0.25D * scalar, (m.element32 + m.element23) / scalar, (m.element31 - m.element13) / scalar));
		} else {
			final double scalar = 2.0F * Doubles.sqrt(1.0D + m.element33 - m.element11 - m.element22);
			
			return normalize(new Quaternion4D((m.element31 + m.element13) / scalar, (m.element23 + m.element32) / scalar, 0.25D * scalar, (m.element12 - m.element21) / scalar));
		}
	}
	
	/**
	 * Performs a normalized linear interpolation between {@code qLHS} and {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the operation.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Quaternion4D.lerp(qLHS, qRHS, 0.5D);
	 * }
	 * </pre>
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D lerp(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return lerp(qLHS, qRHS, 0.5D);
	}
	
	/**
	 * Performs a normalized linear interpolation between {@code qLHS} and {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the operation.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Quaternion4D.lerp(qLHS, qRHS, t, false);
	 * }
	 * </pre>
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @param t the factor
	 * @return a {@code Quaternion4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D lerp(final Quaternion4D qLHS, final Quaternion4D qRHS, final double t) {
		return lerp(qLHS, qRHS, t, false);
	}
	
	/**
	 * Performs a normalized linear interpolation between {@code qLHS} and {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the operation.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @param t the factor
	 * @param isInterpolatingShortest {@code true} if, and only if, the shortest interpolation should be used, {@code false} otherwise
	 * @return a {@code Quaternion4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D lerp(final Quaternion4D qLHS, final Quaternion4D qRHS, final double t, final boolean isInterpolatingShortest) {
		return normalize(add(multiply(subtract(isInterpolatingShortest && dotProduct(qLHS, qRHS) < 0.0D ? negate(qRHS) : qRHS, qLHS), t), qLHS));
	}
	
	/**
	 * Multiplies the component values of {@code qLHS} with the component values of {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the multiplication.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Quaternion multiplication is performed componentwise.
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
	public static Quaternion4D multiply(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return new Quaternion4D(qLHS.x * qRHS.w + qLHS.w * qRHS.x + qLHS.y * qRHS.z - qLHS.z * qRHS.y, qLHS.y * qRHS.w + qLHS.w * qRHS.y + qLHS.z * qRHS.x - qLHS.x * qRHS.z, qLHS.z * qRHS.w + qLHS.w * qRHS.z + qLHS.x * qRHS.y - qLHS.y * qRHS.x, qLHS.w * qRHS.w - qLHS.x * qRHS.x - qLHS.y * qRHS.y - qLHS.z * qRHS.z);
	}
	
	/**
	 * Multiplies the component values of {@code q} with the component values of {@code v}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the multiplication.
	 * <p>
	 * If either {@code q} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Quaternion multiplication is performed componentwise.
	 * 
	 * @param q the {@code Quaternion4D} instance on the left-hand side
	 * @param v the {@link Vector3D} instance on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, either {@code q} or {@code v} are {@code null}
	 */
	public static Quaternion4D multiply(final Quaternion4D q, final Vector3D v) {
		return new Quaternion4D(+q.w * v.x + q.y * v.z - q.z * v.y, +q.w * v.y + q.z * v.x - q.x * v.z, +q.w * v.z + q.x * v.y - q.y * v.x, -q.x * v.x - q.y * v.y - q.z * v.z);
	}
	
	/**
	 * Multiplies the component values of {@code q} with {@code s}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the multiplication.
	 * <p>
	 * If {@code q} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Quaternion multiplication is performed componentwise.
	 * 
	 * @param q the {@code Quaternion4D} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code q} is {@code null}
	 */
	public static Quaternion4D multiply(final Quaternion4D q, final double s) {
		return new Quaternion4D(q.x * s, q.y * s, q.z * s, q.w * s);
	}
	
	/**
	 * Negates the component values of {@code q}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the negation.
	 * <p>
	 * If {@code q} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param q a {@code Quaternion4D} instance
	 * @return a {@code Quaternion4D} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code q} is {@code null}
	 */
	public static Quaternion4D negate(final Quaternion4D q) {
		return new Quaternion4D(-q.x, -q.y, -q.z, -q.w);
	}
	
	/**
	 * Normalizes the component values of {@code q}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the normalization.
	 * <p>
	 * If {@code q} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param q a {@code Quaternion4D} instance
	 * @return a {@code Quaternion4D} instance with the result of the normalization
	 * @throws NullPointerException thrown if, and only if, {@code q} is {@code null}
	 */
	public static Quaternion4D normalize(final Quaternion4D q) {
		final double length = q.length();
		
		final boolean isLengthGTEThreshold = length >= Doubles.NEXT_DOWN_1_3;
		final boolean isLengthLTEThreshold = length <= Doubles.NEXT_UP_1_1;
		
		if(isLengthGTEThreshold && isLengthLTEThreshold) {
			return q;
		}
		
		return divide(q, length);
	}
	
	/**
	 * Returns a {@code Quaternion4D} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Quaternion4D} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Quaternion4D read(final DataInput dataInput) {
		try {
			return new Quaternion4D(dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Performs a spherical linear interpolation between {@code qLHS} and {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the operation.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Quaternion4D.slerp(qLHS, qRHS, 0.5D);
	 * }
	 * </pre>
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D slerp(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return slerp(qLHS, qRHS, 0.5D);
	}
	
	/**
	 * Performs a spherical linear interpolation between {@code qLHS} and {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the operation.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Quaternion4D.slerp(qLHS, qRHS, t, false);
	 * }
	 * </pre>
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @param t the factor
	 * @return a {@code Quaternion4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D slerp(final Quaternion4D qLHS, final Quaternion4D qRHS, final double t) {
		return slerp(qLHS, qRHS, t, false);
	}
	
	/**
	 * Performs a spherical linear interpolation between {@code qLHS} and {@code qRHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the operation.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @param t the factor
	 * @param isInterpolatingShortest {@code true} if, and only if, the shortest interpolation should be used, {@code false} otherwise
	 * @return a {@code Quaternion4D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Quaternion4D slerp(final Quaternion4D qLHS, final Quaternion4D qRHS, final double t, final boolean isInterpolatingShortest) {
		final double cos = dotProduct(qLHS, qRHS);
		
		final double x = isInterpolatingShortest && cos < 0.0D ? -cos : cos;
		final double y = Doubles.sqrt(1.0D - x * x);
		
		final Quaternion4D quaternion1 = isInterpolatingShortest && cos < 0.0D ? negate(qRHS) : qRHS;
		
		if(Doubles.abs(x) >= 1.0D - 1000.0D) {
			return lerp(qLHS, quaternion1, t);
		}
		
		final double theta = Doubles.atan2(y, x);
		
		return add(multiply(qLHS, Doubles.sin((1.0D - t) * theta) / y), multiply(quaternion1, Doubles.sin(t * theta) / y));
	}
	
	/**
	 * Subtracts the component values of {@code qRHS} from the component values of {@code qLHS}.
	 * <p>
	 * Returns a {@code Quaternion4D} instance with the result of the subtraction.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Quaternion subtraction is performed componentwise.
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @return a {@code Quaternion4D} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
	public static Quaternion4D subtract(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return new Quaternion4D(qLHS.x - qRHS.x, qLHS.y - qRHS.y, qLHS.z - qRHS.z, qLHS.w - qRHS.w);
	}
	
	/**
	 * Returns the dot product of {@code qLHS} and {@code qRHS}.
	 * <p>
	 * If either {@code qLHS} or {@code qRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param qLHS the {@code Quaternion4D} instance on the left-hand side
	 * @param qRHS the {@code Quaternion4D} instance on the right-hand side
	 * @return the dot product of {@code qLHS} and {@code qRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code qLHS} or {@code qRHS} are {@code null}
	 */
	public static double dotProduct(final Quaternion4D qLHS, final Quaternion4D qRHS) {
		return qLHS.x * qRHS.x + qLHS.y * qRHS.y + qLHS.z * qRHS.z + qLHS.w * qRHS.w;
	}
}