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
package org.macroing.geo4j.shape.rectangle;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.shape.Shape2I;
import org.macroing.geo4j.shape.circle.Circle2I;
import org.macroing.geo4j.shape.ls.LineSegment2I;
import org.macroing.java.lang.Doubles;
import org.macroing.java.lang.Floats;
import org.macroing.java.lang.Ints;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;

/**
 * A {@code Rectangle2I} is an implementation of {@link Shape2I} that represents a rectangle.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Rectangle2I implements Shape2I {
	/**
	 * The name used by this {@code Rectangle2I} class.
	 */
	public static final String NAME = "Rectangle";
	
	/**
	 * The ID used by this {@code Rectangle2I} class.
	 */
	public static final int ID = 4;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<LineSegment2I> lineSegments;
	private final Point2I a;
	private final Point2I b;
	private final Point2I c;
	private final Point2I d;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Rectangle2I} instance that contains {@code circle}.
	 * <p>
	 * If {@code circle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param circle a {@link Circle2I} instance
	 * @throws NullPointerException thrown if, and only if, {@code circle} is {@code null}
	 */
	public Rectangle2I(final Circle2I circle) {
		this(new Point2I(circle.getCenter().x - circle.getRadius(), circle.getCenter().y - circle.getRadius()), new Point2I(circle.getCenter().x + circle.getRadius(), circle.getCenter().y + circle.getRadius()));
	}
	
	/**
	 * Constructs a new {@code Rectangle2I} instance based on {@code x} and {@code y}.
	 * <p>
	 * If either {@code x} or {@code y} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x a {@link Point2I} instance
	 * @param y a {@code Point2I} instance
	 * @throws NullPointerException thrown if, and only if, either {@code x} or {@code y} are {@code null}
	 */
	public Rectangle2I(final Point2I x, final Point2I y) {
		this.a = new Point2I(Math.min(x.x, y.x), Math.min(x.y, y.y));
		this.b = new Point2I(Math.max(x.x, y.x), Math.min(x.y, y.y));
		this.c = new Point2I(Math.max(x.x, y.x), Math.max(x.y, y.y));
		this.d = new Point2I(Math.min(x.x, y.x), Math.max(x.y, y.y));
		this.lineSegments = LineSegment2I.fromPoints(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Constructs a new {@code Rectangle2I} instance based on {@code a}, {@code b} and {@code c}.
	 * <p>
	 * If either {@code a}, {@code b} or {@code c} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code a}, {@code b} and {@code c} cannot form a rectangle, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * It is currently not possible to specify {@code a}, {@code b} and {@code c} in counterclockwise order. This will likely be fixed in the future.
	 * 
	 * @param a a {@link Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code a}, {@code b} and {@code c} cannot form a rectangle
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b} or {@code c} are {@code null}
	 */
	public Rectangle2I(final Point2I a, final Point2I b, final Point2I c) {
		this.a = Objects.requireNonNull(a, "a == null");
		this.b = Objects.requireNonNull(b, "b == null");
		this.c = Objects.requireNonNull(c, "c == null");
		this.d = doComputeD(a, b, c);
		this.lineSegments = LineSegment2I.fromPoints(this.a, this.b, this.c, this.d);
		
		doCheckPointValidity(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Constructs a new {@code Rectangle2I} instance based on {@code a}, {@code b}, {@code c} and {@code d}.
	 * <p>
	 * If either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code a}, {@code b}, {@code c} and {@code d} does not form a rectangle, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param a a {@link Point2I} instance
	 * @param b a {@code Point2I} instance
	 * @param c a {@code Point2I} instance
	 * @param d a {@code Point2I} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code a}, {@code b}, {@code c} and {@code d} does not form a rectangle
	 * @throws NullPointerException thrown if, and only if, either {@code a}, {@code b}, {@code c} or {@code d} are {@code null}
	 */
	public Rectangle2I(final Point2I a, final Point2I b, final Point2I c, final Point2I d) {
		doCheckPointValidity(a, b, c, d);
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.lineSegments = LineSegment2I.fromPoints(this.a, this.b, this.c, this.d);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} that contains {@link LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code List} that contains {@code LineSegment2I} instances that connects all {@link Point2I} instances in this {@code Rectangle2I} instance
	 */
	public List<LineSegment2I> getLineSegments() {
		return new ArrayList<>(this.lineSegments);
	}
	
	/**
	 * Returns a {@code List} with {@link Point2I} instances contained in this {@code Rectangle2I} instance.
	 * 
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Rectangle2I} instance, {@code false} otherwise
	 * @return a {@code List} with {@code Point2I} instances contained in this {@code Rectangle2I} instance
	 */
	@Override
	public List<Point2I> findPoints(final boolean isIncludingBorderOnly) {
		final List<Point2I> points = new ArrayList<>();
		
		final Point2I max = max();
		final Point2I min = min();
		
		final int maxX = max.x;
		final int minX = min.x;
		final int maxY = max.y;
		final int minY = min.y;
		
		for(int y = minY; y <= maxY; y++) {
			for(int x = minX; x <= maxX; x++) {
				final Point2I point = new Point2I(x, y);
				
				if(contains(point, isIncludingBorderOnly)) {
					points.add(point);
				}
			}
		}
		
		return points;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by A.
	 * <p>
	 * This {@code Point2I} instance usually contains the minimum X and minimum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by A
	 */
	public Point2I getA() {
		return this.a;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by B.
	 * <p>
	 * This {@code Point2I} instance usually contains the maximum X and minimum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by B
	 */
	public Point2I getB() {
		return this.b;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by C.
	 * <p>
	 * This {@code Point2I} instance usually contains the maximum X and maximum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by C
	 */
	public Point2I getC() {
		return this.c;
	}
	
	/**
	 * Returns the {@link Point2I} instance denoted by D.
	 * <p>
	 * This {@code Point2I} instance usually contains the minimum X and maximum Y component values.
	 * 
	 * @return the {@code Point2I} instance denoted by D
	 */
	public Point2I getD() {
		return this.d;
	}
	
	/**
	 * Returns a {@link Point2I} with the largest component values needed to contain this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code Point2I} with the largest component values needed to contain this {@code Rectangle2I} instance
	 */
	@Override
	public Point2I max() {
		return Point2I.max(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Returns a {@link Point2I} with the smallest component values needed to contain this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code Point2I} with the smallest component values needed to contain this {@code Rectangle2I} instance
	 */
	@Override
	public Point2I min() {
		return Point2I.min(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Returns a {@code String} with the name of this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code String} with the name of this {@code Rectangle2I} instance
	 */
	@Override
	public String getName() {
		return NAME;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Rectangle2I} instance.
	 * 
	 * @return a {@code String} representation of this {@code Rectangle2I} instance
	 */
	@Override
	public String toString() {
		return String.format("new Rectangle2I(%s, %s, %s, %s)", this.a, this.b, this.c, this.d);
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
				for(final LineSegment2I lineSegment : this.lineSegments) {
					if(!lineSegment.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				if(!this.a.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.b.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.c.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.d.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code point} is contained in this {@code Rectangle2I} instance, {@code false} otherwise.
	 * <p>
	 * If {@code point} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param point a {@link Point2I} instance
	 * @param isIncludingBorderOnly {@code true} if, and only if, this method should only include {@code Point2I} instances on the border of this {@code Rectangle2I} instance, {@code false} otherwise
	 * @return {@code true} if, and only if, {@code point} is contained in this {@code Rectangle2I} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code point} is {@code null}
	 */
	@Override
	public boolean contains(final Point2I point, final boolean isIncludingBorderOnly) {
		return isIncludingBorderOnly ? doContainsOnLineSegments(point) : doContains(point) || doContainsOnLineSegments(point);
	}
	
	/**
	 * Compares {@code object} to this {@code Rectangle2I} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Rectangle2I}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Rectangle2I} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Rectangle2I}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Rectangle2I)) {
			return false;
		} else if(!Objects.equals(this.a, Rectangle2I.class.cast(object).a)) {
			return false;
		} else if(!Objects.equals(this.b, Rectangle2I.class.cast(object).b)) {
			return false;
		} else if(!Objects.equals(this.c, Rectangle2I.class.cast(object).c)) {
			return false;
		} else {
			return Objects.equals(this.d, Rectangle2I.class.cast(object).d);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Rectangle2I} instance is axis-aligned, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Rectangle2I} instance is axis-aligned, {@code false} otherwise
	 */
	public boolean isAxisAligned() {
		final boolean isAxisAlignedAB = this.a.y == this.b.y;
		final boolean isAxisAlignedBC = this.b.x == this.c.x;
		final boolean isAxisAlignedCD = this.c.y == this.d.y;
		final boolean isAxisAlignedDA = this.d.x == this.a.x;
		final boolean isAxisAligned = isAxisAlignedAB & isAxisAlignedBC & isAxisAlignedCD & isAxisAlignedDA;//TODO: Using & instead of && to get full code coverage. Should this be fixed?
		
		return isAxisAligned;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Rectangle2I} instance is rotated, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Rectangle2I} instance is rotated, {@code false} otherwise
	 */
	public boolean isRotated() {
		return !isAxisAligned();
	}
	
	/**
	 * Returns an {@code int} with the ID of this {@code Rectangle2I} instance.
	 * 
	 * @return an {@code int} with the ID of this {@code Rectangle2I} instance
	 */
	@Override
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns a hash code for this {@code Rectangle2I} instance.
	 * 
	 * @return a hash code for this {@code Rectangle2I} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.a, this.b, this.c, this.d);
	}
	
	/**
	 * Writes this {@code Rectangle2I} instance to {@code dataOutput}.
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
			this.c.write(dataOutput);
			this.d.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Rectangle2I} instance that contains all {@link Point2I} instances in {@code points}.
	 * <p>
	 * If either {@code points} or an element in {@code points} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code points.length} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param points a {@code Point2I[]} instance
	 * @return a {@code Rectangle2I} instance that contains all {@code Point2I} instances in {@code points}
	 * @throws IllegalArgumentException thrown if, and only if, {@code points.length} is less than {@code 1}
	 * @throws NullPointerException thrown if, and only if, either {@code points} or an element in {@code points} are {@code null}
	 */
	public static Rectangle2I fromPoints(final Point2I... points) {
		if(points.length < 1) {
			throw new IllegalArgumentException("points.length < 1");
		}
		
		Point2I max = Point2I.MIN;
		Point2I min = Point2I.MAX;
		
		for(final Point2I point : points) {
			max = Point2I.max(max, point);
			min = Point2I.min(min, point);
		}
		
		return new Rectangle2I(max, min);
	}
	
	/**
	 * Rotates {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} by {@code angle} degrees or radians around {@code center}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code rectangle} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, either {@code rectangle} or {@code center} are {@code null}
	 */
	public static Rectangle2I rotate(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians, final Point2I center) {
		final double angleRadians = isAngleInRadians ? angle : Doubles.toRadians(angle);
		final double angleCos = Doubles.cos(angleRadians);
		final double angleSin = Doubles.sin(angleRadians);
		
		final Point2I oldA = rectangle.getA();
		final Point2I oldB = rectangle.getB();
		final Point2I oldC = rectangle.getC();
		final Point2I oldD = rectangle.getD();
		
		final int distanceOldAB = Point2I.distance(oldA, oldB);
		final int distanceOldBC = Point2I.distance(oldB, oldC);
		final int distanceOldCD = Point2I.distance(oldC, oldD);
		final int distanceOldDA = Point2I.distance(oldD, oldA);
		
		final Point2I newA = Point2I.rotate(oldA, angleCos, angleSin, center);
		final Point2I newB = Point2I.rotate(oldB, angleCos, angleSin, center);
		final Point2I newC = Point2I.rotate(oldC, angleCos, angleSin, center);
		final Point2I newD = Point2I.rotate(oldD, angleCos, angleSin, center);
		
		final int distanceNewAB = Point2I.distance(newA, newB);
		final int distanceNewBC = Point2I.distance(newB, newC);
		final int distanceNewCD = Point2I.distance(newC, newD);
		final int distanceNewDA = Point2I.distance(newD, newA);
		
//		TODO: Using & instead of && to get full code coverage. Should this be fixed?
		if(distanceOldAB == distanceNewAB & distanceOldBC == distanceNewBC & distanceOldCD == distanceNewCD & distanceOldDA == distanceNewDA) {
			return new Rectangle2I(newA, newB, newC, newD);
		}
		
		/*
		 * The maximum recursion depth for the value 0.48 seems to be 7, given that angle is 1.0 degrees.
		 * Values that are less than or greater than 0.48 require a maximum recursion depth that is greater than 7.
		 * A better approach needs to be found and implemented.
		 */
		
		final double newAngleChangeDegrees = center.equals(oldA) | center.equals(oldB) | center.equals(oldC) | center.equals(oldB) ? 0.12D : 0.48D;
		final double newAngleChange = isAngleInRadians ? Doubles.toRadians(newAngleChangeDegrees) : newAngleChangeDegrees;
		final double newAngle = angle >= 0.0D ? angle + newAngleChange : angle - newAngleChange;
		
		return rotate(rectangle, newAngle, isAngleInRadians, center);
	}
	
	/**
	 * Rotates {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} by {@code angle} degrees or radians around {@code center}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code rectangle} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws NullPointerException thrown if, and only if, either {@code rectangle} or {@code center} are {@code null}
	 */
	public static Rectangle2I rotate(final Rectangle2I rectangle, final float angle, final boolean isAngleInRadians, final Point2I center) {
		final float angleRadians = isAngleInRadians ? angle : Floats.toRadians(angle);
		final float angleCos = Floats.cos(angleRadians);
		final float angleSin = Floats.sin(angleRadians);
		
		final Point2I oldA = rectangle.getA();
		final Point2I oldB = rectangle.getB();
		final Point2I oldC = rectangle.getC();
		final Point2I oldD = rectangle.getD();
		
		final int distanceOldAB = Point2I.distance(oldA, oldB);
		final int distanceOldBC = Point2I.distance(oldB, oldC);
		final int distanceOldCD = Point2I.distance(oldC, oldD);
		final int distanceOldDA = Point2I.distance(oldD, oldA);
		
		final Point2I newA = Point2I.rotate(oldA, angleCos, angleSin, center);
		final Point2I newB = Point2I.rotate(oldB, angleCos, angleSin, center);
		final Point2I newC = Point2I.rotate(oldC, angleCos, angleSin, center);
		final Point2I newD = Point2I.rotate(oldD, angleCos, angleSin, center);
		
		final int distanceNewAB = Point2I.distance(newA, newB);
		final int distanceNewBC = Point2I.distance(newB, newC);
		final int distanceNewCD = Point2I.distance(newC, newD);
		final int distanceNewDA = Point2I.distance(newD, newA);
		
//		TODO: Using & instead of && to get full code coverage. Should this be fixed?
		if(distanceOldAB == distanceNewAB & distanceOldBC == distanceNewBC & distanceOldCD == distanceNewCD & distanceOldDA == distanceNewDA) {
			return new Rectangle2I(newA, newB, newC, newD);
		}
		
		/*
		 * The maximum recursion depth for the value 0.48 seems to be 7, given that angle is 1.0 degrees.
		 * Values that are less than or greater than 0.48 require a maximum recursion depth that is greater than 7.
		 * A better approach needs to be found and implemented.
		 */
		
		final float newAngleChangeDegrees = center.equals(oldA) | center.equals(oldB) | center.equals(oldC) | center.equals(oldB) ? 0.12F : 0.48F;
		final float newAngleChange = isAngleInRadians ? Floats.toRadians(newAngleChangeDegrees) : newAngleChangeDegrees;
		final float newAngle = angle >= 0.0F ? angle + newAngleChange : angle - newAngleChange;
		
		return rotate(rectangle, newAngle, isAngleInRadians, center);
	}
	
	/**
	 * Rotates {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} by {@code angle} degrees around {@code rectangle.getA()}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If {@code rectangle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The returned {@code Rectangle2I} instance may have a resolution that is different from the resolution of {@code rectangle}. However, the {@link LineSegment2I#findPoints()} method for all four {@link LineSegment2I} instances in both {@code Rectangle2I} instances will return the same number of {@link Point2I} instances.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Rectangle2I.rotateABCD(rectangle, angle, false);
	 * }
	 * </pre>
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws IllegalArgumentException thrown if, and only if, {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order
	 * @throws NullPointerException thrown if, and only if, {@code rectangle} is {@code null}
	 */
	public static Rectangle2I rotateABCD(final Rectangle2I rectangle, final double angle) {
		return rotateABCD(rectangle, angle, false);
	}
	
	/**
	 * Rotates {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} by {@code angle} degrees or radians around {@code rectangle.getA()}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If {@code rectangle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The returned {@code Rectangle2I} instance may have a resolution that is different from the resolution of {@code rectangle}. However, the {@link LineSegment2I#findPoints()} method for all four {@link LineSegment2I} instances in both {@code Rectangle2I} instances will return the same number of {@link Point2I} instances.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Rectangle2I.rotateABCD(rectangle, angle, isAngleInRadians, rectangle.getA());
	 * }
	 * </pre>
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws IllegalArgumentException thrown if, and only if, {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order
	 * @throws NullPointerException thrown if, and only if, {@code rectangle} is {@code null}
	 */
	public static Rectangle2I rotateABCD(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
		return rotateABCD(rectangle, angle, isAngleInRadians, rectangle.getA());
	}
	
	/**
	 * Rotates {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} by {@code angle} degrees or radians around {@code center}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code rectangle} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The returned {@code Rectangle2I} instance may have a resolution that is different from the resolution of {@code rectangle}. However, the {@link LineSegment2I#findPoints()} method for all four {@link LineSegment2I} instances in both {@code Rectangle2I} instances will return the same number of {@link Point2I} instances.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws IllegalArgumentException thrown if, and only if, {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order
	 * @throws NullPointerException thrown if, and only if, either {@code rectangle} or {@code center} are {@code null}
	 */
	public static Rectangle2I rotateABCD(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians, final Point2I center) {
		final Point2I[] points = doRotate(angle, isAngleInRadians, center, rectangle.getA(), rectangle.getB(), rectangle.getC());
		
		final Point2I a = points[0];
		final Point2I b = points[1];
		final Point2I c = points[2];
		final Point2I d = points[3];
		
		return new Rectangle2I(a, b, c, d);
	}
	
	/**
	 * Rotates {@code rectangle.getC()}, {@code rectangle.getD()} and {@code rectangle.getA()} by {@code angle} degrees around {@code rectangle.getB()}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If {@code rectangle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The returned {@code Rectangle2I} instance may have a resolution that is different from the resolution of {@code rectangle}. However, the {@link LineSegment2I#findPoints()} method for all four {@link LineSegment2I} instances in both {@code Rectangle2I} instances will return the same number of {@link Point2I} instances.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Rectangle2I.rotateBCDA(rectangle, angle, false);
	 * }
	 * </pre>
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws IllegalArgumentException thrown if, and only if, {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order
	 * @throws NullPointerException thrown if, and only if, {@code rectangle} is {@code null}
	 */
	public static Rectangle2I rotateBCDA(final Rectangle2I rectangle, final double angle) {
		return rotateBCDA(rectangle, angle, false);
	}
	
	/**
	 * Rotates {@code rectangle.getC()}, {@code rectangle.getD()} and {@code rectangle.getA()} by {@code angle} degrees or radians around {@code rectangle.getB()}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If {@code rectangle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The returned {@code Rectangle2I} instance may have a resolution that is different from the resolution of {@code rectangle}. However, the {@link LineSegment2I#findPoints()} method for all four {@link LineSegment2I} instances in both {@code Rectangle2I} instances will return the same number of {@link Point2I} instances.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Rectangle2I.rotateBCDA(rectangle, angle, isAngleInRadians, rectangle.getB());
	 * }
	 * </pre>
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws IllegalArgumentException thrown if, and only if, {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order
	 * @throws NullPointerException thrown if, and only if, {@code rectangle} is {@code null}
	 */
	public static Rectangle2I rotateBCDA(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians) {
		return rotateBCDA(rectangle, angle, isAngleInRadians, rectangle.getB());
	}
	
	/**
	 * Rotates {@code rectangle.getB()}, {@code rectangle.getC()}, {@code rectangle.getD()} and {@code rectangle.getA()} by {@code angle} degrees or radians around {@code center}.
	 * <p>
	 * Returns a new {@code Rectangle2I} instance with the result of the rotation.
	 * <p>
	 * If either {@code rectangle} or {@code center} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The returned {@code Rectangle2I} instance may have a resolution that is different from the resolution of {@code rectangle}. However, the {@link LineSegment2I#findPoints()} method for all four {@link LineSegment2I} instances in both {@code Rectangle2I} instances will return the same number of {@link Point2I} instances.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points up, the rotation is counterclockwise.
	 * <p>
	 * If the coordinate system used is configured such that the X-axis points from left to right and the Y-axis points down, the rotation is clockwise.
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to rotate
	 * @param angle the rotation angle in degrees or radians
	 * @param isAngleInRadians {@code true} if, and only if, {@code angle} is specified in radians, {@code false} otherwise
	 * @param center a {@code Point2I} instance that represents the center of the rotation
	 * @return a new {@code Rectangle2I} instance with the result of the rotation
	 * @throws IllegalArgumentException thrown if, and only if, {@code rectangle.getA()}, {@code rectangle.getB()}, {@code rectangle.getC()} and {@code rectangle.getD()} are specified in counterclockwise order
	 * @throws NullPointerException thrown if, and only if, either {@code rectangle} or {@code center} are {@code null}
	 */
	public static Rectangle2I rotateBCDA(final Rectangle2I rectangle, final double angle, final boolean isAngleInRadians, final Point2I center) {
		final Point2I[] points = doRotate(angle, isAngleInRadians, center, rectangle.getB(), rectangle.getC(), rectangle.getD());
		
		final Point2I a = points[3];
		final Point2I b = points[0];
		final Point2I c = points[1];
		final Point2I d = points[2];
		
		return new Rectangle2I(a, b, c, d);
	}
	
	/**
	 * Translates {@code rectangle} by {@code point}.
	 * <p>
	 * Returns {@code rectangle} or a new {@code Rectangle2I} instance with the result of the translation.
	 * <p>
	 * If either {@code rectangle} or {@code point} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to translate
	 * @param point the {@link Point2I} instance to translate {@code rectangle} with
	 * @return {@code rectangle} or a new {@code Rectangle2I} instance with the result of the translation
	 * @throws NullPointerException thrown if, and only if, either {@code rectangle} or {@code point} are {@code null}
	 */
	public static Rectangle2I translate(final Rectangle2I rectangle, final Point2I point) {
		Objects.requireNonNull(rectangle, "rectangle == null");
		Objects.requireNonNull(point, "point == null");
		
		if(point.x == 0 && point.y == 0) {
			return rectangle;
		}
		
		final Point2I oldA = rectangle.getA();
		final Point2I oldB = rectangle.getB();
		final Point2I oldC = rectangle.getC();
		final Point2I oldD = rectangle.getD();
		
		final Point2I newA = new Point2I(oldA.x + point.x, oldA.y + point.y);
		final Point2I newB = new Point2I(oldB.x + point.x, oldB.y + point.y);
		final Point2I newC = new Point2I(oldC.x + point.x, oldC.y + point.y);
		final Point2I newD = new Point2I(oldD.x + point.x, oldD.y + point.y);
		
		return new Rectangle2I(newA, newB, newC, newD);
	}
	
	/**
	 * Translates {@code rectangle} to origin.
	 * <p>
	 * Returns {@code rectangle} or a new {@code Rectangle2I} instance with the result of the translation.
	 * <p>
	 * If {@code rectangle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param rectangle the {@code Rectangle2I} instance to translate to origin
	 * @return {@code rectangle} or a new {@code Rectangle2I} instance with the result of the translation
	 * @throws NullPointerException thrown if, and only if, {@code rectangle} is {@code null}
	 */
	public static Rectangle2I translateToOrigin(final Rectangle2I rectangle) {
		Objects.requireNonNull(rectangle, "rectangle == null");
		
		final Point2I oldA = rectangle.getA();
		final Point2I oldB = rectangle.getB();
		final Point2I oldC = rectangle.getC();
		final Point2I oldD = rectangle.getD();
		
		boolean hasTranslatedX = false;
		boolean hasTranslatedY = false;
		
		int currentAX = oldA.x;
		int currentAY = oldA.y;
		int currentBX = oldB.x;
		int currentBY = oldB.y;
		int currentCX = oldC.x;
		int currentCY = oldC.y;
		int currentDX = oldD.x;
		int currentDY = oldD.y;
		
		if(currentAX < 0 || currentBX < 0 || currentCX < 0 || currentDX < 0) {
			final int deltaX = 0 - Ints.min(currentAX, currentBX, currentCX, currentDX);
			
			currentAX += deltaX;
			currentBX += deltaX;
			currentCX += deltaX;
			currentDX += deltaX;
			
			hasTranslatedX = true;
		} else if(currentAX > 0 && currentBX > 0 && currentCX > 0 && currentDX > 0) {
			final int deltaX = Ints.min(currentAX, currentBX, currentCX, currentDX);
			
			currentAX -= deltaX;
			currentBX -= deltaX;
			currentCX -= deltaX;
			currentDX -= deltaX;
			
			hasTranslatedX = true;
		}
		
		if(currentAY < 0 || currentBY < 0 || currentCY < 0 || currentDY < 0) {
			final int deltaY = 0 - Ints.min(currentAY, currentBY, currentCY, currentDY);
			
			currentAY += deltaY;
			currentBY += deltaY;
			currentCY += deltaY;
			currentDY += deltaY;
			
			hasTranslatedY = true;
		} else if(currentAY > 0 && currentBY > 0 && currentCY > 0 && currentDY > 0) {
			final int deltaY = Ints.min(currentAY, currentBY, currentCY, currentDY);
			
			currentAY -= deltaY;
			currentBY -= deltaY;
			currentCY -= deltaY;
			currentDY -= deltaY;
			
			hasTranslatedY = true;
		}
		
		if(hasTranslatedX || hasTranslatedY) {
			final Point2I newA = new Point2I(currentAX, currentAY);
			final Point2I newB = new Point2I(currentBX, currentBY);
			final Point2I newC = new Point2I(currentCX, currentCY);
			final Point2I newD = new Point2I(currentDX, currentDY);
			
			return new Rectangle2I(newA, newB, newC, newD);
		}
		
		return rectangle;
	}
	
	/**
	 * Returns a {@code Rectangle2I} instance that is the union of {@code a} and {@code b}.
	 * <p>
	 * If either {@code a} or {@code b} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param a a {@code Rectangle2I} instance
	 * @param b a {@code Rectangle2I} instance
	 * @return a {@code Rectangle2I} instance that is the union of {@code a} and {@code b}
	 * @throws NullPointerException thrown if, and only if, either {@code a} or {@code b} are {@code null}
	 */
	public static Rectangle2I union(final Rectangle2I a, final Rectangle2I b) {
		final Point2I min = Point2I.min(a.min(), b.min());
		final Point2I max = Point2I.max(a.max(), b.max());
		
		return new Rectangle2I(min, max);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean doContains(final Point2I point) {
		boolean isInside = false;
		
		final int pX = point.x;
		final int pY = point.y;
		
		final Point2I[] points = {this.a, this.b, this.c, this.d};
		
		for(int i = 0, j = points.length - 1; i < points.length; j = i++) {
			final Point2I pointI = points[i];
			final Point2I pointJ = points[j];
			
			final int iX = pointI.x;
			final int iY = pointI.y;
			final int jX = pointJ.x;
			final int jY = pointJ.y;
			
			if((iY > pY) != (jY > pY) && pX < (jX - iX) * (pY - iY) / (jY - iY) + iX) {
				isInside = !isInside;
			}
		}
		
		return isInside;
	}
	
	private boolean doContainsOnLineSegments(final Point2I point) {
		for(final LineSegment2I lineSegment : this.lineSegments) {
			if(lineSegment.contains(point)) {
				return true;
			}
		}
		
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Point2I doComputeD(final Point2I a, final Point2I b, final Point2I c) {
		final double aX = a.x;
		final double aY = a.y;
		final double bX = b.x;
		final double bY = b.y;
		final double cX = c.x;
		final double cY = c.y;
		
		final double dABX = bX - aX;
		final double dABY = bY - aY;
		
		final double distance = Doubles.abs(dABY * cX - dABX * cY + bX * aY - bY * aX) / Doubles.sqrt(dABX * dABX + dABY * dABY);
		
		final double perpendicularX = -dABY;
		final double perpendicularY = +dABX;
		final double perpendicularLength = Doubles.sqrt(perpendicularX * perpendicularX + perpendicularY * perpendicularY);
		final double perpendicularNormalizedX = perpendicularX / perpendicularLength;
		final double perpendicularNormalizedY = perpendicularY / perpendicularLength;
		
		final double dX = aX + perpendicularNormalizedX * distance;
		final double dY = aY + perpendicularNormalizedY * distance;
		
		final int x = (int)(Doubles.rint(dX));
		final int y = (int)(Doubles.rint(dY));
		
		return new Point2I(x, y);
	}
	
	private static Point2I[] doRotate(final double angle, final boolean isAngleInRadians, final Point2I center, final Point2I oldA, final Point2I oldB, final Point2I oldC) {
		/*
		 * Rotate the old A-point around the center point:
		 */
		
		final Point2I newA = Point2I.rotate(oldA, angle, isAngleInRadians, center);
		
		final int newAX = newA.x;
		final int newAY = newA.y;
		
		/*
		 * Rotate the old B-point around the old A-point and update it with regards to the new A-point:
		 */
		
		final Point2I newB = Point2I.rotate(oldB, angle, isAngleInRadians, oldA);
		
		final int oldDeltaABX = oldB.x - oldA.x;
		final int oldDeltaABXAbs = Ints.abs(oldDeltaABX);
		final int oldDeltaABY = oldB.y - oldA.y;
		final int oldDeltaABYAbs = Ints.abs(oldDeltaABY);
		
		final int newDeltaABX = newB.x - oldA.x;
		final int newDeltaABXAbs = Ints.abs(newDeltaABX);
		final int newDeltaABY = newB.y - oldA.y;
		final int newDeltaABYAbs = Ints.abs(newDeltaABY);
		
		final int newBIncrement0X = newDeltaABX < 0 ? -1 : newDeltaABX > 0 ? 1 : 0;
		final int newBIncrement0Y = newDeltaABY < 0 ? -1 : newDeltaABY > 0 ? 1 : 0;
		
		final int newBIncrement1X = newDeltaABXAbs > newDeltaABYAbs ? newBIncrement0X : 0;
		final int newBIncrement1Y = newDeltaABXAbs > newDeltaABYAbs ? 0 : newBIncrement0Y;
		
		final int oldBL = oldDeltaABXAbs > oldDeltaABYAbs ? oldDeltaABXAbs : oldDeltaABYAbs;
		
		final int newBL = newDeltaABXAbs > newDeltaABYAbs ? newDeltaABXAbs : newDeltaABYAbs;
		final int newBS = newDeltaABXAbs > newDeltaABYAbs ? newDeltaABYAbs : newDeltaABXAbs;
		
		int nB = newBL >> 1;
		
		int newBX = newAX;
		int newBY = newAY;
		
		int oldBX = newAX;
		int oldBY = newAY;
		
		for(int i = 0; i <= oldBL; i++) {
			newBX = oldBX;
			newBY = oldBY;
			
			nB += newBS;
			
			if(nB >= newBL) {
				nB -= newBL;
				
				oldBX += newBIncrement0X;
				oldBY += newBIncrement0Y;
			} else {
				oldBX += newBIncrement1X;
				oldBY += newBIncrement1Y;
			}
		}
		
		/*
		 * Rotate the old C-point around the old B-point and update it with regards to the new B-point:
		 */
		
		final Point2I newC = Point2I.rotate(oldC, angle, isAngleInRadians, oldB);
		
		final int oldDeltaBCX = oldC.x - oldB.x;
		final int oldDeltaBCXAbs = Ints.abs(oldDeltaBCX);
		final int oldDeltaBCY = oldC.y - oldB.y;
		final int oldDeltaBCYAbs = Ints.abs(oldDeltaBCY);
		
		final int newDeltaBCX = newC.x - oldB.x;
		final int newDeltaBCXAbs = Ints.abs(newDeltaBCX);
		final int newDeltaBCY = newC.y - oldB.y;
		final int newDeltaBCYAbs = Ints.abs(newDeltaBCY);
		
		final int newCIncrement0X = newDeltaBCX < 0 ? -1 : newDeltaBCX > 0 ? 1 : 0;
		final int newCIncrement0Y = newDeltaBCY < 0 ? -1 : newDeltaBCY > 0 ? 1 : 0;
		
		final int newCIncrement1X = newDeltaBCXAbs > newDeltaBCYAbs ? newCIncrement0X : 0;
		final int newCIncrement1Y = newDeltaBCXAbs > newDeltaBCYAbs ? 0 : newCIncrement0Y;
		
		final int oldCL = oldDeltaBCXAbs > oldDeltaBCYAbs ? oldDeltaBCXAbs : oldDeltaBCYAbs;
		
		final int newCL = newDeltaBCXAbs > newDeltaBCYAbs ? newDeltaBCXAbs : newDeltaBCYAbs;
		final int newCS = newDeltaBCXAbs > newDeltaBCYAbs ? newDeltaBCYAbs : newDeltaBCXAbs;
		
		int nC = newCL >> 1;
		
		int newCX = newBX;
		int newCY = newBY;
		
		int oldCX = newBX;
		int oldCY = newBY;
		
		for(int i = 0; i <= oldCL; i++) {
			newCX = oldCX;
			newCY = oldCY;
			
			nC += newCS;
			
			if(nC >= newCL) {
				nC -= newCL;
				
				oldCX += newCIncrement0X;
				oldCY += newCIncrement0Y;
			} else {
				oldCX += newCIncrement1X;
				oldCY += newCIncrement1Y;
			}
		}
		
		/*
		 * Construct and return a new Point2I[]:
		 */
		
		final Point2I a = newA;
		final Point2I b = new Point2I(newBX, newBY);
		final Point2I c = new Point2I(newCX, newCY);
		final Point2I d = doComputeD(a, b, c);
		
		return new Point2I[] {a, b, c, d};
	}
	
	private static void doCheckPointValidity(final Point2I a, final Point2I b, final Point2I c, final Point2I d) {
		Objects.requireNonNull(a, "a == null");
		Objects.requireNonNull(b, "b == null");
		Objects.requireNonNull(c, "c == null");
		Objects.requireNonNull(d, "d == null");
		
		final int distanceAB = Point2I.distance(a, b);
		final int distanceBC = Point2I.distance(b, c);
		final int distanceCD = Point2I.distance(c, d);
		final int distanceDA = Point2I.distance(d, a);
		
		final int deltaABCD = Math.abs(distanceAB - distanceCD);
		final int deltaBCDA = Math.abs(distanceBC - distanceDA);
		
		final boolean isValidABCD = deltaABCD == 0;
		final boolean isValidBCDA = deltaBCDA == 0;
		final boolean isValid = isValidABCD & isValidBCDA;//TODO: Using & instead of && to get full code coverage. Should this be fixed?
		
		if(!isValid) {
			throw new IllegalArgumentException(String.format("The distances %s, %s, %s and %s are invalid.", Integer.toString(distanceAB), Integer.toString(distanceBC), Integer.toString(distanceCD), Integer.toString(distanceDA)));
		}
	}
}