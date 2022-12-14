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
package org.macroing.geo4j.shape.ls;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Unit Tests!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.geo4j.bv.BoundingVolume3D;
import org.macroing.geo4j.bv.aabb.AxisAlignedBoundingBox3D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.geo4j.shape.Shape3D;
import org.macroing.geo4j.shape.SurfaceIntersection3D;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.Arrays;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;

/**
 * A {@code LineSegment3D} is an implementation of {@link Shape3D} that represents a line segment.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class LineSegment3D implements Shape3D {
	/**
	 * The name of this {@code LineSegment3D} class.
	 */
	public static final String NAME = "Line Segment";
	
	/**
	 * The ID of this {@code LineSegment3D} class.
	 */
	public static final int ID = 9;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final double LINE_WIDTH = Doubles.PI * 0.5D / 4096.0D;
	private static final double LINE_WIDTH_COS = Doubles.cos(LINE_WIDTH);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Point3D a;
	private final Point3D b;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code LineSegment3D} instance given two {@link Point3D} instances, {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Point3D} instance
	 * @param b a {@code Point3D} instance
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public LineSegment3D(final Point3D a, final Point3D b) {
		this.a = Objects.requireNonNull(a, "a == null");
		this.b = Objects.requireNonNull(b, "b == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link BoundingVolume3D} instance that contains this {@code LineSegment3D} instance.
	 * 
	 * @return a {@code BoundingVolume3D} instance that contains this {@code LineSegment3D} instance
	 */
	@Override
	public BoundingVolume3D getBoundingVolume() {
		return new AxisAlignedBoundingBox3D(this.a, this.b);
	}
	
	/**
	 * Performs an intersection test between {@code ray} and this {@code LineSegment3D} instance.
	 * <p>
	 * Returns an {@code Optional} with an optional {@link SurfaceIntersection3D} instance that contains information about the intersection, if it was found.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@link Ray3D} to perform an intersection test against this {@code LineSegment3D} instance
	 * @param tMinimum the minimum parametric distance
	 * @param tMaximum the maximum parametric distance
	 * @return an {@code Optional} with an optional {@code SurfaceIntersection3D} instance that contains information about the intersection, if it was found
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
//	TODO: Add Unit Tests!
	@Override
	public Optional<SurfaceIntersection3D> intersection(final Ray3D ray, final double tMinimum, final double tMaximum) {
		Objects.requireNonNull(ray, "ray == null");
		
//		TODO: Implement!
		return SurfaceIntersection3D.EMPTY;
	}
	
	/**
	 * Returns the {@link Point3D} instance denoted by {@code A}.
	 * 
	 * @return the {@code Point3D} instance denoted by {@code A}
	 */
	public Point3D getA() {
		return this.a;
	}
	
	/**
	 * Returns the {@link Point3D} instance denoted by {@code B}.
	 * 
	 * @return the {@code Point3D} instance denoted by {@code B}
	 */
	public Point3D getB() {
		return this.b;
	}
	
	/**
	 * Returns a {@code String} with the name of this {@code LineSegment3D} instance.
	 * 
	 * @return a {@code String} with the name of this {@code LineSegment3D} instance
	 */
	@Override
	public String getName() {
		return NAME;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code LineSegment3D} instance.
	 * 
	 * @return a {@code String} representation of this {@code LineSegment3D} instance
	 */
	@Override
	public String toString() {
		return String.format("new LineSegment3D(%s, %s)", this.a, this.b);
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
				if(!this.a.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.b.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code LineSegment3D} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point3D} instance
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code LineSegment3D} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point3D point) {
		final Point3D p = Objects.requireNonNull(point, "point == null");
		final Point3D a = this.a;
		final Point3D b = this.b;
		
		if(p.equals(a) || p.equals(b)) {
			return true;
		}
		
		final Vector3D vP = new Vector3D(p);
		final Vector3D vA = new Vector3D(a);
		final Vector3D vB = new Vector3D(b);
		
		final Vector3D vAP = Vector3D.direction(a, p);
		final Vector3D vAB = Vector3D.direction(a, b);
		
		final double t = Vector3D.dotProduct(vAP, vAB) / Point3D.distanceSquared(a, b);
		
		final Vector3D projection = Vector3D.lerp(vB, vA, t);
		
		final boolean contains = Vector3D.dotProduct(projection, vP) / projection.length() / vP.length() >= LINE_WIDTH_COS;
		
		return contains;
	}
	
	/**
	 * Compares {@code object} to this {@code LineSegment3D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code LineSegment3D}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code LineSegment3D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code LineSegment3D}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof LineSegment3D)) {
			return false;
		} else if(!Objects.equals(this.a, LineSegment3D.class.cast(object).a)) {
			return false;
		} else if(!Objects.equals(this.b, LineSegment3D.class.cast(object).b)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the surface area of this {@code LineSegment3D} instance.
	 * <p>
	 * This method returns {@code 0.0D}.
	 * 
	 * @return the surface area of this {@code LineSegment3D} instance
	 */
	@Override
	public double getSurfaceArea() {
		return 0.0D;
	}
	
	/**
	 * Performs an intersection test between {@code ray} and this {@code LineSegment3D} instance.
	 * <p>
	 * Returns {@code t}, the parametric distance to the surface intersection point, or {@code Double.NaN} if no intersection exists.
	 * <p>
	 * If {@code ray} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param ray the {@link Ray3D} to perform an intersection test against this {@code LineSegment3D} instance
	 * @param tMinimum the minimum parametric distance
	 * @param tMaximum the maximum parametric distance
	 * @return {@code t}, the parametric distance to the surface intersection point, or {@code Double.NaN} if no intersection exists
	 * @throws NullPointerException thrown if, and only if, {@code ray} is {@code null}
	 */
