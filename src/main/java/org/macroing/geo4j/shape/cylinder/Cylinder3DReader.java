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
package org.macroing.geo4j.shape.cylinder;

import java.io.DataInput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.geo4j.common.AngleD;
import org.macroing.geo4j.shape.Shape3DReader;
import org.macroing.java.lang.Ints;

/**
 * A {@code Cylinder3DReader} is a {@link Shape3DReader} implementation that reads {@link Cylinder3D} instances from a {@code DataInput} instance.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Cylinder3DReader implements Shape3DReader {
	/**
	 * Constructs a new {@code Cylinder3DReader} instance.
	 */
	public Cylinder3DReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Reads a {@link Cylinder3D} instance from {@code dataInput}.
	 * <p>
	 * Returns the {@code Cylinder3D} instance that was read.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the ID is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @return the {@code Cylinder3D} instance that was read
	 * @throws IllegalArgumentException thrown if, and only if, the ID is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public Cylinder3D read(final DataInput dataInput) {
		try {
			return read(dataInput, dataInput.readInt());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Reads a {@link Cylinder3D} instance from {@code dataInput}.
	 * <p>
	 * Returns the {@code Cylinder3D} instance that was read.
	 * <p>
	 * If {@code dataInput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code id} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * The ID of the {@code Cylinder3D} instance to read has already been read from {@code dataInput} when this method is called. It is passed to this method as a parameter argument.
	 * 
	 * @param dataInput the {@code DataInput} instance to read from
	 * @param id the ID of the {@code Cylinder3D} to read
	 * @return the {@code Cylinder3D} instance that was read
	 * @throws IllegalArgumentException thrown if, and only if, {@code id} is invalid
	 * @throws NullPointerException thrown if, and only if, {@code dataInput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	@Override
	public Cylinder3D read(final DataInput dataInput, final int id) {
		Objects.requireNonNull(dataInput, "dataInput == null");
		
		Ints.requireExact(id, Cylinder3D.ID, "id");
		
		try {
			return new Cylinder3D(AngleD.read(dataInput), dataInput.readDouble(), dataInput.readDouble(), dataInput.readDouble());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code id == Cylinder3D.ID}, {@code false} otherwise.
	 * 
	 * @param id the ID to check
	 * @return {@code true} if, and only if, {@code id == Cylinder3D.ID}, {@code false} otherwise
	 */
	@Override
	public boolean isSupported(final int id) {
		return id == Cylinder3D.ID;
	}
}