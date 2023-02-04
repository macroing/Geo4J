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
package org.macroing.geo4j.shape.torus;

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
public final class Torus3FReaderUnitTests {
	public Torus3FReaderUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testIsSupported() {
		final Torus3FReader torus3FReader = new Torus3FReader();
		
		assertTrue(torus3FReader.isSupported(Torus3F.ID));
		
		assertFalse(torus3FReader.isSupported(0));
	}
	
	@Test
	public void testReadDataInput() {
		final Torus3FReader torus3FReader = new Torus3FReader();
		
		final Torus3F torusA = new Torus3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		torusA.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Torus3F torusB = torus3FReader.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(torusA, torusB);
		
		assertThrows(IllegalArgumentException.class, () -> torus3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0}))));
		assertThrows(NullPointerException.class, () -> torus3FReader.read(null));
		assertThrows(UncheckedIOException.class, () -> torus3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testReadDataInputInt() {
		final Torus3FReader torus3FReader = new Torus3FReader();
		
		assertThrows(IllegalArgumentException.class, () -> torus3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {})), 0));
		assertThrows(NullPointerException.class, () -> torus3FReader.read(null, Torus3F.ID));
		assertThrows(UncheckedIOException.class, () -> torus3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0})), Torus3F.ID));
	}
}