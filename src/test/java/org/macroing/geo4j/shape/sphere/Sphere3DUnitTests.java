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
package org.macroing.geo4j.shape.sphere;

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
import org.macroing.geo4j.bv.bs.BoundingSphere3D;
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
public final class Sphere3DUnitTests {
	public Sphere3DUnitTests() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testAcceptNodeHierarchicalVisitor() {
		final Sphere3D sphere = new Sphere3D();
		
		assertTrue(sphere.accept(new NodeHierarchicalVisitorMock(node -> false,               node -> node.equals(sphere))));
		assertTrue(sphere.accept(new NodeHierarchicalVisitorMock(node -> node.equals(sphere), node -> node.equals(sphere))));
		
		assertThrows(NodeTraversalException.class, () -> sphere.accept(new NodeHierarchicalVisitorMock(null, null)));
		assertThrows(NullPointerException.class, () -> sphere.accept((NodeHierarchicalVisitor)(null)));
	}
	
	@Test
	public void testAcceptNodeVisitor() {
		final Sphere3D sphere = new Sphere3D();
		
		assertThrows(NodeTraversalException.class, () -> sphere.accept(new NodeVisitorMock(true)));
		assertThrows(NullPointerException.class, () -> sphere.accept((NodeVisitor)(null)));
	}
	
	@Test
	public void testConstants() {
		assertEquals(16, Sphere3D.ID);
		assertEquals("Sphere", Sphere3D.NAME);
	}
	
	@Test
	public void testContains() {
		final Sphere3D sphere = new Sphere3D();
		
		assertTrue(sphere.contains(new Point3D(0.0D, 0.0D, 0.0D)));
		
		assertFalse(sphere.contains(new Point3D(2.0D, 2.0D, 2.0D)));
		
		assertThrows(NullPointerException.class, () -> sphere.contains(null));
	}
	
	@Test
	public void testEquals() {
		final Sphere3D a = new Sphere3D();
		final Sphere3D b = new Sphere3D();
		final Sphere3D c = null;
		
		assertEquals(a, a);
		
		assertEquals(a, b);
		assertEquals(b, a);
		
		assertNotEquals(a, c);
		assertNotEquals(c, a);
	}
	
	@Test
	public void testGetBoundingVolume() {
		final Sphere3D sphere = new Sphere3D();
		
		assertEquals(new BoundingSphere3D(1.0D, new Point3D(0.0D, 0.0D, 0.0D)), sphere.getBoundingVolume());
	}
	
	@Test
	public void testGetID() {
		final Sphere3D sphere = new Sphere3D();
		
		assertEquals(Sphere3D.ID, sphere.getID());
	}
	
	@Test
	public void testGetName() {
		final Sphere3D sphere = new Sphere3D();
		
		assertEquals(Sphere3D.NAME, sphere.getName());
	}
	
	@Test
	public void testGetSurfaceArea() {
		final Sphere3D sphere = new Sphere3D();
		
		assertEquals(Math.PI * 4.0D, sphere.getSurfaceArea());
	}
	
	@Test
	public void testHashCode() {
		final Sphere3D a = new Sphere3D();
		final Sphere3D b = new Sphere3D();
		
		assertEquals(a.hashCode(), a.hashCode());
		assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void testIntersectionRay3DDoubleDouble() {
		final Sphere3D sphere = new Sphere3D();
		
//		Test intersection A:
		final Optional<SurfaceIntersection3D> optionalSurfaceIntersectionA = sphere.intersection(new Ray3D(new Point3D(0.0D, 0.0D, +2.0D), new Vector3D(0.0D, 0.0D, -1.0D)), 0.0D, 3.0D);
		
		assertNotNull(optionalSurfaceIntersectionA);
		
		assertTrue(optionalSurfaceIntersectionA.isPresent());
		
		final SurfaceIntersection3D surfaceIntersectionA = optionalSurfaceIntersectionA.get();
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D)), surfaceIntersectionA.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, 1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D)), surfaceIntersectionA.getOrthonormalBasisS());
		assertEquals(new Ray3D(new Point3D(0.0D, 0.0D, 2.0D), new Vector3D(0.0D, 0.0D, -1.0D)), surfaceIntersectionA.getRay());
		assertEquals(sphere, surfaceIntersectionA.getShape());
		assertEquals(new Point3D(0.0D, 0.0D, 1.0D), surfaceIntersectionA.getSurfaceIntersectionPoint());
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), surfaceIntersectionA.getSurfaceNormalG());
		assertEquals(new Vector3D(0.0D, 0.0D, 1.0D), surfaceIntersectionA.getSurfaceNormalS());
		assertEquals(1.0D, surfaceIntersectionA.getT());
		assertEquals(new Point2D(0.0D, 0.0D), surfaceIntersectionA.getTextureCoordinates());
		
