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
package org.macroing.geo4j.shape.rectangle;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.Point2I;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.shape.circle.Circle2I;
import org.macroing.geo4j.shape.ls.LineSegment2I;

@SuppressWarnings("static-method")
public final class Rectangle2IUnitTests {
	public Rectangle2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructorCircle2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Circle2I(new Point2I(20, 20), 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(30, 10), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(10, 30), rectangle.getD());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I((Circle2I)(null)));
	}
	
	@Test
	public void testConstructorPoint2IPoint2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(30, 30), new Point2I(10, 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(30, 10), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(10, 30), rectangle.getD());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(null, new Point2I()));
	}
	
	@Test
	public void testConstructorPoint2IPoint2IPoint2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(30, 10), new Point2I(30, 30));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(30, 10), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(10, 30), rectangle.getD());
		
		assertNotNull(lineSegments);
		
		assertTrue(lineSegments.size() == 4);
		
		assertNotNull(lineSegments.get(0));
		assertNotNull(lineSegments.get(1));
		assertNotNull(lineSegments.get(2));
		assertNotNull(lineSegments.get(3));
		
		assertEquals(new Point2I(10, 10), lineSegments.get(0).getA());
		assertEquals(new Point2I(30, 10), lineSegments.get(0).getB());
		
		assertEquals(new Point2I(30, 10), lineSegments.get(1).getA());
		assertEquals(new Point2I(30, 30), lineSegments.get(1).getB());
		
		assertEquals(new Point2I(30, 30), lineSegments.get(2).getA());
		assertEquals(new Point2I(10, 30), lineSegments.get(2).getB());
		
		assertEquals(new Point2I(10, 30), lineSegments.get(3).getA());
		assertEquals(new Point2I(10, 10), lineSegments.get(3).getB());
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), null, new Point2I()));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(null, new Point2I(), new Point2I()));
		
		assertThrows(IllegalArgumentException.class, () -> new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30)));
	}
	
	@Test
	public void testConstructorPoint2IPoint2IPoint2IPoint2I() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
		assertEquals(new Point2I(10, 30), rectangle.getB());
		assertEquals(new Point2I(30, 30), rectangle.getC());
		assertEquals(new Point2I(30, 10), rectangle.getD());
		
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
		
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), new Point2I(), new Point2I(), null));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), new Point2I(), null, new Point2I()));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(new Point2I(), null, new Point2I(), new Point2I()));
		assertThrows(NullPointerException.class, () -> new Rectangle2I(null, new Point2I(), new Point2I(), new Point2I()));
		
		assertThrows(IllegalArgumentException.class, () -> new Rectangle2I(new Point2I(10, 10), new Point2I(10, 20), new Point2I(10, 40), new Point2I(10, 80)));
		assertThrows(IllegalArgumentException.class, () -> new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(40, 10), new Point2I(80, 10)));
	}
	
	@Test
	public void testContainsPoint2I() {
		final Rectangle2I rectangle2I = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 30));
		
		assertTrue(rectangle2I.contains(new Point2I(20, 20)));
		assertTrue(rectangle2I.contains(new Point2I(25, 20)));
		assertTrue(rectangle2I.contains(new Point2I(30, 20)));
		assertTrue(rectangle2I.contains(new Point2I(30, 25)));
		assertTrue(rectangle2I.contains(new Point2I(30, 30)));
		assertTrue(rectangle2I.contains(new Point2I(25, 30)));
		assertTrue(rectangle2I.contains(new Point2I(20, 30)));
		assertTrue(rectangle2I.contains(new Point2I(20, 25)));
		assertTrue(rectangle2I.contains(new Point2I(25, 25)));
		
		assertFalse(rectangle2I.contains(new Point2I(19, 20)));
		assertFalse(rectangle2I.contains(new Point2I(31, 20)));
		assertFalse(rectangle2I.contains(new Point2I(19, 30)));
		assertFalse(rectangle2I.contains(new Point2I(31, 30)));
		assertFalse(rectangle2I.contains(new Point2I(20, 19)));
		assertFalse(rectangle2I.contains(new Point2I(20, 31)));
		assertFalse(rectangle2I.contains(new Point2I(30, 19)));
		assertFalse(rectangle2I.contains(new Point2I(30, 31)));
		
		assertThrows(NullPointerException.class, () -> rectangle2I.contains(null));
	}
	
	@Test
	public void testContainsPoint2IBoolean() {
		final Rectangle2I rectangle2I = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 30));
		
		assertTrue(rectangle2I.contains(new Point2I(20, 20), false));
		assertTrue(rectangle2I.contains(new Point2I(25, 20), false));
		assertTrue(rectangle2I.contains(new Point2I(30, 20), false));
		assertTrue(rectangle2I.contains(new Point2I(30, 25), false));
		assertTrue(rectangle2I.contains(new Point2I(30, 30), false));
		assertTrue(rectangle2I.contains(new Point2I(25, 30), false));
		assertTrue(rectangle2I.contains(new Point2I(20, 30), false));
		assertTrue(rectangle2I.contains(new Point2I(20, 25), false));
		assertTrue(rectangle2I.contains(new Point2I(25, 25), false));
		
		assertTrue(rectangle2I.contains(new Point2I(20, 20), true));
		assertTrue(rectangle2I.contains(new Point2I(25, 20), true));
		assertTrue(rectangle2I.contains(new Point2I(30, 20), true));
		assertTrue(rectangle2I.contains(new Point2I(30, 25), true));
		assertTrue(rectangle2I.contains(new Point2I(30, 30), true));
		assertTrue(rectangle2I.contains(new Point2I(25, 30), true));
		assertTrue(rectangle2I.contains(new Point2I(20, 30), true));
		assertTrue(rectangle2I.contains(new Point2I(20, 25), true));
		
		assertFalse(rectangle2I.contains(new Point2I(19, 20), false));
		assertFalse(rectangle2I.contains(new Point2I(31, 20), false));
		assertFalse(rectangle2I.contains(new Point2I(19, 30), false));
		assertFalse(rectangle2I.contains(new Point2I(31, 30), false));
		assertFalse(rectangle2I.contains(new Point2I(20, 19), false));
		assertFalse(rectangle2I.contains(new Point2I(20, 31), false));
		assertFalse(rectangle2I.contains(new Point2I(30, 19), false));
		assertFalse(rectangle2I.contains(new Point2I(30, 31), false));
		
		assertFalse(rectangle2I.contains(new Point2I(19, 20), true));
		assertFalse(rectangle2I.contains(new Point2I(31, 20), true));
		assertFalse(rectangle2I.contains(new Point2I(19, 30), true));
		assertFalse(rectangle2I.contains(new Point2I(31, 30), true));
		assertFalse(rectangle2I.contains(new Point2I(20, 19), true));
		assertFalse(rectangle2I.contains(new Point2I(20, 31), true));
		assertFalse(rectangle2I.contains(new Point2I(30, 19), true));
		assertFalse(rectangle2I.contains(new Point2I(30, 31), true));
		
		assertFalse(rectangle2I.contains(new Point2I(25, 25), true));
		
		assertThrows(NullPointerException.class, () -> rectangle2I.contains(null, false));
	}
	
	@Test
	public void testEquals() {
		final Rectangle2I a = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Rectangle2I c = new Rectangle2I(new Point2I(20, 20), new Point2I(40, 20), new Point2I(40, 30), new Point2I(20, 30));
		final Rectangle2I d = new Rectangle2I(new Point2I(10, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(10, 30));
		final Rectangle2I e = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 40), new Point2I(20, 40));
		final Rectangle2I f = null;
		
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
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(20, 20), new Point2I(20, 30), new Point2I(30, 30), new Point2I(30, 20));
		
		assertEquals(new Point2I(25, 25), rectangle.findPointClosestTo(new Point2I(25, 25)));
		assertEquals(new Point2I(20, 25), rectangle.findPointClosestTo(new Point2I(10, 25)));
		assertEquals(new Point2I(25, 30), rectangle.findPointClosestTo(new Point2I(25, 40)));
		assertEquals(new Point2I(30, 25), rectangle.findPointClosestTo(new Point2I(40, 25)));
		assertEquals(new Point2I(25, 20), rectangle.findPointClosestTo(new Point2I(25, 10)));
		
		assertThrows(NullPointerException.class, () -> rectangle.findPointClosestTo(null));
	}
	
	@Test
	public void testFindPointsBoolean() {
		final Rectangle2I rectangle2I = new Rectangle2I(new Point2I(0, 0), new Point2I(2, 0), new Point2I(2, 2), new Point2I(0, 2));
		
		final List<Point2I> pointsA = rectangle2I.findPoints(false);
		final List<Point2I> pointsB = rectangle2I.findPoints(true);
		
		assertNotNull(pointsA);
		assertNotNull(pointsB);
		
		assertEquals(9, pointsA.size());
		assertEquals(8, pointsB.size());
		
		assertEquals(new Point2I(0, 0), pointsA.get(0));
		assertEquals(new Point2I(1, 0), pointsA.get(1));
		assertEquals(new Point2I(2, 0), pointsA.get(2));
		assertEquals(new Point2I(0, 1), pointsA.get(3));
		assertEquals(new Point2I(1, 1), pointsA.get(4));
		assertEquals(new Point2I(2, 1), pointsA.get(5));
		assertEquals(new Point2I(0, 2), pointsA.get(6));
		assertEquals(new Point2I(1, 2), pointsA.get(7));
		assertEquals(new Point2I(2, 2), pointsA.get(8));
		
		assertEquals(new Point2I(0, 0), pointsB.get(0));
		assertEquals(new Point2I(1, 0), pointsB.get(1));
		assertEquals(new Point2I(2, 0), pointsB.get(2));
		assertEquals(new Point2I(0, 1), pointsB.get(3));
		assertEquals(new Point2I(2, 1), pointsB.get(4));
		assertEquals(new Point2I(0, 2), pointsB.get(5));
		assertEquals(new Point2I(1, 2), pointsB.get(6));
		assertEquals(new Point2I(2, 2), pointsB.get(7));
	}
	
	@Test
	public void testFromPoints() {
		final Rectangle2I a = Rectangle2I.fromPoints(new Point2I(10, 10), new Point2I(15, 15), new Point2I(20, 20), new Point2I(25, 25), new Point2I(30, 30));
		final Rectangle2I b = new Rectangle2I(new Point2I(10, 10), new Point2I(30, 30));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.fromPoints((Point2I[])(null)));
		assertThrows(NullPointerException.class, () -> Rectangle2I.fromPoints(new Point2I(), null));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.fromPoints());
	}
	
	@Test
	public void testGetA() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(10, 10), rectangle.getA());
	}
	
	@Test
	public void testGetB() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(10, 30), rectangle.getB());
	}
	
	@Test
	public void testGetC() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(30, 30), rectangle.getC());
	}
	
	@Test
	public void testGetD() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(new Point2I(30, 10), rectangle.getD());
	}
	
	@Test
	public void testGetID() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals(4, rectangle.getID());
	}
	
	@Test
	public void testGetLineSegments() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		final List<LineSegment2I> lineSegments = rectangle.getLineSegments();
		
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
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(10, 30), new Point2I(30, 30), new Point2I(30, 10));
		
		assertEquals("Rectangle", rectangle.getName());
	}
	
	@Test
	public void testHashCode() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersects() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 20), new Point2I(30, 30), new Point2I(20, 30));
		final Rectangle2I c = new Rectangle2I(new Point2I(30, 30), new Point2I(40, 30), new Point2I(40, 40), new Point2I(30, 40));
		
		assertTrue(a.intersects(b));
		
		assertFalse(a.intersects(c));
		
		assertThrows(NullPointerException.class, () -> a.intersects(null));
	}
	
	@Test
	public void testIsAxisAligned() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(25, 25), new Point2I(20, 30), new Point2I(15, 25));
		
		assertTrue(a.isAxisAligned());
		
		assertFalse(b.isAxisAligned());
	}
	
	@Test
	public void testIsRotated() {
		final Rectangle2I a = new Rectangle2I(new Point2I(20, 20), new Point2I(25, 25), new Point2I(20, 30), new Point2I(15, 25));
		final Rectangle2I b = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		assertTrue(a.isRotated());
		
		assertFalse(b.isRotated());
	}
	
	@Test
	public void testMax() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(20, 20), rectangle.max());
	}
	
	@Test
	public void testMidpoint() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(15, 15), rectangle.midpoint());
	}
	
	@Test
	public void testMin() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals(new Point2I(10, 10), rectangle.min());
	}
	
	@Test
	public void testRotateABCDRectangle2IDouble() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotateABCD(a, +90.0D);
		final Rectangle2I c = Rectangle2I.rotateABCD(b, +90.0D);
		final Rectangle2I d = Rectangle2I.rotateABCD(c, +90.0D);
		final Rectangle2I e = Rectangle2I.rotateABCD(d, +90.0D);
		
		final Rectangle2I f = Rectangle2I.rotateABCD(e, -90.0D);
		final Rectangle2I g = Rectangle2I.rotateABCD(f, -90.0D);
		final Rectangle2I h = Rectangle2I.rotateABCD(g, -90.0D);
		final Rectangle2I i = Rectangle2I.rotateABCD(h, -90.0D);
		
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(  0, 200), new Point2I(  0, 100)), b);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(  0, 100), new Point2I(  0,   0), new Point2I(100,   0)), c);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100,   0), new Point2I(200,   0), new Point2I(200, 100)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100,   0), new Point2I(200,   0), new Point2I(200, 100)), f);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(  0, 100), new Point2I(  0,   0), new Point2I(100,   0)), g);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(  0, 200), new Point2I(  0, 100)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateABCD(null, 0.0D));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.rotateABCD(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(200, 200), new Point2I(200, 100)), 90.0D));
	}
	
	@Test
	public void testRotateABCDRectangle2IDoubleBoolean() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotateABCD(a, +90.0D, false);
		final Rectangle2I c = Rectangle2I.rotateABCD(b, +90.0D, false);
		final Rectangle2I d = Rectangle2I.rotateABCD(c, +90.0D, false);
		final Rectangle2I e = Rectangle2I.rotateABCD(d, +90.0D, false);
		
		final Rectangle2I f = Rectangle2I.rotateABCD(e, Math.toRadians(-90.0D), true);
		final Rectangle2I g = Rectangle2I.rotateABCD(f, Math.toRadians(-90.0D), true);
		final Rectangle2I h = Rectangle2I.rotateABCD(g, Math.toRadians(-90.0D), true);
		final Rectangle2I i = Rectangle2I.rotateABCD(h, Math.toRadians(-90.0D), true);
		
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(  0, 200), new Point2I(  0, 100)), b);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(  0, 100), new Point2I(  0,   0), new Point2I(100,   0)), c);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100,   0), new Point2I(200,   0), new Point2I(200, 100)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100,   0), new Point2I(200,   0), new Point2I(200, 100)), f);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(  0, 100), new Point2I(  0,   0), new Point2I(100,   0)), g);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(  0, 200), new Point2I(  0, 100)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateABCD(null, 0.0D, false));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.rotateABCD(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(200, 200), new Point2I(200, 100)), 90.0D, false));
		
		/*
		 * TODO: Notice the degrees 324.0 instead of 360.0. This is caused by precision loss. Perhaps this can be fixed somehow?
		 */
		
		Rectangle2I rectangle = a;
		
		for(double degrees = 1.0D; degrees <= 324.0D; degrees += 1.0D) {
			rectangle = Rectangle2I.rotateABCD(rectangle, 1.0D, false);
			
			assertEquals(a.getLineSegments().get(0).findPoints().size(), rectangle.getLineSegments().get(0).findPoints().size());
			assertEquals(a.getLineSegments().get(1).findPoints().size(), rectangle.getLineSegments().get(1).findPoints().size());
			assertEquals(a.getLineSegments().get(2).findPoints().size(), rectangle.getLineSegments().get(2).findPoints().size());
			assertEquals(a.getLineSegments().get(3).findPoints().size(), rectangle.getLineSegments().get(3).findPoints().size());
		}
		
		assertEquals(a, rectangle);
	}
	
	@Test
	public void testRotateABCDRectangle2IDoubleBooleanPoint2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotateABCD(a, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I c = Rectangle2I.rotateABCD(b, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I d = Rectangle2I.rotateABCD(c, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I e = Rectangle2I.rotateABCD(d, +90.0D, false, new Point2I(150, 150));
		
		final Rectangle2I f = Rectangle2I.rotateABCD(e, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I g = Rectangle2I.rotateABCD(f, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I h = Rectangle2I.rotateABCD(g, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I i = Rectangle2I.rotateABCD(h, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), b);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), c);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), f);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), g);
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateABCD(a, 0.0D, false, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateABCD(null, 0.0D, false, new Point2I(150, 150)));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.rotateABCD(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(200, 200), new Point2I(200, 100)), 90.0D, false, new Point2I(150, 150)));
		
		/*
		 * TODO: Notice the degrees 314.5 instead of 360.0. This is caused by precision loss. Perhaps this can be fixed somehow?
		 */
		
		Rectangle2I rectangle = a;
		
		for(double degrees = 1.0D; degrees <= 314.5D; degrees += 0.5D) {
			rectangle = Rectangle2I.rotateABCD(rectangle, 0.5D, false, new Point2I(150, 150));
			
			assertEquals(a.getLineSegments().get(0).findPoints().size(), rectangle.getLineSegments().get(0).findPoints().size());
			assertEquals(a.getLineSegments().get(1).findPoints().size(), rectangle.getLineSegments().get(1).findPoints().size());
			assertEquals(a.getLineSegments().get(2).findPoints().size(), rectangle.getLineSegments().get(2).findPoints().size());
			assertEquals(a.getLineSegments().get(3).findPoints().size(), rectangle.getLineSegments().get(3).findPoints().size());
		}
		
		assertEquals(a, rectangle);
	}
	
	@Test
	public void testRotateBCDARectangle2IDouble() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotateBCDA(a, +90.0D);
		final Rectangle2I c = Rectangle2I.rotateBCDA(b, +90.0D);
		final Rectangle2I d = Rectangle2I.rotateBCDA(c, +90.0D);
		final Rectangle2I e = Rectangle2I.rotateBCDA(d, +90.0D);
		
		final Rectangle2I f = Rectangle2I.rotateBCDA(e, -90.0D);
		final Rectangle2I g = Rectangle2I.rotateBCDA(f, -90.0D);
		final Rectangle2I h = Rectangle2I.rotateBCDA(g, -90.0D);
		final Rectangle2I i = Rectangle2I.rotateBCDA(h, -90.0D);
		
		assertEquals(new Rectangle2I(new Point2I(200,   0), new Point2I(200, 100), new Point2I(100, 100), new Point2I(100,   0)), b);
		assertEquals(new Rectangle2I(new Point2I(300, 100), new Point2I(200, 100), new Point2I(200,   0), new Point2I(300,   0)), c);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(200, 100), new Point2I(300, 100), new Point2I(300, 200)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(200, 100), new Point2I(300, 100), new Point2I(300, 200)), f);
		assertEquals(new Rectangle2I(new Point2I(300, 100), new Point2I(200, 100), new Point2I(200,   0), new Point2I(300,   0)), g);
		assertEquals(new Rectangle2I(new Point2I(200,   0), new Point2I(200, 100), new Point2I(100, 100), new Point2I(100,   0)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateBCDA(null, 0.0D));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.rotateBCDA(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(200, 200), new Point2I(200, 100)), 90.0D));
	}
	
	@Test
	public void testRotateBCDARectangle2IDoubleBoolean() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotateBCDA(a, +90.0D, false);
		final Rectangle2I c = Rectangle2I.rotateBCDA(b, +90.0D, false);
		final Rectangle2I d = Rectangle2I.rotateBCDA(c, +90.0D, false);
		final Rectangle2I e = Rectangle2I.rotateBCDA(d, +90.0D, false);
		
		final Rectangle2I f = Rectangle2I.rotateBCDA(e, Math.toRadians(-90.0D), true);
		final Rectangle2I g = Rectangle2I.rotateBCDA(f, Math.toRadians(-90.0D), true);
		final Rectangle2I h = Rectangle2I.rotateBCDA(g, Math.toRadians(-90.0D), true);
		final Rectangle2I i = Rectangle2I.rotateBCDA(h, Math.toRadians(-90.0D), true);
		
		assertEquals(new Rectangle2I(new Point2I(200,   0), new Point2I(200, 100), new Point2I(100, 100), new Point2I(100,   0)), b);
		assertEquals(new Rectangle2I(new Point2I(300, 100), new Point2I(200, 100), new Point2I(200,   0), new Point2I(300,   0)), c);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(200, 100), new Point2I(300, 100), new Point2I(300, 200)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(200, 100), new Point2I(300, 100), new Point2I(300, 200)), f);
		assertEquals(new Rectangle2I(new Point2I(300, 100), new Point2I(200, 100), new Point2I(200,   0), new Point2I(300,   0)), g);
		assertEquals(new Rectangle2I(new Point2I(200,   0), new Point2I(200, 100), new Point2I(100, 100), new Point2I(100,   0)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateBCDA(null, 0.0D, false));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.rotateBCDA(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(200, 200), new Point2I(200, 100)), 90.0D, false));
		
		/*
		 * TODO: Notice the degrees 324.0 instead of 360.0. This is caused by precision loss. Perhaps this can be fixed somehow?
		 */
		
		Rectangle2I rectangle = a;
		
		for(double degrees = 1.0D; degrees <= 324.0D; degrees += 1.0D) {
			rectangle = Rectangle2I.rotateBCDA(rectangle, 1.0D, false);
			
			assertEquals(a.getLineSegments().get(0).findPoints().size(), rectangle.getLineSegments().get(0).findPoints().size());
			assertEquals(a.getLineSegments().get(1).findPoints().size(), rectangle.getLineSegments().get(1).findPoints().size());
			assertEquals(a.getLineSegments().get(2).findPoints().size(), rectangle.getLineSegments().get(2).findPoints().size());
			assertEquals(a.getLineSegments().get(3).findPoints().size(), rectangle.getLineSegments().get(3).findPoints().size());
		}
		
		assertEquals(a, rectangle);
	}
	
	@Test
	public void testRotateBCDARectangle2IDoubleBooleanPoint2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotateBCDA(a, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I c = Rectangle2I.rotateBCDA(b, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I d = Rectangle2I.rotateBCDA(c, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I e = Rectangle2I.rotateBCDA(d, +90.0D, false, new Point2I(150, 150));
		
		final Rectangle2I f = Rectangle2I.rotateBCDA(e, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I g = Rectangle2I.rotateBCDA(f, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I h = Rectangle2I.rotateBCDA(g, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I i = Rectangle2I.rotateBCDA(h, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), b);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), c);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), f);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), g);
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateBCDA(a, 0.0D, false, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotateBCDA(null, 0.0D, false, new Point2I(150, 150)));
		
		assertThrows(IllegalArgumentException.class, () -> Rectangle2I.rotateBCDA(new Rectangle2I(new Point2I(100, 100), new Point2I(100, 200), new Point2I(200, 200), new Point2I(200, 100)), 90.0D, false, new Point2I(150, 150)));
		
		/*
		 * TODO: Notice the degrees 314.5 instead of 360.0. This is caused by precision loss. Perhaps this can be fixed somehow?
		 */
		
		Rectangle2I rectangle = a;
		
		for(double degrees = 1.0D; degrees <= 314.5D; degrees += 0.5D) {
			rectangle = Rectangle2I.rotateBCDA(rectangle, 0.5D, false, new Point2I(150, 150));
			
			assertEquals(a.getLineSegments().get(0).findPoints().size(), rectangle.getLineSegments().get(0).findPoints().size());
			assertEquals(a.getLineSegments().get(1).findPoints().size(), rectangle.getLineSegments().get(1).findPoints().size());
			assertEquals(a.getLineSegments().get(2).findPoints().size(), rectangle.getLineSegments().get(2).findPoints().size());
			assertEquals(a.getLineSegments().get(3).findPoints().size(), rectangle.getLineSegments().get(3).findPoints().size());
		}
		
		assertEquals(a, rectangle);
	}
	
	@Test
	public void testRotateRectangle2IDoubleBooleanPoint2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotate(a, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I c = Rectangle2I.rotate(b, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I d = Rectangle2I.rotate(c, +90.0D, false, new Point2I(150, 150));
		final Rectangle2I e = Rectangle2I.rotate(d, +90.0D, false, new Point2I(150, 150));
		
		final Rectangle2I f = Rectangle2I.rotate(e, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I g = Rectangle2I.rotate(f, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I h = Rectangle2I.rotate(g, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		final Rectangle2I i = Rectangle2I.rotate(h, Math.toRadians(-90.0D), true, new Point2I(150, 150));
		
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), b);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), c);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), f);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), g);
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotate(a, 0.0D, false, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotate(null, 0.0D, false, new Point2I(150, 150)));
		
		/*
		 * TODO: Notice the degrees 314.5 instead of 360.0. This is caused by precision loss. Perhaps this can be fixed somehow?
		 */
		
		Rectangle2I rectangle = a;
		
		for(double degrees = 1.0D; degrees <= 314.5D; degrees += 0.5D) {
			rectangle = Rectangle2I.rotate(rectangle, 0.5D, false, new Point2I(150, 150));
			
			assertEquals(a.getLineSegments().get(0).findPoints().size(), rectangle.getLineSegments().get(0).findPoints().size());
			assertEquals(a.getLineSegments().get(1).findPoints().size(), rectangle.getLineSegments().get(1).findPoints().size());
			assertEquals(a.getLineSegments().get(2).findPoints().size(), rectangle.getLineSegments().get(2).findPoints().size());
			assertEquals(a.getLineSegments().get(3).findPoints().size(), rectangle.getLineSegments().get(3).findPoints().size());
		}
		
		assertEquals(a, rectangle);
		
		/*
		 * TODO: Notice the degrees 284.0 instead of 360.0. This is caused by precision loss. One reason is the angle increment or decrement before the recursive call. Perhaps this can be fixed somehow?
		 */
		
		rectangle = a;
		
		for(double degrees = 1.0D; degrees <= 284.0D; degrees += 1.0D) {
			if(degrees < 100.0D) {
				rectangle = Rectangle2I.rotate(rectangle, 1.0D, false, new Point2I(150, 150));
			} else {
				rectangle = Rectangle2I.rotate(rectangle, Math.toRadians(1.0D), true, new Point2I(150, 150));
			}
			
			assertEquals(a.getLineSegments().get(0).length(), rectangle.getLineSegments().get(0).length());
			assertEquals(a.getLineSegments().get(1).length(), rectangle.getLineSegments().get(1).length());
			assertEquals(a.getLineSegments().get(2).length(), rectangle.getLineSegments().get(2).length());
			assertEquals(a.getLineSegments().get(3).length(), rectangle.getLineSegments().get(3).length());
		}
		
		assertEquals(a, rectangle);
		
		/*
		 * TODO: Notice the degrees 284.0 instead of 360.0. This is caused by precision loss. One reason is the angle increment or decrement before the recursive call. Perhaps this can be fixed somehow?
		 */
		
		for(double degrees = 1.0D; degrees <= 284.0D; degrees += 1.0D) {
			if(degrees < 100.0D) {
				rectangle = Rectangle2I.rotate(rectangle, -1.0D, false, new Point2I(150, 150));
			} else {
				rectangle = Rectangle2I.rotate(rectangle, Math.toRadians(-1.0D), true, new Point2I(150, 150));
			}
			
			assertEquals(a.getLineSegments().get(0).length(), rectangle.getLineSegments().get(0).length());
			assertEquals(a.getLineSegments().get(1).length(), rectangle.getLineSegments().get(1).length());
			assertEquals(a.getLineSegments().get(2).length(), rectangle.getLineSegments().get(2).length());
			assertEquals(a.getLineSegments().get(3).length(), rectangle.getLineSegments().get(3).length());
		}
		
		assertEquals(a, rectangle);
		
		/*
		 * TODO: Notice the degrees 178.5 instead of 360.0. This is caused by precision loss. One reason is the angle increment or decrement before the recursive call. Perhaps this can be fixed somehow?
		 */
		
		for(double degrees = 1.0D; degrees <= 178.5D; degrees += 0.5D) {
			if(degrees < 100.0D) {
				rectangle = Rectangle2I.rotate(rectangle, 0.5D, false, rectangle.getA());
			} else {
				rectangle = Rectangle2I.rotate(rectangle, Math.toRadians(0.5D), true, rectangle.getA());
			}
			
			assertEquals(a.getLineSegments().get(0).length(), rectangle.getLineSegments().get(0).length());
			assertEquals(a.getLineSegments().get(1).length(), rectangle.getLineSegments().get(1).length());
			assertEquals(a.getLineSegments().get(2).length(), rectangle.getLineSegments().get(2).length());
			assertEquals(a.getLineSegments().get(3).length(), rectangle.getLineSegments().get(3).length());
		}
		
		assertEquals(a, rectangle);
	}
	
	@Test
	public void testRotateRectangle2IFloatBooleanPoint2I() {
		final Rectangle2I a = new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200));
		
		final Rectangle2I b = Rectangle2I.rotate(a, +90.0F, false, new Point2I(150, 150));
		final Rectangle2I c = Rectangle2I.rotate(b, +90.0F, false, new Point2I(150, 150));
		final Rectangle2I d = Rectangle2I.rotate(c, +90.0F, false, new Point2I(150, 150));
		final Rectangle2I e = Rectangle2I.rotate(d, +90.0F, false, new Point2I(150, 150));
		
		final Rectangle2I f = Rectangle2I.rotate(e, (float)(Math.toRadians(-90.0F)), true, new Point2I(150, 150));
		final Rectangle2I g = Rectangle2I.rotate(f, (float)(Math.toRadians(-90.0F)), true, new Point2I(150, 150));
		final Rectangle2I h = Rectangle2I.rotate(g, (float)(Math.toRadians(-90.0F)), true, new Point2I(150, 150));
		final Rectangle2I i = Rectangle2I.rotate(h, (float)(Math.toRadians(-90.0F)), true, new Point2I(150, 150));
		
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), b);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), c);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), d);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), e);
		assertEquals(new Rectangle2I(new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200)), f);
		assertEquals(new Rectangle2I(new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100), new Point2I(200, 100)), g);
		assertEquals(new Rectangle2I(new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200), new Point2I(100, 100)), h);
		assertEquals(new Rectangle2I(new Point2I(100, 100), new Point2I(200, 100), new Point2I(200, 200), new Point2I(100, 200)), i);
		
		assertEquals(a.findPoints().size(), b.findPoints().size());
		assertEquals(a.findPoints().size(), c.findPoints().size());
		assertEquals(a.findPoints().size(), d.findPoints().size());
		assertEquals(a.findPoints().size(), e.findPoints().size());
		assertEquals(a.findPoints().size(), f.findPoints().size());
		assertEquals(a.findPoints().size(), g.findPoints().size());
		assertEquals(a.findPoints().size(), h.findPoints().size());
		assertEquals(a.findPoints().size(), i.findPoints().size());
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotate(a, 0.0F, false, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.rotate(null, 0.0F, false, new Point2I(150, 150)));
		
		/*
		 * TODO: Notice the degrees 314.5 instead of 360.0. This is caused by precision loss. Perhaps this can be fixed somehow?
		 */
		
		Rectangle2I rectangle = a;
		
		for(float degrees = 1.0F; degrees <= 314.5F; degrees += 0.5F) {
			rectangle = Rectangle2I.rotate(rectangle, 0.5F, false, new Point2I(150, 150));
			
			assertEquals(a.getLineSegments().get(0).findPoints().size(), rectangle.getLineSegments().get(0).findPoints().size());
			assertEquals(a.getLineSegments().get(1).findPoints().size(), rectangle.getLineSegments().get(1).findPoints().size());
			assertEquals(a.getLineSegments().get(2).findPoints().size(), rectangle.getLineSegments().get(2).findPoints().size());
			assertEquals(a.getLineSegments().get(3).findPoints().size(), rectangle.getLineSegments().get(3).findPoints().size());
		}
		
		assertEquals(a, rectangle);
		
		/*
		 * TODO: Notice the degrees 284.0 instead of 360.0. This is caused by precision loss. One reason is the angle increment or decrement before the recursive call. Perhaps this can be fixed somehow?
		 */
		
		rectangle = a;
		
		for(float degrees = 1.0F; degrees <= 284.0F; degrees += 1.0F) {
			if(degrees < 100.0F) {
				rectangle = Rectangle2I.rotate(rectangle, 1.0F, false, new Point2I(150, 150));
			} else {
				rectangle = Rectangle2I.rotate(rectangle, (float)(Math.toRadians(1.0F)), true, new Point2I(150, 150));
			}
			
			assertEquals(a.getLineSegments().get(0).length(), rectangle.getLineSegments().get(0).length());
			assertEquals(a.getLineSegments().get(1).length(), rectangle.getLineSegments().get(1).length());
			assertEquals(a.getLineSegments().get(2).length(), rectangle.getLineSegments().get(2).length());
			assertEquals(a.getLineSegments().get(3).length(), rectangle.getLineSegments().get(3).length());
		}
		
		assertEquals(a, rectangle);
		
		/*
		 * TODO: Notice the degrees 284.0 instead of 360.0. This is caused by precision loss. One reason is the angle increment or decrement before the recursive call. Perhaps this can be fixed somehow?
		 */
		
		for(float degrees = 1.0F; degrees <= 284.0F; degrees += 1.0F) {
			if(degrees < 100.0F) {
				rectangle = Rectangle2I.rotate(rectangle, -1.0F, false, new Point2I(150, 150));
			} else {
				rectangle = Rectangle2I.rotate(rectangle, (float)(Math.toRadians(-1.0F)), true, new Point2I(150, 150));
			}
			
			assertEquals(a.getLineSegments().get(0).length(), rectangle.getLineSegments().get(0).length());
			assertEquals(a.getLineSegments().get(1).length(), rectangle.getLineSegments().get(1).length());
			assertEquals(a.getLineSegments().get(2).length(), rectangle.getLineSegments().get(2).length());
			assertEquals(a.getLineSegments().get(3).length(), rectangle.getLineSegments().get(3).length());
		}
		
		assertEquals(a, rectangle);
		
		/*
		 * TODO: Notice the degrees 178.5 instead of 360.0. This is caused by precision loss. One reason is the angle increment or decrement before the recursive call. Perhaps this can be fixed somehow?
		 */
		
		for(float degrees = 1.0F; degrees <= 178.5F; degrees += 0.5F) {
			if(degrees < 100.0F) {
				rectangle = Rectangle2I.rotate(rectangle, 0.5F, false, rectangle.getA());
			} else {
				rectangle = Rectangle2I.rotate(rectangle, (float)(Math.toRadians(0.5F)), true, rectangle.getA());
			}
			
			assertEquals(a.getLineSegments().get(0).length(), rectangle.getLineSegments().get(0).length());
			assertEquals(a.getLineSegments().get(1).length(), rectangle.getLineSegments().get(1).length());
			assertEquals(a.getLineSegments().get(2).length(), rectangle.getLineSegments().get(2).length());
			assertEquals(a.getLineSegments().get(3).length(), rectangle.getLineSegments().get(3).length());
		}
		
		assertEquals(a, rectangle);
	}
	
	@Test
	public void testToString() {
		final Rectangle2I rectangle = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		
		assertEquals("new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20))", rectangle.toString());
	}
	
	@Test
	public void testTranslate() {
		final Rectangle2I a = new Rectangle2I(new Point2I(-5, -5), new Point2I(+5, -5), new Point2I(+5, +5), new Point2I(-5, +5));
		final Rectangle2I b = Rectangle2I.translate(a, new Point2I(+0, +0));
		final Rectangle2I c = Rectangle2I.translate(a, new Point2I(+4, +0));
		final Rectangle2I d = Rectangle2I.translate(a, new Point2I(+0, +4));
		final Rectangle2I e = Rectangle2I.translate(a, new Point2I(-4, +0));
		final Rectangle2I f = Rectangle2I.translate(a, new Point2I(+0, -4));
		
		assertTrue(a == b);
		
		assertEquals(new Rectangle2I(new Point2I(-1, -5), new Point2I(+9, -5), new Point2I(+9, +5), new Point2I(-1, +5)), c);
		assertEquals(new Rectangle2I(new Point2I(-5, -1), new Point2I(+5, -1), new Point2I(+5, +9), new Point2I(-5, +9)), d);
		assertEquals(new Rectangle2I(new Point2I(-9, -5), new Point2I(+1, -5), new Point2I(+1, +5), new Point2I(-9, +5)), e);
		assertEquals(new Rectangle2I(new Point2I(-5, -9), new Point2I(+5, -9), new Point2I(+5, +1), new Point2I(-5, +1)), f);
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.translate(a, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.translate(null, new Point2I(0, 0)));
	}
	
	@Test
	public void testTranslateToOrigin() {
		final Rectangle2I a = new Rectangle2I(new Point2I(+0, +0), new Point2I(+9, +0), new Point2I(+9, +9), new Point2I(+0, +9));
		final Rectangle2I b = Rectangle2I.translateToOrigin(a);
		final Rectangle2I c = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(- 1, + 9), new Point2I(+ 8, + 0), new Point2I(+17, + 9), new Point2I(+ 8, +18)));
		final Rectangle2I d = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 8, +18), new Point2I(- 1, + 9), new Point2I(+ 8, + 0), new Point2I(+17, + 9)));
		final Rectangle2I e = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+17, + 9), new Point2I(+ 8, +18), new Point2I(- 1, + 9), new Point2I(+ 8, + 0)));
		final Rectangle2I f = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 8, + 0), new Point2I(+17, + 9), new Point2I(+ 8, +18), new Point2I(- 1, + 9)));
		final Rectangle2I g = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 1, + 9), new Point2I(+10, + 0), new Point2I(+19, + 9), new Point2I(+10, +18)));
		final Rectangle2I h = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+10, +18), new Point2I(+ 1, + 9), new Point2I(+10, + 0), new Point2I(+19, + 9)));
		final Rectangle2I i = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+19, + 9), new Point2I(+10, +18), new Point2I(+ 1, + 9), new Point2I(+10, + 0)));
		final Rectangle2I j = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+10, + 0), new Point2I(+19, + 9), new Point2I(+10, +18), new Point2I(+ 1, + 9)));
		final Rectangle2I k = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 9, - 1), new Point2I(+ 0, + 8), new Point2I(+ 9, +17), new Point2I(+18, + 8)));
		final Rectangle2I l = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+18, + 8), new Point2I(+ 9, - 1), new Point2I(+ 0, + 8), new Point2I(+ 9, +17)));
		final Rectangle2I m = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 9, +17), new Point2I(+18, + 8), new Point2I(+ 9, - 1), new Point2I(+ 0, + 8)));
		final Rectangle2I n = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 0, + 8), new Point2I(+ 9, +17), new Point2I(+18, + 8), new Point2I(+ 9, - 1)));
		final Rectangle2I o = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 9, + 1), new Point2I(+ 0, +10), new Point2I(+ 9, +19), new Point2I(+18, +10)));
		final Rectangle2I p = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+18, +10), new Point2I(+ 9, + 1), new Point2I(+ 0, +10), new Point2I(+ 9, +19)));
		final Rectangle2I q = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 9, +19), new Point2I(+18, +10), new Point2I(+ 9, + 1), new Point2I(+ 0, +10)));
		final Rectangle2I r = Rectangle2I.translateToOrigin(new Rectangle2I(new Point2I(+ 0, +10), new Point2I(+ 9, +19), new Point2I(+18, +10), new Point2I(+ 9, + 1)));
		
		assertTrue(a == b);
		
		assertEquals(new Rectangle2I(new Point2I(+ 0, + 9), new Point2I(+ 9, + 0), new Point2I(+18, + 9), new Point2I(+ 9, +18)), c);
		assertEquals(new Rectangle2I(new Point2I(+ 9, +18), new Point2I(+ 0, + 9), new Point2I(+ 9, + 0), new Point2I(+18, + 9)), d);
		assertEquals(new Rectangle2I(new Point2I(+18, + 9), new Point2I(+ 9, +18), new Point2I(+ 0, + 9), new Point2I(+ 9, + 0)), e);
		assertEquals(new Rectangle2I(new Point2I(+ 9, + 0), new Point2I(+18, + 9), new Point2I(+ 9, +18), new Point2I(+ 0, + 9)), f);
		assertEquals(new Rectangle2I(new Point2I(+ 0, + 9), new Point2I(+ 9, + 0), new Point2I(+18, + 9), new Point2I(+ 9, +18)), g);
		assertEquals(new Rectangle2I(new Point2I(+ 9, +18), new Point2I(+ 0, + 9), new Point2I(+ 9, + 0), new Point2I(+18, + 9)), h);
		assertEquals(new Rectangle2I(new Point2I(+18, + 9), new Point2I(+ 9, +18), new Point2I(+ 0, + 9), new Point2I(+ 9, + 0)), i);
		assertEquals(new Rectangle2I(new Point2I(+ 9, + 0), new Point2I(+18, + 9), new Point2I(+ 9, +18), new Point2I(+ 0, + 9)), j);
		assertEquals(new Rectangle2I(new Point2I(+ 9, + 0), new Point2I(+ 0, + 9), new Point2I(+ 9, +18), new Point2I(+18, + 9)), k);
		assertEquals(new Rectangle2I(new Point2I(+18, + 9), new Point2I(+ 9, + 0), new Point2I(+ 0, + 9), new Point2I(+ 9, +18)), l);
		assertEquals(new Rectangle2I(new Point2I(+ 9, +18), new Point2I(+18, + 9), new Point2I(+ 9, + 0), new Point2I(+ 0, + 9)), m);
		assertEquals(new Rectangle2I(new Point2I(+ 0, + 9), new Point2I(+ 9, +18), new Point2I(+18, + 9), new Point2I(+ 9, + 0)), n);
		assertEquals(new Rectangle2I(new Point2I(+ 9, + 0), new Point2I(+ 0, + 9), new Point2I(+ 9, +18), new Point2I(+18, + 9)), o);
		assertEquals(new Rectangle2I(new Point2I(+18, + 9), new Point2I(+ 9, + 0), new Point2I(+ 0, + 9), new Point2I(+ 9, +18)), p);
		assertEquals(new Rectangle2I(new Point2I(+ 9, +18), new Point2I(+18, + 9), new Point2I(+ 9, + 0), new Point2I(+ 0, + 9)), q);
		assertEquals(new Rectangle2I(new Point2I(+ 0, + 9), new Point2I(+ 9, +18), new Point2I(+18, + 9), new Point2I(+ 9, + 0)), r);
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.translateToOrigin(null));
	}
	
	@Test
	public void testUnion() {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 20));
		final Rectangle2I b = new Rectangle2I(new Point2I(20, 20), new Point2I(30, 30));
		final Rectangle2I c = new Rectangle2I(new Point2I(10, 10), new Point2I(30, 30));
		final Rectangle2I d = Rectangle2I.union(a, b);
		
		assertEquals(c, d);
		
		assertThrows(NullPointerException.class, () -> Rectangle2I.union(a, null));
		assertThrows(NullPointerException.class, () -> Rectangle2I.union(null, b));
	}
	
	@Test
	public void testWriteDataOutput() throws IOException {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final DataInput dataInput = new DataInputStream(new ByteArrayInputStream(bytes));
		
		final int id = dataInput.readInt();
		
		assertEquals(4, id);
		
		final Rectangle2I b = new Rectangle2I(Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
	
	@Test
	public void testWriteFile() throws IOException {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		final File directory = new File("./generated");
		
		if(!directory.isDirectory()) {
			directory.mkdirs();
		}
		
		final File fileA = new File(directory, String.format("./Rectangle2I-%s.dat", Long.toString(System.currentTimeMillis())));
		final File fileB = new File("");
		
		a.write(fileA);
		
		try(final InputStream inputStream = new FileInputStream(fileA)) {
			final DataInput dataInput = new DataInputStream(inputStream);
			
			final int id = dataInput.readInt();
			
			assertEquals(4, id);
			
			final Rectangle2I b = new Rectangle2I(Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput));
			
			assertEquals(a, b);
		}
		
		assertThrows(NullPointerException.class, () -> a.write((File)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(fileB));
		
		fileA.delete();
		
		directory.delete();
	}
	
	@Test
	public void testWriteString() throws IOException {
		final Rectangle2I a = new Rectangle2I(new Point2I(10, 10), new Point2I(20, 10), new Point2I(20, 20), new Point2I(10, 20));
		
		final File directory = new File("./generated");
		
		if(!directory.isDirectory()) {
			directory.mkdirs();
		}
		
		final File fileA = new File(directory, String.format("./Rectangle2I-%s.dat", Long.toString(System.currentTimeMillis())));
		final File fileB = new File("");
		
		a.write(fileA.getAbsolutePath());
		
		try(final InputStream inputStream = new FileInputStream(fileA)) {
			final DataInput dataInput = new DataInputStream(inputStream);
			
			final int id = dataInput.readInt();
			
			assertEquals(4, id);
			
			final Rectangle2I b = new Rectangle2I(Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput), Point2I.read(dataInput));
			
			assertEquals(a, b);
		}
		
		assertThrows(NullPointerException.class, () -> a.write((String)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(fileB.getAbsolutePath()));
		
		fileA.delete();
		
		directory.delete();
	}
}