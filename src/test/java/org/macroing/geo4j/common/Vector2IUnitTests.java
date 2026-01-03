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
package org.macroing.geo4j.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import org.macroing.geo4j.mock.DataOutputMock;

@SuppressWarnings("static-method")
public final class Vector2IUnitTests {
	public Vector2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAbs() {
		final Vector2I a = Vector2I.abs(new Vector2I(+1, +2));
		final Vector2I b = Vector2I.abs(new Vector2I(-1, -2));
		
		assertEquals(1, a.x);
		assertEquals(2, a.y);
		
		assertEquals(1, b.x);
		assertEquals(2, b.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.abs(null));
	}
	
	@Test
	public void testAddVector2IVector2I() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = new Vector2I(2, 3);
		final Vector2I c = Vector2I.add(a, b);
		
		assertEquals(3, c.x);
		assertEquals(5, c.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.add(a, null));
		assertThrows(NullPointerException.class, () -> Vector2I.add(null, b));
	}
	
	@Test
	public void testAddVector2IVector2IVector2I() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = new Vector2I(2, 3);
		final Vector2I c = new Vector2I(3, 4);
		final Vector2I d = Vector2I.add(a, b, c);
		
		assertEquals(6, d.x);
		assertEquals(9, d.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.add(a, b, null));
		assertThrows(NullPointerException.class, () -> Vector2I.add(a, null, c));
		assertThrows(NullPointerException.class, () -> Vector2I.add(null, b, c));
	}
	
	@Test
	public void testConstructor() {
		final Vector2I vector = new Vector2I();
		
		assertEquals(0, vector.x);
		assertEquals(0, vector.y);
	}
	
	@Test
	public void testConstructorIntInt() {
		final Vector2I vector = new Vector2I(1, 2);
		
		assertEquals(1, vector.x);
		assertEquals(2, vector.y);
	}
	
	@Test
	public void testConstructorPoint2I() {
		final Vector2I vector = new Vector2I(new Point2I(1, 2));
		
		assertEquals(1, vector.x);
		assertEquals(2, vector.y);
		
		assertThrows(NullPointerException.class, () -> new Vector2I(null));
	}
	
	@Test
	public void testDirection() {
		final Vector2I vector = Vector2I.direction(new Point2I(1, 2), new Point2I(2, 3));
		
		assertEquals(1, vector.x);
		assertEquals(1, vector.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.direction(new Point2I(1, 2), null));
		assertThrows(NullPointerException.class, () -> Vector2I.direction(null, new Point2I(2, 3)));
	}
	
	@Test
	public void testDivide() {
		final Vector2I a = new Vector2I(2, 4);
		final Vector2I b = Vector2I.divide(a, 2);
		
		assertEquals(1, b.x);
		assertEquals(2, b.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.divide(null, 2));
	}
	
	@Test
	public void testEqualsObject() {
		final Vector2I a = new Vector2I(0, 1);
		final Vector2I b = new Vector2I(0, 1);
		final Vector2I c = new Vector2I(0, 2);
		final Vector2I d = new Vector2I(2, 1);
		final Vector2I e = null;
		
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
	public void testEqualsVector2I() {
		final Vector2I a = new Vector2I(0, 1);
		final Vector2I b = new Vector2I(0, 1);
		final Vector2I c = new Vector2I(0, 2);
		final Vector2I d = new Vector2I(2, 1);
		final Vector2I e = null;
		
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
	public void testGetComponent() {
		final Vector2I v = new Vector2I(1, 2);
		
		assertEquals(1, v.getComponent(0));
		assertEquals(2, v.getComponent(1));
		
		assertThrows(IllegalArgumentException.class, () -> v.getComponent(-1));
		assertThrows(IllegalArgumentException.class, () -> v.getComponent(+2));
	}
	
	@Test
	public void testHashCode() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = new Vector2I(1, 2);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Vector2I(+0, +0).isZero());
		assertTrue(new Vector2I(+0, -0).isZero());
		assertTrue(new Vector2I(-0, +0).isZero());
		assertTrue(new Vector2I(-0, -0).isZero());
		
		assertFalse(new Vector2I(+0, +1).isZero());
		assertFalse(new Vector2I(-0, -1).isZero());
		assertFalse(new Vector2I(+1, +0).isZero());
		assertFalse(new Vector2I(-1, -0).isZero());
		assertFalse(new Vector2I(+1, -1).isZero());
	}
	
	@Test
	public void testLength() {
		final Vector2I a = new Vector2I(4, 0);
		final Vector2I b = new Vector2I(0, 4);
		
		assertEquals(4, a.length());
		assertEquals(4, b.length());
	}
	
	@Test
	public void testLengthSquared() {
		final Vector2I vector = new Vector2I(2, 4);
		
		assertEquals(20, vector.lengthSquared());
	}
	
	@Test
	public void testMultiply() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = Vector2I.multiply(a, 2);
		
		assertEquals(2, b.x);
		assertEquals(4, b.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.multiply(null, 2));
	}
	
	@Test
	public void testNegate() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = Vector2I.negate(a);
		final Vector2I c = Vector2I.negate(b);
		
		assertEquals(-1, b.x);
		assertEquals(-2, b.y);
		
		assertEquals(+1, c.x);
		assertEquals(+2, c.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.negate(null));
	}
	
	@Test
	public void testPerpendicular() {
		final Vector2I a = new Vector2I(1, 2);
		final Vector2I b = Vector2I.perpendicular(a);
		
		assertEquals(+2, b.x);
		assertEquals(-1, b.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.perpendicular(null));
	}
	
	@Test
	public void testRead() throws IOException {
		final Vector2I a = new Vector2I(1, 2);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeInt(a.x);
		dataOutput.writeInt(a.y);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Vector2I b = Vector2I.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Vector2I.read(null));
		assertThrows(UncheckedIOException.class, () -> Vector2I.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testSubtract() {
		final Vector2I a = new Vector2I(3, 5);
		final Vector2I b = new Vector2I(2, 3);
		final Vector2I c = Vector2I.subtract(a, b);
		
		assertEquals(1, c.x);
		assertEquals(2, c.y);
		
		assertThrows(NullPointerException.class, () -> Vector2I.subtract(a, null));
		assertThrows(NullPointerException.class, () -> Vector2I.subtract(null, b));
	}
	
	@Test
	public void testToArray() {
		final Vector2I vector = new Vector2I(1, 2);
		
		final int[] array = vector.toArray();
		
		assertNotNull(array);
		
		assertEquals(2, array.length);
		
		assertEquals(1, array[0]);
		assertEquals(2, array[1]);
	}
	
	@Test
	public void testToString() {
		final Vector2I vector = new Vector2I(1, 2);
		
		assertEquals("new Vector2I(1, 2)", vector.toString());
	}
	
	@Test
	public void testWrite() {
		final Vector2I a = new Vector2I(1, 2);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Vector2I b = Vector2I.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
	
	@Test
	public void testX() {
		final Vector2I vector = Vector2I.x();
		
		assertEquals(1, vector.x);
		assertEquals(0, vector.y);
	}
	
	@Test
	public void testXInt() {
		final Vector2I vector = Vector2I.x(2);
		
		assertEquals(2, vector.x);
		assertEquals(0, vector.y);
	}
	
	@Test
	public void testY() {
		final Vector2I vector = Vector2I.y();
		
		assertEquals(0, vector.x);
		assertEquals(1, vector.y);
	}
	
	@Test
	public void testYInt() {
		final Vector2I vector = Vector2I.y(2);
		
		assertEquals(0, vector.x);
		assertEquals(2, vector.y);
	}
}