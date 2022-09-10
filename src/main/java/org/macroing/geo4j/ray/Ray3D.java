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

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.lang.reflect.Field;//TODO: Add unit tests!
import java.util.Objects;

import org.macroing.geo4j.point.Point3D;
import org.macroing.geo4j.vector.Vector3D;

//TODO: Add Javadocs!
public final class Ray3D {
	private final Point3D origin;
	private final Vector3D direction;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Ray3D(final Point3D origin, final Vector3D direction) {
		this.origin = Objects.requireNonNull(origin, "origin == null");
		this.direction = Vector3D.normalize(Objects.requireNonNull(direction, "direction == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Point3D getOrigin() {
		return this.origin;
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("new Ray3D(%s, %s)", this.origin, this.direction);
	}
	
//	TODO: Add Javadocs!
	public Vector3D getDirection() {
		return this.direction;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Ray3D)) {
			return false;
		} else {
			return equals(Ray3D.class.cast(object));
		}
	}
	
//	TODO: Add Javadocs!
	public boolean equals(final Ray3D r) {
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
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.origin, this.direction);
	}
}