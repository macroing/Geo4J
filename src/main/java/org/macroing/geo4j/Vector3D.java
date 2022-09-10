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
package org.macroing.geo4j;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Strings;

/**
 * A {@code Vector3D} represents a vector with three {@code double}-based components.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Vector3D {
	/**
	 * A {@code Vector3D} instance given the component values {@code Doubles.NaN}, {@code Doubles.NaN} and {@code Doubles.NaN}.
	 */
	public static final Vector3D NaN = new Vector3D(Doubles.NaN, Doubles.NaN, Doubles.NaN);
	
	/**
	 * A {@code Vector3D} instance given the component values {@code 0.0D}, {@code 0.0D} and {@code 0.0D}.
	 */
	public static final Vector3D ZERO = new Vector3D(0.0D, 0.0D, 0.0D);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final Map<Vector3D, Vector3D> CACHE = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The X-component of this {@code Vector3D} instance.
	 */
	public final double x;
	
	/**
	 * The Y-component of this {@code Vector3D} instance.
	 */
	public final double y;
	
	/**
	 * The Z-component of this {@code Vector3D} instance.
	 */
	public final double z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Vector3D} instance given the component values {@code 0.0D}, {@code 0.0D} and {@code 0.0D}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector3D(0.0D, 0.0D, 0.0D);
	 * }
	 * </pre>
	 */
	public Vector3D() {
		this(0.0D, 0.0D, 0.0D);
	}
	
	/**
	 * Constructs a new {@code Vector3D} instance given the component values {@code p.x}, {@code p.y} and {@code p.z}.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector3D(p.x, p.y, p.z);
	 * }
	 * </pre>
	 * 
	 * @param p a {@link Point3D} instance
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
	public Vector3D(final Point3D p) {
		this(p.x, p.y, p.z);
	}
	
	/**
	 * Constructs a new {@code Vector3D} instance given the component values {@code component}, {@code component} and {@code component}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Vector3D(component, component, component);
	 * }
	 * </pre>
	 * 
	 * @param component the value of all components
	 */
	public Vector3D(final double component) {
		this(component, component, component);
	}
	
	/**
	 * Constructs a new {@code Vector3D} instance given the component values {@code x}, {@code y} and {@code z}.
	 * 
	 * @param x the X-component of this {@code Vector3D} instance
	 * @param y the Y-component of this {@code Vector3D} instance
	 * @param z the Z-component of this {@code Vector3D} instance
	 */
	public Vector3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code Vector3D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Vector3D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Vector3D(%s, %s, %s)", Strings.toNonScientificNotationJava(this.x), Strings.toNonScientificNotationJava(this.y), Strings.toNonScientificNotationJava(this.z));
	}
	
	/**
	 * Compares {@code object} to this {@code Vector3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Vector3D} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} instance to compare to this {@code Vector3D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Vector3D} and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Vector3D)) {
			return false;
		} else {
			return equals(Vector3D.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code v} to this {@code Vector3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code v} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param v the {@code Vector3D} instance to compare to this {@code Vector3D} instance for equality
	 * @return {@code true} if, and only if, {@code v} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Vector3D v) {
		if(v == this) {
			return true;
		} else if(v == null) {
			return false;
		} else if(!Doubles.equals(this.x, v.x)) {
			return false;
		} else if(!Doubles.equals(this.y, v.y)) {
			return false;
		} else if(!Doubles.equals(this.z, v.z)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Vector3D} instance has at least one component with a value that is infinite, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Vector3D} instance has at least one component with a value that is infinite, {@code false} otherwise
	 */
	public boolean hasInfinites() {
		return Doubles.isInfinite(this.x) || Doubles.isInfinite(this.y) || Doubles.isInfinite(this.z);
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Vector3D} instance has at least one component with a value that is NaN (Not a Number), {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Vector3D} instance has at least one component with a value that is NaN (Not a Number), {@code false} otherwise
	 */
	public boolean hasNaNs() {
		return Doubles.isNaN(this.x) || Doubles.isNaN(this.y) || Doubles.isNaN(this.z);
	}
	
	/**
	 * Returns {@code true} if, and only if, all components in this {@code Vector3D} instance have values that are finite, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all components in this {@code Vector3D} instance have values that are finite, {@code false} otherwise
	 */
	public boolean isFinite() {
		return !hasInfinites() && !hasNaNs();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Vector3D} instance is a unit vector, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Vector3D} instance is a unit vector, {@code false} otherwise
	 */
	public boolean isUnitVector() {
		final double length = length();
		
		final boolean isLengthGTEThreshold = length >= Doubles.NEXT_DOWN_1_3;
		final boolean isLengthLTEThreshold = length <= Doubles.NEXT_UP_1_1;
		
		return isLengthGTEThreshold && isLengthLTEThreshold;
	}
	
	/**
	 * Returns {@code true} if, and only if, all components in this {@code Vector3D} instance have values that are zero, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, all components in this {@code Vector3D} instance have values that are zero, {@code false} otherwise
	 */
	public boolean isZero() {
		return Doubles.isZero(this.x) && Doubles.isZero(this.y) && Doubles.isZero(this.z);  
	}
	
	/**
	 * Returns the cosine of the angle phi.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double sinTheta = v.sinTheta();
	 * double cosPhi = Doubles.isZero(sinTheta) ? 1.0D : Doubles.saturate(v.x / sinTheta, -1.0D, 1.0D);
	 * 
	 * double sinThetaSimplified = Doubles.sqrt(Doubles.max(0.0D, 1.0D - v.z * v.z));
	 * double cosPhiSimplified = Doubles.isZero(sinThetaSimplified) ? 1.0D : Doubles.saturate(v.x / sinThetaSimplified, -1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle phi
	 */
	public double cosPhi() {
		final double sinTheta = sinTheta();
		final double cosPhi = Doubles.isZero(sinTheta) ? 1.0D : Doubles.saturate(this.x / sinTheta, -1.0D, 1.0D);
		
		return cosPhi;
	}
	
	/**
	 * Returns the cosine of the angle phi in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double cosPhiSquared = v.cosPhi() * v.cosPhi();
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle phi in squared form
	 */
	public double cosPhiSquared() {
		return cosPhi() * cosPhi();
	}
	
	/**
	 * Returns the cosine of the angle theta.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double cosTheta = v.z;
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta
	 */
	public double cosTheta() {
		return this.z;
	}
	
	/**
	 * Returns the cosine of the angle theta in absolute form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double cosThetaAbs = Doubles.abs(v.cosTheta());
	 * double cosThetaAbsSimplified = Doubles.abs(v.z);
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta in absolute form
	 */
	public double cosThetaAbs() {
		return Doubles.abs(cosTheta());
	}
	
	/**
	 * Returns the cosine of the angle theta in quartic form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double cosThetaQuartic = v.cosThetaSquared() * v.cosThetaSquared();
	 * double cosThetaQuarticSimplified = v.z * v.z * v.z * v.z;
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta in quartic form
	 */
	public double cosThetaQuartic() {
		return cosThetaSquared() * cosThetaSquared();
	}
	
	/**
	 * Returns the cosine of the angle theta in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double cosThetaSquared = v.cosTheta() * v.cosTheta();
	 * double cosThetaSquaredSimplified = v.z * v.z;
	 * }
	 * </pre>
	 * 
	 * @return the cosine of the angle theta in squared form
	 */
	public double cosThetaSquared() {
		return cosTheta() * cosTheta();
	}
	
	/**
	 * Returns the length of this {@code Vector3D} instance.
	 * 
	 * @return the length of this {@code Vector3D} instance
	 */
	public double length() {
		return Doubles.sqrt(lengthSquared());
	}
	
	/**
	 * Returns the squared length of this {@code Vector3D} instance.
	 * 
	 * @return the squared length of this {@code Vector3D} instance
	 */
	public double lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}
	
	/**
	 * Returns the sine of the angle phi.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double sinTheta = v.sinTheta();
	 * double sinPhi = Doubles.isZero(sinTheta) ? 0.0D : Doubles.saturate(v.y / sinTheta, -1.0D, 1.0D);
	 * 
	 * double sinThetaSimplified = Doubles.sqrt(Doubles.max(0.0D, 1.0D - v.z * v.z));
	 * double sinPhiSimplified = Doubles.isZero(sinThetaSimplified) ? 0.0D : Doubles.saturate(v.y / sinThetaSimplified, -1.0D, 1.0D);
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle phi
	 */
	public double sinPhi() {
		final double sinTheta = sinTheta();
		final double sinPhi = Doubles.isZero(sinTheta) ? 0.0D : Doubles.saturate(this.y / sinTheta, -1.0D, 1.0D);
		
		return sinPhi;
	}
	
	/**
	 * Returns the sine of the angle phi in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double sinPhiSquared = v.sinPhi() * v.sinPhi();
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle phi in squared form
	 */
	public double sinPhiSquared() {
		return sinPhi() * sinPhi();
	}
	
	/**
	 * Returns the sine of the angle theta.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double sinTheta = Doubles.sqrt(v.sinThetaSquared());
	 * double sinThetaSimplified = Doubles.sqrt(Doubles.max(0.0D, 1.0D - v.z * v.z));
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle theta
	 */
	public double sinTheta() {
		return Doubles.sqrt(sinThetaSquared());
	}
	
	/**
	 * Returns the sine of the angle theta in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double sinThetaSquared = Doubles.max(0.0D, 1.0D - v.cosThetaSquared());
	 * double sinThetaSquaredSimplified = Doubles.max(0.0D, 1.0D - v.z * v.z);
	 * }
	 * </pre>
	 * 
	 * @return the sine of the angle theta in squared form
	 */
	public double sinThetaSquared() {
		return Doubles.max(0.0D, 1.0D - cosThetaSquared());
	}
	
	/**
	 * Returns the spherical phi angle.
	 * 
	 * @return the spherical phi angle
	 */
