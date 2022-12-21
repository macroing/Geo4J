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
public final class AngleFUnitTests {
	public AngleFUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAdd() {
		final AngleF a = AngleF.add(AngleF.degrees(180.0F, 0.0F, 360.0F), AngleF.degrees(180.0F,   0.0F, 360.0F));
		final AngleF b = AngleF.add(AngleF.degrees(180.0F, 0.0F, 180.0F), AngleF.degrees(180.0F, 180.0F, 360.0F));
		final AngleF c = AngleF.add(AngleF.degrees(180.0F, 0.0F, 180.0F), AngleF.degrees(360.0F, 180.0F, 360.0F));
		
		assertEquals(360.0F, a.getDegrees());
		assertEquals(360.0F, a.getDegreesMaximum());
		assertEquals(  0.0F, a.getDegreesMinimum());
		
		assertEquals(360.0F, b.getDegrees());
		assertEquals(360.0F, b.getDegreesMaximum());
		assertEquals(  0.0F, b.getDegreesMinimum());
		
		assertEquals(180.0F, c.getDegrees());
		assertEquals(360.0F, c.getDegreesMaximum());
		assertEquals(  0.0F, c.getDegreesMinimum());
		
		assertThrows(NullPointerException.class, () -> AngleF.add(AngleF.degrees(0.0F), null));
		assertThrows(NullPointerException.class, () -> AngleF.add(null, AngleF.degrees(0.0F)));
	}
	
	@Test
	public void testCos() {
		final AngleF angle = AngleF.degrees(123.0F);
		
		assertEquals((float)(Math.cos((float)(Math.toRadians(123.0F)))), angle.cos());
	}
	
	@Test
	public void testDegreesFloat() {
		final AngleF a = AngleF.degrees(+360.0F);
		final AngleF b = AngleF.degrees(-360.0F);
		
		assertEquals(360.0F, a.getDegrees());
		assertEquals(360.0F, a.getDegreesMaximum());
		assertEquals(  0.0F, a.getDegreesMinimum());
		
		assertEquals((float)(Math.toRadians(360.0F)), a.getRadians());
		assertEquals((float)(Math.toRadians(360.0F)), a.getRadiansMaximum());
		assertEquals((float)(Math.toRadians(  0.0F)), a.getRadiansMinimum());
		
		assertEquals(  0.0F, b.getDegrees());
		assertEquals(360.0F, b.getDegreesMaximum());
		assertEquals(  0.0F, b.getDegreesMinimum());
		
		assertEquals((float)(Math.toRadians(  0.0F)), b.getRadians());
		assertEquals((float)(Math.toRadians(360.0F)), b.getRadiansMaximum());
		assertEquals((float)(Math.toRadians(  0.0F)), b.getRadiansMinimum());
	}
	
	@Test
	public void testDegreesFloatFloatFloat() {
		final AngleF a = AngleF.degrees(200.0F, 100.0F, 300.0F);
		final AngleF b = AngleF.degrees( 99.0F, 300.0F, 100.0F);
		
		assertEquals(200.0F, a.getDegrees());
		assertEquals(300.0F, a.getDegreesMaximum());
		assertEquals(100.0F, a.getDegreesMinimum());
		
		assertEquals((float)(Math.toRadians(200.0F)), a.getRadians());
		assertEquals((float)(Math.toRadians(300.0F)), a.getRadiansMaximum());
		assertEquals((float)(Math.toRadians(100.0F)), a.getRadiansMinimum());
		
		assertEquals(299.0F, b.getDegrees());
		assertEquals(300.0F, b.getDegreesMaximum());
		assertEquals(100.0F, b.getDegreesMinimum());
		
		assertEquals((float)(Math.toRadians(299.0F)), b.getRadians());
		assertEquals((float)(Math.toRadians(300.0F)), b.getRadiansMaximum());
		assertEquals((float)(Math.toRadians(100.0F)), b.getRadiansMinimum());
	}
	
