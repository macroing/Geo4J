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
public final class Point2DUnitTests {
	public Point2DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAddPoint2DDouble() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = Point2D.add(a, 2.0D);
		
		assertEquals(3.0D, b.x);
		assertEquals(4.0D, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.add(null, 2.0D));
	}
	
	@Test
	public void testAddPoint2DVector2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = Point2D.add(a, new Vector2D(1.0D, 2.0D));
		
		assertEquals(2.0D, b.x);
		assertEquals(4.0D, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.add(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.add(null, new Vector2D(1.0D, 2.0D)));
	}
	
	@Test
	public void testAddPoint2DVector2DDouble() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = Point2D.add(a, new Vector2D(1.0D, 2.0D), 2.0D);
		
		assertEquals(3.0D, b.x);
		assertEquals(6.0D, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.add(a, null, 2.0D));
		assertThrows(NullPointerException.class, () -> Point2D.add(null, new Vector2D(1.0D, 2.0D), 2.0D));
	}
	
	@Test
	public void testCentroidPoint2DPoint2D() {
		final Point2D a = new Point2D(2.0D, 4.0D);
		final Point2D b = new Point2D(4.0D, 8.0D);
		final Point2D c = Point2D.centroid(a, b);
		
		assertEquals(3.0D, c.x);
		assertEquals(6.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(null, b));
	}
	
	@Test
	public void testCentroidPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D(2.0D,  4.0D);
		final Point2D b = new Point2D(4.0D,  8.0D);
		final Point2D c = new Point2D(6.0D, 12.0D);
		final Point2D d = Point2D.centroid(a, b, c);
		
		assertEquals(4.0D, d.x);
		assertEquals(8.0D, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(null, b, c));
	}
	
	@Test
	public void testCentroidPoint2DPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D(2.0D,  4.0D);
		final Point2D b = new Point2D(4.0D,  8.0D);
		final Point2D c = new Point2D(6.0D, 12.0D);
		final Point2D d = new Point2D(8.0D, 16.0D);
		final Point2D e = Point2D.centroid(a, b, c, d);
		
		assertEquals( 5.0D, e.x);
		assertEquals(10.0D, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(null, b, c, d));
	}
	
	@Test
	public void testCentroidPoint2DPoint2DPoint2DPoint2DPoint2DPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D( 2.0D,  4.0D);
		final Point2D b = new Point2D( 4.0D,  8.0D);
		final Point2D c = new Point2D( 6.0D, 12.0D);
		final Point2D d = new Point2D( 8.0D, 16.0D);
		final Point2D e = new Point2D(10.0D, 20.0D);
		final Point2D f = new Point2D(12.0D, 24.0D);
		final Point2D g = new Point2D(14.0D, 28.0D);
		final Point2D h = new Point2D(16.0D, 32.0D);
		final Point2D i = Point2D.centroid(a, b, c, d, e, f, g, h);
		
		assertEquals( 9.0D, i.x);
		assertEquals(18.0D, i.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, c, d, e, f, g, null));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, c, d, e, f, null, h));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, c, d, e, null, g, h));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, c, d, null, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, c, null, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, b, null, d, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(a, null, c, d, e, f, g, h));
		assertThrows(NullPointerException.class, () -> Point2D.centroid(null, b, c, d, e, f, g, h));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		assertEquals(0, Point2D.getCacheSize());
		
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		final Point2D c = Point2D.getCached(a);
		final Point2D d = Point2D.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Point2D.getCached(null));
		
		assertEquals(1, Point2D.getCacheSize());
		
		Point2D.clearCache();
		
		assertEquals(0, Point2D.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Point2D(+Double.MAX_VALUE, +Double.MAX_VALUE), Point2D.MAX);
		assertEquals(new Point2D(-Double.MAX_VALUE, -Double.MAX_VALUE), Point2D.MIN);
	}
	
	@Test
	public void testConstructor() {
		final Point2D p = new Point2D();
		
		assertEquals(0.0D, p.x);
		assertEquals(0.0D, p.y);
	}
	
	@Test
	public void testConstructorDouble() {
		final Point2D point = new Point2D(1.0D);
		
		assertEquals(1.0D, point.x);
		assertEquals(1.0D, point.y);
	}
	
	@Test
	public void testConstructorDoubleDouble() {
		final Point2D p = new Point2D(1.0D, 2.0D);
		
		assertEquals(1.0D, p.x);
		assertEquals(2.0D, p.y);
	}
	
	@Test
	public void testConstructorVector2D() {
		final Point2D point = new Point2D(new Vector2D(1.0F, 1.0F));
		
		assertEquals(1.0D, point.x);
		assertEquals(1.0D, point.y);
		
		assertThrows(NullPointerException.class, () -> new Point2D((Vector2D)(null)));
	}
	
	@Test
	public void testDistance() {
		final Point2D a = new Point2D(0.0D, 0.0D);
		final Point2D b = new Point2D(9.0D, 0.0D);
		final Point2D c = new Point2D(0.0D, 9.0D);
		
		assertEquals(9.0D, Point2D.distance(a, b));
		assertEquals(9.0D, Point2D.distance(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2D.distance(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.distance(null, b));
	}
	
	@Test
	public void testDistanceSquared() {
		final Point2D a = new Point2D(0.0D, 0.0D);
		final Point2D b = new Point2D(9.0D, 0.0D);
		final Point2D c = new Point2D(0.0D, 9.0D);
		
		assertEquals(81.0D, Point2D.distanceSquared(a, b));
		assertEquals(81.0D, Point2D.distanceSquared(a, c));
		
		assertThrows(NullPointerException.class, () -> Point2D.distanceSquared(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.distanceSquared(null, b));
	}
	
	@Test
	public void testEqualsObject() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		final Point2D c = new Point2D(1.0D, 3.0D);
		final Point2D d = new Point2D(3.0D, 2.0D);
		final Point2D e = null;
		
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
	public void testEqualsPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		final Point2D c = new Point2D(1.0D, 3.0D);
		final Point2D d = new Point2D(3.0D, 2.0D);
		final Point2D e = null;
		
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
		final Point2D p = new Point2D(1.0D, 2.0D);
		
		assertEquals(1.0D, p.getComponent(0));
		assertEquals(2.0D, p.getComponent(1));
		
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(-1));
		assertThrows(IllegalArgumentException.class, () -> p.getComponent(+2));
	}
	
	@Test
	public void testHasInfinites() {
		final Point2D a = new Point2D(Double.POSITIVE_INFINITY, 2.0D);
		final Point2D b = new Point2D(1.0D, Double.POSITIVE_INFINITY);
		final Point2D c = new Point2D(1.0D, 2.0D);
		
		assertTrue(a.hasInfinites());
		assertTrue(b.hasInfinites());
		
		assertFalse(c.hasInfinites());
	}
	
	@Test
	public void testHasNaNs() {
		final Point2D a = new Point2D(Double.NaN, 2.0D);
		final Point2D b = new Point2D(1.0D, Double.NaN);
		final Point2D c = new Point2D(1.0D, 2.0D);
		
		assertTrue(a.hasNaNs());
		assertTrue(b.hasNaNs());
		
		assertFalse(c.hasNaNs());
	}
	
	@Test
	public void testHashCode() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(1.0D, 2.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIsFinite() {
		final Point2D a = new Point2D(Double.NaN, Double.NaN);
		final Point2D b = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		final Point2D c = new Point2D(Double.NaN, Double.NEGATIVE_INFINITY);
		final Point2D d = new Point2D(1.0D, 2.0D);
		
		assertFalse(a.isFinite());
		assertFalse(b.isFinite());
		assertFalse(c.isFinite());
		
		assertTrue(d.isFinite());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Point2D(0.0D, 0.0D).isZero());
		
		assertFalse(new Point2D(0.0D, 1.0D).isZero());
		assertFalse(new Point2D(1.0D, 0.0D).isZero());
		assertFalse(new Point2D(1.0D, 1.0D).isZero());
	}
	
	@Test
	public void testLerp() {
		final Point2D a = new Point2D(1.0D, 1.0D);
		final Point2D b = new Point2D(5.0D, 5.0D);
		final Point2D c = Point2D.lerp(a, b, 0.25D);
		
		assertEquals(2.0D, c.x);
		assertEquals(2.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.lerp(a, null, 0.25D));
		assertThrows(NullPointerException.class, () -> Point2D.lerp(null, b, 0.25D));
	}
	
	@Test
	public void testMax() {
		assertEquals(new Point2D(+Double.MAX_VALUE, +Double.MAX_VALUE), Point2D.max());
	}
	
	@Test
	public void testMaxPoint2DPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(3.0D, 4.0D);
		final Point2D c = Point2D.max(a, b);
		
		assertEquals(3.0D, c.x);
		assertEquals(4.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.max(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.max(null, b));
	}
	
	@Test
	public void testMaxPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(3.0D, 4.0D);
		final Point2D c = new Point2D(5.0D, 6.0D);
		final Point2D d = Point2D.max(a, b, c);
		
		assertEquals(5.0D, d.x);
		assertEquals(6.0D, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.max(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2D.max(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2D.max(null, b, c));
	}
	
	@Test
	public void testMaxPoint2DPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(3.0D, 4.0D);
		final Point2D c = new Point2D(5.0D, 6.0D);
		final Point2D d = new Point2D(7.0D, 8.0D);
		final Point2D e = Point2D.max(a, b, c, d);
		
		assertEquals(7.0D, e.x);
		assertEquals(8.0D, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.max(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2D.max(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2D.max(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2D.max(null, b, c, d));
	}
	
	@Test
	public void testMidpoint() {
		final Point2D a = new Point2D(2.0D, 4.0D);
		final Point2D b = new Point2D(4.0D, 8.0D);
		final Point2D c = Point2D.midpoint(a, b);
		
		assertEquals(3.0D, c.x);
		assertEquals(6.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.midpoint(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.midpoint(null, b));
	}
	
	@Test
	public void testMin() {
		assertEquals(new Point2D(-Double.MAX_VALUE, -Double.MAX_VALUE), Point2D.min());
	}
	
	@Test
	public void testMinPoint2DPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(3.0D, 4.0D);
		final Point2D c = Point2D.min(a, b);
		
		assertEquals(1.0D, c.x);
		assertEquals(2.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.min(a, null));
		assertThrows(NullPointerException.class, () -> Point2D.min(null, b));
	}
	
	@Test
	public void testMinPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(3.0D, 4.0D);
		final Point2D c = new Point2D(5.0D, 6.0D);
		final Point2D d = Point2D.min(a, b, c);
		
		assertEquals(1.0D, d.x);
		assertEquals(2.0D, d.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.min(a, b, null));
		assertThrows(NullPointerException.class, () -> Point2D.min(a, null, c));
		assertThrows(NullPointerException.class, () -> Point2D.min(null, b, c));
	}
	
	@Test
	public void testMinPoint2DPoint2DPoint2DPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = new Point2D(3.0D, 4.0D);
		final Point2D c = new Point2D(5.0D, 6.0D);
		final Point2D d = new Point2D(7.0D, 8.0D);
		final Point2D e = Point2D.min(a, b, c, d);
		
		assertEquals(1.0D, e.x);
		assertEquals(2.0D, e.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.min(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2D.min(a, b, null, d));
		assertThrows(NullPointerException.class, () -> Point2D.min(a, null, c, d));
		assertThrows(NullPointerException.class, () -> Point2D.min(null, b, c, d));
	}
	
	@Test
	public void testProject() {
		final Point2D a = Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Point2D b = Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Point2D c = Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(0.0D, 0.0D, 1.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		
		assertEquals(1.0D, a.x);
		assertEquals(0.0D, a.y);
		
		assertEquals(0.0D, b.x);
		assertEquals(1.0D, b.y);
		
		assertEquals(0.0D, c.x);
		assertEquals(0.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), new Point3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 1.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> Point2D.project(new Point3D(0.0D, 0.0D, 0.0D), null, new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> Point2D.project(null, new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testRead() throws IOException {
		final Point2D a = new Point2D(1.0D, 0.5D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeDouble(a.x);
		dataOutput.writeDouble(a.y);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point2D b = Point2D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Point2D.read(null));
		assertThrows(UncheckedIOException.class, () -> Point2D.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testRotateCounterclockwise() {
		final Point2D a = new Point2D(1.0D, 1.0D);
		final Point2D b = Point2D.rotateCounterclockwise(a, 360.0D, false);
		final Point2D c = Point2D.rotateCounterclockwise(a, java.lang.Math.toRadians(360.0D), true);
		
		assertEquals(1.0000000000000002D, b.x);
		assertEquals(0.9999999999999998D, b.y);
		
		assertEquals(1.0000000000000002D, c.x);
		assertEquals(0.9999999999999998D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.rotateCounterclockwise(null, 360.0D, false));
	}
	
	@Test
	public void testSampleDiskUniformDistribution() {
		for(int i = 0; i < 1000; i++) {
			final Point2D p = Point2D.sampleDiskUniformDistribution();
			
			assertTrue(p.x >= -1.0D && p.x <= 1.0D);
			assertTrue(p.y >= -1.0D && p.y <= 1.0D);
		}
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMapping() {
		final Point2D a = Point2D.sampleDiskUniformDistributionByConcentricMapping();
		
		assertTrue(a.x >= -1.0D && a.x <= 1.0D);
		assertTrue(a.y >= -1.0D && a.y <= 1.0D);
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMappingPoint2D() {
		final Point2D a = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.0D, 0.0D));
		final Point2D b = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(1.0D, 0.5D));
		final Point2D c = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.5D, 1.0D));
		
		assertEquals(0.0D, a.x);
		assertEquals(0.0D, a.y);
		
		assertEquals(1.0D, b.x);
		assertEquals(0.0D, b.y);
		
		assertEquals(0.00000000000000006123233995736766D, c.x);
		assertEquals(1.00000000000000000000000000000000D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleDiskUniformDistributionByConcentricMapping(null));
	}
	
	@Test
	public void testSampleDiskUniformDistributionByConcentricMappingPoint2DDouble() {
		final Point2D a = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.0D, 0.0D), 1.0D);
		final Point2D b = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(1.0D, 0.5D), 1.0D);
		final Point2D c = Point2D.sampleDiskUniformDistributionByConcentricMapping(new Point2D(0.5D, 1.0D), 1.0D);
		
		assertEquals(0.0D, a.x);
		assertEquals(0.0D, a.y);
		
		assertEquals(1.0D, b.x);
		assertEquals(0.0D, b.y);
		
		assertEquals(0.00000000000000006123233995736766D, c.x);
		assertEquals(1.00000000000000000000000000000000D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleDiskUniformDistributionByConcentricMapping(null, 1.0D));
	}
	
	@Test
	public void testSampleDiskUniformDistributionPoint2D() {
		final Point2D a = Point2D.sampleDiskUniformDistribution(new Point2D(0.0D, 0.0D));
		final Point2D b = Point2D.sampleDiskUniformDistribution(new Point2D(0.0D, 1.0D));
		final Point2D c = Point2D.sampleDiskUniformDistribution(new Point2D(1.0D, 0.0D));
		final Point2D d = Point2D.sampleDiskUniformDistribution(new Point2D(1.0D, 1.0D));
		
		assertEquals(new Point2D(Math.sqrt(0.0D) * Math.cos(Math.PI * 2.0D * 0.0D), Math.sqrt(0.0D) * Math.sin(Math.PI * 2.0D * 0.0D)), a);
		assertEquals(new Point2D(Math.sqrt(0.0D) * Math.cos(Math.PI * 2.0D * 1.0D), Math.sqrt(0.0D) * Math.sin(Math.PI * 2.0D * 1.0D)), b);
		assertEquals(new Point2D(Math.sqrt(1.0D) * Math.cos(Math.PI * 2.0D * 0.0D), Math.sqrt(1.0D) * Math.sin(Math.PI * 2.0D * 0.0D)), c);
		assertEquals(new Point2D(Math.sqrt(1.0D) * Math.cos(Math.PI * 2.0D * 1.0D), Math.sqrt(1.0D) * Math.sin(Math.PI * 2.0D * 1.0D)), d);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleDiskUniformDistribution(null));
	}
	
	@Test
	public void testSampleExactInverseTentFilter() {
		final Point2D p = Point2D.sampleExactInverseTentFilter();
		
		assertTrue(p.x >= -1.0D && p.x <= 1.0D);
		assertTrue(p.y >= -1.0D && p.y <= 1.0D);
	}
	
	@Test
	public void testSampleExactInverseTentFilterPoint2D() {
		final Point2D a = Point2D.sampleExactInverseTentFilter(new Point2D(0.0D, 0.0D));
		final Point2D b = Point2D.sampleExactInverseTentFilter(new Point2D(0.5D, 0.5D));
		final Point2D c = Point2D.sampleExactInverseTentFilter(new Point2D(1.0D, 1.0D));
		
		assertEquals(-1.0D, a.x);
		assertEquals(-1.0D, a.y);
		
		assertEquals(0.0D, b.x);
		assertEquals(0.0D, b.y);
		
		assertEquals(1.0D, c.x);
		assertEquals(1.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.sampleExactInverseTentFilter(null));
	}
	
	@Test
	public void testSampleRandom() {
		final Point2D p = Point2D.sampleRandom();
		
		assertTrue(p.x >= 0.0D && p.x < 1.0D);
		assertTrue(p.y >= 0.0D && p.y < 1.0D);
	}
	
	@Test
	public void testScale() {
		final Point2D p = Point2D.scale(new Point2D(2.0D, 3.0D), new Vector2D(2.0D, 3.0D));
		
		assertEquals(4.0D, p.x);
		assertEquals(9.0D, p.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.scale(new Point2D(2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> Point2D.scale(null, new Vector2D(2.0D, 3.0D)));
	}
	
	@Test
	public void testSubtractPoint2DDouble() {
		final Point2D a = new Point2D(2.0D, 4.0D);
		final Point2D b = Point2D.subtract(a, 2.0D);
		
		assertEquals(0.0D, b.x);
		assertEquals(2.0D, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.subtract(null, 2.0D));
	}
	
	@Test
	public void testSubtractPoint2DVector2D() {
		final Point2D a = new Point2D(2.0D, 4.0D);
		final Point2D b = Point2D.subtract(a, new Vector2D(1.0D, 2.0D));
		
		assertEquals(1.0D, b.x);
		assertEquals(2.0D, b.y);
		
		assertThrows(NullPointerException.class, () -> Point2D.subtract(null, new Vector2D(1.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Point2D.subtract(a, null));
	}
	
	@Test
	public void testToArray() {
		final Point2D point = new Point2D(1.0D, 2.0D);
		
		final double[] array = point.toArray();
		
		assertNotNull(array);
		
		assertEquals(2, array.length);
		
		assertEquals(1.0D, array[0]);
		assertEquals(2.0D, array[1]);
	}
	
	@Test
	public void testToString() {
		final Point2D p = new Point2D(1.0D, 2.0D);
		
		assertEquals("new Point2D(1.0D, 2.0D)", p.toString());
	}
	
	@Test
	public void testToStringPoint2DArray() {
		final Point2D a = new Point2D(1.0D, 1.0D);
		final Point2D b = new Point2D(2.0D, 2.0D);
		final Point2D c = new Point2D(3.0D, 3.0D);
		
		assertEquals("new Point2D[] {new Point2D(1.0D, 1.0D), new Point2D(2.0D, 2.0D), new Point2D(3.0D, 3.0D)}", Point2D.toString(a, b, c));
		
		assertThrows(NullPointerException.class, () -> Point2D.toString((Point2D[])(null)));
		assertThrows(NullPointerException.class, () -> Point2D.toString(a, b, c, null));
		assertThrows(NullPointerException.class, () -> Point2D.toString(new Point2D[] {a, b, c, null}));
	}
	
	@Test
	public void testWrite() {
		final Point2D a = new Point2D(1.0D, 0.5D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Point2D b = Point2D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}