//	TODO: Add Unit Tests!
	public double sphericalPhi() {
		return Doubles.addLessThan(Doubles.atan2(this.y, this.x), 0.0D, Doubles.PI_MULTIPLIED_BY_2);
	}
	
	/**
	 * Returns the spherical theta angle.
	 * 
	 * @return the spherical theta angle
	 */
//	TODO: Add Unit Tests!
	public double sphericalTheta() {
		return Doubles.acos(Doubles.saturate(this.z, -1.0D, 1.0D));
	}
	
	/**
	 * Returns the tangent of the angle theta.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double tanTheta = v.sinTheta() / v.cosTheta();
	 * double tanThetaSimplified = Doubles.sqrt(Doubles.max(0.0D, 1.0D - v.z * v.z)) / v.z;
	 * }
	 * </pre>
	 * 
	 * @return the tangent of the angle theta
	 */
	public double tanTheta() {
		return sinTheta() / cosTheta();
	}
	
	/**
	 * Returns the tangent of the angle theta in absolute form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double tanThetaAbs = Doubles.abs(v.tanTheta());
	 * double tanThetaAbsSimplified = Doubles.abs(Doubles.sqrt(Doubles.max(0.0D, 1.0D - v.z * v.z)) / v.z);
	 * }
	 * </pre>
	 * 
	 * @return the tangent of the angle theta in absolute form
	 */
	public double tanThetaAbs() {
		return Doubles.abs(tanTheta());
	}
	
	/**
	 * Returns the tangent of the angle theta in squared form.
	 * <p>
	 * Calling this method is equivalent to the following, given a {@code Vector3D} instance called {@code v}:
	 * <pre>
	 * {@code
	 * double tanThetaSquared = v.sinThetaSquared() / v.cosThetaSquared();
	 * double tanThetaSquaredSimplified = Doubles.max(0.0D, 1.0D - v.z * v.z) / (v.z * v.z);
	 * }
	 * </pre>
	 * 
	 * @return the tangent of the angle theta in squared form
	 */
	public double tanThetaSquared() {
		return sinThetaSquared() / cosThetaSquared();
	}
	
	/**
	 * Returns a {@code double[]} representation of this {@code Vector3D} instance.
	 * 
	 * @return a {@code double[]} representation of this {@code Vector3D} instance
	 */
	public double[] toArray() {
		return new double[] {this.x, this.y, this.z};
	}
	
	/**
	 * Returns a hash code for this {@code Vector3D} instance.
	 * 
	 * @return a hash code for this {@code Vector3D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z));
	}
	
	/**
	 * Writes this {@code Vector3D} instance to {@code dataOutput}.
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
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an optional {@code Vector3D} instance that represents the refraction of {@code direction} with regards to {@code normal}.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param direction the {@code Vector3D} instance that will be refracted with regards to {@code normal}
	 * @param normal the {@code Vector3D} instance that represents the normal of the surface
	 * @param eta the index of refraction
	 * @return an optional {@code Vector3D} instance that represents the refraction of {@code direction} with regards to {@code normal}
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
	public static Optional<Vector3D> refraction(final Vector3D direction, final Vector3D normal, final double eta) {
		final double cosThetaI = dotProduct(direction, normal);
		final double sinThetaISquared = Doubles.max(0.0D, 1.0D - cosThetaI * cosThetaI);
		final double sinThetaTSquared = eta * eta * sinThetaISquared;
		
		if(sinThetaTSquared >= 1.0D) {
			return Optional.empty();
		}
		
		final double cosThetaT = Doubles.sqrt(1.0D - sinThetaTSquared);
		
//		final double s = eta * cosThetaI - cosThetaT;
		final double s = eta * cosThetaI + cosThetaT;
		
//		final double x = -direction.x * eta + normal.x * s;
//		final double y = -direction.y * eta + normal.y * s;
//		final double z = -direction.z * eta + normal.z * s;
		final double x = +direction.x * eta - normal.x * s;
		final double y = +direction.y * eta - normal.y * s;
		final double z = +direction.z * eta - normal.z * s;
		
		return Optional.of(new Vector3D(x, y, z));
	}
	
	/**
	 * Returns a {@code Vector3D} instance with the absolute component values of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the absolute component values of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D abs(final Vector3D v) {
		return new Vector3D(Doubles.abs(v.x), Doubles.abs(v.y), Doubles.abs(v.z));
	}
	
	/**
	 * Adds the component values of {@code vRHS} to the component values of {@code vLHS}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the addition.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector addition is performed componentwise.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return a {@code Vector3D} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3D add(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x + vRHS.x, vLHS.y + vRHS.y, vLHS.z + vRHS.z);
	}
	
	/**
	 * Adds the component values of {@code a}, {@code b} and {@code c}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the addition.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector addition is performed componentwise.
	 * 
	 * @param a a {@code Vector3D} instance
	 * @param b a {@code Vector3D} instance
	 * @param c a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D add(final Vector3D a, final Vector3D b, final Vector3D c) {
		return new Vector3D(a.x + b.x + c.x, a.y + b.y + c.y, a.z + b.z + c.z);
	}
	
	/**
	 * Computes the cross product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the operation.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return a {@code Vector3D} instance with the result of the operation
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3D crossProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.y * vRHS.z - vLHS.z * vRHS.y, vLHS.z * vRHS.x - vLHS.x * vRHS.z, vLHS.x * vRHS.y - vLHS.y * vRHS.x);
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is pointing in the direction of {@code eye} to {@code lookAt}.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@link Point3D} instance denoting the eye to look from
	 * @param lookAt a {@code Point3D} instance denoting the target to look at
	 * @return a {@code Vector3D} instance that is pointing in the direction of {@code eye} to {@code lookAt}
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static Vector3D direction(final Point3D eye, final Point3D lookAt) {
		return new Vector3D(lookAt.x - eye.x, lookAt.y - eye.y, lookAt.z - eye.z);
	}
	
//	TODO: Add Javadocs!
	public static Vector3D direction(final Vector3D u, final Vector3D v, final Vector3D w) {
		return new Vector3D(u.x + v.x + w.x, u.y + v.y + w.y, u.z + v.z + w.z);
	}
	
//	TODO: Add Javadocs!
	public static Vector3D direction(final Vector3D u, final Vector3D v, final Vector3D w, final Vector3D s) {
		return new Vector3D(u.x * s.x + v.x * s.y + w.x * s.z, u.y * s.x + v.y * s.y + w.y * s.z, u.z * s.x + v.z * s.y + w.z * s.z);
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is pointing in the direction of {@code eye} to {@code lookAt} and is normalized.
	 * <p>
	 * If either {@code eye} or {@code lookAt} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param eye a {@link Point3D} instance denoting the eye to look from
	 * @param lookAt a {@code Point3D} instance denoting the target to look at
	 * @return a {@code Vector3D} instance that is pointing in the direction of {@code eye} to {@code lookAt} and is normalized
	 * @throws NullPointerException thrown if, and only if, either {@code eye} or {@code lookAt} are {@code null}
	 */
	public static Vector3D directionNormalized(final Point3D eye, final Point3D lookAt) {
		return normalize(direction(eye, lookAt));
	}
	
