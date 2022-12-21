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
package org.macroing.geo4j.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.mock.DataOutputMock;

@SuppressWarnings("static-method")
public final class AngleDUnitTests {
	public AngleDUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAdd() {
		final AngleD a = AngleD.add(AngleD.degrees(180.0D, 0.0D, 360.0D), AngleD.degrees(180.0D,   0.0D, 360.0D));
		final AngleD b = AngleD.add(AngleD.degrees(180.0D, 0.0D, 180.0D), AngleD.degrees(180.0D, 180.0D, 360.0D));
		final AngleD c = AngleD.add(AngleD.degrees(180.0D, 0.0D, 180.0D), AngleD.degrees(360.0D, 180.0D, 360.0D));
		
		assertEquals(360.0D, a.getDegrees());
		assertEquals(360.0D, a.getDegreesMaximum());
		assertEquals(  0.0D, a.getDegreesMinimum());
		
		assertEquals(360.0D, b.getDegrees());
		assertEquals(360.0D, b.getDegreesMaximum());
		assertEquals(  0.0D, b.getDegreesMinimum());
		
		assertEquals(180.0D, c.getDegrees());
		assertEquals(360.0D, c.getDegreesMaximum());
		assertEquals(  0.0D, c.getDegreesMinimum());
		
		assertThrows(NullPointerException.class, () -> AngleD.add(AngleD.degrees(0.0D), null));
		assertThrows(NullPointerException.class, () -> AngleD.add(null, AngleD.degrees(0.0D)));
	}
	
	@Test
	public void testCos() {
		final AngleD angle = AngleD.degrees(123.0D);
		
		assertEquals(Math.cos(Math.toRadians(123.0D)), angle.cos());
	}
	
	@Test
	public void testDegreesDouble() {
		final AngleD a = AngleD.degrees(+360.0D);
		final AngleD b = AngleD.degrees(-360.0D);
		
		assertEquals(360.0D, a.getDegrees());
		assertEquals(360.0D, a.getDegreesMaximum());
		assertEquals(  0.0D, a.getDegreesMinimum());
		
		assertEquals(Math.toRadians(360.0D), a.getRadians());
		assertEquals(Math.toRadians(360.0D), a.getRadiansMaximum());
		assertEquals(Math.toRadians(  0.0D), a.getRadiansMinimum());
		
		assertEquals(  0.0D, b.getDegrees());
		assertEquals(360.0D, b.getDegreesMaximum());
		assertEquals(  0.0D, b.getDegreesMinimum());
		
		assertEquals(Math.toRadians(  0.0D), b.getRadians());
		assertEquals(Math.toRadians(360.0D), b.getRadiansMaximum());
		assertEquals(Math.toRadians(  0.0D), b.getRadiansMinimum());
	}
	
	@Test
	public void testDegreesDoubleDoubleDouble() {
		final AngleD a = AngleD.degrees(200.0D, 100.0D, 300.0D);
		final AngleD b = AngleD.degrees( 99.0D, 300.0D, 100.0D);
		
		assertEquals(200.0D, a.getDegrees());
		assertEquals(300.0D, a.getDegreesMaximum());
		assertEquals(100.0D, a.getDegreesMinimum());
		
		assertEquals(Math.toRadians(200.0D), a.getRadians());
		assertEquals(Math.toRadians(300.0D), a.getRadiansMaximum());
		assertEquals(Math.toRadians(100.0D), a.getRadiansMinimum());
		
		assertEquals(299.0D, b.getDegrees());
		assertEquals(300.0D, b.getDegreesMaximum());
		assertEquals(100.0D, b.getDegreesMinimum());
		
		assertEquals(Math.toRadians(299.0D), b.getRadians());
		assertEquals(Math.toRadians(300.0D), b.getRadiansMaximum());
		assertEquals(Math.toRadians(100.0D), b.getRadiansMinimum());
	}
	
	@Test
	public void testEquals() {
		final AngleD a = AngleD.degrees(180.0D, 0.0D, 360.0D);
		final AngleD b = AngleD.degrees(180.0D, 0.0D, 360.0D);
		final AngleD c = AngleD.degrees(180.0D, 0.0D, 359.0D);
		final AngleD d = AngleD.degrees(180.0D, 1.0D, 360.0D);
		final AngleD e = AngleD.degrees(181.0D, 1.0D, 360.0D);
		final AngleD f = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
		assertNotEquals(a, f);
		assertNotEquals(f, a);
	}
	
	@Test
	public void testFieldOfView() {
		final AngleD angle = AngleD.fieldOfView(400.0D, 800.0D);
		
		assertEquals(90.0D, angle.getDegrees());
	}
	