//		Test intersection B:
		final Optional<SurfaceIntersection3D> optionalSurfaceIntersectionB = sphere.intersection(new Ray3D(new Point3D(0.0D, 0.0D, -2.0D), new Vector3D(0.0D, 0.0D, 1.0D)), 0.0D, 3.0D);
		
		assertNotNull(optionalSurfaceIntersectionB);
		
		assertTrue(optionalSurfaceIntersectionB.isPresent());
		
		final SurfaceIntersection3D surfaceIntersectionB = optionalSurfaceIntersectionB.get();
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(-1.0D, 0.0D, 0.0D)), surfaceIntersectionB.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33D(new Vector3D(0.0D, 0.0D, -1.0D), new Vector3D(0.0D, 1.0D, 0.0D), new Vector3D(-1.0D, 0.0D, 0.0D)), surfaceIntersectionB.getOrthonormalBasisS());
		assertEquals(new Ray3D(new Point3D(0.0D, 0.0D, -2.0D), new Vector3D(0.0D, 0.0D, 1.0D)), surfaceIntersectionB.getRay());
		assertEquals(sphere, surfaceIntersectionB.getShape());
		assertEquals(new Point3D(0.0D, 0.0D, -1.0D), surfaceIntersectionB.getSurfaceIntersectionPoint());
		assertEquals(new Vector3D(0.0D, 0.0D, -1.0D), surfaceIntersectionB.getSurfaceNormalG());
		assertEquals(new Vector3D(0.0D, 0.0D, -1.0D), surfaceIntersectionB.getSurfaceNormalS());
		assertEquals(1.0D, surfaceIntersectionB.getT());
		assertEquals(new Point2D(0.0D, 1.0D), surfaceIntersectionB.getTextureCoordinates());
		
//		Test intersection C:
		final Optional<SurfaceIntersection3D> optionalSurfaceIntersectionC = sphere.intersection(new Ray3D(new Point3D(-1.0D, -1.0D, -1.0D), Vector3D.directionNormalized(new Point3D(-1.0D, -1.0D, -1.0D), new Point3D())), 0.0D, 3.0D);
		
		assertNotNull(optionalSurfaceIntersectionC);
		
		assertTrue(optionalSurfaceIntersectionC.isPresent());
		
		final SurfaceIntersection3D surfaceIntersectionC = optionalSurfaceIntersectionC.get();
		
		assertEquals(new OrthonormalBasis33D(new Vector3D(-0.5773502691896258D, -0.5773502691896258D, -0.5773502691896258D), new Vector3D(0.7071067811865476D, -0.7071067811865476D, 0.0D), new Vector3D(0.408248290463863D, 0.408248290463863D, -0.816496580927726D)), surfaceIntersectionC.getOrthonormalBasisG());
		assertEquals(new OrthonormalBasis33D(new Vector3D(-0.5773502691896258D, -0.5773502691896258D, -0.5773502691896258D), new Vector3D(0.7071067811865476D, -0.7071067811865476D, 0.0D), new Vector3D(0.408248290463863D, 0.408248290463863D, -0.816496580927726D)), surfaceIntersectionC.getOrthonormalBasisS());
		assertEquals(new Ray3D(new Point3D(-1.0D, -1.0D, -1.0D), Vector3D.directionNormalized(new Point3D(-1.0D, -1.0D, -1.0D), new Point3D())), surfaceIntersectionC.getRay());
		assertEquals(sphere, surfaceIntersectionC.getShape());
		assertEquals(new Point3D(-0.5773502691896258D, -0.5773502691896258D, -0.5773502691896258D), surfaceIntersectionC.getSurfaceIntersectionPoint());
		assertEquals(new Vector3D(-0.5773502691896258D, -0.5773502691896258D, -0.5773502691896258D), surfaceIntersectionC.getSurfaceNormalG());
		assertEquals(new Vector3D(-0.5773502691896258D, -0.5773502691896258D, -0.5773502691896258D), surfaceIntersectionC.getSurfaceNormalS());
		assertEquals(Vector3D.direction(new Point3D(-1.0D, -1.0D, -1.0D), new Point3D(-0.5773502691896258D, -0.5773502691896258D, -0.5773502691896258D)).length(), surfaceIntersectionC.getT());
		assertEquals(new Point2D(0.625D, 0.6959132760153038D), surfaceIntersectionC.getTextureCoordinates());
		
