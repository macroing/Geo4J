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

@SuppressWarnings("static-method")
public final class Point2FUnitTests {
	public Point2FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddPoint2FFloat() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = Point2F.add(a, 2.0F);
		
		assertEquals(3.0F, b.x);
		assertEquals(4.0F, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.add(null, 2.0F));
	}
	
	@Test
	public void testAddPoint2FVector2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = Point2F.add(a, new Vector2F(1.0F, 2.0F));
		
		assertEquals(2.0F, b.x);
		assertEquals(4.0F, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.add(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.add(null, new Vector2F(1.0F, 2.0F)));
	}
	
	@Test
	public void testAddPoint2FVector2FFloat() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = Point2F.add(a, new Vector2F(1.0F, 2.0F), 2.0F);
		
		assertEquals(3.0F, b.x);
		assertEquals(6.0F, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.add(a, null, 2.0F));
		assertThrows(NullPointerException.class, () -> Point2F.add(null, new Vector2F(1.0F, 2.0F), 2.0F));
	}
	
	@Test
	public void testBarycentricInterpolation() {
		final Point2F p = Point2F.barycentricInterpolation(new Point2F(1.0F, 2.0F), new Point2F(2.0F, 3.0F), new Point2F(3.0F, 4.0F), new Point3F(1.0F, 2.0F, 3.0F));
		
		assertEquals(14.0F, p.x);
		assertEquals(20.0F, p.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.barycentricInterpolation(new Point2F(1.0F, 2.0F), new Point2F(2.0F, 3.0F), new Point2F(3.0F, 4.0F), null));
		assertThrows(NullPointerException.class, () -> Point2F.barycentricInterpolation(new Point2F(1.0F, 2.0F), new Point2F(2.0F, 3.0F), null, new Point3F(1.0F, 2.0F, 3.0F)));
		assertThrows(NullPointerException.class, () -> Point2F.barycentricInterpolation(new Point2F(1.0F, 2.0F), null, new Point2F(3.0F, 4.0F), new Point3F(1.0F, 2.0F, 3.0F)));
		assertThrows(NullPointerException.class, () -> Point2F.barycentricInterpolation(null, new Point2F(2.0F, 3.0F), new Point2F(3.0F, 4.0F), new Point3F(1.0F, 2.0F, 3.0F)));
	}
	
	@Test
	public void testCentroidPoint2FPoint2F() {
		final Point2F a = new Point2F(2.0F, 4.0F);
		final Point2F b = new Point2F(4.0F, 8.0F);
		final Point2F c = Point2F.centroid(a, b);
		
		assertEquals(3.0F, c.x);
		assertEquals(6.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(null, b));
	}
	
	@Test
	public void testCentroidPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F(2.0F,  4.0F);
		final Point2F b = new Point2F(4.0F,  8.0F);
		final Point2F c = new Point2F(6.0F, 12.0F);
		final Point2F d = Point2F.centroid(a, b, c);
		
		assertEquals(4.0F, d.x);
		assertEquals(8.0F, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(null, b, c));
	}
	
	@Test
	public void testCentroidPoint2FPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F(2.0F,  4.0F);
		final Point2F b = new Point2F(4.0F,  8.0F);
		final Point2F c = new Point2F(6.0F, 12.0F);
		final Point2F d = new Point2F(8.0F, 16.0F);
		final Point2F e = Point2F.centroid(a, b, c, d);
		
		assertEquals( 5.0F, e.x);
		assertEquals(10.0F, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(null, b, c, d));
	}
	
	@Test
	public void testCentroidPoint2FPoint2FPoint2FPoint2FPoint2FPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F( 2.0F,  4.0F);
		final Point2F b = new Point2F( 4.0F,  8.0F);
		final Point2F c = new Point2F( 6.0F, 12.0F);
		final Point2F d = new Point2F( 8.0F, 16.0F);
		final Point2F e = new Point2F(10.0F, 20.0F);
		final Point2F f = new Point2F(12.0F, 24.0F);
		final Point2F g = new Point2F(14.0F, 28.0F);
		final Point2F h = new Point2F(16.0F, 32.0F);
		final Point2F i = Point2F.centroid(a, b, c, d, e, f, g, h);
		
		assertEquals( 9.0F, i.x);
		assertEquals(18.0F, i.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, c, d, e, f, g, null));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, c, d, e, f, null, h));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, c, d, e, null, g, h));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, c, d, null, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, c, null, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, b, null, d, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(a, null, c, d, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2F.centroid(null, b, c, d, e, f, g, h));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		assertEquals(0, Point2F.getCacheSize());
		
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(1.0F, 2.0F);
		final Point2F c = Point2F.getCached(a);
		final Point2F d = Point2F.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Point2F.getCached(null));
		
		assertEquals(1, Point2F.getCacheSize());
		
		Point2F.clearCache();
		
		assertEquals(0, Point2F.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Point2F(+Float.MAX_VALUE, +Float.MAX_VALUE), Point2F.MAX);
		assertEquals(new Point2F(-Float.MAX_VALUE, -Float.MAX_VALUE), Point2F.MIN);
	}
	
	@Test
	public void testConstructor() {
		final Point2F p = new Point2F();
		
		assertEquals(0.0F, p.x);
		assertEquals(0.0F, p.y);
	}
	
	@Test
	public void testConstructorFloat() {
		final Point2F point = new Point2F(1.0F);
		
		assertEquals(1.0F, point.x);
		assertEquals(1.0F, point.y);
	}
	
	@Test
	public void testConstructorFloatFloat() {
		final Point2F p = new Point2F(1.0F, 2.0F);
		
		assertEquals(1.0F, p.x);
		assertEquals(2.0F, p.y);
	}
	
	@Test
	public void testConstructorVector2F() {
		final Point2F point = new Point2F(new Vector2F(1.0F, 1.0F));
		
		assertEquals(1.0F, point.x);
		assertEquals(1.0F, point.y);
		
		assertThrows(NullPointerException.class, () -> new Point2F((Vector2F)(null)));
	}
	
	@Test
	public void testDistance() {
		final Point2F a = new Point2F(0.0F, 0.0F);
		final Point2F b = new Point2F(9.0F, 0.0F);
		final Point2F c = new Point2F(0.0F, 9.0F);
		
		assertEquals(9.0F, Point2F.distance(a, b));
		assertEquals(9.0F, Point2F.distance(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2F.distance(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.distance(null, b));
	}
	
	@Test
	public void testDistanceSquared() {
		final Point2F a = new Point2F(0.0F, 0.0F);
		final Point2F b = new Point2F(9.0F, 0.0F);
		final Point2F c = new Point2F(0.0F, 9.0F);
		
		assertEquals(81.0F, Point2F.distanceSquared(a, b));
		assertEquals(81.0F, Point2F.distanceSquared(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2F.distanceSquared(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.distanceSquared(null, b));
	}
	
	@Test
	public void testEqualsObject() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(1.0F, 2.0F);
		final Point2F c = new Point2F(1.0F, 3.0F);
		final Point2F d = new Point2F(3.0F, 2.0F);
		final Point2F e = null;
		
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
	public void testEqualsPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(1.0F, 2.0F);
		final Point2F c = new Point2F(1.0F, 3.0F);
		final Point2F d = new Point2F(3.0F, 2.0F);
		final Point2F e = null;
		
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
		final Point2F p = new Point2F(1.0F, 2.0F);
		
		assertEquals(1.0F, p.getComponent(0));
		assertEquals(2.0F, p.getComponent(1));
		
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(-1));
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(+2));
	}
	
	@Test
	public void testHasInfinites() {
		final Point2F a = new Point2F(Float.POSITIVE_INFINITY, 2.0F);
		final Point2F b = new Point2F(1.0F, Float.POSITIVE_INFINITY);
		final Point2F c = new Point2F(1.0F, 2.0F);
		
		assertTrue(a.hasInfinites());
		assertTrue(b.hasInfinites());
		
		assertFalse(c.hasInfinites());
	}
	
	@Test
	public void testHasNaNs() {
		final Point2F a = new Point2F(Float.NaN, 2.0F);
		final Point2F b = new Point2F(1.0F, Float.NaN);
		final Point2F c = new Point2F(1.0F, 2.0F);
		
		assertTrue(a.hasNaNs());
		assertTrue(b.hasNaNs());
		
		assertFalse(c.hasNaNs());
	}
	
	@Test
	public void testHashCode() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(1.0F, 2.0F);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsFinite() {
		final Point2F a = new Point2F(Float.NaN, Float.NaN);
		final Point2F b = new Point2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		final Point2F c = new Point2F(Float.NaN, Float.NEGATIVE_INFINITY);
		final Point2F d = new Point2F(1.0F, 2.0F);
		
		assertFalse(a.isFinite());
		assertFalse(b.isFinite());
		assertFalse(c.isFinite());
		
		assertTrue(d.isFinite());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Point2F(0.0F, 0.0F).isZero());
		
		assertFalse(new Point2F(0.0F, 1.0F).isZero());
		assertFalse(new Point2F(1.0F, 0.0F).isZero());
		assertFalse(new Point2F(1.0F, 1.0F).isZero());
	}
	
	@Test
	public void testLerp() {
		final Point2F a = new Point2F(1.0F, 1.0F);
		final Point2F b = new Point2F(5.0F, 5.0F);
		final Point2F c = Point2F.lerp(a, b, 0.25F);
		
		assertEquals(2.0F, c.x);
		assertEquals(2.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.lerp(a, null, 0.25F));
		assertThrows(NullPointerException.class, () -> Point2F.lerp(null, b, 0.25F));
	}
	
	@Test
	public void testMax() {
		assertEquals(new Point2F(+Float.MAX_VALUE, +Float.MAX_VALUE), Point2F.max());
	}
	
	@Test
	public void testMaxPoint2FPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(3.0F, 4.0F);
		final Point2F c = Point2F.max(a, b);
		
		assertEquals(3.0F, c.x);
		assertEquals(4.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.max(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.max(null, b));
	}
	
	@Test
	public void testMaxPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(3.0F, 4.0F);
		final Point2F c = new Point2F(5.0F, 6.0F);
		final Point2F d = Point2F.max(a, b, c);
		
		assertEquals(5.0F, d.x);
		assertEquals(6.0F, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.max(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2F.max(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2F.max(null, b, c));
	}
	
	@Test
	public void testMaxPoint2FPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(3.0F, 4.0F);
		final Point2F c = new Point2F(5.0F, 6.0F);
		final Point2F d = new Point2F(7.0F, 8.0F);
		final Point2F e = Point2F.max(a, b, c, d);
		
		assertEquals(7.0F, e.x);
		assertEquals(8.0F, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.max(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2F.max(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2F.max(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2F.max(null, b, c, d));
	}
	
	@Test
	public void testMidpoint() {
		final Point2F a = new Point2F(2.0F, 4.0F);
		final Point2F b = new Point2F(4.0F, 8.0F);
		final Point2F c = Point2F.midpoint(a, b);
		
		assertEquals(3.0F, c.x);
		assertEquals(6.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.midpoint(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.midpoint(null, b));
	}
	
	@Test
	public void testMin() {
		assertEquals(new Point2F(-Float.MAX_VALUE, -Float.MAX_VALUE), Point2F.min());
	}
	
	@Test
	public void testMinPoint2FPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(3.0F, 4.0F);
		final Point2F c = Point2F.min(a, b);
		
		assertEquals(1.0F, c.x);
		assertEquals(2.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.min(a, null));
		assertThrows(NullPointerException.class, () -> Point2F.min(null, b));
	}
	
	@Test
	public void testMinPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(3.0F, 4.0F);
		final Point2F c = new Point2F(5.0F, 6.0F);
		final Point2F d = Point2F.min(a, b, c);
		
		assertEquals(1.0F, d.x);
		assertEquals(2.0F, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.min(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2F.min(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2F.min(null, b, c));
	}
	
	@Test
	public void testMinPoint2FPoint2FPoint2FPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = new Point2F(3.0F, 4.0F);
		final Point2F c = new Point2F(5.0F, 6.0F);
		final Point2F d = new Point2F(7.0F, 8.0F);
		final Point2F e = Point2F.min(a, b, c, d);
		
		assertEquals(1.0F, e.x);
		assertEquals(2.0F, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.min(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2F.min(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2F.min(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2F.min(null, b, c, d));
	}
	
	@Test
	public void testProject() {
		final Point2F a = Point2F.project(new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		final Point2F b = Point2F.project(new Point3F(0.0F, 1.0F, 0.0F), new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		final Point2F c = Point2F.project(new Point3F(0.0F, 0.0F, 1.0F), new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		
		assertEquals(1.0F, a.x);
		assertEquals(0.0F, a.y);
		
		assertEquals(0.0F, b.x);
		assertEquals(1.0F, b.y);
		
		assertEquals(0.0F, c.x);
		assertEquals(0.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.project(new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Point2F.project(new Point3F(1.0F, 0.0F, 0.0F), new Point3F(0.0F, 0.0F, 0.0F), null, new Vector3F(0.0F, 1.0F, 0.0F)));
		assertThrows(NullPointerException.class, () -> Point2F.project(new Point3F(1.0F, 0.0F, 0.0F), null, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F)));
		assertThrows(NullPointerException.class, () -> Point2F.project(null, new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F)));
	}
	
	@Test
	public void testRead() throws IOException {
		final Point2F a = new Point2F(1.0F, 0.5F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeFloat(a.x);
		dataOutput.writeFloat(a.y);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point2F b = Point2F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Point2F.read(null));
		assertThrows(UncheckedIOException.class, () -> Point2F.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testRotateCounterclockwise() {
		final Point2F a = new Point2F(1.0F, 1.0F);
		final Point2F b = Point2F.rotateCounterclockwise(a, 360.0F, false);
		final Point2F c = Point2F.rotateCounterclockwise(a, (float)(java.lang.Math.toRadians(360.0F)), true);
		
		assertEquals(0.9999998F, b.x);
		assertEquals(1.0000001F, b.y);
		
		assertEquals(0.9999998F, c.x);
		assertEquals(1.0000001F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.rotateCounterclockwise(null, 360.0F, false));
	}
	
	@Test
	public void testSampleDiskUniformDistribution() {
		for(int i = 0; i < 1000; i++) {
			final Point2F p = Point2F.sampleDiskUniformDistribution();
			
			assertTrue(p.x >= -1.0F && p.x <= 1.0F);
			assertTrue(p.y >= -1.0F && p.y <= 1.0F);
		}
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMapping() {
		final Point2F a = Point2F.sampleDiskUniformDistributionByConcentricMapping();
		
		assertTrue(a.x >= -1.0F && a.x <= 1.0F);
		assertTrue(a.y >= -1.0F && a.y <= 1.0F);
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMappingPoint2F() {
		final Point2F a = Point2F.sampleDiskUniformDistributionByConcentricMapping(new Point2F(0.0F, 0.0F));
		final Point2F b = Point2F.sampleDiskUniformDistributionByConcentricMapping(new Point2F(1.0F, 0.5F));
		final Point2F c = Point2F.sampleDiskUniformDistributionByConcentricMapping(new Point2F(0.5F, 1.0F));
		
		assertEquals(0.0F, a.x);
		assertEquals(0.0F, a.y);
		
		assertEquals(1.0F, b.x);
		assertEquals(0.0F, b.y);
		
		assertEquals(-0.00000004371139F, c.x);
		assertEquals(+1.00000000000000F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.sampleDiskUniformDistributionByConcentricMapping(null));
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMappingPoint2FFloat() {
		final Point2F a = Point2F.sampleDiskUniformDistributionByConcentricMapping(new Point2F(0.0F, 0.0F), 1.0F);
		final Point2F b = Point2F.sampleDiskUniformDistributionByConcentricMapping(new Point2F(1.0F, 0.5F), 1.0F);
		final Point2F c = Point2F.sampleDiskUniformDistributionByConcentricMapping(new Point2F(0.5F, 1.0F), 1.0F);
		
		assertEquals(0.0F, a.x);
		assertEquals(0.0F, a.y);
		
		assertEquals(1.0F, b.x);
		assertEquals(0.0F, b.y);
		
		assertEquals(-0.00000004371139F, c.x);
		assertEquals(+1.00000000000000F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.sampleDiskUniformDistributionByConcentricMapping(null, 1.0F));
	}
	
	@Test
	public void testSampleDiskUniformDistributionPoint2F() {
		final Point2F a = Point2F.sampleDiskUniformDistribution(new Point2F(0.0F, 0.0F));
		final Point2F b = Point2F.sampleDiskUniformDistribution(new Point2F(0.0F, 1.0F));
		final Point2F c = Point2F.sampleDiskUniformDistribution(new Point2F(1.0F, 0.0F));
		final Point2F d = Point2F.sampleDiskUniformDistribution(new Point2F(1.0F, 1.0F));
		
		assertEquals(new Point2F((float)(Math.sqrt(0.0F)) * (float)(Math.cos((float)(Math.PI) * 2.0F * 0.0F)), (float)(Math.sqrt(0.0F)) * (float)(Math.sin((float)(Math.PI) * 2.0F * 0.0F))), a);
		assertEquals(new Point2F((float)(Math.sqrt(0.0F)) * (float)(Math.cos((float)(Math.PI) * 2.0F * 1.0F)), (float)(Math.sqrt(0.0F)) * (float)(Math.sin((float)(Math.PI) * 2.0F * 1.0F))), b);
		assertEquals(new Point2F((float)(Math.sqrt(1.0F)) * (float)(Math.cos((float)(Math.PI) * 2.0F * 0.0F)), (float)(Math.sqrt(1.0F)) * (float)(Math.sin((float)(Math.PI) * 2.0F * 0.0F))), c);
		assertEquals(new Point2F((float)(Math.sqrt(1.0F)) * (float)(Math.cos((float)(Math.PI) * 2.0F * 1.0F)), (float)(Math.sqrt(1.0F)) * (float)(Math.sin((float)(Math.PI) * 2.0F * 1.0F))), d);
		
		assertThrows(NullPointerException.class, () -> Point2F.sampleDiskUniformDistribution(null));
	}
	
	@Test
	public void testSampleExactInverseTentFilter() {
		final Point2F p = Point2F.sampleExactInverseTentFilter();
		
		assertTrue(p.x >= -1.0F && p.x <= 1.0F);
		assertTrue(p.y >= -1.0F && p.y <= 1.0F);
	}
	
	@Test
	public void testSampleExactInverseTentFilterPoint2F() {
		final Point2F a = Point2F.sampleExactInverseTentFilter(new Point2F(0.0F, 0.0F));
		final Point2F b = Point2F.sampleExactInverseTentFilter(new Point2F(0.5F, 0.5F));
		final Point2F c = Point2F.sampleExactInverseTentFilter(new Point2F(1.0F, 1.0F));
		
		assertEquals(-1.0F, a.x);
		assertEquals(-1.0F, a.y);
		
		assertEquals(0.0F, b.x);
		assertEquals(0.0F, b.y);
		
		assertEquals(1.0F, c.x);
		assertEquals(1.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.sampleExactInverseTentFilter(null));
	}
	
	@Test
	public void testSampleRandom() {
		final Point2F p = Point2F.sampleRandom();
		
		assertTrue(p.x >= 0.0F && p.x < 1.0F);
		assertTrue(p.y >= 0.0F && p.y < 1.0F);
	}
	
	@Test
	public void testScale() {
		final Point2F p = Point2F.scale(new Point2F(2.0F, 3.0F), new Vector2F(2.0F, 3.0F));
		
		assertEquals(4.0F, p.x);
		assertEquals(9.0F, p.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.scale(new Point2F(2.0F, 3.0F), null));
		assertThrows(NullPointerException.class, () -> Point2F.scale(null, new Vector2F(2.0F, 3.0F)));
	}
	
	@Test
	public void testSphericalCoordinates() {
		final Point2F p = Point2F.sphericalCoordinates(new Vector3F(1.0F, 0.0F, 0.0F));
		
		assertEquals(0.0F, p.x);
		assertEquals(0.5F, p.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.sphericalCoordinates(null));
	}
	
	@Test
	public void testSubtractPoint2FFloat() {
		final Point2F a = new Point2F(2.0F, 4.0F);
		final Point2F b = Point2F.subtract(a, 2.0F);
		
		assertEquals(0.0F, b.x);
		assertEquals(2.0F, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.subtract(null, 2.0F));
	}
	
	@Test
	public void testSubtractPoint2FVector2F() {
		final Point2F a = new Point2F(2.0F, 4.0F);
		final Point2F b = Point2F.subtract(a, new Vector2F(1.0F, 2.0F));
		
		assertEquals(1.0F, b.x);
		assertEquals(2.0F, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2F.subtract(null, new Vector2F(1.0F, 2.0F)));
		assertThrows(NullPointerException.class, () -> Point2F.subtract(a, null));
	}
	
	@Test
	public void testToArray() {
		final Point2F point = new Point2F(1.0F, 2.0F);
		
		final float[] array = point.toArray();
		
		assertNotNull(array);
		
		assertEquals(2, array.length);
		
		assertEquals(1.0F, array[0]);
		assertEquals(2.0F, array[1]);
	}
	
	@Test
	public void testToString() {
		final Point2F p = new Point2F(1.0F, 2.0F);
		
		assertEquals("new Point2F(1.0F, 2.0F)", p.toString());
	}
	
	@Test
	public void testToStringPoint2FArray() {
		final Point2F a = new Point2F(1.0F, 1.0F);
		final Point2F b = new Point2F(2.0F, 2.0F);
		final Point2F c = new Point2F(3.0F, 3.0F);
		
		assertEquals("new Point2F[] {new Point2F(1.0F, 1.0F), new Point2F(2.0F, 2.0F), new Point2F(3.0F, 3.0F)}", Point2F.toString(a, b, c));
		
		assertThrows(NullPointerException.class, () -> Point2F.toString((Point2F[])(null)));
		assertThrows(NullPointerException.class, () -> Point2F.toString(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2F.toString(new Point2F[] {a, b, c, null}));
	}
	
	@Test
	public void testWrite() {
		final Point2F a = new Point2F(1.0F, 0.5F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point2F b = Point2F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}