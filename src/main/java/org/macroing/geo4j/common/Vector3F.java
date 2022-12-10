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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Strings;
import org.macroing.java.util.visitor.Node;

/**
 * A {@code Vector3F} represents a vector with three {@code float}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Vector3F implements Node {
	/**
	 * A {@code Vector3F} instance given the component values {@code Floats.NaN}, {@code Floats.NaN} and {@code Floats.NaN}.
	 */
	public static final Vector3F NaN = new Vector3F(Floats.NaN, Floats.NaN, Floats.NaN);
	
	/**
	 * A {@code Vector3F} instance given the component values {@code 0.0F}, {@code 0.0F} and {@code 0.0F}.
	 */
	public static final Vector3F ZERO = new Vector3F(0.0F, 0.0F, 0.0F);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Vector3F, Vector3F> CACHE = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The X-component of this {@code Vector3F} instance.
	 */
	public final float x;
	
	/**
	 * The Y-component of this {@code Vector3F} instance.
	 */
	public final float y;
	
	/**
	 * The Z-component of this {@code Vector3F} instance.
	 */
	public final float z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Vector3F} instance given the component values {@code 0.0F}, {@code 0.0F} and {@code 0.0F}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector3F(0.0F, 0.0F, 0.0F);
	 * }
	 * </pre>
	 */
	public Vector3F() {
		this(0.0F, 0.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code Vector3F} instance given the component values {@code p.x}, {@code p.y} and {@code p.z}.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector3F(p.x, p.y, p.z);
	 * }
	 * </pre>
	 * 
	 * @param p a {@link Point3F} instance
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public Vector3F(final Point3F p) {
		this(p.x, p.y, p.z);
	}
	
	/**
	 * Constructs a new {@code Vector3F} instance given the component values {@code component}, {@code component} and {@code component}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector3F(component, component, component);
	 * }
	 * </pre>
	 * 
	 * @param component the value of all components
	 */
	public Vector3F(final float component) {
		this(component, component, component);
	}
	
	/**
	 * Constructs a new {@code Vector3F} instance given the component values {@code x}, {@code y} and {@code z}.
	 * 
	 * @param x the X-component of this {@code Vector3F} instance
	 * @param y the Y-component of this {@code Vector3F} instance
	 * @param z the Z-component of this {@code Vector3F} instance
	 */
	public Vector3F(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Vector3F} instance.
	 * 
	 * @return a {@code String} representation of this {@code Vector3F} instance
	 */
	@Override
	public String toString() {
		return String.format("new Vector3F(%s, %s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y), Strings.toNonScientificNotationJava(this.z));
	}
	
	/**
	 * Compares {@code object} to this {@code Vector3F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Vector3F} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} instance to compare to this {@code Vector3F} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Vector3F} and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector3F)) {
			return false;
		} else {
			return equals(Vector3F.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code v} to this {@code Vector3F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code v} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param v the {@code Vector3F} instance to compare to this {@code Vector3F} instance for equality
	 * @return {@code true} if, and only if, {@code v} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Vector3F v) {
		if(v == this) {
			return true;
		} else if(v == null) {
			return false;
		} else if(!Floats.equals(this.x, v.x)) {
			return false;
		} else if(!Floats.equals(this.y, v.y)) {
			return false;
		} else if(!Floats.equals(this.z, v.z)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Vector3F} instance has at least one component with a value that is infinite, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Vector3F} instance has at least one component with a value that is infinite, {@code false} otherwise
	 */
	public boolean hasInfinites() {
		return Floats.isInfinite(this.x) || Floats.isInfinite(this.y) || Floats.isInfinite(this.z);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Vector3F} instance has at least one component with a value that is NaN (Not a Number), {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Vector3F} instance has at least one component with a value that is NaN (Not a Number), {@code false} otherwise
	 */
	public boolean hasNaNs() {
		return Floats.isNaN(this.x) || Floats.isNaN(this.y) || Floats.isNaN(this.z);
	}
	
	/**
	 * Returns {@code true} if, and only if, all components in this {@code Vector3F} instance have values that are finite, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all components in this {@code Vector3F} instance have values that are finite, {@code false} otherwise
	 */
	public boolean isFinite() {
		return !hasInfinites() && !hasNaNs();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Vector3F} instance is a unit vector, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Vector3F} instance is a unit vector, {@code false} otherwise
	 */
	public boolean isUnitVector() {
		final float length = length();
		
		final boolean isLengthGTEThreshold = length >= Floats.NEXT_DOWN_1_3;
		final boolean isLengthLTEThreshold = length <= Floats.NEXT_UP_1_1;
		
		return isLengthGTEThreshold && isLengthLTEThreshold;
	}
	
	/**
	 * Returns {@code true} if, and only if, all components in this {@code Vector3F} instance have values that are zero, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all components in this {@code Vector3F} instance have values that are zero, {@code false} otherwise
	 */
	public boolean isZero() {
		return Floats.isZero(this.x) && Floats.isZero(this.y) && Floats.isZero(this.z);  
	}
	
	/**
	 * Returns the cosine of the angle phi.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float sinTheta = v.sinTheta();
	 * float cosPhi = Floats.isZero(sinTheta) ? 1.0F : Floats.saturate(v.x / sinTheta, -1.0F, 1.0F);
	 * 
	 * float sinThetaSimplified = Floats.sqrt(Floats.max(0.0F, 1.0F - v.z * v.z));
	 * float cosPhiSimplified = Floats.isZero(sinThetaSimplified) ? 1.0F : Floats.saturate(v.x / sinThetaSimplified, -1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle phi
	 */
	public float cosPhi() {
		final float sinTheta = sinTheta();
		final float cosPhi = Floats.isZero(sinTheta) ? 1.0F : Floats.saturate(this.x / sinTheta, -1.0F, 1.0F);
		
		return cosPhi;
	}
	
	/**
	 * Returns the cosine of the angle phi in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float cosPhiSquared = v.cosPhi() * v.cosPhi();
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle phi in squared form
	 */
	public float cosPhiSquared() {
		return Floats.pow2(cosPhi());
	}
	
	/**
	 * Returns the cosine of the angle theta.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float cosTheta = v.z;
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta
	 */
	public float cosTheta() {
		return this.z;
	}
	
	/**
	 * Returns the cosine of the angle theta in absolute form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float cosThetaAbs = Floats.abs(v.cosTheta());
	 * float cosThetaAbsSimplified = Floats.abs(v.z);
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta in absolute form
	 */
	public float cosThetaAbs() {
		return Floats.abs(cosTheta());
	}
	
	/**
	 * Returns the cosine of the angle theta in quartic form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float cosThetaQuartic = v.cosThetaSquared() * v.cosThetaSquared();
	 * float cosThetaQuarticSimplified = v.z * v.z * v.z * v.z;
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta in quartic form
	 */
	public float cosThetaQuartic() {
		return Floats.pow2(cosThetaSquared());
	}
	
	/**
	 * Returns the cosine of the angle theta in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float cosThetaSquared = v.cosTheta() * v.cosTheta();
	 * float cosThetaSquaredSimplified = v.z * v.z;
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta in squared form
	 */
	public float cosThetaSquared() {
		return Floats.pow2(cosTheta());
	}
	
	/**
	 * Returns the length of this {@code Vector3F} instance.
	 * 
	 * @return the length of this {@code Vector3F} instance
	 */
	public float length() {
		return Floats.sqrt(lengthSquared());
	}
	
	/**
	 * Returns the squared length of this {@code Vector3F} instance.
	 * 
	 * @return the squared length of this {@code Vector3F} instance
	 */
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	/**
	 * Returns the sine of the angle phi.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float sinTheta = v.sinTheta();
	 * float sinPhi = Floats.isZero(sinTheta) ? 0.0F : Floats.saturate(v.y / sinTheta, -1.0F, 1.0F);
	 * 
	 * float sinThetaSimplified = Floats.sqrt(Floats.max(0.0F, 1.0F - v.z * v.z));
	 * float sinPhiSimplified = Floats.isZero(sinThetaSimplified) ? 0.0F : Floats.saturate(v.y / sinThetaSimplified, -1.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle phi
	 */
	public float sinPhi() {
		final float sinTheta = sinTheta();
		final float sinPhi = Floats.isZero(sinTheta) ? 0.0F : Floats.saturate(this.y / sinTheta, -1.0F, 1.0F);
		
		return sinPhi;
	}
	
	/**
	 * Returns the sine of the angle phi in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float sinPhiSquared = v.sinPhi() * v.sinPhi();
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle phi in squared form
	 */
	public float sinPhiSquared() {
		return Floats.pow2(sinPhi());
	}
	
	/**
	 * Returns the sine of the angle theta.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float sinTheta = Floats.sqrt(v.sinThetaSquared());
	 * float sinThetaSimplified = Floats.sqrt(Floats.max(0.0F, 1.0F - v.z * v.z));
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle theta
	 */
	public float sinTheta() {
		return Floats.sqrt(sinThetaSquared());
	}
	
	/**
	 * Returns the sine of the angle theta in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float sinThetaSquared = Floats.max(0.0F, 1.0F - v.cosThetaSquared());
	 * float sinThetaSquaredSimplified = Floats.max(0.0F, 1.0F - v.z * v.z);
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle theta in squared form
	 */
	public float sinThetaSquared() {
		return Floats.max(0.0F, 1.0F - cosThetaSquared());
	}
	
	/**
	 * Returns the spherical phi angle.
	 * 
	 * @return the spherical phi angle
	 */
	public float sphericalPhi() {
		return Floats.addLessThan(Floats.atan2(this.y, this.x), 0.0F, Floats.PI_MULTIPLIED_BY_2);
	}
	
	/**
	 * Returns the spherical theta angle.
	 * 
	 * @return the spherical theta angle
	 */
	public float sphericalTheta() {
		return Floats.acos(Floats.saturate(this.z, -1.0F, 1.0F));
	}
	
	/**
	 * Returns the tangent of the angle theta.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float tanTheta = v.sinTheta() / v.cosTheta();
	 * float tanThetaSimplified = Floats.sqrt(Floats.max(0.0F, 1.0F - v.z * v.z)) / v.z;
	 * }
	 * </pre>
	 * 
	 * @return the tangent of the angle theta
	 */
	public float tanTheta() {
		return sinTheta() / cosTheta();
	}
	
	/**
	 * Returns the tangent of the angle theta in absolute form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float tanThetaAbs = Floats.abs(v.tanTheta());
	 * float tanThetaAbsSimplified = Floats.abs(Floats.sqrt(Floats.max(0.0F, 1.0F - v.z * v.z)) / v.z);
	 * }
	 * </pre>
	 * 
	 * @return the tangent of the angle theta in absolute form
	 */
	public float tanThetaAbs() {
		return Floats.abs(tanTheta());
	}
	
	/**
	 * Returns the tangent of the angle theta in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3F} instance called {@code v}:
	 * <pre>
	 * {@code
	 * float tanThetaSquared = v.sinThetaSquared() / v.cosThetaSquared();
	 * float tanThetaSquaredSimplified = Floats.max(0.0F, 1.0F - v.z * v.z) / (v.z * v.z);
	 * }
	 * </pre>
	 * 
	 * @return the tangent of the angle theta in squared form
	 */
	public float tanThetaSquared() {
		return sinThetaSquared() / cosThetaSquared();
	}
	
	/**
	 * Returns a {@code float[]} representation of this {@code Vector3F} instance.
	 * 
	 * @return a {@code float[]} representation of this {@code Vector3F} instance
	 */
	public float[] toArray() {
		return new float[] {this.x, this.y, this.z};
	}
	
	/**
	 * Returns a hash code for this {@code Vector3F} instance.
	 * 
	 * @return a hash code for this {@code Vector3F} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z));
	}
	
	/**
	 * Writes this {@code Vector3F} instance to {@code dataOutput}.
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
			dataOutput.writeFloat(this.z);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an optional {@code Vector3F} instance that represents the refraction of {@code direction} with regards to {@code normal}.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param direction the {@code Vector3F} instance that will be refracted with regards to {@code normal}
	 * @param normal the {@code Vector3F} instance that represents the normal of the surface
	 * @param eta the index of refraction
	 * @return an optional {@code Vector3F} instance that represents the refraction of {@code direction} with regards to {@code normal}
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Optional<Vector3F> refraction(final Vector3F direction, final Vector3F normal, final float eta) {
		final float cosThetaI = dotProduct(direction, normal);
		final float sinThetaISquared = Floats.max(0.0F, 1.0F - cosThetaI * cosThetaI);
		final float sinThetaTSquared = eta * eta * sinThetaISquared;
		
		if(sinThetaTSquared >= 1.0F) {
			return Optional.empty();
		}
		
		final float cosThetaT = Floats.sqrt(1.0F - sinThetaTSquared);
		
//		final float s = eta * cosThetaI - cosThetaT;
		final float s = eta * cosThetaI + cosThetaT;
		
//		final float x = -direction.x * eta + normal.x * s;
//		final float y = -direction.y * eta + normal.y * s;
//		final float z = -direction.z * eta + normal.z * s;
		final float x = +direction.x * eta - normal.x * s;
		final float y = +direction.y * eta - normal.y * s;
		final float z = +direction.z * eta - normal.z * s;
		
		return Optional.of(new Vector3F(x, y, z));
	}
	
	/**
	 * Returns a {@code Vector3F} instance with the absolute component values of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the absolute component values of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F abs(final Vector3F v) {
		return new Vector3F(Floats.abs(v.x), Floats.abs(v.y), Floats.abs(v.z));
	}
	
	/**
	 * Adds the component values of {@code vRHS} to the component values of {@code vLHS}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the addition.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector addition is performed componentwise.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return a {@code Vector3F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3F add(final Vector3F vLHS, final Vector3F vRHS) {
		return new Vector3F(vLHS.x + vRHS.x, vLHS.y + vRHS.y, vLHS.z + vRHS.z);
	}
	
	/**
	 * Adds the component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the addition.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector addition is performed componentwise.
	 * 
	 * @param a a {@code Vector3F} instance
	 * @param b a {@code Vector3F} instance
	 * @param c a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Vector3F add(final Vector3F a, final Vector3F b, final Vector3F c) {
		return new Vector3F(a.x + b.x + c.x, a.y + b.y + c.y, a.z + b.z + c.z);
	}
	
	/**
	 * Computes the cross product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the operation.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return a {@code Vector3F} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3F crossProduct(final Vector3F vLHS, final Vector3F vRHS) {
		return new Vector3F(vLHS.y * vRHS.z - vLHS.z * vRHS.y, vLHS.z * vRHS.x - vLHS.x * vRHS.z, vLHS.x * vRHS.y - vLHS.y * vRHS.x);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@link Point3F} instance denoting the eye to look from
	 * @param lookAt a {@code Point3F} instance denoting the target to look at
	 * @return a {@code Vector3F} instance that is pointing in the direction of {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static Vector3F direction(final Point3F eye, final Point3F lookAt) {
		return new Vector3F(lookAt.x - eye.x, lookAt.y - eye.y, lookAt.z - eye.z);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of {@code u}, {@code v} and {@code w} and is scaled by {@code s}.
	 * <p>
	 * If either {@code u}, {@code v}, {@code w} or {@code s} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to, but faster than, the following:
	 * <pre>
	 * {@code
	 * Vector3F.add(Vector3F.multiply(u, s.x), Vector3F.multiply(v, s.y), Vector3F.multiply(w, s.z));
	 * }
	 * </pre>
	 * 
	 * @param u a {@code Vector3F} instance in the U-direction (X-axis)
	 * @param v a {@code Vector3F} instance in the V-direction (Y-axis)
	 * @param w a {@code Vector3F} instance in the W-direction (Z-axis)
	 * @param s a {@code Vector3F} instance that contains the scale factors for each direction (or axis)
	 * @return a {@code Vector3F} instance that is pointing in the direction of {@code u}, {@code v} and {@code w} and is scaled by {@code s}
	 * @throws NullPointerException thrown if, and only if, either {@code u}, {@code v}, {@code w} or {@code s} are {@code null}
	 */
	public static Vector3F direction(final Vector3F u, final Vector3F v, final Vector3F w, final Vector3F s) {
		return new Vector3F(u.x * s.x + v.x * s.y + w.x * s.z, u.y * s.x + v.y * s.y + w.y * s.z, u.z * s.x + v.z * s.y + w.z * s.z);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of {@code eye} to {@code lookAt} and is normalized.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@link Point3F} instance denoting the eye to look from
	 * @param lookAt a {@code Point3F} instance denoting the target to look at
	 * @return a {@code Vector3F} instance that is pointing in the direction of {@code eye} to {@code lookAt} and is normalized
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static Vector3F directionNormalized(final Point3F eye, final Point3F lookAt) {
		return normalize(direction(eye, lookAt));
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of {@code u}, {@code v} and {@code w}, is scaled by {@code s} and is normalized.
	 * <p>
	 * If either {@code u}, {@code v}, {@code w} or {@code s} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to, but faster than, the following:
	 * <pre>
	 * {@code
	 * Vector3F.normalize(Vector3F.direction(u, v, w, s));
	 * }
	 * </pre>
	 * 
	 * @param u a {@code Vector3F} instance in the U-direction (X-axis)
	 * @param v a {@code Vector3F} instance in the V-direction (Y-axis)
	 * @param w a {@code Vector3F} instance in the W-direction (Z-axis)
	 * @param s a {@code Vector3F} instance that contains the scale factors for each direction (or axis)
	 * @return a {@code Vector3F} instance that is pointing in the direction of {@code u}, {@code v} and {@code w}, is scaled by {@code s} and is normalized
	 * @throws NullPointerException thrown if, and only if, either {@code u}, {@code v}, {@code w} or {@code s} are {@code null}
	 */
	public static Vector3F directionNormalized(final Vector3F u, final Vector3F v, final Vector3F w, final Vector3F s) {
		return normalize(direction(u, v, w, s));
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @return a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}
	 */
	public static Vector3F directionSpherical(final float sinTheta, final float cosTheta, final float phi) {
		return new Vector3F(sinTheta * Floats.cos(phi), sinTheta * Floats.sin(phi), cosTheta);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z}.
	 * <p>
	 * If either {@code x}, {@code y} or {@code z} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @param x the X-direction of the coordinate system
	 * @param y the Y-direction of the coordinate system
	 * @param z the Z-direction of the coordinate system
	 * @return a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z}
	 * @throws NullPointerException thrown if, and only if, either {@code x}, {@code y} or {@code z} are {@code null}
	 */
	public static Vector3F directionSpherical(final float sinTheta, final float cosTheta, final float phi, final Vector3F x, final Vector3F y, final Vector3F z) {
		return add(multiply(x, sinTheta * Floats.cos(phi)), multiply(y, sinTheta * Floats.sin(phi)), multiply(z, cosTheta));
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is normalized.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @return a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is normalized
	 */
	public static Vector3F directionSphericalNormalized(final float sinTheta, final float cosTheta, final float phi) {
		return normalize(directionSpherical(sinTheta, cosTheta, phi));
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}, is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z} and is normalized.
	 * <p>
	 * If either {@code x}, {@code y} or {@code z} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @param x the X-direction of the coordinate system
	 * @param y the Y-direction of the coordinate system
	 * @param z the Z-direction of the coordinate system
	 * @return a {@code Vector3F} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}, is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z} and is normalized
	 * @throws NullPointerException thrown if, and only if, either {@code x}, {@code y} or {@code z} are {@code null}
	 */
	public static Vector3F directionSphericalNormalized(final float sinTheta, final float cosTheta, final float phi, final Vector3F x, final Vector3F y, final Vector3F z) {
		return normalize(directionSpherical(sinTheta, cosTheta, phi, x, y, z));
	}
	
	/**
	 * Divides the component values of {@code v} with {@code s}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the division.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector division is performed componentwise.
	 * 
	 * @param v the {@code Vector3F} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Vector3F} instance with the result of the division
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F divide(final Vector3F v, final float s) {
		return new Vector3F(v.x / s, v.y / s, v.z / s);
	}
	
	/**
	 * Returns a cached version of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a cached version of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F getCached(final Vector3F v) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(v, "v == null"), key -> v);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that contains the Hadamard product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return a {@code Vector3F} instance that contains the Hadamard product of {@code vLHS} and {@code vRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3F hadamardProduct(final Vector3F vLHS, final Vector3F vRHS) {
		return new Vector3F(vLHS.x * vRHS.x, vLHS.y * vRHS.y, vLHS.z * vRHS.z);
	}
	
	/**
	 * Performs a linear interpolation operation on the supplied values.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the linear interpolation operation.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Vector3F} instance
	 * @param b a {@code Vector3F} instance
	 * @param t the factor
	 * @return a {@code Vector3F} instance with the result of the linear interpolation operation
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Vector3F lerp(final Vector3F a, final Vector3F b, final float t) {
		return new Vector3F(Floats.lerp(a.x, b.x, t), Floats.lerp(a.y, b.y, t), Floats.lerp(a.z, b.z, t));
	}
	
	/**
	 * Multiplies the component values of {@code v} with {@code s}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the multiplication.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector multiplication is performed componentwise.
	 * 
	 * @param v the {@code Vector3F} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Vector3F} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F multiply(final Vector3F v, final float s) {
		return new Vector3F(v.x * s, v.y * s, v.z * s);
	}
	
	/**
	 * Negates the component values of {@code v}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F negate(final Vector3F v) {
		return new Vector3F(-v.x, -v.y, -v.z);
	}
	
	/**
	 * Negates the X-component of {@code v}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F negateX(final Vector3F v) {
		return new Vector3F(-v.x, v.y, v.z);
	}
	
	/**
	 * Negates the Y-component of {@code v}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F negateY(final Vector3F v) {
		return new Vector3F(v.x, -v.y, v.z);
	}
	
	/**
	 * Negates the Z-component of {@code v}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F negateZ(final Vector3F v) {
		return new Vector3F(v.x, v.y, -v.z);
	}
	
	/**
	 * Returns a {@code Vector3F} instance denoting the normal of the plane defined by the {@link Point3F} instances {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point3F} instance, corresponding to one of the three points in the plane
	 * @param b a {@code Point3F} instance, corresponding to one of the three points in the plane
	 * @param c a {@code Point3F} instance, corresponding to one of the three points in the plane
	 * @return a {@code Vector3F} instance denoting the normal of the plane defined by the {@code Point3F} instances {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Vector3F normal(final Point3F a, final Point3F b, final Point3F c) {
		return crossProduct(directionNormalized(a, b), directionNormalized(a, c));
	}
	
	/**
	 * Returns a {@code Vector3F} instance denoting the normalized normal of the plane defined by the {@link Point3F} instances {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point3F} instance, corresponding to one of the three points in the plane
	 * @param b a {@code Point3F} instance, corresponding to one of the three points in the plane
	 * @param c a {@code Point3F} instance, corresponding to one of the three points in the plane
	 * @return a {@code Vector3F} instance denoting the normalized normal of the plane defined by the {@code Point3F} instances {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Vector3F normalNormalized(final Point3F a, final Point3F b, final Point3F c) {
		return normalize(normal(a, b, c));
	}
	
	/**
	 * Normalizes the component values of {@code v}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the normalization.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance with the result of the normalization
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F normalize(final Vector3F v) {
		final float length = v.length();
		
		final boolean isLengthGTEThreshold = length >= Floats.NEXT_DOWN_1_3;
		final boolean isLengthLTEThreshold = length <= Floats.NEXT_UP_1_1;
		
		if(isLengthGTEThreshold && isLengthLTEThreshold) {
			return v;
		}
		
		return divide(v, length);
	}
	
	/**
	 * Returns {@code Vector3F.negate(normal)} or {@code normal} as {@code Vector3F.dotProduct(direction, normal)} is less than {@code 0.0F} or greater than or equal to {@code 0.0F}, respectively.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param direction a {@code Vector3F} instance that represents a direction
	 * @param normal a {@code Vector3F} instance that represents a normal
	 * @return {@code Vector3F.negate(normal)} or {@code normal} as {@code Vector3F.dotProduct(direction, normal)} is less than {@code 0.0F} or greater than or equal to {@code 0.0F}, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Vector3F orientNormal(final Vector3F direction, final Vector3F normal) {
		return dotProduct(direction, normal) < 0.0F ? negate(normal) : normal;
	}
	
	/**
	 * Returns {@code normal} or {@code Vector3F.negate(normal)} as {@code Vector3F.dotProduct(direction, normal)} is less than {@code 0.0F} or greater than or equal to {@code 0.0F}, respectively.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param direction a {@code Vector3F} instance that represents a direction
	 * @param normal a {@code Vector3F} instance that represents a normal
	 * @return {@code normal} or {@code Vector3F.negate(normal)} as {@code Vector3F.dotProduct(direction, normal)} is less than {@code 0.0F} or greater than or equal to {@code 0.0F}, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Vector3F orientNormalNegated(final Vector3F direction, final Vector3F normal) {
		return dotProduct(direction, normal) < 0.0F ? normal : negate(normal);
	}
	
	/**
	 * Returns {@code normal} or {@code Vector3F.negate(normal)} as {@code Vector3F.sameHemisphereZ(direction, normal)} is {@code true} or {@code false}, respectively.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param direction a {@code Vector3F} instance that represents a direction
	 * @param normal a {@code Vector3F} instance that represents a normal
	 * @return {@code normal} or {@code Vector3F.negate(normal)} as {@code Vector3F.sameHemisphereZ(direction, normal)} is {@code true} or {@code false}, respectively
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Vector3F orientNormalSameHemisphereZ(final Vector3F direction, final Vector3F normal) {
		return sameHemisphereZ(direction, normal) ? normal : negate(normal);
	}
	
	/**
	 * Returns a normalized {@code Vector3F} instance that is orthogonal to a normalized representation of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a normalized {@code Vector3F} instance that is orthogonal to a normalized representation of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F orthogonal(final Vector3F v) {
		final Vector3F v0 = normalize(v);
		final Vector3F v1 = abs(v0);
		
		if(v1.x < v1.y && v1.x < v1.z) {
			return normalize(new Vector3F(+0.0F, +v0.z, -v0.y));
		} else if(v1.y < v1.z) {
			return normalize(new Vector3F(+v0.z, +0.0F, -v0.x));
		} else {
			return normalize(new Vector3F(+v0.y, -v0.x, +0.0F));
		}
	}
	
	/**
	 * Returns a {@code Vector3F} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Vector3F} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Vector3F read(final DataInput dataInput) {
		try {
			return new Vector3F(dataInput.readFloat(), dataInput.readFloat(), dataInput.readFloat());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns a {@code Vector3F} instance that is the reciprocal (or multiplicative inverse) of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation is performed componentwise.
	 * 
	 * @param v a {@code Vector3F} instance
	 * @return a {@code Vector3F} instance that is the reciprocal (or multiplicative inverse) of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3F reciprocal(final Vector3F v) {
		return new Vector3F(1.0F / v.x, 1.0F / v.y, 1.0F / v.z);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that represents the reflection of {@code direction} with regards to {@code normal}.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.reflection(direction, normal, false);
	 * }
	 * </pre>
	 * 
	 * @param direction the {@code Vector3F} instance that will be reflected with regards to {@code normal}
	 * @param normal the {@code Vector3F} instance that represents the normal of the surface
	 * @return a {@code Vector3F} instance that represents the reflection of {@code direction} with regards to {@code normal}
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Vector3F reflection(final Vector3F direction, final Vector3F normal) {
		return reflection(direction, normal, false);
	}
	
	/**
	 * Returns a {@code Vector3F} instance that represents the reflection of {@code direction} with regards to {@code normal}.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code isFacingSurface} is {@code true}, it is assumed that {@code direction} is facing the surface. This is usually the case for the direction of a ray that intersects the surface. If {@code isFacingSurface} is {@code false}, it is assumed that {@code direction} is pointing in the opposite direction. That is, the ray starts at the surface intersection point and is directed away from the surface.
	 * 
	 * @param direction the {@code Vector3F} instance that will be reflected with regards to {@code normal}
	 * @param normal the {@code Vector3F} instance that represents the normal of the surface
	 * @param isFacingSurface {@code true} if, and only if, {@code direction} is facing the surface, {@code false} otherwise
	 * @return a {@code Vector3F} instance that represents the reflection of {@code direction} with regards to {@code normal}
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Vector3F reflection(final Vector3F direction, final Vector3F normal, final boolean isFacingSurface) {
		return isFacingSurface ? subtract(direction, multiply(normal, dotProduct(direction, normal) * 2.0F)) : subtract(multiply(normal, dotProduct(direction, normal) * 2.0F), direction);
	}
	
	/**
	 * Samples a direction on a cone with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2F} instance with components in the interval [0.0, 1.0]
	 * @param cosThetaMax the maximum cos theta value
	 * @return a {@code Vector3F} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Vector3F sampleConeUniformDistribution(final Point2F p, final float cosThetaMax) {
		final float cosTheta = (1.0F - p.x) + p.x * cosThetaMax;
		final float sinTheta = Floats.sqrt(1.0F - cosTheta * cosTheta);
		final float phi = Floats.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Samples a direction on a hemisphere with a cosine distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.sampleHemisphereCosineDistribution(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3F} instance with the sampled direction
	 */
	public static Vector3F sampleHemisphereCosineDistribution() {
		return sampleHemisphereCosineDistribution(Point2F.sampleRandom());
	}
	
	/**
	 * Samples a direction on a hemisphere with a cosine distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3F} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Vector3F sampleHemisphereCosineDistribution(final Point2F p) {
		final Point2F q = Point2F.sampleDiskUniformDistributionByConcentricMapping(p);
		
		return new Vector3F(q.x, q.y, Floats.sqrt(Floats.max(0.0F, 1.0F - q.x * q.x - q.y * q.y)));
	}
	
	/**
	 * Samples a direction on a hemisphere with a power-cosine distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.sampleHemispherePowerCosineDistribution(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3F} instance with the sampled direction
	 */
	public static Vector3F sampleHemispherePowerCosineDistribution() {
		return sampleHemispherePowerCosineDistribution(Point2F.sampleRandom());
	}
	
	/**
	 * Samples a direction on a hemisphere with a power-cosine distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.sampleHemispherePowerCosineDistribution(p, 20.0F);
	 * }
	 * </pre>
	 * 
	 * @param p a {@link Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3F} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Vector3F sampleHemispherePowerCosineDistribution(final Point2F p) {
		return sampleHemispherePowerCosineDistribution(p, 20.0F);
	}
	
	/**
	 * Samples a direction on a hemisphere with a power-cosine distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2F} instance with components in the interval [0.0, 1.0]
	 * @param exponent the exponent to use
	 * @return a {@code Vector3F} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Vector3F sampleHemispherePowerCosineDistribution(final Point2F p, final float exponent) {
		final float cosTheta = Floats.pow(1.0F - p.x, 1.0F / (exponent + 1.0F));
		final float sinTheta = Floats.sqrt(Floats.max(0.0F, 1.0F - cosTheta * cosTheta));
		final float phi = Floats.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Samples a direction on a hemisphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.sampleHemisphereUniformDistribution(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3F} instance with the sampled direction
	 */
	public static Vector3F sampleHemisphereUniformDistribution() {
		return sampleHemisphereUniformDistribution(Point2F.sampleRandom());
	}
	
	/**
	 * Samples a direction on a hemisphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3F} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Vector3F sampleHemisphereUniformDistribution(final Point2F p) {
		final float cosTheta = p.x;
		final float sinTheta = Floats.sqrt(Floats.max(0.0F, 1.0F - cosTheta * cosTheta));
		final float phi = Floats.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Samples a direction on a sphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.sampleSphereUniformDistribution(Point2F.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3F} instance with the sampled direction
	 */
	public static Vector3F sampleSphereUniformDistribution() {
		return sampleSphereUniformDistribution(Point2F.sampleRandom());
	}
	
	/**
	 * Samples a direction on a sphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3F} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2F} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3F} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public static Vector3F sampleSphereUniformDistribution(final Point2F p) {
		final float cosTheta = 1.0F - 2.0F * p.x;
		final float sinTheta = Floats.sqrt(Floats.max(0.0F, 1.0F - cosTheta * cosTheta));
		final float phi = Floats.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Subtracts the component values of {@code vRHS} from the component values of {@code vLHS}.
	 * <p>
	 * Returns a {@code Vector3F} instance with the result of the subtraction.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector subtraction is performed componentwise.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return a {@code Vector3F} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3F subtract(final Vector3F vLHS, final Vector3F vRHS) {
		return new Vector3F(vLHS.x - vRHS.x, vLHS.y - vRHS.y, vLHS.z - vRHS.z);
	}
	
	/**
	 * Returns a {@code Vector3F} instance equivalent to {@code new Vector3F(1.0F, 0.0F, 0.0F)}.
	 * 
	 * @return a {@code Vector3F} instance equivalent to {@code new Vector3F(1.0F, 0.0F, 0.0F)}
	 */
	public static Vector3F x() {
		return x(1.0F);
	}
	
	/**
	 * Returns a {@code Vector3F} instance equivalent to {@code new Vector3F(x, 0.0F, 0.0F)}.
	 * 
	 * @param x the value of the X-component
	 * @return a {@code Vector3F} instance equivalent to {@code new Vector3F(x, 0.0F, 0.0F)}
	 */
	public static Vector3F x(final float x) {
		return new Vector3F(x, 0.0F, 0.0F);
	}
	
	/**
	 * Returns a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, 1.0F, 0.0F)}.
	 * 
	 * @return a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, 1.0F, 0.0F)}
	 */
	public static Vector3F y() {
		return y(1.0F);
	}
	
	/**
	 * Returns a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, y, 0.0F)}.
	 * 
	 * @param y the value of the Y-component
	 * @return a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, y, 0.0F)}
	 */
	public static Vector3F y(final float y) {
		return new Vector3F(0.0F, y, 0.0F);
	}
	
	/**
	 * Returns a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, 0.0F, 1.0F)}.
	 * 
	 * @return a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, 0.0F, 1.0F)}
	 */
	public static Vector3F z() {
		return z(1.0F);
	}
	
	/**
	 * Returns a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, 0.0F, z)}.
	 * 
	 * @param z the value of the Z-component
	 * @return a {@code Vector3F} instance equivalent to {@code new Vector3F(0.0F, 0.0F, z)}
	 */
	public static Vector3F z(final float z) {
		return new Vector3F(0.0F, 0.0F, z);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code vLHS} and {@code vRHS} are orthogonal, {@code false} otherwise.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return {@code true} if, and only if, {@code vLHS} and {@code vRHS} are orthogonal, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static boolean orthogonal(final Vector3F vLHS, final Vector3F vRHS) {
		final float dotProduct = dotProduct(vLHS, vRHS);
		
		final boolean isDotProductGTEThreshold = dotProduct >= 0.0F - 0.000001F;
		final boolean isDotProductLTEThreshold = dotProduct <= 0.0F + 0.000001F;
		
		return isDotProductGTEThreshold && isDotProductLTEThreshold;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static boolean sameHemisphere(final Vector3F vLHS, final Vector3F vRHS) {
		return dotProduct(vLHS, vRHS) > 0.0F;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method only operates on the Z-component.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static boolean sameHemisphereZ(final Vector3F vLHS, final Vector3F vRHS) {
		return vLHS.z * vRHS.z > 0.0F;
	}
	
	/**
	 * Returns the probability density function (PDF) value for {@code cosThetaMax}.
	 * <p>
	 * This method is used together with {@link #sampleConeUniformDistribution(Point2F, float)}.
	 * 
	 * @param cosThetaMax the maximum cos theta value
	 * @return the probability density function (PDF) value for {@code cosThetaMax}
	 */
	public static float coneUniformDistributionPDF(final float cosThetaMax) {
		return 1.0F / (Floats.PI_MULTIPLIED_BY_2 * (1.0F - cosThetaMax));
	}
	
	/**
	 * Returns the dot product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return the dot product of {@code vLHS} and {@code vRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static float dotProduct(final Vector3F vLHS, final Vector3F vRHS) {
		return vLHS.x * vRHS.x + vLHS.y * vRHS.y + vLHS.z * vRHS.z;
	}
	
	/**
	 * Returns the absolute dot product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3F} instance on the left-hand side
	 * @param vRHS the {@code Vector3F} instance on the right-hand side
	 * @return the absolute dot product of {@code vLHS} and {@code vRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static float dotProductAbs(final Vector3F vLHS, final Vector3F vRHS) {
		return Floats.abs(dotProduct(vLHS, vRHS));
	}
	
	/**
	 * Returns the probability density function (PDF) value for {@code cosTheta}.
	 * <p>
	 * This method is used together with {@link #sampleHemisphereCosineDistribution(Point2F)}.
	 * 
	 * @param cosTheta the cos theta value
	 * @return the probability density function (PDF) value for {@code cosTheta}
	 */
	public static float hemisphereCosineDistributionPDF(final float cosTheta) {
		return cosTheta * Floats.PI_RECIPROCAL;
	}
	
	/**
	 * Returns the probability density function (PDF) value.
	 * <p>
	 * This method is used together with {@link #sampleHemisphereUniformDistribution(Point2F)}.
	 * 
	 * @return the probability density function (PDF) value
	 */
	public static float hemisphereUniformDistributionPDF() {
		return Floats.PI_MULTIPLIED_BY_2_RECIPROCAL;
	}
	
	/**
	 * Returns the probability density function (PDF) value.
	 * <p>
	 * This method is used together with {@link #sampleSphereUniformDistribution(Point2F)}.
	 * 
	 * @return the probability density function (PDF) value
	 */
	public static float sphereUniformDistributionPDF() {
		return Floats.PI_MULTIPLIED_BY_4_RECIPROCAL;
	}
	
	/**
	 * Returns the triple product of {@code vLHSDP}, {@code vLHSCP} and {@code vRHSCP}.
	 * <p>
	 * If either {@code vLHSDP}, {@code vLHSCP} or {@code vRHSCP} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3F.dotProduct(vLHSDP, Vector3F.crossProduct(vLHSCP, vRHSCP));
	 * }
	 * </pre>
	 * 
	 * @param vLHSDP the {@code Vector3F} instance on the left-hand side of the dot product
	 * @param vLHSCP the {@code Vector3F} instance on the left-hand side of the cross product
	 * @param vRHSCP the {@code Vector3F} instance on the right-hand side of the cross product
	 * @return the triple product of {@code vLHSDP}, {@code vLHSCP} and {@code vRHSCP}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHSDP}, {@code vLHSCP} or {@code vRHSCP} are {@code null}
	 */
	public static float tripleProduct(final Vector3F vLHSDP, final Vector3F vLHSCP, final Vector3F vRHSCP) {
		return dotProduct(vLHSDP, crossProduct(vLHSCP, vRHSCP));
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