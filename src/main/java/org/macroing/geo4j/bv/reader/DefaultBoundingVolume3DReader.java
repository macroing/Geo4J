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
package org.macroing.geo4j.bv.reader;

import java.io.DataInput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.macroing.geo4j.bv.BoundingVolume3D;
import org.macroing.geo4j.bv.BoundingVolume3DReader;
import org.macroing.geo4j.bv.aabb.AxisAlignedBoundingBox3D;
import org.macroing.geo4j.bv.aabb.AxisAlignedBoundingBox3DReader;
import org.macroing.geo4j.bv.bs.BoundingSphere3D;
import org.macroing.geo4j.bv.bs.BoundingSphere3DReader;
import org.macroing.geo4j.bv.ibv.InfiniteBoundingVolume3D;
import org.macroing.geo4j.bv.ibv.InfiniteBoundingVolume3DReader;

/**
 * A {@code DefaultBoundingVolume3DReader} is a {@link BoundingVolume3DReader} implementation that reads all official {@link BoundingVolume3D} instances from a {@code DataInput} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DefaultBoundingVolume3DReader implements BoundingVolume3DReader {
	private final Map<Integer, BoundingVolume3DReader> boundingVolume3DReaders;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code DefaultBoundingVolume3DReader} instance.
	 */
	public DefaultBoundingVolume3DReader() {
		this.boundingVolume3DReaders = new LinkedHashMap<>();
		this.boundingVolume3DReaders.put(Integer.valueOf(AxisAlignedBoundingBox3D.ID), new AxisAlignedBoundingBox3DReader());
		this.boundingVolume3DReaders.put(Integer.valueOf(BoundingSphere3D.ID), new BoundingSphere3DReader());
		this.boundingVolume3DReaders.put(Integer.valueOf(InfiniteBoundingVolume3D.ID), new InfiniteBoundingVolume3DReader());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Reads a {@link BoundingVolume3D} instance from {@code dataInput}.
	 * <p>
	 * Returns the {@code BoundingVolume3D} instance that was read.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the ID is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return the {@code BoundingVolume3D} instance that was read
	 * @throws IllegalArgumentException thrown if, and only if, the ID is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public BoundingVolume3D read(final DataInput dataInput) {
		try {
			return read(dataInput, dataInput.readInt());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Reads a {@link BoundingVolume3D} instance from {@code dataInput}.
	 * <p>
	 * Returns the {@code BoundingVolume3D} instance that was read.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code id} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * The ID of the {@code BoundingVolume3D} instance to read has already been read from {@code dataInput} when this method is called. It is passed to this method as a parameter argument.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @param id the ID of the {@code BoundingVolume3D} type to read
	 * @return the {@code BoundingVolume3D} instance that was read
	 * @throws IllegalArgumentException thrown if, and only if, {@code id} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public BoundingVolume3D read(final DataInput dataInput, final int id) {
		Objects.requireNonNull(dataInput, "dataInput == null");
		
		switch(id) {
			case AxisAlignedBoundingBox3D.ID:
			case BoundingSphere3D.ID:
			case InfiniteBoundingVolume3D.ID:
				return this.boundingVolume3DReaders.get(Integer.valueOf(id)).read(dataInput, id);
			default:
				throw new IllegalArgumentException(String.format("The ID %d is invalid.", Integer.valueOf(id)));
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code DefaultBoundingVolume3DReader} instance supports reading {@link BoundingVolume3D} instances with an ID of {@code id}, {@code false} otherwise.
	 * 
	 * @param id the ID of the {@code BoundingVolume3D} type to check
	 * @return {@code true} if, and only if, this {@code DefaultBoundingVolume3DReader} instance supports reading {@code BoundingVolume3D} instances with an ID of {@code id}, {@code false} otherwise
	 */
	@Override
	public boolean isSupported(final int id) {
		return this.boundingVolume3DReaders.containsKey(Integer.valueOf(id));
	}
}