	@Test
	public void testEquals() {
		final AngleF a = AngleF.degrees(180.0F, 0.0F, 360.0F);
		final AngleF b = AngleF.degrees(180.0F, 0.0F, 360.0F);
		final AngleF c = AngleF.degrees(180.0F, 0.0F, 359.0F);
		final AngleF d = AngleF.degrees(180.0F, 1.0F, 360.0F);
		final AngleF e = AngleF.degrees(181.0F, 1.0F, 360.0F);
		final AngleF f = null;
		
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
		final AngleF angle = AngleF.fieldOfView(400.0F, 800.0F);
		
		assertEquals(90.0F, angle.getDegrees());
	}
	
	@Test
	public void testFieldOfViewX() {
		final AngleF angle = AngleF.fieldOfViewX(AngleF.degrees(90.0F), 800.0F, 800.0F);
		
		assertEquals(90.0F, angle.getDegrees());
		
		assertThrows(NullPointerException.class, () -> AngleF.fieldOfViewX(null, 800.0F, 800.0F));
	}
	
	@Test
	public void testFieldOfViewY() {
		final AngleF angle = AngleF.fieldOfViewY(AngleF.degrees(90.0F), 800.0F, 800.0F);
		
		assertEquals(90.0F, angle.getDegrees());
		
		assertThrows(NullPointerException.class, () -> AngleF.fieldOfViewY(null, 800.0F, 800.0F));
	}
	
	@Test
	public void testGetDegrees() {
		final AngleF a = AngleF.degrees(250.0F, 200.0F, 300.0F);
		final AngleF b = AngleF.degrees(301.0F, 200.0F, 300.0F);
		final AngleF c = AngleF.degrees(199.0F, 200.0F, 300.0F);
		
		assertEquals(250.0F, a.getDegrees());
		assertEquals(201.0F, b.getDegrees());
		assertEquals(299.0F, c.getDegrees());
	}
	
	@Test
	public void testGetDegreesMaximum() {
		final AngleF a = AngleF.degrees(0.0F,   0.0F, 360.0F);
		final AngleF b = AngleF.degrees(0.0F, 360.0F,   0.0F);
		
		assertEquals(360.0F, a.getDegreesMaximum());
		assertEquals(360.0F, b.getDegreesMaximum());
	}
	
	@Test
	public void testGetDegreesMinimum() {
		final AngleF a = AngleF.degrees(0.0F,   0.0F, 360.0F);
		final AngleF b = AngleF.degrees(0.0F, 360.0F,   0.0F);
		
		assertEquals(0.0F, a.getDegreesMinimum());
		assertEquals(0.0F, b.getDegreesMinimum());
	}
	
	@Test
	public void testGetRadians() {
		final AngleF a = AngleF.radians(250.0F, 200.0F, 300.0F);
		final AngleF b = AngleF.radians(301.0F, 200.0F, 300.0F);
		final AngleF c = AngleF.radians(199.0F, 200.0F, 300.0F);
		
		assertEquals(250.0F, a.getRadians());
		assertEquals(201.0F, b.getRadians());
		assertEquals(299.0F, c.getRadians());
	}
	
	@Test
	public void testGetRadiansMaximum() {
		final AngleF a = AngleF.radians(0.0F,   0.0F, 360.0F);
		final AngleF b = AngleF.radians(0.0F, 360.0F,   0.0F);
		
		assertEquals(360.0F, a.getRadiansMaximum());
		assertEquals(360.0F, b.getRadiansMaximum());
	}
	
	@Test
	public void testGetRadiansMinimum() {
		final AngleF a = AngleF.radians(0.0F,   0.0F, 360.0F);
		final AngleF b = AngleF.radians(0.0F, 360.0F,   0.0F);
		
		assertEquals(0.0F, a.getRadiansMinimum());
		assertEquals(0.0F, b.getRadiansMinimum());
	}
	
	@Test
	public void testHashCode() {
		final AngleF a = AngleF.degrees(0.0F, 360.0F, 0.0F);
		final AngleF b = AngleF.degrees(0.0F, 360.0F, 0.0F);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testPitchPoint3FPoint3F() {
		final AngleF a = AngleF.pitch(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(1.0F, 0.0F, 0.0F));
		final AngleF b = AngleF.pitch(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(0.0F, 1.0F, 0.0F));
		final AngleF c = AngleF.pitch(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 1.0F));
		
		assertEquals(AngleF.degrees( 0.0F, -90.0F, 90.0F), a);
		assertEquals(AngleF.degrees(90.0F, -90.0F, 90.0F), b);
		assertEquals(AngleF.degrees( 0.0F, -90.0F, 90.0F), c);
		
		assertThrows(NullPointerException.class, () -> AngleF.pitch(new Point3F(), null));
		assertThrows(NullPointerException.class, () -> AngleF.pitch(null, new Point3F()));
	}
	
