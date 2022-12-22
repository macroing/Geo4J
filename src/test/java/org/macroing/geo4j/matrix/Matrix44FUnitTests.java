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
package org.macroing.geo4j.matrix;

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
import org.macroing.geo4j.common.AngleF;
import org.macroing.geo4j.common.Point3F;
import org.macroing.geo4j.common.Point4F;
import org.macroing.geo4j.common.Vector3F;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.ray.Ray3F;
import org.macroing.java.lang.Floats;

@SuppressWarnings("static-method")
public final class Matrix44FUnitTests {
	public Matrix44FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		assertEquals( 0, Matrix44F.ARRAY_OFFSET_ELEMENT_1_1);
		assertEquals( 1, Matrix44F.ARRAY_OFFSET_ELEMENT_1_2);
		assertEquals( 2, Matrix44F.ARRAY_OFFSET_ELEMENT_1_3);
		assertEquals( 3, Matrix44F.ARRAY_OFFSET_ELEMENT_1_4);
		assertEquals( 4, Matrix44F.ARRAY_OFFSET_ELEMENT_2_1);
		assertEquals( 5, Matrix44F.ARRAY_OFFSET_ELEMENT_2_2);
		assertEquals( 6, Matrix44F.ARRAY_OFFSET_ELEMENT_2_3);
		assertEquals( 7, Matrix44F.ARRAY_OFFSET_ELEMENT_2_4);
		assertEquals( 8, Matrix44F.ARRAY_OFFSET_ELEMENT_3_1);
		assertEquals( 9, Matrix44F.ARRAY_OFFSET_ELEMENT_3_2);
		assertEquals(10, Matrix44F.ARRAY_OFFSET_ELEMENT_3_3);
		assertEquals(11, Matrix44F.ARRAY_OFFSET_ELEMENT_3_4);
		assertEquals(12, Matrix44F.ARRAY_OFFSET_ELEMENT_4_1);
		assertEquals(13, Matrix44F.ARRAY_OFFSET_ELEMENT_4_2);
		assertEquals(14, Matrix44F.ARRAY_OFFSET_ELEMENT_4_3);
		assertEquals(15, Matrix44F.ARRAY_OFFSET_ELEMENT_4_4);
		assertEquals(16, Matrix44F.ARRAY_SIZE);
	}
	
	@Test
	public void testConstructor() {
		final Matrix44F m = new Matrix44F();
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
	}
	
	@Test
	public void testConstructorFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloatFloat() {
		final Matrix44F m = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		assertEquals( 1.0F, m.element11);
		assertEquals( 2.0F, m.element12);
		assertEquals( 3.0F, m.element13);
		assertEquals( 4.0F, m.element14);
		assertEquals( 5.0F, m.element21);
		assertEquals( 6.0F, m.element22);
		assertEquals( 7.0F, m.element23);
		assertEquals( 8.0F, m.element24);
		assertEquals( 9.0F, m.element31);
		assertEquals(10.0F, m.element32);
		assertEquals(11.0F, m.element33);
		assertEquals(12.0F, m.element34);
		assertEquals(13.0F, m.element41);
		assertEquals(14.0F, m.element42);
		assertEquals(15.0F, m.element43);
		assertEquals(16.0F, m.element44);
	}
	
	@Test
	public void testDeterminant() {
		final Matrix44F m = Matrix44F.identity();
		
		assertEquals(1.0F, m.determinant());
	}
	
	@Test
	public void testEqualsMatrix44F() {
		final Matrix44F a = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F b = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F c = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 16.0F);
		final Matrix44F d = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 16.0F, 15.0F);
		final Matrix44F e = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 16.0F, 14.0F, 15.0F);
		final Matrix44F f = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 16.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F g = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 16.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F h = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 16.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F i = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F, 16.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F j = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F, 16.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F k = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F, 16.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F l = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F, 16.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F m = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F, 16.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F n = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F, 16.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F o = new Matrix44F( 0.0F,  1.0F,  2.0F, 16.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F p = new Matrix44F( 0.0F,  1.0F, 16.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F q = new Matrix44F( 0.0F, 16.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F r = new Matrix44F(16.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F s = null;
		
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
		assertFalse(f.equals(a));
		assertFalse(a.equals(g));
		assertFalse(g.equals(a));
		assertFalse(a.equals(h));
		assertFalse(h.equals(a));
		assertFalse(a.equals(i));
		assertFalse(i.equals(a));
		assertFalse(a.equals(j));
		assertFalse(j.equals(a));
		assertFalse(a.equals(k));
		assertFalse(k.equals(a));
		assertFalse(a.equals(l));
		assertFalse(l.equals(a));
		assertFalse(a.equals(m));
		assertFalse(m.equals(a));
		assertFalse(a.equals(n));
		assertFalse(n.equals(a));
		assertFalse(a.equals(o));
		assertFalse(o.equals(a));
		assertFalse(a.equals(p));
		assertFalse(p.equals(a));
		assertFalse(a.equals(q));
		assertFalse(q.equals(a));
		assertFalse(a.equals(r));
		assertFalse(r.equals(a));
		assertFalse(a.equals(s));
	}
	
	@Test
	public void testEqualsObject() {
		final Matrix44F a = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F b = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F c = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 16.0F);
		final Matrix44F d = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 16.0F, 15.0F);
		final Matrix44F e = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 16.0F, 14.0F, 15.0F);
		final Matrix44F f = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 16.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F g = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 16.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F h = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 16.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F i = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F, 16.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F j = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F, 16.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F k = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F, 16.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F l = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F, 16.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F m = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F,  4.0F, 16.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F n = new Matrix44F( 0.0F,  1.0F,  2.0F,  3.0F, 16.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F o = new Matrix44F( 0.0F,  1.0F,  2.0F, 16.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F p = new Matrix44F( 0.0F,  1.0F, 16.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F q = new Matrix44F( 0.0F, 16.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F r = new Matrix44F(16.0F,  1.0F,  2.0F,  3.0F,  4.0F,  5.0F,  6.0F,  7.0F,  8.0F,  9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F);
		final Matrix44F s = null;
		
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
		assertNotEquals(a, h);
		assertNotEquals(h, a);
		assertNotEquals(a, i);
		assertNotEquals(i, a);
		assertNotEquals(a, j);
		assertNotEquals(j, a);
		assertNotEquals(a, k);
		assertNotEquals(k, a);
		assertNotEquals(a, l);
		assertNotEquals(l, a);
		assertNotEquals(a, m);
		assertNotEquals(m, a);
		assertNotEquals(a, n);
		assertNotEquals(n, a);
		assertNotEquals(a, o);
		assertNotEquals(o, a);
		assertNotEquals(a, p);
		assertNotEquals(p, a);
		assertNotEquals(a, q);
		assertNotEquals(q, a);
		assertNotEquals(a, r);
		assertNotEquals(r, a);
		assertNotEquals(a, s);
		assertNotEquals(s, a);
	}
	
	@Test
	public void testGetElementInt() {
		final Matrix44F matrix = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		assertEquals( 1.0F, matrix.getElement( 0));
		assertEquals( 2.0F, matrix.getElement( 1));
		assertEquals( 3.0F, matrix.getElement( 2));
		assertEquals( 4.0F, matrix.getElement( 3));
		assertEquals( 5.0F, matrix.getElement( 4));
		assertEquals( 6.0F, matrix.getElement( 5));
		assertEquals( 7.0F, matrix.getElement( 6));
		assertEquals( 8.0F, matrix.getElement( 7));
		assertEquals( 9.0F, matrix.getElement( 8));
		assertEquals(10.0F, matrix.getElement( 9));
		assertEquals(11.0F, matrix.getElement(10));
		assertEquals(12.0F, matrix.getElement(11));
		assertEquals(13.0F, matrix.getElement(12));
		assertEquals(14.0F, matrix.getElement(13));
		assertEquals(15.0F, matrix.getElement(14));
		assertEquals(16.0F, matrix.getElement(15));
		
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(- 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(+16));
	}
	
	@Test
	public void testGetElementIntInt() {
		final Matrix44F matrix = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		assertEquals( 1.0F, matrix.getElement(1, 1));
		assertEquals( 2.0F, matrix.getElement(1, 2));
		assertEquals( 3.0F, matrix.getElement(1, 3));
		assertEquals( 4.0F, matrix.getElement(1, 4));
		assertEquals( 5.0F, matrix.getElement(2, 1));
		assertEquals( 6.0F, matrix.getElement(2, 2));
		assertEquals( 7.0F, matrix.getElement(2, 3));
		assertEquals( 8.0F, matrix.getElement(2, 4));
		assertEquals( 9.0F, matrix.getElement(3, 1));
		assertEquals(10.0F, matrix.getElement(3, 2));
		assertEquals(11.0F, matrix.getElement(3, 3));
		assertEquals(12.0F, matrix.getElement(3, 4));
		assertEquals(13.0F, matrix.getElement(4, 1));
		assertEquals(14.0F, matrix.getElement(4, 2));
		assertEquals(15.0F, matrix.getElement(4, 3));
		assertEquals(16.0F, matrix.getElement(4, 4));
		
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(0, 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(1, 0));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(5, 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(1, 5));
	}
	
	@Test
	public void testHashCode() {
		final Matrix44F a = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		final Matrix44F b = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIdentity() {
		final Matrix44F m = Matrix44F.identity();
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
	}
	
	@Test
	public void testInverse() {
		final Matrix44F a = new Matrix44F(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F);
		final Matrix44F b = Matrix44F.inverse(a);
		final Matrix44F c = Matrix44F.inverse(b);
		final Matrix44F d = Matrix44F.multiply(a, b);
		final Matrix44F e = Matrix44F.identity();
		
		assertEquals(a, c);
		assertEquals(d, e);
		
		assertThrows(IllegalArgumentException.class, () -> Matrix44F.inverse(new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F)));
		assertThrows(NullPointerException.class, () -> Matrix44F.inverse(null));
	}
	
	@Test
	public void testIsInvertible() {
		final Matrix44F a = new Matrix44F(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F,  0.0F,  1.0F,  0.0F,  0.0F,  0.0F,  0.0F,  1.0F);
		final Matrix44F b = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		assertTrue(a.isInvertible());
		
		assertFalse(b.isInvertible());
	}
	
	@Test
	public void testLookAt() {
		final Matrix44F matrix = Matrix44F.lookAt(new Point3F(1.0F, 1.0F, 1.0F), new Point3F(1.0F, 1.0F, 2.0F), Vector3F.y());
		
		assertEquals(1.0F, matrix.element11);
		assertEquals(0.0F, matrix.element12);
		assertEquals(0.0F, matrix.element13);
		assertEquals(1.0F, matrix.element14);
		assertEquals(0.0F, matrix.element21);
		assertEquals(1.0F, matrix.element22);
		assertEquals(0.0F, matrix.element23);
		assertEquals(1.0F, matrix.element24);
		assertEquals(0.0F, matrix.element31);
		assertEquals(0.0F, matrix.element32);
		assertEquals(1.0F, matrix.element33);
		assertEquals(1.0F, matrix.element34);
		assertEquals(0.0F, matrix.element41);
		assertEquals(0.0F, matrix.element42);
		assertEquals(0.0F, matrix.element43);
		assertEquals(1.0F, matrix.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.lookAt(new Point3F(), new Point3F(), null));
		assertThrows(NullPointerException.class, () -> Matrix44F.lookAt(new Point3F(), null, new Vector3F()));
		assertThrows(NullPointerException.class, () -> Matrix44F.lookAt(null, new Point3F(), new Vector3F()));
	}
	
	@Test
	public void testMultiply() {
		final Matrix44F a = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		final Matrix44F b = Matrix44F.identity();
		final Matrix44F c = Matrix44F.multiply(a, b);
		
		assertEquals(a, c);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.multiply(a, null));
		assertThrows(NullPointerException.class, () -> Matrix44F.multiply(null, b));
	}
	
	@Test
	public void testPerspective() {
		final float fieldOfView = (float)(Math.toRadians(90.0F));
		final float aspectRatio = 1.0F;
		final float zNear = 0.0F;
		final float zFar = 100.0F;
		
		final Matrix44F m = Matrix44F.perspective(fieldOfView, aspectRatio, zNear, zFar);
		
		final float a = 1.0F / ((float)(Math.tan(fieldOfView / 2.0F)) * aspectRatio);
		final float b = 1.0F / ((float)(Math.tan(fieldOfView / 2.0F)));
		final float c = zFar / (zFar - zNear);
		final float d = -zFar * zNear / (zFar - zNear);
		
		assertEquals(   a, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(   b, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(   c, m.element33);
		assertEquals(   d, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(1.0F, m.element43);
		assertEquals(0.0F, m.element44);
	}
	
	@Test
	public void testRead() throws IOException {
		final Matrix44F a = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeFloat(a.element11);
		dataOutput.writeFloat(a.element12);
		dataOutput.writeFloat(a.element13);
		dataOutput.writeFloat(a.element14);
		dataOutput.writeFloat(a.element21);
		dataOutput.writeFloat(a.element22);
		dataOutput.writeFloat(a.element23);
		dataOutput.writeFloat(a.element24);
		dataOutput.writeFloat(a.element31);
		dataOutput.writeFloat(a.element32);
		dataOutput.writeFloat(a.element33);
		dataOutput.writeFloat(a.element34);
		dataOutput.writeFloat(a.element41);
		dataOutput.writeFloat(a.element42);
		dataOutput.writeFloat(a.element43);
		dataOutput.writeFloat(a.element44);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Matrix44F b = Matrix44F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.read(null));
		assertThrows(UncheckedIOException.class, () -> Matrix44F.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testRotateAngleFFloatFloatFloat() {
		final Matrix44F a = Matrix44F.rotate(AngleF.degrees( 45.0F), 1.0F, 0.0F, 0.0F);
		final Matrix44F b = Matrix44F.rotate(AngleF.degrees(180.0F), 0.0F, 1.0F, 0.0F);
		final Matrix44F c = Matrix44F.rotate(AngleF.degrees(270.0F), 0.0F, 0.0F, 1.0F);
		
		final Matrix44F d = Matrix44F.rotateX(AngleF.degrees( 45.0F));
		final Matrix44F e = Matrix44F.rotateY(AngleF.degrees(180.0F));
		final Matrix44F f = Matrix44F.rotateZ(AngleF.degrees(270.0F));
		
		assertEquals(d, a);
		assertEquals(e, b);
		assertEquals(f, c);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate(null, 1.0F, 0.0F, 0.0F));
	}
	
	@Test
	public void testRotateAngleFVector3F() {
		final Matrix44F a = Matrix44F.rotate(AngleF.degrees( 45.0F), Vector3F.x());
		final Matrix44F b = Matrix44F.rotate(AngleF.degrees(180.0F), Vector3F.y());
		final Matrix44F c = Matrix44F.rotate(AngleF.degrees(270.0F), Vector3F.z());
		
		final Matrix44F d = Matrix44F.rotateX(AngleF.degrees( 45.0F));
		final Matrix44F e = Matrix44F.rotateY(AngleF.degrees(180.0F));
		final Matrix44F f = Matrix44F.rotateZ(AngleF.degrees(270.0F));
		
		assertEquals(d, a);
		assertEquals(e, b);
		assertEquals(f, c);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate(AngleF.degrees(0.0F), null));
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate((AngleF)(null), Vector3F.x()));
	}
	
	@Test
	public void testRotateVector3FVector3F() {
		final Matrix44F m = Matrix44F.rotate(new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(0.0F, 1.0F, 0.0F));
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate(new Vector3F(0.0F, 0.0F, 1.0F), null));
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate((Vector3F)(null), new Vector3F(0.0F, 1.0F, 0.0F)));
	}
	
	@Test
	public void testRotateVector3FVector3FVector3F() {
		final Matrix44F m = Matrix44F.rotate(new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F));
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate(new Vector3F(0.0F, 0.0F, 1.0F), new Vector3F(0.0F, 1.0F, 0.0F), null));
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate(new Vector3F(0.0F, 0.0F, 1.0F), null, new Vector3F(1.0F, 0.0F, 0.0F)));
		assertThrows(NullPointerException.class, () -> Matrix44F.rotate(null, new Vector3F(0.0F, 1.0F, 0.0F), new Vector3F(1.0F, 0.0F, 0.0F)));
	}
	
	@Test
	public void testRotateXAngleF() {
		final float angleDegrees = 90.0F;
		final float angleRadians = (float)(Math.toRadians(angleDegrees));
		final float angleRadiansCos = (float)(Math.cos(angleRadians));
		final float angleRadiansSin = (float)(Math.sin(angleRadians));
		
		final Matrix44F matrix = Matrix44F.rotateX(AngleF.degrees(angleDegrees));
		
		assertEquals(1.0F,             matrix.element11);
		assertEquals(0.0F,             matrix.element12);
		assertEquals(0.0F,             matrix.element13);
		assertEquals(0.0F,             matrix.element14);
		assertEquals(0.0F,             matrix.element21);
		assertEquals(+angleRadiansCos, matrix.element22);
		assertEquals(-angleRadiansSin, matrix.element23);
		assertEquals(0.0F,             matrix.element24);
		assertEquals(0.0F,             matrix.element31);
		assertEquals(+angleRadiansSin, matrix.element32);
		assertEquals(+angleRadiansCos, matrix.element33);
		assertEquals(0.0F,             matrix.element34);
		assertEquals(0.0F,             matrix.element41);
		assertEquals(0.0F,             matrix.element42);
		assertEquals(0.0F,             matrix.element43);
		assertEquals(1.0F,             matrix.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotateX(null));
	}
	
	@Test
	public void testRotateXFloat() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix44F m = Matrix44F.rotateX(angleDegrees);
		
		assertEquals(1.0F,             m.element11);
		assertEquals(0.0F,             m.element12);
		assertEquals(0.0F,             m.element13);
		assertEquals(0.0F,             m.element14);
		assertEquals(0.0F,             m.element21);
		assertEquals(+angleRadiansCos, m.element22);
		assertEquals(-angleRadiansSin, m.element23);
		assertEquals(0.0F,             m.element24);
		assertEquals(0.0F,             m.element31);
		assertEquals(+angleRadiansSin, m.element32);
		assertEquals(+angleRadiansCos, m.element33);
		assertEquals(0.0F,             m.element34);
		assertEquals(0.0F,             m.element41);
		assertEquals(0.0F,             m.element42);
		assertEquals(0.0F,             m.element43);
		assertEquals(1.0F,             m.element44);
	}
	
	@Test
	public void testRotateXFloatBoolean() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix44F a = Matrix44F.rotateX(angleDegrees, false);
		final Matrix44F b = Matrix44F.rotateX(angleRadians, true);
		
		assertEquals(1.0F,             a.element11);
		assertEquals(0.0F,             a.element12);
		assertEquals(0.0F,             a.element13);
		assertEquals(0.0F,             a.element14);
		assertEquals(0.0F,             a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(-angleRadiansSin, a.element23);
		assertEquals(0.0F,             a.element24);
		assertEquals(0.0F,             a.element31);
		assertEquals(+angleRadiansSin, a.element32);
		assertEquals(+angleRadiansCos, a.element33);
		assertEquals(0.0F,             a.element34);
		assertEquals(0.0F,             a.element41);
		assertEquals(0.0F,             a.element42);
		assertEquals(0.0F,             a.element43);
		assertEquals(1.0F,             a.element44);
		
		assertEquals(1.0F,             b.element11);
		assertEquals(0.0F,             b.element12);
		assertEquals(0.0F,             b.element13);
		assertEquals(0.0F,             b.element14);
		assertEquals(0.0F,             b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(-angleRadiansSin, b.element23);
		assertEquals(0.0F,             b.element24);
		assertEquals(0.0F,             b.element31);
		assertEquals(+angleRadiansSin, b.element32);
		assertEquals(+angleRadiansCos, b.element33);
		assertEquals(0.0F,             b.element34);
		assertEquals(0.0F,             b.element41);
		assertEquals(0.0F,             b.element42);
		assertEquals(0.0F,             b.element43);
		assertEquals(1.0F,             b.element44);
	}
	
	@Test
	public void testRotateYAngleF() {
		final float angleDegrees = 90.0F;
		final float angleRadians = (float)(Math.toRadians(angleDegrees));
		final float angleRadiansCos = (float)(Math.cos(angleRadians));
		final float angleRadiansSin = (float)(Math.sin(angleRadians));
		
		final Matrix44F matrix = Matrix44F.rotateY(AngleF.degrees(angleDegrees));
		
		assertEquals(+angleRadiansCos, matrix.element11);
		assertEquals(0.0F,             matrix.element12);
		assertEquals(+angleRadiansSin, matrix.element13);
		assertEquals(0.0F,             matrix.element14);
		assertEquals(0.0F,             matrix.element21);
		assertEquals(1.0F,             matrix.element22);
		assertEquals(0.0F,             matrix.element23);
		assertEquals(0.0F,             matrix.element24);
		assertEquals(-angleRadiansSin, matrix.element31);
		assertEquals(0.0F,             matrix.element32);
		assertEquals(+angleRadiansCos, matrix.element33);
		assertEquals(0.0F,             matrix.element34);
		assertEquals(0.0F,             matrix.element41);
		assertEquals(0.0F,             matrix.element42);
		assertEquals(0.0F,             matrix.element43);
		assertEquals(1.0F,             matrix.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotateY(null));
	}
	
	@Test
	public void testRotateYFloat() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix44F m = Matrix44F.rotateY(angleDegrees);
		
		assertEquals(+angleRadiansCos, m.element11);
		assertEquals(0.0F,             m.element12);
		assertEquals(+angleRadiansSin, m.element13);
		assertEquals(0.0F,             m.element14);
		assertEquals(0.0F,             m.element21);
		assertEquals(1.0F,             m.element22);
		assertEquals(0.0F,             m.element23);
		assertEquals(0.0F,             m.element24);
		assertEquals(-angleRadiansSin, m.element31);
		assertEquals(0.0F,             m.element32);
		assertEquals(+angleRadiansCos, m.element33);
		assertEquals(0.0F,             m.element34);
		assertEquals(0.0F,             m.element41);
		assertEquals(0.0F,             m.element42);
		assertEquals(0.0F,             m.element43);
		assertEquals(1.0F,             m.element44);
	}
	
	@Test
	public void testRotateYFloatBoolean() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix44F a = Matrix44F.rotateY(angleDegrees, false);
		final Matrix44F b = Matrix44F.rotateY(angleRadians, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(0.0F,             a.element12);
		assertEquals(+angleRadiansSin, a.element13);
		assertEquals(0.0F,             a.element14);
		assertEquals(0.0F,             a.element21);
		assertEquals(1.0F,             a.element22);
		assertEquals(0.0F,             a.element23);
		assertEquals(0.0F,             a.element24);
		assertEquals(-angleRadiansSin, a.element31);
		assertEquals(0.0F,             a.element32);
		assertEquals(+angleRadiansCos, a.element33);
		assertEquals(0.0F,             a.element34);
		assertEquals(0.0F,             a.element41);
		assertEquals(0.0F,             a.element42);
		assertEquals(0.0F,             a.element43);
		assertEquals(1.0F,             a.element44);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(0.0F,             b.element12);
		assertEquals(+angleRadiansSin, b.element13);
		assertEquals(0.0F,             b.element14);
		assertEquals(0.0F,             b.element21);
		assertEquals(1.0F,             b.element22);
		assertEquals(0.0F,             b.element23);
		assertEquals(0.0F,             b.element24);
		assertEquals(-angleRadiansSin, b.element31);
		assertEquals(0.0F,             b.element32);
		assertEquals(+angleRadiansCos, b.element33);
		assertEquals(0.0F,             b.element34);
		assertEquals(0.0F,             b.element41);
		assertEquals(0.0F,             b.element42);
		assertEquals(0.0F,             b.element43);
		assertEquals(1.0F,             b.element44);
	}
	
	@Test
	public void testRotateZAngleF() {
		final float angleDegrees = 90.0F;
		final float angleRadians = (float)(Math.toRadians(angleDegrees));
		final float angleRadiansCos = (float)(Math.cos(angleRadians));
		final float angleRadiansSin = (float)(Math.sin(angleRadians));
		
		final Matrix44F matrix = Matrix44F.rotateZ(AngleF.degrees(angleDegrees));
		
		assertEquals(+angleRadiansCos, matrix.element11);
		assertEquals(-angleRadiansSin, matrix.element12);
		assertEquals(0.0F,             matrix.element13);
		assertEquals(0.0F,             matrix.element14);
		assertEquals(+angleRadiansSin, matrix.element21);
		assertEquals(+angleRadiansCos, matrix.element22);
		assertEquals(0.0F,             matrix.element23);
		assertEquals(0.0F,             matrix.element24);
		assertEquals(0.0F,             matrix.element31);
		assertEquals(0.0F,             matrix.element32);
		assertEquals(1.0F,             matrix.element33);
		assertEquals(0.0F,             matrix.element34);
		assertEquals(0.0F,             matrix.element41);
		assertEquals(0.0F,             matrix.element42);
		assertEquals(0.0F,             matrix.element43);
		assertEquals(1.0F,             matrix.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.rotateZ(null));
	}
	
	@Test
	public void testRotateZFloat() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix44F m = Matrix44F.rotateZ(angleDegrees);
		
		assertEquals(+angleRadiansCos, m.element11);
		assertEquals(-angleRadiansSin, m.element12);
		assertEquals(0.0F,             m.element13);
		assertEquals(0.0F,             m.element14);
		assertEquals(+angleRadiansSin, m.element21);
		assertEquals(+angleRadiansCos, m.element22);
		assertEquals(0.0F,             m.element23);
		assertEquals(0.0F,             m.element24);
		assertEquals(0.0F,             m.element31);
		assertEquals(0.0F,             m.element32);
		assertEquals(1.0F,             m.element33);
		assertEquals(0.0F,             m.element34);
		assertEquals(0.0F,             m.element41);
		assertEquals(0.0F,             m.element42);
		assertEquals(0.0F,             m.element43);
		assertEquals(1.0F,             m.element44);
	}
	
	@Test
	public void testRotateZFloatBoolean() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix44F a = Matrix44F.rotateZ(angleDegrees, false);
		final Matrix44F b = Matrix44F.rotateZ(angleRadians, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(-angleRadiansSin, a.element12);
		assertEquals(0.0F,             a.element13);
		assertEquals(0.0F,             a.element14);
		assertEquals(+angleRadiansSin, a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(0.0F,             a.element23);
		assertEquals(0.0F,             a.element24);
		assertEquals(0.0F,             a.element31);
		assertEquals(0.0F,             a.element32);
		assertEquals(1.0F,             a.element33);
		assertEquals(0.0F,             a.element34);
		assertEquals(0.0F,             a.element41);
		assertEquals(0.0F,             a.element42);
		assertEquals(0.0F,             a.element43);
		assertEquals(1.0F,             a.element44);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(-angleRadiansSin, b.element12);
		assertEquals(0.0F,             b.element13);
		assertEquals(0.0F,             b.element14);
		assertEquals(+angleRadiansSin, b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(0.0F,             b.element23);
		assertEquals(0.0F,             b.element24);
		assertEquals(0.0F,             b.element31);
		assertEquals(0.0F,             b.element32);
		assertEquals(1.0F,             b.element33);
		assertEquals(0.0F,             b.element34);
		assertEquals(0.0F,             b.element41);
		assertEquals(0.0F,             b.element42);
		assertEquals(0.0F,             b.element43);
		assertEquals(1.0F,             b.element44);
	}
	
	@Test
	public void testScaleFloat() {
		final Matrix44F m = Matrix44F.scale(2.0F);
		
		assertEquals(2.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(2.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(2.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
	}
	
	@Test
	public void testScaleFloatFloatFloat() {
		final Matrix44F m = Matrix44F.scale(2.0F, 3.0F, 4.0F);
		
		assertEquals(2.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(3.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(4.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
	}
	
	@Test
	public void testScaleVector3F() {
		final Matrix44F m = Matrix44F.scale(new Vector3F(2.0F, 3.0F, 4.0F));
		
		assertEquals(2.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(3.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(4.0F, m.element33);
		assertEquals(0.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.scale(null));
	}
	
	@Test
	public void testScreenSpaceTransform() {
		final Matrix44F m = Matrix44F.screenSpaceTransform(1024.0F, 768.0F);
		
		assertEquals(+512.0F, m.element11);
		assertEquals(   0.0F, m.element12);
		assertEquals(   0.0F, m.element13);
		assertEquals(+511.5F, m.element14);
		assertEquals(   0.0F, m.element21);
		assertEquals(-384.0F, m.element22);
		assertEquals(   0.0F, m.element23);
		assertEquals(+383.5F, m.element24);
		assertEquals(   0.0F, m.element31);
		assertEquals(   0.0F, m.element32);
		assertEquals(   1.0F, m.element33);
		assertEquals(   0.0F, m.element34);
		assertEquals(   0.0F, m.element41);
		assertEquals(   0.0F, m.element42);
		assertEquals(   0.0F, m.element43);
		assertEquals(   1.0F, m.element44);
	}
	
	@Test
	public void testToArray() {
		final Matrix44F matrix = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		final float[] array = matrix.toArray();
		
		assertNotNull(array);
		
		assertEquals(16, array.length);
		
		assertEquals( 1.0F, array[ 0]);
		assertEquals( 2.0F, array[ 1]);
		assertEquals( 3.0F, array[ 2]);
		assertEquals( 4.0F, array[ 3]);
		assertEquals( 5.0F, array[ 4]);
		assertEquals( 6.0F, array[ 5]);
		assertEquals( 7.0F, array[ 6]);
		assertEquals( 8.0F, array[ 7]);
		assertEquals( 9.0F, array[ 8]);
		assertEquals(10.0F, array[ 9]);
		assertEquals(11.0F, array[10]);
		assertEquals(12.0F, array[11]);
		assertEquals(13.0F, array[12]);
		assertEquals(14.0F, array[13]);
		assertEquals(15.0F, array[14]);
		assertEquals(16.0F, array[15]);
	}
	
	@Test
	public void testToString() {
		final Matrix44F m = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		assertEquals("new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F)", m.toString());
	}
	
	@Test
	public void testTransformAndDividePoint3F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = Matrix44F.scale(1.0F, 2.0F, 3.0F).transformAndDivide(a);
		final Point3F c = Matrix44F.translate(1.0F, 2.0F, 3.0F).transformAndDivide(a);
		final Point3F d = new Matrix44F(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0F).transformAndDivide(a);
		final Point3F e = new Matrix44F(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F).transformAndDivide(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(9.0F, b.z);
		
		assertEquals(2.0F, c.x);
		assertEquals(4.0F, c.y);
		assertEquals(6.0F, c.z);
		
		assertEquals(0.5F, d.x);
		assertEquals(1.0F, d.y);
		assertEquals(1.5F, d.z);
		
		assertEquals(1.0F, e.x);
		assertEquals(2.0F, e.y);
		assertEquals(3.0F, e.z);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(1.0F, 2.0F, 3.0F).transformAndDivide((Point3F)(null)));
	}
	
	@Test
	public void testTransformAndDividePoint4F() {
		final Point4F a = new Point4F(1.0F, 2.0F, 3.0F, 1.0F);
		final Point4F b = Matrix44F.scale(1.0F, 2.0F, 3.0F).transformAndDivide(a);
		final Point4F c = Matrix44F.translate(1.0F, 2.0F, 3.0F).transformAndDivide(a);
		final Point4F d = new Point4F(1.0F, 2.0F, 3.0F, 2.0F);
		final Point4F e = Matrix44F.translate(1.0F, 2.0F, 3.0F).transformAndDivide(d);
		final Point4F f = new Point4F(1.0F, 2.0F, 3.0F, 0.0F);
		final Point4F g = Matrix44F.scale(1.0F, 2.0F, 3.0F).transformAndDivide(f);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(9.0F, b.z);
		assertEquals(1.0F, b.w);
		
		assertEquals(2.0F, c.x);
		assertEquals(4.0F, c.y);
		assertEquals(6.0F, c.z);
		assertEquals(1.0F, c.w);
		
		assertEquals(1.5F, e.x);
		assertEquals(3.0F, e.y);
		assertEquals(4.5F, e.z);
		assertEquals(2.0F, e.w);
		
		assertEquals(1.0F, g.x);
		assertEquals(4.0F, g.y);
		assertEquals(9.0F, g.z);
		assertEquals(0.0F, g.w);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(1.0F, 2.0F, 3.0F).transformAndDivide((Point4F)(null)));
	}
	
	@Test
	public void testTransformPoint3F() {
		final Point3F a = new Point3F(1.0F, 2.0F, 3.0F);
		final Point3F b = Matrix44F.scale(1.0F, 2.0F, 3.0F).transform(a);
		final Point3F c = Matrix44F.translate(1.0F, 2.0F, 3.0F).transform(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(9.0F, b.z);
		
		assertEquals(2.0F, c.x);
		assertEquals(4.0F, c.y);
		assertEquals(6.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(1.0F, 2.0F, 3.0F).transform((Point3F)(null)));
	}
	
	@Test
	public void testTransformPoint4F() {
		final Point4F a = new Point4F(1.0F, 2.0F, 3.0F, 1.0F);
		final Point4F b = Matrix44F.scale(1.0F, 2.0F, 3.0F).transform(a);
		final Point4F c = Matrix44F.translate(1.0F, 2.0F, 3.0F).transform(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(9.0F, b.z);
		assertEquals(1.0F, b.w);
		
		assertEquals(2.0F, c.x);
		assertEquals(4.0F, c.y);
		assertEquals(6.0F, c.z);
		assertEquals(1.0F, c.w);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(1.0F, 2.0F, 3.0F).transform((Point4F)(null)));
	}
	
	@Test
	public void testTransformRay3F() {
		final Ray3F a = new Ray3F(new Point3F(1.0F, 2.0F, 3.0F), new Vector3F(1.0F, 0.0F, 0.0F));
		final Ray3F b = Matrix44F.scale(1.0F, 2.0F, 3.0F).transform(a);
		final Ray3F c = Matrix44F.translate(1.0F, 2.0F, 3.0F).transform(a);
		
		assertEquals(1.0F, b.getOrigin().x);
		assertEquals(4.0F, b.getOrigin().y);
		assertEquals(9.0F, b.getOrigin().z);
		
		assertEquals(1.0F, b.getDirection().x);
		assertEquals(0.0F, b.getDirection().y);
		assertEquals(0.0F, b.getDirection().z);
		
		assertEquals(2.0F, c.getOrigin().x);
		assertEquals(4.0F, c.getOrigin().y);
		assertEquals(6.0F, c.getOrigin().z);
		
		assertEquals(1.0F, b.getDirection().x);
		assertEquals(0.0F, b.getDirection().y);
		assertEquals(0.0F, b.getDirection().z);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(1.0F, 2.0F, 3.0F).transform((Ray3F)(null)));
	}
	
	@Test
	public void testTransformT() {
		final Matrix44F mA = Matrix44F.translate(0.0F, 0.0F, 0.0F);
		final Matrix44F mB = Matrix44F.scale(0.0F, 0.0F, 2.0F);
		
		final Ray3F rOldSpaceA = new Ray3F(new Point3F(), Vector3F.z());
		final Ray3F rNewSpaceA = new Ray3F(new Point3F(), Vector3F.z());
		
		assertEquals(Floats.NaN,       mA.transformT(rOldSpaceA, rNewSpaceA, Floats.NaN));
		assertEquals(0.0F,              mA.transformT(rOldSpaceA, rNewSpaceA, 0.0F));
		assertEquals(Floats.MAX_VALUE, mA.transformT(rOldSpaceA, rNewSpaceA, Floats.MAX_VALUE));
		
		assertEquals(1.0F, mA.transformT(rOldSpaceA, rNewSpaceA, 1.0F));
		assertEquals(2.0F, mB.transformT(rOldSpaceA, rNewSpaceA, 1.0F));
		
		assertThrows(NullPointerException.class, () -> mA.transformT(rOldSpaceA, null,       1.0F));
		assertThrows(NullPointerException.class, () -> mA.transformT(null,       rNewSpaceA, 1.0F));
	}
	
	@Test
	public void testTransformTransposeVector3F() {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = Matrix44F.transpose(Matrix44F.scale(1.0F, 2.0F, 3.0F)).transformTranspose(a);
		final Vector3F c = Matrix44F.transpose(Matrix44F.translate(1.0F, 2.0F, 3.0F)).transformTranspose(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(9.0F, b.z);
		
		assertEquals(1.0F, c.x);
		assertEquals(2.0F, c.y);
		assertEquals(3.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.transpose(Matrix44F.translate(1.0F, 2.0F, 3.0F)).transformTranspose(null));
	}
	
	@Test
	public void testTransformVector3F() {
		final Vector3F a = new Vector3F(1.0F, 2.0F, 3.0F);
		final Vector3F b = Matrix44F.scale(1.0F, 2.0F, 3.0F).transform(a);
		final Vector3F c = Matrix44F.translate(1.0F, 2.0F, 3.0F).transform(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		assertEquals(9.0F, b.z);
		
		assertEquals(1.0F, c.x);
		assertEquals(2.0F, c.y);
		assertEquals(3.0F, c.z);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(1.0F, 2.0F, 3.0F).transform((Vector3F)(null)));
	}
	
	@Test
	public void testTranslateFloatFloatFloat() {
		final Matrix44F m = Matrix44F.translate(2.0F, 3.0F, 4.0F);
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(2.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(3.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		assertEquals(4.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
	}
	
	@Test
	public void testTranslatePoint3F() {
		final Matrix44F m = Matrix44F.translate(new Point3F(2.0F, 3.0F, 4.0F));
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(2.0F, m.element14);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(3.0F, m.element24);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		assertEquals(4.0F, m.element34);
		assertEquals(0.0F, m.element41);
		assertEquals(0.0F, m.element42);
		assertEquals(0.0F, m.element43);
		assertEquals(1.0F, m.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.translate(null));
	}
	
	@Test
	public void testTranspose() {
		final Matrix44F a = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		final Matrix44F b = Matrix44F.transpose(a);
		
		assertEquals( 1.0F, b.element11);
		assertEquals( 5.0F, b.element12);
		assertEquals( 9.0F, b.element13);
		assertEquals(13.0F, b.element14);
		assertEquals( 2.0F, b.element21);
		assertEquals( 6.0F, b.element22);
		assertEquals(10.0F, b.element23);
		assertEquals(14.0F, b.element24);
		assertEquals( 3.0F, b.element31);
		assertEquals( 7.0F, b.element32);
		assertEquals(11.0F, b.element33);
		assertEquals(15.0F, b.element34);
		assertEquals( 4.0F, b.element41);
		assertEquals( 8.0F, b.element42);
		assertEquals(12.0F, b.element43);
		assertEquals(16.0F, b.element44);
		
		assertThrows(NullPointerException.class, () -> Matrix44F.transpose(null));
	}
	
	@Test
	public void testWrite() {
		final Matrix44F a = new Matrix44F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F, 10.0F, 11.0F, 12.0F, 13.0F, 14.0F, 15.0F, 16.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Matrix44F b = Matrix44F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}