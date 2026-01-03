/**
 * Copyright 2022 - 2026 J&#246;rgen Lundgren
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

import org.macroing.geo4j.common.Point2F;
import org.macroing.geo4j.common.Vector2F;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.ray.Ray2F;
import org.macroing.java.lang.Floats;

@SuppressWarnings("static-method")
public final class Matrix33FUnitTests {
	public Matrix33FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		assertEquals(0, Matrix33F.ARRAY_OFFSET_ELEMENT_1_1);
		assertEquals(1, Matrix33F.ARRAY_OFFSET_ELEMENT_1_2);
		assertEquals(2, Matrix33F.ARRAY_OFFSET_ELEMENT_1_3);
		assertEquals(3, Matrix33F.ARRAY_OFFSET_ELEMENT_2_1);
		assertEquals(4, Matrix33F.ARRAY_OFFSET_ELEMENT_2_2);
		assertEquals(5, Matrix33F.ARRAY_OFFSET_ELEMENT_2_3);
		assertEquals(6, Matrix33F.ARRAY_OFFSET_ELEMENT_3_1);
		assertEquals(7, Matrix33F.ARRAY_OFFSET_ELEMENT_3_2);
		assertEquals(8, Matrix33F.ARRAY_OFFSET_ELEMENT_3_3);
		assertEquals(9, Matrix33F.ARRAY_SIZE);
	}
	
	@Test
	public void testConstructor() {
		final Matrix33F m = new Matrix33F();
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
	}
	
	@Test
	public void testConstructorFloatFloatFloatFloatFloatFloatFloatFloatFloat() {
		final Matrix33F m = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		assertEquals(1.0F, m.element11);
		assertEquals(2.0F, m.element12);
		assertEquals(3.0F, m.element13);
		assertEquals(4.0F, m.element21);
		assertEquals(5.0F, m.element22);
		assertEquals(6.0F, m.element23);
		assertEquals(7.0F, m.element31);
		assertEquals(8.0F, m.element32);
		assertEquals(9.0F, m.element33);
	}
	
	@Test
	public void testDeterminant() {
		final Matrix33F m = Matrix33F.identity();
		
		assertEquals(1.0F, m.determinant());
	}
	
	@Test
	public void testEqualsMatrix33F() {
		final Matrix33F a = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F b = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F c = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 9.0F);
		final Matrix33F d = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 9.0F, 8.0F);
		final Matrix33F e = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 9.0F, 7.0F, 8.0F);
		final Matrix33F f = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 9.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F g = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 9.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F h = new Matrix33F(0.0F, 1.0F, 2.0F, 9.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F i = new Matrix33F(0.0F, 1.0F, 9.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F j = new Matrix33F(0.0F, 9.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F k = new Matrix33F(9.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F l = null;
		
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
	}
	
	@Test
	public void testEqualsObject() {
		final Matrix33F a = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F b = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F c = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 9.0F);
		final Matrix33F d = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 9.0F, 8.0F);
		final Matrix33F e = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 9.0F, 7.0F, 8.0F);
		final Matrix33F f = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 4.0F, 9.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F g = new Matrix33F(0.0F, 1.0F, 2.0F, 3.0F, 9.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F h = new Matrix33F(0.0F, 1.0F, 2.0F, 9.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F i = new Matrix33F(0.0F, 1.0F, 9.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F j = new Matrix33F(0.0F, 9.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix33F k = new Matrix33F(9.0F, 1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F);
		final Matrix44F l = null;
		
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
	}
	
	@Test
	public void testGetElementInt() {
		final Matrix33F matrix = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		assertEquals( 1.0F, matrix.getElement( 0));
		assertEquals( 2.0F, matrix.getElement( 1));
		assertEquals( 3.0F, matrix.getElement( 2));
		assertEquals( 4.0F, matrix.getElement( 3));
		assertEquals( 5.0F, matrix.getElement( 4));
		assertEquals( 6.0F, matrix.getElement( 5));
		assertEquals( 7.0F, matrix.getElement( 6));
		assertEquals( 8.0F, matrix.getElement( 7));
		assertEquals( 9.0F, matrix.getElement( 8));
		
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(-1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(+9));
	}
	
	@Test
	public void testGetElementIntInt() {
		final Matrix33F matrix = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		assertEquals( 1.0F, matrix.getElement(1, 1));
		assertEquals( 2.0F, matrix.getElement(1, 2));
		assertEquals( 3.0F, matrix.getElement(1, 3));
		assertEquals( 4.0F, matrix.getElement(2, 1));
		assertEquals( 5.0F, matrix.getElement(2, 2));
		assertEquals( 6.0F, matrix.getElement(2, 3));
		assertEquals( 7.0F, matrix.getElement(3, 1));
		assertEquals( 8.0F, matrix.getElement(3, 2));
		assertEquals( 9.0F, matrix.getElement(3, 3));
		
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(0, 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(1, 0));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(4, 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(1, 4));
	}
	
	@Test
	public void testHashCode() {
		final Matrix33F a = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		final Matrix33F b = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIdentity() {
		final Matrix33F m = Matrix33F.identity();
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
	}
	
	@Test
	public void testInverse() {
		final Matrix33F a = new Matrix33F(2.0F, 0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 2.0F);
		final Matrix33F b = Matrix33F.inverse(a);
		final Matrix33F c = Matrix33F.inverse(b);
		final Matrix33F d = Matrix33F.multiply(a, b);
		final Matrix33F e = Matrix33F.identity();
		
		assertEquals(a, c);
		assertEquals(d, e);
		
		assertThrows(IllegalArgumentException.class, () -> Matrix33F.inverse(new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F)));
		assertThrows(NullPointerException.class, () -> Matrix33F.inverse(null));
	}
	
	@Test
	public void testIsInvertible() {
		final Matrix33F a = new Matrix33F(1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
		final Matrix33F b = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		assertTrue(a.isInvertible());
		
		assertFalse(b.isInvertible());
	}
	
	@Test
	public void testMultiply() {
		final Matrix33F a = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		final Matrix33F b = Matrix33F.identity();
		final Matrix33F c = Matrix33F.multiply(a, b);
		
		assertEquals(a, c);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.multiply(a, null));
		assertThrows(NullPointerException.class, () -> Matrix33F.multiply(null, b));
	}
	
	@Test
	public void testRead() throws IOException {
		final Matrix33F a = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeFloat(a.element11);
		dataOutput.writeFloat(a.element12);
		dataOutput.writeFloat(a.element13);
		dataOutput.writeFloat(a.element21);
		dataOutput.writeFloat(a.element22);
		dataOutput.writeFloat(a.element23);
		dataOutput.writeFloat(a.element31);
		dataOutput.writeFloat(a.element32);
		dataOutput.writeFloat(a.element33);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Matrix33F b = Matrix33F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.read(null));
		assertThrows(UncheckedIOException.class, () -> Matrix33F.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testRotateFloat() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix33F m = Matrix33F.rotate(angleDegrees);
		
		assertEquals(+angleRadiansCos, m.element11);
		assertEquals(-angleRadiansSin, m.element12);
		assertEquals(0.0F,             m.element13);
		assertEquals(+angleRadiansSin, m.element21);
		assertEquals(+angleRadiansCos, m.element22);
		assertEquals(0.0F,             m.element23);
		assertEquals(0.0F,             m.element31);
		assertEquals(0.0F,             m.element32);
		assertEquals(1.0F,             m.element33);
	}
	
	@Test
	public void testRotateFloatBoolean() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix33F a = Matrix33F.rotate(angleDegrees, false);
		final Matrix33F b = Matrix33F.rotate(angleRadians, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(-angleRadiansSin, a.element12);
		assertEquals(0.0F,             a.element13);
		assertEquals(+angleRadiansSin, a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(0.0F,             a.element23);
		assertEquals(0.0F,             a.element31);
		assertEquals(0.0F,             a.element32);
		assertEquals(1.0F,             a.element33);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(-angleRadiansSin, b.element12);
		assertEquals(0.0F,             b.element13);
		assertEquals(+angleRadiansSin, b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(0.0F,             b.element23);
		assertEquals(0.0F,             b.element31);
		assertEquals(0.0F,             b.element32);
		assertEquals(1.0F,             b.element33);
	}
	
	@Test
	public void testRotateFloatBooleanBoolean() {
		final float angleDegrees = 90.0F;
		final float angleRadians = Floats.toRadians(angleDegrees);
		final float angleRadiansCos = Floats.cos(angleRadians);
		final float angleRadiansSin = Floats.sin(angleRadians);
		
		final Matrix33F a = Matrix33F.rotate(angleDegrees, false, false);
		final Matrix33F b = Matrix33F.rotate(angleRadians, true, false);
		final Matrix33F c = Matrix33F.rotate(angleDegrees, false, true);
		final Matrix33F d = Matrix33F.rotate(angleRadians, true, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(-angleRadiansSin, a.element12);
		assertEquals(0.0F,             a.element13);
		assertEquals(+angleRadiansSin, a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(0.0F,             a.element23);
		assertEquals(0.0F,             a.element31);
		assertEquals(0.0F,             a.element32);
		assertEquals(1.0F,             a.element33);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(-angleRadiansSin, b.element12);
		assertEquals(0.0F,             b.element13);
		assertEquals(+angleRadiansSin, b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(0.0F,             b.element23);
		assertEquals(0.0F,             b.element31);
		assertEquals(0.0F,             b.element32);
		assertEquals(1.0F,             b.element33);
		
		assertEquals(+angleRadiansCos, c.element11);
		assertEquals(+angleRadiansSin, c.element12);
		assertEquals(0.0F,             c.element13);
		assertEquals(-angleRadiansSin, c.element21);
		assertEquals(+angleRadiansCos, c.element22);
		assertEquals(0.0F,             c.element23);
		assertEquals(0.0F,             c.element31);
		assertEquals(0.0F,             c.element32);
		assertEquals(1.0F,             c.element33);
		
		assertEquals(+angleRadiansCos, d.element11);
		assertEquals(+angleRadiansSin, d.element12);
		assertEquals(0.0F,             d.element13);
		assertEquals(-angleRadiansSin, d.element21);
		assertEquals(+angleRadiansCos, d.element22);
		assertEquals(0.0F,             d.element23);
		assertEquals(0.0F,             d.element31);
		assertEquals(0.0F,             d.element32);
		assertEquals(1.0F,             d.element33);
	}
	
	@Test
	public void testScaleFloat() {
		final Matrix33F m = Matrix33F.scale(2.0F);
		
		assertEquals(2.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(2.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
	}
	
	@Test
	public void testScaleFloatFloat() {
		final Matrix33F m = Matrix33F.scale(2.0F, 3.0F);
		
		assertEquals(2.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(3.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
	}
	
	@Test
	public void testScaleVector2F() {
		final Matrix33F m = Matrix33F.scale(new Vector2F(2.0F, 3.0F));
		
		assertEquals(2.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(0.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(3.0F, m.element22);
		assertEquals(0.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.scale(null));
	}
	
	@Test
	public void testToArray() {
		final Matrix33F matrix = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		final float[] array = matrix.toArray();
		
		assertNotNull(array);
		
		assertEquals(9, array.length);
		
		assertEquals(1.0F, array[0]);
		assertEquals(2.0F, array[1]);
		assertEquals(3.0F, array[2]);
		assertEquals(4.0F, array[3]);
		assertEquals(5.0F, array[4]);
		assertEquals(6.0F, array[5]);
		assertEquals(7.0F, array[6]);
		assertEquals(8.0F, array[7]);
		assertEquals(9.0F, array[8]);
	}
	
	@Test
	public void testToString() {
		final Matrix33F m = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		assertEquals("new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F)", m.toString());
	}
	
	@Test
	public void testTransformAndDividePoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = Matrix33F.scale(1.0F, 2.0F).transformAndDivide(a);
		final Point2F c = Matrix33F.translate(1.0F, 2.0F).transformAndDivide(a);
		final Point2F d = new Matrix33F(1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 2.0F).transformAndDivide(a);
		final Point2F e = new Matrix33F(1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F).transformAndDivide(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		
		assertEquals(2.0F, c.x);
		assertEquals(4.0F, c.y);
		
		assertEquals(0.5F, d.x);
		assertEquals(1.0F, d.y);
		
		assertEquals(1.0F, e.x);
		assertEquals(2.0F, e.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.translate(1.0F, 2.0F).transformAndDivide(null));
	}
	
	@Test
	public void testTransformPoint2F() {
		final Point2F a = new Point2F(1.0F, 2.0F);
		final Point2F b = Matrix33F.scale(1.0F, 2.0F).transform(a);
		final Point2F c = Matrix33F.translate(1.0F, 2.0F).transform(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		
		assertEquals(2.0F, c.x);
		assertEquals(4.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.translate(1.0F, 2.0F).transform((Point2F)(null)));
	}
	
	@Test
	public void testTransformRay2F() {
		final Ray2F a = new Ray2F(new Point2F(1.0F, 2.0F), new Vector2F(1.0F, 0.0F));
		final Ray2F b = Matrix33F.scale(1.0F, 2.0F).transform(a);
		final Ray2F c = Matrix33F.translate(1.0F, 2.0F).transform(a);
		
		assertEquals(1.0F, b.getOrigin().x);
		assertEquals(4.0F, b.getOrigin().y);
		
		assertEquals(1.0F, b.getDirection().x);
		assertEquals(0.0F, b.getDirection().y);
		
		assertEquals(2.0F, c.getOrigin().x);
		assertEquals(4.0F, c.getOrigin().y);
		
		assertEquals(1.0F, b.getDirection().x);
		assertEquals(0.0F, b.getDirection().y);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.translate(1.0F, 2.0F).transform((Ray2F)(null)));
	}
	
	@Test
	public void testTransformTransposeVector2F() {
		final Vector2F a = new Vector2F(1.0F, 2.0F);
		final Vector2F b = Matrix33F.transpose(Matrix33F.scale(1.0F, 2.0F)).transformTranspose(a);
		final Vector2F c = Matrix33F.transpose(Matrix33F.translate(1.0F, 2.0F)).transformTranspose(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		
		assertEquals(1.0F, c.x);
		assertEquals(2.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.transpose(Matrix33F.translate(1.0F, 2.0F)).transformTranspose(null));
	}
	
	@Test
	public void testTransformVector2F() {
		final Vector2F a = new Vector2F(1.0F, 2.0F);
		final Vector2F b = Matrix33F.scale(1.0F, 2.0F).transform(a);
		final Vector2F c = Matrix33F.translate(1.0F, 2.0F).transform(a);
		
		assertEquals(1.0F, b.x);
		assertEquals(4.0F, b.y);
		
		assertEquals(1.0F, c.x);
		assertEquals(2.0F, c.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.translate(1.0F, 2.0F).transform((Vector2F)(null)));
	}
	
	@Test
	public void testTranslateFloatFloat() {
		final Matrix33F m = Matrix33F.translate(2.0F, 3.0F);
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(2.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(3.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
	}
	
	@Test
	public void testTranslatePoint2F() {
		final Matrix33F m = Matrix33F.translate(new Point2F(2.0F, 3.0F));
		
		assertEquals(1.0F, m.element11);
		assertEquals(0.0F, m.element12);
		assertEquals(2.0F, m.element13);
		assertEquals(0.0F, m.element21);
		assertEquals(1.0F, m.element22);
		assertEquals(3.0F, m.element23);
		assertEquals(0.0F, m.element31);
		assertEquals(0.0F, m.element32);
		assertEquals(1.0F, m.element33);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.translate(null));
	}
	
	@Test
	public void testTranspose() {
		final Matrix33F a = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		final Matrix33F b = Matrix33F.transpose(a);
		
		assertEquals(1.0F, b.element11);
		assertEquals(4.0F, b.element12);
		assertEquals(7.0F, b.element13);
		assertEquals(2.0F, b.element21);
		assertEquals(5.0F, b.element22);
		assertEquals(8.0F, b.element23);
		assertEquals(3.0F, b.element31);
		assertEquals(6.0F, b.element32);
		assertEquals(9.0F, b.element33);
		
		assertThrows(NullPointerException.class, () -> Matrix33F.transpose(null));
	}
	
	@Test
	public void testWrite() {
		final Matrix33F a = new Matrix33F(1.0F, 2.0F, 3.0F, 4.0F, 5.0F, 6.0F, 7.0F, 8.0F, 9.0F);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Matrix33F b = Matrix33F.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}