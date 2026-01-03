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
package org.macroing.geo4j.shape.cone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.UncheckedIOException;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.AngleF;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Cone3FUnitTests {
	public Cone3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Cone3F cone = new Cone3F();
		
		assertTrue(cone.accept(new NodeHierarchicalVisitorMock(node -> false,             node -> node.equals(cone))));
		assertTrue(cone.accept(new NodeHierarchicalVisitorMock(node -> node.equals(cone), node -> node.equals(cone))));
		
		assertThrows(NodeTraversalException.class, () -> cone.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> cone.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Cone3F cone = new Cone3F();
		
		assertThrows(NodeTraversalException.class, () -> cone.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> cone.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(2, Cone3F.ID);
		assertEquals("Cone", Cone3F.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Cone3F cone = new Cone3F();
		
		assertEquals(AngleF.degrees(360.0F), cone.getPhiMax());
		assertEquals(1.0F, cone.getRadius());
		assertEquals(1.0F, cone.getZMax());
	}
	
	@Test
	public void testConstructorAngleF() {
		final Cone3F cone = new Cone3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), cone.getPhiMax());
		assertEquals(1.0F, cone.getRadius());
		assertEquals(1.0F, cone.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Cone3F(null));
	}
	
	@Test
	public void testConstructorAngleFFloat() {
		final Cone3F cone = new Cone3F(AngleF.degrees(180.0F), 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), cone.getPhiMax());
		assertEquals(2.0F, cone.getRadius());
		assertEquals(1.0F, cone.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Cone3F(null, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloat() {
		final Cone3F cone = new Cone3F(AngleF.degrees(180.0F), 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), cone.getPhiMax());
		assertEquals(2.0F, cone.getRadius());
		assertEquals(2.0F, cone.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Cone3F(null, 2.0F, 2.0F));
	}
	
	@Test
	public void testEquals() {
		final Cone3F a = new Cone3F(AngleF.degrees(360.0F), 1.0F, 1.0F);
		final Cone3F b = new Cone3F(AngleF.degrees(360.0F), 1.0F, 1.0F);
		final Cone3F c = new Cone3F(AngleF.degrees(360.0F), 1.0F, 2.0F);
		final Cone3F d = new Cone3F(AngleF.degrees(360.0F), 2.0F, 1.0F);
		final Cone3F e = new Cone3F(AngleF.degrees(180.0F), 1.0F, 1.0F);
		final Cone3F f = null;
		
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
	public void testGetID() {
		final Cone3F cone = new Cone3F();
		
		assertEquals(Cone3F.ID, cone.getID());
	}
	
	@Test
	public void testGetName() {
		final Cone3F cone = new Cone3F();
		
		assertEquals(Cone3F.NAME, cone.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Cone3F cone = new Cone3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), cone.getPhiMax());
	}
	
	@Test
	public void testGetRadius() {
		final Cone3F cone = new Cone3F(AngleF.degrees(360.0F), 2.0F);
		
		assertEquals(2.0F, cone.getRadius());
	}
	
	@Test
	public void testGetZMax() {
		final Cone3F cone = new Cone3F(AngleF.degrees(360.0F), 1.0F, 2.0F);
		
		assertEquals(2.0F, cone.getZMax());
	}
	
	@Test
	public void testHashCode() {
		final Cone3F a = new Cone3F();
		final Cone3F b = new Cone3F();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final Cone3F cone = new Cone3F(AngleF.degrees(360.0F), 1.0F, 1.0F);
		
		assertEquals("new Cone3F(AngleF.degrees(360.0F, 0.0F, 360.0F), 1.0F, 1.0F)", cone.toString());
	}
	
	@Test
	public void testWrite() {
		final Cone3F a = new Cone3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Cone3F b = new Cone3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}