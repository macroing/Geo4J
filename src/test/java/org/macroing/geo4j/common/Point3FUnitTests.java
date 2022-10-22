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
import org.macroing.java.lang.Floats;

@SuppressWarnings("static-method")
public final class Point3FUnitTests {
	public Point3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddPoint3FFloat() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = Point3F.add(a, 2.0F);
		
		assertEquals(3.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(5.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.add(null, 2.0F));
	}
	
	@Test
	public void testAddPoint3FVector3F() {
		final Point3F p = Point3F.add(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F));
		
		assertEquals(2.0F, p.x);
		assertEquals(4.0F, p.y);
		assertEquals(6.0F, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.add(new Point3F(1.0F, 2.0F, 3.0F), null));
		assertThrows(NullPointerException.class, () -> Point3F.add(null, new Vector3F(1.0F, 2.0F, 3.0F)));
	}
	
	@Test
	public void testAddPoint3FVector3FFloat() {
		final Point3F p = Point3F.add(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), 2.0F);
		
		assertEquals(3.0F, p.x);
		assertEquals(6.0F, p.y);
		assertEquals(9.0F, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.add(new Point3F(1.0F, 2.0F, 3.0F), null, 2.0F));
		assertThrows(NullPointerException.class, () -> Point3F.add(null, new Vector3F(1.0F, 2.0F, 3.0F), 2.0F));
	}
	
	@Test
	public void testCentroidPoint3FPoint3F() {
		final Point3F a = new Point3F(2.0F, 4.0F,  6.0F);
		final Point3F b = new Point3F(4.0F, 8.0F, 12.0F);
		final Point3F c = Point3F.centroid(a, b);
		
		assertEquals(3.0F, c.x);
		assertEquals(6.0F, c.y);
		assertEquals(9.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, null));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(null, b));
	}
	
	@Test
	public void testCentroidPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F(2.0F,  4.0F,  6.0F);
		final Point3F b = new Point3F(4.0F,  8.0F, 12.0F);
		final Point3F c = new Point3F(6.0F, 12.0F, 18.0F);
		final Point3F d = Point3F.centroid(a, b, c);
		
		assertEquals( 4.0F, d.x);
		assertEquals( 8.0F, d.y);
		assertEquals(12.0F, d.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, null));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, null, c));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(null, b, c));
	}
	
	@Test
	public void testCentroidPoint3FPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F(2.0F,  4.0F,  6.0F);
		final Point3F b = new Point3F(4.0F,  8.0F, 12.0F);
		final Point3F c = new Point3F(6.0F, 12.0F, 18.0F);
		final Point3F d = new Point3F(8.0F, 16.0F, 24.0F);
		final Point3F e = Point3F.centroid(a, b, c, d);
		
		assertEquals( 5.0F, e.x);
		assertEquals(10.0F, e.y);
		assertEquals(15.0F, e.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(null, b, c, d));
	}
	
	@Test
	public void testCentroidPoint3FPoint3FPoint3FPoint3FPoint3FPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F( 2.0F,  4.0F,  6.0F);
		final Point3F b = new Point3F( 4.0F,  8.0F, 12.0F);
		final Point3F c = new Point3F( 6.0F, 12.0F, 18.0F);
		final Point3F d = new Point3F( 8.0F, 16.0F, 24.0F);
		final Point3F e = new Point3F(10.0F, 20.0F, 30.0F);
		final Point3F f = new Point3F(12.0F, 24.0F, 36.0F);
		final Point3F g = new Point3F(14.0F, 28.0F, 42.0F);
		final Point3F h = new Point3F(16.0F, 32.0F, 48.0F);
		final Point3F i = Point3F.centroid(a, b, c, d, e, f, g, h);
		
		assertEquals( 9.0F, i.x);
		assertEquals(18.0F, i.y);
		assertEquals(27.0F, i.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, c, d, e, f, g, null));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, c, d, e, f, null, h));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, c, d, e, null, g, h));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, c, d, null, f, g, h));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, c, null, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, b, null, d, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(a, null, c, d, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point3F.centroid(null, b, c, d, e, f, g, h));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		Point3F.clearCache();
		
		assertEquals(0, Point3F.getCacheSize());
		
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F c = Point3F.getCached(a);
		final Point3F d = Point3F.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Point3F.getCached(null));
		
		assertEquals(1, Point3F.getCacheSize());
		
		Point3F.clearCache();
		
		assertEquals(0, Point3F.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Point3F(+Float.MAX_VALUE, +Float.MAX_VALUE, +Float.MAX_VALUE), Point3F.MAX);
		assertEquals(new Point3F(-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE), Point3F.MIN);
	}
	
	@Test
	public void testConstructor() {
		final Point3F p = new Point3F();
		
		assertEquals(0.0F, p.x);
		assertEquals(0.0F, p.y);
		assertEquals(0.0F, p.z);
	}
	
	@Test
	public void testConstructorFloat() {
		final Point3F p = new Point3F(1.0F);
		
		assertEquals(1.0F, p.x);
		assertEquals(1.0F, p.y);
		assertEquals(1.0F, p.z);
	}
	
	@Test
	public void testConstructorFloatFloatFloat() {
		final Point3F p = new Point3F(1.0F, 2.0F, 3.0F);
		
		assertEquals(1.0F, p.x);
		assertEquals(2.0F, p.y);
		assertEquals(3.0F, p.z);
	}
	
	@Test
	public void testConstructorVector3F() {
		final Point3F p = new Point3F(new Vector3F(1.0F, 2.0F, 3.0F));
		
		assertEquals(1.0F, p.x);
		assertEquals(2.0F, p.y);
		assertEquals(3.0F, p.z);
		
		assertThrows(NullPointerException.class, () -> new Point3F((Vector3F)(null)));
	}
	
	@Test
	public void testCoplanar() {
		assertTrue(Point3F.coplanar(new Point3F(1.0F, 2.0F, 3.0F), new Point3F(4.0F, 5.0F, 6.0F), new Point3F(7.0F, 8.0F, 9.0F)));
		assertTrue(Point3F.coplanar(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 1.0F, 0.0F), new Point3F(2.0F, 2.0F, 0.0F)));
		assertTrue(Point3F.coplanar(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 1.0F), new Point3F(2.0F, 0.0F, 2.0F)));
		assertTrue(Point3F.coplanar(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(0.0F, 1.0F, 0.0F), new Point3F(0.0F, 0.0F, 1.0F), new Point3F(0.0F, 2.0F, 2.0F)));
		
		assertFalse(Point3F.coplanar(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 1.0F, 0.0F), new Point3F(2.0F, 2.0F, 2.0F)));
		assertFalse(Point3F.coplanar(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 1.0F), new Point3F(2.0F, 2.0F, 2.0F)));
		assertFalse(Point3F.coplanar(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(0.0F, 1.0F, 0.0F), new Point3F(0.0F, 0.0F, 1.0F), new Point3F(2.0F, 2.0F, 2.0F)));
		
		assertThrows(NullPointerException.class, () -> Point3F.coplanar((Point3F[])(null)));
		assertThrows(NullPointerException.class, () -> Point3F.coplanar(new Point3F(), null, new Point3F()));
		
		assertThrows(IllegalArgumentException.class, () -> Point3F.coplanar(new Point3F(), new Point3F()));
	}
	
	@Test
	public void testDistance() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(1.0F, 6.0F, 3.0F);
		
		assertEquals(4.0F, Point3F.distance(a, b));
		
		assertThrows(NullPointerException.class, () -> Point3F.distance(a, null));
		assertThrows(NullPointerException.class, () -> Point3F.distance(null, b));
	}
	
	@Test
	public void testDistanceSquared() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(2.0F, 4.0F, 6.0F);
		
		assertEquals(14.0F, Point3F.distanceSquared(a, b));
		
		assertThrows(NullPointerException.class, () -> Point3F.distanceSquared(a, null));
		assertThrows(NullPointerException.class, () -> Point3F.distanceSquared(null, b));
	}
	
	@Test
	public void testEqualsObject() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F c = new Point3F(1.0F, 2.0F, 4.0F);
		final Point3F d = new Point3F(1.0F, 4.0F, 3.0F);
		final Point3F e = new Point3F(4.0F, 2.0F, 3.0F);
		final Point3F f = null;
		
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
	public void testEqualsPoint3F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F c = new Point3F(1.0F, 2.0F, 4.0F);
		final Point3F d = new Point3F(1.0F, 4.0F, 3.0F);
		final Point3F e = new Point3F(4.0F, 2.0F, 3.0F);
		final Point3F f = null;
		
		assertTrue(a.equals(a));
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		
		assertFalse(a.equals(c));
		assertFalse(c.equals(a));
		assertFalse(a.equals(d));
		assertFalse(d.equals(a));
		assertFalse(a.equals(e));
		assertFalse(e.equals(a));
		assertFalse(a.equals(f));
	}
	
	@Test
	public void testGetComponent() {
		final Point3F p = new Point3F(1.0F, 2.0F, 3.0F);
		
		assertEquals(1.0F, p.getComponent(0));
		assertEquals(2.0F, p.getComponent(1));
		assertEquals(3.0F, p.getComponent(2));
		
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(-1));
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(+3));
	}
	
	@Test
	public void testHashCode() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(1.0F, 2.0F, 3.0F);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testLerp() {
		final Point3F a = Point3F.lerp(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 20.0F, 20.0F), 0.0F);
		final Point3F b = Point3F.lerp(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 20.0F, 20.0F), 0.5F);
		final Point3F c = Point3F.lerp(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 20.0F, 20.0F), 1.0F);
		
		assertEquals(new Point3F(10.0F, 10.0F, 10.0F), a);
		assertEquals(new Point3F(15.0F, 15.0F, 15.0F), b);
		assertEquals(new Point3F(20.0F, 20.0F, 20.0F), c);
		
		assertThrows(NullPointerException.class, () -> Point3F.lerp(new Point3F(10.0F, 10.0F, 10.0F), null, 0.5F));
		assertThrows(NullPointerException.class, () -> Point3F.lerp(null, new Point3F(20.0F, 20.0F, 20.0F), 0.5F));
	}
	
	@Test
	public void testMax() {
		final Point3F p = Point3F.max();
		
		assertEquals(+Float.MAX_VALUE, p.x);
		assertEquals(+Float.MAX_VALUE, p.y);
		assertEquals(+Float.MAX_VALUE, p.z);
	}
	
	@Test
	public void testMaxPoint3FPoint3F() {
		final Point3F p = Point3F.max(new Point3F(1.0F, 4.0F, 5.0F), new Point3F(2.0F, 3.0F, 6.0F));
		
		assertEquals(2.0F, p.x);
		assertEquals(4.0F, p.y);
		assertEquals(6.0F, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.max(new Point3F(1.0F, 4.0F, 5.0F), null));
		assertThrows(NullPointerException.class, () -> Point3F.max(null, new Point3F(2.0F, 3.0F, 6.0F)));
	}
	
	@Test
	public void testMaxPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(4.0F, 5.0F, 6.0F);
		final Point3F c = new Point3F(7.0F, 8.0F, 9.0F);
		final Point3F d = Point3F.max(a, b, c);
		
		assertEquals(7.0F, d.x);
		assertEquals(8.0F, d.y);
		assertEquals(9.0F, d.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.max(a, b, null));
		assertThrows(NullPointerException.class, () -> Point3F.max(a, null, c));
		assertThrows(NullPointerException.class, () -> Point3F.max(null, b, c));
	}
	
	@Test
	public void testMaxPoint3FPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F( 1.0F,  2.0F,  3.0F);
		final Point3F b = new Point3F( 4.0F,  5.0F,  6.0F);
		final Point3F c = new Point3F( 7.0F,  8.0F,  9.0F);
		final Point3F d = new Point3F(10.0F, 11.0F, 12.0F);
		final Point3F e = Point3F.max(a, b, c, d);
		
		assertEquals(10.0F, e.x);
		assertEquals(11.0F, e.y);
		assertEquals(12.0F, e.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.max(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point3F.max(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point3F.max(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point3F.max(null, b, c, d));
	}
	
	@Test
	public void testMidpoint() {
		final Point3F p = Point3F.midpoint(new Point3F(10.0F, 20.0F, 30.0F), new Point3F(20.0F, 30.0F, 40.0F));
		
		assertEquals(15.0F, p.x);
		assertEquals(25.0F, p.y);
		assertEquals(35.0F, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.midpoint(new Point3F(10.0F, 20.0F, 30.0F), null));
		assertThrows(NullPointerException.class, () -> Point3F.midpoint(null, new Point3F(20.0F, 30.0F, 40.0F)));
	}
	
	@Test
	public void testMin() {
		final Point3F p = Point3F.min();
		
		assertEquals(-Float.MAX_VALUE, p.x);
		assertEquals(-Float.MAX_VALUE, p.y);
		assertEquals(-Float.MAX_VALUE, p.z);
	}
	
	@Test
	public void testMinPoint3FPoint3F() {
		final Point3F p = Point3F.min(new Point3F(1.0F, 4.0F, 5.0F), new Point3F(2.0F, 3.0F, 6.0F));
		
		assertEquals(1.0F, p.x);
		assertEquals(3.0F, p.y);
		assertEquals(5.0F, p.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.min(new Point3F(1.0F, 4.0F, 5.0F), null));
		assertThrows(NullPointerException.class, () -> Point3F.min(null, new Point3F(2.0F, 3.0F, 6.0F)));
	}
	
	@Test
	public void testMinPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = new Point3F(4.0F, 5.0F, 6.0F);
		final Point3F c = new Point3F(7.0F, 8.0F, 9.0F);
		final Point3F d = Point3F.min(a, b, c);
		
		assertEquals(1.0F, d.x);
		assertEquals(2.0F, d.y);
		assertEquals(3.0F, d.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.min(a, b, null));
		assertThrows(NullPointerException.class, () -> Point3F.min(a, null, c));
		assertThrows(NullPointerException.class, () -> Point3F.min(null, b, c));
	}
	
	@Test
	public void testMinPoint3FPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F( 1.0F,  2.0F,  3.0F);
		final Point3F b = new Point3F( 4.0F,  5.0F,  6.0F);
		final Point3F c = new Point3F( 7.0F,  8.0F,  9.0F);
		final Point3F d = new Point3F(10.0F, 11.0F, 12.0F);
		final Point3F e = Point3F.min(a, b, c, d);
		
		assertEquals(1.0F, e.x);
		assertEquals(2.0F, e.y);
		assertEquals(3.0F, e.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.min(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point3F.min(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point3F.min(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point3F.min(null, b, c, d));
	}
	
	@Test
	public void testOffset() {
		final Point3F a = Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(+1.0F, +0.0F, +0.0F), new Vector3F(1.0F, 0.0F, 0.0F));
		final Point3F b = Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(-1.0F, +0.0F, +0.0F), new Vector3F(1.0F, 0.0F, 0.0F));
		
		final Point3F c = Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(+0.0F, +1.0F, +0.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		final Point3F d = Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(+0.0F, -1.0F, +0.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		
		final Point3F e = Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(+0.0F, +0.0F, +1.0F), new Vector3F(0.0F, 0.0F, 1.0F));
		final Point3F f = Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(+0.0F, +0.0F, -1.0F), new Vector3F(0.0F, 0.0F, 1.0F));
		
		assertEquals(Math.nextUp  (1.0F), a.x);
		assertEquals(Math.nextDown(0.0F), a.y);
		assertEquals(Math.nextDown(0.0F), a.z);
		
		assertEquals(Math.nextDown(1.0F), b.x);
		assertEquals(Math.nextDown(0.0F), b.y);
		assertEquals(Math.nextDown(0.0F), b.z);
		
		assertEquals(Math.nextDown(0.0F), c.x);
		assertEquals(Math.nextUp  (1.0F), c.y);
		assertEquals(Math.nextDown(0.0F), c.z);
		
		assertEquals(Math.nextDown(0.0F), d.x);
		assertEquals(Math.nextDown(1.0F), d.y);
		assertEquals(Math.nextDown(0.0F), d.z);
		
		assertEquals(Math.nextDown(0.0F), e.x);
		assertEquals(Math.nextDown(0.0F), e.y);
		assertEquals(Math.nextUp  (1.0F), e.z);
		
		assertEquals(Math.nextDown(0.0F), f.x);
		assertEquals(Math.nextDown(0.0F), f.y);
		assertEquals(Math.nextDown(1.0F), f.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), null, new Vector3F(1.0F, 0.0F, 0.0F)));
		assertThrows(NullPointerException.class, () -> Point3F.offset(new Point3F(0.0F, 0.0F, 0.0F), null, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F)));
		assertThrows(NullPointerException.class, () -> Point3F.offset(null, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F)));
	}
	
	@Test
	public void testRead() throws IOException {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeFloat(a.x);
		dataOutput.writeFloat(a.y);
		dataOutput.writeFloat(a.z);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point3F b = Point3F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Point3F.read(null));
		assertThrows(UncheckedIOException.class, () -> Point3F.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testSampleTriangleUniformDistribution() {
		for(int i = 0; i < 1000; i++) {
			final Point3F p = Point3F.sampleTriangleUniformDistribution();
			
			assertTrue(p.x >= 0.0F && p.x <= 1.0F);
			assertTrue(p.y >= 0.0F && p.y <= 1.0F);
			assertTrue(p.z >= 0.0F && p.z <= 1.0F);
			
			final float sum = p.x + p.y + p.z;
			
			assertTrue(sum >= 0.0F && sum <= 1.0F);
		}
	}
	
	@Test
	public void testSampleTriangleUniformDistributionPoint2F() {
		final Point3F a = Point3F.sampleTriangleUniformDistribution(new Point2F(1.0F, 0.5F));
		final Point3F b = Point3F.sampleTriangleUniformDistribution(new Point2F(0.0F, 0.5F));
		
		assertEquals(0.0F, a.x);
		assertEquals(0.5F, a.y);
		assertEquals(0.5F, a.z);
		
		assertEquals(1.0F, b.x);
		assertEquals(0.0F, b.y);
		assertEquals(0.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.sampleTriangleUniformDistribution(null));
	}
	
	@Test
	public void testScalePoint3FVector2F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = Point3F.scale(a, new Vector2F(2.0F, 3.0F));
		
		assertEquals(2.0F, b.x);
		assertEquals(6.0F, b.y);
		assertEquals(3.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.scale(a, (Vector2F)(null)));
		assertThrows(NullPointerException.class, () -> Point3F.scale(null, new Vector2F(1.0F, 1.0F)));
	}
	
	@Test
	public void testScalePoint3FVector3F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = Point3F.scale(a, new Vector3F(2.0F, 3.0F, 4.0F));
		
		assertEquals( 2.0F, b.x);
		assertEquals( 6.0F, b.y);
		assertEquals(12.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.scale(a, (Vector3F)(null)));
		assertThrows(NullPointerException.class, () -> Point3F.scale(null, new Vector3F(1.0F, 1.0F, 1.0F)));
	}
	
	@Test
	public void testSphericalPhi() {
		final Point3F x = new Point3F(1.0F, 0.0F, 0.0F);
		final Point3F y = new Point3F(0.0F, 1.0F, 0.0F);
		final Point3F z = new Point3F(0.0F, 0.0F, 1.0F);
		
		assertEquals(0.0F,              x.sphericalPhi());
		assertEquals(Floats.PI / 2.0F, y.sphericalPhi());
		assertEquals(0.0F,              z.sphericalPhi());
	}
	
	@Test
	public void testSubtractPoint3FFloat() {
		final Point3F a = new Point3F(3.0F, 4.0F, 5.0F);
		final Point3F b = Point3F.subtract(a, 3.0F);
		
		assertEquals(0.0F, b.x);
		assertEquals(1.0F, b.y);
		assertEquals(2.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.subtract(null, 1.0F));
	}
	
	@Test
	public void testSubtractPoint3FVector3F() {
		final Point3F a = new Point3F(3.0F, 4.0F, 5.0F);
		final Point3F b = Point3F.subtract(a, new Vector3F(1.0F, 3.0F, 5.0F));
		
		assertEquals(2.0F, b.x);
		assertEquals(1.0F, b.y);
		assertEquals(0.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Point3F.subtract(a, (Vector3F)(null)));
		assertThrows(NullPointerException.class, () -> Point3F.subtract(null, new Vector3F(1.0F, 1.0F, 1.0F)));
	}
	
	@Test
	public void testToArray() {
		final Point3F point = new Point3F(1.0F, 2.0F, 3.0F);
		
		final float[] array = point.toArray();
		
		assertNotNull(array);
		
		assertEquals(3, array.length);
		
		assertEquals(1.0F, array[0]);
		assertEquals(2.0F, array[1]);
		assertEquals(3.0F, array[2]);
	}
	
	@Test
	public void testToString() {
		final Point3F p = new Point3F(1.0F, 2.0F, 3.0F);
		
		assertEquals("new Point3F(1.0F, 2.0F, 3.0F)", p.toString());
	}
	
	@Test
	public void testToStringPoint3FArray() {
		final Point3F a = new Point3F(1.0F, 1.0F, 1.0F);
		final Point3F b = new Point3F(2.0F, 2.0F, 2.0F);
		final Point3F c = new Point3F(3.0F, 3.0F, 3.0F);
		
		assertEquals("new Point3F[] {new Point3F(1.0F, 1.0F, 1.0F), new Point3F(2.0F, 2.0F, 2.0F), new Point3F(3.0F, 3.0F, 3.0F)}", Point3F.toString(a, b, c));
		
		assertThrows(NullPointerException.class, () -> Point3F.toString((Point3F[])(null)));
		assertThrows(NullPointerException.class, () -> Point3F.toString(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point3F.toString(new Point3F[] {a, b, c, null}));
	}
	
	@Test
	public void testWrite() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point3F b = Point3F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}