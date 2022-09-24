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
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.mock.DataOutputMock;

@SuppressWarnings("static-method")
public final class Vector3DUnitTests {
	public Vector3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAbs() {
		assertEquals(new Vector3D(1.0D, 2.0D, 3.0D), Vector3D.abs(new Vector3D(+1.0D, +2.0D, +3.0D)));
		assertEquals(new Vector3D(1.0D, 2.0D, 3.0D), Vector3D.abs(new Vector3D(-1.0D, -2.0D, -3.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.abs(null));
	}
	
	@Test
	public void testAddVector3DVector3D() {
		final Vector3D v = Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), new Vector3D(2.0D, 3.0D, 4.0D));
		
		assertEquals(3.0D, v.x);
		assertEquals(5.0D, v.y);
		assertEquals(7.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.add(null, new Vector3D(2.0D, 3.0D, 4.0D)));
	}
	
	@Test
	public void testAddVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), new Vector3D(2.0D, 3.0D, 4.0D), new Vector3D(3.0D, 4.0D, 5.0D));
		
		assertEquals( 6.0D, v.x);
		assertEquals( 9.0D, v.y);
		assertEquals(12.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), new Vector3D(2.0D, 3.0D, 4.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.add(new Vector3D(1.0D, 2.0D, 3.0D), null, new Vector3D(3.0D, 4.0D, 5.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.add(null, new Vector3D(2.0D, 3.0D, 4.0D), new Vector3D(3.0D, 4.0D, 5.0D)));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		Vector3D.clearCache();
		
		assertEquals(0, Vector3D.getCacheSize());
		
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D c = Vector3D.getCached(a);
		final Vector3D d = Vector3D.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Vector3D.getCached(null));
		
		assertEquals(1, Vector3D.getCacheSize());
		
		Vector3D.clearCache();
		
		assertEquals(0, Vector3D.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConeUniformDistributionPDF() {
		assertEquals(1.0D / (Math.PI * 2.0D * (1.0D - 0.5D)), Vector3D.coneUniformDistributionPDF(0.5D));
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Vector3D(Double.NaN, Double.NaN, Double.NaN), Vector3D.NaN);
		assertEquals(new Vector3D(0.0D, 0.0D, 0.0D), Vector3D.ZERO);
	}
	
	@Test
	public void testConstructor() {
		final Vector3D vector = new Vector3D();
		
		assertEquals(0.0D, vector.x);
		assertEquals(0.0D, vector.y);
		assertEquals(0.0D, vector.z);
	}
	
	@Test
	public void testConstructorDouble() {
		final Vector3D vector = new Vector3D(1.0D);
		
		assertEquals(1.0D, vector.x);
		assertEquals(1.0D, vector.y);
		assertEquals(1.0D, vector.z);
	}
	
	@Test
	public void testConstructorDoubleDoubleDouble() {
		final Vector3D v = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(1.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(3.0D, v.z);
	}
	
	@Test
	public void testConstructorPoint3D() {
		final Vector3D v = new Vector3D(new Point3D(1.0D, 2.0D, 3.0D));
		
		assertEquals(1.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(3.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> new Vector3D(null));
	}
	
	@Test
	public void testCosPhi() {
		assertEquals(+1.0D, new Vector3D(+0.5D, +0.0D, +1.0D).cosPhi());
		assertEquals(-1.0D, new Vector3D(-2.0D, +0.0D, +0.0D).cosPhi());
		assertEquals(+0.5D, new Vector3D(+0.5D, +0.0D, +0.0D).cosPhi());
		assertEquals(+1.0D, new Vector3D(+2.0D, +0.0D, +0.0D).cosPhi());
		assertEquals(+1.0D, new Vector3D(+0.5D, +0.0D, -1.0D).cosPhi());
	}
	
	@Test
	public void testCosPhiSquared() {
		assertEquals(+1.00D, new Vector3D(+0.5D, +0.0D, +1.0D).cosPhiSquared());
		assertEquals(+1.00D, new Vector3D(-2.0D, +0.0D, +0.0D).cosPhiSquared());
		assertEquals(+0.25D, new Vector3D(+0.5D, +0.0D, +0.0D).cosPhiSquared());
		assertEquals(+1.00D, new Vector3D(+2.0D, +0.0D, +0.0D).cosPhiSquared());
		assertEquals(+1.00D, new Vector3D(+0.5D, +0.0D, -1.0D).cosPhiSquared());
	}
	
	@Test
	public void testCosTheta() {
		assertEquals(3.0D, new Vector3D(1.0D, 2.0D, 3.0D).cosTheta());
	}
	
	@Test
	public void testCosThetaAbs() {
		assertEquals(3.0D, new Vector3D(+1.0D, +2.0D, +3.0D).cosThetaAbs());
		assertEquals(3.0D, new Vector3D(-1.0D, -2.0D, -3.0D).cosThetaAbs());
	}
	
	@Test
	public void testCosThetaQuartic() {
		assertEquals(81.0D, new Vector3D(+1.0D, +2.0D, +3.0D).cosThetaQuartic());
		assertEquals(81.0D, new Vector3D(-1.0D, -2.0D, -3.0D).cosThetaQuartic());
	}
	
	@Test
	public void testCosThetaSquared() {
		assertEquals(9.0D, new Vector3D(+1.0D, +2.0D, +3.0D).cosThetaSquared());
		assertEquals(9.0D, new Vector3D(-1.0D, -2.0D, -3.0D).cosThetaSquared());
	}
	
	@Test
	public void testCrossProduct() {
		final Vector3D a = Vector3D.crossProduct(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		final Vector3D b = Vector3D.crossProduct(new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D));
		final Vector3D c = Vector3D.crossProduct(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), a);
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), b);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), c);
		
		assertThrows(NullPointerException.class, () -> Vector3D.crossProduct(new Vector3D(1.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.crossProduct(null, new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testDirectionNormalizedPoint3DPoint3D() {
		final Vector3D v = Vector3D.directionNormalized(new Point3D(10.0D, 0.0D, 0.0D), new Point3D(20.0D, 0.0D, 0.0D));
		
		assertEquals(1.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(0.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.directionNormalized(new Point3D(), null));
		assertThrows(NullPointerException.class, () -> Vector3D.directionNormalized(null, new Point3D()));
	}
	
	@Test
	public void testDirectionNormalizedVector3DVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.directionNormalized(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D));
		
		assertEquals(Vector3D.normalize(new Vector3D(2.0D, 2.0D, 2.0D)), v);
		
		assertThrows(NullPointerException.class, () -> Vector3D.directionNormalized(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.directionNormalized(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), null, new Vector3D(2.0D, 2.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.directionNormalized(new Vector3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.directionNormalized(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D)));
	}
	
	@Test
	public void testDirectionPoint3DPoint3D() {
		final Vector3D v = Vector3D.direction(new Point3D(10.0D, 20.0D, 30.0D), new Point3D(20.0D, 40.0D, 60.0D));
		
		assertEquals(10.0D, v.x);
		assertEquals(20.0D, v.y);
		assertEquals(30.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Point3D(), null));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(null, new Point3D()));
	}
	
	@Test
	public void testDirectionSphericalDoubleDoubleDouble() {
		final Vector3D v = Vector3D.directionSpherical(2.0D, 1.0D, Math.PI / 2.0D);
		
		assertEquals(0.00000000000000012246467991473532D, v.x);
		assertEquals(2.00000000000000000000000000000000D, v.y);
		assertEquals(1.00000000000000000000000000000000D, v.z);
	}
	
	@Test
	public void testDirectionSphericalDoubleDoubleDoubleVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.directionSpherical(2.0D, 1.0D, Math.PI / 2.0D, new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D));
		
		assertEquals(0.00000000000000012246467991473532D, v.x);
		assertEquals(2.00000000000000000000000000000000D, v.y);
		assertEquals(1.00000000000000000000000000000000D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.directionSpherical(2.0D, 1.0D, Math.PI / 2.0D, new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.directionSpherical(2.0D, 1.0D, Math.PI / 2.0D, new Vector3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 0.0D, 1.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.directionSpherical(2.0D, 1.0D, Math.PI / 2.0D, null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D)));
	}
	
	@Test
	public void testDirectionSphericalNormalizedDoubleDoubleDouble() {
		final Vector3D v = Vector3D.directionSphericalNormalized(2.0D, 1.0D, Math.PI / 2.0D);
		
		assertEquals(Vector3D.normalize(new Vector3D(0.00000000000000012246467991473532D, 2.0D, 1.0D)), v);
	}
	
	@Test
	public void testDirectionSphericalNormalizedDoubleDoubleDoubleVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.directionSphericalNormalized(2.0D, 1.0D, Math.PI / 2.0D, new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D));
		
		assertEquals(Vector3D.normalize(new Vector3D(0.00000000000000012246467991473532D, 2.0D, 1.0D)), v);
		
		assertThrows(NullPointerException.class, () -> Vector3D.directionSphericalNormalized(2.0D, 1.0D, Math.PI / 2.0D, new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.directionSphericalNormalized(2.0D, 1.0D, Math.PI / 2.0D, new Vector3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 0.0D, 1.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.directionSphericalNormalized(2.0D, 1.0D, Math.PI / 2.0D, null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D)));
	}
	
	@Test
	public void testDirectionVector3DVector3DVector3DVector3D() {
		final Vector3D v = Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D));
		
		assertEquals(2.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(2.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), null, new Vector3D(2.0D, 2.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(new Vector3D(1.0D, 0.0D, 0.0D), null, new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D)));
		assertThrows(NullPointerException.class, () -> Vector3D.direction(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(2.0D, 2.0D, 2.0D)));
	}
	
	@Test
	public void testDivide() {
		final Vector3D v = Vector3D.divide(new Vector3D(1.0D, 2.0D, 3.0D), 2.0D);
		
		assertEquals(0.5D, v.x);
		assertEquals(1.0D, v.y);
		assertEquals(1.5D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.divide(null, 2.0D));
	}
	
	@Test
	public void testDotProduct() {
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.x(-1.0D), Vector3D.x(+1.0D)));
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.x(+1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.x(-1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.x(+1.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(+0.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(+0.0D), Vector3D.x(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(+1.0D), Vector3D.x(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.x(-1.0D), Vector3D.x(+0.0D)));
		
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.y(-1.0D), Vector3D.y(+1.0D)));
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.y(+1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.y(-1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.y(+1.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(+0.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(+0.0D), Vector3D.y(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(+1.0D), Vector3D.y(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.y(-1.0D), Vector3D.y(+0.0D)));
		
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.z(-1.0D), Vector3D.z(+1.0D)));
		assertEquals(-1.0D, Vector3D.dotProduct(Vector3D.z(+1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.z(-1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProduct(Vector3D.z(+1.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(+0.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(+0.0D), Vector3D.z(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(+1.0D), Vector3D.z(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProduct(Vector3D.z(-1.0D), Vector3D.z(+0.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.dotProduct(Vector3D.x(1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.dotProduct(null, Vector3D.x(1.0D)));
	}
	
	@Test
	public void testDotProductAbs() {
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(-1.0D), Vector3D.x(+1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(+1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(-1.0D), Vector3D.x(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.x(+1.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(+0.0D), Vector3D.x(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(+0.0D), Vector3D.x(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(+1.0D), Vector3D.x(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.x(-1.0D), Vector3D.x(+0.0D)));
		
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(-1.0D), Vector3D.y(+1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(+1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(-1.0D), Vector3D.y(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.y(+1.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(+0.0D), Vector3D.y(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(+0.0D), Vector3D.y(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(+1.0D), Vector3D.y(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.y(-1.0D), Vector3D.y(+0.0D)));
		
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(-1.0D), Vector3D.z(+1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(+1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(-1.0D), Vector3D.z(-1.0D)));
		assertEquals(+1.0D, Vector3D.dotProductAbs(Vector3D.z(+1.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(+0.0D), Vector3D.z(+1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(+0.0D), Vector3D.z(-1.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(+1.0D), Vector3D.z(+0.0D)));
		assertEquals(+0.0D, Vector3D.dotProductAbs(Vector3D.z(-1.0D), Vector3D.z(+0.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.dotProductAbs(Vector3D.x(1.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.dotProductAbs(null, Vector3D.x(1.0D)));
	}
	
	@Test
	public void testEqualsObject() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D c = new Vector3D(1.0D, 2.0D, 4.0D);
		final Vector3D d = new Vector3D(1.0D, 4.0D, 3.0D);
		final Vector3D e = new Vector3D(4.0D, 2.0D, 3.0D);
		final Vector3D f = null;
		
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
	public void testEqualsVector3D() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D c = new Vector3D(1.0D, 2.0D, 4.0D);
		final Vector3D d = new Vector3D(1.0D, 4.0D, 3.0D);
		final Vector3D e = new Vector3D(4.0D, 2.0D, 3.0D);
		final Vector3D f = null;
		
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
	public void testHadamardProduct() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(2.0D, 3.0D, 4.0D);
		final Vector3D c = Vector3D.hadamardProduct(a, b);
		
		assertEquals( 2.0D, c.x);
		assertEquals( 6.0D, c.y);
		assertEquals(12.0D, c.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.hadamardProduct(a, null));
		assertThrows(NullPointerException.class, () -> Vector3D.hadamardProduct(null, b));
	}
	
	@Test
	public void testHasInfinites() {
		final Vector3D a = new Vector3D(Double.POSITIVE_INFINITY, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, Double.POSITIVE_INFINITY, 3.0D);
		final Vector3D c = new Vector3D(1.0D, 2.0D, Double.POSITIVE_INFINITY);
		final Vector3D d = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertTrue(a.hasInfinites());
		assertTrue(b.hasInfinites());
		assertTrue(c.hasInfinites());
		
		assertFalse(d.hasInfinites());
	}
	
	@Test
	public void testHasNaNs() {
		final Vector3D a = new Vector3D(Double.NaN, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, Double.NaN, 3.0D);
		final Vector3D c = new Vector3D(1.0D, 2.0D, Double.NaN);
		final Vector3D d = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertTrue(a.hasNaNs());
		assertTrue(b.hasNaNs());
		assertTrue(c.hasNaNs());
		
		assertFalse(d.hasNaNs());
	}
	
	@Test
	public void testHashCode() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		final Vector3D b = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testHemisphereCosineDistributionPDF() {
		assertEquals(1.0D * (1.0D / Math.PI), Vector3D.hemisphereCosineDistributionPDF(1.0D));
	}
	
	@Test
	public void testHemisphereUniformDistributionPDF() {
		assertEquals(1.0D / (Math.PI * 2.0D), Vector3D.hemisphereUniformDistributionPDF());
	}
	
	@Test
	public void testIsFinite() {
		final Vector3D a = new Vector3D(Double.NaN, Double.NaN, Double.NaN);
		final Vector3D b = new Vector3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		final Vector3D c = new Vector3D(Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		final Vector3D d = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertFalse(a.isFinite());
		assertFalse(b.isFinite());
		assertFalse(c.isFinite());
		
		assertTrue(d.isFinite());
	}
	
	@Test
	public void testIsUnitVector() {
		assertTrue(new Vector3D(+1.0D, +0.0D, +0.0D).isUnitVector());
		assertTrue(new Vector3D(+0.0D, +1.0D, +0.0D).isUnitVector());
		assertTrue(new Vector3D(+0.0D, +0.0D, +1.0D).isUnitVector());
		assertTrue(new Vector3D(-1.0D, -0.0D, -0.0D).isUnitVector());
		assertTrue(new Vector3D(-0.0D, -1.0D, -0.0D).isUnitVector());
		assertTrue(new Vector3D(-0.0D, -0.0D, -1.0D).isUnitVector());
		assertTrue(Vector3D.normalize(new Vector3D(1.0D, 1.0D, 1.0D)).isUnitVector());
		
		assertFalse(new Vector3D(+1.0D, +1.0D, +1.0D).isUnitVector());
		assertFalse(new Vector3D(+0.5D, +0.5D, +0.5D).isUnitVector());
		assertFalse(new Vector3D(-1.0D, -1.0D, -1.0D).isUnitVector());
		assertFalse(new Vector3D(-0.5D, -0.5D, -0.5D).isUnitVector());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Vector3D(0.0D, 0.0D, 0.0D).isZero());
		
		assertFalse(new Vector3D(0.0D, 0.0D, 1.0D).isZero());
		assertFalse(new Vector3D(0.0D, 1.0D, 0.0D).isZero());
		assertFalse(new Vector3D(1.0D, 0.0D, 0.0D).isZero());
		assertFalse(new Vector3D(1.0D, 1.0D, 1.0D).isZero());
	}
	
	@Test
	public void testLength() {
		assertEquals(4.0D, new Vector3D(0.0D, 0.0D, 4.0D).length());
		assertEquals(4.0D, new Vector3D(0.0D, 4.0D, 0.0D).length());
		assertEquals(4.0D, new Vector3D(4.0D, 0.0D, 0.0D).length());
	}
	
	@Test
	public void testLengthSquared() {
		assertEquals(14.0D, new Vector3D(1.0D, 2.0D, 3.0D).lengthSquared());
	}
	
	@Test
	public void testLerp() {
		final Vector3D a = new Vector3D(1.0D, 1.0D, 1.0D);
		final Vector3D b = new Vector3D(5.0D, 5.0D, 5.0D);
		final Vector3D c = Vector3D.lerp(a, b, 0.25D);
		
		assertEquals(2.0D, c.x);
		assertEquals(2.0D, c.y);
		assertEquals(2.0D, c.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.lerp(a, null, 0.25D));
		assertThrows(NullPointerException.class, () -> Vector3D.lerp(null, b, 0.25D));
	}
	
	@Test
	public void testMultiply() {
		final Vector3D v = Vector3D.multiply(new Vector3D(1.0D, 2.0D, 3.0D), 2.0D);
		
		assertEquals(2.0D, v.x);
		assertEquals(4.0D, v.y);
		assertEquals(6.0D, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.multiply(null, 2.0D));
	}
	
	@Test
	public void testNegate() {
		final Vector3D a = Vector3D.negate(new Vector3D(+1.0D, +1.0D, +1.0D));
		final Vector3D b = Vector3D.negate(new Vector3D(-1.0D, -1.0D, -1.0D));
		
		assertEquals(-1.0D, a.x);
		assertEquals(-1.0D, a.y);
		assertEquals(-1.0D, a.z);
		
		assertEquals(+1.0D, b.x);
		assertEquals(+1.0D, b.y);
		assertEquals(+1.0D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.negate(null));
	}
	
	@Test
	public void testNegateX() {
		final Vector3D a = Vector3D.negateX(new Vector3D(+1.0D, +1.0D, +1.0D));
		final Vector3D b = Vector3D.negateX(new Vector3D(-1.0D, -1.0D, -1.0D));
		
		assertEquals(-1.0D, a.x);
		assertEquals(+1.0D, a.y);
		assertEquals(+1.0D, a.z);
		
		assertEquals(+1.0D, b.x);
		assertEquals(-1.0D, b.y);
		assertEquals(-1.0D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.negateX(null));
	}
	
	@Test
	public void testNegateY() {
		final Vector3D a = Vector3D.negateY(new Vector3D(+1.0D, +1.0D, +1.0D));
		final Vector3D b = Vector3D.negateY(new Vector3D(-1.0D, -1.0D, -1.0D));
		
		assertEquals(+1.0D, a.x);
		assertEquals(-1.0D, a.y);
		assertEquals(+1.0D, a.z);
		
		assertEquals(-1.0D, b.x);
		assertEquals(+1.0D, b.y);
		assertEquals(-1.0D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.negateY(null));
	}
	
	@Test
	public void testNegateZ() {
		final Vector3D a = Vector3D.negateZ(new Vector3D(+1.0D, +1.0D, +1.0D));
		final Vector3D b = Vector3D.negateZ(new Vector3D(-1.0D, -1.0D, -1.0D));
		
		assertEquals(+1.0D, a.x);
		assertEquals(+1.0D, a.y);
		assertEquals(-1.0D, a.z);
		
		assertEquals(-1.0D, b.x);
		assertEquals(-1.0D, b.y);
		assertEquals(+1.0D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.negateZ(null));
	}
	
	@Test
	public void testNormalNormalizedPoint3DPoint3DPoint3D() {
		final Point3D a = new Point3D(0.0D, 0.0D, 0.0D);
		final Point3D b = new Point3D(1.0D, 0.0D, 0.0D);
		final Point3D c = new Point3D(0.0D, 1.0D, 0.0D);
		
		final Vector3D vector = Vector3D.normalNormalized(a, b, c);
		
		assertEquals(0.0D, vector.x);
		assertEquals(0.0D, vector.y);
		assertEquals(1.0D, vector.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.normalNormalized(a, b, null));
		assertThrows(NullPointerException.class, () -> Vector3D.normalNormalized(a, null, c));
		assertThrows(NullPointerException.class, () -> Vector3D.normalNormalized(null, b, c));
	}
	
	@Test
	public void testNormalPoint3DPoint3DPoint3D() {
		final Point3D a = new Point3D(0.0D, 0.0D, 0.0D);
		final Point3D b = new Point3D(1.0D, 0.0D, 0.0D);
		final Point3D c = new Point3D(0.0D, 1.0D, 0.0D);
		
		final Vector3D vector = Vector3D.normal(a, b, c);
		
		assertEquals(0.0D, vector.x);
		assertEquals(0.0D, vector.y);
		assertEquals(1.0D, vector.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.normal(a, b, null));
		assertThrows(NullPointerException.class, () -> Vector3D.normal(a, null, c));
		assertThrows(NullPointerException.class, () -> Vector3D.normal(null, b, c));
	}
	
	@Test
	public void testNormalize() {
		final Vector3D a = new Vector3D(+1.0D, +0.0D, +0.0D);
		final Vector3D b = Vector3D.normalize(a);
		final Vector3D c = new Vector3D(+0.0D, +1.0D, +0.0D);
		final Vector3D d = Vector3D.normalize(c);
		final Vector3D e = new Vector3D(+0.0D, +0.0D, +1.0D);
		final Vector3D f = Vector3D.normalize(e);
		final Vector3D g = new Vector3D(+1.0D, +1.0D, +1.0D);
		final Vector3D h = Vector3D.normalize(g);
		final Vector3D i = Vector3D.normalize(h);
		final Vector3D j = new Vector3D(-1.0D, -1.0D, -1.0D);
		final Vector3D k = Vector3D.normalize(j);
		final Vector3D l = Vector3D.normalize(k);
		
		assertEquals(a, b);
		assertEquals(c, d);
		assertEquals(e, f);
		assertEquals(h, i);
		assertEquals(k, l);
		
		assertTrue(a == b);
		assertTrue(c == d);
		assertTrue(e == f);
		assertTrue(h == i);
		assertTrue(k == l);
		
		assertThrows(NullPointerException.class, () -> Vector3D.normalize(null));
	}
	
	@Test
	public void testOrientNormal() {
		final Vector3D a = Vector3D.orientNormal(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, +1.0D));
		final Vector3D b = Vector3D.orientNormal(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, -1.0D));
		final Vector3D c = Vector3D.orientNormal(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, -1.0D));
		final Vector3D d = Vector3D.orientNormal(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, +1.0D));
		
		assertEquals(new Vector3D(+0.0D, +0.0D, +1.0D), a);
		assertEquals(new Vector3D(+0.0D, +0.0D, -1.0D), b);
		assertEquals(new Vector3D(-0.0D, -0.0D, +1.0D), c);
		assertEquals(new Vector3D(-0.0D, -0.0D, -1.0D), d);
		
		assertThrows(NullPointerException.class, () -> Vector3D.orientNormal(new Vector3D(0.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.orientNormal(null, new Vector3D(0.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testOrientNormalNegated() {
		final Vector3D a = Vector3D.orientNormalNegated(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, +1.0D));
		final Vector3D b = Vector3D.orientNormalNegated(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, -1.0D));
		final Vector3D c = Vector3D.orientNormalNegated(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, -1.0D));
		final Vector3D d = Vector3D.orientNormalNegated(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, +1.0D));
		
		assertEquals(new Vector3D(-0.0D, -0.0D, -1.0D), a);
		assertEquals(new Vector3D(-0.0D, -0.0D, +1.0D), b);
		assertEquals(new Vector3D(+0.0D, +0.0D, -1.0D), c);
		assertEquals(new Vector3D(+0.0D, +0.0D, +1.0D), d);
		
		assertThrows(NullPointerException.class, () -> Vector3D.orientNormalNegated(new Vector3D(0.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.orientNormalNegated(null, new Vector3D(0.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testOrientNormalSameHemisphereZ() {
		final Vector3D a = Vector3D.orientNormalSameHemisphereZ(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, +1.0D));
		final Vector3D b = Vector3D.orientNormalSameHemisphereZ(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, -1.0D));
		final Vector3D c = Vector3D.orientNormalSameHemisphereZ(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, -1.0D));
		final Vector3D d = Vector3D.orientNormalSameHemisphereZ(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, +1.0D));
		
		assertEquals(new Vector3D(+0.0D, +0.0D, +1.0D), a);
		assertEquals(new Vector3D(+0.0D, +0.0D, -1.0D), b);
		assertEquals(new Vector3D(-0.0D, -0.0D, +1.0D), c);
		assertEquals(new Vector3D(-0.0D, -0.0D, -1.0D), d);
		
		assertThrows(NullPointerException.class, () -> Vector3D.orientNormalSameHemisphereZ(new Vector3D(0.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.orientNormalSameHemisphereZ(null, new Vector3D(0.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testOrthogonalVector3D() {
		final Vector3D a = new Vector3D(1.0D, 0.0D, 0.0D);
		final Vector3D b = Vector3D.orthogonal(a);
		final Vector3D c = new Vector3D(0.0D, 1.0D, 0.0D);
		final Vector3D d = Vector3D.orthogonal(c);
		final Vector3D e = new Vector3D(0.0D, 0.0D, 1.0D);
		final Vector3D f = Vector3D.orthogonal(e);
		final Vector3D g = Vector3D.normalize(new Vector3D(1.0D, 2.0D, 3.0D));
		final Vector3D h = Vector3D.orthogonal(g);
		final Vector3D i = Vector3D.normalize(new Vector3D(0.0D, g.z, -g.y));
		
		assertEquals(+0.0D, b.x);
		assertEquals(-1.0D, b.y);
		assertEquals(+0.0D, b.z);
		
		assertEquals(+1.0D, d.x);
		assertEquals(-0.0D, d.y);
		assertEquals(+0.0D, d.z);
		
		assertEquals(+1.0D, f.x);
		assertEquals(+0.0D, f.y);
		assertEquals(-0.0D, f.z);
		
		assertEquals(i.x, h.x);
		assertEquals(i.y, h.y);
		assertEquals(i.z, h.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.orthogonal(null));
	}
	
	@Test
	public void testOrthogonalVector3DVector3D() {
		final Vector3D a = new Vector3D(1.0D, 0.0D, 0.0D);
		final Vector3D b = new Vector3D(0.0D, 1.0D, 0.0D);
		final Vector3D c = new Vector3D(1.0D, 1.0D, 1.0D);
		
		assertTrue(Vector3D.orthogonal(a, b));
		
		assertFalse(Vector3D.orthogonal(a, c));
		
		assertThrows(NullPointerException.class, () -> Vector3D.orthogonal(a, null));
		assertThrows(NullPointerException.class, () -> Vector3D.orthogonal(null, b));
	}
	
	@Test
	public void testRead() throws IOException {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeDouble(a.x);
		dataOutput.writeDouble(a.y);
		dataOutput.writeDouble(a.z);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Vector3D b = Vector3D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Vector3D.read(null));
		assertThrows(UncheckedIOException.class, () -> Vector3D.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testReciprocal() {
		final Vector3D a = Vector3D.reciprocal(new Vector3D(Double.NaN, Double.NaN, Double.NaN));
		final Vector3D b = Vector3D.reciprocal(new Vector3D(2.0D, 4.0D, 8.0D));
		
		assertEquals(Double.NaN, a.x);
		assertEquals(Double.NaN, a.y);
		assertEquals(Double.NaN, a.z);
		
		assertEquals(0.500D, b.x);
		assertEquals(0.250D, b.y);
		assertEquals(0.125D, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.reciprocal(null));
	}
	
	@Test
	public void testRefraction() {
		final Optional<Vector3D> a = Vector3D.refraction(new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(0.0D, 1.0D, 0.0D), 1.0D);
		final Optional<Vector3D> b = Vector3D.refraction(new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		
		assertNotNull(a);
		assertNotNull(b);
		
		assertTrue(a.isPresent());
		
		assertFalse(b.isPresent());
		
		assertEquals(+0.0D, a.get().x);
		assertEquals(-1.0D, a.get().y);
		assertEquals(+0.0D, a.get().z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.refraction(new Vector3D(0.0D, 1.0D, 0.0D), null, 1.0D));
		assertThrows(NullPointerException.class, () -> Vector3D.refraction(null, new Vector3D(0.0D, 1.0D, 0.0D), 1.0D));
	}
	
	@Test
	public void testSameHemisphere() {
		final Vector3D a = new Vector3D(+1.0D, 0.0D, 0.0D);
		final Vector3D b = new Vector3D(-1.0D, 0.0D, 0.0D);
		
		assertTrue(Vector3D.sameHemisphere(a, a));
		
		assertFalse(Vector3D.sameHemisphere(a, b));
		
		assertThrows(NullPointerException.class, () -> Vector3D.sameHemisphere(a, null));
		assertThrows(NullPointerException.class, () -> Vector3D.sameHemisphere(null, b));
	}
	
	@Test
	public void testSameHemisphereZ() {
		assertTrue(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, +1.0D)));
		assertTrue(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, -1.0D)));
		
		assertFalse(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, +1.0D), new Vector3D(0.0D, 0.0D, -1.0D)));
		assertFalse(Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 0.0D, +1.0D)));
		
		assertThrows(NullPointerException.class, () -> Vector3D.sameHemisphereZ(new Vector3D(0.0D, 0.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> Vector3D.sameHemisphereZ(null, new Vector3D(0.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testSampleHemispherePowerCosineDistribution() {
		for(int i = 0; i < 10000; i++) {
			final Vector3D v = Vector3D.sampleHemispherePowerCosineDistribution();
			
			assertTrue(v.x >= -1.0D && v.x <= +1.0D);
			assertTrue(v.y >= -1.0D && v.y <= +1.0D);
			assertTrue(v.z >= +0.0D && v.z <= +1.0D);
		}
	}
	
	@Test
	public void testSampleHemispherePowerCosineDistributionPoint2D() {
		for(int i = 0; i < 10000; i++) {
			final Vector3D v = Vector3D.sampleHemispherePowerCosineDistribution(Point2D.sampleRandom());
			
			assertTrue(v.x >= -1.0D && v.x <= +1.0D);
			assertTrue(v.y >= -1.0D && v.y <= +1.0D);
			assertTrue(v.z >= +0.0D && v.z <= +1.0D);
		}
		
		assertThrows(NullPointerException.class, () -> Vector3D.sampleHemispherePowerCosineDistribution(null));
	}
	
	@Test
	public void testSampleHemispherePowerCosineDistributionPoint2DDouble() {
		final Vector3D a = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.0D, 0.0D), 0.0D);
		final Vector3D b = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.0D, 0.5D), 0.0D);
		final Vector3D c = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.0D, 1.0D), 0.0D);
		final Vector3D d = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.5D, 0.0D), 0.0D);
		final Vector3D e = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.5D, 0.5D), 0.0D);
		final Vector3D f = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.5D, 1.0D), 0.0D);
		final Vector3D g = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(1.0D, 0.0D), 0.0D);
		final Vector3D h = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(1.0D, 0.5D), 0.0D);
		final Vector3D i = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(1.0D, 1.0D), 0.0D);
		
		final Vector3D j = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.0D, 0.0D), 1.0D);
		final Vector3D k = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.0D, 0.5D), 1.0D);
		final Vector3D l = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.0D, 1.0D), 1.0D);
		final Vector3D m = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.5D, 0.0D), 1.0D);
		final Vector3D n = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.5D, 0.5D), 1.0D);
		final Vector3D o = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(0.5D, 1.0D), 1.0D);
		final Vector3D p = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(1.0D, 0.0D), 1.0D);
		final Vector3D q = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(1.0D, 0.5D), 1.0D);
		final Vector3D r = Vector3D.sampleHemispherePowerCosineDistribution(new Point2D(1.0D, 1.0D), 1.0D);
		
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(1.0D, 1.0D) * Math.pow(1.0D, 1.0D)) * Math.cos(Math.PI * 0.0D), Math.sqrt(1.0D - Math.pow(1.0D, 1.0D) * Math.pow(1.0D, 1.0D)) * Math.sin(Math.PI * 0.0D), Math.pow(1.0D, 1.0D)), a);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(1.0D, 1.0D) * Math.pow(1.0D, 1.0D)) * Math.cos(Math.PI * 1.0D), Math.sqrt(1.0D - Math.pow(1.0D, 1.0D) * Math.pow(1.0D, 1.0D)) * Math.sin(Math.PI * 1.0D), Math.pow(1.0D, 1.0D)), b);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(1.0D, 1.0D) * Math.pow(1.0D, 1.0D)) * Math.cos(Math.PI * 2.0D), Math.sqrt(1.0D - Math.pow(1.0D, 1.0D) * Math.pow(1.0D, 1.0D)) * Math.sin(Math.PI * 2.0D), Math.pow(1.0D, 1.0D)), c);
		
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.5D, 1.0D) * Math.pow(0.5D, 1.0D)) * Math.cos(Math.PI * 0.0D), Math.sqrt(1.0D - Math.pow(0.5D, 1.0D) * Math.pow(0.5D, 1.0D)) * Math.sin(Math.PI * 0.0D), Math.pow(0.5D, 1.0D)), d);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.5D, 1.0D) * Math.pow(0.5D, 1.0D)) * Math.cos(Math.PI * 1.0D), Math.sqrt(1.0D - Math.pow(0.5D, 1.0D) * Math.pow(0.5D, 1.0D)) * Math.sin(Math.PI * 1.0D), Math.pow(0.5D, 1.0D)), e);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.5D, 1.0D) * Math.pow(0.5D, 1.0D)) * Math.cos(Math.PI * 2.0D), Math.sqrt(1.0D - Math.pow(0.5D, 1.0D) * Math.pow(0.5D, 1.0D)) * Math.sin(Math.PI * 2.0D), Math.pow(0.5D, 1.0D)), f);
		
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.0D, 1.0D) * Math.pow(0.0D, 1.0D)) * Math.cos(Math.PI * 0.0D), Math.sqrt(1.0D - Math.pow(0.0D, 1.0D) * Math.pow(0.0D, 1.0D)) * Math.sin(Math.PI * 0.0D), Math.pow(0.0D, 1.0D)), g);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.0D, 1.0D) * Math.pow(0.0D, 1.0D)) * Math.cos(Math.PI * 1.0D), Math.sqrt(1.0D - Math.pow(0.0D, 1.0D) * Math.pow(0.0D, 1.0D)) * Math.sin(Math.PI * 1.0D), Math.pow(0.0D, 1.0D)), h);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.0D, 1.0D) * Math.pow(0.0D, 1.0D)) * Math.cos(Math.PI * 2.0D), Math.sqrt(1.0D - Math.pow(0.0D, 1.0D) * Math.pow(0.0D, 1.0D)) * Math.sin(Math.PI * 2.0D), Math.pow(0.0D, 1.0D)), i);
		
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(1.0D, 0.5D) * Math.pow(1.0D, 0.5D)) * Math.cos(Math.PI * 0.0D), Math.sqrt(1.0D - Math.pow(1.0D, 0.5D) * Math.pow(1.0D, 0.5D)) * Math.sin(Math.PI * 0.0D), Math.pow(1.0D, 0.5D)), j);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(1.0D, 0.5D) * Math.pow(1.0D, 0.5D)) * Math.cos(Math.PI * 1.0D), Math.sqrt(1.0D - Math.pow(1.0D, 0.5D) * Math.pow(1.0D, 0.5D)) * Math.sin(Math.PI * 1.0D), Math.pow(1.0D, 0.5D)), k);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(1.0D, 0.5D) * Math.pow(1.0D, 0.5D)) * Math.cos(Math.PI * 2.0D), Math.sqrt(1.0D - Math.pow(1.0D, 0.5D) * Math.pow(1.0D, 0.5D)) * Math.sin(Math.PI * 2.0D), Math.pow(1.0D, 0.5D)), l);
		
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.5D, 0.5D) * Math.pow(0.5D, 0.5D)) * Math.cos(Math.PI * 0.0D), Math.sqrt(1.0D - Math.pow(0.5D, 0.5D) * Math.pow(0.5D, 0.5D)) * Math.sin(Math.PI * 0.0D), Math.pow(0.5D, 0.5D)), m);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.5D, 0.5D) * Math.pow(0.5D, 0.5D)) * Math.cos(Math.PI * 1.0D), Math.sqrt(1.0D - Math.pow(0.5D, 0.5D) * Math.pow(0.5D, 0.5D)) * Math.sin(Math.PI * 1.0D), Math.pow(0.5D, 0.5D)), n);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.5D, 0.5D) * Math.pow(0.5D, 0.5D)) * Math.cos(Math.PI * 2.0D), Math.sqrt(1.0D - Math.pow(0.5D, 0.5D) * Math.pow(0.5D, 0.5D)) * Math.sin(Math.PI * 2.0D), Math.pow(0.5D, 0.5D)), o);
		
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.0D, 0.5D) * Math.pow(0.0D, 0.5D)) * Math.cos(Math.PI * 0.0D), Math.sqrt(1.0D - Math.pow(0.0D, 0.5D) * Math.pow(0.0D, 0.5D)) * Math.sin(Math.PI * 0.0D), Math.pow(0.0D, 0.5D)), p);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.0D, 0.5D) * Math.pow(0.0D, 0.5D)) * Math.cos(Math.PI * 1.0D), Math.sqrt(1.0D - Math.pow(0.0D, 0.5D) * Math.pow(0.0D, 0.5D)) * Math.sin(Math.PI * 1.0D), Math.pow(0.0D, 0.5D)), q);
		assertEquals(new Vector3D(Math.sqrt(1.0D - Math.pow(0.0D, 0.5D) * Math.pow(0.0D, 0.5D)) * Math.cos(Math.PI * 2.0D), Math.sqrt(1.0D - Math.pow(0.0D, 0.5D) * Math.pow(0.0D, 0.5D)) * Math.sin(Math.PI * 2.0D), Math.pow(0.0D, 0.5D)), r);
		
		assertThrows(NullPointerException.class, () -> Vector3D.sampleHemispherePowerCosineDistribution(null, 0.0D));
	}
	
	@Test
	public void testSampleHemisphereUniformDistribution() {
		for(int i = 0; i < 10000; i++) {
			final Vector3D v = Vector3D.sampleHemisphereUniformDistribution();
			
			assertTrue(v.x >= -1.0D && v.x <= +1.0D);
			assertTrue(v.y >= -1.0D && v.y <= +1.0D);
			assertTrue(v.z >= -1.0D && v.z <= +1.0D);
		}
	}
	
	@Test
	public void testSampleHemisphereUniformDistributionPoint2D() {
		final Vector3D a = Vector3D.sampleHemisphereUniformDistribution(new Point2D(0.0D, 0.0D));
		final Vector3D b = Vector3D.sampleHemisphereUniformDistribution(new Point2D(0.0D, 1.0D));
		final Vector3D c = Vector3D.sampleHemisphereUniformDistribution(new Point2D(1.0D, 0.0D));
		final Vector3D d = Vector3D.sampleHemisphereUniformDistribution(new Point2D(1.0D, 1.0D));
		
		assertEquals(new Vector3D(1.0D * Math.cos(Math.PI * 0.0D), 1.0D * Math.sin(Math.PI * 0.0D), 0.0D), a);
		assertEquals(new Vector3D(1.0D * Math.cos(Math.PI * 2.0D), 1.0D * Math.sin(Math.PI * 2.0D), 0.0D), b);
		
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 0.0D), 0.0D * Math.sin(Math.PI * 0.0D), 1.0D), c);
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 2.0D), 0.0D * Math.sin(Math.PI * 2.0D), 1.0D), d);
		
		assertThrows(NullPointerException.class, () -> Vector3D.sampleHemisphereUniformDistribution(null));
	}
	
	@Test
	public void testSampleSphereUniformDistribution() {
		for(int i = 0; i < 10000; i++) {
			final Vector3D v = Vector3D.sampleSphereUniformDistribution();
			
			assertTrue(v.x >= -1.0D && v.x <= +1.0D);
			assertTrue(v.y >= -1.0D && v.y <= +1.0D);
			assertTrue(v.z >= -1.0D && v.z <= +1.0D);
		}
	}
	
	@Test
	public void testSampleSphereUniformDistributionPoint2D() {
		final Vector3D a = Vector3D.sampleSphereUniformDistribution(new Point2D(0.0D, 0.0D));
		final Vector3D b = Vector3D.sampleSphereUniformDistribution(new Point2D(0.0D, 0.5D));
		final Vector3D c = Vector3D.sampleSphereUniformDistribution(new Point2D(0.0D, 1.0D));
		final Vector3D d = Vector3D.sampleSphereUniformDistribution(new Point2D(0.5D, 0.0D));
		final Vector3D e = Vector3D.sampleSphereUniformDistribution(new Point2D(0.5D, 0.5D));
		final Vector3D f = Vector3D.sampleSphereUniformDistribution(new Point2D(0.5D, 1.0D));
		final Vector3D g = Vector3D.sampleSphereUniformDistribution(new Point2D(1.0D, 0.0D));
		final Vector3D h = Vector3D.sampleSphereUniformDistribution(new Point2D(1.0D, 0.5D));
		final Vector3D i = Vector3D.sampleSphereUniformDistribution(new Point2D(1.0D, 1.0D));
		
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 0.0D), 0.0D * Math.sin(Math.PI * 0.0D), +1.0D), a);
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 1.0D), 0.0D * Math.sin(Math.PI * 1.0D), +1.0D), b);
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 2.0D), 0.0D * Math.sin(Math.PI * 2.0D), +1.0D), c);
		
		assertEquals(new Vector3D(1.0D * Math.cos(Math.PI * 0.0D), 1.0D * Math.sin(Math.PI * 0.0D), +0.0D), d);
		assertEquals(new Vector3D(1.0D * Math.cos(Math.PI * 1.0D), 1.0D * Math.sin(Math.PI * 1.0D), +0.0D), e);
		assertEquals(new Vector3D(1.0D * Math.cos(Math.PI * 2.0D), 1.0D * Math.sin(Math.PI * 2.0D), +0.0D), f);
		
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 0.0D), 0.0D * Math.sin(Math.PI * 0.0D), -1.0D), g);
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 1.0D), 0.0D * Math.sin(Math.PI * 1.0D), -1.0D), h);
		assertEquals(new Vector3D(0.0D * Math.cos(Math.PI * 2.0D), 0.0D * Math.sin(Math.PI * 2.0D), -1.0D), i);
		
		assertThrows(NullPointerException.class, () -> Vector3D.sampleSphereUniformDistribution(null));
	}
	
	@Test
	public void testSinPhi() {
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.5D, +1.0D).sinPhi());
		assertEquals(-1.0D, new Vector3D(+0.0D, -2.0D, +0.0D).sinPhi());
		assertEquals(+0.5D, new Vector3D(+0.0D, +0.5D, +0.0D).sinPhi());
		assertEquals(+1.0D, new Vector3D(+0.0D, +2.0D, +0.0D).sinPhi());
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.5D, -1.0D).sinPhi());
	}
	
	@Test
	public void testSinPhiSquared() {
		assertEquals(+0.00D, new Vector3D(+0.0D, +0.5D, +1.0D).sinPhiSquared());
		assertEquals(+1.00D, new Vector3D(+0.0D, -2.0D, +0.0D).sinPhiSquared());
		assertEquals(+0.25D, new Vector3D(+0.0D, +0.5D, +0.0D).sinPhiSquared());
		assertEquals(+1.00D, new Vector3D(+0.0D, +2.0D, +0.0D).sinPhiSquared());
		assertEquals(+0.00D, new Vector3D(+0.0D, +0.5D, -1.0D).sinPhiSquared());
	}
	
	@Test
	public void testSinTheta() {
		assertEquals(0.0D, new Vector3D(+0.0D, +0.0D, +1.0D).sinTheta());
		assertEquals(1.0D, new Vector3D(+0.0D, +0.0D, +0.0D).sinTheta());
		assertEquals(0.0D, new Vector3D(+0.0D, +0.0D, -1.0D).sinTheta());
	}
	
	@Test
	public void testSinThetaSquared() {
		assertEquals(0.0D, new Vector3D(+0.0D, +0.0D, +1.0D).sinThetaSquared());
		assertEquals(1.0D, new Vector3D(+0.0D, +0.0D, +0.0D).sinThetaSquared());
		assertEquals(0.0D, new Vector3D(+0.0D, +0.0D, -1.0D).sinThetaSquared());
	}
	
	@Test
	public void testSphereUniformDistributionPDF() {
		assertEquals(1.0D / (Math.PI * 4.0D), Vector3D.sphereUniformDistributionPDF());
	}
	
	@Test
	public void testSubtract() {
		final Vector3D a = new Vector3D(3.0D, 5.0D, 7.0D);
		final Vector3D b = new Vector3D(2.0D, 3.0D, 4.0D);
		final Vector3D c = Vector3D.subtract(a, b);
		
		assertEquals(1.0D, c.x);
		assertEquals(2.0D, c.y);
		assertEquals(3.0D, c.z);
		
		assertThrows(NullPointerException.class, () -> Vector3D.subtract(a, null));
		assertThrows(NullPointerException.class, () -> Vector3D.subtract(null, b));
	}
	
	@Test
	public void testTanTheta() {
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.0D, +1.0D).tanTheta());
		assertEquals(-0.0D, new Vector3D(+0.0D, +0.0D, -1.0D).tanTheta());
	}
	
	@Test
	public void testTanThetaAbs() {
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.0D, +1.0D).tanThetaAbs());
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.0D, -1.0D).tanThetaAbs());
	}
	
	@Test
	public void testTanThetaSquared() {
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.0D, +1.0D).tanThetaSquared());
		assertEquals(+0.0D, new Vector3D(+0.0D, +0.0D, -1.0D).tanThetaSquared());
	}
	
	@Test
	public void testToArray() {
		final Vector3D vector = new Vector3D(1.0D, 2.0D, 3.0D);
		
		final double[] array = vector.toArray();
		
		assertNotNull(array);
		
		assertEquals(3, array.length);
		
		assertEquals(1.0D, array[0]);
		assertEquals(2.0D, array[1]);
		assertEquals(3.0D, array[2]);
	}
	
	@Test
	public void testToString() {
		final Vector3D v = new Vector3D(1.0D, 2.0D, 3.0D);
		
		assertEquals("new Vector3D(1.0D, 2.0D, 3.0D)", v.toString());
	}
	
	@Test
	public void testTripleProduct() {
		assertEquals(+1.0D, Vector3D.tripleProduct(Vector3D.x(), Vector3D.y(), Vector3D.z()));
		assertEquals(+1.0D, Vector3D.tripleProduct(Vector3D.y(), Vector3D.z(), Vector3D.x()));
		assertEquals(+1.0D, Vector3D.tripleProduct(Vector3D.z(), Vector3D.x(), Vector3D.y()));
		
		assertEquals(-1.0D, Vector3D.tripleProduct(Vector3D.z(), Vector3D.y(), Vector3D.x()));
		assertEquals(-1.0D, Vector3D.tripleProduct(Vector3D.y(), Vector3D.x(), Vector3D.z()));
		assertEquals(-1.0D, Vector3D.tripleProduct(Vector3D.x(), Vector3D.z(), Vector3D.y()));
		
		assertThrows(NullPointerException.class, () -> Vector3D.tripleProduct(Vector3D.x(), Vector3D.y(), null));
		assertThrows(NullPointerException.class, () -> Vector3D.tripleProduct(Vector3D.x(), null, Vector3D.z()));
		assertThrows(NullPointerException.class, () -> Vector3D.tripleProduct(null, Vector3D.y(), Vector3D.z()));
	}
	
	@Test
	public void testWrite() {
		final Vector3D a = new Vector3D(1.0D, 2.0D, 3.0D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Vector3D b = Vector3D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
	
	@Test
	public void testX() {
		final Vector3D v = Vector3D.x();
		
		assertEquals(1.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testXDouble() {
		final Vector3D v = Vector3D.x(2.0D);
		
		assertEquals(2.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testY() {
		final Vector3D v = Vector3D.y();
		
		assertEquals(0.0D, v.x);
		assertEquals(1.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testYDouble() {
		final Vector3D v = Vector3D.y(2.0D);
		
		assertEquals(0.0D, v.x);
		assertEquals(2.0D, v.y);
		assertEquals(0.0D, v.z);
	}
	
	@Test
	public void testZ() {
		final Vector3D v = Vector3D.z();
		
		assertEquals(0.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(1.0D, v.z);
	}
	
	@Test
	public void testZDouble() {
		final Vector3D v = Vector3D.z(2.0D);
		
		assertEquals(0.0D, v.x);
		assertEquals(0.0D, v.y);
		assertEquals(2.0D, v.z);
	}
}