//	TODO: Add Javadocs!
//	TODO: Add unit tests!
	public static Vector3D directionNormalized(final Vector3D u, final Vector3D v, final Vector3D w) {
		return normalize(direction(u, v, w));
	}
	
//	TODO: Add Javadocs!
//	TODO: Add unit tests!
	public static Vector3D directionNormalized(final Vector3D u, final Vector3D v, final Vector3D w, final Vector3D s) {
		return normalize(direction(u, v, w, s));
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @return a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}
	 */
//	TODO: Add unit tests!
	public static Vector3D directionSpherical(final double sinTheta, final double cosTheta, final double phi) {
		return new Vector3D(sinTheta * Doubles.cos(phi), sinTheta * Doubles.sin(phi), cosTheta);
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z}.
	 * <p>
	 * If either {@code x}, {@code y} or {@code z} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @param x the X-direction of the coordinate system
	 * @param y the Y-direction of the coordinate system
	 * @param z the Z-direction of the coordinate system
	 * @return a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z}
	 * @throws NullPointerException thrown if, and only if, either {@code x}, {@code y} or {@code z} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D directionSpherical(final double sinTheta, final double cosTheta, final double phi, final Vector3D x, final Vector3D y, final Vector3D z) {
		return add(multiply(x, sinTheta * Doubles.cos(phi)), multiply(y, sinTheta * Doubles.sin(phi)), multiply(z, cosTheta));
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is normalized.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @return a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi} and is normalized
	 */
