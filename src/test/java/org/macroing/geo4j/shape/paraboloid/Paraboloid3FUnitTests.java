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
package org.macroing.geo4j.shape.paraboloid;

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
public final class Paraboloid3FUnitTests {
	public Paraboloid3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Paraboloid3F paraboloid = new Paraboloid3F();
		
		assertTrue(paraboloid.accept(new NodeHierarchicalVisitorMock(node -> false,                   node -> node.equals(paraboloid))));
		assertTrue(paraboloid.accept(new NodeHierarchicalVisitorMock(node -> node.equals(paraboloid), node -> node.equals(paraboloid))));
		
		assertThrows(NodeTraversalException.class, () -> paraboloid.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> paraboloid.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Paraboloid3F paraboloid = new Paraboloid3F();
		
		assertThrows(NodeTraversalException.class, () -> paraboloid.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> paraboloid.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(10, Paraboloid3F.ID);
		assertEquals("Paraboloid", Paraboloid3F.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Paraboloid3F paraboloid = new Paraboloid3F();
		
		assertEquals(AngleF.degrees(360.0F), paraboloid.getPhiMax());
		assertEquals(1.0F, paraboloid.getRadius());
		assertEquals(1.0F, paraboloid.getZMax());
		assertEquals(0.0F, paraboloid.getZMin());
	}
	
	@Test
	public void testConstructorAngleF() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), paraboloid.getPhiMax());
		assertEquals(1.0F, paraboloid.getRadius());
		assertEquals(1.0F, paraboloid.getZMax());
		assertEquals(0.0F, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3F(null));
	}
	
	@Test
	public void testConstructorAngleFFloat() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(180.0F), 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), paraboloid.getPhiMax());
		assertEquals(2.0F, paraboloid.getRadius());
		assertEquals(1.0F, paraboloid.getZMax());
		assertEquals(0.0F, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3F(null, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloat() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(180.0F), 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), paraboloid.getPhiMax());
		assertEquals(2.0F, paraboloid.getRadius());
		assertEquals(2.0F, paraboloid.getZMax());
		assertEquals(0.0F, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3F(null, 2.0F, 2.0F));
	}
	
	@Test
	public void testConstructorAngleFFloatFloatFloat() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(180.0F), 2.0F, 2.0F, 2.0F);
		
		assertEquals(AngleF.degrees(180.0F), paraboloid.getPhiMax());
		assertEquals(2.0F, paraboloid.getRadius());
		assertEquals(2.0F, paraboloid.getZMax());
		assertEquals(2.0F, paraboloid.getZMin());
		
		assertThrows(NullPointerException.class, () -> new Paraboloid3F(null, 2.0F, 2.0F, 2.0F));
	}
	
	@Test
	public void testEquals() {
		final Paraboloid3F a = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 1.0F, 0.0F);
		final Paraboloid3F b = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 1.0F, 0.0F);
		final Paraboloid3F c = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 1.0F, 2.0F);
		final Paraboloid3F d = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 2.0F, 0.0F);
		final Paraboloid3F e = new Paraboloid3F(AngleF.degrees(360.0F), 2.0F, 1.0F, 0.0F);
		final Paraboloid3F f = new Paraboloid3F(AngleF.degrees(180.0F), 1.0F, 1.0F, 0.0F);
		final Paraboloid3F g = null;
		
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
	}
	
	@Test
	public void testGetID() {
		final Paraboloid3F paraboloid = new Paraboloid3F();
		
		assertEquals(Paraboloid3F.ID, paraboloid.getID());
	}
	
	@Test
	public void testGetName() {
		final Paraboloid3F paraboloid = new Paraboloid3F();
		
		assertEquals(Paraboloid3F.NAME, paraboloid.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(180.0F));
		
		assertEquals(AngleF.degrees(180.0F), paraboloid.getPhiMax());
	}
	
	@Test
	public void testGetRadius() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(360.0F), 2.0F);
		
		assertEquals(2.0F, paraboloid.getRadius());
	}
	
	@Test
	public void testGetZMax() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 2.0F);
		
		assertEquals(2.0F, paraboloid.getZMax());
	}
	
	@Test
	public void testGetZMin() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 1.0F, 2.0F);
		
		assertEquals(2.0F, paraboloid.getZMin());
	}
	
	@Test
	public void testHashCode() {
		final Paraboloid3F a = new Paraboloid3F();
		final Paraboloid3F b = new Paraboloid3F();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final Paraboloid3F paraboloid = new Paraboloid3F(AngleF.degrees(360.0F), 1.0F, 1.0F, -1.0F);
		
		assertEquals("new Paraboloid3F(AngleF.degrees(360.0F, 0.0F, 360.0F), 1.0F, 1.0F, -1.0F)", paraboloid.toString());
	}
	
	@Test
	public void testWrite() {
		final Paraboloid3F a = new Paraboloid3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Paraboloid3F b = new Paraboloid3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}