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

import org.macroing.geo4j.common.Point3D;

@SuppressWarnings("static-method")
public final class LineSegment3DReaderUnitTests {
	public LineSegment3DReaderUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testIsSupported() {
		final LineSegment3DReader lineSegment3DReader = new LineSegment3DReader();
		
		assertTrue(lineSegment3DReader.isSupported(LineSegment3D.ID));
		
		assertFalse(lineSegment3DReader.isSupported(0));
	}
	
	@Test
	public void testReadDataInput() {
		final LineSegment3DReader lineSegment3DReader = new LineSegment3DReader();
		
		final LineSegment3D lineSegmentA = new LineSegment3D(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(5.0D, 5.0D, 5.0D));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		lineSegmentA.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final LineSegment3D lineSegmentB = lineSegment3DReader.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(lineSegmentA, lineSegmentB);
		
		assertThrows(IllegalArgumentException.class, () -> lineSegment3DReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0}))));
		assertThrows(NullPointerException.class, () -> lineSegment3DReader.read(null));
		assertThrows(UncheckedIOException.class, () -> lineSegment3DReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testReadDataInputInt() {
		final LineSegment3DReader lineSegment3DReader = new LineSegment3DReader();
		
		assertThrows(IllegalArgumentException.class, () -> lineSegment3DReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {})), 0));
		assertThrows(NullPointerException.class, () -> lineSegment3DReader.read(null, LineSegment3D.ID));
		assertThrows(UncheckedIOException.class, () -> lineSegment3DReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})), LineSegment3D.ID));
	}
}