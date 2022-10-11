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
package org.macroing.geo4j.shape.triangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.shape.ls.LineSegment2I;
import org.macroing.geo4j.shape.rectangle.Rectangle2I;

@SuppressWarnings("static-method")
public final class Triangle2IUnitTests {
	public Triangle2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructorPoint2IPoint2IPoint2I() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		final List<LineSegment2I> lineSegments = triangle.getLineSegments();
		
		final Rectangle2I rectangle = triangle.getRectangle();
		
		assertEquals(new Point2I(10, 10), triangle.getA());
		assertEquals(new Point2I(20, 10), triangle.getB());
		assertEquals(new Point2I(20, 20), triangle.getC());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 3);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(20, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(20, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(20, 20), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(20, 20), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(2).getB());
		
		assertNotNull(rectangle);
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(20, 10), rectangle.getB());
		assertEquals(new Point2I(20, 20), rectangle.getC());
		assertEquals(new Point2I(10, 20), rectangle.getD());
		
		assertThrows(NullPointerException.class, () -> new Triangle2I(new Point2I(), new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Triangle2I(new Point2I(), null, new Point2I()));
		assertThrows(NullPointerException.class, () -> new Triangle2I(null, new Point2I(), new Point2I()));
	}
	
	@Test
	public void testContainsPoint2IBoolean() {
		final Triangle2I triangle = new Triangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30));
		
		assertTrue(triangle.contains(new Point2I(20, 20), false));
		assertTrue(triangle.contains(new Point2I(25, 20), false));
		assertTrue(triangle.contains(new Point2I(30, 20), false));
		assertTrue(triangle.contains(new Point2I(30, 25), false));
		assertTrue(triangle.contains(new Point2I(30, 30), false));
		assertTrue(triangle.contains(new Point2I(25, 25), false));
		assertTrue(triangle.contains(new Point2I(27, 23), false));
		
		assertTrue(triangle.contains(new Point2I(20, 20), true));
		assertTrue(triangle.contains(new Point2I(25, 20), true));
		assertTrue(triangle.contains(new Point2I(30, 20), true));
		assertTrue(triangle.contains(new Point2I(30, 25), true));
		assertTrue(triangle.contains(new Point2I(30, 30), true));
		assertTrue(triangle.contains(new Point2I(25, 25), true));
		
		assertFalse(triangle.contains(new Point2I(19, 20), false));
		assertFalse(triangle.contains(new Point2I(31, 20), false));
		assertFalse(triangle.contains(new Point2I(29, 30), false));
		assertFalse(triangle.contains(new Point2I(31, 30), false));
		assertFalse(triangle.contains(new Point2I(20, 19), false));
		assertFalse(triangle.contains(new Point2I(20, 21), false));
		assertFalse(triangle.contains(new Point2I(30, 19), false));
		assertFalse(triangle.contains(new Point2I(30, 31), false));
		
		assertFalse(triangle.contains(new Point2I(19, 20), true));
		assertFalse(triangle.contains(new Point2I(31, 20), true));
		assertFalse(triangle.contains(new Point2I(29, 30), true));
		assertFalse(triangle.contains(new Point2I(31, 30), true));
		assertFalse(triangle.contains(new Point2I(20, 19), true));
		assertFalse(triangle.contains(new Point2I(20, 21), true));
		assertFalse(triangle.contains(new Point2I(30, 19), true));
		assertFalse(triangle.contains(new Point2I(30, 31), true));
		
		assertFalse(triangle.contains(new Point2I(27, 23), true));
		
		assertThrows(NullPointerException.class, () -> triangle.contains(null, false));
	}
	
	@Test
	public void testEquals() {
		final Triangle2I a = new Triangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30));
		final Triangle2I b = new Triangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30));
		final Triangle2I c = new Triangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 40));
		final Triangle2I d = new Triangle2I(new Point2I(20, 20), new Point2I(30, 10), new Point2I(30, 30));
		final Triangle2I e = new Triangle2I(new Point2I(10, 20), new Point2I(30, 20), new Point2I(30, 30));
		final Triangle2I f = null;
		
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
	public void testFindPointClosestTo() {
		final Triangle2I triangle = new Triangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30));
		
		assertEquals(new Point2I(25, 25), triangle.findPointClosestTo(new Point2I(25, 25)));
		assertEquals(new Point2I(25, 20), triangle.findPointClosestTo(new Point2I(25, 10)));
		assertEquals(new Point2I(30, 25), triangle.findPointClosestTo(new Point2I(35, 25)));
		assertEquals(new Point2I(25, 25), triangle.findPointClosestTo(new Point2I(24, 26)));
		
		assertThrows(NullPointerException.class, () -> triangle.findPointClosestTo(null));
	}
	
	@Test
	public void testFindPointsBoolean() {
		final Triangle2I triangle = new Triangle2I(new Point2I(0, 0), new Point2I(3, 0), new Point2I(3, 3));
		
		final List<Point2I> pointsA = triangle.findPoints(false);
		final List<Point2I> pointsB = triangle.findPoints(true);
		
		assertNotNull(pointsA);
		assertNotNull(pointsB);
		
		assertEquals(10, pointsA.size());
		assertEquals( 9, pointsB.size());
		
		assertEquals(new Point2I(0, 0), pointsA.get(0));
		assertEquals(new Point2I(1, 0), pointsA.get(1));
		assertEquals(new Point2I(2, 0), pointsA.get(2));
		assertEquals(new Point2I(3, 0), pointsA.get(3));
		assertEquals(new Point2I(1, 1), pointsA.get(4));
		assertEquals(new Point2I(2, 1), pointsA.get(5));
		assertEquals(new Point2I(3, 1), pointsA.get(6));
		assertEquals(new Point2I(2, 2), pointsA.get(7));
		assertEquals(new Point2I(3, 2), pointsA.get(8));
		assertEquals(new Point2I(3, 3), pointsA.get(9));
		
		assertEquals(new Point2I(0, 0), pointsB.get(0));
		assertEquals(new Point2I(1, 0), pointsB.get(1));
		assertEquals(new Point2I(2, 0), pointsB.get(2));
		assertEquals(new Point2I(3, 0), pointsB.get(3));
		assertEquals(new Point2I(1, 1), pointsB.get(4));
		assertEquals(new Point2I(3, 1), pointsB.get(5));
		assertEquals(new Point2I(2, 2), pointsB.get(6));
		assertEquals(new Point2I(3, 2), pointsB.get(7));
		assertEquals(new Point2I(3, 3), pointsB.get(8));
	}
	
	@Test
	public void testGetA() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(10, 10), triangle.getA());
	}
	
	@Test
	public void testGetB() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(20, 10), triangle.getB());
	}
	
	@Test
	public void testGetC() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(20, 20), triangle.getC());
	}
	
	@Test
	public void testGetID() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(5, triangle.getID());
	}
	
	@Test
	public void testGetName() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals("Triangle", triangle.getName());
	}
	
	@Test
	public void testGetLineSegments() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		final List<LineSegment2I> lineSegments = triangle.getLineSegments();
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 3);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(20, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(20, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(20, 20), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(20, 20), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(2).getB());
	}
	
	@Test
	public void testGetRectangle() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		final Rectangle2I rectangle = triangle.getRectangle();
		
		assertNotNull(rectangle);
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(20, 10), rectangle.getB());
		assertEquals(new Point2I(20, 20), rectangle.getC());
		assertEquals(new Point2I(10, 20), rectangle.getD());
	}
	
	@Test
	public void testHashCode() {
		final Triangle2I a = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		final Triangle2I b = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersects() {
		final Triangle2I a = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		final Triangle2I b = new Triangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30));
		final Triangle2I c = new Triangle2I(new Point2I(30, 30), new Point2I(40, 30), new Point2I(40, 40));
		
		assertTrue(a.intersects(b));
		
		assertFalse(a.intersects(c));
		
		assertThrows(NullPointerException.class, () -> a.intersects(null));
	}
	
	@Test
	public void testMax() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(20, 20), triangle.max());
	}
	
	@Test
	public void testMidpoint() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(15, 15), triangle.midpoint());
	}
	
	@Test
	public void testMin() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(10, 10), triangle.min());
	}
	
	@Test
	public void testToString() {
		final Triangle2I triangle = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		assertEquals("new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20))", triangle.toString());
	}
	
	@Test
	public void testWrite() throws IOException {
		final Triangle2I a = new Triangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final DataInput dataInput = new DataInputStream(new ByteArrayInputStream(bytes));
		
		final int id = dataInput.readInt();
		
		assertEquals(5, id);
		
		final Triangle2I b = new Triangle2I(Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}