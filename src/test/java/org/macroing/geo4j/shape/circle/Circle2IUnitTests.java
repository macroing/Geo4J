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
package org.macroing.geo4j.shape.circle;

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
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.geo4j.shape.Shape2I;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Circle2IUnitTests {
	public Circle2IUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Point2I center = new Point2I(10, 10);
		
		final Circle2I circle = new Circle2I(center, 20);
		
		assertTrue(circle.accept(new NodeHierarchicalVisitorMock(node -> false,                                      node -> node.equals(circle))));
		assertTrue(circle.accept(new NodeHierarchicalVisitorMock(node -> node.equals(circle),                        node -> node.equals(circle))));
		assertTrue(circle.accept(new NodeHierarchicalVisitorMock(node -> node.equals(circle) || node.equals(center), node -> node.equals(circle) || node.equals(center))));
		
		assertThrows(NodeTraversalException.class, () -> circle.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> circle.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Circle2I circle = new Circle2I();
		
		assertThrows(NodeTraversalException.class, () -> circle.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> circle.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstructor() {
		final Circle2I circle = new Circle2I();
		
		assertEquals(new Point2I(), circle.getCenter());
		assertEquals(10, circle.getRadius());
	}
	
	@Test
	public void testConstructorPoint2I() {
		final Circle2I circle = new Circle2I(new Point2I(10, 10));
		
		assertEquals(new Point2I(10, 10), circle.getCenter());
		assertEquals(10.0D, circle.getRadius());
		
		assertThrows(NullPointerException.class, () -> new Circle2I((Point2I)(null)));
	}
	
	@Test
	public void testConstructorPoint2IInt() {
		final Circle2I circle = new Circle2I(new Point2I(10, 10), 20);
		
		assertEquals(new Point2I(10, 10), circle.getCenter());
		assertEquals(20, circle.getRadius());
		
		assertThrows(NullPointerException.class, () -> new Circle2I(null, 20));
	}
	
	@Test
	public void testContains() {
		final Circle2I circle = new Circle2I(new Point2I(0, 0), 10);
		
		assertTrue(circle.contains(new Point2I(+10, 0)));
		assertTrue(circle.contains(new Point2I(-10, 0)));
		
		assertTrue(circle.contains(new Point2I(0, +10)));
		assertTrue(circle.contains(new Point2I(0, -10)));
		
		assertTrue(circle.contains(new Point2I(0, 0)));
		
		assertFalse(circle.contains(new Point2I(10, 10)));
		
		assertThrows(NullPointerException.class, () -> circle.contains(null));
	}
	
	@Test
	public void testContainsBoolean() {
		final Circle2I circle = new Circle2I(new Point2I(0, 0), 10);
		
		assertTrue(circle.contains(new Point2I(+10, 0), false));
		assertTrue(circle.contains(new Point2I(+10, 0), true));
		assertTrue(circle.contains(new Point2I(-10, 0), false));
		assertTrue(circle.contains(new Point2I(-10, 0), true));
		
		assertTrue(circle.contains(new Point2I(0, +10), false));
		assertTrue(circle.contains(new Point2I(0, +10), true));
		assertTrue(circle.contains(new Point2I(0, -10), false));
		assertTrue(circle.contains(new Point2I(0, -10), true));
		
		assertTrue(circle.contains(new Point2I(0, 0), false));
		
		assertFalse(circle.contains(new Point2I(0, 0), true));
		
		assertFalse(circle.contains(new Point2I(10, 10), false));
		assertFalse(circle.contains(new Point2I(10, 10), true));
		
		assertThrows(NullPointerException.class, () -> circle.contains(null, false));
	}
	
	@Test
	public void testContainsDifference() {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 10);
		final Circle2I b = new Circle2I(new Point2I(30, 10), 10);
		
		assertTrue(Shape2I.containsDifference(new Point2I(10, 10), a, b));
		
		assertFalse(Shape2I.containsDifference(new Point2I(20, 10), a, b));
		assertFalse(Shape2I.containsDifference(new Point2I(30, 10), a, b));
		assertFalse(Shape2I.containsDifference(new Point2I(50, 10), a, b));
		
		assertThrows(NullPointerException.class, () -> Shape2I.containsDifference(new Point2I(), a, null));
		assertThrows(NullPointerException.class, () -> Shape2I.containsDifference(new Point2I(), null, b));
		assertThrows(NullPointerException.class, () -> Shape2I.containsDifference(null, a, b));
	}
	
	@Test
	public void testContainsIntersection() {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 10);
		final Circle2I b = new Circle2I(new Point2I(20, 10), 10);
		
		assertTrue(Shape2I.containsIntersection(new Point2I(15, 10), a, b));
		
		assertFalse(Shape2I.containsIntersection(new Point2I( 0, 10), a, b));
		assertFalse(Shape2I.containsIntersection(new Point2I(30, 10), a, b));
		assertFalse(Shape2I.containsIntersection(new Point2I(50, 10), a, b));
		
		assertThrows(NullPointerException.class, () -> Shape2I.containsIntersection(new Point2I(), a, null));
		assertThrows(NullPointerException.class, () -> Shape2I.containsIntersection(new Point2I(), null, b));
		assertThrows(NullPointerException.class, () -> Shape2I.containsIntersection(null, a, b));
	}
	
	@Test
	public void testContainsUnion() {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 10);
		final Circle2I b = new Circle2I(new Point2I(20, 10), 10);
		
		assertTrue(Shape2I.containsUnion(new Point2I( 0, 10), a, b));
		assertTrue(Shape2I.containsUnion(new Point2I(15, 10), a, b));
		assertTrue(Shape2I.containsUnion(new Point2I(30, 10), a, b));
		
		assertFalse(Shape2I.containsUnion(new Point2I(50, 10), a, b));
		
		assertThrows(NullPointerException.class, () -> Shape2I.containsUnion(new Point2I(), a, null));
		assertThrows(NullPointerException.class, () -> Shape2I.containsUnion(new Point2I(), null, b));
		assertThrows(NullPointerException.class, () -> Shape2I.containsUnion(null, a, b));
	}
	
	@Test
	public void testEquals() {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 20);
		final Circle2I b = new Circle2I(new Point2I(10, 10), 20);
		final Circle2I c = new Circle2I(new Point2I(10, 10), 30);
		final Circle2I d = new Circle2I(new Point2I(20, 20), 20);
		final Circle2I e = null;
		
		assertEquals(a, a);
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
		assertNotEquals(a, d);
		assertNotEquals(d, a);
		assertNotEquals(a, e);
		assertNotEquals(e, a);
	}
	
	@Test
	public void testFindPointClosestTo() {
		final Circle2I circle = new Circle2I(new Point2I(0, 0), 8);
		
		assertEquals(new Point2I(+0, +0), circle.findPointClosestTo(new Point2I(+0, +0)));
		assertEquals(new Point2I(-8, +0), circle.findPointClosestTo(new Point2I(-9, +0)));
		assertEquals(new Point2I(+8, +0), circle.findPointClosestTo(new Point2I(+9, +0)));
		assertEquals(new Point2I(+0, -8), circle.findPointClosestTo(new Point2I(+0, -9)));
		assertEquals(new Point2I(+0, +8), circle.findPointClosestTo(new Point2I(+0, +9)));
		
		assertThrows(NullPointerException.class, () -> circle.findPointClosestTo(null));
	}
	
	@Test
	public void testFindPoints() {
		final Circle2I circle = new Circle2I();
		
		final List<Point2I> points = circle.findPoints();
		
		assertNotNull(points);
		
		for(final Point2I point : points) {
			assertNotNull(point);
			
			assertTrue(circle.contains(point));
		}
	}
	
	@Test
	public void testFindPointsBoolean() {
		final Circle2I circle = new Circle2I();
		
		final List<Point2I> points = circle.findPoints(false);
		final List<Point2I> pointsIncludingBorderOnly = circle.findPoints(true);
		
		assertNotNull(points);
		assertNotNull(pointsIncludingBorderOnly);
		
		for(final Point2I point : points) {
			assertNotNull(point);
			
			assertTrue(circle.contains(point, false));
		}
		
		for(final Point2I point : pointsIncludingBorderOnly) {
			assertNotNull(point);
			
			assertTrue(circle.contains(point, true));
		}
	}
	
	@Test
	public void testFindPointsOfComplementShape2I() {
		final Circle2I circle = new Circle2I();
		
		final List<Point2I> pointsOfComplement = circle.findPointsOfComplement(circle);
		
		assertNotNull(pointsOfComplement);
		
		assertTrue(pointsOfComplement.isEmpty());
		
		assertThrows(NullPointerException.class, () -> circle.findPointsOfComplement(null));
	}
	
	@Test
	public void testFindPointsOfComplementShape2IBoolean() {
		final Circle2I circle = new Circle2I();
		
		final List<Point2I> points = circle.findPoints();
		final List<Point2I> pointsIncludingBorderOnly = circle.findPoints(true);
		final List<Point2I> pointsOfComplement = circle.findPointsOfComplement(circle, false);
		final List<Point2I> pointsOfComplementExcludingBorderOnly = circle.findPointsOfComplement(circle, true);
		
		points.removeAll(pointsIncludingBorderOnly);
		
		assertNotNull(pointsOfComplement);
		assertNotNull(pointsOfComplementExcludingBorderOnly);
		
		assertTrue(pointsOfComplement.isEmpty());
		
		assertEquals(points, pointsOfComplementExcludingBorderOnly);
		
		assertThrows(NullPointerException.class, () -> circle.findPointsOfComplement(null, false));
	}
	
	@Test
	public void testFindPointsOfIntersectionShape2I() {
		final Circle2I circle = new Circle2I();
		
		final List<Point2I> points = circle.findPoints();
		final List<Point2I> pointsOfIntersection = circle.findPointsOfIntersection(circle);
		
		assertNotNull(pointsOfIntersection);
		
		assertEquals(points, pointsOfIntersection);
		
		assertThrows(NullPointerException.class, () -> circle.findPointsOfIntersection(null));
	}
	
	@Test
	public void testFindPointsOfIntersectionShape2IBoolean() {
		final Circle2I circle = new Circle2I();
		
		final List<Point2I> points = circle.findPoints();
		final List<Point2I> pointsIncludingBorderOnly = circle.findPoints(true);
		final List<Point2I> pointsOfIntersection = circle.findPointsOfIntersection(circle, false);
		final List<Point2I> pointsOfIntersectionIncludingBorderOnly = circle.findPointsOfIntersection(circle, true);
		
		assertNotNull(pointsOfIntersection);
		assertNotNull(pointsOfIntersectionIncludingBorderOnly);
		
		assertEquals(points, pointsOfIntersection);
		assertEquals(pointsIncludingBorderOnly, pointsOfIntersectionIncludingBorderOnly);
		
		assertThrows(NullPointerException.class, () -> circle.findPointsOfIntersection(null, false));
	}
	
	@Test
	public void testGetCenter() {
		final Circle2I circle = new Circle2I(new Point2I(10, 10));
		
		assertEquals(new Point2I(10, 10), circle.getCenter());
	}
	
	@Test
	public void testGetID() {
		final Circle2I circle = new Circle2I(new Point2I(10, 10));
		
		assertEquals(Circle2I.ID, circle.getID());
	}
	
	@Test
	public void testGetName() {
		final Circle2I circle = new Circle2I(new Point2I(10, 10));
		
		assertEquals("Circle", circle.getName());
	}
	
	@Test
	public void testGetRadius() {
		final Circle2I circle = new Circle2I(new Point2I(), 20);
		
		assertEquals(20, circle.getRadius());
	}
	
	@Test
	public void testHashCode() {
		final Circle2I a = new Circle2I();
		final Circle2I b = new Circle2I();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersects() {
		final Circle2I a = new Circle2I(new Point2I(20, 20), 10);
		final Circle2I b = new Circle2I(new Point2I(30, 20), 10);
		final Circle2I c = new Circle2I(new Point2I(50, 20), 10);
		
		assertTrue(a.intersects(b));
		
		assertFalse(a.intersects(c));
		
		assertThrows(NullPointerException.class, () -> a.intersects(null));
	}
	
	@Test
	public void testMax() {
		final Circle2I circle = new Circle2I(new Point2I(20, 20), 10);
		
		assertEquals(new Point2I(30, 30), circle.max());
	}
	
	@Test
	public void testMidpoint() {
		final Circle2I circle = new Circle2I(new Point2I(20, 20), 10);
		
		assertEquals(new Point2I(20, 20), circle.midpoint());
	}
	
	@Test
	public void testMin() {
		final Circle2I circle = new Circle2I(new Point2I(20, 20), 10);
		
		assertEquals(new Point2I(10, 10), circle.min());
	}
	
	@Test
	public void testToString() {
		final Circle2I circle = new Circle2I(new Point2I(10, 10), 20);
		
		assertEquals("new Circle2I(new Point2I(10, 10), 20)", circle.toString());
	}
	
	@Test
	public void testWriteDataOutput() throws IOException {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 20);
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final DataInput dataInput = new DataInputStream(new ByteArrayInputStream(bytes));
		
		final int id = dataInput.readInt();
		
		assertEquals(Circle2I.ID, id);
		
		final Circle2I b = new Circle2I(Point2I.read(dataInput), dataInput.readInt());
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
	
	@Test
	public void testWriteFile() throws IOException {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 20);
		
		final File directory = new File("./generated");
		
		if(!directory.isDirectory()) {
			directory.mkdirs();
		}
		
		final File fileA = new File(directory, String.format("./Circle2I-%s.dat", Long.toString(System.currentTimeMillis())));
		final File fileB = new File("");
		
		a.write(fileA);
		
		try(final InputStream inputStream = new FileInputStream(fileA)) {
			final DataInput dataInput = new DataInputStream(inputStream);
			
			final int id = dataInput.readInt();
			
			assertEquals(Circle2I.ID, id);
			
			final Circle2I b = new Circle2I(Point2I.read(dataInput), dataInput.readInt());
			
			assertEquals(a, b);
		}
		
		assertThrows(NullPointerException.class, () -> a.write((File)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(fileB));
		
		fileA.delete();
		
		directory.delete();
	}
	
	@Test
	public void testWriteString() throws IOException {
		final Circle2I a = new Circle2I(new Point2I(10, 10), 20);
		
		final File directory = new File("./generated");
		
		if(!directory.isDirectory()) {
			directory.mkdirs();
		}
		
		final File fileA = new File(directory, String.format("./Circle2I-%s.dat", Long.toString(System.currentTimeMillis())));
		final File fileB = new File("");
		
		a.write(fileA.getAbsolutePath());
		
		try(final InputStream inputStream = new FileInputStream(fileA)) {
			final DataInput dataInput = new DataInputStream(inputStream);
			
			final int id = dataInput.readInt();
			
			assertEquals(Circle2I.ID, id);
			
			final Circle2I b = new Circle2I(Point2I.read(dataInput), dataInput.readInt());
			
			assertEquals(a, b);
		}
		
		assertThrows(NullPointerException.class, () -> a.write((String)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(fileB.getAbsolutePath()));
		
		fileA.delete();
		
		directory.delete();
	}
}