	@Test
	public void testPitchVector3F() {
		final AngleF a = AngleF.pitch(Vector3F.x());
		final AngleF b = AngleF.pitch(Vector3F.y());
		final AngleF c = AngleF.pitch(Vector3F.z());
		
		assertEquals(AngleF.degrees( 0.0F, -90.0F, 90.0F), a);
		assertEquals(AngleF.degrees(90.0F, -90.0F, 90.0F), b);
		assertEquals(AngleF.degrees( 0.0F, -90.0F, 90.0F), c);
		
		assertThrows(NullPointerException.class, () -> AngleF.pitch(null));
	}
	
	@Test
	public void testRadiansFloat() {
		final AngleF a = AngleF.radians((float)(Math.PI) * 2.0F);
		final AngleF b = AngleF.radians(0.0F);
		
		assertEquals((float)(Math.PI) * 2.0F, a.getRadians());
		assertEquals((float)(Math.PI) * 2.0F, a.getRadiansMaximum());
		assertEquals(                   0.0F, a.getRadiansMinimum());
		
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 2.0F)), a.getDegrees());
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 2.0F)), a.getDegreesMaximum());
		assertEquals((float)(Math.toDegrees(                   0.0F)), a.getDegreesMinimum());
		
		assertEquals(                   0.0F, b.getRadians());
		assertEquals((float)(Math.PI) * 2.0F, b.getRadiansMaximum());
		assertEquals(                   0.0F, b.getRadiansMinimum());
		
		assertEquals((float)(Math.toDegrees(                   0.0F)), b.getDegrees());
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 2.0F)), b.getDegreesMaximum());
		assertEquals((float)(Math.toDegrees(                   0.0F)), b.getDegreesMinimum());
	}
	
	@Test
	public void testRadiansFloatFloatFloat() {
		final AngleF a = AngleF.radians((float)(Math.PI) * 2.0F, (float)(Math.PI) * 1.0F, (float)(Math.PI) * 4.0F);
		final AngleF b = AngleF.radians((float)(Math.PI) - 1.0F, (float)(Math.PI) * 4.0F, (float)(Math.PI) * 1.0F);
		
		assertEquals((float)(Math.PI) * 2.0F, a.getRadians());
		assertEquals((float)(Math.PI) * 4.0F, a.getRadiansMaximum());
		assertEquals((float)(Math.PI) * 1.0F, a.getRadiansMinimum());
		
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 2.0F)), a.getDegrees());
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 4.0F)), a.getDegreesMaximum());
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 1.0F)), a.getDegreesMinimum());
		
		assertEquals((float)(Math.PI) * 4.0F - 1.0F, b.getRadians());
		assertEquals((float)(Math.PI) * 4.0F,        b.getRadiansMaximum());
		assertEquals((float)(Math.PI) * 1.0F,        b.getRadiansMinimum());
		
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 4.0F - 1.0F)), b.getDegrees());
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 4.0F)),        b.getDegreesMaximum());
		assertEquals((float)(Math.toDegrees((float)(Math.PI) * 1.0F)),        b.getDegreesMinimum());
	}
	
	@Test
	public void testRead() throws IOException {
		final AngleF a = AngleF.degrees(180.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeFloat(a.getDegrees());
		dataOutput.writeFloat(a.getDegreesMinimum());
		dataOutput.writeFloat(a.getDegreesMaximum());
		dataOutput.writeFloat(a.getRadians());
		dataOutput.writeFloat(a.getRadiansMinimum());
		dataOutput.writeFloat(a.getRadiansMaximum());
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final AngleF b = AngleF.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> AngleF.read(null));
		assertThrows(UncheckedIOException.class, () -> AngleF.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testSin() {
		final AngleF angle = AngleF.degrees(123.0F);
		
		assertEquals((float)(Math.sin((float)(Math.toRadians(123.0F)))), angle.sin());
	}
	
	@Test
	public void testSubtract() {
		final AngleF a = AngleF.subtract(AngleF.degrees(360.0F,   0.0F, 360.0F), AngleF.degrees(180.0F,   0.0F, 360.0F));
		final AngleF b = AngleF.subtract(AngleF.degrees(360.0F, 180.0F, 360.0F), AngleF.degrees(180.0F,   0.0F, 180.0F));
		final AngleF c = AngleF.subtract(AngleF.degrees(180.0F,   0.0F, 180.0F), AngleF.degrees(360.0F, 180.0F, 360.0F));
		
		assertEquals(180.0F, a.getDegrees());
		assertEquals(360.0F, a.getDegreesMaximum());
		assertEquals(  0.0F, a.getDegreesMinimum());
		
		assertEquals(180.0F, b.getDegrees());
		assertEquals(360.0F, b.getDegreesMaximum());
		assertEquals(  0.0F, b.getDegreesMinimum());
		
		assertEquals(180.0F, c.getDegrees());
		assertEquals(360.0F, c.getDegreesMaximum());
		assertEquals(  0.0F, c.getDegreesMinimum());
		
		assertThrows(NullPointerException.class, () -> AngleF.subtract(AngleF.degrees(0.0F), null));
		assertThrows(NullPointerException.class, () -> AngleF.subtract(null, AngleF.degrees(0.0F)));
	}
	
	@Test
	public void testToString() {
		final AngleF angle = AngleF.degrees(180.0F, 0.0F, 360.0F);
		
		assertEquals("AngleF.degrees(180.0F, 0.0F, 360.0F)", angle.toString());
	}
	
	@Test
	public void testWrite() {
		final AngleF a = AngleF.degrees(200.0F, 100.0F, 300.0F);
		final AngleF b = AngleF.radians(200.0F, 100.0F, 300.0F);
		
		final ByteArrayOutputStream byteArrayOutputStreamA = new ByteArrayOutputStream();
		final ByteArrayOutputStream byteArrayOutputStreamB = new ByteArrayOutputStream();
		
		final DataOutput dataOutputA = new DataOutputStream(byteArrayOutputStreamA);
		final DataOutput dataOutputB = new DataOutputStream(byteArrayOutputStreamB);
		
		a.write(dataOutputA);
		b.write(dataOutputB);
		
		final byte[] bytesA = byteArrayOutputStreamA.toByteArray();
		final byte[] bytesB = byteArrayOutputStreamB.toByteArray();
		
		final AngleF c = AngleF.read(new DataInputStream(new ByteArrayInputStream(bytesA)));
		final AngleF d = AngleF.read(new DataInputStream(new ByteArrayInputStream(bytesB)));
		
		assertEquals(a, c);
		assertEquals(b, d);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
	
	@Test
	public void testYawPoint3FPoint3F() {
		final AngleF a = AngleF.yaw(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(1.0F, 0.0F, 0.0F));
		final AngleF b = AngleF.yaw(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(0.0F, 1.0F, 0.0F));
		final AngleF c = AngleF.yaw(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 1.0F));
		
		assertEquals(AngleF.degrees(90.0F), a);
		assertEquals(AngleF.degrees( 0.0F), b);
		assertEquals(AngleF.degrees( 0.0F), c);
		
		assertThrows(NullPointerException.class, () -> AngleF.yaw(new Point3F(), null));
		assertThrows(NullPointerException.class, () -> AngleF.yaw(null, new Point3F()));
	}
	
	@Test
	public void testYawVector3F() {
		final AngleF a = AngleF.yaw(Vector3F.x());
		final AngleF b = AngleF.yaw(Vector3F.y());
		final AngleF c = AngleF.yaw(Vector3F.z());
		
		assertEquals(AngleF.degrees(90.0F), a);
		assertEquals(AngleF.degrees( 0.0F), b);
		assertEquals(AngleF.degrees( 0.0F), c);
		
		assertThrows(NullPointerException.class, () -> AngleF.yaw(null));
	}
}