	@Test
	public void testFieldOfViewX() {
		final AngleD angle = AngleD.fieldOfViewX(AngleD.degrees(90.0D), 800.0D, 800.0D);
		
		assertEquals(90.0D, angle.getDegrees());
		
		assertThrows(NullPointerException.class, () -> AngleD.fieldOfViewX(null, 800.0D, 800.0D));
	}
	
	@Test
	public void testFieldOfViewY() {
		final AngleD angle = AngleD.fieldOfViewY(AngleD.degrees(90.0D), 800.0D, 800.0D);
		
		assertEquals(90.0D, angle.getDegrees());
		
		assertThrows(NullPointerException.class, () -> AngleD.fieldOfViewY(null, 800.0D, 800.0D));
	}
	
	@Test
	public void testGetDegrees() {
		final AngleD a = AngleD.degrees(250.0D, 200.0D, 300.0D);
		final AngleD b = AngleD.degrees(301.0D, 200.0D, 300.0D);
		final AngleD c = AngleD.degrees(199.0D, 200.0D, 300.0D);
		
		assertEquals(250.0D, a.getDegrees());
		assertEquals(201.0D, b.getDegrees());
		assertEquals(299.0D, c.getDegrees());
	}
	
	@Test
	public void testGetDegreesMaximum() {
		final AngleD a = AngleD.degrees(0.0D,   0.0D, 360.0D);
		final AngleD b = AngleD.degrees(0.0D, 360.0D,   0.0D);
		
		assertEquals(360.0D, a.getDegreesMaximum());
		assertEquals(360.0D, b.getDegreesMaximum());
	}
	
	@Test
	public void testGetDegreesMinimum() {
		final AngleD a = AngleD.degrees(0.0D,   0.0D, 360.0D);
		final AngleD b = AngleD.degrees(0.0D, 360.0D,   0.0D);
		
		assertEquals(0.0D, a.getDegreesMinimum());
		assertEquals(0.0D, b.getDegreesMinimum());
	}
	
	@Test
	public void testGetRadians() {
		final AngleD a = AngleD.radians(250.0D, 200.0D, 300.0D);
		final AngleD b = AngleD.radians(301.0D, 200.0D, 300.0D);
		final AngleD c = AngleD.radians(199.0D, 200.0D, 300.0D);
		
		assertEquals(250.0D, a.getRadians());
		assertEquals(201.0D, b.getRadians());
		assertEquals(299.0D, c.getRadians());
	}
	
	@Test
	public void testGetRadiansMaximum() {
		final AngleD a = AngleD.radians(0.0D,   0.0D, 360.0D);
		final AngleD b = AngleD.radians(0.0D, 360.0D,   0.0D);
		
		assertEquals(360.0D, a.getRadiansMaximum());
		assertEquals(360.0D, b.getRadiansMaximum());
	}
	
	@Test
	public void testGetRadiansMinimum() {
		final AngleD a = AngleD.radians(0.0D,   0.0D, 360.0D);
		final AngleD b = AngleD.radians(0.0D, 360.0D,   0.0D);
		
		assertEquals(0.0D, a.getRadiansMinimum());
		assertEquals(0.0D, b.getRadiansMinimum());
	}
	
