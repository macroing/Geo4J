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
package org.macroing.geo4j.bv.aabb;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.geo4j.bv.BoundingVolume3D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.matrix.Matrix44D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Arrays;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;

/**
 * An {@code AxisAlignedBoundingBox3D} is an implementation of {@link BoundingVolume3D} that represents an axis-aligned bounding box (AABB).
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class AxisAlignedBoundingBox3D implements BoundingVolume3D {
	/**
	 * The name used by this {@code AxisAlignedBoundingBox3D} class.
	 */
	public static final String NAME = "Axis Aligned Bounding Box";
	
	/**
	 * The ID used by this {@code AxisAlignedBoundingBox3D} class.
	 */
	public static final int ID = 1;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Point3D max;
	private final Point3D min;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AxisAlignedBoundingBox3D} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new AxisAlignedBoundingBox3D(new Point3D(-0.5D, -0.5D, -0.5D), new Point3D(0.5D, 0.5D, 0.5D));
	 * }
	 * </pre>
	 */
	public AxisAlignedBoundingBox3D() {
		this(new Point3D(-0.5D, -0.5D, -0.5D), new Point3D(0.5D, 0.5D, 0.5D));
	}
	
	/**
	 * Constructs a new {@code AxisAlignedBoundingBox3D} instance.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a reference {@link Point3D}
	 * @param b a reference {@code Point3D}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public AxisAlignedBoundingBox3D(final Point3D a, final Point3D b) {
		this.max = Point3D.getCached(Point3D.max(a, b));
		this.min = Point3D.getCached(Point3D.min(a, b));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Performs a transformation.
	 * <p>
	 * Returns an {@code AxisAlignedBoundingBox3D} instance with the result of the transformation.
	 * <p>
	 * If {@code matrix} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param matrix the {@link Matrix44D} instance to perform the transformation with
	 * @return an {@code AxisAlignedBoundingBox3D} instance with the result of the transformation
	 * @throws NullPointerException thrown if, and only if, {@code matrix} is {@code null}
	 */
	@Override
	public AxisAlignedBoundingBox3D transform(final Matrix44D matrix) {
		final double maxX = this.max.x;
		final double maxY = this.max.y;
		final double maxZ = this.max.z;
		final double minX = this.min.x;
		final double minY = this.min.y;
		final double minZ = this.min.z;
		
		final Point3D[] points = new Point3D[] {
			matrix.transformAndDivide(new Point3D(minX, minY, minZ)),
			matrix.transformAndDivide(new Point3D(maxX, minY, minZ)),
			matrix.transformAndDivide(new Point3D(minX, maxY, minZ)),
			matrix.transformAndDivide(new Point3D(minX, minY, maxZ)),
			matrix.transformAndDivide(new Point3D(minX, maxY, maxZ)),
			matrix.transformAndDivide(new Point3D(maxX, maxY, minZ)),
			matrix.transformAndDivide(new Point3D(maxX, minY, maxZ)),
			matrix.transformAndDivide(new Point3D(maxX, maxY, maxZ))
		};
		
		Point3D max = Point3D.MIN;
		Point3D min = Point3D.MAX;
		
		for(final Point3D point : points) {
			max = Point3D.max(max, point);
			min = Point3D.min(min, point);
		}
		
		return new AxisAlignedBoundingBox3D(max, min);
	}
	
	/**
	 * Returns a {@link Point3D} instance that represents the closest point to {@code point} and is contained in this {@code AxisAlignedBoundingBox3D} instance.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@code Point3D} instance
	 * @return a {@code Point3D} instance that represents the closest point to {@code point} and is contained in this {@code AxisAlignedBoundingBox3D} instance
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public Point3D getClosestPointTo(final Point3D point) {
		final double maxX = this.max.x;
		final double maxY = this.max.y;
		final double maxZ = this.max.z;
		
		final double minX = this.min.x;
		final double minY = this.min.y;
		final double minZ = this.min.z;
		
		final double x = point.x < minX ? minX : point.x > maxX ? maxX : point.x;
		final double y = point.y < minY ? minY : point.y > maxY ? maxY : point.y;
		final double z = point.z < minZ ? minZ : point.z > maxZ ? maxZ : point.z;
		
		return new Point3D(x, y, z);
	}
	
	/**
	 * Returns a {@link Point3D} with the largest component values needed to contain this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return a {@code Point3D} with the largest component values needed to contain this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public Point3D max() {
		return this.max;
	}
	
	/**
	 * Returns a {@link Point3D} with the smallest component values needed to contain this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return a {@code Point3D} with the smallest component values needed to contain this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public Point3D min() {
		return this.min;
	}
	
	/**
	 * Returns a {@code String} with the name of this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return a {@code String} with the name of this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public String getName() {
		return NAME;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return a {@code String} representation of this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public String toString() {
		return String.format("new AxisAlignedBoundingBox3D(%s, %s)", this.max, this.min);
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
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code AxisAlignedBoundingBox3D} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point3D} instance
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code AxisAlignedBoundingBox3D} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point3D point) {
		return point.x >= this.min.x && point.x <= this.max.x && point.y >= this.min.y && point.y <= this.max.y && point.z >= this.min.z && point.z <= this.max.z;
	}
	
	/**
	 * Compares {@code object} to this {@code AxisAlignedBoundingBox3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AxisAlignedBoundingBox3D}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code AxisAlignedBoundingBox3D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AxisAlignedBoundingBox3D}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AxisAlignedBoundingBox3D)) {
			return false;
		} else if(!Objects.equals(this.max, AxisAlignedBoundingBox3D.class.cast(object).max)) {
			return false;
		} else if(!Objects.equals(this.min, AxisAlignedBoundingBox3D.class.cast(object).min)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the surface area of this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return the surface area of this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public double getSurfaceArea() {
		final double x = this.max.x - this.min.x;
		final double y = this.max.y - this.min.y;
		final double z = this.max.z - this.min.z;
		final double surfaceArea = 2.0D * (x * y + y * z + z * x);
		
		return surfaceArea;
	}
	
	/**
	 * Returns the volume of this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return the volume of this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public double getVolume() {
		final double x = this.max.x - this.min.x;
		final double y = this.max.y - this.min.y;
		final double z = this.max.z - this.min.z;
		final double volume = x * y * z;
		
		return volume;
	}
	
	/**
	 * Performs an intersection test between {@code ray} and this {@code AxisAlignedBoundingBox3D} instance.
	 * <p>
	 * Returns {@code t}, the parametric distance from {@code ray} to this {@code AxisAlignedBoundingBox3D} instance, or {@code Double.NaN} if no intersection exists.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@link Ray3D} to perform an intersection test against this {@code AxisAlignedBoundingBox3D} instance
	 * @param tMinimum the minimum parametric distance
	 * @param tMaximum the maximum parametric distance
	 * @return {@code t}, the parametric distance from {@code ray} to this {@code AxisAlignedBoundingBox3D} instance, or {@code Double.NaN} if no intersection exists
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
	@Override
	public double intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
		final Point3D max = max();
		final Point3D min = min();
		final Point3D origin = ray.getOrigin();
		
		final Vector3D direction = ray.getDirection();
		final Vector3D directionReciprocal = Vector3D.reciprocal(direction);
		final Vector3D directionA = Vector3D.hadamardProduct(Vector3D.direction(origin, max), directionReciprocal);
		final Vector3D directionB = Vector3D.hadamardProduct(Vector3D.direction(origin, min), directionReciprocal);
		
		final double t0 = Doubles.max(Doubles.min(directionA.x, directionB.x), Doubles.min(directionA.y, directionB.y), Doubles.min(directionA.z, directionB.z));
		final double t1 = Doubles.min(Doubles.max(directionA.x, directionB.x), Doubles.max(directionA.y, directionB.y), Doubles.max(directionA.z, directionB.z));
		
		return t0 > t1 ? Doubles.NaN : t0 > tMinimum && t0 < tMaximum ? t0 : t1 > tMinimum && t1 < tMaximum ? t1 : Doubles.NaN;
	}
	
	/**
	 * Returns an {@code int} with the ID of this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return an {@code int} with the ID of this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns a hash code for this {@code AxisAlignedBoundingBox3D} instance.
	 * 
	 * @return a hash code for this {@code AxisAlignedBoundingBox3D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.max, this.min);
	}
	
	/**
	 * Writes this {@code AxisAlignedBoundingBox3D} instance to {@code dataOutput}.
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
	 * Returns an {@code AxisAlignedBoundingBox3D} instance that is an expanded version of {@code axisAlignedBoundingBox}.
	 * <p>
	 * If {@code axisAlignedBoundingBox} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param axisAlignedBoundingBox an {@code AxisAlignedBoundingBox3D} instance
	 * @param delta the delta to expand with
	 * @return an {@code AxisAlignedBoundingBox3D} instance that is an expanded version of {@code axisAlignedBoundingBox}
	 * @throws NullPointerException thrown if, and only if, {@code axisAlignedBoundingBox} is {@code null}
	 */
	public static AxisAlignedBoundingBox3D expand(final AxisAlignedBoundingBox3D axisAlignedBoundingBox, final double delta) {
		final Point3D max = Point3D.add(axisAlignedBoundingBox.max, delta);
		final Point3D min = Point3D.subtract(axisAlignedBoundingBox.min, delta);
		
		return new AxisAlignedBoundingBox3D(max, min);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3D} instance that contains all {@link Point3D} instances in {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point3D[]} instance
	 * @return an {@code AxisAlignedBoundingBox3D} instance that contains all {@code Point3D} instances in {@code points}
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static AxisAlignedBoundingBox3D fromPoints(final Point3D... points) {
		Arrays.requireNonNull(points, "points");
		
		Ints.requireRange(points.length, 1, Integer.MAX_VALUE, "points.length");
		
		Point3D max = Point3D.MIN;
		Point3D min = Point3D.MAX;
		
		for(final Point3D point : points) {
			max = Point3D.max(max, point);
			min = Point3D.min(min, point);
		}
		
		return new AxisAlignedBoundingBox3D(max, min);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3D} instance that is the union of {@code boundingVolumeLHS} and {@code boundingVolumeRHS}.
	 * <p>
	 * If either {@code boundingVolumeLHS} or {@code boundingVolumeRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param boundingVolumeLHS a {@link BoundingVolume3D} instance
	 * @param boundingVolumeRHS a {@code BoundingVolume3D} instance
	 * @return an {@code AxisAlignedBoundingBox3D} instance that is the union of {@code boundingVolumeLHS} and {@code boundingVolumeRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code boundingVolumeLHS} or {@code boundingVolumeRHS} are {@code null}
	 */
	public static AxisAlignedBoundingBox3D union(final BoundingVolume3D boundingVolumeLHS, final BoundingVolume3D boundingVolumeRHS) {
		final Point3D max = Point3D.max(boundingVolumeLHS.max(), boundingVolumeRHS.max());
		final Point3D min = Point3D.min(boundingVolumeLHS.min(), boundingVolumeRHS.min());
		
		return new AxisAlignedBoundingBox3D(max, min);
	}
	
	/**
	 * Returns an {@code AxisAlignedBoundingBox3D} instance that is the union of {@code boundingVolumeLHS} and {@code pointRHS}.
	 * <p>
	 * If either {@code boundingVolumeLHS} or {@code pointRHS} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param boundingVolumeLHS a {@link BoundingVolume3D} instance
	 * @param pointRHS a {@code Point3D} instance
	 * @return an {@code AxisAlignedBoundingBox3D} instance that is the union of {@code boundingVolumeLHS} and {@code pointRHS}
	 * @throws NullPointerException thrown if, and only if, either {@code boundingVolumeLHS} or {@code pointRHS} are {@code null}
	 */
	public static AxisAlignedBoundingBox3D union(final BoundingVolume3D boundingVolumeLHS, final Point3D pointRHS) {
		final Point3D max = Point3D.max(boundingVolumeLHS.max(), pointRHS);
		final Point3D min = Point3D.min(boundingVolumeLHS.min(), pointRHS);
		
		return new AxisAlignedBoundingBox3D(max, min);
	}
}