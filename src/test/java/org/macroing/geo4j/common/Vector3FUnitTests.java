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
public final class Vector3FUnitTests {
	public Vector3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAbs() {
		assertEquals(new Vector3F(1.0F, 2.0F, 3.0F), Vector3F.abs(new Vector3F(+1.0F, +2.0F, +3.0F)));
		assertEquals(new Vector3F(1.0F, 2.0F, 3.0F), Vector3F.abs(new Vector3F(-1.0F, -2.0F, -3.0F)));
		
		assertThrows(NullPointerException.class, () -> Vector3F.abs(null));
	}
	
	@Test
	public void testAddVector3FVector3F() {
		final Vector3F v = Vector3F.add(new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(2.0F, 3.0F, 4.0F));
		
		assertEquals(3.0F, v.x);
		assertEquals(5.0F, v.y);
		assertEquals(7.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.add(new Vector3F(1.0F, 2.0F, 3.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.add(null, new Vector3F(2.0F, 3.0F, 4.0F)));
	}
	
	@Test
	public void testAddVector3FVector3FVector3F() {
		final Vector3F v = Vector3F.add(new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(2.0F, 3.0F, 4.0F), new Vector3F(3.0F, 4.0F, 5.0F));
		
		assertEquals( 6.0F, v.x);
		assertEquals( 9.0F, v.y);
		assertEquals(12.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.add(new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(2.0F, 3.0F, 4.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.add(new Vector3F(1.0F, 2.0F, 3.0F), null, new Vector3F(3.0F, 4.0F, 5.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.add(null, new Vector3F(2.0F, 3.0F, 4.0F), new Vector3F(3.0F, 4.0F, 5.0F)));
	}
	
	@Test
	public void testClearCacheAndGetCacheSizeAndGetCached() {
		Vector3F.clearCache();
		
		assertEquals(0, Vector3F.getCacheSize());
		
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F c = Vector3F.getCached(a);
		final Vector3F d = Vector3F.getCached(b);
		
		assertThrows(NullPointerException.class, () -> Vector3F.getCached(null));
		
		assertEquals(1, Vector3F.getCacheSize());
		
		Vector3F.clearCache();
		
		assertEquals(0, Vector3F.getCacheSize());
		
		assertTrue(a != b);
		assertTrue(a == c);
		assertTrue(a == d);
		
		assertTrue(b != a);
		assertTrue(b != c);
		assertTrue(b != d);
	}
	
	@Test
	public void testConeUniformDistributionPDF() {
		assertEquals(1.0F / ((float)(Math.PI) * 2.0F * (1.0F - 0.5F)), Vector3F.coneUniformDistributionPDF(0.5F));
	}
	
	@Test
	public void testConstants() {
		assertEquals(new Vector3F(Float.NaN, Float.NaN, Float.NaN), Vector3F.NaN);
		assertEquals(new Vector3F(0.0F, 0.0F, 0.0F), Vector3F.ZERO);
	}
	
	@Test
	public void testConstructor() {
		final Vector3F vector = new Vector3F();
		
		assertEquals(0.0F, vector.x);
		assertEquals(0.0F, vector.y);
		assertEquals(0.0F, vector.z);
	}
	
	@Test
	public void testConstructorFloat() {
		final Vector3F vector = new Vector3F(1.0F);
		
		assertEquals(1.0F, vector.x);
		assertEquals(1.0F, vector.y);
		assertEquals(1.0F, vector.z);
	}
	
	@Test
	public void testConstructorFloatFloatFloat() {
		final Vector3F v = new Vector3F(1.0F, 2.0F, 3.0F);
		
		assertEquals(1.0F, v.x);
		assertEquals(2.0F, v.y);
		assertEquals(3.0F, v.z);
	}
	
	@Test
	public void testConstructorPoint3F() {
		final Vector3F v = new Vector3F(new Point3F(1.0F, 2.0F, 3.0F));
		
		assertEquals(1.0F, v.x);
		assertEquals(2.0F, v.y);
		assertEquals(3.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> new Vector3F(null));
	}
	
	@Test
	public void testCosPhi() {
		assertEquals(+1.0F, new Vector3F(+0.5F, +0.0F, +1.0F).cosPhi());
		assertEquals(-1.0F, new Vector3F(-2.0F, +0.0F, +0.0F).cosPhi());
		assertEquals(+0.5F, new Vector3F(+0.5F, +0.0F, +0.0F).cosPhi());
		assertEquals(+1.0F, new Vector3F(+2.0F, +0.0F, +0.0F).cosPhi());
		assertEquals(+1.0F, new Vector3F(+0.5F, +0.0F, -1.0F).cosPhi());
	}
	
	@Test
	public void testCosPhiSquared() {
		assertEquals(+1.00F, new Vector3F(+0.5F, +0.0F, +1.0F).cosPhiSquared());
		assertEquals(+1.00F, new Vector3F(-2.0F, +0.0F, +0.0F).cosPhiSquared());
		assertEquals(+0.25F, new Vector3F(+0.5F, +0.0F, +0.0F).cosPhiSquared());
		assertEquals(+1.00F, new Vector3F(+2.0F, +0.0F, +0.0F).cosPhiSquared());
		assertEquals(+1.00F, new Vector3F(+0.5F, +0.0F, -1.0F).cosPhiSquared());
	}
	
	@Test
	public void testCosTheta() {
		assertEquals(3.0F, new Vector3F(1.0F, 2.0F, 3.0F).cosTheta());
	}
	
	@Test
	public void testCosThetaAbs() {
		assertEquals(3.0F, new Vector3F(+1.0F, +2.0F, +3.0F).cosThetaAbs());
		assertEquals(3.0F, new Vector3F(-1.0F, -2.0F, -3.0F).cosThetaAbs());
	}
	
	@Test
	public void testCosThetaQuartic() {
		assertEquals(81.0F, new Vector3F(+1.0F, +2.0F, +3.0F).cosThetaQuartic());
		assertEquals(81.0F, new Vector3F(-1.0F, -2.0F, -3.0F).cosThetaQuartic());
	}
	
	@Test
	public void testCosThetaSquared() {
		assertEquals(9.0F, new Vector3F(+1.0F, +2.0F, +3.0F).cosThetaSquared());
		assertEquals(9.0F, new Vector3F(-1.0F, -2.0F, -3.0F).cosThetaSquared());
	}
	
	@Test
	public void testCrossProduct() {
		final Vector3F a = Vector3F.crossProduct(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		final Vector3F b = Vector3F.crossProduct(new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F));
		final Vector3F c = Vector3F.crossProduct(new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(1.0F, 0.0F, 0.0F));
		
		assertEquals(new Vector3F(0.0F, 0.0F, 1.0F), a);
		assertEquals(new Vector3F(1.0F, 0.0F, 0.0F), b);
		assertEquals(new Vector3F(0.0F, 1.0F, 0.0F), c);
		
		assertThrows(NullPointerException.class, () -> Vector3F.crossProduct(new Vector3F(1.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.crossProduct(null, new Vector3F(0.0F, 1.0F, 0.0F)));
	}
	
	@Test
	public void testDirectionNormalizedPoint3FPoint3F() {
		final Vector3F v = Vector3F.directionNormalized(new Point3F(10.0F, 0.0F, 0.0F), new Point3F(20.0F, 0.0F, 0.0F));
		
		assertEquals(1.0F, v.x);
		assertEquals(0.0F, v.y);
		assertEquals(0.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.directionNormalized(new Point3F(), null));
		assertThrows(NullPointerException.class, () -> Vector3F.directionNormalized(null, new Point3F()));
	}
	
	@Test
	public void testDirectionNormalizedVector3FVector3FVector3FVector3F() {
		final Vector3F v = Vector3F.directionNormalized(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(2.0F, 2.0F, 2.0F));
		
		assertEquals(Vector3F.normalize(new Vector3F(2.0F, 2.0F, 2.0F)), v);
		
		assertThrows(NullPointerException.class, () -> Vector3F.directionNormalized(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.directionNormalized(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), null, new Vector3F(2.0F, 2.0F, 2.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.directionNormalized(new Vector3F(1.0F, 0.0F, 0.0F), null, new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(2.0F, 2.0F, 2.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.directionNormalized(null, new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(2.0F, 2.0F, 2.0F)));
	}
	
	@Test
	public void testDirectionPoint3FPoint3F() {
		final Vector3F v = Vector3F.direction(new Point3F(10.0F, 20.0F, 30.0F), new Point3F(20.0F, 40.0F, 60.0F));
		
		assertEquals(10.0F, v.x);
		assertEquals(20.0F, v.y);
		assertEquals(30.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.direction(new Point3F(), null));
		assertThrows(NullPointerException.class, () -> Vector3F.direction(null, new Point3F()));
	}
	
	@Test
	public void testDirectionSphericalFloatFloatFloat() {
		final Vector3F v = Vector3F.directionSpherical(2.0F, 1.0F, (float)(Math.PI) / 2.0F);
		
		assertEquals(-0.00000008742278F, v.x);
		assertEquals(+2.00000000000000F, v.y);
		assertEquals(+1.00000000000000F, v.z);
	}
	
	@Test
	public void testDirectionSphericalFloatFloatFloatVector3FVector3FVector3F() {
		final Vector3F v = Vector3F.directionSpherical(2.0F, 1.0F, (float)(Math.PI) / 2.0F, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F));
		
		assertEquals(-0.00000008742278F, v.x);
		assertEquals(+2.00000000000000F, v.y);
		assertEquals(+1.00000000000000F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.directionSpherical(2.0F, 1.0F, (float)(Math.PI) / 2.0F, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.directionSpherical(2.0F, 1.0F, (float)(Math.PI) / 2.0F, new Vector3F(1.0F, 0.0F, 0.0F), null, new Vector3F(0.0F, 0.0F, 1.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.directionSpherical(2.0F, 1.0F, (float)(Math.PI) / 2.0F, null, new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F)));
	}
	
	@Test
	public void testDirectionSphericalNormalizedFloatFloatFloat() {
		final Vector3F v = Vector3F.directionSphericalNormalized(2.0F, 1.0F, (float)(Math.PI) / 2.0F);
		
		assertEquals(Vector3F.normalize(new Vector3F(-0.00000008742278F, 2.0F, 1.0F)), v);
	}
	
	@Test
	public void testDirectionSphericalNormalizedFloatFloatFloatVector3FVector3FVector3F() {
		final Vector3F v = Vector3F.directionSphericalNormalized(2.0F, 1.0F, (float)(Math.PI) / 2.0F, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F));
		
		assertEquals(Vector3F.normalize(new Vector3F(-0.00000008742278F, 2.0F, 1.0F)), v);
		
		assertThrows(NullPointerException.class, () -> Vector3F.directionSphericalNormalized(2.0F, 1.0F, (float)(Math.PI) / 2.0F, new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.directionSphericalNormalized(2.0F, 1.0F, (float)(Math.PI) / 2.0F, new Vector3F(1.0F, 0.0F, 0.0F), null, new Vector3F(0.0F, 0.0F, 1.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.directionSphericalNormalized(2.0F, 1.0F, (float)(Math.PI) / 2.0F, null, new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F)));
	}
	
	@Test
	public void testDirectionVector3FVector3FVector3FVector3F() {
		final Vector3F v = Vector3F.direction(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(2.0F, 2.0F, 2.0F));
		
		assertEquals(2.0F, v.x);
		assertEquals(2.0F, v.y);
		assertEquals(2.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.direction(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.direction(new Vector3F(1.0F, 0.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), null, new Vector3F(2.0F, 2.0F, 2.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.direction(new Vector3F(1.0F, 0.0F, 0.0F), null, new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(2.0F, 2.0F, 2.0F)));
		assertThrows(NullPointerException.class, () -> Vector3F.direction(null, new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(2.0F, 2.0F, 2.0F)));
	}
	
	@Test
	public void testDivide() {
		final Vector3F v = Vector3F.divide(new Vector3F(1.0F, 2.0F, 3.0F), 2.0F);
		
		assertEquals(0.5F, v.x);
		assertEquals(1.0F, v.y);
		assertEquals(1.5F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.divide(null, 2.0F));
	}
	
	@Test
	public void testDotProduct() {
		assertEquals(-1.0F, Vector3F.dotProduct(Vector3F.x(-1.0F), Vector3F.x(+1.0F)));
		assertEquals(-1.0F, Vector3F.dotProduct(Vector3F.x(+1.0F), Vector3F.x(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProduct(Vector3F.x(-1.0F), Vector3F.x(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProduct(Vector3F.x(+1.0F), Vector3F.x(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.x(+0.0F), Vector3F.x(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.x(+0.0F), Vector3F.x(-1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.x(+1.0F), Vector3F.x(+0.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.x(-1.0F), Vector3F.x(+0.0F)));
		
		assertEquals(-1.0F, Vector3F.dotProduct(Vector3F.y(-1.0F), Vector3F.y(+1.0F)));
		assertEquals(-1.0F, Vector3F.dotProduct(Vector3F.y(+1.0F), Vector3F.y(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProduct(Vector3F.y(-1.0F), Vector3F.y(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProduct(Vector3F.y(+1.0F), Vector3F.y(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.y(+0.0F), Vector3F.y(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.y(+0.0F), Vector3F.y(-1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.y(+1.0F), Vector3F.y(+0.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.y(-1.0F), Vector3F.y(+0.0F)));
		
		assertEquals(-1.0F, Vector3F.dotProduct(Vector3F.z(-1.0F), Vector3F.z(+1.0F)));
		assertEquals(-1.0F, Vector3F.dotProduct(Vector3F.z(+1.0F), Vector3F.z(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProduct(Vector3F.z(-1.0F), Vector3F.z(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProduct(Vector3F.z(+1.0F), Vector3F.z(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.z(+0.0F), Vector3F.z(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.z(+0.0F), Vector3F.z(-1.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.z(+1.0F), Vector3F.z(+0.0F)));
		assertEquals(+0.0F, Vector3F.dotProduct(Vector3F.z(-1.0F), Vector3F.z(+0.0F)));
		
		assertThrows(NullPointerException.class, () -> Vector3F.dotProduct(Vector3F.x(1.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.dotProduct(null, Vector3F.x(1.0F)));
	}
	
	@Test
	public void testDotProductAbs() {
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.x(-1.0F), Vector3F.x(+1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.x(+1.0F), Vector3F.x(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.x(-1.0F), Vector3F.x(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.x(+1.0F), Vector3F.x(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.x(+0.0F), Vector3F.x(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.x(+0.0F), Vector3F.x(-1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.x(+1.0F), Vector3F.x(+0.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.x(-1.0F), Vector3F.x(+0.0F)));
		
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.y(-1.0F), Vector3F.y(+1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.y(+1.0F), Vector3F.y(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.y(-1.0F), Vector3F.y(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.y(+1.0F), Vector3F.y(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.y(+0.0F), Vector3F.y(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.y(+0.0F), Vector3F.y(-1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.y(+1.0F), Vector3F.y(+0.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.y(-1.0F), Vector3F.y(+0.0F)));
		
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.z(-1.0F), Vector3F.z(+1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.z(+1.0F), Vector3F.z(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.z(-1.0F), Vector3F.z(-1.0F)));
		assertEquals(+1.0F, Vector3F.dotProductAbs(Vector3F.z(+1.0F), Vector3F.z(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.z(+0.0F), Vector3F.z(+1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.z(+0.0F), Vector3F.z(-1.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.z(+1.0F), Vector3F.z(+0.0F)));
		assertEquals(+0.0F, Vector3F.dotProductAbs(Vector3F.z(-1.0F), Vector3F.z(+0.0F)));
		
		assertThrows(NullPointerException.class, () -> Vector3F.dotProductAbs(Vector3F.x(1.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.dotProductAbs(null, Vector3F.x(1.0F)));
	}
	
	@Test
	public void testEqualsObject() {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F c = new Vector3F(1.0F, 2.0F, 4.0F);
		final Vector3F d = new Vector3F(1.0F, 4.0F, 3.0F);
		final Vector3F e = new Vector3F(4.0F, 2.0F, 3.0F);
		final Vector3F f = null;
		
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
	public void testEqualsVector3F() {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F c = new Vector3F(1.0F, 2.0F, 4.0F);
		final Vector3F d = new Vector3F(1.0F, 4.0F, 3.0F);
		final Vector3F e = new Vector3F(4.0F, 2.0F, 3.0F);
		final Vector3F f = null;
		
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
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(2.0F, 3.0F, 4.0F);
		final Vector3F c = Vector3F.hadamardProduct(a, b);
		
		assertEquals( 2.0F, c.x);
		assertEquals( 6.0F, c.y);
		assertEquals(12.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.hadamardProduct(a, null));
		assertThrows(NullPointerException.class, () -> Vector3F.hadamardProduct(null, b));
	}
	
	@Test
	public void testHasInfinites() {
		final Vector3F a = new Vector3F(Float.POSITIVE_INFINITY, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(1.0F, Float.POSITIVE_INFINITY, 3.0F);
		final Vector3F c = new Vector3F(1.0F, 2.0F, Float.POSITIVE_INFINITY);
		final Vector3F d = new Vector3F(1.0F, 2.0F, 3.0F);
		
		assertTrue(a.hasInfinites());
		assertTrue(b.hasInfinites());
		assertTrue(c.hasInfinites());
		
		assertFalse(d.hasInfinites());
	}
	
	@Test
	public void testHasNaNs() {
		final Vector3F a = new Vector3F(Float.NaN, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(1.0F, Float.NaN, 3.0F);
		final Vector3F c = new Vector3F(1.0F, 2.0F, Float.NaN);
		final Vector3F d = new Vector3F(1.0F, 2.0F, 3.0F);
		
		assertTrue(a.hasNaNs());
		assertTrue(b.hasNaNs());
		assertTrue(c.hasNaNs());
		
		assertFalse(d.hasNaNs());
	}
	
	@Test
	public void testHashCode() {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = new Vector3F(1.0F, 2.0F, 3.0F);
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testHemisphereCosineDistributionPDF() {
		assertEquals(1.0F * (1.0F / (float)(Math.PI)), Vector3F.hemisphereCosineDistributionPDF(1.0F));
	}
	
	@Test
	public void testHemisphereUniformDistributionPDF() {
		assertEquals(1.0F / ((float)(Math.PI) * 2.0F), Vector3F.hemisphereUniformDistributionPDF());
	}
	
	@Test
	public void testIsFinite() {
		final Vector3F a = new Vector3F(Float.NaN, Float.NaN, Float.NaN);
		final Vector3F b = new Vector3F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		final Vector3F c = new Vector3F(Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
		final Vector3F d = new Vector3F(1.0F, 2.0F, 3.0F);
		
		assertFalse(a.isFinite());
		assertFalse(b.isFinite());
		assertFalse(c.isFinite());
		
		assertTrue(d.isFinite());
	}
	
	@Test
	public void testIsUnitVector() {
		assertTrue(new Vector3F(+1.0F, +0.0F, +0.0F).isUnitVector());
		assertTrue(new Vector3F(+0.0F, +1.0F, +0.0F).isUnitVector());
		assertTrue(new Vector3F(+0.0F, +0.0F, +1.0F).isUnitVector());
		assertTrue(new Vector3F(-1.0F, -0.0F, -0.0F).isUnitVector());
		assertTrue(new Vector3F(-0.0F, -1.0F, -0.0F).isUnitVector());
		assertTrue(new Vector3F(-0.0F, -0.0F, -1.0F).isUnitVector());
		assertTrue(Vector3F.normalize(new Vector3F(1.0F, 1.0F, 1.0F)).isUnitVector());
		
		assertFalse(new Vector3F(+1.0F, +1.0F, +1.0F).isUnitVector());
		assertFalse(new Vector3F(+0.5F, +0.5F, +0.5F).isUnitVector());
		assertFalse(new Vector3F(-1.0F, -1.0F, -1.0F).isUnitVector());
		assertFalse(new Vector3F(-0.5F, -0.5F, -0.5F).isUnitVector());
	}
	
	@Test
	public void testIsZero() {
		assertTrue(new Vector3F(0.0F, 0.0F, 0.0F).isZero());
		
		assertFalse(new Vector3F(0.0F, 0.0F, 1.0F).isZero());
		assertFalse(new Vector3F(0.0F, 1.0F, 0.0F).isZero());
		assertFalse(new Vector3F(1.0F, 0.0F, 0.0F).isZero());
		assertFalse(new Vector3F(1.0F, 1.0F, 1.0F).isZero());
	}
	
	@Test
	public void testLength() {
		assertEquals(4.0F, new Vector3F(0.0F, 0.0F, 4.0F).length());
		assertEquals(4.0F, new Vector3F(0.0F, 4.0F, 0.0F).length());
		assertEquals(4.0F, new Vector3F(4.0F, 0.0F, 0.0F).length());
	}
	
	@Test
	public void testLengthSquared() {
		assertEquals(14.0F, new Vector3F(1.0F, 2.0F, 3.0F).lengthSquared());
	}
	
	@Test
	public void testLerp() {
		final Vector3F a = new Vector3F(1.0F, 1.0F, 1.0F);
		final Vector3F b = new Vector3F(5.0F, 5.0F, 5.0F);
		final Vector3F c = Vector3F.lerp(a, b, 0.25F);
		
		assertEquals(2.0F, c.x);
		assertEquals(2.0F, c.y);
		assertEquals(2.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.lerp(a, null, 0.25F));
		assertThrows(NullPointerException.class, () -> Vector3F.lerp(null, b, 0.25F));
	}
	
	@Test
	public void testMultiply() {
		final Vector3F v = Vector3F.multiply(new Vector3F(1.0F, 2.0F, 3.0F), 2.0F);
		
		assertEquals(2.0F, v.x);
		assertEquals(4.0F, v.y);
		assertEquals(6.0F, v.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.multiply(null, 2.0F));
	}
	
	@Test
	public void testNegate() {
		final Vector3F a = Vector3F.negate(new Vector3F(+1.0F, +1.0F, +1.0F));
		final Vector3F b = Vector3F.negate(new Vector3F(-1.0F, -1.0F, -1.0F));
		
		assertEquals(-1.0F, a.x);
		assertEquals(-1.0F, a.y);
		assertEquals(-1.0F, a.z);
		
		assertEquals(+1.0F, b.x);
		assertEquals(+1.0F, b.y);
		assertEquals(+1.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.negate(null));
	}
	
	@Test
	public void testNegateX() {
		final Vector3F a = Vector3F.negateX(new Vector3F(+1.0F, +1.0F, +1.0F));
		final Vector3F b = Vector3F.negateX(new Vector3F(-1.0F, -1.0F, -1.0F));
		
		assertEquals(-1.0F, a.x);
		assertEquals(+1.0F, a.y);
		assertEquals(+1.0F, a.z);
		
		assertEquals(+1.0F, b.x);
		assertEquals(-1.0F, b.y);
		assertEquals(-1.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.negateX(null));
	}
	
	@Test
	public void testNegateY() {
		final Vector3F a = Vector3F.negateY(new Vector3F(+1.0F, +1.0F, +1.0F));
		final Vector3F b = Vector3F.negateY(new Vector3F(-1.0F, -1.0F, -1.0F));
		
		assertEquals(+1.0F, a.x);
		assertEquals(-1.0F, a.y);
		assertEquals(+1.0F, a.z);
		
		assertEquals(-1.0F, b.x);
		assertEquals(+1.0F, b.y);
		assertEquals(-1.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.negateY(null));
	}
	
	@Test
	public void testNegateZ() {
		final Vector3F a = Vector3F.negateZ(new Vector3F(+1.0F, +1.0F, +1.0F));
		final Vector3F b = Vector3F.negateZ(new Vector3F(-1.0F, -1.0F, -1.0F));
		
		assertEquals(+1.0F, a.x);
		assertEquals(+1.0F, a.y);
		assertEquals(-1.0F, a.z);
		
		assertEquals(-1.0F, b.x);
		assertEquals(-1.0F, b.y);
		assertEquals(+1.0F, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.negateZ(null));
	}
	
	@Test
	public void testNormalNormalizedPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F(0.0F, 0.0F, 0.0F);
		final Point3F b = new Point3F(1.0F, 0.0F, 0.0F);
		final Point3F c = new Point3F(0.0F, 1.0F, 0.0F);
		
		final Vector3F vector = Vector3F.normalNormalized(a, b, c);
		
		assertEquals(0.0F, vector.x);
		assertEquals(0.0F, vector.y);
		assertEquals(1.0F, vector.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.normalNormalized(a, b, null));
		assertThrows(NullPointerException.class, () -> Vector3F.normalNormalized(a, null, c));
		assertThrows(NullPointerException.class, () -> Vector3F.normalNormalized(null, b, c));
	}
	
	@Test
	public void testNormalPoint3FPoint3FPoint3F() {
		final Point3F a = new Point3F(0.0F, 0.0F, 0.0F);
		final Point3F b = new Point3F(1.0F, 0.0F, 0.0F);
		final Point3F c = new Point3F(0.0F, 1.0F, 0.0F);
		
		final Vector3F vector = Vector3F.normal(a, b, c);
		
		assertEquals(0.0F, vector.x);
		assertEquals(0.0F, vector.y);
		assertEquals(1.0F, vector.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.normal(a, b, null));
		assertThrows(NullPointerException.class, () -> Vector3F.normal(a, null, c));
		assertThrows(NullPointerException.class, () -> Vector3F.normal(null, b, c));
	}
	
	@Test
	public void testNormalize() {
		final Vector3F a = new Vector3F(+1.0F, +0.0F, +0.0F);
		final Vector3F b = Vector3F.normalize(a);
		final Vector3F c = new Vector3F(+0.0F, +1.0F, +0.0F);
		final Vector3F d = Vector3F.normalize(c);
		final Vector3F e = new Vector3F(+0.0F, +0.0F, +1.0F);
		final Vector3F f = Vector3F.normalize(e);
		final Vector3F g = new Vector3F(+1.0F, +1.0F, +1.0F);
		final Vector3F h = Vector3F.normalize(g);
		final Vector3F i = Vector3F.normalize(h);
		final Vector3F j = new Vector3F(-1.0F, -1.0F, -1.0F);
		final Vector3F k = Vector3F.normalize(j);
		final Vector3F l = Vector3F.normalize(k);
		
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
		
		assertThrows(NullPointerException.class, () -> Vector3F.normalize(null));
	}
	
	@Test
	public void testOrientNormal() {
		final Vector3F a = Vector3F.orientNormal(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, +1.0F));
		final Vector3F b = Vector3F.orientNormal(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, -1.0F));
		final Vector3F c = Vector3F.orientNormal(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, -1.0F));
		final Vector3F d = Vector3F.orientNormal(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, +1.0F));
		
		assertEquals(new Vector3F(+0.0F, +0.0F, +1.0F), a);
		assertEquals(new Vector3F(+0.0F, +0.0F, -1.0F), b);
		assertEquals(new Vector3F(-0.0F, -0.0F, +1.0F), c);
		assertEquals(new Vector3F(-0.0F, -0.0F, -1.0F), d);
		
		assertThrows(NullPointerException.class, () -> Vector3F.orientNormal(new Vector3F(0.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.orientNormal(null, new Vector3F(0.0F, 0.0F, 0.0F)));
	}
	
	@Test
	public void testOrientNormalNegated() {
		final Vector3F a = Vector3F.orientNormalNegated(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, +1.0F));
		final Vector3F b = Vector3F.orientNormalNegated(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, -1.0F));
		final Vector3F c = Vector3F.orientNormalNegated(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, -1.0F));
		final Vector3F d = Vector3F.orientNormalNegated(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, +1.0F));
		
		assertEquals(new Vector3F(-0.0F, -0.0F, -1.0F), a);
		assertEquals(new Vector3F(-0.0F, -0.0F, +1.0F), b);
		assertEquals(new Vector3F(+0.0F, +0.0F, -1.0F), c);
		assertEquals(new Vector3F(+0.0F, +0.0F, +1.0F), d);
		
		assertThrows(NullPointerException.class, () -> Vector3F.orientNormalNegated(new Vector3F(0.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.orientNormalNegated(null, new Vector3F(0.0F, 0.0F, 0.0F)));
	}
	
	@Test
	public void testOrientNormalSameHemisphereZ() {
		final Vector3F a = Vector3F.orientNormalSameHemisphereZ(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, +1.0F));
		final Vector3F b = Vector3F.orientNormalSameHemisphereZ(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, -1.0F));
		final Vector3F c = Vector3F.orientNormalSameHemisphereZ(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, -1.0F));
		final Vector3F d = Vector3F.orientNormalSameHemisphereZ(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, +1.0F));
		
		assertEquals(new Vector3F(+0.0F, +0.0F, +1.0F), a);
		assertEquals(new Vector3F(+0.0F, +0.0F, -1.0F), b);
		assertEquals(new Vector3F(-0.0F, -0.0F, +1.0F), c);
		assertEquals(new Vector3F(-0.0F, -0.0F, -1.0F), d);
		
		assertThrows(NullPointerException.class, () -> Vector3F.orientNormalSameHemisphereZ(new Vector3F(0.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.orientNormalSameHemisphereZ(null, new Vector3F(0.0F, 0.0F, 0.0F)));
	}
	
	@Test
	public void testOrthogonalVector3F() {
		final Vector3F a = new Vector3F(1.0F, 0.0F, 0.0F);
		final Vector3F b = Vector3F.orthogonal(a);
		final Vector3F c = new Vector3F(0.0F, 1.0F, 0.0F);
		final Vector3F d = Vector3F.orthogonal(c);
		final Vector3F e = new Vector3F(0.0F, 0.0F, 1.0F);
		final Vector3F f = Vector3F.orthogonal(e);
		final Vector3F g = Vector3F.normalize(new Vector3F(1.0F, 2.0F, 3.0F));
		final Vector3F h = Vector3F.orthogonal(g);
		final Vector3F i = Vector3F.normalize(new Vector3F(0.0F, g.z, -g.y));
		
		assertEquals(+0.0F, b.x);
		assertEquals(-1.0F, b.y);
		assertEquals(+0.0F, b.z);
		
		assertEquals(+1.0F, d.x);
		assertEquals(-0.0F, d.y);
		assertEquals(+0.0F, d.z);
		
		assertEquals(+1.0F, f.x);
		assertEquals(+0.0F, f.y);
		assertEquals(-0.0F, f.z);
		
		assertEquals(i.x, h.x);
		assertEquals(i.y, h.y);
		assertEquals(i.z, h.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.orthogonal(null));
	}
	
	@Test
	public void testOrthogonalVector3FVector3F() {
		final Vector3F a = new Vector3F(1.0F, 0.0F, 0.0F);
		final Vector3F b = new Vector3F(0.0F, 1.0F, 0.0F);
		final Vector3F c = new Vector3F(1.0F, 1.0F, 1.0F);
		
		assertTrue(Vector3F.orthogonal(a, b));
		
		assertFalse(Vector3F.orthogonal(a, c));
		
		assertThrows(NullPointerException.class, () -> Vector3F.orthogonal(a, null));
		assertThrows(NullPointerException.class, () -> Vector3F.orthogonal(null, b));
	}
	
	@Test
	public void testRead() throws IOException {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeFloat(a.x);
		dataOutput.writeFloat(a.y);
		dataOutput.writeFloat(a.z);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Vector3F b = Vector3F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Vector3F.read(null));
		assertThrows(UncheckedIOException.class, () -> Vector3F.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testReciprocal() {
		final Vector3F a = Vector3F.reciprocal(new Vector3F(Float.NaN, Float.NaN, Float.NaN));
		final Vector3F b = Vector3F.reciprocal(new Vector3F(2.0F, 4.0F, 8.0F));
		
		assertEquals(Float.NaN, a.x);
		assertEquals(Float.NaN, a.y);
		assertEquals(Float.NaN, a.z);
		
		assertEquals(0.500F, b.x);
		assertEquals(0.250F, b.y);
		assertEquals(0.125F, b.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.reciprocal(null));
	}
	
	@Test
	public void testRefraction() {
		final Optional<Vector3F> a = Vector3F.refraction(new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(0.0F, 1.0F, 0.0F), 1.0F);
		final Optional<Vector3F> b = Vector3F.refraction(new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertNotNull(a);
		assertNotNull(b);
		
		assertTrue(a.isPresent());
		
		assertFalse(b.isPresent());
		
		assertEquals(+0.0F, a.get().x);
		assertEquals(-1.0F, a.get().y);
		assertEquals(+0.0F, a.get().z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.refraction(new Vector3F(0.0F, 1.0F, 0.0F), null, 1.0F));
		assertThrows(NullPointerException.class, () -> Vector3F.refraction(null, new Vector3F(0.0F, 1.0F, 0.0F), 1.0F));
	}
	
	@Test
	public void testSameHemisphere() {
		final Vector3F a = new Vector3F(+1.0F, 0.0F, 0.0F);
		final Vector3F b = new Vector3F(-1.0F, 0.0F, 0.0F);
		
		assertTrue(Vector3F.sameHemisphere(a, a));
		
		assertFalse(Vector3F.sameHemisphere(a, b));
		
		assertThrows(NullPointerException.class, () -> Vector3F.sameHemisphere(a, null));
		assertThrows(NullPointerException.class, () -> Vector3F.sameHemisphere(null, b));
	}
	
	@Test
	public void testSameHemisphereZ() {
		assertTrue(Vector3F.sameHemisphereZ(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, +1.0F)));
		assertTrue(Vector3F.sameHemisphereZ(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, -1.0F)));
		
		assertFalse(Vector3F.sameHemisphereZ(new Vector3F(0.0F, 0.0F, +1.0F), new Vector3F(0.0F, 0.0F, -1.0F)));
		assertFalse(Vector3F.sameHemisphereZ(new Vector3F(0.0F, 0.0F, -1.0F), new Vector3F(0.0F, 0.0F, +1.0F)));
		
		assertThrows(NullPointerException.class, () -> Vector3F.sameHemisphereZ(new Vector3F(0.0F, 0.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Vector3F.sameHemisphereZ(null, new Vector3F(0.0F, 0.0F, 0.0F)));
	}
	
	@Test
	public void testSampleHemispherePowerCosineDistribution() {
		for(int i = 0; i < 10000; i++) {
			final Vector3F v = Vector3F.sampleHemispherePowerCosineDistribution();
			
			assertTrue(v.x >= -1.0F && v.x <= +1.0F);
			assertTrue(v.y >= -1.0F && v.y <= +1.0F);
			assertTrue(v.z >= +0.0F && v.z <= +1.0F);
		}
	}
	
	@Test
	public void testSampleHemispherePowerCosineDistributionPoint2F() {
		for(int i = 0; i < 10000; i++) {
			final Vector3F v = Vector3F.sampleHemispherePowerCosineDistribution(Point2F.sampleRandom());
			
			assertTrue(v.x >= -1.0F && v.x <= +1.0F);
			assertTrue(v.y >= -1.0F && v.y <= +1.0F);
			assertTrue(v.z >= +0.0F && v.z <= +1.0F);
		}
		
		assertThrows(NullPointerException.class, () -> Vector3F.sampleHemispherePowerCosineDistribution(null));
	}
	
	@Test
	public void testSampleHemispherePowerCosineDistributionPoint2FFloat() {
		final Vector3F a = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.0F, 0.0F), 0.0F);
		final Vector3F b = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.0F, 0.5F), 0.0F);
		final Vector3F c = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.0F, 1.0F), 0.0F);
		final Vector3F d = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.5F, 0.0F), 0.0F);
		final Vector3F e = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.5F, 0.5F), 0.0F);
		final Vector3F f = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.5F, 1.0F), 0.0F);
		final Vector3F g = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(1.0F, 0.0F), 0.0F);
		final Vector3F h = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(1.0F, 0.5F), 0.0F);
		final Vector3F i = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(1.0F, 1.0F), 0.0F);
		
		final Vector3F j = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.0F, 0.0F), 1.0F);
		final Vector3F k = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.0F, 0.5F), 1.0F);
		final Vector3F l = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.0F, 1.0F), 1.0F);
		final Vector3F m = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.5F, 0.0F), 1.0F);
		final Vector3F n = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.5F, 0.5F), 1.0F);
		final Vector3F o = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(0.5F, 1.0F), 1.0F);
		final Vector3F p = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(1.0F, 0.0F), 1.0F);
		final Vector3F q = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(1.0F, 0.5F), 1.0F);
		final Vector3F r = Vector3F.sampleHemispherePowerCosineDistribution(new Point2F(1.0F, 1.0F), 1.0F);
		
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 1.0F)) * (float)(Math.pow(1.0F, 1.0F)))) * (float)(Math.cos(Math.PI * 0.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 1.0F)) * (float)(Math.pow(1.0F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 0.0F)), (float)(Math.pow(1.0F, 1.0F))), a);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 1.0F)) * (float)(Math.pow(1.0F, 1.0F)))) * (float)(Math.cos(Math.PI * 1.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 1.0F)) * (float)(Math.pow(1.0F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 1.0F)), (float)(Math.pow(1.0F, 1.0F))), b);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 1.0F)) * (float)(Math.pow(1.0F, 1.0F)))) * (float)(Math.cos(Math.PI * 2.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 1.0F)) * (float)(Math.pow(1.0F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 2.0F)), (float)(Math.pow(1.0F, 1.0F))), c);
		
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 1.0F)) * (float)(Math.pow(0.5F, 1.0F)))) * (float)(Math.cos(Math.PI * 0.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 1.0F)) * (float)(Math.pow(0.5F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 0.0F)), (float)(Math.pow(0.5F, 1.0F))), d);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 1.0F)) * (float)(Math.pow(0.5F, 1.0F)))) * (float)(Math.cos(Math.PI * 1.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 1.0F)) * (float)(Math.pow(0.5F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 1.0F)), (float)(Math.pow(0.5F, 1.0F))), e);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 1.0F)) * (float)(Math.pow(0.5F, 1.0F)))) * (float)(Math.cos(Math.PI * 2.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 1.0F)) * (float)(Math.pow(0.5F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 2.0F)), (float)(Math.pow(0.5F, 1.0F))), f);
		
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 1.0F)) * (float)(Math.pow(0.0F, 1.0F)))) * (float)(Math.cos(Math.PI * 0.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 1.0F)) * (float)(Math.pow(0.0F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 0.0F)), (float)(Math.pow(0.0F, 1.0F))), g);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 1.0F)) * (float)(Math.pow(0.0F, 1.0F)))) * (float)(Math.cos(Math.PI * 1.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 1.0F)) * (float)(Math.pow(0.0F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 1.0F)), (float)(Math.pow(0.0F, 1.0F))), h);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 1.0F)) * (float)(Math.pow(0.0F, 1.0F)))) * (float)(Math.cos(Math.PI * 2.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 1.0F)) * (float)(Math.pow(0.0F, 1.0F)))) * (float)(Math.sin((float)(Math.PI) * 2.0F)), (float)(Math.pow(0.0F, 1.0F))), i);
		
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 0.5F)) * (float)(Math.pow(1.0F, 0.5F)))) * (float)(Math.cos(Math.PI * 0.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 0.5F)) * (float)(Math.pow(1.0F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 0.0F)), (float)(Math.pow(1.0F, 0.5F))), j);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 0.5F)) * (float)(Math.pow(1.0F, 0.5F)))) * (float)(Math.cos(Math.PI * 1.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 0.5F)) * (float)(Math.pow(1.0F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 1.0F)), (float)(Math.pow(1.0F, 0.5F))), k);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 0.5F)) * (float)(Math.pow(1.0F, 0.5F)))) * (float)(Math.cos(Math.PI * 2.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(1.0F, 0.5F)) * (float)(Math.pow(1.0F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 2.0F)), (float)(Math.pow(1.0F, 0.5F))), l);
		
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 0.5F)) * (float)(Math.pow(0.5F, 0.5F)))) * (float)(Math.cos(Math.PI * 0.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 0.5F)) * (float)(Math.pow(0.5F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 0.0F)), (float)(Math.pow(0.5F, 0.5F))), m);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 0.5F)) * (float)(Math.pow(0.5F, 0.5F)))) * (float)(Math.cos(Math.PI * 1.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 0.5F)) * (float)(Math.pow(0.5F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 1.0F)), (float)(Math.pow(0.5F, 0.5F))), n);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 0.5F)) * (float)(Math.pow(0.5F, 0.5F)))) * (float)(Math.cos(Math.PI * 2.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.5F, 0.5F)) * (float)(Math.pow(0.5F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 2.0F)), (float)(Math.pow(0.5F, 0.5F))), o);
		
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 0.5F)) * (float)(Math.pow(0.0F, 0.5F)))) * (float)(Math.cos(Math.PI * 0.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 0.5F)) * (float)(Math.pow(0.0F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 0.0F)), (float)(Math.pow(0.0F, 0.5F))), p);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 0.5F)) * (float)(Math.pow(0.0F, 0.5F)))) * (float)(Math.cos(Math.PI * 1.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 0.5F)) * (float)(Math.pow(0.0F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 1.0F)), (float)(Math.pow(0.0F, 0.5F))), q);
		assertEquals(new Vector3F((float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 0.5F)) * (float)(Math.pow(0.0F, 0.5F)))) * (float)(Math.cos(Math.PI * 2.0F)), (float)(Math.sqrt(1.0F - (float)(Math.pow(0.0F, 0.5F)) * (float)(Math.pow(0.0F, 0.5F)))) * (float)(Math.sin((float)(Math.PI) * 2.0F)), (float)(Math.pow(0.0F, 0.5F))), r);
		
		assertThrows(NullPointerException.class, () -> Vector3F.sampleHemispherePowerCosineDistribution(null, 0.0F));
	}
	
	@Test
	public void testSampleHemisphereUniformDistribution() {
		for(int i = 0; i < 10000; i++) {
			final Vector3F v = Vector3F.sampleHemisphereUniformDistribution();
			
			assertTrue(v.x >= -1.0F && v.x <= +1.0F);
			assertTrue(v.y >= -1.0F && v.y <= +1.0F);
			assertTrue(v.z >= -1.0F && v.z <= +1.0F);
		}
	}
	
	@Test
	public void testSampleHemisphereUniformDistributionPoint2F() {
		final Vector3F a = Vector3F.sampleHemisphereUniformDistribution(new Point2F(0.0F, 0.0F));
		final Vector3F b = Vector3F.sampleHemisphereUniformDistribution(new Point2F(0.0F, 1.0F));
		final Vector3F c = Vector3F.sampleHemisphereUniformDistribution(new Point2F(1.0F, 0.0F));
		final Vector3F d = Vector3F.sampleHemisphereUniformDistribution(new Point2F(1.0F, 1.0F));
		
		assertEquals(new Vector3F(1.0F * (float)(Math.cos((float)(Math.PI) * 0.0F)), 1.0F * (float)(Math.sin((float)(Math.PI) * 0.0F)), 0.0F), a);
		assertEquals(new Vector3F(1.0F * (float)(Math.cos((float)(Math.PI) * 2.0F)), 1.0F * (float)(Math.sin((float)(Math.PI) * 2.0F)), 0.0F), b);
		
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 0.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 0.0F)), 1.0F), c);
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 2.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 2.0F)), 1.0F), d);
		
		assertThrows(NullPointerException.class, () -> Vector3F.sampleHemisphereUniformDistribution(null));
	}
	
	@Test
	public void testSampleSphereUniformDistribution() {
		for(int i = 0; i < 10000; i++) {
			final Vector3F v = Vector3F.sampleSphereUniformDistribution();
			
			assertTrue(v.x >= -1.0F && v.x <= +1.0F);
			assertTrue(v.y >= -1.0F && v.y <= +1.0F);
			assertTrue(v.z >= -1.0F && v.z <= +1.0F);
		}
	}
	
	@Test
	public void testSampleSphereUniformDistributionPoint2F() {
		final Vector3F a = Vector3F.sampleSphereUniformDistribution(new Point2F(0.0F, 0.0F));
		final Vector3F b = Vector3F.sampleSphereUniformDistribution(new Point2F(0.0F, 0.5F));
		final Vector3F c = Vector3F.sampleSphereUniformDistribution(new Point2F(0.0F, 1.0F));
		final Vector3F d = Vector3F.sampleSphereUniformDistribution(new Point2F(0.5F, 0.0F));
		final Vector3F e = Vector3F.sampleSphereUniformDistribution(new Point2F(0.5F, 0.5F));
		final Vector3F f = Vector3F.sampleSphereUniformDistribution(new Point2F(0.5F, 1.0F));
		final Vector3F g = Vector3F.sampleSphereUniformDistribution(new Point2F(1.0F, 0.0F));
		final Vector3F h = Vector3F.sampleSphereUniformDistribution(new Point2F(1.0F, 0.5F));
		final Vector3F i = Vector3F.sampleSphereUniformDistribution(new Point2F(1.0F, 1.0F));
		
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 0.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 0.0F)), +1.0F), a);
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 1.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 1.0F)), +1.0F), b);
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 2.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 2.0F)), +1.0F), c);
		
		assertEquals(new Vector3F(1.0F * (float)(Math.cos((float)(Math.PI) * 0.0F)), 1.0F * (float)(Math.sin((float)(Math.PI) * 0.0F)), +0.0F), d);
		assertEquals(new Vector3F(1.0F * (float)(Math.cos((float)(Math.PI) * 1.0F)), 1.0F * (float)(Math.sin((float)(Math.PI) * 1.0F)), +0.0F), e);
		assertEquals(new Vector3F(1.0F * (float)(Math.cos((float)(Math.PI) * 2.0F)), 1.0F * (float)(Math.sin((float)(Math.PI) * 2.0F)), +0.0F), f);
		
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 0.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 0.0F)), -1.0F), g);
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 1.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 1.0F)), -1.0F), h);
		assertEquals(new Vector3F(0.0F * (float)(Math.cos((float)(Math.PI) * 2.0F)), 0.0F * (float)(Math.sin((float)(Math.PI) * 2.0F)), -1.0F), i);
		
		assertThrows(NullPointerException.class, () -> Vector3F.sampleSphereUniformDistribution(null));
	}
	
	@Test
	public void testSinPhi() {
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.5F, +1.0F).sinPhi());
		assertEquals(-1.0F, new Vector3F(+0.0F, -2.0F, +0.0F).sinPhi());
		assertEquals(+0.5F, new Vector3F(+0.0F, +0.5F, +0.0F).sinPhi());
		assertEquals(+1.0F, new Vector3F(+0.0F, +2.0F, +0.0F).sinPhi());
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.5F, -1.0F).sinPhi());
	}
	
	@Test
	public void testSinPhiSquared() {
		assertEquals(+0.00F, new Vector3F(+0.0F, +0.5F, +1.0F).sinPhiSquared());
		assertEquals(+1.00F, new Vector3F(+0.0F, -2.0F, +0.0F).sinPhiSquared());
		assertEquals(+0.25F, new Vector3F(+0.0F, +0.5F, +0.0F).sinPhiSquared());
		assertEquals(+1.00F, new Vector3F(+0.0F, +2.0F, +0.0F).sinPhiSquared());
		assertEquals(+0.00F, new Vector3F(+0.0F, +0.5F, -1.0F).sinPhiSquared());
	}
	
	@Test
	public void testSinTheta() {
		assertEquals(0.0F, new Vector3F(+0.0F, +0.0F, +1.0F).sinTheta());
		assertEquals(1.0F, new Vector3F(+0.0F, +0.0F, +0.0F).sinTheta());
		assertEquals(0.0F, new Vector3F(+0.0F, +0.0F, -1.0F).sinTheta());
	}
	
	@Test
	public void testSinThetaSquared() {
		assertEquals(0.0F, new Vector3F(+0.0F, +0.0F, +1.0F).sinThetaSquared());
		assertEquals(1.0F, new Vector3F(+0.0F, +0.0F, +0.0F).sinThetaSquared());
		assertEquals(0.0F, new Vector3F(+0.0F, +0.0F, -1.0F).sinThetaSquared());
	}
	
	@Test
	public void testSphereUniformDistributionPDF() {
		assertEquals(1.0F / ((float)(Math.PI) * 4.0F), Vector3F.sphereUniformDistributionPDF());
	}
	
	@Test
	public void testSphericalPhi() {
		assertEquals(0.0F,    new Vector3F(+1.0F, 0.0F, 0.0F).sphericalPhi());
		assertEquals(0.0F,    new Vector3F(+0.0F, 0.0F, 0.0F).sphericalPhi());
		assertEquals((float)(Math.PI), new Vector3F(-0.0F, 0.0F, 0.0F).sphericalPhi());
		assertEquals((float)(Math.PI), new Vector3F(-1.0F, 0.0F, 0.0F).sphericalPhi());
		
		assertEquals((float)(Math.PI) / 4.0000000000000000F, new Vector3F(+1.0F, 1.0F, 0.0F).sphericalPhi());
		assertEquals((float)(Math.PI) / 2.0000000000000000F, new Vector3F(+0.0F, 1.0F, 0.0F).sphericalPhi());
		assertEquals((float)(Math.PI) / 2.0000000000000000F, new Vector3F(-0.0F, 1.0F, 0.0F).sphericalPhi());
		assertEquals((float)(Math.PI) / 1.3333333333333333F, new Vector3F(-1.0F, 1.0F, 0.0F).sphericalPhi());
	}
	
	@Test
	public void testSubtract() {
		final Vector3F a = new Vector3F(3.0F, 5.0F, 7.0F);
		final Vector3F b = new Vector3F(2.0F, 3.0F, 4.0F);
		final Vector3F c = Vector3F.subtract(a, b);
		
		assertEquals(1.0F, c.x);
		assertEquals(2.0F, c.y);
		assertEquals(3.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Vector3F.subtract(a, null));
		assertThrows(NullPointerException.class, () -> Vector3F.subtract(null, b));
	}
	
	@Test
	public void testTanTheta() {
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.0F, +1.0F).tanTheta());
		assertEquals(-0.0F, new Vector3F(+0.0F, +0.0F, -1.0F).tanTheta());
	}
	
	@Test
	public void testTanThetaAbs() {
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.0F, +1.0F).tanThetaAbs());
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.0F, -1.0F).tanThetaAbs());
	}
	
	@Test
	public void testTanThetaSquared() {
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.0F, +1.0F).tanThetaSquared());
		assertEquals(+0.0F, new Vector3F(+0.0F, +0.0F, -1.0F).tanThetaSquared());
	}
	
	@Test
	public void testToArray() {
		final Vector3F vector = new Vector3F(1.0F, 2.0F, 3.0F);
		
		final float[] array = vector.toArray();
		
		assertNotNull(array);
		
		assertEquals(3, array.length);
		
		assertEquals(1.0F, array[0]);
		assertEquals(2.0F, array[1]);
		assertEquals(3.0F, array[2]);
	}
	
	@Test
	public void testToString() {
		final Vector3F v = new Vector3F(1.0F, 2.0F, 3.0F);
		
		assertEquals("new Vector3F(1.0F, 2.0F, 3.0F)", v.toString());
	}
	
	@Test
	public void testTripleProduct() {
		assertEquals(+1.0F, Vector3F.tripleProduct(Vector3F.x(), Vector3F.y(), Vector3F.z()));
		assertEquals(+1.0F, Vector3F.tripleProduct(Vector3F.y(), Vector3F.z(), Vector3F.x()));
		assertEquals(+1.0F, Vector3F.tripleProduct(Vector3F.z(), Vector3F.x(), Vector3F.y()));
		
		assertEquals(-1.0F, Vector3F.tripleProduct(Vector3F.z(), Vector3F.y(), Vector3F.x()));
		assertEquals(-1.0F, Vector3F.tripleProduct(Vector3F.y(), Vector3F.x(), Vector3F.z()));
		assertEquals(-1.0F, Vector3F.tripleProduct(Vector3F.x(), Vector3F.z(), Vector3F.y()));
		
		assertThrows(NullPointerException.class, () -> Vector3F.tripleProduct(Vector3F.x(), Vector3F.y(), null));
		assertThrows(NullPointerException.class, () -> Vector3F.tripleProduct(Vector3F.x(), null, Vector3F.z()));
		assertThrows(NullPointerException.class, () -> Vector3F.tripleProduct(null, Vector3F.y(), Vector3F.z()));
	}
	
	@Test
	public void testWrite() {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Vector3F b = Vector3F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
	
	@Test
	public void testX() {
		final Vector3F v = Vector3F.x();
		
		assertEquals(1.0F, v.x);
		assertEquals(0.0F, v.y);
		assertEquals(0.0F, v.z);
	}
	
	@Test
	public void testXFloat() {
		final Vector3F v = Vector3F.x(2.0F);
		
		assertEquals(2.0F, v.x);
		assertEquals(0.0F, v.y);
		assertEquals(0.0F, v.z);
	}
	
	@Test
	public void testY() {
		final Vector3F v = Vector3F.y();
		
		assertEquals(0.0F, v.x);
		assertEquals(1.0F, v.y);
		assertEquals(0.0F, v.z);
	}
	
	@Test
	public void testYFloat() {
		final Vector3F v = Vector3F.y(2.0F);
		
		assertEquals(0.0F, v.x);
		assertEquals(2.0F, v.y);
		assertEquals(0.0F, v.z);
	}
	
	@Test
	public void testZ() {
		final Vector3F v = Vector3F.z();
		
		assertEquals(0.0F, v.x);
		assertEquals(0.0F, v.y);
		assertEquals(1.0F, v.z);
	}
	
	@Test
	public void testZFloat() {
		final Vector3F v = Vector3F.z(2.0F);
		
		assertEquals(0.0F, v.x);
		assertEquals(0.0F, v.y);
		assertEquals(2.0F, v.z);
	}
}