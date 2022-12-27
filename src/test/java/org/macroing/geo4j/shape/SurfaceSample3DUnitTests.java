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
package org.macroing.geo4j.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.matrix.Matrix44D;

@SuppressWarnings("static-method")
public final class SurfaceSample3DUnitTests {
	public SurfaceSample3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		final Optional<SurfaceSample3D> optionalSurfaceSample = SurfaceSample3D.EMPTY;
		
		assertTrue(optionalSurfaceSample.isEmpty());
	}
	
	@Test
	public void testConstructor() {
		final SurfaceSample3D surfaceSample = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals(new Point3D(1.0D, 2.0D, 3.0D), surfaceSample.getPoint());
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), surfaceSample.getSurfaceNormal());
		assertEquals(1.0D, surfaceSample.getProbabilityDensityFunctionValue());
		
		assertThrows(NullPointerException.class, () -> new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), null, 1.0D));
		assertThrows(NullPointerException.class, () -> new SurfaceSample3D(null, new Vector3D(1.0D, 0.0D, 0.0D), 1.0D));
	}
	
	@Test
	public void testEquals() {
		final SurfaceSample3D a = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D b = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D c = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 2.0D);
		final SurfaceSample3D d = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(0.0D, 1.0D, 0.0D), 1.0D);
		final SurfaceSample3D e = new SurfaceSample3D(new Point3D(2.0D, 3.0D, 4.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D f = null;
		
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
	public void testGetPoint() {
		final SurfaceSample3D surfaceSample = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals(new Point3D(1.0D, 2.0D, 3.0D), surfaceSample.getPoint());
	}
	
	@Test
	public void testGetProbabilityDensityFunctionValue() {
		final SurfaceSample3D surfaceSample = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals(1.0D, surfaceSample.getProbabilityDensityFunctionValue());
	}
	
	@Test
	public void testGetSurfaceNormal() {
		final SurfaceSample3D a = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D b = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(2.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), a.getSurfaceNormal());
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), b.getSurfaceNormal());
	}
	
	@Test
	public void testHashCode() {
		final SurfaceSample3D a = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D b = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final SurfaceSample3D surfaceSample = new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		
		assertEquals("new SurfaceSample3D(new Point3D(1.0D, 2.0D, 3.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D)", surfaceSample.toString());
	}
	
	@Test
	public void testTransformSurfaceSample3DMatrix44D() {
		final SurfaceSample3D a = new SurfaceSample3D(new Point3D(0.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D b = SurfaceSample3D.transform(a, Matrix44D.identity());
		final SurfaceSample3D c = SurfaceSample3D.transform(a, Matrix44D.rotateY(180.0D));
		
		assertEquals(new Point3D(0.0D, 0.0D, 0.0D), b.getPoint());
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), b.getSurfaceNormal());
		assertEquals(1.0D, b.getProbabilityDensityFunctionValue());
		
		assertEquals(new Point3D(0.0D, 0.0D, 0.0D), c.getPoint());
		assertEquals(new Vector3D(-1.0D, 0.0D, -0.00000000000000012246467991473532D), c.getSurfaceNormal());
		assertEquals(1.0D, c.getProbabilityDensityFunctionValue());
		
		assertThrows(NullPointerException.class, () -> SurfaceSample3D.transform(a, null));
		assertThrows(NullPointerException.class, () -> SurfaceSample3D.transform(null, Matrix44D.identity()));
	}
	
	@Test
	public void testTransformSurfaceSample3DMatrix44DMatrix44D() {
		final SurfaceSample3D a = new SurfaceSample3D(new Point3D(0.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D), 1.0D);
		final SurfaceSample3D b = SurfaceSample3D.transform(a, Matrix44D.identity(), Matrix44D.identity());
		final SurfaceSample3D c = SurfaceSample3D.transform(a, Matrix44D.rotateY(180.0D), Matrix44D.inverse(Matrix44D.rotateY(180.0D)));
		
		assertEquals(new Point3D(0.0D, 0.0D, 0.0D), b.getPoint());
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), b.getSurfaceNormal());
		assertEquals(1.0D, b.getProbabilityDensityFunctionValue());
		
		assertEquals(new Point3D(0.0D, 0.0D, 0.0D), c.getPoint());
		assertEquals(new Vector3D(-1.0D, 0.0D, -0.00000000000000012246467991473532D), c.getSurfaceNormal());
		assertEquals(1.0D, c.getProbabilityDensityFunctionValue());
		
		assertThrows(NullPointerException.class, () -> SurfaceSample3D.transform(a, Matrix44D.identity(), null));
		assertThrows(NullPointerException.class, () -> SurfaceSample3D.transform(a, null, Matrix44D.identity()));
		assertThrows(NullPointerException.class, () -> SurfaceSample3D.transform(null, Matrix44D.identity(), Matrix44D.identity()));
	}
}