	@Test
	public void testHashCode() {
		final AngleD a = AngleD.degrees(0.0D, 360.0D, 0.0D);
		final AngleD b = AngleD.degrees(0.0D, 360.0D, 0.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testRadiansDouble() {
		final AngleD a = AngleD.radians(Math.PI * 2.0D);
		final AngleD b = AngleD.radians(0.0D);
		
		assertEquals(Math.PI * 2.0D, a.getRadians());
		assertEquals(Math.PI * 2.0D, a.getRadiansMaximum());
		assertEquals(          0.0D, a.getRadiansMinimum());
		
		assertEquals(Math.toDegrees(Math.PI * 2.0D), a.getDegrees());
		assertEquals(Math.toDegrees(Math.PI * 2.0D), a.getDegreesMaximum());
		assertEquals(Math.toDegrees(          0.0D), a.getDegreesMinimum());
		
		assertEquals(          0.0D, b.getRadians());
		assertEquals(Math.PI * 2.0D, b.getRadiansMaximum());
		assertEquals(          0.0D, b.getRadiansMinimum());
		
		assertEquals(Math.toDegrees(          0.0D), b.getDegrees());
		assertEquals(Math.toDegrees(Math.PI * 2.0D), b.getDegreesMaximum());
		assertEquals(Math.toDegrees(          0.0D), b.getDegreesMinimum());
	}
	
	@Test
	public void testRadiansDoubleDoubleDouble() {
		final AngleD a = AngleD.radians(Math.PI * 2.0D, Math.PI * 1.0D, Math.PI * 4.0D);
		final AngleD b = AngleD.radians(Math.PI - 1.0D, Math.PI * 4.0D, Math.PI * 1.0D);
		
		assertEquals(Math.PI * 2.0D, a.getRadians());
		assertEquals(Math.PI * 4.0D, a.getRadiansMaximum());
		assertEquals(Math.PI * 1.0D, a.getRadiansMinimum());
		
		assertEquals(Math.toDegrees(Math.PI * 2.0D), a.getDegrees());
		assertEquals(Math.toDegrees(Math.PI * 4.0D), a.getDegreesMaximum());
		assertEquals(Math.toDegrees(Math.PI * 1.0D), a.getDegreesMinimum());
		
		assertEquals(Math.PI * 4.0D - 1.0D, b.getRadians());
		assertEquals(Math.PI * 4.0D,        b.getRadiansMaximum());
		assertEquals(Math.PI * 1.0D,        b.getRadiansMinimum());
		
		assertEquals(Math.toDegrees(Math.PI * 4.0D - 1.0D), b.getDegrees());
		assertEquals(Math.toDegrees(Math.PI * 4.0D),        b.getDegreesMaximum());
		assertEquals(Math.toDegrees(Math.PI * 1.0D),        b.getDegreesMinimum());
	}
	
	@Test
	public void testRead() throws IOException {
		final AngleD a = AngleD.degrees(180.0D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeDouble(a.getDegrees());
		dataOutput.writeDouble(a.getDegreesMinimum());
		dataOutput.writeDouble(a.getDegreesMaximum());
		dataOutput.writeDouble(a.getRadians());
		dataOutput.writeDouble(a.getRadiansMinimum());
		dataOutput.writeDouble(a.getRadiansMaximum());
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final AngleD b = AngleD.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> AngleD.read(null));
		assertThrows(UncheckedIOException.class, () -> AngleD.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testSin() {
		final AngleD angle = AngleD.degrees(123.0D);
		
		assertEquals(Math.sin(Math.toRadians(123.0D)), angle.sin());
	}
	
	@Test
	public void testSubtract() {
		final AngleD a = AngleD.subtract(AngleD.degrees(360.0D,   0.0D, 360.0D), AngleD.degrees(180.0D,   0.0D, 360.0D));
		final AngleD b = AngleD.subtract(AngleD.degrees(360.0D, 180.0D, 360.0D), AngleD.degrees(180.0D,   0.0D, 180.0D));
		final AngleD c = AngleD.subtract(AngleD.degrees(180.0D,   0.0D, 180.0D), AngleD.degrees(360.0D, 180.0D, 360.0D));
		
		assertEquals(180.0D, a.getDegrees());
		assertEquals(360.0D, a.getDegreesMaximum());
		assertEquals(  0.0D, a.getDegreesMinimum());
		
		assertEquals(180.0D, b.getDegrees());
		assertEquals(360.0D, b.getDegreesMaximum());
		assertEquals(  0.0D, b.getDegreesMinimum());
		
		assertEquals(180.0D, c.getDegrees());
		assertEquals(360.0D, c.getDegreesMaximum());
		assertEquals(  0.0D, c.getDegreesMinimum());
		
		assertThrows(NullPointerException.class, () -> AngleD.subtract(AngleD.degrees(0.0D), null));
		assertThrows(NullPointerException.class, () -> AngleD.subtract(null, AngleD.degrees(0.0D)));
	}
	
	@Test
	public void testToString() {
		final AngleD angle = AngleD.degrees(180.0D, 0.0D, 360.0D);
		
		assertEquals("AngleD.degrees(180.0D, 0.0D, 360.0D)", angle.toString());
	}
	
	@Test
	public void testWrite() {
		final AngleD a = AngleD.degrees(200.0D, 100.0D, 300.0D);
		final AngleD b = AngleD.radians(200.0D, 100.0D, 300.0D);
		
		final ByteArrayOutputStream byteArrayOutputStreamA = new ByteArrayOutputStream();
		final ByteArrayOutputStream byteArrayOutputStreamB = new ByteArrayOutputStream();
		
		final DataOutput dataOutputA = new DataOutputStream(byteArrayOutputStreamA);
		final DataOutput dataOutputB = new DataOutputStream(byteArrayOutputStreamB);
		
		a.write(dataOutputA);
		b.write(dataOutputB);
		
		final byte[] bytesA = byteArrayOutputStreamA.toByteArray();
		final byte[] bytesB = byteArrayOutputStreamB.toByteArray();
		
		final AngleD c = AngleD.read(new DataInputStream(new ByteArrayInputStream(bytesA)));
		final AngleD d = AngleD.read(new DataInputStream(new ByteArrayInputStream(bytesB)));
		
		assertEquals(a, c);
		assertEquals(b, d);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}