//		Test no intersection:
		assertFalse(sphere.intersection(new Ray3D(new Point3D(1.0D, 0.0D, 0.0D), new Vector3D(1.0D, 0.0D, 0.0D)), 0.0D, 1.0D).isPresent());
		
		assertThrows(NullPointerException.class, () -> sphere.intersection(null, 0.0D, 0.0D));
	}
	
//	@Test
//	public void testIntersectionSurfaceIntersector3D() {
//		
//	}
	
	@Test
	public void testIntersectionT() {
		final Sphere3D sphere = new Sphere3D();
		
		assertEquals(Double.NaN, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, +2.0D), new Vector3D(0.0D, 0.0D, -1.0D)), 0.0D, 1.0D));
		assertEquals(Double.NaN, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, +2.0D), new Vector3D(0.0D, 0.0D, +1.0D)), 0.0D, 1.0D));
		assertEquals(Double.NaN, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, -2.0D), new Vector3D(0.0D, 0.0D, -1.0D)), 0.0D, 1.0D));
		assertEquals(Double.NaN, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, -2.0D), new Vector3D(0.0D, 0.0D, +1.0D)), 0.0D, 1.0D));
		
		assertEquals(Double.NaN, sphere.intersectionT(new Ray3D(new Point3D(2.0D, 0.0D, 0.0D), new Vector3D(0.0D, 0.0D, +1.0D)), 0.0D, 2.0D));
		
		assertEquals(1.0D, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, +0.0D), new Vector3D(0.0D, 0.0D, +1.0D)), 0.0D, 2.0D));
		assertEquals(1.0D, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, +0.0D), new Vector3D(0.0D, 0.0D, -1.0D)), 0.0D, 2.0D));
		assertEquals(1.0D, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, +2.0D), new Vector3D(0.0D, 0.0D, -1.0D)), 0.0D, 2.0D));
		assertEquals(1.0D, sphere.intersectionT(new Ray3D(new Point3D(0.0D, 0.0D, -2.0D), new Vector3D(0.0D, 0.0D, +1.0D)), 0.0D, 2.0D));
		
		assertThrows(NullPointerException.class, () -> sphere.intersectionT(null, 0.0D, 0.0D));
	}
	
//	@Test
//	public void testIntersects() {
//		
//	}
	
//	@Test
//	public void testSamplePoint2D() {
//		
//	}
	
	@Test
	public void testToString() {
		final Sphere3D sphere = new Sphere3D();
		
		assertEquals("new Sphere3D()", sphere.toString());
	}
	
	@Test
	public void testWrite() {
		final Sphere3D a = new Sphere3D();
		
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		final DataOutput dataOutput = new DataOutputStream(byteArrayOutputStream);
		
		a.write(dataOutput);
		
		final byte[] bytes = byteArrayOutputStream.toByteArray();
		
		final Sphere3D b = new Sphere3DReader().read(new DataInputStream(new ByteArrayInputStream(bytes)));
		
		assertEquals(a, b);
		
		assertThrows(NullPointerException.class, () -> a.write((DataOutput)(null)));
		assertThrows(UncheckedIOException.class, () -> a.write(new DataOutputMock()));
	}
}