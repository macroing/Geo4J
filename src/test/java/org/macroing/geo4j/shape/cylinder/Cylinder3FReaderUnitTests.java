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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.UncheckedIOException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public final class Cylinder3FReaderUnitTests {
	public Cylinder3FReaderUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testIsSupported() {
		final Cylinder3FReader cylinder3FReader = new Cylinder3FReader();
		
		assertTrue(cylinder3FReader.isSupported(Cylinder3F.ID));
		
		assertFalse(cylinder3FReader.isSupported(0));
	}
	
	@Test
	public void testReadDataInput() {
		final Cylinder3FReader cylinder3FReader = new Cylinder3FReader();
		
		final Cylinder3F cylinderA = new Cylinder3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		cylinderA.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Cylinder3F cylinderB = cylinder3FReader.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(cylinderA, cylinderB);
		
		assertThrows(IllegalArgumentException.class, () -> cylinder3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0}))));
		assertThrows(NullPointerException.class, () -> cylinder3FReader.read(null));
		assertThrows(UncheckedIOException.class, () -> cylinder3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testReadDataInputInt() {
		final Cylinder3FReader cylinder3FReader = new Cylinder3FReader();
		
		assertThrows(IllegalArgumentException.class, () -> cylinder3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {})), 0));
		assertThrows(NullPointerException.class, () -> cylinder3FReader.read(null, Cylinder3F.ID));
		assertThrows(UncheckedIOException.class, () -> cylinder3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})), Cylinder3F.ID));
	}
}