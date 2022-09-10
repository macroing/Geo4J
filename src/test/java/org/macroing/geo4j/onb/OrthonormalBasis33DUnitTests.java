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
package org.macroing.geo4j.onb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.macroing.geo4j.matrix.Matrix44D;
import org.macroing.geo4j.vector.Vector3D;

@SuppressWarnings("static-method")
public final class OrthonormalBasis33DUnitTests {
	public OrthonormalBasis33DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D();
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
	}
	
	@Test
	public void testConstructorVector3D() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D));
		
		assertEquals(new Vector3D(+1.0D, 0.0D, -0.0D), o.u);
		assertEquals(new Vector3D(-0.0D, 1.0D, +0.0D), o.v);
		assertEquals(new Vector3D(+0.0D, 0.0D, +1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(null));
	}
	
	@Test
	public void testConstructorVector3DVector3D() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D));
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), null));
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(null, new Vector3D(0.0D, 1.0D, 0.0D)));
	}
	
	@Test
	public void testConstructorVector3DVector3DVector3D() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(new Vector3D(1.0D, 0.0D, 0.0D), o.u);
		assertEquals(new Vector3D(0.0D, 1.0D, 0.0D), o.v);
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), o.w);
		
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), null));
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), null, new Vector3D(1.0D, 0.0D, 0.0D)));
		assertThrows(NullPointerException.class, () -> new OrthonormalBasis33D(null, new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D)));
	}
	
	@Test
	public void testEqualsObject() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D c = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(2.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D d = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 2.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D e = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 2.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D f = null;
		
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
	public void testEqualsOrthonormalBasis33D() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D c = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(2.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D d = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 2.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D e = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 2.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D f = null;
		
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
	public void testHashCode() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToMatrix() {
		final Matrix44D m = new OrthonormalBasis33D(Vector3D.z(), Vector3D.y(), Vector3D.x()).toMatrix();
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element14);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element24);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		assertEquals(0.0D, m.element34);
		assertEquals(0.0D, m.element41);
		assertEquals(0.0D, m.element42);
		assertEquals(0.0D, m.element43);
		assertEquals(1.0D, m.element44);
	}
	
	@Test
	public void testToString() {
		final OrthonormalBasis33D o = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		
		assertEquals("new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D))", o.toString());
	}
	
	@Test
	public void testTransformMatrix44D() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = a.transform(Matrix44D.rotateX(+180.0D));
		final OrthonormalBasis33D c = b.transform(Matrix44D.rotateX(-180.0D));
		final OrthonormalBasis33D d = a.transform(Matrix44D.rotateY(+180.0D));
		final OrthonormalBasis33D e = d.transform(Matrix44D.rotateY(-180.0D));
		final OrthonormalBasis33D f = a.transform(Matrix44D.rotateZ(+180.0D));
		final OrthonormalBasis33D g = f.transform(Matrix44D.rotateZ(-180.0D));
		
		assertEquals(a, c);
		assertEquals(a, e);
		assertEquals(a, g);
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, -0.00000000000000012246467991473532D, -1.0D), new Vector3D(0.0D, -1.0D, 0.00000000000000012246467991473532D), new Vector3D(1.0D, 0.0D, 0.0D)), b);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.00000000000000012246467991473532D, 0.0D, -1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(-1.0D, 0.0D, -0.00000000000000012246467991473532D)), d);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(-0.00000000000000012246467991473532D, -1.0D, 0.0D), new Vector3D(-1.0D, 0.00000000000000012246467991473532D, 0.0D)), f);
		
		assertThrows(NullPointerException.class, () -> a.transform((Matrix44D)(null)));
	}
	
	@Test
	public void testTransformTranspose() {
		final OrthonormalBasis33D a = new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D));
		final OrthonormalBasis33D b = a.transformTranspose(Matrix44D.transpose(Matrix44D.rotateX(+180.0D)));
		final OrthonormalBasis33D c = b.transformTranspose(Matrix44D.transpose(Matrix44D.rotateX(-180.0D)));
		final OrthonormalBasis33D d = a.transformTranspose(Matrix44D.transpose(Matrix44D.rotateY(+180.0D)));
		final OrthonormalBasis33D e = d.transformTranspose(Matrix44D.transpose(Matrix44D.rotateY(-180.0D)));
		final OrthonormalBasis33D f = a.transformTranspose(Matrix44D.transpose(Matrix44D.rotateZ(+180.0D)));
		final OrthonormalBasis33D g = f.transformTranspose(Matrix44D.transpose(Matrix44D.rotateZ(-180.0D)));
		
		assertEquals(a, c);
		assertEquals(a, e);
		assertEquals(a, g);
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, -0.00000000000000012246467991473532D, -1.0D), new Vector3D(0.0D, -1.0D, 0.00000000000000012246467991473532D), new Vector3D(1.0D, 0.0D, 0.0D)), b);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.00000000000000012246467991473532D, 0.0D, -1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(-1.0D, 0.0D, -0.00000000000000012246467991473532D)), d);
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(-0.00000000000000012246467991473532D, -1.0D, 0.0D), new Vector3D(-1.0D, 0.00000000000000012246467991473532D, 0.0D)), f);
		
		assertThrows(NullPointerException.class, () -> a.transformTranspose((Matrix44D)(null)));
	}
}