//	TODO: Add unit tests!
	public static Vector3D directionSphericalNormalized(final double sinTheta, final double cosTheta, final double phi) {
		return normalize(directionSpherical(sinTheta, cosTheta, phi));
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}, is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z} and is normalized.
	 * <p>
	 * If either {@code x}, {@code y} or {@code z} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param sinTheta the sine of the angle theta
	 * @param cosTheta the cosine of the angle theta
	 * @param phi the angle phi
	 * @param x the X-direction of the coordinate system
	 * @param y the Y-direction of the coordinate system
	 * @param z the Z-direction of the coordinate system
	 * @return a {@code Vector3D} instance that is pointing in the direction of the spherical coordinates {@code sinTheta}, {@code cosTheta} and {@code phi}, is transformed with the coordinate system defined by {@code x}, {@code y} and {@code z} and is normalized
	 * @throws NullPointerException thrown if, and only if, either {@code x}, {@code y} or {@code z} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D directionSphericalNormalized(final double sinTheta, final double cosTheta, final double phi, final Vector3D x, final Vector3D y, final Vector3D z) {
		return normalize(directionSpherical(sinTheta, cosTheta, phi, x, y, z));
	}
	
	/**
	 * Divides the component values of {@code v} with {@code s}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the division.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector division is performed componentwise.
	 * 
	 * @param v the {@code Vector3D} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Vector3D} instance with the result of the division
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D divide(final Vector3D v, final double s) {
		return new Vector3D(v.x / s, v.y / s, v.z / s);
	}
	
	/**
	 * Returns a cached version of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a cached version of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D getCached(final Vector3D v) {
		return CACHE.computeIfAbsent(Objects.requireNonNull(v, "v == null"), key -> v);
	}
	
	/**
	 * Returns a {@code Vector3D} instance that contains the Hadamard product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return a {@code Vector3D} instance that contains the Hadamard product of {@code vLHS} and {@code vRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3D hadamardProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x * vRHS.x, vLHS.y * vRHS.y, vLHS.z * vRHS.z);
	}
	
	/**
	 * Performs a linear interpolation operation on the supplied values.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the linear interpolation operation.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Vector3D} instance
	 * @param b a {@code Vector3D} instance
	 * @param t the factor
	 * @return a {@code Vector3D} instance with the result of the linear interpolation operation
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Vector3D lerp(final Vector3D a, final Vector3D b, final double t) {
		return new Vector3D(Doubles.lerp(a.x, b.x, t), Doubles.lerp(a.y, b.y, t), Doubles.lerp(a.z, b.z, t));
	}
	
	/**
	 * Multiplies the component values of {@code v} with {@code s}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the multiplication.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector multiplication is performed componentwise.
	 * 
	 * @param v the {@code Vector3D} instance on the left-hand side
	 * @param s the scalar value on the right-hand side
	 * @return a {@code Vector3D} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D multiply(final Vector3D v, final double s) {
		return new Vector3D(v.x * s, v.y * s, v.z * s);
	}
	
	/**
	 * Negates the component values of {@code v}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D negate(final Vector3D v) {
		return new Vector3D(-v.x, -v.y, -v.z);
	}
	
	/**
	 * Negates the X-component of {@code v}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D negateX(final Vector3D v) {
		return new Vector3D(-v.x, v.y, v.z);
	}
	
	/**
	 * Negates the Y-component of {@code v}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D negateY(final Vector3D v) {
		return new Vector3D(v.x, -v.y, v.z);
	}
	
	/**
	 * Negates the Z-component of {@code v}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the negation.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the negation
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D negateZ(final Vector3D v) {
		return new Vector3D(v.x, v.y, -v.z);
	}
	
	/**
	 * Returns a {@code Vector3D} instance denoting the normal of the plane defined by the {@link Point3D} instances {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point3D} instance, corresponding to one of the three points in the plane
	 * @param b a {@code Point3D} instance, corresponding to one of the three points in the plane
	 * @param c a {@code Point3D} instance, corresponding to one of the three points in the plane
	 * @return a {@code Vector3D} instance denoting the normal of the plane defined by the {@code Point3D} instances {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Vector3D normal(final Point3D a, final Point3D b, final Point3D c) {
		return crossProduct(directionNormalized(a, b), directionNormalized(a, c));
	}
	
	/**
	 * Returns a {@code Vector3D} instance denoting the normalized normal of the plane defined by the {@link Point3D} instances {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point3D} instance, corresponding to one of the three points in the plane
	 * @param b a {@code Point3D} instance, corresponding to one of the three points in the plane
	 * @param c a {@code Point3D} instance, corresponding to one of the three points in the plane
	 * @return a {@code Vector3D} instance denoting the normalized normal of the plane defined by the {@code Point3D} instances {@code a}, {@code b} and {@code c}
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public static Vector3D normalNormalized(final Point3D a, final Point3D b, final Point3D c) {
		return normalize(normal(a, b, c));
	}
	
	/**
	 * Normalizes the component values of {@code v}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the normalization.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the normalization
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D normalize(final Vector3D v) {
		final double length = v.length();
		
		final boolean isLengthGTEThreshold = length >= Doubles.NEXT_DOWN_1_3;
		final boolean isLengthLTEThreshold = length <= Doubles.NEXT_UP_1_1;
		
		if(isLengthGTEThreshold && isLengthLTEThreshold) {
			return v;
		}
		
		return divide(v, length);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add unit tests!
	public static Vector3D orientNormal(final Vector3D direction, final Vector3D normal) {
		return dotProduct(direction, normal) < 0.0D ? normal : negate(normal);
	}
	
//	TODO: Add Javadocs!
//	TODO: Add unit tests!
	public static Vector3D orientNormalSameHemisphereZ(final Vector3D direction, final Vector3D normal) {
		return sameHemisphereZ(direction, normal) ? normal : negate(normal);
	}
	
//	TODO: Add Javadocs!
	public static Vector3D orthogonal(final Vector3D v) {
		final Vector3D v0 = normalize(v);
		final Vector3D v1 = abs(v0);
		
		if(v1.x < v1.y && v1.x < v1.z) {
			return normalize(new Vector3D(+0.0D, +v0.z, -v0.y));
		} else if(v1.y < v1.z) {
			return normalize(new Vector3D(+v0.z, +0.0D, -v0.x));
		} else {
			return normalize(new Vector3D(+v0.y, -v0.x, +0.0D));
		}
	}
	
	/**
	 * Returns a {@code Vector3D} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Vector3D} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Vector3D read(final DataInput dataInput) {
		try {
			return new Vector3D(dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns a {@code Vector3D} instance that is the reciprocal (or multiplicative inverse) of {@code v}.
	 * <p>
	 * If {@code v} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This operation is performed componentwise.
	 * 
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance that is the reciprocal (or multiplicative inverse) of {@code v}
	 * @throws NullPointerException thrown if, and only if, {@code v} is {@code null}
	 */
	public static Vector3D reciprocal(final Vector3D v) {
		return new Vector3D(1.0D / v.x, 1.0D / v.y, 1.0D / v.z);
	}
	
	/**
	 * Returns a {@code Vector3D} instance that represents the reflection of {@code direction} with regards to {@code normal}.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.reflection(direction, normal, false);
	 * }
	 * </pre>
	 * 
	 * @param direction the {@code Vector3D} instance that will be reflected with regards to {@code normal}
	 * @param normal the {@code Vector3D} instance that represents the normal of the surface
	 * @return a {@code Vector3D} instance that represents the reflection of {@code direction} with regards to {@code normal}
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D reflection(final Vector3D direction, final Vector3D normal) {
		return reflection(direction, normal, false);
	}
	
	/**
	 * Returns a {@code Vector3D} instance that represents the reflection of {@code direction} with regards to {@code normal}.
	 * <p>
	 * If either {@code direction} or {@code normal} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code isFacingSurface} is {@code true}, it is assumed that {@code direction} is facing the surface. This is usually the case for the direction of a ray that intersects the surface. If {@code isFacingSurface} is {@code false}, it is assumed that {@code direction} is pointing in the opposite direction. That is, the ray starts at the surface intersection point and is directed away from the surface.
	 * 
	 * @param direction the {@code Vector3D} instance that will be reflected with regards to {@code normal}
	 * @param normal the {@code Vector3D} instance that represents the normal of the surface
	 * @param isFacingSurface {@code true} if, and only if, {@code direction} is facing the surface, {@code false} otherwise
	 * @return a {@code Vector3D} instance that represents the reflection of {@code direction} with regards to {@code normal}
	 * @throws NullPointerException thrown if, and only if, either {@code direction} or {@code normal} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D reflection(final Vector3D direction, final Vector3D normal, final boolean isFacingSurface) {
		return isFacingSurface ? subtract(direction, multiply(normal, dotProduct(direction, normal) * 2.0D)) : subtract(multiply(normal, dotProduct(direction, normal) * 2.0D), direction);
	}
	
	/**
	 * Samples a direction on a cone with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2D} instance with components in the interval [0.0, 1.0]
	 * @param cosThetaMax the maximum cos theta value
	 * @return a {@code Vector3D} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleConeUniformDistribution(final Point2D p, final double cosThetaMax) {
		final double cosTheta = (1.0D - p.x) + p.x * cosThetaMax;
		final double sinTheta = Doubles.sqrt(1.0D - cosTheta * cosTheta);
		final double phi = Doubles.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Samples a direction on a hemisphere with a cosine distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.sampleHemisphereCosineDistribution(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3D} instance with the sampled direction
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemisphereCosineDistribution() {
		return sampleHemisphereCosineDistribution(Point2D.sampleRandom());
	}
	
	/**
	 * Samples a direction on a hemisphere with a cosine distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3D} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemisphereCosineDistribution(final Point2D p) {
		final Point2D q = Point2D.sampleDiskUniformDistributionByConcentricMapping(p);
		
		return new Vector3D(q.x, q.y, Doubles.sqrt(Doubles.max(0.0D, 1.0D - q.x * q.x - q.y * q.y)));
	}
	
	/**
	 * Samples a direction on a hemisphere with a power-cosine distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.sampleHemispherePowerCosineDistribution(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3D} instance with the sampled direction
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemispherePowerCosineDistribution() {
		return sampleHemispherePowerCosineDistribution(Point2D.sampleRandom());
	}
	
	/**
	 * Samples a direction on a hemisphere with a power-cosine distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.sampleHemispherePowerCosineDistribution(p, 20.0D);
	 * }
	 * </pre>
	 * 
	 * @param p a {@link Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3D} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemispherePowerCosineDistribution(final Point2D p) {
		return sampleHemispherePowerCosineDistribution(p, 20.0D);
	}
	
	/**
	 * Samples a direction on a hemisphere with a power-cosine distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2D} instance with components in the interval [0.0, 1.0]
	 * @param exponent the exponent to use
	 * @return a {@code Vector3D} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemispherePowerCosineDistribution(final Point2D p, final double exponent) {
		final double cosTheta = Doubles.pow(1.0D - p.y, 1.0D / (exponent + 1.0D));
		final double sinTheta = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta));
		final double phi = Doubles.PI_MULTIPLIED_BY_2 * p.x;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Samples a direction on a hemisphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.sampleHemisphereUniformDistribution(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3D} instance with the sampled direction
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemisphereUniformDistribution() {
		return sampleHemisphereUniformDistribution(Point2D.sampleRandom());
	}
	
	/**
	 * Samples a direction on a hemisphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3D} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleHemisphereUniformDistribution(final Point2D p) {
		final double cosTheta = p.x;
		final double sinTheta = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta));
		final double phi = Doubles.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Samples a direction on a sphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.sampleSphereUniformDistribution(Point2D.sampleRandom());
	 * }
	 * </pre>
	 * 
	 * @return a {@code Vector3D} instance with the sampled direction
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleSphereUniformDistribution() {
		return sampleSphereUniformDistribution(Point2D.sampleRandom());
	}
	
	/**
	 * Samples a direction on a sphere with a uniform distribution.
	 * <p>
	 * Returns a {@code Vector3D} instance with the sampled direction.
	 * <p>
	 * If {@code p} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param p a {@link Point2D} instance with components in the interval [0.0, 1.0]
	 * @return a {@code Vector3D} instance with the sampled direction
	 * @throws NullPointerException thrown if, and only if, {@code p} is {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D sampleSphereUniformDistribution(final Point2D p) {
		final double cosTheta = 1.0D - 2.0D * p.x;
		final double sinTheta = Doubles.sqrt(Doubles.max(0.0D, 1.0D - cosTheta * cosTheta));
		final double phi = Doubles.PI_MULTIPLIED_BY_2 * p.y;
		
		return directionSpherical(sinTheta, cosTheta, phi);
	}
	
	/**
	 * Subtracts the component values of {@code vRHS} from the component values of {@code vLHS}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the subtraction.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Vector subtraction is performed componentwise.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return a {@code Vector3D} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static Vector3D subtract(final Vector3D vLHS, final Vector3D vRHS) {
		return new Vector3D(vLHS.x - vRHS.x, vLHS.y - vRHS.y, vLHS.z - vRHS.z);
	}
	
	/**
	 * Transforms the {@link Matrix44D} {@code m} with the {@code Vector3D} {@code v}.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the transformation.
	 * <p>
	 * If either {@code m} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@code Matrix44D} instance
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, either {@code m} or {@code v} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D transform(final Matrix44D m, final Vector3D v) {
		return new Vector3D(m.element11 * v.x + m.element12 * v.y + m.element13 * v.z, m.element21 * v.x + m.element22 * v.y + m.element23 * v.z, m.element31 * v.x + m.element32 * v.y + m.element33 * v.z);
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
	public static Vector3D transform(final Vector3D v, final OrthonormalBasis33D o) {
		return new Vector3D(v.x * o.u.x + v.y * o.v.x + v.z * o.w.x, v.x * o.u.y + v.y * o.v.y + v.z * o.w.y, v.x * o.u.z + v.y * o.v.z + v.z * o.w.z);
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
	public static Vector3D transformNormalize(final Vector3D v, final OrthonormalBasis33D o) {
		return normalize(transform(v, o));
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
	public static Vector3D transformReverse(final Vector3D v, final OrthonormalBasis33D o) {
		return new Vector3D(dotProduct(v, o.u), dotProduct(v, o.v), dotProduct(v, o.w));
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
	public static Vector3D transformReverseNormalize(final Vector3D v, final OrthonormalBasis33D o) {
		return normalize(transformReverse(v, o));
	}
	
	/**
	 * Transforms the {@link Matrix44D} {@code m} with the {@code Vector3D} {@code v} in transpose order.
	 * <p>
	 * Returns a {@code Vector3D} instance with the result of the transformation.
	 * <p>
	 * If either {@code m} or {@code v} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param m a {@code Matrix44D} instance
	 * @param v a {@code Vector3D} instance
	 * @return a {@code Vector3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, either {@code m} or {@code v} are {@code null}
	 */
