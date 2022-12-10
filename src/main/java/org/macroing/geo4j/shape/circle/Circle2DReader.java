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
package org.macroing.geo4j.shape.circle;

import java.io.DataInput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.shape.Shape2DReader;
import org.macroing.java.lang.Ints;

/**
 * A {@code Circle2DReader} is a {@link Shape2DReader} implementation that reads {@link Circle2D} instances from a {@code DataInput} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Circle2DReader implements Shape2DReader {
	/**
	 * Constructs a new {@code Circle2DReader} instance.
	 */
	public Circle2DReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Reads a {@link Circle2D} instance from {@code dataInput}.
	 * <p>
	 * Returns the {@code Circle2D} instance that was read.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the ID is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return the {@code Circle2D} instance that was read
	 * @throws IllegalArgumentException thrown if, and only if, the ID is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public Circle2D read(final DataInput dataInput) {
		try {
			return read(dataInput, dataInput.readInt());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Reads a {@link Circle2D} instance from {@code dataInput}.
	 * <p>
	 * Returns the {@code Circle2D} instance that was read.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code id} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * The ID of the {@code Circle2D} instance to read has already been read from {@code dataInput} when this method is called. It is passed to this method as a parameter argument.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @param id the ID of the {@code Circle2D} to read
	 * @return the {@code Circle2D} instance that was read
	 * @throws IllegalArgumentException thrown if, and only if, {@code id} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public Circle2D read(final DataInput dataInput, final int id) {
		Objects.requireNonNull(dataInput, "dataInput == null");
		
		Ints.requireRange(id, Circle2D.ID, Circle2D.ID, "id");
		
		try {
			return new Circle2D(Point2D.read(dataInput), dataInput.readDouble());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code id == Circle2D.ID}, {@code false} otherwise.
	 * 
	 * @param id the ID to check
	 * @return {@code true} if, and only if, {@code id == Circle2D.ID}, {@code false} otherwise
	 */
	@Override
	public boolean isSupported(final int id) {
		return id == Circle2D.ID;
	}
}