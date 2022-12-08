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
package org.macroing.geo4j.shape.ls;

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
import java.io.UncheckedIOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.bv.BoundingVolume3F;
import org.macroing.geo4j.bv.aabb.AxisAlignedBoundingBox3F;
import org.macroing.geo4j.common.Point3F;
import org.macroing.geo4j.common.Vector3F;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.ray.Ray3F;

@SuppressWarnings("static-method")
public final class LineSegment3FUnitTests {
	public LineSegment3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstants() {
		assertEquals(2, LineSegment3F.ID);
		assertEquals("Line Segment", LineSegment3F.NAME);
	}
	
	@Test
	public void testConstructor() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(new Point3F(10.0F, 10.0F, 10.0F), lineSegment.getA());
		assertEquals(new Point3F(20.0F, 10.0F, 10.0F), lineSegment.getB());
		
		assertThrows(NullPointerException.class, () -> new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), null));
		assertThrows(NullPointerException.class, () -> new LineSegment3F(null, new Point3F(20.0F, 10.0F, 10.0F)));
	}
	
	@Test
	public void testContains() {
		final List<LineSegment3F> lineSegments = LineSegment3F.fromPoints(new Point3F(10.0F, 0.0F, 0.0F), new Point3F(20.0F, 0.0F, 0.0F), new Point3F(30.0F, 10.0F, 0.0F), new Point3F(30.0F, 20.0F, 0.0F), new Point3F(20.0F, 30.0F, 0.0F), new Point3F(10.0F, 30.0F, 0.0F), new Point3F(0.0F, 20.0F, 0.0F), new Point3F(0.0F, 10.0F, 0.0F));
		
		for(final LineSegment3F lineSegment : lineSegments) {
			assertTrue(lineSegment.contains(lineSegment.getA()));
			assertTrue(lineSegment.contains(Point3F.midpoint(lineSegment.getA(), lineSegment.getB())));
			assertTrue(lineSegment.contains(lineSegment.getB()));
			
			assertFalse(lineSegment.contains(new Point3F(lineSegment.getA().x + 100.0F, lineSegment.getA().y, lineSegment.getA().z)));
			assertFalse(lineSegment.contains(new Point3F(lineSegment.getA().x - 100.0F, lineSegment.getA().y, lineSegment.getA().z)));
			
			assertFalse(lineSegment.contains(new Point3F(lineSegment.getA().x, lineSegment.getA().y + 100.0F, lineSegment.getA().z)));
			assertFalse(lineSegment.contains(new Point3F(lineSegment.getA().x, lineSegment.getA().y - 100.0F, lineSegment.getA().z)));
			
			assertFalse(lineSegment.contains(new Point3F(lineSegment.getA().x, lineSegment.getA().y, lineSegment.getA().z + 100.0F)));
			assertFalse(lineSegment.contains(new Point3F(lineSegment.getA().x, lineSegment.getA().y, lineSegment.getA().z - 100.0F)));
		}
		
		assertThrows(NullPointerException.class, () -> new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F)).contains(null));
	}
	
	@Test
	public void testEquals() {
		final LineSegment3F a = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		final LineSegment3F b = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		final LineSegment3F c = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(30.0F, 30.0F, 30.0F));
		final LineSegment3F d = new LineSegment3F(new Point3F(30.0F, 30.0F, 30.0F), new Point3F(20.0F, 10.0F, 10.0F));
		final LineSegment3F e = null;
		
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
	public void testFromPoints() {
		final List<LineSegment3F> lineSegments = LineSegment3F.fromPoints(new Point3F(0.0F, 0.0F, 0.0F), new Point3F(10.0F, 0.0F, 0.0F), new Point3F(10.0F, 10.0F, 0.0F), new Point3F(0.0F, 10.0F, 0.0F));
		
		assertNotNull(lineSegments);
		
		assertEquals(4, lineSegments.size());
		
		assertEquals(new LineSegment3F(new Point3F( 0.0F,  0.0F, 0.0F), new Point3F(10.0F,  0.0F, 0.0F)), lineSegments.get(0));
		assertEquals(new LineSegment3F(new Point3F(10.0F,  0.0F, 0.0F), new Point3F(10.0F, 10.0F, 0.0F)), lineSegments.get(1));
		assertEquals(new LineSegment3F(new Point3F(10.0F, 10.0F, 0.0F), new Point3F( 0.0F, 10.0F, 0.0F)), lineSegments.get(2));
		assertEquals(new LineSegment3F(new Point3F( 0.0F, 10.0F, 0.0F), new Point3F( 0.0F,  0.0F, 0.0F)), lineSegments.get(3));
		
		assertThrows(IllegalArgumentException.class, () -> LineSegment3F.fromPoints(new Point3F()));
		assertThrows(NullPointerException.class, () -> LineSegment3F.fromPoints((Point3F[])(null)));
		assertThrows(NullPointerException.class, () -> LineSegment3F.fromPoints(new Point3F[] {new Point3F(), null}));
	}
	
	@Test
	public void testGetA() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(new Point3F(10.0F, 10.0F, 10.0F), lineSegment.getA());
	}
	
	@Test
	public void testGetB() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(new Point3F(20.0F, 10.0F, 10.0F), lineSegment.getB());
	}
	
	@Test
	public void testGetBoundingVolume() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		final BoundingVolume3F a = new AxisAlignedBoundingBox3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		final BoundingVolume3F b = lineSegment.getBoundingVolume();
		
		assertEquals(a, b);
	}
	
	@Test
	public void testGetID() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(LineSegment3F.ID, lineSegment.getID());
	}
	
	@Test
	public void testGetName() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(LineSegment3F.NAME, lineSegment.getName());
	}
	
	@Test
	public void testGetSurfaceArea() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(0.0F, lineSegment.getSurfaceArea());
	}
	
	@Test
	public void testHashCode() {
		final LineSegment3F a = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		final LineSegment3F b = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersectionT() {
		assertEquals(2.0F, new LineSegment3F(new Point3F(0.0F, 5.0F, 7.0F), new Point3F(9.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1000.0F));
		assertEquals(2.0F, new LineSegment3F(new Point3F(0.0F, 5.0F, 7.0F), new Point3F(5.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1000.0F));
		assertEquals(2.0F, new LineSegment3F(new Point3F(5.0F, 5.0F, 7.0F), new Point3F(9.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1000.0F));
		
		assertEquals(0.0000009536743F, new LineSegment3F(new Point3F(5.0F, 5.0F, 5.000001F), new Point3F(9.0F, 5.0F, 5.000001F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1000.0F));
		
		assertEquals(Float.NaN, new LineSegment3F(new Point3F(0.0F, 5.0F, 7.0F), new Point3F(4.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1000.0F));
		assertEquals(Float.NaN, new LineSegment3F(new Point3F(6.0F, 5.0F, 7.0F), new Point3F(9.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1000.0F));
		
		assertEquals(Float.NaN, new LineSegment3F(new Point3F(0.0F, 5.0F, 7.0F), new Point3F(9.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 0.0F, 1.0F));
		assertEquals(Float.NaN, new LineSegment3F(new Point3F(0.0F, 5.0F, 7.0F), new Point3F(9.0F, 5.0F, 7.0F)).intersectionT(new Ray3F(new Point3F(5.0F, 5.0F, 5.0F), new Vector3F(0.0F, 0.0F, 1.0F)), 3.0F, 1000.0F));
		
		assertThrows(NullPointerException.class, () -> new LineSegment3F(new Point3F(0.0F, 5.0F, 7.0F), new Point3F(9.0F, 5.0F, 7.0F)).intersectionT(null, 0.0F, 1000.0F));
	}
	
	@Test
	public void testToString() {
		final LineSegment3F lineSegment = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		assertEquals("new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F))", lineSegment.toString());
	}
	
	@Test
	public void testWrite() {
		final LineSegment3F a = new LineSegment3F(new Point3F(10.0F, 10.0F, 10.0F), new Point3F(20.0F, 10.0F, 10.0F));
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final LineSegment3F b = new LineSegment3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}