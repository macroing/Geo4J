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
package org.macroing.geo4j.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.Point3F;
import org.macroing.geo4j.common.Vector3F;
import org.macroing.geo4j.matrix.Matrix44F;

@SuppressWarnings("static-method")
public final class SurfaceSample3FUnitTests {
	public SurfaceSample3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		final Optional<SurfaceSample3F> optionalSurfaceSample = SurfaceSample3F.EMPTY;
		
		assertTrue(optionalSurfaceSample.isEmpty());
	}
	
	@Test
	public void testConstructor() {
		final SurfaceSample3F surfaceSample = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals(new Point3F(1.0F, 2.0F, 3.0F), surfaceSample.getPoint());
		assertEquals(new Vector3F(1.0F, 2.0F, 3.0F), surfaceSample.getPointError());
		assertEquals(new Vector3F(1.0F, 0.0F, 0.0F), surfaceSample.getSurfaceNormal());
		assertEquals(1.0F, surfaceSample.getProbabilityDensityFunctionValue());
		
		assertThrows(NullPointerException.class, () -> new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), null, 1.0F));
		assertThrows(NullPointerException.class, () -> new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), null, new Vector3F(1.0F, 0.0F, 0.0F), 1.0F));
		assertThrows(NullPointerException.class, () -> new SurfaceSample3F(null, new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F));
	}
	
	@Test
	public void testEquals() {
		final SurfaceSample3F a = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F b = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F c = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 2.0F);
		final SurfaceSample3F d = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(0.0F, 1.0F, 0.0F), 1.0F);
		final SurfaceSample3F e = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(2.0F, 3.0F, 4.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F f = new SurfaceSample3F(new Point3F(2.0F, 3.0F, 4.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F g = null;
		
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
		assertNotEquals(a, g);
		assertNotEquals(g, a);
	}
	
	@Test
	public void testGetPoint() {
		final SurfaceSample3F surfaceSample = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals(new Point3F(1.0F, 2.0F, 3.0F), surfaceSample.getPoint());
	}
	
	@Test
	public void testGetPointError() {
		final SurfaceSample3F surfaceSample = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals(new Vector3F(1.0F, 2.0F, 3.0F), surfaceSample.getPointError());
	}
	
	@Test
	public void testGetProbabilityDensityFunctionValue() {
		final SurfaceSample3F surfaceSample = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals(1.0F, surfaceSample.getProbabilityDensityFunctionValue());
	}
	
	@Test
	public void testGetSurfaceNormal() {
		final SurfaceSample3F a = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F b = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(2.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals(new Vector3F(1.0F, 0.0F, 0.0F), a.getSurfaceNormal());
		assertEquals(new Vector3F(1.0F, 0.0F, 0.0F), b.getSurfaceNormal());
	}
	
	@Test
	public void testHashCode() {
		final SurfaceSample3F a = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F b = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final SurfaceSample3F surfaceSample = new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		
		assertEquals("new SurfaceSample3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F)", surfaceSample.toString());
	}
	
	@Test
	public void testTransformSurfaceSample3FMatrix44F() {
		final SurfaceSample3F a = new SurfaceSample3F(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F b = SurfaceSample3F.transform(a, Matrix44F.identity());
		final SurfaceSample3F c = SurfaceSample3F.transform(a, Matrix44F.rotateY(180.0F));
		
		assertEquals(new Point3F(0.0F, 0.0F, 0.0F), b.getPoint());
		assertEquals(new Vector3F(0.0F, 0.0F, 0.0F), b.getPointError());
		assertEquals(new Vector3F(1.0F, 0.0F, 0.0F), b.getSurfaceNormal());
		assertEquals(1.0F, b.getProbabilityDensityFunctionValue());
		
		assertEquals(new Point3F(0.0F, 0.0F, 0.0F), c.getPoint());
		assertEquals(new Vector3F(0.0F, 0.0F, 0.0F), c.getPointError());
		assertEquals(new Vector3F(-1.0F, 0.0F, 0.00000008742278F), c.getSurfaceNormal());
		assertEquals(1.0F, c.getProbabilityDensityFunctionValue());
		
		assertThrows(NullPointerException.class, () -> SurfaceSample3F.transform(a, null));
		assertThrows(NullPointerException.class, () -> SurfaceSample3F.transform(null, Matrix44F.identity()));
	}
	
	@Test
	public void testTransformSurfaceSample3FMatrix44FMatrix44F() {
		final SurfaceSample3F a = new SurfaceSample3F(new Point3F(0.0F, 0.0F, 0.0F), new Vector3F(0.0F, 0.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F), 1.0F);
		final SurfaceSample3F b = SurfaceSample3F.transform(a, Matrix44F.identity(), Matrix44F.identity());
		final SurfaceSample3F c = SurfaceSample3F.transform(a, Matrix44F.rotateY(180.0F), Matrix44F.inverse(Matrix44F.rotateY(180.0F)));
		
		assertEquals(new Point3F(0.0F, 0.0F, 0.0F), b.getPoint());
		assertEquals(new Vector3F(0.0F, 0.0F, 0.0F), b.getPointError());
		assertEquals(new Vector3F(1.0F, 0.0F, 0.0F), b.getSurfaceNormal());
		assertEquals(1.0F, b.getProbabilityDensityFunctionValue());
		
		assertEquals(new Point3F(0.0F, 0.0F, 0.0F), c.getPoint());
		assertEquals(new Vector3F(0.0F, 0.0F, 0.0F), c.getPointError());
		assertEquals(new Vector3F(-1.0F, 0.0F, 0.00000008742278F), c.getSurfaceNormal());
		assertEquals(1.0F, c.getProbabilityDensityFunctionValue());
		
		assertThrows(NullPointerException.class, () -> SurfaceSample3F.transform(a, Matrix44F.identity(), null));
		assertThrows(NullPointerException.class, () -> SurfaceSample3F.transform(a, null, Matrix44F.identity()));
		assertThrows(NullPointerException.class, () -> SurfaceSample3F.transform(null, Matrix44F.identity(), Matrix44F.identity()));
	}
}