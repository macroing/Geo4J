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
package org.macroing.geo4j.ray;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.common.Vector2D;
import org.macroing.java.util.visitor.Node;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;

/**
 * A {@code Ray2D} represents a 2-dimensional ray with a point of type {@link Point2D} and a vector of type {@link Vector2D}.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Ray2D implements Node {
	private final Point2D origin;
	private final Vector2D direction;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Ray2D} instance given {@code origin} and {@code direction}.
	 * <p>
	 * If either {@code origin} or {@code direction} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param origin a {@link Point2D} instance to represent the origin
	 * @param direction a {@link Vector2D} instance to represent the direction
	 * @throws NullPointerException thrown if, and only if, either {@code origin} or {@code direction} are {@code null}
	 */
	public Ray2D(final Point2D origin, final Vector2D direction) {
		this.origin = Objects.requireNonNull(origin, "origin == null");
		this.direction = Vector2D.normalize(Objects.requireNonNull(direction, "direction == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Point2D} instance used by this {@code Ray2D} instance to represent its origin.
	 * 
	 * @return the {@code Point2D} instance used by this {@code Ray2D} instance to represent its origin
	 */
	public Point2D getOrigin() {
		return this.origin;
	}
	
	/**
	 * Returns a {@link Point2D} instance given the parametric distance {@code t}.
	 * 
	 * @param t the parametric distance
	 * @return a {@code Point2D} instance given the parametric distance {@code t}
	 */
	public Point2D getPointAt(final double t) {
		return Point2D.add(this.origin, this.direction, t);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Ray2D} instance.
	 * 
	 * @return a {@code String} representation of this {@code Ray2D} instance
	 */
	@Override
	public String toString() {
		return String.format("new Ray2D(%s, %s)", this.origin, this.direction);
	}
	
	/**
	 * Returns the {@link Vector2D} instance used by this {@code Ray2D} instance to represent its direction.
	 * 
	 * @return the {@code Vector2D} instance used by this {@code Ray2D} instance to represent its direction
	 */
	public Vector2D getDirection() {
		return this.direction;
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
				if(!this.origin.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				if(!this.direction.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code Ray2D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Ray2D}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Ray2D} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Ray2D}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Ray2D)) {
			return false;
		} else {
			return equals(Ray2D.class.cast(object));
		}
	}
	
	/**
	 * Compares {@code r} to this {@code Ray2D} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code r} is not {@code null} and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param r the {@code Ray2D} instance to compare to this {@code Ray2D} instance for equality
	 * @return {@code true} if, and only if, {@code r} is not {@code null} and their respective values are equal, {@code false} otherwise
	 */
	public boolean equals(final Ray2D r) {
		if(r == this) {
			return true;
		} else if(r == null) {
			return false;
		} else if(!Objects.equals(this.origin, r.origin)) {
			return false;
		} else if(!Objects.equals(this.direction, r.direction)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Ray2D} instance.
	 * 
	 * @return a hash code for this {@code Ray2D} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.origin, this.direction);
	}
	
	/**
	 * Writes this {@code Ray2D} instance to {@code dataOutput}.
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
		this.origin.write(dataOutput);
		this.direction.write(dataOutput);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Ray2D} instance by reading it from {@code dataInput}.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return a {@code Ray2D} instance by reading it from {@code dataInput}
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Ray2D read(final DataInput dataInput) {
		return new Ray2D(Point2D.read(dataInput), Vector2D.read(dataInput));
	}
}