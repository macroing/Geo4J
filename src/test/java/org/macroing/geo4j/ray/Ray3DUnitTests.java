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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.mock.DataOutputMock;

@SuppressWarnings("static-method")
public final class Ray3DUnitTests {
	public Ray3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final Ray3D r = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Point3D(1.0D, 2.0D, 3.0D), r.getOrigin());
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), r.getDirection());
		
		assertThrows(NullPointerException.class, () -> new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> new Ray3D(null, new Vector3D(1.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testEqualsObject() {
		final Ray3D a = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D b = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D c = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Ray3D d = new Ray3D(new Point3D(4.0D, 5.0D, 6.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D e = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
	}
	
	@Test
	public void testEqualsRay3D() {
		final Ray3D a = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D b = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D c = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Ray3D d = new Ray3D(new Point3D(4.0D, 5.0D, 6.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D e = null;
		
		assertTrue(a.equals(a));
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		
		assertFalse(a.equals(c));
		assertFalse(c.equals(a));
		assertFalse(a.equals(d));
		assertFalse(d.equals(a));
		assertFalse(a.equals(e));
	}
	
	@Test
	public void testGetDirection() {
		final Ray3D r = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), r.getDirection());
	}
	
	@Test
	public void testGetOrigin() {
		final Ray3D r = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Point3D(1.0D, 2.0D, 3.0D), r.getOrigin());
	}
	
	@Test
	public void testGetPointAt() {
		final Ray3D ray = new Ray3D(new Point3D(0.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Point3D(1.0D, 0.0D, 0.0D), ray.getPointAt(1.0D));
	}
	
	@Test
	public void testHashCode() {
		final Ray3D a = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final Ray3D b = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testRead() throws IOException {
		final Ray3D a = new Ray3D(new Point3D(0.0D, 1.0D, 2.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeDouble(a.getOrigin().x);
		dataOutput.writeDouble(a.getOrigin().y);
		dataOutput.writeDouble(a.getOrigin().z);
		dataOutput.writeDouble(a.getDirection().x);
		dataOutput.writeDouble(a.getDirection().y);
		dataOutput.writeDouble(a.getDirection().z);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Ray3D b = Ray3D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Ray3D.read(null));
		assertThrows(UncheckedIOException.class, () -> Ray3D.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testToString() {
		final Ray3D r = new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals("new Ray3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D))", r.toString());
	}
	
	@Test
	public void testWrite() {
		final Ray3D a = new Ray3D(new Point3D(0.0D, 1.0D, 2.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Ray3D b = Ray3D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}