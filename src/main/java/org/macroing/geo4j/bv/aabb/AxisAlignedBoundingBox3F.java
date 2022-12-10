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
package org.macroing.geo4j.bv.aabb;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.geo4j.bv.BoundingVolume3F;
import org.macroing.geo4j.common.Point3F;
import org.macroing.geo4j.common.Vector3F;
import org.macroing.geo4j.matrix.Matrix44F;
import org.macroing.geo4j.ray.Ray3F;
import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Arrays;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;

/**
 * An {@code AxisAlignedBoundingBox3F} is an implementation of {@link BoundingVolume3F} that represents an axis-aligned bounding box (AABB).
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class AxisAlignedBoundingBox3F implements BoundingVolume3F {
	/**
	 * The name used by this {@code AxisAlignedBoundingBox3F} class.
	 */
	public static final String NAME = "Axis Aligned Bounding Box";
	
	/**
	 * The ID used by this {@code AxisAlignedBoundingBox3F} class.
	 */
	public static final int ID = 1;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Point3F max;
	private final Point3F min;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AxisAlignedBoundingBox3F} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new AxisAlignedBoundingBox3F(new Point3F(-0.5F, -0.5F, -0.5F), new Point3F(0.5F, 0.5F, 0.5F));
	 * }
	 * </pre>
	 */
	public AxisAlignedBoundingBox3F() {
		this(new Point3F(-0.5F, -0.5F, -0.5F), new Point3F(0.5F, 0.5F, 0.5F));
	}
	
	/**
	 * Constructs a new {@code AxisAlignedBoundingBox3F} instance.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a reference {@link Point3F}
	 * @param b a reference {@code Point3F}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public AxisAlignedBoundingBox3F(final Point3F a, final Point3F b) {
		this.max = Point3F.getCached(Point3F.max(a, b));
		this.min = Point3F.getCached(Point3F.min(a, b));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Performs a transformation.
	 * <p>
	 * Returns an {@code AxisAlignedBoundingBox3F} instance with the result of the transformation.
	 * <p>
	 * If {@code matrix} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param matrix the {@link Matrix44F} instance to perform the transformation with
	 * @return an {@code AxisAlignedBoundingBox3F} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code matrix} is {@code null}
	 */
	@Override
	public AxisAlignedBoundingBox3F transform(final Matrix44F matrix) {
		final float maxX = this.max.x;
		final float maxY = this.max.y;
		final float maxZ = this.max.z;
		final float minX = this.min.x;
		final float minY = this.min.y;
		final float minZ = this.min.z;
		
		final Point3F[] points = new Point3F[] {
			matrix.transformAndDivide(new Point3F(minX, minY, minZ)),
			matrix.transformAndDivide(new Point3F(maxX, minY, minZ)),
			matrix.transformAndDivide(new Point3F(minX, maxY, minZ)),
			matrix.transformAndDivide(new Point3F(minX, minY, maxZ)),
			matrix.transformAndDivide(new Point3F(minX, maxY, maxZ)),
			matrix.transformAndDivide(new Point3F(maxX, maxY, minZ)),
			matrix.transformAndDivide(new Point3F(maxX, minY, maxZ)),
			matrix.transformAndDivide(new Point3F(maxX, maxY, maxZ))
		};
		
		Point3F max = Point3F.MIN;
		Point3F min = Point3F.MAX;
		
		for(final Point3F point : points) {
			max = Point3F.max(max, point);
			min = Point3F.min(min, point);
		}
		
		return new AxisAlignedBoundingBox3F(max, min);
	}
	
	/**
	 * Returns a {@link Point3F} instance that represents the closest point to {@code point} and is contained in this {@code AxisAlignedBoundingBox3F} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@code Point3F} instance
	 * @return a {@code Point3F} instance that represents the closest point to {@code point} and is contained in this {@code AxisAlignedBoundingBox3F} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public Point3F getClosestPointTo(final Point3F point) {
		final float maxX = this.max.x;
		final float maxY = this.max.y;
		final float maxZ = this.max.z;
		
		final float minX = this.min.x;
		final float minY = this.min.y;
		final float minZ = this.min.z;
		
		final float x = point.x < minX ? minX : point.x > maxX ? maxX : point.x;
		final float y = point.y < minY ? minY : point.y > maxY ? maxY : point.y;
		final float z = point.z < minZ ? minZ : point.z > maxZ ? maxZ : point.z;
		
		return new Point3F(x, y, z);
	}
	
	/**
	 * Returns a {@link Point3F} with the largest component values needed to contain this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return a {@code Point3F} with the largest component values needed to contain this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public Point3F max() {
		return this.max;
	}
	
	/**
	 * Returns a {@link Point3F} with the smallest component values needed to contain this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return a {@code Point3F} with the smallest component values needed to contain this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public Point3F min() {
		return this.min;
	}
	
	/**
	 * Returns a {@code String} with the name of this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return a {@code String} with the name of this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public String getName() {
		return NAME;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return a {@code String} representation of this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public String toString() {
		return String.format("new AxisAlignedBoundingBox3F(%s, %s)", this.max, this.min);
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node} instances.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.max.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.min.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code AxisAlignedBoundingBox3F} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point3F} instance
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code AxisAlignedBoundingBox3F} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point3F point) {
		return point.x >= this.min.x && point.x <= this.max.x && point.y >= this.min.y && point.y <= this.max.y && point.z >= this.min.z && point.z <= this.max.z;
	}
	
	/**
	 * Compares {@code object} to this {@code AxisAlignedBoundingBox3F} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AxisAlignedBoundingBox3F}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code AxisAlignedBoundingBox3F} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AxisAlignedBoundingBox3F}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AxisAlignedBoundingBox3F)) {
			return false;
		} else if(!Objects.equals(this.max, AxisAlignedBoundingBox3F.class.cast(object).max)) {
			return false;
		} else if(!Objects.equals(this.min, AxisAlignedBoundingBox3F.class.cast(object).min)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the surface area of this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return the surface area of this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public float getSurfaceArea() {
		final float x = this.max.x - this.min.x;
		final float y = this.max.y - this.min.y;
		final float z = this.max.z - this.min.z;
		final float surfaceArea = 2.0F * (x * y + y * z + z * x);
		
		return surfaceArea;
	}
	
	/**
	 * Returns the volume of this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return the volume of this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public float getVolume() {
		final float x = this.max.x - this.min.x;
		final float y = this.max.y - this.min.y;
		final float z = this.max.z - this.min.z;
		final float volume = x * y * z;
		
		return volume;
	}
	
	/**
	 * Performs an intersection test between {@code ray} and this {@code AxisAlignedBoundingBox3F} instance.
	 * <p>
	 * Returns {@code t}, the parametric distance from {@code ray} to this {@code AxisAlignedBoundingBox3F} instance, or {@code Float.NaN} if no intersection exists.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@link Ray3F} to perform an intersection test against this {@code AxisAlignedBoundingBox3F} instance
	 * @param tMinimum the minimum parametric distance
	 * @param tMaximum the maximum parametric distance
	 * @return {@code t}, the parametric distance from {@code ray} to this {@code AxisAlignedBoundingBox3F} instance, or {@code Float.NaN} if no intersection exists
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
	@Override
	public float intersection(final Ray3F ray, final float tMinimum, final float tMaximum) {
		final Point3F max = max();
		final Point3F min = min();
		final Point3F origin = ray.getOrigin();
		
		final Vector3F direction = ray.getDirection();
		final Vector3F directionReciprocal = Vector3F.reciprocal(direction);
		final Vector3F directionA = Vector3F.hadamardProduct(Vector3F.direction(origin, max), directionReciprocal);
		final Vector3F directionB = Vector3F.hadamardProduct(Vector3F.direction(origin, min), directionReciprocal);
		
		final float t0 = Floats.max(Floats.min(directionA.x, directionB.x), Floats.min(directionA.y, directionB.y), Floats.min(directionA.z, directionB.z));
		final float t1 = Floats.min(Floats.max(directionA.x, directionB.x), Floats.max(directionA.y, directionB.y), Floats.max(directionA.z, directionB.z));
		
		return t0 > t1 ? Floats.NaN : t0 > tMinimum && t0 < tMaximum ? t0 : t1 > tMinimum && t1 < tMaximum ? t1 : Floats.NaN;
	}
	
	/**
	 * Returns an {@code int} with the ID of this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return an {@code int} with the ID of this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns a hash code for this {@code AxisAlignedBoundingBox3F} instance.
	 * 
	 * @return a hash code for this {@code AxisAlignedBoundingBox3F} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.max, this.min);
	}
	
	/**
	 * Writes this {@code AxisAlignedBoundingBox3F} instance to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} instance to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeInt(ID);
			
			this.max.write(dataOutput);
			this.min.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3F} instance that is an expanded version of {@code axisAlignedBoundingBox}.
	 * <p>
	 * If {@code axisAlignedBoundingBox} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param axisAlignedBoundingBox an {@code AxisAlignedBoundingBox3F} instance
	 * @param delta the delta to expand with
	 * @return an {@code AxisAlignedBoundingBox3F} instance that is an expanded version of {@code axisAlignedBoundingBox}
	 * @throws NullPointerException thrown if, and only if, {@code axisAlignedBoundingBox} is {@code null}
	 */
	public static AxisAlignedBoundingBox3F expand(final AxisAlignedBoundingBox3F axisAlignedBoundingBox, final float delta) {
		final Point3F max = Point3F.add(axisAlignedBoundingBox.max, delta);
		final Point3F min = Point3F.subtract(axisAlignedBoundingBox.min, delta);
		
		return new AxisAlignedBoundingBox3F(max, min);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3F} instance that contains all {@link Point3F} instances in {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point3F[]} instance
	 * @return an {@code AxisAlignedBoundingBox3F} instance that contains all {@code Point3F} instances in {@code points}
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static AxisAlignedBoundingBox3F fromPoints(final Point3F... points) {
		Arrays.requireNonNull(points, "points");
		
		Ints.requireRange(points.length, 1, Integer.MAX_VALUE, "points.length");
		
		Point3F max = Point3F.MIN;
		Point3F min = Point3F.MAX;
		
		for(final Point3F point : points) {
			max = Point3F.max(max, point);
			min = Point3F.min(min, point);
		}
		
		return new AxisAlignedBoundingBox3F(max, min);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3F} instance that is the union of {@code boundingVolumeLHS} and {@code boundingVolumeRHS}.
	 * <p>
	 * If either {@code boundingVolumeLHS} or {@code boundingVolumeRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param boundingVolumeLHS a {@link BoundingVolume3F} instance
	 * @param boundingVolumeRHS a {@code BoundingVolume3F} instance
	 * @return an {@code AxisAlignedBoundingBox3F} instance that is the union of {@code boundingVolumeLHS} and {@code boundingVolumeRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code boundingVolumeLHS} or {@code boundingVolumeRHS} are {@code null}
	 */
	public static AxisAlignedBoundingBox3F union(final BoundingVolume3F boundingVolumeLHS, final BoundingVolume3F boundingVolumeRHS) {
		final Point3F max = Point3F.max(boundingVolumeLHS.max(), boundingVolumeRHS.max());
		final Point3F min = Point3F.min(boundingVolumeLHS.min(), boundingVolumeRHS.min());
		
		return new AxisAlignedBoundingBox3F(max, min);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3F} instance that is the union of {@code boundingVolumeLHS} and {@code pointRHS}.
	 * <p>
	 * If either {@code boundingVolumeLHS} or {@code pointRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param boundingVolumeLHS a {@link BoundingVolume3F} instance
	 * @param pointRHS a {@code Point3F} instance
	 * @return an {@code AxisAlignedBoundingBox3F} instance that is the union of {@code boundingVolumeLHS} and {@code pointRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code boundingVolumeLHS} or {@code pointRHS} are {@code null}
	 */
	public static AxisAlignedBoundingBox3F union(final BoundingVolume3F boundingVolumeLHS, final Point3F pointRHS) {
		final Point3F max = Point3F.max(boundingVolumeLHS.max(), pointRHS);
		final Point3F min = Point3F.min(boundingVolumeLHS.min(), pointRHS);
		
		return new AxisAlignedBoundingBox3F(max, min);
	}
}