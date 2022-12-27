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

import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.common.Vector2D;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.ray.Ray2D;
import org.macroing.java.lang.Doubles;

@SuppressWarnings("static-method")
public final class Matrix33DUnitTests {
	public Matrix33DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		assertEquals(0, Matrix33D.ARRAY_OFFSET_ELEMENT_1_1);
		assertEquals(1, Matrix33D.ARRAY_OFFSET_ELEMENT_1_2);
		assertEquals(2, Matrix33D.ARRAY_OFFSET_ELEMENT_1_3);
		assertEquals(3, Matrix33D.ARRAY_OFFSET_ELEMENT_2_1);
		assertEquals(4, Matrix33D.ARRAY_OFFSET_ELEMENT_2_2);
		assertEquals(5, Matrix33D.ARRAY_OFFSET_ELEMENT_2_3);
		assertEquals(6, Matrix33D.ARRAY_OFFSET_ELEMENT_3_1);
		assertEquals(7, Matrix33D.ARRAY_OFFSET_ELEMENT_3_2);
		assertEquals(8, Matrix33D.ARRAY_OFFSET_ELEMENT_3_3);
		assertEquals(9, Matrix33D.ARRAY_SIZE);
	}
	
	@Test
	public void testConstructor() {
		final Matrix33D m = new Matrix33D();
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
	}
	
	@Test
	public void testConstructorDoubleDoubleDoubleDoubleDoubleDoubleDoubleDoubleDouble() {
		final Matrix33D m = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		assertEquals(1.0D, m.element11);
		assertEquals(2.0D, m.element12);
		assertEquals(3.0D, m.element13);
		assertEquals(4.0D, m.element21);
		assertEquals(5.0D, m.element22);
		assertEquals(6.0D, m.element23);
		assertEquals(7.0D, m.element31);
		assertEquals(8.0D, m.element32);
		assertEquals(9.0D, m.element33);
	}
	
	@Test
	public void testDeterminant() {
		final Matrix33D m = Matrix33D.identity();
		
		assertEquals(1.0D, m.determinant());
	}
	
	@Test
	public void testEqualsMatrix33D() {
		final Matrix33D a = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D b = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D c = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 9.0D);
		final Matrix33D d = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 9.0D, 8.0D);
		final Matrix33D e = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 9.0D, 7.0D, 8.0D);
		final Matrix33D f = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 9.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D g = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 9.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D h = new Matrix33D(0.0D, 1.0D, 2.0D, 9.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D i = new Matrix33D(0.0D, 1.0D, 9.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D j = new Matrix33D(0.0D, 9.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D k = new Matrix33D(9.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D l = null;
		
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
		final Matrix33D a = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D b = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D c = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 9.0D);
		final Matrix33D d = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 9.0D, 8.0D);
		final Matrix33D e = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 9.0D, 7.0D, 8.0D);
		final Matrix33D f = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 9.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D g = new Matrix33D(0.0D, 1.0D, 2.0D, 3.0D, 9.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D h = new Matrix33D(0.0D, 1.0D, 2.0D, 9.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D i = new Matrix33D(0.0D, 1.0D, 9.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D j = new Matrix33D(0.0D, 9.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix33D k = new Matrix33D(9.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D);
		final Matrix44D l = null;
		
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
		final Matrix33D matrix = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		assertEquals( 1.0D, matrix.getElement( 0));
		assertEquals( 2.0D, matrix.getElement( 1));
		assertEquals( 3.0D, matrix.getElement( 2));
		assertEquals( 4.0D, matrix.getElement( 3));
		assertEquals( 5.0D, matrix.getElement( 4));
		assertEquals( 6.0D, matrix.getElement( 5));
		assertEquals( 7.0D, matrix.getElement( 6));
		assertEquals( 8.0D, matrix.getElement( 7));
		assertEquals( 9.0D, matrix.getElement( 8));
		
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(-1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(+9));
	}
	
	@Test
	public void testGetElementIntInt() {
		final Matrix33D matrix = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		assertEquals( 1.0D, matrix.getElement(1, 1));
		assertEquals( 2.0D, matrix.getElement(1, 2));
		assertEquals( 3.0D, matrix.getElement(1, 3));
		assertEquals( 4.0D, matrix.getElement(2, 1));
		assertEquals( 5.0D, matrix.getElement(2, 2));
		assertEquals( 6.0D, matrix.getElement(2, 3));
		assertEquals( 7.0D, matrix.getElement(3, 1));
		assertEquals( 8.0D, matrix.getElement(3, 2));
		assertEquals( 9.0D, matrix.getElement(3, 3));
		
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(0, 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(1, 0));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(4, 1));
		assertThrows(IllegalArgumentException.class, () -> matrix.getElement(1, 4));
	}
	
	@Test
	public void testHashCode() {
		final Matrix33D a = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		final Matrix33D b = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIdentity() {
		final Matrix33D m = Matrix33D.identity();
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
	}
	
	@Test
	public void testInverse() {
		final Matrix33D a = new Matrix33D(2.0D, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0D, 2.0D);
		final Matrix33D b = Matrix33D.inverse(a);
		final Matrix33D c = Matrix33D.inverse(b);
		final Matrix33D d = Matrix33D.multiply(a, b);
		final Matrix33D e = Matrix33D.identity();
		
		assertEquals(a, c);
		assertEquals(d, e);
		
		assertThrows(IllegalArgumentException.class, () -> Matrix33D.inverse(new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D)));
		assertThrows(NullPointerException.class, () -> Matrix33D.inverse(null));
	}
	
	@Test
	public void testIsInvertible() {
		final Matrix33D a = new Matrix33D(1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D);
		final Matrix33D b = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		assertTrue(a.isInvertible());
		
		assertFalse(b.isInvertible());
	}
	
	@Test
	public void testMultiply() {
		final Matrix33D a = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		final Matrix33D b = Matrix33D.identity();
		final Matrix33D c = Matrix33D.multiply(a, b);
		
		assertEquals(a, c);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.multiply(a, null));
		assertThrows(NullPointerException.class, () -> Matrix33D.multiply(null, b));
	}
	
	@Test
	public void testRead() throws IOException {
		final Matrix33D a = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final
		DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		dataOutput.writeDouble(a.element11);
		dataOutput.writeDouble(a.element12);
		dataOutput.writeDouble(a.element13);
		dataOutput.writeDouble(a.element21);
		dataOutput.writeDouble(a.element22);
		dataOutput.writeDouble(a.element23);
		dataOutput.writeDouble(a.element31);
		dataOutput.writeDouble(a.element32);
		dataOutput.writeDouble(a.element33);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Matrix33D b = Matrix33D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.read(null));
		assertThrows(UncheckedIOException.class, () -> Matrix33D.read(new DataInputStream(new ByteArrayInputStream(new byte[] {}))));
	}
	
	@Test
	public void testRotateDouble() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Doubles.toRadians(angleDegrees);
		final double angleRadiansCos = Doubles.cos(angleRadians);
		final double angleRadiansSin = Doubles.sin(angleRadians);
		
		final Matrix33D m = Matrix33D.rotate(angleDegrees);
		
		assertEquals(+angleRadiansCos, m.element11);
		assertEquals(-angleRadiansSin, m.element12);
		assertEquals(0.0D,             m.element13);
		assertEquals(+angleRadiansSin, m.element21);
		assertEquals(+angleRadiansCos, m.element22);
		assertEquals(0.0D,             m.element23);
		assertEquals(0.0D,             m.element31);
		assertEquals(0.0D,             m.element32);
		assertEquals(1.0D,             m.element33);
	}
	
	@Test
	public void testRotateDoubleBoolean() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Doubles.toRadians(angleDegrees);
		final double angleRadiansCos = Doubles.cos(angleRadians);
		final double angleRadiansSin = Doubles.sin(angleRadians);
		
		final Matrix33D a = Matrix33D.rotate(angleDegrees, false);
		final Matrix33D b = Matrix33D.rotate(angleRadians, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(-angleRadiansSin, a.element12);
		assertEquals(0.0D,             a.element13);
		assertEquals(+angleRadiansSin, a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(0.0D,             a.element23);
		assertEquals(0.0D,             a.element31);
		assertEquals(0.0D,             a.element32);
		assertEquals(1.0D,             a.element33);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(-angleRadiansSin, b.element12);
		assertEquals(0.0D,             b.element13);
		assertEquals(+angleRadiansSin, b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(0.0D,             b.element23);
		assertEquals(0.0D,             b.element31);
		assertEquals(0.0D,             b.element32);
		assertEquals(1.0D,             b.element33);
	}
	
	@Test
	public void testRotateDoubleBooleanBoolean() {
		final double angleDegrees = 90.0D;
		final double angleRadians = Doubles.toRadians(angleDegrees);
		final double angleRadiansCos = Doubles.cos(angleRadians);
		final double angleRadiansSin = Doubles.sin(angleRadians);
		
		final Matrix33D a = Matrix33D.rotate(angleDegrees, false, false);
		final Matrix33D b = Matrix33D.rotate(angleRadians, true, false);
		final Matrix33D c = Matrix33D.rotate(angleDegrees, false, true);
		final Matrix33D d = Matrix33D.rotate(angleRadians, true, true);
		
		assertEquals(+angleRadiansCos, a.element11);
		assertEquals(-angleRadiansSin, a.element12);
		assertEquals(0.0D,             a.element13);
		assertEquals(+angleRadiansSin, a.element21);
		assertEquals(+angleRadiansCos, a.element22);
		assertEquals(0.0D,             a.element23);
		assertEquals(0.0D,             a.element31);
		assertEquals(0.0D,             a.element32);
		assertEquals(1.0D,             a.element33);
		
		assertEquals(+angleRadiansCos, b.element11);
		assertEquals(-angleRadiansSin, b.element12);
		assertEquals(0.0D,             b.element13);
		assertEquals(+angleRadiansSin, b.element21);
		assertEquals(+angleRadiansCos, b.element22);
		assertEquals(0.0D,             b.element23);
		assertEquals(0.0D,             b.element31);
		assertEquals(0.0D,             b.element32);
		assertEquals(1.0D,             b.element33);
		
		assertEquals(+angleRadiansCos, c.element11);
		assertEquals(+angleRadiansSin, c.element12);
		assertEquals(0.0D,             c.element13);
		assertEquals(-angleRadiansSin, c.element21);
		assertEquals(+angleRadiansCos, c.element22);
		assertEquals(0.0D,             c.element23);
		assertEquals(0.0D,             c.element31);
		assertEquals(0.0D,             c.element32);
		assertEquals(1.0D,             c.element33);
		
		assertEquals(+angleRadiansCos, d.element11);
		assertEquals(+angleRadiansSin, d.element12);
		assertEquals(0.0D,             d.element13);
		assertEquals(-angleRadiansSin, d.element21);
		assertEquals(+angleRadiansCos, d.element22);
		assertEquals(0.0D,             d.element23);
		assertEquals(0.0D,             d.element31);
		assertEquals(0.0D,             d.element32);
		assertEquals(1.0D,             d.element33);
	}
	
	@Test
	public void testScaleDouble() {
		final Matrix33D m = Matrix33D.scale(2.0D);
		
		assertEquals(2.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(2.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
	}
	
	@Test
	public void testScaleDoubleDouble() {
		final Matrix33D m = Matrix33D.scale(2.0D, 3.0D);
		
		assertEquals(2.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(3.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
	}
	
	@Test
	public void testScaleVector2D() {
		final Matrix33D m = Matrix33D.scale(new Vector2D(2.0D, 3.0D));
		
		assertEquals(2.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(0.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(3.0D, m.element22);
		assertEquals(0.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.scale(null));
	}
	
	@Test
	public void testToArray() {
		final Matrix33D matrix = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		final double[] array = matrix.toArray();
		
		assertNotNull(array);
		
		assertEquals(9, array.length);
		
		assertEquals(1.0D, array[0]);
		assertEquals(2.0D, array[1]);
		assertEquals(3.0D, array[2]);
		assertEquals(4.0D, array[3]);
		assertEquals(5.0D, array[4]);
		assertEquals(6.0D, array[5]);
		assertEquals(7.0D, array[6]);
		assertEquals(8.0D, array[7]);
		assertEquals(9.0D, array[8]);
	}
	
	@Test
	public void testToString() {
		final Matrix33D m = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		assertEquals("new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D)", m.toString());
	}
	
	@Test
	public void testTransformAndDividePoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = Matrix33D.scale(1.0D, 2.0D).transformAndDivide(a);
		final Point2D c = Matrix33D.translate(1.0D, 2.0D).transformAndDivide(a);
		final Point2D d = new Matrix33D(1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 2.0D).transformAndDivide(a);
		final Point2D e = new Matrix33D(1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D).transformAndDivide(a);
		
		assertEquals(1.0D, b.x);
		assertEquals(4.0D, b.y);
		
		assertEquals(2.0D, c.x);
		assertEquals(4.0D, c.y);
		
		assertEquals(0.5D, d.x);
		assertEquals(1.0D, d.y);
		
		assertEquals(1.0D, e.x);
		assertEquals(2.0D, e.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.translate(1.0D, 2.0D).transformAndDivide(null));
	}
	
	@Test
	public void testTransformPoint2D() {
		final Point2D a = new Point2D(1.0D, 2.0D);
		final Point2D b = Matrix33D.scale(1.0D, 2.0D).transform(a);
		final Point2D c = Matrix33D.translate(1.0D, 2.0D).transform(a);
		
		assertEquals(1.0D, b.x);
		assertEquals(4.0D, b.y);
		
		assertEquals(2.0D, c.x);
		assertEquals(4.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.translate(1.0D, 2.0D).transform((Point2D)(null)));
	}
	
	@Test
	public void testTransformRay2D() {
		final Ray2D a = new Ray2D(new Point2D(1.0D, 2.0D), new Vector2D(1.0D, 0.0D));
		final Ray2D b = Matrix33D.scale(1.0D, 2.0D).transform(a);
		final Ray2D c = Matrix33D.translate(1.0D, 2.0D).transform(a);
		
		assertEquals(1.0D, b.getOrigin().x);
		assertEquals(4.0D, b.getOrigin().y);
		
		assertEquals(1.0D, b.getDirection().x);
		assertEquals(0.0D, b.getDirection().y);
		
		assertEquals(2.0D, c.getOrigin().x);
		assertEquals(4.0D, c.getOrigin().y);
		
		assertEquals(1.0D, b.getDirection().x);
		assertEquals(0.0D, b.getDirection().y);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.translate(1.0D, 2.0D).transform((Ray2D)(null)));
	}
	
	@Test
	public void testTransformTransposeVector2D() {
		final Vector2D a = new Vector2D(1.0D, 2.0D);
		final Vector2D b = Matrix33D.transpose(Matrix33D.scale(1.0D, 2.0D)).transformTranspose(a);
		final Vector2D c = Matrix33D.transpose(Matrix33D.translate(1.0D, 2.0D)).transformTranspose(a);
		
		assertEquals(1.0D, b.x);
		assertEquals(4.0D, b.y);
		
		assertEquals(1.0D, c.x);
		assertEquals(2.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.transpose(Matrix33D.translate(1.0D, 2.0D)).transformTranspose(null));
	}
	
	@Test
	public void testTransformVector2D() {
		final Vector2D a = new Vector2D(1.0D, 2.0D);
		final Vector2D b = Matrix33D.scale(1.0D, 2.0D).transform(a);
		final Vector2D c = Matrix33D.translate(1.0D, 2.0D).transform(a);
		
		assertEquals(1.0D, b.x);
		assertEquals(4.0D, b.y);
		
		assertEquals(1.0D, c.x);
		assertEquals(2.0D, c.y);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.translate(1.0D, 2.0D).transform((Vector2D)(null)));
	}
	
	@Test
	public void testTranslateDoubleDouble() {
		final Matrix33D m = Matrix33D.translate(2.0D, 3.0D);
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(2.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(3.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
	}
	
	@Test
	public void testTranslatePoint2D() {
		final Matrix33D m = Matrix33D.translate(new Point2D(2.0D, 3.0D));
		
		assertEquals(1.0D, m.element11);
		assertEquals(0.0D, m.element12);
		assertEquals(2.0D, m.element13);
		assertEquals(0.0D, m.element21);
		assertEquals(1.0D, m.element22);
		assertEquals(3.0D, m.element23);
		assertEquals(0.0D, m.element31);
		assertEquals(0.0D, m.element32);
		assertEquals(1.0D, m.element33);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.translate(null));
	}
	
	@Test
	public void testTranspose() {
		final Matrix33D a = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		final Matrix33D b = Matrix33D.transpose(a);
		
		assertEquals(1.0D, b.element11);
		assertEquals(4.0D, b.element12);
		assertEquals(7.0D, b.element13);
		assertEquals(2.0D, b.element21);
		assertEquals(5.0D, b.element22);
		assertEquals(8.0D, b.element23);
		assertEquals(3.0D, b.element31);
		assertEquals(6.0D, b.element32);
		assertEquals(9.0D, b.element33);
		
		assertThrows(NullPointerException.class, () -> Matrix33D.transpose(null));
	}
	
	@Test
	public void testWrite() {
		final Matrix33D a = new Matrix33D(1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 7.0D, 8.0D, 9.0D);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Matrix33D b = Matrix33D.read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write(null));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}