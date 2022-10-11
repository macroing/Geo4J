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
package org.macroing.geo4j.shape.polygon;

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
public final class Polygon2IUnitTests {
	public Polygon2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructorPoint2Is() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		final List<LineSegment2I> lineSegments = polygon.getLineSegments();
		
		final List<Point2I> points = polygon.getPoints();
		
		final Rectangle2I rectangle = polygon.getRectangle();
		
		assertEquals(new Point2I(10, 10), points.get(0));
		assertEquals(new Point2I(10, 30), points.get(1));
		assertEquals(new Point2I(30, 30), points.get(2));
		assertEquals(new Point2I(30, 10), points.get(3));
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(30, 10), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(10, 30), rectangle.getD());
		
		assertThrows(NullPointerException.class, () -> new Polygon2I(new Point2I(), new Point2I(), new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Polygon2I(new Point2I(), new Point2I(), null, new Point2I()));
		assertThrows(NullPointerException.class, () -> new Polygon2I(new Point2I(), null, new Point2I(), new Point2I()));
		assertThrows(NullPointerException.class, () -> new Polygon2I(null, new Point2I(), new Point2I(), new Point2I()));
		assertThrows(NullPointerException.class, () -> new Polygon2I((Point2I[])(null)));
		
		assertThrows(IllegalArgumentException.class, () -> new Polygon2I(new Point2I(10, 10), new Point2I(10, 20)));
	}
	
	@Test
	public void testContainsPoint2I() {
		final Polygon2I polygon = new Polygon2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		
		assertTrue(polygon.contains(new Point2I(20, 20)));
		assertTrue(polygon.contains(new Point2I(25, 20)));
		assertTrue(polygon.contains(new Point2I(30, 20)));
		assertTrue(polygon.contains(new Point2I(30, 25)));
		assertTrue(polygon.contains(new Point2I(30, 30)));
		assertTrue(polygon.contains(new Point2I(25, 30)));
		assertTrue(polygon.contains(new Point2I(20, 30)));
		assertTrue(polygon.contains(new Point2I(20, 25)));
		assertTrue(polygon.contains(new Point2I(25, 25)));
		
		assertFalse(polygon.contains(new Point2I(19, 20)));
		assertFalse(polygon.contains(new Point2I(31, 20)));
		assertFalse(polygon.contains(new Point2I(19, 30)));
		assertFalse(polygon.contains(new Point2I(31, 30)));
		assertFalse(polygon.contains(new Point2I(20, 19)));
		assertFalse(polygon.contains(new Point2I(20, 31)));
		assertFalse(polygon.contains(new Point2I(30, 19)));
		assertFalse(polygon.contains(new Point2I(30, 31)));
		
		assertThrows(NullPointerException.class, () -> polygon.contains(null));
	}
	
	@Test
	public void testContainsPoint2IBoolean() {
		final Polygon2I polygon = new Polygon2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		
		assertTrue(polygon.contains(new Point2I(20, 20), false));
		assertTrue(polygon.contains(new Point2I(25, 20), false));
		assertTrue(polygon.contains(new Point2I(30, 20), false));
		assertTrue(polygon.contains(new Point2I(30, 25), false));
		assertTrue(polygon.contains(new Point2I(30, 30), false));
		assertTrue(polygon.contains(new Point2I(25, 30), false));
		assertTrue(polygon.contains(new Point2I(20, 30), false));
		assertTrue(polygon.contains(new Point2I(20, 25), false));
		assertTrue(polygon.contains(new Point2I(25, 25), false));
		
		assertTrue(polygon.contains(new Point2I(20, 20), true));
		assertTrue(polygon.contains(new Point2I(25, 20), true));
		assertTrue(polygon.contains(new Point2I(30, 20), true));
		assertTrue(polygon.contains(new Point2I(30, 25), true));
		assertTrue(polygon.contains(new Point2I(30, 30), true));
		assertTrue(polygon.contains(new Point2I(25, 30), true));
		assertTrue(polygon.contains(new Point2I(20, 30), true));
		assertTrue(polygon.contains(new Point2I(20, 25), true));
		
		assertFalse(polygon.contains(new Point2I(19, 20), false));
		assertFalse(polygon.contains(new Point2I(31, 20), false));
		assertFalse(polygon.contains(new Point2I(19, 30), false));
		assertFalse(polygon.contains(new Point2I(31, 30), false));
		assertFalse(polygon.contains(new Point2I(20, 19), false));
		assertFalse(polygon.contains(new Point2I(20, 31), false));
		assertFalse(polygon.contains(new Point2I(30, 19), false));
		assertFalse(polygon.contains(new Point2I(30, 31), false));
		
		assertFalse(polygon.contains(new Point2I(19, 20), true));
		assertFalse(polygon.contains(new Point2I(31, 20), true));
		assertFalse(polygon.contains(new Point2I(19, 30), true));
		assertFalse(polygon.contains(new Point2I(31, 30), true));
		assertFalse(polygon.contains(new Point2I(20, 19), true));
		assertFalse(polygon.contains(new Point2I(20, 31), true));
		assertFalse(polygon.contains(new Point2I(30, 19), true));
		assertFalse(polygon.contains(new Point2I(30, 31), true));
		
		assertFalse(polygon.contains(new Point2I(25, 25), true));
		
		assertThrows(NullPointerException.class, () -> polygon.contains(null, false));
	}
	
	@Test
	public void testEquals() {
		final Polygon2I a = new Polygon2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Polygon2I b = new Polygon2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Polygon2I c = new Polygon2I(new Point2I(20, 20), new Point2I(40, 20), new Point2I(40, 30), new Point2I(20, 30));
		final Polygon2I d = new Polygon2I(new Point2I(10, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(10, 30));
		final Polygon2I e = new Polygon2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 40), new Point2I(20, 40));
		final Polygon2I f = null;
		
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
		final Polygon2I polygon = new Polygon2I(new Point2I(20, 20), new Point2I(20, 30), new Point2I(30, 30), new Point2I(30, 20));
		
		assertEquals(new Point2I(25, 25), polygon.findPointClosestTo(new Point2I(25, 25)));
		assertEquals(new Point2I(20, 25), polygon.findPointClosestTo(new Point2I(10, 25)));
		assertEquals(new Point2I(25, 30), polygon.findPointClosestTo(new Point2I(25, 40)));
		assertEquals(new Point2I(30, 25), polygon.findPointClosestTo(new Point2I(40, 25)));
		assertEquals(new Point2I(25, 20), polygon.findPointClosestTo(new Point2I(25, 10)));
		
		assertThrows(NullPointerException.class, () -> polygon.findPointClosestTo(null));
	}
	
	@Test
	public void testFindPointsBoolean() {
		final Polygon2I polygon = new Polygon2I(new Point2I(1, 0), new Point2I(3, 0), new Point2I(4, 1), new Point2I(3, 2), new Point2I(1, 2), new Point2I(0, 1));
		
		final List<Point2I> pointsA = polygon.findPoints(false);
		final List<Point2I> pointsB = polygon.findPoints(true);
		
		assertNotNull(pointsA);
		assertNotNull(pointsB);
		
		assertEquals(11, pointsA.size());
		assertEquals( 8, pointsB.size());
		
		assertEquals(new Point2I(1, 0), pointsA.get( 0));
		assertEquals(new Point2I(2, 0), pointsA.get( 1));
		assertEquals(new Point2I(3, 0), pointsA.get( 2));
		assertEquals(new Point2I(0, 1), pointsA.get( 3));
		assertEquals(new Point2I(1, 1), pointsA.get( 4));
		assertEquals(new Point2I(2, 1), pointsA.get( 5));
		assertEquals(new Point2I(3, 1), pointsA.get( 6));
		assertEquals(new Point2I(4, 1), pointsA.get( 7));
		assertEquals(new Point2I(1, 2), pointsA.get( 8));
		assertEquals(new Point2I(2, 2), pointsA.get( 9));
		assertEquals(new Point2I(3, 2), pointsA.get(10));
		
		assertEquals(new Point2I(1, 0), pointsB.get(0));
		assertEquals(new Point2I(2, 0), pointsB.get(1));
		assertEquals(new Point2I(3, 0), pointsB.get(2));
		assertEquals(new Point2I(0, 1), pointsB.get(3));
		assertEquals(new Point2I(4, 1), pointsB.get(4));
		assertEquals(new Point2I(1, 2), pointsB.get(5));
		assertEquals(new Point2I(2, 2), pointsB.get(6));
		assertEquals(new Point2I(3, 2), pointsB.get(7));
	}
	
	@Test
	public void testGetID() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals(3, polygon.getID());
	}
	
	@Test
	public void testGetLineSegments() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		final List<LineSegment2I> lineSegments = polygon.getLineSegments();
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
	}
	
	@Test
	public void testGetName() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals("Polygon", polygon.getName());
	}
	
	@Test
	public void testGetPoints() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		final List<Point2I> points = polygon.getPoints();
		
		assertNotNull(points);
		
		assertTrue(points.size() == 4);
		
		assertEquals(new Point2I(10, 10), points.get(0));
		assertEquals(new Point2I(20, 10), points.get(1));
		assertEquals(new Point2I(20, 20), points.get(2));
		assertEquals(new Point2I(10, 20), points.get(3));
	}
	
	@Test
	public void testGetRectangle() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		final Rectangle2I rectangle = polygon.getRectangle();
		
		assertNotNull(rectangle);
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(20, 10), rectangle.getB());
		assertEquals(new Point2I(20, 20), rectangle.getC());
		assertEquals(new Point2I(10, 20), rectangle.getD());
	}
	
	@Test
	public void testHashCode() {
		final Polygon2I a = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		final Polygon2I b = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersects() {
		final Polygon2I a = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		final Polygon2I b = new Polygon2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Polygon2I c = new Polygon2I(new Point2I(30, 30), new Point2I(40, 30), new Point2I(40, 40), new Point2I(30, 40));
		
		assertTrue(a.intersects(b));
		
		assertFalse(a.intersects(c));
		
		assertThrows(NullPointerException.class, () -> a.intersects(null));
	}
	
	@Test
	public void testMax() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals(new Point2I(20, 20), polygon.max());
	}
	
	@Test
	public void testMidpoint() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals(new Point2I(15, 15), polygon.midpoint());
	}
	
	@Test
	public void testMin() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals(new Point2I(10, 10), polygon.min());
	}
	
	@Test
	public void testToString() {
		final Polygon2I polygon = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertEquals("new Polygon2I(new Point2I[] {new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20)})", polygon.toString());
	}
	
	@Test
	public void testWrite() throws IOException {
		final Polygon2I a = new Polygon2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final DataInput dataInput = new DataInputStream(new ByteArrayInputStream(bytes));
		
		final int id = dataInput.readInt();
		final int length = dataInput.readInt();
		
		assertEquals(3, id);
		assertEquals(4, length);
		
		final Polygon2I b = new Polygon2I(Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}