package blas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JBlasBLASTest {

	// daxpy data
	JavaMatrix x1 = null;
	JavaMatrix y1 = null;
	JavaMatrix y1original = null;

	// dgemv data
	JavaMatrix A = null;
	JavaMatrix x2 = null;
	JavaMatrix y2 = null;
	JavaMatrix y2original = null;

	// JavaMatrix x1 = JavaMatrix.createRandomInt10(2, 1);
	// JavaMatrix y1 = JavaMatrix.createRandomInt10(2, 1);

	/*
	 * JavaMatrix A = JavaMatrix.createRandomDouble01(3, 2); JavaMatrix x2 =
	 * JavaMatrix.createRandomDouble01(2, 1); JavaMatrix y2 =
	 * JavaMatrix.createRandomDouble01(2, 1);
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void before() {

		// init daxpy
		this.x1 = JavaMatrix.createRandomDouble01(3, 1, MatrixOrder.ColMajor);
		this.y1 = JavaMatrix.createRandomDouble01(3, 1, MatrixOrder.ColMajor);
		this.y1original = y1.copy();

		// init dgemv
		this.A = JavaMatrix.createRandomDouble01(110, 100, MatrixOrder.ColMajor);
		this.x2 = JavaMatrix.createRandomDouble01(100, 1, MatrixOrder.ColMajor);
		this.y2 = JavaMatrix.createRandomDouble01(110, 1, MatrixOrder.ColMajor);
		this.y2original = y2.copy();

		// init dgemm
		
	}

	@Test
	public void testDaxpy() {
		try {
			JBlasBLAS jbb = new JBlasBLAS();
			jbb.daxpy(this.x1.rows(), 1.5, this.x1.data, 1, this.y1.data, 1);
			assertEquals(1.5*this.x1.data[0] + this.y1original.data[0], this.y1.data[0], 0.00001);
		} finally {
			System.out.println("\n\n========== DAXPY ==========\n");
			System.out.println("x1:\n" + this.x1 + "\n");
			System.out.println("y1original:\n" + this.y1original + "\n");
			System.out.println("y1:\n" + this.y1 + "\n");
		}
	}

	@Test
	public void testDgemv() {
		try {
			JBlasBLAS jbb = new JBlasBLAS();
			jbb.dgemv(0, 'N', this.A.rows(), this.A.columns(), 1, this.A.data,
					this.A.rows(), this.x2.data, 1, 1, this.y2.data, 1);
			
			double expected = 0.0;
			for (int i = 0; i < this.A.columns(); i++) {
				expected += this.A.get(0, i)*this.x2.data[i];
			}
			expected += this.y2original.data[0];

			assertEquals(expected, this.y2.data[0], 0.00001);
		} finally {
			System.out.println("\n\n========== DGEMV ==========\n");
			System.out.println("A:\n" + this.A + "\n");
			System.out.println("x2:\n" + this.x2 + "\n");
			System.out.println("y2original:\n" + this.y2original + "\n");
			System.out.println("y2:\n" + this.y2 + "\n");
		}

	}

	@Ignore
	public void testDgemm() {
		fail("Not yet implemented");
	}

}
