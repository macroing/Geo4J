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
public final class Point2IUnitTests {
	public Point2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddPoint2IInt() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = Point2I.add(a, 2);
		
		assertEquals(3, b.x);
		assertEquals(4, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.add(null, 2));
	}
	
	@Test
	public void testAddPoint2IVector2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = Point2I.add(a, new Vector2I(1, 2));
		
		assertEquals(2, b.x);
		assertEquals(4, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.add(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.add(null, new Vector2I(1, 2)));
	}
	
	@Test
	public void testAddPoint2IVector2IInt() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = Point2I.add(a, new Vector2I(1, 2), 2);
		
		assertEquals(3, b.x);
		assertEquals(6, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.add(a, null, 2));
		assertThrows(NullPointerException.class, () -> Point2I.add(null, new Vector2I(1, 2), 2));
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Point2I(Integer.MAX_VALUE, Integer.MAX_VALUE), Point2I.MAX);
		assertEquals(new Point2I(Integer.MIN_VALUE, Integer.MIN_VALUE), Point2I.MIN);
	}
	
	@Test
	public void testConstructor() {
		final Point2I point = new Point2I();
		
		assertEquals(0, point.x);
		assertEquals(0, point.y);
	}
	
	@Test
	public void testConstructorIntInt() {
		final Point2I point = new Point2I(1, 2);
		
		assertEquals(1, point.x);
		assertEquals(2, point.y);
	}
	
	@Test
	public void testConstructorPoint2D() {
		final Point2I point = new Point2I(new Point2D(1.0D, 2.0D));
		
		assertEquals(1, point.x);
		assertEquals(2, point.y);
		
		assertThrows(NullPointerException.class, () -> new Point2I((Point2D)(null)));
	}
	
	@Test
	public void testConstructorVector2I() {
		final Point2I point = new Point2I(new Vector2I(1, 2));
		
		assertEquals(1, point.x);
		assertEquals(2, point.y);
		
		assertThrows(NullPointerException.class, () -> new Point2I((Vector2I)(null)));
	}
	
	@Test
	public void testDistance() {
		final Point2I a = new Point2I(0, 0);
		final Point2I b = new Point2I(9, 0);
		final Point2I c = new Point2I(0, 9);
		
		assertEquals(9, Point2I.distance(a, b));
		assertEquals(9, Point2I.distance(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2I.distance(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.distance(null, b));
	}
	
	@Test
	public void testDistanceSquared() {
		final Point2I a = new Point2I(0, 0);
		final Point2I b = new Point2I(9, 0);
		final Point2I c = new Point2I(0, 9);
		
		assertEquals(81, Point2I.distanceSquared(a, b));
		assertEquals(81, Point2I.distanceSquared(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2I.distanceSquared(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.distanceSquared(null, b));
	}
	
	@Test
	public void testEqualsObject() {
		final Point2I a = new Point2I(0, 1);
		final Point2I b = new Point2I(0, 1);
		final Point2I c = new Point2I(0, 2);
		final Point2I d = new Point2I(2, 1);
		final Point2I e = null;
		
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
	public void testEqualsPoint2I() {
		final Point2I a = new Point2I(0, 1);
		final Point2I b = new Point2I(0, 1);
		final Point2I c = new Point2I(0, 2);
		final Point2I d = new Point2I(2, 1);
		final Point2I e = null;
		
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
		final Point2I p = new Point2I(1, 2);
		
		assertEquals(1, p.getComponent(0));
		assertEquals(2, p.getComponent(1));
		
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(-1));
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(+2));
	}
	
	@Test
	public void testHashCode() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(1, 2);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Point2I(+0, +0).isZero());
		assertTrue(new Point2I(+0, -0).isZero());
		assertTrue(new Point2I(-0, +0).isZero());
		assertTrue(new Point2I(-0, -0).isZero());
		
		assertFalse(new Point2I(+0, +1).isZero());
		assertFalse(new Point2I(-0, -1).isZero());
		assertFalse(new Point2I(+1, +0).isZero());
		assertFalse(new Point2I(-1, -0).isZero());
		assertFalse(new Point2I(+1, -1).isZero());
	}
	
	@Test
	public void testMax() {
		assertEquals(new Point2I(Integer.MAX_VALUE, Integer.MAX_VALUE), Point2I.max());
	}
	
	@Test
	public void testMaxPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = Point2I.max(a, b);
		
		assertEquals(3, c.x);
		assertEquals(4, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.max(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.max(null, b));
	}
	
	@Test
	public void testMaxPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = Point2I.max(a, b, c);
		
		assertEquals(5, d.x);
		assertEquals(6, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.max(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2I.max(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2I.max(null, b, c));
	}
	
	@Test
	public void testMaxPoint2IPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = new Point2I(7, 8);
		final Point2I e = Point2I.max(a, b, c, d);
		
		assertEquals(7, e.x);
		assertEquals(8, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.max(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2I.max(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2I.max(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2I.max(null, b, c, d));
	}
	
	@Test
	public void testMidpoint() {
		final Point2I a = new Point2I(2, 4);
		final Point2I b = new Point2I(4, 8);
		final Point2I c = Point2I.midpoint(a, b);
		
		assertEquals(3, c.x);
		assertEquals(6, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.midpoint(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.midpoint(null, b));
	}
	
	@Test
	public void testMin() {
		assertEquals(new Point2I(Integer.MIN_VALUE, Integer.MIN_VALUE), Point2I.min());
	}
	
	@Test
	public void testMinPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = Point2I.min(a, b);
		
		assertEquals(1, c.x);
		assertEquals(2, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.min(a, null));
		assertThrows(NullPointerException.class, () -> Point2I.min(null, b));
	}
	
	@Test
	public void testMinPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = Point2I.min(a, b, c);
		
		assertEquals(1, d.x);
		assertEquals(2, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.min(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2I.min(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2I.min(null, b, c));
	}
	
	@Test
	public void testMinPoint2IPoint2IPoint2IPoint2I() {
		final Point2I a = new Point2I(1, 2);
		final Point2I b = new Point2I(3, 4);
		final Point2I c = new Point2I(5, 6);
		final Point2I d = new Point2I(7, 8);
		final Point2I e = Point2I.min(a, b, c, d);
		
		assertEquals(1, e.x);
		assertEquals(2, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.min(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2I.min(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2I.min(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2I.min(null, b, c, d));
	}
	
	@Test
	public void testRead() throws IOException {
		final Point2I a = new Point2I(1, 2);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeInt(a.x);
		dataOutput.writeInt(a.y);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point2I b = Point2I.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Point2I.read(null));
		assertThrows(UncheckedIOException.class, () -> Point2I.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testRotatePoint2IDouble() {
		final Point2I a = new Point2I(+9, +0);
		
		final Point2I b = Point2I.rotate(a, +90.0D);
		final Point2I c = Point2I.rotate(b, +90.0D);
		final Point2I d = Point2I.rotate(c, +90.0D);
		final Point2I e = Point2I.rotate(d, +90.0D);
		
		final Point2I f = Point2I.rotate(e, -90.0D);
		final Point2I g = Point2I.rotate(f, -90.0D);
		final Point2I h = Point2I.rotate(g, -90.0D);
		final Point2I i = Point2I.rotate(h, -90.0D);
		
		assertEquals(+0, b.x);
		assertEquals(+9, b.y);
		assertEquals(-9, c.x);
		assertEquals(+0, c.y);
		assertEquals(-0, d.x);
		assertEquals(-9, d.y);
		assertEquals(+9, e.x);
		assertEquals(+0, e.y);
		
		assertEquals(-0, f.x);
		assertEquals(-9, f.y);
		assertEquals(-9, g.x);
		assertEquals(+0, g.y);
		assertEquals(+0, h.x);
		assertEquals(+9, h.y);
		assertEquals(+9, i.x);
		assertEquals(+0, i.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0D));
	}
	
	@Test
	public void testRotatePoint2IDoubleBoolean() {
		final Point2I a = new Point2I(+9, +0);
		
		final Point2I b = Point2I.rotate(a, +90.0D, false);
		final Point2I c = Point2I.rotate(b, +90.0D, false);
		final Point2I d = Point2I.rotate(c, +90.0D, false);
		final Point2I e = Point2I.rotate(d, +90.0D, false);
		
		final Point2I f = Point2I.rotate(e, Math.toRadians(-90.0D), true);
		final Point2I g = Point2I.rotate(f, Math.toRadians(-90.0D), true);
		final Point2I h = Point2I.rotate(g, Math.toRadians(-90.0D), true);
		final Point2I i = Point2I.rotate(h, Math.toRadians(-90.0D), true);
		
		assertEquals(+0, b.x);
		assertEquals(+9, b.y);
		assertEquals(-9, c.x);
		assertEquals(+0, c.y);
		assertEquals(-0, d.x);
		assertEquals(-9, d.y);
		assertEquals(+9, e.x);
		assertEquals(+0, e.y);
		
		assertEquals(-0, f.x);
		assertEquals(-9, f.y);
		assertEquals(-9, g.x);
		assertEquals(+0, g.y);
		assertEquals(+0, h.x);
		assertEquals(+9, h.y);
		assertEquals(+9, i.x);
		assertEquals(+0, i.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0D, false));
	}
	
	@Test
	public void testRotatePoint2IDoubleBooleanPoint2I() {
		final Point2I a = new Point2I(+0, +0);
		final Point2I b = new Point2I(+9, +0);
		
		final Point2I c = Point2I.rotate(b, +90.0D, false, a);
		final Point2I d = Point2I.rotate(c, +90.0D, false, a);
		final Point2I e = Point2I.rotate(d, +90.0D, false, a);
		final Point2I f = Point2I.rotate(e, +90.0D, false, a);
		
		final Point2I g = Point2I.rotate(f, Math.toRadians(-90.0D), true, a);
		final Point2I h = Point2I.rotate(g, Math.toRadians(-90.0D), true, a);
		final Point2I i = Point2I.rotate(h, Math.toRadians(-90.0D), true, a);
		final Point2I j = Point2I.rotate(i, Math.toRadians(-90.0D), true, a);
		
		final Point2I k = new Point2I(+1, +1);
		final Point2I l = new Point2I(+8, +1);
		
		final Point2I m = Point2I.rotate(l, +90.0D, false, k);
		final Point2I n = Point2I.rotate(m, +90.0D, false, k);
		final Point2I o = Point2I.rotate(n, +90.0D, false, k);
		final Point2I p = Point2I.rotate(o, +90.0D, false, k);
		
		final Point2I q = Point2I.rotate(k, +90.0D, false, k);
		
		assertEquals(+0, c.x);
		assertEquals(+9, c.y);
		assertEquals(-9, d.x);
		assertEquals(+0, d.y);
		assertEquals(-0, e.x);
		assertEquals(-9, e.y);
		assertEquals(+9, f.x);
		assertEquals(+0, f.y);
		
		assertEquals(-0, g.x);
		assertEquals(-9, g.y);
		assertEquals(-9, h.x);
		assertEquals(+0, h.y);
		assertEquals(+0, i.x);
		assertEquals(+9, i.y);
		assertEquals(+9, j.x);
		assertEquals(+0, j.y);
		
		assertEquals(+1, m.x);
		assertEquals(+8, m.y);
		assertEquals(-6, n.x);
		assertEquals(+1, n.y);
		assertEquals(+1, o.x);
		assertEquals(-6, o.y);
		assertEquals(+8, p.x);
		assertEquals(+1, p.y);
		
		assertEquals(+1, q.x);
		assertEquals(+1, q.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(new Point2I(), 0.0D, false, null));
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0D, false, new Point2I()));
	}
	
	@Test
	public void testRotatePoint2IDoubleDoublePoint2I() {
		final Point2I a = new Point2I(+0, +0);
		final Point2I b = new Point2I(+9, +0);
		
		final Point2I c = Point2I.rotate(b, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), a);
		final Point2I d = Point2I.rotate(c, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), a);
		final Point2I e = Point2I.rotate(d, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), a);
		final Point2I f = Point2I.rotate(e, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), a);
		
		final Point2I g = Point2I.rotate(f, Math.cos(Math.toRadians(-90.0D)), Math.sin(Math.toRadians(-90.0D)), a);
		final Point2I h = Point2I.rotate(g, Math.cos(Math.toRadians(-90.0D)), Math.sin(Math.toRadians(-90.0D)), a);
		final Point2I i = Point2I.rotate(h, Math.cos(Math.toRadians(-90.0D)), Math.sin(Math.toRadians(-90.0D)), a);
		final Point2I j = Point2I.rotate(i, Math.cos(Math.toRadians(-90.0D)), Math.sin(Math.toRadians(-90.0D)), a);
		
		final Point2I k = new Point2I(+1, +1);
		final Point2I l = new Point2I(+8, +1);
		
		final Point2I m = Point2I.rotate(l, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), k);
		final Point2I n = Point2I.rotate(m, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), k);
		final Point2I o = Point2I.rotate(n, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), k);
		final Point2I p = Point2I.rotate(o, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), k);
		
		final Point2I q = Point2I.rotate(k, Math.cos(Math.toRadians(+90.0D)), Math.sin(Math.toRadians(+90.0D)), k);
		
		assertEquals(+0, c.x);
		assertEquals(+9, c.y);
		assertEquals(-9, d.x);
		assertEquals(+0, d.y);
		assertEquals(-0, e.x);
		assertEquals(-9, e.y);
		assertEquals(+9, f.x);
		assertEquals(+0, f.y);
		
		assertEquals(-0, g.x);
		assertEquals(-9, g.y);
		assertEquals(-9, h.x);
		assertEquals(+0, h.y);
		assertEquals(+0, i.x);
		assertEquals(+9, i.y);
		assertEquals(+9, j.x);
		assertEquals(+0, j.y);
		
		assertEquals(+1, m.x);
		assertEquals(+8, m.y);
		assertEquals(-6, n.x);
		assertEquals(+1, n.y);
		assertEquals(+1, o.x);
		assertEquals(-6, o.y);
		assertEquals(+8, p.x);
		assertEquals(+1, p.y);
		
		assertEquals(+1, q.x);
		assertEquals(+1, q.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(new Point2I(), 0.0D, 0.0D, null));
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0D, 0.0D, new Point2I()));
	}
	
	@Test
	public void testRotatePoint2IFloat() {
		final Point2I a = new Point2I(+9, +0);
		
		final Point2I b = Point2I.rotate(a, +90.0F);
		final Point2I c = Point2I.rotate(b, +90.0F);
		final Point2I d = Point2I.rotate(c, +90.0F);
		final Point2I e = Point2I.rotate(d, +90.0F);
		
		final Point2I f = Point2I.rotate(e, -90.0F);
		final Point2I g = Point2I.rotate(f, -90.0F);
		final Point2I h = Point2I.rotate(g, -90.0F);
		final Point2I i = Point2I.rotate(h, -90.0F);
		
		assertEquals(+0, b.x);
		assertEquals(+9, b.y);
		assertEquals(-9, c.x);
		assertEquals(+0, c.y);
		assertEquals(-0, d.x);
		assertEquals(-9, d.y);
		assertEquals(+9, e.x);
		assertEquals(+0, e.y);
		
		assertEquals(-0, f.x);
		assertEquals(-9, f.y);
		assertEquals(-9, g.x);
		assertEquals(+0, g.y);
		assertEquals(+0, h.x);
		assertEquals(+9, h.y);
		assertEquals(+9, i.x);
		assertEquals(+0, i.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0F));
	}
	
	@Test
	public void testRotatePoint2IFloatBoolean() {
		final Point2I a = new Point2I(+9, +0);
		
		final Point2I b = Point2I.rotate(a, +90.0F, false);
		final Point2I c = Point2I.rotate(b, +90.0F, false);
		final Point2I d = Point2I.rotate(c, +90.0F, false);
		final Point2I e = Point2I.rotate(d, +90.0F, false);
		
		final Point2I f = Point2I.rotate(e, (float)(Math.toRadians(-90.0F)), true);
		final Point2I g = Point2I.rotate(f, (float)(Math.toRadians(-90.0F)), true);
		final Point2I h = Point2I.rotate(g, (float)(Math.toRadians(-90.0F)), true);
		final Point2I i = Point2I.rotate(h, (float)(Math.toRadians(-90.0F)), true);
		
		assertEquals(+0, b.x);
		assertEquals(+9, b.y);
		assertEquals(-9, c.x);
		assertEquals(+0, c.y);
		assertEquals(-0, d.x);
		assertEquals(-9, d.y);
		assertEquals(+9, e.x);
		assertEquals(+0, e.y);
		
		assertEquals(-0, f.x);
		assertEquals(-9, f.y);
		assertEquals(-9, g.x);
		assertEquals(+0, g.y);
		assertEquals(+0, h.x);
		assertEquals(+9, h.y);
		assertEquals(+9, i.x);
		assertEquals(+0, i.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0F, false));
	}
	
	@Test
	public void testRotatePoint2IFloatBooleanPoint2I() {
		final Point2I a = new Point2I(+0, +0);
		final Point2I b = new Point2I(+9, +0);
		
		final Point2I c = Point2I.rotate(b, +90.0F, false, a);
		final Point2I d = Point2I.rotate(c, +90.0F, false, a);
		final Point2I e = Point2I.rotate(d, +90.0F, false, a);
		final Point2I f = Point2I.rotate(e, +90.0F, false, a);
		
		final Point2I g = Point2I.rotate(f, (float)(Math.toRadians(-90.0F)), true, a);
		final Point2I h = Point2I.rotate(g, (float)(Math.toRadians(-90.0F)), true, a);
		final Point2I i = Point2I.rotate(h, (float)(Math.toRadians(-90.0F)), true, a);
		final Point2I j = Point2I.rotate(i, (float)(Math.toRadians(-90.0F)), true, a);
		
		final Point2I k = new Point2I(+1, +1);
		final Point2I l = new Point2I(+8, +1);
		
		final Point2I m = Point2I.rotate(l, +90.0F, false, k);
		final Point2I n = Point2I.rotate(m, +90.0F, false, k);
		final Point2I o = Point2I.rotate(n, +90.0F, false, k);
		final Point2I p = Point2I.rotate(o, +90.0F, false, k);
		
		final Point2I q = Point2I.rotate(k, +90.0F, false, k);
		
		assertEquals(+0, c.x);
		assertEquals(+9, c.y);
		assertEquals(-9, d.x);
		assertEquals(+0, d.y);
		assertEquals(-0, e.x);
		assertEquals(-9, e.y);
		assertEquals(+9, f.x);
		assertEquals(+0, f.y);
		
		assertEquals(-0, g.x);
		assertEquals(-9, g.y);
		assertEquals(-9, h.x);
		assertEquals(+0, h.y);
		assertEquals(+0, i.x);
		assertEquals(+9, i.y);
		assertEquals(+9, j.x);
		assertEquals(+0, j.y);
		
		assertEquals(+1, m.x);
		assertEquals(+8, m.y);
		assertEquals(-6, n.x);
		assertEquals(+1, n.y);
		assertEquals(+1, o.x);
		assertEquals(-6, o.y);
		assertEquals(+8, p.x);
		assertEquals(+1, p.y);
		
		assertEquals(+1, q.x);
		assertEquals(+1, q.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(new Point2I(), 0.0F, false, null));
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0F, false, new Point2I()));
	}
	
	@Test
	public void testRotatePoint2IFloatFloatPoint2I() {
		final Point2I a = new Point2I(+0, +0);
		final Point2I b = new Point2I(+9, +0);
		
		final Point2I c = Point2I.rotate(b, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), a);
		final Point2I d = Point2I.rotate(c, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), a);
		final Point2I e = Point2I.rotate(d, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), a);
		final Point2I f = Point2I.rotate(e, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), a);
		
		final Point2I g = Point2I.rotate(f, (float)(Math.cos((float)(Math.toRadians(-90.0F)))), (float)(Math.sin((float)(Math.toRadians(-90.0F)))), a);
		final Point2I h = Point2I.rotate(g, (float)(Math.cos((float)(Math.toRadians(-90.0F)))), (float)(Math.sin((float)(Math.toRadians(-90.0F)))), a);
		final Point2I i = Point2I.rotate(h, (float)(Math.cos((float)(Math.toRadians(-90.0F)))), (float)(Math.sin((float)(Math.toRadians(-90.0F)))), a);
		final Point2I j = Point2I.rotate(i, (float)(Math.cos((float)(Math.toRadians(-90.0F)))), (float)(Math.sin((float)(Math.toRadians(-90.0F)))), a);
		
		final Point2I k = new Point2I(+1, +1);
		final Point2I l = new Point2I(+8, +1);
		
		final Point2I m = Point2I.rotate(l, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), k);
		final Point2I n = Point2I.rotate(m, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), k);
		final Point2I o = Point2I.rotate(n, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), k);
		final Point2I p = Point2I.rotate(o, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), k);
		
		final Point2I q = Point2I.rotate(k, (float)(Math.cos((float)(Math.toRadians(+90.0F)))), (float)(Math.sin((float)(Math.toRadians(+90.0F)))), k);
		
		assertEquals(+0, c.x);
		assertEquals(+9, c.y);
		assertEquals(-9, d.x);
		assertEquals(+0, d.y);
		assertEquals(-0, e.x);
		assertEquals(-9, e.y);
		assertEquals(+9, f.x);
		assertEquals(+0, f.y);
		
		assertEquals(-0, g.x);
		assertEquals(-9, g.y);
		assertEquals(-9, h.x);
		assertEquals(+0, h.y);
		assertEquals(+0, i.x);
		assertEquals(+9, i.y);
		assertEquals(+9, j.x);
		assertEquals(+0, j.y);
		
		assertEquals(+1, m.x);
		assertEquals(+8, m.y);
		assertEquals(-6, n.x);
		assertEquals(+1, n.y);
		assertEquals(+1, o.x);
		assertEquals(-6, o.y);
		assertEquals(+8, p.x);
		assertEquals(+1, p.y);
		
		assertEquals(+1, q.x);
		assertEquals(+1, q.y);
		
		assertThrows(NullPointerException.class, () -> Point2I.rotate(new Point2I(), 0.0F, 0.0F, null));
		assertThrows(NullPointerException.class, () -> Point2I.rotate(null, 0.0F, 0.0F, new Point2I()));
	}
	
	@Test
	public void testToArray() {
		final Point2I point = new Point2I(1, 2);
		
		final int[] array = point.toArray();
		
		assertNotNull(array);
		
		assertEquals(2, array.length);
		
		assertEquals(1, array[0]);
		assertEquals(2, array[1]);
	}
	
	@Test
	public void testToString() {
		final Point2I point = new Point2I(1, 2);
		
		assertEquals("new Point2I(1, 2)", point.toString());
	}
	
	@Test
	public void testToStringPoint2IArray() {
		final Point2I a = new Point2I(1, 1);
		final Point2I b = new Point2I(2, 2);
		final Point2I c = new Point2I(3, 3);
		
		assertEquals("new Point2I[] {new Point2I(1, 1), new Point2I(2, 2), new Point2I(3, 3)}", Point2I.toString(a, b, c));
		
		assertThrows(NullPointerException.class, () -> Point2I.toString((Point2I[])(null)));
		assertThrows(NullPointerException.class, () -> Point2I.toString(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2I.toString(new Point2I[] {a, b, c, null}));
	}
	
	@Test
	public void testWrite() {
		final Point2I a = new Point2I(1, 2);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point2I b = Point2I.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}