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
package org.macroing.geo4j.shape.disk;

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
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.macroing.geo4j.common.AngleD;
import org.macroing.geo4j.common.Point2D;
import org.macroing.geo4j.common.Point3D;
import org.macroing.geo4j.common.Vector3D;
import org.macroing.geo4j.mock.DataOutputMock;
import org.macroing.geo4j.mock.NodeHierarchicalVisitorMock;
import org.macroing.geo4j.mock.NodeVisitorMock;
import org.macroing.geo4j.onb.OrthonormalBasis33D;
import org.macroing.geo4j.ray.Ray3D;
import org.macroing.geo4j.shape.SurfaceIntersection3D;
import org.macroing.java.util.visitor.NodeHierarchicalVisitor;
import org.macroing.java.util.visitor.NodeTraversalException;
import org.macroing.java.util.visitor.NodeVisitor;

@SuppressWarnings("static-method")
public final class Disk3DUnitTests {
	public Disk3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Disk3D disk = new Disk3D();
		
		assertTrue(disk.accept(new NodeHierarchicalVisitorMock(node -> false,             node -> node.equals(disk))));
		assertTrue(disk.accept(new NodeHierarchicalVisitorMock(node -> node.equals(disk), node -> node.equals(disk))));
		
		assertThrows(NodeTraversalException.class, () -> disk.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> disk.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Disk3D disk = new Disk3D();
		
		assertThrows(NodeTraversalException.class, () -> disk.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> disk.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(7, Disk3D.ID);
		assertEquals("Disk", Disk3D.NAME);
	}
	
	@Test
	public void testConstructor() {
		final Disk3D disk = new Disk3D();
		
		assertEquals(AngleD.degrees(360.0D), disk.getPhiMax());
		assertEquals(0.0D, disk.getRadiusInner());
		assertEquals(1.0D, disk.getRadiusOuter());
		assertEquals(0.0D, disk.getZMax());
	}
	
	@Test
	public void testConstructorAngleD() {
		final Disk3D disk = new Disk3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), disk.getPhiMax());
		assertEquals(0.0D, disk.getRadiusInner());
		assertEquals(1.0D, disk.getRadiusOuter());
		assertEquals(0.0D, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3D(null));
	}
	
	@Test
	public void testConstructorAngleDDouble() {
		final Disk3D disk = new Disk3D(AngleD.degrees(180.0D), 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), disk.getPhiMax());
		assertEquals(2.0D, disk.getRadiusInner());
		assertEquals(1.0D, disk.getRadiusOuter());
		assertEquals(0.0D, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3D(null, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDouble() {
		final Disk3D disk = new Disk3D(AngleD.degrees(180.0D), 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), disk.getPhiMax());
		assertEquals(2.0D, disk.getRadiusInner());
		assertEquals(2.0D, disk.getRadiusOuter());
		assertEquals(0.0D, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3D(null, 2.0D, 2.0D));
	}
	
	@Test
	public void testConstructorAngleDDoubleDoubleDouble() {
		final Disk3D disk = new Disk3D(AngleD.degrees(180.0D), 2.0D, 2.0D, 2.0D);
		
		assertEquals(AngleD.degrees(180.0D), disk.getPhiMax());
		assertEquals(2.0D, disk.getRadiusInner());
		assertEquals(2.0D, disk.getRadiusOuter());
		assertEquals(2.0D, disk.getZMax());
		
		assertThrows(NullPointerException.class, () -> new Disk3D(null, 2.0D, 2.0D, 2.0D));
	}
	
	@Test
	public void testEquals() {
		final Disk3D a = new Disk3D(AngleD.degrees(360.0D), 0.0D, 1.0D, 0.0D);
		final Disk3D b = new Disk3D(AngleD.degrees(360.0D), 0.0D, 1.0D, 0.0D);
		final Disk3D c = new Disk3D(AngleD.degrees(360.0D), 0.0D, 1.0D, 2.0D);
		final Disk3D d = new Disk3D(AngleD.degrees(360.0D), 0.0D, 2.0D, 0.0D);
		final Disk3D e = new Disk3D(AngleD.degrees(360.0D), 2.0D, 1.0D, 0.0D);
		final Disk3D f = new Disk3D(AngleD.degrees(180.0D), 0.0D, 1.0D, 0.0D);
		final Disk3D g = null;
		
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
		final Disk3D disk = new Disk3D();
		
		assertEquals(Disk3D.ID, disk.getID());
	}
	
	@Test
	public void testGetName() {
		final Disk3D disk = new Disk3D();
		
		assertEquals(Disk3D.NAME, disk.getName());
	}
	
	@Test
	public void testGetPhiMax() {
		final Disk3D disk = new Disk3D(AngleD.degrees(180.0D));
		
		assertEquals(AngleD.degrees(180.0D), disk.getPhiMax());
	}
	
	@Test
	public void testGetRadiusInner() {
		final Disk3D disk = new Disk3D(AngleD.degrees(360.0D), 2.0D);
		
		assertEquals(2.0D, disk.getRadiusInner());
	}
	
	@Test
	public void testGetRadiusOuter() {
		final Disk3D disk = new Disk3D(AngleD.degrees(360.0D), 0.0D, 2.0D);
		
		assertEquals(2.0D, disk.getRadiusOuter());
	}
	
	@Test
	public void testGetZMax() {
		final Disk3D disk = new Disk3D(AngleD.degrees(360.0D), 0.0D, 1.0D, 2.0D);
		
		assertEquals(2.0D, disk.getZMax());
	}
	
	@Test
	public void testHashCode() {
		final Disk3D a = new Disk3D();
		final Disk3D b = new Disk3D();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersectionRay3DDoubleDouble() {
		final Disk3D disk = new Disk3D();
		
		final Optional<SurfaceIntersection3D> optionalSurfaceIntersection = disk.intersection(new Ray3D(new Point3D(0.1D, 0.0D, -1.0D), Vector3D.z()), 0.0D, 3.0D);
		
		assertNotNull(optionalSurfaceIntersection);
		
		assertTrue(optionalSurfaceIntersection.isPresent());
		
		final SurfaceIntersection3D surfaceIntersection = optionalSurfaceIntersection.get();
		
		assertEquals(new OrthonormalBasis33D(Vector3D.z(), new Vector3D(-1.0D, -0.0D, 0.0D)), surfaceIntersection.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33D(Vector3D.z(), new Vector3D(-1.0D, -0.0D, 0.0D)), surfaceIntersection.getOrthonormalBasisS());
		assertEquals(new Ray3D(new Point3D(0.1D, 0.0D, -1.0D), Vector3D.z()), surfaceIntersection.getRay());
		assertEquals(disk, surfaceIntersection.getShape());
		assertEquals(new Point3D(0.1D, 0.0D, 0.0D), surfaceIntersection.getSurfaceIntersectionPoint());
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), surfaceIntersection.getSurfaceNormalG());
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), surfaceIntersection.getSurfaceNormalS());
		assertEquals(1.0D, surfaceIntersection.getT());
		assertEquals(new Point2D(0.0D, 0.9D), surfaceIntersection.getTextureCoordinates());
		
		assertFalse(disk.intersection(new Ray3D(new Point3D(0.1D, 0.0D, -1.0D), Vector3D.z()), 0.0D, 1.0D).isPresent());
		
		assertThrows(NullPointerException.class, () -> disk.intersection(null, 0.0D, 0.0D));
	}
	
	@Test
	public void testToString() {
		final Disk3D disk = new Disk3D(AngleD.degrees(360.0D), 1.0D, 1.0D, 1.0D);
		
		assertEquals("new Disk3D(AngleD.degrees(360.0D, 0.0D, 360.0D), 1.0D, 1.0D, 1.0D)", disk.toString());
	}
	
	@Test
	public void testWrite() {
		final Disk3D a = new Disk3D();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Disk3D b = new Disk3DReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}