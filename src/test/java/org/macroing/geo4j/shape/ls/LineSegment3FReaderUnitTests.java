/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

import org.macroing.geo4j.common.Point3F;

@SuppressWarnings("static-method")
public final class LineSegment3FReaderUnitTests {
	public LineSegment3FReaderUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testIsSupported() {
		final LineSegment3FReader lineSegment3FReader = new LineSegment3FReader();
		
		assertTrue(lineSegment3FReader.isSupported(LineSegment3F.ID));
		
		assertFalse(lineSegment3FReader.isSupported(0));
	}
	
	@Test
	public void testReadDataInput() {
		final LineSegment3FReader lineSegment3FReader = new LineSegment3FReader();
		
		final LineSegment3F lineSegmentA = new LineSegment3F(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(5.0F, 5.0F, 5.0F));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		lineSegmentA.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final LineSegment3F lineSegmentB = lineSegment3FReader.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(lineSegmentA, lineSegmentB);
		
		assertThrows(IllegalArgumentException.class, () -> lineSegment3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0}))));
		assertThrows(NullPointerException.class, () -> lineSegment3FReader.read(null));
		assertThrows(UncheckedIOException.class, () -> lineSegment3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testReadDataInputInt() {
		final LineSegment3FReader lineSegment3FReader = new LineSegment3FReader();
		
		assertThrows(IllegalArgumentException.class, () -> lineSegment3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {})), 0));
		assertThrows(NullPointerException.class, () -> lineSegment3FReader.read(null, LineSegment3F.ID));
		assertThrows(UncheckedIOException.class, () -> lineSegment3FReader.read(new DataInputStream(new ByteArrayInputStream(new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})), LineSegment3F.ID));
	}
}