//	TODO: Add unit tests!
	public static Vector3D transformTranspose(final Matrix44D m, final Vector3D v) {
		return new Vector3D(m.element11 * v.x + m.element21 * v.y + m.element31 * v.z, m.element12 * v.x + m.element22 * v.y + m.element32 * v.z, m.element13 * v.x + m.element23 * v.y + m.element33 * v.z);
	}
	
	/**
	 * Returns a {@code Vector3D} instance equivalent to {@code new Vector3D(1.0D, 0.0D, 0.0D)}.
	 * 
	 * @return a {@code Vector3D} instance equivalent to {@code new Vector3D(1.0D, 0.0D, 0.0D)}
	 */
	public static Vector3D x() {
		return x(1.0D);
	}
	
	/**
	 * Returns a {@code Vector3D} instance equivalent to {@code new Vector3D(x, 0.0D, 0.0D)}.
	 * 
	 * @param x the value of the X-component
	 * @return a {@code Vector3D} instance equivalent to {@code new Vector3D(x, 0.0D, 0.0D)}
	 */
	public static Vector3D x(final double x) {
		return new Vector3D(x, 0.0D, 0.0D);
	}
	
	/**
	 * Returns a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, 1.0D, 0.0D)}.
	 * 
	 * @return a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, 1.0D, 0.0D)}
	 */
	public static Vector3D y() {
		return y(1.0D);
	}
	
	/**
	 * Returns a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, y, 0.0D)}.
	 * 
	 * @param y the value of the Y-component
	 * @return a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, y, 0.0D)}
	 */
	public static Vector3D y(final double y) {
		return new Vector3D(0.0D, y, 0.0D);
	}
	
	/**
	 * Returns a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, 0.0D, 1.0D)}.
	 * 
	 * @return a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, 0.0D, 1.0D)}
	 */
	public static Vector3D z() {
		return z(1.0D);
	}
	
	/**
	 * Returns a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, 0.0D, z)}.
	 * 
	 * @param z the value of the Z-component
	 * @return a {@code Vector3D} instance equivalent to {@code new Vector3D(0.0D, 0.0D, z)}
	 */
	public static Vector3D z(final double z) {
		return new Vector3D(0.0D, 0.0D, z);
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code vLHS} and {@code vRHS} are orthogonal, {@code false} otherwise.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return {@code true} if, and only if, {@code vLHS} and {@code vRHS} are orthogonal, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static boolean orthogonal(final Vector3D vLHS, final Vector3D vRHS) {
		final double dotProduct = dotProduct(vLHS, vRHS);
		
		final boolean isDotProductGTEThreshold = dotProduct >= 0.0D - 0.000001D;
		final boolean isDotProductLTEThreshold = dotProduct <= 0.0D + 0.000001D;
		
		return isDotProductGTEThreshold && isDotProductLTEThreshold;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static boolean sameHemisphere(final Vector3D vLHS, final Vector3D vRHS) {
		return dotProduct(vLHS, vRHS) > 0.0D;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method only operates on the Z-component.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return {@code true} if, and only if, {@code vLHS} and {@code vRHS} are in the same hemisphere, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static boolean sameHemisphereZ(final Vector3D vLHS, final Vector3D vRHS) {
		return vLHS.z * vRHS.z > 0.0D;
	}
	
	/**
	 * Returns the probability density function (PDF) value for {@code cosThetaMax}.
	 * <p>
	 * This method is used together with {@link #sampleConeUniformDistribution(Point2D, double)}.
	 * 
	 * @param cosThetaMax the maximum cos theta value
	 * @return the probability density function (PDF) value for {@code cosThetaMax}
	 */
//	TODO: Add unit tests!
	public static double coneUniformDistributionPDF(final double cosThetaMax) {
		return 1.0D / (Doubles.PI_MULTIPLIED_BY_2 * (1.0D - cosThetaMax));
	}
	
	/**
	 * Returns the dot product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return the dot product of {@code vLHS} and {@code vRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static double dotProduct(final Vector3D vLHS, final Vector3D vRHS) {
		return vLHS.x * vRHS.x + vLHS.y * vRHS.y + vLHS.z * vRHS.z;
	}
	
	/**
	 * Returns the absolute dot product of {@code vLHS} and {@code vRHS}.
	 * <p>
	 * If either {@code vLHS} or {@code vRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param vLHS the {@code Vector3D} instance on the left-hand side
	 * @param vRHS the {@code Vector3D} instance on the right-hand side
	 * @return the absolute dot product of {@code vLHS} and {@code vRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHS} or {@code vRHS} are {@code null}
	 */
	public static double dotProductAbs(final Vector3D vLHS, final Vector3D vRHS) {
		return Doubles.abs(dotProduct(vLHS, vRHS));
	}
	
	/**
	 * Returns the probability density function (PDF) value for {@code cosTheta}.
	 * <p>
	 * This method is used together with {@link #sampleHemisphereCosineDistribution(Point2D)}.
	 * 
	 * @param cosTheta the cos theta value
	 * @return the probability density function (PDF) value for {@code cosTheta}
	 */
//	TODO: Add unit tests!
	public static double hemisphereCosineDistributionPDF(final double cosTheta) {
		return cosTheta * Doubles.PI_RECIPROCAL;
	}
	
	/**
	 * Returns the probability density function (PDF) value.
	 * <p>
	 * This method is used together with {@link #sampleHemisphereUniformDistribution(Point2D)}.
	 * 
	 * @return the probability density function (PDF) value
	 */
//	TODO: Add unit tests!
	public static double hemisphereUniformDistributionPDF() {
		return Doubles.PI_MULTIPLIED_BY_2_RECIPROCAL;
	}
	
	/**
	 * Returns the probability density function (PDF) value.
	 * <p>
	 * This method is used together with {@link #sampleSphereUniformDistribution(Point2D)}.
	 * 
	 * @return the probability density function (PDF) value
	 */
//	TODO: Add unit tests!
	public static double sphereUniformDistributionPDF() {
		return Doubles.PI_MULTIPLIED_BY_4_RECIPROCAL;
	}
	
	/**
	 * Returns the triple product of {@code vLHSDP}, {@code vLHSCP} and {@code vRHSCP}.
	 * <p>
	 * If either {@code vLHSDP}, {@code vLHSCP} or {@code vRHSCP} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Vector3D.dotProduct(vLHSDP, Vector3D.crossProduct(vLHSCP, vRHSCP));
	 * }
	 * </pre>
	 * 
	 * @param vLHSDP the {@code Vector3D} instance on the left-hand side of the dot product
	 * @param vLHSCP the {@code Vector3D} instance on the left-hand side of the cross product
	 * @param vRHSCP the {@code Vector3D} instance on the right-hand side of the cross product
	 * @return the triple product of {@code vLHSDP}, {@code vLHSCP} and {@code vRHSCP}
	 * @throws NullPointerException thrown if, and only if, either {@code vLHSDP}, {@code vLHSCP} or {@code vRHSCP} are {@code null}
	 */
//	TODO: Add unit tests!
	public static double tripleProduct(final Vector3D vLHSDP, final Vector3D vLHSCP, final Vector3D vRHSCP) {
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