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
package org.macroing.geo4j.shape.torus;

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

import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Torus3FUnitTests {
	public Torus3FUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Torus3F torus = new Torus3F();
		
		assertTrue(torus.accept(new NodeHierarchicalVisitorMock(node -> false,              node -> node.equals(torus))));
		assertTrue(torus.accept(new NodeHierarchicalVisitorMock(node -> node.equals(torus), node -> node.equals(torus))));
		
		assertThrows(NodeTraversalException.class, () -> torus.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> torus.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Torus3F torus = new Torus3F();
		
		assertThrows(NodeTraversalException.class, () -> torus.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> torus.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(17, Torus3F.ID);
		assertEquals("Torus", Torus3F.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Torus3F torus = new Torus3F();
		
		assertEquals(0.25F, torus.getRadiusInner());
		assertEquals(1.00F, torus.getRadiusOuter());
	}
	
	@Test
	public void testConstructorFloatFloat() {
		final Torus3F torus = new Torus3F(0.5F, 2.0F);
		
		assertEquals(0.5F, torus.getRadiusInner());
		assertEquals(2.0F, torus.getRadiusOuter());
	}
	
	@Test
	public void testEquals() {
		final Torus3F a = new Torus3F(0.25F, 1.0F);
		final Torus3F b = new Torus3F(0.25F, 1.0F);
		final Torus3F c = new Torus3F(0.25F, 2.0F);
		final Torus3F d = new Torus3F(0.50F, 1.0F);
		final Torus3F e = null;
		
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
	public void testGetID() {
		final Torus3F torus = new Torus3F();
		
		assertEquals(Torus3F.ID, torus.getID());
	}
	
	@Test
	public void testGetName() {
		final Torus3F torus = new Torus3F();
		
		assertEquals(Torus3F.NAME, torus.getName());
	}
	
	@Test
	public void testGetRadiusInner() {
		final Torus3F torus = new Torus3F(0.5F, 2.0F);
		
		assertEquals(0.5F, torus.getRadiusInner());
	}
	
	@Test
	public void testGetRadiusOuter() {
		final Torus3F torus = new Torus3F(0.5F, 2.0F);
		
		assertEquals(2.0F, torus.getRadiusOuter());
	}
	
	@Test
	public void testHashCode() {
		final Torus3F a = new Torus3F();
		final Torus3F b = new Torus3F();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testToString() {
		final Torus3F torus = new Torus3F(0.5F, 2.0F);
		
		assertEquals("new Torus3F(0.5F, 2.0F)", torus.toString());
	}
	
	@Test
	public void testWrite() {
		final Torus3F a = new Torus3F();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Torus3F b = new Torus3FReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}