//	TODO: Add Unit Tests!
	@Override
	public double intersectionT(final Ray3D ray, final double tMinimum, final double tMaximum) {
		Objects.requireNonNull(ray, "ray == null");
		
		final Vector3D u = ray.getDirection();
		final Vector3D v = Vector3D.direction(this.a, this.b);
		final Vector3D w = Vector3D.direction(this.a, ray.getOrigin());
		
		final double a = Vector3D.dotProduct(u, u);
		final double b = Vector3D.dotProduct(u, v);
		final double c = Vector3D.dotProduct(v, v);
		final double d = Vector3D.dotProduct(u, w);
		final double e = Vector3D.dotProduct(v, w);
		
		final double determinant = a * c - b * b;
		
		double sN;
		double sD = determinant;
		
		double tN;
		double tD = determinant;
		
		if(determinant < Doubles.MACHINE_EPSILON) {
			sN = 0.0D;
			sD = 1.0D;
			
			tN = e;
			tD = c;
		} else {
			sN = b * e - c * d;
			
			tN = a * e - b * d;
			
			if(sN < 0.0D) {
				sN = 0.0D;
				
				tN = e;
				tD = c;
			}
		}
		
		if(tN < 0.0D) {
			tN = 0.0D;
			
			if(-d < 0.0D) {
				sN = 0.0D;
			} else {
				sN = -d;
				sD = +a;
			}
		} else if(tN > tD) {
			tN = tD;
			
			if(-d + b < 0.0D) {
				sN = 0.0D;
			} else {
				sN = -d + b;
				sD = a;
			}
		}
		
		final double sC = Doubles.abs(sN) < Doubles.MACHINE_EPSILON ? 0.0D : sN / sD;
		final double tC = Doubles.abs(tN) < Doubles.MACHINE_EPSILON ? 0.0D : tN / tD;
		
		final Vector3D directionToClosestPoint = Vector3D.subtract(Vector3D.add(w, Vector3D.multiply(u, sC)), Vector3D.multiply(v, tC));
		
		if(Doubles.isZero(directionToClosestPoint.length())) {
			final double t = sC;
			
			if(t <= tMinimum || t >= tMaximum) {
				return Doubles.NaN;
			}
			
			return t;
		}
		
		return Doubles.NaN;
	}
	
	/**
	 * Returns an {@code int} with the ID of this {@code LineSegment3D} instance.
	 * 
	 * @return an {@code int} with the ID of this {@code LineSegment3D} instance
	 */
	@Override
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns a hash code for this {@code LineSegment3D} instance.
	 * 
	 * @return a hash code for this {@code LineSegment3D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.a, this.b);
	}
	
	/**
	 * Writes this {@code LineSegment3D} instance to {@code dataOutput}.
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
			
			this.a.write(dataOutput);
			this.b.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} of {@code LineSegment3D} instances that are connecting the {@link Point3D} instances in {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 2}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point3D[]} instance
	 * @return a {@code List} of {@code LineSegment3D} instances that are connecting the {@code Point3D} instances in {@code points}
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 2}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static List<LineSegment3D> fromPoints(final Point3D... points) {
		Arrays.requireNonNull(points, "points");
		
		Ints.requireRange(points.length, 2, Integer.MAX_VALUE, "points.length");
		
		final List<LineSegment3D> lineSegments = new ArrayList<>(points.length);
		
		for(int i = 0, j = 1; i < points.length; i++, j = (j + 1) % points.length) {
			final Point3D pointI = points[i];
			final Point3D pointJ = points[j];
			
			final LineSegment3D lineSegment = new LineSegment3D(pointI, pointJ);
			
			lineSegments.add(lineSegment);
		}
		
		return lineSegments;
	}
}